/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.JavaType
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.np.definition.common.DimensionFilterListType
 *  com.jiuqi.np.definition.common.DimensionFilterType
 *  com.jiuqi.np.definition.common.FormulaCheckType
 *  com.jiuqi.np.definition.internal.impl.FormatProperties
 *  com.jiuqi.np.logging.LogFactory
 *  com.jiuqi.np.logging.Logger
 *  com.jiuqi.np.period.PeriodType
 */
package com.jiuqi.nr.definition.internal.db;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.np.definition.common.DimensionFilterListType;
import com.jiuqi.np.definition.common.DimensionFilterType;
import com.jiuqi.np.definition.common.FormulaCheckType;
import com.jiuqi.np.definition.internal.impl.FormatProperties;
import com.jiuqi.np.logging.LogFactory;
import com.jiuqi.np.logging.Logger;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.nr.definition.common.DataLinkEditMode;
import com.jiuqi.nr.definition.common.DataLinkType;
import com.jiuqi.nr.definition.common.DataRegionKind;
import com.jiuqi.nr.definition.common.EnumDisplayMode;
import com.jiuqi.nr.definition.common.FormType;
import com.jiuqi.nr.definition.common.FormViewType;
import com.jiuqi.nr.definition.common.FormulaSchemeDisplayMode;
import com.jiuqi.nr.definition.common.FormulaSchemeMenuApply;
import com.jiuqi.nr.definition.common.FormulaSchemeType;
import com.jiuqi.nr.definition.common.FormulaSyntaxStyle;
import com.jiuqi.nr.definition.common.PageSize;
import com.jiuqi.nr.definition.common.PeriodMatchingType;
import com.jiuqi.nr.definition.common.RegionEnterNext;
import com.jiuqi.nr.definition.common.SubmitCheckStrategy;
import com.jiuqi.nr.definition.common.TaskGatherType;
import com.jiuqi.nr.definition.common.TaskLinkExpressionType;
import com.jiuqi.nr.definition.common.TaskLinkMatchingType;
import com.jiuqi.nr.definition.common.TaskType;
import com.jiuqi.nr.definition.internal.impl.FillDateType;
import com.jiuqi.nr.definition.internal.impl.MouldDataDefineImpl;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.util.StringUtils;

public class TransUtil {
    private static Logger logger = LogFactory.getLogger(TransUtil.class);

    public FormViewType transFormViewType(Integer type) {
        return FormViewType.forValue(type);
    }

    public Integer transFormViewType(FormViewType type) {
        return type.getValue();
    }

    public Integer transBooleanThreeStates(Boolean b) {
        if (b == null) {
            return -1;
        }
        if (b.booleanValue()) {
            return 1;
        }
        return 0;
    }

    public Boolean transBooleanThreeStates(Integer b) {
        if (b == -1) {
            return null;
        }
        if (b == 1) {
            return true;
        }
        return false;
    }

    public FormType transFormType(Integer type) {
        return FormType.forValue(type);
    }

    public Integer transFormType(FormType type) {
        return type.getValue();
    }

    public TaskType transTaskType(Integer type) {
        return TaskType.forValue(type);
    }

    public Integer transTaskType(TaskType type) {
        return type.getValue();
    }

    public PeriodType transPeriodType(Integer type) {
        return PeriodType.fromType((int)type);
    }

    public Integer transPeriodType(PeriodType type) {
        return type == null ? PeriodType.YEAR.type() : type.type();
    }

    public DataRegionKind transDataRegionKind(Integer type) {
        return DataRegionKind.forValue(type);
    }

    public Integer transDataRegionKind(DataRegionKind type) {
        return type.getValue();
    }

    public RegionEnterNext transRegionEnterNext(Integer type) {
        return RegionEnterNext.forValue(type);
    }

    public Integer transRegionEnterNext(RegionEnterNext type) {
        return type.getValue();
    }

    public DataLinkEditMode transDataLinkEditMode(Integer type) {
        return DataLinkEditMode.forValue(type);
    }

