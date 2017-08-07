package services

class ProductService extends IProductService{
  override def insert(product: models.Product): Long = ???

  override def update(id: Long, product: models.Product): Boolean = ???

  override def remove(id: Long): Boolean = ???

  override def findById(id: Long): Option[models.Product] = ???

  override def findAll(): Option[List[models.Product]] = ???

  override def findAllProducts(): Seq[(String, String)] = ???
}
