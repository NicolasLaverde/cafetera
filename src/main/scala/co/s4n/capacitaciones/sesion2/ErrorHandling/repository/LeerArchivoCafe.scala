package co.s4n.capacitaciones.sesion2.ErrorHandling.repository

import java.io.{ BufferedWriter, File, FileWriter, InputStream }

import co.s4n.capacitaciones.sesion2.ErrorHandling.{ Agua, CafeGrano, Ingrediente, Leche }

/**
 * Created by daniel on 6/03/17.
 */
case class LeerArchivoCafe() {

  private val archivoCafe: String = "/inventarioCafe.txt"
  private val archivoAgua: String = "/inventarioAgua.txt"
  private val archivoSalidaCafe: String = "./src/main/resources/inventarioCafe2.txt"
  private val archivoSalidaAgua: String = "./src/main/resources/inventarioAgua2.txt"

  def editar[A <: Ingrediente](ingrediente: A): Ingrediente = {
    ingrediente match {
      case CafeGrano(_, _) => editarUnIngrediente(ingrediente)
      case _ => ingrediente
    }
  }

  private def editarUnIngrediente[A <: Ingrediente](ingrediente: A)(): Ingrediente = {
    val (titulo: String, datos: List[Array[String]]) = leerArchivoIngredientes(archivoLectura(ingrediente))
    val ingredienteList: List[Ingrediente] = datos.map(x => crearIngrediente(ingrediente, x.head, x(1)))
    val filtro: List[Ingrediente] = ingredienteList.filter(x => filtrar(ingrediente, x))

    escribirArchivoIngrediente(titulo, seEdita(ingrediente, filtro.head), ingredienteList.filterNot(x => filtrar(ingrediente, x)))
  }

  private def leerArchivoIngredientes(archivo: String): (String, List[Array[String]]) = {
    val stream: InputStream = getClass.getResourceAsStream(archivo)
    val listaIngredientes: List[String] = scala.io.Source.fromInputStream(stream).getLines.toList
    val ingredientesOrdenados: List[Array[String]] = listaIngredientes.tail.map(x => x.split(","))
    (listaIngredientes.head, ingredientesOrdenados)
  }

  private def crearIngrediente[A <: Ingrediente](i: A, a: String, b: String): Ingrediente = {
    i match {
      case CafeGrano(_, _) => CafeGrano(a, b.toInt)
      case Agua(_, _) => Agua(a.toInt, b.toDouble)
      case _ => i
    }
  }

  private def filtrar2[A <: Ingrediente](ingrediente: A, lista: List[A]): List[Ingrediente] = {
    ingrediente match {
      case CafeGrano(origen1, _) => lista.filter(x => filtrar(ingrediente, x))
      //case Agua(_, _) => val suma = lista.foldLeft(0) (x => x.)
      case _ => List()
    }
  }

  private def filtrar[A <: Ingrediente](ingrediente1: A, ingrediente2: A): Boolean = {
    ingrediente1 match {
      case CafeGrano(origen1, _) =>
        ingrediente2 match {
          case CafeGrano(origen2, _) if origen1 == origen2 => true
          case _ => false
        }
      case _ => false
    }
  }

  private def seEdita[A <: Ingrediente](a: A, b: A): Ingrediente = {
    a match {
      case CafeGrano(origen1, cantidad1) =>
        b match {
          case CafeGrano(_, cantidad2) if cantidad2 - cantidad1 >= 0 => CafeGrano(origen1, cantidad2 - cantidad1)
          case _ => a
        }
      case _ => a
    }
  }

  private def escribirArchivoIngrediente[A <: Ingrediente](titulo: String, ingrediente: A, lista: List[Ingrediente]): Ingrediente = {
    ingrediente match {
      case CafeGrano(_, _) =>
        escribir(titulo :: lista.map(x => x.toString) ++ List(ingrediente.toString), archivoEscritura(ingrediente))
        ingrediente
      case _ => ingrediente
    }
  }

  private def archivoLectura[A <: Ingrediente](ingrediente: A): String = {
    ingrediente match {
      case CafeGrano(_, _) => archivoCafe
      case Agua(_, _) => archivoAgua
      case _ => ""
    }
  }

  private def archivoEscritura[A <: Ingrediente](ingrediente: A): String = {
    ingrediente match {
      case CafeGrano(_, _) => archivoSalidaCafe
      case Agua(_, _) => archivoSalidaAgua
      case _ => ""
    }
  }

  private def escribir(texto: List[String], nombreArchivo: String): Unit = {
    val bw = new BufferedWriter(new FileWriter(nombreArchivo))
    texto.foreach(x => bw.write(x + "\n"))
    bw.close()
    println("Escribio en el archivo")
  }
}
