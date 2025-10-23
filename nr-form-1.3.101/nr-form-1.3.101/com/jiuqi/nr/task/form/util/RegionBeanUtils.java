/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.common.DataRegionKind
 *  com.jiuqi.nr.definition.facade.DesignDataRegionDefine
 *  com.jiuqi.nr.definition.facade.DesignRegionSettingDefine
 *  com.jiuqi.nr.definition.facade.RegionEdgeStyleDefine
 *  com.jiuqi.nr.definition.facade.RegionTabSettingDefine
 *  com.jiuqi.nr.definition.internal.dao.DesignBigDataTableDao
 *  com.jiuqi.nr.definition.internal.impl.DesignBigDataTable
 *  com.jiuqi.nr.definition.util.SerializeListImpl
 *  com.jiuqi.nr.task.api.util.DateTimeUtils
 */
package com.jiuqi.nr.task.form.util;

import com.jiuqi.nr.definition.common.DataRegionKind;
import com.jiuqi.nr.definition.facade.DesignDataRegionDefine;
import com.jiuqi.nr.definition.facade.DesignRegionSettingDefine;
import com.jiuqi.nr.definition.facade.RegionEdgeStyleDefine;
import com.jiuqi.nr.definition.facade.RegionTabSettingDefine;
import com.jiuqi.nr.definition.internal.dao.DesignBigDataTableDao;
import com.jiuqi.nr.definition.internal.impl.DesignBigDataTable;
import com.jiuqi.nr.definition.util.SerializeListImpl;
import com.jiuqi.nr.task.api.util.DateTimeUtils;
import com.jiuqi.nr.task.form.region.dto.DataRegionDTO;
import com.jiuqi.nr.task.form.region.dto.DataRegionSettingDTO;
import com.jiuqi.nr.task.form.region.dto.RegionEdgeStyleDTO;
import com.jiuqi.nr.task.form.region.dto.RegionExtensionDTO;
import com.jiuqi.nr.task.form.region.dto.RegionOrderDTO;
import com.jiuqi.nr.task.form.region.dto.RegionTabSettingDTO;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RegionBeanUtils {
    private static final Logger log = LoggerFactory.getLogger(RegionBeanUtils.class);

    public static DataRegionDTO toDto(DesignDataRegionDefine define) {
        DataRegionDTO dataRegionDTO = new DataRegionDTO();
        dataRegionDTO.setRegionLeft(define.getRegionLeft());
        dataRegionDTO.setRegionRight(define.getRegionRight());
        dataRegionDTO.setRegionTop(define.getRegionTop());
        dataRegionDTO.setRegionBottom(define.getRegionBottom());
        dataRegionDTO.setRegionKind(define.getRegionKind().getValue());
        dataRegionDTO.setKey(define.getKey());
        dataRegionDTO.setTitle(define.getTitle());
        dataRegionDTO.setOrder(define.getOrder());
        dataRegionDTO.setLevel(define.getOwnerLevelAndId());
        dataRegionDTO.setUpdateTime(DateTimeUtils.format((Date)define.getUpdateTime()));
        dataRegionDTO.setRegionEnterNext(define.getRegionEnterNext());
        dataRegionDTO.setFormKey(define.getFormKey());
        return dataRegionDTO;
    }

    public static void toDefine(DataRegionSettingDTO dataRegionSettingDTO, DesignDataRegionDefine designDataRegionDefine) {
        designDataRegionDefine.setUpdateTime(DateTimeUtils.parse((String)dataRegionSettingDTO.getUpdateTime()));
        designDataRegionDefine.setTitle(dataRegionSettingDTO.getTitle());
        designDataRegionDefine.setRegionLeft(dataRegionSettingDTO.getRegionLeft().intValue());
        designDataRegionDefine.setRegionRight(dataRegionSettingDTO.getRegionRight().intValue());
        designDataRegionDefine.setRegionTop(dataRegionSettingDTO.getRegionTop().intValue());
        designDataRegionDefine.setRegionBottom(dataRegionSettingDTO.getRegionBottom().intValue());
        designDataRegionDefine.setRegionKind(DataRegionKind.forValue((int)dataRegionSettingDTO.getRegionKind()));
        designDataRegionDefine.setInputOrderFieldKey(dataRegionSettingDTO.getInputOrderFieldKey());
        designDataRegionDefine.setSortFieldsList(dataRegionSettingDTO.getSortFieldsList());
        designDataRegionDefine.setRowsInFloatRegion(dataRegionSettingDTO.getRowsInFloatRegion().intValue());
        designDataRegionDefine.setGatherFields(dataRegionSettingDTO.getGatherFields());
        designDataRegionDefine.setLevelSetting(dataRegionSettingDTO.getLevelSetting());
        designDataRegionDefine.setShowGatherDetailRows(dataRegionSettingDTO.getShowGatherDetailRows().booleanValue());
        designDataRegionDefine.setShowGatherDetailRowByOne(dataRegionSettingDTO.getShowGatherDetailRowByOne().booleanValue());
        designDataRegionDefine.setShowGatherSummaryRow(dataRegionSettingDTO.getShowGatherSummaryRow().booleanValue());
        designDataRegionDefine.setShowAddress(dataRegionSettingDTO.getShowAddress());
        designDataRegionDefine.setFilterCondition(dataRegionSettingDTO.getFilterCondition());
        designDataRegionDefine.setMaxRowCount(dataRegionSettingDTO.getMaxRowCount().intValue());
        designDataRegionDefine.setCanDeleteRow(dataRegionSettingDTO.getCanDeleteRow().booleanValue());
        designDataRegionDefine.setCanInsertRow(dataRegionSettingDTO.getCanInsertRow().booleanValue());
        designDataRegionDefine.setPageSize(dataRegionSettingDTO.getPageSize().intValue());
        designDataRegionDefine.setAllowDuplicateKey(dataRegionSettingDTO.getAllowDuplicateKey().booleanValue());
        designDataRegionDefine.setIsCanFold(dataRegionSettingDTO.getCanFold().booleanValue());
        designDataRegionDefine.setHideZeroGatherFields(dataRegionSettingDTO.getHideZeroGatherFields());
        designDataRegionDefine.setRegionEnterNext(dataRegionSettingDTO.getRegionEnterNext());
        designDataRegionDefine.setReadOnlyCondition(dataRegionSettingDTO.getReadOnlyCondition());
        designDataRegionDefine.setDisplayLevel(dataRegionSettingDTO.getDisplayLevelFields());
        designDataRegionDefine.setFormKey(dataRegionSettingDTO.getFormKey());
        designDataRegionDefine.setKey(dataRegionSettingDTO.getKey());
    }

    public static DataRegionSettingDTO toSettingDto(DesignDataRegionDefine regionDefine, DesignRegionSettingDefine settingDefine, DesignBigDataTableDao bigDataTableDao) {
        if (regionDefine == null) {
            return null;
        }
        DataRegionSettingDTO dataRegionSettingDTO = new DataRegionSettingDTO();
        RegionBeanUtils.setBaseData(dataRegionSettingDTO, regionDefine);
        if (settingDefine != null) {
            RegionExtensionDTO extensionDTO = new RegionExtensionDTO();
            dataRegionSettingDTO.setRegionExtension(extensionDTO);
            RegionBeanUtils.setExtData(extensionDTO, settingDefine);
            if (bigDataTableDao != null) {
                RegionBeanUtils.setBigData(settingDefine, extensionDTO, bigDataTableDao);
            }
        }
        return dataRegionSettingDTO;
    }

    private static void setBigData(DesignRegionSettingDefine settingDefine, RegionExtensionDTO extensionDTO, DesignBigDataTableDao bigDataTableDao) {
        try {
            DesignBigDataTable bigDataTable = bigDataTableDao.queryigDataDefine(settingDefine.getKey(), "REGION_ORDER");
            if (bigDataTable == null) {
                return;
            }
            byte[] bytes = bigDataTable.getData();
            if (bytes != null) {
                SerializeListImpl serializeUtil = new SerializeListImpl(RegionOrderDTO.class);
                List rowNumberSettings = serializeUtil.deserialize(bytes, RegionOrderDTO.class);
                extensionDTO.setRowNumberSettings(rowNumberSettings);
            }
        }
        catch (Exception e) {
            log.error("\u8bbe\u7f6e\u533a\u57df\u5e8f\u53f7\u5931\u8d25", e);
        }
    }

    private static void setExtData(RegionExtensionDTO extensionDTO, DesignRegionSettingDefine settingDefine) {
        List edgeStyleDefines;
        List regionTabSettings = settingDefine.getRegionTabSetting();
        if (regionTabSettings != null) {
            ArrayList<RegionTabSettingDTO> tabSettingDTOS = new ArrayList<RegionTabSettingDTO>();
            for (RegionTabSettingDefine regionTabSetting : regionTabSettings) {
                RegionTabSettingDTO tabSettingDTO = new RegionTabSettingDTO();
                tabSettingDTO.setTitle(regionTabSetting.getTitle());
                tabSettingDTO.setDisplayCondition(regionTabSetting.getDisplayCondition());
                tabSettingDTO.setFilterCondition(regionTabSetting.getFilterCondition());
                tabSettingDTO.setBindingExpression(regionTabSetting.getBindingExpression());
                tabSettingDTO.setOrder(regionTabSetting.getOrder());
                tabSettingDTO.setRowNum(regionTabSetting.getRowNum());
                tabSettingDTO.setId(regionTabSetting.getId());
                tabSettingDTOS.add(tabSettingDTO);
            }
            extensionDTO.setRegionTabSettings(tabSettingDTOS);
        }
        if ((edgeStyleDefines = settingDefine.getLastRowStyles()) != null) {
            ArrayList<RegionEdgeStyleDTO> styleDTOS = new ArrayList<RegionEdgeStyleDTO>();
            for (RegionEdgeStyleDefine edgeStyleDefine : edgeStyleDefines) {
                RegionEdgeStyleDTO styleDTO = new RegionEdgeStyleDTO();
                styleDTO.setEndIndex(edgeStyleDefine.getEndIndex());
                styleDTO.setStartIndex(edgeStyleDefine.getStartIndex());
                styleDTO.setEdgeStyle(edgeStyleDefine.getEdgeLineStyle());
                styleDTO.setEdgeLineColor(RegionEdgeStyleDTO.intToHtmlColor(edgeStyleDefine.getEdgeLineColor(), null));
                styleDTOS.add(styleDTO);
            }
            extensionDTO.setRegionEdgeStyles(styleDTOS);
        }
        extensionDTO.setKey(settingDefine.getKey());
        extensionDTO.setDictionaryFillLinks(settingDefine.getDictionaryFillLinks());
        extensionDTO.setCardRecord(settingDefine.getCardRecord());
        extensionDTO.setEntityDefaultValues(settingDefine.getEntityDefaultValue());
    }

    private static void setBaseData(DataRegionSettingDTO dataRegionSettingDTO, DesignDataRegionDefine regionDefine) {
        dataRegionSettingDTO.setInputOrderFieldKey(regionDefine.getInputOrderFieldKey());
        dataRegionSettingDTO.setSortFieldsList(regionDefine.getSortFieldsList());
        dataRegionSettingDTO.setRowsInFloatRegion(regionDefine.getRowsInFloatRegion());
        dataRegionSettingDTO.setGatherFields(regionDefine.getGatherFields());
        dataRegionSettingDTO.setLevelSetting(regionDefine.getLevelSetting());
        dataRegionSettingDTO.setShowGatherDetailRows(regionDefine.getShowGatherDetailRows());
        dataRegionSettingDTO.setShowGatherDetailRowByOne(regionDefine.getShowGatherDetailRowByOne());
        dataRegionSettingDTO.setShowGatherSummaryRow(regionDefine.getShowGatherSummaryRow());
        dataRegionSettingDTO.setMaxRowCount(regionDefine.getMaxRowCount());
        dataRegionSettingDTO.setCanDeleteRow(regionDefine.getCanDeleteRow());
        dataRegionSettingDTO.setCanInsertRow(regionDefine.getCanInsertRow());
        dataRegionSettingDTO.setPageSize(regionDefine.getPageSize());
        dataRegionSettingDTO.setAllowDuplicateKey(regionDefine.getAllowDuplicateKey());
        dataRegionSettingDTO.setCanFold(regionDefine.getIsCanFold());
        dataRegionSettingDTO.setHideZeroGatherFields(regionDefine.getHideZeroGatherFields());
        dataRegionSettingDTO.setBizKeyFields(regionDefine.getBizKeyFields());
        dataRegionSettingDTO.setRegionEnterNext(regionDefine.getRegionEnterNext());
        dataRegionSettingDTO.setRegionLeft(regionDefine.getRegionLeft());
        dataRegionSettingDTO.setRegionRight(regionDefine.getRegionRight());
        dataRegionSettingDTO.setRegionTop(regionDefine.getRegionTop());
        dataRegionSettingDTO.setRegionBottom(regionDefine.getRegionBottom());
        dataRegionSettingDTO.setRegionKind(regionDefine.getRegionKind().getValue());
        dataRegionSettingDTO.setKey(regionDefine.getKey());
        dataRegionSettingDTO.setFormKey(regionDefine.getFormKey());
        dataRegionSettingDTO.setTitle(regionDefine.getTitle());
        dataRegionSettingDTO.setOrder(regionDefine.getOrder());
        dataRegionSettingDTO.setLevel(regionDefine.getOwnerLevelAndId());
        dataRegionSettingDTO.setUpdateTime(DateTimeUtils.format((Date)regionDefine.getUpdateTime()));
        dataRegionSettingDTO.setShowAddress(regionDefine.getShowAddress());
        dataRegionSettingDTO.setLevelSetting(regionDefine.getLevelSetting());
        dataRegionSettingDTO.setFilterCondition(regionDefine.getFilterCondition());
        dataRegionSettingDTO.setReadOnlyCondition(regionDefine.getReadOnlyCondition());
        dataRegionSettingDTO.setDisplayLevelFields(regionDefine.getDisplayLevel());
    }
}

