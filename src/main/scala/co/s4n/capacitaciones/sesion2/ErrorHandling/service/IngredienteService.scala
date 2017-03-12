package co.s4n.capacitaciones.sesion2.ErrorHandling.service

import co.s4n.capacitaciones.sesion2.ErrorHandling.Ingrediente

trait IngredienteService[A <: Ingrediente] {

  def prepararIngrediente(ingrediente: A): Option[A]

  def crearIngrediente(a: String, b: String): A

  def agruparInventario(ingredientes: List[A]): List[A]

  def validarInventario(ingrediente: A, lista: List[A]): (List[A], List[A])

  def seEdita(a: A, b: A): A

  def actualizarInventarioIngrediente(titulo: String, ingrediente: A, lista: List[A]): Option[A]

  def obtenerArchivoLectura(ingrediente: A): String

  def obtenerArchivoEscritura(ingrediente: A): String

}