/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.ConverterUtils
 *  com.jiuqi.common.base.util.DateUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.billcore.common.GcBillModelImpl
 *  com.jiuqi.gcreport.billcore.enums.GcBillStatusEnum
 *  com.jiuqi.gcreport.billcore.util.InvestBillTool
 *  com.jiuqi.gcreport.common.util.UUIDOrderUtils
 *  com.jiuqi.gcreport.i18n.util.GcI18nUtil
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.enums.GcOrgKindEnum
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  com.jiuqi.gcreport.samecontrol.dao.SameCtrlChgOptionDao
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.va.bill.action.SaveAction
 *  com.jiuqi.va.bill.impl.BillContextImpl
 *  com.jiuqi.va.bill.impl.BillModelImpl
 *  com.jiuqi.va.bill.intf.BillContext
 *  com.jiuqi.va.bill.intf.BillDefineService
 *  com.jiuqi.va.bill.intf.BillException
 *  com.jiuqi.va.bill.intf.BillModel
 *  com.jiuqi.va.bill.utils.VerifyUtils
 *  com.jiuqi.va.biz.impl.data.DataRowImpl
 *  com.jiuqi.va.biz.impl.data.DataTableImpl
 *  com.jiuqi.va.biz.intf.action.Action
 *  com.jiuqi.va.biz.intf.action.ActionRequest
 *  com.jiuqi.va.biz.intf.action.ActionResponse
 *  com.jiuqi.va.biz.intf.data.DataRow
 *  com.jiuqi.va.biz.intf.data.DataRowState
 *  com.jiuqi.va.biz.intf.data.DataState
 *  com.jiuqi.va.datamodel.service.VaDataModelPublishedService
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.datamodel.DataModelDO
 *  com.jiuqi.va.domain.datamodel.DataModelDTO
 *  com.jiuqi.va.domain.datamodel.DataModelType$ColumnAttr
 *  org.jetbrains.annotations.NotNull
 */
package com.jiuqi.gcreport.invest.investbill.bill.impl;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.common.base.util.DateUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.billcore.common.GcBillModelImpl;
import com.jiuqi.gcreport.billcore.enums.GcBillStatusEnum;
import com.jiuqi.gcreport.billcore.util.InvestBillTool;
import com.jiuqi.gcreport.common.util.UUIDOrderUtils;
import com.jiuqi.gcreport.i18n.util.GcI18nUtil;
import com.jiuqi.gcreport.invest.investbill.enums.InvestInfoEnum;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.enums.GcOrgKindEnum;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.gcreport.samecontrol.dao.SameCtrlChgOptionDao;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.va.bill.action.SaveAction;
import com.jiuqi.va.bill.impl.BillContextImpl;
import com.jiuqi.va.bill.impl.BillModelImpl;
import com.jiuqi.va.bill.intf.BillContext;
import com.jiuqi.va.bill.intf.BillDefineService;
import com.jiuqi.va.bill.intf.BillException;
import com.jiuqi.va.bill.intf.BillModel;
import com.jiuqi.va.bill.utils.VerifyUtils;
import com.jiuqi.va.biz.impl.data.DataRowImpl;
import com.jiuqi.va.biz.impl.data.DataTableImpl;
import com.jiuqi.va.biz.intf.action.Action;
import com.jiuqi.va.biz.intf.action.ActionRequest;
import com.jiuqi.va.biz.intf.action.ActionResponse;
import com.jiuqi.va.biz.intf.data.DataRow;
import com.jiuqi.va.biz.intf.data.DataRowState;
import com.jiuqi.va.biz.intf.data.DataState;
import com.jiuqi.va.datamodel.service.VaDataModelPublishedService;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.datamodel.DataModelDO;
import com.jiuqi.va.domain.datamodel.DataModelDTO;
import com.jiuqi.va.domain.datamodel.DataModelType;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;

