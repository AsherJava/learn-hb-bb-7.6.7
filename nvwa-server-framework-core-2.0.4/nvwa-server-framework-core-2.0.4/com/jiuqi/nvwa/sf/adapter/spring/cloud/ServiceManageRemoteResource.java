/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.remote.service.IRemoteServiceResource
 */
package com.jiuqi.nvwa.sf.adapter.spring.cloud;

import com.jiuqi.nvwa.remote.service.IRemoteServiceResource;
import com.jiuqi.nvwa.sf.adapter.spring.cloud.IServiceManager;
import org.springframework.stereotype.Component;

@Component
public class ServiceManageRemoteResource
implements IRemoteServiceResource {
    public String getClassName() {
        return IServiceManager.class.getName();
    }

    public String getTitle() {
        return "\u670d\u52a1\u7ba1\u7406\u63a5\u53e3";
    }

    public boolean anon() {
        return true;
    }

    public boolean duplicated() {
        return true;
    }
}

