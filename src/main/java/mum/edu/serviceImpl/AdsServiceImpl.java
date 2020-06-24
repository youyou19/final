package mum.edu.serviceImpl;

import mum.edu.model.Ads;
import mum.edu.repository.AdsRepository;
import mum.edu.service.AdsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class AdsServiceImpl implements AdsService {

    @Autowired
  private AdsRepository adsRepository;

    @Override
    //@PreAuthorize("hasRole(@roles.ADMIN)")
    public Ads saveAds(Ads a) {
        a.setCreationDate(new Date());
        return adsRepository.save(a);
    }

    @Override
    public Ads edit(Ads a) {
        a.setUpdateDate(new Date());
        return adsRepository.save(a);
    }

    @Override
    public Ads findAds(Long id) {
        return adsRepository.findById(id).get();
    }
@Override
public void deleteAds(Long id){
     adsRepository.deleteById(id);
}

    @Override
    public List<Ads> findAll() {
        return (List<Ads>)adsRepository.findAll();
    }
}
