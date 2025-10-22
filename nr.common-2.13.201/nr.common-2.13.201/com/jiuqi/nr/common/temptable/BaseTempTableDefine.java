/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.temp.AbstractTempTableMeta
 *  com.jiuqi.bi.database.temp.ITempTableMeta
 *  com.jiuqi.nvwa.sf.models.IModuleTempTable
 */
package com.jiuqi.nr.common.temptable;

import com.jiuqi.bi.database.temp.AbstractTempTableMeta;
import com.jiuqi.bi.database.temp.ITempTableMeta;
import com.jiuqi.nr.common.temptable.IndexMeta;
import com.jiuqi.nvwa.sf.models.IModuleTempTable;
import java.util.List;

public abstract class BaseTempTableDefine
extends AbstractTempTableMeta
implements IModuleTempTable,
ITempTableMeta {
    public abstract boolean isDynamic();

    public abstract String getType();

    public abstract String getTypeTitle();

    public String getTempTableNameRule() {
        if (!this.isDynamic()) {
            return "T_" + this.getType() + "_%";
        }
        return this.getType() + "_%_%";
    }

    public List<IndexMeta> getIndexes() {
        return null;
    }

    public String toString() {
        return "ModuleName:" + this.getModuleName() + ",Desc:" + this.getDesc() + "Class:" + ((Object)((Object)this)).getClass();
    }
}

