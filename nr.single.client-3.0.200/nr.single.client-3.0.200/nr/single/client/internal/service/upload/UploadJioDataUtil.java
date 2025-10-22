/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonParseException
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.DeserializationFeature
 *  com.fasterxml.jackson.databind.JsonMappingException
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.definition.common.Consts$EntityField
 *  com.jiuqi.np.definition.common.FieldType
 *  com.jiuqi.np.definition.common.FieldValueType
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.dataentry.bean.UploadParam
 *  com.jiuqi.nr.jtable.params.base.FieldData
 *  nr.single.map.data.TaskDataContext
 *  nr.single.map.data.bean.JioImportParam
 *  nr.single.map.data.bean.RepeatImportParam
 *  nr.single.map.data.facade.SingleFileFieldInfo
 *  nr.single.map.data.facade.dataset.ReportRegionDataSet
 *  nr.single.map.data.facade.dataset.ReportRegionDataSetList
 */
package nr.single.client.internal.service.upload;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.definition.common.Consts;
import com.jiuqi.np.definition.common.FieldType;
import com.jiuqi.np.definition.common.FieldValueType;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.dataentry.bean.UploadParam;
import com.jiuqi.nr.jtable.params.base.FieldData;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import nr.single.map.data.TaskDataContext;
import nr.single.map.data.bean.JioImportParam;
import nr.single.map.data.bean.RepeatImportParam;
import nr.single.map.data.facade.SingleFileFieldInfo;
import nr.single.map.data.facade.dataset.ReportRegionDataSet;
import nr.single.map.data.facade.dataset.ReportRegionDataSetList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UploadJioDataUtil {
    private static final Logger logger = LoggerFactory.getLogger(UploadJioDataUtil.class);

    public static Map<String, DimensionValue> getNewDimensionSet(Map<String, DimensionValue> dimensionValueSet) {
        HashMap<String, DimensionValue> dimensionSet = new HashMap<String, DimensionValue>();
        for (String code : dimensionValueSet.keySet()) {
            DimensionValue value = new DimensionValue();
            value.setName(code);
            value.setValue(dimensionValueSet.get(code) != null ? dimensionValueSet.get(code).getValue() : "");
            dimensionSet.put(value.getName(), value);
        }
        return dimensionSet;
    }

    public static RepeatImportParam getRepeatImportParam(UploadParam param, String section) throws IllegalAccessException, InvocationTargetException {
        RepeatImportParam jioRepeatParam = null;
        Object o = param.getVariableMap().get(section);
        if (o != null) {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            jioRepeatParam = (RepeatImportParam)objectMapper.convertValue(o, RepeatImportParam.class);
        }
        return jioRepeatParam;
    }

    public static RepeatImportParam getNetRepeatImportParam(UploadParam param, String section) throws JsonProcessingException {
        RepeatImportParam jioRepeatParam = null;
        Object o = param.getVariableMap().get(section);
        if (o != null) {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            jioRepeatParam = (RepeatImportParam)objectMapper.readValue((String)o, RepeatImportParam.class);
        }
        return jioRepeatParam;
    }

    public static JioImportParam getJIOImportParam(UploadParam param, String section) throws IllegalAccessException, InvocationTargetException {
        JioImportParam jioImportParam = null;
        Object o = param.getVariableMap().get(section);
        if (o != null) {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            jioImportParam = (JioImportParam)objectMapper.convertValue(o, JioImportParam.class);
        }
        return jioImportParam;
    }

    public static void setImportRowValueByFields(TaskDataContext context, List<FieldData> fields, Object[] rowDatas, ReportRegionDataSetList dataSets, String zdm) throws JsonParseException, JsonMappingException, IOException {
        Map entityZdmKeyMap = context.getEntityZdmKeyMap();
        for (int i = 0; i < fields.size(); ++i) {
            FieldData netField = fields.get(i);
            String fieldName = netField.getFieldCode();
            String fieldFlag = netField.getTableName() + "." + fieldName;
            String fieldValue = "";
            ReportRegionDataSet dataSet = null;
            int negativeLen = 0;
            int decimalLen = 0;
            if (netField.getFieldValueType() == FieldValueType.FIELD_VALUE_BIZKEY_ORDER.getValue()) {
                fieldValue = UUID.randomUUID().toString();
            } else if (!dataSets.getFieldDataSetMap().containsKey(fieldFlag)) {
                fieldValue = "IMPORT_INVALID_DATA";
            } else {
                dataSet = (ReportRegionDataSet)dataSets.getFieldDataSetMap().get(fieldFlag);
                if (Consts.EntityField.ENTITY_FIELD_PARENTKEY.fieldKey.equals(fieldName)) {
                    String fjd;
                    fieldValue = "IMPORT_INVALID_DATA";
                    Object obj = dataSet.getCurDataRow().getValue("SYS_FJD");
                    if (null != obj && entityZdmKeyMap.containsKey(fjd = obj.toString())) {
                        fieldValue = (String)entityZdmKeyMap.get(fjd);
                    }
                } else {
                    SingleFileFieldInfo field = (SingleFileFieldInfo)dataSet.getFieldMap().get(fieldFlag);
                    fieldName = field.getFieldCode();
                    Object obj = dataSet.getCurDataRow().getValue(fieldName);
                    if (null != obj) {
                        fieldValue = obj.toString();
                    }
                    String enumFlag = field.getEnumCode();
                    String newFieldValue = context.getMapingCache().getEnumNetItemCodeFromItem(enumFlag, fieldValue);
                    if (StringUtils.isNotEmpty((String)newFieldValue)) {
                        fieldValue = newFieldValue;
                    }
                    if (StringUtils.isNotEmpty((String)fieldValue)) {
                        FieldType type = FieldType.forValue((int)netField.getFieldType());
                        if (type == FieldType.FIELD_TYPE_FLOAT || type == FieldType.FIELD_TYPE_DECIMAL) {
                            if (fieldValue.startsWith("-")) {
                                negativeLen = 1;
                            }
                            if (fieldValue.contains(".")) {
                                decimalLen = 1;
                                if (fieldValue.length() > field.getFieldSize()) {
                                    String numCode1 = fieldValue.substring(0, fieldValue.indexOf("."));
                                    String numCode2 = fieldValue.substring(fieldValue.indexOf(".") + 1, fieldValue.length());
                                    if (StringUtils.isNotEmpty((String)numCode1) && numCode1.length() > netField.getFieldSize() - netField.getFractionDigits() + negativeLen) {
                                        numCode1 = numCode1.substring(0, netField.getFieldSize() - netField.getFractionDigits() + negativeLen);
                                    }
                                    if (StringUtils.isNotEmpty((String)numCode2) && numCode2.length() > netField.getFractionDigits()) {
                                        numCode2 = numCode2.substring(0, netField.getFractionDigits());
                                    }
                                    fieldValue = numCode1 + "." + numCode2;
                                }
                            } else if (fieldValue.length() > netField.getFieldSize() - netField.getFractionDigits() + negativeLen && netField.getFieldSize() - netField.getFractionDigits() > 0) {
                                fieldValue = fieldValue.substring(0, netField.getFieldSize() - netField.getFractionDigits() + negativeLen);
                            }
                        } else if (type == FieldType.FIELD_TYPE_INTEGER && fieldValue.length() > netField.getFieldSize()) {
                            if (fieldValue.startsWith("-")) {
                                negativeLen = 1;
                            }
                            fieldValue = fieldValue.substring(0, netField.getFieldSize() + negativeLen);
                        }
                    }
                }
            }
            if (StringUtils.isNotEmpty((String)fieldValue) && fieldValue.length() > netField.getFieldSize() && netField.getFieldSize() > 0 && !"IMPORT_INVALID_DATA".equalsIgnoreCase(fieldValue)) {
                int newLen = netField.getFieldSize() + negativeLen + decimalLen;
                if (fieldValue.length() > newLen) {
                    String oldFieldValue = fieldValue;
                    fieldValue = fieldValue.substring(0, newLen);
                    if (logger.isDebugEnabled()) {
                        logger.debug(fieldName + "=" + oldFieldValue, (Object)(",\u622a\u53d6\u540e\uff1a" + fieldValue + ",\u4fdd\u7559\u957f\u5ea6=" + newLen));
                    }
                }
            }
            rowDatas[i] = fieldValue;
        }
    }
}

