/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.dataengine.intf;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.definitions.Formula;
import com.jiuqi.np.dataengine.node.NodeAdapter;
import java.util.List;
import java.util.UUID;

public interface IFormulaCheckEvent {
    public Formula getFormulaObj();

    public String getFormulaExpression();

    public String getFormulaMeaning();

    public UUID getFloatId();

    public AbstractData getLeftValue();

    public AbstractData getRightValue();

    public AbstractData getDifferenceValue();

    public String getCompliedFormulaExpression();

    public UUID getEntityId();

    public DimensionValueSet getRowkey();

    public List<NodeAdapter> getNodes();

    public int getWildcardCol();

    public int getWildcardRow();

    public String getParsedExpresionKey();

    default public Exception getCheckException() {
        return null;
    }
}

