package machinelearning

import org.scalatest._
import Matchers._
import breeze.linalg._

class MultivariantLinearRegressionSuite extends FunSuite {

  import GradientDescent._
  import HypothesisFunction._

  val trainingSet01 = List(
    Sample(Vector(1.0, 0.0), 2.1),
    Sample(Vector(1.0, 0.5), 2.2),
    Sample(Vector(1.0, 1.0), 3.2),
    Sample(Vector(1.0, 2.1), 3.0),
    Sample(Vector(1.0, 4.8), 6.1))

  val trainingSet02 = List(
    Sample(Vector(1.0, 0.0, 0.3, 1.1), 2.1),
    Sample(Vector(1.0, 0.5, 2.3, 0.7), 2.2),
    Sample(Vector(1.0, 1.0, 0.3, 1.2), 3.2),
    Sample(Vector(1.0, 2.1, 4.3, 0.3), 3.0),
    Sample(Vector(1.0, 4.8, 5.2, 2.4), 6.1),
    Sample(Vector(1.0, 2.0, 6.3, 0.7), 2.1),
    Sample(Vector(1.0, 3.5, 7.4, 2.5), 2.2),
    Sample(Vector(1.0, 4.0, 3.5, 3.6), 3.2),
    Sample(Vector(1.0, 2.6, 0.6, 0.7), 3.0),
    Sample(Vector(1.0, 4.7, 0.8, 5.8), 6.1))

  {
    val testData = List(
      (Vector(0.0, 0.0), 6.57),
      (Vector(1.0, 0.0), 3.75),
      (Vector(0.0, 1.0), 1.46),
      (Vector(1.0, 1.0), 0.32),
      (Vector(1.93, 0.82), 0.07))

    val cf = costFunc(linearFunc)(trainingSet01) _
    testData.foreach { case (t, shouldCost) =>
      test(s"Cost 01 ${format(t)}") {
        cf(t) should be(shouldCost +- 0.01)
      }
    }
  }


  {
    val testData = List(
      (Vector(0.0, 0.0, 2.9, 4.1), 132.37),
      (Vector(1.0, 0.0, 4.4, 5.3), 316.01),
      (Vector(0.0, 1.0, 1.1, 3.3), 60.81),
      (Vector(1.0, 1.0, 1.0, 1.0), 20.67),
      (Vector(2.1057, 0.8435, -0.2732, -0.0338), 0.29))

    val cf = costFunc(linearFunc)(trainingSet02) _
    testData.foreach { case (t, shouldCost) =>
      test(s"Cost 02 ${format(t)}") {
        cf(t) should be(shouldCost +- 0.01)
      }
    }
  }

  {
    val expectedValues = Map(
      0 -> Vector(0.00, 0.00),
      10 -> Vector(0.99, 1.12),
      20 -> Vector(1.35, 1.01),
      30 -> Vector(1.57, 0.94),
      40 -> Vector(1.71, 0.89),
      50 -> Vector(1.80, 0.87),
      60 -> Vector(1.85, 0.85),
      70 -> Vector(1.88, 0.84),
      80 -> Vector(1.91, 0.83),
      90 -> Vector(1.92, 0.83))
    val gd = gradientDescent(0.1)(linearFunc)(trainingSet01) _
    val initialThet: Vector[Double] = Vector(0.0, 0.0)
    val steps = Stream.iterate(initialThet)(thet => gd(thet))
    steps.take(100)
      .zipWithIndex
      .filter { case (_, i) => i % 10 == 0 }
      .foreach { case (thet, i) =>
        test(s"Gradient Descent 01 assert step $i") {
          eq(thet, expectedValues(i))
        }
      }
  }

  {
    val expectedValues = Map(
      0 -> Vector(0.00, 0.00, 0.00, 0.00),
      50 -> Vector(1.00, 0.77, -0.11, 0.25),
      100 -> Vector(1.49, 0.83, -0.19, 0.10),
      150 -> Vector(1.76, 0.85, -0.23, 0.03),
      200 -> Vector(1.91, 0.85, -0.25, -0.00),
      250 -> Vector(2.00, 0.85, -0.26, -0.02),
      300 -> Vector(2.05, 0.85, -0.27, -0.02),
      350 -> Vector(2.07, 0.85, -0.27, -0.03),
      400 -> Vector(2.09, 0.84, -0.27, -0.03),
      450 -> Vector(2.10, 0.84, -0.27, -0.03))

    val gd = gradientDescent(0.05)(linearFunc)(trainingSet02) _
    val initialThet: Vector[Double] = Vector(0.0, 0.0, 0.0, 0.0)
    val steps = Stream.iterate(initialThet)(thet => gd(thet))
    steps.take(500)
      .zipWithIndex
      .filter { case (_, i) => i % 50 == 0 }
      .foreach { case (thet, i) =>
        test(s"Gradient Descent 02 assert step $i") {
          eq(thet, expectedValues(i))
        }
      }
  }

  /**
    * Compares two double vectors
    */
  private def eq(is: Vector[Double], should: Vector[Double]): Unit = {
    is.size should be(should.size)
    0 until is.size foreach { i =>
      is(i) should be(should(i) +- 0.05)
    }

  }

  /**
    * @return Formated double vector
    */
  private def format(v: Vector[Double]): String = {
    val values = v.toArray.map(v => " %.2f" format v).mkString(", ")
    f"Vector[$values]"
  }

}
