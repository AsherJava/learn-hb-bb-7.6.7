/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.DataSetException
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.np.definition.common.FieldGatherType
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.sql.SummarizingMethod
 */
package com.jiuqi.np.dataengine.intf;

import com.jiuqi.bi.dataset.DataSetException;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.np.dataengine.exception.DataTypeException;
import com.jiuqi.np.dataengine.exception.ExpressionException;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.ICommonQuery;
import com.jiuqi.np.dataengine.intf.IGroupingTable;
import com.jiuqi.np.dataengine.intf.impl.ReloadTreeInfo;
import com.jiuqi.np.dataengine.setting.DataRegTotalInfo;
import com.jiuqi.np.definition.common.FieldGatherType;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.sql.SummarizingMethod;
import java.sql.SQLException;
import java.util.List;

public interface IGroupingQuery
extends ICommonQuery {
    public void setSummarizingMethod(SummarizingMethod var1);

    public void addGroupColumn(int var1);

    public int addGroupColumn(FieldDefine var1);

    public void setWantDetail(boolean var1);

    public void setSortGroupingAndDetailRows(boolean var1);

    public void setHasRootGatherRow(boolean var1);

    public FieldGatherType getGatherType(int var1);

    public void setGatherType(int var1, FieldGatherType var2);

    public void setDataRegTotalInfo(DataRegTotalInfo var1);

    public void setPeriodLevelGather(int var1, List<Integer> var2);

    public void setEntityLevelGather(String var1, int var2, EntityViewDefine var3, List<Integer> var4, ReloadTreeInfo var5);

    public void setCalcExpressions(List<IExpression> var1);

    @Override
    public IGroupingTable executeReader(ExecutorContext var1) throws ParseException, ExpressionException, DataSetException, SQLException, SyntaxException, DataTypeException, Exception;

    public void setHidenOneDetailRow(boolean var1);

    public void setFilledEnumLinks(List<FieldDefine> var1, List<List<String>> var2);

    default public void setGroupRowCalc(boolean needCalc) {
    }

    default public void setOldQueryModule(boolean oldQueryModule) {
    }
}

