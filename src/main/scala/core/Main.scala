package core

import common.Manipulator._
import common.Cli._

object Main {

  def main(args: Array[String]) =
    (toCliOpSeq(args).map(_.toCommand) :+ WriteOut)
      .sortBy(_.priority)
      .foreach(_.exec)
}
