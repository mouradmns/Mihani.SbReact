package com.mihani.services;

import com.mihani.entities.User;

import java.util.List;

public interface IUserService {

    public List<User> findAllUsersByAvailable(Boolean available);
    public User blockUser(Long id, boolean available) throws Exception;
    public void deleteUser(Long id) throws Exception;

}
