/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.option.IBdeSystemOptionItemHandler
 *  com.jiuqi.bde.log.utils.BdeLogUtil
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.nvwa.systemoption.extend.ISystemOptionItemValue
 */
package com.jiuqi.bde.bizmodel.define.option;

import com.jiuqi.bde.bizmodel.define.config.BdeBizModelConfig;
import com.jiuqi.bde.common.option.IBdeSystemOptionItemHandler;
import com.jiuqi.bde.log.utils.BdeLogUtil;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.nvwa.systemoption.extend.ISystemOptionItemValue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class BdeDebugOptionItemHandler
implements IBdeSystemOptionItemHandler {
    private static Logger logger = LoggerFactory.getLogger(BdeDebugOptionItemHandler.class);
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public void doSaveHandle(ISystemOptionItemValue item) {
        if (!"BDE_IS_DEBUG".equals(item.getKey()) || !"1".equals(item.getValue())) {
            return;
        }
        logger.info("\u8bb0\u5f55\u53d6\u6570\u8fc7\u7a0b\u8be6\u7ec6\u65e5\u5fd7\u88ab\u4fee\u6539\u4e3a\u5f00\u542f\u72b6\u6001\uff0c\u89e6\u53d1\u5b9a\u65f6\u4efb\u52a1\u91cd\u7f6e\u4e3a\u5173\u95ed\u72b6\u6001");
        String username = NpContextHolder.getContext().getUserName();
        Runnable resetDebugOptionTask = () -> {
            try {
                logger.info("\u5b9a\u65f6\u4efb\u52a1\u5f00\u59cb\u91cd\u7f6e\u3010\u8bb0\u5f55\u53d6\u6570\u8fc7\u7a0b\u8be6\u7ec6\u65e5\u5fd7\u3011\u4e3a\u5173\u95ed\u72b6\u6001");
                BdeLogUtil.resetDebugMode((String)username);
                logger.info("\u5b9a\u65f6\u4efb\u52a1\u5f00\u59cb\u91cd\u7f6e\u3010\u8bb0\u5f55\u53d6\u6570\u8fc7\u7a0b\u8be6\u7ec6\u65e5\u5fd7\u3011\u4e3a\u5173\u95ed\u5b8c\u6210");
            }
            catch (Exception e) {
                logger.info("\u5b9a\u65f6\u4efb\u52a1\u5f00\u59cb\u91cd\u7f6e\u3010\u8bb0\u5f55\u53d6\u6570\u8fc7\u7a0b\u8be6\u7ec6\u65e5\u5fd7\u3011\u4e3a\u5173\u95ed\u51fa\u73b0\u9519\u8bef:\u8be6\u7ec6\u539f\u56e0\uff1a{}", (Object)e.getMessage(), (Object)e);
            }
        };
        this.scheduler.schedule(resetDebugOptionTask, (long)BdeBizModelConfig.getDebugTimeoutMinutes().intValue(), TimeUnit.MINUTES);
    }
}

