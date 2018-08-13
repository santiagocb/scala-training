package co.com.scalatraining.training

import org.scalatest.FunSuite

class StatisticsTCSuite extends FunSuite {

  //Type classes
  object Math {
    trait NumberLike[T] {
      def plus(x: T, y: T): T
      def divide(x: T, y: Int): T
      def minus(x: T, y: T): T
    }
    object NumberLike {
      implicit object NumberLikeDouble extends NumberLike[Double] {
        def plus(x: Double, y: Double): Double = x + y
        def divide(x: Double, y: Int): Double = x / y
        def minus(x: Double, y: Double): Double = x - y
      }
      implicit object NumberLikeInt extends NumberLike[Int] {
        def plus(x: Int, y: Int): Int = x + y
        def divide(x: Int, y: Int): Int = x / y
        def minus(x: Int, y: Int): Int = x - y
      }
    }
  }

  object Statistics {
    def mean[T](xs: Vector[T])(implicit ev: Math.NumberLike[T]): T =
      ev.divide(xs.reduce((a, b) => ev.plus(a, b)), xs.size)
    def median[T : Math.NumberLike](xs: Vector[T]): T = xs(xs.size / 2)
    def quartiles[T: Math.NumberLike](xs: Vector[T]): (T, T, T) =
      (xs(xs.size / 4), median(xs), xs(xs.size / 4 * 3))
    def iqr[T: Math.NumberLike](xs: Vector[T]): T = quartiles(xs) match {
      case (lowerQuartile, _, upperQuartile) =>
        implicitly[Math.NumberLike[T]].minus(upperQuartile, lowerQuartile)
    }
  }

  /*object Statistics {     ADAPTER
    trait NumberLike[A] {
      def get: A
      def plus(y: NumberLike[A]): NumberLike[A]
      def minus(y: NumberLike[A]): NumberLike[A]
      def divide(y: Int): NumberLike[A]
    }
    case class NumberLikeDouble(x: Double) extends NumberLike[Double] {
      def get: Double = x
      def minus(y: NumberLike[Double]) = NumberLikeDouble(x - y.get)
      def plus(y: NumberLike[Double]) = NumberLikeDouble(x + y.get)
      def divide(y: Int) = NumberLikeDouble(x / y)
    }
    type Quartile[A] = (NumberLike[A], NumberLike[A], NumberLike[A])
    def median[A](xs: Vector[NumberLike[A]]): NumberLike[A] = xs(xs.size / 2)
    def quartiles[A](xs: Vector[NumberLike[A]]): Quartile[A] =
      (xs(xs.size / 4), median(xs), xs(xs.size / 4 * 3))
    def iqr[A](xs: Vector[NumberLike[A]]): NumberLike[A] = quartiles(xs) match {
      case (lowerQuartile, _, upperQuartile) => upperQuartile.minus(lowerQuartile)
    }
    def mean[A](xs: Vector[NumberLike[A]]): NumberLike[A] =
      xs.reduce(_.plus(_)).divide(xs.size)
  }*/
}
