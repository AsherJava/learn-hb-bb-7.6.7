/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.http.PageInfo
 *  com.jiuqi.common.base.util.ConverterUtils
 *  com.jiuqi.common.base.util.DateCommonFormatEnum
 *  com.jiuqi.common.base.util.DateUtils
 *  com.jiuqi.common.base.util.NumberUtils
 *  com.jiuqi.gcreport.billcore.service.CommonBillService
 *  com.jiuqi.gcreport.billcore.util.BillParseTool
 *  com.jiuqi.gcreport.billcore.util.InvestBillTool
 *  com.jiuqi.gcreport.billcore.vo.BillInfoVo
 *  com.jiuqi.gcreport.common.util.NrTool
 *  com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 *  com.jiuqi.gcreport.offsetitem.init.dao.GcOffSetVchrItemInitDao
 *  com.jiuqi.gcreport.offsetitem.init.entity.GcOffSetVchrItemInitEO
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.nvwa.definition.common.ColumnModelType
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.IModelDefineItem
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.invest.investbill.service.impl;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.http.PageInfo;
import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.common.base.util.DateCommonFormatEnum;
import com.jiuqi.common.base.util.DateUtils;
import com.jiuqi.common.base.util.NumberUtils;
import com.jiuqi.gcreport.billcore.service.CommonBillService;
import com.jiuqi.gcreport.billcore.util.BillParseTool;
import com.jiuqi.gcreport.billcore.util.InvestBillTool;
import com.jiuqi.gcreport.billcore.vo.BillInfoVo;
import com.jiuqi.gcreport.common.util.NrTool;
import com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import com.jiuqi.gcreport.invest.investbill.dao.FairValueBillDao;
import com.jiuqi.gcreport.invest.investbill.dao.InvestBillDao;
import com.jiuqi.gcreport.invest.investbill.service.InvestBillService;
import com.jiuqi.gcreport.offsetitem.init.dao.GcOffSetVchrItemInitDao;
import com.jiuqi.gcreport.offsetitem.init.entity.GcOffSetVchrItemInitEO;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.nvwa.definition.common.ColumnModelType;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.IModelDefineItem;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

