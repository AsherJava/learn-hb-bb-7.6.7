/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.define.IBdePluginType
 *  com.jiuqi.bde.common.intf.PenetrateColumn
 *  com.jiuqi.dc.mappingscheme.impl.define.gather.IPluginTypeGather
 */
package com.jiuqi.bde.penetrate.impl.core;

import com.jiuqi.bde.bizmodel.define.IBdePluginType;
import com.jiuqi.bde.common.intf.PenetrateColumn;
import com.jiuqi.bde.penetrate.impl.common.ColumnAlignmentEnum;
import com.jiuqi.bde.penetrate.impl.common.ColumnTypeEnum;
import com.jiuqi.bde.penetrate.impl.model.IPenetrateColumnBuilder;
import com.jiuqi.dc.mappingscheme.impl.define.gather.IPluginTypeGather;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractPenetrateColumnBuilder<C>
implements IPenetrateColumnBuilder<C> {
    @Autowired
    private IPluginTypeGather pluginTypeGather;
    private static final String TEMPLATE_PARENT_NAME = "%s_P";

    protected boolean supportPenetrateShowMultiOrg(String pluginTypeStr) {
        IBdePluginType pluginType = (IBdePluginType)this.pluginTypeGather.getPluginType(pluginTypeStr);
        if (pluginType == null) {
            return false;
        }
        return pluginType.supportPenetrateShowMultiOrg();
    }

    protected PenetrateColumn createParentCloumn(String name, String title) {
        PenetrateColumn column = new PenetrateColumn(this.formatParentName(name), title, ColumnTypeEnum.STRING.name());
        column.setAlignment(ColumnAlignmentEnum.CENTER.getCode()).setFixed(Boolean.valueOf(false)).setInternal(Boolean.valueOf(false)).setVisiable(Boolean.valueOf(true));
        return column;
    }

    protected PenetrateColumn createStringCloumn(String name, String title) {
        PenetrateColumn column = new PenetrateColumn(name, title, ColumnTypeEnum.STRING.name());
        column.setAlignment(ColumnAlignmentEnum.LEFT.getCode()).setWidth(Integer.valueOf(150)).setFixed(Boolean.valueOf(false)).setInternal(Boolean.valueOf(false)).setVisiable(Boolean.valueOf(true)).setShowTooltip(Boolean.valueOf(true));
        return column;
    }

    protected PenetrateColumn createIntCloumn(String name, String title) {
        PenetrateColumn column = new PenetrateColumn(name, title, ColumnTypeEnum.INT.name());
        column.setAlignment(ColumnAlignmentEnum.RIGHT.getCode()).setWidth(Integer.valueOf(60)).setFixed(Boolean.valueOf(false)).setInternal(Boolean.valueOf(false)).setVisiable(Boolean.valueOf(true));
        return column;
    }

    protected PenetrateColumn createNumericCloumn(String name, String title) {
        PenetrateColumn column = new PenetrateColumn(name, title, ColumnTypeEnum.NUMERIC.name());
        column.setAlignment(ColumnAlignmentEnum.RIGHT.getCode()).setDigits(Integer.valueOf(2)).setWidth(Integer.valueOf(145)).setFixed(Boolean.valueOf(false)).setInternal(Boolean.valueOf(false)).setVisiable(Boolean.valueOf(true));
        return column;
    }

    protected PenetrateColumn createDateCloumn(String name, String title) {
        PenetrateColumn column = new PenetrateColumn(name, title, ColumnTypeEnum.DATE.name());
        column.setAlignment(ColumnAlignmentEnum.LEFT.getCode()).setWidth(Integer.valueOf(100)).setFixed(Boolean.valueOf(false)).setInternal(Boolean.valueOf(false)).setVisiable(Boolean.valueOf(true));
        return column;
    }

    private String formatParentName(String name) {
        return String.format(TEMPLATE_PARENT_NAME, name);
    }
}

