package co.com.scalatraining.tests

import co.com.scalatrainig.repositorios.Usuario.{ServicioUsuario, Usuario}
import org.scalatest.FunSuite

import scala.language.postfixOps
import scala.util.{Failure, Success}
import scala.concurrent.{Await, ExecutionContext, Future}
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global

class Repositorios extends FunSuite {
  test("repos") {
    val nickName = "santiagocb"
    val user = Usuario(nickName)

    val result = Await.result(ServicioUsuario.getMisRepositorios("santiagocb"), 10 seconds)
    val result2 = Await.result(ServicioUsuario.getDetallesRepositorio("santiagocb", "scala-training"), 10 seconds)

    val r3 = Option(List(1,2,3,4)).map(x => x.size)

    println(result)
    println(result2)

  }
}
