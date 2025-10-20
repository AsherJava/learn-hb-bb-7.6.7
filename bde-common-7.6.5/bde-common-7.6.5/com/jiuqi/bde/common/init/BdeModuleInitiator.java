/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.nvwa.sf.models.ModuleInitiator
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 *  javax.servlet.ServletContext
 */
package com.jiuqi.bde.common.init;

import com.jiuqi.bde.common.init.BdeModuleItemInitiatorGather;
import com.jiuqi.bde.common.init.IBdeModuleItemInitiator;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.nvwa.sf.models.ModuleInitiator;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import java.util.List;
import javax.servlet.ServletContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BdeModuleInitiator
implements ModuleInitiator {
    private final Logger LOGGER = LoggerFactory.getLogger(BdeModuleInitiator.class);

    public void init(ServletContext context) throws Exception {
    }

    public void initWhenStarted(ServletContext context) throws Exception {
        this.LOGGER.info("BDE\u6a21\u5757\u6267\u884c\u521d\u59cb\u5316");
        BdeModuleItemInitiatorGather moduleItemInitiatorGather = (BdeModuleItemInitiatorGather)ApplicationContextRegister.getBean(BdeModuleItemInitiatorGather.class);
        if (moduleItemInitiatorGather == null) {
            this.LOGGER.error("BDE\u6a21\u5757\u521d\u59cb\u6ca1\u6709\u83b7\u53d6\u5230\u5b50\u6a21\u5757\u6536\u96c6\u5668\uff0c\u8df3\u8fc7\u6267\u884c");
            return;
        }
        List<IBdeModuleItemInitiator> itemInitiators = ((BdeModuleItemInitiatorGather)ApplicationContextRegister.getBean(BdeModuleItemInitiatorGather.class)).listModuleInitiators();
        for (IBdeModuleItemInitiator itemInitiator : itemInitiators) {
            try {
                this.LOGGER.info("BDE\u6a21\u5757\u6267\u884c\u521d\u59cb\u5316-\u6267\u884c\u5b50\u6a21\u5757 \u3010{}\u3011 \u521d\u59cb", (Object)itemInitiator.getName());
                this.LOGGER.info("BDE\u6a21\u5757\u6267\u884c\u521d\u59cb\u5316-\u6267\u884c\u5b50\u6a21\u5757 \u3010{}\u3011 \u5b8c\u6210", (Object)itemInitiator.getName());
                itemInitiator.initWhenStarted(context);
            }
            catch (Exception e) {
                this.LOGGER.info("BDE\u6a21\u5757\u6267\u884c\u521d\u59cb\u5316-\u6267\u884c\u5b50\u6a21\u5757 \u3010{}\u3011 \u51fa\u73b0\u9519\u8bef", (Object)itemInitiator.getName());
                throw new BusinessRuntimeException(String.format("BDE\u6a21\u5757\u6267\u884c\u521d\u59cb\u5316-\u6267\u884c\u5b50\u6a21\u5757 \u3010%1s\u3011 \u51fa\u73b0\u9519\u8bef", itemInitiator.getName()), (Throwable)e);
            }
        }
        this.LOGGER.info("BDE\u6a21\u5757\u5b8c\u6210\u6a21\u5757\u521d\u59cb\u5316");
    }
}

