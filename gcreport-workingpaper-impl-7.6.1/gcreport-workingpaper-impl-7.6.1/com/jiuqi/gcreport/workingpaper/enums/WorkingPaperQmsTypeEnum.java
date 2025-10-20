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

public enum WorkingPaperQmsTypeEnum {
    CONTAIN_DXS("CONTAIN_DXS", "gc.workingpaper.qms.type.contain"),
    NOT_CONTAIN_DXS("NOT_CONTAIN_DXS", "gc.workingpaper.qms.type.notcontain"),
    NO_SHOW("NO_SHOWS", "gc.workingpaper.qms.type.noshow");

    private String code;
    private String title;

    private WorkingPaperQmsTypeEnum(String code, String title) {
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

    public static WorkingPaperQmsTypeEnum getEnumByCode(String code) {
        for (WorkingPaperQmsTypeEnum typeEnum : WorkingPaperQmsTypeEnum.values()) {
            if (!typeEnum.getCode().equals(code)) continue;
            return typeEnum;
        }
        return null;
    }

    public static List<WorkingPaperTypeOptionVO> toWorkPaperTypeOptionVOs() {
        ArrayList<WorkingPaperTypeOptionVO> vos = new ArrayList<WorkingPaperTypeOptionVO>();
        for (WorkingPaperQmsTypeEnum enumItem : WorkingPaperQmsTypeEnum.values()) {
            vos.add(new WorkingPaperTypeOptionVO(enumItem.getCode(), enumItem.getTitle()));
        }
        return vos;
    }
}

