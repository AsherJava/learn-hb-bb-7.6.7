/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.ConverterUtils
 *  com.jiuqi.common.base.util.DateUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.va.bill.action.CommitAction
 *  com.jiuqi.va.bill.action.SaveAction
 *  com.jiuqi.va.bill.actions.BillChangeCheckSaveAction
 *  com.jiuqi.va.bill.actions.VaBillChangeSaveAction
 *  com.jiuqi.va.bill.impl.BillContextImpl
 *  com.jiuqi.va.bill.impl.BillModelImpl
 *  com.jiuqi.va.bill.intf.BillContext
 *  com.jiuqi.va.bill.intf.BillDefineService
 *  com.jiuqi.va.bill.intf.BillException
 *  com.jiuqi.va.bill.intf.BillModel
 *  com.jiuqi.va.bill.model.VaChangeBillModel
 *  com.jiuqi.va.bill.pugins.VaBillChangeDefineImpl
 *  com.jiuqi.va.billcode.common.BillCodeUtils
 *  com.jiuqi.va.billcode.service.IBillCodeFlowService
 *  com.jiuqi.va.billcode.service.IBillCodeRuleService
 *  com.jiuqi.va.billcode.service.impl.BillCodeFlowService
 *  com.jiuqi.va.biz.impl.data.DataTableImpl
 *  com.jiuqi.va.biz.intf.action.Action
 *  com.jiuqi.va.biz.intf.action.ActionRequest
 *  com.jiuqi.va.biz.intf.action.ActionResponse
 *  com.jiuqi.va.biz.intf.data.DataRow
 *  com.jiuqi.va.biz.intf.data.DataTable
 *  com.jiuqi.va.biz.intf.model.Plugin
 *  com.jiuqi.va.domain.billcode.BillCodeDTO
 *  com.jiuqi.va.domain.billcode.BillCodeRuleDTO
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.org.OrgDO
 *  com.jiuqi.va.domain.org.OrgDTO
 *  com.jiuqi.va.domain.org.OrgDataOption$AuthType
 *  com.jiuqi.va.domain.org.OrgDataOption$QueryChildrenType
 *  com.jiuqi.va.domain.org.OrgDataOption$QueryDataStructure
 *  com.jiuqi.va.organization.service.OrgDataService
 *  org.apache.commons.lang3.StringUtils
 */
