/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.internal.anno.DBAnno$DBField
 *  com.jiuqi.np.definition.internal.anno.DBAnno$DBTable
 *  com.jiuqi.nr.period.util.JacksonUtils
 */
package com.jiuqi.nr.definition.internal.impl;

import com.jiuqi.np.definition.internal.anno.DBAnno;
import com.jiuqi.nr.definition.facade.RegionEdgeStyleDefine;
import com.jiuqi.nr.definition.facade.RegionSettingDefine;
import com.jiuqi.nr.definition.facade.RegionTabSettingDefine;
import com.jiuqi.nr.definition.facade.RowNumberSetting;
import com.jiuqi.nr.definition.util.EntityDefaultValue;
import com.jiuqi.nr.definition.util.RecordCard;
import com.jiuqi.nr.period.util.JacksonUtils;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@DBAnno.DBTable(dbTable="NR_PARAM_REGIONSETTING")
public class RunTimeRegionSettingDefineImpl
implements RegionSettingDefine {
    private static final long serialVersionUID = 5987256507585304899L;
    @DBAnno.DBField(dbField="rs_key", isPk=true)
    private String key;
    @DBAnno.DBField(dbField="rs_title")
    private String title;
    @DBAnno.DBField(dbField="rs_order")
    private String order;
    @DBAnno.DBField(dbField="rs_version")
    private String version;
    @DBAnno.DBField(dbField="rs_level")
    private String ownerLevelAndId;
    @DBAnno.DBField(dbField="rs_updatetime", tranWith="transTimeStamp", dbType=Timestamp.class, appType=Date.class, autoDate=true)
    private Date updateTime;
    @DBAnno.DBField(dbField="rs_collapse_index")
    private String collapseCellIndex;
    @DBAnno.DBField(dbField="rs_dict_fill_Links")
    private String dictionaryFillLinks;
    @DBAnno.DBField(dbField="rs_default_row")
    private String defaultRowSetting;
    @DBAnno.DBField(dbField="rs_entity_value")
    private String entityDefaultValue;
    private RecordCard cardRecord;
    private List<RegionTabSettingDefine> regionTabSettings;
    private List<RowNumberSetting> rowNumberSettings;
    private List<RegionEdgeStyleDefine> rowStyles;
    private String regionSurvey;

    @Override
    public String getRegionSurvey() {
        return this.regionSurvey;
    }

    public void setRegionSurvey(String regionSurvey) {
        this.regionSurvey = regionSurvey;
    }

    @Override
    public String getCollapseCellIndex() {
        return this.collapseCellIndex;
    }

    @Override
    public String getDictionaryFillLinks() {
        return this.dictionaryFillLinks;
    }

    @Override
    public String getDefaultRowSetting() {
        return this.defaultRowSetting;
    }

    public String getKey() {
        return this.key;
    }

    public String getTitle() {
        return this.title;
    }

    public String getOrder() {
        return this.order;
    }

    public String getVersion() {
        return this.version;
    }

    public String getOwnerLevelAndId() {
        return this.ownerLevelAndId;
    }

    public Date getUpdateTime() {
        return this.updateTime;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public void setOwnerLevelAndId(String ownerLevelAndId) {
        this.ownerLevelAndId = ownerLevelAndId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public void setCollapseCellIndex(String indexStr) {
        this.collapseCellIndex = indexStr;
    }

    public void setDictionaryFillLinks(String fillLinks) {
        this.dictionaryFillLinks = fillLinks;
    }

    public void setDefaultRowSetting(String defaultRows) {
        this.defaultRowSetting = defaultRows;
    }

    @Override
    public List<RegionTabSettingDefine> getRegionTabSetting() {
        return this.regionTabSettings;
    }

    public void setRegionTabSetting(List<RegionTabSettingDefine> regionTabSettings) {
        this.regionTabSettings = regionTabSettings;
    }

    @Override
    public List<RowNumberSetting> getRowNumberSetting() {
        return this.rowNumberSettings;
    }

    public void setRowNumberSetting(List<RowNumberSetting> rowNumberSettings) {
        this.rowNumberSettings = rowNumberSettings;
    }

    @Override
    public List<RegionEdgeStyleDefine> getLastRowStyles() {
        return this.rowStyles;
    }

    public void setLastColumnStyle(List<RegionEdgeStyleDefine> colStyles) {
    }

    @Override
    public List<RegionEdgeStyleDefine> getLastColumnStyle() {
        return null;
    }

    public void setLastRowStyle(List<RegionEdgeStyleDefine> rowStyles) {
        this.rowStyles = rowStyles;
    }

    @Override
    public RecordCard getCardRecord() {
        return this.cardRecord;
    }

    public void setCardRecord(RecordCard cardRecord) {
        this.cardRecord = cardRecord;
    }

    @Override
    public List<EntityDefaultValue> getEntityDefaultValue() {
        return JacksonUtils.toList((String)this.entityDefaultValue, EntityDefaultValue.class);
    }
}

