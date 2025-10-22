/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.inputdata.dto.GcBatchCopyActionParamDTO
 *  com.jiuqi.gcreport.inputdata.dto.GcBatchCopyEnvDTO
 *  com.jiuqi.gcreport.nr.impl.constant.GcAsyncTaskPoolType
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.asynctask.NpAsyncTaskExecutor
 */
package com.jiuqi.gcreport.inputdata.asynctask;

import com.jiuqi.gcreport.inputdata.dto.GcBatchCopyActionParamDTO;
import com.jiuqi.gcreport.inputdata.dto.GcBatchCopyEnvDTO;
import com.jiuqi.gcreport.inputdata.service.GcBatchCopyService;
import com.jiuqi.gcreport.nr.impl.constant.GcAsyncTaskPoolType;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.asynctask.NpAsyncTaskExecutor;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Component
public class GcBatchCopyAsyncTaskExecutor
implements NpAsyncTaskExecutor {
    @Lazy
    @Autowired
    private GcBatchCopyService batchCopyService;
    private Logger logger = LoggerFactory.getLogger(GcBatchCopyAsyncTaskExecutor.class);

    public void execute(Object args, AsyncTaskMonitor asyncTaskMonitor) {
        if (!(args instanceof GcBatchCopyActionParamDTO)) {
            asyncTaskMonitor.error("\u53c2\u6570\u4e0d\u5408\u6cd5\uff0c\u4e0d\u7b26\u5408GcBatchCopyActionParam\u683c\u5f0f\u3002", null);
        }
        GcBatchCopyEnvDTO env = new GcBatchCopyEnvDTO((GcBatchCopyActionParamDTO)args);
        try {
            env.setAsyncTaskMonitor(asyncTaskMonitor);
            this.batchCopyService.batchCopy(env);
            if (asyncTaskMonitor.isCancel()) {
                String retStr = "\u4efb\u52a1\u53d6\u6d88";
                asyncTaskMonitor.canceled(retStr, (Object)retStr);
            } else {
                List messages = new ArrayList<String>(env.getMessages());
                boolean isTipDetailInfo = false;
                if (messages.size() > 5) {
                    messages = messages.subList(0, 5);
                    isTipDetailInfo = true;
                }
                if (isTipDetailInfo) {
                    messages.add("\u5177\u4f53\u8be6\u7ec6\u4fe1\u606f\u8bf7\u5230\u6279\u91cf\u590d\u5236\u7ed3\u679c\u91cc\u67e5\u770b\u3002");
                }
                String msg = "";
                if (!CollectionUtils.isEmpty(messages)) {
                    msg = messages.stream().reduce("", (s1, s2) -> s1 + "\n" + s2);
                }
                asyncTaskMonitor.finish("\u6279\u91cf\u590d\u5236\u6210\u529f\u3002", (Object)msg);
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
        return GcAsyncTaskPoolType.ASYNCTASK_GC_BATCH_COPY.getName();
    }
}

