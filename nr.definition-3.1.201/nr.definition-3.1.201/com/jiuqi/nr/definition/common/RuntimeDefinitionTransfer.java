/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.definition.facade.TableDefine
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.DataTable
 */
package com.jiuqi.nr.definition.common;

import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.definition.facade.TableDefine;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.DataTable;

public class RuntimeDefinitionTransfer {
    public static FieldDefine toFieldDefine(DataField dataField) {
        return (FieldDefine)dataField;
    }

    public static TableDefine toTableDefine(DataTable dataTable) {
        return (TableDefine)dataTable;
    }
}

