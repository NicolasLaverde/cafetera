package co.s4n.capacitaciones.sesion2.ErrorHandling.repository

import co.s4n.capacitaciones.sesion2.ErrorHandling.{ CafeGrano, Ingrediente }

import scala.io.Source

/**
 * Created by daniel on 6/03/17.
 */
case class LeerArchivoCafe() {

  private def leerArchivoIngredientes(rutaArchivo: String): List[Array[String]] = {
    val listaIngredientes = io.Source.fromFile(rutaArchivo).getLines().toList.tail
    val ingredientesOrdenados = listaIngredientes.map(x => x.split(","))
    ingredientesOrdenados
  }

  def manipularArchivo() = {
    val lista = leerArchivoIngredientes("").tail //TODO definir la ruta del archivo
  }

  def editarIngrediente(cafe: CafeGrano, archivo: String): CafeGrano = {

    leerArchivoIngredientes(archivo).filter(x => x(0) == cafe.origen).map(y => y(1) + cafe.cantidad)

    CafeGrano("", 0)
  }

}

