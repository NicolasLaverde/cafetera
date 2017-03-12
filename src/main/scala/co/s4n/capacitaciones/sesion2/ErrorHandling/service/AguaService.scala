package co.s4n.capacitaciones.sesion2.ErrorHandling.service

import co.s4n.capacitaciones.sesion2.ErrorHandling.Agua
import co.s4n.capacitaciones.sesion2.ErrorHandling.repository.GestorInventario

import scala.util.{ Failure, Success }

case object AguaService extends IngredienteService[Agua] {

  private val archivoAgua: String = "/inventarioAgua.txt"
  private val archivoSalidaAgua: String = "./src/main/resources/inventarioAgua2.txt"

  def prepararIngrediente(ingrediente: Agua): Option[Agua] = {
    GestorInventario.leerInventario(obtenerArchivoLectura(ingrediente)) match {
      case Success((titulo, datos)) =>
        val agua: List[Agua] = agruparInventario(datos.map(x => crearIngrediente(x.head, x(1))))
        val (filtro: List[Agua], noFiltro: List[Agua]) = validarInventario(ingrediente, agua)
        val editado: Agua = seEdita(ingrediente, filtro.head)
        actualizarInventarioIngrediente(titulo, editado, noFiltro)
      case Failure(_) => None
    }
  }

  def agruparInventario(lista: List[Agua]): List[Agua] = {
    List(Agua(lista.head.temperatura, lista.map(x => x.cantLitros).sum))
  }

  def crearIngrediente(a: String, b: String): Agua = {
    Agua(a.toInt, b.toDouble)
  }

  def validarInventario(ingrediente: Agua, lista: List[Agua]): (List[Agua], List[Agua]) = {
    val list: List[Agua] = List(Agua(ingrediente.temperatura, lista.map(x => x.cantLitros).sum - ingrediente.cantLitros))
    (list, list)
  }

  def seEdita(a: Agua, b: Agua): Agua = {
    if (b.cantLitros - a.cantLitros >= 0)
      Agua(a.temperatura, b.cantLitros - a.cantLitros)
    else
      b
  }

  def actualizarInventarioIngrediente(titulo: String, ingrediente: Agua, lista: List[Agua]): Option[Agua] = {
    GestorInventario.escribirInventario(titulo :: List(ingrediente.toString()), obtenerArchivoEscritura(ingrediente)) match {
      case Success(_) => Option(ingrediente)
      case Failure(_) => None
    }
  }

  def obtenerArchivoLectura(ingrediente: Agua): String = {
    archivoAgua
  }

  def obtenerArchivoEscritura(ingrediente: Agua): String = {
    archivoSalidaAgua
  }
}