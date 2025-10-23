/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFormat
 *  com.fasterxml.jackson.annotation.JsonFormat$Shape
 *  com.fasterxml.jackson.annotation.JsonIgnoreProperties
 *  com.jiuqi.nr.definition.facade.DesignRegionSettingDefine
 *  com.jiuqi.nr.definition.facade.RegionSettingDefine
 *  com.jiuqi.nr.definition.util.RecordCard
 *  com.jiuqi.nr.period.util.JacksonUtils
 */
package com.jiuqi.nr.param.transfer.definition.dto.form;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jiuqi.nr.definition.facade.DesignRegionSettingDefine;
import com.jiuqi.nr.definition.facade.RegionSettingDefine;
import com.jiuqi.nr.definition.util.RecordCard;
import com.jiuqi.nr.param.transfer.definition.dto.form.RegionTabSettingDTO;
import com.jiuqi.nr.param.transfer.definition.dto.form.RowNumberSettingDTO;
import com.jiuqi.nr.period.util.JacksonUtils;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.util.CollectionUtils;

@JsonIgnoreProperties(ignoreUnknown=true)
public class RegionSettingDTO {
    private String key;
    private String title;
    private String order;
    private String version;
    private String ownerLevelAndId;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH-mm-ss.sss zzz")
    private Date updateTime;
    private String collapseCellIndex;
    private String dictionaryFillLinks;
    private String defaultRowSetting;
    private RecordCard cardRecord;
    private List<RegionTabSettingDTO> regionTabSettings;
    private List<RowNumberSettingDTO> rowNumberSettings;
    private String entityDefaultValue;
    private String regionSurvey;

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

    public RecordCard getCardRecord() {
        return this.cardRecord;
    }

    public void setCardRecord(RecordCard cardRecord) {
        this.cardRecord = cardRecord;
    }

    public List<RegionTabSettingDTO> getRegionTabSettings() {
        return this.regionTabSettings;
    }

    public void setRegionTabSettings(List<RegionTabSettingDTO> regionTabSettings) {
        this.regionTabSettings = regionTabSettings;
    }

    public List<RowNumberSettingDTO> getRowNumberSettings() {
        return this.rowNumberSettings;
    }

    public void setRowNumberSettings(List<RowNumberSettingDTO> rowNumberSetting) {
        this.rowNumberSettings = rowNumberSetting;
    }

    public String getEntityDefaultValue() {
        return this.entityDefaultValue;
    }

    public void setEntityDefaultValue(String entityDefaultValue) {
        this.entityDefaultValue = entityDefaultValue;
    }

    public String getRegionSurvey() {
        return this.regionSurvey;
    }

    public void setRegionSurvey(String regionSurvey) {
        this.regionSurvey = regionSurvey;
    }

    public void value2Define(DesignRegionSettingDefine regionSettingDefine) {
        List<RowNumberSettingDTO> rowNumberSettings;
        regionSettingDefine.setKey(this.getKey());
        regionSettingDefine.setTitle(this.getTitle());
        regionSettingDefine.setOrder(this.getOrder());
        regionSettingDefine.setVersion(this.getVersion());
        regionSettingDefine.setUpdateTime(this.getUpdateTime());
        regionSettingDefine.setCollapseCellIndex(this.getCollapseCellIndex());
        regionSettingDefine.setDictionaryFillLinks(this.getDictionaryFillLinks());
        List<RegionTabSettingDTO> regionTabSettings = this.getRegionTabSettings();
        if (!CollectionUtils.isEmpty(regionTabSettings)) {
            regionSettingDefine.setRegionTabSetting(regionTabSettings.stream().map(RegionTabSettingDTO::value2Define).collect(Collectors.toList()));
        }
        if (!CollectionUtils.isEmpty(rowNumberSettings = this.getRowNumberSettings())) {
            regionSettingDefine.setRowNumberSetting(rowNumberSettings.stream().map(RowNumberSettingDTO::value2Define).collect(Collectors.toList()));
        }
        regionSettingDefine.setDefaultRowSetting(this.getDefaultRowSetting());
        regionSettingDefine.setOwnerLevelAndId(this.getOwnerLevelAndId());
        regionSettingDefine.setCardRecord(this.getCardRecord());
        regionSettingDefine.setEntityDefaultValue(this.getEntityDefaultValue());
        regionSettingDefine.setRegionSurvey(this.getRegionSurvey());
    }

    public static RegionSettingDTO valueOf(RegionSettingDefine regionSettingDefine) {
        List rowNumberSetting;
        if (regionSettingDefine == null) {
            return null;
        }
        RegionSettingDTO regionSettingDTOParam = new RegionSettingDTO();
        regionSettingDTOParam.setKey(regionSettingDefine.getKey());
        regionSettingDTOParam.setTitle(regionSettingDefine.getTitle());
        regionSettingDTOParam.setOrder(regionSettingDefine.getOrder());
        regionSettingDTOParam.setVersion(regionSettingDefine.getVersion());
        regionSettingDTOParam.setUpdateTime(regionSettingDefine.getUpdateTime());
        regionSettingDTOParam.setCollapseCellIndex(regionSettingDefine.getCollapseCellIndex());
        regionSettingDTOParam.setDictionaryFillLinks(regionSettingDefine.getDictionaryFillLinks());
        List regionTabSetting = regionSettingDefine.getRegionTabSetting();
        if (!CollectionUtils.isEmpty(regionTabSetting)) {
            regionSettingDTOParam.setRegionTabSettings(regionTabSetting.stream().map(RegionTabSettingDTO::valueOf).collect(Collectors.toList()));
        }
        if (!CollectionUtils.isEmpty(rowNumberSetting = regionSettingDefine.getRowNumberSetting())) {
            regionSettingDTOParam.setRowNumberSettings(rowNumberSetting.stream().map(RowNumberSettingDTO::valueOf).collect(Collectors.toList()));
        }
        regionSettingDTOParam.setDefaultRowSetting(regionSettingDefine.getDefaultRowSetting());
        regionSettingDTOParam.setOwnerLevelAndId(regionSettingDefine.getOwnerLevelAndId());
        regionSettingDTOParam.setCardRecord(regionSettingDefine.getCardRecord());
        regionSettingDTOParam.setEntityDefaultValue(JacksonUtils.objectToJson((Object)regionSettingDefine.getEntityDefaultValue()));
        regionSettingDTOParam.setRegionSurvey(regionSettingDefine.getRegionSurvey());
        return regionSettingDTOParam;
    }
}

