/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFormat
 *  com.fasterxml.jackson.annotation.JsonIgnore
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 *  com.fasterxml.jackson.annotation.JsonProperty
 *  com.jiuqi.va.domain.datamodel.DataModelDO
 *  javax.persistence.Table
 */
package com.jiuqi.va.datamodel.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.jiuqi.va.domain.datamodel.DataModelDO;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Table;
import org.springframework.format.annotation.DateTimeFormat;

@Table(name="DATAMODEL_DEFINE")
@JsonInclude(value=JsonInclude.Include.NON_NULL)
public class DataModelPublishedDO
extends DataModelDO {
    private static final long serialVersionUID = 1L;
    @JsonProperty(index=20)
    protected BigDecimal ver;
    @JsonProperty(index=21)
    @JsonFormat(timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    protected Date createtime;
    @JsonProperty(index=22)
    protected String modifyuser;
    @JsonProperty(index=23)
    @JsonFormat(timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    protected Date modifytime;
    @JsonIgnore
    protected String definedata;

    public BigDecimal getVer() {
        return this.ver;
    }

    public void setVer(BigDecimal ver) {
        this.ver = ver;
    }

    public Date getCreatetime() {
        return this.createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getModifyuser() {
        return this.modifyuser;
    }

    public void setModifyuser(String modifyUserId) {
        this.modifyuser = modifyUserId;
    }

    public Date getModifytime() {
        return this.modifytime;
    }

    public void setModifytime(Date modifytime) {
        this.modifytime = modifytime;
    }

    public String getDefinedata() {
        return this.definedata;
    }

    public void setDefinedata(String definedata) {
        this.definedata = definedata;
    }

    public void convert(DataModelDO dataModelDO) {
        this.setName(dataModelDO.getName());
        this.setTitle(dataModelDO.getTitle());
        this.setBiztype(dataModelDO.getBiztype());
        this.setSubBiztype(dataModelDO.getSubBiztype());
        this.setGroupcode(dataModelDO.getGroupcode());
        this.setRemark(dataModelDO.getRemark());
        this.setColumns(dataModelDO.getColumns());
        this.setIndexConsts(dataModelDO.getIndexConsts());
        this.setTenantName(dataModelDO.getTenantName());
        this.setExtInfo(dataModelDO.getExtInfo());
    }
}

