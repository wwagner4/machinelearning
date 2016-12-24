package machinelearning

import org.scalatest._
import Matchers._
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
    }
    sum(s) / (2 * m)
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
    thet.foreach { t =>
      val cost = cf(t)
      println(f"${format(t)}%30s -> $cost%10.2f")
    }
  }

  test("Cost function training set 02") {
    val thet = List(
      DenseVector(0.0, 0.0, 2.9, 4.1),
      DenseVector(1.0, 0.0, 4.4, 5.3),
      DenseVector(0.0, 1.0, 1.1, 3.3),
      DenseVector(1.0, 1.0, 1.0, 1.0),
      DenseVector(2.1057738032395816, 0.8435673329439073, -0.27329323871209565, -0.033870769165503745))

    println("-- COST FUNCTION -----------------------------")
    val cf = costFunc(linearFunc)(trainingSet02) _
    thet.foreach { t =>
      val cost = cf(t)
      println(f"${format(t)}%40s -> $cost%10.2f")
    }
  }

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
    DenseVector(array)
  }

  test("Gradient descent 01") {
    println("-- GRADIENT DESCENT -----------------------------")
    val gd = gradientDescent(0.1)(linearFunc)(trainingSet01) _
    val initialThet: Vector[Double] = DenseVector(0.0, 0.0)
    val steps = Stream.iterate(initialThet)(thet => gd(thet))
    steps.take(100)
      .zipWithIndex
      .filter { case (_, i) => i % 5 == 0 }
      .foreach { case (thet, i) => println(f"$i%4d : ${format(thet)}") }
  }

  test("Gradient descent 02 output") {
    println("-- GRADIENT DESCENT -----------------------------")
    val gd = gradientDescent(0.05)(linearFunc)(trainingSet02) _
    val initialThet: Vector[Double] = DenseVector(0.0, 0.0, 0.0, 0.0)
    val steps = Stream.iterate(initialThet)(thet => gd(thet))
    steps.take(500)
      .zipWithIndex
      .filter { case (_, i) => i % 50 == 0 }
      .foreach { case (thet, i) => println(f"$i%4d : ${format(thet)}") }
  }

  {
    val expectedValues = Map(
      (0 -> Vector(0.00, 0.00, 0.00, 0.00)),
      (50 -> Vector(1.00, 0.77, -0.11, 0.25)),
      (100 -> Vector(1.49, 0.83, -0.19, 0.10)),
      (150 -> Vector(1.76, 0.85, -0.23, 0.03)),
      (200 -> Vector(1.91, 0.85, -0.25, -0.00)),
      (250 -> Vector(2.00, 0.85, -0.26, -0.02)),
      (300 -> Vector(2.05, 0.85, -0.27, -0.02)),
      (350 -> Vector(2.07, 0.85, -0.27, -0.03)),
      (400 -> Vector(2.09, 0.84, -0.27, -0.03)),
      (450 -> Vector(2.10, 0.84, -0.27, -0.03)))

    val gd = gradientDescent(0.05)(linearFunc)(trainingSet02) _
    val initialThet: Vector[Double] = DenseVector(0.0, 0.0, 0.0, 0.0)
    val steps = Stream.iterate(initialThet)(thet => gd(thet))
    steps.take(500)
      .zipWithIndex
      .filter { case (_, i) => i % 50 == 0 }
      .foreach { case (thet, i) =>
        test(s"Gradient Descent $i ${format(thet)}") {
          eq(thet, expectedValues(i))
        }
      }
  }

  private def eq(is: Vector[Double], should: Vector[Double]): Unit = {
    is.size should be (should.size)
    0 until is.size foreach {i =>
      is(i) should be (should(i) +- 0.05)
    }

  }

  private def format(v: Vector[Double]): String = {
    val values = v.toArray.map(v => " %.2f" format v).mkString(", ")
    f"Vector[$values]"
  }

}
