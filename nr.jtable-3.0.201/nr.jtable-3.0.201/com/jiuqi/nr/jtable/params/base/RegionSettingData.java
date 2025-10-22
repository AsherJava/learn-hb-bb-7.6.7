/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.facade.RegionEdgeStyleDefine
 *  com.jiuqi.nr.definition.facade.RegionSettingDefine
 *  com.jiuqi.nr.definition.facade.RegionTabSettingDefine
 *  com.jiuqi.nr.definition.facade.RowNumberSetting
 *  com.jiuqi.nr.definition.util.EntityDefaultValue
 *  com.jiuqi.nr.definition.util.RecordCard
 */
package com.jiuqi.nr.jtable.params.base;

import com.jiuqi.nr.definition.facade.RegionEdgeStyleDefine;
import com.jiuqi.nr.definition.facade.RegionSettingDefine;
import com.jiuqi.nr.definition.facade.RegionTabSettingDefine;
import com.jiuqi.nr.definition.facade.RowNumberSetting;
import com.jiuqi.nr.definition.util.EntityDefaultValue;
import com.jiuqi.nr.definition.util.RecordCard;
import com.jiuqi.nr.jtable.params.base.RecordCardData;
import com.jiuqi.nr.jtable.params.base.RegionTab;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RegionSettingData {
    private String key;
    private String title;
    private String order;
    private String version;
    private String ownerLevelAndId;
    private Date updateTime;
    private String collapseCellIndex;
    private String dictionaryFillLinks;
    private String defaultRowSetting;
    private List<RegionTab> regionTabSetting;
    private List<RegionEdgeStyleDefine> lastRowStyles;
    private List<RegionEdgeStyleDefine> lastColumnStyle;
    private List<RowNumberSetting> rowNumberSetting;
    private List<EntityDefaultValue> regionEntityDefaultValue;
    private RecordCardData cardRecord;

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
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

    public String getVersion() {
        return this.version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getOwnerLevelAndId() {
        return this.ownerLevelAndId;
    }

    public void setOwnerLevelAndId(String ownerLevelAndId) {
        this.ownerLevelAndId = ownerLevelAndId;
    }

    public Date getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getCollapseCellIndex() {
        return this.collapseCellIndex;
    }

    public void setCollapseCellIndex(String collapseCellIndex) {
        this.collapseCellIndex = collapseCellIndex;
    }

    public String getDictionaryFillLinks() {
        return this.dictionaryFillLinks;
    }

    public void setDictionaryFillLinks(String dictionaryFillLinks) {
        this.dictionaryFillLinks = dictionaryFillLinks;
    }

    public String getDefaultRowSetting() {
        return this.defaultRowSetting;
    }

    public void setDefaultRowSetting(String defaultRowSetting) {
        this.defaultRowSetting = defaultRowSetting;
    }

    public List<RegionTab> getRegionTabSetting() {
        return this.regionTabSetting;
    }

    public List<RegionEdgeStyleDefine> getLastRowStyles() {
        return this.lastRowStyles;
    }

    public List<RegionEdgeStyleDefine> getLastColumnStyle() {
        return this.lastColumnStyle;
    }

    public List<RowNumberSetting> getRowNumberSetting() {
        return this.rowNumberSetting;
    }

    public List<EntityDefaultValue> getRegionEntityDefaultValue() {
        return this.regionEntityDefaultValue;
    }

    public void setRegionEntityDefaultValue(List<EntityDefaultValue> regionEntityDefaultValue) {
        this.regionEntityDefaultValue = regionEntityDefaultValue;
    }

    public void setRegionTabSetting(List<RegionTab> regionTabSetting) {
        this.regionTabSetting = regionTabSetting;
    }

    public void setLastRowStyles(List<RegionEdgeStyleDefine> lastRowStyles) {
        this.lastRowStyles = lastRowStyles;
    }

    public void setLastColumnStyle(List<RegionEdgeStyleDefine> lastColumnStyle) {
        this.lastColumnStyle = lastColumnStyle;
    }

    public void setRowNumberSetting(List<RowNumberSetting> rowNumberSetting) {
        this.rowNumberSetting = rowNumberSetting;
    }

    public RecordCardData getCardRecord() {
        return this.cardRecord;
    }

    public void setCardRecord(RecordCardData cardRecord) {
        this.cardRecord = cardRecord;
    }

    public void init(RegionSettingDefine regionSettingDefine) {
        this.collapseCellIndex = regionSettingDefine.getCollapseCellIndex();
        this.defaultRowSetting = regionSettingDefine.getDefaultRowSetting();
        this.dictionaryFillLinks = regionSettingDefine.getDictionaryFillLinks();
        this.key = regionSettingDefine.getKey();
        this.order = regionSettingDefine.getOrder();
        this.ownerLevelAndId = regionSettingDefine.getOwnerLevelAndId();
        this.title = regionSettingDefine.getTitle();
        this.updateTime = regionSettingDefine.getUpdateTime();
        this.version = regionSettingDefine.getVersion();
        List regintabsets = regionSettingDefine.getRegionTabSetting();
        if (null != regintabsets && regintabsets.size() > 0) {
            ArrayList<RegionTab> tabs = new ArrayList<RegionTab>();
            for (RegionTabSettingDefine regionTabSettingDefine : regintabsets) {
                RegionTab tab = new RegionTab(regionTabSettingDefine);
                tabs.add(tab);
            }
        } else {
            this.regionTabSetting = new ArrayList<RegionTab>();
        }
        this.lastRowStyles = regionSettingDefine.getLastRowStyles();
        this.lastColumnStyle = regionSettingDefine.getLastColumnStyle();
        this.rowNumberSetting = regionSettingDefine.getRowNumberSetting();
        this.regionEntityDefaultValue = regionSettingDefine.getEntityDefaultValue();
        this.initRecord(regionSettingDefine);
    }

    private void initRecord(RegionSettingDefine regionSettingDefine) {
        String surveyCard = regionSettingDefine.getRegionSurvey();
        if (surveyCard != null) {
            RecordCardData recordCardData;
            this.cardRecord = recordCardData = new RecordCardData(surveyCard);
        } else {
            RecordCard regionCard = regionSettingDefine.getCardRecord();
            if (regionCard != null) {
                this.cardRecord = new RecordCardData(regionCard);
            }
        }
    }
}

