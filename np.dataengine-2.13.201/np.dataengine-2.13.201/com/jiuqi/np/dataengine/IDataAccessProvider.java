/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.IDatabase
 *  com.jiuqi.bi.dataset.DataSet
 *  com.jiuqi.np.definition.facade.FieldDefine
 */
package com.jiuqi.np.dataengine;

import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.dataset.DataSet;
import com.jiuqi.np.dataengine.IDataQueryFactory;
import com.jiuqi.np.dataengine.QueryEnvironment;
import com.jiuqi.np.dataengine.QueryParam;
import com.jiuqi.np.dataengine.definitions.FormulaCallBack;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IDataAssist;
import com.jiuqi.np.dataengine.intf.IDataQuery;
import com.jiuqi.np.dataengine.intf.IDataSetExprEvaluator;
import com.jiuqi.np.dataengine.intf.IEncryptDecryptProcesser;
import com.jiuqi.np.dataengine.intf.IEntityQuery;
import com.jiuqi.np.dataengine.intf.IExpressionEvaluator;
import com.jiuqi.np.dataengine.intf.IFormulaRunner;
import com.jiuqi.np.dataengine.intf.IGroupingQuery;
import com.jiuqi.np.dataengine.intf.IMainDimFilter;
import com.jiuqi.np.definition.facade.FieldDefine;

public interface IDataAccessProvider {
    public IDataAssist newDataAssist(ExecutorContext var1);

    public IDataQuery newDataQuery();

    public IDataQuery newDataQuery(QueryEnvironment var1);

    public void registerDataQuery(IDataQueryFactory var1);

    public IGroupingQuery newGroupingQuery();

    public IGroupingQuery newGroupingQuery(QueryEnvironment var1);

    public IEntityQuery newEntityQuery();

    public IFormulaRunner newFormulaRunner(FormulaCallBack var1);

    public IExpressionEvaluator newExpressionEvaluator();

    public IDataSetExprEvaluator newDataSetExprEvaluator(DataSet<FieldDefine> var1);

    public void registerEncryptDecryptProcesser(IEncryptDecryptProcesser var1);

    public IEncryptDecryptProcesser getEncryptDecryptProcesser();

    public IMainDimFilter newMainDimFilter();

    public IDatabase getDatabase();

    public QueryParam getQueryParam();

    public QueryParam getFormulaParam();
}

