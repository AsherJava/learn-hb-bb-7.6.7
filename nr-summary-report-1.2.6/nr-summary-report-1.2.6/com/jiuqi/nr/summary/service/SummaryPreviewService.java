/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.cellbook.model.CellBook
 */
package com.jiuqi.nr.summary.service;

import com.jiuqi.nr.summary.vo.PreviewConfigVo;
import com.jiuqi.nr.summary.vo.PreviewInitParam;
import com.jiuqi.nr.summary.vo.PreviewInitReqParam;
import com.jiuqi.nvwa.cellbook.model.CellBook;

public interface SummaryPreviewService {
    public PreviewInitParam getInitParam(PreviewInitReqParam var1) throws Exception;

    public CellBook getSummaryResult(PreviewConfigVo var1) throws Exception;
}

