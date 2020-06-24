package mum.edu.service;

import mum.edu.model.Follow;

import java.util.List;

public interface FollowService {
    Follow saveFollow(Follow follow);
    List<Follow> findAll();
}
