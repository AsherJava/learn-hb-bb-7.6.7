/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFormat
 *  com.fasterxml.jackson.annotation.JsonFormat$Shape
 *  com.fasterxml.jackson.annotation.JsonIgnoreProperties
 *  com.jiuqi.nr.multcheck2.bean.MCSchemeParam
 *  com.jiuqi.nr.multcheck2.bean.MultcheckItem
 *  com.jiuqi.nr.multcheck2.bean.MultcheckScheme
 *  com.jiuqi.nr.multcheck2.bean.MultcheckSchemeOrg
 *  com.jiuqi.nr.multcheck2.common.OrgType
 *  com.jiuqi.nr.multcheck2.common.SchemeType
 */
package com.jiuqi.nr.param.transfer.definition.dto.formscheme;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jiuqi.nr.multcheck2.bean.MCSchemeParam;
import com.jiuqi.nr.multcheck2.bean.MultcheckItem;
import com.jiuqi.nr.multcheck2.bean.MultcheckScheme;
import com.jiuqi.nr.multcheck2.bean.MultcheckSchemeOrg;
import com.jiuqi.nr.multcheck2.common.OrgType;
import com.jiuqi.nr.multcheck2.common.SchemeType;
import com.jiuqi.nr.param.transfer.definition.dto.formscheme.MultCheckItemDTO;
import com.jiuqi.nr.param.transfer.definition.dto.formscheme.MultCheckSchemeOrgDTO;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown=true)
public class MultCheckParamDTO {
    private String key;
    private String code;
    private String title;
    private String task;
    private String formScheme;
    private OrgType orgType;
    private String orgFml;
    private SchemeType type;
    private String order;
    private String desc;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH-mm-ss.sss zzz")
    private Date updateTime;
    private String level = "";
    private String org;
    private String reportDim;
    List<MultCheckSchemeOrgDTO> mcsOrgList;
    List<MultCheckItemDTO> mcItemList;

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
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

