/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.bi.syntax.interpret.InterpretException
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.np.definition.common.FieldGatherType
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 */
package com.jiuqi.np.dataengine.query;

import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.bi.syntax.interpret.InterpretException;
import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.np.dataengine.QueryParam;
import com.jiuqi.np.dataengine.common.BitMaskAndNullValue;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.definitions.DefinitionsCache;
import com.jiuqi.np.dataengine.exception.DataTypeException;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.impl.DataRowImpl;
import com.jiuqi.np.dataengine.intf.impl.ReadonlyTableImpl;
import com.jiuqi.np.dataengine.intf.impl.ReloadTreeInfo;
import com.jiuqi.np.dataengine.queryparam.GroupTreeRow;
import com.jiuqi.np.dataengine.setting.DataRegTotalInfo;
import com.jiuqi.np.definition.common.FieldGatherType;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import java.util.HashMap;
import java.util.List;

@Deprecated
public class AdvancedGatherHelper {
    public AdvancedGatherHelper(ExecutorContext context, ReadonlyTableImpl tableImpl, DefinitionsCache cache, DimensionValueSet masterKeys, List<Integer> groupColumnIndexes, List<FieldGatherType> gatherTypes, QueryParam queryParam, HashMap<Integer, Boolean> setTypes) {
    }

    public void doPeriodLevelGather(Integer periodLevelIndex, List<Integer> periodLevels) throws DataTypeException {
    }

    public void doEntityLevelGather(String parentEntity, Integer entityLevelIndex, EntityViewDefine dwViewDefine, List<Integer> entityLevels, ReloadTreeInfo reloadTreeInfo) throws Exception {
    }

    public void fixTotalFieldValue(DataRegTotalInfo dataRegTotalInfo) throws Exception {
    }

    public static List<GroupTreeRow> getGroupTreeRows(List<DataRowImpl> dataRowImpls, HashMap<Integer, BitMaskAndNullValue> grpByColsInGroupingId) {
        return null;
    }

    public static void getDataRows(List<GroupTreeRow> groupTreeRows, List<DataRowImpl> dataRowImpls, int maxLevel) {
        if (groupTreeRows.size() <= 0) {
            return;
        }
        int grpLevel = (int)Math.pow(2.0, maxLevel) - 1;
        for (GroupTreeRow groupTreeRow : groupTreeRows) {
            if (groupTreeRow.getCurrentRow().getGroupingFlag() >= 0) {
                groupTreeRow.getCurrentRow().setGroupingLevel(grpLevel);
            }
            dataRowImpls.add(groupTreeRow.getCurrentRow());
            if (groupTreeRow.getChildRows().size() <= 0) continue;
            AdvancedGatherHelper.getDataRows(groupTreeRow.getChildRows(), dataRowImpls, maxLevel - 1);
        }
    }

    public void mergeGroupResult() throws DataTypeException {
    }

    public void reCalcExpFields() throws ParseException, InterpretException {
    }

    public void reCalcExpressions(List<IExpression> expressions) throws ParseException, InterpretException {
    }
}

