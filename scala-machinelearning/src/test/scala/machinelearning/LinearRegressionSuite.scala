package machinelearning

import org.scalatest.FunSuite

class LinearRegressionSuite extends FunSuite {

  val trainingSet = List(
    (0.0, 2.1),
    (0.5, 2.2),
    (1.0, 3.2),
    (2.1, 3.0),
    (4.8, 6.1))

  def hypothesisFunc(a: Double, b: Double)(x: Double) = a + b * x

  def costFunc(a: Double, b: Double)(trainingSet: List[(Double, Double)]): Double = {
    val m = trainingSet.size
    val hf = hypothesisFunc(a, b)_
    val s = trainingSet
      .map { case (x, y) => hf(x) - y }
      .map(math.pow(_, 2))
      .sum
    s / (2 * m)
  }

  test("Costs for various parameter sets") {
    val parameters = List(
      (0.0, 0.0),
      (1.0, 0.0),
      (0.0, 1.0),
      (1.0, 1.0))

    parameters.foreach{case (a, b) =>
      val cf = costFunc(a, b)_
      val cost = cf(trainingSet)
      println("%.2f, %.2f -> %.2f" format(a, b, cost))

    }

  }

  def dfACost(a: Double, b: Double)(trainingSet: List[(Double, Double)]): Double = {
    val m = trainingSet.size
    val hf = hypothesisFunc(a, b)_
    val s = trainingSet
      .map { case (x, y) => hf(x) - y }
      .sum
    s / m
  }

  def dfBCost(a: Double, b: Double)(trainingSet: List[(Double, Double)]): Double = {
    val m = trainingSet.size
    val hf = hypothesisFunc(a, b)_
    val s = trainingSet
      .map { case (x, y) => (hf(x) - y) * x }
      .sum
    s / m
  }

  def oneStep(a: Double, b:Double): (Double, Double) = {
    val alpha = 0.1
    ???

  }

  test("Gradient decent") {



  }

}