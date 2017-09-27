package com.github.ignite.app;

import org.apache.ignite.Ignite;
import org.apache.ignite.Ignition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @author Bhuwan Upadhyay
 * @date 2017/05/07
 */
@Component
public class IgniteConfig {

    public IgniteConfig() {
        Ignite start = Ignition.start("poc-node-config.xml");
    }

}
