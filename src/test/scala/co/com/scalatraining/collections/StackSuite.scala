package co.com.scalatraining.collections

import scala.collection.immutable.Stack
import org.scalatest.FunSuite

class StackSuite extends FunSuite {

  test("Empty stack") {
    val emptyStack = Stack[String]()
    assert(emptyStack == Stack())
  }

  test("Push elements to Stack ") {
    val stack = Stack[Int](1, 2, 3, 4)
    val res: Stack[Int] = stack.push(5,6,7)
    assert(res == Stack(7, 6, 5, 1, 2, 3, 4))
    val res2: Stack[Int] = res.push(8)
    assert(res2 == Stack(8, 7, 6, 5, 1, 2, 3, 4))
    val res3: Stack[Int] = res2.pushAll(List(9, 10, 11))
    assert(res3 == Stack(11, 10, 9, 8, 7, 6, 5, 1, 2, 3, 4))
  }

  test("Pop elements from stack (pop/pop2)"){
    val stack = Stack[Int](7, 6, 5, 1, 2, 3, 4)
    val res: Stack[Int] = stack.pop
    println(res)
    assert(res == Stack(6, 5, 1, 2, 3, 4))
    val res2: (Int, Stack[Int]) = stack.pop2      //pop2 specify what element you pop
    assert(res2._1 == 7)
    assert(res2._2 == Stack(6, 5, 1, 2, 3, 4))
  }

  test("Top and tail") {
    val stack = Stack[Int](7, 6, 5, 1, 2, 3, 4)
    assert(stack.top == 7)
    assert(stack.tail == Stack(6,5,1,2,3,4))
  }

  test("Append 2 stacks with ++") {
    val stack = Stack[Int](7, 6, 5, 1, 2, 3, 4)
    val stack2 = Stack[Int](8, 9, 10)
    val res = stack ++ stack2
    println(res)
    assert(res == Stack(7, 6, 5, 1, 2, 3, 4, 8, 9, 10))
  }

  test("head of empty stack") {
    val stack2 = Stack()
    assertThrows[NoSuchElementException] {
      println(stack2.head)
    }
  }

  test("Reversing a stack") {
    val stack = Stack(11, 10, 9, 8, 7, 6, 5, 1, 2, 3, 4)
    assert(stack.reverse == Stack(4, 3, 2, 1, 5, 6, 7, 8, 9, 10, 11))
  }

  //Stacks al ser secuencias se comportan igual que listas, por tanto, se pueden aplicar métodos como fold, map, etc..


}
