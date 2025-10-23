/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.common.UUIDUtils
 *  com.jiuqi.nr.datascheme.api.type.DataFieldGatherType
 *  com.jiuqi.nr.task.api.common.Constants$DataStatus
 *  com.jiuqi.util.OrderGenerator
 */
package com.jiuqi.nr.task.form.service.impl;

import com.jiuqi.np.definition.common.UUIDUtils;
import com.jiuqi.nr.datascheme.api.type.DataFieldGatherType;
import com.jiuqi.nr.task.api.common.Constants;
import com.jiuqi.nr.task.form.controller.dto.ReverseModeRegionDTO;
import com.jiuqi.nr.task.form.field.dto.DataFieldSettingDTO;
import com.jiuqi.nr.task.form.link.dto.FormatDTO;
import com.jiuqi.nr.task.form.service.IReverseModelingExecutor;
import com.jiuqi.util.OrderGenerator;
import java.util.List;
import java.util.Set;

public abstract class AbstractReverseModelingExecutor
implements IReverseModelingExecutor {
    public static final String UNDERLINE = "_";

    protected String judgeFieldCode(Set<String> fieldCodes, String tableCodePrefix, int start) {
        StringBuilder build = new StringBuilder(tableCodePrefix).append(UNDERLINE).append(this.fillIndexNum(start));
        while (fieldCodes.contains(build.toString())) {
            build = new StringBuilder(tableCodePrefix).append(UNDERLINE).append(this.fillIndexNum(++start));
        }
        fieldCodes.add(build.toString());
        return build.toString();
    }

    protected String judgeTableTitle(Set<String> tableTitles, ReverseModeRegionDTO region, String form) {
        return this.judgeTableCode(tableTitles, form);
    }

    protected String judgeTableCode(Set<String> tableCodes, String code) {
        int start = 1;
        StringBuilder builder = new StringBuilder(code).append(UNDERLINE).append(start);
        if (tableCodes.contains(code)) {
            while (tableCodes.contains(builder.toString())) {
                builder = new StringBuilder(code).append(UNDERLINE).append(++start);
            }
        } else {
            tableCodes.add(code);
            return code;
        }
        tableCodes.add(builder.toString());
        return builder.toString();
    }

    protected String fillIndexNum(int i) {
        StringBuilder originBuilder = new StringBuilder().append(i);
        while (originBuilder.length() < 3) {
            originBuilder.insert(0, "0");
        }
        return originBuilder.toString();
    }

    protected String formatSchemePrefix(String schemePrefix) {
        return schemePrefix == null ? "" : schemePrefix + UNDERLINE;
    }

    protected void initField(List<DataFieldSettingDTO> list, DataFieldSettingDTO fieldVO, ReverseModeRegionDTO region, int fieldType) {
        fieldVO.setDataFieldType(fieldType);
        fieldVO.setKey(UUIDUtils.getKey());
        fieldVO.setDataTableKey(region.getTableKey());
        fieldVO.setState(Constants.DataStatus.NONE.ordinal());
        fieldVO.setDataFieldKind(region.getFieldKind());
        fieldVO.setNullable(true);
        fieldVO.setDataFieldGatherType(DataFieldGatherType.NONE.getValue());
        fieldVO.setUseAuthority(false);
        fieldVO.setOrder(OrderGenerator.newOrder());
        this.initFieldByType(fieldVO, fieldType);
        list.add(fieldVO);
    }

    protected void initFieldByType(DataFieldSettingDTO field, int fieldType) {
        field.setFieldFormat(new FormatDTO());
        switch (fieldType) {
            case 6: {
                field.setDecimal(0);
                field.setPrecision(50);
                field.setMeasureUnit(null);
                field.setAllowMultipleSelect(false);
                break;
            }
            case 10: {
                field.setDecimal(2);
                field.setPrecision(20);
                field.setRefDataFieldKey(null);
                field.setAllowMultipleSelect(null);
                field.setAllowUndefinedCode(null);
                field.setDataFieldGatherType(DataFieldGatherType.SUM.getValue());
                field.setMeasureUnit("YUAN");
                break;
            }
            case 5: {
                field.setDecimal(0);
                field.setPrecision(10);
                field.setDataFieldGatherType(DataFieldGatherType.SUM.getValue());
                field.setMeasureUnit("YUAN");
                field.setRefDataFieldKey(null);
                field.setAllowMultipleSelect(null);
                field.setAllowUndefinedCode(null);
                break;
            }
            default: {
                field.setPrecision(0);
                field.setMeasureUnit(null);
                field.setDecimal(0);
                field.setRefDataFieldKey(null);
                field.setAllowMultipleSelect(false);
                field.setAllowUndefinedCode(null);
            }
        }
    }

    protected String rmPrefix(String schemePrefix, String tableCode) {
        if (schemePrefix == null) {
            return tableCode;
        }
        if (tableCode.startsWith(schemePrefix)) {
            return tableCode.replaceFirst(schemePrefix + UNDERLINE, "");
        }
        return tableCode;
    }
}

