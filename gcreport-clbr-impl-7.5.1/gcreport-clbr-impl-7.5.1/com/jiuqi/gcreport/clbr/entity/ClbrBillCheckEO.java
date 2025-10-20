/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn$DBType
 *  com.jiuqi.gcreport.definition.impl.anno.DBTable
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 */
package com.jiuqi.gcreport.clbr.entity;

import com.jiuqi.gcreport.definition.impl.anno.DBColumn;
import com.jiuqi.gcreport.definition.impl.anno.DBTable;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import java.util.Date;

@DBTable(name="GC_CLBR_BILLCHECK", title="\u5df2\u534f\u540c\u91d1\u989d\u5bf9\u8d26\u8868")
public class ClbrBillCheckEO
extends DefaultTableEntity {
    public static final String TABLENAME = "GC_CLBR_BILLCHECK";
    @DBColumn(title="\u672c\u65b9\u534f\u540c\u5355ID", dbType=DBColumn.DBType.Varchar, length=60)
    private String billId;
    @DBColumn(title="\u672c\u65b9\u534f\u540c\u5355\u6765\u6e90ID", dbType=DBColumn.DBType.Varchar, length=200)
    private String srcId;
    @DBColumn(title="\u672c\u65b9\u534f\u540c\u5355\u636e\u7f16\u53f7", dbType=DBColumn.DBType.Varchar, length=100)
    private String clbrBillCode;
    @DBColumn(title="\u672c\u65b9\u534f\u540c\u5355\u6240\u5c5e\u7cfb\u7edf", dbType=DBColumn.DBType.Varchar, length=60)
    private String sysCode;
    @DBColumn(title="\u534f\u540c\u786e\u8ba4\u7c7b\u578b", dbType=DBColumn.DBType.Int, length=2)
    private Integer confirmType;
    @DBColumn(title="\u5e01\u79cd", dbType=DBColumn.DBType.Varchar, length=60)
    private String currency = "CNY";
    @DBColumn(title="\u672c\u6b21\u534f\u540c\u91d1\u989d", dbType=DBColumn.DBType.Numeric, precision=19, scale=2)
    private Double clbrAmount;
    @DBColumn(title="\u672c\u6b21\u534f\u540c\u7801", dbType=DBColumn.DBType.Varchar, length=60)
    private String clbrCode;
    @DBColumn(title="\u672c\u6b21\u534f\u540c\u65f6\u95f4", dbType=DBColumn.DBType.DateTime)
    private Date clbrTime;
    @DBColumn(title="\u53d1\u8d77\u65b9\u5173\u8054\u65b9\u540d\u5f55", dbType=DBColumn.DBType.Varchar, length=200, refTabField="MD_RELATION.OBJECTCODE")
    private String relation;
    @DBColumn(title="\u63a5\u6536\u65b9\u5173\u8054\u65b9\u540d\u5f55", dbType=DBColumn.DBType.Varchar, length=200, refTabField="MD_RELATION.OBJECTCODE")
    private String oppRelation;
    @DBColumn(title="\u672c\u65b9\u5355\u4f4d", dbType=DBColumn.DBType.Varchar, length=200, refTabField="MD_RELATION.OBJECTCODE")
    private String thisRelation;
    @DBColumn(title="\u5bf9\u65b9\u5355\u4f4d", dbType=DBColumn.DBType.Varchar, length=200, refTabField="MD_RELATION.OBJECTCODE")
    private String thatRelation;
    @DBColumn(title="\u4e1a\u52a1\u7c7b\u578b", dbType=DBColumn.DBType.Varchar, length=60, refTabField="MD_CLBRTYPE.OBJECTCODE")
    private String clbrType;
    @DBColumn(title="\u5bf9\u65b9\u4e1a\u52a1\u7c7b\u578b", dbType=DBColumn.DBType.Varchar, length=60, refTabField="MD_CLBRTYPE.OBJECTCODE")
    private String oppClbrType;
    @DBColumn(title="\u672c\u65b9\u603b\u91d1\u989d", dbType=DBColumn.DBType.Numeric, precision=19, scale=2)
    private Double amount;
    @DBColumn(title="\u672c\u65b9\u5355\u636e\u521b\u5efa\u65f6\u95f4", dbType=DBColumn.DBType.DateTime)
    private Date createTime;
    @DBColumn(title="\u534f\u540c\u4e1a\u52a1\u5355\u7c7b\u578b", dbType=DBColumn.DBType.Int, length=2)
    private Integer clbrBillType;
    @DBColumn(title="\u5206\u7ec4Id", dbType=DBColumn.DBType.Varchar, length=60)
    private String groupId;
    @DBColumn(title="\u534f\u540c\u4e1a\u52a1\u5355\u7c7b\u578b", dbType=DBColumn.DBType.Int, length=2)
    private Integer confirmStatus;
    @DBColumn(title="\u4e1a\u52a1\u7528\u6237\u540d", dbType=DBColumn.DBType.Varchar, length=60)
    private String userName;
    @DBColumn(title="\u5bf9\u65b9\u4e1a\u52a1\u7528\u6237\u540d", dbType=DBColumn.DBType.Varchar, length=60)
    private String oppUserName;
    @DBColumn(title="\u53d1\u8d77\u534f\u540c\u786e\u8ba4\u7528\u6237\u540d", dbType=DBColumn.DBType.Varchar, length=60)
    private String initiateConfirmUsername;
    @DBColumn(title="\u63a5\u6536\u534f\u540c\u786e\u8ba4\u7528\u6237\u540d", dbType=DBColumn.DBType.Varchar, length=60)
    private String receiveConfirmUsername;
    @DBColumn(title="\u5907\u6ce8", dbType=DBColumn.DBType.NVarchar, length=300)
    private String remark;

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getThisRelation() {
        return this.thisRelation;
    }

    public void setThisRelation(String thisRelation) {
        this.thisRelation = thisRelation;
    }

    public String getThatRelation() {
        return this.thatRelation;
    }

    public void setThatRelation(String thatRelation) {
        this.thatRelation = thatRelation;
    }

    public String getRelation() {
        return this.relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public String getOppRelation() {
        return this.oppRelation;
    }

    public void setOppRelation(String oppRelation) {
        this.oppRelation = oppRelation;
    }

    public String getClbrType() {
        return this.clbrType;
    }

    public void setClbrType(String clbrType) {
        this.clbrType = clbrType;
    }

    public Double getAmount() {
        return this.amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getBillId() {
        return this.billId;
    }

    public void setBillId(String billId) {
        this.billId = billId;
    }

    public Integer getConfirmType() {
        return this.confirmType;
    }

    public void setConfirmType(Integer confirmType) {
        this.confirmType = confirmType;
    }

    public String getCurrency() {
        return this.currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Double getClbrAmount() {
        return this.clbrAmount;
    }

    public void setClbrAmount(Double clbrAmount) {
        this.clbrAmount = clbrAmount;
    }

    public String getClbrCode() {
        return this.clbrCode;
    }

    public void setClbrCode(String clbrCode) {
        this.clbrCode = clbrCode;
    }

    public Date getClbrTime() {
        return this.clbrTime;
    }

    public void setClbrTime(Date clbrTime) {
        this.clbrTime = clbrTime;
    }

    public String getClbrBillCode() {
        return this.clbrBillCode;
    }

    public void setClbrBillCode(String clbrBillCode) {
        this.clbrBillCode = clbrBillCode;
    }

    public String getSysCode() {
        return this.sysCode;
    }

    public void setSysCode(String sysCode) {
        this.sysCode = sysCode;
    }

    public String getSrcId() {
        return this.srcId;
    }

    public void setSrcId(String srcId) {
        this.srcId = srcId;
    }

    public Integer getClbrBillType() {
        return this.clbrBillType;
    }

    public void setClbrBillType(Integer clbrBillType) {
        this.clbrBillType = clbrBillType;
    }

    public Integer getConfirmStatus() {
        return this.confirmStatus;
    }

    public void setConfirmStatus(Integer confirmStatus) {
        this.confirmStatus = confirmStatus;
    }

    public String getGroupId() {
        return this.groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getOppClbrType() {
        return this.oppClbrType;
    }

    public void setOppClbrType(String oppClbrType) {
        this.oppClbrType = oppClbrType;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getOppUserName() {
        return this.oppUserName;
    }

    public void setOppUserName(String oppUserName) {
        this.oppUserName = oppUserName;
    }

    public String getInitiateConfirmUsername() {
        return this.initiateConfirmUsername;
    }

    public void setInitiateConfirmUsername(String initiateConfirmUsername) {
        this.initiateConfirmUsername = initiateConfirmUsername;
    }

    public String getReceiveConfirmUsername() {
        return this.receiveConfirmUsername;
    }

    public void setReceiveConfirmUsername(String receiveConfirmUsername) {
        this.receiveConfirmUsername = receiveConfirmUsername;
    }
}

