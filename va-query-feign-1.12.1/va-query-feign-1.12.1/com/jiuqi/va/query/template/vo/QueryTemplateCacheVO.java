/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.query.template.vo;

import com.jiuqi.va.query.template.vo.TemplateInfoVO;

public class QueryTemplateCacheVO {
    private String templateId;
    private String templateCode;
    private String templateDesign;
    private TemplateInfoVO templateInfo;

    public String getTemplateId() {
        return this.templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String getTemplateCode() {
        return this.templateCode;
    }

    public void setTemplateCode(String templateCode) {
        this.templateCode = templateCode;
    }

    public String getTemplateDesign() {
        return this.templateDesign;
    }

    public void setTemplateDesign(String templateDesign) {
        this.templateDesign = templateDesign;
    }

    public TemplateInfoVO getTemplateInfo() {
        return this.templateInfo;
    }

    public void setTemplateInfo(TemplateInfoVO templateInfo) {
        this.templateInfo = templateInfo;
    }
}

