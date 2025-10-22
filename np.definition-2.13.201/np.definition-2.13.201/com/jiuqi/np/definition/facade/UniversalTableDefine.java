/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.definition.facade;

import com.jiuqi.np.definition.common.TableKind;
import com.jiuqi.np.definition.facade.IBaseMetaItem;

public interface UniversalTableDefine
extends IBaseMetaItem {
    public String getCode();

    public String getDescription();

    public TableKind getKind();

    public String getBizKeyFieldsStr();

    public String[] getBizKeyFieldsID();
}

