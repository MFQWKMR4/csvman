package core

import common.Manipulator.toCliOpSeq

object Main {
  def main(args: Array[String]) = {
    val cliOptions = toCliOpSeq(args)
    cliOptions
      .map { cliOption =>
        val csvFilePathOpt = cliOption.getFilePathOpt(cliOptions)
        cliOption.toCommand(csvFilePathOpt)
      }
      .sortBy(_.priority)
      .foreach(_.exec)
  }
}
