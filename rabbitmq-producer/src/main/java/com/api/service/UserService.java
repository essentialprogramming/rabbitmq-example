package com.api.service;

import com.api.entities.User;
import com.api.mapper.UserMapper;
import com.api.model.UserInput;
import com.api.output.UserJSON;
import com.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import com.util.crypto.PasswordHash;

import java.security.GeneralSecurityException;
import java.util.Optional;


@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;


    @Transactional
    public UserJSON save(UserInput input) throws GeneralSecurityException {

        final User user = UserMapper.inputToUser(input);
        final User result = saveUser(user, input);

        return UserMapper.userToJson(result);

    }

    @Transactional
    public boolean checkAvailabilityByEmail(String email) {

        Optional<User> user = userRepository.findByEmail(email);
        return !user.isPresent();
    }

    private User saveUser(User user, UserInput input) {

        String uuid = NanoIdUtils.randomNanoId();
        user.setUserKey(uuid);

        userRepository.save(user);
        if (user.getId() > 0) {
            logger.debug("Start password hashing");
            String password = PasswordHash.encode(input.getPassword());
            logger.debug("Finished password hashing");

            user.setPassword(password);
        }

        logger.info("user saved userId={}", user.getId());
        return user;
    }
}