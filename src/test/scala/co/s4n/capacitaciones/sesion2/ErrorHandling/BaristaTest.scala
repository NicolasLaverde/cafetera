package co.s4n.capacitaciones.sesion2.ErrorHandling

import co.s4n.capacitaciones.sesion2.ErrorHandling.service.BaristaService
import org.scalatest.FunSuite

import scala.concurrent.Await
import scala.concurrent.duration.Duration
import scala.concurrent.ExecutionContext.Implicits.global

/**
 * Created by daniel on 10/03/17.
 */
class BaristaTest extends FunSuite {

  test("probando el main...") {
    println("Empezando con el barista...")
    val barista: BaristaService = BaristaService(150)
    val await = Await.result(Barista.prepararCafe(barista), Duration.Inf)
    println(await)

    assert(true)
  }

}
