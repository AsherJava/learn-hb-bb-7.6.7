/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.xml.bind.annotation.XmlAccessType
 *  javax.xml.bind.annotation.XmlAccessorType
 *  javax.xml.bind.annotation.XmlElement
 *  javax.xml.bind.annotation.XmlRootElement
 *  javax.xml.bind.annotation.XmlType
 */
package com.jiuqi.gcreport.webserviceclient.vo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={})
@XmlRootElement(name="REQUEST_GC_WS_DATA")
public class RequestGcWsDataParam {
    @XmlElement(name="SENDR", required=true)
    private String sendr;
    @XmlElement(name="ZIFNO", required=true)
    private String zifno;
    @XmlElement(name="MSGID", required=true)
    private String msgid;
    @XmlElement(name="INPUTJSON", required=true)
    private String inputJson;

    public String getSendr() {
        return this.sendr;
    }

    public void setSendr(String sendr) {
        this.sendr = sendr;
    }

    public String getZifno() {
        return this.zifno;
    }

    public void setZifno(String zifno) {
        this.zifno = zifno;
    }

    public String getMsgid() {
        return this.msgid;
    }

    public void setMsgid(String msgid) {
        this.msgid = msgid;
    }

    public String getInputJson() {
        return this.inputJson;
    }

    public void setInputJson(String inputJson) {
        this.inputJson = inputJson;
    }
}

