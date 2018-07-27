package co.com.scalatraining.tests

import org.scalatest.FunSuite
import co.com.scalatrainig.repositorios.servicios

import scala.language.postfixOps
import scala.util.{Failure, Success}
import scala.concurrent.{Await, ExecutionContext, Future}
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global

class Repositorios extends FunSuite {
  test("repos") {
    val nickName = "santiagocb"

    /*val listaRepositorios = servicios.getRepositorios("santiagocb").fold{}{
      +      x => Future{x}.map(x => x.map(x => Future{servicios.getRepositorio(x)}))
      +      .map(x => Future.sequence(x)).flatten
      +      .map(x => x.groupBy(x => x.lenguaje))
      +      .map(x => x.map(x => x._1 -> x._2
        +        .foldLeft(0){(acu, x) => acu + 1}
        +      ))
      +    }*/


    val res = servicios.getRepositorios(nickName)
      .map(opt => opt.fold{
        Future{Map("no" -> 0)}
      }{
        list => Future.sequence(list.flatMap(str => List(servicios.getRepositorio((str)))))
          .map(listRepos => listRepos.groupBy(repo => repo.lenguaje).mapValues(listRep => listRep.size))
      })

    val res2 = servicios.getRepositorios(nickName)
      .map(opt => opt.fold{

      }{
        list => list.sortWith((x,y) => x < y)
      })

    val res3 = Future{List(12)}
      .map(x => 2)

    val resultado = Await.result(res, 10 seconds)
    val resultado2 = Await.result(res2, 10 seconds)
    val resultado3 = Await.result(res3, 10 seconds)
    println(resultado)

  }
}
