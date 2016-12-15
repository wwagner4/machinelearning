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
    val hf = hypothesisFunc(a, b) _
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
      (1.0, 1.0),
      (1.93, 0.82),
      (1.94, 0.82))

    println("-- COST FUNCTION -----------------------------")
    parameters.foreach { case (a, b) =>
      val cf = costFunc(a, b) _
      val cost = cf(trainingSet)
      println("  (%5.2f, %5.2f) -> %5.5f" format(a, b, cost))

    }

  }

  def dfACost(a: Double, b: Double)(trainingSet: List[(Double, Double)]): Double = {
    val m = trainingSet.size
    val hf = hypothesisFunc(a, b)(_)
    val s = trainingSet
      .map { case (x, y) => hf(x) - y }
      .sum
    s / m
  }

  def dfBCost(a: Double, b: Double)(trainingSet: List[(Double, Double)]): Double = {
    val m = trainingSet.size
    val hf = hypothesisFunc(a, b)(_)
    val s = trainingSet
      .map { case (x, y) => (hf(x) - y) * x }
      .sum
    s / m
  }

  def oneStep(alpha: Double)(trainingSet: List[(Double, Double)])(a: Double, b: Double): (Double, Double) = {
    val a1 = a - alpha * dfACost(a, b)(trainingSet)
    val b1 = b - alpha * dfBCost(a, b)(trainingSet)
    (a1, b1)
  }

  test("Gradient decent") {
    println("-- GRADIENT DECENT -----------------------------")
    val fs = oneStep(0.1)(trainingSet)(_, _)
    val steps = Stream.iterate((0.0, 0.0)) { case (a, b) => fs(a, b) }
    steps.take(100)
      .zipWithIndex
      .filter { case (_, i) => i % 10 == 0 }
      .foreach { case ((a, b), i) => println("%4d: %6.2f %6.2f" format(i, a, b)) }
  }

}