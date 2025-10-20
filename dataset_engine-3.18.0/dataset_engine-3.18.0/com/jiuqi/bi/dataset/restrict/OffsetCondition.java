/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.json.JSONObject
 */
package com.jiuqi.bi.dataset.restrict;

import com.jiuqi.bi.dataset.BIDataSetException;
import com.jiuqi.bi.dataset.BIDataSetFieldInfo;
import com.jiuqi.bi.dataset.BIDataSetImpl;
import com.jiuqi.bi.dataset.expression.DSFormulaContext;
import com.jiuqi.bi.dataset.manager.TimeKeyBuilder;
import com.jiuqi.bi.dataset.model.field.TimeGranularity;
import com.jiuqi.bi.dataset.restrict.ICondition;
import com.jiuqi.bi.dataset.restrict.RestrictionDescriptor;
import java.util.List;
import org.json.JSONObject;

class OffsetCondition
implements ICondition {
    private BIDataSetImpl dataset;
    private List<RestrictionDescriptor> offsetTimeRst;
    private TimeGranularity granularity;
    private int timekeycol;
    private int offsets;
    private boolean useTimekey;
    private int minMonth = -1;
    private int maxMonth = -1;
    private int calcValueStrategy = 1;
    private static final int USE_OFFSET_STRATEGY = 1;
    private static final int USE_RESTRICTION_DESCRIPTOR_YEAR_WEEK_STRATEGY = 2;
    private static final int USE_RESTRICTION_DESCRIPTOR_SIMPLE_STRATEGY = 3;

    public OffsetCondition(BIDataSetImpl dataset, List<RestrictionDescriptor> offsetRst, boolean useTimekey) {
        String fmstr;
        this.dataset = dataset;
        this.offsetTimeRst = offsetRst;
        this.useTimekey = useTimekey;
        if (useTimekey) {
            this.timekeycol = this.dataset.getMetadata().indexOf("SYS_TIMEKEY");
            if (this.timekeycol != -1) {
                this.granularity = ((BIDataSetFieldInfo)this.dataset.getMetadata().getColumn(this.timekeycol).getInfo()).getTimegranularity();
                this.initCalcValueStrategy();
                if (1 == this.calcValueStrategy) {
                    this.initOffsetValue();
                }
            }
        } else {
            this.timekeycol = dataset.getMetadata().indexOf(this.offsetTimeRst.get((int)0).item.getName());
            this.granularity = ((BIDataSetFieldInfo)this.dataset.getMetadata().getColumn(this.timekeycol).getInfo()).getTimegranularity();
            this.offsets = -((Integer)offsetRst.get((int)0).condition).intValue();
        }
        if ((fmstr = (String)dataset.getMetadata().getProperties().getOrDefault("FiscalMonth", null)) != null) {
            JSONObject json = new JSONObject(fmstr);
            this.minMonth = Integer.parseInt(json.optString("min", "-1"));
            this.maxMonth = Integer.parseInt(json.optString("max", "-1"));
        }
    }

    @Override
    public boolean canUseIndex() {
        return true;
    }

    @Override
    public int getCol() {
        return this.timekeycol;
    }

    @Override
    public Object getValue(DSFormulaContext dsCxt) throws BIDataSetException {
        Object data = dsCxt.getCurrentValue(this.getCol());
        if (data == null) {
            throw new BIDataSetException("\u516c\u5f0f\u6267\u884c\u8fc7\u6ee4\u51fa\u9519\uff0c\u65e0\u6cd5\u6839\u636e\u4e0a\u4e0b\u6587\u4fe1\u606f\u83b7\u53d6\u5f53\u524d\u65f6\u671f");
        }
        if (this.useTimekey) {
            if (this.calcValueStrategy == 2) {
                return this.useResDesYearWeekStrategy(dsCxt);
            }
            if (this.calcValueStrategy == 3) {
                return this.useResDesSimpleStrategy(dsCxt);
            }
            return TimeKeyBuilder.prev((String)data, this.granularity, this.offsets, this.minMonth, this.maxMonth);
        }
        if (data instanceof Integer) {
            Integer cur = (Integer)data;
            return cur - this.offsets;
        }
        if (data instanceof String) {
            int cur = Integer.parseInt((String)data);
            return cur - this.offsets;
        }
        throw new BIDataSetException("\u65f6\u671f\u7ef4\u5ea6\u53ea\u80fd\u4e3a\u6570\u503c\u683c\u5f0f\u6216\u5b57\u7b26\u4e32\u683c\u5f0f");
    }

    private String useResDesYearWeekStrategy(DSFormulaContext dsCxt) {
        String data = (String)dsCxt.getCurrentValue(this.getCol());
        for (RestrictionDescriptor rstDesc : this.offsetTimeRst) {
            TimeGranularity curGranularity = rstDesc.item.getTimegranularity();
            int n = -((Integer)rstDesc.condition).intValue();
            if (TimeGranularity.YEAR == curGranularity) {
                data = TimeKeyBuilder.prevWeekByYear(data, n);
                continue;
            }
            data = TimeKeyBuilder.prev(data, curGranularity, n);
        }
        return data;
    }

    private String useResDesSimpleStrategy(DSFormulaContext dsCxt) {
        String data = (String)dsCxt.getCurrentValue(this.getCol());
        for (RestrictionDescriptor rstDesc : this.offsetTimeRst) {
            TimeGranularity curGranularity = rstDesc.item.getTimegranularity();
            int n = -((Integer)rstDesc.condition).intValue();
            data = TimeKeyBuilder.prev(data, curGranularity, n, this.minMonth, this.maxMonth);
        }
        return data;
    }

    @Override
    public void validate() throws BIDataSetException {
        if (this.timekeycol == -1) {
            throw new BIDataSetException("\u6570\u636e\u96c6\u4e2d\u4e0d\u5305\u542b\u65f6\u671f\u4e3b\u952e\uff0c\u65e0\u6cd5\u8fdb\u884c\u504f\u79fb\u8ba1\u7b97");
        }
        for (RestrictionDescriptor rstDesc : this.offsetTimeRst) {
            if (rstDesc.mode == 1) continue;
            throw new BIDataSetException("\u4f20\u5165\u7684\u9650\u5b9a\u6761\u4ef6\u4e2d\u5305\u542b\u975e\u504f\u79fb\u7684\u9650\u5b9a");
        }
    }

    private void initOffsetValue() {
        for (RestrictionDescriptor rstDesc : this.offsetTimeRst) {
            TimeGranularity curGranularity = rstDesc.item.getTimegranularity();
            int val = curGranularity.days() / this.granularity.days();
            int len = (Integer)rstDesc.condition;
            this.offsets -= val * len;
        }
    }

    private void initCalcValueStrategy() {
        for (RestrictionDescriptor rstDesc : this.offsetTimeRst) {
            TimeGranularity curGranularity = rstDesc.item.getTimegranularity();
            if (this.granularity == TimeGranularity.WEEK) {
                if (curGranularity != TimeGranularity.YEAR) continue;
                this.calcValueStrategy = 2;
                return;
            }
            if (curGranularity == TimeGranularity.DAY) continue;
            this.calcValueStrategy = 3;
            return;
        }
    }
}

