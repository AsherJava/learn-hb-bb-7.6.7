/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.query.dataset.dao;

import com.jiuqi.nr.query.dataset.defines.DataSetGroupDefine;
import java.util.List;

public interface IDataSetGroupDao {
    public Boolean InsertDataSetGroup(DataSetGroupDefine var1);

    public Boolean DeleteDataSetGroupById(String var1);

    public Boolean UpdateDataSetGroup(DataSetGroupDefine var1);

    public DataSetGroupDefine QueryDataSetGroupById(String var1);

    public List<DataSetGroupDefine> QueryDataSetGroupByParentIdAndCreator(String var1);

    public List<DataSetGroupDefine> QueryDataSetGroupByParentId(String var1);

    public List<DataSetGroupDefine> QueryDataSetGroupByParentIdAndTitleAndCreator(String var1, String var2);

    public List<DataSetGroupDefine> QueryDataSetGroupByParentIdAndTitle(String var1, String var2);

    public List<DataSetGroupDefine> QueryDataSetGroupByCreator();

    public List<DataSetGroupDefine> QueryDataSetGroup();
}

