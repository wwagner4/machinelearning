package machinelearning

import org.scalatest.FunSuite
import breeze.linalg._

class BreezeSuite extends FunSuite {

  test("performance inverse matrix") {
    List(5, 5, 10, 100, 200, 500, 1000, 2000, 5000).foreach{n =>
      val y = DenseMatrix.rand[Double](n, n)

      val t1  = System.currentTimeMillis()
      inv(y)
      val t2  = System.currentTimeMillis()

      println("inv %5d x %5d  %10d milis" format(n, n, (t2 - t1)))


    }


  }

}
