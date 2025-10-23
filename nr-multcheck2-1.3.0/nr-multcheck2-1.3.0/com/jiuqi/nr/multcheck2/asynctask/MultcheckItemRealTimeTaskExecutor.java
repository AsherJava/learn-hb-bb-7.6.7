/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.JobContext
 *  com.jiuqi.bi.core.jobs.JobExecutionException
 *  com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob
 *  com.jiuqi.bi.core.jobs.realtime.RealTimeJob
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.asynctask.NpRealTimeTaskExecutor
 *  com.jiuqi.np.asynctask.impl.RealTimeTaskMonitor
 *  com.jiuqi.np.asynctask.util.SimpleParamConverter$SerializationUtils
 *  com.jiuqi.np.blob.util.BeanUtil
 *  com.jiuqi.np.log.comm.LogTraceIDUtil
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.context.cxt.DsContext
 *  com.jiuqi.nr.context.cxt.DsContextHolder
 *  com.jiuqi.nr.context.cxt.impl.DsContextImpl
 *  com.jiuqi.nr.data.access.util.DimensionValueSetUtil
 */
package com.jiuqi.nr.multcheck2.asynctask;

import com.jiuqi.bi.core.jobs.JobContext;
import com.jiuqi.bi.core.jobs.JobExecutionException;
import com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob;
import com.jiuqi.bi.core.jobs.realtime.RealTimeJob;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.asynctask.NpRealTimeTaskExecutor;
import com.jiuqi.np.asynctask.impl.RealTimeTaskMonitor;
import com.jiuqi.np.asynctask.util.SimpleParamConverter;
import com.jiuqi.np.blob.util.BeanUtil;
import com.jiuqi.np.log.comm.LogTraceIDUtil;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.context.cxt.DsContext;
import com.jiuqi.nr.context.cxt.DsContextHolder;
import com.jiuqi.nr.context.cxt.impl.DsContextImpl;
import com.jiuqi.nr.data.access.util.DimensionValueSetUtil;
import com.jiuqi.nr.multcheck2.asynctask.MCRealTimeTaskMonitor;
import com.jiuqi.nr.multcheck2.collector.MultcheckCollector;
import com.jiuqi.nr.multcheck2.provider.CheckItemParam;
import com.jiuqi.nr.multcheck2.provider.CheckItemResult;
import com.jiuqi.nr.multcheck2.provider.CheckItemResultDate;
import com.jiuqi.nr.multcheck2.provider.MultcheckContext;
import com.jiuqi.nr.multcheck2.web.vo.CheckItemEnvParam;
import com.jiuqi.nr.multcheck2.web.vo.MultcheckEnvContext;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@RealTimeJob(subject="\u62a5\u8868", group="ASYNCTASK_MULTCHECK_ITEM", groupTitle="\u7efc\u5408\u5ba1\u6838\u9879\u6267\u884c")
public class MultcheckItemRealTimeTaskExecutor
extends NpRealTimeTaskExecutor {
    private static final Logger logger = LoggerFactory.getLogger(MultcheckItemRealTimeTaskExecutor.class);

    public void executeWithNpContext(JobContext jobContext) throws JobExecutionException {
        String taskId = jobContext.getInstanceId();
        AbstractRealTimeJob job = jobContext.getRealTimeJob();
        Map params = job.getParams();
        RealTimeTaskMonitor asyncTaskMonitor = new RealTimeTaskMonitor(taskId, "ASYNCTASK_MULTCHECK_ITEM", jobContext);
        MCRealTimeTaskMonitor asyncTaskMonitor1 = new MCRealTimeTaskMonitor(taskId, "ASYNCTASK_MULTCHECK_ITEM", jobContext);
        String title = "";
        CheckItemParam itemParam = new CheckItemParam();
        try {
            if (Objects.nonNull(params) && Objects.nonNull(params.get("NR_ARGS"))) {
                CheckItemEnvParam itemEnvParam = (CheckItemEnvParam)SimpleParamConverter.SerializationUtils.deserialize((String)((String)params.get("NR_ARGS")));
                MultcheckCollector collector = (MultcheckCollector)BeanUtil.getBean(MultcheckCollector.class);
                if (StringUtils.hasText(itemEnvParam.getTraceid())) {
                    LogTraceIDUtil.setLogTraceId((String)itemEnvParam.getTraceid());
                }
                DsContextImpl newContext = (DsContextImpl)DsContextHolder.createEmptyContext();
                newContext.setEntityId(itemEnvParam.getEnvContext().getOrg());
                DsContextHolder.setDsContext((DsContext)newContext);
                BeanUtils.copyProperties(itemEnvParam, itemParam);
                MultcheckContext context = new MultcheckContext();
                MultcheckEnvContext envContext = itemEnvParam.getEnvContext();
                BeanUtils.copyProperties(envContext, context);
                if (!CollectionUtils.isEmpty(itemEnvParam.getItemDimForReport())) {
                    for (String dim : itemEnvParam.getItemDimForReport().keySet()) {
                        DimensionValue dimensionValue = envContext.getDimSetMap().get(dim);
                        dimensionValue.setValue(itemEnvParam.getItemDimForReport().get(dim));
                    }
                }
                context.setDims(DimensionValueSetUtil.buildDimensionCollection(envContext.getDimSetMap(), (String)envContext.getFormSchemeKey()));
                itemParam.setContext(context);
                itemParam.setAsyncTaskMonitor((AsyncTaskMonitor)asyncTaskMonitor1);
                title = itemParam.getCheckItem().getTitle();
                ArrayList<String> orgList = new ArrayList<String>(itemParam.getContext().getOrgList());
                Date begin = new Date();
                CheckItemResult res = collector.getProvider(itemEnvParam.getCheckItem().getType()).runCheck(itemParam);
                if (asyncTaskMonitor.isCancel()) {
                    logger.warn(itemParam.getCheckItem().getTitle() + "\uff0c\u5ba1\u6838\u9879\u76ee\u5df2\u53d6\u6d88");
                    asyncTaskMonitor.canceled("", (Object)"");
                } else {
                    int size3;
                    if (res == null) {
                        throw new Exception("\u3010" + itemEnvParam.getCheckItem().getTitle() + "\u3011\u6267\u884c\u5931\u8d25\uff01");
                    }
                    CheckItemResultDate resDate = new CheckItemResultDate();
                    BeanUtils.copyProperties(res, resDate);
                    resDate.setBegin(begin);
                    resDate.setEnd(new Date());
                    int size1 = CollectionUtils.isEmpty(resDate.getSuccessOrgs()) ? 0 : resDate.getSuccessOrgs().size();
                    int size2 = CollectionUtils.isEmpty(resDate.getSuccessWithExplainOrgs()) ? 0 : resDate.getSuccessWithExplainOrgs().size();
                    int n = size3 = CollectionUtils.isEmpty(resDate.getFailedOrgs()) ? 0 : resDate.getFailedOrgs().size();
                    if (orgList.size() != size1 + size2 + size3) {
                        if (size1 != 0) {
                            orgList.removeAll(resDate.getSuccessOrgs());
                        }
                        if (size2 != 0) {
                            orgList.removeAll(resDate.getSuccessWithExplainOrgs());
                        }
                        if (size3 != 0) {
                            orgList.removeAll(resDate.getFailedOrgs().keySet());
                        }
                        if (!CollectionUtils.isEmpty(resDate.getIgnoreOrgs())) {
                            orgList.removeAll(resDate.getIgnoreOrgs());
                        }
                        if (!CollectionUtils.isEmpty(orgList)) {
                            if (resDate.getIgnoreOrgs() == null) {
                                resDate.setIgnoreOrgs(new ArrayList<String>());
                            }
                            resDate.getIgnoreOrgs().addAll(orgList);
                            logger.info("\u5ba1\u6838\u9879" + title + "\u5b58\u5728\u672a\u6267\u884c\u5355\u4f4d:" + orgList.size());
                            logger.info("\u5ba1\u6838\u9879" + title + "\u603b\u5171\u672a\u6267\u884c\u5355\u4f4d:" + resDate.getIgnoreOrgs().size());
                        }
                    }
                    asyncTaskMonitor.finish(itemParam.getCheckItem().getTitle() + "\u6267\u884c\u5b8c\u6210", (Object)resDate);
                }
            }
        }
        catch (Exception e) {
            logger.error("\u5ba1\u6838\u9879" + title + "\u6267\u884c\u5931\u8d25\uff1a" + e.getMessage(), e);
            asyncTaskMonitor.error(e.getMessage(), (Throwable)e, e.getMessage());
        }
    }
}

