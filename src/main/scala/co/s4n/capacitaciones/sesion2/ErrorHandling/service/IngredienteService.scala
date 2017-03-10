package co.s4n.capacitaciones.sesion2.ErrorHandling.service

import co.s4n.capacitaciones.sesion2.ErrorHandling.Ingrediente
import co.s4n.capacitaciones.sesion2.ErrorHandling.repository.GestorArchivo

import scala.util.{ Failure, Success }

trait IngredienteService[A <: Ingrediente] {

  def editar(ingrediente: A): Option[A]

  def editarUnIngrediente(ingrediente: A)(): Option[A] = {
    GestorArchivo().leerArchivo(archivoLectura(ingrediente)) match {
      case Success((titulo, datos)) =>
        val (filtro: List[A], noFiltro: List[A]) =
          filtrarIngrediente(ingrediente, datos.map(x => crearIngrediente(x.head, x(1))))
        escribirArchivoIngrediente(titulo, seEdita(ingrediente, filtro.head), noFiltro)
      case Failure(f) => None
    }
  }

  def crearIngrediente(a: String, b: String): A

  def filtrarIngrediente(ingrediente: A, lista: List[A]): (List[A], List[A])

  def seEdita(a: A, b: A): A

  def escribirArchivoIngrediente(titulo: String, ingrediente: A, lista: List[A]): Option[A]

  def archivoLectura(ingrediente: A): String

  def archivoEscritura(ingrediente: A): String

}