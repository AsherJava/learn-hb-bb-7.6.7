/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.i18n.util.GcI18nUtil
 *  com.jiuqi.gcreport.workingpaper.vo.WorkingPaperTypeOptionVO
 */
package com.jiuqi.gcreport.workingpaper.enums;

import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.i18n.util.GcI18nUtil;
import com.jiuqi.gcreport.workingpaper.enums.WorkingPaperType;
import com.jiuqi.gcreport.workingpaper.vo.WorkingPaperTypeOptionVO;
import java.util.ArrayList;
import java.util.List;

public enum WorkingPaperQueryTypeEnum {
    SUBJECT("SUBJECT", "gc.workingpaper.query.type.subject", WorkingPaperType.SIMPLE),
    PRIMARY("PRIMARY", "gc.workingpaper.query.type.primary", WorkingPaperType.SIMPLE),
    RULE("RULE", "gc.workingpaper.query.type.rule", WorkingPaperType.SIMPLE),
    CUBES_SUBJECT("CUBES_SUBJECT", "gc.workingpaper.query.type.cubes.subject", WorkingPaperType.CUBES),
    MERGE("MERGE", "gc.workingpaper.query.type.merge", WorkingPaperType.MERGE);

    private String code;
    private String title;
    private WorkingPaperType workingPaperType;

    private WorkingPaperQueryTypeEnum(String code, String title, WorkingPaperType workingPaperType) {
        this.code = code;
        this.title = title;
        this.workingPaperType = workingPaperType;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public WorkingPaperType getWorkingPaperType() {
        return this.workingPaperType;
    }

    public void setWorkingPaperType(WorkingPaperType workingPaperType) {
        this.workingPaperType = workingPaperType;
    }

    public String getCode() {
        return this.code;
    }

    public String getTitle() {
        String i18nTitle = GcI18nUtil.getMessage((String)this.title);
        if (StringUtils.isEmpty((String)i18nTitle)) {
            i18nTitle = this.title;
        }
        return i18nTitle;
    }

    public static WorkingPaperQueryTypeEnum getEnumByCode(String code) {
        for (WorkingPaperQueryTypeEnum typeEnum : WorkingPaperQueryTypeEnum.values()) {
            if (!typeEnum.getCode().equals(code)) continue;
            return typeEnum;
        }
        return null;
    }

    public static List<WorkingPaperTypeOptionVO> toWorkPaperTypeOptionVOs(WorkingPaperType workingPaperType) {
        ArrayList<WorkingPaperTypeOptionVO> vos = new ArrayList<WorkingPaperTypeOptionVO>();
        for (WorkingPaperQueryTypeEnum enumItem : WorkingPaperQueryTypeEnum.values()) {
            if (enumItem.getWorkingPaperType() != workingPaperType) continue;
            vos.add(new WorkingPaperTypeOptionVO(enumItem.getCode(), enumItem.getTitle()));
        }
        return vos;
    }
}

