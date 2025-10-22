/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnore
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.nvwa.definition.interval.anno.DBAnno$DBField
 *  com.jiuqi.nvwa.definition.interval.anno.DBAnno$DBTable
 */
package com.jiuqi.nr.period.modal.impl;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.nr.period.common.utils.NrPeriodConst;
import com.jiuqi.nr.period.common.utils.PeriodPropertyGroup;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import com.jiuqi.nvwa.definition.interval.anno.DBAnno;
import java.sql.Timestamp;
import java.util.Date;

@DBAnno.DBTable(dbTable="nr_period")
public class PeriodDefineImpl
implements IPeriodEntity {
    private static final long serialVersionUID = 1L;
    @DBAnno.DBField(dbField="P_KEY", isPk=true)
    private String key;
    @DBAnno.DBField(dbField="P_CODE")
    private String code;
    @DBAnno.DBField(dbField="P_TITLE")
    private String title;
    @DBAnno.DBField(dbField="P_TYPE", set="setPeriodTypeDB", get="getPeriodTypeDB", dbType=Integer.class)
    private PeriodType type;
    @DBAnno.DBField(dbField="P_CREATE_USER")
    private String createUser;
    @DBAnno.DBField(dbField="P_CREATE_TIME", tranWith="transTimeStamp", dbType=Timestamp.class, appType=Date.class)
    private Date createTime;
    @DBAnno.DBField(dbField="P_UPDATE_TIME", tranWith="transTimeStamp", dbType=Timestamp.class, appType=Date.class)
    private Date updateTime;
    @DBAnno.DBField(dbField="P_UPDATE_USER")
    private String updateUser;
    @DBAnno.DBField(dbField="P_PERIOD_GROUP", set="setPeriodPropertyGroupDB", get="getPeriodPropertyGroupDB", dbType=Integer.class)
    private PeriodPropertyGroup periodPropertyGroup;
    @DBAnno.DBField(dbField="P_MAXFISCALMONTH")
    private int maxFiscalMonth;
    @DBAnno.DBField(dbField="P_MINFISCALMONTH")
    private int minFiscalMonth;
    @DBAnno.DBField(dbField="P_MINYEAR")
    private int minYear;
    @DBAnno.DBField(dbField="P_MAXYEAR")
    private int maxYear;
    @DBAnno.DBField(dbField="P_DATATYPE")
    private int dataType;

    @Override
    public int getDataType() {
        return this.dataType;
    }

    public void setDataType(int dataType) {
        this.dataType = dataType;
    }

    @Override
    public int getMaxFiscalMonth() {
        return this.maxFiscalMonth;
    }

    public void setMaxFiscalMonth(int maxFiscalMonth) {
        this.maxFiscalMonth = maxFiscalMonth;
    }

    @Override
    public int getMinFiscalMonth() {
        return this.minFiscalMonth;
    }

    public void setMinFiscalMonth(int minFiscalMonth) {
        this.minFiscalMonth = minFiscalMonth;
    }

    @Override
    public int getMinYear() {
        return this.minYear;
    }

    public void setMinYear(int minYear) {
        this.minYear = minYear;
    }

    @Override
    public int getMaxYear() {
        return this.maxYear;
    }

    public void setMaxYear(int maxYear) {
        this.maxYear = maxYear;
    }

    public PeriodDefineImpl() {
    }

    public PeriodDefineImpl(String key, String code, String title, PeriodType type, String createUser, Date createTime, Date updateTime, String updateUser, PeriodPropertyGroup periodPropertyGroup) {
        this.key = key;
        this.code = code;
        this.title = title;
        this.type = type;
        this.createUser = createUser;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.updateUser = updateUser;
        this.periodPropertyGroup = periodPropertyGroup;
    }

    @Override
    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public PeriodType getType() {
        return this.type;
    }

    public void setType(PeriodType type) {
        this.type = type;
    }

    public void setPeriodTypeDB(Integer type) {
        this.type = PeriodType.fromType((int)type);
    }

    @JsonIgnore
    public int getPeriodTypeDB() {
        return this.type == null ? PeriodType.YEAR.type() : this.type.type();
    }

    @Override
    public String getCreateUser() {
        return this.createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    @Override
    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public Date getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String getUpdateUser() {
        return this.updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    @Override
    public PeriodPropertyGroup getPeriodPropertyGroup() {
        return this.periodPropertyGroup;
    }

    public void setPeriodPropertyGroup(PeriodPropertyGroup periodPropertyGroup) {
        this.periodPropertyGroup = periodPropertyGroup;
    }

    @JsonIgnore
    public int getPeriodPropertyGroupDB() {
        return this.periodPropertyGroup == null ? 0 : this.periodPropertyGroup.getType();
    }

    public void setPeriodPropertyGroupDB(Integer type) {
        this.periodPropertyGroup = PeriodPropertyGroup.forType(type);
    }

    @Override
    @JsonIgnore
    public PeriodType getPeriodType() {
        return this.type;
    }

    @Override
    @JsonIgnore
    public String getDimensionName() {
        return NrPeriodConst.DATETIME;
    }

    @Override
    @JsonIgnore
    public String getOrder() {
        return null;
    }

    @Override
    @JsonIgnore
    public String getVersion() {
        return null;
    }

    @Override
    @JsonIgnore
    public String getOwnerLevelAndId() {
        return null;
    }

    public String getID() {
        return this.key;
    }
}

