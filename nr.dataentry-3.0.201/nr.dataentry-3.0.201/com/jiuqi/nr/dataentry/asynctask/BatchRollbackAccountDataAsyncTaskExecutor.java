/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.JobContext
 *  com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.asynctask.NpRealTimeTaskExecutor
 *  com.jiuqi.np.asynctask.util.SimpleParamConverter$SerializationUtils
 *  com.jiuqi.np.blob.util.BeanUtil
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.definition.common.FormType
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormGroupDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 */
package com.jiuqi.nr.dataentry.asynctask;

import com.jiuqi.bi.core.jobs.JobContext;
import com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.asynctask.NpRealTimeTaskExecutor;
import com.jiuqi.np.asynctask.util.SimpleParamConverter;
import com.jiuqi.np.blob.util.BeanUtil;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.dataentry.internal.service.BatchRollbackAccountDataService;
import com.jiuqi.nr.dataentry.paramInfo.AccountRollBackParam;
import com.jiuqi.nr.dataentry.paramInfo.BatchAccountRollBack;
import com.jiuqi.nr.definition.common.FormType;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormGroupDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.entity.engine.executors.ExecutorContext;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

public class BatchRollbackAccountDataAsyncTaskExecutor
extends NpRealTimeTaskExecutor {
    private static final Logger logger = LoggerFactory.getLogger(BatchRollbackAccountDataAsyncTaskExecutor.class);

    public void executeWithNpContext(JobContext jobContext) {
        String errorInfo = "task_error_info";
        BatchRollbackAccountDataService batchRollbackAccountDataService = (BatchRollbackAccountDataService)BeanUtil.getBean(BatchRollbackAccountDataService.class);
        AbstractRealTimeJob job = jobContext.getRealTimeJob();
        Map params = job.getParams();
        AsyncTaskMonitorLog monitor = null;
        IRunTimeViewController runTimeViewController = (IRunTimeViewController)BeanUtil.getBean(IRunTimeViewController.class);
        IEntityDataService entityDataService = (IEntityDataService)BeanUtil.getBean(IEntityDataService.class);
        IDataDefinitionRuntimeController runtimeController = (IDataDefinitionRuntimeController)BeanUtil.getBean(IDataDefinitionRuntimeController.class);
        IEntityMetaService entityMetaService = (IEntityMetaService)BeanUtil.getBean(IEntityMetaService.class);
        try {
            BatchAccountRollBack batchAccountRollBack;
            if (Objects.nonNull(params) && Objects.nonNull(params.get("NR_ARGS")) && (batchAccountRollBack = (BatchAccountRollBack)SimpleParamConverter.SerializationUtils.deserialize((String)((String)params.get("NR_ARGS")))) != null) {
                FormSchemeDefine formScheme;
                String period = batchAccountRollBack.getPeriod();
                String formSchemeKey = batchAccountRollBack.getFormSchemeKey();
                boolean allChildren = batchAccountRollBack.isAllChildren();
                boolean allDw = batchAccountRollBack.isAllDw();
                if (StringUtils.hasLength(period) && StringUtils.hasLength(formSchemeKey) && (formScheme = runTimeViewController.getFormScheme(formSchemeKey)) != null) {
                    IEntityTable table;
                    EntityViewDefine viewByFormSchemeKey;
                    DimensionValueSet masterKey;
                    IEntityQuery entityQuery;
                    String formKeys = batchAccountRollBack.getFormKeys();
                    List formDefines = StringUtils.hasLength(formKeys) ? runTimeViewController.queryFormsById(Arrays.stream(formKeys.split(";")).collect(Collectors.toList())) : runTimeViewController.queryAllFormDefinesByFormScheme(formSchemeKey);
                    formDefines.removeIf(next -> next.getFormType() != FormType.FORM_TYPE_ACCOUNT);
                    List<Object> dwKeys = new ArrayList();
                    String dwKey = batchAccountRollBack.getDwKey();
                    String dwDimName = entityMetaService.getDimensionName(formScheme.getDw());
                    if (allChildren && StringUtils.hasLength(dwKey)) {
                        entityQuery = entityDataService.newEntityQuery();
                        masterKey = new DimensionValueSet();
                        masterKey.setValue("DATATIME", (Object)period);
                        entityQuery.setMasterKeys(masterKey);
                        viewByFormSchemeKey = runTimeViewController.getViewByFormSchemeKey(formSchemeKey);
                        entityQuery.setEntityView(viewByFormSchemeKey);
                        ExecutorContext executorContext = new ExecutorContext(runtimeController);
                        table = entityQuery.executeReader((IContext)executorContext);
                        List allChildRows = table.getAllChildRows(dwKey);
                        for (IEntityRow allRow : allChildRows) {
                            dwKeys.add(allRow.getEntityKeyData());
                        }
                    } else if (StringUtils.hasLength(dwKey)) {
                        dwKeys = Arrays.stream(dwKey.split(";")).collect(Collectors.toList());
                    } else if (allDw) {
                        entityQuery = entityDataService.newEntityQuery();
                        masterKey = new DimensionValueSet();
                        masterKey.setValue("DATATIME", (Object)period);
                        entityQuery.setMasterKeys(masterKey);
                        viewByFormSchemeKey = runTimeViewController.getViewByFormSchemeKey(formSchemeKey);
                        entityQuery.setEntityView(viewByFormSchemeKey);
                        ExecutorContext executorContext = new ExecutorContext(runtimeController);
                        table = entityQuery.executeReader((IContext)executorContext);
                        List allRows = table.getAllRows();
                        for (IEntityRow allRow : allRows) {
                            dwKeys.add(allRow.getEntityKeyData());
                        }
                    } else {
                        logger.warn("\u8bf7\u5148\u6307\u5b9a\u5355\u4f4d\u8303\u56f4");
                        return;
                    }
                    logger.info("{} \u4e2a\u5355\u4f4d{} \u4e2a\u8868\u5355 \u5f00\u59cb\u56de\u6eda", (Object)dwKeys.size(), (Object)formDefines.size());
                    HashMap<String, String> cache = new HashMap<String, String>();
                    int i = 0;
                    monitor = new AsyncTaskMonitorLog();
                    for (String string : dwKeys) {
                        logger.info("\u7b2c {} \u4e2a\u5355\u4f4d \u5f00\u59cb\u56de\u6eda, \u5171 {} \u4e2a\u5355\u4f4d", (Object)(++i), (Object)dwKeys.size());
                        for (FormDefine form : formDefines) {
                            List groups;
                            Optional first;
                            String groupKey = (String)cache.get(form.getKey());
                            if (groupKey == null && (first = (groups = runTimeViewController.getFormGroupsByFormKey(form.getKey())).stream().findFirst()).isPresent()) {
                                FormGroupDefine formGroupDefine = (FormGroupDefine)first.get();
                                groupKey = formGroupDefine.getKey();
                                cache.put(form.getKey(), groupKey);
                            }
                            AccountRollBackParam accountRollBackParam = new AccountRollBackParam();
                            JtableContext jtableContext = new JtableContext();
                            jtableContext.setFormKey(form.getKey());
                            jtableContext.setFormGroupKey(groupKey);
                            jtableContext.setTaskKey(formScheme.getTaskKey());
                            HashMap<String, DimensionValue> dimensionSet = new HashMap<String, DimensionValue>();
                            DimensionValue periodDimensionValue = new DimensionValue();
                            periodDimensionValue.setName("DATATIME");
                            periodDimensionValue.setValue(period);
                            dimensionSet.put("DATATIME", periodDimensionValue);
                            DimensionValue dwValue = new DimensionValue();
                            dwValue.setName(dwDimName);
                            dwValue.setValue(string);
                            dimensionSet.put(dwDimName, dwValue);
                            jtableContext.setFormSchemeKey(formSchemeKey);
                            jtableContext.setDimensionSet(dimensionSet);
                            accountRollBackParam.setFormKeys(form.getKey());
                            accountRollBackParam.setJtableContext(jtableContext);
                            try {
                                logger.info("\u5355\u4f4d{} \u8868\u5355 {} \u5f00\u59cb\u56de\u6eda", (Object)string, (Object)form.getTitle());
                                batchRollbackAccountDataService.asyncRollbackAccountData(accountRollBackParam, monitor);
                            }
                            catch (Exception e) {
                                logger.error("\u56de\u6eda\u5931\u8d25", e);
                            }
                        }
                    }
                }
            }
        }
        catch (Exception e) {
            monitor.error(errorInfo, e);
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a{}", (Object)e.getMessage(), (Object)e);
        }
    }

    public static class AsyncTaskMonitorLog
    implements AsyncTaskMonitor {
        public String getTaskId() {
            return "";
        }

        public String getTaskPoolTask() {
            return "";
        }

        public void progressAndMessage(double v, String s) {
        }

        public boolean isCancel() {
            return false;
        }

        public void finish(String s, Object o) {
            logger.info("\u6b64\u5355\u4f4d\u8868\u5355\u6267\u884c\u5b8c\u6210 {} ", (Object)s);
        }

        public void canceling(String s, Object o) {
        }

        public void canceled(String s, Object o) {
        }

        public void error(String s, Throwable throwable) {
            logger.error("\u6b64\u5355\u4f4d\u8868\u5355\u6267\u884c\u5931\u8d25 {} {} ", (Object)s, (Object)throwable);
        }

        public boolean isFinish() {
            return false;
        }
    }
}

