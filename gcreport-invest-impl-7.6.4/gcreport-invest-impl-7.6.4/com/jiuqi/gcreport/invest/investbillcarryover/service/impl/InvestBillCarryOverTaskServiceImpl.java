/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.ConverterUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.NumberUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.gcreport.billcore.util.BillParseTool
 *  com.jiuqi.gcreport.billcore.util.InvestBillTool
 *  com.jiuqi.gcreport.billcore.util.OrgUtil
 *  com.jiuqi.gcreport.billcore.vo.BillInfoVo
 *  com.jiuqi.gcreport.calculate.dto.GcCalcArgmentsDTO
 *  com.jiuqi.gcreport.calculate.env.GcCalcEnvContext
 *  com.jiuqi.gcreport.calculate.env.impl.GcCalcEnvContextImpl
 *  com.jiuqi.gcreport.carryover.service.GcCarryOverLogService
 *  com.jiuqi.gcreport.carryover.service.GcCarryOverProcessService
 *  com.jiuqi.gcreport.carryover.task.AbstractTaskLog
 *  com.jiuqi.gcreport.carryover.vo.QueryParamsVO
 *  com.jiuqi.gcreport.common.util.UUIDOrderUtils
 *  com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService
 *  com.jiuqi.gcreport.conversion.function.RateValueFunction
 *  com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 *  com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils
 *  com.jiuqi.gcreport.i18n.util.GcI18nUtil
 *  com.jiuqi.gcreport.investcarryover.vo.InvestBillCarryOverSettingVO
 *  com.jiuqi.gcreport.offsetitem.enums.OffSetSrcTypeEnum
 *  com.jiuqi.gcreport.offsetitem.utils.OrgPeriodUtil
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  com.jiuqi.gcreport.rate.impl.utils.CommonRateUtils
 *  com.jiuqi.gcreport.temp.dto.TaskLog
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.nr.dataentry.service.IFuncExecuteService
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.util.StringUtils
 *  com.jiuqi.va.bill.action.SaveAction
 *  com.jiuqi.va.bill.impl.BillContextImpl
 *  com.jiuqi.va.bill.impl.BillModelImpl
 *  com.jiuqi.va.bill.intf.BillContext
 *  com.jiuqi.va.bill.intf.BillDefineService
 *  com.jiuqi.va.bill.intf.BillException
 *  com.jiuqi.va.biz.intf.action.Action
 *  com.jiuqi.va.biz.intf.action.ActionManager
 *  com.jiuqi.va.biz.intf.action.ActionRequest
 *  com.jiuqi.va.biz.intf.action.ActionResponse
 *  com.jiuqi.va.domain.common.ShiroUtil
 */
