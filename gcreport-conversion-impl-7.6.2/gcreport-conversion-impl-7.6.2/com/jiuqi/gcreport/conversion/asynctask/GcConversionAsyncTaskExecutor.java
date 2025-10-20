/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.conversion.common.GcConversionContextEnv
 *  com.jiuqi.gcreport.i18n.util.GcI18nUtil
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.asynctask.NpAsyncTaskExecutor
 */
package com.jiuqi.gcreport.conversion.asynctask;

import com.jiuqi.gcreport.conversion.common.GcConversionContextEnv;
import com.jiuqi.gcreport.conversion.service.ConversionService;
import com.jiuqi.gcreport.conversion.service.impl.ConversionServiceImpl;
import com.jiuqi.gcreport.i18n.util.GcI18nUtil;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.asynctask.NpAsyncTaskExecutor;
import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class GcConversionAsyncTaskExecutor
implements NpAsyncTaskExecutor {
    @Lazy
    @Autowired
    private ConversionService conversionService;
    private Logger logger = LoggerFactory.getLogger(ConversionServiceImpl.class);

    public void execute(Object args, AsyncTaskMonitor asyncTaskMonitor) {
        try {
            if (args instanceof GcConversionContextEnv) {
                GcConversionContextEnv conversionContextEnv = (GcConversionContextEnv)args;
                conversionContextEnv.setAsyncTaskMonitor(asyncTaskMonitor);
                this.conversionService.conversion(conversionContextEnv);
                if (asyncTaskMonitor.isCancel()) {
                    String retStr = GcI18nUtil.getMessage((String)"gc.coversion.asynctask.cancel.title");
                    asyncTaskMonitor.canceled(retStr, (Object)retStr);
                } else {
                    ArrayList<String> conversionMessages = new ArrayList<String>(conversionContextEnv.getConversionMessages());
                    boolean isTipDetailInfo = false;
                    if (conversionMessages.size() > 50) {
                        conversionMessages = new ArrayList(conversionMessages.subList(0, 50));
                        isTipDetailInfo = true;
                    }
                    if (isTipDetailInfo) {
                        conversionMessages.add(GcI18nUtil.getMessage((String)"gc.coversion.asynctask.detail.info"));
                    }
                    asyncTaskMonitor.finish(GcI18nUtil.getMessage((String)"gc.coversion.asynctask.success.title"), conversionMessages);
                }
            }
        }
        catch (Exception e) {
            String message = e.getMessage();
            this.logger.error(message, e);
            if (!StringUtils.isEmpty(message) && message.length() > 200) {
                message = message.substring(0, 200) + "...";
            }
            asyncTaskMonitor.error(message, (Throwable)e);
        }
    }

    public String getTaskPoolType() {
        return "GC_ASYNCTASK_CONVERSION";
    }
}

