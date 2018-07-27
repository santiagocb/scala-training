package co.com.scalatrainig.repositorios

import java.util.concurrent.Executors

import scala.concurrent.{ExecutionContext, Future}

object servicios {
  def getRepositorios(id: String): Option[List[String]] = {
    if(id == "santiagocb"){
      Some((List("myapp", "scala-training", "backend-sura")))
    }
    else None
  }

  def getRepositorio(nombre: String): Repositorio = {
    if(nombre == "myapp"){
      Repositorio("myapp", 20,"java")
    }
    else if(nombre == "scala-training") { Repositorio("scala-training", 1000, "scala")}
    else if(nombre == "backend-sura") {  Repositorio("backend-sura", 10200, "scala")}
    else  Repositorio("",0,"nada")
  }
}
