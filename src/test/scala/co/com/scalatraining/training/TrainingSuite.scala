package co.com.scalatraining.training

import org.scalatest.FunSuite

class TrainingSuite extends FunSuite{
  test("Reduce -> counting words") {
    val sentences = List("Mary read a story to Sam and Isla.",
    "Isla cuddled Sam.",
    "Sam chortled.")
    assert(sentences.foldLeft(0)((acu, item) => acu + item.split(" ").count(str => str.equals("Sam"))) == 2)
  }

  test("Filter") {
    val sentences = List("Mary read a story to Sam and Isla.",
      "Isla cuddled Sam.",
      "Sam chortled.")
    
  }
}