    public Integer transDataLinkEditMode(DataLinkEditMode type) {
        return type.getValue();
    }

    public EnumDisplayMode transEnumDisplayMode(Integer type) {
        return EnumDisplayMode.forValue(type);
    }

    public Integer transEnumDisplayMode(EnumDisplayMode type) {
        return type.getValue();
    }

    public FormulaCheckType transFormulaCheckType(Integer type) {
        return FormulaCheckType.forValue((int)type);
    }

    public Integer transFormulaCheckType(FormulaCheckType type) {
        return type.getValue();
    }

    public SubmitCheckStrategy transSubmitCheckStrategy(Integer type) {
        return SubmitCheckStrategy.forValue(type);
    }

    public Integer transSubmitCheckStrategy(SubmitCheckStrategy type) {
        return type.getValue();
    }

    public FormulaSchemeType transFormulaSchemeType(Integer type) {
        return FormulaSchemeType.forValue(type);
    }

    public Integer transFormulaSchemeType(FormulaSchemeType type) {
        return type.getValue();
    }

    public FormulaSchemeDisplayMode transFormulaSchemeDisplayMode(Integer displayMode) {
        return FormulaSchemeDisplayMode.forValue(displayMode);
    }

    public Integer transFormulaSchemeDisplayMode(FormulaSchemeDisplayMode displayMode) {
        return displayMode.getValue();
    }

    public TaskGatherType transTaskGatherType(Integer type) {
        return TaskGatherType.forValue(type);
    }

    public Integer transTaskGatherType(TaskGatherType type) {
        return type.getValue();
    }

    public FormulaSyntaxStyle transFormulaSyntaxStyle(Integer type) {
        return FormulaSyntaxStyle.forValue(type);
    }

    public Integer transFormulaSyntaxStyle(FormulaSyntaxStyle type) {
        return type.getValue();
    }

    public TaskLinkMatchingType transTaskLinkMatchingType(Integer type) {
        return TaskLinkMatchingType.forValue(type);
    }

    public Integer transTaskLinkMatchingType(TaskLinkMatchingType type) {
        return type.getValue();
    }

    public PeriodMatchingType transPeriodMatchingType(Integer type) {
        return PeriodMatchingType.forValue(type);
    }

    public Integer transPeriodMatchingType(PeriodMatchingType type) {
        return type.getValue();
    }

    public DataLinkType transLinkType(Integer type) {
        return DataLinkType.forValue(type);
    }

    public Integer transLinkType(DataLinkType type) {
        return type.getValue();
    }

    public TaskLinkExpressionType transTaskLinkExpressionType(Integer type) {
        return TaskLinkExpressionType.forValue(type);
    }

    public Integer transTaskLinkExpressionType(TaskLinkExpressionType type) {
        return type.getValue();
    }

