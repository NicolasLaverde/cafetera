package co.s4n.capacitaciones.sesion2.ErrorHandling

import co.s4n.capacitaciones.sesion2.ErrorHandling.service.BaristaService

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
import scala.concurrent.{ Await, Future }

object Barista {
  def prepararCafe(barista: BaristaService): Future[Cafe] = {
    for {
      (agua, granos) <- Future(barista.prepararIngredientesCafe(List(Agua(15, 5), CafeGrano("Manizales", 12))))
      cafeMolido <- Future(barista.moler(granos))
      aguaCaliente <- Future(barista.calentar(agua))
      cafe <- Future(barista.preparar(cafeMolido, aguaCaliente))
    } yield cafe
  }

  def main(args: Array[String]): Unit = {
    println("Empezando con el barista...")
    val barista: BaristaService = BaristaService(150)
    val await = Await.result(Barista.prepararCafe(barista), Duration.Inf)
    println(await)
  }

}
