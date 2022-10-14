package common

import com.github.tototoshi.csv.CSVWriter
import common.Manipulator.csvData
import order.OrderFunction.sequentialInsert
import order.OrderValue.SequentialInsert

import scala.io.Source

abstract class Command {
  /* the order of command execution */
  val priority: Int

  /* command execution */
  def exec: Unit
}

/* -f --file */
case class OptionF(value: String) extends Command {

  override val priority: Int = 0

  override def exec = {
    val src = Source.fromFile(value)
    try {
      csvData = src.getLines().map(_.replace(",", ", ") .split(",").toList).toList.map(_.map(_.trim))
    }
    finally {
      src.close()
    }
  }

}

/* -o --order */
case class OptionO(value: String, csvFilePath: String) extends Command {

  override val priority: Int = 1

  override def exec = {
    value match {
      case _ if value == SequentialInsert.value =>
        csvData = sequentialInsert(csvData)
        val writer = CSVWriter.open(csvFilePath)
        writer.writeAll(csvData)
        writer.close()
      case _ => throw new Exception("The order does not exist.")
    }
  }

}