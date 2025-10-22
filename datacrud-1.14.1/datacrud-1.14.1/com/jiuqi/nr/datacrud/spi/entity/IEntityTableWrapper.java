/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.engine.setting.IFieldsInfo
 *  com.jiuqi.nr.entity.engine.var.PageCondition
 *  com.jiuqi.nr.entity.model.IEntityModel
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 */
package com.jiuqi.nr.datacrud.spi.entity;

import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.engine.setting.IFieldsInfo;
import com.jiuqi.nr.entity.engine.var.PageCondition;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface IEntityTableWrapper {
    public void putIEntityTable(String var1, IEntityTable var2);

    public boolean resetIEntityTable(String var1);

    public IEntityTable getIEntityTable(String var1);

    public IFieldsInfo getFieldsInfo();

    public TableModelDefine getEntityTableDefine();

    public IEntityModel getEntityModel();

    public boolean isI18nAttribute(String var1);

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

