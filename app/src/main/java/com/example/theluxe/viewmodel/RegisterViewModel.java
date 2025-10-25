package com.example.theluxe.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import com.example.theluxe.repository.UserRepository;

public class RegisterViewModel extends AndroidViewModel {

    private UserRepository userRepository;
    public MutableLiveData<Boolean> registrationStatus = new MutableLiveData<>();

    public RegisterViewModel(@NonNull Application application) {
        super(application);
        userRepository = UserRepository.getInstance(application);
    }

    public void register(String email, String password) {
        userRepository.register(email, password, registrationStatus);
    }
}
