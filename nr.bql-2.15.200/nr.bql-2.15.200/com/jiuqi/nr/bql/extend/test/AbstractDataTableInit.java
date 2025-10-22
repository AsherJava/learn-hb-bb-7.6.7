/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DataScheme
 *  com.jiuqi.nr.datascheme.api.DesignDataTable
 *  com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService
 *  com.jiuqi.nr.query.datascheme.extend.DataTableModel
 */
package com.jiuqi.nr.bql.extend.test;

import com.jiuqi.nr.datascheme.api.DataScheme;
import com.jiuqi.nr.datascheme.api.DesignDataTable;
import com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService;
import com.jiuqi.nr.query.datascheme.extend.DataTableModel;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public abstract class AbstractDataTableInit {
    protected static final List<String> srcTables = new ArrayList<String>();
    private static final String DATA_SCHEME_CODE = "";
    @Autowired
    protected IDesignDataSchemeService dataSchemeService;

    public void init() throws Exception {
    }

    protected abstract DesignDataTable initTable(String var1) throws Exception;

    protected abstract String getSrcType();

    protected DataScheme getDataScheme() {
        return this.dataSchemeService.getDataSchemeByCode(DATA_SCHEME_CODE);
    }

    protected void saveDataTable(DataTableModel dataTableModel) {
        DesignDataTable exsitTable = this.dataSchemeService.getDataTableByCode(dataTableModel.getDataTable().getCode());
        if (exsitTable != null) {
            this.dataSchemeService.deleteDataTable(exsitTable.getKey());
        }
        this.dataSchemeService.insertDataTable(dataTableModel.getDataTable());
        this.dataSchemeService.insertDataFields(dataTableModel.getFields());
    }
}

