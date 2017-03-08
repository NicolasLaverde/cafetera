package co.s4n.capacitaciones.sesion2.ErrorHandling

sealed trait Ingrediente

case class Agua(temperatura: Int, cantLitros: Double) extends Ingrediente {
  override def toString(): String = { temperatura + "," + cantLitros }
}

case class Leche(temperatura: Int, cantLitros: Double, tipoLeche: String) extends Ingrediente

case class CafeGrano(origen: String, cantidad: Double) extends Ingrediente {
  override def toString(): String = { origen + "," + cantidad.toString }
}

case class CafeMolido(cantidad: Double, cafeGrano: CafeGrano) extends Ingrediente

//trait despacharIngredientes[A <: Ingrediente] {

//  def editarIngrediente[A <: Ingrediente](ingrendiente: A): A =  {
//    ingrendiente match {
//      case Agua(_, _) => Agua(_, _)
//    }
//  }
//}

//object despacharIngrediente extends despacharIngredientes[Cafe] {
//
//  def editarIngrediente(ingrendiente: Ingrediente): Cafe = ???

//}