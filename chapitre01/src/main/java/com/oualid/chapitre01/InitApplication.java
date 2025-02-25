package com.oualid.chapitre01;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Component
public class InitApplication {

    private final Logger LOGGER = LoggerFactory.getLogger(InitApplication.class);

    @PostConstruct
    public void initDb(){

        LOGGER.info("Start");

        LOGGER.debug("Doing Nothing...");

        LOGGER.info("End");
    }
}
