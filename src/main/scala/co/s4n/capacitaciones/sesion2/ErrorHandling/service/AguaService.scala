package co.s4n.capacitaciones.sesion2.ErrorHandling.service

import co.s4n.capacitaciones.sesion2.ErrorHandling.{ Agua, CafeGrano }
import co.s4n.capacitaciones.sesion2.ErrorHandling.repository.GestorArchivo

import scala.util.{ Failure, Success }

case class AguaService() extends IngredienteService[Agua] {

  private val archivoAgua: String = "/inventarioAgua.txt"
  private val archivoSalidaAgua: String = "./src/main/resources/inventarioAgua2.txt"

  def editar(ingrediente: Agua): Option[Agua] = {
    editarUnIngrediente(ingrediente)
  }

  def editarUnIngrediente(ingrediente: Agua)(): Option[Agua] = {
    GestorArchivo().leerArchivo(archivoLectura(ingrediente)) match {
      case Success((titulo, datos)) =>
        val (filtro: List[Agua], noFiltro: List[Agua]) =
          filtrarIngrediente(ingrediente, datos.map(x => crearIngrediente(x.head, x(1))))
        escribirArchivoIngrediente(titulo, seEdita(ingrediente, filtro.head), noFiltro)
      case Failure(f) => None
    }

    //val (titulo: String, datos: List[Array[String]]) = GestorArchivo().leerArchivo(archivoLectura(ingrediente))
    //val ingredienteList: List[Agua] = datos.map(x => crearIngrediente(x.head, x(1)))
    //val (filtro: List[Agua], noFiltro: List[Agua]) = filtrarIngrediente(ingrediente, ingredienteList)

    //escribirArchivoIngrediente(titulo, seEdita(ingrediente, filtro.head), noFiltro)
  }

  def crearIngrediente(a: String, b: String): Agua = {
    Agua(a.toInt, b.toDouble)
  }

  def filtrarIngrediente(ingrediente: Agua, lista: List[Agua]): (List[Agua], List[Agua]) = {
    val list: List[Agua] = List(Agua(ingrediente.temperatura, lista.map(x => x.cantLitros).sum - ingrediente.cantLitros))
    (list, list)
  }

  //  def filtrar(ingrediente1: Agua, ingrediente2: Agua): Boolean = {
  //  }

  def seEdita(a: Agua, b: Agua): Agua = {
    if (b.cantLitros - a.cantLitros >= 0)
      Agua(a.temperatura, b.cantLitros - a.cantLitros)
    else
      a
  }

  def escribirArchivoIngrediente(titulo: String, ingrediente: Agua, lista: List[Agua]): Option[Agua] = {
    GestorArchivo().escribir(titulo :: lista.map(x => x.toString)
      ++ List(ingrediente.toString), archivoEscritura(ingrediente)) match {
      case Success(s) => Option(ingrediente)
      case Failure(f) => None
    }

    //GestorArchivo().escribir(titulo :: lista.map(x => x.toString), archivoEscritura(ingrediente))
    //ingrediente
  }

  def archivoLectura(ingrediente: Agua): String = {
    archivoAgua
  }

  def archivoEscritura(ingrediente: Agua): String = {
    archivoSalidaAgua
  }

}