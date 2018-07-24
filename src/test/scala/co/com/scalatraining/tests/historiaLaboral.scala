package co.com.scalatraining.tests

import org.scalatest.FunSuite

class historiaLaboral extends FunSuite{

  test ("historia") {
    case class Cotizacion (periodo: String, aportante: String, diasCotizados: Int, ibs: Int){}

    def limpiarHistorial(lista:List[Cotizacion]):Int = {
      val regla1 = lista.filter(x => x.ibs != 0 && x.diasCotizados != 0)
      val regla2 = regla1.map(x => Cotizacion(x.periodo, x.aportante, x.diasCotizados, (30*x.ibs/x.diasCotizados)))
      val regla3 = regla2.distinct
      val regla35 = regla3.groupBy(x => x.aportante)
      val regla4 = regla35.map(x => x._1 -> x._2.foldLeft(0){(ac, cot) =>
        if(ac < cot.ibs){
          cot.ibs
        }  else {
          ac
        }
      })
      val regla5 = regla4.foldLeft(0){(ac, item) => ac + item._2}
      println("Regla 1")
      regla1.foreach(println)
      println("Regla 2")
      regla2.foreach(println)
      println("Regla 3")
      regla3.foreach(println)
      println("Regla 35")
      println(regla35)
      println("Regla 4")
      println(regla4)
      println("Regla 5")
      println(regla5)
      regla5
    }

    val cotizacion1 = new Cotizacion("2018/07", "S4N", 10, 1000)
    val cotizacion2 = new Cotizacion("2018/07", "S4N", 20, 1000)
    val cotizacion3 = new Cotizacion("2018/08", "S4N", 30, 2000)
    val cotizacion4 = new Cotizacion("2018/09", "S4N", 0, 2000)
    val cotizacion5 = new Cotizacion("2018/10", "S4N", 20, 1000)
    val cotizacion6 = new Cotizacion("2018/08", "S4N", 30, 2000)
    val cotizacion7 = new Cotizacion("2018/11", "udea", 30, 870)
    //val cotizacion8 = new Cotizacion("2018/11", "S4N", 15, 5000)
    //val cotizacion9 = new Cotizacion("2018/12", "udea", 15, 5000)

    val listaCotizaciones = List(cotizacion1, cotizacion2, cotizacion3, cotizacion4, cotizacion5, cotizacion6, cotizacion7)
    //val listaCotizaciones = List(cotizacion1, cotizacion2, cotizacion3, cotizacion4, cotizacion5, cotizacion6, cotizacion7, cotizacion8)
    //val listaCotizaciones = List(cotizacion1, cotizacion2, cotizacion3, cotizacion4, cotizacion5, cotizacion6, cotizacion7, cotizacion8, cotizacion9)


    listaCotizaciones.foreach(println)
    val historialLimpio = limpiarHistorial(listaCotizaciones)
    assert(historialLimpio == 3870)

  }
}