@Service
public class InvestBillServiceImpl
implements InvestBillService {
    private static final Logger logger = LoggerFactory.getLogger(InvestBillServiceImpl.class);
    @Autowired
    private InvestBillDao investBillDao;
    @Autowired
    private FairValueBillDao fairValueBillDao;
    @Autowired
    private CommonBillService commonBillService;
    @Autowired
    private GcOffSetVchrItemInitDao offSetVchrItemInitDao;

    @Override
    public PageInfo<Map<String, Object>> listInvestBills(Map<String, Object> params) {
        int pageSize = (Integer)params.get("pageSize");
        int pageNum = (Integer)params.get("pageNum");
        int offset = (pageNum - 1) * pageSize;
        int totalCount = this.investBillDao.countInvestBills(params);
        if (totalCount <= 0) {
            return PageInfo.of(new ArrayList(), (int)(offset / pageSize + 1), (int)pageSize, (int)0);
        }
        List<Map<String, Object>> records = this.investBillDao.listInvestBillsByPaging(params);
        InvestBillTool.formatBillContent(records, params, (String)"GC_INVESTBILL");
        records.get(0).put("tabDisplayObj", this.tabDisplayData(params));
        return PageInfo.of(records, (int)(offset / pageSize + 1), (int)pageSize, (int)totalCount);
    }

    @Override
    public List<Map<String, Object>> listInvests(Map<String, Object> params) {
        List<Map<String, Object>> investList = this.investBillDao.listInvests(params);
        InvestBillTool.formatBillContent(investList, params, (String)"GC_INVESTBILL");
        return investList;
    }

    private Map<String, Object> tabDisplayData(Map<String, Object> params) {
        params.put("pageSize", -1);
        params.put("pageNum", -1);
        List<Map<String, Object>> recordsTemp = this.investBillDao.listInvestBillsByPaging(params);
        double endBookBalance = 0.0;
        double beginBookbalance = 0.0;
        for (Map<String, Object> item : recordsTemp) {
            beginBookbalance = NumberUtils.sum((double)beginBookbalance, (double)ConverterUtils.getAsDoubleValue((Object)item.get("BEGINBOOKBALANCE")));
            if (null != item.get("DISPOSEDATE")) continue;
            endBookBalance = NumberUtils.sum((double)endBookBalance, (double)ConverterUtils.getAsDoubleValue((Object)item.get("ENDBOOKBALANCE")));
        }
        double changeAmt = NumberUtils.sub((double)endBookBalance, (double)beginBookbalance);
        double changeRatio = 0.0;
        if (!NumberUtils.isZreo((Double)beginBookbalance)) {
            changeRatio = NumberUtils.div((double)changeAmt, (double)beginBookbalance, (int)6);
        }
        HashMap<String, Object> tabDisplayObj = new HashMap<String, Object>();
        tabDisplayObj.put("beginBookbalance", beginBookbalance);
        tabDisplayObj.put("endBookBalance", endBookBalance);
        tabDisplayObj.put("changeAmt", changeAmt);
        tabDisplayObj.put("changeRatio", changeRatio);
        return tabDisplayObj;
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void batchDelete(List<String> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return;
        }
        List<Map<String, Object>> investBills = this.investBillDao.listInvestBillsByIds(ids);
        ArrayList<String> delInvestIdList = new ArrayList<String>();
        for (Map<String, Object> investBill : investBills) {
            String srcId = (String)investBill.get("SRCID");
            int period = ConverterUtils.getAsIntValue((Object)investBill.get("PERIOD"));
            List<Map<String, Object>> investBillList = this.investBillDao.listByWhere(new String[]{"SRCID"}, new Object[]{srcId});
            investBillList.sort(Comparator.comparing(item -> ConverterUtils.getAsIntValue(item.get("PERIOD"))));
            if (period == ConverterUtils.getAsIntValue((Object)investBillList.get(0).get("PERIOD"))) {
                List fvchMasterData = InvestBillTool.listByWhere((String[])new String[]{"SRCID"}, (Object[])new Object[]{srcId}, (String)"GC_FVCHBILL");
                if (!CollectionUtils.isEmpty(fvchMasterData)) {
                    throw new BusinessRuntimeException("\u8bf7\u5148\u5220\u9664\u516c\u5141\u4ef7\u503c\u8c03\u6574\u7684\u8bb0\u5f55");
                }
                GcOffSetVchrItemInitEO entity = new GcOffSetVchrItemInitEO();
                entity.setSrcOffsetGroupId(srcId);
                if (this.offSetVchrItemInitDao.countByEntity((BaseEntity)entity) > 0) {
                    throw new BusinessRuntimeException("\u8bf7\u5148\u5220\u9664\u5206\u5f55\u521d\u59cb\u5316\u8bb0\u5f55");
                }
            }
            List<String> investBillIds = this.investBillDao.getInvestIdsBySrcIdAndBeginPeriod(srcId, period);
            delInvestIdList.addAll(investBillIds);
            this.addInvestBillDelLog(investBill);
        }
        this.investBillDao.batchDeleteByIdList(delInvestIdList);
    }

    @Override
    public String getIdByUnitAndYear(String investUnitCode, String investedUnitCode, int acctYear) {
        return this.investBillDao.getIdByUnitAndYear(investUnitCode, investedUnitCode, acctYear);
    }

    @Override
    public void updateFairValueAdjustFlag(String investUnit, String investedUnit, Integer acctYear, int fairValueAdjustFlag) {
        this.investBillDao.updateFairValueAdjustFlag(investUnit, investedUnit, acctYear, fairValueAdjustFlag);
    }

    @Override
    public Map<String, Object> checkInvestBillOffset(String unitType, String mergeId, String investBillId, String periodStr) {
        Map<String, Object> resultMap = this.investBillDao.getInvestBillById(investBillId);
        if (CollectionUtils.isEmpty(resultMap)) {
            throw new BusinessRuntimeException("\u53f0\u8d26\u6570\u636e\u4e0d\u5b58\u5728\uff0c\u8bf7\u68c0\u67e5\u6570\u636e");
        }
        int acctYear = ConverterUtils.getAsInteger((Object)resultMap.get("ACCTYEAR"));
        String investUnit = (String)resultMap.get("UNITCODE");
        String investedUnit = (String)resultMap.get("INVESTEDUNIT");
        return this.commonBillService.checkCommonParent(unitType, periodStr, mergeId, investUnit, investedUnit);
    }

    @Override
    public Map<String, Object> getByUnitAndYear(String investUnitCode, String investedUnitCode, int acctYear) {
        return this.investBillDao.getByUnitAndYear(investUnitCode, investedUnitCode, acctYear);
    }

    @Override
    public void updateOffsetStatus(String investBillId, int offsetStatus) {
        this.investBillDao.updateOffsetStatus(investBillId, offsetStatus);
    }

    @Override
    public void updateFairValueOffsetStatus(String investBillId, int FairValueOffsetStatus2) {
        this.investBillDao.updateFairValueOffsetStatus(investBillId, FairValueOffsetStatus2);
    }

    @Override
    public void updateDisPoseDate(Date disposeDate, List<String> investBillIds) {
        Map<String, Object> investBillMap = null;
        String operateType = null == disposeDate ? "\u542f\u52a8" : "\u5904\u7f6e";
        HashSet<String> srcIdSet = new HashSet<String>();
        for (String investBillId : investBillIds) {
            investBillMap = this.investBillDao.getInvestBillById(investBillId);
            String unitCode = (String)investBillMap.get("UNITCODE");
            String investedUnitCode = (String)investBillMap.get("INVESTEDUNIT");
            int acctYear = ConverterUtils.getAsIntValue((Object)investBillMap.get("ACCTYEAR"));
            int period = ConverterUtils.getAsIntValue((Object)investBillMap.get("PERIOD"));
            String srcId = ConverterUtils.getAsString((Object)investBillMap.get("SRCID"));
            String[] columnNamesInDB = new String[]{"UNITCODE", "INVESTEDUNIT", "ACCTYEAR", "PERIOD"};
            Object[] values = new Object[]{investBillMap.get("UNITCODE"), investBillMap.get("INVESTEDUNIT"), acctYear, period};
            List investBillList = InvestBillTool.listByWhere((String[])columnNamesInDB, (Object[])values, (String)"GC_INVESTBILL");
            if (CollectionUtils.isEmpty(investBillList = investBillList.stream().filter(investBill -> !srcId.equals((String)investBill.get("SRCID")) && !ConverterUtils.getAsBooleanValue(investBill.get("DISPOSEFLAG"))).collect(Collectors.toList()))) {
                String operateTypeTitle = String.format("%1s-\u5e74\u5ea6%2s-\u6295\u8d44\u5355\u4f4d%3s-\u88ab\u6295\u8d44\u5355\u4f4d%4s", operateType, acctYear, unitCode, investedUnitCode);
                LogHelper.info((String)"\u5408\u5e76-\u6295\u8d44\u53f0\u8d26", (String)operateTypeTitle, (String)String.format("\u6295\u8d44\u5355\u4f4d\uff1a%1s, \u88ab\u6295\u8d44\u5355\u4f4d\uff1a%2s", unitCode, investedUnitCode));
                srcIdSet.add(ConverterUtils.getAsString((Object)investBillMap.get("SRCID")));
                continue;
            }
            throw new BusinessRuntimeException("\u5b58\u5728\u5df2\u542f\u7528\u7684\u53f0\u8d26");
        }
        this.investBillDao.updateDisPoseDate(disposeDate, srcIdSet);
    }

    @Override
    public List<DefaultTableEntity> getMastByInvestAndInvestedUnit(Set<String> investUnit, Set<String> investedUnit, int acctYear, int period) {
        return this.investBillDao.getMastByInvestAndInvestedUnit(investUnit, investedUnit, acctYear, period);
    }

    @Override
    public Map<String, Double> listInvestedCompreEquityRatio(int acctYear) {
        List<Map<String, Object>> result = this.investBillDao.listInvestedCompreEquityRatio(acctYear);
        HashMap<String, Double> oppUnitCode2EquityRadio = new HashMap<String, Double>();
        for (Map<String, Object> record : result) {
            String oppUnitCode = (String)record.get("INVESTEDUNIT");
            Double equityRadio = (Double)record.get("INVESTEDCOMPREEQUITYRATIO");
            if (null == equityRadio) continue;
            oppUnitCode2EquityRadio.put(oppUnitCode, equityRadio);
        }
        return oppUnitCode2EquityRadio;
    }

    @Override
    public Map<String, Object> queryHistoryChangeRecord(Map<String, Object> params) {
        Map<String, Object> investBillMap = this.investBillDao.getInvestBillById((String)params.get("investBillId"));
        String unitCode = (String)investBillMap.get("UNITCODE");
        String investedUnit = (String)investBillMap.get("INVESTEDUNIT");
        int acctYear = ConverterUtils.getAsIntValue((Object)investBillMap.get("ACCTYEAR"));
        String[] columns = new String[]{"UNITCODE", "INVESTEDUNIT", "PERIOD"};
        List<Map<String, Object>> historyYearInvestBills = this.investBillDao.listByWhere(columns, new Object[]{unitCode, investedUnit, 12});
        historyYearInvestBills = historyYearInvestBills.stream().filter(item -> ConverterUtils.getAsIntValue(item.get("ACCTYEAR")) < acctYear).collect(Collectors.toList());
        HashMap<String, Object> returnMap = new HashMap<String, Object>();
        ArrayList rowDatas = new ArrayList();
        historyYearInvestBills.add(investBillMap);
        for (Map<String, Object> investItem : historyYearInvestBills) {
            List subItems = InvestBillTool.getBillItemByMasterId((String)((String)investItem.get("ID")), (String)"GC_INVESTBILLITEM");
            String srcId = (String)investItem.get("SRCID");
            String[] columnNamesInDB = new String[]{"SRCID", "ACCTYEAR", "PERIOD"};
            for (Map subItem : subItems) {
                Date inputDate = (Date)subItem.get("INPUTDATE");
                Date changeDate = (Date)subItem.get("CHANGEDATE");
                InvestBillTool.formatBillContent(Arrays.asList(subItem), params, (String)"GC_INVESTBILLITEM");
                subItem.put("INPUTDATE", DateUtils.format((Date)inputDate, (DateCommonFormatEnum)DateCommonFormatEnum.MONTH_CHAR_BY_DASH));
                subItem.put("CHANGEDATE", DateUtils.format((Date)changeDate, (DateCommonFormatEnum)DateCommonFormatEnum.MONTH_CHAR_BY_DASH));
                Object[] values = new Object[]{srcId, investItem.get("ACCTYEAR"), inputDate.getMonth() + 1};
                List<Map<String, Object>> masterData = this.investBillDao.listByWhere(columnNamesInDB, values);
                InvestBillTool.formatBillContent(masterData, params, (String)"GC_INVESTBILL");
                subItem.putAll(masterData.get(0));
            }
            if (CollectionUtils.isEmpty(subItems)) continue;
            rowDatas.addAll(subItems);
        }
        BillInfoVo billInfoVo = BillParseTool.parseBillInfo((String)((String)params.get("defineCode")));
        List subColumnCodes = billInfoVo.getSubColumnCodes();
        ArrayList subColumns = new ArrayList();
        List columnModelDefines = NrTool.queryAllColumnsInTable((String)"GC_INVESTBILLITEM");
        Map<String, ColumnModelDefine> columnCode2FieldDefineMap = columnModelDefines.stream().collect(Collectors.toMap(IModelDefineItem::getCode, item -> item, (v1, v2) -> v2));
        LinkedHashSet<String> subColumnCodeSet = new LinkedHashSet<String>(Arrays.asList("INPUTDATE", "CHANGEDATE"));
        subColumnCodeSet.addAll((Collection)subColumnCodes.get(0));
        for (String columnCode : subColumnCodeSet) {
            HashMap<String, String> columnInfo = new HashMap<String, String>(8);
            ColumnModelDefine define = columnCode2FieldDefineMap.get(columnCode);
            if (null == define) {
                logger.info(String.format("\u5728%1s\u8868\u4e2d \u672a\u67e5\u8be2\u5230\u5b57\u6bb5\u6a21\u578b\uff1a%2s", "GC_INVESTBILLITEM", columnCode));
                continue;
            }
            columnInfo.put("key", columnCode);
            columnInfo.put("label", define.getTitle());
            columnInfo.put("align", "left");
            columnInfo.put("width", "110");
            columnInfo.put("columnType", define.getColumnType().name());
            if (define.getColumnType() == ColumnModelType.DOUBLE || define.getColumnType() == ColumnModelType.INTEGER || define.getColumnType() == ColumnModelType.BIGDECIMAL) {
                columnInfo.put("align", "right");
            }
            subColumns.add(columnInfo);
        }
        returnMap.put("subColumns", subColumns);
        returnMap.put("rowDatas", rowDatas);
        return returnMap;
    }

    @Override
    public List<Map<String, Object>> listByWhere(String[] columnNamesInDB, Object[] values) {
        return this.investBillDao.listByWhere(columnNamesInDB, values);
    }

    private void checkFvchBill(List<String> ids) {
        List<Map<String, Object>> investList = this.investBillDao.listInvestBillsByIds(ids);
        for (int i = 0; i < investList.size(); ++i) {
            int acctYear;
            String investedUnit;
            Map<String, Object> stringObjectMap = investList.get(i);
            String unitCode = (String)stringObjectMap.get("UNITCODE");
            if (this.fairValueBillDao.getByUnitAndYear(unitCode, investedUnit = (String)stringObjectMap.get("INVESTEDUNIT"), acctYear = ConverterUtils.getAsIntValue((Object)stringObjectMap.get("ACCTYEAR"))) == null) continue;
            throw new BusinessRuntimeException("\u8bf7\u5148\u5220\u9664\u5df2\u5b8c\u6210\u516c\u5141\u4ef7\u503c\u8c03\u6574\u7684\u8bb0\u5f55");
        }
    }

    private void addInvestBillDelLog(Map<String, Object> investBill) {
        String investUnitCode = (String)investBill.get("UNITCODE");
        String investedUnitCode = (String)investBill.get("INVESTEDUNIT");
        int acctYear = ConverterUtils.getAsIntValue((Object)investBill.get("ACCTYEAR"));
        int period1 = ConverterUtils.getAsIntValue((Object)investBill.get("PERIOD"));
        String operateTypeTitle = String.format("\u5220\u9664-\u5e74\u5ea6%1d-\u6708\u4efd%2d-\u6295\u8d44\u5355\u4f4d%3s-\u88ab\u6295\u8d44\u5355\u4f4d%4s", acctYear, period1, investUnitCode, investedUnitCode);
        LogHelper.info((String)"\u5408\u5e76-\u6295\u8d44\u53f0\u8d26", (String)operateTypeTitle, (String)operateTypeTitle);
    }
}

