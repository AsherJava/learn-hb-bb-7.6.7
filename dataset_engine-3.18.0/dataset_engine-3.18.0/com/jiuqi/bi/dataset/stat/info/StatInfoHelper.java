/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.Column
 *  com.jiuqi.bi.dataset.Metadata
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.bi.dataset.stat.info;

import com.jiuqi.bi.dataset.AggrMeasureItem;
import com.jiuqi.bi.dataset.BIDataSetFieldInfo;
import com.jiuqi.bi.dataset.BIDataSetImpl;
import com.jiuqi.bi.dataset.Column;
import com.jiuqi.bi.dataset.Metadata;
import com.jiuqi.bi.dataset.model.field.FieldType;
import com.jiuqi.bi.dataset.model.field.TimeGranularity;
import com.jiuqi.bi.dataset.model.hierarchy.DSHierarchy;
import com.jiuqi.bi.dataset.model.hierarchy.DSHierarchyType;
import com.jiuqi.bi.dataset.stat.StatConfig;
import com.jiuqi.bi.dataset.stat.info.StatInfo;
import com.jiuqi.bi.util.StringUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class StatInfoHelper {
    private BIDataSetImpl dataset;
    private StatConfig statConfig;
    private int sys_timekey_index;
    private List<Integer> parentColIdx;
    private List<Integer> parentKeyColIdx;
    private List<Integer> destDimColIdx;
    private List<Integer> destKeyColIdx;
    private List<Integer> srcKeyColIdx;
    private List<Integer> destMsColIdx;
    private Map<Integer, Integer> dest2srcMap;
    private boolean isTimeWasAggr;

    public StatInfoHelper(BIDataSetImpl dataset, StatConfig statConfig) {
        this.dataset = dataset;
        this.statConfig = statConfig;
    }

    public StatInfo createStatInfo(List<Integer> reserveDimColList, List<AggrMeasureItem> reserveMsList) {
        StatInfo info = new StatInfo();
        this.sys_timekey_index = this.dataset.getMetadata().indexOf("SYS_TIMEKEY");
        this.dest2srcMap = new HashMap<Integer, Integer>();
        info.sys_timekey_index = this.sys_timekey_index;
        info.metadata = this.createMetadata(reserveDimColList, reserveMsList);
        info.srcKeyColIdx = this.srcKeyColIdx;
        info.destDimColIdx = this.destDimColIdx;
        info.destKeyColIdx = this.destKeyColIdx;
        info.destMsColIdx = this.destMsColIdx;
        info.isTimeWasAggr = this.isTimeWasAggr;
        info.dest2srcColMap = this.dest2srcMap;
        this.initParentColIdx(new HashSet<Integer>(this.dest2srcMap.values()));
        info.parentColIdx = this.parentColIdx;
        info.parentKeyColIdx = this.parentKeyColIdx;
        info.reserveParentColIdx = this.getParentColList(info.metadata);
        return info;
    }

    private List<Integer> getParentColList(Metadata<BIDataSetFieldInfo> metadata) {
        ArrayList<Integer> parentColList = new ArrayList<Integer>();
        Map props = metadata.getProperties();
        List hiers = (List)props.get("HIERARCHY");
        if (hiers != null) {
            for (DSHierarchy hier : hiers) {
                if (hier.getType() != DSHierarchyType.PARENT_HIERARCHY) continue;
                int idx = metadata.indexOf(hier.getParentFieldName());
                parentColList.add(idx);
            }
        }
        return parentColList;
    }

    private void initParentColIdx(Set<Integer> reserveDimColList) {
        List<DSHierarchy> hiers = this.getHierarchy();
        if (hiers == null || hiers.size() == 0) {
            return;
        }
        this.parentKeyColIdx = new ArrayList<Integer>();
        this.parentColIdx = new ArrayList<Integer>();
        for (DSHierarchy hier : hiers) {
            DSHierarchyType type = hier.getType();
            if (type != DSHierarchyType.PARENT_HIERARCHY) continue;
            Metadata<BIDataSetFieldInfo> metadata = this.dataset.getMetadata();
            Integer v1 = metadata.indexOf(hier.getLevels().get(0));
            Integer v2 = metadata.indexOf(hier.getParentFieldName());
            if (reserveDimColList.contains(v1) || reserveDimColList.contains(v2)) continue;
            this.parentKeyColIdx.add(v1);
            this.parentColIdx.add(v2);
        }
    }

    private Metadata<BIDataSetFieldInfo> createMetadata(List<Integer> reserveDimColList, List<AggrMeasureItem> reserveMsList) {
        Object v;
        BIDataSetFieldInfo info;
        Column<BIDataSetFieldInfo> newColumn;
        Column column;
        this.destDimColIdx = new ArrayList<Integer>();
        this.srcKeyColIdx = new ArrayList<Integer>();
        this.destKeyColIdx = new ArrayList<Integer>();
        this.destMsColIdx = new ArrayList<Integer>();
        ArrayList<Integer> showDimColList = new ArrayList<Integer>();
        this.preprocess(reserveDimColList, showDimColList);
        Metadata<BIDataSetFieldInfo> metadata = this.dataset.getMetadata();
        Metadata newMetadata = new Metadata(null);
        int colIdx = 0;
        boolean containTimekey = false;
        boolean containSystimekey = false;
        for (Integer dimCol : showDimColList) {
            column = metadata.getColumn(dimCol.intValue());
            newColumn = this.createNewDimColumn((Column<BIDataSetFieldInfo>)column, showDimColList);
            info = (BIDataSetFieldInfo)newColumn.getInfo();
            newMetadata.getColumns().add(newColumn);
            this.destDimColIdx.add(colIdx);
            this.dest2srcMap.put(colIdx, dimCol);
            if (dimCol == this.sys_timekey_index) {
                containSystimekey = true;
            } else if (((BIDataSetFieldInfo)column.getInfo()).isTimekey()) {
                containTimekey = true;
            }
            if (info.getName().equalsIgnoreCase(info.getKeyField())) {
                this.srcKeyColIdx.add(dimCol);
                this.destKeyColIdx.add(colIdx);
            }
            ++colIdx;
        }
        if (containTimekey && containSystimekey) {
            this.srcKeyColIdx.remove((Object)this.sys_timekey_index);
        }
        for (AggrMeasureItem ms : reserveMsList) {
            column = metadata.find(ms.getFieldName());
            newColumn = this.createNewMsColumn((Column<BIDataSetFieldInfo>)column, ms);
            info = (BIDataSetFieldInfo)newColumn.getInfo();
            if (info.hasRefDim()) {
                ArrayList<String> list = new ArrayList<String>(info.getRefDimCols());
                for (String s : list) {
                    if (newMetadata.contains(s)) continue;
                    info.getRefDimCols().remove(s);
                }
            }
            newMetadata.getColumns().add(newColumn);
            this.destMsColIdx.add(colIdx);
            this.dest2srcMap.put(colIdx, column.getIndex());
            ++colIdx;
        }
        List<DSHierarchy> hiers = this.getHierarchy();
        if (hiers != null) {
            List<DSHierarchy> newHiers = this.createNewHierarchy(hiers, showDimColList);
            newMetadata.getProperties().put("HIERARCHY", newHiers);
        }
        if ((v = metadata.getProperties().get("FiscalMonth")) != null) {
            newMetadata.getProperties().put("FiscalMonth", v);
        }
        return newMetadata;
    }

    private Column<BIDataSetFieldInfo> createNewDimColumn(Column<BIDataSetFieldInfo> column, List<Integer> showDimColList) {
        BIDataSetFieldInfo info = ((BIDataSetFieldInfo)column.getInfo()).clone();
        Column newColumn = column.clone();
        newColumn.setInfo((Object)info);
        if (StringUtils.isNotEmpty((String)info.getKeyField()) && info.getName().equalsIgnoreCase(info.getKeyField())) {
            String nameField = info.getNameField();
            int nameCol = this.dataset.getMetadata().indexOf(nameField);
            if (!showDimColList.contains(nameCol)) {
                ((BIDataSetFieldInfo)newColumn.getInfo()).setNameField(info.getKeyField());
            }
        }
        return newColumn;
    }

    private Column<BIDataSetFieldInfo> createNewMsColumn(Column<BIDataSetFieldInfo> column, AggrMeasureItem ms) {
        String name = ms.getAlias();
        if (StringUtils.isEmpty((String)name)) {
            name = ms.getFieldName();
        }
        BIDataSetFieldInfo info = ((BIDataSetFieldInfo)column.getInfo()).clone();
        info.setName(name);
        info.setTitle(ms.getTitle());
        info.setFieldType(FieldType.MEASURE);
        info.setAggregation(ms.getAggrType());
        Column newColumn = new Column(name, column.getDataType(), (Object)info);
        newColumn.setTitle(ms.getTitle());
        return newColumn;
    }

    private List<DSHierarchy> createNewHierarchy(List<DSHierarchy> hiers, List<Integer> showDimColList) {
        ArrayList<DSHierarchy> newHiers = new ArrayList<DSHierarchy>();
        for (DSHierarchy hier : hiers) {
            int col = this.dataset.getMetadata().indexOf(hier.getLevels().get(0));
            if (!showDimColList.contains(col)) continue;
            newHiers.add(hier.clone());
        }
        return newHiers;
    }

    private void preprocess(List<Integer> reserveDimColList, List<Integer> showDimColList) {
        Metadata<BIDataSetFieldInfo> metadata = this.dataset.getMetadata();
        HashSet<Integer> dimColSet = new HashSet<Integer>();
        for (Integer n : reserveDimColList) {
            int keyCol;
            BIDataSetFieldInfo info = (BIDataSetFieldInfo)metadata.getColumn(n.intValue()).getInfo();
            if (dimColSet.add(n)) {
                showDimColList.add(n);
            }
            if (!StringUtils.isNotEmpty((String)info.getKeyField()) || (keyCol = metadata.indexOf(info.getKeyField())) == -1 || keyCol == n || !dimColSet.add(keyCol)) continue;
            showDimColList.add(keyCol);
        }
        List<DSHierarchy> hiers = this.getHierarchy();
        if (this.statConfig.isAutoFillParentColumn && hiers != null) {
            for (DSHierarchy hier : hiers) {
                if (hier.getType() != DSHierarchyType.PARENT_HIERARCHY) continue;
                Integer v1 = metadata.indexOf(hier.getLevels().get(0));
                Integer v2 = metadata.indexOf(hier.getParentFieldName());
                if (!dimColSet.contains(v1) || dimColSet.contains(v2) || v2 == -1) continue;
                dimColSet.add(v2);
                showDimColList.add(v2);
            }
        }
        if (this.sys_timekey_index != -1) {
            Column column = metadata.getColumn(this.sys_timekey_index);
            Integer col = column.getIndex();
            this.isTimeWasAggr = this.judgeTimeWasAggr(reserveDimColList);
            if (!this.isTimeWasAggr && !dimColSet.contains(col)) {
                showDimColList.add(col);
            }
        } else {
            this.isTimeWasAggr = false;
        }
    }

    private boolean judgeTimeWasAggr(List<Integer> reserveDimColList) {
        Metadata<BIDataSetFieldInfo> metadata = this.dataset.getMetadata();
        HashSet<TimeGranularity> timeGys = new HashSet<TimeGranularity>();
        for (Integer col : reserveDimColList) {
            BIDataSetFieldInfo info = (BIDataSetFieldInfo)metadata.getColumn(col.intValue()).getInfo();
            if (info.isTimekey()) {
                return false;
            }
            if (!info.getName().equals("SYS_TIMEKEY") && (info.getFieldType() == null || !info.getFieldType().isTimeDimField())) continue;
            TimeGranularity tg = info.getTimegranularity();
            timeGys.add(tg);
        }
        if (timeGys.size() == 0) {
            return true;
        }
        Column sys_tgCol = metadata.getColumn(this.sys_timekey_index);
        TimeGranularity sysTg = ((BIDataSetFieldInfo)sys_tgCol.getInfo()).getTimegranularity();
        if (!timeGys.contains((Object)sysTg)) {
            return true;
        }
        switch (sysTg) {
            case YEAR: 
            case HALFYEAR: {
                return false;
            }
            case QUARTER: 
            case MONTH: {
                return !timeGys.contains((Object)TimeGranularity.YEAR);
            }
            case XUN: 
            case DAY: {
                return !timeGys.contains((Object)TimeGranularity.YEAR) || !timeGys.contains((Object)TimeGranularity.MONTH);
            }
            case WEEK: {
                return !timeGys.contains((Object)TimeGranularity.YEAR);
            }
        }
        return true;
    }

    private List<DSHierarchy> getHierarchy() {
        Metadata<BIDataSetFieldInfo> metadata = this.dataset.getMetadata();
        return (List)metadata.getProperties().get("HIERARCHY");
    }
}

