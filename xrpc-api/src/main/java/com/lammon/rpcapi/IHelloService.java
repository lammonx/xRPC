package com.lammon.rpcapi;

/**
 * 测试用hello服务接口
 *
 * @author lammon
 * @date 2021/3/9
 */
public interface IHelloService {

    /**
     * hell 服务接口
     * @param object 传参的实体类
     * @return id
     */
    String hello(HelloObject object);

}
