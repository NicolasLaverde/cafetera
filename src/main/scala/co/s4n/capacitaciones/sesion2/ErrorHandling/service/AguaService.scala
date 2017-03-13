package co.s4n.capacitaciones.sesion2.ErrorHandling.service

import co.s4n.capacitaciones.sesion2.ErrorHandling.Agua
import co.s4n.capacitaciones.sesion2.ErrorHandling.repository.GestorInventario

import scala.util.{ Failure, Success }

case object AguaService extends IngredienteService[Agua] {

  private val archivoAgua: String = "/inventarioAgua.txt"
  private val archivoSalidaAgua: String = "./src/main/resources/inventarioAgua2.txt"

  def agruparInventario(lista: List[Array[String]]): Option[List[Agua]] = {
    if (lista.isEmpty) None
    else {
      val aguas = lista.map(x => crearIngrediente(x.head, x(1)))
      Option(List(Agua(aguas.head.temperatura, aguas.map(x => x.cantLitros).sum)))
    }
  }

  def crearIngrediente(a: String, b: String): Agua = {
    Agua(a.toInt, b.toDouble)
  }

  def validarInventario(ingrediente: Agua, lista: List[Agua]): Option[(List[Agua], List[Agua])] = {
    if (lista.isEmpty) None
    else {
      val list: List[Agua] = List(Agua(ingrediente.temperatura, lista.map(x => x.cantLitros).sum - ingrediente.cantLitros))
      Option((list, list))
    }
  }

  def seEdita(a: Agua, b: Agua): Option[Agua] = {
    if (b.cantLitros - a.cantLitros >= 0)
      Option(Agua(a.temperatura, b.cantLitros - a.cantLitros))
    else
      None
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