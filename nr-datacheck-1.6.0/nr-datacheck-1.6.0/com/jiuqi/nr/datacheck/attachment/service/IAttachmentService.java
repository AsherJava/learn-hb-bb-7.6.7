/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.multcheck2.bean.MultcheckItem
 *  com.jiuqi.nr.multcheck2.provider.CheckItemParam
 *  com.jiuqi.nr.multcheck2.provider.CheckItemResult
 *  javax.servlet.http.HttpServletResponse
 */
package com.jiuqi.nr.datacheck.attachment.service;

import com.jiuqi.nr.datacheck.attachment.vo.AttachmentQueryPM;
import com.jiuqi.nr.datacheck.attachment.vo.AttachmentResVO;
import com.jiuqi.nr.datacheck.attachment.vo.QueryResParam;
import com.jiuqi.nr.multcheck2.bean.MultcheckItem;
import com.jiuqi.nr.multcheck2.provider.CheckItemParam;
import com.jiuqi.nr.multcheck2.provider.CheckItemResult;
import javax.servlet.http.HttpServletResponse;

public interface IAttachmentService {
    public CheckItemResult runCheck(CheckItemParam var1);

    public String getItemDescribe(String var1, MultcheckItem var2);

    public String getRunItemDescribe(String var1, MultcheckItem var2);

    public AttachmentResVO queryResult(QueryResParam var1);

    public void exportResult(HttpServletResponse var1, AttachmentQueryPM var2);
}

