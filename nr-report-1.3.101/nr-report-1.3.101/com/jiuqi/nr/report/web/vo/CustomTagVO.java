/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.facade.DesignReportTagDefine
 *  com.jiuqi.nr.definition.internal.impl.DesignReportTagDefineImpl
 *  com.jiuqi.nr.definition.reportTag.common.ReportTagType
 */
package com.jiuqi.nr.report.web.vo;

import com.jiuqi.nr.definition.facade.DesignReportTagDefine;
import com.jiuqi.nr.definition.internal.impl.DesignReportTagDefineImpl;
import com.jiuqi.nr.definition.reportTag.common.ReportTagType;
import com.jiuqi.nr.report.dto.ReportTagDTO;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.util.StringUtils;

public class CustomTagVO {
    private String key;
    private String rptKey;
    private String type;
    private String content;
    private String expression;
    private boolean _disabled;
    private TreeNodeSelectVO expressionNode = new TreeNodeSelectVO();

    public CustomTagVO() {
    }

    public CustomTagVO(String key, String rptKey, String type, String content, String expression) {
        this.key = key;
        this.rptKey = rptKey;
        this.type = type;
        this.content = content;
        this.expression = expression;
        this.expressionNode.setKey(expression);
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

    public DesignReportTagDefine toCustomTagVOList() {
        DesignReportTagDefineImpl designReportTagDefine = new DesignReportTagDefineImpl();
        designReportTagDefine.setKey(this.key);
        designReportTagDefine.setRptKey(this.rptKey);
        designReportTagDefine.setType(ReportTagType.getKeyByValue((String)this.type));
        designReportTagDefine.setContent(this.content);
        if (StringUtils.hasLength(this.expressionNode.getKey())) {
            designReportTagDefine.setExpression(this.expressionNode.getKey());
        } else {
            designReportTagDefine.setExpression(this.expression);
        }
        return designReportTagDefine;
    }

    public static List<CustomTagVO> dtoToCustomTagVOList(List<ReportTagDTO> reportTagDefines) {
        if (reportTagDefines == null || reportTagDefines.isEmpty()) {
            return Collections.emptyList();
        }
        ArrayList<CustomTagVO> list = new ArrayList<CustomTagVO>();
        for (ReportTagDTO reportTagDefine : reportTagDefines) {
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

    public TreeNodeSelectVO getExpressionNode() {
        return this.expressionNode;
    }

    public void setExpressionNode(TreeNodeSelectVO expressionNode) {
        this.expressionNode = expressionNode;
    }

    public class TreeNodeSelectVO {
        private String title;
        private String key;

        public String getTitle() {
            return this.title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getKey() {
            return this.key;
        }

        public void setKey(String key) {
            this.key = key;
        }
    }
}

