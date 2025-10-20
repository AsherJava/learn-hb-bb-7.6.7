/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.ConverterUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.carryover.vo.CarryOverOrgInfo
 *  com.jiuqi.gcreport.carryover.vo.QueryParamsVO
 *  com.jiuqi.gcreport.common.task.common.TaskPeriodUtils
 *  com.jiuqi.gcreport.common.task.vo.Scheme
 *  com.jiuqi.gcreport.common.task.web.TaskConditionBoxController
 *  com.jiuqi.gcreport.common.util.NrTool
 *  com.jiuqi.gcreport.consolidatedsystem.entity.ConsolidatedSystemEO
 *  com.jiuqi.gcreport.consolidatedsystem.service.ConsolidatedSystemService
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 *  com.jiuqi.gcreport.i18n.util.GcI18nUtil
 *  com.jiuqi.gcreport.offsetitem.vo.DesignFieldDefineVO
 *  com.jiuqi.gcreport.offsetitem.vo.Pagination
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 */
package com.jiuqi.gcreport.carryover.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.carryover.dao.CarryOverLogDao;
import com.jiuqi.gcreport.carryover.dao.CarryOverTaskRelDao;
import com.jiuqi.gcreport.carryover.entity.CarryOverLogEO;
import com.jiuqi.gcreport.carryover.service.GcCarryOverLogService;
import com.jiuqi.gcreport.carryover.service.GcCarryOverProcessService;
import com.jiuqi.gcreport.carryover.service.GcCarryOverService;
import com.jiuqi.gcreport.carryover.service.GcCarryOverTaskService;
import com.jiuqi.gcreport.carryover.task.GcCarryOverTaskExecutor;
import com.jiuqi.gcreport.carryover.task.factory.CarryOverTaskExecutorFactory;
import com.jiuqi.gcreport.carryover.utils.CarryOverUtil;
import com.jiuqi.gcreport.carryover.vo.CarryOverOrgInfo;
import com.jiuqi.gcreport.carryover.vo.QueryParamsVO;
import com.jiuqi.gcreport.common.task.common.TaskPeriodUtils;
import com.jiuqi.gcreport.common.task.vo.Scheme;
import com.jiuqi.gcreport.common.task.web.TaskConditionBoxController;
import com.jiuqi.gcreport.common.util.NrTool;
import com.jiuqi.gcreport.consolidatedsystem.entity.ConsolidatedSystemEO;
import com.jiuqi.gcreport.consolidatedsystem.service.ConsolidatedSystemService;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import com.jiuqi.gcreport.i18n.util.GcI18nUtil;
import com.jiuqi.gcreport.offsetitem.vo.DesignFieldDefineVO;
import com.jiuqi.gcreport.offsetitem.vo.Pagination;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
public class GcCarryOverServiceImpl
implements GcCarryOverService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private IRunTimeViewController iRunTimeViewController;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private ConsolidatedSystemService consolidatedSystemService;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private GcCarryOverLogService gcCarryOverLogService;
    @Autowired
    private CarryOverLogDao carryOverLogDao;
    @Autowired
    private CarryOverTaskExecutorFactory taskExecutorFactory;
    @Autowired
    private GcCarryOverProcessService carryOverProcessService;
    @Autowired
    private GcCarryOverTaskService carryOverTaskService;
    @Autowired
    private CarryOverTaskRelDao carryOverTaskRelDao;

    @Override
    public void doCarryOver(QueryParamsVO queryParamsVO) {
        try {
            GcCarryOverTaskExecutor taskExecutor = this.taskExecutorFactory.createTask(queryParamsVO.getTypeCode());
            taskExecutor.checkParam(queryParamsVO);
            this.carryOverProcessService.createTaskProcess(queryParamsVO);
            this.carryOverTaskService.createAsyncTask(taskExecutor, queryParamsVO);
            this.carryOverTaskService.publishAsyncTask(taskExecutor, queryParamsVO, NpContextHolder.getContext());
        }
        catch (Exception e) {
            this.logger.error(e.getMessage(), e);
            throw new BusinessRuntimeException("\u6267\u884c\u5931\u8d25:" + e.getMessage());
        }
    }

    @Override
    public Map<String, String> getOrgVerAndType(String schemeId, String defaultAcctYear) {
        FormSchemeDefine formScheme = this.iRunTimeViewController.getFormScheme(schemeId);
        if (formScheme == null) {
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.carryover.not.form.scheme.message"));
        }
        String defaultPeriod = defaultAcctYear + (char)formScheme.getPeriodType().code() + "0000";
        String orgVer = CarryOverUtil.getQueryOrgPeriod(defaultPeriod);
        HashMap<String, String> orgVerMap = new HashMap<String, String>();
        orgVerMap.put("orgVer", orgVer);
        TableModelDefine designTableDefine = this.entityMetaService.getTableModel(formScheme.getDw());
        orgVerMap.put("orgType", designTableDefine.getName());
        return orgVerMap;
    }

    @Override
    public Scheme getSchemeByTaskKeyAndAcctYear(String taskKey, Integer acctYear) {
        try {
            List formSchemeDefines = this.runTimeViewController.queryFormSchemeByTask(taskKey);
            for (FormSchemeDefine schemeDefine : formSchemeDefines) {
                int lastPeriodNum = TaskPeriodUtils.getPeriodNum((int)schemeDefine.getPeriodType().type());
                String lastPeriod = acctYear + String.valueOf((char)schemeDefine.getPeriodType().code()) + "00" + (lastPeriodNum < 10 ? "0" + lastPeriodNum : String.valueOf(lastPeriodNum));
                List schemePeriodLinkDefines = ((IRunTimeViewController)SpringContextUtils.getBean(IRunTimeViewController.class)).querySchemePeriodLinkByScheme(schemeDefine.getKey());
                if (CollectionUtils.isEmpty((Collection)schemePeriodLinkDefines)) {
                    return null;
                }
                Optional<SchemePeriodLinkDefine> optional = schemePeriodLinkDefines.stream().filter(periodDefine -> periodDefine.getPeriodKey().contains(lastPeriod)).findAny();
                if (!optional.isPresent()) continue;
                return ((TaskConditionBoxController)SpringContextUtils.getBean(TaskConditionBoxController.class)).convertSchemeDefinToScheme(schemeDefine);
            }
        }
        catch (Exception e) {
            this.logger.error(e.getMessage(), e);
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.carryover.not.form.scheme.error") + e.getMessage());
        }
        return null;
    }

    @Override
    public List<DesignFieldDefineVO> listCarryOverSumColumns() {
        String GcOffSetVchrItemInitTableName = "GC_OFFSETVCHRITEM_INIT";
        List columnModelDefines = NrTool.queryAllColumnsInTable((String)GcOffSetVchrItemInitTableName);
        String[] notSelectColumnCodes = new String[]{"ID", "RECVER", "MRECID", "ACCTYEAR", "SRCOFFSETGROUPID", "SYSTEMID", "OFFSET_DEBIT", "OFFSET_CREDIT", "DIFFD", "DIFFC", "MEMO", "MODIFYTIME", "SORTORDER", "CREATETIME", "SRCID", "GCBUSINESSTYPECODE", "OFFSETCURR", "SUBJECTORIENT", "DISABLEFLAG", "ELMMODE"};
        Set selectColumnModelDefineSet = Arrays.stream(notSelectColumnCodes).collect(Collectors.toSet());
        List columnModelDefineList = columnModelDefines.stream().filter(column -> !selectColumnModelDefineSet.contains(column.getCode())).collect(Collectors.toList());
        return columnModelDefineList.stream().map(columnModelDefine -> this.convertEO2VO((ColumnModelDefine)columnModelDefine)).collect(Collectors.toList());
    }

    @Override
    public Pagination<Map<String, Object>> listCarryOverLogInfo(QueryParamsVO queryParamsVO) {
        Pagination<Map<String, Object>> pagination = this.gcCarryOverLogService.listLogInfo(queryParamsVO);
        List listLogInfo = pagination.getContent();
        if (CollectionUtils.isEmpty((Collection)listLogInfo)) {
            return pagination;
        }
        Map<String, String> taskKey2TitleMap = this.runTimeViewController.getAllTaskDefines().stream().filter(eo -> !StringUtils.isEmpty((String)eo.getTitle())).collect(Collectors.toMap(IBaseMetaItem::getKey, IBaseMetaItem::getTitle));
        Map<String, String> systemId2TitleMap = this.consolidatedSystemService.getConsolidatedSystemEOS().stream().filter(eo -> !StringUtils.isEmpty((String)eo.getSystemName())).collect(Collectors.toMap(DefaultTableEntity::getId, ConsolidatedSystemEO::getSystemName));
        ArrayList<String> logIds = new ArrayList<String>();
        listLogInfo.stream().forEach(logInfo -> {
            String targetSystemId;
            logIds.add(ConverterUtils.getAsString(logInfo.get("ID")));
            List orgs = (List)JsonUtils.readValue((String)ConverterUtils.getAsString(logInfo.get("UNITINFO")), (TypeReference)new TypeReference<List<CarryOverOrgInfo>>(){});
            String unitTitles = orgs.stream().map(CarryOverOrgInfo::getTitle).collect(Collectors.joining(","));
            logInfo.put("UNITTITLE", unitTitles);
            String taskId = ConverterUtils.getAsString(logInfo.get("TASKID"));
            if (!StringUtils.isEmpty((String)taskId)) {
                logInfo.put("TASKTITLE", taskKey2TitleMap.get(taskId));
            }
            if (!StringUtils.isEmpty((String)(targetSystemId = ConverterUtils.getAsString(logInfo.get("TARGETSYSTEMID"))))) {
                logInfo.put("TARGETSYSTEMTITLE", systemId2TitleMap.get(targetSystemId));
            }
            logInfo.remove("INFO");
        });
        Map<String, Map<String, Object>> map = this.gcCarryOverLogService.listLogExtendInfoByIds(logIds);
        if (ObjectUtils.isEmpty(map)) {
            pagination.setContent(listLogInfo);
            return pagination;
        }
        listLogInfo.stream().forEach(logInfo -> {
            String logId = ConverterUtils.getAsString(logInfo.get("ID"));
            Map extendInfoMap = (Map)map.get(logId);
            if (!ObjectUtils.isEmpty(extendInfoMap)) {
                logInfo.putAll(extendInfoMap);
            }
        });
        pagination.setContent(listLogInfo);
        return pagination;
    }

    @Override
    public Map<String, Object> getTaskProcess(String taskLogId) {
        HashSet<String> ids = new HashSet<String>();
        ids.add(taskLogId);
        CarryOverLogEO eo = this.carryOverLogDao.listByIds(ids).get(0);
        Map fields = eo.getFields();
        try {
            String targetSystemId;
            List orgs = (List)JsonUtils.readValue((String)eo.getUnitInfo(), (TypeReference)new TypeReference<List<CarryOverOrgInfo>>(){});
            String unitTitles = ((CarryOverOrgInfo)orgs.get(0)).getTitle();
            fields.put("UNITTITLE", unitTitles);
            String taskId = eo.getTaskId();
            if (!StringUtils.isEmpty((String)taskId)) {
                fields.put("TASKTITLE", this.runTimeViewController.queryTaskDefine(taskId).getTitle());
            }
            if (!StringUtils.isEmpty((String)(targetSystemId = eo.getTargetSystemId()))) {
                fields.put("TARGETSYSTEMTITLE", this.consolidatedSystemService.getConsolidatedSystemEO(targetSystemId).getSystemName());
            }
        }
        catch (Exception e) {
            this.logger.error(e.getMessage(), e);
        }
        fields.remove("INFO");
        Map<String, Map<String, Object>> map = this.gcCarryOverLogService.listLogExtendInfoByIds(Collections.singletonList(taskLogId));
        if (!ObjectUtils.isEmpty(map)) {
            Map<String, Object> extendInfo = map.get(taskLogId);
            fields.putAll(extendInfo);
        }
        return fields;
    }

    private DesignFieldDefineVO convertEO2VO(ColumnModelDefine columnModelDefine) {
        DesignFieldDefineVO designFieldDefineVO = new DesignFieldDefineVO();
        designFieldDefineVO.setKey(columnModelDefine.getCode());
        String title = StringUtils.isEmpty((String)columnModelDefine.getTitle()) ? columnModelDefine.getCode() : columnModelDefine.getTitle();
        designFieldDefineVO.setLabel(title);
        designFieldDefineVO.setType(columnModelDefine.getColumnType());
        return designFieldDefineVO;
    }
}

