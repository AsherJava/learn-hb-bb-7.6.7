/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.task.api.tree.UITreeNode
 */
package com.jiuqi.nr.task.form.service;

import com.jiuqi.nr.task.api.tree.UITreeNode;
import com.jiuqi.nr.task.form.dto.AnalysisNode;
import com.jiuqi.nr.task.form.form.dto.FormDTO;
import com.jiuqi.nr.task.form.formstyle.dto.FormStyleDTO;
import com.jiuqi.nr.task.form.vo.AnalysisInsertVo;
import com.jiuqi.nr.task.form.vo.ResultVo;
import java.util.List;

public interface IAnalysisService {
    public List<UITreeNode<AnalysisNode>> getRootTree();

    public List<UITreeNode<AnalysisNode>> getChildrenTree(String var1, boolean var2);

    public List<UITreeNode<AnalysisNode>> getAllChildrenTree();

    public List<UITreeNode<AnalysisNode>> locationTree(String var1);

    public List<AnalysisNode> search(String var1) throws Exception;

    public ResultVo insertForm(AnalysisInsertVo var1) throws Exception;

    public List<ResultVo> batchInsertForm(List<AnalysisInsertVo> var1) throws Exception;

    public void updateForm(FormDTO var1) throws Exception;

    public FormStyleDTO getStyle(String var1);
}

