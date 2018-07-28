package co.com.scalatrainig.repositorios

case class Usuario(id: String, repositorios: List[String], resumen: Map[String, Int])
case class Repositorio(dueno: String, nombre: String, lineas: Int, lenguaje: String)
case class Resumen(repos: List[String], contadores: Map[String, Int])

