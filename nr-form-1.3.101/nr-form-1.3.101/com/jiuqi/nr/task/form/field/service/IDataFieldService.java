/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.core.DataSchemeNode
 *  com.jiuqi.nr.datascheme.api.core.ITree
 *  com.jiuqi.nr.datascheme.internal.tree.DataSchemeNodeDTO
 *  com.jiuqi.nr.datascheme.web.param.DataSchemeTreeQuery
 */
package com.jiuqi.nr.task.form.field.service;

import com.jiuqi.nr.datascheme.api.core.DataSchemeNode;
import com.jiuqi.nr.datascheme.api.core.ITree;
import com.jiuqi.nr.datascheme.internal.tree.DataSchemeNodeDTO;
import com.jiuqi.nr.datascheme.web.param.DataSchemeTreeQuery;
import com.jiuqi.nr.task.form.dto.DragNodeDTO;
import com.jiuqi.nr.task.form.dto.DragResultDTO;
import com.jiuqi.nr.task.form.field.dto.DataFieldDTO;
import com.jiuqi.nr.task.form.field.dto.DataFieldSettingDTO;
import java.util.List;

public interface IDataFieldService {
    public void saveFields(String var1, List<DataFieldSettingDTO> var2);

    public DataFieldSettingDTO getFieldSetting(String var1);

    public List<DataFieldSettingDTO> listFieldsByTable(String var1);

    public List<ITree<DataSchemeNode>> getFieldTreeRoot(DataSchemeTreeQuery<DataSchemeNodeDTO> var1);

    public List<ITree<DataSchemeNode>> getFieldTreeChild(DataSchemeTreeQuery<DataSchemeNodeDTO> var1);

    public List<DataSchemeNode> filterFieldTree(DataSchemeTreeQuery<DataSchemeNodeDTO> var1);

    public List<ITree<DataSchemeNode>> getFieldTreePath(DataSchemeTreeQuery<DataSchemeNodeDTO> var1);

    public DragResultDTO queryDragResult(List<DragNodeDTO> var1);

    public List<DataFieldDTO> listFieldsByRefEntityKeys(List<String> var1);
}

