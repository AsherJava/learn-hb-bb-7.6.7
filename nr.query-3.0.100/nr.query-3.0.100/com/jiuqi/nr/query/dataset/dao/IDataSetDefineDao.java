/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.query.dataset.dao;

import com.jiuqi.nr.query.dataset.defines.DataSetDefine;
import java.util.List;

public interface IDataSetDefineDao {
    public Boolean InsertDataSet(DataSetDefine var1);

    public Boolean DeleteDataSetById(String var1);

    public Boolean DeleteDataSetByName(String var1);

    public Boolean UpdateDataSet(DataSetDefine var1);

    public DataSetDefine QueryDataSetDefineById(String var1);

    public List<DataSetDefine> QueryDataSetDefinesByNameAndCreator(String var1);

    public List<DataSetDefine> QueryDataSetDefinesByName(String var1);

    public DataSetDefine QueryDataSetDefineByNameAndCreator(String var1);

    public DataSetDefine QueryDataSetDefineByName(String var1);

    public List<DataSetDefine> QueryDataSetDefineByTitleAndParentIdAndCreator(String var1, String var2);

    public List<DataSetDefine> QueryDataSetDefineByTitleAndParentId(String var1, String var2);

    public List<DataSetDefine> QueryDataSetDefineByCreator();

    public List<DataSetDefine> QueryDataSetDefine();

    public List<DataSetDefine> QueryDataSetDefineByParentIdAndCreator(String var1);

    public List<DataSetDefine> QueryDataSetDefineByParentId(String var1);

    public DataSetDefine QueryDataSetModelById(String var1);

    public Boolean UpdateDataSetModel(String var1, String var2);
}

