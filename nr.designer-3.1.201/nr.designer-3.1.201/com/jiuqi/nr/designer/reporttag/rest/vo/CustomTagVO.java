/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.facade.DesignReportTagDefine
 *  com.jiuqi.nr.definition.reportTag.common.ReportTagType
 */
package com.jiuqi.nr.designer.reporttag.rest.vo;

import com.jiuqi.nr.definition.facade.DesignReportTagDefine;
import com.jiuqi.nr.definition.reportTag.common.ReportTagType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CustomTagVO {
    private String key;
    private String rptKey;
    private String type;
    private String content;
    private String expression;
    private boolean _disabled;

    public CustomTagVO() {
    }

    public CustomTagVO(String key, String rptKey, String type, String content, String expression) {
        this.key = key;
        this.rptKey = rptKey;
        this.type = type;
        this.content = content;
        this.expression = expression;
    }

    public static List<CustomTagVO> toCustomTagVOList(List<DesignReportTagDefine> reportTagDefines) {
        if (reportTagDefines == null || reportTagDefines.isEmpty()) {
            return Collections.emptyList();
        }
        ArrayList<CustomTagVO> list = new ArrayList<CustomTagVO>();
        for (DesignReportTagDefine reportTagDefine : reportTagDefines) {
            CustomTagVO customTagVO = new CustomTagVO(reportTagDefine.getKey(), reportTagDefine.getRptKey(), ReportTagType.getValueByKey((int)reportTagDefine.getType()), reportTagDefine.getContent(), reportTagDefine.getExpression());
            list.add(customTagVO);
        }
        return list;
    }

    public String getKey() {
        return this.key;
    }

    public String getRptKey() {
        return this.rptKey;
    }

    public String getType() {
        return this.type;
    }

    public String getContent() {
        return this.content;
    }

    public String getExpression() {
        return this.expression;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setRptKey(String rptKey) {
        this.rptKey = rptKey;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public boolean is_disabled() {
        return this._disabled;
    }

    public void set_disabled(boolean _disabled) {
        this._disabled = _disabled;
    }
}

