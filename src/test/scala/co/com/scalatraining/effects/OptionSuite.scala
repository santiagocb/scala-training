package co.com.scalatraining.effects

import org.scalatest.FunSuite

import scala.collection.immutable.Seq

class OptionSuite extends FunSuite {

  test("Se debe poder crear un Option con valor"){
    val s = Option{   //Llaves o parentesis
      1
    }
    assert(s == Some(1))
  }

  test("Se debe poder crear un Some con valor"){
    val s = Some(1)   //Llaves o parentesis
    assert(s == Some(1))
  }

  test("Se debe poder crear un Some con valor null"){
    val s = Some{
      null
    }
    println(s)
    assert(s == Some(null))
  }

  test("Se debe poder crear un Option con valor null --> None"){
    val s = Option(null)
    println(s)
    assert(s == None)
  }

  test("Se debe poder crear un Option para denotar que no hay valor"){
    val s = None
    assert(s == None)
  }

  test("Es inseguro acceder al valor de un Option con get"){
    val s = None
    assertThrows[NoSuchElementException]{
      val r = s.get                       //get es inseguro NO USARLO
    }
  }

  test("Se debe poder hacer pattern match sobre un Option") {
    val lista: Seq[Option[String]] = List(Some("Andres"), None, Some("Luis"), Some("Pedro"))
    val nombre: Option[String] = lista(1)
    var res = ""
    res = nombre match {              //No es una forma funcional de trabajar el Option
      case Some(nom) => nom
      case None => "NONAME"
    }
    assert(res == "NONAME")
  }

  test("Fold en Option"){
    val o = Option(1)

    val res: Int = o.fold{
      10
    }{
      x => x + 20
    }
    println(res)
    assert(res == 21)
  }

  test("Fold en Option = {Some or non}"){
    def f (i: Int):Option[Int] = {
      if (i % 2 == 0) {
        Some(i)
      }else{
        None
      }
    }

    val o = f(2)

    val res: Int = o.fold{
      10
    }{
      x => x + 20
    }
    println(res)
    assert(res == 22)
  }

  test("Se debe poder saber si un Option tiene valor con isDefined") {
    val lista = List(Some("Andres"), None, Some("Luis"), Some("Pedro"))
    val nombre = lista(0)
    assert(nombre.isDefined)
  }

  test("Se debe poder acceder al valor de un Option de forma segura con getOrElse") {
    val lista = List(Some("Andres"), None, Some("Luis"), Some("Pedro"))
    val nombre = lista(1)
    val res = nombre.getOrElse("NONAME")
    assert(res == "NONAME")
  }

  test("getOrElse como un fold") {
    val lista = List(Some("Andres"), None, Some("Luis"), Some("Pedro"))
    val nombre = lista(1)

    def goe(s: Option[String]) = {
      s.fold{"NONAME"}{x => x}
    }

    val res2 = goe(nombre)
    val res = nombre.getOrElse("NONAME")
    assert(res == res2)
    assert(res == "NONAME")
  }

  test("Un Option se debe poder transformar con un map") {
    val lista = List(Some("Andres"), None, Some("Luis"), Some("Pedro"))
    val nombre = lista(0)
    val nombreCompleto: Option[String] = nombre.map(s => s + " Felipe")
    assert(nombreCompleto.getOrElse("NONAME") == "Andres Felipe")
  }

  test("Un Option se debe poder transformar con flatMap en otro Option") {
    val lista = List(Some("Andres"), None, Some("Luis"), Some("Pedro"))
    val nombre = lista(0)

    val resultado: Option[String] = nombre.flatMap(s => Option(s.toUpperCase))
    resultado.map( s => assert( s == "ANDRES"))
  }

  test("Un Option se debe poder filtrar con una hof con filter") {
    val lista = List(Some(5), None, Some(40), Some(20))
    val option0 = lista(0)
    val option1 = lista(1)
    val res0 = option0.filter(_>10)
    val res1 = option1.filter(_>10)

    assert(res0 == None)
    assert(res1 == None)
  }

  test("Un Option se debe poder filtrar con una hof con filter 2") {
    val lista = List(Some(5), None, Some(40), Some(20))
    val option0 = lista(0)
    val option1 = lista(1)
    val option2 = lista(0)
    val res0 = option0.filter(_>10)
    val res1 = option1.filter(_>10)
    val res2 = option2.filter(_>2)
    //val res3 = lista.filter(x => x >10)
    assert(res0 == None)
    assert(res1 == None)
    assert(res2 == Some(5))
  }

  test("for comprehensions en Option") {
    val lista = List(Some(5), None, Some(40), Some(20))
    val s1 = lista(0)
    val s2 = lista(2)

    val resultado = for {
      x <- s1
      y <- s2
      z <- s2
    } yield x+y+z

    assert(resultado == Some(85))
  }

  test("for comprehensions en Option con None") {
    val lista = List(Some(5), None, Some(40), Some(20))
    val s1 = lista(0)
    val s2 = lista(2)
    val s3 = lista(1)

    val resultado = for {
      x <- s1
      y <- s2
      z <- s3
    } yield x+z
    assert(resultado == None)
  }

