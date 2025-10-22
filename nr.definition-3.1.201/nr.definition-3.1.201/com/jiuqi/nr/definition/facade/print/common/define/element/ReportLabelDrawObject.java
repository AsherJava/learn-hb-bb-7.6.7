/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.util.StringUtils
 *  com.jiuqi.xg.process.obj.TextDrawObject
 */
package com.jiuqi.nr.definition.facade.print.common.define.element;

import com.jiuqi.util.StringUtils;
import com.jiuqi.xg.process.obj.TextDrawObject;

public class ReportLabelDrawObject
extends TextDrawObject {
    public static final String EXT_PAGENUM = "isPageNum";
    public static final String EXT_EXP = "exp";
    public static final String EXT_CURRPAGE = "currPage";

    public String getKind() {
        return "element_reportLabel";
    }

    public boolean containsPageNum() {
        String content = this.getContent();
        if (StringUtils.isEmpty((String)content)) {
            return false;
        }
        return content.contains("PageNumber") || content.contains("TotalCount") || content.contains("ReportPageNum") || content.contains("ReportPageCount") || content.contains("PageNum");
    }
}

