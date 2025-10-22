/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnoreProperties
 *  com.fasterxml.jackson.databind.DeserializationFeature
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.nr.common.summary.SummaryScheme
 */
package com.jiuqi.nr.query.block;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.common.summary.SummaryScheme;
import com.jiuqi.nr.query.common.GridBlockType;
import com.jiuqi.nr.query.common.LevelNodeView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@JsonIgnoreProperties(ignoreUnknown=true)
public final class QueryGridExtension {
    private static final Logger logger = LoggerFactory.getLogger(QueryGridExtension.class);
    private GridBlockType gridBlockType = GridBlockType.DETAILED;
    private int rowHeight = 28;
    private boolean alternateColor = true;
    private boolean showGridHead = false;
    private boolean transposition = false;
    private boolean showDetail = true;
    private boolean showGather = true;
    private String sumScheme = null;
    private String measureMap = null;
    private String formKey = null;
    private String formSchemeId = null;
    private String decimal = null;
    private String penetrationType = null;
    private LevelNodeView levelNodeView = LevelNodeView.COLLAPSABLECELL;

    public QueryGridExtension() {
        this.gridBlockType = GridBlockType.DETAILED;
        this.rowHeight = 28;
        this.alternateColor = true;
        this.showGridHead = false;
        this.transposition = false;
        this.showDetail = true;
        this.showGather = true;
        this.levelNodeView = LevelNodeView.COLLAPSABLECELL;
    }

    public QueryGridExtension(String queryGridPropertyStr) {
        if (StringUtils.isEmpty((String)queryGridPropertyStr)) {
            this.gridBlockType = GridBlockType.DETAILED;
            this.rowHeight = 28;
            this.alternateColor = true;
            this.showGridHead = false;
            this.transposition = false;
            this.showDetail = true;
            this.showGather = true;
            this.levelNodeView = LevelNodeView.COLLAPSABLECELL;
            return;
        }
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            QueryGridExtension item = (QueryGridExtension)objectMapper.readValue(queryGridPropertyStr, QueryGridExtension.class);
            this.gridBlockType = item.getGridBlockType();
            this.rowHeight = item.getRowHeight();
            this.alternateColor = item.isAlternateColor();
            this.showGridHead = item.isShowGridHead();
            this.transposition = item.isTransposition();
            this.showDetail = item.isShowDetail();
            this.showGather = item.isShowGather();
            this.levelNodeView = item.getLevelNodeView();
            this.sumScheme = item.getSumScheme();
            this.penetrationType = item.getPenetrationType();
            this.measureMap = item.getMeasureMap();
            this.formKey = item.getFormKey();
            this.formSchemeId = item.getFormSchemeId();
            this.decimal = item.getDecimal();
        }
        catch (Exception e) {
            logger.error("\u65b0\u5efa\u67e5\u8be2\u6a21\u5757\u6269\u5c55\u4fe1\u606f\u51fa\u9519(QueryGridExtension.java 85):" + e.getMessage());
            this.gridBlockType = GridBlockType.DETAILED;
            this.rowHeight = 28;
            this.alternateColor = true;
            this.showGridHead = false;
            this.transposition = false;
            this.showDetail = true;
            this.showGather = true;
            this.levelNodeView = LevelNodeView.COLLAPSABLECELL;
        }
    }

    public String getPenetrationType() {
        return this.penetrationType;
    }

    public void setPenetrationType(String penetrationType) {
        this.penetrationType = penetrationType;
    }

    public String getSumScheme() {
        return this.sumScheme;
    }

    public void setSumScheme(String sumScheme) {
        this.sumScheme = sumScheme;
    }

    public GridBlockType getGridBlockType() {
        return this.gridBlockType;
    }

    public void setGridBlockType(GridBlockType gridBlockType) {
        this.gridBlockType = gridBlockType;
    }

    public int getRowHeight() {
        return this.rowHeight;
    }

    public void setRowHeight(int rowHeight) {
        this.rowHeight = rowHeight;
    }

    public boolean isAlternateColor() {
        return this.alternateColor;
    }

    public void setAlternateColor(boolean alternateColor) {
        this.alternateColor = alternateColor;
    }

    public boolean isShowGridHead() {
        return this.showGridHead;
    }

    public void setShowGridHead(boolean showGridHead) {
        this.showGridHead = showGridHead;
    }

    public boolean isTransposition() {
        return this.transposition;
    }

    public void setTransposition(boolean transPosition) {
        this.transposition = transPosition;
    }

    public boolean isShowDetail() {
        return this.showDetail;
    }

    public void setShowDetail(boolean showDetail) {
        this.showDetail = showDetail;
    }

    public boolean isShowGather() {
        return this.showGather;
    }

    public void setShowGather(boolean showGather) {
        this.showGather = showGather;
    }

    public LevelNodeView getLevelNodeView() {
        return this.levelNodeView;
    }

    public void setLevelNodeView(LevelNodeView levelNodeView) {
        this.levelNodeView = levelNodeView;
    }

    public SummaryScheme getSumSchemeObject() {
        if (this.sumScheme == null) {
            return null;
        }
        SummaryScheme summaryScheme = null;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            summaryScheme = (SummaryScheme)objectMapper.readValue(this.sumScheme, SummaryScheme.class);
        }
        catch (Exception e) {
            logger.error("SummaryScheme parse failed form json!");
        }
        return summaryScheme;
    }

    public String getMeasureMap() {
        return this.measureMap;
    }

    public void setMeasureMap(String measureMap) {
        this.measureMap = measureMap;
    }

    public String getFormKey() {
        return this.formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    public String getFormSchemeId() {
        return this.formSchemeId;
    }

    public void setFormSchemeId(String formSchemeId) {
        this.formSchemeId = formSchemeId;
    }

    public String getDecimal() {
        return this.decimal;
    }

    public void setDecimal(String decimal) {
        this.decimal = decimal;
    }
}

