/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.Column
 *  org.json.JSONObject
 */
package com.jiuqi.bi.dataset.restrict;

import com.jiuqi.bi.dataset.BIDataSetException;
import com.jiuqi.bi.dataset.BIDataSetFieldInfo;
import com.jiuqi.bi.dataset.BIDataSetImpl;
import com.jiuqi.bi.dataset.Column;
import com.jiuqi.bi.dataset.expression.DSFormulaContext;
import com.jiuqi.bi.dataset.manager.TimeKeyBuilder;
import com.jiuqi.bi.dataset.model.field.TimeGranularity;
import com.jiuqi.bi.dataset.restrict.ICondition;
import com.jiuqi.bi.dataset.restrict.RestrictionDescriptor;
import java.util.List;
import org.json.JSONObject;

class MixOffsetValueCondition
implements ICondition {
    private BIDataSetImpl dataset;
    private RestrictionDescriptor yearRst;
    private List<RestrictionDescriptor> valueTimeRst;
    private int sys_timekeycol;
    private String year;
    private String month;
    private String day;
    private String rstTimekey;

    public MixOffsetValueCondition(BIDataSetImpl dataset, RestrictionDescriptor yearRst, List<RestrictionDescriptor> otherTimeRst) {
        this.dataset = dataset;
        this.yearRst = yearRst;
        this.valueTimeRst = otherTimeRst;
        this.sys_timekeycol = dataset.getMetadata().indexOf("SYS_TIMEKEY");
        this.init();
    }

    @Override
    public boolean canUseIndex() {
        return true;
    }

    @Override
    public int getCol() {
        return this.sys_timekeycol;
    }

    @Override
    public void validate() throws BIDataSetException {
        if (this.yearRst.item == null) {
            throw new BIDataSetException("\u5e74\u504f\u79fb\u9650\u5b9a\u4e0d\u80fd\u662f\u4e00\u4e2a\u8868\u8fbe\u5f0f");
        }
        TimeGranularity granularity = this.yearRst.item.getTimegranularity();
        if (granularity != TimeGranularity.YEAR) {
            throw new BIDataSetException("\u5982\u679c\u8868\u8fbe\u5f0f\u65e2\u6709\u504f\u79fb\uff0c\u53c8\u6709\u503c\u9650\u5b9a\uff0c\u5219\u504f\u79fb\u53ea\u80fd\u662f\u5bf9\u5e74\u7c92\u5ea6\u5b57\u6bb5\u8fdb\u884c");
        }
    }

    @Override
    public Object getValue(DSFormulaContext dsCxt) throws BIDataSetException {
        String timekey = (String)dsCxt.getCurrentValue(this.sys_timekeycol);
        if (timekey == null) {
            throw new BIDataSetException("\u516c\u5f0f\u6267\u884c\u8fc7\u6ee4\u51fa\u9519\uff0c\u65e0\u6cd5\u6839\u636e\u4e0a\u4e0b\u6587\u4fe1\u606f\u83b7\u53d6\u5f53\u524dtimekey");
        }
        int minMonth = -1;
        int maxMonth = -1;
        String fmstr = this.dataset.getMetadata().getProperties().getOrDefault("FiscalMonth", null);
        if (fmstr != null) {
            JSONObject json = new JSONObject(fmstr);
            minMonth = Integer.parseInt(json.optString("min", "-1"));
            maxMonth = Integer.parseInt(json.optString("max", "-1"));
        }
        String newTimekey = TimeKeyBuilder.prev(timekey, TimeGranularity.YEAR, -((Integer)this.yearRst.condition).intValue(), minMonth, maxMonth);
        return this.replaceTimekey(newTimekey);
    }

    private String replaceTimekey(String timekey) {
        if (this.rstTimekey != null) {
            return this.rstTimekey;
        }
        StringBuilder buf = new StringBuilder(8);
        String y = this.year != null ? this.year : timekey.substring(0, 4);
        String m = this.month != null ? this.month : timekey.substring(4, 6);
        String d = this.day != null ? this.day : timekey.substring(6, 8);
        buf.append(y).append(m).append(d);
        return buf.toString();
    }

    private void init() {
        for (RestrictionDescriptor rstDesc : this.valueTimeRst) {
            TimeGranularity granularity = rstDesc.item.getTimegranularity();
            if (rstDesc.mode != 2) continue;
            switch (granularity) {
                case YEAR: {
                    this.year = this.getYearStrValue(rstDesc);
                    break;
                }
                case MONTH: {
                    this.month = this.getMonthStrValue(rstDesc);
                    break;
                }
                case DAY: {
                    this.day = this.getDayStrValue(rstDesc);
                    break;
                }
            }
        }
        Column sys_timekeyInfo = this.dataset.getMetadata().getColumn(this.sys_timekeycol);
        TimeGranularity granularity = ((BIDataSetFieldInfo)sys_timekeyInfo.getInfo()).getTimegranularity();
        switch (granularity) {
            case YEAR: {
                if (this.year == null) break;
                this.rstTimekey = this.year + "0101";
                break;
            }
            case MONTH: 
            case HALFYEAR: 
            case QUARTER: {
                if (this.year == null || this.month == null) break;
                this.rstTimekey = this.year + this.month + "01";
                break;
            }
            case DAY: 
            case XUN: {
                if (this.year == null || this.month == null || this.day == null) break;
                this.rstTimekey = this.year + this.month + this.day;
            }
        }
    }

    private String getYearStrValue(RestrictionDescriptor rstDesc) {
        this.year = rstDesc.condition instanceof Number ? ((Number)rstDesc.condition).intValue() + "" : String.valueOf(rstDesc.condition);
        return this.year;
    }

    private String getMonthStrValue(RestrictionDescriptor rstDesc) {
        if (rstDesc.condition instanceof Number) {
            int val = ((Number)rstDesc.condition).intValue();
            this.month = val < 10 ? "0" + val : val + "";
        } else {
            this.month = String.valueOf(rstDesc.condition);
            if (this.month.length() == 1) {
                this.month = "0" + this.month;
            }
        }
        return this.month;
    }

    private String getDayStrValue(RestrictionDescriptor rstDesc) {
        if (rstDesc.condition instanceof Number) {
            int val = ((Number)rstDesc.condition).intValue();
            this.day = val < 10 ? "0" + val : val + "";
        } else {
            this.day = String.valueOf(rstDesc.condition);
            if (this.day.length() == 1) {
                this.day = "0" + this.day;
            }
        }
        return this.day;
    }
}

