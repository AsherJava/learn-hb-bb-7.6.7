/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.dc.base.common.vo.SelectOptionVO
 */
package com.jiuqi.dc.mappingscheme.client.vo;

import com.jiuqi.dc.base.common.vo.SelectOptionVO;

public class SchemeDimVo
extends SelectOptionVO {
    private String referField;

    public SchemeDimVo() {
    }

    public SchemeDimVo(String code, String name, String referField) {
        super(code, name);
        this.referField = referField;
    }

    public String getReferField() {
        return this.referField;
    }

    public void setReferField(String referField) {
        this.referField = referField;
    }
}

