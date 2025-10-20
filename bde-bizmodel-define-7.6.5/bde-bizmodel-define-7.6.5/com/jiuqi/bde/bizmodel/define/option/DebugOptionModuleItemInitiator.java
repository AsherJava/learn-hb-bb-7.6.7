/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.init.IBdeModuleItemInitiator
 *  com.jiuqi.bde.log.utils.BdeLogUtil
 *  javax.servlet.ServletContext
 */
package com.jiuqi.bde.bizmodel.define.option;

import com.jiuqi.bde.common.init.IBdeModuleItemInitiator;
import com.jiuqi.bde.log.utils.BdeLogUtil;
import javax.servlet.ServletContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DebugOptionModuleItemInitiator
implements IBdeModuleItemInitiator {
    @Value(value="${jiuqi.np.user.system[0].name:}")
    private String username;

    public String getName() {
        return "\u8bb0\u5f55\u53d6\u6570\u8fc7\u7a0b\u8be6\u7ec6\u65e5\u5fd7\u9009\u9879\u91cd\u7f6e";
    }

    public void init(ServletContext context) throws Exception {
    }

    public void initWhenStarted(ServletContext context) throws Exception {
        BdeLogUtil.resetDebugMode((String)this.username);
    }

    public int getOrder() {
        return 0;
    }
}

