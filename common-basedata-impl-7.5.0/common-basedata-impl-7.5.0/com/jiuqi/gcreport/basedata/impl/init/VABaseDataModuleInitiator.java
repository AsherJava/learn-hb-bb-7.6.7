/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.nvwa.sf.models.ModuleInitiator
 *  javax.servlet.ServletContext
 */
package com.jiuqi.gcreport.basedata.impl.init;

import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.gcreport.basedata.impl.init.VABaseDataInitService;
import com.jiuqi.nvwa.sf.models.ModuleInitiator;
import javax.servlet.ServletContext;
import org.springframework.stereotype.Component;

@Component
public class VABaseDataModuleInitiator
implements ModuleInitiator {
    public void init(ServletContext context) throws Exception {
    }

    public void initWhenStarted(ServletContext context) throws Exception {
        VABaseDataInitService vaVABaseDataInit = SpringContextUtils.getApplicationContext().getBean(VABaseDataInitService.class);
        vaVABaseDataInit.init();
    }
}

