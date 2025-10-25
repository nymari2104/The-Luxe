package com.example.theluxe.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import com.example.theluxe.model.User;
import com.example.theluxe.repository.UserRepository;

public class LoginViewModel extends AndroidViewModel {

    private UserRepository userRepository;
    public MutableLiveData<User> loggedInUser = new MutableLiveData<>();

    public LoginViewModel(@NonNull Application application) {
        super(application);
        userRepository = UserRepository.getInstance(application);
    }

    public void login(String email, String password) {
        userRepository.login(email, password, loggedInUser);
    }
}
