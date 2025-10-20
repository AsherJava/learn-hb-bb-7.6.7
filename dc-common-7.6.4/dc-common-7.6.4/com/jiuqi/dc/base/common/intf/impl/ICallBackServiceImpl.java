/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.base.common.intf.impl;

import com.jiuqi.dc.base.common.intf.ICallBack;
import com.jiuqi.dc.base.common.intf.ICallBackService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class ICallBackServiceImpl
implements ICallBackService {
    @Override
    @Async
    public void asyncCallBack(ICallBack action) {
        action.callback();
    }
}

