package TUI

//código da ficha 6
case class Container(name:String, data : Map[String, String]) {

  object Container {
    def addEntryG(key: => String, value: => String)(container: Container): Container = {
      new Container(container.name, container.data + (key -> value))
    }
  }
}
