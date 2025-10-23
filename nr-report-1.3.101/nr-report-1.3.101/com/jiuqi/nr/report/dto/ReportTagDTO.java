/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.facade.DesignReportTagDefine
 *  com.jiuqi.nr.definition.internal.impl.DesignReportTagDefineImpl
 */
package com.jiuqi.nr.report.dto;

import com.jiuqi.nr.definition.facade.DesignReportTagDefine;
import com.jiuqi.nr.definition.internal.impl.DesignReportTagDefineImpl;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ReportTagDTO {
    private String key;
    private String rptKey;
    private int type;
    private String content;
    private String expression;

    public ReportTagDTO() {
    }

    public ReportTagDTO(DesignReportTagDefine define) {
        if (define != null) {
            this.key = define.getKey();
            this.rptKey = define.getRptKey();
            this.type = define.getType();
            this.content = define.getContent();
            this.expression = define.getExpression();
        }
    }

    public DesignReportTagDefine toDesignReportTagDefine() {
        DesignReportTagDefineImpl reportTagDefine = new DesignReportTagDefineImpl();
        reportTagDefine.setKey(this.key);
        reportTagDefine.setRptKey(this.rptKey);
        reportTagDefine.setType(this.type);
        reportTagDefine.setContent(this.content);
        reportTagDefine.setExpression(this.expression);
        return reportTagDefine;
    }

    public static List<ReportTagDTO> toCustomTagVOList(List<DesignReportTagDefine> reportTagDefines) {
        if (reportTagDefines == null || reportTagDefines.isEmpty()) {
            return Collections.emptyList();
        }
        ArrayList<ReportTagDTO> list = new ArrayList<ReportTagDTO>();
        for (DesignReportTagDefine reportTagDefine : reportTagDefines) {
            ReportTagDTO reportTagDTO = new ReportTagDTO(reportTagDefine);
            list.add(reportTagDTO);
        }
        return list;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getRptKey() {
        return this.rptKey;
    }

    public void setRptKey(String rptKey) {
        this.rptKey = rptKey;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getExpression() {
        return this.expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }
}

