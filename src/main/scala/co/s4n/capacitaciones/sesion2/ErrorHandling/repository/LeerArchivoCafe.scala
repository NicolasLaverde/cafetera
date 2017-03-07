package co.s4n.capacitaciones.sesion2.ErrorHandling.repository

import java.io.{ BufferedWriter, File, FileWriter, InputStream }

import co.s4n.capacitaciones.sesion2.ErrorHandling.{ CafeGrano, Ingrediente }

/**
 * Created by daniel on 6/03/17.
 */
case class LeerArchivoCafe() {

  private val archivo: String = "/inventarioCafe.txt"

  private def leerArchivoIngredientes(): (String, List[Array[String]]) = {
    val stream: InputStream = getClass.getResourceAsStream(archivo)
    val listaIngredientes: List[String] = scala.io.Source.fromInputStream(stream).getLines.toList
    val ingredientesOrdenados: List[Array[String]] = listaIngredientes.tail.map(x => x.split(","))
    (listaIngredientes.head, ingredientesOrdenados)
  }

  def editarIngrediente(cafe: CafeGrano): CafeGrano = {

    val (titulo: String, datos: List[Array[String]]) = leerArchivoIngredientes()
    val cafeGranoList: List[CafeGrano] = datos.map(x => CafeGrano(x.head, x(1).toInt))
    val filtro: List[CafeGrano] = cafeGranoList.filter(x => x.origen == cafe.origen)

    if (filtro.head.cantidad - cafe.cantidad >= 0) {
      escribirArchivo(crearListaEscribir(titulo, datos, cafe.origen, filtro.head.cantidad - cafe.cantidad))
      CafeGrano(cafe.origen, filtro.head.cantidad - cafe.cantidad)
    } else {
      cafe
    }
  }

  def crearListaEscribir(titulo: String, datos: List[Array[String]], origen: String, cantidad: Double): List[String] = {
    List(titulo) ::: datos.filterNot(x => x.head.equals(origen))
      .map(x => x.head.concat(",").concat(x(1))) ::: List(origen.concat(",").concat(cantidad.toString))
  }

  def escribirArchivo(texto: List[String]): Unit = {

    val file = new File(archivo)
    val bw = new BufferedWriter(new FileWriter("./src/main/resources/inventarioCafe2.txt"))
    texto.foreach(x => bw.write(x + "\n"))
    bw.close()
  }

}

