/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonParser
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.DeserializationContext
 *  com.fasterxml.jackson.databind.JsonDeserializer
 *  com.fasterxml.jackson.databind.JsonNode
 *  com.fasterxml.jackson.databind.ObjectMapper
 */
package nr.single.map.configurations.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import nr.single.map.data.internal.SingleFileFormulaItemImpl;

public class SingleFileFormulaDeserializer
extends JsonDeserializer<SingleFileFormulaItemImpl> {
    private static final String JSON_SINGLE_FORMULA_CODE = "singleFormulaCode";
    private static final String JSON_NET_FORMULA_CODE = "netFormulaCode";
    private static final String JSON_SINGLE_FORMULA_EXP = "singleFormulaExp";
    private static final String JSON_NET_FORMULA_EXP = "netFormulaExp";
    private static final String JSON_NET_FORMULA_KEY = "netFormulaKey";
    private static final String JSON_SINGLE_SCHEME_NAME = "singleSchemeName";
    private static final String JSON_NET_SCHEME_NAME = "netSchemeName";
    private static final String JSON_NET_SCHEME_KEY = "netSchemeKey";
    private static final String JSON_SINGLE_TABLE_CODE = "singleTableCode";
    private static final String JSON_NET_FORM_CODE = "netFormCode";
    private static final String JSON_NET_FORM_KEY = "netFormKey";

    public SingleFileFormulaItemImpl deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        ObjectMapper mapper = (ObjectMapper)p.getCodec();
        JsonNode jNode = (JsonNode)p.getCodec().readTree(p);
        SingleFileFormulaItemImpl impl = new SingleFileFormulaItemImpl();
        JsonNode target = jNode.get(JSON_SINGLE_FORMULA_CODE);
        String singleFormulaCode = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.get(JSON_NET_FORMULA_CODE);
        String netFormulaCode = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.get(JSON_SINGLE_FORMULA_EXP);
        String singleFormulaExp = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.get(JSON_NET_FORMULA_EXP);
        String netFormulaExp = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.get(JSON_NET_FORMULA_KEY);
        String netFormulaKey = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.get(JSON_SINGLE_SCHEME_NAME);
        String singleSchemeName = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.get(JSON_NET_SCHEME_NAME);
        String netSchemeName = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.get(JSON_NET_SCHEME_KEY);
        String netSchemeKey = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.get(JSON_SINGLE_TABLE_CODE);
        String singleTableCode = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.get(JSON_NET_FORM_CODE);
        String netFormCode = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.get(JSON_NET_FORM_KEY);
        String netFormKey = target != null && !target.isNull() ? target.asText() : null;
        impl.setSingleFormulaCode(singleFormulaCode);
        impl.setSingleFormulaExp(singleFormulaExp);
        impl.setNetFormulaCode(netFormulaCode);
        impl.setNetFormulaExp(netFormulaExp);
        if (!"".equals(netFormulaKey) && netFormulaKey != null) {
            impl.setNetFormulaKey(netFormulaKey);
        }
        impl.setSingleSchemeName(singleSchemeName);
        impl.setNetSchemeName(netSchemeName);
        if (!"".equals(netSchemeKey) && netSchemeKey != null) {
            impl.setNetSchemeKey(netSchemeKey);
        }
        impl.setSingleTableCode(singleTableCode);
        impl.setNetFormCode(netFormCode);
        if (!"".equals(netFormKey) && netFormKey != null) {
            impl.setNetFormKey(netFormKey);
        }
        return impl;
    }
}

