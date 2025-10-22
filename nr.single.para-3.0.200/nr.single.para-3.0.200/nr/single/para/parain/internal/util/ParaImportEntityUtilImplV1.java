/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.common.TableKind
 *  com.jiuqi.np.definition.facade.DesignTableDefine
 *  com.jiuqi.np.definition.facade.TableDefine
 *  com.jiuqi.nr.datascheme.api.DesignDataTable
 *  com.jiuqi.nr.datascheme.api.type.DataTableType
 */
package nr.single.para.parain.internal.util;

import com.jiuqi.np.definition.common.TableKind;
import com.jiuqi.np.definition.facade.DesignTableDefine;
import com.jiuqi.np.definition.facade.TableDefine;
import com.jiuqi.nr.datascheme.api.DesignDataTable;
import com.jiuqi.nr.datascheme.api.type.DataTableType;
import nr.single.para.common.TableKindEx;
import nr.single.para.parain.util.IParaImportEntityUtil;

public class ParaImportEntityUtilImplV1
implements IParaImportEntityUtil {
    @Override
    public TableKindEx getTableKind(DesignTableDefine tableDefine) {
        return this.getTableKindBy(tableDefine.getKind());
    }

    @Override
    public TableKindEx getTableKind(TableDefine tableDefine) {
        return this.getTableKindBy(tableDefine.getKind());
    }

    private TableKindEx getTableKindBy(TableKind tableKind) {
        TableKindEx result = null;
        result = tableKind == TableKind.TABLE_KIND_ENTITY ? TableKindEx.TABLE_KIND_ENTITY : (tableKind == TableKind.TABLE_KIND_ENTITY_PERIOD ? TableKindEx.TABLE_KIND_ENTITY_PERIOD : (tableKind == TableKind.TABLE_KIND_ENTITY_VERSION ? TableKindEx.TABLE_KIND_ENTITY_VERSION : (tableKind == TableKind.TABLE_KIND_BIZDATA ? TableKindEx.TABLE_KIND_BIZDATA : TableKindEx.forValue(tableKind.getValue()))));
        return result;
    }

    @Override
    public void setTableKind(DesignTableDefine tableDefine, TableKindEx tableKind) {
        TableKind newTableKind = null;
        newTableKind = tableKind == TableKindEx.TABLE_KIND_ENTITY ? TableKind.TABLE_KIND_ENTITY : (tableKind == TableKindEx.TABLE_KIND_ENTITY_PERIOD ? TableKind.TABLE_KIND_ENTITY_PERIOD : (tableKind == TableKindEx.TABLE_KIND_ENTITY_VERSION ? TableKind.TABLE_KIND_ENTITY_VERSION : (tableKind == TableKindEx.TABLE_KIND_BIZDATA ? TableKind.TABLE_KIND_BIZDATA : TableKind.forValue((int)tableKind.getValue()))));
        tableDefine.setKind(newTableKind);
    }

    private TableKindEx getTableKindBy(DataTableType tableKind) {
        TableKindEx result = null;
        return result;
    }

    @Override
    public TableKindEx getTableKind(DesignDataTable tableDefine) {
        return this.getTableKindBy(tableDefine.getDataTableType());
    }

    @Override
    public void setTableKind(DesignDataTable tableDefine, TableKindEx tableKind) {
        Object newTableKind = null;
    }
}

