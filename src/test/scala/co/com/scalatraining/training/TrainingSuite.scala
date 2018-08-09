package co.com.scalatraining.training

import org.scalatest.FunSuite

class TrainingSuite extends FunSuite{
  test("Reduce -> counting words") {
    val sentences = List("Mary read a story to Sam and Isla.",
    "Isla cuddled Sam.",
    "Sam chortled.")
    assert(sentences.foldLeft(0)((acu, item) => acu + item.split(" ").count(str => str.equals("Sam"))) == 2)
  }

  test("Funciones anónimas / Funciones anónimas como parámetros") {
    val resFuncion = (i: Int) => i + 1
    assert(resFuncion(3) == 4)

    def foo(x: Int): Int = {x * 2}
    assert(foo(resFuncion(2)) == 6)
  }

  test("Aplicación parcial de los parámetros de una función") {
    def suma(m: Int, n: Int) = m + n
    val res = suma(_:Int, 2)
    assert(res(3) == 5)
  }

  test("Currying args") {     //pasar parámetros en distintos momentos
    def multiplicar(m: Int)(n: Int): Int = m * n
    assert(multiplicar(5)(3) == 15)

    val res = multiplicar(3)(_)
    val res2 = res(3)
    val res3 = multiplicar _
    assert(res2 == 9)
    assert(res3(3)(3) == 9)
  }

  test("Se puede currear una función que tiene múltiples parámetros") {
    def suma(m: Int, n: Int) = m + n
    val curriedSuma = (suma _).curried
    assert(curriedSuma(3)(3) == 6)
    val curried1 = curriedSuma(3)(_)
    assert(curried1(2) == 5)
  }

  test("Tamaño variable de los args") {
    def capitalizeAll(args: String*): Seq[String] = {
      args.map { arg =>
        arg.capitalize
      }
    }
    assert(capitalizeAll("hola", "mundo") == List("Hola", "Mundo"))
  }

  test("Sobre herencia") {
    class A(b: String){
      val e = b
      def a(x: Int, y: Int): Int = x + 1 + y
    }

    class B(b: String) extends A(b: String) {
      override def a(x: Int, y: Int): Int = super.a(x, y)   //reusar el método de la clase padre
    }

    class C(b: String) extends A(b: String) {
      override def a(x: Int, y: Int): Int = -x -y     //redefinir un método
    }

    val a = new A("a")
    val b = new B("b")
    val c = new C("c")
    assert(a.a(1, 1) == b.a(1, 1))
    assert(c.a(1, 1) == -2)
  }

  test("Clases abstractas") {
    abstract class Forma {                  //abstract class no puede instanciarse
      def getArea(): Int
    }

    class Circulo(radio: Int) extends Forma {
      def getArea(): Int = radio * radio * 3
    }

    val circulo1 = new Circulo(2)
    assert(circulo1.getArea() == 12)
  }

  test("Redefiniendo el apply") {
    class A {
      def apply() = 0
    }
    val a = new A()
    assert(a() == 0)
  }

  test("Trait Function 0-22") {
    object addOne extends Function1[Int, Int] {
      def apply(m: Int): Int = m + 1
    }

    object sum extends Function2[Int, Int, String] {
      def apply(m: Int, n: Int): String = "hola"
    }

    assert(addOne(1) == 2)
    assert(sum(2, 1) == "hola")
  }

  test("Pattern matching con guards") {
    val times = 1
    val res = times match {
      case i if i == 1 => "one"
      case i if i == 2 => "two"
      case _ => "some other number"
    }
    assert(res == "one")
  }

  test("Compose: funciones") {
    def f(s: String) = "f(" + s + ")"
    def g(s: String) = "g(" + s + ")"

    val fComposeG = f _ compose g _
    assert(fComposeG("yay") == f(g("yay")))
  }

  test("andThen -> reverse compose: funciones") {
    def f(s: String) = "f(" + s + ")"
    def g(s: String) = "g(" + s + ")"

    val fAndThenG = f _ andThen  g _
    val fAndThenGAndThenf = f _ andThen g _ andThen f _   //componer más de 2 funciones
    assert(fAndThenG("yay") == g(f("yay")))
  }

  test("partialFunction: funciones con dominio limitado") {
    val one: PartialFunction[Int, String] = { case 1 => "one" }
    assert(one.isDefinedAt(1))
    assert(!one.isDefinedAt(2))
  }

test("Componiendo un partialFunction") {          //A PartialFunction is a subtype of Function so filter can also take a PartialFunction!
    val one: PartialFunction[Int, String] = { case 1 => "one" }
    val two: PartialFunction[Int, String] = { case 2 => "two" }
    val three: PartialFunction[Int, String] = { case 3 => "three" }
    val wildcard: PartialFunction[Int, String] = { case _ => "something else" }
    val partial = one orElse two orElse three orElse wildcard
    assert(partial(1) == "one")
    assert(partial.isDefinedAt(9))
  }
}
