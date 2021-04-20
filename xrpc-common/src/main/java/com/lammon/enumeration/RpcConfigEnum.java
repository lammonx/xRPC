package com.lammon.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Lammon
 */

@AllArgsConstructor
@Getter
public enum RpcConfigEnum {

    /**
     *
     */
    RPC_CONFIG_PATH("xrpc.properties"),
    NACOS_ADDRESS("xrpc.nacos.address");

    private final String propertyValue;

}