package com.jiuqi.gcreport.invest.investbillcarryover.service.impl;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.NumberUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.gcreport.billcore.util.BillParseTool;
import com.jiuqi.gcreport.billcore.util.InvestBillTool;
import com.jiuqi.gcreport.billcore.util.OrgUtil;
import com.jiuqi.gcreport.billcore.vo.BillInfoVo;
import com.jiuqi.gcreport.calculate.dto.GcCalcArgmentsDTO;
import com.jiuqi.gcreport.calculate.env.GcCalcEnvContext;
import com.jiuqi.gcreport.calculate.env.impl.GcCalcEnvContextImpl;
import com.jiuqi.gcreport.carryover.service.GcCarryOverLogService;
import com.jiuqi.gcreport.carryover.service.GcCarryOverProcessService;
import com.jiuqi.gcreport.carryover.task.AbstractTaskLog;
import com.jiuqi.gcreport.carryover.vo.QueryParamsVO;
import com.jiuqi.gcreport.common.util.UUIDOrderUtils;
import com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService;
import com.jiuqi.gcreport.conversion.function.RateValueFunction;
import com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils;
import com.jiuqi.gcreport.i18n.util.GcI18nUtil;
import com.jiuqi.gcreport.invest.formula.service.GcBillFormulaEvalService;
import com.jiuqi.gcreport.invest.investbill.dao.FairValueBillDao;
import com.jiuqi.gcreport.invest.investbill.dao.InvestBillDao;
import com.jiuqi.gcreport.invest.investbill.dto.GcInvestBillGroupDTO;
import com.jiuqi.gcreport.invest.investbillcarryover.enums.AccoutCarryOverModeEnum;
import com.jiuqi.gcreport.invest.investbillcarryover.enums.AccoutTypeEnum;
import com.jiuqi.gcreport.invest.investbillcarryover.service.InvestBillCarryOverSettingService;
import com.jiuqi.gcreport.invest.investbillcarryover.service.InvestBillCarryOverTaskService;
import com.jiuqi.gcreport.investcarryover.vo.InvestBillCarryOverSettingVO;
import com.jiuqi.gcreport.offsetitem.enums.OffSetSrcTypeEnum;
import com.jiuqi.gcreport.offsetitem.utils.OrgPeriodUtil;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.gcreport.rate.impl.utils.CommonRateUtils;
import com.jiuqi.gcreport.temp.dto.TaskLog;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.nr.dataentry.service.IFuncExecuteService;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.util.StringUtils;
import com.jiuqi.va.bill.action.SaveAction;
import com.jiuqi.va.bill.impl.BillContextImpl;
import com.jiuqi.va.bill.impl.BillModelImpl;
import com.jiuqi.va.bill.intf.BillContext;
import com.jiuqi.va.bill.intf.BillDefineService;
import com.jiuqi.va.bill.intf.BillException;
import com.jiuqi.va.biz.intf.action.Action;
import com.jiuqi.va.biz.intf.action.ActionManager;
import com.jiuqi.va.biz.intf.action.ActionRequest;
import com.jiuqi.va.biz.intf.action.ActionResponse;
import com.jiuqi.va.domain.common.ShiroUtil;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InvestBillCarryOverTaskServiceImpl
extends AbstractTaskLog
implements InvestBillCarryOverTaskService {
    @Autowired
    private InvestBillDao investBillDao;
    @Autowired
    private FairValueBillDao fairValueBillDao;
    @Autowired
    private InvestBillCarryOverSettingService carryOverSettingService;
    @Autowired
    private GcBillFormulaEvalService billFormulaEvalService;
    @Autowired
    private RateValueFunction rateValueFunction;
    @Autowired
    private GcCarryOverLogService carryOverLogService;
    @Autowired
    private IRunTimeViewController iRunTimeViewController;
    @Autowired
    private IFuncExecuteService funcExecuteService;
    @Autowired
    private ConsolidatedTaskService consolidatedTaskService;
    @Autowired
    private GcCarryOverProcessService carryOverProcessService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private static final int SRC_TYPE = OffSetSrcTypeEnum.CARRY_OVER.getSrcTypeValue();

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public TaskLog doTask(QueryParamsVO queryParamsVO, AsyncTaskMonitor monitor) {
        try {
            this.initTaskLog(queryParamsVO);
            this.setSubTaskFullPercent(Float.valueOf(1.0f));
            this.setSubTaskFullWeight(3);
            List<String> orgCodeList = this.getOrgCodeList(queryParamsVO);
            this.execute(queryParamsVO, orgCodeList, monitor);
            TaskLog taskLog = this.carryOverProcessService.getTaskLog(queryParamsVO.getTaskLogId() + "_" + ((GcOrgCacheVO)queryParamsVO.getOrgList().get(0)).getCode());
            String result = JsonUtils.writeValueAsString((Object)taskLog.getMessages());
            monitor.finish(null, (Object)result);
            TaskLog taskLog2 = taskLog;
            return taskLog2;
        }
        finally {
            this.finish();
        }
    }

    public void execute(QueryParamsVO queryParamsVO, List<String> orgCodeList, AsyncTaskMonitor monitor) {
        Set<String> currYearNeedUpdateInvestSrcIDSet = this.getNeedUpdateInvestSrcIDSet(queryParamsVO.getAcctYear(), orgCodeList);
        this.delCarryOverData(queryParamsVO, orgCodeList);
        Set<String> currYearUnitAndInvestedUnitKey = this.getCurrYearUnitAndInvestedUnitKey(queryParamsVO.getAcctYear(), orgCodeList);
        List<Map<String, Object>> lastYear12PeroidInvestBills = this.investBillDao.getByYear(queryParamsVO.getAcctYear() - 1, 12, 0, orgCodeList);
        Set<String> masterBillDesignViewFieldSet = this.getBillDesignViewFieldSet();
        this.setQueryParams(queryParamsVO);
        GcOrgCenterService orgTool = this.getOrgCenterService(queryParamsVO.getPeriodStr());
        int carryOverSuccessCount = 0;
        int carryOverSkipCount = 0;
        Collections.reverse(lastYear12PeroidInvestBills);
        StringBuffer unitLog = new StringBuffer();
        String orgTitle = this.getOrgTitle(orgTool, orgCodeList.get(0));
        this.logInfo(GcI18nUtil.getMessage((String)"gc.invest.carryover.unit.start.execution", (Object[])new Object[]{orgTitle}), Float.valueOf(0.1f));
        for (Map<String, Object> lastYeatInvestBill : lastYear12PeroidInvestBills) {
            String lastYearSrcId = (String)lastYeatInvestBill.get("SRCID");
            if (this.doInvestCarryOver(lastYeatInvestBill, queryParamsVO, currYearUnitAndInvestedUnitKey, currYearNeedUpdateInvestSrcIDSet, masterBillDesignViewFieldSet, orgTool, unitLog)) {
                ++carryOverSuccessCount;
                this.doFvchCarryOver(lastYeatInvestBill, queryParamsVO, lastYearSrcId);
                continue;
            }
            ++carryOverSkipCount;
        }
        this.logInfo(GcI18nUtil.getMessage((String)"gc.invest.carryover.unit.execution_result", (Object[])new Object[]{lastYear12PeroidInvestBills.size(), carryOverSuccessCount, carryOverSkipCount}), Float.valueOf(0.9f));
        this.logInfo(GcI18nUtil.getMessage((String)"gc.invest.carryover.unit.execution.complete", (Object[])new Object[]{orgTitle}), Float.valueOf(0.99f));
        this.addCarryOverInvestLog(unitLog.toString(), queryParamsVO.getAcctYear() - 1);
        HashMap<String, String> extendInfo = new HashMap<String, String>();
        extendInfo.put("CARRYOVERTOTAL", String.valueOf(lastYear12PeroidInvestBills.size()));
        extendInfo.put("CARRYOVERSUCCESSTOTAL", String.valueOf(carryOverSuccessCount));
        this.carryOverLogService.saveLogExtend(queryParamsVO.getTaskLogId(), extendInfo);
    }

    private String getOrgTitle(GcOrgCenterService orgTool, String orgCode) {
        GcOrgCacheVO orgCacheVO = orgTool.getOrgByID(orgCode);
        return orgCode + "|" + (orgCacheVO == null ? " " : orgCacheVO.getTitle());
    }

    private void addCarryOverInvestLog(String unitLog, int acctYear) {
        LogHelper.info((String)"\u5408\u5e76-\u5355\u636e\u53f0\u8d26\u5e74\u7ed3", (String)("\u53f0\u8d26\u5e74\u7ed3-\u5e74\u5ea6" + acctYear), (String)String.format("%1s, \u5e74\u5ea6\uff1a%2s", unitLog, acctYear));
    }

    private void setQueryParams(QueryParamsVO queryParamsVO) {
        if (StringUtils.isEmpty((String)queryParamsVO.getTaskId())) {
            return;
        }
        TaskDefine taskDefine = this.iRunTimeViewController.queryTaskDefine(queryParamsVO.getTaskId());
        if (null != taskDefine) {
            String defaultPeriod = ConverterUtils.getAsString((Object)(queryParamsVO.getAcctYear() - 1)) + (char)taskDefine.getPeriodType().code() + "0000";
            queryParamsVO.setPeriodStr(OrgPeriodUtil.getQueryOrgPeriod((String)defaultPeriod));
            FormSchemeDefine formSchemeDefine = this.funcExecuteService.queryFormScheme(queryParamsVO.getTaskId(), queryParamsVO.getPeriodStr());
            if (null != formSchemeDefine) {
                queryParamsVO.setSchemeId(formSchemeDefine.getKey());
                String consSystemId = this.consolidatedTaskService.getConsolidatedSystemIdBySchemeId(formSchemeDefine.getKey(), queryParamsVO.getPeriodStr());
                queryParamsVO.setConsSystemId(consSystemId);
            }
        }
    }

    private void doFvchCarryOver(Map<String, Object> newCurrYearInvest, QueryParamsVO queryParamsVO, String lastYearSrcId) {
        Map<String, Object> fvchMasterData = this.fairValueBillDao.getMasterByYearAndSrcId(queryParamsVO.getAcctYear() - 1, lastYearSrcId);
        if (fvchMasterData == null || fvchMasterData.isEmpty()) {
            return;
        }
        List fvchFixedItemBillList = InvestBillTool.getBillItemByMasterId((String)((String)fvchMasterData.get("ID")), (String)"GC_FVCH_FIXEDITEM");
        List fvchOtherItemBillList = InvestBillTool.getBillItemByMasterId((String)((String)fvchMasterData.get("ID")), (String)"GC_FVCH_OTHERITEM");
        this.initNewFvchBill(fvchMasterData, newCurrYearInvest);
        fvchFixedItemBillList.forEach(fvchFixedItem -> this.initNewFvchItem((Map<String, Object>)fvchFixedItem, fvchMasterData, queryParamsVO));
        fvchOtherItemBillList.forEach(fvchOtherItem -> this.initNewFvchItem((Map<String, Object>)fvchOtherItem, fvchMasterData, queryParamsVO));
        BillModelImpl billModel = this.createBillModel("GCBILL_B_FVCHBILL");
        billModel.getRuler().getRulerExecutor().setEnable(true);
        billModel.add();
        fvchMasterData.remove("ID");
        billModel.getMaster().setData(fvchMasterData);
        fvchFixedItemBillList.forEach(item -> item.put("MASTERID", billModel.getMaster().getValue("ID")));
        fvchOtherItemBillList.forEach(item -> item.put("MASTERID", billModel.getMaster().getValue("ID")));
        billModel.getTable("GC_FVCH_FIXEDITEM").setRowsData(fvchFixedItemBillList);
        billModel.getTable("GC_FVCH_OTHERITEM").setRowsData(fvchOtherItemBillList);
        try {
            this.doBillSave(billModel);
        }
        catch (BillException e) {
            List checkMessages = e.getCheckMessages();
            String checkMessage = CollectionUtils.isEmpty((Collection)checkMessages) ? "" : "\u6821\u9a8c\u7ed3\u679c:" + checkMessages.stream().map(item -> item.getCheckMessage()).collect(Collectors.joining(";"));
            this.logError(String.format("\u516c\u5141\u53f0\u8d26\u4fdd\u5b58\u5931\u8d25\uff0c\u539f\u56e0\u4e3a\uff1a%1s; %2s", e.getMessage(), checkMessage), this.plusWeight(1));
            this.logger.error("\u516c\u5141\u53f0\u8d26\u4fdd\u5b58\u5931\u8d25:" + e.getMessage(), e);
        }
        catch (Exception e) {
            this.logError("\u516c\u5141\u53f0\u8d26\u4fdd\u5b58\u5931\u8d25\uff0c\u539f\u56e0\u4e3a\uff1a" + e.getMessage(), this.plusWeight(1));
            this.logger.error("\u516c\u5141\u53f0\u8d26\u4fdd\u5b58\u5931\u8d25:" + e.getMessage(), e);
        }
    }

    private void initNewFvchBill(Map<String, Object> fvchMasterData, Map<String, Object> newCurrYearInvest) {
        fvchMasterData.put("SRCID", newCurrYearInvest.get("SRCID"));
        fvchMasterData.put("SRCTYPE", SRC_TYPE);
        fvchMasterData.put("ACCTYEAR", newCurrYearInvest.get("ACCTYEAR"));
        fvchMasterData.put("BILLCODE", this.getBillCode("GCBILL_B_FVCHBILL", (String)fvchMasterData.get("UNITCODE")));
        fvchMasterData.put("CREATETIME", new Date());
        fvchMasterData.put("BILLDATE", new Date());
    }

    private void initNewFvchItem(Map<String, Object> fvchFixedItemMap, Map<String, Object> fvchBillMap, QueryParamsVO queryParamsVO) {
        fvchFixedItemMap.put("MASTERID", fvchBillMap.get("ID"));
        fvchFixedItemMap.put("SRCID", fvchFixedItemMap.get("ID"));
        fvchFixedItemMap.put("ID", UUIDOrderUtils.newUUIDStr());
        fvchFixedItemMap.put("ACCTYEAR", fvchBillMap.get("ACCTYEAR"));
        fvchFixedItemMap.put("SRCTYPE", SRC_TYPE);
        fvchFixedItemMap.put("BILLCODE", fvchBillMap.get("BILLCODE"));
        this.excuteCarryOverSettingCalc(fvchFixedItemMap, queryParamsVO, AccoutTypeEnum.FAIRVALUE.getCode());
    }

    private boolean doInvestCarryOver(Map<String, Object> investBill, QueryParamsVO queryParamsVO, Set<String> currYearUnitAndInvestedUnitKey, Set<String> currYearNeedUpdateInvestSrcIDSet, Set<String> masterBillDesignViewFieldSet, GcOrgCenterService orgTool, StringBuffer unitLog) {
        String unitCode = (String)investBill.get("UNITCODE");
        String investedUnit = (String)investBill.get("INVESTEDUNIT");
        GcOrgCacheVO investUnitOrg = orgTool.getOrgByID((String)investBill.get("UNITCODE"));
        GcOrgCacheVO investedUnitOrg = orgTool.getOrgByID((String)investBill.get("INVESTEDUNIT"));
        String unitStr = unitCode + "|" + (investUnitOrg == null ? " " : investUnitOrg.getTitle());
        String investedUnitStr = investedUnit + "|" + (investedUnitOrg == null ? " " : investedUnitOrg.getTitle());
        this.logInfo(GcI18nUtil.getMessage((String)"gc.invest.carryover.unit.execution.investunit_and_investedunit", (Object[])new Object[]{unitStr, investedUnitStr}), Float.valueOf(0.2f));
        if (investBill.get("DISPOSEDATE") != null) {
            this.logInfo(GcI18nUtil.getMessage((String)"gc.invest.carryover.unit.execution.hasdisposed", (Object[])new Object[]{unitStr, investedUnitStr}), this.plusWeight(3));
            return false;
        }
        if (!currYearNeedUpdateInvestSrcIDSet.contains(investBill.get("ID")) && currYearUnitAndInvestedUnitKey.contains(unitCode + "_" + investedUnit)) {
            this.logInfo(GcI18nUtil.getMessage((String)"gc.invest.carryover.unit.execution.data.already.exists", (Object[])new Object[]{unitStr, investedUnitStr}), this.plusWeight(3));
            return false;
        }
        BillModelImpl billModel = this.createBillModel("GCBILL_B_INVESTBILL");
        billModel.getRuler().getRulerExecutor().setEnable(true);
        if (currYearNeedUpdateInvestSrcIDSet.contains(investBill.get("ID"))) {
            Map<String, Object> masterData = this.investBillDao.getInvestBySrcIdAndPeriod((String)investBill.get("ID"), 1);
            billModel.loadByCode((String)masterData.get("BILLCODE"));
            billModel.edit();
            this.initNewInvestBill(investBill, queryParamsVO);
            masterBillDesignViewFieldSet.addAll(Arrays.asList("ACCTYEAR", "SRCID"));
            investBill.entrySet().removeIf(entry -> !masterBillDesignViewFieldSet.contains(entry.getKey()));
            investBill.forEach((field, fieldValue) -> billModel.getMaster().setValue(field, fieldValue));
            this.logInfo(GcI18nUtil.getMessage((String)"gc.invest.carryover.unit.execution.subdata.already.exists", (Object[])new Object[]{unitStr, investedUnitStr}), this.plusWeight(3));
        } else {
            billModel.add();
            this.initNewInvestBill(investBill, queryParamsVO);
            billModel.getMaster().setData(investBill);
        }
        try {
            this.doBillSave(billModel);
        }
        catch (BillException e) {
            List checkMessages = e.getCheckMessages();
            String checkMessage = CollectionUtils.isEmpty((Collection)checkMessages) ? "" : "\u6821\u9a8c\u7ed3\u679c:" + checkMessages.stream().map(item -> item.getCheckMessage()).collect(Collectors.joining(";"));
            this.logError(String.format("\u4fdd\u5b58\u5931\u8d25\uff0c\u539f\u56e0\u4e3a\uff1a%1s; %2s", e.getMessage(), checkMessage), this.plusWeight(1));
            this.logger.error("\u4fdd\u5b58\u5931\u8d25:" + e.getMessage(), e);
            return false;
        }
        catch (Exception e) {
            this.logError("\u4fdd\u5b58\u5931\u8d25\uff0c\u539f\u56e0\u4e3a\uff1a" + e.getMessage(), this.plusWeight(1));
            this.logger.error("\u4fdd\u5b58\u5931\u8d25:" + e.getMessage(), e);
            return false;
        }
        this.logInfo(GcI18nUtil.getMessage((String)"gc.invest.carryover.unit.execution.investunit_and_investedunit_complete", (Object[])new Object[]{unitStr, investedUnitStr}), this.plusWeight(1));
        unitLog.append(String.format(" \u6295\u8d44\u5355\u4f4d\uff1a%1s, \u88ab\u6295\u8d44\u5355\u4f4d\uff1a%2s", unitStr, investedUnitStr));
        return true;
    }

    private GcOrgCenterService getOrgCenterService(String periodStr) {
        YearPeriodObject yp = new YearPeriodObject(null, periodStr);
        return GcOrgPublicTool.getInstance((String)OrgUtil.getOrgType((String)"GC_INVESTBILL"), (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
    }

    private void doBillSave(BillModelImpl billModel) {
        ActionManager actionManager = (ActionManager)SpringContextUtils.getBean(ActionManager.class);
        ActionResponse response = new ActionResponse();
        ActionRequest request = new ActionRequest();
        Action action = (Action)actionManager.get("bill-save");
        HashMap<String, Boolean> params1 = new HashMap<String, Boolean>();
        params1.put("datasync", true);
        request.setParams(params1);
        billModel.executeAction(action, request, response);
    }

    private Set<String> getCurrYearUnitAndInvestedUnitKey(int acctYear, List<String> orgCodeList) {
        List<Map<String, Object>> currYearInvestBills = this.investBillDao.getByYear(acctYear, 0, orgCodeList);
        Set<String> currYearUnitAndInvestedUnitKey = currYearInvestBills.stream().map(currYearInvestItem -> (String)currYearInvestItem.get("UNITCODE") + "_" + currYearInvestItem.get("INVESTEDUNIT")).collect(Collectors.toSet());
        return currYearUnitAndInvestedUnitKey;
    }

    private List<String> getOrgCodeList(QueryParamsVO queryParamsVO) {
        List<String> orgCodeList = queryParamsVO.getOrgList().stream().map(orgCacheVO -> orgCacheVO.getCode()).collect(Collectors.toList());
        return orgCodeList;
    }

    private Set<String> getNeedUpdateInvestSrcIDSet(Integer acctYear, List<String> orgCodeList) {
        List<Map<String, Object>> returnData = this.investBillDao.listSubItemsOfManual(acctYear, orgCodeList);
        Set<String> srcIdSet = returnData.stream().map(item -> (String)item.get("SRCID")).collect(Collectors.toSet());
        return srcIdSet;
    }

    private void excuteSave(BillModelImpl billModel) {
        ActionRequest request = new ActionRequest();
        request.setParams(new HashMap());
        ActionResponse response = new ActionResponse();
        SaveAction saveAction = (SaveAction)SpringContextUtils.getBean(SaveAction.class);
        billModel.executeAction((Action)saveAction, request, response);
    }

    private void delCarryOverData(QueryParamsVO queryParamsVO, List<String> orgCodeList) {
        String sql = " SELECT m.srcid  FROM gc_investbill m  \nLEFT JOIN gc_investbillitem s ON m.id = s.masterid  \nWHERE m.srctype = 33  and m.acctyear=? and %1$s AND s.masterid IS NULL";
        String whereCondition = SqlUtils.getConditionOfIdsUseOr(orgCodeList, (String)"m.UNITCODE");
        String formatSQL = String.format(sql, whereCondition);
        List returnMap = EntNativeSqlDefaultDao.getInstance().selectMap(formatSQL, new Object[]{queryParamsVO.getAcctYear()});
        Set<String> srcIdSet = returnMap.stream().map(item -> (String)item.get("SRCID")).collect(Collectors.toSet());
        sql = "SELECT m.srcid  FROM gc_investbill m JOIN gc_investbillitem s ON m.id = s.masterid  WHERE m.srctype = 33  and m.acctyear=?  and %1$s";
        formatSQL = String.format(sql, whereCondition);
        List notDelSrcIdList = EntNativeSqlDefaultDao.getInstance().selectMap(formatSQL, new Object[]{queryParamsVO.getAcctYear()});
        Set notDelsrcIdSet = notDelSrcIdList.stream().map(item -> (String)item.get("SRCID")).collect(Collectors.toSet());
        srcIdSet.removeAll(notDelsrcIdSet);
        int count = this.investBillDao.deleteBySrcIds(srcIdSet, queryParamsVO.getAcctYear());
        this.logger.info("\u6295\u8d44\u4e3b\u8868\u5220\u9664\u4e86{} \u6761\u8bb0\u5f55", (Object)srcIdSet.size());
        count = this.investBillDao.deleteByYearAndUnit("GC_FVCH_FIXEDITEM", queryParamsVO.getAcctYear(), SRC_TYPE, orgCodeList);
        this.logger.info("\u516c\u5141\u5355\u636e\u56fa\u5b9a\u65e0\u5f62\u8d44\u4ea7\u8868\u5220\u9664\u4e86{} \u6761\u8bb0\u5f55", (Object)count);
        count = this.investBillDao.deleteByYearAndUnit("GC_FVCH_OTHERITEM", queryParamsVO.getAcctYear(), SRC_TYPE, orgCodeList);
        this.logger.info("\u516c\u5141\u5355\u636e\u5176\u5b83\u8d44\u4ea7\u7c7b\u8868\u5220\u9664\u4e86{} \u6761\u8bb0\u5f55", (Object)count);
        count = this.investBillDao.deleteByYearAndUnit("GC_FVCHBILL", queryParamsVO.getAcctYear(), SRC_TYPE, orgCodeList);
        this.logger.info("\u516c\u5141\u5355\u636e\u4e3b\u8868\u5220\u9664\u4e86{} \u6761\u8bb0\u5f55", (Object)count);
    }

    private void initNewInvestBill(Map<String, Object> itemMap, QueryParamsVO queryParamsVO) {
        itemMap.put("ACCTYEAR", queryParamsVO.getAcctYear());
        itemMap.put("PERIOD", 1);
        itemMap.put("SRCID", itemMap.get("ID"));
        itemMap.put("SRCTYPE", SRC_TYPE);
        itemMap.remove("ID");
        itemMap.put("BILLCODE", this.getBillCode("GCBILL_B_INVESTBILL", (String)itemMap.get("UNITCODE")));
        itemMap.put("CREATETIME", new Date());
        itemMap.put("BILLDATE", new Date());
        this.excuteCarryOverSettingCalc(itemMap, queryParamsVO, AccoutTypeEnum.INVESTMENT.getCode());
    }

    private void excuteCarryOverSettingCalc(Map<String, Object> itemMap, QueryParamsVO queryParamsVO, String accoutType) {
        List<Object> carryOverSettingVOS = this.carryOverSettingService.listSettings(queryParamsVO.getCarryOverSchemeId());
        carryOverSettingVOS = carryOverSettingVOS.stream().filter(item -> accoutType.equals(item.getAccountType())).collect(Collectors.toList());
        for (InvestBillCarryOverSettingVO investBillCarryOverSettingVO : carryOverSettingVOS) {
            String carryOverMode = investBillCarryOverSettingVO.getCarryOverMode();
            AccoutCarryOverModeEnum carryOverModeEnum = AccoutCarryOverModeEnum.getEnumBycode(carryOverMode);
            switch (carryOverModeEnum) {
                case END: {
                    this.excuteEndCarryOver(investBillCarryOverSettingVO, itemMap);
                    break;
                }
                case CHANGE: {
                    this.excuteChangeCarryOver(investBillCarryOverSettingVO, itemMap);
                    break;
                }
                case CALCFORMULA: {
                    this.excuteFormulaCarryOver(investBillCarryOverSettingVO, itemMap, queryParamsVO);
                    break;
                }
                case CHANGEZERO: {
                    this.excuteChangeZeroCarryOver(investBillCarryOverSettingVO, itemMap);
                    break;
                }
            }
        }
    }

    private void excuteChangeZeroCarryOver(InvestBillCarryOverSettingVO carryOverSettingVO, Map<String, Object> itemMap) {
        itemMap.put(carryOverSettingVO.getTargetField(), 0.0);
    }

    private void excuteFormulaCarryOver(InvestBillCarryOverSettingVO carryOverSettingVO, Map<String, Object> investMap, QueryParamsVO queryParamsVO) {
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
        String calcformula = carryOverSettingVO.getFormula();
        if (StringUtils.isEmpty((String)calcformula)) {
            return;
        }
        if (calcformula.contains(this.rateValueFunction.name())) {
            decimal = CommonRateUtils.getRateValueFieldFractionDigits();
        }
        try {
            double res = this.billFormulaEvalService.evaluateInvestBillData((GcCalcEnvContext)env, dset, calcformula, investBillGroupDTO, decimal);
            investMap.put(carryOverSettingVO.getTargetField(), res);
        }
        catch (Exception e) {
            this.logger.error("\u53f0\u8d26\u5e74\u7ed3\u6267\u884c\u8fd0\u7b97\u516c\u5f0f\u5931\u8d25\uff1a" + e.getMessage(), e);
            LogHelper.error((String)"\u5408\u5e76-\u5355\u636e\u53f0\u8d26\u5e74\u7ed3", (String)"\u53f0\u8d26\u5e74\u7ed3-\u8fd0\u7b97\u516c\u5f0f", (String)e.getMessage());
        }
    }

    private void excuteChangeCarryOver(InvestBillCarryOverSettingVO carryOverSettingVO, Map<String, Object> itemMap) {
        String targetField = carryOverSettingVO.getTargetField();
        double sourceFieldVal = this.getFieldValue(itemMap, carryOverSettingVO.getSourceField());
        double lastAddValue = this.getFieldValue(itemMap, carryOverSettingVO.getSourceAddField());
        double lastReduceValue = this.getFieldValue(itemMap, carryOverSettingVO.getSourceReduceField());
        double targetFieldVal = NumberUtils.sub((double)NumberUtils.sum((double)sourceFieldVal, (double)lastAddValue), (double)lastReduceValue);
        itemMap.put(targetField, targetFieldVal);
    }

    private void excuteEndCarryOver(InvestBillCarryOverSettingVO carryOverSettingVO, Map<String, Object> itemMap) {
        String sourceField = carryOverSettingVO.getSourceField();
        String targetField = carryOverSettingVO.getTargetField();
        itemMap.put(targetField, itemMap.get(sourceField));
    }

    private BillModelImpl createBillModel(String billDefineName) {
        BillContextImpl billContext = new BillContextImpl();
        billContext.setTenantName(ShiroUtil.getTenantName());
        billContext.setDisableVerify(true);
        BillDefineService billDefineService = (BillDefineService)SpringContextUtils.getBean(BillDefineService.class);
        BillModelImpl model = (BillModelImpl)billDefineService.createModel((BillContext)billContext, billDefineName);
        model.getRuler().getRulerExecutor().setEnable(true);
        return model;
    }

    private Object getBillCode(String uniqueCode, String unitCode) {
        return InvestBillTool.getBillCode((String)uniqueCode, (String)unitCode);
    }

    private Set<String> getBillDesignViewFieldSet() {
        BillInfoVo billInfoVo = BillParseTool.parseBillInfo((String)"GCBILL_B_INVESTBILL");
        LinkedHashSet masterColumnCodes = billInfoVo.getMasterColumnCodes();
        return masterColumnCodes;
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
}

