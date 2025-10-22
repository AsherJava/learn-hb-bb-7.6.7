/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.common.ReportInfo
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IDataModelLinkFinder
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.period.PeriodConsts
 *  com.jiuqi.np.period.PeriodModifier
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.np.period.PeriodWrapper
 */
package com.jiuqi.nr.function.func.floatcopy;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.common.ReportInfo;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IDataModelLinkFinder;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.period.PeriodConsts;
import com.jiuqi.np.period.PeriodModifier;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.nr.function.func.floatcopy.FloatCopyQueryInfo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FloatCopySrcQueryCondition {
    private FloatCopyQueryInfo row;
    private String filter;
    private String periodType;
    private int periodOffset;
    private int periodCount = 1;
    private String sortingInfoList;
    private List<String> operators = new ArrayList<String>();
    private String linkedAlias;
    private ReportInfo reportInfo;
    private HashMap<String, Object> srcDestUnitKeyMap;
    private String srcUnitDimension;
    private String destUnitDimension;
    private PeriodType linkedSchemePeriodType;
    private List<String> periodList = new ArrayList<String>();

    public FloatCopyQueryInfo getRow() {
        return this.row;
    }

    public void setRow(FloatCopyQueryInfo row) {
        this.row = row;
    }

    public String getFilter() {
        return this.filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public void setPeriodType(String periodType) {
        this.periodType = periodType;
    }

    public void setPeriodOffset(long periodOffset) {
        this.periodOffset = (int)periodOffset;
    }

    public void setPeriodCount(long periodCount) {
        this.periodCount = (int)periodCount;
    }

    public PeriodWrapper getSrcPeriod(ExecutorContext exeContext, String periodValue) {
        PeriodWrapper srcPeriod = new PeriodWrapper(periodValue);
        if (StringUtils.isNotEmpty((String)this.periodType)) {
            char periodChar = this.periodType.charAt(0);
            srcPeriod.setType(PeriodConsts.codeToType((int)periodChar));
        } else {
            this.periodType = String.valueOf((char)PeriodConsts.typeToCode((int)srcPeriod.getType()));
        }
        if (this.periodOffset != 0) {
            int absPeriodOffset = Math.abs(this.periodOffset);
            if (absPeriodOffset < 10000) {
                PeriodModifier modifier;
                String offsetStr = this.periodOffset + this.periodType;
                if (this.periodOffset > 0) {
                    offsetStr = "+" + offsetStr;
                }
                if ((modifier = PeriodModifier.parse((String)offsetStr)) != null) {
                    String modifyPeriod = periodValue;
                    if (srcPeriod.getType() == PeriodType.YEAR.type() && PeriodType.YEAR == this.linkedSchemePeriodType) {
                        modifyPeriod = srcPeriod.toString();
                    }
                    String destPeriodStr = exeContext.getPeriodAdapter().modify(modifyPeriod, modifier);
                    srcPeriod = new PeriodWrapper(destPeriodStr);
                }
            } else if (absPeriodOffset > 10000 && absPeriodOffset < 100000000) {
                int yearOffset = this.periodOffset / 10000;
                int period = absPeriodOffset % 10000;
                srcPeriod.setYear(srcPeriod.getYear() + yearOffset);
                srcPeriod.setPeriod(period);
            } else {
                int type = absPeriodOffset / 100000000;
                String offsetStr = String.valueOf(absPeriodOffset);
                String periodStr = offsetStr.substring(offsetStr.length() - 4, offsetStr.length());
                if (type == 1) {
                    int period = Integer.parseInt(periodStr);
                    srcPeriod.setPeriod(period);
                } else if (type == 2) {
                    String yearStr = offsetStr.substring(1, 5);
                    int year = Integer.valueOf(yearStr);
                    int period = Integer.parseInt(periodStr);
                    srcPeriod.setYear(year);
                    srcPeriod.setPeriod(period);
                } else if (type == 3) {
                    String yearStr = offsetStr.substring(1, 5);
                    int year = Integer.valueOf(yearStr);
                    if (this.periodOffset < 0) {
                        periodStr = "-" + periodStr;
                    }
                    int periodOff = Integer.parseInt(periodStr);
                    srcPeriod.setYear(year);
                    srcPeriod.setPeriod(srcPeriod.getPeriod() + periodOff);
                }
            }
        }
        return srcPeriod;
    }

    public List<DimensionValueSet> getDimValueSetList(ExecutorContext exeContext, DimensionValueSet currValueSet) {
        ArrayList<DimensionValueSet> vsList = new ArrayList<DimensionValueSet>();
        String periodValue = (String)currValueSet.getValue("DATATIME");
        PeriodWrapper srcPeriod = this.getSrcPeriod(exeContext, periodValue);
        for (int i = 0; i < this.periodCount; ++i) {
            PeriodWrapper newPeriodWrapper = new PeriodWrapper(srcPeriod);
            if (i > 0) {
                exeContext.getPeriodAdapter().nextPeriod(newPeriodWrapper);
            }
            this.periodList.add(newPeriodWrapper.toString());
            srcPeriod = newPeriodWrapper;
        }
        for (String period : this.periodList) {
            String srcUnitDimesion;
            if (this.linkedAlias == null) {
                DimensionValueSet newValueSet = new DimensionValueSet(currValueSet);
                Object unitKey = newValueSet.getValue(exeContext.getUnitDimension());
                this.getSrcDestUnitKeyMap().put(unitKey.toString(), unitKey);
                newValueSet.setValue("DATATIME", (Object)period);
                vsList.add(newValueSet);
                continue;
            }
            IFmlExecEnvironment destEnv = exeContext.getEnv();
            IDataModelLinkFinder destDataLinkFinder = destEnv.getDataModelLinkFinder();
            String destUnitDimesion = destEnv.getUnitDimesion(exeContext);
            this.srcUnitDimension = srcUnitDimesion = destDataLinkFinder.getRelatedUnitDimName(exeContext, this.linkedAlias, destUnitDimesion);
            this.destUnitDimension = destUnitDimesion;
            Object destUnitKey = currValueSet.getValue(destUnitDimesion);
            List srcUnitKeys = destDataLinkFinder.findRelatedUnitKey(exeContext, this.linkedAlias, destUnitDimesion, destUnitKey);
            if (srcUnitKeys != null && srcUnitKeys.size() > 0) {
                for (Object srcUnitKey : srcUnitKeys) {
                    DimensionValueSet newValueSet = new DimensionValueSet(currValueSet);
                    newValueSet.setValue("DATATIME", (Object)period);
                    newValueSet.clearValue(destUnitDimesion);
                    newValueSet.setValue(srcUnitDimesion, srcUnitKey);
                    this.getSrcDestUnitKeyMap().put(srcUnitKey.toString(), destUnitKey);
                    vsList.add(newValueSet);
                }
                continue;
            }
            DimensionValueSet newValueSet = new DimensionValueSet(currValueSet);
            Object unitKey = newValueSet.getValue(exeContext.getUnitDimension());
            this.getSrcDestUnitKeyMap().put(unitKey.toString(), unitKey);
            newValueSet.setValue("DATATIME", (Object)period);
            vsList.add(newValueSet);
        }
        return vsList;
    }

    public DimensionValueSet createDimValueSet(DimensionValueSet currValueSet, PeriodWrapper newPeriod) {
        DimensionValueSet newValueSet = new DimensionValueSet(currValueSet);
        newValueSet.setValue("DATATIME", (Object)newPeriod.toString());
        return newValueSet;
    }

    public String getOperator(int i) {
        return this.operators.get(i);
    }

    public void addOperator(String oper) {
        this.operators.add(oper);
    }

    public String getLinkedAlias() {
        return this.linkedAlias;
    }

    public void setLinkedAlias(String linkedAlias) {
        this.linkedAlias = linkedAlias;
    }

    public HashMap<String, Object> getSrcDestUnitKeyMap() {
        if (this.srcDestUnitKeyMap == null) {
            this.srcDestUnitKeyMap = new HashMap();
        }
        return this.srcDestUnitKeyMap;
    }

    public ReportInfo getReportInfo() {
        return this.reportInfo;
    }

    public void setReportInfo(ReportInfo reportInfo) {
        this.reportInfo = reportInfo;
    }

    public String getSrcUnitDimension() {
        return this.srcUnitDimension;
    }

    public void setSrcUnitDimension(String srcUnitDimension) {
        this.srcUnitDimension = srcUnitDimension;
    }

    public String getDestUnitDimension() {
        return this.destUnitDimension;
    }

    public void setDestUnitDimension(String destUnitDimension) {
        this.destUnitDimension = destUnitDimension;
    }

    public String getSortingInfoList() {
        return this.sortingInfoList;
    }

    public void setSortingInfoList(String sortingInfoList) {
        this.sortingInfoList = sortingInfoList;
    }

    public boolean hasPeriodOffset() {
        return this.periodOffset != 0;
    }

    public PeriodType getLinkedSchemePeriodType() {
        return this.linkedSchemePeriodType;
    }

    public void setLinkedSchemePeriodType(PeriodType linkedSchemePeriodType) {
        this.linkedSchemePeriodType = linkedSchemePeriodType;
    }

    public String getPeriodType() {
        return this.periodType;
    }

    public int getPeriodOffset() {
        return this.periodOffset;
    }

    public int getPeriodCount() {
        return this.periodCount;
    }

    public List<String> getOperators() {
        return this.operators;
    }

    public List<String> getPeriodList() {
        return this.periodList;
    }
}

