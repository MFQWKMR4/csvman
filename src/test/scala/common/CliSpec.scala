package common

import org.scalatest.mockito.MockitoSugar
import org.scalatest.{FlatSpec, Matchers}

class CliSpec extends FlatSpec with Matchers with MockitoSugar {

  trait Fixture {
    val pwd: String = sys.env.getOrElse("PWD", "~/csvman")
    val readPath: String = pwd + "/files/sample1.csv"
    val writePath: String = pwd + "/files/sampleOut1.csv"
    val cli = new Cli()
  }

  "Convert to cliOption sequence" should "success" in new Fixture {
    // given
    val argumentArray: Array[String] = Array("-s", readPath, "-d", writePath, "-o", "sequential_insert")

    // when
    val toCliOpSeq: Seq[cli.Op] = cli.toCliOpSeq(argumentArray)

    // then
    toCliOpSeq should be(Seq(cli.Op("-s", readPath), cli.Op("-d", writePath), cli.Op("-o", "sequential_insert")))
  }

  "convert to OptionF" should "success" in new Fixture {
    // given
    val op: cli.Op = cli.Op("-s", readPath)

    // when
    val command = op.toCommand

    // then
    command should be(cli.OptionF(readPath))
  }
}
