/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterFactory
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.tool.OrgCenterParam
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.gcreport.org.impl.util;

import com.jiuqi.gcreport.org.api.tool.GcOrgCenterFactory;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.tool.OrgCenterParam;
import com.jiuqi.gcreport.org.impl.util.GcOrgCenterServiceImpl;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.RestController;

@Lazy(value=false)
@RestController
class GcOrgCenterFactoryImpl
implements GcOrgCenterFactory,
InitializingBean {
    GcOrgCenterFactoryImpl() {
    }

    public GcOrgCenterService getInstance(OrgCenterParam param) {
        return GcOrgCenterServiceImpl.getInstance(param.getOrgType(), param.getYp(), param.getAuthType());
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        GcOrgPublicTool.setFactory((GcOrgCenterFactory)this);
    }
}

