package net.spacedelta.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * SpaceApi for doing sexy things for sexy people
 * <p>
 * The only current functionality is getting stats but WIP!
 */
@SpringBootApplication
public class SpaceApi {

    public static void main(String[] args) {
        SpringApplication.run(SpaceApi.class, args);
    }

}
