/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.jiuqi.nr.definition.common.DataLinkEditMode
 *  com.jiuqi.nr.definition.common.DataLinkType
 *  com.jiuqi.nr.definition.common.EnumDisplayMode
 *  com.jiuqi.nr.definition.facade.DesignDataLinkDefine
 *  com.jiuqi.nr.definition.internal.dao.DesignBigDataTableDao
 *  com.jiuqi.nr.period.util.JacksonUtils
 *  com.jiuqi.nr.task.api.util.DateTimeUtils
 */
package com.jiuqi.nr.task.form.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.jiuqi.nr.definition.common.DataLinkEditMode;
import com.jiuqi.nr.definition.common.DataLinkType;
import com.jiuqi.nr.definition.common.EnumDisplayMode;
import com.jiuqi.nr.definition.facade.DesignDataLinkDefine;
import com.jiuqi.nr.definition.internal.dao.DesignBigDataTableDao;
import com.jiuqi.nr.period.util.JacksonUtils;
import com.jiuqi.nr.task.api.util.DateTimeUtils;
import com.jiuqi.nr.task.form.link.dto.DataLinkDTO;
import com.jiuqi.nr.task.form.link.dto.DataLinkSettingDTO;
import com.jiuqi.nr.task.form.util.FormatPropertiesUtils;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

public class LinkBeanUtils {
    private static final Logger log = LoggerFactory.getLogger(LinkBeanUtils.class);

    public static DataLinkDTO toDto(DesignDataLinkDefine linkDefine) {
        DataLinkDTO dataLinkDTO = new DataLinkDTO();
        dataLinkDTO.setRegionKey(linkDefine.getRegionKey());
        dataLinkDTO.setLinkExpression(linkDefine.getLinkExpression());
        dataLinkDTO.setPosX(linkDefine.getPosX());
        dataLinkDTO.setPosY(linkDefine.getPosY());
        dataLinkDTO.setColNum(linkDefine.getColNum());
        dataLinkDTO.setRowNum(linkDefine.getRowNum());
        dataLinkDTO.setType(linkDefine.getType().getValue());
        dataLinkDTO.setKey(linkDefine.getKey());
        dataLinkDTO.setTitle(linkDefine.getTitle());
        dataLinkDTO.setOrder(linkDefine.getOrder());
        dataLinkDTO.setLevel(linkDefine.getOwnerLevelAndId());
        dataLinkDTO.setUpdateTime(DateTimeUtils.format((Date)linkDefine.getUpdateTime()));
        return dataLinkDTO;
    }

    public static void toDefine(DataLinkSettingDTO dataLinkSettingDTO, DesignDataLinkDefine designDataLinkDefine) {
        designDataLinkDefine.setKey(dataLinkSettingDTO.getKey());
        designDataLinkDefine.setOrder(dataLinkSettingDTO.getOrder());
        designDataLinkDefine.setOwnerLevelAndId(dataLinkSettingDTO.getLevel());
        designDataLinkDefine.setUniqueCode(dataLinkSettingDTO.getUniqueCode());
        designDataLinkDefine.setTitle(dataLinkSettingDTO.getTitle());
        designDataLinkDefine.setUpdateTime(DateTimeUtils.parse((String)dataLinkSettingDTO.getUpdateTime()));
        designDataLinkDefine.setRegionKey(dataLinkSettingDTO.getRegionKey());
        designDataLinkDefine.setLinkExpression(dataLinkSettingDTO.getLinkExpression());
        designDataLinkDefine.setPosX(dataLinkSettingDTO.getPosX().intValue());
        designDataLinkDefine.setPosY(dataLinkSettingDTO.getPosY().intValue());
        designDataLinkDefine.setColNum(dataLinkSettingDTO.getColNum().intValue());
        designDataLinkDefine.setRowNum(dataLinkSettingDTO.getRowNum().intValue());
        if (dataLinkSettingDTO.getEditMode() != null) {
            designDataLinkDefine.setEditMode(DataLinkEditMode.forValue((int)dataLinkSettingDTO.getEditMode()));
        }
        if (dataLinkSettingDTO.getDisplayMode() != null) {
            designDataLinkDefine.setDisplayMode(EnumDisplayMode.forValue((int)dataLinkSettingDTO.getDisplayMode()));
        }
        designDataLinkDefine.setCaptionFieldsString(dataLinkSettingDTO.getCaptionFieldsString());
        designDataLinkDefine.setDropDownFieldsString(dataLinkSettingDTO.getDropDownFieldsString());
        designDataLinkDefine.setAllowUndefinedCode(dataLinkSettingDTO.getAllowUndefinedCode());
        designDataLinkDefine.setAllowNullAble(dataLinkSettingDTO.getAllowNullAble());
        designDataLinkDefine.setAllowNotLeafNodeRefer(dataLinkSettingDTO.getAllowNotLeafNodeRefer().booleanValue());
        designDataLinkDefine.setFormatProperties(FormatPropertiesUtils.convert(dataLinkSettingDTO.getLinkFormat()));
        designDataLinkDefine.setUniqueCode(dataLinkSettingDTO.getUniqueCode());
        designDataLinkDefine.setEnumShowFullPath(dataLinkSettingDTO.getEnumShowFullPath());
        designDataLinkDefine.setEnumTitleField(dataLinkSettingDTO.getEnumTitleField());
        designDataLinkDefine.setType(DataLinkType.forValue((int)dataLinkSettingDTO.getType()));
        designDataLinkDefine.setEnumLinkage(JacksonUtils.objectToJson(dataLinkSettingDTO.getEnumLinkage()));
        designDataLinkDefine.setEnumCount(dataLinkSettingDTO.getEnumCount().intValue());
        designDataLinkDefine.setEnumPos(null);
        Map<String, Object> enumPosMap = dataLinkSettingDTO.getEnumPosMap();
        if (enumPosMap != null) {
            for (String s : enumPosMap.keySet()) {
                if (enumPosMap.get(s) != null) continue;
                enumPosMap.remove(s);
            }
            if (!enumPosMap.isEmpty()) {
                designDataLinkDefine.setEnumPos(JacksonUtils.objectToJson(enumPosMap));
            }
        }
        designDataLinkDefine.setEnumLinkageStatus(dataLinkSettingDTO.getEnumLinkageStatus().booleanValue());
        designDataLinkDefine.setFilterExpression(dataLinkSettingDTO.getFilterExpression());
        designDataLinkDefine.setIgnorePermissions(dataLinkSettingDTO.getIgnorePermissions().booleanValue());
        designDataLinkDefine.setFilterTemplate(dataLinkSettingDTO.getFilterTemplateKey());
        String measureUnit = dataLinkSettingDTO.getMeasureUnit();
        if (measureUnit != null) {
            designDataLinkDefine.setMeasureUnit("9493b4eb-6516-48a8-a878-25a63a23e63a;" + measureUnit);
        }
        designDataLinkDefine.setFormatProperties(dataLinkSettingDTO.getFormatProperties());
    }

