/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.common.OrientEnum
 */
package com.jiuqi.gcreport.offsetitem.util;

import com.jiuqi.gcreport.common.OrientEnum;
import com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrDTO;
import com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrItemDTO;
import com.jiuqi.gcreport.offsetitem.entity.GcOffSetVchrItemAdjustEO;
import com.jiuqi.gcreport.offsetitem.enums.OffSetSrcTypeEnum;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.beans.BeanUtils;

public class OffsetCoreConvertUtil {
    public static GcOffSetVchrItemAdjustEO convertDTO2EO(GcOffSetVchrItemDTO itemDTO) {
        if (itemDTO == null) {
            return null;
        }
        GcOffSetVchrItemAdjustEO itemEo = new GcOffSetVchrItemAdjustEO();
        HashMap fields = new HashMap();
        fields.putAll(itemDTO.getFields());
        BeanUtils.copyProperties((Object)itemDTO, (Object)itemEo);
        itemEo.resetFields(fields);
        if (null == itemEo.getSrcOffsetGroupId()) {
            itemEo.setSrcOffsetGroupId(itemEo.getmRecid());
        }
        Map<String, Object> unSysFields = itemDTO.getUnSysFields();
        for (Map.Entry<String, Object> unSysField : unSysFields.entrySet()) {
            if (unSysField.getValue() instanceof Map) {
                itemEo.addFieldValue(unSysField.getKey(), ((Map)unSysField.getValue()).get("code"));
                continue;
            }
            itemEo.addFieldValue(unSysField.getKey(), unSysField.getValue());
        }
        itemEo.setOffSetSrcType(OffSetSrcTypeEnum.getEnumValue(itemDTO.getOffSetSrcType()));
        OffsetCoreConvertUtil.initNullValue(itemEo);
        if (itemDTO.getDisableFlag() == null) {
            itemEo.setDisableFlag(0);
        } else {
            itemEo.setDisableFlag(itemDTO.getDisableFlag() != false ? 1 : 0);
        }
        itemEo.setSubjectOrient(itemDTO.getSubjectOrient().getValue());
        itemEo.setAdjust(itemDTO.getSelectAdjustCode());
        return itemEo;
    }

    private static void initNullValue(GcOffSetVchrItemAdjustEO item) {
        if (item.getOffSetDebit() == null) {
            item.setOffSetDebit(0.0);
        }
        if (item.getDiffd() == null) {
            item.setDiffd(0.0);
        }
        if (item.getOffSetCredit() == null) {
            item.setOffSetCredit(0.0);
        }
        if (item.getDiffc() == null) {
            item.setDiffc(0.0);
        }
    }

    public static GcOffSetVchrDTO convertEO2DTO(String mrecid, List<GcOffSetVchrItemAdjustEO> itemEOs) {
        List itemDTOs = null;
        if (itemEOs != null && itemEOs.size() > 0) {
            itemDTOs = itemEOs.stream().map(itemEO -> OffsetCoreConvertUtil.convertEO2DTO(itemEO)).collect(Collectors.toList());
        }
        GcOffSetVchrDTO dto = new GcOffSetVchrDTO();
        dto.setMrecid(mrecid);
        dto.setItems(itemDTOs);
        return dto;
    }

    public static GcOffSetVchrItemDTO convertEO2DTO(GcOffSetVchrItemAdjustEO itemEO) {
        if (itemEO == null) {
            return null;
        }
        GcOffSetVchrItemDTO itemDTO = new GcOffSetVchrItemDTO();
        BeanUtils.copyProperties((Object)itemEO, (Object)itemDTO);
        itemDTO.setDisableFlag(Objects.equals(itemEO.getDisableFlag(), 1));
        itemDTO.resetFields(new HashMap());
        itemDTO.getFields().putAll(itemEO.getFields());
        itemDTO.setOffSetSrcType(OffSetSrcTypeEnum.getEnumByValue(itemEO.getOffSetSrcType()));
        if (OffSetSrcTypeEnum.OFFSET_ITEM_INIT.getSrcTypeValue() == itemEO.getOffSetSrcType().intValue()) {
            if (OrientEnum.D.getValue().equals(itemEO.getOrient())) {
                itemDTO.setOrient(OrientEnum.D);
            } else if (OrientEnum.C.getValue().equals(itemEO.getOrient())) {
                itemDTO.setOrient(OrientEnum.C);
            }
        }
        return itemDTO;
    }
}

