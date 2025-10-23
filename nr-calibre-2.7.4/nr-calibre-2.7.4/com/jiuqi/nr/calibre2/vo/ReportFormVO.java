/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.common.FormType
 *  com.jiuqi.nr.definition.facade.FormDefine
 */
package com.jiuqi.nr.calibre2.vo;

import com.jiuqi.nr.calibre2.vo.CommonReportFormVO;
import com.jiuqi.nr.definition.common.FormType;
import com.jiuqi.nr.definition.facade.FormDefine;

public class ReportFormVO
extends CommonReportFormVO {
    private String code;
    private FormType type;
    private String order;

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public FormType getType() {
        return this.type;
    }

    public void setType(FormType type) {
        this.type = type;
    }

    public String getOrder() {
        return this.order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public static ReportFormVO newReportForm(FormDefine formDefine) {
        ReportFormVO reportFormVO = new ReportFormVO();
        reportFormVO.setKey(formDefine.getKey());
        reportFormVO.setTitle(formDefine.getTitle());
        reportFormVO.setCode(formDefine.getFormCode());
        reportFormVO.setType(formDefine.getFormType());
        reportFormVO.setOrder(formDefine.getOrder());
        return reportFormVO;
    }
}

