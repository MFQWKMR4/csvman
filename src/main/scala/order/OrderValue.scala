package order

sealed abstract class OrderValue(val value: String)

object OrderValue {
  case object SequentialInsert extends OrderValue("sequential_insert")
  case object Pass extends OrderValue("pass")
}
