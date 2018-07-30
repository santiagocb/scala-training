package co.com.scalatrainig.repositorios.Usuario

import co.com.scalatrainig.repositorios.Repositorio
import co.com.scalatrainig.repositorios.serviciosGitHub.serviciosGitHub

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

sealed trait ServicioUsuarioAlgebra {
  def getMisRepositorios(usuario: String): Future[Option[List[String]]]
  def getDetallesRepositorio(usuario: String, repoName: String): Future[Repositorio]
  def getRepositoriosOrdenados(usuario: String): Future[List[String]]
  //def getUsuarioCompleto(usuario: String): Unit
}

sealed trait ServicioUsuario extends ServicioUsuarioAlgebra {
  def getMisRepositorios(usuario: String): Future[Option[List[String]]] = {
    Future{ Usuario(usuario).map(user => serviciosGitHub.getRepositoriosUser(user.id)) }
  }

  def getDetallesRepositorio(usuario: String, repoName: String): Future[Repositorio] = {
    Future{ serviciosGitHub.getRepositorioDetalles(usuario, repoName) }
  }

  def getRepositoriosOrdenados(usuario: String): Future[List[String]] = {
    val listaOrdenada = getMisRepositorios(usuario)
      .map(opt => opt
        .map(listRepos =>
          Future.sequence(listRepos.flatMap(str => List(getDetallesRepositorio(usuario, str))))
            .map(listaR => listaR.sortWith((x, y) => x.lineas < y.lineas))))
  }

  /*def getUsuarioCompleto(usuario: String): Unit = {

  }*/
}

object ServicioUsuario extends ServicioUsuario