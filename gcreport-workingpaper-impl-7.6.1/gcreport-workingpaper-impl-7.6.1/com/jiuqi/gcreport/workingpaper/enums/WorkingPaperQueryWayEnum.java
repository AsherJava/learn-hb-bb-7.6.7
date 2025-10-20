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
import com.jiuqi.gcreport.workingpaper.vo.WorkingPaperTypeOptionVO;
import java.util.ArrayList;
import java.util.List;

public enum WorkingPaperQueryWayEnum {
    JD("JD", "gc.workingpaper.query.way.jd"),
    GCYWXL("GCYWXL", "gc.workingpaper.query.way.gcywlx"),
    DX("DX", "gc.workingpaper.query.way.dx"),
    HBMX("HBMX", "gc.workingpaper.query.way.hbmx"),
    CUBES_JD("CUBES_JD", "gc.workingpaper.query.way.cubes.jd"),
    CUBES_GCYWXL("CUBES_GCYWXL", "gc.workingpaper.query.way.cubes.gcywlx"),
    CUBES_UNIT("CUBES_UNIT", "gc.workingpaper.query.way.cubes.unit"),
    MERGE_JD("MERGE_JD", "gc.workingpaper.query.way.merge.jd");

    private String code;
    private String title;

    private WorkingPaperQueryWayEnum(String code, String title) {
        this.code = code;
        this.title = title;
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

    public static WorkingPaperQueryWayEnum getEnumByCode(String code) {
        for (WorkingPaperQueryWayEnum typeEnum : WorkingPaperQueryWayEnum.values()) {
            if (!typeEnum.getCode().equals(code)) continue;
            return typeEnum;
        }
        return null;
    }

    public static List<WorkingPaperTypeOptionVO> toWorkPaperTypeOptionVOs() {
        ArrayList<WorkingPaperTypeOptionVO> vos = new ArrayList<WorkingPaperTypeOptionVO>();
        for (WorkingPaperQueryWayEnum enumItem : WorkingPaperQueryWayEnum.values()) {
            vos.add(new WorkingPaperTypeOptionVO(enumItem.getCode(), enumItem.getTitle()));
        }
        return vos;
    }
}

