package co.com.scalatraining.tests

import co.com.scalatrainig.repositorios.Repositorio
import co.com.scalatrainig.repositorios.Usuario.{ServicioUsuario, Usuario}
import co.com.scalatrainig.repositorios.serviciosGitHub.ServiciosGitHub
import org.scalatest.FunSuite

import scala.language.postfixOps
import scala.util.{Failure, Success}
import scala.concurrent.{Await, ExecutionContext, Future}
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global

class TestRepositorio extends FunSuite {
  test("Servicios Github") {
    assert(ServiciosGitHub.getAllRepositorios() ==
      List(
        Repositorio("santiagocb", "S4N-distributed", 400000, "java"),
        Repositorio("santiagocb", "myapp", 20, "java"),
        Repositorio("santiagocb", "scala-training", 1000, "scala"),
        Repositorio("santiagocb", "backend-sura", 10200, "scala"),
        Repositorio("markzberg", "backend-facebook", 5600000, "assembler")
      ))

    assert(ServiciosGitHub.getAllUsers() == List("santiagocb", "dt", "markzberg"))

    assert(ServiciosGitHub.getRepositoriosUser("santiagocb") ==
      List("S4N-distributed", "myapp", "scala-training", "backend-sura"))

    assert(ServiciosGitHub.getRepositorioDetalles("santiagocb", "scala-training") ==
      Repositorio("santiagocb", "scala-training", 1000, "scala"))
  }

  test("Validar la creación del usuario") {
    val user = Usuario("santiagocb")
    val user2 = Usuario("noexisteesteusuario")
    assert(user != None)
    assert(user2 == None)
  }

  test("Test Servicio getMisRepositorios") {
    val result = Await.result(ServicioUsuario.getMisRepositorios("santiagocb"), 10 seconds)
    assert(result == Some(List("S4N-distributed", "myapp", "scala-training", "backend-sura")))
    println(s"1. ${result}")
  }

  test("Test Servicio getDetallesRepositorio") {
    val result = Await.result(ServicioUsuario.getDetallesRepositorio("santiagocb", "scala-training"), 10 seconds)
    assert(result == Repositorio("santiagocb","scala-training",1000, "scala"))
    println(s"2. ${result}")
  }

  test("Test Servicio getUsuarioCompleto") {
    val result = ServicioUsuario.getUsuarioCompleto("santiagocb")
    val resultSort = Await.result(result.repos.map(opt => opt.map(x => Await.result(x, 5 seconds))), 10 seconds)
    val resultMap = Await.result(result.contadores.map(opt => opt.map(x => Await.result(x, 5 seconds))), 10 seconds)

    println(s"3. ${resultSort}")
    assert(resultSort == Some(List("myapp", "scala-training", "backend-sura", "S4N-distributed")))
    println(s"4. ${resultMap}")
    assert(resultMap == Some(Map("scala" -> 2, "java" -> 2)))
  }

  test ("Test con usuario no existente") {
    val result = ServicioUsuario.getUsuarioCompleto("noexistoegithub")
    val resultSort = Await.result(result.repos.map(opt => opt.map(x => Await.result(x, 5 seconds))), 10 seconds)
    val resultMap = Await.result(result.contadores.map(opt => opt.map(x => Await.result(x, 5 seconds))), 10 seconds)

    println(s"5. ${resultSort}")
    assert(resultSort == None)
    println(s"6. ${resultMap}")
    assert(resultMap == None)
  }

  test ("Test usuario existente pero sin repositorios") {
    val result = ServicioUsuario.getUsuarioCompleto("dt")
    val resultSort = Await.result(result.repos.map(opt => opt.map(x => Await.result(x, 5 seconds))), 10 seconds)
    val resultMap = Await.result(result.contadores.map(opt => opt.map(x => Await.result(x, 5 seconds))), 10 seconds)

    println(s"7. ${resultSort}")
    assert(resultSort == Some(List()))
    println(s"8. ${resultMap}")
    assert(resultMap == Some(Map()))
  }

  test("Test con otro usuario") {
    val result = ServicioUsuario.getUsuarioCompleto("markzberg")
    val resultSort = Await.result(result.repos.map(opt => opt.map(x => Await.result(x, 5 seconds))), 10 seconds)
    val resultMap = Await.result(result.contadores.map(opt => opt.map(x => Await.result(x, 5 seconds))), 10 seconds)

    println(s"7. ${resultSort}")
    assert(resultSort == Some(List("backend-facebook")))
    println(s"8. ${resultMap}")
    assert(resultMap == Some(Map("assembler" -> 1)))
  }
}
