package utils

import play.api.libs.json._

object JsonUtils {
  def unwrapValue(value: JsLookupResult): String = {
    value match {
      case JsDefined(v) => 
        v match {
          case JsString(str) => str
          case _             => v.toString()
        }
      case _ => ""
    }
  }
}