package com.jiuqi.common.billbasedopsorg.service.impl;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.common.base.util.DateUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.billbasedopsorg.service.GcOrgsSyncToBillService;
import com.jiuqi.va.bill.action.CommitAction;
import com.jiuqi.va.bill.action.SaveAction;
import com.jiuqi.va.bill.actions.BillChangeCheckSaveAction;
import com.jiuqi.va.bill.actions.VaBillChangeSaveAction;
import com.jiuqi.va.bill.impl.BillContextImpl;
import com.jiuqi.va.bill.impl.BillModelImpl;
import com.jiuqi.va.bill.intf.BillContext;
import com.jiuqi.va.bill.intf.BillDefineService;
import com.jiuqi.va.bill.intf.BillException;
import com.jiuqi.va.bill.intf.BillModel;
import com.jiuqi.va.bill.model.VaChangeBillModel;
import com.jiuqi.va.bill.pugins.VaBillChangeDefineImpl;
import com.jiuqi.va.billcode.common.BillCodeUtils;
import com.jiuqi.va.billcode.service.IBillCodeFlowService;
import com.jiuqi.va.billcode.service.IBillCodeRuleService;
import com.jiuqi.va.billcode.service.impl.BillCodeFlowService;
import com.jiuqi.va.biz.impl.data.DataTableImpl;
import com.jiuqi.va.biz.intf.action.Action;
import com.jiuqi.va.biz.intf.action.ActionRequest;
import com.jiuqi.va.biz.intf.action.ActionResponse;
import com.jiuqi.va.biz.intf.data.DataRow;
import com.jiuqi.va.biz.intf.data.DataTable;
import com.jiuqi.va.biz.intf.model.Plugin;
import com.jiuqi.va.domain.billcode.BillCodeDTO;
import com.jiuqi.va.domain.billcode.BillCodeRuleDTO;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.org.OrgDO;
import com.jiuqi.va.domain.org.OrgDTO;
import com.jiuqi.va.domain.org.OrgDataOption;
import com.jiuqi.va.organization.service.OrgDataService;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class GcOrgsSyncToBillServiceImpl
implements GcOrgsSyncToBillService {
    private static final Logger logger = LoggerFactory.getLogger(GcOrgsSyncToBillServiceImpl.class);
    private static final String ORG_CODE = "ORG_CODE";
    @Autowired
    private OrgDataService orgDataService;

    @Override
    public void orgsSync(Map<String, Object> syncMasterData, List<Map<String, Object>> syncSubItemList) {
        BillModelImpl ydBillModel = this.createYdBillAndCommit(syncMasterData);
        this.setYdbillData(syncSubItemList, ydBillModel);
        String bgdDefineCode = this.getBgdDefineCode(ydBillModel);
        VaChangeBillModel bgdModel = (VaChangeBillModel)this.getModel(bgdDefineCode);
        this.addBgdBill(ydBillModel, bgdModel);
        String bgdMasterId = bgdModel.getMaster().getId().toString();
        this.billChangeCheckSave(ydBillModel, bgdMasterId);
        this.billChangeSave(bgdModel, ydBillModel.getData().getTablesData());
        this.billCommit((BillModelImpl)bgdModel);
    }

    private String getBgdDefineCode(BillModelImpl ydBillModel) {
        Plugin changePlugin = (Plugin)ydBillModel.getPlugins().find("billChange");
        if (changePlugin == null) {
            throw new BillException("\u672a\u914d\u7f6e\u5355\u636e\u53d8\u66f4\u63d2\u4ef6\uff0c\u8bf7\u68c0\u67e5\u914d\u7f6e");
        }
        VaBillChangeDefineImpl billChange = (VaBillChangeDefineImpl)changePlugin.getDefine();
        String scene = billChange.getScene();
        if (!StringUtils.hasText(scene)) {
            throw new BillException("\u5355\u636e\u53d8\u66f4\u63d2\u4ef6\u672a\u914d\u7f6e\u89c4\u5219\u573a\u666f");
        }
        return billChange.getDefineCode();
    }

    private void setYdbillData(List<Map<String, Object>> syncSubItemList, BillModelImpl ydBillModel) {
        List subRowsData = ((DataTableImpl)ydBillModel.getData().getTables().get("GC_BILLPUSHORGSITEM")).getRowList();
        Map<String, List<Map>> syncOrgCode2SubItems = syncSubItemList.stream().collect(Collectors.groupingBy(item -> (String)item.get(ORG_CODE)));
        Set<String> syncOrgCode = syncOrgCode2SubItems.keySet();
        ArrayList needDelSubItemList = new ArrayList();
        subRowsData.forEach(subRowData -> {
            String orgCode = subRowData.getString(ORG_CODE);
            if (syncOrgCode.contains(orgCode)) {
                List subItemList = (List)syncOrgCode2SubItems.get(orgCode);
                subRowData.setData((Map)subItemList.get(0));
            }
            if (org.apache.commons.lang3.StringUtils.isNotEmpty((CharSequence)orgCode) && !syncOrgCode.contains(orgCode)) {
                needDelSubItemList.add(subRowData);
            }
        });
        needDelSubItemList.forEach(item -> ((DataTableImpl)ydBillModel.getData().getTables().get("GC_BILLPUSHORGSITEM")).removeRow((DataRow)item));
        List hasOrgCodeBillRowData = subRowsData.stream().filter(rowData -> org.apache.commons.lang3.StringUtils.isNotEmpty((CharSequence)rowData.getString(ORG_CODE))).collect(Collectors.toList());
        Set hasOrgCodeSet = hasOrgCodeBillRowData.stream().map(item -> item.getString(ORG_CODE)).collect(Collectors.toSet());
        List<Map> appendRowList = syncSubItemList.stream().filter(syncSubItem -> !hasOrgCodeSet.contains(syncSubItem.get(ORG_CODE))).collect(Collectors.toList());
        appendRowList.forEach(row -> ydBillModel.getTable("GC_BILLPUSHORGSITEM").appendRow(row));
    }

    private void billChangeSave(VaChangeBillModel bgdModel, Map<String, List<Map<String, Object>>> ydTablesData) {
        HashMap<String, Map<String, List<Map<String, Object>>>> params = new HashMap<String, Map<String, List<Map<String, Object>>>>();
        params.put("srcData", ydTablesData);
        VaBillChangeSaveAction vaBillChangeSaveAction = (VaBillChangeSaveAction)SpringContextUtils.getBean(VaBillChangeSaveAction.class);
        vaBillChangeSaveAction.executeReturn((BillModel)bgdModel, params);
    }

    private void billChangeCheckSave(BillModelImpl ydBillModel, String bgdMasterId) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("id", bgdMasterId);
        BillChangeCheckSaveAction vaBillChangeCheckSaveAction = (BillChangeCheckSaveAction)SpringContextUtils.getBean(BillChangeCheckSaveAction.class);
        Map vaBillChangeCheckSaveReturn = (Map)vaBillChangeCheckSaveAction.executeReturn((BillModel)ydBillModel, params);
    }

    private void addBgdBill(BillModelImpl ydBillModel, VaChangeBillModel bgdModel) {
        bgdModel.add();
        bgdModel.getMaster().setValue("UNITCODE", ydBillModel.getMaster().getValue("UNITCODE"));
        bgdModel.getMaster().setValue("VER", (Object)System.currentTimeMillis());
        bgdModel.getMaster().setValue("BILLCODE", (Object)this.getBillCode(bgdModel.getDefine().getName(), (String)ydBillModel.getMaster().getValue("UNITCODE", String.class)));
        bgdModel.getMaster().setValue("SRCBILLDEFINE", (Object)ydBillModel.getDefine().getName());
        bgdModel.getMaster().setValue("SRCBILLID", ydBillModel.getMaster().getId());
        bgdModel.getMaster().setValue("SRCVER", (Object)ydBillModel.getMaster().getVersion());
        bgdModel.getMaster().setValue("SRCBILLCODE", ydBillModel.getMaster().getValue("BILLCODE"));
        bgdModel.getMaster().setValue("CHANGESTATUS", (Object)"0");
        this.excuteSave((BillModelImpl)bgdModel);
    }

    private BillModelImpl createYdBillAndCommit(Map<String, Object> masterData) {
        List<OrgDO> orgDOList = this.getOrgList(masterData);
        BillModelImpl ydBillModel = this.getModel("GCBILL_B_GC_BILLPUSHORGS");
        this.setYdBillData(masterData, orgDOList, ydBillModel);
        this.excuteSave(ydBillModel);
        this.billCommit(ydBillModel);
        return ydBillModel;
    }

    private void setYdBillData(Map<String, Object> masterData, List<OrgDO> orgDOList, BillModelImpl ydBillModel) {
        ydBillModel.add();
        ydBillModel.getMaster().setData(masterData);
        ydBillModel.getMaster().setValue("BILLCODE", (Object)this.getBillCode("GCBILL_B_GC_BILLPUSHORGS", (String)ydBillModel.getMaster().getValue("UNITCODE", String.class)));
        DataTable dataTable = ydBillModel.getTable("GC_BILLPUSHORGSITEM");
        List fieldNames = dataTable.getDefine().getFields().stream().filter(dataField -> dataField.getName().startsWith("ORG_")).map(dataField -> dataField.getName().substring(4)).collect(Collectors.toList());
        orgDOList.forEach(orgDO -> {
            HashMap subItem = new HashMap();
            fieldNames.forEach(fieldName -> {
                if (orgDO.get((Object)fieldName.toLowerCase()) != null) {
                    subItem.put("ORG_" + fieldName, orgDO.get((Object)fieldName.toLowerCase()));
                }
            });
            DataRow dataRow = dataTable.appendRow();
            dataRow.setData(subItem);
        });
    }

    private void billSave(BillModelImpl ydBillModel) {
        SaveAction saveAction = (SaveAction)SpringContextUtils.getBean(SaveAction.class);
        ActionRequest request = new ActionRequest();
        request.setParams(new HashMap());
        try {
            ydBillModel.executeAction((Action)saveAction, request, new ActionResponse());
        }
        catch (Exception e) {
            logger.error("\u6e90\u5355\u4fdd\u5b58\u62a5\u9519\uff1a" + e.getMessage(), e);
        }
    }

    private List<OrgDO> getOrgList(Map<String, Object> masterData) {
        OrgDTO orgDTO = new OrgDTO();
        orgDTO.setCategoryname((String)masterData.get("SYNCORGTYPE"));
        orgDTO.setVersionDate(this.getVessionDate((String)masterData.get("SYNCPERIOD")));
        orgDTO.setQueryDataStructure(OrgDataOption.QueryDataStructure.ALL);
        orgDTO.setAuthType(OrgDataOption.AuthType.NONE);
        orgDTO.setQueryChildrenType(OrgDataOption.QueryChildrenType.ALL_CHILDREN);
        String parentCode = ConverterUtils.getAsString((Object)masterData.get("SYNCPARENTCODE"));
        if (org.apache.commons.lang3.StringUtils.isEmpty((CharSequence)parentCode)) {
            logger.info("\u4e3b\u8868\u672a\u586b\u5199\u540c\u6b65\u7684\u7236\u7ea7\u5355\u4f4d\uff0c\u5c06\u67e5\u8be2\u51fa\u673a\u6784\u7c7b\u578b\u7684\u6240\u6709\u5355\u4f4d");
        } else {
            orgDTO.setCode(parentCode);
        }
        List orgDOList = this.orgDataService.list(orgDTO).getRows();
        return orgDOList;
    }

    private Date getVessionDate(String period) {
        if (org.apache.commons.lang3.StringUtils.isEmpty((CharSequence)period)) {
            return new Date();
        }
        String[] split = period.split("-");
        if (split.length == 2) {
            return DateUtils.firstDateOf((int)Integer.parseInt(split[0]), (int)Integer.parseInt(split[1]));
        }
        return new Date();
    }

    private void billCommit(BillModelImpl billModel) {
        CommitAction commitAction = (CommitAction)SpringContextUtils.getBean(CommitAction.class);
        ActionRequest request = new ActionRequest();
        request.setParams(new HashMap());
        try {
            billModel.executeAction((Action)commitAction, request, new ActionResponse());
        }
        catch (Exception e) {
            logger.error(billModel.getDefine().getName() + "\u5de5\u4f5c\u6d41\u63d0\u4ea4\u62a5\u9519\uff1a" + e.getMessage(), e);
            throw new RuntimeException(billModel.getDefine().getName() + "\u5de5\u4f5c\u6d41\u63d0\u4ea4\u62a5\u9519\uff1a" + e.getMessage(), e);
        }
    }

    private BillModelImpl getModel(String modelDefine) {
        BillContextImpl billContext = new BillContextImpl();
        billContext.setTenantName(ShiroUtil.getTenantName());
        billContext.setDisableVerify(true);
        BillDefineService billDefineService = (BillDefineService)SpringContextUtils.getBean(BillDefineService.class);
        BillModelImpl model = (BillModelImpl)billDefineService.createModel((BillContext)billContext, modelDefine);
        model.getRuler().getRulerExecutor().setEnable(true);
        return model;
    }

    public String getBillCode(String uniqueCode, String unitCode) {
        BillCodeRuleDTO codeRuleDTO;
        try {
            IBillCodeRuleService billCodeRuleService = (IBillCodeRuleService)SpringContextUtils.getBean(IBillCodeRuleService.class);
            codeRuleDTO = billCodeRuleService.getRuleByUniqueCodeUnCheck(uniqueCode, false);
        }
        catch (Exception e) {
            throw new RuntimeException(uniqueCode + "\u751f\u6210\u5355\u636e\u7f16\u53f7\u5931\u8d25");
        }
        BillCodeDTO billCodeDTO = new BillCodeDTO();
        billCodeDTO.setUnitCode(unitCode);
        billCodeDTO.setCreateTime(new Date());
        billCodeDTO.setDimFormulaValue("");
        return BillCodeUtils.createBillCode((BillCodeRuleDTO)codeRuleDTO, (BillCodeDTO)billCodeDTO, (IBillCodeFlowService)((IBillCodeFlowService)SpringContextUtils.getBean(BillCodeFlowService.class)));
    }

    private void excuteSave(BillModelImpl billModel) {
        try {
            ActionRequest request = new ActionRequest();
            request.setParams(new HashMap());
            ActionResponse response = new ActionResponse();
            SaveAction saveAction = (SaveAction)SpringContextUtils.getBean(SaveAction.class);
            billModel.executeAction((Action)saveAction, request, response);
        }
        catch (BillException e) {
            List checkMessages = e.getCheckMessages();
            String checkMessage = CollectionUtils.isEmpty((Collection)checkMessages) ? "" : "\u6821\u9a8c\u7ed3\u679c:" + checkMessages.stream().map(item -> item.getCheckMessage()).collect(Collectors.joining(";"));
            logger.error(billModel.getDefine().getName() + "\u4fdd\u5b58\u5931\u8d25:" + checkMessage + e.getMessage(), e);
            throw new RuntimeException(billModel.getDefine().getName() + "\u4fdd\u5b58\u5931\u8d25:" + checkMessage + e.getMessage(), e);
        }
        catch (Exception e) {
            logger.error(billModel.getDefine().getName() + "\u4fdd\u5b58\u5931\u8d25:" + e.getMessage(), e);
            throw new RuntimeException(billModel.getDefine().getName() + "\u4fdd\u5b58\u5931\u8d25:" + e.getMessage(), e);
        }
    }
}

