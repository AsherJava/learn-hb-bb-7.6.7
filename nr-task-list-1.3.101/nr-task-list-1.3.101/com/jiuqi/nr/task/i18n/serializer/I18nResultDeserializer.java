/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JacksonException
 *  com.fasterxml.jackson.core.JsonParser
 *  com.fasterxml.jackson.databind.DeserializationContext
 *  com.fasterxml.jackson.databind.JsonDeserializer
 *  com.fasterxml.jackson.databind.JsonNode
 *  com.fasterxml.jackson.databind.ObjectMapper
 */
package com.jiuqi.nr.task.i18n.serializer;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.nr.task.i18n.bean.dto.I18nBaseDTO;
import com.jiuqi.nr.task.i18n.bean.vo.I18nResultVO;
import com.jiuqi.nr.task.i18n.common.I18nResourceType;
import com.jiuqi.nr.task.i18n.factory.I18nWorkShopFactory;
import java.io.IOException;
import java.util.ArrayList;

public class I18nResultDeserializer
extends JsonDeserializer<I18nResultVO> {
    public I18nResultVO deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
        ObjectMapper mapper = (ObjectMapper)p.getCodec();
        JsonNode node = (JsonNode)mapper.readTree(p);
        I18nResultVO resultVO = new I18nResultVO();
        if (node.has("languageType")) {
            resultVO.setLanguageType(node.get("languageType").textValue());
        }
        if (node.has("resourceType")) {
            String resourceTypeVal = node.get("resourceType").textValue();
            resultVO.setResourceType(resourceTypeVal);
            I18nResourceType resourceType = I18nResourceType.valueOf(Integer.valueOf(resourceTypeVal));
            if (node.has("datas")) {
                ArrayList<I18nBaseDTO> trulyDatas = new ArrayList<I18nBaseDTO>();
                JsonNode datas = node.get("datas");
                if (datas.isArray()) {
                    for (JsonNode jsonNode : datas) {
                        trulyDatas.add(I18nWorkShopFactory.getTrulyDTO(resourceType, jsonNode));
                    }
                }
                resultVO.setDatas(trulyDatas);
            }
        }
        return resultVO;
    }
}

