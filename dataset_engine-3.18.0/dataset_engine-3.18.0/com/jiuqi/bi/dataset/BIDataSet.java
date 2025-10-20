/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.Metadata
 *  com.jiuqi.nvwa.framework.parameter.IParameterEnv
 */
package com.jiuqi.bi.dataset;

import com.jiuqi.bi.dataset.AggrMeasureItem;
import com.jiuqi.bi.dataset.BIDataRow;
import com.jiuqi.bi.dataset.BIDataSetException;
import com.jiuqi.bi.dataset.BIDataSetFieldInfo;
import com.jiuqi.bi.dataset.FilterItem;
import com.jiuqi.bi.dataset.IDSFilter;
import com.jiuqi.bi.dataset.IDSTreeItem;
import com.jiuqi.bi.dataset.Metadata;
import com.jiuqi.bi.dataset.SortItem;
import com.jiuqi.bi.dataset.idx.DSIndex;
import com.jiuqi.bi.dataset.model.hierarchy.DSHierarchy;
import com.jiuqi.bi.parameter.engine.IParameterEnv;
import java.io.IOException;
import java.io.Writer;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public interface BIDataSet
extends Iterable<BIDataRow> {
    public static final String PROPERTY_HIERARCHY = "HIERARCHY";
    public static final String PROPERTY_TIMEKEYINDEX = "TIMEKEY_INDEX";
    public static final String PROPERTY_ROWNUMINDEX = "ROWNUM_INDEX";
    public static final String PROPERTY_FISCALMONTH = "FiscalMonth";
    public static final String PROPERTY_TIMEKEYGRANULARITY = "TIMEKEY_GRANULARITY";
    public static final String SYS_TIMEKEY = "SYS_TIMEKEY";
    public static final String SYS_ROWNUM = "SYS_ROWNUM";

    @Deprecated
    public IParameterEnv getParameterEnv();

    public com.jiuqi.nvwa.framework.parameter.IParameterEnv getEnhancedParameterEnv();

    public void compute(List<Integer> var1) throws BIDataSetException;

    public Metadata<BIDataSetFieldInfo> getMetadata();

    public int getRecordCount();

    public BIDataRow get(int var1);

    public DSIndex getDSIndex();

    @Override
    public Iterator<BIDataRow> iterator();

    public BIDataSet sort(Comparator<BIDataRow> var1);

    public BIDataSet sort(List<SortItem> var1);

    public BIDataSet filter(String var1) throws BIDataSetException;

    public BIDataSet filter(IDSFilter var1) throws BIDataSetException;

    public BIDataSet filter(List<FilterItem> var1) throws BIDataSetException;

    public BIDataSet distinct(List<String> var1) throws BIDataSetException;

    public int distinctCount(List<String> var1) throws BIDataSetException;

    public BIDataSet aggregate(List<String> var1, List<String> var2, boolean var3) throws BIDataSetException;

    public BIDataSet aggregate(List<String> var1, boolean var2) throws BIDataSetException;

    public BIDataSet aggregate(List<String> var1) throws BIDataSetException;

    public BIDataSet aggregate(List<String> var1, List<String> var2) throws BIDataSetException;

    public BIDataSet aggregateByTree(BIDataSet var1, List<String> var2) throws BIDataSetException;

    public BIDataSet aggregateByItems(List<String> var1, List<AggrMeasureItem> var2, boolean var3) throws BIDataSetException;

    public List<Integer> lookup(String var1, Object var2);

    public List<Integer> lookup(int var1, Object var2);

    public Double sum(String var1) throws BIDataSetException;

    public Double sum(int var1) throws BIDataSetException;

    public Double avg(String var1) throws BIDataSetException;

    public Double avg(int var1) throws BIDataSetException;

    public Object max(String var1) throws BIDataSetException;

    public Object max(int var1) throws BIDataSetException;

    public Object min(String var1) throws BIDataSetException;

    public Object min(int var1) throws BIDataSetException;

    public BIDataSet addCalcFields(Map<String, String> var1) throws BIDataSetException;

    public BIDataSet selectFields(List<String> var1) throws BIDataSetException;

    public IDSTreeItem createTree(DSHierarchy var1, String var2) throws BIDataSetException;

    public void dump(Writer var1) throws IOException;

    public void dump(String var1) throws IOException;

    public void doCalcField(int[] var1) throws BIDataSetException;

    public BIDataSet aggregate(String[] var1, String[] var2, boolean var3) throws BIDataSetException;

    public BIDataSet aggregate(String[] var1, boolean var2) throws BIDataSetException;

    public BIDataSet aggregate(String[] var1) throws BIDataSetException;

    public BIDataSet aggregate(String[] var1, String[] var2) throws BIDataSetException;
}

