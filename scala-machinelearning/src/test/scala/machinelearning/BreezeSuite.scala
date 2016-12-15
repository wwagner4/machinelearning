package machinelearning

import org.scalatest.FunSuite
import breeze.linalg._
import breeze.linalg.operators._

class BreezeSuite extends FunSuite {

  test("create and show matrixes") {
    val x = DenseVector.rand[Double](5)
    println(x)

    val y = DenseMatrix.rand[Double](5, 5)
    println(y)

    val z =  inv(y)
    println(z)

  }

}
