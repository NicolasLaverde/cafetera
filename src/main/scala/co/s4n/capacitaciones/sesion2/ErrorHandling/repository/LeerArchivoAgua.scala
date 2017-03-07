package co.s4n.capacitaciones.sesion2.ErrorHandling.repository

import co.s4n.capacitaciones.sesion2.ErrorHandling.{Agua, Ingrediente}

/**
 * Created by daniel on 6/03/17.
 */
case class LeerArchivoAgua() {

  private def leerArchivoIngredientes(rutaArchivo: String): String = {
    io.Source.fromFile(rutaArchivo).mkString
  }

  def escribirArchivoIngredientes() = {

  }

  def prepararIngrediente(ing: Ingrediente) : Agua = ???
}

