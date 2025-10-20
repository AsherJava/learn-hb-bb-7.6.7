/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.bill.impl.BillContextImpl
 *  com.jiuqi.va.bill.impl.BillModelImpl
 *  com.jiuqi.va.bill.intf.BillException
 *  com.jiuqi.va.biz.intf.data.DataRow
 *  com.jiuqi.va.biz.intf.model.Model
 *  com.jiuqi.va.biz.intf.model.ModelContext
 *  com.jiuqi.va.biz.intf.model.ModelDefineService
 *  com.jiuqi.va.biz.utils.FormulaUtils
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 *  com.jiuqi.va.trans.domain.VaTransMessageDO
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
import com.jiuqi.va.biz.intf.data.DataRow;
import com.jiuqi.va.biz.intf.model.Model;
import com.jiuqi.va.biz.intf.model.ModelContext;
import com.jiuqi.va.biz.intf.model.ModelDefineService;
import com.jiuqi.va.biz.utils.FormulaUtils;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import com.jiuqi.va.trans.domain.VaTransMessageDO;
import com.jiuqi.va.trans.service.VaTransMessageService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

public class BillAlterModel
extends BillModelImpl {
    private static final Logger logger = LoggerFactory.getLogger(BillAlterModel.class);
    private VaTransMessageDO transMessage;
    private BillToMasterHandle billToMasterHandle;
    private VaTransMessageService vaTransMessageService;
    private ModelDefineService modelDefineService;
    private MaintainBillService maintainBillService;
    private PlatformTransactionManager platformTransactionManager;

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
            this.check(applyRegMapDOs);
            super.save();
            this.changeBill(applyRegMapDOs);
            this.getPlatformTransactionManager().commit(transaction);
            if (this.transMessage != null && (r = this.getVaTransMessageService().sendAndReceive(this.transMessage, JSONUtil.parseMap((String)this.transMessage.getInputparam()))).getCode() == 1) {
                logger.error("\u4fdd\u5b58\u53d8\u66f4\u5355\u5f02\u5e38\uff1a" + r.getMsg());
            }
        }
        catch (Exception e) {
            logger.error("\u53d8\u66f4\u5355\u4fdd\u5b58\u5931\u8d25\uff1a" + e.getMessage(), e);
            this.getPlatformTransactionManager().rollback(transaction);
            throw new BillException(e.getMessage());
        }
    }

    public boolean afterApproval() {
        List<ApplyRegMapDO> applyRegMapDOs = this.getApplyRegMap(2);
        if (applyRegMapDOs.size() == 0) {
            return super.afterApproval();
        }
        this.getMaintainBillService().afterApprovalChangeBill((Model)this, applyRegMapDOs, ShiroUtil.getUser());
        return super.afterApproval();
    }

    private void check(List<ApplyRegMapDO> applyRegMapDOs) {
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
            DataRow master = this.getMaster();
            if (mapDefine.getCreatetype() == 2 || mapDefine.getCreatetype() == 4) {
                String writebackname = master.getString(mapDefine.getWritebackname());
                if (!StringUtils.hasText(writebackname)) {
                    throw new BillException(BillBdI18nUtil.getMessage("va.billbd.main.table.back.write.field.not.empty") + mapDefine.getWritebackname());
                }
                String basedataname = master.getString((String)reFieldMap.get("BASEDATANAME"));
                if (!StringUtils.hasText(basedataname)) {
                    throw new BillException(BillBdI18nUtil.getMessage("va.billbd.basedata.table.field.not.empty"));
                }
                this.getBillToMasterHandle().beforeCheckByAlter(this, null, mapDefine, mapinfo, reFieldMap, basedataname, null);
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
                Object writebackname = applyitemValue.get(mapDefine.getWritebackname());
                if (ObjectUtils.isEmpty(writebackname)) {
                    throw new BillException(BillBdI18nUtil.getMessage("va.billbd.detail.table.back.write.field.not.empty") + mapDefine.getWritebackname());
                }
                String basedataname = applyitemValue.get(reFieldMap.get("BASEDATANAME")).toString();
                if (!StringUtils.hasText(basedataname)) {
                    throw new BillException(BillBdI18nUtil.getMessage("va.billbd.basedata.table.field.not.empty"));
                }
                this.getBillToMasterHandle().beforeCheckByAlter(this, applyitemValue, mapDefine, mapinfo, reFieldMap, basedataname, applyBillDatas);
                this.checkBefore(mapDefine, false, applyitemValue);
            }
        }
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
            for (int i = 0; i < mapinfos.size(); ++i) {
                MapInfoDTO mapinfo = (MapInfoDTO)JSONUtil.parseObject((String)JSONUtil.toJSONString(mapinfos.get(i)), MapInfoDTO.class);
                this.getMaintainBillService().createBillByMatser(mapinfo, regBillModal, this.getData().getTablesData(false));
            }
        } else {
            this.getMaintainBillService().setValuesDetail(regBillModal, applyitemValue, this.getData().getTablesData(false), define);
        }
        regBillModal.beforeCheck();
    }

    public void changeBill(List<ApplyRegMapDO> applyRegMapDOs) {
        for (ApplyRegMapDO mapDefine : applyRegMapDOs) {
            if (mapDefine.getCreatetype() == 2 || mapDefine.getCreatetype() == 4) {
                CreateBillEntry cnEntry = new CreateBillEntry();
                cnEntry.setTenantName(ShiroUtil.getTenantName());
                cnEntry.setCreatetype(mapDefine.getCreatetype());
                cnEntry.setUser(ShiroUtil.getUser());
                cnEntry.setBillcode(this.getMaster().getString("BILLCODE"));
                cnEntry.setDefine(mapDefine);
                cnEntry.setMasterid(this.getMaster().getString("ID"));
                this.insertChangeTransMessage(cnEntry);
                continue;
            }
            if (mapDefine.getCreatetype() != 3 && mapDefine.getCreatetype() != 5) continue;
            List mapinfos = JSONUtil.parseMapArray((String)mapDefine.getMapinfos());
            for (int i = 0; i < mapinfos.size(); ++i) {
                MapInfoDTO mapinfo = (MapInfoDTO)JSONUtil.parseObject((String)JSONUtil.toJSONString(mapinfos.get(i)), MapInfoDTO.class);
                this.changeBillBydetail(mapDefine, mapinfo);
            }
        }
    }

    private void getBindingTemplate(List<ApplyRegMapDO> applyRegMapDOs) {
        for (int i = 0; i < applyRegMapDOs.size(); ++i) {
            ApplyRegMapDO mapDefine = applyRegMapDOs.get(i);
            if (!StringUtils.hasText(mapDefine.getRelationtemplate())) continue;
            String relationtemplate = mapDefine.getRelationtemplate();
            ApplyRegMapService regMapService = (ApplyRegMapService)ApplicationContextRegister.getBean(ApplyRegMapService.class);
            ApplyRegMapDO applyRegMapDO = new ApplyRegMapDO();
            applyRegMapDO.setName(relationtemplate);
            List<ApplyRegMapDO> map = regMapService.getMap(applyRegMapDO);
            if (CollectionUtils.isEmpty(map)) continue;
            applyRegMapDOs.add(map.get(0));
        }
    }

    private void changeBillBydetail(ApplyRegMapDO mapDefine, MapInfoDTO mapinfo) {
        String unitcode = this.getMaster().getString("UNITCODE");
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
        for (int i = 0; i < applyBillDatas.size(); ++i) {
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
            cnEntry.setBillcode(this.getMaster().getString("BILLCODE"));
            cnEntry.setMasterid(this.getMaster().getString("ID"));
            this.insertChangeTransMessage(cnEntry);
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
        inputParam.put("body", cnEntry);
        inputParam.put("tableData", this.getData().getTablesData(false));
        this.getVaTransMessageService().insertMessage(vaTransMessageDO, inputParam);
        if (this.transMessage == null) {
            this.transMessage = vaTransMessageDO;
        }
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
                    logger.error("\u516c\u5f0f\u6267\u884c\u5f02\u5e38" + e.getMessage(), e);
                    return false;
                }
            }
            return true;
        }).collect(Collectors.toList());
    }
}

