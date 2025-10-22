/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.ArrayKey
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.ConverterUtils
 *  com.jiuqi.common.base.util.DateUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.basedata.impl.bean.GcBaseData
 *  com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool
 *  com.jiuqi.gcreport.carryover.service.GcCarryOverConfigService
 *  com.jiuqi.gcreport.carryover.service.GcCarryOverProcessService
 *  com.jiuqi.gcreport.carryover.task.AbstractTaskLog
 *  com.jiuqi.gcreport.carryover.utils.CarryOverUtil
 *  com.jiuqi.gcreport.carryover.vo.QueryParamsVO
 *  com.jiuqi.gcreport.common.GCOrgTypeEnum
 *  com.jiuqi.gcreport.common.OrientEnum
 *  com.jiuqi.gcreport.common.util.DimensionUtils
 *  com.jiuqi.gcreport.common.util.UUIDOrderSnowUtils
 *  com.jiuqi.gcreport.common.util.UUIDOrderUtils
 *  com.jiuqi.gcreport.consolidatedsystem.entity.ConsolidatedSystemEO
 *  com.jiuqi.gcreport.consolidatedsystem.entity.subject.ConsolidatedSubjectEO
 *  com.jiuqi.gcreport.consolidatedsystem.service.ConsolidatedSystemService
 *  com.jiuqi.gcreport.consolidatedsystem.service.Formula.ConsolidatedFormulaService
 *  com.jiuqi.gcreport.consolidatedsystem.service.subject.ConsolidatedSubjectService
 *  com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService
 *  com.jiuqi.gcreport.consolidatedsystem.util.ConsolidatedSystemUtils
 *  com.jiuqi.gcreport.consolidatedsystem.vo.formula.ConsolidatedFormulaVO
 *  com.jiuqi.gcreport.consolidatedsystem.vo.task.ConsolidatedTaskVO
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 *  com.jiuqi.gcreport.i18n.util.GcI18nUtil
 *  com.jiuqi.gcreport.nr.impl.util.GcOrgTypeUtils
 *  com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrItemDTO
 *  com.jiuqi.gcreport.offsetitem.dto.OffsetVchrCodeDTO
 *  com.jiuqi.gcreport.offsetitem.dto.QueryParamsDTO
 *  com.jiuqi.gcreport.offsetitem.entity.GcOffSetVchrItemAdjustEO
 *  com.jiuqi.gcreport.offsetitem.enums.EFFECTTYPE
 *  com.jiuqi.gcreport.offsetitem.enums.OffSetSrcTypeEnum
 *  com.jiuqi.gcreport.offsetitem.enums.OffsetElmModeEnum
 *  com.jiuqi.gcreport.offsetitem.init.carryover.vo.CarryOverOffsetConfigVO
 *  com.jiuqi.gcreport.offsetitem.init.carryover.vo.CarryOverOffsetRuleVO
 *  com.jiuqi.gcreport.offsetitem.init.carryover.vo.CarryOverOffsetSubjectVO
 *  com.jiuqi.gcreport.offsetitem.inputdata.service.GcInputDataOffsetItemService
 *  com.jiuqi.gcreport.offsetitem.service.GcInputAdjustService
 *  com.jiuqi.gcreport.offsetitem.service.GcOffSetItemAdjustCoreService
 *  com.jiuqi.gcreport.offsetitem.util.OffsetVchrCodeUtil
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.enums.GcOrgKindEnum
 *  com.jiuqi.gcreport.org.api.period.YearPeriodDO
 *  com.jiuqi.gcreport.org.api.period.YearPeriodUtil
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  com.jiuqi.gcreport.temp.dto.TaskLog
 *  com.jiuqi.gcreport.unionrule.dao.UnionRuleDao
 *  com.jiuqi.gcreport.unionrule.dto.FlexibleRuleDTO
 *  com.jiuqi.gcreport.unionrule.entity.UnionRuleEO
 *  com.jiuqi.gcreport.unionrule.enums.RuleTypeEnum
 *  com.jiuqi.gcreport.unionrule.service.UnionRuleService
 *  com.jiuqi.gcreport.unionrule.vo.BaseRuleVO
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.nr.definition.api.IParamLanguageController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.TaskDefine
 */
package com.jiuqi.gcreport.offsetitem.init.carryover.service.impl;

