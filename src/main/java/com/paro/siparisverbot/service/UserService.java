package com.paro.siparisverbot.service;

import com.paro.siparisverbot.model.User;
import com.paro.siparisverbot.repository.UserRepository;
import com.paro.siparisverbot.utils.BotState;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void setUsersCurrentBotState(int userId, BotState botState) {
        Optional<User> userOptional = userRepository.findById(userId);
        userOptional.ifPresentOrElse(user -> {
            log.info("User with id {} exists, updating state to: {}", userId, botState.name());
            user.setBotState(botState.name());
            userRepository.save(user);
        }, ()-> {
            User newUser = new User();
            newUser.setId(userId);
            newUser.setBotState(botState.name());
            log.info("User with id {} does not exist, saving user with state: {}", userId, botState.name());

            userRepository.save(newUser);
        });
    }

    public BotState getUsersCurrentBotState(int userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        BotState botState = userOptional.map(user -> BotState.valueOf(user.getBotState())).orElse(BotState.SELECT_MARKET);
        log.info("Bot state returned from the usercache for the user {}: {}", userId, botState.name());
        return botState;
    }


    public Optional<User> getUser(int userId) {
        Optional<User> userOptional = userRepository.findById(userId);
//        User user = userOptional.orElseThrow(() -> new NoSuchElementException());
        log.info("User profile data returned for user {}", userId);
        return userOptional;

    }

    public void saveUser(User user) {
        userRepository.save(user);
        log.info("User profile data saved for user {}", user.getId());
    }
}
