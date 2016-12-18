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

  def costFunc(hyp: HypType)(thet: Vector[Double])(trainingSet: List[Sample]): Double = {
    val v1 = trainingSet.map{s =>
      val v2 = thet.t * s.x - s.y

      ""
    }

    ???
  }


}
