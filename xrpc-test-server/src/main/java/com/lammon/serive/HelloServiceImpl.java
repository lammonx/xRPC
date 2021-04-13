package com.lammon.serive;

import com.lammon.rpcapi.HelloObject;
import com.lammon.rpcapi.IHelloService;
import lombok.extern.slf4j.Slf4j;

/**
 * hello服务接口的实现
 *
 * @author lammon
 * @date 2021/3/9
 */
@Slf4j
public class HelloServiceImpl implements IHelloService {


    @Override
    public String hello(HelloObject object) {
        log.info("服务端接收到消息：{}", object.getMessage());
        return "这是调用服务的返回值，id = " + object.getId();
    }
}
