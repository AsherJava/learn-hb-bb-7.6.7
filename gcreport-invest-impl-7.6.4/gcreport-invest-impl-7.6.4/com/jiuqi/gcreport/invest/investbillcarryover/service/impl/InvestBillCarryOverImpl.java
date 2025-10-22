/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.ConverterUtils
 *  com.jiuqi.common.base.util.DateUtils
 *  com.jiuqi.common.base.util.DoubleKeyMap
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.basedata.impl.bean.GcBaseData
 *  com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool
 *  com.jiuqi.gcreport.billcore.util.InvestBillTool
 *  com.jiuqi.gcreport.billcore.util.OrgUtil
 *  com.jiuqi.gcreport.billcore.util.SQLHelper
 *  com.jiuqi.gcreport.calculate.dto.GcCalcArgmentsDTO
 *  com.jiuqi.gcreport.calculate.env.GcCalcEnvContext
 *  com.jiuqi.gcreport.calculate.env.impl.GcCalcEnvContextImpl
 *  com.jiuqi.gcreport.carryover.task.AbstractTaskLog
 *  com.jiuqi.gcreport.carryover.vo.QueryParamsVO
 *  com.jiuqi.gcreport.common.util.NrTool
 *  com.jiuqi.gcreport.common.util.UUIDOrderUtils
 *  com.jiuqi.gcreport.consolidatedsystem.service.subject.ConsolidatedSubjectService
 *  com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService
 *  com.jiuqi.gcreport.conversion.function.RateValueFunction
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 *  com.jiuqi.gcreport.offsetitem.entity.GcOffSetVchrItemAdjustEO
 *  com.jiuqi.gcreport.offsetitem.enums.OffSetSrcTypeEnum
 *  com.jiuqi.gcreport.offsetitem.service.GcOffSetAppOffsetService
 *  com.jiuqi.gcreport.offsetitem.utils.OrgPeriodUtil
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  com.jiuqi.gcreport.rate.impl.utils.CommonRateUtils
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.nr.dataentry.service.IFuncExecuteService
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nvwa.definition.common.ColumnModelType
 *  jdk.nashorn.internal.objects.NativeString
 *  org.apache.commons.lang3.StringUtils
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.invest.investbillcarryover.service.impl;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.common.base.util.DateUtils;
import com.jiuqi.common.base.util.DoubleKeyMap;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.basedata.impl.bean.GcBaseData;
import com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool;
import com.jiuqi.gcreport.billcore.util.InvestBillTool;
import com.jiuqi.gcreport.billcore.util.OrgUtil;
import com.jiuqi.gcreport.billcore.util.SQLHelper;
import com.jiuqi.gcreport.calculate.dto.GcCalcArgmentsDTO;
import com.jiuqi.gcreport.calculate.env.GcCalcEnvContext;
import com.jiuqi.gcreport.calculate.env.impl.GcCalcEnvContextImpl;
import com.jiuqi.gcreport.carryover.task.AbstractTaskLog;
import com.jiuqi.gcreport.carryover.vo.QueryParamsVO;
import com.jiuqi.gcreport.common.util.NrTool;
import com.jiuqi.gcreport.common.util.UUIDOrderUtils;
import com.jiuqi.gcreport.consolidatedsystem.service.subject.ConsolidatedSubjectService;
import com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService;
import com.jiuqi.gcreport.conversion.function.RateValueFunction;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import com.jiuqi.gcreport.invest.formula.service.GcBillFormulaEvalService;
import com.jiuqi.gcreport.invest.investbill.dao.FairValueBillDao;
import com.jiuqi.gcreport.invest.investbill.dao.InvestBillDao;
import com.jiuqi.gcreport.invest.investbill.dto.GcInvestBillGroupDTO;
import com.jiuqi.gcreport.invest.investbillcarryover.service.InvestBillGcCarryOverService;
import com.jiuqi.gcreport.offsetitem.entity.GcOffSetVchrItemAdjustEO;
import com.jiuqi.gcreport.offsetitem.enums.OffSetSrcTypeEnum;
import com.jiuqi.gcreport.offsetitem.service.GcOffSetAppOffsetService;
import com.jiuqi.gcreport.offsetitem.utils.OrgPeriodUtil;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.gcreport.rate.impl.utils.CommonRateUtils;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.nr.dataentry.service.IFuncExecuteService;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nvwa.definition.common.ColumnModelType;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import jdk.nashorn.internal.objects.NativeString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InvestBillCarryOverImpl
extends AbstractTaskLog
implements InvestBillGcCarryOverService {
    @Autowired
    private InvestBillDao investBillDao;
    @Autowired
    private FairValueBillDao fairValueBillDao;
    @Autowired
    private ConsolidatedSubjectService consolidatedSubjectService;
    @Autowired
    private GcBillFormulaEvalService billFormulaEvalService;
    @Autowired
    private IRunTimeViewController iRunTimeViewController;
    @Autowired
    private IFuncExecuteService funcExecuteService;
    @Autowired
    private RateValueFunction rateValueFunction;
    @Autowired
    private ConsolidatedTaskService consolidatedTaskService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private static final int SRC_TYPE = OffSetSrcTypeEnum.CARRY_OVER.getSrcTypeValue();
    private final String ACCOUNTTYPE = "ACCOUNTTYPE";
    private final String INVESTMENT = "INVESTMENT";
    private final String FAIRVALUE = "FAIRVALUE";

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void doInvestCarryOverTask(QueryParamsVO queryParamsVO) {
        try {
            this.initTaskLog(queryParamsVO);
            this.logInfo("\u5f00\u59cb\u5e74\u7ed3", new Float(0.01f));
            this.logInfo("\u5f00\u59cb\u65f6\u95f4 " + DateUtils.nowTimeStr((String)"yyyy-MM-dd HH:mm:ss"), new Float(0.02f));
            Date beginTime = DateUtils.now();
            this.setSubTaskFullPercent(Float.valueOf(1.0f));
            this.doInvestCarryOver(queryParamsVO);
            this.logInfo("\u7ed3\u675f\u65f6\u95f4 " + DateUtils.nowTimeStr((String)"yyyy-MM-dd HH:mm:ss"), Float.valueOf(1.0f));
            this.logInfo("\u5e74\u7ed3\u7ed3\u675f\uff0c\u603b\u8017\u65f6:" + this.formatSecond(DateUtils.diffOf((Date)beginTime, (Date)DateUtils.now(), (int)13)), Float.valueOf(100.0f));
        }
        catch (Exception e) {
            this.logger.warn(e.getMessage(), e);
            this.logWarn(e.getMessage(), Float.valueOf(100.0f));
        }
        finally {
            this.finish();
        }
    }

    private void doInvestCarryOver(QueryParamsVO queryParamsVO) {
        this.initTaskLog(queryParamsVO);
        this.setSubTaskFullWeight(3);
        Date beginTime = Calendar.getInstance().getTime();
        String schemeTitle = this.getSchemeTitleAndSetParams(queryParamsVO);
        SQLHelper investSqlHelper = new SQLHelper("GC_INVESTBILL");
        SQLHelper investItemSqlHelper = new SQLHelper("GC_INVESTBILLITEM");
        SQLHelper fvchSqlHelper = new SQLHelper("GC_FVCHBILL");
        SQLHelper fvchFixedItemSqlHelper = new SQLHelper("GC_FVCH_FIXEDITEM");
        SQLHelper fvchOtherItemSqlHelper = new SQLHelper("GC_FVCH_OTHERITEM");
        List<String> orgCodeList = this.getOrgCodeList(queryParamsVO);
        Map<String, String> investColumn2ColumnType = this.getColumn2ColumnType("GC_INVESTBILL");
        Map<String, String> fvchFixedColumn2ColumnType = this.getColumn2ColumnType("GC_FVCH_FIXEDITEM");
        Map<String, String> fvchOtherColumn2ColumnType = this.getColumn2ColumnType("GC_FVCH_OTHERITEM");
        this.delCarryOverData(queryParamsVO);
        DoubleKeyMap<String, String, Double> offsetGroupId2SubjectCode2OffsetValueMap = this.getOffsetGroupId2SubjectCode2OffsetValueMap(queryParamsVO);
        List<Map<String, Object>> investBills = this.investBillDao.getByYear(queryParamsVO.getAcctYear() - 1, 0, orgCodeList);
        Collections.reverse(investBills);
        List<Map<String, Object>> currYearInvestBills = this.investBillDao.getByYear(queryParamsVO.getAcctYear(), 0, orgCodeList);
        Set investBillSet = currYearInvestBills.stream().map(currYearInvestItem -> (String)currYearInvestItem.get("UNITCODE") + currYearInvestItem.get("INVESTEDUNIT")).collect(Collectors.toSet());
        YearPeriodObject yp = new YearPeriodObject(Calendar.getInstance());
        GcOrgCenterService tool = GcOrgPublicTool.getInstance((String)OrgUtil.getOrgType((String)"GC_INVESTBILL"), (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
        int repeatCount = 0;
        StringBuffer unitLog = new StringBuffer();
        for (Map<String, Object> investBillMap : investBills) {
            String unitCode = (String)investBillMap.get("UNITCODE");
            String investedUnit = (String)investBillMap.get("INVESTEDUNIT");
            GcOrgCacheVO investUnitOrg = tool.getOrgByID((String)investBillMap.get("UNITCODE"));
            GcOrgCacheVO investedUnitOrg = tool.getOrgByID((String)investBillMap.get("INVESTEDUNIT"));
            if (null == investUnitOrg || null == investedUnitOrg) {
                String unitTypeName = null == investUnitOrg ? "\u6295\u8d44\u5355\u4f4d" : "\u88ab\u6295\u8d44\u5355\u4f4d";
                this.logInfo(String.format("\u5e74\u7ed3\u540e\u5e74\u5ea6%1s\u4e0d\u5b58\u5728\uff1a\u6295\u8d44\u5355\u4f4d%2s, \u88ab\u6295\u8d44\u5355\u4f4d%3s", unitTypeName, this.getUnitCodeAndTitle(tool, unitCode), this.getUnitCodeAndTitle(tool, investedUnit)), this.plusWeight(3));
                ++repeatCount;
                continue;
            }
            if (investBillSet.contains((String)investBillMap.get("UNITCODE") + investBillMap.get("INVESTEDUNIT"))) {
                this.logInfo("\u6570\u636e\u5df2\u5b58\u5728\u81ea\u52a8\u8df3\u8fc7\uff1a\u6295\u8d44\u5355\u4f4d" + investBillMap.get("UNITCODE") + "|" + (investUnitOrg == null ? " " : investUnitOrg.getTitle()) + "\uff0c\u88ab\u6295\u8d44\u5355\u4f4d" + investBillMap.get("INVESTEDUNIT") + "|" + (investedUnitOrg == null ? " " : investedUnitOrg.getTitle()), this.plusWeight(3));
                ++repeatCount;
                continue;
            }
            if (investBillMap.get("DISPOSEDATE") != null) {
                this.logInfo("\u5df2\u5904\u7f6e\u7684\u6570\u636e\u4e0d\u5e74\u7ed3\uff1a\u6295\u8d44\u5355\u4f4d" + investBillMap.get("UNITCODE") + "|" + (investUnitOrg == null ? " " : investUnitOrg.getTitle()) + "\uff0c\u88ab\u6295\u8d44\u5355\u4f4d" + investBillMap.get("INVESTEDUNIT") + "|" + (investedUnitOrg == null ? " " : investedUnitOrg.getTitle()), this.plusWeight(3));
                ++repeatCount;
                continue;
            }
            List<Map<String, Object>> investBillItemList = this.investBillDao.getInvestBillItemByBillCode((String)investBillMap.get("BILLCODE"));
            this.initNewInvestBill(investBillMap, queryParamsVO, offsetGroupId2SubjectCode2OffsetValueMap, investColumn2ColumnType);
            investBillItemList.forEach(investItem -> this.initNewInvestItemBill(investBillMap, (Map<String, Object>)investItem, queryParamsVO.getAcctYear()));
            investSqlHelper.saveData(investBillMap);
            investBillItemList.forEach(investItemMap -> investItemSqlHelper.saveData(investItemMap));
            unitLog.append(String.format(" \u6295\u8d44\u5355\u4f4d\uff1a%1s, \u88ab\u6295\u8d44\u5355\u4f4d\uff1a%2s", investBillMap.get("UNITCODE") + "|" + (investUnitOrg == null ? " " : investUnitOrg.getTitle()), investBillMap.get("INVESTEDUNIT") + "|" + (investedUnitOrg == null ? " " : investedUnitOrg.getTitle())));
            Map<String, Object> fvchBillMap = this.fairValueBillDao.getByUnitAndYear((String)investBillMap.get("UNITCODE"), (String)investBillMap.get("INVESTEDUNIT"), queryParamsVO.getAcctYear() - 1);
            if (fvchBillMap == null || fvchBillMap.isEmpty()) continue;
            this.initNewFvchBill(fvchBillMap, investBillMap);
            List<DefaultTableEntity> fvchFixedItemBillList = this.fairValueBillDao.getFvchFixedItemBills((String)fvchBillMap.get("SRCID"), null, null, null);
            List<DefaultTableEntity> fvchOtherItemBillList = this.fairValueBillDao.getFvchOtherItemBills((String)fvchBillMap.get("SRCID"), null, null, null);
            fvchFixedItemBillList.forEach(fvchFixedItem -> this.initNewFvchItem((DefaultTableEntity)fvchFixedItem, fvchBillMap, offsetGroupId2SubjectCode2OffsetValueMap, fvchFixedColumn2ColumnType, queryParamsVO));
            fvchOtherItemBillList.forEach(fvchOtherItem -> this.initNewFvchItem((DefaultTableEntity)fvchOtherItem, fvchBillMap, offsetGroupId2SubjectCode2OffsetValueMap, fvchOtherColumn2ColumnType, queryParamsVO));
            fvchSqlHelper.saveData(fvchBillMap);
            fvchFixedItemBillList.forEach(fvchFixedItem -> fvchFixedItemSqlHelper.saveData(fvchFixedItem.getFields()));
            fvchOtherItemBillList.forEach(fvchOtherItem -> fvchOtherItemSqlHelper.saveData(fvchOtherItem.getFields()));
        }
        this.addCarryOverInvestLog(unitLog.toString(), queryParamsVO.getAcctYear() - 1, schemeTitle);
        this.logInfo("\u6295\u8d44\u8868\u590d\u5236\u4e86" + (investBills.size() - repeatCount) + "\u6761\u8bb0\u5f55\uff0c\u8017\u65f6:" + this.formatSecond(DateUtils.diffOf((Date)beginTime, (Date)DateUtils.now(), (int)13)), this.plusWeight(3));
        offsetGroupId2SubjectCode2OffsetValueMap.clear();
    }

    private String getSchemeTitleAndSetParams(QueryParamsVO queryParamsVO) {
        TaskDefine taskDefine = this.iRunTimeViewController.queryTaskDefine(queryParamsVO.getTaskId());
        String schemeTitle = "";
        if (null != taskDefine) {
            String defaultPeriod = ConverterUtils.getAsString((Object)(queryParamsVO.getAcctYear() - 1)) + (char)taskDefine.getPeriodType().code() + "0000";
            queryParamsVO.setPeriodStr(OrgPeriodUtil.getQueryOrgPeriod((String)defaultPeriod));
            FormSchemeDefine formSchemeDefine = this.funcExecuteService.queryFormScheme(queryParamsVO.getTaskId(), queryParamsVO.getPeriodStr());
            if (null != formSchemeDefine) {
                queryParamsVO.setSchemeId(formSchemeDefine.getKey());
                schemeTitle = formSchemeDefine.getTitle();
                String consSystemId = this.consolidatedTaskService.getConsolidatedSystemIdBySchemeId(formSchemeDefine.getKey(), queryParamsVO.getPeriodStr());
                queryParamsVO.setConsSystemId(consSystemId);
            }
        }
        return schemeTitle;
    }

    private void addCarryOverInvestLog(String unitLog, int acctYear, String schemeTitle) {
        LogHelper.info((String)"\u5408\u5e76-\u5355\u636e\u53f0\u8d26\u5e74\u7ed3", (String)("\u53f0\u8d26\u5e74\u7ed3-\u5e74\u5ea6" + acctYear), (String)String.format("%1s, \u5e74\u5ea6\uff1a%2s, \u65b9\u6848\uff1a%3s", unitLog, acctYear, schemeTitle));
    }

    private List<String> getOrgCodeList(QueryParamsVO queryParamsVO) {
        ArrayList<String> orgCodeList = new ArrayList<String>();
        queryParamsVO.getOrgList().forEach(org -> {
            if (!StringUtils.isEmpty((String)org.getCode())) {
                orgCodeList.add(org.getCode());
            }
        });
        return orgCodeList;
    }

    private Map<String, String> getColumn2ColumnType(String tableName) {
        HashMap<String, String> column2ColumnType = new HashMap<String, String>();
        NrTool.queryAllColumnsInTable((String)tableName).forEach(fieldDefine -> column2ColumnType.put(fieldDefine.getCode(), fieldDefine.getColumnType().name()));
        return column2ColumnType;
    }

    private void delCarryOverData(QueryParamsVO queryParamsVO) {
        ArrayList<String> orgCodeList = new ArrayList<String>();
        queryParamsVO.getOrgList().forEach(org -> {
            if (!StringUtils.isEmpty((String)org.getCode())) {
                orgCodeList.add(org.getCode());
            }
        });
        List<Map<String, Object>> currYearInvestBills = this.investBillDao.getByYear(queryParamsVO.getAcctYear(), SRC_TYPE, orgCodeList);
        HashSet<String> masterIds = new HashSet<String>(16);
        currYearInvestBills.forEach(itemMap -> masterIds.add((String)itemMap.get("ID")));
        int count = this.investBillDao.deleteInvestItemByMasterId(masterIds);
        this.logger.info("\u6295\u8d44\u53d8\u52a8\u8868\u5220\u9664\u4e86{} \u6761\u8bb0\u5f55", (Object)count);
        count = this.investBillDao.deleteByYearAndUnit("GC_INVESTBILL", queryParamsVO.getAcctYear(), SRC_TYPE, orgCodeList);
        this.logger.info("\u6295\u8d44\u8868\u5220\u9664\u4e86{} \u6761\u8bb0\u5f55", (Object)count);
        count = this.investBillDao.deleteByYearAndUnit("GC_FVCH_FIXEDITEM", queryParamsVO.getAcctYear(), SRC_TYPE, orgCodeList);
        this.logger.info("\u516c\u5141\u5355\u636e\u56fa\u5b9a\u65e0\u5f62\u8d44\u4ea7\u8868\u5220\u9664\u4e86{} \u6761\u8bb0\u5f55", (Object)count);
        count = this.investBillDao.deleteByYearAndUnit("GC_FVCH_OTHERITEM", queryParamsVO.getAcctYear(), SRC_TYPE, orgCodeList);
        this.logger.info("\u516c\u5141\u5355\u636e\u5176\u5b83\u8d44\u4ea7\u7c7b\u8868\u5220\u9664\u4e86{} \u6761\u8bb0\u5f55", (Object)count);
        count = this.investBillDao.deleteByYearAndUnit("GC_FVCHBILL", queryParamsVO.getAcctYear(), SRC_TYPE, orgCodeList);
        this.logger.info("\u516c\u5141\u5355\u636e\u4e3b\u8868\u5220\u9664\u4e86{} \u6761\u8bb0\u5f55", (Object)count);
    }

    private void initNewInvestBill(Map<String, Object> itemMap, QueryParamsVO queryParamsVO, DoubleKeyMap<String, String, Double> offsetGroupId2SubjectCode2OffsetValueMap, Map<String, String> column2ColumnType) {
        itemMap.put("ACCTYEAR", queryParamsVO.getAcctYear());
        itemMap.put("SRCID", itemMap.get("ID"));
        itemMap.put("SRCTYPE", SRC_TYPE);
        itemMap.put("OFFSETINITFLAG", 0);
        itemMap.put("FAIRVALUEADJUSTFLAG", 0);
        itemMap.put("FAIRVALUEOFFSETFLAG", 0);
        itemMap.put("ID", UUIDOrderUtils.newUUIDStr());
        itemMap.put("BILLCODE", this.getBillCode("GCBILL_B_INVESTBILL", (String)itemMap.get("UNITCODE")));
        itemMap.put("CREATETIME", new Date());
        itemMap.put("BILLDATE", new Date());
        this.handleDataByFormula(itemMap, "INVESTMENT", offsetGroupId2SubjectCode2OffsetValueMap, column2ColumnType, queryParamsVO);
    }

    private void handleDataByFormula(Map<String, Object> itemMap, String accountType, DoubleKeyMap<String, String, Double> offsetGroupId2SubjectCode2OffsetValueMap, Map<String, String> column2ColumnType, QueryParamsVO queryParamsVO) {
        List baseData = GcBaseDataCenterTool.getInstance().queryBasedataItems("MD_TZYSFILEDDJ");
        baseData = baseData.stream().filter(iBaseData -> accountType.equals(iBaseData.getFieldVal("ACCOUNTTYPE")) && ConverterUtils.getAsBoolean((Object)iBaseData.getFieldVal("STOPFLAG")) == false).collect(Collectors.toList());
        for (GcBaseData iBaseData2 : baseData) {
            String calcformula = (String)iBaseData2.getFieldVal("CALCFORMULA");
            String begainField = iBaseData2.getFieldVal("BEGAINFIELD") == null ? null : iBaseData2.getFieldVal("BEGAINFIELD").toString().toUpperCase();
            String endField = iBaseData2.getFieldVal("ENDFIELD") == null ? null : NativeString.toUpperCase((Object)iBaseData2.getFieldVal("ENDFIELD"));
            String addField = iBaseData2.getFieldVal("CURRENTPERIODADD") == null ? null : NativeString.toUpperCase((Object)iBaseData2.getFieldVal("CURRENTPERIODADD"));
            String reduceField = iBaseData2.getFieldVal("CURRENTPERIODREDUCE") == null ? null : NativeString.toUpperCase((Object)iBaseData2.getFieldVal("CURRENTPERIODREDUCE"));
            String relatedSubjectStr = (String)iBaseData2.getFieldVal("RELATEDSUBJECT");
            if (StringUtils.isEmpty((String)begainField)) {
                this.setZeroOfFieldValue(itemMap, addField, reduceField);
                continue;
            }
            if (!StringUtils.isEmpty((String)calcformula) && "INVESTMENT".equals(accountType)) {
                itemMap.put(begainField, this.getCalcFormulaValue(itemMap, queryParamsVO, calcformula));
                this.setZeroOfFieldValue(itemMap, addField, reduceField);
                continue;
            }
            Object currYearBegainValue = null;
            if (ColumnModelType.BIGDECIMAL.name().equals(column2ColumnType.get(begainField)) || ColumnModelType.DOUBLE.name().equals(column2ColumnType.get(begainField)) || ColumnModelType.INTEGER.name().equals(column2ColumnType.get(begainField))) {
                Double lastYearBegainValue = this.getFieldValue(itemMap, begainField);
                Double lastAddValue = this.getFieldValue(itemMap, addField);
                Double lastReduceValue = this.getFieldValue(itemMap, reduceField);
                Double lastYearEndValue = 0.0;
                if (org.apache.commons.lang3.StringUtils.isNotEmpty((CharSequence)endField) && endField.contains("[")) {
                    Object zbValue = this.getZbValue(endField, itemMap.get("INVESTEDUNIT"), queryParamsVO.getPeriodStr());
                    if (zbValue != null) {
                        lastYearEndValue = ((BigDecimal)zbValue).doubleValue();
                    }
                } else {
                    lastYearEndValue = this.getFieldValue(itemMap, endField);
                }
                double relatedSubjectOffset = 0.0;
                if (!StringUtils.isEmpty((String)relatedSubjectStr) && "INVESTMENT".equals(accountType)) {
                    relatedSubjectOffset = this.getRelatedSubjectOffsetValue(relatedSubjectStr, itemMap, offsetGroupId2SubjectCode2OffsetValueMap, queryParamsVO.getConsSystemId());
                }
                currYearBegainValue = StringUtils.isEmpty((String)endField) ? Double.valueOf(lastYearBegainValue + lastAddValue - lastReduceValue + relatedSubjectOffset) : Double.valueOf(lastYearEndValue + relatedSubjectOffset);
            } else if (org.apache.commons.lang3.StringUtils.isNotEmpty((CharSequence)endField) && endField.contains("[")) {
                Object zbValue = this.getZbValue(endField, itemMap.get("INVESTEDUNIT"), queryParamsVO.getPeriodStr());
                if (zbValue != null) {
                    currYearBegainValue = zbValue;
                }
            } else {
                currYearBegainValue = endField;
            }
            itemMap.put(begainField, currYearBegainValue);
            this.setZeroOfFieldValue(itemMap, addField, reduceField);
        }
    }

    private double getCalcFormulaValue(Map<String, Object> investMap, QueryParamsVO queryParamsVO, String calcformula) {
        DimensionValueSet dset = new DimensionValueSet();
        dset.setValue("DATATIME", (Object)queryParamsVO.getPeriodStr());
        dset.setValue("MD_CURRENCY", investMap.get("CURRENCYCODE"));
        dset.setValue("MD_GCORGTYPE", (Object)queryParamsVO.getOrgType());
        dset.setValue("MD_ORG", investMap.get("UNITCODE"));
        DefaultTableEntity master = new DefaultTableEntity();
        master.resetFields(investMap);
        GcInvestBillGroupDTO investBillGroupDTO = new GcInvestBillGroupDTO(master, null, true);
        GcCalcArgmentsDTO calcArgmentsDTO = new GcCalcArgmentsDTO();
        calcArgmentsDTO.setPeriodStr(queryParamsVO.getPeriodStr());
        calcArgmentsDTO.setSchemeId(queryParamsVO.getSchemeId());
        calcArgmentsDTO.setTaskId(queryParamsVO.getTaskId());
        calcArgmentsDTO.setCurrency((String)investMap.get("CURRENCYCODE"));
        calcArgmentsDTO.setOrgType(queryParamsVO.getOrgType());
        calcArgmentsDTO.setOrgId((String)investMap.get("UNITCODE"));
        calcArgmentsDTO.setSelectAdjustCode(queryParamsVO.getSelectAdjustCode());
        GcCalcEnvContextImpl env = new GcCalcEnvContextImpl();
        env.setCalcArgments(calcArgmentsDTO);
        Integer decimal = null;
        if (calcformula.contains(this.rateValueFunction.name())) {
            decimal = CommonRateUtils.getRateValueFieldFractionDigits();
        }
        try {
            return this.billFormulaEvalService.evaluateInvestBillData((GcCalcEnvContext)env, dset, calcformula, investBillGroupDTO, decimal);
        }
        catch (Exception e) {
            this.logger.error("\u53f0\u8d26\u5e74\u7ed3\u6267\u884c\u8fd0\u7b97\u516c\u5f0f\u5931\u8d25\uff1a" + e.getMessage(), e);
            LogHelper.error((String)"\u5408\u5e76-\u5355\u636e\u53f0\u8d26\u5e74\u7ed3", (String)"\u53f0\u8d26\u5e74\u7ed3-\u8fd0\u7b97\u516c\u5f0f", (String)e.getMessage());
            throw new BusinessRuntimeException("\u5e74\u7ed3\u57fa\u7840\u6570\u636e[MD_TZYSFILEDDJ]\u7684[\u8fd0\u7b97\u516c\u5f0f]\u8bed\u6cd5\u6709\u8bef\uff0c\u8bf7\u68c0\u67e5\u4fee\u6539!" + e.getMessage());
        }
    }

    private Double getFieldValue(Map<String, Object> itemMap, String field) {
        if (itemMap.get(field) == null) {
            return 0.0;
        }
        if (itemMap.get(field) instanceof Integer) {
            return ((Integer)itemMap.get(field)).doubleValue();
        }
        if (itemMap.get(field) instanceof Double) {
            return (Double)itemMap.get(field);
        }
        return 0.0;
    }

    private Double getRelatedSubjectOffsetValue(String relatedSubjectStr, Map<String, Object> itemMap, DoubleKeyMap<String, String, Double> offsetGroupId2SubjectCode2OffsetValueMap, String systemId) {
        String investBillId = (String)itemMap.get("SRCID");
        HashSet allSubjectCode = new HashSet();
        List<String> subjectCodeList = Arrays.asList(relatedSubjectStr.split(","));
        subjectCodeList.forEach(subjectCode -> {
            List allChildrenSubjects = this.consolidatedSubjectService.listAllChildrenSubjects(systemId, subjectCode);
            if (!CollectionUtils.isEmpty((Collection)allChildrenSubjects)) {
                allChildrenSubjects.forEach(item -> allSubjectCode.add(item.getCode()));
            }
            allSubjectCode.add(subjectCode);
        });
        Double relatedSubjectOffsetValue = allSubjectCode.stream().mapToDouble(s -> offsetGroupId2SubjectCode2OffsetValueMap.get((Object)investBillId, s) == null ? 0.0 : (Double)offsetGroupId2SubjectCode2OffsetValueMap.get((Object)investBillId, s)).sum();
        return relatedSubjectOffsetValue == null ? 0.0 : relatedSubjectOffsetValue;
    }

    private Object getZbValue(String zbCode, Object orgCode, String periodStr) {
        try {
            DimensionValueSet ds = new DimensionValueSet();
            ds.setValue("MD_ORG", orgCode);
            ds.setValue("DATATIME", (Object)periodStr);
            ds.setValue("MD_GCORGTYPE", (Object)"MD_ORG_CORPORATE");
            AbstractData zbValue = NrTool.getZbValue((DimensionValueSet)ds, (String)zbCode);
            if (zbValue != null) {
                return zbValue.getAsObject();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void setZeroOfFieldValue(Map<String, Object> itemMap, String addField, String reduceField) {
        if (!StringUtils.isEmpty((String)addField)) {
            itemMap.put(addField, 0);
        }
        if (!StringUtils.isEmpty((String)reduceField)) {
            itemMap.put(reduceField, 0);
        }
    }

    private void initNewInvestItemBill(Map<String, Object> investMap, Map<String, Object> investItemMap, int acctYear) {
        investItemMap.put("MASTERID", investMap.get("ID"));
        investItemMap.put("ID", UUIDOrderUtils.newUUIDStr());
        investItemMap.put("SRCID", investItemMap.get("ID"));
        investItemMap.put("SRCTYPE", SRC_TYPE);
        investItemMap.put("ACCTYEAR", acctYear);
        investItemMap.put("BILLCODE", investMap.get("BILLCODE"));
    }

    private void initNewFvchBill(Map<String, Object> fvchBillMap, Map<String, Object> investBillMap) {
        fvchBillMap.put("MASTERID", investBillMap.get("ID"));
        fvchBillMap.put("SRCID", fvchBillMap.get("ID"));
        fvchBillMap.put("SRCTYPE", SRC_TYPE);
        fvchBillMap.put("ID", UUIDOrderUtils.newUUIDStr());
        fvchBillMap.put("ACCTYEAR", investBillMap.get("ACCTYEAR"));
        fvchBillMap.put("BILLCODE", this.getBillCode("GCBILL_B_FVCHBILL", (String)fvchBillMap.get("UNITCODE")));
    }

    private void initNewFvchItem(DefaultTableEntity fvchFixedItem, Map<String, Object> fvchBillMap, DoubleKeyMap<String, String, Double> offsetGroupId2SubjectCode2OffsetValueMap, Map<String, String> column2ColumnType, QueryParamsVO queryParamsVO) {
        Map fvchFixedItemMap = fvchFixedItem.getFields();
        fvchFixedItemMap.put("MASTERID", fvchBillMap.get("ID"));
        fvchFixedItemMap.put("SRCID", fvchFixedItemMap.get("ID"));
        fvchFixedItemMap.put("ID", UUIDOrderUtils.newUUIDStr());
        fvchFixedItemMap.put("ACCTYEAR", fvchBillMap.get("ACCTYEAR"));
        fvchFixedItemMap.put("SRCTYPE", SRC_TYPE);
        fvchFixedItemMap.put("BILLCODE", fvchBillMap.get("BILLCODE"));
        this.handleDataByFormula(fvchFixedItemMap, "FAIRVALUE", offsetGroupId2SubjectCode2OffsetValueMap, column2ColumnType, queryParamsVO);
    }

    private DoubleKeyMap<String, String, Double> getOffsetGroupId2SubjectCode2OffsetValueMap(QueryParamsVO queryParamsVO) {
        String[] columnNamesInDB = new String[]{"systemId", "DATATIME", "offsetSrcType"};
        Object[] values = new Object[]{queryParamsVO.getConsSystemId(), queryParamsVO.getPeriodStr(), OffSetSrcTypeEnum.EQUITY_METHOD_ADJ.getSrcTypeValue()};
        List offsetDetails = ((GcOffSetAppOffsetService)SpringContextUtils.getBean(GcOffSetAppOffsetService.class)).listOffsetRecordsByWhere(columnNamesInDB, values);
        DoubleKeyMap offsetGroupId2SubjectCode2OffsetValueMap = new DoubleKeyMap();
        if (!CollectionUtils.isEmpty((Collection)offsetDetails)) {
            for (GcOffSetVchrItemAdjustEO offsetDetail : offsetDetails) {
                if (StringUtils.isEmpty((String)offsetDetail.getSrcOffsetGroupId())) continue;
                Double offsetValue = (Double)offsetGroupId2SubjectCode2OffsetValueMap.get((Object)offsetDetail.getSrcOffsetGroupId(), (Object)offsetDetail.getSubjectCode());
                if (null == offsetValue) {
                    offsetValue = 0.0;
                }
                offsetValue = offsetValue + (offsetDetail.getOffSetCredit() - offsetDetail.getOffSetDebit());
                offsetGroupId2SubjectCode2OffsetValueMap.put((Object)offsetDetail.getSrcOffsetGroupId(), (Object)offsetDetail.getSubjectCode(), (Object)offsetValue);
            }
        }
        return offsetGroupId2SubjectCode2OffsetValueMap;
    }

    private Object getBillCode(String uniqueCode, String unitCode) {
        return InvestBillTool.getBillCode((String)uniqueCode, (String)unitCode);
    }

    private String getUnitCodeAndTitle(GcOrgCenterService tool, String unitCode) {
        GcOrgCacheVO orgCacheVO = tool.getBaseOrgByCode(unitCode);
        return orgCacheVO.getCode() + "|" + orgCacheVO.getTitle();
    }
}

