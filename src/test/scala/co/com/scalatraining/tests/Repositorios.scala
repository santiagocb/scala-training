package co.com.scalatraining.tests

import org.scalatest.FunSuite
import co.com.scalatrainig.repositorios.servicios
import scala.concurrent.ExecutionContext.Implicits.global

import scala.concurrent.Future

class Repositorios extends FunSuite {
  test("repos") {
    val nickName = "santiagocb"

    val listaRepositorios = servicios.getRepositorios("santiagocb").fold{}{
      x => Future{x}.map(x => x.map(x => Future{servicios.getRepositorio(x)}))
      .map(x => Future.sequence(x)).flatten
      .map(x => x.groupBy(x => x.lenguaje))
      .map(x => x.map(x => x._1 -> x._2
        .foldLeft(0){(acu, x) => acu + 1}
      ))
    }
      /*.map(x => Future{x})
      .map(x => x.map(x => x.map(x => Future{servicios.getRepositorio(x)}))
      .map(x => Future.sequence(x)).flatten
      .map(x => x.groupBy(x => x.lenguaje))
      .map(x => x.map(x => x._1 -> x._2
        .foldLeft(0){(acu, x) => acu + 1}
      )))*/

    println(listaRepositorios)


    /*val res = listaRepositorios
      .map(x => x.map(x => Future{servicios.getRepositorio(x)}))
      .map(x => Future.sequence(x)).flatten
      .map(x => x.groupBy(x => x.lenguaje))
      .map(x => x.map(x => x._1 -> x._2
      .foldLeft(0){(acu, x) => acu + 1}
      ))*/



    //res.map(println)
    //listaDetalles.map(x => println(x))

  }
}
