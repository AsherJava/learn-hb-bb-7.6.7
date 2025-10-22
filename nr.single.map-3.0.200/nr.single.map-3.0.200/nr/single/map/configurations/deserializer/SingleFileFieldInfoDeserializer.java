/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonParser
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.DeserializationContext
 *  com.fasterxml.jackson.databind.JsonDeserializer
 *  com.fasterxml.jackson.databind.JsonNode
 *  com.jiuqi.np.definition.common.FieldType
 */
package nr.single.map.configurations.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.jiuqi.np.definition.common.FieldType;
import java.io.IOException;
import nr.single.map.data.internal.SingleFileFieldInfoImpl;

public class SingleFileFieldInfoDeserializer
extends JsonDeserializer<SingleFileFieldInfoImpl> {
    private static final String JSON_FIELD_CODE = "fieldCode";
    private static final String JSON_FIELD_TYPE = "fieldType";
    private static final String JSON_FIELD_SIZE = "fieldSize";
    private static final String JSON_FIELD_DECIMAL = "fieldDecimal";
    private static final String JSON_TABLE_CODE = "tableCode";
    private static final String JSON_FORM_CODE = "formCode";
    private static final String JSON_ENUM_CODE = "enumCode";
    private static final String JSON_DEFAULT_VALUE = "defaultValue";
    private static final String JSON_NET_TABLE_CODE = "netTableCode";
    private static final String JSON_NET_FIELD_CODE = "netFieldCode";
    private static final String JSON_NET_FIELD_KEY = "netFieldKey";
    private static final String JSON_NET_DATALINK_KEY = "netDataLinkKey";
    private static final String JSON_NET_FORM_CODE = "netFormCode";
    private static final String JSON_FLOAT_ENUM_TYPE = "floatEnumType";
    private static final String JSON_FLOAT_ENUM_CODE = "floatEnumCode";
    private static final String JSON_FLOAT_ENUM_ORDER = "floatEnumOrder";
    private static final String JSON_REGION_KEY = "regionKey";
    private static final String JSON_REGION_CODE = "regionCode";

    public SingleFileFieldInfoImpl deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        JsonNode jNode = (JsonNode)p.getCodec().readTree(p);
        SingleFileFieldInfoImpl impl = new SingleFileFieldInfoImpl();
        JsonNode target = jNode.get(JSON_FIELD_CODE);
        String fieldCode = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.get(JSON_FIELD_TYPE);
        FieldType fieldType = target != null && !target.isNull() ? FieldType.valueOf((String)target.asText()) : FieldType.FIELD_TYPE_STRING;
        target = jNode.get(JSON_FIELD_SIZE);
        int fieldSize = target != null && !target.isNull() ? target.asInt() : 0;
        target = jNode.get(JSON_FIELD_DECIMAL);
        int fieldDecimal = target != null && !target.isNull() ? target.asInt() : 0;
        target = jNode.get(JSON_TABLE_CODE);
        String tableCode = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.get(JSON_FORM_CODE);
        String formCode = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.get(JSON_ENUM_CODE);
        String enumCode = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.get(JSON_DEFAULT_VALUE);
        String defaultValue = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.get(JSON_NET_TABLE_CODE);
        String netTableCode = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.get(JSON_NET_FIELD_CODE);
        String netFieldCode = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.get(JSON_NET_FIELD_KEY);
        String netFieldKey = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.get(JSON_NET_DATALINK_KEY);
        String netDataLinkKey = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.get(JSON_NET_FORM_CODE);
        String netFormCode = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.get(JSON_FLOAT_ENUM_CODE);
        String floatEnumCode = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.get(JSON_FLOAT_ENUM_TYPE);
        Integer floatEnumType = target != null && !target.isNull() ? Integer.valueOf(target.asInt()) : null;
        target = jNode.get(JSON_FLOAT_ENUM_ORDER);
        Integer floatEnumOrder = target != null && !target.isNull() ? Integer.valueOf(target.asInt()) : null;
        target = jNode.get(JSON_REGION_KEY);
        String regionKey = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.get(JSON_REGION_CODE);
        String regionCode = target != null && !target.isNull() ? target.asText() : null;
        impl.setFieldCode(fieldCode);
        impl.setFieldType(fieldType);
        impl.setFieldSize(fieldSize);
        impl.setFieldDecimal(fieldDecimal);
        impl.setTableCode(tableCode);
        impl.setFormCode(formCode);
        impl.setEnumCode(enumCode);
        impl.setDefaultValue(defaultValue);
        impl.setNetTableCode(netTableCode);
        impl.setNetFieldCode(netFieldCode);
        impl.setFloatEnumCode(floatEnumCode);
        impl.setFloatEnumOrder(floatEnumOrder);
        impl.setFloatEnumType(floatEnumType);
        if (!"".equals(netFieldKey) && netFieldKey != null) {
            impl.setNetFieldKey(netFieldKey);
        }
        if (!"".equals(netDataLinkKey) && netDataLinkKey != null) {
            impl.setNetDataLinkKey(netDataLinkKey);
        }
        impl.setNetFormCode(netFormCode);
        impl.setRegionKey(regionKey);
        impl.setRegionCode(regionCode);
        return impl;
    }
}