  test("for comprehensions en Option con None 2") {
    val lista = List(Some(5), None, Some(40), Some(20))
    val s1 = lista(0)
    val s2 = lista(2)
    val s3 = lista(1)

    def foo(i: Int): Some[Int] = {
      println(s"ejecutando foo con ${i}")
      Some(i)
    }

    def bar(i: Int): Option[Int] = {
      println(s"ejecutando bar con ${i}")
      None
    }

    val resultado = for {
      x <- foo(1)
      y <- bar(1)
      z <- foo(1)
    } yield x+y+z
    //println(resultado)
    assert(resultado == None)
  }

  test("for comprehensions en Option con None 3") {
    val lista = List(Some(5), None, Some(40), Some(20))
    val s1 = lista(0)
    val s2 = lista(2)
    val s3 = lista(1)

    def foo(i: Int): Some[Int] = {
      println(s"ejecutando foo con ${i}")
      Some(i)
    }

    def bar(i: Int): Option[Int] = {
      println(s"ejecutando bar con ${i}")
      None
    }

    val resultado = for {
      x <- foo(1)
      y <- foo(1)
      z <- foo(1)
      q <- foo(1)
      w <- bar(1)
      e <- foo(1)
      r <- foo(1)
      t <- foo(1)
      u <- foo(1)
    } yield x+y+z+q+w+e+r+t+u
    //println(resultado)
    assert(resultado == None)
  }


  test("for comprehensions igual a flatMaps") {
    val o1 = Some(0)
    val o2 = Some(2)
    val o3 = Some(1)

    val res0 = for {
      o1 <- Some(0)
      o2 <- Some(2)
      o3 <- Some(1)
    } yield (o1+o2+o3)

    val res: Option[Int] = o1.flatMap(x =>
      o2.flatMap(y =>
        o3.flatMap(z =>
          Option(x+y+z))))
    assert(res == res0)

    //falta implementar el for yield para comprar
  }



  test("for comprehesions None en Option") {
    val consultarNombre = Some("Andres")
    val consultarApellido = Some("Estrada")
    val consultarEdad = None
    val consultarSexo = Some("M")

    val resultado = for {
      nom <- consultarNombre
      ape <- consultarApellido
      eda <- consultarEdad
      sex <- consultarSexo
    //} yield (nom+","+ape+","+eda+","+sex)
    } yield (s"$nom $ape, $eda,$sex")

    assert(resultado == None)
  }

  test("for comprehesions None en Option 2") {

    def consultarNombre(dni:String): Option[String] = Some("Felix")
    def consultarApellido(dni:String): Option[String] = Some("Vergara")
    def consultarEdad(dni:String): Option[String] = None
    def consultarSexo(dni:String): Option[String] = Some("M")

    val dni = "8027133"
    val resultado = for {
      nom <- consultarNombre(dni)
      ape <- consultarApellido(dni)
      eda <- consultarEdad(dni)
      sex <- consultarSexo(dni)
    //} yield (nom+","+ape+","+eda+","+sex)
    } yield (s"$nom $ape, $eda,$sex")

    assert(resultado == None)
  }

  //------------------------ Tony Morris tests --------------------------------

  test("Flatmap con option") {
    val res = Some(1)
    def foo (o: Option[Int]): Option[Int] = {
      None        //Retorna none siempre con la funcion
    }
    assert(foo(res).flatMap(x => Option(x + x)) == None)
    assert(res.flatMap(x => Option(x + x)) == Option(2))
  }

  test("Flatten con option") {      //Flatten debe tener wrapper de tipo y subtipo
    val res = Some(Some(1))
    assert(res.flatten == Some(1))
  }

  test("Mapeando una lista que está dentro del option") {
    val res = Some(List(1,2,3))    //Some(List(1,1,1))
    def foo (o: Option[List[Int]]): Option[List[Int]] = {
      None        //Retorna none siempre con la funcion
    }
    val res1 = res.map(l => l.map(x => 1))
    assert(res1 == Some(List(1,1,1)))
    assert(foo(res1) == None)
  }

  test("Foreach con option") {
    val res = Some(1, List(2))
    var i = 0
    res.foreach(x => i += 1)
    assert(i == 1)
  }

  test("isDefined con option") {
    val res = Some(1)
    assert(res.isDefined == true)
  }

  test("forAll con option") {
    val res = Some(1,2)
    assert(res.forall(x => x==(1,2)) == true)
    assert(None.forall(x => x==(2)) == true)
  }

  test("isEmpty con option") {
    val res = Some(1,2)
    assert(res.isEmpty == false)
    assert(None.isEmpty == true)
  }

  test("exists con option") {
    val res = Some(1)
    assert(res.exists(x => x==2) == false)
  }

  test("orElse con option") {
    val res = Some(1)
    val res1 = res.orElse(Option(3))
    val res2 = None.orElse(Option(2))
    assert(res1 == Some(1))
    assert(res2 == Some(2))
  }

  test("getOrElse con option") {
    val res = Some(1)
    val res1 = res.getOrElse(Option(3))
    val res2 = None.getOrElse(2)
    assert(res1 == 1)
    assert(res2 == 2)
  }

  test("toList con option") {
    val res = Some(1)
    val res1 = res.toList
    val res2 = None.toList
    assert(res1 == List(1))
    assert(res2 == List())
  }

}

