package machinelearning

import breeze.linalg.{DenseMatrix, sum}

/**
  * Container for one row of the training set
  */
case class TrainingSet(X: DenseMatrix[Double], y: DenseMatrix[Double])

/**
  * Implementation of the Gradient-Descent algorithm
  */
object GradientDescent {

  /**
    * A function with two input vectors an one output value
    *
    * thet => X => y
    *
    * thet: column vector of parameters (to be optimized)
    * X   : sample values (featuers). first column must be 1.0
    * y   : result (column vector) of the hypothesis (to be minimized)
    */
  type HypType = DenseMatrix[Double] => DenseMatrix[Double] => DenseMatrix[Double]

  def costFunc(hyp: HypType)(ts: TrainingSet)(thet: DenseMatrix[Double]): Double = {
    val h = hyp(thet)(_)
    val m = ts.y.rows
    sum((h(ts.X) - ts.y) :^ 2.0) / (2 * m)
  }

  /**
    * @param alpha step width
    * @param hypo hypothesis function
    * @param ts training set
    * @param thet set of parameters
    * @return the next set of parameters
    */
  def gradientDescent(alpha: Double)(hypo: HypType)(ts: TrainingSet)
                     (thet: DenseMatrix[Double]): DenseMatrix[Double] = {
    val m = ts.y.rows.toDouble
    val hf = hypo(thet)(_)

    val v0 = hf(ts.X)
    println(s"v0 ${v0.rows} x ${v0.cols}\n$v0")
    println(s"ts.y ${ts.y.rows} x ${ts.y.cols}\n${ts.y}")
    val v1 = v0 - ts.y

    thet - alpha * ((1/m) *  v1.t * ts.X).t
  }

}

object HypothesisFunction {

  /**
    * Linear function of type 'HypType'
    */
  def linearFunc: GradientDescent.HypType = {
    case (thet: DenseMatrix[Double]) => (X: DenseMatrix[Double]) => {
      require(thet.cols == 1)
      require(thet.rows > 0)
      require(X.rows > 0)
      require(X.cols == thet.rows)

      X * thet

    }
  }




}
