/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.util.EntityDefaultValue
 *  com.jiuqi.nr.definition.util.RecordCard
 */
package com.jiuqi.nr.task.form.region.dto;

import com.jiuqi.nr.definition.util.EntityDefaultValue;
import com.jiuqi.nr.definition.util.RecordCard;
import com.jiuqi.nr.task.form.dto.AbstractState;
import com.jiuqi.nr.task.form.region.dto.RegionEdgeStyleDTO;
import com.jiuqi.nr.task.form.region.dto.RegionOrderDTO;
import com.jiuqi.nr.task.form.region.dto.RegionTabSettingDTO;
import java.util.List;

public class RegionExtensionDTO
extends AbstractState {
    private String key;
    private String dictionaryFillLinks;
    private List<RegionTabSettingDTO> regionTabSettings;
    private List<RegionOrderDTO> rowNumberSettings;
    private List<RegionEdgeStyleDTO> regionEdgeStyles;
    private List<EntityDefaultValue> entityDefaultValues;
    private RecordCard cardRecord;

    public String getDictionaryFillLinks() {
        return this.dictionaryFillLinks;
    }

    public void setDictionaryFillLinks(String dictionaryFillLinks) {
        this.dictionaryFillLinks = dictionaryFillLinks;
    }

    public List<RegionTabSettingDTO> getRegionTabSettings() {
        return this.regionTabSettings;
    }

    public void setRegionTabSettings(List<RegionTabSettingDTO> regionTabSettings) {
        this.regionTabSettings = regionTabSettings;
    }

    public List<RegionOrderDTO> getRowNumberSettings() {
        return this.rowNumberSettings;
    }

    public void setRowNumberSettings(List<RegionOrderDTO> rowNumberSettings) {
        this.rowNumberSettings = rowNumberSettings;
    }

    public List<RegionEdgeStyleDTO> getRegionEdgeStyles() {
        return this.regionEdgeStyles;
    }

    public void setRegionEdgeStyles(List<RegionEdgeStyleDTO> regionEdgeStyles) {
        this.regionEdgeStyles = regionEdgeStyles;
    }

    public List<EntityDefaultValue> getEntityDefaultValues() {
        return this.entityDefaultValues;
    }

    public void setEntityDefaultValues(List<EntityDefaultValue> entityDefaultValues) {
        this.entityDefaultValues = entityDefaultValues;
    }

    public RecordCard getCardRecord() {
        return this.cardRecord;
    }

    public void setCardRecord(RecordCard cardRecord) {
        this.cardRecord = cardRecord;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}

