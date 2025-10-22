/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonParser
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.fasterxml.jackson.databind.DeserializationContext
 *  com.fasterxml.jackson.databind.JavaType
 *  com.fasterxml.jackson.databind.JsonDeserializer
 *  com.fasterxml.jackson.databind.JsonNode
 *  com.fasterxml.jackson.databind.Module
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.fasterxml.jackson.databind.module.SimpleModule
 *  com.fasterxml.jackson.databind.type.TypeFactory
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.nvwa.grid2.Grid2Data
 */
package com.jiuqi.nr.designer.paramlanguage.vo;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.designer.common.Grid2DataDeserializeGege;
import com.jiuqi.nr.designer.paramlanguage.vo.BigDataSaveObject;
import com.jiuqi.nr.designer.paramlanguage.vo.RegionTabSettingObject;
import com.jiuqi.nvwa.grid2.Grid2Data;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class BigDataSaveObjectDeserializer
extends JsonDeserializer<BigDataSaveObject> {
    public BigDataSaveObject deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        String fillingGuide;
        JsonNode fillingGuideData;
        String language;
        JsonNode languageData;
        String regionTabSetting;
        JsonNode regionTabSettingData;
        String grid;
        BigDataSaveObject bigDataSaveObject = new BigDataSaveObject();
        ObjectMapper mapper = (ObjectMapper)p.getCodec();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(Grid2Data.class, (JsonDeserializer)new Grid2DataDeserializeGege());
        mapper.registerModule((Module)module);
        JsonNode node = (JsonNode)mapper.readTree(p);
        JsonNode grid2Data = node.get("grid2Data");
        if (grid2Data != null && StringUtils.isNotEmpty((String)(grid = grid2Data.toString()))) {
            Map gridData = (Map)mapper.readValue(grid, (TypeReference)new TypeReference<Map<String, Grid2Data>>(){});
            bigDataSaveObject.setGrid2Data(gridData);
        }
        if ((regionTabSettingData = node.get("RegionTabSettingData")) != null && StringUtils.isNotEmpty((String)(regionTabSetting = regionTabSettingData.toString()))) {
            TypeFactory typeFactory = mapper.getTypeFactory();
            Map regionTabSettingMap = (Map)mapper.readValue(regionTabSetting, (JavaType)typeFactory.constructMapType(Map.class, typeFactory.constructType(String.class), (JavaType)typeFactory.constructCollectionType(List.class, RegionTabSettingObject.class)));
            bigDataSaveObject.setRegionTabSettingData(regionTabSettingMap);
        }
        if ((languageData = node.get("language")) != null && StringUtils.isNotEmpty((String)(language = languageData.textValue()))) {
            bigDataSaveObject.setLanguage(language);
        }
        if (null != (fillingGuideData = node.get("fillingGuideData")) && StringUtils.isNotEmpty((String)(fillingGuide = fillingGuideData.toString()))) {
            TypeFactory typeFactory = mapper.getTypeFactory();
            Map fillingGuideMap = (Map)mapper.readValue(fillingGuide, (JavaType)typeFactory.constructMapType(Map.class, typeFactory.constructType(String.class), typeFactory.constructType(String.class)));
            bigDataSaveObject.setFillingGuideData(fillingGuideMap);
        }
        return bigDataSaveObject;
    }
}

