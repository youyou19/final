package mum.edu.repository;

import mum.edu.model.Category;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
//import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface CategoryRepository extends CrudRepository<Category, Long>{

}
