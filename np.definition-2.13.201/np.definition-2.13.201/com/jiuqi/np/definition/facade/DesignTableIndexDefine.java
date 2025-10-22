/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.definition.facade;

import com.jiuqi.np.definition.common.TableIndexType;
import com.jiuqi.np.definition.facade.TableIndexDefine;

public interface DesignTableIndexDefine
extends TableIndexDefine {
    public void setKey(String var1);

    public void setName(String var1);

    public void setColumnsFieldKeys(String[] var1);

    public void setIndexType(TableIndexType var1);

    public void setDBName(String var1);

    public void setTableKey(String var1);
}

