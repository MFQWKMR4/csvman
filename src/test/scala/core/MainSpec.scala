package core

import org.scalatest._

class MainSpec extends FlatSpec with Matchers {

    val pwd = sys.env.getOrElse("PWD", "~/csvman")

    "read and write" should "success" in {
        Main.main(Array("-s", pwd + "/files/sample1.csv", "-o", "pass"))
        println("success without exception!")
    }

    "copy" should "success" in {
        Main.main(Array("-s", pwd + "/files/sample1.csv", "-o", "pass", "-d", pwd + "/files/sampleOut1.csv"))
        println("success without exception!")
    }
}
