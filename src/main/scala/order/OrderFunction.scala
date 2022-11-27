package order

trait OrderFunctionComponent {
  val orderFunction: OrderFunction

  trait OrderFunction {
    def sequentialInsert(csvList: List[List[String]]): List[List[String]]

    def pass(csvList: List[List[String]]): List[List[String]]
  }
}

trait OrderFunctionImplComponent extends OrderFunctionComponent {

  class OrderFunctionImpl extends OrderFunction {
    override def sequentialInsert(csvList: List[List[String]]): List[List[String]] = {
      val (header, rowFields) = (csvList.head, csvList.tail)
      val displayOrderIndex = header.indexOf("displayOrder")

      val displayOrderList = rowFields.flatMap { row =>
        row.zipWithIndex.filter { case (_, countIndex) => countIndex == displayOrderIndex }.map(_._1)
      }

      val nonEmptyList = displayOrderList.take(2)
      require(nonEmptyList.forall(_.nonEmpty), "The first two element of displayOrderList require a value.")

      def updateDisplayOrderList(ret: List[String], rest: List[String]): List[String] = {
        rest match {
          case Nil => ret
          case h +: li if h.isEmpty =>
            val diff = ret.last.toInt - ret(ret.length - 2).toInt
            val updatedValue = ret.last.toInt + diff
            updateDisplayOrderList(ret :+ updatedValue.toString, li)
          case h +: li => updateDisplayOrderList(ret :+ h, li)
        }
      }

      val updatedDisplayOrderList = updateDisplayOrderList(List.empty, displayOrderList)

      val updatedRowFields = rowFields.zipWithIndex.map { case (row, rowIndex) =>
        row.zipWithIndex.map { case (column, columnIndex) =>
          if (columnIndex == displayOrderIndex) {
            updatedDisplayOrderList(rowIndex)
          }
          else column
        }
      }

      header :: updatedRowFields
    }

     override def pass(csvList: List[List[String]]): List[List[String]] = {
      csvList
    }
  }

}

