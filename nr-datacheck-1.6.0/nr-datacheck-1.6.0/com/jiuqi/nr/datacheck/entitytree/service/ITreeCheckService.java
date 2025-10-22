/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.multcheck2.provider.CheckItemParam
 *  com.jiuqi.nr.multcheck2.provider.CheckItemResult
 *  com.jiuqi.nr.multcheck2.provider.MultCheckItemDTO
 *  javax.servlet.http.HttpServletResponse
 *  nr.single.data.bean.CheckResultNode
 */
package com.jiuqi.nr.datacheck.entitytree.service;

import com.jiuqi.nr.datacheck.entitytree.vo.TreeCheckPM;
import com.jiuqi.nr.multcheck2.provider.CheckItemParam;
import com.jiuqi.nr.multcheck2.provider.CheckItemResult;
import com.jiuqi.nr.multcheck2.provider.MultCheckItemDTO;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import nr.single.data.bean.CheckResultNode;

public interface ITreeCheckService {
    public List<CheckResultNode> queryRunCheckResult(TreeCheckPM var1);

    public CheckItemResult runCheck(CheckItemParam var1);

    public void exportRunCheckResult(HttpServletResponse var1, TreeCheckPM var2);

    public MultCheckItemDTO getDefaultCheckItem(String var1);
}

