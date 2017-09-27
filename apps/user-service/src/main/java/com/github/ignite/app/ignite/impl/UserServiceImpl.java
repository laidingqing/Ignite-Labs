package com.github.ignite.app.ignite.impl;

import com.node.model.User;
import com.node.services.UserService;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.resources.IgniteInstanceResource;
import com.node.model.Payment;
import org.apache.ignite.services.ServiceContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by skylai on 2017/9/26.
 */
public class UserServiceImpl implements UserService {

    private static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @IgniteInstanceResource
    private Ignite ignite;

    private IgniteCache<Long, User> userCache;

    private AtomicLong atomicLong = new AtomicLong();

    @Override
    public User getUser(Long userId) {
        return userCache.get(userId);
    }

    @Override
    public void addUser(User user) {
        long key = atomicLong.incrementAndGet();
        userCache.put(key, user);
    }

    @Override
    public void cancel(ServiceContext ctx) {
        System.out.println("Stopping User Service on node:" + ignite.cluster().localNode());
    }

    @Override
    public void init(ServiceContext ctx) throws Exception {
        System.out.println("Executing User Service on node:" + ignite.cluster().localNode());
        userCache = ignite.cache("userCache");
    }

    @Override
    public void execute(ServiceContext ctx) throws Exception {
        System.out.println("Executing User Service on node:" + ignite.cluster().localNode());
    }
}
