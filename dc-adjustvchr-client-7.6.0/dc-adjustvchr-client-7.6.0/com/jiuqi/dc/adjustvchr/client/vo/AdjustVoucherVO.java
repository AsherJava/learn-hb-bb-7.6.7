/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.validation.constraints.NotNull
 */
package com.jiuqi.dc.adjustvchr.client.vo;

import com.jiuqi.dc.adjustvchr.client.vo.AdjustVchrItemVO;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.validation.constraints.NotNull;

public class AdjustVoucherVO
implements Serializable {
    private static final long serialVersionUID = 3211668165480225677L;
    private String id;
    private Long ver;
    @NotNull(message="\u7ec4\u7ec7\u673a\u6784\u4e0d\u80fd\u4e3a\u7a7a")
    private @NotNull(message="\u7ec4\u7ec7\u673a\u6784\u4e0d\u80fd\u4e3a\u7a7a") String unitCode;
    @NotNull(message="\u5e74\u5ea6\u4e0d\u80fd\u4e3a\u7a7a")
    private @NotNull(message="\u5e74\u5ea6\u4e0d\u80fd\u4e3a\u7a7a") Integer acctYear;
    private String vchrNum;
    @NotNull(message="\u8c03\u6574\u7c7b\u578b\u4e0d\u80fd\u4e3a\u7a7a")
    private @NotNull(message="\u8c03\u6574\u7c7b\u578b\u4e0d\u80fd\u4e3a\u7a7a") String adjustTypeCode;
    private String adjustTypeName;
    @NotNull(message="\u8d77\u59cb\u5f71\u54cd\u671f\u95f4\u4e0d\u80fd\u4e3a\u7a7a")
    private @NotNull(message="\u8d77\u59cb\u5f71\u54cd\u671f\u95f4\u4e0d\u80fd\u4e3a\u7a7a") String affectPeriodStart;
    @NotNull(message="\u622a\u6b62\u5f71\u54cd\u671f\u95f4\u4e0d\u80fd\u4e3a\u7a7a")
    private @NotNull(message="\u622a\u6b62\u5f71\u54cd\u671f\u95f4\u4e0d\u80fd\u4e3a\u7a7a") String affectPeriodEnd;
    private Date createTime;
    private String creator;
    private Date modifyTime;
    private String modifyUser;
    private List<AdjustVchrItemVO> items;
    private Double dsum;
    private Double csum;
    private Double difference;
    private String periodType;
    private String groupId;
    private Integer currentPeriod;
    private Integer acctPeriod;
    private String vchrSrcType;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getVer() {
        return this.ver;
    }

    public void setVer(Long ver) {
        this.ver = ver;
    }

    public String getUnitCode() {
        return this.unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public Integer getAcctYear() {
        return this.acctYear;
    }

    public void setAcctYear(Integer acctYear) {
        this.acctYear = acctYear;
    }

    public String getVchrNum() {
        return this.vchrNum;
    }

    public void setVchrNum(String vchrNum) {
        this.vchrNum = vchrNum;
    }

    public String getAdjustTypeCode() {
        return this.adjustTypeCode;
    }

    public void setAdjustTypeCode(String adjustTypeCode) {
        this.adjustTypeCode = adjustTypeCode;
    }

    public String getAdjustTypeName() {
        return this.adjustTypeName;
    }

    public void setAdjustTypeName(String adjustTypeName) {
        this.adjustTypeName = adjustTypeName;
    }

    public String getAffectPeriodStart() {
        return this.affectPeriodStart;
    }

    public void setAffectPeriodStart(String affectPeriodStart) {
        this.affectPeriodStart = affectPeriodStart;
    }

    public String getAffectPeriodEnd() {
        return this.affectPeriodEnd;
    }

    public void setAffectPeriodEnd(String affectPeriodEnd) {
        this.affectPeriodEnd = affectPeriodEnd;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreator() {
        return this.creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Date getModifyTime() {
        return this.modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getModifyUser() {
        return this.modifyUser;
    }

    public void setModifyUser(String modifyUser) {
        this.modifyUser = modifyUser;
    }

    public List<AdjustVchrItemVO> getItems() {
        return this.items;
    }

    public void setItems(List<AdjustVchrItemVO> items) {
        this.items = items;
    }

    public Double getDsum() {
        return this.dsum;
    }

    public void setDsum(Double dsum) {
        this.dsum = dsum;
    }

    public Double getCsum() {
        return this.csum;
    }

    public void setCsum(Double csum) {
        this.csum = csum;
    }

    public Double getDifference() {
        return this.difference;
    }

    public void setDifference(Double difference) {
        this.difference = difference;
    }

    public String getPeriodType() {
        return this.periodType;
    }

    public void setPeriodType(String periodType) {
        this.periodType = periodType;
    }

    public String getGroupId() {
        return this.groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public Integer getCurrentPeriod() {
        return this.currentPeriod;
    }

    public void setCurrentPeriod(Integer currentPeriod) {
        this.currentPeriod = currentPeriod;
    }

    public Integer getAcctPeriod() {
        return this.acctPeriod;
    }

    public void setAcctPeriod(Integer acctPeriod) {
        this.acctPeriod = acctPeriod;
    }

    public String getVchrSrcType() {
        return this.vchrSrcType;
    }

    public void setVchrSrcType(String vchrSrcType) {
        this.vchrSrcType = vchrSrcType;
    }
}

