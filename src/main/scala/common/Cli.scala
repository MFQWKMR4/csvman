package common

trait CliComponent {
  this: ManipulatorComponent =>
  val cli: Cli

  class Cli {
    case class Op(key: String, value: String) {
      require(key.startsWith("-"))

      def toCommand: manipulator.Command = key match {
        case _ if key == "-s" || key == "--source" => manipulator.OptionF(value)
        case _ if key == "-d" || key == "--destination" => manipulator.OptionD(value)
        case _ if key == "-o" || key == "--order" => manipulator.OptionO(value)
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


}
