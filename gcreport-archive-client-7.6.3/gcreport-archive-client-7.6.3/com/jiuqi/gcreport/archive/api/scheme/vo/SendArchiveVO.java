/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonProperty
 */
package com.jiuqi.gcreport.archive.api.scheme.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jiuqi.gcreport.archive.api.scheme.vo.SendArchiveItemVO;
import java.util.List;

public class SendArchiveVO {
    @JsonProperty(value="RES_APP")
    private String RES_APP;
    @JsonProperty(value="DATA")
    private List<SendArchiveItemVO> DATA;

    public SendArchiveVO() {
    }

    public SendArchiveVO(String RES_APP, List<SendArchiveItemVO> DATA) {
        this.RES_APP = RES_APP;
        this.DATA = DATA;
    }

    @JsonProperty(value="RES_APP")
    public String getRES_APP() {
        return this.RES_APP;
    }

    public void setRES_APP(String RES_APP) {
        this.RES_APP = RES_APP;
    }

    @JsonProperty(value="DATA")
    public List<SendArchiveItemVO> getDATA() {
        return this.DATA;
    }

    public void setDATA(List<SendArchiveItemVO> DATA) {
        this.DATA = DATA;
    }
}

