package models

case class Image
(id: Option[Long],
 productId: Option[Long],
 url: String
) {
  override def toString: String = {
    "Image { id: " + id.getOrElse(0) + ", productId: " + productId.getOrElse(0) + ", url: " + url + "}"
  }
}
