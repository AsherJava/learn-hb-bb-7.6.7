/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.type.DataFieldGatherType
 *  com.jiuqi.nr.datascheme.api.type.DataFieldKind
 *  com.jiuqi.nr.datascheme.api.type.DataFieldType
 */
package com.jiuqi.nr.summary.executor.deploy.comparator;

import com.jiuqi.nr.datascheme.api.type.DataFieldGatherType;
import com.jiuqi.nr.datascheme.api.type.DataFieldKind;
import com.jiuqi.nr.datascheme.api.type.DataFieldType;

public class TemporaryCell {
    public String key;
    public String code;
    public String title;
    public String fieldKey;
    public DataFieldKind fieldKind;
    public DataFieldType fieldType;
    public Integer precision;
    public Integer decimal;
    public DataFieldGatherType gatherType;
    public String referEntityKey;
    public boolean nullable;
    public long modityTime;
}

