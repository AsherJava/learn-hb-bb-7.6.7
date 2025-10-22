/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.common.TableKind
 *  com.jiuqi.np.definition.facade.DesignTableDefine
 *  com.jiuqi.nvwa.definition.common.TableModelType
 *  com.jiuqi.nvwa.definition.interval.bean.TableModelDefineImpl
 */
package com.jiuqi.np.dataengine.definitions;

import com.jiuqi.np.dataengine.definitions.DataModelDefinitionsCache;
import com.jiuqi.np.definition.common.TableKind;
import com.jiuqi.np.definition.facade.DesignTableDefine;
import com.jiuqi.nvwa.definition.common.TableModelType;
import com.jiuqi.nvwa.definition.interval.bean.TableModelDefineImpl;

public class TableModelAdapter
extends TableModelDefineImpl {
    private static final long serialVersionUID = -6405850141963336337L;
    private DesignTableDefine tableDefine;

    public TableModelAdapter(DesignTableDefine tableDefine, DataModelDefinitionsCache dataModelDefinitionsCache) {
        this.tableDefine = tableDefine;
    }

    public String getID() {
        return this.tableDefine.getKey();
    }

    public String getCode() {
        return this.tableDefine.getCode();
    }

    public String getName() {
        return this.tableDefine.getCode();
    }

    public String getTitle() {
        return this.tableDefine.getTitle();
    }

    public TableModelType getType() {
        TableKind kind = this.tableDefine.getKind();
        if (kind == TableKind.TABLE_KIND_DICTIONARY) {
            return TableModelType.ENUM;
        }
        return TableModelType.DATA;
    }

    public String getBizKeys() {
        return this.tableDefine.getBizKeyFieldsStr();
    }
}

