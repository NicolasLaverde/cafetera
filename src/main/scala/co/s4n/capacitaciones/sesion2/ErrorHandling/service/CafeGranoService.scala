package co.s4n.capacitaciones.sesion2.ErrorHandling.service

import co.s4n.capacitaciones.sesion2.ErrorHandling.CafeGrano
import co.s4n.capacitaciones.sesion2.ErrorHandling.repository.GestorInventario

import scala.util.{ Failure, Success }

case object CafeGranoService extends IngredienteService[CafeGrano] {

  private val archivoCafe: String = "/inventarioCafe.txt"
  private val archivoSalidaCafe: String = "./src/main/resources/inventarioCafe2.txt"

  def agruparInventario(lista: List[Array[String]]): Option[List[CafeGrano]] = {
    if (lista.isEmpty) None
    else {
      val cafes = lista.map(x => crearIngrediente(x.head, x(1)))

      val cafeOrigen: Map[String, List[CafeGrano]] = cafes.groupBy(cafe => cafe.origen)
      val totalCafeOrigen: Map[String, Double] = cafeOrigen.mapValues(l => l.map(c => c.cantidad).sum)
      val agrupadoMap = totalCafeOrigen.toList
      Option(agrupadoMap.map(cafe => crearIngrediente(cafe._1, cafe._2.toString)))
    }
  }

  def crearIngrediente(a: String, b: String): CafeGrano = {
    CafeGrano(a, b.toDouble)
  }

  def validarInventario(ingrediente: CafeGrano, lista: List[CafeGrano]): Option[(List[CafeGrano], List[CafeGrano])] = {
    if (lista.isEmpty) None
    else {
      Option(lista.span(x => filtrar(ingrediente, x)))
    }

  }

  def filtrar(ingrediente1: CafeGrano, ingrediente2: CafeGrano): Boolean = {
    ingrediente1.origen == ingrediente2.origen
  }

  def seEdita(a: CafeGrano, b: CafeGrano): Option[CafeGrano] = {
    if (b.cantidad - a.cantidad >= 0) Option(CafeGrano(a.origen, b.cantidad - a.cantidad))
    else None
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