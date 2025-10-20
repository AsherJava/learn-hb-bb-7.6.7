/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.AdjustPeriod
 */
package com.jiuqi.nr.definition.web.vo;

import com.jiuqi.nr.datascheme.api.AdjustPeriod;
import java.util.Objects;

public class AdjustPeriodVO {
    private String code;
    private String title;
    private String order;
    private String period;

    public String getPeriod() {
        return this.period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOrder() {
        return this.order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public static AdjustPeriodVO convertToVO(AdjustPeriod adjustPeriod) {
        if (Objects.isNull(adjustPeriod)) {
            return null;
        }
        AdjustPeriodVO adjustPeriodVO = new AdjustPeriodVO();
        adjustPeriodVO.setPeriod(adjustPeriod.getPeriod());
        adjustPeriodVO.setCode(adjustPeriod.getCode());
        adjustPeriodVO.setTitle(adjustPeriod.getTitle());
        adjustPeriodVO.setOrder(adjustPeriod.getOrder());
        return adjustPeriodVO;
    }
}

