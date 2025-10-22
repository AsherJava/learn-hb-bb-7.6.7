/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.util.NpRollbackException
 *  com.jiuqi.nr.common.cache.remote.CacheObjectResourceRemote
 *  com.jiuqi.nr.dataentry.monitor.SimpleAsyncProgressMonitor
 *  com.jiuqi.nr.dataentry.paramInfo.BatchReturnInfo
 *  com.jiuqi.nr.jtable.util.JsonUtil
 *  javax.annotation.Resource
 *  org.springframework.transaction.annotation.Propagation
 *  org.springframework.transaction.annotation.Transactional
 */
package nr.single.client.internal.service;

import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.util.NpRollbackException;
import com.jiuqi.nr.common.cache.remote.CacheObjectResourceRemote;
import com.jiuqi.nr.dataentry.monitor.SimpleAsyncProgressMonitor;
import com.jiuqi.nr.dataentry.paramInfo.BatchReturnInfo;
import com.jiuqi.nr.jtable.util.JsonUtil;
import java.util.Map;
import javax.annotation.Resource;
import nr.single.client.bean.SingleExportData;
import nr.single.client.bean.SingleExportParam;
import nr.single.client.service.ISingleExportService;
import nr.single.client.service.ISingleFuncExecuteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Transactional(rollbackFor={NpRollbackException.class})
@Service
public class SingleFuncExecuteServiceImpl
implements ISingleFuncExecuteService {
    private static final Logger log = LoggerFactory.getLogger(SingleFuncExecuteServiceImpl.class);
    @Resource
    private Map<String, ISingleExportService> exportServiceMap;
    @Autowired
    private CacheObjectResourceRemote cacheObjectResourceRemote;

    @Override
    @Transactional(propagation=Propagation.SUPPORTS, readOnly=true)
    public SingleExportData export(SingleExportParam param) {
        SingleExportData result;
        block4: {
            BatchReturnInfo batchReturnInfo = new BatchReturnInfo();
            String type = param.getType();
            ISingleExportService exportService = this.exportServiceMap.get(type);
            result = null;
            SimpleAsyncProgressMonitor monitor = null;
            try {
                if (null != param.getSyncTaskID()) {
                    monitor = new SimpleAsyncProgressMonitor(param.getSyncTaskID().toString(), this.cacheObjectResourceRemote);
                    monitor.progressAndMessage(0.01, "\u5f00\u59cb");
                }
                result = exportService.export(param, (AsyncTaskMonitor)monitor);
                if (null != monitor) {
                    String objectToJson = JsonUtil.objectToJson((Object)batchReturnInfo);
                    monitor.finish("\u4efb\u52a1\u5b8c\u6210\u3002", (Object)objectToJson);
                }
            }
            catch (Exception e) {
                log.error(e.getMessage(), e);
                if (null == monitor) break block4;
                batchReturnInfo.setStatus(2);
                String objectToJson = JsonUtil.objectToJson((Object)batchReturnInfo);
                monitor.error("\u5f02\u5e38:" + objectToJson, (Throwable)e);
            }
        }
        return result;
    }
}

