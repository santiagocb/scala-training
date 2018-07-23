package co.com.scalatraining.tuples

import org.scalatest.FunSuite

class TupleSuite  extends FunSuite {

  test("Una tupla se debe poder crear"){
    val tupla = (1, 2,"3", List(1, 2, 3))
    assert(tupla._2 == 2)
    assert(tupla._4.tail.head == 2)
  }

  //haga tupla de 5 lista, y de cada lista el head y al final una tupla con cada head
  test("asdasd"){
    val tupla: (List[Int], List[Int], List[Int], List[Int], List[Int]) = (List(2,3,4), List(2,3,4), List(2,3,4), List(2,3,4), List(1, 2, 3))
    val res: (Int, Int, Int, Int, Int) = (tupla._1.head, tupla._2.head, tupla._3.head, tupla._4.head, tupla._5.head)
    println(res)
    assert(res == (2,2,2,2,1))
  }

}
