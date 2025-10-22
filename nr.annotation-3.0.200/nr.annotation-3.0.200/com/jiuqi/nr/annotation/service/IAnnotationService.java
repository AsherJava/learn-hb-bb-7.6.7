/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.annotation.service;

import com.jiuqi.nr.annotation.input.CellAnnotationQueryInfo;
import com.jiuqi.nr.annotation.input.ExpAnnotationParam;
import com.jiuqi.nr.annotation.input.FormAnnotationBatchSaveInfo;
import com.jiuqi.nr.annotation.input.FormAnnotationDeleteInfo;
import com.jiuqi.nr.annotation.input.FormAnnotationQueryInfo;
import com.jiuqi.nr.annotation.input.FormCellAnnotationQueryInfo;
import com.jiuqi.nr.annotation.input.SaveFormAnnotationCommentInfo;
import com.jiuqi.nr.annotation.input.SaveFormAnnotationInfo;
import com.jiuqi.nr.annotation.input.UpdateFormAnnotationCommentInfo;
import com.jiuqi.nr.annotation.input.UpdateFormAnnotationInfo;
import com.jiuqi.nr.annotation.output.CellAnnotationComment;
import com.jiuqi.nr.annotation.output.CellAnnotationContent;
import com.jiuqi.nr.annotation.output.CellAnnotationResult;
import com.jiuqi.nr.annotation.output.ExpAnnotationResult;
import com.jiuqi.nr.annotation.output.FormAnnotationResult;
import java.util.List;

public interface IAnnotationService {
    public FormAnnotationResult queryFormAnnotation(FormCellAnnotationQueryInfo var1);

    public List<CellAnnotationContent> queryFormAnnotationDetailed(FormAnnotationQueryInfo var1);

    public CellAnnotationResult queryCellAnnotation(CellAnnotationQueryInfo var1);

    public List<ExpAnnotationResult> queryAnnotation(ExpAnnotationParam var1);

    public CellAnnotationContent saveFormAnnotation(SaveFormAnnotationInfo var1);

    public CellAnnotationContent updateFormAnnotation(UpdateFormAnnotationInfo var1);

    public void batchSaveFormAnnotation(FormAnnotationBatchSaveInfo var1) throws Exception;

    public String removeFormAnnotation(FormAnnotationDeleteInfo var1);

    public CellAnnotationComment saveFormAnnotationComment(SaveFormAnnotationCommentInfo var1);

    public CellAnnotationComment updateFormAnnotationComment(UpdateFormAnnotationCommentInfo var1);

    public String removeFormAnnotationComment(FormAnnotationDeleteInfo var1);
}

