package com.lammon.serive;

import com.lammon.rpcapi.IAddService;

/**
 * Add服务的实现
 *
 * @author lammon
 * @date 2021/3/23
 */
public class AddServiceImpl implements IAddService {
    @Override
    public int add(int a) {
        return a + 1;
    }
}
