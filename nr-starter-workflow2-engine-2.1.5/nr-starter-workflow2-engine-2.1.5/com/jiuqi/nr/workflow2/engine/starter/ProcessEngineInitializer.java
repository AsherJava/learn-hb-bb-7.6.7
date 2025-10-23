/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.nr.workflow2.engine.dflt.process.runtime.repository.ProcessInstanceLockManager
 *  com.jiuqi.nvwa.sf.models.ModuleInitiator
 *  javax.servlet.ServletContext
 */
package com.jiuqi.nr.workflow2.engine.starter;

import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.nr.workflow2.engine.dflt.process.runtime.repository.ProcessInstanceLockManager;
import com.jiuqi.nvwa.sf.models.ModuleInitiator;
import javax.servlet.ServletContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProcessEngineInitializer
implements ModuleInitiator {
    public void init(ServletContext context) throws Exception {
    }

    public void initWhenStarted(ServletContext context) throws Exception {
        Logger logger = LoggerFactory.getLogger(ProcessEngineInitializer.class);
        try {
            ProcessInstanceLockManager lockManager = SpringBeanUtils.getApplicationContext().getBean(ProcessInstanceLockManager.class);
            if (lockManager != null) {
                lockManager.startup();
                logger.info("\u6d41\u7a0b\u5b9e\u4f8b\u9501\u521d\u59cb\u5316\u6210\u529f\u3002");
            } else {
                logger.error("\u6d41\u7a0b\u5b9e\u4f8b\u9501\u521d\u59cb\u5316\u5931\u8d25\uff1a\u672a\u80fd\u52a0\u8f7d\u6d41\u7a0b\u5b9e\u4f8b\u9501\u7ba1\u7406\u5668\u3002");
            }
        }
        catch (Exception e) {
            logger.error("\u6d41\u7a0b\u5b9e\u4f8b\u9501\u521d\u59cb\u5316\u5931\u8d25\u3002", e);
        }
    }
}

