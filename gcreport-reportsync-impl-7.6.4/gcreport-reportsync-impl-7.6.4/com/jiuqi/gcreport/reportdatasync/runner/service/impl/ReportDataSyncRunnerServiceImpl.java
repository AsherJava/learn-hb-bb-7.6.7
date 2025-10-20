/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.JobContext
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.DateUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.gcreport.common.util.DimensionUtils
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.OrgVersionVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  com.jiuqi.gcreport.org.impl.util.internal.GcOrgVerTool
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.period.DefaultPeriodAdapter
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.nr.common.cache.remote.CacheObjectResourceRemote
 *  com.jiuqi.nr.common.util.DimensionValueSetUtil
 *  com.jiuqi.nr.dataentry.bean.BatchExportInfo
 *  com.jiuqi.nr.dataentry.bean.ExportData
 *  com.jiuqi.nr.dataentry.bean.ExportParam
 *  com.jiuqi.nr.dataentry.internal.service.JioBatchExportExecuter
 *  com.jiuqi.nr.dataentry.model.BatchDimensionParam
 *  com.jiuqi.nr.dataentry.monitor.SimpleAsyncProgressMonitor
 *  com.jiuqi.nr.dataentry.util.BatchExportConsts
 *  com.jiuqi.nr.definition.api.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.service.RunTimeTaskDefineService
 *  com.jiuqi.nr.jtable.params.base.EntityViewData
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nr.jtable.service.IJtableParamService
 *  nr.single.map.configurations.bean.ISingleMappingConfig
 *  nr.single.map.data.service.SingleMappingService
 *  org.apache.commons.io.FilenameUtils
 */
package com.jiuqi.gcreport.reportdatasync.runner.service.impl;

