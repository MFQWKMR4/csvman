package Helpers

import common.{CliComponent, ManipulatorComponent}
import order.OrderFunctionImplComponent

object ComponentRegistry extends OrderFunctionImplComponent with ManipulatorComponent with CliComponent {
  val orderFunction = new OrderFunctionImpl()
  val manipulator = new Manipulator()
  val cli = new Cli()
}
