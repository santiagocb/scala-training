package co.com.scalatraining.training

import org.scalatest.FunSuite

class AnimalTCSuite extends FunSuite {

  test("Types members/classes 2") {     //IMPORTANTE
  case class Perro(nombre: String, sonido: String = "")
    case class Gato(nombre: String, sonido: String = "")

    sealed trait GenericAnimalBehavior[T] {
      def emitirSonido(animal: T): T
    }
    object GenericAnimalBehavior {
      implicit object PerroBehavior extends GenericAnimalBehavior[Perro]{
        def emitirSonido(animal: Perro) = {
          Perro(animal.nombre, "wow")
        }
      }
      implicit object GatoBehavior extends GenericAnimalBehavior[Gato]{
        def emitirSonido(animal: Gato) = {
          Gato(animal.nombre, "miau")
        }
      }
    }

    object AnimalInteraction {
      def fight[T](animal: T, animal2: T)(implicit ev: GenericAnimalBehavior[T]): String = {
        ev.emitirSonido(animal)
        ev.emitirSonido(animal2)
        "Los animales se están peleando"
      }
      def fightHarder[T: GenericAnimalBehavior, L: GenericAnimalBehavior](animal: T, animal2: L): String = {
        implicitly[GenericAnimalBehavior[T]].emitirSonido(animal)
        implicitly[GenericAnimalBehavior[L]].emitirSonido(animal2)
        "Los animales se están peleando más fuerte"
      }
    }

    println(AnimalInteraction.fightHarder(Perro("Kaiser"), Gato("Kato")))
  }

}
