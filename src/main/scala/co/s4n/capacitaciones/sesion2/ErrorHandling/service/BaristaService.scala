package co.s4n.capacitaciones.sesion2.ErrorHandling.service

import co.s4n.capacitaciones.sesion2.ErrorHandling._

import scala.util.Random

/**
 * Created by daniel on 9/03/17.
 */
case class BaristaService(tiempoEspera: Int) {

  def random(): Unit = Thread.sleep(Random.nextInt(tiempoEspera))

  def editarIngrediente[A <: Ingrediente](ing: A): Option[Ingrediente] =
    ing match {

      case CafeGrano(origen, cantidad) =>
        CafeGranoService().editar(CafeGrano(origen, cantidad))

      case Agua(temperatura, cantLitros) =>
        AguaService().editar(Agua(temperatura, cantLitros))

      case Leche(_, _, _) => None

      case CafeMolido(_, _) => None
    }

  def prepararIngredientes(ingredientes: List[Ingrediente]): (Agua, CafeGrano) = {

    val optionList: List[Option[Ingrediente]] = ingredientes map (i => editarIngrediente(i))
    val list = optionList.filter(_.isDefined).map(x => x.get)

    list match {
      case Nil => (Agua(1, 1), CafeGrano("", 1)) //TODO validar si hay una mejor forma de controlarlo
      case x :: xs =>
        (list.head, list(1)) match {
          case (Agua(temperatura, cantLitros), CafeGrano(origen, cantidad)) =>
            (Agua(temperatura, cantLitros), CafeGrano(origen, cantidad))
          case _ => (Agua(1, 1), CafeGrano("", 1)) //TODO validar si hay una mejor forma de controlarlo
        }
    }

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
      case Left(_) =>
        calentar(agua)
    }
  }

  def verificarTemperatura(temperatura: Double): Either[String, Double] = {
    if (temperatura >= 50d && temperatura < 80d) {
      Right(temperatura)
    } else Left("La temperatura no está bien")
  }

  def preparar(cafe: CafeMolido, aguaCaliente: Agua): Cafe = {
    Cafe(List(cafe, aguaCaliente))
  }
}
