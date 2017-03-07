package co.s4n.capacitaciones.sesion2.ErrorHandling

import co.s4n.capacitaciones.sesion2.ErrorHandling.repository.{LeerArchivoAgua, LeerArchivoCafe}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.{Failure, Random, Success}

case class Barista(tiempoEspera: Int, rutaArchivo : String) {

  def random(): Unit = Thread.sleep(Random.nextInt(tiempoEspera))

  //TODO : Importar de un archivo .txt los ingredientes necesarios y descontar lo que se utiliza.
  def prepararIngredientes(ingrediente: List[Ingrediente]) = {

    def revisarIngrediente(ing : Ingrediente): Ingrediente = {
      ing match {
        case CafeGrano(origen, cantidad) =>
          LeerArchivoCafe().prepararIngrediente(CafeGrano(origen, cantidad))
        case Agua(temperatura, cantLitros) =>
          LeerArchivoAgua().prepararIngrediente(Agua(temperatura, cantLitros))

      }
    }
    ingrediente.map(_ => revisarIngrediente(_)).toMap[Agua, CafeGrano]
  }

  def moler(granos: CafeGrano): Future[CafeMolido] = Future {
    random()
    CafeMolido(Random.nextInt(granos.cantidad.toInt + 1), granos)
  }

  //TODO : Implementar una función para simular la evaporación y el cambio de temperatura del agua.
  def calentar(agua: Agua): Future[Option[Agua]] = {
    random()
    val temperatura : Int = Random.nextInt(150)
    verificarTemperatura(temperatura) match {
      case Right (r) => {
        val agua2 = Agua(agua.temperatura + temperatura, agua.cantLitros / (temperatura/agua.temperatura))
        Future(Option(agua2))
      }
      case Left (l) => Future(None)
    }
  }

  //TODO: Implementar una función verificarTemperatura que debe usar calentar
  def verificarTemperatura(temperatura: Double): Either[String, Double] = {
    if (temperatura > 50d) Right(temperatura)
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
      (agua, granos) <- Future(barista.prepararIngredientes(List(Agua(15, 5), CafeGrano("Manizales", 12))))
      cafeMolido <- barista.moler(granos)
      aguaCaliente <- barista.calentar(agua)
      cafePreparado: Cafe <- barista.preparar(cafeMolido, aguaCaliente.get)
    } yield cafePreparado
  }

  def main(args: Array[String]): Unit = {
    val barista : Barista = Barista(150, "/inventarioCafe.txt")
    Barista.prepararCafe(barista) onComplete {
      case Success(s) => print(s)
      case Failure(e) => print(e)
    }

  }

}
