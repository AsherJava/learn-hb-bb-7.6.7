/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.AdjustPeriod
 *  com.jiuqi.nr.datascheme.api.core.Ordered
 *  javax.validation.constraints.NotNull
 */
package com.jiuqi.nr.datascheme.adjustment.entity;

import com.jiuqi.nr.datascheme.api.AdjustPeriod;
import com.jiuqi.nr.datascheme.api.core.Ordered;
import com.jiuqi.nr.datascheme.internal.anno.DBAnno;
import java.util.Objects;
import java.util.StringJoiner;
import javax.validation.constraints.NotNull;

@DBAnno.DBTable(dbTable="NR_ADJUST_PERIOD")
public class AdjustPeriodDO
implements AdjustPeriod {
    private static final long serialVersionUID = 1449231019453446754L;
    @DBAnno.DBField(dbField="AP_DS_KEY")
    @NotNull
    private String dataSchemeKey;
    @DBAnno.DBField(dbField="AP_PERIOD")
    @NotNull
    private String period;
    @DBAnno.DBField(dbField="AP_CODE")
    @NotNull
    private String code;
    @DBAnno.DBField(dbField="AP_TITLE")
    private String title;
    @DBAnno.DBField(dbField="AP_ORDER")
    private String order;

    public static AdjustPeriodDO toDO(AdjustPeriod period) {
        AdjustPeriodDO periodDO = new AdjustPeriodDO();
        periodDO.setOrder(period.getOrder());
        periodDO.setCode(period.getCode());
        periodDO.setDataSchemeKey(period.getDataSchemeKey());
        periodDO.setTitle(period.getTitle());
        periodDO.setPeriod(period.getPeriod());
        return periodDO;
    }

    public String getDataSchemeKey() {
        return this.dataSchemeKey;
    }

    public String getPeriod() {
        return this.period;
    }

    public String getCode() {
        return this.code;
    }

    public String getTitle() {
        return this.title;
    }

    public String getOrder() {
        return this.order;
    }

    public void setDataSchemeKey(String dataSchemeKey) {
        this.dataSchemeKey = dataSchemeKey;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        AdjustPeriodDO that = (AdjustPeriodDO)o;
        return Objects.equals(this.getDataSchemeKey(), that.getDataSchemeKey()) && Objects.equals(this.getPeriod(), that.getPeriod()) && Objects.equals(this.getCode(), that.getCode()) && Objects.equals(this.getTitle(), that.getTitle());
    }

    public int hashCode() {
        return Objects.hash(this.getDataSchemeKey(), this.getPeriod(), this.getCode(), this.getTitle());
    }

    public String toString() {
        return new StringJoiner(", ", AdjustPeriodDO.class.getSimpleName() + "[", "]").add("dataSchemeKey='" + this.dataSchemeKey + "'").add("period='" + this.period + "'").add("code='" + this.code + "'").add("title='" + this.title + "'").toString();
    }

    public int compareTo(Ordered o) {
        if (o == null || o.getOrder() == null) {
            return 1;
        }
        if (this.getOrder() == null) {
            return -1;
        }
        return this.getOrder().compareTo(o.getOrder());
    }
}

