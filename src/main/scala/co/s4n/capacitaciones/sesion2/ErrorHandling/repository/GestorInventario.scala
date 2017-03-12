package co.s4n.capacitaciones.sesion2.ErrorHandling.repository

import java.io.{ BufferedWriter, FileWriter }

import scala.util.Try

case object GestorInventario {

  def leerInventario(archivo: String): Try[(String, List[Array[String]])] = {
    Try {
      val listaIngredientes: List[String] = scala.io.Source.fromInputStream(
        getClass.getResourceAsStream(archivo)).getLines.toList
      val ingredientesOrdenados: List[Array[String]] = listaIngredientes.tail.map(x => x.split(","))
      (listaIngredientes.head, ingredientesOrdenados)
    }
  }

  def escribirInventario(texto: List[String], nombreArchivo: String): Try[Unit] = {
    Try {
      val bw = new BufferedWriter(new FileWriter(nombreArchivo))
      texto.foreach(x => { bw.write(x + "\n"); println("-" + x) })
      bw.close()
    }
  }
}
