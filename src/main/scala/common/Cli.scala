package common

import Manipulator._

object Cli {

  case class Op(key: String, value: String) {
    require(key.startsWith("-"))

    def toCommand: Command = key match {
      case _ if key == "-s" || key == "--source"      => OptionF(value)
      case _ if key == "-d" || key == "--destination" => OptionD(value)
      case _ if key == "-o" || key == "--order"       => OptionO(value)
      case _ => throw new Exception("failed to convert to Command")
    }
  }

  def toCliOpSeq(args: Array[String]): Seq[Cli.Op] = {
    def inner(ret: Seq[Cli.Op], rest: Seq[String]): Seq[Cli.Op] = {
      rest match {
        case Nil => ret
        case h +: li =>
          inner(ret :+ Cli.Op(h, li.head), li.tail)
      }
    }

    inner(Seq(), args.toSeq)
  }
}
