/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.internal.util.SpringUtil
 */
package com.jiuqi.gcreport.webserviceclient.job;

import com.jiuqi.gcreport.webserviceclient.job.DataIntegrationExtendService;
import com.jiuqi.np.definition.internal.util.SpringUtil;
import java.util.Map;
import org.springframework.context.ApplicationContext;

public class DataIntegrationFactory {
    private static final DataIntegrationFactory dataIntegrationInstance = new DataIntegrationFactory();
    private DataIntegrationExtendService dataIntegrationExtendService;

    public static DataIntegrationFactory getInstance() {
        return dataIntegrationInstance;
    }

    public DataIntegrationExtendService getDataIntegrationJob() {
        Map<String, DataIntegrationExtendService> beansOfType;
        if (this.dataIntegrationExtendService == null && !(beansOfType = ((ApplicationContext)SpringUtil.getBean(ApplicationContext.class)).getBeansOfType(DataIntegrationExtendService.class)).isEmpty()) {
            this.setDataIntegrationJob(beansOfType.values().iterator().next());
        }
        return this.dataIntegrationExtendService;
    }

    public void setDataIntegrationJob(DataIntegrationExtendService dataIntegrationExtendService) {
        this.dataIntegrationExtendService = dataIntegrationExtendService;
    }
}

