package core

import org.scalatest._

class MainSpec extends FlatSpec with Matchers {

  trait Fixture {
    val pwd: String = sys.env.getOrElse("PWD", "~/csvman")
    val readPath: String = pwd + "/files/sample1.csv"
    val writePath: String = pwd + "/files/sampleOut1.csv"
  }

  "read and write" should "success" in new Fixture {
    Main.main(Array("-s", readPath, "-o", "pass"))
    println("success without exception!")
  }

  "copy" should "success" in new Fixture {
    Main.main(Array("-s", readPath, "-o", "pass", "-d", writePath))
    println("success without exception!")
  }

  "sequential_insert" should "success" in new Fixture {
    Main.main(Array("-s", readPath, "-d", writePath, "-o", "sequential_insert"))
    println("success without exception!")
  }
}
