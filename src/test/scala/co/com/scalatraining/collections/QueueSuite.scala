package co.com.scalatraining.collections

import scala.collection.immutable.Queue
import org.scalatest.FunSuite

class QueueSuite extends FunSuite {
  test("Empty queue") {
    val emptyQueue: Queue[String] = Queue.empty[String]
    assert(emptyQueue == Queue())
  }

  test("Prepend new element to queue :+/enqueue") {
    val queue: Queue[String] = Queue("mundo", "cruel")
    val res: Queue[String] = ("hola"+:queue).enqueue("y feo")
    println(res)
    assert(res == Queue("hola", "mundo", "cruel", "y feo"))
  }

  test("Append and prepend new element to queue :+ +:") {
    val queue: Queue[String] = Queue("a", "todo")
    val res: Queue[String] = "hola"+:queue:+"el mundo"
    println(res)
    assert(res == Queue("hola", "a", "todo", "el mundo"))
  }

  test("Dequeue function on Queue") {
    val queue: Queue[String] = Queue("hola", "todo", "el", "mundo")
    val afterDequeue: (String, Queue[String]) = queue.dequeue
    println("Primer elemento en ser sacado de la pila: " + afterDequeue._1)
    println("La lista que queda después del dequeue: " + afterDequeue._2)
    assert(afterDequeue._1 == "hola")
    assert(afterDequeue._2 == Queue("todo", "el", "mundo"))
  }

  test("function: ++ (append 2 queues)") {
    val queue: Queue[String] = Queue("hola a")
    val queue2: Queue[String] = Queue("S4N")
    val res: Queue[String] = (queue ++ queue2)
    println(res)
    assert(res == Queue("hola a", "S4N"))
    
    val res2: Queue[String] = (queue++:queue2)
    assert(res == res2)
    val res3: Queue[String] = (queue2++:queue)
    assert(res != res3)
  }

  test("function: /: (like fold to list)") {
    val queue: Queue[Int] = Queue(1, 2, 3, 4)
    val res: Int = queue./:(5) { (x, y) => x + y }
    println(res)
    assert(res == 15)
  }

  test("apply, lastOption") {
    val queue: Queue[Int] = Queue(1, 2, 3, 4, 5)
    val emptyQueue: Queue[Int] = Queue.empty
    assert(queue.apply(0) == 1)
    assert(queue.lastOption == Some(5))
    assert(emptyQueue.lastOption == None)
  }

  //Queues al ser secuencias se comportan igual que listas, por tanto, se pueden aplicar métodos como fold, map, etc..
}
