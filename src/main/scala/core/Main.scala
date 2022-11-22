package core

import common.Cli

object Main extends Cli{

  def main(args: Array[String]) =
    (toCliOpSeq(args).map(_.toCommand) :+ WriteOut)
      .sortBy(_.priority)
      .foreach(_.exec)
}
