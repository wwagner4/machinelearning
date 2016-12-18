package machinelearning

import org.scalatest.FunSuite
import breeze.linalg._

/**
  * Experiments around multivariant linear regression
  */
class MultivariantLinearRegressionSuite extends FunSuite {

  /**
    * Container for one row of the training set
    */
  case class Sample(x: Vector[Double], y: Double) {}

  /**
    * Same values as in the LinearRegressionSuite
    */
  val trainingSet01 = List(
    Sample(DenseVector(1.0, 0.0), 2.1),
    Sample(DenseVector(1.0, 0.5), 2.2),
    Sample(DenseVector(1.0, 1.0), 3.2),
    Sample(DenseVector(1.0, 2.1), 3.0),
    Sample(DenseVector(1.0, 4.8), 6.1))

  /**
    * Other random testfeatures
    */
  val trainingSet02 = List(
    Sample(DenseVector(1.0, 0.0, 0.3, 1.1), 2.1),
    Sample(DenseVector(1.0, 0.5, 2.3, 0.7), 2.2),
    Sample(DenseVector(1.0, 1.0, 0.3, 1.2), 3.2),
    Sample(DenseVector(1.0, 2.1, 4.3, 0.3), 3.0),
    Sample(DenseVector(1.0, 4.8, 5.2, 2.4), 6.1),
    Sample(DenseVector(1.0, 2.0, 6.3, 0.7), 2.1),
    Sample(DenseVector(1.0, 3.5, 7.4, 2.5), 2.2),
    Sample(DenseVector(1.0, 4.0, 3.5, 3.6), 3.2),
    Sample(DenseVector(1.0, 2.6, 0.6, 0.7), 3.0),
    Sample(DenseVector(1.0, 4.7, 0.8, 5.8), 6.1))


  /**
    * A function with two input vectors an one output value
    *
    * thet => x => y
    *
    * thet: parameters (to be optimized)
    * x   : sample values (featuers)
    * y   : result of the hypothesis (to be minimized)
    */
  type HypType = Vector[Double] => Vector[Double] => Double

  /**
    * Linear function of type 'HypType'
    */
  def linearFunc(thet: Vector[Double])(x: Vector[Double]): Double = {
    require(thet.size > 0)
    require(x.size > 0)
    require(thet.size == x.size)

    thet.t * x
  }

  def costFunc(hyp: HypType)(trainingSet: List[Sample])(thet: Vector[Double]): Double = {
    val m = trainingSet.size
    val s = trainingSet.map { s =>
      math.pow(thet.t * s.x - s.y, 2)
    }.sum
    s / ( 2 * m)
  }

  test("Cost function training set 01") {
    val thet = List(
      DenseVector(0.0, 0.0),
      DenseVector(1.0, 0.0),
      DenseVector(0.0, 1.0),
      DenseVector(1.0, 1.0),
      DenseVector(1.93, 0.82))

    println("-- COST FUNCTION -----------------------------")
    val cf = costFunc(linearFunc)(trainingSet01) _
    thet.foreach{t =>
      val cost = cf(t)
      println("   %30s -> %10.2f" format(t, cost))
    }
  }

  test("Cost function training set 02") {
    val thet = List(
      DenseVector(0.0, 0.0, 2.9, 4.1),
      DenseVector(1.0, 0.0, 4.4, 5.3),
      DenseVector(0.0, 1.0, 1.1, 3.3),
      DenseVector(1.0, 1.0, 1.0, 1.0),
      DenseVector(1.93, 0.82, 2.0, 3.0))

    println("-- COST FUNCTION -----------------------------")
    val cf = costFunc(linearFunc)(trainingSet02) _
    thet.foreach{t =>
      val cost = cf(t)
      println("   %30s -> %10.2f" format(t, cost))
    }
  }

}
