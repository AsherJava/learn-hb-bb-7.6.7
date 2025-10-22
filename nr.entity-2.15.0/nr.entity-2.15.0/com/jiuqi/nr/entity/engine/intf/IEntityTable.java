/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.entity.engine.intf;

import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IReadonlyTable;
import com.jiuqi.nr.entity.engine.var.PageCondition;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface IEntityTable
extends IReadonlyTable {
    public List<IEntityRow> getAllRows();

    public List<IEntityRow> getRootRows();

    public List<IEntityRow> getChildRows(String var1);

    public List<IEntityRow> getAllChildRows(String var1);

    public IEntityRow findByEntityKey(String var1);

    public Map<String, IEntityRow> findByEntityKeys(Set<String> var1);

    public int getMaxDepth();

    public int getMaxDepthByEntityKey(String var1);

    public IEntityRow findByCode(String var1);

    public List<IEntityRow> findAllByCode(String var1);

    public Map<String, IEntityRow> findByCodes(List<String> var1);

    public Map<String, List<IEntityRow>> findAllByCodes(List<String> var1);

    public int getDirectChildCount(String var1);

    public int getAllChildCount(String var1);

    public Map<String, Integer> getDirectChildCountByParent(String var1);

    public Map<String, Integer> getAllChildCountByParent(String var1);

    public String[] getParentsEntityKeyDataPath(String var1);

    public int getTotalCount();

    public List<IEntityRow> getPageRows(PageCondition var1);

    public IEntityRow quickFindByEntityKey(String var1);

    public Map<String, IEntityRow> quickFindByEntityKeys(Set<String> var1);
}

