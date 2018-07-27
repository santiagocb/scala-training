package co.com.scalatrainig.repositorios

import java.util.concurrent.Executors
import scala.concurrent.ExecutionContext.Implicits.global

import scala.concurrent.Future

object servicios {
  def getRepositorios(id: String): Future[Option[List[String]]] = {
    if(id == "santiagocb"){
      Future{Some(List("myapp", "scala-training", "backend-sura"))}
    }
    else {
      println("Tratando de traer algo.. no encontró usuario")
      Future{None}
    }
  }

  def getRepositorio(nombre: String): Future[Repositorio] = {
    if(nombre == "myapp"){ Future{Repositorio("myapp", 20,"java")} }
    else if(nombre == "scala-training") { Future{Repositorio("scala-training", 1000, "scala")} }
    else if(nombre == "backend-sura") { Future{Repositorio("backend-sura", 10200, "scala")} }
    else  Future{ Repositorio("",0,"nada") }
  }

  def getUsuarioCompleto() = {

  }
}
