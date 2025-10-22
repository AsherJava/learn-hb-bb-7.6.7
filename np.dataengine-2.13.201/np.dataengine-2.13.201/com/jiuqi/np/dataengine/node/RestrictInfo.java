/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.data.DimensionNode
 *  com.jiuqi.bi.syntax.dynamic.DynamicNodeException
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.period.PeriodModifier
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 */
package com.jiuqi.np.dataengine.node;

import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.data.DimensionNode;
import com.jiuqi.bi.syntax.dynamic.DynamicNodeException;
import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.definitions.DefinitionsCache;
import com.jiuqi.np.dataengine.definitions.TableModelRunInfo;
import com.jiuqi.np.dataengine.intf.IDataModelLinkFinder;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.period.PeriodModifier;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RestrictInfo {
    private List<DimensionNode> dimesionNodes = null;
    public PeriodModifier periodModifier = null;
    public boolean isLJ = false;
    public int statKind = -1;
    public IASTNode conditionNode = null;
    public String conditionFormula = null;
    public String relateItem = null;
    public boolean showDictTitle = false;

    public List<DimensionNode> getDimesionNodes() {
        if (this.dimesionNodes == null) {
            this.dimesionNodes = new ArrayList<DimensionNode>();
        }
        return this.dimesionNodes;
    }

    public boolean hasDimensionNodes() {
        return this.dimesionNodes != null && this.dimesionNodes.size() > 0;
    }

    public DimensionValueSet getDimensionRestriction(QueryContext qContext, ColumnModelDefine columnModel) throws DynamicNodeException, ParseException {
        Map<String, String> dimValuesByLinkAlias = null;
        if (this.relateItem != null) {
            IDataModelLinkFinder dataLinkFinder = qContext.getExeContext().getDataModelLinkFinder();
            dimValuesByLinkAlias = dataLinkFinder.getDimValuesByLinkAlias(qContext.getExeContext(), this.relateItem);
        }
        if (dimValuesByLinkAlias == null && !this.hasDimensionNodes()) {
            return null;
        }
        DimensionValueSet dimensionValueSet = new DimensionValueSet();
        DefinitionsCache cache = qContext.getExeContext().getCache();
        TableModelDefine tableModel = cache.getDataModelDefinitionsCache().findTable(columnModel.getTableID());
        TableModelRunInfo tableRunInfo = cache.getDataModelDefinitionsCache().getTableInfo(tableModel);
        if (this.dimesionNodes != null) {
            for (DimensionNode dimensionNode : this.dimesionNodes) {
                String fieldDimensionName;
                String dimensionName = dimensionNode.getDimesionName().toUpperCase();
                ColumnModelDefine dimField = tableRunInfo.getDimensionField(dimensionName);
                if (dimField == null && (dimField = tableRunInfo.parseSearchField(dimensionName)) != null && StringUtils.isNotEmpty((String)(fieldDimensionName = tableRunInfo.getDimensionName(dimField.getCode())))) {
                    dimensionName = fieldDimensionName;
                }
                if (dimField == null && dimensionName.equals("CALIBER")) {
                    dimensionName = cache.getDataModelDefinitionsCache().getDimensionProvider().getCaliberDimensionName(qContext.getExeContext());
                    dimField = tableRunInfo.getDimensionField(dimensionName);
                }
                if (dimField == null) {
                    throw new DynamicNodeException("\u7ef4\u5ea6\u6807\u8bc6[" + dimensionNode.getDimesionName() + "]\u5728[" + tableModel.getCode() + "]\u8868\u4e2d\u6ca1\u6709\u627e\u5230\u5b57\u6bb5\u5b57\u4e49");
                }
                dimensionValueSet.setValue(dimensionName, dimensionNode.getDimesionValue());
            }
        }
        if (dimValuesByLinkAlias != null) {
            for (Map.Entry entry : dimValuesByLinkAlias.entrySet()) {
                dimensionValueSet.setValue((String)entry.getKey(), entry.getValue());
            }
        }
        return dimensionValueSet;
    }

    public DimensionValueSet getDimensionRestriction() throws DynamicNodeException, ParseException {
        if (!this.hasDimensionNodes()) {
            return null;
        }
        DimensionValueSet dimensionValueSet = new DimensionValueSet();
        for (DimensionNode dimesionNode : this.dimesionNodes) {
            String dimensionName = dimesionNode.getDimesionName().toUpperCase();
            dimensionValueSet.setValue(dimensionName, dimesionNode.getDimesionValue());
        }
        return dimensionValueSet;
    }
}

