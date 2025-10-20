/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bde.bizmodel.impl.adaptor.update.dto;

import com.jiuqi.bde.bizmodel.impl.adaptor.update.dto.BdeNvwaAppUpdateDTO;
import java.util.List;

public class BdeNvwaCertifyUpdateDTO {
    private String id;
    private String code;
    private String title;
    private String type;
    private int curentService;
    private String url;
    private String frontendURL;
    private String clientid;
    private String clientsecret;
    private String ordinal;
    private boolean useToken;
    private String extraInfo;
    private List<BdeNvwaAppUpdateDTO> apps;

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

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getCurentService() {
        return this.curentService;
    }

    public void setCurentService(int curentService) {
        this.curentService = curentService;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFrontendURL() {
        return this.frontendURL;
    }

    public void setFrontendURL(String frontendURL) {
        this.frontendURL = frontendURL;
    }

    public String getClientid() {
        return this.clientid;
    }

    public void setClientid(String clientid) {
        this.clientid = clientid;
    }

    public String getClientsecret() {
        return this.clientsecret;
    }

    public void setClientsecret(String clientsecret) {
        this.clientsecret = clientsecret;
    }

    public String getOrdinal() {
        return this.ordinal;
    }

    public void setOrdinal(String ordinal) {
        this.ordinal = ordinal;
    }

    public boolean isUseToken() {
        return this.useToken;
    }

    public void setUseToken(boolean useToken) {
        this.useToken = useToken;
    }

    public String getExtraInfo() {
        return this.extraInfo;
    }

    public void setExtraInfo(String extraInfo) {
        this.extraInfo = extraInfo;
    }

    public List<BdeNvwaAppUpdateDTO> getApps() {
        return this.apps;
    }

    public void setApps(List<BdeNvwaAppUpdateDTO> apps) {
        this.apps = apps;
    }
}

