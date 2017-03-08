package co.s4n.capacitaciones.sesion2.ErrorHandling.repository

import java.io.{ BufferedWriter, File, FileWriter, InputStream }

import co.s4n.capacitaciones.sesion2.ErrorHandling.{ Agua, CafeGrano, Ingrediente, Leche }

/**
 * Created by daniel on 6/03/17.
 */
case class LeerArchivoCafe() {

  private val archivoCafe: String = "/inventarioCafe.txt"
  private val archivoAgua: String = "/inventarioAgua.txt"
  private val archivoSalidaCafe : String = "./src/main/resources/inventarioCafe2.txt"
  private val archivoSalidaAgua : String = "./src/main/resources/inventarioAgua2.txt"

  private def leerArchivoIngredientes(archivo: String): (String, List[Array[String]]) = {
    val stream: InputStream = getClass.getResourceAsStream(archivo)
    val listaIngredientes: List[String] = scala.io.Source.fromInputStream(stream).getLines.toList
    val ingredientesOrdenados: List[Array[String]] = listaIngredientes.tail.map(x => x.split(","))
    (listaIngredientes.head, ingredientesOrdenados)
  }

  private def filtrar[A <: Ingrediente](ingrediente1: A, ingrediente2: A): Boolean = {
    ingrediente1 match {
      case CafeGrano(origen1, cantidad1) =>
        ingrediente2 match {
          case CafeGrano(origen2, cantidad2) if origen1 == origen2 => true
          case _ => false
        }
      case _ => false
    }
  }

  private def crearIngrediente[A <: Ingrediente](i: A, a: String, b: String, c: String): Ingrediente = {
    i match {
      case CafeGrano(origen1, cantidad1) => CafeGrano(a, b.toInt)
      case Leche(temperatura, cantLitros, tipoLeche) => Leche(a.toInt, b.toInt, c)
      case _ => i
    }
  }

  private def seEdita[A <: Ingrediente](a: A, b: A): Ingrediente = {
    a match {
      case CafeGrano(origen1, cantidad1) =>
        b match {
          case CafeGrano(origen2, cantidad2) if cantidad2 - cantidad1 >= 0 => CafeGrano(origen1, cantidad2 - cantidad1)
          case _ => a
        }
      case _ => a
    }
  }

  def editar[A <: Ingrediente](ingrediente: A): Ingrediente = {
    ingrediente match {
      case CafeGrano(origen, cantidad) => editarUnIngrediente(ingrediente)
      case _ => ingrediente
    }
  }

  private def archivoLectura[A <: Ingrediente](ingrediente: A): String = {
    ingrediente match {
      case CafeGrano(origen, cantidad) => archivoCafe
      case Agua(temperatura, cantLitros) => archivoAgua
      case _ => ""
    }
  }

  private def archivoEscritura[A <: Ingrediente](ingrediente: A): String = {
    ingrediente match {
      case CafeGrano(origen, cantidad) => archivoSalidaCafe
      case Agua(temperatura, cantLitros) => archivoSalidaAgua
      case _ => ""
    }
  }

  private def editarUnIngrediente[A <: Ingrediente](ingrediente: A): Ingrediente = {
    val (titulo: String, datos: List[Array[String]]) = leerArchivoIngredientes(archivoLectura(ingrediente))
    val ingredienteList: List[Ingrediente] = datos.map(x => crearIngrediente(ingrediente, x.head, x(1), ""))
    val filtro: List[Ingrediente] = ingredienteList.filter(x => filtrar(ingrediente, x))

    escribirArchivoIngrediente(titulo, seEdita(ingrediente, filtro.head), ingredienteList.filterNot(x => filtrar(ingrediente, x)))
  }

  private def escribirArchivoIngrediente[A <: Ingrediente](titulo: String, ingrediente: A, lista: List[Ingrediente]): Ingrediente = {
    ingrediente match {
      case CafeGrano(origen, cantidad) =>
        escribir(titulo :: lista.map(x => x.toString) ++ List(ingrediente.toString), archivoEscritura(ingrediente))
        ingrediente
      case _ => ingrediente
    }
  }

  private def escribir(texto: List[String], nombreArchivo: String): Unit = {
    val bw = new BufferedWriter(new FileWriter(nombreArchivo))
    texto.foreach(x => bw.write(x + "\n"))
    bw.close()
    println("Escribio en el archivo")
  }

}
