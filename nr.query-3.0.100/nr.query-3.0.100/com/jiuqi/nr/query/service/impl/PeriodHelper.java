/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.period.DefaultPeriodAdapter
 *  com.jiuqi.np.period.IPeriodAdapter
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.np.period.PeriodUtil
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  io.netty.util.internal.StringUtil
 */
package com.jiuqi.nr.query.service.impl;

import com.jiuqi.np.period.DefaultPeriodAdapter;
import com.jiuqi.np.period.IPeriodAdapter;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.np.period.PeriodUtil;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.query.block.QueryBlockDefine;
import com.jiuqi.nr.query.block.QueryDimensionDefine;
import com.jiuqi.nr.query.block.QuerySelectItem;
import com.jiuqi.nr.query.common.QueryLayoutType;
import com.jiuqi.nr.query.service.impl.QueryHelper;
import io.netty.util.internal.StringUtil;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class PeriodHelper {
    private QueryBlockDefine block;
    List<String> periodCodes;
    Map<String, String> periodTitles;
    Map<String, String> conditionPeriods;
    Map<String, Map<String, String>> customPeriods = new LinkedHashMap<String, Map<String, String>>();
    List<QueryDimensionDefine> periodDims = new ArrayList<QueryDimensionDefine>();
    QueryDimensionDefine conditionPeriodDim;
    PeriodType ptype;
    IPeriodAdapter periodApapter;
    List<String> rowPeriods = new ArrayList<String>();
    List<String> colPeriods = new ArrayList<String>();
    List<String> condiPeriods = new ArrayList<String>();

    public PeriodHelper(QueryBlockDefine block) {
        Map<String, String> periods;
        this.block = block;
        this.ptype = PeriodType.valueOf((String)block.getPeriodTypeInCache());
        List<QueryDimensionDefine> dims = block.getQueryDimensions();
        this.periodApapter = new DefaultPeriodAdapter();
        for (QueryDimensionDefine dim : dims) {
            if (!dim.isPeriodDim()) continue;
            if (dim.getLayoutType() != QueryLayoutType.LYT_CONDITION) {
                this.conditionPeriodDim = dim;
            } else {
                this.periodDims.add(dim);
            }
            this.initPeriods(dim);
        }
        if (!(this.condiPeriods != null && this.condiPeriods.size() != 0 || block.getTaskDefStartPeriod() == null || "".equals(block.getTaskDefStartPeriod()) || (periods = this.getPeriods(block.getTaskDefStartPeriod(), block.getTaskDefEndPeriod(), this.ptype)) == null)) {
            this.condiPeriods.addAll(periods.keySet());
        }
    }

    public List<String> getPeriodsForQuery() {
        if (this.rowPeriods.size() == 0 && this.colPeriods.size() == 0) {
            return this.condiPeriods;
        }
        ArrayList<String> temp = new ArrayList<String>();
        for (String period : this.condiPeriods) {
            if (!this.rowPeriods.contains(period) && !this.colPeriods.contains(period)) continue;
            temp.add(period);
        }
        return temp;
    }

    public List<String> getPeriodsForCondition() {
        return null;
    }

    private Map<String, String> getPeriods(String startTime, String endTime, PeriodType periodType) {
        if (StringUtil.isNullOrEmpty((String)startTime) || StringUtil.isNullOrEmpty((String)endTime)) {
            return null;
        }
        LinkedHashMap<String, String> dateList = new LinkedHashMap<String, String>();
        ArrayList list = PeriodUtil.getPeriodCodes((String)startTime, (String)endTime, (PeriodType)periodType);
        for (int l = 0; l < list.size(); ++l) {
            String code = (String)list.get(l);
            dateList.put(code, this.periodApapter.getPeriodTitle(code));
        }
        return dateList;
    }

    public Map<String, String> getMaxAllPeriods(PeriodType ptype) {
        Calendar dataTime = Calendar.getInstance();
        LinkedHashMap<String, String> dateList = new LinkedHashMap<String, String>();
        Map<Object, Object> temp = new LinkedHashMap();
        int year = dataTime.get(1);
        String startTime = year + "-01-01";
        String endTime = year + "-12-31";
        String s1 = year - 11 + "-01-01";
        temp = this.getPeriods(s1, endTime, PeriodType.YEAR);
        dateList.putAll(temp);
        switch (ptype) {
            case YEAR: {
                temp = this.getPeriods(s1, endTime, PeriodType.YEAR);
                break;
            }
            case HALFYEAR: {
                temp = this.getPeriods(s1, endTime, PeriodType.YEAR);
                dateList.putAll(temp);
                temp = this.getPeriods(startTime, endTime, PeriodType.HALFYEAR);
                dateList.putAll(temp);
                break;
            }
            case SEASON: {
                temp = this.getPeriods(s1, endTime, PeriodType.YEAR);
                dateList.putAll(temp);
                temp = this.getPeriods(startTime, endTime, PeriodType.HALFYEAR);
                dateList.putAll(temp);
                temp = this.getPeriods(startTime, endTime, PeriodType.SEASON);
                dateList.putAll(temp);
                break;
            }
            case MONTH: {
                temp = this.getPeriods(s1, endTime, PeriodType.YEAR);
                dateList.putAll(temp);
                temp = this.getPeriods(startTime, endTime, PeriodType.HALFYEAR);
                dateList.putAll(temp);
                temp = this.getPeriods(startTime, endTime, PeriodType.SEASON);
                dateList.putAll(temp);
                temp = this.getPeriods(startTime, endTime, PeriodType.MONTH);
                dateList.putAll(temp);
                break;
            }
            case TENDAY: {
                temp = this.getPeriods(s1, endTime, PeriodType.YEAR);
                dateList.putAll(temp);
                temp = this.getPeriods(startTime, endTime, PeriodType.HALFYEAR);
                dateList.putAll(temp);
                temp = this.getPeriods(startTime, endTime, PeriodType.SEASON);
                dateList.putAll(temp);
                temp = this.getPeriods(startTime, endTime, PeriodType.MONTH);
                dateList.putAll(temp);
                temp = this.getPeriods(startTime, endTime, PeriodType.TENDAY);
                dateList.putAll(temp);
                break;
            }
            case WEEK: {
                temp = this.getPeriods(s1, endTime, PeriodType.YEAR);
                dateList.putAll(temp);
                temp = this.getPeriods(startTime, endTime, PeriodType.HALFYEAR);
                dateList.putAll(temp);
                temp = this.getPeriods(startTime, endTime, PeriodType.SEASON);
                dateList.putAll(temp);
                temp = this.getPeriods(startTime, endTime, PeriodType.MONTH);
                dateList.putAll(temp);
                temp = this.getPeriods(startTime, endTime, PeriodType.TENDAY);
                dateList.putAll(temp);
                temp = this.getPeriods(startTime, endTime, PeriodType.WEEK);
                dateList.putAll(temp);
                break;
            }
            case DAY: {
                temp = this.getPeriods(s1, endTime, PeriodType.YEAR);
                dateList.putAll(temp);
                temp = this.getPeriods(startTime, endTime, PeriodType.HALFYEAR);
                dateList.putAll(temp);
                temp = this.getPeriods(startTime, endTime, PeriodType.SEASON);
                dateList.putAll(temp);
                temp = this.getPeriods(startTime, endTime, PeriodType.MONTH);
                dateList.putAll(temp);
                temp = this.getPeriods(startTime, endTime, PeriodType.TENDAY);
                dateList.putAll(temp);
                temp = this.getPeriods(startTime, endTime, PeriodType.WEEK);
                dateList.putAll(temp);
                temp = this.getPeriods(startTime, endTime, PeriodType.DAY);
                dateList.putAll(temp);
            }
        }
        return dateList;
    }

    private void initPeriods(QueryDimensionDefine dim) {
        switch (dim.getLayoutType()) {
            case LYT_CONDITION: {
                this.condiPeriods = this.getPeriods(dim);
                break;
            }
            case LYT_ROW: {
                if (dim.getSelectItems() == null || dim.getSelectItems().size() <= 0) break;
                this.rowPeriods = this.getPeriods(dim);
                break;
            }
            case LYT_COL: {
                if (dim.getSelectItems() == null || dim.getSelectItems().size() <= 0) break;
                this.rowPeriods = this.getPeriods(dim);
            }
        }
    }

    private List<String> getPeriods(QueryDimensionDefine dim) {
        ArrayList periods = new ArrayList();
        List<QuerySelectItem> items = dim.getSelectItems();
        block0 : switch (dim.getSelectType()) {
            case MULTIITES: 
            case SINGLE: {
                for (QuerySelectItem seItem : items) {
                    periods.add((String)seItem.getCode());
                }
                break;
            }
            case RANGE: 
            case NONE: {
                if (this.block.getPeriodTypeInCache() == null) {
                    Map<String, String> allPeriods = this.getMaxAllPeriods(this.ptype);
                    periods.addAll(allPeriods.keySet());
                    break;
                }
                String startTime = dim.getSelectItems().get(0).getCode();
                String endTime = dim.getSelectItems().get(1).getCode();
                if (this.ptype == PeriodType.CUSTOM) {
                    boolean start = false;
                    if (this.customPeriods.containsKey(dim.getViewId())) {
                        for (String period : this.customPeriods.get(dim.getViewId()).keySet()) {
                            if (period == endTime) {
                                periods.add(period);
                                start = false;
                                break block0;
                            }
                            if (period != startTime && !start) continue;
                            start = true;
                            periods.add(period);
                        }
                        break;
                    }
                    LinkedHashMap<String, String> cusPeriodsTemp = new LinkedHashMap<String, String>();
                    IEntityTable periodTable = QueryHelper.getEntityTable(dim.getViewId());
                    for (IEntityRow row : periodTable.getAllRows()) {
                        if (row.getEntityKeyData() == endTime) {
                            periods.add(row.getEntityKeyData());
                            start = false;
                        } else if (row.getEntityKeyData() == startTime || start) {
                            start = true;
                            periods.add(row.getEntityKeyData());
                        }
                        cusPeriodsTemp.put(row.getEntityKeyData(), row.getTitle());
                    }
                    this.customPeriods.put(dim.getViewId(), cusPeriodsTemp);
                    break;
                }
                periods = PeriodUtil.getPeriodCodes((String)startTime, (String)endTime, (PeriodType)this.ptype);
                break;
            }
        }
        return periods;
    }

    public static int maxperiodType(int a, int b) {
        if (a == 8 || b == 8) {
            return 8;
        }
        if ((a == 7 || b == 7) && b + a == 15) {
            return 7;
        }
        return Math.min(a, b);
    }

    public static int strToPeriod(String periodStr) {
        char c = periodStr.charAt(4);
        String typeString = String.valueOf(c);
        int strType = 6;
        switch (typeString) {
            case "N": {
                strType = 1;
                break;
            }
            case "H": {
                strType = 2;
                break;
            }
            case "J": {
                strType = 3;
                break;
            }
            case "Y": {
                strType = 4;
                break;
            }
            case "X": {
                strType = 5;
                break;
            }
            case "R": {
                strType = 6;
                break;
            }
            case "Z": {
                strType = 7;
                break;
            }
            case "B": {
                strType = 8;
            }
        }
        return strType;
    }
}

