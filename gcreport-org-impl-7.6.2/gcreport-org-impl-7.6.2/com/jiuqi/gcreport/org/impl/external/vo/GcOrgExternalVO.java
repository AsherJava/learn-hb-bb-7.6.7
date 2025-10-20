/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.org.api.enums.GcOrgKindEnum
 *  io.swagger.annotations.ApiModel
 *  io.swagger.annotations.ApiModelProperty
 */
package com.jiuqi.gcreport.org.impl.external.vo;

import com.jiuqi.gcreport.org.api.enums.GcOrgKindEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.HashMap;
import java.util.Map;

@ApiModel(value="\u7ec4\u7ec7\u673a\u6784\u5916\u90e8\u63a5\u53e3\u5bf9\u8c61")
public class GcOrgExternalVO {
    @ApiModelProperty(value="\u7ec4\u7ec7\u673a\u6784\u4ee3\u7801", allowEmptyValue=false)
    private String code;
    @ApiModelProperty(value="\u7ec4\u7ec7\u673a\u6784\u540d\u79f0", allowEmptyValue=false)
    private String name;
    @ApiModelProperty(value="\u7ec4\u7ec7\u673a\u6784\u7b80\u79f0", allowEmptyValue=true)
    private String shortName;
    @ApiModelProperty(value="\u7ec4\u7ec7\u673a\u6784\u7236\u7ea7\u4ee3\u7801", allowEmptyValue=true)
    private String parentCode;
    @ApiModelProperty(value="\u7ec4\u7ec7\u673a\u6784\u5e01\u79cd", allowEmptyValue=true)
    private String currencyCode;
    @ApiModelProperty(value="\u5f53\u524d\u7ec4\u7ec7\u673a\u6784\u7684\u79cd\u7c7b", allowEmptyValue=true)
    private GcOrgKindEnum orgKind;
    private Map<String, Object> datas = new HashMap<String, Object>();

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return this.shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getParentCode() {
        return this.parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public String getCurrencyCode() {
        return this.currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public GcOrgKindEnum getOrgKind() {
        return this.orgKind;
    }

    public void setOrgKind(GcOrgKindEnum orgKind) {
        this.orgKind = orgKind;
    }

    public Map<String, Object> getDatas() {
        return this.datas;
    }

    public void setDatas(Map<String, Object> datas) {
        this.datas = datas;
    }

    public void setFieldValue(String key, Object value) {
        if (this.datas == null) {
            this.datas = new HashMap<String, Object>();
        }
        this.datas.put(key.trim().toUpperCase(), value);
    }
}