    public String getTask() {
        return this.task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getFormScheme() {
        return this.formScheme;
    }

    public void setFormScheme(String formScheme) {
        this.formScheme = formScheme;
    }

    public OrgType getOrgType() {
        return this.orgType;
    }

    public void setOrgType(OrgType orgType) {
        this.orgType = orgType;
    }

    public String getOrgFml() {
        return this.orgFml;
    }

    public void setOrgFml(String orgFml) {
        this.orgFml = orgFml;
    }

    public SchemeType getType() {
        return this.type;
    }

    public void setType(SchemeType type) {
        this.type = type;
    }

    public String getOrder() {
        return this.order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Date getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getLevel() {
        return this.level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getOrg() {
        return this.org;
    }

    public void setOrg(String org) {
        this.org = org;
    }

    public String getReportDim() {
        return this.reportDim;
    }

    public void setReportDim(String reportDim) {
        this.reportDim = reportDim;
    }

    public List<MultCheckSchemeOrgDTO> getMcsOrgList() {
        return this.mcsOrgList;
    }

    public void setMcsOrgList(List<MultCheckSchemeOrgDTO> mcsOrgList) {
        this.mcsOrgList = mcsOrgList;
    }

    public List<MultCheckItemDTO> getMcItemList() {
        return this.mcItemList;
    }

    public void setMcItemList(List<MultCheckItemDTO> mcItemList) {
        this.mcItemList = mcItemList;
    }

    public static MultCheckParamDTO valueOf(MCSchemeParam mcSchemeParam) {
        List mcItemList;
        if (mcSchemeParam == null) {
            return null;
        }
        MultCheckParamDTO multcheckParamDTO = new MultCheckParamDTO();
        MultcheckScheme mcScheme = mcSchemeParam.getMcScheme();
        if (mcScheme == null) {
            return null;
        }
        multcheckParamDTO.setKey(mcScheme.getKey());
        multcheckParamDTO.setCode(mcScheme.getCode());
        multcheckParamDTO.setTitle(mcScheme.getTitle());
        multcheckParamDTO.setTask(mcScheme.getTask());
        multcheckParamDTO.setFormScheme(mcScheme.getFormScheme());
        multcheckParamDTO.setOrgType(mcScheme.getOrgType());
        multcheckParamDTO.setOrgFml(mcScheme.getOrgFml());
        multcheckParamDTO.setType(mcScheme.getType());
        multcheckParamDTO.setOrder(mcScheme.getOrder());
        multcheckParamDTO.setDesc(mcScheme.getDesc());
        multcheckParamDTO.setUpdateTime(mcScheme.getUpdateTime());
        multcheckParamDTO.setDesc(mcScheme.getDesc());
        multcheckParamDTO.setLevel(mcScheme.getLevel());
        multcheckParamDTO.setOrg(mcScheme.getOrg());
        multcheckParamDTO.setReportDim(mcScheme.getReportDim());
        List mcsOrgList = mcSchemeParam.getMcsOrgList();
        if (mcsOrgList != null && mcsOrgList.size() > 0) {
            ArrayList<MultCheckSchemeOrgDTO> mcsOrgListDTO = new ArrayList<MultCheckSchemeOrgDTO>();
            for (MultcheckSchemeOrg multcheckSchemeOrg : mcsOrgList) {
                if (multcheckSchemeOrg == null) continue;
                mcsOrgListDTO.add(MultCheckSchemeOrgDTO.valueOf(multcheckSchemeOrg));
            }
            multcheckParamDTO.setMcsOrgList(mcsOrgListDTO);
        }
        if ((mcItemList = mcSchemeParam.getMcItemList()) != null && mcItemList.size() > 0) {
            ArrayList<MultCheckItemDTO> mcItemListDTO = new ArrayList<MultCheckItemDTO>();
            for (MultcheckItem multcheckItem : mcItemList) {
                if (multcheckItem == null) continue;
                mcItemListDTO.add(MultCheckItemDTO.valueOf(multcheckItem));
            }
            multcheckParamDTO.setMcItemList(mcItemListDTO);
        }
        return multcheckParamDTO;
    }

    public void valueDefine(MCSchemeParam schemeDefine) {
        List<MultCheckItemDTO> mcItemListDTOs;
        MultcheckScheme mcScheme = new MultcheckScheme();
        mcScheme.setKey(this.getKey());
        mcScheme.setCode(this.getCode());
        mcScheme.setTitle(this.getTitle());
        mcScheme.setTask(this.getTask());
        mcScheme.setFormScheme(this.getFormScheme());
        mcScheme.setOrgType(this.getOrgType());
        mcScheme.setOrgFml(this.getOrgFml());
        mcScheme.setType(this.getType());
        mcScheme.setOrder(this.getOrder());
        mcScheme.setDesc(this.getDesc());
        mcScheme.setUpdateTime(this.getUpdateTime());
        mcScheme.setDesc(this.getDesc());
        mcScheme.setLevel(this.getLevel());
        mcScheme.setOrg(this.getOrg());
        mcScheme.setReportDim(this.getReportDim());
        schemeDefine.setMcScheme(mcScheme);
        List<MultCheckSchemeOrgDTO> mcsOrgListDTOs = this.getMcsOrgList();
        if (mcsOrgListDTOs != null && mcsOrgListDTOs.size() > 0) {
            ArrayList<MultcheckSchemeOrg> mcsOrgList = new ArrayList<MultcheckSchemeOrg>();
            for (MultCheckSchemeOrgDTO mcsOrgListDTO : mcsOrgListDTOs) {
                MultcheckSchemeOrg multcheckSchemeOrg = new MultcheckSchemeOrg();
                mcsOrgListDTO.valueDefine(multcheckSchemeOrg);
                mcsOrgList.add(multcheckSchemeOrg);
            }
            schemeDefine.setMcsOrgList(mcsOrgList);
        }
        if ((mcItemListDTOs = this.getMcItemList()) != null && mcItemListDTOs.size() > 0) {
            ArrayList<MultcheckItem> multCheckItems = new ArrayList<MultcheckItem>();
            for (MultCheckItemDTO multcheckItemDTO : mcItemListDTOs) {
                MultcheckItem multcheckItem = new MultcheckItem();
                multcheckItemDTO.valueDefine(multcheckItem);
                multCheckItems.add(multcheckItem);
            }
            schemeDefine.setMcItemList(multCheckItems);
        }
    }
}

