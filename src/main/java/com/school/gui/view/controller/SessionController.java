package com.school.gui.view.controller;

import java.util.List;
import java.util.UUID;

import com.school.data.model.UserModel;
import com.school.data.repository.UserRepository;
import com.school.gui.view.stateholder.SessionStateHolder;
import com.school.utils.LoginUtils;

public class SessionController
{
    private SessionStateHolder sessionStateHolder = SessionStateHolder.get();
    private UserRepository userRepository = UserRepository.get();

    private final LoginUtils loginUtils = new LoginUtils();

    public UserModel getLoggedInUser()
    {
        return sessionStateHolder.getUser();
    }

    public boolean isLoggedIn() 
    {
        return sessionStateHolder.getUser() != null;
    }

    public boolean tryLogin(UserModel loginModel)
    {
        UserModel repoModel = userRepository.getByUsername(loginModel.getUsername(), true);

        if (repoModel == null)
        {
            return false;
        }

        String pass = loginUtils.hashPassword(loginModel.getPassword(), repoModel.getSalt());

        if (!repoModel.getPassword().equals(pass))
        {
            return false;
        }

        repoModel.setPassword(null);
        sessionStateHolder.setUser(repoModel);

        return true;
    }

    public void register(UserModel registModel)
    {
        if (registModel.getSalt() == null)
        {
            registModel.setSalt(UUID.randomUUID().toString());
        }
        registModel.setPassword(loginUtils.hashPassword(registModel.getPassword(), registModel.getSalt()));

        userRepository.save(registModel);
    }

    public boolean usernameExists(String name)
    {
        return userRepository.getByUsername(name) != null;
    }

    public List<UserModel> getUsers()
    {
        return userRepository.getUsers();
    }

    public void deleteUserById(Long id)
    {
        userRepository.deleteById(id);
    }

    public UserModel getUserByUsername(String username)
    {
        return userRepository.getByUsername(username);
    }

    public void save(UserModel model)
    {
        if (model.getPassword() == null)
        {
            UserModel fullModel = userRepository.getByUsername(model.getUsername(), true);
            fullModel.setRole(model.getRole());
            userRepository.save(fullModel);
        }
        else
        {
            userRepository.save(model);
        }
    }
}
