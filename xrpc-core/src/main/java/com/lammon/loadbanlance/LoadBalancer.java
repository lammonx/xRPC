package com.lammon.loadbanlance;

import com.alibaba.nacos.api.naming.pojo.Instance;

import java.util.List;

/**
 * 负载均衡接口
 *
 * @author Lammon
 * @date 2021/4/14
 */
public interface LoadBalancer {

    /**
     * 从一系列实例中选择一个
     */
    Instance select(List<Instance> instances);
    
}