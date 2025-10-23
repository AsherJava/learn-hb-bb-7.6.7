/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFormat
 *  com.fasterxml.jackson.annotation.JsonFormat$Shape
 *  com.fasterxml.jackson.annotation.JsonIgnore
 *  com.fasterxml.jackson.annotation.JsonIgnoreProperties
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.nr.period.common.utils.NrPeriodConst
 *  com.jiuqi.nr.period.common.utils.PeriodPropertyGroup
 *  com.jiuqi.nr.period.modal.IPeriodEntity
 */
package com.jiuqi.nr.param.transfer.period;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.nr.period.common.utils.NrPeriodConst;
import com.jiuqi.nr.period.common.utils.PeriodPropertyGroup;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import java.util.Date;
import org.springframework.beans.BeanUtils;

@JsonIgnoreProperties(ignoreUnknown=true)
public class PeriodEntityDTO
implements IPeriodEntity {
    private static final long serialVersionUID = 1L;
    private String key;
    private String code;
    private String title;
    private PeriodType type;
    private String createUser;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH-mm-ss.sss zzz")
    private Date createTime;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH-mm-ss.sss zzz")
    private Date updateTime;
    private String updateUser;
    private PeriodPropertyGroup periodPropertyGroup;
    private int maxFiscalMonth;
    private int minFiscalMonth;
    private int minYear;
    private int maxYear;
    private int dataType;

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getID() {
        return this.key;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public PeriodType getType() {
        return this.type;
    }

    public void setType(PeriodType type) {
        this.type = type;
    }

    public String getCreateUser() {
        return this.createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateUser() {
        return this.updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    @JsonIgnore
    public PeriodType getPeriodType() {
        return this.type;
    }

    @JsonIgnore
    public String getDimensionName() {
        return NrPeriodConst.DATETIME;
    }

    public void setPeriodPropertyGroup(PeriodPropertyGroup periodPropertyGroup) {
        this.periodPropertyGroup = periodPropertyGroup;
    }

    public PeriodPropertyGroup getPeriodPropertyGroup() {
        return this.periodPropertyGroup;
    }

    @JsonIgnore
    public String getOrder() {
        return null;
    }

    @JsonIgnore
    public String getVersion() {
        return null;
    }

    @JsonIgnore
    public String getOwnerLevelAndId() {
        return null;
    }

    public int getMaxFiscalMonth() {
        return this.maxFiscalMonth;
    }

    public void setMaxFiscalMonth(int maxFiscalMonth) {
        this.maxFiscalMonth = maxFiscalMonth;
    }

    public int getMinFiscalMonth() {
        return this.minFiscalMonth;
    }

    public void setMinFiscalMonth(int minFiscalMonth) {
        this.minFiscalMonth = minFiscalMonth;
    }

    public int getMinYear() {
        return this.minYear;
    }

    public void setMinYear(int minYear) {
        this.minYear = minYear;
    }

    public int getMaxYear() {
        return this.maxYear;
    }

    public void setMaxYear(int maxYear) {
        this.maxYear = maxYear;
    }

    public int getDataType() {
        return this.dataType;
    }

    public void setDataType(int dataType) {
        this.dataType = dataType;
    }

    public PeriodEntityDTO() {
    }

    public PeriodEntityDTO(IPeriodEntity periodEntity) {
        BeanUtils.copyProperties(periodEntity, this);
    }
}

