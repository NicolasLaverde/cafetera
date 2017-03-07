package co.s4n.capacitaciones.sesion2.ErrorHandling

sealed trait Ingrediente
case class Agua(temperatura: Int, cantLitros: Double) extends Ingrediente
case class Leche(temperatura: Int, cantLitros: Double, tipoLeche: String) extends Ingrediente
case class CafeGrano(origen: String, cantidad: Double) extends Ingrediente
case class CafeMolido(cantidad: Double, cafeGrano: CafeGrano) extends Ingrediente