package mum.edu.repository;

import mum.edu.model.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {
@Query("select  p from Product p where p.category.name=:name")
public List<Product> findProductByCategory(@Param("name") String name);
    @Query("select  p from Product p join p.seller where p.seller.id=:user_id")
    public List<Product> findProductBySeller(@Param("user_id") Long user_id);
}
