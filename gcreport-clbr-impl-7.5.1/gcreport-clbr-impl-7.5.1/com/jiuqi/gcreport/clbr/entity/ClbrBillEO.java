/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.clbr.enums.ClbrBillStateEnum
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn$DBType
 *  com.jiuqi.gcreport.definition.impl.anno.DBIndex
 *  com.jiuqi.gcreport.definition.impl.anno.DBIndex$TableIndexType
 *  com.jiuqi.gcreport.definition.impl.anno.DBTable
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 */
package com.jiuqi.gcreport.clbr.entity;

import com.jiuqi.gcreport.clbr.enums.ClbrBillStateEnum;
import com.jiuqi.gcreport.definition.impl.anno.DBColumn;
import com.jiuqi.gcreport.definition.impl.anno.DBIndex;
import com.jiuqi.gcreport.definition.impl.anno.DBTable;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import java.util.Date;

@DBTable(name="GC_CLBR_BILL", title="\u534f\u540c\u4e1a\u52a1\u5355", indexs={@DBIndex(name="IDX_GC_CLBR_BILL_SRCID", columnsFields={"SRCID"}, type=DBIndex.TableIndexType.TABLE_INDEX_UNIQUE)})
public class ClbrBillEO
extends DefaultTableEntity {
    public static final String TABLENAME = "GC_CLBR_BILL";
    @DBColumn(title="\u6e90\u5355ID", dbType=DBColumn.DBType.Varchar, length=200)
    private String srcId;
    @DBColumn(title="\u6765\u6e90\u7cfb\u7edf\u6807\u8bc6", dbType=DBColumn.DBType.Varchar, length=60)
    private String sysCode;
    @DBColumn(title="\u534f\u540c\u4e1a\u52a1\u5355\u7c7b\u578b", dbType=DBColumn.DBType.Int, length=2)
    private Integer clbrBillType;
    @DBColumn(title="\u534f\u540c\u786e\u8ba4\u7c7b\u578b", dbType=DBColumn.DBType.Int, length=2)
    private Integer confirmType;
    @DBColumn(title="\u53d1\u8d77\u65b9\u5355\u4f4d", dbType=DBColumn.DBType.Varchar, length=200, refTabField="MD_RELATION.OBJECTCODE")
    private String relation;
    @DBColumn(title="\u672c\u65b9\u5355\u4f4d", dbType=DBColumn.DBType.Varchar, length=200, refTabField="MD_RELATION.OBJECTCODE")
    private String thisRelation;
    @DBColumn(title="\u53d1\u8d77\u65b9\u7ec4\u7ec7\u673a\u6784", dbType=DBColumn.DBType.Varchar, length=200)
    private String orgCode;
    @DBColumn(title="\u63a5\u6536\u65b9\u5355\u4f4d", dbType=DBColumn.DBType.Varchar, length=200, refTabField="MD_RELATION.OBJECTCODE")
    private String oppRelation;
    @DBColumn(title="\u5bf9\u65b9\u5355\u4f4d", dbType=DBColumn.DBType.Varchar, length=200, refTabField="MD_RELATION.OBJECTCODE")
    private String thatRelation;
    @DBColumn(title="\u63a5\u6536\u65b9\u7ec4\u7ec7\u673a\u6784", dbType=DBColumn.DBType.Varchar, length=200)
    private String oppOrgCode;
    @DBColumn(title="\u53d1\u8d77\u65b9\u534f\u540c\u4e1a\u52a1\u7c7b\u578b", dbType=DBColumn.DBType.Varchar, length=60, refTabField="MD_CLBRTYPE.OBJECTCODE")
    private String clbrType;
    @DBColumn(title="\u63a5\u6536\u65b9\u534f\u540c\u4e1a\u52a1\u7c7b\u578b", dbType=DBColumn.DBType.Varchar, length=60, refTabField="MD_CLBRTYPE.OBJECTCODE")
    private String oppClbrType;
    @DBColumn(title="\u534f\u540c\u5355\u636e\u7f16\u53f7", dbType=DBColumn.DBType.Varchar, length=100)
    private String clbrBillCode;
    @DBColumn(title="\u5bf9\u65b9\u534f\u540c\u5355\u636e\u7f16\u53f7", dbType=DBColumn.DBType.Varchar, length=100)
    private String oppClbrBillCode;
    @DBColumn(title="\u5bf9\u65b9\u534f\u540c\u5355\u636eID\uff08\u53d1\u8d77\u65b9SRCID\uff09", dbType=DBColumn.DBType.Varchar, length=200)
    private String oppSrcId;
    @DBColumn(title="\u5e01\u79cd", dbType=DBColumn.DBType.Varchar, length=60)
    private String currency = "CNY";
    @DBColumn(title="\u4e1a\u52a1\u7528\u6237\u540d", dbType=DBColumn.DBType.Varchar, length=60)
    private String userName;
    @DBColumn(title="\u4e1a\u52a1\u7528\u6237\u540d\u79f0", dbType=DBColumn.DBType.Varchar, length=60)
    private String userTitle;
    @DBColumn(title="\u4e0b\u4e00\u8282\u70b9\u7528\u6237\u540d", dbType=DBColumn.DBType.Varchar, length=60)
    private String nextUserName;
    @DBColumn(title="\u5bf9\u65b9\u4e1a\u52a1\u7528\u6237\u540d", dbType=DBColumn.DBType.Varchar, length=60)
    private String oppUserName;
    @DBColumn(title="\u91d1\u989d", dbType=DBColumn.DBType.Numeric, precision=19, scale=2)
    private Double amount;
    @DBColumn(title="\u5df2\u786e\u8ba4\u91d1\u989d", dbType=DBColumn.DBType.Numeric, precision=19, scale=2)
    private Double verifyedAmount;
    @DBColumn(title="\u672a\u786e\u8ba4\u91d1\u989d", dbType=DBColumn.DBType.Numeric, precision=19, scale=2)
    private Double noverifyAmount;
    @DBColumn(title="\u534f\u540c\u65b9\u6848ID", dbType=DBColumn.DBType.Varchar)
    private String clbrSchemeId;
    @DBColumn(title="\u6d41\u7a0b\u63a7\u5236\u7c7b\u578b", dbType=DBColumn.DBType.Varchar)
    private String flowControlType;
    @DBColumn(title="\u51ed\u8bc1\u63a7\u5236\u7c7b\u578b", dbType=DBColumn.DBType.Varchar)
    private String vchrControlType;
    @DBColumn(title="\u534f\u540c\u7801", dbType=DBColumn.DBType.Varchar)
    private String clbrCode;
    @DBColumn(title="\u534f\u540c\u65e5\u671f", dbType=DBColumn.DBType.DateTime)
    private Date clbrTime;
    @DBColumn(title="\u4e1a\u52a1\u65e5\u671f", dbType=DBColumn.DBType.DateTime)
    private Date createTime;
    @DBColumn(title="\u4fee\u6539\u65e5\u671f", dbType=DBColumn.DBType.DateTime)
    private Date modifyTime;
    @DBColumn(title="\u9a73\u56de\u4eba", dbType=DBColumn.DBType.NVarchar, length=100)
    private String rejectUserName;
    @DBColumn(title="\u9a73\u56de\u65e5\u671f", dbType=DBColumn.DBType.DateTime)
    private Date rejectTime;
    @DBColumn(title="\u6570\u636e\u72b6\u6001", dbType=DBColumn.DBType.Int)
    private Integer billState = ClbrBillStateEnum.INIT.getCode();
    @DBColumn(title="\u6279\u6b21\u53f7", dbType=DBColumn.DBType.Varchar, length=100)
    private String sn;
    @DBColumn(title="\u4ef2\u88c1\u4eba", dbType=DBColumn.DBType.NVarchar, length=100)
    private String arbitrationUserName;
    @DBColumn(title="\u534f\u540c\u9a73\u56de\u539f\u56e0", dbType=DBColumn.DBType.NVarchar, length=500)
    private String rejectionMessage;
    @DBColumn(title="\u5907\u6ce8", dbType=DBColumn.DBType.NVarchar, length=500)
    private String remark;
    @DBColumn(title="\u5f85\u534f\u540c\u63a5\u6536\u65b9\u5355\u636e\u7f16\u53f7", dbType=DBColumn.DBType.Varchar, length=100)
    private String unClbrReceBillCode;

    public String getUnClbrReceBillCode() {
        return this.unClbrReceBillCode;
    }

    public void setUnClbrReceBillCode(String unClbrReceBillCode) {
        this.unClbrReceBillCode = unClbrReceBillCode;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getArbitrationUserName() {
        return this.arbitrationUserName;
    }

    public void setArbitrationUserName(String arbitrationUserName) {
        this.arbitrationUserName = arbitrationUserName;
    }

    public String getRejectionMessage() {
        return this.rejectionMessage;
    }

    public void setRejectionMessage(String rejectionMessage) {
        this.rejectionMessage = rejectionMessage;
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

    public String getSrcId() {
        return this.srcId;
    }

    public void setSrcId(String srcId) {
        this.srcId = srcId;
    }

    public String getSysCode() {
        return this.sysCode;
    }

    public void setSysCode(String sysCode) {
        this.sysCode = sysCode;
    }

    public Integer getClbrBillType() {
        return this.clbrBillType;
    }

    public void setClbrBillType(Integer clbrBillType) {
        this.clbrBillType = clbrBillType;
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

    public String getOppClbrType() {
        return this.oppClbrType;
    }

    public void setOppClbrType(String oppClbrType) {
        this.oppClbrType = oppClbrType;
    }

    public String getClbrBillCode() {
        return this.clbrBillCode;
    }

    public void setClbrBillCode(String clbrBillCode) {
        this.clbrBillCode = clbrBillCode;
    }

    public String getOppClbrBillCode() {
        return this.oppClbrBillCode;
    }

    public void setOppClbrBillCode(String oppClbrBillCode) {
        this.oppClbrBillCode = oppClbrBillCode;
    }

    public String getCurrency() {
        return this.currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserTitle() {
        return this.userTitle;
    }

    public void setUserTitle(String userTitle) {
        this.userTitle = userTitle;
    }

    public String getNextUserName() {
        return this.nextUserName;
    }

    public void setNextUserName(String nextUserName) {
        this.nextUserName = nextUserName;
    }

    public Double getAmount() {
        return this.amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getVerifyedAmount() {
        return this.verifyedAmount;
    }

    public void setVerifyedAmount(Double verifyedAmount) {
        this.verifyedAmount = verifyedAmount;
    }

    public Double getNoverifyAmount() {
        return this.noverifyAmount;
    }

    public void setNoverifyAmount(Double noverifyAmount) {
        this.noverifyAmount = noverifyAmount;
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

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return this.modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Integer getBillState() {
        return this.billState;
    }

    public void setBillState(Integer billState) {
        this.billState = billState;
    }

    public String getSn() {
        return this.sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getOrgCode() {
        return this.orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getOppOrgCode() {
        return this.oppOrgCode;
    }

    public void setOppOrgCode(String oppOrgCode) {
        this.oppOrgCode = oppOrgCode;
    }

    public String getOppUserName() {
        return this.oppUserName;
    }

    public void setOppUserName(String oppUserName) {
        this.oppUserName = oppUserName;
    }

    public String getRejectUserName() {
        return this.rejectUserName;
    }

    public void setRejectUserName(String rejectUserName) {
        this.rejectUserName = rejectUserName;
    }

    public Date getRejectTime() {
        return this.rejectTime;
    }

    public void setRejectTime(Date rejectTime) {
        this.rejectTime = rejectTime;
    }

    public Integer getConfirmType() {
        return this.confirmType;
    }

    public void setConfirmType(Integer confirmType) {
        this.confirmType = confirmType;
    }

    public void setClbrSchemeId(String clbrSchemeId) {
        this.clbrSchemeId = clbrSchemeId;
    }

    public String getClbrSchemeId() {
        return this.clbrSchemeId;
    }

    public String getFlowControlType() {
        return this.flowControlType;
    }

    public void setFlowControlType(String flowControlType) {
        this.flowControlType = flowControlType;
    }

    public String getVchrControlType() {
        return this.vchrControlType;
    }

    public void setVchrControlType(String vchrControlType) {
        this.vchrControlType = vchrControlType;
    }

    public String getOppSrcId() {
        return this.oppSrcId;
    }

    public void setOppSrcId(String oppSrcId) {
        this.oppSrcId = oppSrcId;
    }
}

