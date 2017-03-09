package co.s4n.capacitaciones.sesion2.ErrorHandling

import java.io.{ BufferedWriter, FileWriter, InputStream }

import co.s4n.capacitaciones.sesion2.ErrorHandling.repository.GestorArchivo

sealed trait Ingrediente
case class Agua(temperatura: Int, cantLitros: Double) extends Ingrediente {
  override def toString(): String = { temperatura + "," + cantLitros }
}

case class Leche(temperatura: Int, cantLitros: Double, tipoLeche: String) extends Ingrediente

case class CafeGrano(origen: String, cantidad: Double) extends Ingrediente {
  override def toString(): String = { origen + "," + cantidad.toString }
}

case class CafeMolido(cantidad: Double, cafeGrano: CafeGrano) extends Ingrediente

