package co.com.scalatrainig.repositorios.Usuario

import co.com.scalatrainig.repositorios.serviciosGitHub.serviciosGitHub

import scala.concurrent.Future

class Usuario(val id: String)

object Usuario {
  def apply(id: String): Option[Usuario] = {
    val res: Int = serviciosGitHub.getAllRepositorios().count(repo => repo.dueno == id)
    if(res == 0) None
    else Some(new Usuario(id))
  }
}

