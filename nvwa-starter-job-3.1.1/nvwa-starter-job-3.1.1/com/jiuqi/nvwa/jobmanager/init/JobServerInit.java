/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.nvwa.jobmanager.service.RegJobFactory
 */
package com.jiuqi.nvwa.jobmanager.init;

import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.nvwa.jobmanager.service.RegJobFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JobServerInit {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public void init() throws Exception {
        try {
            RegJobFactory regJobFactory = SpringBeanUtils.getApplicationContext().getBean(RegJobFactory.class);
            regJobFactory.init();
        }
        catch (Exception e) {
            this.logger.error(e.getMessage(), e);
        }
    }

    public void initWhenStarted() throws Exception {
    }

    public void destroy() throws Exception {
        System.out.println("DESTROY");
    }
}

