package common

object Manipulator {

  var csvData: List[List[String]] = _

  case class CliOp(key: String, value: String) {
    require(key.startsWith("-"))

    def getFilePathOpt(cliOptions: Seq[Manipulator.CliOp]): Option[String] = {
      val filePathSeq = cliOptions.filter(cliOption => cliOption.key == "-f" || cliOption.key == "--file").map(_.value)
      filePathSeq match {
        case filePathSeq if filePathSeq.nonEmpty =>
          require(filePathSeq.length == 1, "Please select only one file path")
          Some(filePathSeq.head)
        case _ => None
      }
    }

    def toCommand(csvFilePathOpt: Option[String]): Command = key match {
      case _ if key == "-f" || key == "--file" => OptionF(value)
      case _ if key == "-o" || key == "--order" => OptionO(value, csvFilePathOpt.get)
      case _ => throw new Exception("failed to convert to Command")
    }
  }

  def toCliOpSeq(args: Array[String]): Seq[Manipulator.CliOp] = {
    def inner(ret: Seq[Manipulator.CliOp], rest: Seq[String]): Seq[Manipulator.CliOp] = {
      rest match {
        case Nil => ret
        case h +: li =>
          inner(ret :+ Manipulator.CliOp(h, li.head), li.tail)
      }
    }

    inner(Seq(), args.toSeq)
  }

}