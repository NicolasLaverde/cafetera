package co.s4n.capacitaciones.sesion2.ErrorHandling.repository

import java.io.{ BufferedWriter, FileWriter }

import scala.util.Try

/**
 * Created by daniel on 6/03/17.
 */
case class GestorArchivo() {

  def leerArchivo(archivo: String): Try[(String, List[Array[String]])] = {
    Try {
      val listaIngredientes: List[String] = scala.io.Source.fromInputStream(
        getClass.getResourceAsStream(archivo)).getLines.toList
      val ingredientesOrdenados: List[Array[String]] = listaIngredientes.tail.map(x => x.split(","))
      (listaIngredientes.head, ingredientesOrdenados)
    }

    //val stream: InputStream = getClass.getResourceAsStream(archivo)
    //val listaIngredientes: List[String] = scala.io.Source.fromInputStream(stream).getLines.toList
    //val ingredientesOrdenados: List[Array[String]] = listaIngredientes.tail.map(x => x.split(","))

    //(listaIngredientes.head, ingredientesOrdenados)
  }

  def escribir(texto: List[String], nombreArchivo: String): Try[Unit] = {

    Try {
      val bw = new BufferedWriter(new FileWriter(nombreArchivo))
      texto.foreach(x => { bw.write(x + "\n"); println("-" + x) })
      bw.close()
    }
    //val bw = new BufferedWriter(new FileWriter(nombreArchivo))
    //texto.foreach(x => bw.write(x + "\n"))
    //bw.close()

    //println("Escribio en el archivo " + nombreArchivo)
  }
}
