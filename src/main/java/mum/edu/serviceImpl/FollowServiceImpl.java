package mum.edu.serviceImpl;

import mum.edu.model.Follow;
import mum.edu.repository.FollowRepository;
import mum.edu.service.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class FollowServiceImpl implements FollowService {

    @Autowired
    private FollowRepository followRepository;
    @Override
    public Follow saveFollow(Follow follow) {
        follow.setCreationDate(new Date());
        return followRepository.save(follow);
    }

    @Override
    public List<Follow> findAll() {
        return (List<Follow>) followRepository.findAll();
    }
}
