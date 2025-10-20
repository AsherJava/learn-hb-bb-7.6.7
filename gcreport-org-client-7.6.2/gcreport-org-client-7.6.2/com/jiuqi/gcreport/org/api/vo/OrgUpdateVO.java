/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnoreProperties
 */
package com.jiuqi.gcreport.org.api.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jiuqi.gcreport.org.api.vo.OrgVersionVO;
import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown=true)
public class OrgUpdateVO {
    private String id;
    private Map<String, Object> datas;
    private List<OrgVersionVO> versionList;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Map<String, Object> getDatas() {
        return this.datas;
    }

    public void setDatas(Map<String, Object> datas) {
        this.datas = datas;
    }

    public List<OrgVersionVO> getVersionList() {
        return this.versionList;
    }

    public void setVersionList(List<OrgVersionVO> versionList) {
        this.versionList = versionList;
    }
}

