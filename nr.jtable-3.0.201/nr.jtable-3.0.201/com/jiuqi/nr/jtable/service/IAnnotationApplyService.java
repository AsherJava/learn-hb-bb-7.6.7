/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.annotation.output.CellAnnotationComment
 *  com.jiuqi.nr.annotation.output.CellAnnotationContent
 *  com.jiuqi.nr.annotation.output.CellAnnotationResult
 *  com.jiuqi.nr.annotation.output.FormAnnotationResult
 *  com.jiuqi.nr.basedata.select.bean.BaseDataInfo
 *  com.jiuqi.nr.common.itree.ITree
 */
package com.jiuqi.nr.jtable.service;

import com.jiuqi.nr.annotation.output.CellAnnotationComment;
import com.jiuqi.nr.annotation.output.CellAnnotationContent;
import com.jiuqi.nr.annotation.output.CellAnnotationResult;
import com.jiuqi.nr.annotation.output.FormAnnotationResult;
import com.jiuqi.nr.basedata.select.bean.BaseDataInfo;
import com.jiuqi.nr.common.itree.ITree;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.params.input.AnnotationTypePositionCondition;
import com.jiuqi.nr.jtable.params.input.AnnotationTypeQueryCondition;
import com.jiuqi.nr.jtable.params.input.AnnotationTypeSearchCondition;
import com.jiuqi.nr.jtable.params.input.CellAnnotationQueryCondition;
import com.jiuqi.nr.jtable.params.input.FormAnnotationCommentCondition;
import com.jiuqi.nr.jtable.params.input.FormAnnotationCondition;
import com.jiuqi.nr.jtable.params.input.FormAnnotationDeleteCondition;
import com.jiuqi.nr.jtable.params.input.FormAnnotationQueryCondition;
import com.jiuqi.nr.jtable.params.output.ReturnInfo;
import java.util.List;

public interface IAnnotationApplyService {
    public FormAnnotationResult queryFormAnnotation(JtableContext var1, String var2);

    public List<CellAnnotationContent> queryFormAnnotationDetailed(FormAnnotationQueryCondition var1);

    public CellAnnotationResult queryCellAnnotation(CellAnnotationQueryCondition var1);

    public CellAnnotationContent saveOrUpdateFormAnnotation(FormAnnotationCondition var1);

    public ReturnInfo removeFormAnnotation(FormAnnotationDeleteCondition var1);

    public CellAnnotationComment saveOrUpdateFormAnnotationComment(FormAnnotationCommentCondition var1);

    public ReturnInfo removeFormAnnotationComment(FormAnnotationDeleteCondition var1);

    public ITree<BaseDataInfo> queryAnnotationTypeTree(AnnotationTypeQueryCondition var1);

    public ITree<BaseDataInfo> positioningAnnotationType(AnnotationTypePositionCondition var1);

    public ITree<BaseDataInfo> searchAnnotationType(AnnotationTypeSearchCondition var1);
}

