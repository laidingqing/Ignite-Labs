package com.github.ignite.app.ignite;

import org.apache.ignite.Ignite;
import org.apache.ignite.Ignition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @author Bhuwan Upadhyay
 * @date 2017/05/07
 */

@Configuration
public class IgniteClientCfg {

    @Bean
    public Ignite ignite() {
        return Ignition.start("client-node-config.xml");
    }

}