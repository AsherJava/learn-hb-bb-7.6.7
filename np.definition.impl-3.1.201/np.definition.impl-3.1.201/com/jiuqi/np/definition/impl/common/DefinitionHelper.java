/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.exception.NotSupportedException
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.definition.facade.TableDefine
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 */
package com.jiuqi.np.definition.impl.common;

import com.jiuqi.np.definition.exception.NotSupportedException;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.definition.facade.TableDefine;
import com.jiuqi.np.definition.impl.internal.FieldDefineImpl;
import com.jiuqi.np.definition.impl.internal.TableDefineImpl;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DefinitionHelper {
    private static IDesignDataSchemeService iDesignDataSchemeService;
    private static IRuntimeDataSchemeService iRuntimeDataSchemeService;

    @Autowired
    public static void setiDesignDataSchemeService(IDesignDataSchemeService iDesignDataSchemeService) {
        DefinitionHelper.iDesignDataSchemeService = iDesignDataSchemeService;
    }

    @Autowired
    public static void setiRuntimeDataSchemeService(IRuntimeDataSchemeService iRuntimeDataSchemeService) {
        DefinitionHelper.iRuntimeDataSchemeService = iRuntimeDataSchemeService;
    }

    public static IDesignDataSchemeService getiDesignDataSchemeService() {
        return iDesignDataSchemeService;
    }

    public static IRuntimeDataSchemeService getiRuntimeDataSchemeService() {
        return iRuntimeDataSchemeService;
    }

    public static DataTable toDataTable(TableDefine tableDefine) {
        if (tableDefine instanceof DataTable) {
            return (DataTable)tableDefine;
        }
        if (tableDefine instanceof TableDefineImpl) {
            return ((TableDefineImpl)tableDefine).getDataTable();
        }
        throw new NotSupportedException();
    }

    public static DataField toDataField(FieldDefine fieldDefine) {
        if (fieldDefine instanceof DataField) {
            return (DataField)fieldDefine;
        }
        if (fieldDefine instanceof FieldDefineImpl) {
            return ((FieldDefineImpl)fieldDefine).getDataField();
        }
        throw new NotSupportedException();
    }
}

