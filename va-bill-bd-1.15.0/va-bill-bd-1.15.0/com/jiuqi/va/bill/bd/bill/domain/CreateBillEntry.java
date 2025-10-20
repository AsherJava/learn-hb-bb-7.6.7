/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.user.UserLoginDTO
 */
package com.jiuqi.va.bill.bd.bill.domain;

import com.jiuqi.va.bill.bd.core.domain.ApplyRegMapDO;
import com.jiuqi.va.domain.user.UserLoginDTO;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class CreateBillEntry
implements Serializable {
    private static final long serialVersionUID = 1L;
    public String alterdefinecode;
    public ApplyRegMapDO define;
    public String tablename;
    public String ID;
    public String billcode;
    private Integer createtype;
    private Map<String, Object> applyitemValue = new HashMap<String, Object>();
    public String tenantName;
    public UserLoginDTO user;
    public String unitcode;
    public Integer publishstate;
    public String masterid;
    public String exceptionid;
    private String targetBillCode;
    private String defineName;
    private Object delData;
    private Object syncData;
    private String msgId;

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public String getDefineName() {
        return this.defineName;
    }

    public void setDefineName(String defineName) {
        this.defineName = defineName;
    }

    public Object getDelData() {
        return this.delData;
    }

    public void setDelData(Object delData) {
        this.delData = delData;
    }

    public Object getSyncData() {
        return this.syncData;
    }

    public void setSyncData(Object syncData) {
        this.syncData = syncData;
    }

    public String getExceptionid() {
        return this.exceptionid;
    }

    public void setExceptionid(String exceptionid) {
        this.exceptionid = exceptionid;
    }

    public String getMasterid() {
        return this.masterid;
    }

    public void setMasterid(String masterid) {
        this.masterid = masterid;
    }

    public Integer getPublishstate() {
        return this.publishstate;
    }

    public void setPublishstate(Integer publishstate) {
        this.publishstate = publishstate;
    }

    public String getAlterdefinecode() {
        return this.alterdefinecode;
    }

    public void setAlterdefinecode(String alterdefinecode) {
        this.alterdefinecode = alterdefinecode;
    }

    public UserLoginDTO getUser() {
        return this.user;
    }

    public void setUser(UserLoginDTO user) {
        this.user = user;
    }

    public String getUnitcode() {
        return this.unitcode;
    }

    public void setUnitcode(String unitcode) {
        this.unitcode = unitcode;
    }

    public String getTenantName() {
        return this.tenantName;
    }

    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
    }

    public String getBillcode() {
        return this.billcode;
    }

    public void setBillcode(String billcode) {
        this.billcode = billcode;
    }

    public Map<String, Object> getApplyitemValue() {
        return this.applyitemValue;
    }

    public void setApplyitemValue(Map<String, Object> applyitemValue) {
        this.applyitemValue = applyitemValue;
    }

    public Integer getCreatetype() {
        return this.createtype;
    }

    public void setCreatetype(Integer createtype) {
        this.createtype = createtype;
    }

    public ApplyRegMapDO getDefine() {
        return this.define;
    }

    public void setDefine(ApplyRegMapDO define) {
        this.define = define;
    }

    public String getTablename() {
        return this.tablename;
    }

    public void setTablename(String tablename) {
        this.tablename = tablename;
    }

    public String getID() {
        return this.ID;
    }

    public void setID(String iD) {
        this.ID = iD;
    }

    public String getTargetBillCode() {
        return this.targetBillCode;
    }

    public void setTargetBillCode(String targetBillCode) {
        this.targetBillCode = targetBillCode;
    }

    public void setMessageId(String msgId) {
        this.msgId = msgId;
    }

    public String getMsgId() {
        return this.msgId;
    }
}

