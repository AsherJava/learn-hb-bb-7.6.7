/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.facade.TableDefine
 */
package com.jiuqi.np.dataengine.intf;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.intf.IEntityRow;
import com.jiuqi.np.dataengine.intf.IReadonlyTable;
import com.jiuqi.np.definition.facade.TableDefine;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface IEntityTable
extends IReadonlyTable {
    public TableDefine getEntityTableDefine();

    public List<IEntityRow> getAllRows();

    public List<IEntityRow> getRootRows();

    public List<IEntityRow> getChildRows(String var1);

    public List<IEntityRow> getAllChildRows(String var1);

    public IEntityRow findByEntityKey(String var1);

    public Map<String, IEntityRow> findByEntityKeys(Set<String> var1);

    public IEntityRow findByRecKey(String var1);

    public IEntityRow findByBizKey(String var1);

    public int getMaxDepth();

    public int getMaxDepthByEntityKey(String var1);

    public IEntityRow findByCode(String var1);

    @Override
    public IEntityRow findRow(DimensionValueSet var1);

    public int getDirectChildCount(String var1);

    public int getAllChildCount(String var1);

    public Map<String, Integer> getDirectChildCountByParent(String var1);

    public Map<String, Integer> getAllChildCountByParent(String var1);

    public List<IEntityRow> getSelfAndChildRows(String var1);

    public List<IEntityRow> getSelfAndAllChildRows(String var1);

    public String[] getParentsEntityKeyDataPath(String var1);

    public String getParentsTitlePath(String var1, String var2);

    public boolean isCodeBizKey();

    public boolean isI18NField(int var1);
}

