package co.s4n.capacitaciones.sesion2.ErrorHandling.service

import co.s4n.capacitaciones.sesion2.ErrorHandling.CafeGrano
import co.s4n.capacitaciones.sesion2.ErrorHandling.repository.GestorArchivo

import scala.util.{ Failure, Success }

case class CafeGranoService() extends IngredienteService[CafeGrano] {

  private val archivoCafe: String = "/inventarioCafe1.txt"
  private val archivoSalidaCafe: String = "./src/main/resources/inventarioCafe2.txt"

  def editar(ingrediente: CafeGrano): Option[CafeGrano] = {
    editarUnIngrediente(ingrediente)
  }

  def editarUnIngrediente(ingrediente: CafeGrano)(): Option[CafeGrano] = {

    GestorArchivo().leerArchivo(archivoLectura(ingrediente)) match {
      case Success((titulo, datos)) =>
        val (filtro: List[CafeGrano], noFiltro: List[CafeGrano]) =
          filtrarIngrediente(ingrediente, datos.map(x => crearIngrediente(x.head, x(1))))
        escribirArchivoIngrediente(titulo, seEdita(ingrediente, filtro.head), noFiltro)
      case Failure(f) => None
    }

    //val (titulo: String, datos: List[Array[String]]) = GestorArchivo().leerArchivo(archivoLectura(ingrediente))
    //val ingredienteList: List[CafeGrano] = datos.map(x => crearIngrediente(x.head, x(1)))
    //val (filtro: List[CafeGrano], noFiltro: List[CafeGrano]) = filtrarIngrediente(ingrediente, ingredienteList)

    //escribirArchivoIngrediente(titulo, seEdita(ingrediente, filtro.head), noFiltro)
  }

  def crearIngrediente(a: String, b: String): CafeGrano = {
    CafeGrano(a, b.toInt)
  }

  def filtrarIngrediente(ingrediente: CafeGrano, lista: List[CafeGrano]): (List[CafeGrano], List[CafeGrano]) = {
    (lista.filter(x => filtrar(ingrediente, x)), lista.filterNot(x => filtrar(ingrediente, x)))
  }

  def filtrar(ingrediente1: CafeGrano, ingrediente2: CafeGrano): Boolean = {
    ingrediente1.origen == ingrediente2.origen
  }

  def seEdita(a: CafeGrano, b: CafeGrano): CafeGrano = {
    if (b.cantidad - a.cantidad >= 0) CafeGrano(a.origen, b.cantidad - a.cantidad)
    else a
  }

  def escribirArchivoIngrediente(titulo: String, ingrediente: CafeGrano, lista: List[CafeGrano]): Option[CafeGrano] = {
    GestorArchivo().escribir(titulo :: lista.map(x => x.toString)
      ++ List(ingrediente.toString), archivoEscritura(ingrediente)) match {
      case Success(s) => Option(ingrediente)
      case Failure(f) => None
    }

  }

  def archivoLectura(ingrediente: CafeGrano): String = {
    archivoCafe
  }

  def archivoEscritura(ingrediente: CafeGrano): String = {
    archivoSalidaCafe
  }
}