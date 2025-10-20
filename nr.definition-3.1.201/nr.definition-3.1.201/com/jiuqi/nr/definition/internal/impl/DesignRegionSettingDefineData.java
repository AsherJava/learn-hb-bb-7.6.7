/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.nr.period.common.utils.StringUtils
 */
package com.jiuqi.nr.definition.internal.impl;

import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.nr.definition.exception.NrDefinitionErrorEnum2;
import com.jiuqi.nr.definition.exception.NrDefinitionRuntimeException;
import com.jiuqi.nr.definition.facade.DesignRegionSettingDefine;
import com.jiuqi.nr.definition.facade.RegionEdgeStyleDefine;
import com.jiuqi.nr.definition.facade.RegionTabSettingDefine;
import com.jiuqi.nr.definition.facade.RowNumberSetting;
import com.jiuqi.nr.definition.internal.impl.DesignFormDefineBigDataUtil;
import com.jiuqi.nr.definition.internal.impl.RegionTabSettingData;
import com.jiuqi.nr.definition.internal.service.DesignBigDataService;
import com.jiuqi.nr.definition.util.EntityDefaultValue;
import com.jiuqi.nr.definition.util.RecordCard;
import com.jiuqi.nr.definition.util.SerializeListImpl;
import com.jiuqi.nr.period.common.utils.StringUtils;
import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DesignRegionSettingDefineData
implements DesignRegionSettingDefine {
    private DesignRegionSettingDefine designRegionSettingDefine;
    private DesignBigDataService designBigDataService;

    public DesignRegionSettingDefineData(DesignRegionSettingDefine designRegionSettingDefine, DesignBigDataService designBigDataService) {
        this.designRegionSettingDefine = designRegionSettingDefine;
        this.designBigDataService = designBigDataService;
    }

    public DesignRegionSettingDefine getDesignRegionSettingDefine() {
        return this.designRegionSettingDefine;
    }

    @Override
    public List<RegionTabSettingDefine> getRegionTabSetting() {
        if (null != this.designRegionSettingDefine.getRegionTabSetting()) {
            return this.designRegionSettingDefine.getRegionTabSetting();
        }
        try {
            byte[] bigData = this.designBigDataService.getBigData(this.getKey(), "REGION_TAB");
            if (null == bigData) {
                return null;
            }
            ArrayList<RegionTabSettingDefine> regionTabSettingDataList = new ArrayList<RegionTabSettingDefine>(RegionTabSettingData.bytesToRegionTabSettingData(bigData));
            this.designRegionSettingDefine.setRegionTabSetting(regionTabSettingDataList);
            return this.designRegionSettingDefine.getRegionTabSetting();
        }
        catch (Exception e) {
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.DATA_REGION_SETTING_QUERY, (Throwable)e);
        }
    }

    @Override
    public List<RowNumberSetting> getRowNumberSetting() {
        if (null != this.designRegionSettingDefine.getRowNumberSetting()) {
            return this.designRegionSettingDefine.getRowNumberSetting();
        }
        try {
            byte[] bigData = this.designBigDataService.getBigData(this.getKey(), "REGION_ORDER");
            if (null == bigData) {
                return null;
            }
            SerializeListImpl<RowNumberSetting> serializeUtil = new SerializeListImpl<RowNumberSetting>(RowNumberSetting.class);
            List<RowNumberSetting> rowNumberSettings = serializeUtil.deserialize(bigData, RowNumberSetting.class);
            this.designRegionSettingDefine.setRowNumberSetting(rowNumberSettings);
            return this.designRegionSettingDefine.getRowNumberSetting();
        }
        catch (Exception e) {
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.DATA_REGION_SETTING_QUERY, (Throwable)e);
        }
    }

    @Override
    public List<RegionEdgeStyleDefine> getLastRowStyles() {
        if (null != this.designRegionSettingDefine.getLastRowStyles()) {
            return this.designRegionSettingDefine.getLastRowStyles();
        }
        try {
            byte[] bigData = this.designBigDataService.getBigData(this.getKey(), "REGION_LT_ROW_STYLES");
            if (null == bigData) {
                return null;
            }
            SerializeListImpl<RegionEdgeStyleDefine> serializeUtil = new SerializeListImpl<RegionEdgeStyleDefine>(RegionEdgeStyleDefine.class);
            List<RegionEdgeStyleDefine> lastRowStyles = serializeUtil.deserialize(bigData, RegionEdgeStyleDefine.class);
            this.designRegionSettingDefine.setLastRowStyle(lastRowStyles);
            return this.designRegionSettingDefine.getLastRowStyles();
        }
        catch (Exception e) {
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.DATA_REGION_SETTING_QUERY, (Throwable)e);
        }
    }

    @Override
    public RecordCard getCardRecord() {
        if (null != this.designRegionSettingDefine.getCardRecord()) {
            return this.designRegionSettingDefine.getCardRecord();
        }
        try {
            byte[] bigData = this.designBigDataService.getBigData(this.getKey(), "BIG_REGION_CARD");
            if (null == bigData) {
                return null;
            }
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bigData);
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            RecordCard parseObject = (RecordCard)objectInputStream.readObject();
            this.designRegionSettingDefine.setCardRecord(parseObject);
            return this.designRegionSettingDefine.getCardRecord();
        }
        catch (Exception e) {
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.DATA_REGION_SETTING_QUERY, (Throwable)e);
        }
    }

    @Override
    public String getRegionSurvey() {
        if (StringUtils.isNotEmpty((String)this.designRegionSettingDefine.getRegionSurvey())) {
            return this.designRegionSettingDefine.getRegionSurvey();
        }
        try {
            byte[] bigData = this.designBigDataService.getBigData(this.getKey(), "BIG_REGION_SURVEY");
            if (null == bigData) {
                return null;
            }
            this.designRegionSettingDefine.setRegionSurvey(DesignFormDefineBigDataUtil.bytesToString(bigData));
            return this.designRegionSettingDefine.getRegionSurvey();
        }
        catch (Exception e) {
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.DATA_REGION_SETTING_QUERY, (Throwable)e);
        }
    }

    @Override
    public void setRegionSurvey(String regionSurvey) {
        this.designRegionSettingDefine.setRegionSurvey(regionSurvey);
    }

    @Override
    public void setKey(String key) {
        this.designRegionSettingDefine.setKey(key);
    }

    @Override
    public void setOrder(String order) {
        this.designRegionSettingDefine.setOrder(order);
    }

    @Override
    public void setVersion(String version) {
        this.designRegionSettingDefine.setVersion(version);
    }

    @Override
    public void setOwnerLevelAndId(String ownerLevelAndId) {
        this.designRegionSettingDefine.setOwnerLevelAndId(ownerLevelAndId);
    }

    @Override
    public void setTitle(String title) {
        this.designRegionSettingDefine.setTitle(title);
    }

    @Override
    public void setUpdateTime(Date updateTime) {
        this.designRegionSettingDefine.setUpdateTime(updateTime);
    }

    @Override
    public void setCollapseCellIndex(String indexStr) {
        this.designRegionSettingDefine.setCollapseCellIndex(indexStr);
    }

    @Override
    public void setDictionaryFillLinks(String fillLinks) {
        this.designRegionSettingDefine.setDictionaryFillLinks(fillLinks);
    }

    @Override
    public void setDefaultRowSetting(String defaultRows) {
        this.designRegionSettingDefine.setDefaultRowSetting(defaultRows);
    }

    @Override
    public void setRegionTabSetting(List<RegionTabSettingDefine> regionTabSettings) {
        this.designRegionSettingDefine.setRegionTabSetting(regionTabSettings);
    }

    @Override
    public void setLastRowStyle(List<RegionEdgeStyleDefine> rowStyles) {
        this.designRegionSettingDefine.setLastRowStyle(rowStyles);
    }

    @Override
    public void setLastColumnStyle(List<RegionEdgeStyleDefine> colStyles) {
        this.designRegionSettingDefine.setLastColumnStyle(colStyles);
    }

    @Override
    public void setRowNumberSetting(List<RowNumberSetting> rowNumberSetting) {
        this.designRegionSettingDefine.setRowNumberSetting(rowNumberSetting);
    }

    @Override
    public void setCardRecord(RecordCard cardRecord) {
        this.designRegionSettingDefine.setCardRecord(cardRecord);
    }

    @Override
    public void setEntityDefaultValue(String entityDefaultValue) {
        this.designRegionSettingDefine.setEntityDefaultValue(entityDefaultValue);
    }

    @Override
    public String getCollapseCellIndex() {
        return this.designRegionSettingDefine.getCollapseCellIndex();
    }

    @Override
    public String getDictionaryFillLinks() {
        return this.designRegionSettingDefine.getDictionaryFillLinks();
    }

    @Override
    public String getDefaultRowSetting() {
        return this.designRegionSettingDefine.getDefaultRowSetting();
    }

    @Override
    public List<RegionEdgeStyleDefine> getLastColumnStyle() {
        return this.designRegionSettingDefine.getLastColumnStyle();
    }

    @Override
    public List<EntityDefaultValue> getEntityDefaultValue() {
        return this.designRegionSettingDefine.getEntityDefaultValue();
    }

    public Date getUpdateTime() {
        return this.designRegionSettingDefine.getUpdateTime();
    }

    public String getKey() {
        return this.designRegionSettingDefine.getKey();
    }

    public String getTitle() {
        return this.designRegionSettingDefine.getTitle();
    }

    public String getOrder() {
        return this.designRegionSettingDefine.getOrder();
    }

    public String getVersion() {
        return this.designRegionSettingDefine.getVersion();
    }

    public String getOwnerLevelAndId() {
        return this.designRegionSettingDefine.getOwnerLevelAndId();
    }
}

