package com.node.filters;

import org.apache.ignite.cluster.ClusterNode;
import org.apache.ignite.lang.IgnitePredicate;

public class DataNodeFilter implements IgnitePredicate<ClusterNode>{

    public boolean apply(ClusterNode node) {
        Boolean dataNode = node.attribute("data.node");

        return dataNode != null && dataNode;
    }
}
