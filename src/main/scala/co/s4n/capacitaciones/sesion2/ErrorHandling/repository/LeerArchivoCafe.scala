package co.s4n.capacitaciones.sesion2.ErrorHandling.repository

import co.s4n.capacitaciones.sesion2.ErrorHandling.{CafeGrano, Ingrediente}

import scala.io.Source

/**
 * Created by daniel on 6/03/17.
 */
case class LeerArchivoCafe() {

  private def leerArchivoIngredientes(rutaArchivo: String): String = {
    io.Source.fromFile(rutaArchivo).mkString
  }

  def manipularArchivo() = {
    val lista = leerArchivoIngredientes("").tail //TODO definir la ruta del archivo
  }

  def prepararIngrediente(cafe: CafeGrano) : CafeGrano = {

    val texto: List[String] = Source.fromFile(
      "").getLines().toList
    //Se hace split de todas las lineas
    val splitted: List[Array[String]] = texto.map(x => x.split(","))
    //Se filtra el ingrediente que se tiene y se suma o resta su cantidad
    val ingredienteCambiado: List[String] = splitted.filter(x => x(0) == cafe.origen).map(y => y(1) + cafe.cantidad)

  }

}

