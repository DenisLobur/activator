package models

case class Review
(var id: Option[Long],
 var productId: Option[Long],
 var author: String,
 var comment: String) {

  override def toString: String = {
    "Review { id: " + id.getOrElse(0) +
      ", productId: " +
      productId.getOrElse(0) +
      ", author: " + author +
      ", comment: " +
      comment + "}"
  }
}
