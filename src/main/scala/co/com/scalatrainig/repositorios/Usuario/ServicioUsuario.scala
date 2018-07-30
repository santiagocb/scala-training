package co.com.scalatrainig.repositorios.Usuario

import co.com.scalatrainig.repositorios.{Repositorio, Resumen}
import co.com.scalatrainig.repositorios.serviciosGitHub.ServiciosGitHub

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

sealed trait ServicioUsuarioAlgebra {
  def getMisRepositorios(usuario: String): Future[Option[List[String]]]
  def getDetallesRepositorio(usuario: String, repoName: String): Future[Repositorio]
  //def getRepositoriosOrdenados(usuario: String): Unit
  //def getConteoLenguaje(usuario: String)
  def getUsuarioCompleto(usuario: String): Resumen
}

sealed trait ServicioUsuario extends ServicioUsuarioAlgebra {
  def getMisRepositorios(usuario: String): Future[Option[List[String]]] = {
    Future{ Usuario(usuario).map(user => ServiciosGitHub.getRepositoriosUser(user.id)) }
  }

  def getDetallesRepositorio(usuario: String, repoName: String): Future[Repositorio] = {
    Future{ ServiciosGitHub.getRepositorioDetalles(usuario, repoName) }
  }

  def getUsuarioCompleto(usuario: String): Resumen = {
    val listaOrdenada: Future[Option[Future[List[String]]]] = getMisRepositorios(usuario)
      .map(opt => opt
        .map(listRepos =>
          Future.sequence(listRepos.flatMap(str => List(getDetallesRepositorio(usuario, str))))
            .map(listaR => listaR.sortWith((x, y) => x.lineas < y.lineas)
            .map(repo => repo.nombre))))
    val conteo: Future[Option[Future[Map[String, Int]]]] = getMisRepositorios(usuario)
      .map(opt => opt
        .map(list =>
          Future.sequence(list.flatMap(str => List(getDetallesRepositorio(usuario, str))))
        .map(listRepos => listRepos.groupBy(_.lenguaje).mapValues(_.size))))
    Resumen(listaOrdenada, conteo)
  }
}

object ServicioUsuario extends ServicioUsuario