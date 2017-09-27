package com.github.ignite.app.ignite.impl;

import com.node.services.PaymentService;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteCompute;
import org.apache.ignite.cluster.ClusterNode;
import org.apache.ignite.compute.*;
import org.apache.ignite.resources.IgniteInstanceResource;
import org.apache.ignite.services.ServiceContext;
import com.node.model.Payment;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by skylai on 2017/9/26.
 */
public class PaymentServiceImpl implements PaymentService {

    private static Logger logger = LoggerFactory.getLogger(PaymentServiceImpl.class);

    @IgniteInstanceResource
    private Ignite ignite;

    private IgniteCache<Long, Payment> paymentCache;

    private AtomicLong atomicLong = new AtomicLong();

    @Override
    public Payment getPayment(Long paymentId) {
        IgniteCompute compute = ignite.compute();
        int cnt = compute.execute(CharacterCountTask.class, "Hello Grid Enabled World!");
        logger.info(">>> Total number of characters in the phrase is '" + cnt + "'.");
        compute.broadcast(() -> logger.info("Ignite Computer at: "+ ignite.cluster().localNode()));
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


    private static class CharacterCountTask extends ComputeTaskAdapter<String, Integer> {
        @Override public Map<? extends ComputeJob, ClusterNode> map(List<ClusterNode> nodes, String arg) {
            Map<ComputeJob, ClusterNode> map = new HashMap<>();
            Iterator<ClusterNode> it = nodes.iterator();
            for (final String word : arg.split(" ")) {
                if (!it.hasNext())
                    it = nodes.iterator();

                ClusterNode node = it.next();

                map.put(new ComputeJobAdapter() {
                    @Nullable
                    @Override public Object execute() {
                        logger.info(">>> Printing '" + word + "' on this node from ignite job.");
                        return word.length();
                    }
                }, node);
            }

            return map;
        }
        @Override
        public Integer reduce(List<ComputeJobResult> results) {
            int sum = 0;
            for (ComputeJobResult res : results)
                sum += res.<Integer>getData();
            return sum;
        }
    }
}
