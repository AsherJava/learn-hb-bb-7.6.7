/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DesignDataField
 *  com.jiuqi.nr.datascheme.api.exception.SchemeDataException
 *  com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService
 */
package com.jiuqi.nr.query.datascheme.service;

import com.jiuqi.nr.datascheme.api.DesignDataField;
import com.jiuqi.nr.datascheme.api.exception.SchemeDataException;
import com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService;
import com.jiuqi.nr.query.datascheme.service.dto.QueryDataTableDTO;
import java.util.List;
import java.util.Map;

public interface IDesignQueryDataSchemeService
extends IDesignDataSchemeService {
    public QueryDataTableDTO getQueryDataTable(String var1);

    public String insertQueryDataTable(QueryDataTableDTO var1, Map<String, String> var2);

    public void deleteQueryDataTable(String var1);

    public void deleteQueryDataGroup(String var1);

    public void deleteQueryDataScheme(String var1);

    public void updateQueryDataField(DesignDataField var1) throws SchemeDataException;

    public <E extends DesignDataField> void updateQueryDataFields(List<E> var1) throws SchemeDataException;
}

