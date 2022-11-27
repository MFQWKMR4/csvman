package core

import Helpers.ComponentRegistry

object Main {

  def main(args: Array[String]) =
    (ComponentRegistry.cli.toCliOpSeq(args).map(_.toCommand) :+ ComponentRegistry.manipulator.WriteOut)
      .sortBy(_.priority)
      .foreach(_.exec)

}
