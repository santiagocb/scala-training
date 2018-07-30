package co.com.scalatrainig.repositorios

case class Repositorio(dueno: String, nombre: String, lineas: Int, lenguaje: String)
case class Resumen(repos: List[String], contadores: Map[String, Int])

