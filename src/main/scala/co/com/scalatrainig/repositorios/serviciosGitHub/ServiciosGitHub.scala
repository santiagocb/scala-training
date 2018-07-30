package co.com.scalatrainig.repositorios.serviciosGitHub

import co.com.scalatrainig.repositorios.Repositorio

object ServiciosGitHub {
  val allRepos = List(
    Repositorio("santiagocb", "S4N-distributed", 400000, "java"),
    Repositorio("santiagocb", "myapp", 20, "java"),
    Repositorio("santiagocb", "scala-training", 1000, "scala"),
    Repositorio("santiagocb", "backend-sura", 10200, "scala"),
    Repositorio("markzberg", "backend-facebook", 5600000, "assembler")
  )

  val users = List("santiagocb", "dt", "markzberg")

  def getAllUsers(): List[String] = users

  def getAllRepositorios(): List[Repositorio] = allRepos

  def getRepositoriosUser(userName: String): List[String] = allRepos.filter(repo => repo.dueno == userName).map(repo => repo.nombre)

  def getRepositorioDetalles(userName: String, name: String): Repositorio = allRepos.filter(repo => repo.dueno == userName && repo.nombre == name).head
}
