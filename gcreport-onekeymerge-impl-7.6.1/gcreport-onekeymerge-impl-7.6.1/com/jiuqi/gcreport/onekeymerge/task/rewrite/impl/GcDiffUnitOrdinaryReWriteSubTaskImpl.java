/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.ArrayKey
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.DoubleKeyMap
 *  com.jiuqi.common.base.util.NumberUtils
 *  com.jiuqi.gcreport.calculate.vo.DebugZbInfoVO
 *  com.jiuqi.gcreport.common.util.MapUtils
 *  com.jiuqi.gcreport.consolidatedsystem.entity.primaryworkpaper.PrimaryWorkPaperSettingEO
 *  com.jiuqi.gcreport.consolidatedsystem.entity.subject.ConsolidatedSubjectEO
 *  com.jiuqi.gcreport.consolidatedsystem.service.primaryworkpaper.PrimaryWorkpaperService
 *  com.jiuqi.gcreport.consolidatedsystem.service.subject.ConsolidatedSubjectService
 *  com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService
 *  com.jiuqi.gcreport.consolidatedsystem.vo.task.GcDiffRewriteWayEnum
 *  com.jiuqi.gcreport.nr.impl.uploadstate.vo.DimensionParamsVO
 *  com.jiuqi.gcreport.offsetitem.service.GcOffSetAppOffsetService
 *  com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO
 *  com.jiuqi.gcreport.onekeymerge.vo.GcActionParamsVO
 *  com.jiuqi.gcreport.onekeymerge.vo.QueryCondition
 *  com.jiuqi.gcreport.onekeymerge.vo.ReturnObject
 *  com.jiuqi.gcreport.org.api.period.YearPeriodDO
 *  com.jiuqi.gcreport.org.api.period.YearPeriodUtil
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.rewritesetting.vo.ReWriteSubject
 *  com.jiuqi.gcreport.temp.dto.TaskLog
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.core.Basic
 *  com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.internal.runtime.controller.RuntimeViewController
 *  org.apache.commons.lang3.StringUtils
 */
package com.jiuqi.gcreport.onekeymerge.task.rewrite.impl;

