/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.swagger.annotations.ApiModel
 *  io.swagger.annotations.ApiModelProperty
 */
package com.jiuqi.gcreport.org.api.vo.tool;

import com.jiuqi.gcreport.org.api.period.YearPeriodDO;
import com.jiuqi.gcreport.org.api.period.YearPeriodUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Calendar;
import org.springframework.util.StringUtils;

@ApiModel(value="\u7ec4\u7ec7\u673a\u6784\u67e5\u8be2\u7248\u672c\u671f\u95f4\u53c2\u6570")
public class YearPeriodObject {
    @ApiModelProperty(value="\u671f\u95f4\u5b9a\u4e49\u8868\u8868\u540d", allowEmptyValue=true)
    private String formSchemeKey;
    @ApiModelProperty(value="\u5e74\u5ea6", allowEmptyValue=true)
    private int year;
    @ApiModelProperty(value="\u671f\u95f4", allowEmptyValue=true)
    private int period;
    @ApiModelProperty(value="\u671f\u95f4\u7c7b\u578b(\u9ed8\u8ba4\u6708\u4efd\u671f\u95f4)", allowEmptyValue=true, example="")
    private int type = 4;
    @ApiModelProperty(value="\u671f\u95f4\u5b57\u7b26\u4e32(\u5e74\u5ea6+\u7c7b\u578b+\u671f\u95f4yyyyTpppp)(\u6216\u8005\u65e5\u671fyyyyMMdd\u683c\u5f0f\u5316\u5b57\u7b26\u4e32)", allowEmptyValue=true)
    private String ytm;

    public YearPeriodObject() {
    }

    public YearPeriodObject(Calendar calendar) {
        this(calendar.get(1), calendar.get(2) + 1);
    }

    public YearPeriodObject(int year, int period) {
        this(null, year, 4, period);
    }

    public YearPeriodObject(String formSchemeKey, String periodStr) {
        this.formSchemeKey = formSchemeKey;
        this.ytm = periodStr;
        YearPeriodDO formatYP = this.formatYP();
        this.year = formatYP.getYear();
        this.period = formatYP.getPeriod();
        this.type = formatYP.getType();
    }

    public YearPeriodObject(String formSchemeKey, int year, int type, int period) {
        this.formSchemeKey = formSchemeKey;
        this.year = year;
        this.period = period;
        this.type = type;
    }

    public int getYear() {
        return this.year;
    }

    public int getPeriod() {
        return this.period;
    }

    public int getType() {
        return this.type;
    }

    public String getYtm() {
        return this.ytm;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setYtm(String ytm) {
        this.ytm = ytm;
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public YearPeriodDO formatYP() {
        if (!StringUtils.isEmpty(this.getYtm())) {
            return YearPeriodUtil.transform(this.getFormSchemeKey(), this.getYtm());
        }
        if (this.getType() >= 0 && this.getYear() > 0 && this.getPeriod() >= 0) {
            return YearPeriodUtil.transform(this.getFormSchemeKey(), this.getYear(), this.getType(), this.getPeriod());
        }
        return YearPeriodUtil.transform();
    }
}