import com.jiuqi.bi.core.jobs.JobContext;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.DateUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.gcreport.common.util.DimensionUtils;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.OrgVersionVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.gcreport.org.impl.util.internal.GcOrgVerTool;
import com.jiuqi.gcreport.reportdatasync.enums.ReportDataSyncUploadSchemePeriodType;
import com.jiuqi.gcreport.reportdatasync.runner.context.ReportDataSyncRunnerContext;
import com.jiuqi.gcreport.reportdatasync.runner.param.ReportDataSyncRunnerParam;
import com.jiuqi.gcreport.reportdatasync.runner.service.ReportDataSyncRunnerService;
import com.jiuqi.gcreport.reportdatasync.runner.systemhook.ReportDataSyncRunnerSystemHook;
import com.jiuqi.gcreport.reportdatasync.util.ReportDataSyncDataUtils;
import com.jiuqi.gcreport.reportdatasync.util.ReportDataSyncUtils;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.period.DefaultPeriodAdapter;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.nr.common.cache.remote.CacheObjectResourceRemote;
import com.jiuqi.nr.common.util.DimensionValueSetUtil;
import com.jiuqi.nr.dataentry.bean.BatchExportInfo;
import com.jiuqi.nr.dataentry.bean.ExportData;
import com.jiuqi.nr.dataentry.bean.ExportParam;
import com.jiuqi.nr.dataentry.internal.service.JioBatchExportExecuter;
import com.jiuqi.nr.dataentry.model.BatchDimensionParam;
import com.jiuqi.nr.dataentry.monitor.SimpleAsyncProgressMonitor;
import com.jiuqi.nr.dataentry.util.BatchExportConsts;
import com.jiuqi.nr.definition.api.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.service.RunTimeTaskDefineService;
import com.jiuqi.nr.jtable.params.base.EntityViewData;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.service.IJtableParamService;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import nr.single.map.configurations.bean.ISingleMappingConfig;
import nr.single.map.data.service.SingleMappingService;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReportDataSyncRunnerServiceImpl
implements ReportDataSyncRunnerService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReportDataSyncRunnerServiceImpl.class);
    @Autowired
    private RunTimeTaskDefineService taskDefineService;
    @Autowired
    private SingleMappingService mappingConfigService;
    @Autowired
    private IJtableParamService jtableParamService;
    @Autowired(required=false)
    private List<ReportDataSyncRunnerSystemHook> runnerSystemHooks;

    @Override
    public List<ReportDataSyncRunnerSystemHook> getRunnerSystemHooks() {
        return this.runnerSystemHooks;
    }

    @Override
    public void runner(JobContext jobContext, ReportDataSyncRunnerParam reportDataSyncRunnerParam, List<String> logs) throws Exception {
        String periodValue;
        if (CollectionUtils.isEmpty(this.runnerSystemHooks)) {
            throw new BusinessRuntimeException("\u627e\u4e0d\u5230\u5bf9\u63a5\u7cfb\u7edf\uff0c\u672c\u6b21\u8df3\u8fc7\u63a8\u9001jio\u6570\u636e\u3002");
        }
        String taskId = reportDataSyncRunnerParam.getTaskId();
        String mappingConfigKey = reportDataSyncRunnerParam.getMappingConfigKey();
        String periodType = reportDataSyncRunnerParam.getPeriodType();
        String orgType = reportDataSyncRunnerParam.getOrgType();
        TaskDefine taskDefine = this.taskDefineService.queryTaskDefine(taskId);
        if (taskDefine == null) {
            throw new BusinessRuntimeException("\u627e\u4e0d\u5230\u5bf9\u5e94\u7684\u62a5\u8868\u4efb\u52a1\uff0ctaskid:" + taskId);
        }
        PeriodType taskPeriodType = taskDefine.getPeriodType();
        ISingleMappingConfig mappingConfig = this.mappingConfigService.getConfigByKey(mappingConfigKey);
        if (mappingConfig == null) {
            throw new BusinessRuntimeException("\u627e\u4e0d\u5230\u5bf9\u5e94\u7684\u62a5\u8868JIO\u6620\u5c04\u65b9\u6848\uff0cmappingConfigKey:" + mappingConfigKey);
        }
        ReportDataSyncUploadSchemePeriodType uploadSchemePeriodType = ReportDataSyncUploadSchemePeriodType.valueOf(periodType);
        switch (uploadSchemePeriodType) {
            case before: {
                periodValue = ReportDataSyncUtils.getBeforePriorPeriod(taskPeriodType);
                break;
            }
            case current: {
                periodValue = ReportDataSyncUtils.getCurrentPriorPeriod(taskPeriodType);
                break;
            }
            case custom: {
                periodValue = reportDataSyncRunnerParam.getPeriodStr();
                break;
            }
            default: {
                throw new BusinessRuntimeException("\u4e0d\u652f\u6301\u7684\u65f6\u671f\u7c7b\u578b\u3002" + periodType);
            }
        }
        String periodTitle = ReportDataSyncUtils.getPeriodTitle(periodValue);
        logs.add("\u5f53\u524d\u751f\u6210JIO\u6570\u636e\u6240\u5c5e\u65f6\u671f\uff1a" + periodTitle);
        try {
            boolean allCorp;
            Set orgCodes;
            String schemeKey = mappingConfig.getSchemeKey();
            if (StringUtils.isEmpty((String)orgType)) {
                EntityViewData dwEntityView = this.jtableParamService.getDwEntity(schemeKey);
                orgType = dwEntityView.getTableName();
            }
            Date[] periodDateRegion = new DefaultPeriodAdapter().getPeriodDateRegion(periodValue);
            OrgVersionVO orgVersionVO = GcOrgVerTool.getInstance().getOrgVersionByDate(orgType, periodDateRegion[0]);
            if (orgVersionVO == null) {
                throw new BusinessRuntimeException("\u627e\u4e0d\u5230\u7ec4\u7ec7\u673a\u6784\u7c7b\u578b[" + orgType + "]\u5bf9\u5e94\u65e5\u671f[" + DateUtils.format((Date)periodDateRegion[0]) + "]\u4e0b\u7684\u7ec4\u7ec7\u7248\u672c\u4fe1\u606f\u3002");
            }
            String orgCheckType = reportDataSyncRunnerParam.getOrgCheckType();
            YearPeriodObject yp = new YearPeriodObject(null, periodValue);
            GcOrgCenterService tool = GcOrgPublicTool.getInstance((String)orgType, (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
            if ("all_org".equals(orgCheckType)) {
                orgCodes = tool.listAllOrgByParentIdContainsSelf(null).stream().map(GcOrgCacheVO::getCode).collect(Collectors.toSet());
                allCorp = true;
            } else if ("choose_org".equals(orgCheckType)) {
                orgCodes = new HashSet<String>(reportDataSyncRunnerParam.getSelectedOrgIds());
                allCorp = false;
            } else {
                throw new BusinessRuntimeException("\u4e0d\u652f\u6301\u7684\u5355\u4f4d\u9009\u62e9\u65b9\u5f0f\uff0c" + orgCheckType);
            }
            String orgCodesDimValue = String.join((CharSequence)";", orgCodes);
            DimensionValueSet ds = DimensionUtils.generateDimSet((Object)orgCodesDimValue, (Object)periodValue, (Object)"CNY", (Object)"", (String)"0", (String)taskId);
            Map dimensionSet = DimensionValueSetUtil.getDimensionSet((DimensionValueSet)ds);
            JtableContext jtableContext = new JtableContext();
            jtableContext.setTaskKey(taskId);
            jtableContext.setFormSchemeKey(schemeKey);
            jtableContext.setDimensionSet(dimensionSet);
            ExportParam param = new ExportParam();
            param.setContext(jtableContext);
            param.setTabs(Collections.emptyList());
            param.setType("EXPORT_JIO");
            param.setConfigKey(mappingConfigKey);
            param.setAllCorp(allCorp);
            ExportData result = this.buildJioExportData(jtableContext, mappingConfigKey, periodValue);
            String jioFilePath = result.getFileLocation();
            File jioFile = new File(jioFilePath);
            logs.add("\u5bfc\u51fajio\u6587\u4ef6\u4f4d\u7f6e\uff1a" + jioFilePath + "\uff0c\u6587\u4ef6\u5927\u5c0f\uff1a" + jioFile.length());
            ReportDataSyncRunnerContext context = new ReportDataSyncRunnerContext();
            context.setReportDataSyncRunnerParam(reportDataSyncRunnerParam);
            context.setExportParam(param);
            context.setPeriodValue(periodValue);
            context.setOrgVersionVO(orgVersionVO);
            context.setStarttime(periodDateRegion[0]);
            context.setEndtime(periodDateRegion[1]);
            context.setMappingConfig(mappingConfig);
            this.runnerSystemHooks.stream().forEach(runnerSystemHook -> {
                String systemName = runnerSystemHook.getHookName();
                if (!systemName.equals(reportDataSyncRunnerParam.getSystemHookName())) {
                    return;
                }
                try {
                    logs.add("---\u5f00\u59cbJIO\u6587\u4ef6\u63a8\u9001\u81f3\u201c" + runnerSystemHook.getHookTitle() + "\u7cfb\u7edf\u201d---");
                    runnerSystemHook.pushHook(context, jioFile);
                }
                catch (Exception e) {
                    LOGGER.error(e.getMessage(), e);
                    throw new BusinessRuntimeException("JIO\u6587\u4ef6\u63a8\u9001\u81f3\u201c" + runnerSystemHook.getHookTitle() + "\u7cfb\u7edf\u201d\u53d1\u751f\u5f02\u5e38\uff1a" + e.getMessage(), (Throwable)e);
                }
                finally {
                    context.getLogs().stream().forEach(logInfo -> logs.add((String)logInfo));
                    logs.add("---\u7ed3\u675fJIO\u6587\u4ef6\u63a8\u9001\u81f3\u201c" + runnerSystemHook.getHookTitle() + "\u7cfb\u7edf\u201d---");
                }
            });
        }
        catch (Exception e) {
            logs.add(e.getMessage());
            LOGGER.error(e.getMessage(), e);
        }
    }

    private ExportData buildJioExportData(JtableContext jtableContext, String mappingConfigKey, String period) throws Exception {
        JioBatchExportExecuter exportExecuter = (JioBatchExportExecuter)SpringContextUtils.getBean(JioBatchExportExecuter.class);
        String jioFilepath = BatchExportConsts.EXPORTDIR + File.separator + "jioFile" + File.separator;
        BatchExportInfo info = new BatchExportInfo();
        info.setConfigKey(mappingConfigKey);
        info.setFileType("EXPORT_BATCH_JIO");
        info.setCompressionType("zip");
        info.setContext(jtableContext);
        CacheObjectResourceRemote cacheObjectResourceRemote = (CacheObjectResourceRemote)SpringContextUtils.getBean(CacheObjectResourceRemote.class);
        SimpleAsyncProgressMonitor asyncTaskMonitor = new SimpleAsyncProgressMonitor(UUIDUtils.newUUIDStr(), cacheObjectResourceRemote);
        ArrayList<BatchDimensionParam> dimensionInfoBuild = new ArrayList<BatchDimensionParam>();
        ArrayList<Map> resultDimension = new ArrayList<Map>();
        resultDimension.add(info.getContext().getDimensionSet());
        BatchDimensionParam batchDimensionParam = ReportDataSyncDataUtils.dimensionInfoBuild(info.getContext(), (AsyncTaskMonitor)asyncTaskMonitor);
        dimensionInfoBuild.add(batchDimensionParam);
        ArrayList<String> multiplePeriodList = new ArrayList<String>();
        multiplePeriodList.add(period);
        String dateDir = new DefaultPeriodAdapter().getPeriodTitle(period) + BatchExportConsts.SEPARATOR;
        ArrayList datas = new ArrayList();
        info.setLocation(jioFilepath);
        info.setZipLocation(jioFilepath);
        IRunTimeViewController controller = (IRunTimeViewController)SpringContextUtils.getBean(IRunTimeViewController.class);
        String schemeKey = jtableContext.getFormSchemeKey();
        List formKeys = controller.listFormByFormScheme(schemeKey).stream().map(v -> v.getKey()).collect(Collectors.toList());
        ExportData exportData = exportExecuter.exportOfMultiplePeriod(info, (AsyncTaskMonitor)asyncTaskMonitor, dimensionInfoBuild, multiplePeriodList, formKeys, dateDir, datas);
        return exportData;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private File uploadFile(byte[] file, String filePath, String fileName) throws Exception {
        File targetFile = new File(FilenameUtils.normalize((String)filePath));
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        String jioFilePath = FilenameUtils.normalize((String)(filePath + fileName));
        try (FileOutputStream out = new FileOutputStream(jioFilePath);){
            out.write(file);
            out.flush();
        }
        File jioFile = new File(jioFilePath);
        return jioFile;
    }
}

