package com.lammon.service;

import com.lammon.annotation.Service;
import com.lammon.api.service.IAddService;

/**
 * Add服务的实现
 *
 * @author lammon
 * @date 2021/3/23
 */
@Service
public class AddServiceImpl implements IAddService {
    @Override
    public int addOne(int a) {
        return a + 1;
    }
}
