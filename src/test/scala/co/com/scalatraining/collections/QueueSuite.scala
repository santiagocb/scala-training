package co.com.scalatraining.collections

import scala.collection.immutable.Queue
import org.scalatest.FunSuite

class QueueSuite extends FunSuite {
  test("Prepend new element to queue") {
    val queue = Queue("asdasd", "asdads")

    println("asd"+:queue)
  }

  test("Append and prepend new element to queue") {
    val queue = Queue("a", "todo")
    val res = queue:+"el mundo"
    println("hola"+:res)
  }




}
