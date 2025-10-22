/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.JsonMappingException
 *  com.fasterxml.jackson.databind.ObjectMapper
 */
package com.jiuqi.nr.bpm.custom.bean;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class WorkFlowLine
implements Serializable {
    private static final long serialVersionUID = 3562688061904170800L;
    private String id;
    private String linkid;
    private String code;
    private String title;
    private String desc;
    private String actionid;
    private String formula;
    private String mdim;
    private String report;
    private boolean creatDataVersion;
    private boolean allmdim = true;
    private boolean allreport = true;
    private Date updatetime;
    private String beforeNodeID;
    private String afterNodeID;
    private String msgcontent;
    private boolean sendby_phone;
    private boolean sendby_mail;
    private boolean sendby_protal = true;
    private Map<String, String> msguser;
    private String conditionExecute;

    public String getMsgcontent() {
        return this.msgcontent;
    }

    public void setMsgcontent(String msgcontent) {
        this.msgcontent = msgcontent;
    }

    public boolean isSendby_phone() {
        return this.sendby_phone;
    }

    public void setSendby_phone(boolean sendby_phone) {
        this.sendby_phone = sendby_phone;
    }

    public boolean isSendby_mail() {
        return this.sendby_mail;
    }

    public void setSendby_mail(boolean sendby_mail) {
        this.sendby_mail = sendby_mail;
    }

    public boolean isSendby_protal() {
        return this.sendby_protal;
    }

    public void setSendby_protal(boolean sendby_protal) {
        this.sendby_protal = sendby_protal;
    }

    public Map<String, Object> getMsguser() {
        if (this.msguser == null) {
            return new HashMap<String, Object>();
        }
        ObjectMapper mapper = new ObjectMapper();
        HashMap<String, Object> msgUserMap = new HashMap<String, Object>();
        if (!this.msguser.isEmpty()) {
            for (Map.Entry<String, String> entry : this.msguser.entrySet()) {
                try {
                    Object readValue = mapper.readValue(entry.getValue(), Object.class);
                    msgUserMap.put(entry.getKey(), readValue);
                }
                catch (JsonMappingException e) {
                    e.printStackTrace();
                }
                catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            }
        }
        return msgUserMap;
    }

    public void setMsguser(Map<String, Object> msguser) {
        ObjectMapper mapper = new ObjectMapper();
        HashMap<String, String> msgUserMap = new HashMap<String, String>();
        if (!msguser.isEmpty()) {
            for (Map.Entry<String, Object> entry : msguser.entrySet()) {
                try {
                    String writeValueAsString = mapper.writeValueAsString(entry.getValue());
                    msgUserMap.put(entry.getKey(), writeValueAsString);
                }
                catch (JsonProcessingException e) {
                    throw new ClassCastException("\u5e8f\u5217\u5316\u5931\u8d25");
                }
            }
        }
        this.msguser = msgUserMap;
    }

    public Date getUpdatetime() {
        return this.updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getActionid() {
        return this.actionid;
    }

    public void setActionid(String actionid) {
        this.actionid = actionid;
    }

    public String getFormula() {
        return this.formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }

    public String getMdim() {
        return this.mdim;
    }

    public void setMdim(String mdim) {
        this.mdim = mdim;
    }

    public String getReport() {
        return this.report;
    }

    public void setReport(String report) {
        this.report = report;
    }

    public boolean isCreatDataVersion() {
        return this.creatDataVersion;
    }

    public void setCreatDataVersion(boolean creatDataVersion) {
        this.creatDataVersion = creatDataVersion;
    }

    public boolean isAllmdim() {
        return this.allmdim;
    }

    public void setAllmdim(boolean allmdim) {
        this.allmdim = allmdim;
    }

    public boolean isAllreport() {
        return this.allreport;
    }

    public void setAllreport(boolean allreport) {
        this.allreport = allreport;
    }

    public String getLinkid() {
        return this.linkid;
    }

    public void setLinkid(String linkid) {
        this.linkid = linkid;
    }

    public String getBeforeNodeID() {
        return this.beforeNodeID;
    }

    public void setBeforeNodeID(String beforeNodeID) {
        this.beforeNodeID = beforeNodeID;
    }

    public String getAfterNodeID() {
        return this.afterNodeID;
    }

    public void setAfterNodeID(String afterNodeID) {
        this.afterNodeID = afterNodeID;
    }

    public String getConditionExecute() {
        return this.conditionExecute;
    }

    public void setConditionExecute(String conditionExecute) {
        this.conditionExecute = conditionExecute;
    }
}

