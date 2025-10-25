package com.example.theluxe.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.theluxe.model.User;
import com.example.theluxe.repository.UserRepository;

public class ProfileViewModel extends AndroidViewModel {

    private final UserRepository userRepository;
    public final MutableLiveData<Boolean> updateStatus = new MutableLiveData<>();
    public LiveData<User> user;

    public ProfileViewModel(@NonNull Application application) {
        super(application);
        userRepository = UserRepository.getInstance(application);
    }

    public void loadUser(String email) {
        user = userRepository.getUser(email);
    }

    public void updateUser(User user) {
        userRepository.updateUser(user, updateStatus);
    }
}
