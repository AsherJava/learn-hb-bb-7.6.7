/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 *  com.jiuqi.va.mapper.domain.TenantDO
 *  javax.persistence.Id
 *  javax.persistence.Table
 */
package com.jiuqi.coop.feign.config.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jiuqi.va.mapper.domain.TenantDO;
import java.math.BigDecimal;
import java.util.UUID;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name="FO_CLBR_RECORD")
@JsonInclude(value=JsonInclude.Include.NON_NULL)
public class ClbrRecordDO
extends TenantDO {
    private static final long serialVersionUID = 1L;
    @Id
    private UUID id;
    private BigDecimal ver;
    private String clbrbilltype;
    private String sendiator;
    private String sendrelationcode;
    private String sendclbrtypecode;
    private String senduser;
    private String sendbillcode;
    private String billrowid;
    private Double sendmoney;
    private String receiver;
    private String recerelationcode;
    private String receclbrtypecode;
    private String receiveuser;
    private String recebillcode;
    private Double recemoney;
    private Integer recerowno;
    private String clbrcode;
    private String clbrtbname;
    private String oppsrcid;

    public String getOppsrcid() {
        return this.oppsrcid;
    }

    public void setOppsrcid(String oppsrcid) {
        this.oppsrcid = oppsrcid;
    }

    public UUID getId() {
        return this.id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getClbrbilltype() {
        return this.clbrbilltype;
    }

    public void setClbrbilltype(String clbrbilltype) {
        this.clbrbilltype = clbrbilltype;
    }

    public String getSendiator() {
        return this.sendiator;
    }

    public void setSendiator(String sendiator) {
        this.sendiator = sendiator;
    }

    public String getSendrelationcode() {
        return this.sendrelationcode;
    }

    public void setSendrelationcode(String sendrelationcode) {
        this.sendrelationcode = sendrelationcode;
    }

    public String getSendclbrtypecode() {
        return this.sendclbrtypecode;
    }

    public void setSendclbrtypecode(String sendclbrtypecode) {
        this.sendclbrtypecode = sendclbrtypecode;
    }

    public String getSenduser() {
        return this.senduser;
    }

    public void setSenduser(String senduser) {
        this.senduser = senduser;
    }

    public String getSendbillcode() {
        return this.sendbillcode;
    }

    public void setSendbillcode(String sendbillcode) {
        this.sendbillcode = sendbillcode;
    }

    public Double getSendmoney() {
        return this.sendmoney;
    }

    public void setSendmoney(Double sendmoney) {
        this.sendmoney = sendmoney;
    }

    public String getReceiver() {
        return this.receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getRecerelationcode() {
        return this.recerelationcode;
    }

    public void setRecerelationcode(String recerelationcode) {
        this.recerelationcode = recerelationcode;
    }

    public String getRececlbrtypecode() {
        return this.receclbrtypecode;
    }

    public void setRececlbrtypecode(String receclbrtypecode) {
        this.receclbrtypecode = receclbrtypecode;
    }

    public String getReceiveuser() {
        return this.receiveuser;
    }

    public void setReceiveuser(String receiveuser) {
        this.receiveuser = receiveuser;
    }

    public String getRecebillcode() {
        return this.recebillcode;
    }

    public void setRecebillcode(String recebillcode) {
        this.recebillcode = recebillcode;
    }

    public Double getRecemoney() {
        return this.recemoney;
    }

    public void setRecemoney(Double recemoney) {
        this.recemoney = recemoney;
    }

    public Integer getRecerowno() {
        return this.recerowno;
    }

    public void setRecerowno(Integer recerowno) {
        this.recerowno = recerowno;
    }

    public String getClbrcode() {
        return this.clbrcode;
    }

    public void setClbrcode(String clbrcode) {
        this.clbrcode = clbrcode;
    }

    public String getClbrtbname() {
        return this.clbrtbname;
    }

    public void setClbrtbname(String clbrtbname) {
        this.clbrtbname = clbrtbname;
    }

    public BigDecimal getVer() {
        return this.ver;
    }

    public void setVer(BigDecimal ver) {
        this.ver = ver;
    }

    public String getBillrowid() {
        return this.billrowid;
    }

    public void setBillrowid(String billrowid) {
        this.billrowid = billrowid;
    }
}

