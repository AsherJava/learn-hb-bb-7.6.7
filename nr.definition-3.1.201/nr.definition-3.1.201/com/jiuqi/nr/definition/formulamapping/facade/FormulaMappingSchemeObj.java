/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.formulamapping.facade;

import com.jiuqi.nr.definition.formulamapping.bean.FormulaMappingSchemeDefine;

public class FormulaMappingSchemeObj
extends FormulaMappingSchemeDefine {
    private String targetTitle;
    private String sourceTitle;

    public String getTargetTitle() {
        return this.targetTitle;
    }

    public void setTargetTitle(String targetTitle) {
        this.targetTitle = targetTitle;
    }

    public String getSourceTitle() {
        return this.sourceTitle;
    }

    public void setSourceTitle(String sourceTitle) {
        this.sourceTitle = sourceTitle;
    }

    public FormulaMappingSchemeObj converObj(FormulaMappingSchemeDefine formulaMappingSchemeDefine) {
        super.setKey(formulaMappingSchemeDefine.getKey());
        super.setOrder(formulaMappingSchemeDefine.getOrder());
        super.setSourceFSKey(formulaMappingSchemeDefine.getSourceFSKey());
        super.setTargetFSKey(formulaMappingSchemeDefine.getTargetFSKey());
        return this;
    }
}

