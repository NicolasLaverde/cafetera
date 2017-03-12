package co.s4n.capacitaciones.sesion2.ErrorHandling.service

import co.s4n.capacitaciones.sesion2.ErrorHandling.CafeGrano
import co.s4n.capacitaciones.sesion2.ErrorHandling.repository.GestorInventario

import scala.util.{ Failure, Success }

case object CafeGranoService extends IngredienteService[CafeGrano] {

  private val archivoCafe: String = "/inventarioCafe.txt"
  private val archivoSalidaCafe: String = "./src/main/resources/inventarioCafe2.txt"

  def prepararIngrediente(ingrediente: CafeGrano): Option[CafeGrano] = {
    GestorInventario.leerInventario(obtenerArchivoLectura(ingrediente)) match {
      case Success((titulo, datos)) =>
        val cafeLista: List[CafeGrano] = agruparInventario(datos.map(x => crearIngrediente(x.head, x(1))))
        val (filtro: List[CafeGrano], noFiltro: List[CafeGrano]) = validarInventario(ingrediente, cafeLista)
        val editado: CafeGrano = seEdita(ingrediente, filtro.head)
        actualizarInventarioIngrediente(titulo, editado, noFiltro)
      case Failure(_) => None
    }
  }

  def agruparInventario(lista: List[CafeGrano]): List[CafeGrano] = {
    val cafeOrigen: Map[String, List[CafeGrano]] = lista.groupBy(cafe => cafe.origen)
    val totalCafeOrigen: Map[String, Double] = cafeOrigen.mapValues(l => l.map(c => c.cantidad).sum)
    val agrupadoMap = totalCafeOrigen.toList
    agrupadoMap.map(cafe => crearIngrediente(cafe._1, cafe._2.toString))
  }

  def crearIngrediente(a: String, b: String): CafeGrano = {
    CafeGrano(a, b.toDouble)
  }

  def validarInventario(ingrediente: CafeGrano, lista: List[CafeGrano]): (List[CafeGrano], List[CafeGrano]) = {
    lista.span(x => filtrar(ingrediente, x))
  }

  def filtrar(ingrediente1: CafeGrano, ingrediente2: CafeGrano): Boolean = {
    ingrediente1.origen == ingrediente2.origen
  }

  def seEdita(a: CafeGrano, b: CafeGrano): CafeGrano = {
    if (b.cantidad - a.cantidad >= 0) CafeGrano(a.origen, b.cantidad - a.cantidad)
    else b
  }

  def actualizarInventarioIngrediente(titulo: String, ingrediente: CafeGrano, lista: List[CafeGrano]): Option[CafeGrano] = {
    GestorInventario.escribirInventario(titulo :: lista.map(x => x.toString())
      ++ List(ingrediente.toString()), obtenerArchivoEscritura(ingrediente)) match {
      case Success(_) => Option(ingrediente)
      case Failure(_) => None
    }

  }

  def obtenerArchivoLectura(ingrediente: CafeGrano): String = {
    archivoCafe
  }

  def obtenerArchivoEscritura(ingrediente: CafeGrano): String = {
    archivoSalidaCafe
  }

}