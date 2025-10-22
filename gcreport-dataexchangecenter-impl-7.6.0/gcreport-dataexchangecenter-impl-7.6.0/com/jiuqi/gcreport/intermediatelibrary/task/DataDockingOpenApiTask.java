/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.openapi.OpenApiRegisterDO
 *  com.jiuqi.va.domain.task.OpenApiRegisterTask
 */
package com.jiuqi.gcreport.intermediatelibrary.task;

import com.jiuqi.va.domain.openapi.OpenApiRegisterDO;
import com.jiuqi.va.domain.task.OpenApiRegisterTask;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class DataDockingOpenApiTask
implements OpenApiRegisterTask {
    public List<OpenApiRegisterDO> getApis() {
        ArrayList<OpenApiRegisterDO> openApis = new ArrayList<OpenApiRegisterDO>();
        openApis.add(this.initApi("DataDocking", "\u7b2c\u4e09\u65b9\u6570\u636e\u5bf9\u63a5", "\u5408\u5e76\u62a5\u8868"));
        return openApis;
    }
}

