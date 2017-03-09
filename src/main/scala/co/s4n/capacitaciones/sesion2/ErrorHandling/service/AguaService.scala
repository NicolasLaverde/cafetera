package co.s4n.capacitaciones.sesion2.ErrorHandling.service

import co.s4n.capacitaciones.sesion2.ErrorHandling.Agua
import co.s4n.capacitaciones.sesion2.ErrorHandling.repository.GestorArchivo

case class AguaService() extends IngredienteService[Agua] {

  private val archivoAgua: String = "/inventarioAgua.txt"
  private val archivoSalidaAgua: String = "./src/main/resources/inventarioAgua2-.txt"

  def editar(ingrediente: Agua): Agua = {
    editarUnIngrediente(ingrediente)
  }

  def editarUnIngrediente(ingrediente: Agua)(): Agua = {
    val (titulo: String, datos: List[Array[String]]) = GestorArchivo().leerArchivo(archivoLectura(ingrediente))
    val ingredienteList: List[Agua] = datos.map(x => crearIngrediente(x.head, x(1)))
    val (filtro: List[Agua], noFiltro: List[Agua]) = filtrarIngrediente(ingrediente, ingredienteList)

    escribirArchivoIngrediente(titulo, seEdita(ingrediente, filtro.head), noFiltro)
  }

  def crearIngrediente(a: String, b: String): Agua = {
    Agua(a.toInt, b.toDouble)
  }

  def filtrarIngrediente(ingrediente: Agua, lista: List[Agua]): (List[Agua], List[Agua]) = {
    val list: List[Agua] = List(Agua(ingrediente.temperatura, lista.map(x => x.cantLitros).sum - ingrediente.cantLitros))
    (list, list)
  }

  //  def filtrar(ingrediente1: Agua, ingrediente2: Agua): Boolean = {
  //  }

  def seEdita(a: Agua, b: Agua): Agua = {
    if (b.cantLitros - a.cantLitros >= 0)
      Agua(a.temperatura, b.cantLitros - a.cantLitros)
    else
      a
  }

  def escribirArchivoIngrediente(titulo: String, ingrediente: Agua, lista: List[Agua]): Agua = {
    GestorArchivo().escribir(titulo :: lista.map(x => x.toString), archivoEscritura(ingrediente))
    ingrediente
  }

  def archivoLectura(ingrediente: Agua): String = {
    archivoAgua
  }

  def archivoEscritura(ingrediente: Agua): String = {
    archivoSalidaAgua
  }

}