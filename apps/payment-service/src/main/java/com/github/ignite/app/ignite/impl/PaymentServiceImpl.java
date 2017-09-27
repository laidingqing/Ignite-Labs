package com.github.ignite.app.ignite.impl;

import com.node.services.PaymentService;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.resources.IgniteInstanceResource;
import org.apache.ignite.services.ServiceContext;
import com.node.model.Payment;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by skylai on 2017/9/26.
 */
public class PaymentServiceImpl implements PaymentService {

    @IgniteInstanceResource
    private Ignite ignite;

    private IgniteCache<Long, Payment> paymentCache;

    private AtomicLong atomicLong = new AtomicLong();

    @Override
    public Payment getPayment(Long paymentId) {
        return paymentCache.get(paymentId);
    }

    @Override
    public void addPayment(Payment payment) {
        long key = atomicLong.incrementAndGet();
        paymentCache.put(key, payment);
    }

    @Override
    public void cancel(ServiceContext ctx) {
        System.out.println("Stopping Vehicle Service on node:" + ignite.cluster().localNode());
    }

    @Override
    public void init(ServiceContext ctx) throws Exception {
        System.out.println("Executing Vehicle Service on node:" + ignite.cluster().localNode());
        paymentCache = ignite.cache("paymentCache");
    }

    @Override
    public void execute(ServiceContext ctx) throws Exception {
        System.out.println("Executing Vehicle Service on node:" + ignite.cluster().localNode());
    }
}
