package co.s4n.capacitaciones.sesion2.ErrorHandling

import co.s4n.capacitaciones.sesion2.ErrorHandling.service.{ AguaService, CafeGranoService }

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
import scala.concurrent.{ Await, Future }
import scala.util.{ Failure, Random, Success }

case class Barista(tiempoEspera: Int) {

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
        case (Agua(temperatura, cantLitros), CafeGrano(origen, cantidad)) => (Agua(temperatura, cantLitros), CafeGrano(origen, cantidad))
        case _ => (Agua(0, 0), CafeGrano("", 0))
      }
    } else (Agua(0, 0), CafeGrano("", 0))
  }

  def moler(granos: CafeGrano): Option[CafeMolido] = {
    random()
    Option(CafeMolido(Random.nextInt(granos.cantidad.toInt + 1), granos))
  }

  def calentar(agua: Agua): Option[Agua] = {
    val temperatura: Int = Random.nextInt(100)
    verificarTemperatura(temperatura) match {
      case Right(_) => {
        val agua2 = Agua(agua.temperatura + temperatura, agua.cantLitros / (temperatura / agua.temperatura))
        Option(agua2)
      }

      case Left(_) =>
        println("\nCalentar agua ha fallado\n")
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

object Barista {
  def prepararCafe(barista: Barista): Future[Cafe] = {
    (for {
      (agua, granos) <- Future(barista.prepararIngredientesCafe(List(Agua(15, 5), CafeGrano("Manizales", 12))))
      cafeMolido <- Future(barista.moler(granos))
      aguaCaliente <- Future(barista.calentar(agua))
    } yield {
      for {
        xxx <- cafeMolido
        yyy <- aguaCaliente
      } yield barista.preparar(xxx, yyy)
    }).flatMap {
      case Some(cafe) => Future.successful(cafe)
      case None => Future.failed(new Exception("Error"))
    }
  }

  def main(args: Array[String]): Unit = {
    println("Empezando con el barista...")
    val barista: Barista = Barista(150)
    val await = Await.result(Barista.prepararCafe(barista), Duration.Inf)
    println(await)
    //    Barista.prepararCafe(barista) onComplete {
    //      case Success(cafe) => println("Termino de forma exitosa ")
    //      case Failure(error) => println("Termino con error....... ")
    //    }

  }

}
