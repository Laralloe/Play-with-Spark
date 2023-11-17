package controllers

import javax.inject.Inject
import play.api.mvc._
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits._
import play.api.libs.json.Json._
import models.Model._


class HomeController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  def index = Action { implicit request =>
    val headers: Seq[String] = getHeaders(data)
    Ok(views.html.index(dataFrameToJson(data), headers))
  }

  // A simple example to call Apache Spark
  def test = Action { implicit request =>

    Ok(views.html.test_args(s"A call to Spark, with result"))
  }

  // A non-blocking call to Apache Spark 
  // def testAsync = Action.async{
  // 	Ok(views.html.test_args("TEST"))
  // }

}
