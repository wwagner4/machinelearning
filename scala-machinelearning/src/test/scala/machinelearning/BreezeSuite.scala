package machinelearning

import org.scalatest.FunSuite
import breeze.linalg._

class BreezeSuite extends FunSuite {

  test("performance inverse matrix") {
    //    List(5, 5, 10, 100, 200, 500, 1000, 2000, 5000).foreach{n =>
    List(5, 5, 10, 100).foreach { n =>
      val y = DenseMatrix.rand[Double](n, n)

      val t1 = System.currentTimeMillis()
      inv(y)
      val t2 = System.currentTimeMillis()

      println("inv %5d x %5d  %10d milis" format(n, n, (t2 - t1)))
    }
  }

  test("vector operations") {
    println("--- VECTOR OPERATIONS -------------------------------------")

    val x: Vector[Double] = DenseVector(1.0, 2.0, 3.0)
    println("x   : %s" format x)
    println("x'  : %s" format x.t)

    val y = x.t * x
    println("x' * x : %s" format y)

    val s = x + x
    println("x + x : %s" format s)

    val s1 = sum(x)
    println("sum(x) : %s" format s1)

  }

  test("vector matrix operations") {
    println("--- VECTOR MATRIX OPERATIONS -------------------------------------")

    val x: Vector[Double] = DenseVector(1.0, 2.0, 3.0)
    val M: Matrix[Double] = DenseMatrix((1.0, 2.0, 3.0), (2.0, 3.0, 4.0), (3.0, 4.0, 5.0))

    println("M: %s" format M)

    val M1 = M.t
    println("M': %s" format M1)

    val y0 =  M * x
    println("M * x : %s" format y0)

  }

}