    public static DataLinkSettingDTO toSettingDto(DesignDataLinkDefine define, DesignBigDataTableDao bigDataTableDao) {
        DataLinkSettingDTO dataLinkSettingDTO = new DataLinkSettingDTO();
        if (define.getEditMode() != null) {
            dataLinkSettingDTO.setEditMode(define.getEditMode().getValue());
        }
        if (define.getDisplayMode() != null) {
            dataLinkSettingDTO.setDisplayMode(define.getDisplayMode().getValue());
        }
        dataLinkSettingDTO.setCaptionFieldsString(define.getCaptionFieldsString());
        dataLinkSettingDTO.setDropDownFieldsString(define.getDropDownFieldsString());
        dataLinkSettingDTO.setAllowUndefinedCode(define.getAllowUndefinedCode());
        dataLinkSettingDTO.setAllowNullAble(define.getAllowNullAble());
        dataLinkSettingDTO.setAllowNotLeafNodeRefer(define.getAllowNotLeafNodeRefer());
        dataLinkSettingDTO.setLinkFormat(FormatPropertiesUtils.convert(define.getFormatProperties()));
        dataLinkSettingDTO.setUniqueCode(define.getUniqueCode());
        dataLinkSettingDTO.setEnumShowFullPath(define.getEnumShowFullPath());
        dataLinkSettingDTO.setEnumTitleField(define.getEnumTitleField());
        if (define.getEnumLinkage() != null) {
            dataLinkSettingDTO.setEnumLinkage((List)JacksonUtils.jsonToObject((String)define.getEnumLinkage(), (TypeReference)new TypeReference<List<Map<String, String>>>(){}));
            dataLinkSettingDTO.setEnumLinkageMethod(define.getEnumLinkageMethod());
            dataLinkSettingDTO.setEnumLinkageData(define.getEnumLinkageData());
            dataLinkSettingDTO.setEnumLinkageStatus(define.getEnumLinkageStatus());
        }
        dataLinkSettingDTO.setEnumCount(define.getEnumCount());
        dataLinkSettingDTO.setEnumPos(define.getEnumPos());
        dataLinkSettingDTO.setEnumPosMap(define.getEnumPosMap());
        dataLinkSettingDTO.setEnumLinkageStatus(define.getEnumLinkageStatus());
        dataLinkSettingDTO.setFilterExpression(define.getFilterExpression());
        dataLinkSettingDTO.setFilterTemplateKey(define.getFilterTemplate());
        dataLinkSettingDTO.setIgnorePermissions(define.isIgnorePermissions());
        dataLinkSettingDTO.setRegionKey(define.getRegionKey());
        dataLinkSettingDTO.setLinkExpression(define.getLinkExpression());
        dataLinkSettingDTO.setPosX(define.getPosX());
        dataLinkSettingDTO.setPosY(define.getPosY());
        dataLinkSettingDTO.setColNum(define.getColNum());
        dataLinkSettingDTO.setRowNum(define.getRowNum());
        dataLinkSettingDTO.setType(define.getType().getValue());
        dataLinkSettingDTO.setKey(define.getKey());
        dataLinkSettingDTO.setTitle(define.getTitle());
        dataLinkSettingDTO.setOrder(define.getOrder());
        dataLinkSettingDTO.setLevel(define.getOwnerLevelAndId());
        dataLinkSettingDTO.setUpdateTime(DateTimeUtils.format((Date)define.getUpdateTime()));
        String measureUnit = define.getMeasureUnit();
        if (StringUtils.hasLength(measureUnit)) {
            measureUnit = measureUnit.replace("9493b4eb-6516-48a8-a878-25a63a23e63a;", "");
            dataLinkSettingDTO.setMeasureUnit(measureUnit);
        }
        dataLinkSettingDTO.setFormatProperties(define.getFormatProperties());
        return dataLinkSettingDTO;
    }
}

