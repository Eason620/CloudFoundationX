package com.ziyao.cfx.openapi.service.impl;

import com.ziyao.cfx.openapi.service.OpenService;
import org.apache.dubbo.config.annotation.DubboService;

/**
 * @author zhangziyao
 * @since 2023/4/23
 */
@DubboService(group = "open-api", version = "1.0.0")
public class OpenServiceImpl implements OpenService {
    @Override
    public void open() {

    }
}
