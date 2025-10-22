/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.JobContext
 *  com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob
 *  com.jiuqi.bi.core.jobs.realtime.RealTimeJob
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.asynctask.NpRealTimeTaskExecutor
 *  com.jiuqi.np.asynctask.impl.RealTimeTaskMonitor
 *  com.jiuqi.np.asynctask.util.SimpleParamConverter$SerializationUtils
 *  com.jiuqi.np.blob.util.BeanUtil
 *  com.jiuqi.nr.common.exception.NrCommonException
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.data.logic.internal.util.DimensionCollectionUtil
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollectionBuilder
 *  com.jiuqi.nr.definition.common.FormType
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.snapshot.input.CreateSnapshotContext
 *  com.jiuqi.nr.snapshot.service.SnapshotService
 */
package com.jiuqi.nr.dataSnapshot.asynctask;

import com.jiuqi.bi.core.jobs.JobContext;
import com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob;
import com.jiuqi.bi.core.jobs.realtime.RealTimeJob;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.asynctask.NpRealTimeTaskExecutor;
import com.jiuqi.np.asynctask.impl.RealTimeTaskMonitor;
import com.jiuqi.np.asynctask.util.SimpleParamConverter;
import com.jiuqi.np.blob.util.BeanUtil;
import com.jiuqi.nr.common.exception.NrCommonException;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.data.logic.internal.util.DimensionCollectionUtil;
import com.jiuqi.nr.dataSnapshot.param.DataSnapshotParam;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollectionBuilder;
import com.jiuqi.nr.definition.common.FormType;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.snapshot.input.CreateSnapshotContext;
import com.jiuqi.nr.snapshot.service.SnapshotService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RealTimeJob(group="ASYNCTASK_DATASNAPSHOT", groupTitle="\u5feb\u7167")
public class DataSnapshotTaskExecutor
extends NpRealTimeTaskExecutor {
    private static final Logger logger = LoggerFactory.getLogger(DataSnapshotTaskExecutor.class);

    public void execute(JobContext jobContext) {
        String errorInfo = "task_error_info";
        String cancelInfo = "task_cancel_info";
        RealTimeTaskMonitor asyncTaskMonitor = null;
        try {
            SnapshotService snapshotService = (SnapshotService)BeanUtil.getBean(SnapshotService.class);
            DimensionCollectionUtil dimensionCollectionUtil = (DimensionCollectionUtil)BeanUtil.getBean(DimensionCollectionUtil.class);
            IRunTimeViewController iRunTimeViewController = (IRunTimeViewController)BeanUtil.getBean(IRunTimeViewController.class);
            String taskId = jobContext.getInstanceId();
            AbstractRealTimeJob job = jobContext.getRealTimeJob();
            Map params = job.getParams();
            asyncTaskMonitor = new RealTimeTaskMonitor(taskId, "ASYNCTASK_DATASNAPSHOT", jobContext);
            if (Objects.nonNull(params) && Objects.nonNull(params.get("NR_ARGS"))) {
                DataSnapshotParam param = (DataSnapshotParam)SimpleParamConverter.SerializationUtils.deserialize((String)((String)params.get("NR_ARGS")));
                CreateSnapshotContext createSnapshotContext = new CreateSnapshotContext();
                createSnapshotContext.setDescribe(param.getDescribe());
                createSnapshotContext.setFormKeys(param.getFormKeys());
                createSnapshotContext.setTitle(param.getTitle());
                createSnapshotContext.setFormSchemeKey(param.getContext().getFormSchemeKey());
                Map dimensionSet = param.getContext().getDimensionSet();
                DimensionCollectionBuilder builder = new DimensionCollectionBuilder();
                for (String key : dimensionSet.keySet()) {
                    String[] values = ((DimensionValue)dimensionSet.get(key)).getValue().split(";");
                    if (values.length == 1 || values.length == 0) {
                        builder.setEntityValue(key, null, new Object[]{((DimensionValue)dimensionSet.get(key)).getValue()});
                        continue;
                    }
                    List<String> valueList = Arrays.asList(values);
                    builder.setEntityValue(key, null, new Object[]{valueList});
                }
                DimensionCollection dimensionCollection = builder.getCollection();
                createSnapshotContext.setDimensionCollection(dimensionCollection);
                List forms = iRunTimeViewController.queryAllFormDefinesByFormScheme(param.getContext().getFormSchemeKey());
                ArrayList<String> formkeys = new ArrayList<String>();
                for (FormDefine formDefine : forms) {
                    if (formDefine.getFormType().equals((Object)FormType.FORM_TYPE_NEWFMDM) || formDefine.getFormType().equals((Object)FormType.FORM_TYPE_ACCOUNT) || formDefine.getFormType().equals((Object)FormType.FORM_TYPE_ANALYSISREPORT) || formDefine.getFormType().equals((Object)FormType.FORM_TYPE_INSERTANALYSIS)) continue;
                    formkeys.add(formDefine.getKey());
                }
                createSnapshotContext.setFormKeys(formkeys);
                snapshotService.createSnapshot(createSnapshotContext, (AsyncTaskMonitor)asyncTaskMonitor);
                if (asyncTaskMonitor.isCancel()) {
                    asyncTaskMonitor.canceled(cancelInfo, (Object)cancelInfo);
                }
                asyncTaskMonitor.progressAndMessage(1.0, "success");
                asyncTaskMonitor.finish("success", (Object)"");
            }
        }
        catch (NrCommonException nrCommonException) {
            asyncTaskMonitor.error(nrCommonException.getMessage(), (Throwable)nrCommonException);
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + nrCommonException.getMessage(), nrCommonException);
        }
        catch (Exception e) {
            asyncTaskMonitor.error(e.getMessage(), (Throwable)e);
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
    }

    public String getTaskPoolType() {
        return "ASYNCTASK_DATASNAPSHOT";
    }
}

