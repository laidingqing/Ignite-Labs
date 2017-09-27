package com.node.filters;

import org.apache.ignite.cluster.ClusterNode;
import org.apache.ignite.lang.IgnitePredicate;

/**
 * Created by skylai on 2017/9/26.
 */
public class PaymentServiceFilter implements IgnitePredicate<ClusterNode> {

    public boolean apply(ClusterNode node) {
        Boolean dataNode = node.attribute("payment.service.node");

        return dataNode != null && dataNode;
    }
}
