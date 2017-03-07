package co.s4n.capacitaciones.sesion2.ErrorHandling.repository

import co.s4n.capacitaciones.sesion2.ErrorHandling.{ Agua, Ingrediente }

/**
 * Created by daniel on 6/03/17.
 */
case class LeerArchivoAgua() {

  private def leerArchivoIngredientes(rutaArchivo: String): List[Array[String]] = {
    val listaIngredientes = io.Source.fromFile(rutaArchivo).getLines().toList.tail
    val ingredientesOrdenados = listaIngredientes.map(x => x.split(","))
    ingredientesOrdenados
  }

  def escribirArchivoIngredientes() = {

  }

  def editarIngrediente(ing: Ingrediente, archivo: String): Agua = {
    //Leer ingredientes
    leerArchivoIngredientes(archivo)

    Agua(0, 0)
  }
}

