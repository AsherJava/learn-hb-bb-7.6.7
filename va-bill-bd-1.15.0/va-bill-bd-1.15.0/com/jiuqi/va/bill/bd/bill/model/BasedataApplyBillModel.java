/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.bill.impl.BillContextImpl
 *  com.jiuqi.va.bill.impl.BillModelImpl
 *  com.jiuqi.va.bill.intf.BillException
 *  com.jiuqi.va.bill.intf.BillState
 *  com.jiuqi.va.biz.intf.action.Action
 *  com.jiuqi.va.biz.intf.action.ActionManager
 *  com.jiuqi.va.biz.intf.action.ActionRequest
 *  com.jiuqi.va.biz.intf.action.ActionResponse
 *  com.jiuqi.va.biz.intf.data.DataRow
 *  com.jiuqi.va.biz.intf.data.DataRowState
 *  com.jiuqi.va.biz.intf.data.DataUpdate
 *  com.jiuqi.va.biz.intf.model.Model
 *  com.jiuqi.va.biz.intf.model.ModelContext
 *  com.jiuqi.va.biz.intf.model.ModelDefineService
 *  com.jiuqi.va.biz.utils.FormulaUtils
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 *  com.jiuqi.va.trans.domain.VaTransMessageDO
 *  com.jiuqi.va.trans.domain.VaTransMessageDTO
 *  com.jiuqi.va.trans.service.VaTransMessageService
 *  org.springframework.transaction.PlatformTransactionManager
 *  org.springframework.transaction.TransactionDefinition
 *  org.springframework.transaction.TransactionStatus
 *  org.springframework.transaction.support.DefaultTransactionDefinition
 */
package com.jiuqi.va.bill.bd.bill.model;

import com.jiuqi.va.bill.bd.bill.domain.CreateBillEntry;
import com.jiuqi.va.bill.bd.bill.domain.MapInfoDTO;
import com.jiuqi.va.bill.bd.bill.impl.BillToMasterHandle;
import com.jiuqi.va.bill.bd.bill.model.RegistrationBillModel;
import com.jiuqi.va.bill.bd.bill.service.MaintainBillService;
import com.jiuqi.va.bill.bd.core.domain.ApplyRegMapDO;
import com.jiuqi.va.bill.bd.core.service.ApplyRegMapService;
import com.jiuqi.va.bill.bd.utils.BillBdI18nUtil;
import com.jiuqi.va.bill.impl.BillContextImpl;
import com.jiuqi.va.bill.impl.BillModelImpl;
import com.jiuqi.va.bill.intf.BillException;
import com.jiuqi.va.bill.intf.BillState;
import com.jiuqi.va.biz.intf.action.Action;
import com.jiuqi.va.biz.intf.action.ActionManager;
import com.jiuqi.va.biz.intf.action.ActionRequest;
import com.jiuqi.va.biz.intf.action.ActionResponse;
import com.jiuqi.va.biz.intf.data.DataRow;
import com.jiuqi.va.biz.intf.data.DataRowState;
import com.jiuqi.va.biz.intf.data.DataUpdate;
import com.jiuqi.va.biz.intf.model.Model;
import com.jiuqi.va.biz.intf.model.ModelContext;
import com.jiuqi.va.biz.intf.model.ModelDefineService;
import com.jiuqi.va.biz.utils.FormulaUtils;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import com.jiuqi.va.trans.domain.VaTransMessageDO;
import com.jiuqi.va.trans.domain.VaTransMessageDTO;
import com.jiuqi.va.trans.service.VaTransMessageService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

