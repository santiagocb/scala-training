package co.com.scalatrainig.repositorios

case class Usuario(id: String)
case class Repositorio(nombre: String, lineas: Int, lenguaje: String)
case class Resumen(repos: List[String], contadores: Map[String, Int])
