/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.itree.INode
 *  com.jiuqi.nr.datacheck.dataanalyze.OrgSelectType
 */
package com.jiuqi.nr.singlequery.multcheck.vo;

import com.jiuqi.nr.common.itree.INode;
import com.jiuqi.nr.datacheck.dataanalyze.OrgSelectType;

public class QueryCheckModel
implements INode {
    private String key;
    private String code;
    private String title;
    private String type;
    private String fml;
    private OrgSelectType orgSelectType = OrgSelectType.UCURRENTALLSUB;

    public String getFml() {
        return this.fml;
    }

    public void setFml(String fml) {
        this.fml = fml;
    }

    public OrgSelectType getOrgSelectType() {
        return this.orgSelectType;
    }

    public void setOrgSelectType(OrgSelectType orgSelectType) {
        this.orgSelectType = orgSelectType;
    }

    public QueryCheckModel() {
    }

    public QueryCheckModel(String key, String code, String title, String type, String fml) {
        this.key = key;
        this.code = code;
        this.title = title;
        this.type = type;
        this.fml = fml;
    }

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

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

