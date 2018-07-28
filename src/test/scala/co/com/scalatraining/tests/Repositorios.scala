package co.com.scalatraining.tests

import org.scalatest.FunSuite
import co.com.scalatrainig.repositorios.serviciosGitLab

import scala.language.postfixOps
import scala.util.{Failure, Success}
import scala.concurrent.{Await, ExecutionContext, Future}
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global

class Repositorios extends FunSuite {
  test("repos") {
    val nickName = "santiagocb"
    val r = serviciosGitLab.getRepositorios(nickName)
    val resultado = Await.result(r, 10 seconds)
    println(resultado)

  }
}
