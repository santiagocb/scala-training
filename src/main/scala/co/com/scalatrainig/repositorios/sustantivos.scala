package co.com.scalatrainig.repositorios

import scala.concurrent.Future

case class Repositorio(dueno: String, nombre: String, lineas: Int, lenguaje: String)
case class Resumen(repos: Future[Option[Future[List[String]]]],
                   contadores: Future[Option[Future[Map[String, Int]]]])