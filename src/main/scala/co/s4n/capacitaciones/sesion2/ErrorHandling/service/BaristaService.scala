package co.s4n.capacitaciones.sesion2.ErrorHandling.service

import co.s4n.capacitaciones.sesion2.ErrorHandling._

import scala.util.Random

/**
 * Created by daniel on 9/03/17.
 */
case class BaristaService(tiempoEspera: Int) {

  def random(): Unit = Thread.sleep(Random.nextInt(tiempoEspera))

  def editarIngrediente[A <: Ingrediente](ing: A): Ingrediente =
    ing match {

      case CafeGrano(origen, cantidad) =>
        CafeGranoService().editar(CafeGrano(origen, cantidad))

      case Agua(temperatura, cantLitros) =>
        AguaService().editar(Agua(temperatura, cantLitros))

      case Leche(temperatura, cantLitros, tipoLeche) => Leche(temperatura, cantLitros, tipoLeche)

      case CafeMolido(cantidad, cafeGrano) => CafeMolido(cantidad, cafeGrano)
    }

  def prepararIngredientesCafe(ingredientes: List[Ingrediente]): (Agua, CafeGrano) = {

    val list: List[Ingrediente] = ingredientes map (i => editarIngrediente(i))
    if (list.size == 2) {
      (list.head, list(1)) match {
        case (Agua(temperatura, cantLitros), CafeGrano(origen, cantidad)) =>
          (Agua(temperatura, cantLitros), CafeGrano(origen, cantidad))
        case _ => (Agua(0, 0), CafeGrano("", 0))
      }
    } else (Agua(0, 0), CafeGrano("", 0))
  }

  def moler(granos: CafeGrano): CafeMolido = {
    random()
    CafeMolido(Random.nextInt(granos.cantidad.toInt + 1), granos)
  }

  def calentar(agua: Agua): Agua = {
    val temperatura: Int = Random.nextInt(100)
    verificarTemperatura(temperatura) match {
      case Right(_) => {
        Agua(agua.temperatura + temperatura, agua.cantLitros / (temperatura / agua.temperatura))

      }
      case Left(s) =>
        println(s)
        calentar(agua)
    }
  }

  def verificarTemperatura(temperatura: Double): Either[String, Double] = {
    if (temperatura >= 50d && temperatura < 80d) Right(temperatura)
    else Left("La temperatura no está bien")
  }

  def preparar(cafe: CafeMolido, aguaCaliente: Agua): Cafe = {
    Cafe(List(cafe, aguaCaliente))
  }
}