import com.jiuqi.bi.util.ArrayKey;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.DoubleKeyMap;
import com.jiuqi.common.base.util.NumberUtils;
import com.jiuqi.gcreport.calculate.vo.DebugZbInfoVO;
import com.jiuqi.gcreport.common.util.MapUtils;
import com.jiuqi.gcreport.consolidatedsystem.entity.primaryworkpaper.PrimaryWorkPaperSettingEO;
import com.jiuqi.gcreport.consolidatedsystem.entity.subject.ConsolidatedSubjectEO;
import com.jiuqi.gcreport.consolidatedsystem.service.primaryworkpaper.PrimaryWorkpaperService;
import com.jiuqi.gcreport.consolidatedsystem.service.subject.ConsolidatedSubjectService;
import com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService;
import com.jiuqi.gcreport.consolidatedsystem.vo.task.GcDiffRewriteWayEnum;
import com.jiuqi.gcreport.nr.impl.uploadstate.vo.DimensionParamsVO;
import com.jiuqi.gcreport.offsetitem.service.GcOffSetAppOffsetService;
import com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO;
import com.jiuqi.gcreport.onekeymerge.task.rewrite.IGcDiffUnitReWriteSubTask;
import com.jiuqi.gcreport.onekeymerge.task.rewrite.ordinary.DetailUnitQueryParamCache;
import com.jiuqi.gcreport.onekeymerge.task.rewrite.ordinary.OrdinaryEndZbSubTask;
import com.jiuqi.gcreport.onekeymerge.task.rewrite.ordinary.QueryParamCache;
import com.jiuqi.gcreport.onekeymerge.util.NrUtils;
import com.jiuqi.gcreport.onekeymerge.util.OneKeyMergeUtils;
import com.jiuqi.gcreport.onekeymerge.util.OrgUtils;
import com.jiuqi.gcreport.onekeymerge.vo.GcActionParamsVO;
import com.jiuqi.gcreport.onekeymerge.vo.QueryCondition;
import com.jiuqi.gcreport.onekeymerge.vo.ReturnObject;
import com.jiuqi.gcreport.org.api.period.YearPeriodDO;
import com.jiuqi.gcreport.org.api.period.YearPeriodUtil;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.rewritesetting.vo.ReWriteSubject;
import com.jiuqi.gcreport.temp.dto.TaskLog;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.core.Basic;
import com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.internal.runtime.controller.RuntimeViewController;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GcDiffUnitOrdinaryReWriteSubTaskImpl
implements IGcDiffUnitReWriteSubTask {
    @Autowired
    private RuntimeViewController runtimeViewController;
    @Autowired
    private ConsolidatedSubjectService consolidatedSubjectService;
    @Autowired
    private ConsolidatedTaskService taskService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private OrdinaryEndZbSubTask ordinaryEndZbSubTask;
    @Autowired
    private PrimaryWorkpaperService primaryWorkpaperService;
    @Autowired
    private IRuntimeDataSchemeService runtimeDataSchemeService;
    @Autowired
    private GcOffSetAppOffsetService gcOffSetAppOffsetService;

    @Override
    public String getName() {
        return GcDiffRewriteWayEnum.SUBJECT_MAPPING.getCode();
    }

    @Override
    public ReturnObject doTask(GcActionParamsVO paramsVO, List<String> hbUnitIds, List<String> diffUnitIds, TaskLog taskLog) {
        ReturnObject returnObject = new ReturnObject();
        returnObject.setSuccess(true);
        DimensionParamsVO param = new DimensionParamsVO();
        BeanUtils.copyProperties(paramsVO, param);
        QueryParamCache queryParamCache = this.initCache(paramsVO);
        for (int i = 0; i < hbUnitIds.size(); ++i) {
            String hbUnitId = hbUnitIds.get(i);
            String diffUnitId = diffUnitIds.get(i);
            OneKeyMergeUtils.checkStopOrNot(paramsVO);
            this.checkUnitFormState(taskLog, paramsVO, diffUnitId);
            List<String> filterFormIdList = OneKeyMergeUtils.getFilterLockedAndHiddenForm(paramsVO.getSchemeId(), diffUnitId, paramsVO);
            DetailUnitQueryParamCache detailUnitQueryParamCache = new DetailUnitQueryParamCache(queryParamCache);
            Set<String> tableDefineCodeSet = NrUtils.convert2SimpleTableDefineCodes(filterFormIdList);
            detailUnitQueryParamCache.setTableDefineCodeSet(tableDefineCodeSet);
            QueryCondition queryCondition = OneKeyMergeUtils.buildQueryCondition(paramsVO, hbUnitId);
            Map<String, BigDecimal> subjectCode2AmtMap = this.getSubjectCode2AmtMap(queryCondition);
            detailUnitQueryParamCache.setHbUnitCode(hbUnitId);
            detailUnitQueryParamCache.setDiffUnitCode(diffUnitId);
            detailUnitQueryParamCache.setSubjectCode2AmtMap(subjectCode2AmtMap);
            this.ordinaryEndZbSubTask.reWriteEndOffset(paramsVO, detailUnitQueryParamCache);
        }
        return returnObject;
    }

    private Map<String, BigDecimal> getSubjectCode2AmtMap(QueryCondition queryCondition) {
        List offsetValueList = this.gcOffSetAppOffsetService.sumOffsetValueGroupBySubjectcode(this.covertQueryParamsVO(queryCondition));
        HashMap<String, BigDecimal> subjectCode2AmtMap = new HashMap<String, BigDecimal>();
        for (Map record : offsetValueList) {
            String subjectcode = (String)record.get("SUBJECTCODE");
            BigDecimal creditvalue = new BigDecimal(String.valueOf(record.get("CREDITVALUE")));
            BigDecimal debitvalue = new BigDecimal(String.valueOf(record.get("DEBITVALUE")));
            MapUtils.add(subjectCode2AmtMap, (Object)subjectcode, (BigDecimal)NumberUtils.sub((BigDecimal)debitvalue, (BigDecimal)creditvalue));
        }
        return subjectCode2AmtMap;
    }

    private QueryParamsVO covertQueryParamsVO(QueryCondition condition) {
        QueryParamsVO queryParamsVO = new QueryParamsVO();
        queryParamsVO.setAcctPeriod(condition.getAcctPeriod());
        queryParamsVO.setAcctYear(condition.getAcctYear());
        queryParamsVO.setOrgId(condition.getOrgid());
        queryParamsVO.setCurrency(condition.getCurrencyCode());
        queryParamsVO.setElmModes(condition.getElmModes());
        queryParamsVO.setFilterCondition(condition.getFilterCondition());
        if (!queryParamsVO.getOrgId().equals(condition.getOppUnitId())) {
            LinkedList<String> uuids = new LinkedList<String>();
            uuids.add(condition.getOppUnitId());
            queryParamsVO.setOppUnitIdList(uuids);
        }
        YearPeriodDO yearPeriodUtil = YearPeriodUtil.transform(null, (int)condition.getAcctYear(), (int)condition.getPeriodType(), (int)condition.getAcctPeriod());
        condition.setPeriodStr(yearPeriodUtil.toString());
        queryParamsVO.setOrgType(condition.getOrg_type());
        queryParamsVO.setPageSize(-1);
        queryParamsVO.setPageNum(-1);
        queryParamsVO.setTaskId(condition.getTaskID());
        if (!queryParamsVO.getOrgId().equals(condition.getUnitId())) {
            LinkedList<String> unitIdList = new LinkedList<String>();
            unitIdList.add(condition.getUnitId());
            queryParamsVO.setUnitIdList(unitIdList);
        }
        ArrayList<String> showColumns = new ArrayList<String>();
        showColumns.add("OFFSETSRCTYPE");
        List otherColumnKeys = condition.getOtherShowColumnKeys();
        if (otherColumnKeys != null && otherColumnKeys.size() > 0) {
            showColumns.addAll(otherColumnKeys);
        }
        queryParamsVO.setOtherShowColumns(showColumns);
        queryParamsVO.setCurrency(condition.getCurrencyCode());
        queryParamsVO.setPeriodStr(condition.getPeriodStr());
        queryParamsVO.setArbitrarilyMerge(condition.getArbitrarilyMerge());
        queryParamsVO.setOrgBatchId(condition.getOrgBatchId());
        queryParamsVO.setOrgComSupLength(condition.getOrgComSupLength());
        queryParamsVO.setSelectAdjustCode(condition.getSelectAdjustCode());
        return queryParamsVO;
    }

    private QueryParamCache initCache(GcActionParamsVO paramsVO) {
        DoubleKeyMap<String, String, ReWriteSubject> tableCode2FieldCode2ReWriteSubject = this.getTableCode2FieldCode2ReWriteSubject(paramsVO);
        this.appendPrimaryWPSetMap(paramsVO, tableCode2FieldCode2ReWriteSubject);
        QueryParamCache queryParamCache = new QueryParamCache();
        DebugZbInfoVO debugZb = paramsVO.getDebugZb();
        if (null != debugZb) {
            ReWriteSubject reWriteSubject = (ReWriteSubject)tableCode2FieldCode2ReWriteSubject.get((Object)debugZb.calcTableName(), (Object)debugZb.calcZbCode());
            debugZb.setReWriteSubject(reWriteSubject);
        }
        queryParamCache.setTableCode2FieldCode2ReWriteSubjectMap(tableCode2FieldCode2ReWriteSubject);
        return queryParamCache;
    }

    private DoubleKeyMap<String, String, ReWriteSubject> appendPrimaryWPSetMap(GcActionParamsVO paramsVO, DoubleKeyMap<String, String, ReWriteSubject> tableCode2FieldCode2ReWriteSubject) {
        String reportSystemId = this.taskService.getConsolidatedSystemIdBySchemeId(paramsVO.getSchemeId(), paramsVO.getPeriodStr());
        List primaryWorkPaperSettingList = this.primaryWorkpaperService.listSetRecordsBySystemId(reportSystemId);
        for (PrimaryWorkPaperSettingEO workPaperSettingEO : primaryWorkPaperSettingList) {
            if (StringUtils.isEmpty((CharSequence)workPaperSettingEO.getBoundZbId())) continue;
            try {
                String subjectCodeStr;
                List deployInfos = this.runtimeDataSchemeService.getDeployInfoByDataFieldKeys(new String[]{workPaperSettingEO.getBoundZbId()});
                if (deployInfos.size() != 1 || StringUtils.isEmpty((CharSequence)(subjectCodeStr = workPaperSettingEO.getBoundSubjectCodes()))) continue;
                String[] subjectCodeArr = subjectCodeStr.split(";");
                HashSet<String> subjectCodeSet = new HashSet<String>(16);
                HashSet<String> originSubjectCodeSet = new HashSet<String>(16);
                for (String subjectCode : subjectCodeArr) {
                    originSubjectCodeSet.add(subjectCode);
                    subjectCodeSet.add(subjectCode);
                    subjectCodeSet.addAll(this.consolidatedSubjectService.listAllChildrenCodes(subjectCode, reportSystemId));
                }
                DataFieldDeployInfo dataFieldDeployInfo = (DataFieldDeployInfo)deployInfos.get(0);
                ArrayKey zbCode = new ArrayKey(new Object[]{dataFieldDeployInfo.getTableName(), dataFieldDeployInfo.getFieldName()});
                ReWriteSubject wpSetting = new ReWriteSubject(reportSystemId, zbCode, workPaperSettingEO.getOrient(), originSubjectCodeSet);
                wpSetting.setSubjectCodeSet(subjectCodeSet);
                tableCode2FieldCode2ReWriteSubject.put((Object)dataFieldDeployInfo.getTableName(), (Object)dataFieldDeployInfo.getFieldName(), (Object)wpSetting);
            }
            catch (Exception e) {
                this.logger.warn("\u67e5\u627e\u6307\u6807\u5931\u8d25", e);
            }
        }
        return tableCode2FieldCode2ReWriteSubject;
    }

    private void checkUnitFormState(TaskLog taskLog, GcActionParamsVO paramsVO, String diffUnitId) {
        List<String> lockedFormIdList = OneKeyMergeUtils.getLockedForm(paramsVO.getSchemeId(), diffUnitId, paramsVO);
        String lockTitle = lockedFormIdList.stream().map(lockedFormId -> {
            FormDefine formDefine = this.runtimeViewController.queryFormById(lockedFormId);
            return formDefine.getTitle();
        }).collect(Collectors.joining("\uff0c"));
        if (StringUtils.isNotEmpty((CharSequence)lockTitle)) {
            if (null == paramsVO.getDebugZb()) {
                taskLog.writeWarnLog("\u5dee\u989d\u5355\u4f4d\u4ee5\u4e0b\u8868\u4e3a\u5df2\u9501\u5b9a\u6216\u4e0a\u62a5\u72b6\u6001\uff0c\u8df3\u8fc7\u56de\u5199\uff1a" + lockTitle, Float.valueOf(taskLog.getProcessPercent()), Integer.valueOf(100));
            } else {
                paramsVO.getDebugZb().writeMessage("\u5dee\u989d\u5355\u4f4d\u4ee5\u4e0b\u8868\u4e3a\u5df2\u9501\u5b9a\u6216\u4e0a\u62a5\u72b6\u6001\uff0c\u8df3\u8fc7\u56de\u5199\uff1a" + lockTitle);
            }
        }
    }

    private DoubleKeyMap<String, String, ReWriteSubject> getTableCode2FieldCode2ReWriteSubject(GcActionParamsVO paramsVO) {
        String reportSystemId = this.taskService.getConsolidatedSystemIdBySchemeId(paramsVO.getSchemeId(), paramsVO.getPeriodStr());
        List subjectEOList = this.consolidatedSubjectService.listAllSubjectsBySystemId(reportSystemId);
        DoubleKeyMap tableCode2FieldCodeReWriteSubject = new DoubleKeyMap();
        HashMap<String, Map<String, DataFieldDeployInfo>> tableName2DataFieldKey2DeployInfoMap = new HashMap<String, Map<String, DataFieldDeployInfo>>();
        HashMap<String, Map<String, String>> tableName2DataFieldKey2FieldCodeMap = new HashMap<String, Map<String, String>>();
        for (ConsolidatedSubjectEO subject : subjectEOList) {
            Map dataFieldKey2FieldCodeMap;
            if (StringUtils.isEmpty((CharSequence)subject.getBoundIndexPath())) continue;
            ArrayKey zbCode = NrUtils.parseZbCode(subject.getBoundIndexPath());
            if (zbCode == null) {
                this.logger.error(String.format("\u79d1\u76ee\u4ee3\u7801\u4e3a\u3010%1s\u3011\u7684\u5173\u8054\u6307\u6807\u3010%2s\u3011\u8bbe\u7f6e\u5f02\u5e38", subject.getCode(), subject.getBoundIndexPath()));
                continue;
            }
            ReWriteSubject reWriteSubject = new ReWriteSubject(reportSystemId, zbCode, subject.getOrient(), subject.getCode());
            String tableName = (String)zbCode.get(0);
            String fieldCode = (String)zbCode.get(1);
            if (!tableName2DataFieldKey2DeployInfoMap.containsKey(tableName)) {
                DataTable dataTable = this.runtimeDataSchemeService.getDataTableByCode(tableName);
                if (dataTable == null) {
                    this.logger.error(String.format("\u6570\u636e\u65b9\u6848\u4e2d\u672a\u67e5\u8be2\u5230\u8868\u3010%1s\u3011", tableName));
                    continue;
                }
                List deployInfoList = this.runtimeDataSchemeService.getDeployInfoByDataTableKey(dataTable.getKey());
                List dataFields = this.runtimeDataSchemeService.getDataFieldByTableCode(tableName);
                Map<String, String> dataFieldCode2FieldKeyMap = dataFields.stream().collect(Collectors.toMap(Basic::getCode, Basic::getKey, (v1, v2) -> v1));
                tableName2DataFieldKey2FieldCodeMap.put(tableName, dataFieldCode2FieldKeyMap);
                Map<String, DataFieldDeployInfo> dataFieldKey2DeployInfo = deployInfoList.stream().collect(Collectors.toMap(DataFieldDeployInfo::getDataFieldKey, e1 -> e1, (v1, v2) -> v1));
                tableName2DataFieldKey2DeployInfoMap.put(tableName, dataFieldKey2DeployInfo);
            }
            if ((dataFieldKey2FieldCodeMap = (Map)tableName2DataFieldKey2FieldCodeMap.get(tableName)) == null || !dataFieldKey2FieldCodeMap.containsKey(fieldCode)) {
                this.logger.error(String.format("\u5728\u5b58\u50a8\u8868\u3010%1s\u3011\u4e2d\u672a\u67e5\u8be2\u5230code\u4e3a\u3010%2s\u3011\u7684\u6307\u6807", tableName, fieldCode));
                continue;
            }
            String fieldKey = (String)((Map)tableName2DataFieldKey2FieldCodeMap.get(tableName)).get(fieldCode);
            Map field2TableName = (Map)tableName2DataFieldKey2DeployInfoMap.get(tableName);
            DataFieldDeployInfo deployInfo = (DataFieldDeployInfo)field2TableName.get(fieldKey);
            if (deployInfo == null) {
                this.logger.error(String.format("\u5728\u5b58\u50a8\u8868\u3010%1s\u3011\u4e2d\u672a\u67e5\u8be2\u5230code\u4e3a\u3010%2s\u3011,Id\u4e3a\u3010%3s\u3011\u7684\u6307\u6807", tableName, fieldCode, fieldKey));
                continue;
            }
            tableCode2FieldCodeReWriteSubject.put((Object)deployInfo.getTableName(), (Object)deployInfo.getFieldName(), (Object)reWriteSubject);
        }
        return tableCode2FieldCodeReWriteSubject;
    }

    public DebugZbInfoVO debugZbReWrite(GcActionParamsVO paramsVO, String zbCode) {
        ArrayKey zbCodeArrayKey = NrUtils.parseZbCode(zbCode);
        if (null == zbCodeArrayKey) {
            return null;
        }
        paramsVO.setDebugZb(new DebugZbInfoVO(zbCodeArrayKey));
        GcOrgCacheVO org = OrgUtils.getCurrentUnit(paramsVO);
        List<String> hbUnitIds = Collections.singletonList(org.getId());
        Assert.isNotEmpty((String)org.getDiffUnitId(), (String)"\u5dee\u989d\u5355\u4f4d\u67e5\u8be2\u5931\u8d25", (Object[])new Object[0]);
        List<String> diffUnitIds = Collections.singletonList(org.getDiffUnitId());
        TaskLog taskLog = new TaskLog();
        this.doTask(paramsVO, hbUnitIds, diffUnitIds, taskLog);
        return paramsVO.getDebugZb();
    }
}

