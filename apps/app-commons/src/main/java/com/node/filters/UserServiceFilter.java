package com.node.filters;

import org.apache.ignite.cluster.ClusterNode;
import org.apache.ignite.lang.IgnitePredicate;

/**
 * Created by skylai on 2017/9/27.
 */
public class UserServiceFilter implements IgnitePredicate<ClusterNode> {

    public boolean apply(ClusterNode node) {
        Boolean dataNode = node.attribute("user.service.node");

        return dataNode != null && dataNode;
    }
}
