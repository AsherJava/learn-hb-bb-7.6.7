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
 *  com.fasterxml.jackson.databind.node.ArrayNode
 */
package nr.single.map.configurations.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import nr.single.map.configurations.bean.AutoAppendCode;
import nr.single.map.configurations.bean.CompleteUser;
import nr.single.map.configurations.bean.DataImportRule;
import nr.single.map.configurations.bean.MappingConfig;
import nr.single.map.configurations.bean.SkipUnit;
import nr.single.map.configurations.bean.UpdateWay;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MappingConfigDeserializer
extends JsonDeserializer<MappingConfig> {
    private static final Logger logger = LoggerFactory.getLogger(MappingConfigDeserializer.class);

    public MappingConfig deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        List<String> ari;
        ObjectMapper mapper = (ObjectMapper)p.getCodec();
        JsonNode jNode = (JsonNode)p.getCodec().readTree(p);
        MappingConfig config = new MappingConfig();
        JsonNode target = jNode.get("arithmetic");
        if (target != null && target.isArray()) {
            ari = this.readListAsString(p, mapper, (ArrayNode)target);
            config.setArithmetic(ari);
        }
        if ((target = jNode.get("examine")) != null && target.isArray()) {
            ari = this.readListAsString(p, mapper, (ArrayNode)target);
            config.setExamine(ari);
        }
        boolean uploadStatus = (target = jNode.get("uploadStatus")) != null && !target.isNull() ? target.asBoolean() : false;
        target = jNode.get("forceUpload");
        boolean forceUpload = target != null && !target.isNull() ? target.asBoolean() : false;
        target = jNode.get("checkParent");
        boolean checkParent = target != null && !target.isNull() ? target.asBoolean() : false;
        target = jNode.get("uploadEntityAndData");
        boolean uploadEntityAndData = target != null && !target.isNull() ? target.asBoolean() : false;
        target = jNode.get("unitUpdateWay");
        if (target != null && !target.isNull()) {
            UpdateWay ari2 = (UpdateWay)mapper.readValue(target.traverse(p.getCodec()), UpdateWay.class);
            config.setUnitUpdateWay(ari2);
        }
        if ((target = jNode.get("skipUnit")) != null && !target.isNull()) {
            SkipUnit skipUnit = (SkipUnit)mapper.readValue(target.traverse(p.getCodec()), SkipUnit.class);
            config.setSkipUnit(skipUnit);
        }
        if ((target = jNode.get("autoAppendCode")) != null && !target.isNull()) {
            AutoAppendCode appendCodeConfig = (AutoAppendCode)mapper.readValue(target.traverse(p.getCodec()), AutoAppendCode.class);
            config.setAutoAppendCode(appendCodeConfig);
        }
        if ((target = jNode.get("dataImportRule")) != null && !target.isNull()) {
            DataImportRule importRule = (DataImportRule)mapper.readValue(target.traverse(p.getCodec()), DataImportRule.class);
            config.setDataImportRule(importRule);
        }
        if ((target = jNode.get("completeUser")) != null && !target.isNull()) {
            CompleteUser completeUser = (CompleteUser)((Object)mapper.readValue(target.traverse(p.getCodec()), CompleteUser.class));
            config.setCompleteUser(completeUser);
        }
        config.setUploadStatus(uploadStatus);
        config.setForceUpload(forceUpload);
        config.setCheckParent(checkParent);
        config.setUploadEntityAndData(uploadEntityAndData);
        return config;
    }

    private List<String> readListAsString(JsonParser p, ObjectMapper mapper, ArrayNode target) {
        ArrayNode arr = target;
        ArrayList<String> ari = new ArrayList<String>();
        arr.forEach(e -> {
            try {
                String unit = (String)mapper.readValue(e.traverse(p.getCodec()), String.class);
                ari.add(unit);
            }
            catch (IOException e1) {
                logger.error(e1.getMessage(), e1);
            }
        });
        return ari;
    }
}

