/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.persistence.Table
 */
package com.jiuqi.va.bill.bd.core.domain;

import com.jiuqi.va.bill.bd.core.domain.ApplyRegMapConfigItemDTO;
import java.util.Date;
import javax.persistence.Table;

@Table(name="APPLYREG_MP")
public class ApplyRegMapDO
extends ApplyRegMapConfigItemDTO {
    private static final long serialVersionUID = 1L;
    private String billdefine;
    private String billdefinecode;
    private Integer createopportunity;
    private Integer createtype;
    private String groupcode;
    private Integer startflag;
    private Integer deleteflag;
    private String adaptcondition;
    private String mapinfos;
    private Date createtime;
    private String srcbilldefine;
    private String srcbilldefinecode;
    private String writebackname;
    private String deleteflagname;
    private String relationtemplate;

    public String getWritebackname() {
        return this.writebackname;
    }

    public void setWritebackname(String writebackname) {
        this.writebackname = writebackname;
    }

    public String getGroupcode() {
        return this.groupcode;
    }

    public void setGroupcode(String groupcode) {
        this.groupcode = groupcode;
    }

    public Integer getStartflag() {
        return this.startflag;
    }

    public void setStartflag(Integer startflag) {
        this.startflag = startflag;
    }

    public String getAdaptcondition() {
        return this.adaptcondition;
    }

    public void setAdaptcondition(String adaptcondition) {
        this.adaptcondition = adaptcondition;
    }

    public Integer getCreatetype() {
        return this.createtype;
    }

    public void setCreatetype(Integer createtype) {
        this.createtype = createtype;
    }

    public Date getCreatetime() {
        return this.createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Integer getCreateopportunity() {
        return this.createopportunity;
    }

    public void setCreateopportunity(Integer createopportunity) {
        this.createopportunity = createopportunity;
    }

    public String getBilldefine() {
        return this.billdefine;
    }

    public void setBilldefine(String billdefine) {
        this.billdefine = billdefine;
    }

    public String getSrcbilldefine() {
        return this.srcbilldefine;
    }

    public void setSrcbilldefine(String srcbilldefine) {
        this.srcbilldefine = srcbilldefine;
    }

    public String getSrcbilldefinecode() {
        return this.srcbilldefinecode;
    }

    public void setSrcbilldefinecode(String srcbilldefinecode) {
        this.srcbilldefinecode = srcbilldefinecode;
    }

    public String getMapinfos() {
        return this.mapinfos;
    }

    public void setMapinfos(String mapinfos) {
        this.mapinfos = mapinfos;
    }

    public String getBilldefinecode() {
        return this.billdefinecode;
    }

    public void setBilldefinecode(String billdefinecode) {
        this.billdefinecode = billdefinecode;
    }

    public String getRelationtemplate() {
        return this.relationtemplate;
    }

    public void setRelationtemplate(String relationtemplate) {
        this.relationtemplate = relationtemplate;
    }

    public Integer getDeleteflag() {
        return this.deleteflag;
    }

    public void setDeleteflag(Integer deleteflag) {
        this.deleteflag = deleteflag;
    }

    public String getDeleteflagname() {
        return this.deleteflagname;
    }

    public void setDeleteflagname(String deleteflagname) {
        this.deleteflagname = deleteflagname;
    }
}

