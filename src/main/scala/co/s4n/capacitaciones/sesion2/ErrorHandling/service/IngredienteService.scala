package co.s4n.capacitaciones.sesion2.ErrorHandling.service

import co.s4n.capacitaciones.sesion2.ErrorHandling.Ingrediente
import co.s4n.capacitaciones.sesion2.ErrorHandling.repository.GestorInventario

import scala.util.{ Failure, Success }

trait IngredienteService[A <: Ingrediente] {

  def prepararIngrediente(ingrediente: A): Option[A] = {
    GestorInventario.leerInventario(obtenerArchivoLectura(ingrediente)) match {
      case Success((titulo: String, datos: List[Array[String]])) =>

        for {
          ingredientes: List[A] <- agruparInventario(datos)
          (filtro: List[A], noFiltro: List[A]) <- validarInventario(ingrediente, ingredientes)
          editado <- seEdita(ingrediente, filtro.head)
          escribio <- actualizarInventarioIngrediente(titulo, editado, noFiltro)
        } yield escribio

      case Failure(_) => None
    }
  }

  def crearIngrediente(a: String, b: String): A

  def agruparInventario(ingredientes: List[Array[String]]): Option[List[A]]

  def validarInventario(ingrediente: A, lista: List[A]): Option[(List[A], List[A])]

  def seEdita(a: A, b: A): Option[A]

  def actualizarInventarioIngrediente(titulo: String, ingrediente: A, lista: List[A]): Option[A]

  def obtenerArchivoLectura(ingrediente: A): String

  def obtenerArchivoEscritura(ingrediente: A): String

}