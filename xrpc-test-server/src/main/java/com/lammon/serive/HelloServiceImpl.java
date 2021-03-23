package com.lammon.serive;

import com.lammon.rpcapi.HelloObject;
import com.lammon.rpcapi.IHelloService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * hello服务接口的实现
 *
 * @author lammon
 * @date 2021/3/9
 */
public class HelloServiceImpl implements IHelloService {


    private static final Logger logger = LoggerFactory.getLogger(HelloServiceImpl.class);

    @Override
    public String hello(HelloObject object) {
        logger.info("服务接收到消息：{}", object.getMessage());
        return "这是调用服务的返回值，id = " + object.getId();
    }
}
