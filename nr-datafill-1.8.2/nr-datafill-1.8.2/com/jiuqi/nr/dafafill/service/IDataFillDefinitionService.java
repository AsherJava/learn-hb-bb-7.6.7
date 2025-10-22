/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.nr.common.itree.ITree
 */
package com.jiuqi.nr.dafafill.service;

import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.nr.common.itree.ITree;
import com.jiuqi.nr.dafafill.entity.DataFillDefinition;
import com.jiuqi.nr.dafafill.web.vo.TaskTreeNodeVO;
import java.util.List;
import java.util.Map;

public interface IDataFillDefinitionService {
    public void add(DataFillDefinition var1) throws JQException;

    public void modify(DataFillDefinition var1) throws JQException;

    public void delete(String var1) throws JQException;

    public void batchDelete(List<String> var1) throws JQException;

    public void deleteByParentId(String var1) throws JQException;

    public DataFillDefinition findById(String var1);

    public DataFillDefinition findByCode(String var1);

    public List<DataFillDefinition> findByParentId(String var1);

    public List<DataFillDefinition> fuzzySearch(String var1);

    public List<DataFillDefinition> findAllDefinitions();

    public Map<String, List<DataFillDefinition>> findAllByParent();

    public void batchModifyParentId(List<String> var1, String var2);

    public ITree<TaskTreeNodeVO> buildTaskTree(String var1);
}

