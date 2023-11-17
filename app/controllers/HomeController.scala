package controllers

import javax.inject.Inject
import play.api.mvc._
import play.api.libs.json.Json._
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits._
import models.Model._


class HomeController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  def index(pageNumber: Int) = Action { implicit request =>
    val headers: Seq[String] = getHeaders(data)
    val paginatedDataFrame = paginate(data, pageNumber)
    Ok(views.html.index(dataFrameToJson(paginatedDataFrame), headers, pageNumber))
  }

}
