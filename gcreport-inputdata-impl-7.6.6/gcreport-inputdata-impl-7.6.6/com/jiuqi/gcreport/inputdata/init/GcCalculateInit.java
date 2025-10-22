/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.nvwa.sf.models.ModuleInitiator
 *  javax.servlet.ServletContext
 */
package com.jiuqi.gcreport.inputdata.init;

import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.gcreport.inputdata.init.upgrade.UpgradeInputDataConvertGroupId;
import com.jiuqi.nvwa.sf.models.ModuleInitiator;
import javax.servlet.ServletContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GcCalculateInit
implements ModuleInitiator {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public void init(ServletContext context) throws Exception {
    }

    public void initWhenStarted(ServletContext context) {
        this.inputDataHandler();
    }

    private void inputDataHandler() {
        ((UpgradeInputDataConvertGroupId)SpringContextUtils.getBean(UpgradeInputDataConvertGroupId.class)).execute();
    }
}

