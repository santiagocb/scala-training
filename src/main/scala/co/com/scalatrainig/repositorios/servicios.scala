package co.com.scalatrainig.repositorios

import java.util.concurrent.Executors

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

object serviciosGitLab {

  val allRepos = List(
    Repositorio("santiagocb", "myapp", 20, "java"),
    Repositorio("santiagocb", "scala-training", 1000, "scala"),
    Repositorio("santiagocb", "backend-sura", 10200, "scala")
  )

  val users = List("santiagocb")

  def getRepositorios(dueno: String): Future[Option[List[String]]] = {
    Future{Option(allRepos.filter(x => x.dueno == dueno).map(repo => repo.nombre))}
  }

  /*def getRepositorio(nombre: String): Future[Repositorio] = {
    if(nombre == "myapp"){ Future{Repositorio("myapp", 20,"java")} }
    else if(nombre == "scala-training") { Future{Repositorio( }
    else if(nombre == "backend-sura") { Future{Repositorio("backend-sura", 10200, "scala")} }
    else  Future{ Repositorio("",0,"nada") }
  }

  def getUsuarioCompleto(nombre: String): Future[(List[String], Map[String, Int])] = {
    val contarLenguajes = servicios.getRepositorios(nombre)
      .flatMap(opt => opt.fold{
        Future{Map("no" -> 0)}
      }{
        list => Future.sequence(list.flatMap(str => List(servicios.getRepositorio((str)))))
          .map(listRepos => listRepos.groupBy(repo => repo.lenguaje).mapValues(listRep => listRep.size))
      })
  }*/

  def getUsuarioCompleto(nombre: String) = {

  }

}
