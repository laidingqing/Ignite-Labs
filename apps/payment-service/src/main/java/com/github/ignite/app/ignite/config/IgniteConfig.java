package com.github.ignite.app.ignite.config;

import org.apache.ignite.Ignite;
import org.apache.ignite.Ignition;
import org.springframework.stereotype.Component;

/**
 * @author Bhuwan Upadhyay
 * @date 2017/05/07
 */
@Component
public class IgniteConfig {

    public IgniteConfig() {
        Ignite start = Ignition.start("bank-service-node-config.xml");
    }

}