public class BasedataApplyBillModel
extends BillModelImpl {
    private static final Logger logger = LoggerFactory.getLogger(BasedataApplyBillModel.class);
    private VaTransMessageDO transMessage;
    private VaTransMessageService vaTransMessageService;
    private BillToMasterHandle billToMasterHandle;
    private ModelDefineService modelDefineService;
    private MaintainBillService maintainBillService;
    private PlatformTransactionManager platformTransactionManager;
    private DataRow masterOriginalRow;
    private Map<String, List<Map<String, Object>>> detailOriginalDeleteRow;
    private Map<String, List<Map<String, Object>>> detailOriginalUpdateRow;

    public VaTransMessageDO getTransMessage() {
        return this.transMessage;
    }

    private PlatformTransactionManager getPlatformTransactionManager() {
        if (this.platformTransactionManager == null) {
            this.platformTransactionManager = (PlatformTransactionManager)ApplicationContextRegister.getBean(PlatformTransactionManager.class);
        }
        return this.platformTransactionManager;
    }

    private ModelDefineService getModelDefineService() {
        if (this.modelDefineService == null) {
            this.modelDefineService = (ModelDefineService)ApplicationContextRegister.getBean(ModelDefineService.class);
        }
        return this.modelDefineService;
    }

    private MaintainBillService getMaintainBillService() {
        if (this.maintainBillService == null) {
            this.maintainBillService = (MaintainBillService)ApplicationContextRegister.getBean(MaintainBillService.class);
        }
        return this.maintainBillService;
    }

    public VaTransMessageService getVaTransMessageService() {
        if (this.vaTransMessageService == null) {
            this.vaTransMessageService = (VaTransMessageService)ApplicationContextRegister.getBean(VaTransMessageService.class);
        }
        return this.vaTransMessageService;
    }

    private BillToMasterHandle getBillToMasterHandle() {
        if (this.billToMasterHandle == null) {
            this.billToMasterHandle = (BillToMasterHandle)ApplicationContextRegister.getBean(BillToMasterHandle.class);
        }
        return this.billToMasterHandle;
    }

    public void add() {
        super.add();
    }

    public void edit() {
        BillState oldState = (BillState)this.getMaster().getValue("BILLSTATE", BillState.class);
        ActionManager actionManager = (ActionManager)ApplicationContextRegister.getBean(ActionManager.class);
        Action action = (Action)actionManager.get("bill-reload");
        ActionRequest request = new ActionRequest();
        ActionResponse response = new ActionResponse();
        super.executeAction(action, request, response);
        if (!this.isCreating()) {
            BillState newState = (BillState)this.getMaster().getValue("BILLSTATE", BillState.class);
            if (newState.getValue() != oldState.getValue()) {
                this.getMaster().setValue("BILLSTATE", (Object)oldState);
            }
        } else {
            throw new RuntimeException(BillBdI18nUtil.getMessage("va.billbd.back.exist.bill"));
        }
        super.edit();
    }

    public void delete() {
        super.delete();
    }

    public boolean afterApproval() {
        List<ApplyRegMapDO> applyRegMapDOs = this.getApplyRegMap(2);
        if (applyRegMapDOs.size() == 0) {
            return super.afterApproval();
        }
        this.getMaintainBillService().afterApprovalCreateBill((Model)this, applyRegMapDOs, ShiroUtil.getUser());
        return true;
    }

    public void save() {
        List<ApplyRegMapDO> applyRegMapDOs;
        List<ApplyRegMapDO> applyRegMapDOsApproval = this.getApplyRegMap(2);
        if (applyRegMapDOsApproval.size() != 0) {
            this.check(applyRegMapDOsApproval);
        }
        if ((applyRegMapDOs = this.getApplyRegMap(1)).size() == 0) {
            super.save();
            return;
        }
        TransactionStatus transaction = this.getPlatformTransactionManager().getTransaction((TransactionDefinition)new DefaultTransactionDefinition());
        try {
            R r;
            Map<String, Map<String, List<String>>> check = this.check(applyRegMapDOs);
            this.masterOriginalRow = this.getMaster().getState() == DataRowState.MODIFIED ? this.getMaster().getOriginRow() : null;
            this.getDetailOriginRow();
            super.save();
            this.createBill(applyRegMapDOs, check);
            this.getPlatformTransactionManager().commit(transaction);
            if (this.transMessage != null && (r = this.getVaTransMessageService().sendAndReceive(this.transMessage, JSONUtil.parseMap((String)this.transMessage.getInputparam()))).getCode() == 1) {
                logger.error("\u4fdd\u5b58\u540e\u751f\u5355\u5f02\u5e38\uff1a" + r.getMsg());
            }
        }
        catch (Exception e) {
            logger.error("\u7533\u8bf7\u5355\u4fdd\u5b58\u5931\u8d25\uff1a" + e.getMessage(), e);
            this.getPlatformTransactionManager().rollback(transaction);
            throw new BillException(e.getMessage());
        }
    }

    private void getDetailOriginRow() {
        Map updateData = this.getData().getUpdate();
        String masterName = this.getMasterTable().getName();
        this.detailOriginalDeleteRow = new HashMap<String, List<Map<String, Object>>>();
        this.detailOriginalUpdateRow = new HashMap<String, List<Map<String, Object>>>();
        for (Map.Entry stringDataUpdateEntry : updateData.entrySet()) {
            List update;
            String name = (String)stringDataUpdateEntry.getKey();
            if (name.equals(masterName)) continue;
            ArrayList deleteTableData = new ArrayList();
            this.detailOriginalDeleteRow.put(name, deleteTableData);
            Stream deletedRows = this.getTable(name).getDeletedRows();
            if (deletedRows != null) {
                deletedRows.forEach(dataRow -> deleteTableData.add(dataRow.getData()));
            }
            if ((update = ((DataUpdate)stringDataUpdateEntry.getValue()).getUpdate()) == null) continue;
            ArrayList<Map> updateTableData = new ArrayList<Map>();
            this.detailOriginalUpdateRow.put(name, updateTableData);
            for (Map stringObjectMap : update) {
                String o = stringObjectMap.get("ID").toString();
                for (int i = 0; i < this.getTable(name).getRows().size(); ++i) {
                    String string = ((DataRow)this.getTable(name).getRows().get(i)).getString("ID");
                    if (!string.equals(o)) continue;
                    Map data = ((DataRow)this.getTable(name).getRows().get(i)).getOriginRow().getData();
                    updateTableData.add(data);
                }
            }
        }
    }

    public Map<String, Map<String, List<String>>> check(List<ApplyRegMapDO> applyRegMapDOs) {
        HashMap<String, Map<String, List<String>>> treeCodes = new HashMap<String, Map<String, List<String>>>();
        this.getBindingTemplate(applyRegMapDOs);
        for (ApplyRegMapDO mapDefine : applyRegMapDOs) {
            HashMap<String, String> reFieldMap = new HashMap<String, String>();
            List mapinfos = JSONUtil.parseMapArray((String)mapDefine.getMapinfos());
            MapInfoDTO mapinfo = null;
            for (int i = 0; i < mapinfos.size(); ++i) {
                mapinfo = (MapInfoDTO)JSONUtil.parseObject((String)JSONUtil.toJSONString(mapinfos.get(i)), MapInfoDTO.class);
                for (MapInfoDTO.Filedmap f : mapinfo.filedmaps) {
                    String field = f.getSrcfiledname();
                    if (!StringUtils.hasText(field)) continue;
                    reFieldMap.put(f.getFiledname(), field);
                }
            }
            if (!StringUtils.hasText((String)reFieldMap.get("BASEDATANAME"))) {
                throw new BillException(BillBdI18nUtil.getMessage("va.billbd.basedata.code.not.empty"));
            }
            if (mapDefine.getCreatetype() == 2 || mapDefine.getCreatetype() == 4) {
                try {
                    this.getMaster().getString(mapDefine.getWritebackname());
                }
                catch (Exception e) {
                    throw new BillException(BillBdI18nUtil.getMessage("va.billbd.back.write.field.not.empty"));
                }
                DataRow dataRow = this.getMaster();
                String basedataname = dataRow.getString((String)reFieldMap.get("BASEDATANAME"));
                if (!StringUtils.hasText(basedataname)) {
                    throw new BillException(BillBdI18nUtil.getMessage("va.billbd.basedata.table.field.not.empty"));
                }
                this.getBillToMasterHandle().beforeCheckByApply(this, null, mapDefine, mapinfo, reFieldMap, basedataname, treeCodes, null);
                this.checkBefore(mapDefine, true, null);
                continue;
            }
            if (mapDefine.getCreatetype() != 3 && mapDefine.getCreatetype() != 5) continue;
            if (mapinfo == null) {
                throw new BillException(BillBdI18nUtil.getMessage("va.billbd.mapping.config.not.empty"));
            }
            List applyBillDatas = (List)this.getData().getTablesData(false).get(mapinfo.getSrctablename());
            for (int i = 0; i < applyBillDatas.size(); ++i) {
                Map applyitemValue = (Map)applyBillDatas.get(i);
                if (ObjectUtils.isEmpty(applyitemValue.get(reFieldMap.get("BASEDATANAME")))) {
                    throw new BillException(BillBdI18nUtil.getMessage("va.billbd.detail.basedata.table.name.not.empty"));
                }
                String basedataname = applyitemValue.get(reFieldMap.get("BASEDATANAME")).toString();
                this.getBillToMasterHandle().beforeCheckByApply(this, applyitemValue, mapDefine, mapinfo, reFieldMap, basedataname, treeCodes, applyBillDatas);
                this.checkBefore(mapDefine, false, applyitemValue);
            }
        }
        return treeCodes;
    }

    private void checkBefore(ApplyRegMapDO define, Boolean flag, Map<String, Object> applyitemValue) {
        BillContextImpl context = new BillContextImpl();
        context.setDisableVerify(true);
        context.setTenantName(ShiroUtil.getTenantName());
        RegistrationBillModel regBillModal = (RegistrationBillModel)this.getModelDefineService().createModel((ModelContext)context, define.getBilldefinecode());
        regBillModal.getRuler().getRulerExecutor().setEnable(true);
        regBillModal.add();
        if (flag.booleanValue()) {
            List mapinfos = JSONUtil.parseMapArray((String)define.getMapinfos());
            MapInfoDTO mapinfo = null;
            for (int i = 0; i < mapinfos.size(); ++i) {
                mapinfo = (MapInfoDTO)JSONUtil.parseObject((String)JSONUtil.toJSONString(mapinfos.get(i)), MapInfoDTO.class);
                this.getMaintainBillService().createBillByMatser(mapinfo, regBillModal, this.getData().getTablesData(false));
            }
        } else {
            this.getMaintainBillService().setValuesDetail(regBillModal, applyitemValue, this.getData().getTablesData(false), define);
        }
        regBillModal.beforeCheck();
    }

    public void createBill(List<ApplyRegMapDO> applyRegMapDOs, Map<String, Map<String, List<String>>> treeData) {
        for (ApplyRegMapDO mapDefine : applyRegMapDOs) {
            MapInfoDTO mapinfo;
            HashMap<String, String> reFieldMap = new HashMap<String, String>();
            List mapinfos = JSONUtil.parseMapArray((String)mapDefine.getMapinfos());
            for (int i = 0; i < mapinfos.size(); ++i) {
                mapinfo = (MapInfoDTO)JSONUtil.parseObject((String)JSONUtil.toJSONString(mapinfos.get(i)), MapInfoDTO.class);
                for (MapInfoDTO.Filedmap f : mapinfo.filedmaps) {
                    String field = f.getSrcfiledname();
                    if (!StringUtils.hasText(field)) continue;
                    reFieldMap.put(f.getFiledname(), field);
                }
            }
            DataRow master = this.getMaster();
            if (mapDefine.getCreatetype() == 2 || mapDefine.getCreatetype() == 4) {
                String s;
                CreateBillEntry cnEntry = new CreateBillEntry();
                cnEntry.setTenantName(ShiroUtil.getTenantName());
                cnEntry.setCreatetype(mapDefine.getCreatetype());
                cnEntry.setUser(ShiroUtil.getUser());
                cnEntry.setBillcode(master.getString("BILLCODE"));
                cnEntry.setDefine(mapDefine);
                cnEntry.setMasterid(master.getString("ID"));
                if (this.getBillToMasterHandle().checkDataExist(master, null, true, reFieldMap, master.getString((String)reFieldMap.get("BASEDATANAME")).toUpperCase()) && master.getString(mapDefine.getWritebackname()) != null) {
                    this.insertChangeTransMessage(cnEntry);
                    continue;
                }
                this.insertCreateTransMessage(cnEntry);
                if (mapDefine.getDeleteflag() == null || mapDefine.getDeleteflag() != 1 || (s = this.checkMasterIsDelete(reFieldMap, mapDefine)) == null) continue;
                cnEntry.setTargetBillCode(s);
                this.insertDeleteTransMessage(cnEntry);
                continue;
            }
            if (mapDefine.getCreatetype() != 3 && mapDefine.getCreatetype() != 5) continue;
            Map<String, List<String>> treeDataCode = treeData.get(mapDefine.getId());
            for (int i = 0; i < mapinfos.size(); ++i) {
                mapinfo = (MapInfoDTO)JSONUtil.parseObject((String)JSONUtil.toJSONString(mapinfos.get(i)), MapInfoDTO.class);
                this.createBillBydetail(mapDefine, mapinfo, treeDataCode);
            }
        }
    }

    private String checkMasterIsDelete(Map<String, String> reFieldMap, ApplyRegMapDO mapDefine) {
        if (this.masterOriginalRow == null) {
            return null;
        }
        DataRow master = this.getMaster();
        DataRow masterOriginalRow = this.masterOriginalRow;
        boolean isChange = this.getBillToMasterHandle().checkMasterDataIsChangeByDataRow(reFieldMap, master, masterOriginalRow, master.getString(reFieldMap.get("BASEDATANAME")));
        if (isChange) {
            return masterOriginalRow.getString(mapDefine.getWritebackname());
        }
        return null;
    }

    private void insertDeleteTransMessage(CreateBillEntry cnEntry) {
        VaTransMessageDO vaTransMessageDO = new VaTransMessageDO();
        vaTransMessageDO.setId(UUID.randomUUID().toString());
        vaTransMessageDO.setBizcategory("BILL");
        vaTransMessageDO.setBiztype(this.getDefine().getName());
        String bizCode = this.getMaster().getString("BILLCODE");
        vaTransMessageDO.setBizcode(bizCode);
        vaTransMessageDO.setMqname("VA_BILL_BD_DELETEEBILL");
        if (cnEntry.getCreatetype() == 2 || cnEntry.getCreatetype() == 4) {
            vaTransMessageDO.setOperatordesc("\u53d1\u9001\u4e3b\u8868\u5220\u9664\u6d88\u606f\u961f\u5217");
        } else {
            vaTransMessageDO.setOperatordesc("\u53d1\u9001\u5b50\u8868\u5220\u9664\u6d88\u606f\u961f\u5217");
        }
        HashMap<String, CreateBillEntry> inputParam = new HashMap<String, CreateBillEntry>();
        inputParam.put("body", cnEntry);
        this.getVaTransMessageService().insertMessage(vaTransMessageDO, inputParam);
        if (this.transMessage == null) {
            this.transMessage = vaTransMessageDO;
        }
    }

    private void insertChangeTransMessage(CreateBillEntry cnEntry) {
        VaTransMessageDO vaTransMessageDO = new VaTransMessageDO();
        vaTransMessageDO.setId(UUID.randomUUID().toString());
        vaTransMessageDO.setBizcategory("BILL");
        vaTransMessageDO.setBiztype(this.getDefine().getName());
        String bizCode = this.getMaster().getString("BILLCODE");
        vaTransMessageDO.setBizcode(bizCode);
        vaTransMessageDO.setMqname("VA_BILL_BD_CHANGEBILL");
        if (cnEntry.getCreatetype() == 2 || cnEntry.getCreatetype() == 4) {
            vaTransMessageDO.setOperatordesc("\u53d1\u9001\u4e3b\u8868\u53d8\u66f4\u6d88\u606f\u961f\u5217");
        } else {
            vaTransMessageDO.setOperatordesc("\u53d1\u9001\u5b50\u8868\u53d8\u66f4\u6d88\u606f\u961f\u5217");
        }
        HashMap<String, Object> inputParam = new HashMap<String, Object>();
        inputParam.put("tableData", this.getData().getTablesData(false));
        inputParam.put("body", cnEntry);
        this.getVaTransMessageService().insertMessage(vaTransMessageDO, inputParam);
        if (this.transMessage == null) {
            this.transMessage = vaTransMessageDO;
        }
    }

    private void getBindingTemplate(List<ApplyRegMapDO> applyRegMapDOs) {
        for (int i = 0; i < applyRegMapDOs.size(); ++i) {
            ApplyRegMapDO define = applyRegMapDOs.get(i);
            if (!StringUtils.hasText(define.getRelationtemplate())) continue;
            String relationtemplate = define.getRelationtemplate();
            ApplyRegMapService regMapService = (ApplyRegMapService)ApplicationContextRegister.getBean(ApplyRegMapService.class);
            ApplyRegMapDO applyRegMapDO = new ApplyRegMapDO();
            applyRegMapDO.setName(relationtemplate);
            List<ApplyRegMapDO> map = regMapService.getMap(applyRegMapDO);
            if (CollectionUtils.isEmpty(map)) continue;
            applyRegMapDOs.add(map.get(0));
        }
    }

    private void createBillBydetail(ApplyRegMapDO mapDefine, MapInfoDTO mapinfo, Map<String, List<String>> treeData) {
        DataRow master = this.getMaster();
        String unitcode = master.getString("UNITCODE");
        List applyBillDatas = (List)this.getData().getTablesData(false).get(mapinfo.getSrctablename());
        HashMap<String, String> fieldMap = new HashMap<String, String>();
        HashMap<String, String> reFieldMap = new HashMap<String, String>();
        for (MapInfoDTO.Filedmap f : mapinfo.filedmaps) {
            String field = f.getSrcfiledname();
            if (!StringUtils.hasText(field)) continue;
            fieldMap.put(field, f.getFiledname());
            reFieldMap.put(f.getFiledname(), field);
        }
        BillContextImpl context = new BillContextImpl();
        context.setDisableVerify(true);
        context.setTenantName(ShiroUtil.getTenantName());
        HashMap treeBaseDatas = new HashMap();
        for (int i = 0; i < applyBillDatas.size(); ++i) {
            String b;
            Map applyitemValue = (Map)applyBillDatas.get(i);
            CreateBillEntry cnEntry = new CreateBillEntry();
            cnEntry.setTenantName(ShiroUtil.getTenantName());
            cnEntry.setUser(ShiroUtil.getUser());
            cnEntry.setDefine(mapDefine);
            cnEntry.setUnitcode(unitcode);
            cnEntry.setTablename(mapinfo.getSrctablename());
            cnEntry.setApplyitemValue(applyitemValue);
            cnEntry.setCreatetype(mapDefine.getCreatetype());
            cnEntry.setAlterdefinecode(this.getDefine().getName());
            cnEntry.setBillcode(master.getString("BILLCODE"));
            cnEntry.setMasterid(master.getString("ID"));
            String basedataname = applyitemValue.get(reFieldMap.get("BASEDATANAME")).toString();
            if (treeData != null && treeData.containsKey(basedataname)) {
                ArrayList baseDataList = new ArrayList();
                for (int i1 = i; i1 < applyBillDatas.size(); ++i1) {
                    if (!((Map)applyBillDatas.get(i1)).get(reFieldMap.get("BASEDATANAME")).equals(basedataname)) continue;
                    baseDataList.add(applyBillDatas.get(i1));
                }
                treeBaseDatas.put(basedataname, baseDataList);
                treeData.remove(basedataname);
                continue;
            }
            if (treeBaseDatas.containsKey(basedataname)) continue;
            if (this.getBillToMasterHandle().checkDataExist(master, applyitemValue, false, reFieldMap, basedataname) && applyitemValue.get(mapDefine.getWritebackname()) != null) {
                this.insertChangeTransMessage(cnEntry);
                continue;
            }
            if (mapDefine.getDeleteflag() != null && mapDefine.getDeleteflag() == 1 && (b = this.checkDetailIsDelete(basedataname, applyitemValue, reFieldMap, mapDefine, mapinfo)) != null) {
                cnEntry.setTargetBillCode(b);
                this.insertDeleteTransMessage(cnEntry);
            }
            this.insertCreateTransMessage(cnEntry);
        }
        if (!CollectionUtils.isEmpty(treeBaseDatas)) {
            for (Map.Entry stringListEntry : treeBaseDatas.entrySet()) {
                List<Map<String, Object>> mapList = this.sortTreeData((List)stringListEntry.getValue(), (String)reFieldMap.get("PARENTCODE"), (String)reFieldMap.get("CODE"));
                int j = mapList.size();
                for (int i = 0; i < j; ++i) {
                    String b;
                    Map<String, Object> stringObjectMap = mapList.get(i);
                    CreateBillEntry cnEntry = new CreateBillEntry();
                    cnEntry.setTenantName(ShiroUtil.getTenantName());
                    cnEntry.setUser(ShiroUtil.getUser());
                    cnEntry.setDefine(mapDefine);
                    cnEntry.setUnitcode(unitcode);
                    cnEntry.setTablename(mapinfo.getSrctablename());
                    cnEntry.setApplyitemValue(stringObjectMap);
                    cnEntry.setCreatetype(mapDefine.getCreatetype());
                    cnEntry.setAlterdefinecode(this.getDefine().getName());
                    String basedataname = stringObjectMap.get(reFieldMap.get("BASEDATANAME")).toString();
                    if (this.getBillToMasterHandle().checkDataExist(master, stringObjectMap, false, reFieldMap, basedataname) && reFieldMap.get(mapDefine.getWritebackname()) != null) {
                        this.insertChangeTransMessage(cnEntry);
                        continue;
                    }
                    if (mapDefine.getDeleteflag() != null && mapDefine.getDeleteflag() == 1 && (b = this.checkDetailIsDelete(basedataname, stringObjectMap, reFieldMap, mapDefine, mapinfo)) != null) {
                        cnEntry.setTargetBillCode(b);
                        this.insertDeleteTransMessage(cnEntry);
                    }
                    this.insertCreateTransMessage(cnEntry);
                }
            }
        }
        if (mapDefine.getDeleteflag() != null && mapDefine.getDeleteflag() == 1) {
            this.handleDetailDeleteBill(reFieldMap, mapDefine, mapinfo);
        }
    }

    private void handleDetailDeleteBill(Map<String, String> reFieldMap, ApplyRegMapDO mapDefine, MapInfoDTO mapinfo) {
        String srctablename = mapinfo.getSrctablename();
        List<Map<String, Object>> deleteMap = this.detailOriginalDeleteRow.get(srctablename);
        if (!CollectionUtils.isEmpty(deleteMap)) {
            for (Map<String, Object> row : deleteMap) {
                String basedataname = row.get(reFieldMap.get("BASEDATANAME")).toString();
                if (!this.getBillToMasterHandle().checkDataExist(this.getMaster(), row, false, reFieldMap, basedataname)) continue;
                CreateBillEntry cnEntry = new CreateBillEntry();
                cnEntry.setTenantName(ShiroUtil.getTenantName());
                cnEntry.setCreatetype(mapDefine.getCreatetype());
                cnEntry.setUser(ShiroUtil.getUser());
                cnEntry.setBillcode(this.getMaster().getString("BILLCODE"));
                cnEntry.setDefine(mapDefine);
                cnEntry.setMasterid(this.getMaster().getString("ID"));
                cnEntry.setTargetBillCode((String)row.get(mapDefine.getWritebackname()));
                this.insertDeleteTransMessage(cnEntry);
            }
        }
    }

    private String checkDetailIsDelete(String basedataname, Map<String, Object> applyBillDatas, Map<String, String> reFieldMap, ApplyRegMapDO mapDefine, MapInfoDTO mapinfo) {
        String id = applyBillDatas.get("ID").toString();
        Map<String, Object> detailOriginalRow = null;
        for (Map<String, Object> stringObjectMap : this.detailOriginalUpdateRow.get(mapinfo.getSrctablename())) {
            if (!stringObjectMap.get("ID").toString().equals(id)) continue;
            detailOriginalRow = stringObjectMap;
        }
        if (detailOriginalRow == null) {
            return null;
        }
        boolean isChange = this.getBillToMasterHandle().checkMasterDataIsChangeByDataMap(this.getMaster(), reFieldMap, applyBillDatas, detailOriginalRow, basedataname);
        if (isChange) {
            return (String)detailOriginalRow.get(mapDefine.getWritebackname());
        }
        return null;
    }

    private List<Map<String, Object>> sortTreeData(List<Map<String, Object>> value, String parentcode, String code) {
        ArrayList<String> codes = new ArrayList<String>();
        ArrayList<String> newCodes = new ArrayList<String>();
        for (int i = 0; i < value.size(); ++i) {
            codes.add(value.get(i).get(code).toString());
        }
        ArrayList<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        for (int i = value.size() - 1; i >= 0; --i) {
            if (value.get(i).get(parentcode) != null && codes.contains(value.get(i).get(parentcode))) continue;
            result.add(value.get(i));
            newCodes.add(value.get(i).get(code).toString());
            value.remove(i);
        }
        this.sortTreeData(value, codes, result, parentcode, code);
        return result;
    }

    private List<Map<String, Object>> sortTreeData(List<Map<String, Object>> value, List<String> newCodes, List<Map<String, Object>> result, String parentcode, String code) {
        ArrayList<String> newCodess = new ArrayList<String>();
        for (int i = value.size() - 1; i >= 0; --i) {
            if (!newCodes.contains(value.get(i).get(parentcode))) continue;
            result.add(value.get(i));
            newCodess.add(value.get(i).get(code).toString());
            value.remove(i);
        }
        if (!CollectionUtils.isEmpty(value)) {
            this.sortTreeData(value, newCodess, result, parentcode, code);
        }
        return result;
    }

    private void insertCreateTransMessage(CreateBillEntry cnEntry) {
        VaTransMessageDO vaTransMessageDO = new VaTransMessageDO();
        vaTransMessageDO.setId(UUID.randomUUID().toString());
        vaTransMessageDO.setBizcategory("BILL");
        vaTransMessageDO.setBiztype(this.getDefine().getName());
        String bizCode = this.getMaster().getString("BILLCODE");
        vaTransMessageDO.setBizcode(bizCode);
        vaTransMessageDO.setMqname("VA_BILL_BD_CREATEBILL");
        if (cnEntry.getCreatetype() == 2 || cnEntry.getCreatetype() == 4) {
            vaTransMessageDO.setOperatordesc("\u53d1\u9001\u4e3b\u8868\u751f\u5355\u6d88\u606f\u961f\u5217");
        } else {
            vaTransMessageDO.setOperatordesc("\u53d1\u9001\u5b50\u8868\u751f\u5355\u6d88\u606f\u961f\u5217");
        }
        HashMap<String, Object> inputParam = new HashMap<String, Object>();
        inputParam.put("body", cnEntry);
        inputParam.put("tableData", this.getData().getTablesData(false));
        this.getVaTransMessageService().insertMessage(vaTransMessageDO, inputParam);
        if (this.transMessage == null) {
            this.transMessage = vaTransMessageDO;
        }
    }

    private boolean isCreating() {
        VaTransMessageDTO vaTransMessageDO = new VaTransMessageDTO();
        vaTransMessageDO.setBizcode(this.getMaster().getString("BILLCODE"));
        List resule = this.getVaTransMessageService().listTransMessage(vaTransMessageDO);
        return !CollectionUtils.isEmpty(resule) && ((VaTransMessageDO)resule.get(resule.size() - 1)).getStatus() != 2 && ((VaTransMessageDO)resule.get(resule.size() - 1)).getStatus() != 3;
    }

    private List<ApplyRegMapDO> getApplyRegMap(int createopportunity) {
        ApplyRegMapService applyRegMapService = (ApplyRegMapService)ApplicationContextRegister.getBean(ApplyRegMapService.class);
        ApplyRegMapDO applyRegMapDO = new ApplyRegMapDO();
        applyRegMapDO.setSrcbilldefinecode(this.getDefine().getName());
        if (createopportunity != 0) {
            applyRegMapDO.setCreateopportunity(createopportunity);
        }
        applyRegMapDO.setStartflag(1);
        List<ApplyRegMapDO> applyRegMapDOs = applyRegMapService.getMap(applyRegMapDO);
        return applyRegMapDOs.stream().filter(o -> {
            if (StringUtils.hasText(o.getAdaptcondition())) {
                try {
                    Object evaluate = FormulaUtils.evaluate((String)o.getAdaptcondition(), (Model)this);
                    return (Boolean)evaluate;
                }
                catch (Exception e) {
                    logger.error("\u516c\u5f0f\u6267\u884c\u5f02\u5e38{}", (Object)e.getMessage(), (Object)e);
                    return false;
                }
            }
            return true;
        }).collect(Collectors.toList());
    }
}

