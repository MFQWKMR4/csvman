package common

class Cli extends Manipulator {

  case class Op(key: String, value: String) {
    require(key.startsWith("-"))

    def toCommand: Command = key match {
      case _ if key == "-s" || key == "--source" => OptionF(value)
      case _ if key == "-d" || key == "--destination" => OptionD(value)
      case _ if key == "-o" || key == "--order" => OptionO(value)
      case _ => throw new Exception("failed to convert to Command")
    }
  }

  def toCliOpSeq(args: Array[String]): Seq[Op] = {
    def inner(ret: Seq[Op], rest: Seq[String]): Seq[Op] = {
      rest match {
        case Nil => ret
        case h +: li =>
          inner(ret :+ Op(h, li.head), li.tail)
      }
    }

    inner(Seq(), args.toSeq)
  }
}
