/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.util.StringUtils
 */
package com.jiuqi.nr.definition.paramlanguage.bean;

import com.jiuqi.nr.definition.facade.FormGroupDefine;
import com.jiuqi.util.StringUtils;
import java.util.Date;

public class I18nRunTimeFormGroupDefine
implements FormGroupDefine {
    private final FormGroupDefine formGroupDefine;
    private String title;

    public I18nRunTimeFormGroupDefine(FormGroupDefine formGroupDefine) {
        this.formGroupDefine = formGroupDefine;
    }

    @Override
    public String getFormSchemeKey() {
        return this.formGroupDefine.getFormSchemeKey();
    }

    @Override
    public String getCondition() {
        return this.formGroupDefine.getCondition();
    }

    @Override
    public String getMeasureUnit() {
        return this.formGroupDefine.getMeasureUnit();
    }

    public String getKey() {
        return this.formGroupDefine.getKey();
    }

    public String getOrder() {
        return this.formGroupDefine.getOrder();
    }

    public String getVersion() {
        return this.formGroupDefine.getVersion();
    }

    public String getOwnerLevelAndId() {
        return this.formGroupDefine.getOwnerLevelAndId();
    }

    public Date getUpdateTime() {
        return this.formGroupDefine.getUpdateTime();
    }

    public String getDescription() {
        return this.formGroupDefine.getDescription();
    }

    public String getParentKey() {
        return this.formGroupDefine.getParentKey();
    }

    public String getCode() {
        return this.formGroupDefine.getCode();
    }

    public String getTitle() {
        return StringUtils.isEmpty((String)this.title) ? this.formGroupDefine.getTitle() : this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

