package com.mihani.services;

import com.mihani.entities.User;
import com.mihani.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepo userRepo;

    @Override
    public List<User> findAllUsersByAvailable(Boolean available) {
        if (available != null) {
            Specification<User> specification = Specification.where(UserRepo.available(available));
            return userRepo.findAll(specification);
        }
        return userRepo.findAll();
    }

    @Override
    public User blockUser(Long id, boolean available) throws Exception {
        Optional<User> optionalUser = userRepo.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setAvailable(available);
            return userRepo.save(user);
        }
        throw new Exception("User not found");
    }

    @Override
    public void deleteUser(Long id) throws Exception {
        Optional<User> user = userRepo.findById(id);
        if (user.isPresent()) {
            userRepo.delete(user.get());
        }
        throw new Exception("User not found");
    }
}
