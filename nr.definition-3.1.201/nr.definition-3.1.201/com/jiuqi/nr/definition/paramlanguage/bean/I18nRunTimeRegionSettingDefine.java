/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.paramlanguage.bean;

import com.jiuqi.nr.definition.facade.RegionEdgeStyleDefine;
import com.jiuqi.nr.definition.facade.RegionSettingDefine;
import com.jiuqi.nr.definition.facade.RegionTabSettingDefine;
import com.jiuqi.nr.definition.facade.RowNumberSetting;
import com.jiuqi.nr.definition.util.EntityDefaultValue;
import com.jiuqi.nr.definition.util.RecordCard;
import java.util.Date;
import java.util.List;
import org.springframework.util.CollectionUtils;

public class I18nRunTimeRegionSettingDefine
implements RegionSettingDefine {
    private final RegionSettingDefine regionSettingDefine;
    private List<RegionTabSettingDefine> regionTabSetting;

    public I18nRunTimeRegionSettingDefine(RegionSettingDefine regionSettingDefine) {
        this.regionSettingDefine = regionSettingDefine;
    }

    @Override
    public String getCollapseCellIndex() {
        return this.regionSettingDefine.getCollapseCellIndex();
    }

    @Override
    public String getDictionaryFillLinks() {
        return this.regionSettingDefine.getDictionaryFillLinks();
    }

    @Override
    public String getDefaultRowSetting() {
        return this.regionSettingDefine.getDefaultRowSetting();
    }

    @Override
    public List<RowNumberSetting> getRowNumberSetting() {
        return this.regionSettingDefine.getRowNumberSetting();
    }

    @Override
    public List<RegionEdgeStyleDefine> getLastRowStyles() {
        return this.regionSettingDefine.getLastRowStyles();
    }

    @Override
    public List<RegionEdgeStyleDefine> getLastColumnStyle() {
        return this.regionSettingDefine.getLastColumnStyle();
    }

    @Override
    public RecordCard getCardRecord() {
        return this.regionSettingDefine.getCardRecord();
    }

    @Override
    public List<EntityDefaultValue> getEntityDefaultValue() {
        return this.regionSettingDefine.getEntityDefaultValue();
    }

    public String getKey() {
        return this.regionSettingDefine.getKey();
    }

    public String getTitle() {
        return this.regionSettingDefine.getTitle();
    }

    public String getOrder() {
        return this.regionSettingDefine.getOrder();
    }

    public String getVersion() {
        return this.regionSettingDefine.getVersion();
    }

    public String getOwnerLevelAndId() {
        return this.regionSettingDefine.getOwnerLevelAndId();
    }

    public Date getUpdateTime() {
        return this.regionSettingDefine.getUpdateTime();
    }

    @Override
    public List<RegionTabSettingDefine> getRegionTabSetting() {
        return CollectionUtils.isEmpty(this.regionTabSetting) ? this.regionSettingDefine.getRegionTabSetting() : this.regionTabSetting;
    }

    public void setRegionTabSetting(List<RegionTabSettingDefine> regionTabSetting) {
        this.regionTabSetting = regionTabSetting;
    }

    @Override
    public String getRegionSurvey() {
        return this.regionSettingDefine.getRegionSurvey();
    }
}

