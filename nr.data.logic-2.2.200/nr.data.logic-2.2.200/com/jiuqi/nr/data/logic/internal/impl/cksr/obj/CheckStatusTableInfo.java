/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 */
package com.jiuqi.nr.data.logic.internal.impl.cksr.obj;

import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;

public class CheckStatusTableInfo {
    private final TableModelDefine cksTable;
    private final TableModelDefine cksSubTable;
    private final FormSchemeDefine formScheme;

    public CheckStatusTableInfo(TableModelDefine cksTable, TableModelDefine cksSubTable, FormSchemeDefine formScheme) {
        this.cksTable = cksTable;
        this.cksSubTable = cksSubTable;
        this.formScheme = formScheme;
    }

    public TableModelDefine getCksTable() {
        return this.cksTable;
    }

    public TableModelDefine getCksSubTable() {
        return this.cksSubTable;
    }

    public FormSchemeDefine getFormScheme() {
        return this.formScheme;
    }
}

