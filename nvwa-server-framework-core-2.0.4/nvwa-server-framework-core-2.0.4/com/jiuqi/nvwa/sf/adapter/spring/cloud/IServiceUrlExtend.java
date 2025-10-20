/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nvwa.sf.adapter.spring.cloud;

import com.jiuqi.nvwa.sf.ServiceUrl;
import com.jiuqi.nvwa.sf.adapter.spring.Response;

public interface IServiceUrlExtend {
    public ServiceUrl getServiceUrl(String var1);

    public Response save(String var1, ServiceUrl var2);
}

