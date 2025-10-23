/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 */
package com.jiuqi.nr.calibre2.vo;

import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;

public class CommonReportFormVO {
    String key;
    String title;

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public static CommonReportFormVO newFormSchemeDefine(FormSchemeDefine schemeDefine) {
        CommonReportFormVO schemeDefineVO = new CommonReportFormVO();
        schemeDefineVO.setKey(schemeDefine.getKey());
        schemeDefineVO.setTitle(schemeDefine.getTitle());
        return schemeDefineVO;
    }

    public static CommonReportFormVO newTaskDefine(TaskDefine taskDefine) {
        CommonReportFormVO schemeDefineVO = new CommonReportFormVO();
        schemeDefineVO.setKey(taskDefine.getKey());
        schemeDefineVO.setTitle(taskDefine.getTitle());
        return schemeDefineVO;
    }
}

