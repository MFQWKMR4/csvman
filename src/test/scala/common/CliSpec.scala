package common

import Helpers.ComponentRegistry
import org.scalatest.mockito.MockitoSugar
import org.scalatest.{FlatSpec, Matchers}

class CliSpec extends FlatSpec with Matchers with MockitoSugar {

  trait Fixture {
    val pwd: String = sys.env.getOrElse("PWD", "~/csvman")
    val readPath: String = pwd + "/files/sample1.csv"
    val writePath: String = pwd + "/files/sampleOut1.csv"
    val cli: ComponentRegistry.Cli = ComponentRegistry.cli
    val manipulator: ComponentRegistry.Manipulator = ComponentRegistry.manipulator
  }

  "Convert to cliOption sequence" should "success" in new Fixture {
    // given
    val argumentArray: Array[String] = Array("-s", readPath, "-d", writePath, "-o", "sequential_insert")

    // when
    val toCliOpSeq: Seq[cli.Op] = cli.toCliOpSeq(argumentArray)

    // then
    toCliOpSeq should be(Seq(cli.Op("-s", readPath), cli.Op("-d", writePath), cli.Op("-o", "sequential_insert")))
  }

  // REVIEW: 以下のテストは実際の結果と期待値は同じ値で返ってきているが失敗する。
  //  該当箇所のプロダクトコードは複雑でないため、テストコードは書く必要もないかもしれない。
  "convert to OptionF" should "success" in new Fixture {
    // given
    val op: cli.Op = cli.Op("-s", readPath)

    // when
    val command = op.toCommand

    // then
    command should be(manipulator.OptionF(readPath))
  }

}
