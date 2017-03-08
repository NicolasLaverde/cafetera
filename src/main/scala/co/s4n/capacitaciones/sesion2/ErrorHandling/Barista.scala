package co.s4n.capacitaciones.sesion2.ErrorHandling

import co.s4n.capacitaciones.sesion2.ErrorHandling.repository.{ LeerArchivoAgua, LeerArchivoCafe }

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.{ Failure, Random, Success }

case class Barista(tiempoEspera: Int) {

  def random(): Unit = Thread.sleep(Random.nextInt(tiempoEspera))

  def editarIngrediente[A <: Ingrediente](ing: A): Ingrediente =

    ing match {

      case CafeGrano(origen, cantidad) =>
        LeerArchivoCafe().editar(CafeGrano(origen, cantidad))

      case Agua(temperatura, cantLitros) =>
        //LeerArchivoAgua().editarIngrediente(Agua(temperatura, cantLitros))
        //Agua(temperatura, cantLitros)
        LeerArchivoCafe().editar(Agua(temperatura, cantLitros))

      case Leche(temperatura, cantLitros, tipoLeche) => Leche(temperatura, cantLitros, tipoLeche)

      case CafeMolido(cantidad, cafeGrano) => CafeMolido(cantidad, cafeGrano)
    }

  /*  def prepararIngredientes[A <: Ingrediente](ingredientes: List[Ingrediente]): List[ /*A*/ Ingrediente] = {

    ingredientes map (i => editarIngrediente(i))
  }*/

  def prepararIngredientesCafe(ingredientes: List[Ingrediente]): (Agua, CafeGrano) = {

    val list: List[Ingrediente] = ingredientes map (i => editarIngrediente(i))
    if (list.size == 2) {
      (list.head, list(1)) match {
        case (Agua(temperatura, cantLitros), CafeGrano(origen, cantidad)) => (Agua(temperatura, cantLitros), CafeGrano(origen, cantidad))
        case _ => (Agua(0, 0), CafeGrano("", 0))
      }
    } else (Agua(0, 0), CafeGrano("", 0))
  }

  def moler[A <: Ingrediente](granos: CafeGrano /*A*/ ): Future[CafeMolido] = Future {
    random()
    CafeMolido(Random.nextInt(granos.cantidad.toInt + 1), granos)
  }

  def calentar(agua: Agua): Future[Option[Agua]] = {
    random()
    val temperatura: Int = Random.nextInt(30)
    verificarTemperatura(temperatura) match {
      case Right(r) =>
        val agua2 = Agua(agua.temperatura + temperatura, agua.cantLitros / (temperatura / agua.temperatura))
        Future(Option(agua2))

      case Left(l) => calentar(agua)
    }
  }

  def verificarTemperatura(temperatura: Double): Either[String, Double] = {
    if (temperatura >= 50d && temperatura < 80d) Right(temperatura)
    else Left("La temperatura no está bien")
  }

  def preparar(cafe: CafeMolido, aguaCaliente: Agua): Future[Cafe] = Future {
    random()
    Cafe(List(cafe, aguaCaliente))
  }
}

object Barista {
  def prepararCafe(barista: Barista): Future[Cafe] = {

    for {
      (agua, granos) <- Future(barista.prepararIngredientesCafe(List(Agua(15, 5), CafeGrano("Manizales", 12))))
      cafeMolido <- barista.moler(granos)
      aguaCaliente <- barista.calentar(agua)
      cafePreparado: Cafe <- barista.preparar(cafeMolido, aguaCaliente.get)
    } yield cafePreparado
  }

  def main(args: Array[String]): Unit = {
    val barista: Barista = Barista(150)
    Barista.prepararCafe(barista) onComplete {
      case Success(s) => println("Termino de forma exitosa ")
      case Failure(e) => println("Termino con error....... ")
    }

  }

}
