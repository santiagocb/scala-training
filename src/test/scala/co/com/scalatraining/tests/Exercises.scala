package co.com.scalatraining.tests

import java.util.concurrent.Executors

import org.scalatest.FunSuite

import scala.collection.immutable.Queue
import scala.language.postfixOps
import scala.util.{Failure, Success}
import scala.concurrent.{Await, ExecutionContext, Future}
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global

class Exercises extends FunSuite{

  //  Suite de Exercises para hacer ejercicios propios

  test("Crear lista de maps a partir de lista") {
    val lista = Queue(1, 2, 3, 4)
    val res = lista.map(x => Map(x -> x)).flatMap(x => Queue(x.head._1 * x.head._2))
    val lista2 = List(1, 2, 3, 4)
    //println(res)
  }

  test ("Convertir un mapa a un Queue") {
    val mapa1 = Map(1->1, 2->2)
    val mapa2 = Map(3->3, 4->4)
    var res = mapa2.foldLeft(Queue[Int]())((x, y) => x.enqueue(y._2))
    //val res2 = mapa1.map(x => x._2 * res.dequeue._1)
  }

  test ("Multiplicar dos listas") {
    val lista1 = List(2, 3, 4, 5)
    val lista2 = List(6, 7, 8, 0)
    val multiply = lista1.zip(lista2).flatMap(x => List(x._1*x._2))

    assert(multiply == List(12, 21, 32, 0))
  }

  test("Multiplicando listas que tienen None") {
    val lista1 = List(2, 3, Nil, 5)
    val lista2 = List(6, Nil, 8, 0)
    //val multiply = lista1.zip(lista2).flatMap(x => List(x._1*x._2))
    //println(multiply)
  }

  test("Obteniendo temperatura") {
    def getTemperature(): Double = {
      Thread.sleep(300)
      30 / 0
    }
    def toKelvin(grades: Double): Double = {
      Thread.sleep(300)
      grades + 273.15
    }

    val temperature = Future { getTemperature() }
      .flatMap(x => Future{
          toKelvin(x)
        }.recover{case e: Exception => toKelvin(30.0)}
      )
      .recover{case e: Exception => 30.0}
        .flatMap(x => Future{
          toKelvin(x)
        }.recover{case e: Exception => toKelvin(30.0)})
    temperature.foreach(println)
  }

  test("Hilos Clima") {           //IMPLEMENTAR BIEN CON CLASES Y PRUEBA
    val contextWeather = ExecutionContext.fromExecutorService(Executors.newFixedThreadPool(5))
    val contextSave = ExecutionContext.fromExecutorService(Executors.newFixedThreadPool(1))

    def getWeather(): Future[Int] = Future{
      Thread.sleep(50)
      println(s"El servicio de Clima arroja 10 con hilo: ${Thread.currentThread().getName}")
      10
    } (contextWeather)

    def saveDB(weather: Int): Future[String] = Future {
      Thread.sleep(300)
      println(s"Estoy guardanding en la DB con hilo: ${Thread.currentThread().getName}")
      s"Estoy guardando en la DB + ${weather}"
    } (contextSave)

    Future.sequence{ Range(1,10).map(x => getWeather().flatMap(y => saveDB(y))) }

  }

}
