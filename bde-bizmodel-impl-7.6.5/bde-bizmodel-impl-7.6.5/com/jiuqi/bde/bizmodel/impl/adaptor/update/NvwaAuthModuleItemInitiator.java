/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.init.IBdeModuleItemInitiator
 *  javax.servlet.ServletContext
 */
package com.jiuqi.bde.bizmodel.impl.adaptor.update;

import com.jiuqi.bde.bizmodel.impl.adaptor.update.BdeNvwaAuthUpdate;
import com.jiuqi.bde.common.init.IBdeModuleItemInitiator;
import javax.servlet.ServletContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NvwaAuthModuleItemInitiator
implements IBdeModuleItemInitiator {
    @Autowired
    private BdeNvwaAuthUpdate nvwaAuthUpdate;

    public String getName() {
        return "\u5973\u5a32\u8ba4\u8bc1\u670d\u52a1\u5347\u7ea7";
    }

    public void init(ServletContext context) throws Exception {
    }

    public void initWhenStarted(ServletContext context) throws Exception {
        this.nvwaAuthUpdate.doUpdate("jiuqi.gcreport.mdd.datasource");
    }

    public int getOrder() {
        return 0;
    }
}

