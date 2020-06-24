package mum.edu.service;



import mum.edu.model.Ads;

import java.util.List;

public interface AdsService {

    public Ads saveAds(Ads a);
    public Ads edit(Ads a);
    public Ads findAds(Long id);
    public void deleteAds(Long id);
    public List<Ads> findAll();
}
