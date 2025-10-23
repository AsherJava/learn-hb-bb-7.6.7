/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.definition.common.IndexModelType
 *  com.jiuqi.nvwa.definition.common.TableModelType
 *  com.jiuqi.nvwa.definition.common.exception.DataModelException
 *  com.jiuqi.nvwa.definition.common.exception.ModelValidateException
 *  com.jiuqi.nvwa.definition.facade.design.DesignColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.design.DesignIndexModelDefine
 *  com.jiuqi.nvwa.definition.facade.design.DesignTableModel
 *  com.jiuqi.nvwa.definition.facade.design.DesignTableModelDefine
 */
package com.jiuqi.nr.datascheme.api.service;

import com.jiuqi.nvwa.definition.common.IndexModelType;
import com.jiuqi.nvwa.definition.common.TableModelType;
import com.jiuqi.nvwa.definition.common.exception.DataModelException;
import com.jiuqi.nvwa.definition.common.exception.ModelValidateException;
import com.jiuqi.nvwa.definition.facade.design.DesignColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.design.DesignIndexModelDefine;
import com.jiuqi.nvwa.definition.facade.design.DesignTableModel;
import com.jiuqi.nvwa.definition.facade.design.DesignTableModelDefine;
import java.util.List;

public interface INrDesignDataModelService {
    public DesignTableModelDefine createTableModelDefine();

    public int insertTableModelDefine(DesignTableModelDefine var1) throws ModelValidateException, DataModelException;

    public int[] insertTableModelDefines(DesignTableModelDefine[] var1) throws ModelValidateException;

    public int deleteTableModelDefine(String var1);

    public int[] deleteTableModelDefines(String[] var1);

    public int updateTableModelDefine(DesignTableModelDefine var1) throws ModelValidateException;

    public int[] updateTableModelDefines(DesignTableModelDefine[] var1);

    public DesignTableModelDefine getTableModelDefine(String var1);

    public List<DesignTableModelDefine> getTableModelDefines(String[] var1);

    public DesignTableModelDefine getTableModelDefineByCode(String var1);

    public List<DesignTableModelDefine> getTableModelDefines();

    public List<DesignTableModelDefine> getTableModelDefinesByCatalogId(String var1);

    public List<DesignTableModelDefine> getTableModelDefinesByType(TableModelType var1);

    public DesignColumnModelDefine createColumnModelDefine();

    public int insertColumnModelDefine(DesignColumnModelDefine var1) throws ModelValidateException;

    public int[] insertColumnModelDefines(DesignColumnModelDefine[] var1) throws ModelValidateException;

    public int deleteColumnModelDefine(String var1);

    public int[] deleteColumnModelDefines(String[] var1);

    public int deleteColumnModelDefineByTable(String var1);

    public int updateColumnModelDefine(DesignColumnModelDefine var1);

    public int[] updateColumnModelDefines(DesignColumnModelDefine[] var1);

    public DesignColumnModelDefine getColumnModelDefine(String var1);

    public List<DesignColumnModelDefine> getColumnModelDefines(String[] var1);

    public DesignColumnModelDefine getColumnModelDefineByCode(String var1, String var2);

    public List<DesignColumnModelDefine> getColumnModelDefinesByTable(String var1);

    public List<DesignIndexModelDefine> getIndexsByTable(String var1);

    public String addIndexToTable(String var1, String[] var2, String var3, IndexModelType var4);

    public int deleteIndexModelDefine(String var1);

    public int deleteIndexsByTable(String var1);

    public int[] deleteIndexModelDefines(String[] var1);

    public int updateIndexModelDefine(DesignIndexModelDefine var1);

    public int[] updateIndexModelDefines(DesignIndexModelDefine[] var1);

    public DesignTableModel createTableModel();

    public int deleteTableModel(String var1);

    public void saveTableModel(DesignTableModel var1) throws ModelValidateException, DataModelException;

    public DesignTableModel getTableModel(String var1);
}

