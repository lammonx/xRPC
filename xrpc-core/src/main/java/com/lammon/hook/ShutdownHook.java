package com.lammon.hook;

import com.lammon.factory.ThreadPoolFactory;
import com.lammon.utils.NacosUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 说明
 *
 * @author lammon
 * @date 2021/4/13
 */
@Slf4j
public class ShutdownHook {
    /**
     *单例模式创建钩子，保证全局只有这一个钩子
     */
    private static final ShutdownHook SHUTDOWN_HOOK = new ShutdownHook();

    public static ShutdownHook getShutdownHook(){
        return SHUTDOWN_HOOK;
    }

    /**
     * 注销服务的钩子
     */
    public void addClearAllHook() {
        log.info("服务端关闭前将自动注销所有服务");
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            NacosUtil.logoutService();
            ThreadPoolFactory.shutDownAll();
        }));
    }
}
