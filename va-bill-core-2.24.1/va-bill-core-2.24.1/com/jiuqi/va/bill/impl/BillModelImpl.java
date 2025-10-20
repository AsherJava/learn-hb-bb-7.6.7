/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.biz.impl.data.DataImpl
 *  com.jiuqi.va.biz.impl.data.DataTableDefineImpl
 *  com.jiuqi.va.biz.impl.data.DataTableImpl
 *  com.jiuqi.va.biz.impl.model.ModelImpl
 *  com.jiuqi.va.biz.intf.data.DataAccess
 *  com.jiuqi.va.biz.intf.data.DataException
 *  com.jiuqi.va.biz.intf.data.DataField
 *  com.jiuqi.va.biz.intf.data.DataFieldDefine
 *  com.jiuqi.va.biz.intf.data.DataRow
 *  com.jiuqi.va.biz.intf.data.DataState
 *  com.jiuqi.va.biz.intf.data.DataTable
 *  com.jiuqi.va.biz.intf.model.Model
 *  com.jiuqi.va.biz.intf.value.Convert
 *  com.jiuqi.va.biz.intf.value.ListContainer
 *  com.jiuqi.va.biz.intf.value.NamedContainer
 *  com.jiuqi.va.biz.ruler.impl.FormulaImpl
 *  com.jiuqi.va.biz.ruler.impl.RulerImpl
 *  com.jiuqi.va.biz.utils.Env
 *  com.jiuqi.va.biz.utils.FormulaUtils
 *  com.jiuqi.va.biz.utils.Utils
 *  com.jiuqi.va.domain.bill.BillVerifyDTO
 *  com.jiuqi.va.domain.billcode.BillCodeDTO
 *  com.jiuqi.va.domain.billcode.BillCodeRuleDTO
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.user.UserLoginDTO
 *  com.jiuqi.va.feign.client.BillCodeClient
 *  com.jiuqi.va.feign.client.BillSyncDataClient
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 *  com.jiuqi.va.mapper.domain.TenantDO
 *  org.springframework.jdbc.BadSqlGrammarException
 */
package com.jiuqi.va.bill.impl;

import com.jiuqi.va.bill.impl.BillContextImpl;
import com.jiuqi.va.bill.impl.BillDefineImpl;
import com.jiuqi.va.bill.intf.BillContext;
import com.jiuqi.va.bill.intf.BillException;
import com.jiuqi.va.bill.intf.BillModel;
import com.jiuqi.va.bill.intf.BillModelService;
import com.jiuqi.va.bill.intf.BillState;
import com.jiuqi.va.bill.utils.BillCoreI18nUtil;
import com.jiuqi.va.bill.utils.VerifyUtils;
import com.jiuqi.va.biz.impl.data.DataImpl;
import com.jiuqi.va.biz.impl.data.DataTableDefineImpl;
import com.jiuqi.va.biz.impl.data.DataTableImpl;
import com.jiuqi.va.biz.impl.model.ModelImpl;
import com.jiuqi.va.biz.intf.data.DataAccess;
import com.jiuqi.va.biz.intf.data.DataException;
import com.jiuqi.va.biz.intf.data.DataField;
import com.jiuqi.va.biz.intf.data.DataFieldDefine;
import com.jiuqi.va.biz.intf.data.DataRow;
import com.jiuqi.va.biz.intf.data.DataState;
import com.jiuqi.va.biz.intf.data.DataTable;
import com.jiuqi.va.biz.intf.model.Model;
import com.jiuqi.va.biz.intf.value.Convert;
import com.jiuqi.va.biz.intf.value.ListContainer;
import com.jiuqi.va.biz.intf.value.NamedContainer;
import com.jiuqi.va.biz.ruler.impl.FormulaImpl;
import com.jiuqi.va.biz.ruler.impl.RulerImpl;
import com.jiuqi.va.biz.utils.Env;
import com.jiuqi.va.biz.utils.FormulaUtils;
import com.jiuqi.va.biz.utils.Utils;
import com.jiuqi.va.domain.bill.BillVerifyDTO;
import com.jiuqi.va.domain.billcode.BillCodeDTO;
import com.jiuqi.va.domain.billcode.BillCodeRuleDTO;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.user.UserLoginDTO;
import com.jiuqi.va.feign.client.BillCodeClient;
import com.jiuqi.va.feign.client.BillSyncDataClient;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import com.jiuqi.va.mapper.domain.TenantDO;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

