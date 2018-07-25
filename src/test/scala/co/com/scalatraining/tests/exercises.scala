package co.com.scalatraining.tests

import org.scalatest.FunSuite

import scala.collection.immutable.Queue

class Exercises extends FunSuite{

  test("Crear lista de maps a partir de lista") {
    val lista = Queue(1, 2, 3, 4)
    val res = lista.map(x => Map(x -> x)).flatMap(x => Queue(x.head._1 * x.head._2))
    val lista2 = List(1, 2, 3, 4)
    //println(res)
  }

  test ("Convertir un mapa a un Queue"){
    val mapa1 = Map(1->1, 2->2)
    val mapa2 = Map(3->3, 4->4)
    var res = mapa2.foldLeft(Queue[Int]())((x, y) => x.enqueue(y._2))
    //val res2 = mapa1.map(x => x._2 * res.dequeue._1)
  }
}
