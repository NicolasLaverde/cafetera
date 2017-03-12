package co.s4n.capacitaciones.sesion2.ErrorHandling.service

import co.s4n.capacitaciones.sesion2.ErrorHandling._

import scala.util.Random

case class BaristaService(tiempoEspera: Int) {

  def random(): Unit = Thread.sleep(Random.nextInt(tiempoEspera))

  def prepararIngredientes(ingredientes: List[Ingrediente]): (Option[Agua], Option[CafeGrano]) = {

    val optionList: List[Option[Ingrediente]] = ingredientes map (i => revisarInventario(i))
    val list = optionList.filter(_.isDefined).map(x => x.get)

    list match {
      case _ :: _ =>
        (list.head, list(1)) match {
          case (Agua(temperatura, cantLitros), CafeGrano(origen, cantidad)) =>
            (Option(Agua(temperatura, cantLitros)), Option(CafeGrano(origen, cantidad)))
          case _ => (None, None)
        }
      case _ => (None, None)
    }
  }

  def revisarInventario[A <: Ingrediente](ing: A): Option[Ingrediente] =
    ing match {

      case CafeGrano(origen, cantidad) =>
        CafeGranoService.prepararIngrediente(CafeGrano(origen, cantidad))

      case Agua(temperatura, cantLitros) =>
        AguaService.prepararIngrediente(Agua(temperatura, cantLitros))

      case Leche(_, _, _) => None

      case CafeMolido(_, _) => None
    }

  def moler(granos: Option[CafeGrano]): Option[CafeMolido] = {
    random()
    granos match {
      case Some(g) => Option(CafeMolido(Random.nextInt(g.cantidad.toInt + 1), g))
      case None => None
    }
  }

  def calentar(agua: Option[Agua]): Option[Agua] = {
    agua match {
      case Some(a) => calienta(a)
      case None => None
    }
  }

  private def calienta(agua: Agua): Option[Agua] = {
    val temperatura: Int = Random.nextInt(100)
    verificarTemperatura(temperatura) match {
      case Right(_) =>
        Option(Agua(agua.temperatura + temperatura, agua.cantLitros / (temperatura / agua.temperatura)))
      case Left(_) =>
        calienta(agua)
    }
  }

  def verificarTemperatura(temperatura: Double): Either[String, Double] = {
    if (temperatura >= 50d && temperatura < 80d) {
      Right(temperatura)
    } else Left("La temperatura no está bien")
  }

  def preparar(cafe: Option[CafeMolido], aguaCaliente: Option[Agua]): Option[Cafe] = {
    cafe match {
      case Some(c) => aguaCaliente match {
        case Some(a) => Option(Cafe(List(c, a)))
        case None => None
      }
      case None => None
    }

  }
}
