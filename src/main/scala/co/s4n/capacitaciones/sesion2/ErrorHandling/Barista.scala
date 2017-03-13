package co.s4n.capacitaciones.sesion2.ErrorHandling

import co.s4n.capacitaciones.sesion2.ErrorHandling.service.BaristaService

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
import scala.concurrent.{ Await, Future }

object Barista {
  def prepararCafe(barista: BaristaService): Future[Cafe] = {
    for {
      (agua, granos) <- Future(barista.prepararIngredientes(List(Agua(15, 5), CafeGrano("Manizales", 21))))
      cafeMolido <- Future(barista.moler(granos))
      aguaCaliente <- Future(barista.calentar(agua))
    } yield barista.preparar(cafeMolido, aguaCaliente)
  }.flatMap {
    case Some(cafe) => Future.successful(cafe)
    case None => Future.failed(new Exception("No se pudo hacer bien el café..."))
  }

  def main(args: Array[String]): Unit = {
    println("Empezando con el barista...")
    val barista: BaristaService = BaristaService(150)
    val await = Await.result(Barista.prepararCafe(barista), Duration.Inf)
    println(await)
  }
}

