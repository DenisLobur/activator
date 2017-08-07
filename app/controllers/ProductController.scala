package controllers

import com.google.inject.{Inject, Singleton}
import models.Product
import play.Logger
import play.api.data.Form
import play.api.data.Forms._
import play.api.i18n.{I18nSupport, MessagesApi}
import play.api.mvc.{Action, Controller}
import services.IProductService

@Singleton
class ProductController @Inject()(val messagesApi: MessagesApi,
                                  val service: IProductService) extends Controller with I18nSupport {
  val productForm: Form[Product] = Form(
    mapping(
      "id" -> optional(longNumber),
      "name" -> nonEmptyText,
      "details" -> text,
      "price" -> bigDecimal
    )(Product.apply)(Product.unapply))

  def index = Action {
    implicit request =>
      val products = service.findAll().getOrElse(Seq())
      Logger.info("index called. Products: " + products)
      Ok(views.html.product_index(products))
  }
}
