/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datascheme.api.service;

import com.jiuqi.nr.datascheme.api.DesignDataTable;
import com.jiuqi.nr.datascheme.api.exception.SchemeDataException;
import java.util.Collection;

public interface DataTableValidator {
    public void checkTable(DesignDataTable var1) throws SchemeDataException;

    public void levelCheckTable(String var1) throws SchemeDataException;

    public void levelCheckTable(DesignDataTable var1) throws SchemeDataException;

    public <E extends DesignDataTable> void checkTable(Collection<E> var1) throws SchemeDataException;
}

