/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.MemoryDataSet
 */
package com.jiuqi.bi.dataset.manager;

import com.jiuqi.bi.dataset.BIDataSet;
import com.jiuqi.bi.dataset.BIDataSetException;
import com.jiuqi.bi.dataset.BIDataSetFieldInfo;
import com.jiuqi.bi.dataset.BIDataSetNotFoundException;
import com.jiuqi.bi.dataset.IDSContext;
import com.jiuqi.bi.dataset.MemoryDataSet;
import com.jiuqi.bi.dataset.manager.PageDataSetReader;
import com.jiuqi.bi.dataset.model.DSModel;
import com.jiuqi.bi.dataset.model.DataSetTypeNotFoundException;
import com.jiuqi.bi.dataset.model.field.DSField;
import com.jiuqi.bi.dataset.model.hierarchy.DSHierarchy;

public interface IDataSetManager {
    public DSModel findModel(String var1, String var2) throws BIDataSetNotFoundException, BIDataSetException;

    public DSField findField(String var1, String var2, String var3) throws BIDataSetNotFoundException, BIDataSetException;

    public DSHierarchy findHierarchy(String var1, String var2, String var3) throws BIDataSetNotFoundException, BIDataSetException;

    public BIDataSet open(IDSContext var1, String var2, String var3) throws BIDataSetNotFoundException, DataSetTypeNotFoundException, BIDataSetException;

    public BIDataSet open(IDSContext var1, DSModel var2) throws DataSetTypeNotFoundException, BIDataSetException;

    public MemoryDataSet<BIDataSetFieldInfo> distinct(IDSContext var1, DSModel var2, String var3) throws DataSetTypeNotFoundException, BIDataSetException;

    public PageDataSetReader openPageDataSet(IDSContext var1, String var2, String var3) throws BIDataSetNotFoundException, DataSetTypeNotFoundException, BIDataSetException;

    public PageDataSetReader openPageDataSet(IDSContext var1, String var2, String var3, boolean var4) throws BIDataSetNotFoundException, DataSetTypeNotFoundException, BIDataSetException;

    public PageDataSetReader openPageDataSet(IDSContext var1, DSModel var2) throws DataSetTypeNotFoundException, BIDataSetException;

    public PageDataSetReader openPageDataSet(IDSContext var1, DSModel var2, boolean var3) throws DataSetTypeNotFoundException, BIDataSetException;

    public PageDataSetReader openGroupPageDataSet(IDSContext var1, String var2, String var3, String var4) throws BIDataSetNotFoundException, DataSetTypeNotFoundException, BIDataSetException;

    public PageDataSetReader openGroupPageDataSet(IDSContext var1, String var2, DSModel var3) throws BIDataSetNotFoundException, DataSetTypeNotFoundException, BIDataSetException;

    public PageDataSetReader openGroupPageDataSet(IDSContext var1, String var2, DSModel var3, boolean var4) throws BIDataSetNotFoundException, DataSetTypeNotFoundException, BIDataSetException;
}

