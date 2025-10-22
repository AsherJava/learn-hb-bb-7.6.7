/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.facade.IMetaItem
 */
package com.jiuqi.nr.definition.formulatracking.facade;

import com.jiuqi.np.definition.facade.IMetaItem;

public interface FormulaTrackDefine
extends IMetaItem {
    public String getId();

    public String getFormulaSchemeKey();

    public String getFormulaKey();

    public String getFormKey();

    public Integer getFormulaType();

    public String getExpressionKey();

    public String getFormulaFieldKey();

    public String getDataLinkFormKey();

    public String getDataLinkCode();

    public Integer getFormulaDataDirection();

    public Boolean getReadOnly();
}

