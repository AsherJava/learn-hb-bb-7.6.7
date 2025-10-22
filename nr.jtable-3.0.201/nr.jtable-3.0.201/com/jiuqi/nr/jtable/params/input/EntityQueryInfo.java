/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.swagger.annotations.ApiModel
 *  io.swagger.annotations.ApiModelProperty
 */
package com.jiuqi.nr.jtable.params.input;

import com.jiuqi.nr.jtable.params.base.JtableContext;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.LinkedHashSet;
import java.util.Set;

@ApiModel(value="EntityQueryInfo", description="\u4e3b\u4f53\u6570\u636e\u67e5\u8be2\u6761\u4ef6\u53c2\u6570")
public abstract class EntityQueryInfo {
    @ApiModelProperty(value="jtable\u73af\u5883\u4fe1\u606f", name="context", required=true)
    private JtableContext context;
    @ApiModelProperty(value="\u4e3b\u4f53\u89c6\u56fekey", name="entityViewKey")
    private String entityViewKey;
    @ApiModelProperty(value="\u94fe\u63a5key", name="dataLinkKey")
    private String dataLinkKey;
    @ApiModelProperty(value="\u663e\u793a\u5b57\u6bb5\u5217\u8868", name="captionFields")
    private Set<String> captionFields;
    @ApiModelProperty(value="\u4e0b\u62c9\u5b57\u6bb5\u5217\u8868", name="dropDownFields")
    private Set<String> dropDownFields;
    @ApiModelProperty(value="\u662f\u5426\u5224\u65ad\u679a\u4e3e\u6761\u76ee\u53ea\u8bfb\u6743\u9650", name="readAuth")
    private boolean readAuth = true;
    @ApiModelProperty(value="\u4f7f\u7528\u679a\u4e3e\u67e5\u8be2\u6269\u5c55\uff08\u5e9f\u5f03\uff09", name="useExtra")
    private boolean useExtra = false;
    @ApiModelProperty(value="\u5ffd\u7565\u8bbe\u7f6e\u9ed8\u8ba4\u5355\u4f4d\u9694\u79bb\u7ef4\u5ea6", name="ignoreIsolate")
    private boolean ignoreIsolate = false;
    @ApiModelProperty(value="\u5b9e\u4f53\u5b57\u6bb5\u8131\u654f", name="desensitized")
    private boolean desensitized = false;

    public JtableContext getContext() {
        return this.context;
    }

    public void setContext(JtableContext context) {
        this.context = context;
    }

    public String getEntityViewKey() {
        return this.entityViewKey;
    }

    public void setEntityViewKey(String entityViewKey) {
        this.entityViewKey = entityViewKey;
    }

    public String getDataLinkKey() {
        return this.dataLinkKey;
    }

    public void setDataLinkKey(String dataLinkKey) {
        this.dataLinkKey = dataLinkKey;
    }

    public Set<String> getCaptionFields() {
        if (this.captionFields == null) {
            this.captionFields = new LinkedHashSet<String>();
        }
        return this.captionFields;
    }

    public void setCaptionFields(Set<String> captionFields) {
        this.captionFields = captionFields;
    }

    public Set<String> getDropDownFields() {
        if (this.dropDownFields == null) {
            this.dropDownFields = new LinkedHashSet<String>();
        }
        return this.dropDownFields;
    }

    public void setDropDownFields(Set<String> dropDownFields) {
        this.dropDownFields = dropDownFields;
    }

    public boolean isReadAuth() {
        return this.readAuth;
    }

    public void setReadAuth(boolean readAuth) {
        this.readAuth = readAuth;
    }

    public boolean isUseExtra() {
        return this.useExtra;
    }

    public void setUseExtra(boolean useExtra) {
        this.useExtra = useExtra;
    }

    public boolean isIgnoreIsolate() {
        return this.ignoreIsolate;
    }

    public void setIgnoreIsolate(boolean ignoreIsolate) {
        this.ignoreIsolate = ignoreIsolate;
    }

    public boolean isDesensitized() {
        return this.desensitized;
    }

    public void setDesensitized(boolean desensitized) {
        this.desensitized = desensitized;
    }
}

