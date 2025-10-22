/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.JobContext
 *  com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob
 *  com.jiuqi.bi.core.jobs.realtime.RealTimeJob
 *  com.jiuqi.np.asynctask.NpRealTimeTaskExecutor
 *  com.jiuqi.np.asynctask.impl.RealTimeTaskMonitor
 *  com.jiuqi.np.asynctask.util.SimpleParamConverter$SerializationUtils
 *  com.jiuqi.np.blob.util.BeanUtil
 *  com.jiuqi.nr.common.exception.NrCommonException
 *  com.jiuqi.nr.query.dao.IQueryModalDefineDao
 *  com.jiuqi.nr.query.querymodal.QueryModalDefine
 *  com.jiuqi.nr.query.service.impl.QueryServices
 */
package com.jiuqi.nr.finalaccountsaudit.querycheck.asynctask;

import com.jiuqi.bi.core.jobs.JobContext;
import com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob;
import com.jiuqi.bi.core.jobs.realtime.RealTimeJob;
import com.jiuqi.np.asynctask.NpRealTimeTaskExecutor;
import com.jiuqi.np.asynctask.impl.RealTimeTaskMonitor;
import com.jiuqi.np.asynctask.util.SimpleParamConverter;
import com.jiuqi.np.blob.util.BeanUtil;
import com.jiuqi.nr.common.exception.NrCommonException;
import com.jiuqi.nr.finalaccountsaudit.common.AsynctaskPoolType;
import com.jiuqi.nr.finalaccountsaudit.querycheck.bean.QueryParamInfo;
import com.jiuqi.nr.query.dao.IQueryModalDefineDao;
import com.jiuqi.nr.query.querymodal.QueryModalDefine;
import com.jiuqi.nr.query.service.impl.QueryServices;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RealTimeJob(group="ASYNCTASK_QUERYCHECK", groupTitle="\u67e5\u8be2\u5ba1\u6838")
public class QueryCheckAsyncTaskExecutor
extends NpRealTimeTaskExecutor {
    private static final Logger logger = LoggerFactory.getLogger(QueryCheckAsyncTaskExecutor.class);

    public void execute(JobContext jobContext) {
        block11: {
            IQueryModalDefineDao queryModalDefineDao = (IQueryModalDefineDao)BeanUtil.getBean(IQueryModalDefineDao.class);
            HashMap<String, Boolean> result = new HashMap<String, Boolean>();
            String errorInfo = "task_error_info";
            String finishInfo = "task_success_info";
            String taskId = jobContext.getInstanceId();
            AbstractRealTimeJob job = jobContext.getRealTimeJob();
            Map params = job.getParams();
            RealTimeTaskMonitor monitor = new RealTimeTaskMonitor(taskId, AsynctaskPoolType.ASYNCTASK_QUERYCHECK.getName(), jobContext);
            try {
                Object args;
                if (!Objects.nonNull(params) || !Objects.nonNull(params.get("NR_ARGS")) || !((args = SimpleParamConverter.SerializationUtils.deserialize((String)((String)params.get("NR_ARGS")))) instanceof QueryParamInfo)) break block11;
                if (monitor != null) {
                    monitor.progressAndMessage(0.1, "");
                }
                QueryParamInfo param = (QueryParamInfo)args;
                for (String defineKey : param.getQueryDefines()) {
                    QueryModalDefine define = queryModalDefineDao.queryModalDefineById(defineKey);
                    boolean queryResult = false;
                    if (define != null) {
                        try {
                            queryResult = QueryServices.hasQueryData((QueryModalDefine)define);
                        }
                        catch (Exception e) {
                            logger.error(e.getMessage(), e);
                        }
                    }
                    result.put(define.getId(), queryResult);
                }
                if (monitor != null) {
                    monitor.finish(finishInfo, result);
                }
                if (monitor != null) {
                    monitor.progressAndMessage(1.0, "");
                }
            }
            catch (NrCommonException nrCommonException) {
                monitor.error(errorInfo, (Throwable)nrCommonException);
                logger.error(nrCommonException.getMessage(), nrCommonException);
                if (monitor != null) {
                    monitor.finish("", result);
                }
            }
            catch (Exception e) {
                monitor.error(errorInfo, (Throwable)e);
                logger.error(e.getMessage(), e);
                if (monitor == null) break block11;
                monitor.finish("", result);
            }
        }
    }

    public String getTaskPoolType() {
        return AsynctaskPoolType.ASYNCTASK_QUERYCHECK.getName();
    }
}

