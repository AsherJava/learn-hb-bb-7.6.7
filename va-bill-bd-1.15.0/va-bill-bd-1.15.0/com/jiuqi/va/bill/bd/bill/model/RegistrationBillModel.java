/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.bill.impl.BillModelImpl
 *  com.jiuqi.va.bill.intf.BillException
 *  com.jiuqi.va.biz.intf.data.DataRow
 *  com.jiuqi.va.biz.ruler.intf.CheckResult
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.R
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
import com.jiuqi.va.bill.bd.bill.impl.BillToMasterHandle;
import com.jiuqi.va.bill.bd.utils.BillBdI18nUtil;
import com.jiuqi.va.bill.impl.BillModelImpl;
import com.jiuqi.va.bill.intf.BillException;
import com.jiuqi.va.biz.intf.data.DataRow;
import com.jiuqi.va.biz.ruler.intf.CheckResult;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import com.jiuqi.va.trans.domain.VaTransMessageDO;
import com.jiuqi.va.trans.domain.VaTransMessageDTO;
import com.jiuqi.va.trans.service.VaTransMessageService;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

public class RegistrationBillModel
extends BillModelImpl {
    private static final Logger logger = LoggerFactory.getLogger(RegistrationBillModel.class);
    private Boolean writeBackFlag;
    private String tableName;
    private String filedName;
    private String id;
    private String msgId;
    private BillToMasterHandle billToMasterHandle;
    private String deleteFlagField;
    private VaTransMessageDO vaTransMessageDO;
    private VaTransMessageService vaTransMessageService;
    private PlatformTransactionManager platformTransactionManager;

    public void setDeleteFlagField(String deleteFlagField) {
        this.deleteFlagField = deleteFlagField;
    }

    public String getDeleteFlagField() {
        return this.deleteFlagField;
    }

    public void init(Boolean writeBackFlag, String tableName, String filedName, String id) {
        this.writeBackFlag = writeBackFlag;
        this.tableName = tableName;
        this.filedName = filedName;
        this.id = id;
    }

    public Boolean getWriteBackFlag() {
        return this.writeBackFlag;
    }

    public String getTableName() {
        return this.tableName;
    }

    public String getFiledName() {
        return this.filedName;
    }

    public String getId() {
        return this.id;
    }

    public String getMsgId() {
        return this.msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    private BillToMasterHandle getBillToMasterHandle() {
        if (this.billToMasterHandle == null) {
            this.billToMasterHandle = (BillToMasterHandle)ApplicationContextRegister.getBean(BillToMasterHandle.class);
        }
        return this.billToMasterHandle;
    }

    public VaTransMessageService getVaTransMessageService() {
        if (this.vaTransMessageService == null) {
            this.vaTransMessageService = (VaTransMessageService)ApplicationContextRegister.getBean(VaTransMessageService.class);
        }
        return this.vaTransMessageService;
    }

    private PlatformTransactionManager getPlatformTransactionManager() {
        if (this.platformTransactionManager == null) {
            this.platformTransactionManager = (PlatformTransactionManager)ApplicationContextRegister.getBean(PlatformTransactionManager.class);
        }
        return this.platformTransactionManager;
    }

    public void add() {
        super.add();
    }

    public void edit() {
        if (this.isCreating()) {
            throw new BillException("\u5b58\u5728\u6b63\u5728\u540c\u6b65\u7684\u57fa\u7840\u6570\u636e");
        }
        super.edit();
    }

    private boolean isCreating() {
        VaTransMessageDTO vaTransMessageDO = new VaTransMessageDTO();
        vaTransMessageDO.setBizcode(this.getMaster().getString("BILLCODE"));
        List resule = this.getVaTransMessageService().listTransMessage(vaTransMessageDO);
        return !CollectionUtils.isEmpty(resule) && ((VaTransMessageDO)resule.get(resule.size() - 1)).getStatus() != 2 && ((VaTransMessageDO)resule.get(resule.size() - 1)).getStatus() != 3;
    }

    public void save() {
        TransactionStatus transaction = this.getPlatformTransactionManager().getTransaction((TransactionDefinition)new DefaultTransactionDefinition());
        try {
            DataRow dataRow = this.getMaster();
            String basedataname = dataRow.getString("BASEDATANAME");
            if (!StringUtils.hasText(basedataname)) {
                throw new BillException(BillBdI18nUtil.getMessage("va.billbd.basedata.table.field.not.empty"));
            }
            this.check(dataRow, basedataname);
            if (StringUtils.hasText(this.deleteFlagField)) {
                this.getMaster().setValue(this.deleteFlagField, (Object)true);
                super.save();
                this.deleteBasedata(basedataname, dataRow);
            } else {
                super.save();
                this.syncBasedata(dataRow, basedataname);
            }
            this.getPlatformTransactionManager().commit(transaction);
            R r = this.getVaTransMessageService().sendAndReceive(this.vaTransMessageDO, JSONUtil.parseMap((String)this.vaTransMessageDO.getInputparam()));
            if (r.getCode() != 0) {
                logger.error("\u57fa\u7840\u6570\u636e\u540c\u6b65\u5931\u8d25{}", (Object)r.getMsg());
            }
        }
        catch (Exception e) {
            logger.error("\u767b\u8bb0\u5355\u4fdd\u5b58\u5931\u8d25\uff1a{}", (Object)e.getMessage(), (Object)e);
            this.getPlatformTransactionManager().rollback(transaction);
            throw new BillException(e.getMessage());
        }
    }

    private void syncBasedata(DataRow dataRow, String baseDataName) {
        Object syncData = this.getBillToMasterHandle().createMasterData(this, baseDataName.toUpperCase());
        VaTransMessageDO vaTransMessageDO = new VaTransMessageDO();
        vaTransMessageDO.setId(UUID.randomUUID().toString());
        vaTransMessageDO.setBizcategory("BILL");
        vaTransMessageDO.setBiztype(this.getDefine().getName());
        vaTransMessageDO.setBizcode(this.getMaster().getString("BILLCODE"));
        vaTransMessageDO.setMqname("VA_BILL_BD_SYNCREFDATA");
        vaTransMessageDO.setOperatordesc("\u540c\u6b65\u57fa\u7840\u6570\u636e\u9879");
        HashMap<String, CreateBillEntry> inputParam = new HashMap<String, CreateBillEntry>();
        CreateBillEntry createBillEntry = new CreateBillEntry();
        createBillEntry.setDefineName(baseDataName);
        createBillEntry.setSyncData(syncData);
        createBillEntry.setMessageId(this.msgId);
        inputParam.put("body", createBillEntry);
        this.getVaTransMessageService().insertMessage(vaTransMessageDO, inputParam);
        this.vaTransMessageDO = vaTransMessageDO;
    }

    private void deleteBasedata(String basedataname, DataRow dataRow) {
        VaTransMessageDO vaTransMessageDO = new VaTransMessageDO();
        vaTransMessageDO.setId(UUID.randomUUID().toString());
        vaTransMessageDO.setBizcategory("BILL");
        vaTransMessageDO.setBiztype(this.getDefine().getName());
        vaTransMessageDO.setBizcode(this.getMaster().getString("BILLCODE"));
        vaTransMessageDO.setMqname("VA_BILL_BD_SYNCREFDATA");
        vaTransMessageDO.setOperatordesc("\u5220\u9664\u57fa\u7840\u6570\u636e\u9879");
        HashMap<String, CreateBillEntry> inputParam = new HashMap<String, CreateBillEntry>();
        CreateBillEntry createBillEntry = new CreateBillEntry();
        createBillEntry.setDefineName(basedataname);
        createBillEntry.setDelData(this.getBillToMasterHandle().getDelMasterData(dataRow, basedataname));
        createBillEntry.setMessageId(this.msgId);
        inputParam.put("body", createBillEntry);
        this.getVaTransMessageService().insertMessage(vaTransMessageDO, inputParam);
        this.vaTransMessageDO = vaTransMessageDO;
    }

    public void check(DataRow dataRow, String basedataname) {
        this.getBillToMasterHandle().beforeCheckByCreate(dataRow, basedataname.toUpperCase());
    }

    public void delete() {
        super.delete();
    }

    public boolean afterApproval() {
        return super.afterApproval();
    }

    public void beforeCheck() {
        BillModelImpl billModel = (BillModelImpl)this.getData().getModel();
        List save = billModel.getRuler().getRulerExecutor().beforeAction("save");
        if (!save.isEmpty()) {
            StringBuilder stringBuffer = new StringBuilder();
            for (CheckResult checkResult : save) {
                stringBuffer.append(checkResult.getCheckMessage());
            }
            throw new BillException(BillBdI18nUtil.getMessage("va.billbd.basedata.verify.error") + stringBuffer);
        }
    }
}