    public String transFormatProperties(FormatProperties formatProperties) {
        if (formatProperties != null) {
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                return objectMapper.writeValueAsString((Object)formatProperties);
            }
            catch (JsonProcessingException e) {
                logger.warn("\u5355\u5143\u683c\u663e\u793a\u683c\u5f0f\u5c5e\u6027\u5e8f\u5217\u5316\u5f02\u5e38", (Throwable)e);
            }
        }
        return null;
    }

    public FormatProperties transFormatProperties(String formatProperties) {
        if (!StringUtils.isEmpty(formatProperties)) {
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                return (FormatProperties)objectMapper.readValue(formatProperties, FormatProperties.class);
            }
            catch (JsonProcessingException e) {
                logger.error("\u5355\u5143\u683c\u663e\u793a\u683c\u5f0f\u5c5e\u6027\u53cd\u5e8f\u5217\u5316\u5f02\u5e38", (Throwable)e);
            }
        }
        return null;
    }

    public Map<String, Object> transExtensions(String extensions) {
        if (!StringUtils.isEmpty(extensions)) {
            ObjectMapper objectMapper = new ObjectMapper();
            JavaType javaType = objectMapper.getTypeFactory().constructParametricType(HashMap.class, new Class[]{String.class, Object.class});
            try {
                return (Map)objectMapper.readValue(extensions, javaType);
            }
            catch (JsonProcessingException e) {
                logger.warn("\u62a5\u8868\u6269\u5c55\u5c5e\u6027\u53cd\u5e8f\u5217\u5316\u5f02\u5e38", (Throwable)e);
            }
        }
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("INVALIDFORMAT", extensions);
        return map;
    }

    public String transExtensions(HashMap<String, Object> extensionsProp) {
        if (!StringUtils.isEmpty(extensionsProp)) {
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                return objectMapper.writeValueAsString(extensionsProp);
            }
            catch (JsonProcessingException e) {
                logger.error("\u62a5\u8868\u6269\u5c55\u5c5e\u6027\u5e8f\u5217\u5316\u5f02\u5e38", (Throwable)e);
            }
        }
        return "";
    }

    public MouldDataDefineImpl transMouldExtensions(String mouldExtensions) {
        ObjectMapper objectMapper = new ObjectMapper();
        if (!StringUtils.isEmpty(mouldExtensions)) {
            try {
                return (MouldDataDefineImpl)objectMapper.readValue(mouldExtensions, MouldDataDefineImpl.class);
            }
            catch (JsonProcessingException e) {
                logger.error("\u8d26\u671f\u4fe1\u606f\u53cd\u5e8f\u5217\u5316\u5f02\u5e38", (Throwable)e);
            }
        }
        return new MouldDataDefineImpl();
    }

    public String transMouldExtensions(MouldDataDefineImpl mouldExtensions) {
        if (mouldExtensions != null) {
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                return objectMapper.writeValueAsString((Object)mouldExtensions);
            }
            catch (JsonProcessingException e) {
                logger.error("\u8d26\u671f\u4fe1\u606f\u5e8f\u5217\u5316\u5f02\u5e38", (Throwable)e);
            }
        }
        return "";
    }

    public Integer transDimensionFilterListType(DimensionFilterListType type) {
        return type.ordinal();
    }

    public DimensionFilterListType transDimensionFilterListType(Integer value) {
        return DimensionFilterListType.values()[value];
    }

    public Integer transDimensionFilterType(DimensionFilterType type) {
        return type.ordinal();
    }

    public DimensionFilterType transDimensionFilterType(Integer value) {
        return DimensionFilterType.values()[value];
    }

    public Integer transFilldtetype(FillDateType type) {
        return type.getValue();
    }

    public FillDateType transFilldtetype(Integer value) {
        return FillDateType.fromType(value);
    }

    public PageSize transPageSize(Integer value) {
        return PageSize.valueOf(value);
    }

    public Integer transPageSize(PageSize type) {
        if (null == type) {
            return null;
        }
        return type.getValue();
    }

    public int[] transIntArray(String value) {
        if (!StringUtils.hasText(value)) {
            return null;
        }
        String[] split = value.split(";");
        int[] result = new int[split.length];
        for (int i = 0; i < split.length; ++i) {
            result[i] = Integer.parseInt(split[i]);
        }
        return result;
    }

    public String transIntArray(int[] value) {
        if (null == value) {
            return null;
        }
        return Arrays.stream(value).mapToObj(String::valueOf).collect(Collectors.joining(";"));
    }

    public Short transShort(Integer value) {
        return null == value ? null : Short.valueOf(value.shortValue());
    }

    public Integer transShort(Short value) {
        return null == value ? null : Integer.valueOf(value.intValue());
    }

    public Integer transFormulaSchemeMenuApply(FormulaSchemeMenuApply type) {
        return type == null ? null : Integer.valueOf(type.getValue());
    }

    public FormulaSchemeMenuApply transFormulaSchemeMenuApply(Integer value) {
        return value == null ? null : FormulaSchemeMenuApply.forValue(value);
    }
}

