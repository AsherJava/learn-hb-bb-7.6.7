/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bde.bizmodel.impl.adaptor.update.dto;

import java.io.Serializable;

public class BdeNvwaAppUpdateDTO
implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private String code;
    private String title;
    private String type;
    private String csId;
    private String url;
    private String clientid;
    private String clientsecret;
    private String frontendURL;
    private String extraInfo;
    private String ordinal;
    private int ticketValidTime;
    private int tokenValidTime;
    private String ipList;
    private boolean useToken;

    public String getId() {
        return this.id;
    }

    public String getCode() {
        return this.code;
    }

    public String getTitle() {
        return this.title;
    }

    public String getType() {
        return this.type;
    }

    public String getCsId() {
        return this.csId;
    }

    public String getUrl() {
        return this.url;
    }

    public String getClientid() {
        return this.clientid;
    }

    public String getClientsecret() {
        return this.clientsecret;
    }

    public String getFrontendURL() {
        return this.frontendURL;
    }

    public String getExtraInfo() {
        return this.extraInfo;
    }

    public String getOrdinal() {
        return this.ordinal;
    }

    public int getTicketValidTime() {
        return this.ticketValidTime;
    }

    public int getTokenValidTime() {
        return this.tokenValidTime;
    }

    public String getIpList() {
        return this.ipList;
    }

    public boolean isUseToken() {
        return this.useToken;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setCsId(String csId) {
        this.csId = csId;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setClientid(String clientid) {
        this.clientid = clientid;
    }

    public void setClientsecret(String clientsecret) {
        this.clientsecret = clientsecret;
    }

    public void setFrontendURL(String frontendURL) {
        this.frontendURL = frontendURL;
    }

    public void setExtraInfo(String extraInfo) {
        this.extraInfo = extraInfo;
    }

    public void setOrdinal(String ordinal) {
        this.ordinal = ordinal;
    }

    public void setTicketValidTime(int ticketValidTime) {
        this.ticketValidTime = ticketValidTime;
    }

    public void setTokenValidTime(int tokenValidTime) {
        this.tokenValidTime = tokenValidTime;
    }

    public void setIpList(String ipList) {
        this.ipList = ipList;
    }

    public void setUseToken(boolean useToken) {
        this.useToken = useToken;
    }
}

