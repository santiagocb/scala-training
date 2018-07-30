package co.com.scalatrainig.repositorios.Usuario

import co.com.scalatrainig.repositorios.serviciosGitHub.ServiciosGitHub

import scala.concurrent.Future

class Usuario(val id: String)

object Usuario {
  def apply(id: String): Option[Usuario] = {
    val res: Int = ServiciosGitHub.getAllUsers().count(user => user == id)
    if(res == 0) None
    else Some(new Usuario(id))
  }
}

