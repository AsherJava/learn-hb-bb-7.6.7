/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.np.definition.facade.FieldDefine
 */
package com.jiuqi.np.dataengine.intf;

import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.common.IndexItem;
import com.jiuqi.np.dataengine.exception.ExecuteException;
import com.jiuqi.np.dataengine.exception.ExpressionException;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.ICommonQuery;
import com.jiuqi.np.dataengine.intf.IDataRowReader;
import com.jiuqi.np.dataengine.intf.IDataTable;
import com.jiuqi.np.dataengine.intf.IDataUpdator;
import com.jiuqi.np.dataengine.intf.IMonitor;
import com.jiuqi.np.dataengine.intf.IReadonlyTable;
import com.jiuqi.np.definition.facade.FieldDefine;
import java.util.List;

public interface IDataQuery
extends ICommonQuery {
    @Override
    public IReadonlyTable executeReader(ExecutorContext var1) throws ParseException, ExpressionException, ExecuteException, Exception;

    public void queryToDataRowReader(ExecutorContext var1, IDataRowReader var2) throws UnsupportedOperationException, Exception;

    public IDataTable executeQuery(ExecutorContext var1) throws ParseException, ExpressionException, ExecuteException, Exception;

    public IDataUpdator openForUpdate(ExecutorContext var1) throws Exception;

    public IDataUpdator openForUpdate(ExecutorContext var1, boolean var2) throws Exception;

    public void setMonitor(IMonitor var1);

    public void setStatic(boolean var1);

    public IndexItem queryRowIndex(DimensionValueSet var1, ExecutorContext var2) throws Exception;

    public void setFilledEnumLinks(List<FieldDefine> var1, List<List<String>> var2);

    public IMonitor getMonitor();
}

