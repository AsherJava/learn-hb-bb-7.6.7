/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.nr.definition.internal.BeanUtil
 */
package com.jiuqi.nr.jtable.util;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.jtable.params.base.EnumLinkData;
import com.jiuqi.nr.jtable.params.base.LinkData;
import com.jiuqi.nr.jtable.params.input.EntityQueryByKeyInfo;
import com.jiuqi.nr.jtable.params.output.EntityByKeyReturnInfo;
import com.jiuqi.nr.jtable.params.output.EntityData;
import com.jiuqi.nr.jtable.service.IJtableEntityService;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

public class CompareDiffenceUtil {
    public static String translateString(LinkData link, String value) {
        IJtableEntityService jtableEntityService = (IJtableEntityService)BeanUtil.getBean(IJtableEntityService.class);
        if (link instanceof EnumLinkData && StringUtils.isNotEmpty((String)value)) {
            EntityData entityData;
            EnumLinkData enumLink = (EnumLinkData)link;
            EntityQueryByKeyInfo entityQueryByKeyInfo = new EntityQueryByKeyInfo();
            entityQueryByKeyInfo.setEntityViewKey(enumLink.getEntityKey());
            entityQueryByKeyInfo.setEntityKey(value);
            EntityByKeyReturnInfo entityByKeyReturnInfo = jtableEntityService.queryEntityDataByKey(entityQueryByKeyInfo);
            if (null != entityByKeyReturnInfo && null != (entityData = entityByKeyReturnInfo.getEntity())) {
                value = entityData.getTitle();
            }
        }
        return value;
    }

    public static String compareDifference(int fieldType, String initialValue, String compareValue) {
        String difference = "--";
        if (1 == fieldType || 3 == fieldType || 8 == fieldType) {
            BigDecimal initialBigDecimal = null;
            try {
                if (!StringUtils.isEmpty((String)initialValue)) {
                    if (initialValue.contains(",")) {
                        Number number = NumberFormat.getIntegerInstance(Locale.getDefault()).parse(initialValue);
                        initialBigDecimal = new BigDecimal(number.doubleValue());
                    } else {
                        initialBigDecimal = new BigDecimal(initialValue);
                    }
                }
            }
            catch (Exception number) {
                // empty catch block
            }
            BigDecimal compareBigDecimal = null;
            try {
                if (!StringUtils.isEmpty((String)compareValue)) {
                    if (compareValue.contains(",")) {
                        Number number = NumberFormat.getIntegerInstance(Locale.getDefault()).parse(compareValue);
                        compareBigDecimal = new BigDecimal(number.doubleValue());
                    } else {
                        compareBigDecimal = new BigDecimal(compareValue);
                    }
                }
            }
            catch (Exception number) {
                // empty catch block
            }
            if (compareBigDecimal == null) {
                return difference;
            }
            if (null != initialBigDecimal && null != compareBigDecimal && initialBigDecimal.compareTo(BigDecimal.ZERO) == 0 && compareBigDecimal.compareTo(BigDecimal.ZERO) == 0) {
                return difference;
            }
            if (null != initialBigDecimal && null != compareBigDecimal && initialBigDecimal.compareTo(compareBigDecimal) == 0) {
                return difference;
            }
            if (null == initialBigDecimal && null != compareBigDecimal) {
                difference = compareBigDecimal.toString();
            } else if (null == initialBigDecimal && null != compareBigDecimal) {
                difference = compareBigDecimal.toString();
            } else if (null != initialBigDecimal && null == compareBigDecimal) {
                difference = "-" + initialBigDecimal.toString();
            } else {
                BigDecimal differenceBigDecimal = compareBigDecimal.subtract(initialBigDecimal);
                if (differenceBigDecimal != null) {
                    difference = differenceBigDecimal.toString();
                }
            }
        }
        return difference;
    }
}

