/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.util.StringUtils
 */
package com.jiuqi.nr.definition.paramlanguage.bean;

import com.jiuqi.nr.definition.facade.PrintTemplateSchemeDefine;
import com.jiuqi.util.StringUtils;
import java.util.Date;

public class I18nRunTimePrintSchemeDefine
implements PrintTemplateSchemeDefine {
    private final PrintTemplateSchemeDefine schemeDefine;
    private String title;

    public I18nRunTimePrintSchemeDefine(PrintTemplateSchemeDefine schemeDefine) {
        this.schemeDefine = schemeDefine;
    }

    public String getTitle() {
        return StringUtils.isEmpty((String)this.title) ? this.schemeDefine.getTitle() : this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String getDescription() {
        return this.schemeDefine.getDescription();
    }

    @Override
    public String getTaskKey() {
        return this.schemeDefine.getTaskKey();
    }

    @Override
    public String getFormSchemeKey() {
        return this.schemeDefine.getFormSchemeKey();
    }

    @Override
    public byte[] getCommonAttribute() {
        return this.schemeDefine.getCommonAttribute();
    }

    @Override
    public byte[] getGatherCoverData() {
        return this.schemeDefine.getGatherCoverData();
    }

    public Date getUpdateTime() {
        return this.schemeDefine.getUpdateTime();
    }

    public String getKey() {
        return this.schemeDefine.getKey();
    }

    public String getOrder() {
        return this.schemeDefine.getOrder();
    }

    public String getVersion() {
        return this.schemeDefine.getVersion();
    }

    public String getOwnerLevelAndId() {
        return this.schemeDefine.getOwnerLevelAndId();
    }
}

