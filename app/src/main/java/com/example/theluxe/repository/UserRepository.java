package com.example.theluxe.repository;

import android.app.Application;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.theluxe.model.User;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UserRepository {

    private final UserDao userDao;
    private final ExecutorService executorService;
    private static volatile UserRepository instance;


    private UserRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        userDao = db.userDao();
        executorService = Executors.newSingleThreadExecutor();
        addDefaultUser();
    }

    public static UserRepository getInstance(Application application) {
        if (instance == null) {
            instance = new UserRepository(application);
        }
        return instance;
    }


    private void addDefaultUser() {
        executorService.execute(() -> {
            User testUser = userDao.findByEmail("test@example.com");
            if (testUser == null) {
                userDao.insert(new User("test@example.com", "password"));
            }
        });
    }

    public void login(String email, String password, MutableLiveData<User> loginResult) {
        executorService.execute(() -> {
            User user = userDao.findByEmail(email);
            if (user != null && user.getPassword().equals(password)) {
                loginResult.postValue(user);
            } else {
                loginResult.postValue(null);
            }
        });
    }

    public void register(String email, String password, MutableLiveData<Boolean> registrationStatus) {
        executorService.execute(() -> {
            User existingUser = userDao.findByEmail(email);
            if (existingUser == null) {
                userDao.insert(new User(email, password));
                registrationStatus.postValue(true);
            } else {
                registrationStatus.postValue(false);
            }
        });
    }

    public void updateUser(User user, MutableLiveData<Boolean> updateStatus) {
        executorService.execute(() -> {
            userDao.update(user);
            updateStatus.postValue(true); // Assume success for simplicity
        });
    }

    public LiveData<User> getUser(String email) {
        return userDao.findByEmailLiveData(email);
    }
}
