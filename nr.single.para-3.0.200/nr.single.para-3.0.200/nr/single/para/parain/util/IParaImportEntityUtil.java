/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.facade.DesignTableDefine
 *  com.jiuqi.np.definition.facade.TableDefine
 *  com.jiuqi.nr.datascheme.api.DesignDataTable
 *  com.jiuqi.nr.definition.common.IEntityUpgrader
 */
package nr.single.para.parain.util;

import com.jiuqi.np.definition.facade.DesignTableDefine;
import com.jiuqi.np.definition.facade.TableDefine;
import com.jiuqi.nr.datascheme.api.DesignDataTable;
import com.jiuqi.nr.definition.common.IEntityUpgrader;
import nr.single.para.common.TableKindEx;

public interface IParaImportEntityUtil
extends IEntityUpgrader {
    public TableKindEx getTableKind(DesignTableDefine var1);

    public TableKindEx getTableKind(TableDefine var1);

    public void setTableKind(DesignTableDefine var1, TableKindEx var2);

    public TableKindEx getTableKind(DesignDataTable var1);

    public void setTableKind(DesignDataTable var1, TableKindEx var2);
}

