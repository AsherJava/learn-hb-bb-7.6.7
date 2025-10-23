/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.context.ContextExtension
 *  com.jiuqi.np.core.context.ContextIdentity
 *  com.jiuqi.np.core.context.ContextUser
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextBuilder
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.nvwa.login.domain.NvwaContextIdentity
 *  com.jiuqi.nvwa.login.domain.NvwaContextUser
 */
package com.jiuqi.nr.param.transfer;

import com.jiuqi.np.core.context.ContextExtension;
import com.jiuqi.np.core.context.ContextIdentity;
import com.jiuqi.np.core.context.ContextUser;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextBuilder;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.nvwa.login.domain.NvwaContextIdentity;
import com.jiuqi.nvwa.login.domain.NvwaContextUser;
import java.io.Serializable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages={"com.jiuqi.nr.param.transfer"})
public class ParamTransferConfig {
    private static final Logger logger = LoggerFactory.getLogger(ParamTransferConfig.class);

    public static void buildNpContext(String operator) {
        if (operator == null) {
            logger.debug("\u4e0d\u5b58\u5728\u4e0a\u4e0b\u6587\u65f6 \u8bbe\u7f6e\u4e0a\u4e0b\u6587\uff0c\u4f20\u5165\u5f53\u524d\u5bfc\u5165\u7528\u6237\u4e3a\u7a7a\uff0c\u4e0d\u8bbe\u7f6e\u8df3\u8fc7");
            ContextExtension extension = NpContextHolder.getContext().getExtension("transfer-cache");
            extension.put("enable", (Serializable)Boolean.valueOf(false));
            return;
        }
        if (NpContextHolder.getContext() == null || NpContextHolder.getContext().getIdentityId() == null) {
            logger.info("\u4e0d\u5b58\u5728\u4e0a\u4e0b\u6587,\u8bbe\u7f6e\u4e0a\u4e0b\u6587,\u4e0a\u4e0b\u6587\u7528\u6237 {}", (Object)operator);
            NpContextBuilder builder = new NpContextBuilder();
            NvwaContextIdentity identity = new NvwaContextIdentity();
            identity.setId(operator);
            builder.identity((ContextIdentity)identity);
            NvwaContextUser user = new NvwaContextUser();
            user.setId(operator);
            NpContext context = builder.user((ContextUser)user).build();
            NpContextHolder.setContext((NpContext)context);
        }
        ContextExtension extension = NpContextHolder.getContext().getExtension("transfer-cache");
        extension.put("enable", (Serializable)Boolean.valueOf(false));
    }
}