public class BillModelImpl
extends ModelImpl
implements BillModel {
    private BillModelService billModelService;
    private DataImpl data;
    private RulerImpl ruler;

    protected DataAccess getDataAccess() {
        return this.billModelService.getDataAccess();
    }

    protected BillCodeClient getBillCode() {
        return this.billModelService.getBillCode();
    }

    void setBillModelService(BillModelService billModelService) {
        this.billModelService = billModelService;
    }

    @Override
    public BillContext getContext() {
        return (BillContext)super.getContext();
    }

    public BillDefineImpl getDefine() {
        return (BillDefineImpl)super.getDefine();
    }

    public DataImpl getData() {
        return this.data;
    }

    void setData(DataImpl data) {
        this.data = data;
    }

    public RulerImpl getRuler() {
        return this.ruler;
    }

    void setRuler(RulerImpl ruler) {
        this.ruler = ruler;
    }

    @Override
    public DataTable getMasterTable() {
        return (DataTable)this.data.getTables().getMasterTable();
    }

    public boolean load(String fieldName, Object fieldValue) {
        try {
            return this.load(Stream.of(Integer.valueOf(0)).collect(Collectors.toMap(o -> fieldName, o -> fieldValue)));
        }
        catch (Exception e) {
            if (e instanceof BadSqlGrammarException) {
                String message = e.getCause().getMessage();
                if (message.contains("Unknown") && message.split("'").length > 0) {
                    message = BillCoreI18nUtil.getMessage("va.billcore.billmodelimpl.fieldnotexist", new Object[]{message.split("'")[1], this.getDefine().getName()});
                }
                throw new BillException(message, e);
            }
            throw e;
        }
    }

    public boolean load(Map<String, Object> valueMap) {
        this.data.load(valueMap);
        if (this.getMasterTable().getRows().size() == 0) {
            throw new DataException(BillCoreI18nUtil.getMessage("va.billcore.billmodelimpl.datadelete"));
        }
        if (valueMap.get("ID") == null) {
            VerifyUtils.verifyBill(this, 1);
        }
        return ((DataTableImpl)this.data.getTables().getMasterTable()).getRows().size() == 1;
    }

    @Override
    public void loadById(Object id) {
        if (!this.load("ID", id)) {
            throw new BillException(BillCoreI18nUtil.getMessage("va.billcore.billmodelimpl.loadbillnotexist") + ":" + id);
        }
    }

    @Override
    public void loadByCode(String billCode) {
        if (!this.load("BILLCODE", billCode)) {
            throw new BillException(BillCoreI18nUtil.getMessage("va.billcore.billmodelimpl.loadbillnotexist") + ":" + billCode);
        }
    }

    @Override
    public void save() {
        VerifyUtils.verifyBill(this, 2);
        DataState dataState = this.data.getState();
        if (dataState != DataState.EDIT && dataState != DataState.NEW) {
            return;
        }
        int billState = this.getMaster().getInt("BILLSTATE");
        if (billState <= 1) {
            this.getMaster().setValue("BILLSTATE", (Object)BillState.SAVED);
        }
        try {
            this.data.saveWithLock(Stream.of("ID", "VER").collect(Collectors.toSet()));
        }
        catch (Exception e) {
            if (e instanceof BadSqlGrammarException) {
                String message = e.getCause().getMessage();
                if (message.contains("Unknown") && message.split("'").length > 0) {
                    message = BillCoreI18nUtil.getMessage("va.billcore.billmodelimpl.fieldnotexist", new Object[]{message.split("'")[1], this.getDefine().getName()});
                }
                throw new BillException(message, e);
            }
            throw e;
        }
    }

    @Override
    public void edit() {
        this.checkAdmin();
        VerifyUtils.verifyBill(this, 2);
        BillState state = (BillState)((Object)this.getMaster().getValue("BILLSTATE", BillState.class));
        if (state != null && (state.getValue() & (BillState.DELETED.getValue() | BillState.CHECKED.getValue())) != 0) {
            throw new BillException(BillCoreI18nUtil.getMessage("va.billcore.billmodelimpl.currstatecannotedit") + state.getTitle());
        }
        this.data.edit();
    }

    @Override
    public void add(Map<String, Object> param) {
        if (param != null) {
            if (param.containsKey("isTablesData")) {
                this.checkAdmin();
                this.data.create();
                Map tablesData = (Map)param.get("tablesData");
                this.ruler.getRulerExecutor().setEnable(false);
                this.getData().setTablesData(tablesData);
                this.ruler.getRulerExecutor().setEnable(true);
                if (!Objects.equals(this.getMaster().getString("CREATEUSER"), Env.getUserId())) {
                    ((BillContextImpl)this.getContext()).setVerifyCode(VerifyUtils.genVerifyCode(this, 15));
                } else {
                    ((BillContextImpl)this.getContext()).setVerifyCode(null);
                }
            } else {
                this.add();
                if (param.get("master") != null) {
                    Map masterValues = (Map)param.get("master");
                    for (Map.Entry entrySet : masterValues.entrySet()) {
                        this.getMaster().setValue((String)entrySet.getKey(), entrySet.getValue());
                    }
                }
                if (param.get("tableInit") != null) {
                    Map detailTables = (Map)param.get("tableInit");
                    String masterTableName = this.getMasterTable().getDefine().getName();
                    detailTables.keySet().forEach(tableName -> {
                        List tableDatas = (List)detailTables.get(tableName);
                        tableDatas.forEach(tableData -> {
                            if (tableName.equalsIgnoreCase(masterTableName)) {
                                for (Map.Entry entrySet : tableData.entrySet()) {
                                    this.getMaster().setValue((String)entrySet.getKey(), entrySet.getValue());
                                }
                            } else {
                                int size = this.getTable((String)tableName).getRows().size();
                                this.getTable((String)tableName).insertRow(size, tableData);
                            }
                        });
                    });
                }
            }
        } else {
            this.add();
        }
    }

    @Override
    public void add() {
        this.checkAdmin();
        this.data.create();
        DataRow master = this.getMasterTable().insertRow(0);
        String currentTrigger = this.getContext().getTriggerOrigin();
        if (!Utils.isEmpty((String)currentTrigger)) {
            this.getData().getTables().getDetailTables(this.getMasterTable().getId()).stream().forEach(table -> {
                if (table.getRows().size() == 0) {
                    DataTableDefineImpl define = table.getDefine();
                    String singleFieldName = String.format("CALC_%s_SINGLE", define.getName());
                    DataField singleField = (DataField)this.getMasterTable().getFields().find(singleFieldName);
                    boolean isSingle = singleField != null ? master.getBoolean(singleFieldName) : define.isSingle();
                    if (isSingle) {
                        table.appendRow(Utils.makeMap((Object[])new Object[]{"ID", UUID.randomUUID(), "MASTERID", master.getId(), "$UNSET", false}));
                    } else {
                        FormulaImpl formula;
                        int initRows = define.getInitRows();
                        if (initRows == -1 && (formula = (FormulaImpl)this.getRuler().getDefine().getFormulas().stream().filter(o -> o.getObjectId().equals(define.getId()) && "initRows".equals(o.getPropertyType())).findFirst().orElse(null)) != null && formula.isUsed()) {
                            Object evaluate = FormulaUtils.evaluate((String)formula.getExpression(), (Model)this);
                            initRows = evaluate != null ? (Integer)Convert.cast((Object)evaluate, Integer.class) : 0;
                        }
                        for (int i = 0; i < initRows; ++i) {
                            table.appendRow(Utils.makeMap((Object[])new Object[]{"ID", UUID.randomUUID(), "MASTERID", master.getId(), "$UNSET", define.isBlankRow()}));
                        }
                    }
                }
            });
        }
        if (!Objects.equals(this.getMaster().getString("CREATEUSER"), Env.getUserId())) {
            ((BillContextImpl)this.getContext()).setVerifyCode(VerifyUtils.genVerifyCode(this, 15));
        } else {
            ((BillContextImpl)this.getContext()).setVerifyCode(null);
        }
    }

    private void checkAdmin() {
        UserLoginDTO user;
        BillContext context = this.getContext();
        String triggerOrigin = context.getTriggerOrigin();
        if (StringUtils.hasText(triggerOrigin) && (user = ShiroUtil.getUser()) != null && "super".equalsIgnoreCase(user.getMgrFlag())) {
            throw new RuntimeException(BillCoreI18nUtil.getMessage("va.bill.core.admin.no.permission"));
        }
    }

    public String createBillCode() {
        BillCodeClient billCodeClient = this.getBillCode();
        BillCodeDTO billCodeDTO = new BillCodeDTO();
        BillCodeRuleDTO billCodeRuleDTO = new BillCodeRuleDTO();
        billCodeRuleDTO.setUniqueCode(this.getDefine().getName());
        R rRule = billCodeClient.getDimFormulaByUniqueCode(billCodeRuleDTO);
        if (rRule.get((Object)"generateopt") != null && String.valueOf(rRule.get((Object)"generateopt")).equals("1")) {
            return "";
        }
        billCodeDTO.setDimFormulaValue(this.parseDimFormula(rRule, "dimformula"));
        if (billCodeDTO.getExtInfo() == null) {
            HashMap<String, String> extInfoMap = new HashMap<String, String>();
            extInfoMap.put("datedimformula", this.parseDimFormula(rRule, "datedimformula"));
            billCodeDTO.setExtInfo(extInfoMap);
        } else {
            billCodeDTO.getExtInfo().put("datedimformula", this.parseDimFormula(rRule, "datedimformula"));
        }
        billCodeDTO.setDefineCode(this.getDefine().getName());
        billCodeDTO.setCreateTime(this.getMaster().getDate("BILLDATE"));
        billCodeDTO.setTenantName(this.getContext().getTenantName());
        billCodeDTO.setUnitCode(this.getMaster().getString("UNITCODE"));
        billCodeDTO.setGenerateOpt(Integer.valueOf("0"));
        billCodeDTO.addExtInfo("createDefaultFlow", (Object)this.getContext().isPreview());
        R r = billCodeClient.createBillCode(billCodeDTO);
        if (Integer.parseInt(r.get((Object)"code").toString()) == 0) {
            return (String)r.get((Object)"billcode");
        }
        throw new BillException(BillCoreI18nUtil.getMessage("va.billcore.billcodegenerateevent.createbillcodefailed") + r.get((Object)"msg"));
    }

    private String parseDimFormula(R r, String name) {
        String dimFormula = (String)r.get((Object)name);
        Object dimFormulaValue = null;
        if (StringUtils.hasText(dimFormula)) {
            dimFormulaValue = FormulaUtils.evaluate((String)dimFormula, (Model)this);
        }
        return dimFormulaValue == null ? "" : String.valueOf(dimFormulaValue);
    }

    private boolean delete(String fieldName, Object fieldValue) {
        this.checkAdmin();
        if (!this.load(fieldName, fieldValue)) {
            return false;
        }
        if (this.getMaster().getInt("BILLSTATE") == BillState.TEMPORARY.getValue()) {
            this.getData().delete();
        } else {
            this.delete();
        }
        return true;
    }

    @Override
    public void delete() {
        this.checkAdmin();
        VerifyUtils.verifyBill(this, 2);
        BillState state = (BillState)((Object)this.getMaster().getValue("BILLSTATE", BillState.class));
        if (state != BillState.SAVED && state != BillState.TEMPORARY && state != BillState.SENDBACK) {
            throw new BillException(BillCoreI18nUtil.getMessage("va.billcore.billmodelimpl.currstatecannotdelete") + state.getTitle());
        }
        this.data.deleteWithLock(Stream.of("ID", "VER").collect(Collectors.toSet()));
        ((BillContextImpl)this.getContext()).setVerifyCode(null);
    }

    @Override
    public void deleteById(Object id) {
        if (!this.delete("ID", id)) {
            throw new BillException(BillCoreI18nUtil.getMessage("va.billcore.billmodelimpl.deletebillnotexist") + ":" + id);
        }
    }

    @Override
    public void deleteByCode(String billCode) {
        if (!this.delete("BILLCODE", billCode)) {
            throw new BillException(BillCoreI18nUtil.getMessage("va.billcore.billmodelimpl.deletebillnotexist") + ":" + billCode);
        }
    }

    @Override
    public boolean editing() {
        return this.data.getState() == DataState.NEW || this.data.getState() == DataState.EDIT;
    }

    @Override
    public DataRow getMaster() {
        ListContainer rows = this.getMasterTable().getRows();
        return rows.size() == 0 ? null : (DataRow)rows.get(0);
    }

    @Override
    public DataTable getTable(String tableName) {
        return (DataTable)this.data.getTables().get(tableName);
    }

    public List<String> createVerifyCodes(List<String> billCodes) {
        BillVerifyDTO billVerifyDTO = new BillVerifyDTO();
        billVerifyDTO.setBillCodes(billCodes);
        billVerifyDTO.setAuth(1);
        return VerifyUtils.genVerifyCode(billVerifyDTO);
    }

    @Override
    public boolean afterApproval() {
        DataFieldDefine bizsource = (DataFieldDefine)this.getMasterTable().getDefine().getFields().find("BIZSOURCE");
        if (bizsource != null && "02".equals(this.getMaster().getString("BIZSOURCE"))) {
            BillSyncDataClient fssBillClient = (BillSyncDataClient)ApplicationContextRegister.getBean(BillSyncDataClient.class);
            HashMap<String, Object> billMsg = new HashMap<String, Object>();
            billMsg.put("BILLID", this.getMaster().getString("ID"));
            billMsg.put("BIZSOURCE", this.getMaster().getString("BIZSOURCE"));
            billMsg.put("DEFINECODE", this.getDefine().getName());
            billMsg.put("BILLCODE", this.getMaster().getString("BILLCODE"));
            billMsg.put("FEEDBACKENUMCODE", 1);
            billMsg.put("FEEDBACKENUMDESC", "\u5de5\u4f5c\u6d41\u5b8c\u6210");
            billMsg.put("OPERATERESULT", "\u5de5\u4f5c\u6d41\u5b8c\u6210");
            TenantDO tenantMagDO = new TenantDO();
            tenantMagDO.addExtInfo("FEEDBACKMSG", (Object)JSONUtil.toJSONString(billMsg));
            fssBillClient.sendFeedbackMsg(tenantMagDO);
        }
        return true;
    }

    public Map<String, Object> getDimValues(DataFieldDefine fieldDefine, DataRow row) {
        Date bizDate;
        HashMap<String, Object> resultMap = new HashMap<String, Object>();
        if (fieldDefine == null) {
            resultMap.put("UNITCODE", this.getMaster().getString("UNITCODE"));
        } else {
            String unitField = fieldDefine.getUnitField();
            if (StringUtils.hasText(unitField)) {
                String unitCode = row.getString(unitField);
                if (unitCode == null) {
                    unitCode = this.getMaster().getString("UNITCODE");
                }
                resultMap.put("UNITCODE", unitCode);
            } else {
                Map shareFieldMapping = fieldDefine.getShareFieldMapping();
                if (shareFieldMapping != null && shareFieldMapping.size() > 0) {
                    NamedContainer fields = fieldDefine.getTable().getFields();
                    for (Map.Entry entry : shareFieldMapping.entrySet()) {
                        Object value = null;
                        if (!StringUtils.hasText((String)entry.getValue())) {
                            if (fields.find((String)entry.getKey()) != null) {
                                value = row.getValue((String)entry.getKey());
                            }
                            if (ObjectUtils.isEmpty(value)) {
                                value = this.getMaster().getValue((String)entry.getKey());
                            }
                        } else {
                            value = row.getValue((String)entry.getValue());
                            if (ObjectUtils.isEmpty(value)) {
                                value = this.getMaster().getValue((String)entry.getValue());
                            }
                        }
                        resultMap.put((String)entry.getKey(), value);
                    }
                } else {
                    resultMap.put("UNITCODE", this.getMaster().getString("UNITCODE"));
                }
            }
        }
        if (resultMap.get("UNITCODE") == null) {
            String unitCode = this.getContext().getUnitCode();
            if (unitCode == null) {
                unitCode = Env.getUnitCode();
            }
            resultMap.put("UNITCODE", unitCode);
        }
        if ((bizDate = this.getMaster().getDate("BILLDATE")) == null && (bizDate = this.getContext().getBizDate()) == null) {
            bizDate = Env.getBizDate();
        }
        resultMap.put("BIZDATE", bizDate);
        return resultMap;
    }

    public Map<String, Object> getDimValues() {
        return this.getDimValues(null, this.getMaster());
    }
}