import com.jiuqi.bi.util.ArrayKey;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.common.base.util.DateUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.basedata.impl.bean.GcBaseData;
import com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool;
import com.jiuqi.gcreport.carryover.service.GcCarryOverConfigService;
import com.jiuqi.gcreport.carryover.service.GcCarryOverProcessService;
import com.jiuqi.gcreport.carryover.task.AbstractTaskLog;
import com.jiuqi.gcreport.carryover.utils.CarryOverUtil;
import com.jiuqi.gcreport.carryover.vo.QueryParamsVO;
import com.jiuqi.gcreport.common.GCOrgTypeEnum;
import com.jiuqi.gcreport.common.OrientEnum;
import com.jiuqi.gcreport.common.util.DimensionUtils;
import com.jiuqi.gcreport.common.util.UUIDOrderSnowUtils;
import com.jiuqi.gcreport.common.util.UUIDOrderUtils;
import com.jiuqi.gcreport.consolidatedsystem.entity.ConsolidatedSystemEO;
import com.jiuqi.gcreport.consolidatedsystem.entity.subject.ConsolidatedSubjectEO;
import com.jiuqi.gcreport.consolidatedsystem.service.ConsolidatedSystemService;
import com.jiuqi.gcreport.consolidatedsystem.service.Formula.ConsolidatedFormulaService;
import com.jiuqi.gcreport.consolidatedsystem.service.subject.ConsolidatedSubjectService;
import com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService;
import com.jiuqi.gcreport.consolidatedsystem.util.ConsolidatedSystemUtils;
import com.jiuqi.gcreport.consolidatedsystem.vo.formula.ConsolidatedFormulaVO;
import com.jiuqi.gcreport.consolidatedsystem.vo.task.ConsolidatedTaskVO;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import com.jiuqi.gcreport.i18n.util.GcI18nUtil;
import com.jiuqi.gcreport.nr.impl.util.GcOrgTypeUtils;
import com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrItemDTO;
import com.jiuqi.gcreport.offsetitem.dto.OffsetVchrCodeDTO;
import com.jiuqi.gcreport.offsetitem.dto.QueryParamsDTO;
import com.jiuqi.gcreport.offsetitem.entity.GcOffSetVchrItemAdjustEO;
import com.jiuqi.gcreport.offsetitem.enums.EFFECTTYPE;
import com.jiuqi.gcreport.offsetitem.enums.OffSetSrcTypeEnum;
import com.jiuqi.gcreport.offsetitem.enums.OffsetElmModeEnum;
import com.jiuqi.gcreport.offsetitem.init.carryover.dao.GcCarryOverOffsetDao;
import com.jiuqi.gcreport.offsetitem.init.carryover.enums.GcCarryOverInvestTypeEnum;
import com.jiuqi.gcreport.offsetitem.init.carryover.service.GcCarryOverInvestService;
import com.jiuqi.gcreport.offsetitem.init.carryover.service.GcCarryOverOffsetTaskService;
import com.jiuqi.gcreport.offsetitem.init.carryover.vo.CarryOverOffsetConfigVO;
import com.jiuqi.gcreport.offsetitem.init.carryover.vo.CarryOverOffsetRuleVO;
import com.jiuqi.gcreport.offsetitem.init.carryover.vo.CarryOverOffsetSubjectVO;
import com.jiuqi.gcreport.offsetitem.init.dao.GcOffSetVchrItemInitDao;
import com.jiuqi.gcreport.offsetitem.init.entity.GcOffSetVchrItemInitEO;
import com.jiuqi.gcreport.offsetitem.inputdata.service.GcInputDataOffsetItemService;
import com.jiuqi.gcreport.offsetitem.service.GcInputAdjustService;
import com.jiuqi.gcreport.offsetitem.service.GcOffSetItemAdjustCoreService;
import com.jiuqi.gcreport.offsetitem.util.OffsetVchrCodeUtil;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.enums.GcOrgKindEnum;
import com.jiuqi.gcreport.org.api.period.YearPeriodDO;
import com.jiuqi.gcreport.org.api.period.YearPeriodUtil;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.gcreport.temp.dto.TaskLog;
import com.jiuqi.gcreport.unionrule.dao.UnionRuleDao;
import com.jiuqi.gcreport.unionrule.dto.FlexibleRuleDTO;
import com.jiuqi.gcreport.unionrule.entity.UnionRuleEO;
import com.jiuqi.gcreport.unionrule.enums.RuleTypeEnum;
import com.jiuqi.gcreport.unionrule.service.UnionRuleService;
import com.jiuqi.gcreport.unionrule.vo.BaseRuleVO;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.nr.definition.api.IParamLanguageController;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.TaskDefine;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
public class GcCarryOverOffsetOffsetTaskImpl
extends AbstractTaskLog
implements GcCarryOverOffsetTaskService {
    @Autowired
    private GcOffSetItemAdjustCoreService coreService;
    @Autowired
    private GcCarryOverOffsetDao carryOverDao;
    @Autowired
    private GcCarryOverInvestService gcCarryOverInvestService;
    @Autowired
    private ConsolidatedTaskService consolidatedTaskService;
    @Autowired
    private ConsolidatedSystemService consolidatedSystemService;
    @Autowired
    private ConsolidatedSubjectService consolidatedSubjectService;
    @Autowired
    private UnionRuleService ruleService;
    @Autowired
    private GcOffSetVchrItemInitDao offSetVchrItemInitDao;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private UnionRuleService unionRuleService;
    @Autowired
    private ConsolidatedFormulaService consolidatedFormulaService;
    @Autowired
    private GcInputAdjustService gcInputAdjustService;
    @Autowired
    private GcInputDataOffsetItemService gcInputDataOffsetItemService;
    @Autowired
    private GcCarryOverConfigService gcCarryOverConfigService;
    @Autowired
    private GcCarryOverProcessService carryOverProcessService;
    @Autowired
    private IParamLanguageController languageController;
    private ThreadLocal<CarryOverOffsetConfigVO> optionVoLocal = new ThreadLocal();
    private ThreadLocal<Map<String, String>> curr2TargetSystemRuleIdMap = new ThreadLocal();
    private ThreadLocal<Map<String, String>> carryOverSubjectCodeMapping = new ThreadLocal();
    private ThreadLocal<Map<String, ConsolidatedSubjectEO>> currSystemSubjectCode2Subject = new ThreadLocal();
    private ThreadLocal<List<String>> carryOverSubjectCodes = new ThreadLocal();
    private ThreadLocal<Set<String>> targetSystemSubjectCodes = new ThreadLocal();
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final int carryOverSrcTypeValue = OffSetSrcTypeEnum.CARRY_OVER.getSrcTypeValue();
    private final int carryOverPubSrcTypeValue = OffSetSrcTypeEnum.CARRY_OVER_FAIRVALUE.getSrcTypeValue();
    private Set<Integer> initOffsetSrcTypeSet = this.initOffsetSrcType();
    private Set<Integer> offSetSrcTypes = this.getOffSetSrcTypes();

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public TaskLog doTask(QueryParamsVO queryParamsVO, AsyncTaskMonitor monitor) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            this.initTaskLog(queryParamsVO);
            this.checkQueryParamsVO(queryParamsVO);
            this.setSubTaskFullPercent(Float.valueOf(1.0f));
            this.setSubTaskFullWeight(10);
            try {
                this.copyOffsetEntryModule(queryParamsVO, monitor);
            }
            catch (Exception e) {
                this.logError(e.getMessage(), Float.valueOf(0.95f));
                this.logInfo(GcI18nUtil.getMessage((String)"gc.offset.carryover.endTime", (Object[])new Object[]{formatter.format(DateUtils.now())}), Float.valueOf(0.99f));
                this.logger.error(e.getMessage(), e);
            }
            TaskLog taskLog = this.carryOverProcessService.getTaskLog(queryParamsVO.getTaskLogId() + "_" + ((GcOrgCacheVO)queryParamsVO.getOrgList().get(0)).getCode());
            String result = JsonUtils.writeValueAsString((Object)taskLog.getMessages());
            monitor.finish(null, (Object)result);
            TaskLog taskLog2 = taskLog;
            return taskLog2;
        }
        finally {
            this.finish();
            this.removeThreadLocal();
        }
    }

    private void checkQueryParamsVO(QueryParamsVO queryParamsVO) {
        String periodStr = CarryOverUtil.convertPeriod((String)queryParamsVO.getPeriodStr(), (int)queryParamsVO.getAcctYear(), (int)queryParamsVO.getPeriodType());
        ConsolidatedTaskVO taskVO = this.consolidatedTaskService.getTaskByTaskKeyAndPeriodStr(queryParamsVO.getTaskId(), periodStr);
        if (taskVO == null) {
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.carryover.not.system.id.message"));
        }
        YearPeriodObject yp = new YearPeriodObject(null, periodStr);
        GcOrgCenterService tool = GcOrgPublicTool.getInstance((String)queryParamsVO.getOrgType(), (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
        queryParamsVO.setOrgList(queryParamsVO.getOrgList().stream().map(org -> tool.getOrgByCode(org.getCode())).filter(org -> GcOrgKindEnum.UNIONORG.equals((Object)org.getOrgKind())).collect(Collectors.toList()));
        queryParamsVO.setTaskVO(taskVO);
        if (DimensionUtils.isExistAdjust((String)queryParamsVO.getTaskId()) && StringUtils.isEmpty((String)queryParamsVO.getSelectAdjustCode())) {
            queryParamsVO.setSelectAdjustCode("0");
        }
    }

    private void copyOffsetEntryModule(QueryParamsVO queryParamsVO, AsyncTaskMonitor monitor) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date beginTime = DateUtils.now();
        GcOrgCacheVO orgCacheVO = (GcOrgCacheVO)queryParamsVO.getOrgList().get(0);
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(queryParamsVO.getTaskId());
        monitor.progressAndMessage(0.1, GcI18nUtil.getMessage((String)"gc.offset.carryover.startTime", (Object[])new Object[]{formatter.format(beginTime)}));
        this.logInfo(GcI18nUtil.getMessage((String)"gc.offset.carryover.startTime", (Object[])new Object[]{formatter.format(beginTime)}), Float.valueOf(0.1f));
        String taskTitle = StringUtils.isEmpty((String)this.languageController.getTaskTitle(taskDefine.getKey(), null)) ? taskDefine.getTitle() : this.languageController.getTaskTitle(taskDefine.getKey(), null);
        monitor.progressAndMessage(0.15, GcI18nUtil.getMessage((String)"gc.offset.carryover.message", (Object[])new Object[]{taskTitle, ConverterUtils.getAsString((Object)(queryParamsVO.getAcctYear() - 1))}));
        this.logInfo(GcI18nUtil.getMessage((String)"gc.offset.carryover.message", (Object[])new Object[]{orgCacheVO.getTitle(), taskTitle, ConverterUtils.getAsString((Object)(queryParamsVO.getAcctYear() - 1))}), new Float(0.15f));
        this.loadOption(queryParamsVO);
        String srcOrgType = queryParamsVO.getOrgType();
        ConsolidatedSystemEO targetSystem = this.consolidatedSystemService.getConsolidatedSystemEO(queryParamsVO.getSystemId());
        String currSystemId = targetSystem.getId();
        UnionRuleDao unionRuleDao = (UnionRuleDao)SpringContextUtils.getBean(UnionRuleDao.class);
        List unionRules = unionRuleDao.findAllRuleListByReportSystem(targetSystem.getId());
        Map<String, UnionRuleEO> ruleId2UnionRuleMap = unionRules.stream().collect(Collectors.toMap(DefaultTableEntity::getId, item -> item, (v1, v2) -> v1));
        ArrayList<GcOffSetVchrItemAdjustEO> inputAdjustNextRecords = new ArrayList<GcOffSetVchrItemAdjustEO>(256);
        ArrayList<GcOffSetVchrItemAdjustEO> inputAdjustCurrRecords = new ArrayList<GcOffSetVchrItemAdjustEO>(256);
        ArrayList<GcOffSetVchrItemAdjustEO> initOffsetRecords = new ArrayList<GcOffSetVchrItemAdjustEO>(256);
        Map<String, List<GcOffSetVchrItemAdjustEO>> ruleId2OffsetItemMap = this.initRule2OffsetEoMap();
        ArrayList<GcOffSetVchrItemAdjustEO> otherRecords = new ArrayList<GcOffSetVchrItemAdjustEO>(256);
        String orgType = this.checkManageOrg(queryParamsVO);
        boolean isManageOrg = orgType.equals(srcOrgType);
        HashMap<String, String> key2Message = new HashMap<String, String>(16);
        this.initRange(queryParamsVO, ruleId2OffsetItemMap, inputAdjustNextRecords, inputAdjustCurrRecords, initOffsetRecords, otherRecords, key2Message);
        queryParamsVO.setOrgList(Collections.singletonList(orgCacheVO));
        queryParamsVO.setOrgType(srcOrgType);
        GcOrgTypeUtils.setContextEntityId((String)srcOrgType);
        HashSet<String> inputDataRuleIds = new HashSet<String>(16);
        HashSet<String> inventoryRuleIds = new HashSet<String>(16);
        HashSet<String> investRuleIds = new HashSet<String>(16);
        HashSet<String> publicValueRuleIds = new HashSet<String>(16);
        this.initRuleIdRange(queryParamsVO, inputDataRuleIds, inventoryRuleIds, investRuleIds, publicValueRuleIds);
        String periodStr = CarryOverUtil.convertPeriod((String)queryParamsVO.getPeriodStr(), (int)queryParamsVO.getAcctYear(), (int)queryParamsVO.getPeriodType());
        int count = this.offSetVchrItemInitDao.deleteCarryOverByMergeCodes(queryParamsVO.getAcctYear(), queryParamsVO.getConsSystemId(), queryParamsVO.getOrgList(), periodStr, srcOrgType);
        this.logger.info("\u62b5\u9500\u5206\u5f55\u5e74\u7ed3\uff0c\u5220\u9664\u4e86" + count + "\u884c\u62b5\u9500\u5206\u5f55");
        List<ConsolidatedFormulaVO> consolidatedFormulaVOList = this.getConsFormulaCalc(currSystemId);
        int[] num = new int[]{0, 0};
        monitor.progressAndMessage(0.2, "\u7b26\u5408\u5e74\u7ed3\u89c4\u5219\u7684\u5206\u5f55\u6267\u884c\u5e74\u7ed3");
        List<String> sumRuleIds = this.optionVoLocal.get().getCarryOverSumRuleIds().stream().map(CarryOverOffsetRuleVO::getId).collect(Collectors.toList());
        for (String ruleId : ruleId2OffsetItemMap.keySet()) {
            List<GcOffSetVchrItemAdjustEO> gcOffSetVchrItemAdjustEOS;
            if (!isManageOrg && !sumRuleIds.contains(ruleId) || CollectionUtils.isEmpty(gcOffSetVchrItemAdjustEOS = ruleId2OffsetItemMap.get(ruleId))) continue;
            Set<String> srcIds = gcOffSetVchrItemAdjustEOS.stream().map(GcOffSetVchrItemAdjustEO::getSrcOffsetGroupId).collect(Collectors.toSet());
            Map<Object, Object> srcId2IdMap = investRuleIds.contains(ruleId) ? this.gcCarryOverInvestService.getSrcId2IdMap(srcIds, GcCarryOverInvestTypeEnum.INVESTMENT, queryParamsVO.getAcctYear() - 1) : (publicValueRuleIds.contains(ruleId) ? this.gcCarryOverInvestService.getSrcId2IdMap(srcIds, GcCarryOverInvestTypeEnum.PUBLIC_ADJUSTMENT, queryParamsVO.getAcctYear() - 1) : new HashMap());
            this.addNum(num, this.copyOffsetEntryInRules(queryParamsVO, ruleId, this.formulaCalc(gcOffSetVchrItemAdjustEOS, consolidatedFormulaVOList), ruleId2UnionRuleMap, srcId2IdMap, sumRuleIds));
        }
        if (isManageOrg) {
            monitor.progressAndMessage(0.4, "\u8f93\u5165\u8c03\u6574\u4e0d\u5f71\u54cd\u4e0b\u5e74\u7684\u5206\u5f55\u6267\u884c\u5e74\u7ed3");
            if (this.optionVoLocal.get().getCarryOverConformRuleAdjustOffsets().booleanValue()) {
                this.addNum(num, this.copyInputAdjustCurrOffsetEntry(queryParamsVO, this.formulaCalc(inputAdjustCurrRecords, consolidatedFormulaVOList), currSystemId));
            }
            monitor.progressAndMessage(0.6, "\u8f93\u5165\u8c03\u6574\u5f71\u54cd\u4e0b\u5e74\u7684\u5206\u5f55\u6267\u884c\u5e74\u7ed3");
            this.addNum(num, this.copyInputAdjustNextOffsetEntry(queryParamsVO, this.formulaCalc(inputAdjustNextRecords, consolidatedFormulaVOList), currSystemId));
        }
        monitor.progressAndMessage(0.8, "\u521d\u59cb\u5206\u5f55\uff08\u5f71\u54cd\u4e0b\u5e74\uff09\u7684\u5206\u5f55\u6267\u884c\u5e74\u7ed3");
        this.addNum(num, this.copyInitOffsetEntry(queryParamsVO, this.formulaCalc(initOffsetRecords, consolidatedFormulaVOList), ruleId2UnionRuleMap));
        if (key2Message.containsKey("SUBJECTLOGWARN")) {
            this.logWarn((String)key2Message.get("SUBJECTLOGWARN"), Float.valueOf(0.95f));
        }
        this.logInfo(GcI18nUtil.getMessage((String)"gc.offset.carryover.all.finish", (Object[])new Object[]{orgCacheVO.getTitle(), taskTitle, ConverterUtils.getAsString((Object)(queryParamsVO.getAcctYear() - 1)), key2Message.get("RECORDCOUNT"), num[1]}), Float.valueOf(0.95f));
        Date endTime = DateUtils.now();
        this.saveLogHelperInfo(beginTime, endTime, taskDefine, queryParamsVO, targetSystem);
        this.logInfo(GcI18nUtil.getMessage((String)"gc.offset.carryover.endTime", (Object[])new Object[]{formatter.format(endTime)}), Float.valueOf(0.99f));
    }

    private String checkManageOrg(QueryParamsVO queryParamsVO) {
        GcOrgCacheVO orgCacheVO = (GcOrgCacheVO)queryParamsVO.getOrgList().get(0);
        String orgType = queryParamsVO.getOrgType();
        CarryOverOffsetConfigVO optionVO = this.optionVoLocal.get();
        List defaultSumColumns = Arrays.asList("UNITID", "OPPUNITID", "SUBJECTCODE", "ORIENT", "RULEID");
        List<String> unitSumColumns = Arrays.asList("UNITID", "OPPUNITID");
        List carryOverSumColumns = CollectionUtils.isEmpty((Collection)optionVO.getCarryOverSumColumns()) ? defaultSumColumns : optionVO.getCarryOverSumColumns();
        boolean containUnit = carryOverSumColumns.containsAll(unitSumColumns);
        if (containUnit) {
            return orgType;
        }
        List baseDataList = GcBaseDataCenterTool.getInstance().queryBasedataItems("MD_GCGLKJDXFLNJ");
        if (CollectionUtils.isEmpty((Collection)baseDataList)) {
            return orgType;
        }
        Map<String, GcBaseData> map = baseDataList.stream().collect(Collectors.toMap(a -> ConverterUtils.getAsString((Object)a.getFieldVal("GLKJHBDW")), a -> a));
        if (!map.containsKey(orgCacheVO.getCode())) {
            return orgType;
        }
        GcBaseData baseData = map.get(orgCacheVO.getCode());
        String dyhbdwlx = ConverterUtils.getAsString((Object)baseData.getFieldVal("DYHBDWLX"));
        YearPeriodDO period = YearPeriodUtil.transform(null, (String)queryParamsVO.getPeriodStr());
        GcOrgCenterService orgTool = GcOrgPublicTool.getInstance((String)dyhbdwlx, (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodDO)period);
        GcOrgCacheVO manageOrg = orgTool.getOrgByCode(orgCacheVO.getCode());
        queryParamsVO.setOrgList(Collections.singletonList(manageOrg));
        queryParamsVO.setOrgType(dyhbdwlx);
        return dyhbdwlx;
    }

    private void removeThreadLocal() {
        this.optionVoLocal.remove();
        this.curr2TargetSystemRuleIdMap.remove();
        this.carryOverSubjectCodes.remove();
        this.currSystemSubjectCode2Subject.remove();
        this.carryOverSubjectCodes.remove();
        this.targetSystemSubjectCodes.remove();
    }

    private void addNum(int[] oldNum, int[] newNum) {
        oldNum[0] = oldNum[0] + newNum[0];
        oldNum[1] = oldNum[1] + newNum[1];
    }

    private void saveLogHelperInfo(Date beginTime, Date endTime, TaskDefine taskDefine, QueryParamsVO queryParamsVO, ConsolidatedSystemEO targetSystem) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String orgInfo = queryParamsVO.getOrgList().stream().map(org -> org.getCode() + "|" + org.getTitle()).collect(Collectors.joining(","));
        String logMessage = String.format("\u5f00\u59cb\u65f6\u95f4\uff1a%s\uff1b\n\u7ed3\u675f\u65f6\u95f4\uff1a%s\uff1b\n\u5e74\u7ed3\u4efb\u52a1\uff1a%s\uff1b\n\u5e74\u7ed3\u5e74\u5ea6\uff1a%d\u5e74\uff1b\n\u76ee\u6807\u4f53\u7cfb\uff1a%s\uff1b\n\u5e74\u7ed3\u5355\u4f4d\uff1a%s\uff1b", formatter.format(beginTime), formatter.format(endTime), taskDefine.getTitle(), queryParamsVO.getAcctYear() - 1, targetSystem.getSystemName(), orgInfo);
        if (DimensionUtils.isExistAdjust((String)queryParamsVO.getTaskId())) {
            logMessage = String.format("\u5f00\u59cb\u65f6\u95f4\uff1a%s\uff1b\n\u7ed3\u675f\u65f6\u95f4\uff1a%s\uff1b\n\u5e74\u7ed3\u4efb\u52a1\uff1a%s\uff1b\n\u5e74\u7ed3\u5e74\u5ea6\uff1a%d\u5e74\uff1b\u8c03\u6574\u671f\uff1a%s \n\u76ee\u6807\u4f53\u7cfb\uff1a%s\uff1b\n\u5e74\u7ed3\u5355\u4f4d\uff1a%s\uff1b", formatter.format(beginTime), formatter.format(endTime), taskDefine.getTitle(), queryParamsVO.getAcctYear() - 1, queryParamsVO.getSelectAdjustCode(), targetSystem.getSystemName(), orgInfo);
        }
        LogHelper.info((String)"\u5408\u5e76-\u62b5\u9500\u5206\u5f55\u5e74\u7ed3", (String)String.format("\u5e74\u7ed3-\u4efb\u52a1%1$s-\u5e74\u5ea6%2$d", taskDefine.getTitle(), queryParamsVO.getAcctYear() - 1), (String)logMessage);
    }

    private List<GcOffSetVchrItemAdjustEO> formulaCalc(List<GcOffSetVchrItemAdjustEO> gcOffSetVchrItemAdjustEOList, List<ConsolidatedFormulaVO> consolidatedFormulaVOList) {
        ArrayList<GcOffSetVchrItemAdjustEO> gcOffSetVchrItemAdjustEOS = new ArrayList<GcOffSetVchrItemAdjustEO>(256);
        gcOffSetVchrItemAdjustEOList.forEach(gcOffSetVchrItemAdjustEO -> {
            GcOffSetVchrItemDTO gcOffSetVchrItemDTO = this.convertEOToOffsetDTO((GcOffSetVchrItemAdjustEO)gcOffSetVchrItemAdjustEO);
            this.gcInputAdjustService.exeConsFormulaCalcSingle(gcOffSetVchrItemDTO, consolidatedFormulaVOList, null);
            GcOffSetVchrItemAdjustEO offsetEO = new GcOffSetVchrItemAdjustEO();
            BeanUtils.copyProperties(gcOffSetVchrItemDTO, offsetEO);
            offsetEO.setDisableFlag(Integer.valueOf(Objects.equals(Boolean.TRUE, gcOffSetVchrItemDTO.getDisableFlag()) ? 1 : 0));
            offsetEO.setSubjectOrient(gcOffSetVchrItemDTO.getOrient().getValue());
            offsetEO.setOffSetSrcType(Integer.valueOf(gcOffSetVchrItemDTO.getOffSetSrcType().getSrcTypeValue()));
            Map unSysField = gcOffSetVchrItemDTO.getUnSysFields();
            unSysField.forEach((key, value) -> {
                gcOffSetVchrItemDTO.getFields().put(key, gcOffSetVchrItemDTO.getUnSysFieldValue(key));
                offsetEO.getFields().put(key, gcOffSetVchrItemDTO.getUnSysFieldValue(key));
            });
            gcOffSetVchrItemAdjustEOS.add(offsetEO);
        });
        return gcOffSetVchrItemAdjustEOS;
    }

    private GcOffSetVchrItemDTO convertEOToOffsetDTO(GcOffSetVchrItemAdjustEO gcOffSetVchrItemAdjustEO) {
        GcOffSetVchrItemDTO gcOffSetVchrItemDTO = new GcOffSetVchrItemDTO();
        BeanUtils.copyProperties(gcOffSetVchrItemAdjustEO, gcOffSetVchrItemDTO);
        gcOffSetVchrItemDTO.setDisableFlag(Boolean.valueOf(Objects.equals(1, gcOffSetVchrItemAdjustEO.getDisableFlag())));
        gcOffSetVchrItemDTO.setUnSysFields(gcOffSetVchrItemAdjustEO.getFields());
        gcOffSetVchrItemDTO.setOrient(OrientEnum.valueOf((Integer)gcOffSetVchrItemAdjustEO.getOrient()));
        gcOffSetVchrItemDTO.setOffSetSrcType(OffSetSrcTypeEnum.getEnumByValue((int)gcOffSetVchrItemAdjustEO.getOffSetSrcType()));
        return gcOffSetVchrItemDTO;
    }

    private List<ConsolidatedFormulaVO> getConsFormulaCalc(String systemId) {
        List consolidatedFormulaVOS = this.consolidatedFormulaService.listConsFormulas(systemId);
        return consolidatedFormulaVOS.stream().filter(vo -> Boolean.TRUE.equals(vo.getCarryOver())).collect(Collectors.toList());
    }

    private int[] copyInputAdjustCurrOffsetEntry(QueryParamsVO queryParamsVO, List<GcOffSetVchrItemAdjustEO> records, String currSystemId) {
        HashSet<String> containSubjects = new HashSet<String>();
        return this.copyInputAdjustOffsetEntry(queryParamsVO, records, true, GcI18nUtil.getMessage((String)"gc.offset.carryover.adjust.notAffectingNextYear"), containSubjects);
    }

    private int[] copyInputAdjustNextOffsetEntry(QueryParamsVO queryParamsVO, List<GcOffSetVchrItemAdjustEO> records, String currSystemId) {
        HashSet<String> containSubjects = new HashSet<String>();
        return this.copyInputAdjustOffsetEntry(queryParamsVO, records, false, GcI18nUtil.getMessage((String)"gc.offset.carryover.adjust.affectingNextYear"), containSubjects);
    }

    private int[] copyInputAdjustOffsetEntry(QueryParamsVO queryParamsVO, List<GcOffSetVchrItemAdjustEO> inputAdjustRecords, boolean needFilterRule, String typeStr, Set<String> containSubjects) {
        this.logInfo(GcI18nUtil.getMessage((String)"gc.offset.typeStr.carryover.start", (Object[])new Object[]{typeStr}), this.plusWeight(0));
        CarryOverOffsetConfigVO optionVO = this.optionVoLocal.get();
        boolean conformRuleAdjustOffsets = optionVO.getCarryOverConformRuleAdjustOffsets();
        Set mrecids = inputAdjustRecords.stream().map(GcOffSetVchrItemAdjustEO::getmRecid).collect(Collectors.toSet());
        Map<String, List<GcOffSetVchrItemAdjustEO>> mRecid2recordsMap = inputAdjustRecords.stream().collect(Collectors.groupingBy(GcOffSetVchrItemAdjustEO::getmRecid));
        HashSet tempMrecids = new HashSet(mrecids);
        HashMap<String, String> oldMrecids2newMrecids = new HashMap<String, String>(mrecids.size());
        for (String mrecid : mrecids) {
            oldMrecids2newMrecids.put(mrecid, UUIDOrderUtils.newUUIDStr());
        }
        int successNum = 0;
        int failNum = 0;
        for (String mRecid : mRecid2recordsMap.keySet()) {
            ArrayList<GcOffSetVchrItemInitEO> oneGroup = new ArrayList<GcOffSetVchrItemInitEO>(16);
            for (GcOffSetVchrItemAdjustEO adjustEo : mRecid2recordsMap.get(mRecid)) {
                GcOffSetVchrItemInitEO newAdjustEo = this.initOffsetRecord(adjustEo, queryParamsVO);
                newAdjustEo.setmRecid((String)oldMrecids2newMrecids.get(adjustEo.getmRecid()));
                String currMemo = this.getCurrMemo(queryParamsVO, adjustEo, false);
                newAdjustEo.setMemo(currMemo);
                oneGroup.add(newAdjustEo);
            }
            StringBuilder errorStr = new StringBuilder();
            int num = this.flushOffsetEntry(oneGroup, queryParamsVO, false, needFilterRule, false, errorStr, containSubjects);
            if (num > 0) {
                ++successNum;
                continue;
            }
            ++failNum;
            this.logErrorByOffsetItemList(mRecid2recordsMap.get(mRecid), errorStr);
        }
        this.logInfo(GcI18nUtil.getMessage((String)"gc.offset.typeStr.carryover.finish", (Object[])new Object[]{typeStr, mRecid2recordsMap.size(), successNum, failNum}), this.plusWeight(0));
        return new int[]{mRecid2recordsMap.size(), successNum};
    }

    private void logErrorByOffsetItemList(List<GcOffSetVchrItemAdjustEO> records, StringBuilder errorStr) {
        records.forEach(record -> this.logger.info("\u62b5\u9500\u5206\u5f55\u5e74\u7ed3\u5931\u8d25\uff0c\u5931\u8d25\u539f\u56e0\u3010{}\u3011\uff0c\u5931\u8d25\u8be6\u7ec6\u4fe1\u606f\uff1aid\u3010{}\u3011,mRecId\u3010{}\u3011,\u79d1\u76ee\u7f16\u7801\u3010{}\u3011,\u5408\u5e76\u4e1a\u52a1\u7c7b\u578b\u3010{}\u3011,\u672c\u65b9\u5355\u4f4d\u3010{}\u3011,\u5bf9\u65b9\u5355\u4f4d\u3010{}\u3011,\u62b5\u9500\u65b9\u5f0f\u3010{}\u3011,\u89c4\u5219Id\u3010{}\u3011,\u8d37\u65b9\u91d1\u989d\u3010{}\u3011,\u501f\u65b9\u91d1\u989d\u3010{}\u3011", errorStr.toString(), record.getId(), record.getmRecid(), record.getSubjectCode(), record.getGcBusinessTypeCode(), record.getUnitId(), record.getOppUnitId(), OffSetSrcTypeEnum.getNameByValue((int)record.getOffSetSrcType()), record.getRuleId(), record.getOffSetCredit(), record.getOffSetDebit()));
    }

    private int[] copyOffsetEntryInRules(QueryParamsVO queryParamsVO, String ruleId, List<GcOffSetVchrItemAdjustEO> records, Map<String, UnionRuleEO> ruleId2UnionRuleMap, Map<String, String> srcId2IdMap, List<String> sumRuleIds) {
        UnionRuleEO rule = ruleId2UnionRuleMap.get(ruleId);
        HashSet<String> containSubjects = new HashSet<String>();
        return this.doCopyOffsetEntry(records, srcId2IdMap, queryParamsVO, true, false, ruleId2UnionRuleMap, rule == null ? "" : rule.getTitle(), rule == null ? "" : rule.getTitle(), containSubjects, sumRuleIds);
    }

    private int[] copyInitOffsetEntry(QueryParamsVO queryParamsVO, List<GcOffSetVchrItemAdjustEO> records, Map<String, UnionRuleEO> ruleId2UnionRuleMap) {
        Map<Integer, List<GcOffSetVchrItemAdjustEO>> srcType2OffsetMap = records.stream().collect(Collectors.groupingBy(GcOffSetVchrItemAdjustEO::getOffSetSrcType));
        HashMap<String, String> srcId2IdMap = new HashMap<String, String>();
        for (Integer srcType : srcType2OffsetMap.keySet()) {
            Set<String> srcIds = srcType2OffsetMap.get(srcType).stream().map(GcOffSetVchrItemAdjustEO::getSrcOffsetGroupId).collect(Collectors.toSet());
            Map<Object, Object> map = OffSetSrcTypeEnum.FAIRVALUE_ADJUST_ITEM_INIT.getSrcTypeValue() == srcType.intValue() ? this.gcCarryOverInvestService.getSrcId2IdMap(srcIds, GcCarryOverInvestTypeEnum.PUBLIC_ADJUSTMENT, queryParamsVO.getAcctYear() - 1) : (OffSetSrcTypeEnum.INVEST_OFFSET_ITEM_INIT.getSrcTypeValue() == srcType.intValue() ? this.gcCarryOverInvestService.getSrcId2IdMap(srcIds, GcCarryOverInvestTypeEnum.INVESTMENT, queryParamsVO.getAcctYear() - 1) : new HashMap());
            if (ObjectUtils.isEmpty(map)) continue;
            srcId2IdMap.putAll(map);
        }
        HashSet<String> containSubjects = new HashSet<String>();
        return this.doCopyOffsetEntry(records, srcId2IdMap, false, queryParamsVO, ruleId2UnionRuleMap, "\u5206\u5f55\u521d\u59cb", GcI18nUtil.getMessage((String)"gc.offset.carryover.offset.init.affectingNextYear"), containSubjects, null);
    }

    private int[] doCopyOffsetEntry(List<GcOffSetVchrItemAdjustEO> offsetRecords, Map<String, String> srcId2IdMap, boolean needSum, QueryParamsVO queryParamsVO, Map<String, UnionRuleEO> ruleId2UnionRuleMap, String memo, String typeStr, Set<String> containSubjects, List<String> sumRuleIds) {
        return this.doCopyOffsetEntry(offsetRecords, srcId2IdMap, queryParamsVO, needSum, false, ruleId2UnionRuleMap, memo, typeStr, containSubjects, sumRuleIds);
    }

    private int[] doCopyOffsetEntry(List<GcOffSetVchrItemAdjustEO> offsetRecords, Map<String, String> srcId2IdMap, QueryParamsVO queryParamsVO, boolean needSum, boolean needMoreOneChangeSubject, Map<String, UnionRuleEO> ruleId2UnionRuleMap, String memo, String typeStr, Set<String> containSubjects, List<String> sumRuleIds) {
        this.logInfo(GcI18nUtil.getMessage((String)"gc.offset.typeStr.carryover.start", (Object[])new Object[]{typeStr}), this.plusWeight(0));
        ArrayList<GcOffSetVchrItemAdjustEO> sourceRecordList = new ArrayList<GcOffSetVchrItemAdjustEO>(offsetRecords);
        if (needSum) {
            this.sumOffsetRecords(offsetRecords, ruleId2UnionRuleMap, queryParamsVO);
        }
        HashMap oldMrecids2newMrecids = new HashMap(128);
        Map<String, List<GcOffSetVchrItemAdjustEO>> mRecid2recordsMap = offsetRecords.stream().collect(Collectors.groupingBy(GcOffSetVchrItemAdjustEO::getmRecid));
        int successNum = 0;
        int failNum = 0;
        for (String mRecid : mRecid2recordsMap.keySet()) {
            ArrayList<GcOffSetVchrItemInitEO> oneGroup = new ArrayList<GcOffSetVchrItemInitEO>(16);
            for (GcOffSetVchrItemAdjustEO adjustEo : mRecid2recordsMap.get(mRecid)) {
                String oldMrecid = adjustEo.getmRecid();
                GcOffSetVchrItemInitEO newAdjustEo = this.initOffsetRecord(adjustEo, queryParamsVO);
                newAdjustEo.setmRecid((String)oldMrecids2newMrecids.get(adjustEo.getmRecid()));
                boolean isSumRule = !ObjectUtils.isEmpty(sumRuleIds) && sumRuleIds.contains(adjustEo.getRuleId());
                String currMemo = this.getCurrMemo(queryParamsVO, adjustEo, needSum && isSumRule);
                if (ObjectUtils.isEmpty(srcId2IdMap) || !srcId2IdMap.containsKey(adjustEo.getSrcOffsetGroupId())) {
                    newAdjustEo.setSrcOffsetGroupId(oldMrecid);
                } else {
                    newAdjustEo.setSrcOffsetGroupId(srcId2IdMap.get(adjustEo.getSrcOffsetGroupId()));
                }
                newAdjustEo.setMemo(currMemo);
                oneGroup.add(newAdjustEo);
            }
            StringBuilder errorStr = new StringBuilder();
            int num = this.flushOffsetEntry(oneGroup, queryParamsVO, needMoreOneChangeSubject, needSum, errorStr, containSubjects);
            if (num > 0) {
                ++successNum;
                continue;
            }
            ++failNum;
            Object sourceList = mRecid2recordsMap.get(mRecid).get(0).getFields().get("SOURCE_ID");
            List<Object> sourceFilterList = needSum && sourceList != null ? sourceRecordList.stream().filter(record -> ((List)sourceList).contains(record.getId())).collect(Collectors.toList()) : mRecid2recordsMap.get(mRecid);
            this.logErrorByOffsetItemList(sourceFilterList, errorStr);
        }
        this.logInfo(GcI18nUtil.getMessage((String)"gc.offset.typeStr.carryover.end", (Object[])new Object[]{typeStr, mRecid2recordsMap.size(), successNum, failNum}), this.plusWeight(0));
        return new int[]{mRecid2recordsMap.size(), successNum};
    }

    private String getCurrMemo(QueryParamsVO queryParamsVO, GcOffSetVchrItemAdjustEO adjustEo, boolean needSum) {
        String offsetType;
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(adjustEo.getTaskId());
        String srcMemo = StringUtils.isEmpty((String)adjustEo.getMemo()) ? "" : adjustEo.getMemo();
        String string = offsetType = this.offSetSrcTypes.contains(adjustEo.getOffSetSrcType()) ? "\u5e74\u521d\u521d\u59cb\u5316" : OffsetElmModeEnum.getElmModeTitle((Integer)adjustEo.getElmMode());
        if (needSum) {
            return this.getNeedSumCurrMemo(srcMemo, queryParamsVO, adjustEo, taskDefine, offsetType);
        }
        return this.getNotNeedSumCurrMemo(srcMemo, queryParamsVO, adjustEo, taskDefine, offsetType);
    }

    private String getNeedSumCurrMemo(String srcMemo, QueryParamsVO queryParamsVO, GcOffSetVchrItemAdjustEO adjustEo, TaskDefine taskDefine, String offsetType) {
        String currMemo = !srcMemo.contains("\u5e74\u672b\u7ed3\u8f6c") ? String.format("%1$s\u5e74%2$s\u5e74\u672b\u7ed3\u8f6c(%3$s)", adjustEo.getDefaultPeriod().substring(0, 4), taskDefine.getTitle(), offsetType) : queryParamsVO.getPeriodStr().substring(0, 4) + taskDefine.getTitle();
        currMemo = currMemo + "[\u6c47\u603b]";
        return currMemo;
    }

    private String getNotNeedSumCurrMemo(String srcMemo, QueryParamsVO queryParamsVO, GcOffSetVchrItemAdjustEO adjustEo, TaskDefine taskDefine, String offsetType) {
        String currMemo = !srcMemo.contains("\u5e74\u672b\u7ed3\u8f6c") ? String.format("%1$s\u5e74%2$s\u5e74\u672b\u7ed3\u8f6c(%3$s)%4$s", adjustEo.getDefaultPeriod().substring(0, 4), taskDefine.getTitle(), offsetType, srcMemo) : (srcMemo.indexOf("\u5e74\u672b\u7ed3\u8f6c") > 6 ? queryParamsVO.getPeriodStr().substring(0, 4) + taskDefine.getTitle() + srcMemo.substring(4) : queryParamsVO.getPeriodStr().substring(0, 4) + taskDefine.getTitle() + "-" + srcMemo);
        return currMemo;
    }

    private void sumOffsetRecords(List<GcOffSetVchrItemAdjustEO> offsetRecords, Map<String, UnionRuleEO> ruleId2UnionRuleMap, QueryParamsVO queryParamsVO) {
        CarryOverOffsetConfigVO optionVO = this.optionVoLocal.get();
        Set<String> carryOverSumRuleIds = optionVO.getCarryOverSumRuleIds().stream().map(CarryOverOffsetRuleVO::getId).collect(Collectors.toSet());
        List<GcOffSetVchrItemAdjustEO> sumOffsetRecords = offsetRecords.stream().filter(item -> carryOverSumRuleIds.contains(item.getRuleId()) && item.getElmMode().intValue() != OffsetElmModeEnum.MANUAL_ITEM.getValue()).collect(Collectors.toList());
        offsetRecords.removeAll(sumOffsetRecords);
        List<GcOffSetVchrItemAdjustEO> manualOffsetRecords = offsetRecords.stream().filter(item -> item.getElmMode().intValue() == OffsetElmModeEnum.MANUAL_ITEM.getValue()).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(manualOffsetRecords)) {
            offsetRecords.removeAll(manualOffsetRecords);
            this.removeCrossRuleManualOffsetRecords(manualOffsetRecords, sumOffsetRecords, offsetRecords, carryOverSumRuleIds);
        }
        if (CollectionUtils.isEmpty(sumOffsetRecords)) {
            return;
        }
        List defaultSumColumns = Arrays.asList("UNITID", "OPPUNITID", "SUBJECTCODE", "ORIENT", "RULEID");
        List<String> unitSumColumns = Arrays.asList("UNITID", "OPPUNITID");
        List carryOverSumColumns = CollectionUtils.isEmpty((Collection)optionVO.getCarryOverSumColumns()) ? defaultSumColumns : optionVO.getCarryOverSumColumns();
        boolean containUnit = carryOverSumColumns.containsAll(unitSumColumns);
        Map<ArrayKey, List<GcOffSetVchrItemAdjustEO>> groupColumn2OffsetRecordsMap = sumOffsetRecords.stream().collect(Collectors.groupingBy(item -> {
            ArrayKey groupKey = new ArrayKey(new Object[]{item.getRuleId(), item.getElmMode(), item.getFetchSetGroupId()});
            if (!containUnit) {
                return groupKey;
            }
            int compareResult = item.getUnitId().compareTo(item.getOppUnitId());
            return compareResult > 0 ? groupKey.append((Object)item.getUnitId()).append((Object)item.getOppUnitId()) : groupKey.append((Object)item.getOppUnitId()).append((Object)item.getUnitId());
        }));
        for (List<GcOffSetVchrItemAdjustEO> offSetVchrItems : groupColumn2OffsetRecordsMap.values()) {
            List<GcOffSetVchrItemAdjustEO> newVchrItemAdjustEOS = this.listSumOffsetRecords(offSetVchrItems, queryParamsVO);
            String ruleId = offSetVchrItems.get(0).getRuleId();
            boolean needSplit = this.isNeedSplitByDc(ruleId2UnionRuleMap.get(ruleId));
            if (needSplit) {
                this.splitOffsetRecords(offsetRecords, newVchrItemAdjustEOS);
                continue;
            }
            offsetRecords.addAll(newVchrItemAdjustEOS);
        }
    }

    private void splitOffsetRecords(List<GcOffSetVchrItemAdjustEO> offsetRecords, List<GcOffSetVchrItemAdjustEO> newVchrItemAdjustEOS) {
        Map<ArrayKey, List<GcOffSetVchrItemAdjustEO>> unitAndOrient2OffsetRecordsMap = newVchrItemAdjustEOS.stream().collect(Collectors.groupingBy(item -> new ArrayKey(new Object[]{item.getUnitId(), item.getOppUnitId(), item.getOrient()})));
        ArrayKey firstGroupDebitKey = unitAndOrient2OffsetRecordsMap.keySet().iterator().next();
        ArrayKey firstGroupCreditKey = new ArrayKey(new Object[]{firstGroupDebitKey.get(1), firstGroupDebitKey.get(0), -1 * (Integer)firstGroupDebitKey.get(2)});
        boolean isEqualFirstGroup = this.isEqualForOffsetEntry(firstGroupDebitKey, firstGroupCreditKey, unitAndOrient2OffsetRecordsMap);
        ArrayKey secondGroupDebitKey = new ArrayKey(new Object[]{firstGroupDebitKey.get(1), firstGroupDebitKey.get(0), firstGroupDebitKey.get(2)});
        ArrayKey secondGroupCreditKey = new ArrayKey(new Object[]{firstGroupDebitKey.get(0), firstGroupDebitKey.get(1), -1 * (Integer)firstGroupDebitKey.get(2)});
        boolean isEqualSecondGroup = this.isEqualForOffsetEntry(secondGroupDebitKey, secondGroupCreditKey, unitAndOrient2OffsetRecordsMap);
        if (isEqualFirstGroup && isEqualSecondGroup) {
            this.addNewRecordsGroup(offsetRecords, unitAndOrient2OffsetRecordsMap, firstGroupDebitKey, firstGroupCreditKey);
        } else {
            offsetRecords.addAll(newVchrItemAdjustEOS);
        }
    }

    private void addNewRecordsGroup(List<GcOffSetVchrItemAdjustEO> offsetRecords, Map<ArrayKey, List<GcOffSetVchrItemAdjustEO>> unitAndOrient2OffsetRecordsMap, ArrayKey firstGroupDebitKey, ArrayKey firstGroupCreditKey) {
        ArrayList newOffsetRecords = new ArrayList();
        newOffsetRecords.addAll(unitAndOrient2OffsetRecordsMap.get(firstGroupDebitKey));
        newOffsetRecords.addAll(unitAndOrient2OffsetRecordsMap.get(firstGroupCreditKey));
        newOffsetRecords.forEach(record -> record.setmRecid(UUIDOrderUtils.newUUIDStr()));
        offsetRecords.addAll(newOffsetRecords);
    }

    private boolean isEqualForOffsetEntry(ArrayKey debitKey, ArrayKey creditKey, Map<ArrayKey, List<GcOffSetVchrItemAdjustEO>> unitAndOrient2OffsetRecordsMap) {
        List<GcOffSetVchrItemAdjustEO> debitRecords = unitAndOrient2OffsetRecordsMap.get(debitKey);
        if (CollectionUtils.isEmpty(debitRecords)) {
            return false;
        }
        BigDecimal sumDebitValue = debitRecords.stream().map(item -> new BigDecimal((Integer)debitKey.get(2)).multiply(new BigDecimal(item.getOffSetDebit()).subtract(new BigDecimal(item.getOffSetCredit())))).reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
        List<GcOffSetVchrItemAdjustEO> creditRecords = unitAndOrient2OffsetRecordsMap.get(creditKey);
        if (CollectionUtils.isEmpty(creditRecords)) {
            return false;
        }
        BigDecimal sumCreditValue = creditRecords.stream().map(item -> new BigDecimal((Integer)creditKey.get(2)).multiply(new BigDecimal(item.getOffSetDebit()).subtract(new BigDecimal(item.getOffSetCredit())))).reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
        return sumDebitValue.compareTo(sumCreditValue) == 0;
    }

    private List<GcOffSetVchrItemInitEO> listSumOffsetRecordsByOrient(List<GcOffSetVchrItemInitEO> offSetVchrItemAdjustEOS) {
        Map<String, List<GcOffSetVchrItemInitEO>> subjectCode2OffsetRecordsMap = offSetVchrItemAdjustEOS.stream().collect(Collectors.groupingBy(GcOffSetVchrItemInitEO::getSubjectCode));
        List<String> defaultSumColumns = Collections.singletonList("ORIENT");
        ArrayList<GcOffSetVchrItemInitEO> newVchrItemAdjustEOS = new ArrayList<GcOffSetVchrItemInitEO>();
        String mrecid = UUIDOrderSnowUtils.newUUIDStr();
        subjectCode2OffsetRecordsMap.forEach((subjectCode, offsetRecordList) -> {
            if (offsetRecordList.size() <= 1) {
                offsetRecordList.forEach(record -> record.setmRecid(mrecid));
                newVchrItemAdjustEOS.addAll((Collection<GcOffSetVchrItemInitEO>)offsetRecordList);
            } else {
                Map<ArrayKey, List<GcOffSetVchrItemInitEO>> sumColumn2OffsetRecordsMap = this.groupOffsetInitRecordsBySumColumns(defaultSumColumns, (List<GcOffSetVchrItemInitEO>)offsetRecordList);
                for (List<GcOffSetVchrItemInitEO> offsetRecords : sumColumn2OffsetRecordsMap.values()) {
                    boolean isDebit = offsetRecords.get(0).getOrient() == 1;
                    BigDecimal dxjeSumValue = offsetRecords.stream().map(data -> new BigDecimal(String.valueOf(isDebit ? data.getOffSetDebit() : data.getOffSetCredit()))).reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
                    BigDecimal diffSumeValue = offsetRecords.stream().map(data -> new BigDecimal(String.valueOf(isDebit ? data.getDiffd() : data.getDiffc()))).reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
                    GcOffSetVchrItemInitEO newOffsetVchrItem = this.initOffsetInitVchrItem(defaultSumColumns, offsetRecords);
                    newOffsetVchrItem.setmRecid(mrecid);
                    if (isDebit) {
                        newOffsetVchrItem.setOffSetDebit(dxjeSumValue.doubleValue());
                        newOffsetVchrItem.setDiffd(diffSumeValue.doubleValue());
                    } else {
                        newOffsetVchrItem.setOffSetCredit(dxjeSumValue.doubleValue());
                        newOffsetVchrItem.setDiffc(diffSumeValue.doubleValue());
                    }
                    newVchrItemAdjustEOS.add(newOffsetVchrItem);
                }
            }
        });
        return newVchrItemAdjustEOS;
    }

    private List<GcOffSetVchrItemAdjustEO> listSumOffsetRecords(List<GcOffSetVchrItemAdjustEO> offSetVchrItemAdjustEOS, QueryParamsVO queryParamsVO) {
        YearPeriodDO period = YearPeriodUtil.transform(null, (String)queryParamsVO.getPeriodStr());
        GcOrgCenterService orgTool = GcOrgPublicTool.getInstance((String)queryParamsVO.getOrgType(), (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodDO)period);
        CarryOverOffsetConfigVO optionVO = this.optionVoLocal.get();
        List defaultSumColumns = Arrays.asList("UNITID", "OPPUNITID", "SUBJECTCODE", "ORIENT", "RULEID");
        List<String> unitSumColumns = Arrays.asList("UNITID", "OPPUNITID");
        List carryOverSumColumns = CollectionUtils.isEmpty((Collection)optionVO.getCarryOverSumColumns()) ? defaultSumColumns : optionVO.getCarryOverSumColumns();
        boolean containUnit = carryOverSumColumns.containsAll(unitSumColumns);
        Map<ArrayKey, List<GcOffSetVchrItemAdjustEO>> sumColumn2OffsetRecordsMap = this.groupOffsetRecordsBySumColumns(carryOverSumColumns, offSetVchrItemAdjustEOS);
        String vchrCode = this.createVchrCode(queryParamsVO);
        ArrayList<GcOffSetVchrItemAdjustEO> newVchrItemAdjustEOS = new ArrayList<GcOffSetVchrItemAdjustEO>();
        String mrecid = UUIDOrderSnowUtils.newUUIDStr();
        for (List<GcOffSetVchrItemAdjustEO> offsetRecords : sumColumn2OffsetRecordsMap.values()) {
            boolean isDebit = offsetRecords.get(0).getOrient() == 1;
            BigDecimal dxjeSumValue = offsetRecords.stream().map(data -> new BigDecimal(String.valueOf(isDebit ? data.getOffSetDebit() : data.getOffSetCredit()))).reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
            BigDecimal diffSumeValue = offsetRecords.stream().map(data -> new BigDecimal(String.valueOf(isDebit ? data.getDiffd() : data.getDiffc()))).reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
            GcOffSetVchrItemAdjustEO newOffsetVchrItem = this.initOffsetVchrItem(carryOverSumColumns, offsetRecords);
            newOffsetVchrItem.getFields().put("SOURCE_ID", offsetRecords.stream().map(DefaultTableEntity::getId).collect(Collectors.toList()));
            newOffsetVchrItem.setmRecid(mrecid);
            if (isDebit) {
                newOffsetVchrItem.setOffSetDebit(Double.valueOf(dxjeSumValue.doubleValue()));
                newOffsetVchrItem.setDiffd(Double.valueOf(diffSumeValue.doubleValue()));
            } else {
                newOffsetVchrItem.setOffSetCredit(Double.valueOf(dxjeSumValue.doubleValue()));
                newOffsetVchrItem.setDiffc(Double.valueOf(diffSumeValue.doubleValue()));
            }
            if (!containUnit) {
                GcOrgCacheVO org = orgTool.getOrgByCode(((GcOrgCacheVO)queryParamsVO.getOrgList().get(0)).getCode());
                newOffsetVchrItem.setUnitId(org.getCode());
                newOffsetVchrItem.setOppUnitId(org.getDiffUnitId());
            }
            newOffsetVchrItem.setVchrCode(vchrCode);
            newVchrItemAdjustEOS.add(newOffsetVchrItem);
        }
        return newVchrItemAdjustEOS;
    }

    private String createVchrCode(QueryParamsVO queryParamsVO) {
        OffsetVchrCodeDTO vchrCodeDTO = new OffsetVchrCodeDTO();
        vchrCodeDTO.setPeriodType(0);
        vchrCodeDTO.setAcctYear(queryParamsVO.getAcctYear().intValue());
        return OffsetVchrCodeUtil.createVchrCode((OffsetVchrCodeDTO)vchrCodeDTO);
    }

    private GcOffSetVchrItemInitEO initOffsetInitVchrItem(List<String> carryOverSumColumns, List<GcOffSetVchrItemInitEO> vchrItemAdjustEOS) {
        GcOffSetVchrItemInitEO newOffsetVchrItem = new GcOffSetVchrItemInitEO();
        BeanUtils.copyProperties((Object)vchrItemAdjustEOS.get(0), (Object)newOffsetVchrItem);
        newOffsetVchrItem.resetFields(vchrItemAdjustEOS.get(0).getFields());
        String[] notSelectColumnCodes = new String[]{"ID", "RECVER", "MRECID", "ACCTYEAR", "SRCOFFSETGROUPID", "SYSTEMID", "OFFSET_DEBIT", "OFFSET_CREDIT", "DIFFD", "DIFFC", "MEMO", "MODIFYTIME", "SORTORDER", "CREATETIME", "SRCID", "GCBUSINESSTYPECODE", "OFFSETCURR", "SUBJECTORIENT", "DISABLEFLAG", "ELMMODE", "RULEID"};
        Set selectColumnModelDefineSet = Arrays.stream(notSelectColumnCodes).collect(Collectors.toSet());
        selectColumnModelDefineSet.addAll(carryOverSumColumns);
        newOffsetVchrItem.getFields().keySet().stream().forEach(fieldKey -> {
            if (!selectColumnModelDefineSet.contains(fieldKey)) {
                newOffsetVchrItem.getFields().put(fieldKey, null);
            }
        });
        newOffsetVchrItem.setId(null);
        newOffsetVchrItem.setSrcOffsetGroupId(null);
        return newOffsetVchrItem;
    }

    private GcOffSetVchrItemAdjustEO initOffsetVchrItem(List<String> carryOverSumColumns, List<GcOffSetVchrItemAdjustEO> vchrItemAdjustEOS) {
        GcOffSetVchrItemAdjustEO newOffsetVchrItem = new GcOffSetVchrItemAdjustEO();
        BeanUtils.copyProperties(vchrItemAdjustEOS.get(0), newOffsetVchrItem);
        String[] notSelectColumnCodes = new String[]{"ID", "RECVER", "MRECID", "ACCTYEAR", "SRCOFFSETGROUPID", "SYSTEMID", "OFFSET_DEBIT", "OFFSET_CREDIT", "DIFFD", "DIFFC", "MEMO", "MODIFYTIME", "SORTORDER", "CREATETIME", "SRCID", "GCBUSINESSTYPECODE", "OFFSETCURR", "SUBJECTORIENT", "DISABLEFLAG", "ELMMODE"};
        Set selectColumnModelDefineSet = Arrays.stream(notSelectColumnCodes).collect(Collectors.toSet());
        selectColumnModelDefineSet.addAll(carryOverSumColumns);
        newOffsetVchrItem.getFields().keySet().stream().forEach(fieldKey -> {
            if (!selectColumnModelDefineSet.contains(fieldKey)) {
                newOffsetVchrItem.getFields().put(fieldKey, null);
            }
        });
        newOffsetVchrItem.setId(null);
        newOffsetVchrItem.setSrcOffsetGroupId(null);
        return newOffsetVchrItem;
    }

    private Map<ArrayKey, List<GcOffSetVchrItemInitEO>> groupOffsetInitRecordsBySumColumns(List<String> carryOverSumColumns, List<GcOffSetVchrItemInitEO> gcOffSetVchrItemInitEOList) {
        return gcOffSetVchrItemInitEOList.stream().collect(Collectors.groupingBy(item -> {
            ArrayKey sumGroupKey = new ArrayKey(new Object[0]);
            for (String carryOverSumColumn : carryOverSumColumns) {
                Object val = "ORIENT".equals(carryOverSumColumn) ? item.getOrient() : item.getFieldValue(carryOverSumColumn);
                sumGroupKey = sumGroupKey.append(val);
            }
            return sumGroupKey;
        }));
    }

    private Map<ArrayKey, List<GcOffSetVchrItemAdjustEO>> groupOffsetRecordsBySumColumns(List<String> carryOverSumColumns, List<GcOffSetVchrItemAdjustEO> offSetVchrItemAdjustEOS) {
        return offSetVchrItemAdjustEOS.stream().collect(Collectors.groupingBy(item -> {
            ArrayKey sumGroupKey = new ArrayKey(new Object[0]);
            for (String carryOverSumColumn : carryOverSumColumns) {
                Object val = "ORIENT".equals(carryOverSumColumn) ? item.getOrient() : item.getFieldValue(carryOverSumColumn);
                sumGroupKey = sumGroupKey.append(val);
            }
            return sumGroupKey;
        }));
    }

    private void removeCrossRuleManualOffsetRecords(List<GcOffSetVchrItemAdjustEO> manualOffsetRecords, List<GcOffSetVchrItemAdjustEO> sumOffsetRecords, List<GcOffSetVchrItemAdjustEO> offSetVchrItemAdjustEOS, Set<String> carryOverSumRuleIds) {
        Map<String, List<GcOffSetVchrItemAdjustEO>> mRecid2OffsetEntryMap = manualOffsetRecords.stream().collect(Collectors.groupingBy(item -> item.getmRecid()));
        for (List<GcOffSetVchrItemAdjustEO> offsetItems : mRecid2OffsetEntryMap.values()) {
            Set ruleIds = offsetItems.stream().map(item -> item.getRuleId()).collect(Collectors.toSet());
            if (ruleIds.size() > 1) {
                offSetVchrItemAdjustEOS.addAll(offsetItems);
                continue;
            }
            if (carryOverSumRuleIds.contains(offsetItems.get(0).getRuleId())) {
                sumOffsetRecords.addAll(offsetItems);
                continue;
            }
            offSetVchrItemAdjustEOS.addAll(offsetItems);
        }
    }

    private boolean isNeedSplitByDc(UnionRuleEO rule) {
        if (rule == null) {
            return false;
        }
        if (RuleTypeEnum.FLEXIBLE.getCode() == rule.getRuleType()) {
            FlexibleRuleDTO flexibleRuleDTO = new FlexibleRuleDTO();
            BeanUtils.copyProperties(rule, flexibleRuleDTO);
            List offsetGroupingField = flexibleRuleDTO.getOffsetGroupingField();
            return offsetGroupingField.contains("DC");
        }
        return true;
    }

    private void initRange(QueryParamsVO queryParamsVO, Map<String, List<GcOffSetVchrItemAdjustEO>> ruleId2OffsetItemMap, List<GcOffSetVchrItemAdjustEO> inputAdjustNextRecords, List<GcOffSetVchrItemAdjustEO> inputAdjustCurrRecords, List<GcOffSetVchrItemAdjustEO> initOffsetRecords, List<GcOffSetVchrItemAdjustEO> otherRecords, Map<String, String> key2Message) {
        ArrayList<String> orgTypeIds = new ArrayList<String>();
        orgTypeIds.add(queryParamsVO.getOrgType());
        orgTypeIds.add(GCOrgTypeEnum.NONE.getCode());
        ArrayList<String> schemeIds = new ArrayList<String>();
        schemeIds.add(queryParamsVO.getSchemeId());
        String inputSchemeId = ConsolidatedSystemUtils.getSchemeIdByTaskIdAndPeriod((String)queryParamsVO.getTaskVO().getTaskKey(), (String)queryParamsVO.getPeriodStr());
        if (!queryParamsVO.getSchemeId().equals(inputSchemeId)) {
            schemeIds.add(inputSchemeId);
            orgTypeIds.add(queryParamsVO.getTaskVO().getInputTaskInfo().getUnitDefine());
        }
        QueryParamsDTO queryDTO = new QueryParamsDTO();
        BeanUtils.copyProperties(queryParamsVO, queryDTO);
        queryDTO.setQueryAllColumns(true);
        queryDTO.setFilterDisableItem(true);
        ArrayList adjustEos = new ArrayList();
        for (GcOrgCacheVO org : queryParamsVO.getOrgList()) {
            queryDTO.setOrgList(Collections.singletonList(org));
            if (org.getFields().get("CURRENCYIDS") == null) {
                throw new BusinessRuntimeException("\u83b7\u53d6\u5355\u4f4d\u3010" + org.getCode() + "\u3011\u62a5\u8868\u5e01\u79cd\u5931\u8d25");
            }
            List<String> orgCurrency = Arrays.asList(org.getFields().get("CURRENCYIDS").toString().split(";"));
            for (String currency : orgCurrency) {
                queryDTO.setCurrency(currency);
                try {
                    adjustEos.addAll(this.coreService.listWithOnlyItems(queryDTO));
                }
                catch (Exception e) {
                    this.logger.error(e.getMessage(), e);
                    throw new BusinessRuntimeException("\u67e5\u8be2\u62b5\u9500\u5206\u5f55\u6570\u636e\u5931\u8d25");
                }
            }
        }
        key2Message.put("RECORDCOUNT", String.valueOf(adjustEos.size()));
        Map<String, List<GcOffSetVchrItemAdjustEO>> mRecid2OffsetMap = adjustEos.stream().collect(Collectors.groupingBy(GcOffSetVchrItemAdjustEO::getmRecid));
        try {
            this.initOffsetSubjectCode(mRecid2OffsetMap, queryParamsVO.getConsSystemId());
        }
        catch (Exception e) {
            this.logger.error(e.getMessage(), e);
            key2Message.put("SUBJECTLOGWARN", e.getMessage());
        }
        int inputMode = OffsetElmModeEnum.INPUT_ITEM.getValue();
        List ruleIds = this.optionVoLocal.get().getCarryOverRuleVos().stream().map(CarryOverOffsetRuleVO::getId).collect(Collectors.toList());
        Set allInitOffSetSrcTypeValue = OffSetSrcTypeEnum.getAllInitOffSetSrcTypeValue();
        for (List<GcOffSetVchrItemAdjustEO> adjustEOS : mRecid2OffsetMap.values()) {
            if (allInitOffSetSrcTypeValue.contains(adjustEOS.get(0).getOffSetSrcType()) && EFFECTTYPE.LONGTERM.getCode().equals(adjustEOS.get(0).getEffectType())) {
                initOffsetRecords.addAll(adjustEOS);
                continue;
            }
            if (adjustEOS.get(0).getElmMode() == inputMode) {
                if (EFFECTTYPE.LONGTERM.getCode().equals(adjustEOS.get(0).getEffectType())) {
                    inputAdjustNextRecords.addAll(adjustEOS);
                    continue;
                }
                inputAdjustCurrRecords.addAll(adjustEOS);
                continue;
            }
            if (ruleIds.contains(adjustEOS.get(0).getRuleId())) {
                Set rules = adjustEOS.stream().map(GcOffSetVchrItemAdjustEO::getRuleId).collect(Collectors.toSet());
                if (!ruleIds.containsAll(rules)) continue;
                ruleId2OffsetItemMap.get(adjustEOS.get(0).getRuleId()).addAll(adjustEOS);
                continue;
            }
            otherRecords.addAll(adjustEOS);
        }
        adjustEos.clear();
    }

    private Map<String, List<GcOffSetVchrItemAdjustEO>> initRule2OffsetEoMap() {
        HashMap<String, List<GcOffSetVchrItemAdjustEO>> rule2OffsetEoMap = new HashMap<String, List<GcOffSetVchrItemAdjustEO>>();
        CarryOverOffsetConfigVO carryOverOffsetConfigVO = this.optionVoLocal.get();
        List ruleIds = carryOverOffsetConfigVO.getCarryOverRuleVos().stream().map(CarryOverOffsetRuleVO::getId).collect(Collectors.toList());
        for (String ruleId : ruleIds) {
            rule2OffsetEoMap.put(ruleId, new ArrayList());
        }
        return rule2OffsetEoMap;
    }

    private void initOffsetSubjectCode(Map<String, List<GcOffSetVchrItemAdjustEO>> mRecid2OffsetMap, String consSystemId) {
        if (mRecid2OffsetMap.isEmpty()) {
            return;
        }
        CarryOverOffsetConfigVO optionVO = this.optionVoLocal.get();
        Set<String> targetSubjectCodes = this.targetSystemSubjectCodes.get();
        Map carryOverSubjectCodeMapping = optionVO.getSubjectMappingSetByDestSystemId(consSystemId);
        HashMap<String, String> newMapping = new HashMap<String, String>(carryOverSubjectCodeMapping);
        for (String srcSubjectCode : carryOverSubjectCodeMapping.keySet()) {
            String targetSubjectCode = (String)carryOverSubjectCodeMapping.get(srcSubjectCode);
            Set allChildrenCodes = this.consolidatedSubjectService.listAllChildrenCodes(srcSubjectCode, consSystemId);
            for (String childCode : allChildrenCodes) {
                if (newMapping.containsKey(childCode)) continue;
                newMapping.put(childCode, targetSubjectCode);
            }
        }
        ArrayList<String> notExistsSubjectCodeList = new ArrayList<String>();
        ArrayList<String> filterRepeatLogSubjectCode = new ArrayList<String>();
        Iterator<Map.Entry<String, List<GcOffSetVchrItemAdjustEO>>> iterator = mRecid2OffsetMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, List<GcOffSetVchrItemAdjustEO>> entry = iterator.next();
            boolean hasRepeatLogSubjectCode = false;
            for (GcOffSetVchrItemAdjustEO eo : entry.getValue()) {
                List subjectCodes;
                if (newMapping.containsKey(eo.getSubjectCode()) && !StringUtils.isEmpty((String)((String)newMapping.get(eo.getSubjectCode())))) {
                    eo.setSubjectCode((String)newMapping.get(eo.getSubjectCode()));
                }
                if (!CollectionUtils.isEmpty(subjectCodes = optionVO.getCarryOverSubjectVos().stream().map(CarryOverOffsetSubjectVO::getCode).collect(Collectors.toList())) && subjectCodes.contains(eo.getSubjectCode())) {
                    if (optionVO.getUndistributedProfitSubjectVo() == null || targetSubjectCodes.contains(optionVO.getUndistributedProfitSubjectVo().getCode()) || newMapping.containsKey(optionVO.getUndistributedProfitSubjectVo().getCode())) continue;
                    this.appendNoExistSubjectCode(eo, notExistsSubjectCodeList, filterRepeatLogSubjectCode);
                    hasRepeatLogSubjectCode = true;
                    continue;
                }
                if (targetSubjectCodes.contains(eo.getSubjectCode())) continue;
                this.appendNoExistSubjectCode(eo, notExistsSubjectCodeList, filterRepeatLogSubjectCode);
                hasRepeatLogSubjectCode = true;
            }
            if (!hasRepeatLogSubjectCode) continue;
            iterator.remove();
        }
        if (!notExistsSubjectCodeList.isEmpty()) {
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.offset.carryover.no.exist.subject", (Object[])new Object[]{String.join((CharSequence)",", notExistsSubjectCodeList)}));
        }
    }

    private void appendNoExistSubjectCode(GcOffSetVchrItemAdjustEO eo, List<String> notExistsSubjectCodeList, List<String> filterRepeatLogSubjectCode) {
        if (filterRepeatLogSubjectCode.contains(eo.getSubjectCode())) {
            return;
        }
        Map<String, ConsolidatedSubjectEO> consolidatedSubjectVOMap = this.currSystemSubjectCode2Subject.get();
        ConsolidatedSubjectEO subjectEO = consolidatedSubjectVOMap.get(eo.getSubjectCode());
        if (subjectEO != null) {
            notExistsSubjectCodeList.add(subjectEO.getCode() + "|" + subjectEO.getTitle());
            filterRepeatLogSubjectCode.add(eo.getSubjectCode());
        }
    }

    private void initRuleIdRange(QueryParamsVO queryParamsVO, Set<String> inputDataRuleIds, Set<String> inventoryRuleIds, Set<String> investRuleIds, Set<String> publicValueRuleIds) {
        CarryOverOffsetConfigVO carryOverOffsetConfigVO = this.optionVoLocal.get();
        List ruleIds = carryOverOffsetConfigVO.getCarryOverRuleVos().stream().map(CarryOverOffsetRuleVO::getId).collect(Collectors.toList());
        String periodStr = CarryOverUtil.convertPeriod((String)queryParamsVO.getPeriodStr(), (int)queryParamsVO.getAcctYear(), (int)queryParamsVO.getPeriodType());
        List ruleEos = this.ruleService.selectRuleEoListByTaskId(queryParamsVO.getTaskId(), periodStr);
        for (UnionRuleEO ruleEo : ruleEos) {
            switch (RuleTypeEnum.codeOf((String)ruleEo.getRuleType())) {
                case FLEXIBLE: 
                case RELATE_TRANSACTIONS: 
                case FINANCIAL_CHECK: 
                case FIXED_TABLE: 
                case FLOAT_LINE: 
                case FIXED_ASSETS: 
                case LEASE: {
                    inputDataRuleIds.add(ruleEo.getId());
                    break;
                }
                case INVENTORY: {
                    inventoryRuleIds.add(ruleEo.getId());
                    break;
                }
                case DIRECT_INVESTMENT: {
                    investRuleIds.add(ruleEo.getId());
                    break;
                }
                case INDIRECT_INVESTMENT: {
                    investRuleIds.add(ruleEo.getId());
                    break;
                }
                case DIRECT_INVESTMENT_SEGMENT: {
                    investRuleIds.add(ruleEo.getId());
                    break;
                }
                case INDIRECT_INVESTMENT_SEGMENT: {
                    investRuleIds.add(ruleEo.getId());
                    break;
                }
                case PUBLIC_VALUE_ADJUSTMENT: {
                    publicValueRuleIds.add(ruleEo.getId());
                    break;
                }
            }
        }
    }

    private GcOffSetVchrItemInitEO initOffsetRecord(GcOffSetVchrItemAdjustEO adjustEo, QueryParamsVO queryParamsVO) {
        GcOffSetVchrItemInitEO newAdjustEo = new GcOffSetVchrItemInitEO();
        BeanUtils.copyProperties(adjustEo, (Object)newAdjustEo);
        newAdjustEo.resetFields(adjustEo.getFields());
        newAdjustEo.setSrcId(newAdjustEo.getId());
        newAdjustEo.setId(UUIDOrderUtils.newUUIDStr());
        newAdjustEo.setAcctYear(queryParamsVO.getAcctYear());
        newAdjustEo.setOffSetCredit(adjustEo.getOffSetCredit());
        newAdjustEo.setOffSetDebit(adjustEo.getOffSetDebit());
        newAdjustEo.setDiffc(adjustEo.getDiffc());
        newAdjustEo.setDiffd(adjustEo.getDiffd());
        newAdjustEo.setSystemId(queryParamsVO.getConsSystemId());
        newAdjustEo.setEffectType(EFFECTTYPE.LONGTERM.getCode());
        return newAdjustEo;
    }

    private int flushOffsetEntry(List<GcOffSetVchrItemInitEO> oneGroup, QueryParamsVO queryParamsVO, boolean needMoreOneChangeSubject, boolean needSum, StringBuilder errorStr, Set<String> containSubjects) {
        return this.flushOffsetEntry(oneGroup, queryParamsVO, needMoreOneChangeSubject, true, needSum, errorStr, containSubjects);
    }

    private int flushOffsetEntry(List<GcOffSetVchrItemInitEO> oneGroup, QueryParamsVO queryParamsVO, boolean needMoreOneChangeSubject, boolean needFilterRule, boolean needSum, StringBuilder errorStr, Set<String> containSubjects) {
        String log;
        if (oneGroup.isEmpty()) {
            return this.clearList(oneGroup);
        }
        CarryOverOffsetConfigVO optionVO = this.optionVoLocal.get();
        if (!this.checkRuleSuccess(oneGroup, needFilterRule, optionVO)) {
            String log2 = GcI18nUtil.getMessage((String)"gc.offset.typeStr.carryover.norole");
            errorStr.append(log2);
            this.logWarn(log2, this.plusWeight(0));
            return this.clearList(oneGroup);
        }
        List<String> srcSubjectCodes = this.carryOverSubjectCodes.get();
        String carryOverUndisProfitSubjectCode = optionVO.getUndistributedProfitSubjectVo().getCode();
        srcSubjectCodes.add(carryOverUndisProfitSubjectCode);
        int changeSubjectNum = 0;
        HashSet<Double> offSetAmt = new HashSet<Double>(8);
        Map<String, ConsolidatedSubjectEO> consolidatedSubjectVOMap = this.currSystemSubjectCode2Subject.get();
        Set<String> targetSubjectCodes = this.targetSystemSubjectCodes.get();
        HashSet<String> allSubjectCodes = new HashSet<String>();
        for (GcOffSetVchrItemInitEO record2 : oneGroup) {
            offSetAmt.add(record2.getOffSetCredit());
            offSetAmt.add(record2.getOffSetDebit());
            offSetAmt.add(record2.getDiffc());
            offSetAmt.add(record2.getDiffd());
            if (!StringUtils.isEmpty((String)record2.getRuleId())) {
                record2.setRuleId(this.curr2TargetSystemRuleIdMap.get().get(record2.getRuleId()));
            }
            String subjectCode = record2.getSubjectCode();
            allSubjectCodes.add(subjectCode);
            if (!this.carryOverSubjectCodeMapping.get().containsKey(subjectCode) && srcSubjectCodes.contains(subjectCode)) {
                ConsolidatedSubjectEO subjectEO;
                ++changeSubjectNum;
                record2.setSubjectCode(carryOverUndisProfitSubjectCode);
                if (!targetSubjectCodes.contains(carryOverUndisProfitSubjectCode) && !this.carryOverSubjectCodeMapping.get().containsKey(carryOverUndisProfitSubjectCode) && (subjectEO = consolidatedSubjectVOMap.get(carryOverUndisProfitSubjectCode)) != null) {
                    throw new BusinessRuntimeException(String.format("\u5e74\u7ed3\u540e\u5408\u5e76\u4f53\u7cfb\u4e0d\u5b58\u5728\u4ee5\u4e0b\u79d1\u76ee\uff0c\u8bf7\u68c0\u67e5\u5e74\u7ed3\u540e\u5408\u5e76\u4f53\u7cfb\u7684\u79d1\u76ee\uff0c\u6216\u8005\u5728\u5e74\u7ed3\u524d\u5408\u5e76\u4f53\u7cfb\u7684\u9009\u9879\u4e2d\u914d\u7f6e\u5e74\u7ed3\u79d1\u76ee\u6620\u5c04\u3002\u4e0d\u5b58\u5728\u7684\u79d1\u76ee\u4e3a\uff1a%1$s", subjectEO.getCode() + "|" + subjectEO.getTitle()));
                }
            }
            if (!this.initOffsetSrcTypeSet.contains(record2.getOffSetSrcType())) continue;
            needMoreOneChangeSubject = false;
        }
        offSetAmt.remove(0.0);
        offSetAmt.remove(null);
        if (offSetAmt.isEmpty() && needSum) {
            errorStr.append("\u6574\u7ec4\u91d1\u989d\u4e3a0\uff0c\u4e0d\u8fdb\u884c\u5e74\u7ed3\u3002");
            return this.clearList(oneGroup);
        }
        if (needSum) {
            oneGroup = oneGroup.stream().filter(record -> record.getOffSetCredit() != 0.0 || record.getOffSetDebit() != 0.0).collect(Collectors.toList());
        }
        if (allSubjectCodes.size() == 1) {
            log = GcI18nUtil.getMessage((String)"gc.offset.typeStr.carryover.onesubject", (Object[])new Object[]{allSubjectCodes.toArray()[0]});
            errorStr.append(log);
            if (!containSubjects.contains(ConverterUtils.getAsString((Object)allSubjectCodes.toArray()[0]))) {
                this.logInfo(log, this.plusWeight(0));
                containSubjects.add(ConverterUtils.getAsString((Object)allSubjectCodes.toArray()[0]));
            }
            return this.clearList(oneGroup);
        }
        if (needMoreOneChangeSubject && changeSubjectNum == 0) {
            log = GcI18nUtil.getMessage((String)"gc.offset.typeStr.carryover.noundistributedProfit");
            errorStr.append(log);
            this.logWarn(log, this.plusWeight(0));
            return this.clearList(oneGroup);
        }
        int successNum = 0;
        if (changeSubjectNum < oneGroup.size()) {
            successNum = 1;
            boolean isVchrCodeEmpty = StringUtils.isEmpty((String)oneGroup.get(0).getVchrCode());
            String vchrCode = "";
            if (isVchrCodeEmpty) {
                vchrCode = this.createVchrCode(queryParamsVO);
            }
            for (GcOffSetVchrItemInitEO adjustEO : oneGroup) {
                if (adjustEO.getMemo().startsWith("\u516c\u5141\u4ef7\u503c")) {
                    adjustEO.setOffSetSrcType(this.carryOverPubSrcTypeValue);
                } else {
                    adjustEO.setOffSetSrcType(this.carryOverSrcTypeValue);
                }
                if (!isVchrCodeEmpty) continue;
                adjustEO.setVchrCode(vchrCode);
            }
            if (needSum) {
                List<GcOffSetVchrItemInitEO> gcOffSetVchrItemInitEOList = this.listSumOffsetRecordsByOrient(oneGroup);
                oneGroup.clear();
                oneGroup.addAll(gcOffSetVchrItemInitEOList);
            }
        } else {
            String log3 = GcI18nUtil.getMessage((String)"gc.offset.typeStr.carryover.onesubject", (Object[])new Object[]{carryOverUndisProfitSubjectCode});
            errorStr.append(log3);
            if (!containSubjects.contains(ConverterUtils.getAsString((Object)allSubjectCodes.toArray()[0]))) {
                this.logInfo(log3, this.plusWeight(0));
                containSubjects.add(ConverterUtils.getAsString((Object)allSubjectCodes.toArray()[0]));
            }
            return this.clearList(oneGroup);
        }
        this.offSetVchrItemInitDao.saveAll(oneGroup);
        oneGroup.clear();
        return successNum;
    }

    private boolean checkRuleSuccess(List<GcOffSetVchrItemInitEO> oneGroup, boolean needFilterRule, CarryOverOffsetConfigVO optionVO) {
        if (this.initOffsetSrcTypeSet.contains(oneGroup.get(0).getOffSetSrcType())) {
            return true;
        }
        if (!needFilterRule) {
            return true;
        }
        boolean success = false;
        List carryOverRuleVos = optionVO.getCarryOverRuleVos();
        List carryOverRuleIds = carryOverRuleVos.stream().map(CarryOverOffsetRuleVO::getId).collect(Collectors.toList());
        for (GcOffSetVchrItemInitEO record : oneGroup) {
            if (!carryOverRuleIds.contains(record.getRuleId())) continue;
            success = true;
            break;
        }
        return success;
    }

    private int clearList(List oneGroup) {
        oneGroup.clear();
        return 0;
    }

    private void loadOption(QueryParamsVO queryParamsVO) {
        String configOptionDataStr = this.gcCarryOverConfigService.getConfigOptionById(queryParamsVO.getCarryOverSchemeId());
        CarryOverOffsetConfigVO currOptionVO = (CarryOverOffsetConfigVO)JsonUtils.readValue((String)configOptionDataStr, CarryOverOffsetConfigVO.class);
        String convertPeriod = CarryOverUtil.convertPeriod((String)queryParamsVO.getPeriodStr(), (int)queryParamsVO.getAcctYear(), (int)queryParamsVO.getPeriodType());
        String currSystemId = this.consolidatedTaskService.getSystemIdByTaskIdAndPeriodStr(queryParamsVO.getTaskId(), convertPeriod);
        ConsolidatedSystemEO currSystem = this.consolidatedSystemService.getConsolidatedSystemEO(currSystemId);
        List ruleIds = currOptionVO.getCarryOverRuleVos().stream().map(CarryOverOffsetRuleVO::getId).collect(Collectors.toList());
        Assert.isNotNull((Object)currOptionVO, (String)"%s\u672a\u83b7\u53d6\u5230\u4f53\u7cfb\u9009\u9879", (Object[])new Object[]{currSystem.getSystemName()});
        Assert.isNotEmpty(ruleIds, (String)"%s\u672a\u914d\u7f6e\u7ed3\u8f6c\u89c4\u5219", (Object[])new Object[]{currSystem.getSystemName()});
        ConsolidatedSystemEO targetSystem = this.consolidatedSystemService.getConsolidatedSystemEO(queryParamsVO.getConsSystemId());
        if (CollectionUtils.isEmpty((Collection)currOptionVO.getCarryOverSubjectVos()) && ObjectUtils.isEmpty(currOptionVO.getUndistributedProfitSubjectVo())) {
            throw new BusinessRuntimeException(String.format("\u4f53\u7cfb\u3010%1$s\u3011\u672a\u914d\u7f6e\u7ed3\u8f6c\u4e3a\u5e74\u521d\u672a\u5206\u914d\u5229\u6da6\u79d1\u76ee", currSystem.getSystemName()));
        }
        this.optionVoLocal.set(currOptionVO);
        this.carryOverSubjectCodeMapping.set(currOptionVO.getSubjectMappingSetByDestSystemId(queryParamsVO.getConsSystemId()));
        List subjectCodes = currOptionVO.getCarryOverSubjectVos().stream().map(CarryOverOffsetSubjectVO::getCode).collect(Collectors.toList());
        Collections.sort(subjectCodes, Comparator.comparingInt(String::length));
        HashSet<String> subjectCodeContainSelfAndAllChildren = new HashSet<String>();
        for (String code : subjectCodes) {
            if (subjectCodeContainSelfAndAllChildren.contains(code)) continue;
            Set allChildrenCodes = this.consolidatedSubjectService.listAllChildrenCodes(code, currSystemId);
            if (allChildrenCodes.isEmpty()) {
                subjectCodeContainSelfAndAllChildren.add(code);
            }
            subjectCodeContainSelfAndAllChildren.addAll(allChildrenCodes);
        }
        this.carryOverSubjectCodes.set(new ArrayList(subjectCodeContainSelfAndAllChildren));
        List targetSystemAllSubjects = ((ConsolidatedSubjectService)SpringContextUtils.getBean(ConsolidatedSubjectService.class)).listAllSubjectsBySystemId(queryParamsVO.getConsSystemId());
        Set targetSubjectCodes = targetSystemAllSubjects.stream().map(ConsolidatedSubjectEO::getCode).collect(Collectors.toSet());
        this.targetSystemSubjectCodes.set(targetSubjectCodes);
        List currSystemAllSubjects = ((ConsolidatedSubjectService)SpringContextUtils.getBean(ConsolidatedSubjectService.class)).listAllSubjectsBySystemId(currSystemId);
        Map<String, ConsolidatedSubjectEO> subjectCode2Subject = currSystemAllSubjects.stream().collect(Collectors.toMap(ConsolidatedSubjectEO::getCode, entity -> entity));
        this.currSystemSubjectCode2Subject.set(subjectCode2Subject);
        List targetSystemRules = this.unionRuleService.findAllRuleTitles(targetSystem.getId());
        Map<String, String> targetRuleTitle2IdMap = targetSystemRules.stream().collect(Collectors.toMap(BaseRuleVO::getTitle, BaseRuleVO::getId, (o1, o2) -> o2));
        List currSystemRules = this.unionRuleService.findAllRuleTitles(currSystemId);
        HashMap currRuleId2TargetRuleIdMap = new HashMap();
        currSystemRules.stream().forEach(rule -> {
            if (targetRuleTitle2IdMap.containsKey(rule.getTitle())) {
                currRuleId2TargetRuleIdMap.put(rule.getId(), targetRuleTitle2IdMap.get(rule.getTitle()));
            }
        });
        this.curr2TargetSystemRuleIdMap.set(currRuleId2TargetRuleIdMap);
    }

    private Set<Integer> initOffsetSrcType() {
        return OffSetSrcTypeEnum.getAllInitOffSetSrcTypeValue();
    }

    private Set<Integer> getOffSetSrcTypes() {
        return OffSetSrcTypeEnum.getCommonInitOffSetSrcTypeValue();
    }
}

