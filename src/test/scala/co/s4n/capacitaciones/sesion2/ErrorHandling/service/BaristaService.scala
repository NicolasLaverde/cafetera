package co.s4n.capacitaciones.sesion2.ErrorHandling.service

import co.s4n.capacitaciones.sesion2.ErrorHandling.{ Agua, CafeGrano, CafeMolido, Leche }
import org.scalatest.FunSuite

/**
 * Created by daniel on 12/03/17.
 */
class BaristaServiceTest extends FunSuite {

  test("Preparar Ingredientes sin pasar ingredientes") {
    val listaIngredientes = List()
    val tiempoEspera = 150
    val barista: BaristaService = BaristaService(150)
    val ingredientesPreparados = barista.prepararIngredientes(listaIngredientes)

    assert(ingredientesPreparados._1.isEmpty)
    assert(ingredientesPreparados._2.isEmpty)
  }

  test("Revisar Inventario: Agua") {
    val agua = Agua(10, 15)
    val barista: BaristaService = BaristaService(150)
    val resultado = barista.revisarInventario(agua)

    assert(resultado.isDefined)
  }

  test("Revisar Inventario: CafeGrano") {
    val cafe = CafeGrano("Manizales", 15)
    val barista: BaristaService = BaristaService(150)
    val resultado = barista.revisarInventario(cafe)

    assert(resultado.isDefined)
  }

  test("Revisar Inventario: Leche") {
    val leche = Leche(10, 10, "Deslactosada")
    val barista: BaristaService = BaristaService(150)
    val resultado = barista.revisarInventario(leche)

    assert(resultado.isEmpty)
  }

  test("Revisar Inventario: CafeMolido") {
    val cafe = CafeGrano("Manizales", 15)
    val cafeMolido = CafeMolido(10d, cafe)
    val barista: BaristaService = BaristaService(150)
    val resultado = barista.revisarInventario(cafeMolido)

    assert(resultado.isEmpty)
  }

  test("Moler -> Some") {
    val cafe = CafeGrano("Manizales", 15)
    val barista: BaristaService = BaristaService(150)
    val resultado = barista.moler(Option(cafe))

    assert(resultado.isDefined)
  }

  test("Moler -> None") {
    val barista: BaristaService = BaristaService(150)
    val resultado = barista.moler(None)

    assert(resultado.isEmpty)
  }

  test("Calentar -> Some") {
    val agua = Agua(10, 15.00)
    val barista: BaristaService = BaristaService(150)
    val resultado = barista.calentar(Option(agua))

    assert(resultado.isDefined)
  }

  test("Calentar -> None") {
    val barista: BaristaService = BaristaService(150)
    val resultado = barista.calentar(None)

    assert(resultado.isEmpty)
  }

  test("Verificar Temperatura Left") {
    val temperatura = 20d
    val barista: BaristaService = BaristaService(150)
    val resultado = barista.verificarTemperatura(temperatura)

    assert(resultado.isLeft)
  }

  test("Verificar Temperatura Right") {
    val temperatura = 50d
    val barista: BaristaService = BaristaService(150)
    val resultado = barista.verificarTemperatura(temperatura)

    assert(resultado.isRight)
  }

  test("Preparar isDefined") {
    val barista: BaristaService = BaristaService(150)
    val cafe = CafeGrano("Manizales", 15)
    val cafeMolido = CafeMolido(10d, cafe)
    val agua = Agua(10, 15.00)
    val resultado = barista.preparar(Option(cafeMolido), Option(agua))

    assert(resultado.isDefined)
  }

  test("Preparar isEmpty") {
    val barista: BaristaService = BaristaService(150)
    val resultado = barista.preparar(None, None)

    assert(resultado.isEmpty)
  }

}
