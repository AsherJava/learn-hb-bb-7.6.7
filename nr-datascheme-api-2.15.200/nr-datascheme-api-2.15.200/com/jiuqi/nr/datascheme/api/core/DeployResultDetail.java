/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnore
 */
package com.jiuqi.nr.datascheme.api.core;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Collections;
import java.util.List;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

public class DeployResultDetail {
    private String key;
    private String code;
    private String title;
    private List<String> errorMsg;
    private List<String> warnMsg;

    public DeployResultDetail() {
    }

    public DeployResultDetail(String key, String code, String title, List<String> errorMsg) {
        this(key, code, title, errorMsg, Collections.emptyList());
    }

    public DeployResultDetail(String key, String code, String title, List<String> errorMsg, List<String> warnMsg) {
        this.key = key;
        this.code = code;
        this.title = title;
        this.errorMsg = errorMsg;
        this.warnMsg = warnMsg;
    }

    public boolean isSuccess() {
        return CollectionUtils.isEmpty(this.errorMsg) && CollectionUtils.isEmpty(this.warnMsg);
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

    public List<String> getErrorMsg() {
        return this.errorMsg;
    }

    public void setErrorMsg(List<String> errorMsg) {
        this.errorMsg = errorMsg;
    }

    public List<String> getWarnMsg() {
        return this.warnMsg;
    }

    public void setWarnMsg(List<String> warnMsg) {
        this.warnMsg = warnMsg;
    }

    @JsonIgnore
    public String getTableKey() {
        return this.key;
    }

    @JsonIgnore
    public String getTableCode() {
        return this.code;
    }

    @JsonIgnore
    public String getTableTitle() {
        return this.title;
    }

    @Deprecated
    public void setDataTableKey(String dataTableKey) {
        this.key = dataTableKey;
    }

    @Deprecated
    public void setDataTableCode(String dataTableCode) {
        this.code = dataTableCode;
    }

    @Deprecated
    public void setDataTableTitle(String dataTableTitle) {
        this.title = dataTableTitle;
    }

    public String checkDetail() {
        String str = "\u6570\u636e\u8868\u4e3b\u952e\uff1a" + this.key + "\uff0c\u6807\u8bc6\uff1a" + this.code + "\uff0c\u6807\u9898\uff1a" + this.title + "\uff0c";
        str = !CollectionUtils.isEmpty(this.errorMsg) ? str + "\u68c0\u67e5\u5f02\u5e38\uff1a" + StringUtils.collectionToCommaDelimitedString(this.errorMsg) : (!CollectionUtils.isEmpty(this.warnMsg) ? str + "\u68c0\u67e5\u8b66\u544a\uff1a" + StringUtils.collectionToCommaDelimitedString(this.warnMsg) : str + "\u68c0\u67e5\u6210\u529f\u3002");
        return str;
    }

    public String deployDetail() {
        String str = "\u6570\u636e\u8868\u4e3b\u952e\uff1a" + this.key + "\uff0c\u6807\u8bc6\uff1a" + this.code + "\uff0c\u6807\u9898\uff1a" + this.title + "\uff0c";
        str = !CollectionUtils.isEmpty(this.errorMsg) ? str + "\u53d1\u5e03\u5f02\u5e38\uff1a" + StringUtils.collectionToCommaDelimitedString(this.errorMsg) : (!CollectionUtils.isEmpty(this.warnMsg) ? str + "\u53d1\u5e03\u8b66\u544a\uff1a" + StringUtils.collectionToCommaDelimitedString(this.warnMsg) : str + "\u53d1\u5e03\u6210\u529f\u3002");
        return str;
    }
}

