package machinelearning

import breeze.linalg.{Vector, sum}

/**
  * Container for one row of the training set
  */
case class Sample(x: Vector[Double], y: Double) {}

/**
  * Implementation of the Gradient-Descent algorithm
  */
object GradientDescent {

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

  def costFunc(hyp: HypType)(trainingSet: List[Sample])(thet: Vector[Double]): Double = {
    val m = trainingSet.size
    val s = trainingSet.map { s =>
      math.pow(thet.t * s.x - s.y, 2)
    }
    sum(s) / (2 * m)
  }

  /**
    * @param alpha step width
    * @param hypo hypothesis function
    * @param trainingSet training set
    * @param thet set of parameters
    * @return the next set of parameters
    */
  def gradientDescent(alpha: Double)(hypo: HypType)(trainingSet: List[Sample])
                     (thet: Vector[Double]): Vector[Double] = {
    val m = trainingSet.size.toDouble
    val hf = hypo(thet)(_)
    val array = thet.toArray.zipWithIndex.map { case (t, i) =>
      val inner = trainingSet.map { s =>
        (hf(s.x) - s.y) * s.x(i)
      }
      t - alpha * sum(inner) / m
    }
    Vector(array)
  }
}

object HypothesisFunction {

  /**
    * Linear function of type 'HypType'
    */
  def linearFunc: GradientDescent.HypType = {
    case (thet: Vector[Double]) => (x: Vector[Double]) => {
      require(thet.size > 0)
      require(x.size > 0)
      require(thet.size == x.size)

      thet.t * x
    }
  }




}
