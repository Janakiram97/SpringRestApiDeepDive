package com.example.springrestapideepdive.springdata;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class UserCommandLine implements CommandLineRunner {
    private UserDetailsRepository userDetailsRepository;

    public UserCommandLine(UserDetailsRepository userDetailsRepository) {
        this.userDetailsRepository = userDetailsRepository;
    }

    Logger logger= LoggerFactory.getLogger(getClass());
    public void run(String... args) throws Exception {
        logger.info(Arrays.toString(args));
        userDetailsRepository.save(new UserDetailsEntity("Deepak","Admin"));
        userDetailsRepository.save(new UserDetailsEntity("Ranga","Admin"));
        userDetailsRepository.save(new UserDetailsEntity("Ramesh","manager"));

        List<UserDetailsEntity> users=userDetailsRepository.findAll();
        users.forEach(user ->logger.info(user.toString()));

     List<UserDetailsEntity> userRole=  userDetailsRepository. findByRole("Admin");
     for(UserDetailsEntity role:userRole){
         logger.info(role.toString());
     }
    }
}
