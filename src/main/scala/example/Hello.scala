package example

object Hello {

  object Manipulator {

    // たぶんここにcsvを保持して、これを操作する
    var csvData = ???

    case class CliOp(key: String, value: String) {
      require(key.startsWith("-"))

      def toCommand: Command = key match {
        case _ if key == "-f" || key == "--file"  => OptionF(value)
        case _ if key == "-o" || key == "--order" => OptionO(value)
        case _ => throw new Exception("failed to convert to Command")
      }

    }

    abstract class Command {
      /* the order of command execution */
      val priority: Int

      /* command execution */
      def exec: Unit
    }

    /* -f --file */
    case class OptionF(value: String) extends Command {

      override val priority: Int = 0

      override def exec = ???

    }

    /* -o --order */
    case class OptionO(value: String) extends Command {

      override val priority: Int = 1

      override def exec = ???

    }
  }

  def toCliOpSeq(args: Array[String]): Seq[Manipulator.CliOp] = {
    def inner(ret: Seq[Manipulator.CliOp], rest: Array[String]): Seq[Manipulator.CliOp] = {
      rest match {
        case Nil     => ret
        case h :: li => inner(ret :+ Manipulator.CliOp(h, li.head), li.tail)
      }
    }
    inner(Seq(), args)
  }

  def main(args: Array[String]) = {
    val cliOptions = toCliOpSeq(args)
    cliOptions
      .map(_.toCommand)
      .sortBy(_.priority)
      .foreach(_.exec)
  }
}
