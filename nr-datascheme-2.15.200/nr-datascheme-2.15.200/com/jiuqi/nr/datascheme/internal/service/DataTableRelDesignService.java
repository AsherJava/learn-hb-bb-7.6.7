/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DesignDataTableRel
 */
package com.jiuqi.nr.datascheme.internal.service;

import com.jiuqi.nr.datascheme.api.DesignDataTableRel;
import com.jiuqi.nr.datascheme.internal.dto.DataTableRelDesignDTO;
import java.util.List;

public interface DataTableRelDesignService {
    public String insertDataTableRel(DataTableRelDesignDTO var1);

    public List<String> insertDataTableRels(List<DataTableRelDesignDTO> var1);

    public void updateDataTableRel(DataTableRelDesignDTO var1);

    public void updateDataTableRels(List<DataTableRelDesignDTO> var1);

    public void deleteDataTableRel(String var1);

    public void deleteDataTableRels(List<String> var1);

    public void deleteBySrcTable(String var1);

    public void deleteByDesTable(String var1);

    public DesignDataTableRel getBySrcTable(String var1);

    public List<DesignDataTableRel> getByDesTable(String var1);
}

