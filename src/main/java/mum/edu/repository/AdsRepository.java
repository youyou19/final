package mum.edu.repository;


import mum.edu.model.Ads;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdsRepository extends CrudRepository<Ads,Long> {
}
