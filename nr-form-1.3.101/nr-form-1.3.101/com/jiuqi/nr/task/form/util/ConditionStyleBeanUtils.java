/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.conditionalstyle.facade.DesignConditionalStyle
 *  com.jiuqi.nr.conditionalstyle.facade.impl.DesignConditionalStyleImpl
 */
package com.jiuqi.nr.task.form.util;

import com.jiuqi.nr.conditionalstyle.facade.DesignConditionalStyle;
import com.jiuqi.nr.conditionalstyle.facade.impl.DesignConditionalStyleImpl;
import com.jiuqi.nr.task.form.dto.ConditionStyleDTO;

public class ConditionStyleBeanUtils {
    public static DesignConditionalStyle toDefine(ConditionStyleDTO conditionStyleDTO) {
        if (conditionStyleDTO == null) {
            return null;
        }
        DesignConditionalStyleImpl designConditionalStyle = new DesignConditionalStyleImpl();
        designConditionalStyle.setKey(conditionStyleDTO.getKey());
        designConditionalStyle.setFormKey(conditionStyleDTO.getFormKey());
        designConditionalStyle.setLinkKey(conditionStyleDTO.getLinkKey());
        designConditionalStyle.setPosX(conditionStyleDTO.getPosX());
        designConditionalStyle.setPosY(conditionStyleDTO.getPosY());
        designConditionalStyle.setStyleExpression(conditionStyleDTO.getStyleExpression());
        designConditionalStyle.setFontColor(conditionStyleDTO.getFontColor());
        designConditionalStyle.setForeGroundColor(conditionStyleDTO.getForeGroundColor());
        designConditionalStyle.setBold(conditionStyleDTO.getBold());
        designConditionalStyle.setItalic(conditionStyleDTO.getItalic());
        designConditionalStyle.setReadOnly(conditionStyleDTO.getReadOnly());
        designConditionalStyle.setOrder(conditionStyleDTO.getOrder());
        designConditionalStyle.setUpdateTime(conditionStyleDTO.getUpdateTime());
        designConditionalStyle.setStrikeThrough(Boolean.valueOf(conditionStyleDTO.getStrikeThrough() == null ? false : conditionStyleDTO.getStrikeThrough()));
        designConditionalStyle.setHorizontalBar(Boolean.valueOf(conditionStyleDTO.getHorizontalBar() == null ? false : conditionStyleDTO.getHorizontalBar()));
        return designConditionalStyle;
    }

    public static ConditionStyleDTO define2DTO(DesignConditionalStyle conditionalStyle) {
        if (conditionalStyle == null) {
            return null;
        }
        ConditionStyleDTO dto = new ConditionStyleDTO();
        dto.setKey(conditionalStyle.getKey());
        dto.setFormKey(conditionalStyle.getFormKey());
        dto.setLinkKey(conditionalStyle.getLinkKey());
        dto.setPosX(conditionalStyle.getPosX());
        dto.setPosY(conditionalStyle.getPosY());
        dto.setStyleExpression(conditionalStyle.getStyleExpression());
        dto.setFontColor(conditionalStyle.getFontColor());
        dto.setForeGroundColor(conditionalStyle.getForeGroundColor());
        dto.setBold(conditionalStyle.getBold());
        dto.setItalic(conditionalStyle.getItalic());
        dto.setReadOnly(conditionalStyle.getReadOnly());
        dto.setOrder(conditionalStyle.getOrder());
        dto.setUpdateTime(conditionalStyle.getUpdateTime());
        dto.setStrikeThrough(conditionalStyle.getStrikeThrough());
        dto.setHorizontalBar(conditionalStyle.getHorizontalBar());
        return dto;
    }
}

