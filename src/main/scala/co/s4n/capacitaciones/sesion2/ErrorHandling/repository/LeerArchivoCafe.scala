package co.s4n.capacitaciones.sesion2.ErrorHandling.repository

import java.io.{ BufferedWriter, File, FileWriter, InputStream }

import co.s4n.capacitaciones.sesion2.ErrorHandling.{ CafeGrano, Ingrediente, Leche }

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

  def editarUnIngrediente[A <: Ingrediente](ingrediente: A): Ingrediente = {

    val (titulo: String, datos: List[Array[String]]) = leerArchivoIngredientes()
    val ingredienteList: List[Ingrediente] = datos.map(x => crearIngrediente(ingrediente, x.head, x(1), ""))
    val filtro: List[Ingrediente] = ingredienteList.filter(x => filtrar(ingrediente, x))

    escribirArchivoIngrediente(titulo, seEdita(ingrediente, filtro.head), ingredienteList.filterNot(x => filtrar(ingrediente, x)))

  }

  def escribirArchivoIngrediente[A <: Ingrediente](titulo: String, ingrediente: A, lista: List[Ingrediente]): Ingrediente = {
    escribir(titulo :: lista.map(x => x.toString) ++ List(ingrediente.toString))
    ingrediente
  }

  def escribir(texto: List[String]): Unit = {

    val file = new File(archivo)
    val bw = new BufferedWriter(new FileWriter("./src/main/resources/inventarioCafe2.txt"))
    texto.foreach(x => bw.write(x + "\n"))
    bw.close()
    println("Escribio en el archivo")
  }

}
