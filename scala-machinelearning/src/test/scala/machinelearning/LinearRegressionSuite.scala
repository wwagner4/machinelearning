package machinelearning

import org.scalatest.FunSuite

class LinearRegressionSuite extends FunSuite {

  val trainingSet = List(
    (0.0, 2.1),
    (0.5, 2.2),
    (1.0, 3.2),
    (2.1, 3.0),
    (4.8, 6.1))


  def linearFunc(a: Double, b: Double)(x: Double): Double = a + b * x

  type HypothesisFuncType = (Double, Double) => (Double) => Double

  def costFunc(hypothesis: HypothesisFuncType)(trainingSet: List[(Double, Double)])(a: Double, b: Double): Double = {
    val m = trainingSet.size
    val hf = hypothesis(a, b)
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
    val cf = costFunc(linearFunc)(trainingSet) _
    parameters.foreach { case (a, b) =>
      val cost = cf(a, b)
      println("  (%5.2f, %5.2f) -> %5.3f" format(a, b, cost))
    }
  }

  def oneStep(alpha: Double)(hypothesis: HypothesisFuncType)(trainingSet: List[(Double, Double)])(a: Double, b: Double): (Double, Double) = {

    def dfACost: Double = {
      val m = trainingSet.size
      val hf = hypothesis(a, b)(_)
      val s = trainingSet
        .map { case (x, y) => hf(x) - y }
        .sum
      s / m
    }

    def dfBCost: Double = {
      val m = trainingSet.size
      val hf = hypothesis(a, b)(_)
      val s = trainingSet
        .map { case (x, y) => (hf(x) - y) * x }
        .sum
      s / m
    }

    val a1 = a - alpha * dfACost
    val b1 = b - alpha * dfBCost
    (a1, b1)
  }

  test("Gradient decent") {
    println("-- GRADIENT DECENT -----------------------------")
    val os = oneStep(0.1)(linearFunc)(trainingSet)(_, _)
    val steps = Stream.iterate((0.0, 0.0)) { case (a, b) => os(a, b) }
    steps.take(100)
      .zipWithIndex
      .filter { case (_, i) => i % 10 == 0 }
      .foreach { case ((a, b), i) => println("%4d: %6.2f %6.2f" format(i, a, b)) }
  }

}