public class InvestBillModelImpl
extends GcBillModelImpl {
    public static final String SKIP_SELFMODEL = "SKIP_SELF_MODEL";

    public void save() {
        DataState dataState = this.getData().getState();
        if (ConverterUtils.getAsBooleanValue((Object)this.getContext().getContextValue(SKIP_SELFMODEL))) {
            super.save();
            return;
        }
        this.check();
        this.judgeMasterStatus();
        int acctPeriod = ConverterUtils.getAsIntValue((Object)this.getMaster().getValue("PERIOD"));
        int acctYear = ConverterUtils.getAsIntValue((Object)this.getMaster().getValue("ACCTYEAR"));
        if (dataState == DataState.NEW) {
            this.hasInvestBillCheck(acctYear, acctPeriod);
            this.saveInvestBill(acctPeriod, acctYear);
        } else if (dataState == DataState.EDIT) {
            this.updateInvestBill(acctPeriod, acctYear);
        }
        this.addInvestBillLog(this.getMaster());
    }

    private void judgeMasterStatus() {
        List subItems = (List)this.getData().getTablesData().get("GC_INVESTBILLITEM");
        if (CollectionUtils.isEmpty((Collection)subItems)) {
            this.getMaster().setValue("STATUS", (Object)"");
            return;
        }
        Set statusSet = subItems.stream().filter(subItem -> {
            String status;
            if (null == subItem) {
                return false;
            }
            if (subItem.get("STATUS") instanceof Map) {
                Map statusMap = (Map)subItem.get("STATUS");
                status = (String)statusMap.get("name");
            } else {
                status = (String)subItem.get("STATUS");
            }
            return !StringUtils.isEmpty((String)status) && !GcBillStatusEnum.DEPRECATED.getCode().equals(status);
        }).map(item -> {
            if (item.get("STATUS") instanceof Map) {
                Map statusMap = (Map)item.get("STATUS");
                return (String)statusMap.get("name");
            }
            return (String)item.get("STATUS");
        }).collect(Collectors.toSet());
        if (CollectionUtils.isEmpty(statusSet) || statusSet.size() == 1 && statusSet.contains(GcBillStatusEnum.HANDLED.getCode())) {
            this.getMaster().setValue("STATUS", (Object)GcBillStatusEnum.HANDLED.getCode());
        } else {
            this.getMaster().setValue("STATUS", (Object)GcBillStatusEnum.UNHANDLED.getCode());
        }
    }

    private void hasInvestBillCheck(int acctYear, int acctPeriod) {
        String[] columnNamesInDB = new String[]{"UNITCODE", "INVESTEDUNIT", "ACCTYEAR", "DISPOSEFLAG"};
        Object[] values = new Object[]{this.getMaster().getValue("UNITCODE"), this.getMaster().getValue("INVESTEDUNIT"), acctYear, 0};
        List investBillList = InvestBillTool.listByWhere((String[])columnNamesInDB, (Object[])values, (String)"GC_INVESTBILL");
        if (CollectionUtils.isEmpty(investBillList = investBillList.stream().filter(investBill -> ConverterUtils.getAsIntValue(investBill.get("PERIOD")) >= acctPeriod).collect(Collectors.toList()))) {
            return;
        }
        throw new BillException(GcI18nUtil.getMessage((String)"gc.calculate.bill.investbill.hasData_invested"));
    }

    private void check() {
        VerifyUtils.verifyBill((BillModel)this, (int)2);
        DataRow dataRow = this.getMaster();
        String investUnitCode = dataRow.getString("UNITCODE");
        String investedUnitCode = dataRow.getString("INVESTEDUNIT");
        int acctYear = Integer.parseInt(dataRow.getString("ACCTYEAR"));
        boolean hasDisposedFlag = Boolean.parseBoolean(dataRow.getString("DISPOSEFLAG"));
        DataRowState dataRowState = dataRow.getState();
        String id = dataRow.getString("ID");
        if (!InvestBillTool.validateUserId((String)dataRow.getString("CREATEUSER"))) {
            dataRow.setValue("CREATEUSER", null);
        }
        if (StringUtils.isEmpty((String)investUnitCode)) {
            throw new BillException(GcI18nUtil.getMessage((String)"gc.calculate.bill.investbill.investUnit.empty"));
        }
        if (investUnitCode.equals(investedUnitCode)) {
            throw new BillException(GcI18nUtil.getMessage((String)"gc.calculate.bill.investbill.investUnitAndInvestedUnit.cannot.same"));
        }
        GcOrgCenterService orgCenterService = GcOrgPublicTool.getInstance((String)this.getOrgType(), (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)new YearPeriodObject(acctYear, Integer.parseInt(dataRow.getString("PERIOD"))));
        GcOrgCacheVO oppUnit = orgCenterService.getOrgByCode(investedUnitCode);
        if (dataRowState.equals((Object)DataRowState.APPENDED) && oppUnit == null) {
            throw new BillException(GcI18nUtil.getMessage((String)"gc.calculate.bill.investbill.investedtUnit.empty"));
        }
        if (null != oppUnit) {
            if (GcOrgKindEnum.UNIONORG.equals((Object)oppUnit.getOrgKind())) {
                throw new BillException(GcI18nUtil.getMessage((String)"gc.calculate.bill.investbill.investedUnit.cannot.mergeUnit"));
            }
            if (GcOrgKindEnum.DIFFERENCE.equals((Object)oppUnit.getOrgKind())) {
                throw new BillException(GcI18nUtil.getMessage((String)"gc.calculate.bill.investbill.investedUnit.cannot.diffUnit"));
            }
        }
        boolean sameCtrlDisposeFlag = false;
        if (!hasDisposedFlag) {
            sameCtrlDisposeFlag = this.sameCtrlDispose(dataRow);
        }
        if (null != oppUnit && !sameCtrlDisposeFlag) {
            this.handleDirectState(dataRow, investUnitCode, investedUnitCode, orgCenterService);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void updateInvestBill(int acctPeriod, int acctYear) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        Set<String> masterNotSystemFieldSet = this.getNotSystemFieldSet("GC_INVESTBILL");
        Map<String, Object> masterUpdateData = new HashMap();
        if (!CollectionUtils.isEmpty((Collection)((DataTableImpl)this.getData().getTables().get("GC_INVESTBILL")).getUpdateData())) {
            masterUpdateData = (Map)((DataTableImpl)this.getData().getTables().get("GC_INVESTBILL")).getUpdateData().get(0);
        }
        masterUpdateData.entrySet().removeIf(entry -> !masterNotSystemFieldSet.contains(entry.getKey()));
        List rowList = ((DataTableImpl)this.getData().getTables().get("GC_INVESTBILLITEM")).getRowList();
        List<Map<String, Object>> appendRowList = this.getAppendRowList(acctPeriod, acctYear, rowList);
        Set<String> delRowSrcIdSet = ((DataTableImpl)this.getData().getTables().get("GC_INVESTBILLITEM")).getDeletedRows().map(item -> (String)item.getValue("SRCID")).collect(Collectors.toSet());
        List currVerModifiedRowList = rowList.stream().filter(row -> {
            if (row.getState() != DataRowState.MODIFIED) {
                return false;
            }
            Object object = row.getData().get("INPUTDATE");
            LocalDateTime inputDate = object instanceof Date ? DateUtils.convertDateToLDT((Date)((Date)object)) : LocalDateTime.parse(ConverterUtils.getAsString(row.getData().get("INPUTDATE")), formatter);
            return inputDate.getMonth().getValue() == acctPeriod;
        }).collect(Collectors.toList());
        Set updateRowSrcIdSet = currVerModifiedRowList.stream().map(item -> item.getData().get("SRCID")).collect(Collectors.toSet());
        Map<Integer, String> fieldIndex2FieldCode = ((DataTableImpl)this.getData().getTables().get("GC_INVESTBILLITEM")).getFields().stream().collect(Collectors.toMap(item -> item.getIndex(), item -> item.getName()));
        Map<String, Map> srcId2SubItemModifieldData = currVerModifiedRowList.stream().collect(Collectors.toMap(item -> (String)item.getData().get("SRCID"), item -> {
            HashMap map = new HashMap();
            boolean[] modifiedTemp = item.getModified();
            for (int i = 0; i < modifiedTemp.length; ++i) {
                if (!modifiedTemp[i]) continue;
                map.put(fieldIndex2FieldCode.get(i), item.getValue(i));
            }
            return map;
        }));
        BillModelImpl billModel = this.createBillModel("GCBILL_B_INVESTBILL");
        billModel.getRuler().getRulerExecutor().setEnable(true);
        try {
            String sql = "select t.billcode, t.PERIOD  from gc_investbill t where t.srcid=? and t.acctYear=?";
            List currYearMasterDatas = InvestBillTool.queryBySql((String)sql, (Object[])new Object[]{this.getMaster().getData().get("SRCID"), acctYear});
            Set billCodeSet = currYearMasterDatas.stream().filter(item -> (Integer)item.getFieldValue("PERIOD") >= acctPeriod).map(item -> (String)item.getFieldValue("BILLCODE")).collect(Collectors.toSet());
            for (String billCode : billCodeSet) {
                billModel.loadByCode(billCode);
                billModel.edit();
                masterUpdateData.forEach((field, fieldValue) -> billModel.getMaster().setValue(field, fieldValue));
                List<Map> needUpdteSubItemList = ((List)billModel.getData().getTablesData().get("GC_INVESTBILLITEM")).stream().filter(subItem -> updateRowSrcIdSet.contains(subItem.get("SRCID"))).collect(Collectors.toList());
                needUpdteSubItemList.forEach(row -> {
                    Map fieldCode2ValueMap = (Map)srcId2SubItemModifieldData.get(row.get("SRCID"));
                    row.putAll(fieldCode2ValueMap);
                });
                billModel.getTable("GC_INVESTBILLITEM").updateRows(needUpdteSubItemList);
                this.handleAppendRow(appendRowList, billModel);
                this.handleDeleteRow(delRowSrcIdSet, billModel);
                this.excuteSave(billModel);
            }
        }
        finally {
            billModel.getRuler().getRulerExecutor().setEnable(false);
        }
    }

    private void saveInvestBill(int acctPeriod, int acctYear) {
        ArrayList<BillModelImpl> billModels = new ArrayList<BillModelImpl>();
        List subItemsMap = (List)this.getData().getTablesData().get("GC_INVESTBILLITEM");
        List orderItems = this.filterAndSortItems(subItemsMap, acctYear, acctPeriod);
        Map masterMap = (Map)((List)this.getData().getTablesData().get("GC_INVESTBILL")).get(0);
        if (StringUtils.isNull((String)((String)masterMap.get("SRCID")))) {
            masterMap.put("SRCID", UUIDOrderUtils.newUUIDStr());
        }
        Date date = DateUtils.firstDateOf((int)acctYear, (int)acctPeriod);
        for (Map item : orderItems) {
            item.put("SRCID", UUIDOrderUtils.newUUIDStr());
        }
        for (int i = acctPeriod; i <= 12; ++i) {
            BillModelImpl billModel2 = this.createBillModel("GCBILL_B_INVESTBILL");
            billModel2.add();
            String billCode = InvestBillTool.getBillCode((String)"GCBILL_B_INVESTBILL", (String)((String)billModel2.getMaster().getValue("UNITCODE")));
            masterMap.remove("ID");
            billModel2.getMaster().setData(masterMap);
            billModel2.getMaster().setValue("BILLCODE", (Object)billCode);
            billModel2.getMaster().setValue("PERIOD", (Object)i);
            for (Map subRecord : orderItems) {
                DataRow dataRow = billModel2.getTable("GC_INVESTBILLITEM").appendRow();
                subRecord.remove("ID");
                subRecord.remove("MASTERID");
                subRecord.remove("BILLCODE");
                subRecord.put("INPUTDATE", date);
                dataRow.setData(subRecord);
            }
            billModels.add(billModel2);
        }
        billModels.stream().forEach(billModel -> {
            billModel.getRuler().getRulerExecutor().setEnable(true);
            try {
                this.excuteSave((BillModelImpl)billModel);
            }
            finally {
                billModel.getRuler().getRulerExecutor().setEnable(false);
            }
        });
    }

    private void handleAppendRow(List<Map<String, Object>> appendRowList, BillModelImpl billModel) {
        appendRowList.forEach(row -> billModel.getTable("GC_INVESTBILLITEM").appendRow(row));
    }

    private void handleDeleteRow(Set<String> delRowSrcIdSet, BillModelImpl billModel) {
        List<Map> curVersionDelRowList = ((List)billModel.getData().getTablesData().get("GC_INVESTBILLITEM")).stream().filter(subItem -> delRowSrcIdSet.contains(subItem.get("SRCID"))).collect(Collectors.toList());
        curVersionDelRowList.forEach(row -> billModel.getTable("GC_INVESTBILLITEM").deleteRowById(row.get("ID")));
    }

    @NotNull
    private List<Map<String, Object>> getAppendRowList(int acctPeriod, int acctYear, List<DataRowImpl> rowList) {
        List<Map<String, Object>> appendRowList = rowList.stream().filter(row -> row.getState() == DataRowState.APPENDED).map(item -> {
            Map data = item.getData();
            data.put("INPUTDATE", DateUtils.dateOf((int)acctYear, (int)acctPeriod, (int)1));
            data.put("SRCID", UUIDOrderUtils.newUUIDStr());
            data.remove("ID");
            data.remove("MASTERID");
            data.remove("BILLCODE");
            return data;
        }).collect(Collectors.toList());
        return appendRowList;
    }

    private void excuteSave(BillModelImpl billModel) {
        ActionRequest request = new ActionRequest();
        request.setParams(new HashMap());
        ActionResponse response = new ActionResponse();
        SaveAction saveAction = (SaveAction)SpringContextUtils.getBean(SaveAction.class);
        billModel.getContext().setContextValue(SKIP_SELFMODEL, (Object)true);
        billModel.executeAction((Action)saveAction, request, response);
    }

    private Set<String> getNotSystemFieldSet(String tableName) {
        DataModelDTO dataModelDTO = new DataModelDTO();
        dataModelDTO.setName(tableName);
        DataModelDO dataModelDO = ((VaDataModelPublishedService)SpringContextUtils.getBean(VaDataModelPublishedService.class)).get(dataModelDTO);
        List columns = dataModelDO.getColumns();
        Set<String> notSystemFieldSet = columns.stream().filter(item -> item != null && !DataModelType.ColumnAttr.SYSTEM.equals((Object)item.getColumnAttr())).map(item -> item.getColumnName()).collect(Collectors.toSet());
        notSystemFieldSet.remove("INPUTDATE");
        notSystemFieldSet.remove("INVESTBILLSTATE");
        return notSystemFieldSet;
    }

    private void handleDirectState(DataRow dataRow, String investUnitCode, String investedUnitCode, GcOrgCenterService orgCenterService) {
        boolean isDirectInvest = InvestBillTool.isDirectInvest((String)investUnitCode, (String)investedUnitCode, (GcOrgCenterService)orgCenterService);
        if (isDirectInvest) {
            dataRow.setValue("MERGETYPE", (Object)InvestInfoEnum.DIRECT.getCode());
        } else {
            dataRow.setValue("MERGETYPE", (Object)InvestInfoEnum.INDIRECT.getCode());
        }
    }

    private boolean sameCtrlDispose(DataRow dataRow) {
        List investBillItemList = ((DataTableImpl)this.getData().getTables().get("GC_INVESTBILLITEM")).getRowsData();
        if (!CollectionUtils.isEmpty((Collection)investBillItemList)) {
            SameCtrlChgOptionDao sameCtrlChgOptionDao = (SameCtrlChgOptionDao)SpringContextUtils.getBean(SameCtrlChgOptionDao.class);
            List sameCtrlChgSettingOptionEOS = sameCtrlChgOptionDao.listOptions();
            HashSet disposalSceneCodeSet = new HashSet();
            sameCtrlChgSettingOptionEOS.forEach(item -> {
                if (!StringUtils.isEmpty((String)item.getDisposalSceneCodes())) {
                    String[] split = item.getDisposalSceneCodes().split(";");
                    disposalSceneCodeSet.addAll(Arrays.asList(split));
                }
            });
            for (Map item2 : investBillItemList) {
                String changeDateStr;
                Object changeScenario = this.getValue(item2, "CHANGESCENARIO");
                if (!disposalSceneCodeSet.contains(changeScenario) || StringUtils.isEmpty((String)(changeDateStr = (String)item2.get("CHANGEDATE")))) continue;
                dataRow.setValue("DISPOSEFLAG", (Object)Integer.valueOf(InvestInfoEnum.DISPOSE_DONE.getCode()));
                Date changeDate = DateUtils.parse((String)changeDateStr, (String)"yyyy-MM-dd HH:mm:ss");
                dataRow.setValue("DISPOSEDATE", (Object)changeDate);
                return true;
            }
        }
        return false;
    }

    private Object getValue(Map<String, Object> record, String key) {
        Object value = record.get(key);
        if (null == value) {
            return null;
        }
        if (value instanceof Map) {
            return ((Map)record.get(key)).get("name");
        }
        return value;
    }

    private boolean isGroupOutSide(String investedUnitCode, GcOrgCenterService orgCenterService) {
        GcOrgCacheVO oppUnit = orgCenterService.getOrgByCode(investedUnitCode);
        return oppUnit != null && "\u96c6\u56e2\u5916\u5355\u4f4d".equals(oppUnit.getTitle());
    }

    private void addInvestBillLog(DataRow dataRow) {
        String investUnitCode = dataRow.getString("UNITCODE");
        String investedUnitCode = dataRow.getString("INVESTEDUNIT");
        String acctYear = dataRow.getString("ACCTYEAR");
        String operateType = dataRow.getString("INVESTBILLSTATE");
        String operateTypeTitle = String.format("%1s-\u5e74\u5ea6%2s-\u6295\u8d44\u5355\u4f4d%3s-\u88ab\u6295\u8d44\u5355\u4f4d%4s", "NEW".equals(operateType) ? "\u65b0\u589e" : "\u4fee\u6539", acctYear, investUnitCode, investedUnitCode);
        LogHelper.info((String)"\u5408\u5e76-\u6295\u8d44\u53f0\u8d26", (String)operateTypeTitle, (String)String.format("\u6295\u8d44\u5355\u4f4d\uff1a%1s, \u88ab\u6295\u8d44\u5355\u4f4d\uff1a%2s", investUnitCode, investedUnitCode));
    }

    public void edit() {
        this.getMaster().setValue("INVESTBILLSTATE", (Object)"EDIT");
        super.edit();
    }

    public void loadById(Object id) {
        super.loadById(id);
        this.resetBillDate();
    }

    public void loadByCode(String billCode) {
        super.loadByCode(billCode);
        this.resetBillDate();
    }

    public void add() {
        super.add();
        this.getMaster().setValue("INVESTBILLSTATE", (Object)"NEW");
        this.resetBillDate();
    }

    public String getUnitCategory(String tableName) {
        return StringUtils.isEmpty((String)tableName) ? "MD_ORG_CORPORATE" : tableName;
    }

    private void resetBillDate() {
        int acctYear = ConverterUtils.getAsIntValue((Object)this.getMaster().getValue("ACCTYEAR"));
        int acctPeriod = ConverterUtils.getAsIntValue((Object)this.getMaster().getValue("PERIOD"));
        if (this.getMaster().getState().equals((Object)DataRowState.APPENDED)) {
            acctYear = ConverterUtils.getAsIntValue((Object)this.getContext().getContextValue("X--acctYear"));
            acctPeriod = ConverterUtils.getAsIntValue((Object)this.getContext().getContextValue("X--acctPeriod"));
        }
        Calendar newCalendar = Calendar.getInstance();
        newCalendar.set(acctYear, acctPeriod - 1, 15);
        this.getMaster().setValue("BILLDATE", (Object)newCalendar.getTime());
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

    private List filterAndSortItems(List<Map<String, Object>> subItemsMap, int acctYear, int acctPeriod) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        List filterSubItems = subItemsMap.stream().filter(subItem -> {
            Object object = subItem.get("CHANGEDATE");
            LocalDateTime changeDate = object instanceof Date ? DateUtils.convertDateToLDT((Date)((Date)object)) : LocalDateTime.parse(ConverterUtils.getAsString(subItem.get("CHANGEDATE")), formatter);
            int year = changeDate.getYear();
            int month = changeDate.getMonthValue();
            return year == acctYear && acctPeriod >= month;
        }).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(filterSubItems)) {
            return new ArrayList();
        }
        filterSubItems.sort((o1, o2) -> {
            LocalDateTime dateTime2;
            LocalDateTime dateTime1;
            Object o2ChangeDate = o2.get("CHANGEDATE");
            if (o2ChangeDate == null) {
                return -1;
            }
            Object o1ChangeDate = o1.get("CHANGEDATE");
            if (o1ChangeDate == null) {
                return 1;
            }
            if (o2ChangeDate instanceof Date) {
                dateTime1 = DateUtils.convertDateToLDT((Date)((Date)o2ChangeDate));
                dateTime2 = DateUtils.convertDateToLDT((Date)((Date)o1ChangeDate));
            } else {
                dateTime1 = LocalDateTime.parse(ConverterUtils.getAsString(o2ChangeDate), formatter);
                dateTime2 = LocalDateTime.parse(ConverterUtils.getAsString(o1ChangeDate), formatter);
            }
            if (dateTime1.isBefore(dateTime2)) {
                return 1;
            }
            return -1;
        });
        return filterSubItems;
    }
}

