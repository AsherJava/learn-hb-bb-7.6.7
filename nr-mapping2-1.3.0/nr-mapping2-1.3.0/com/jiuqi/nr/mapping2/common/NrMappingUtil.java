/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.nvwa.mapping.bean.MappingScheme
 *  com.jiuqi.nvwa.mapping.common.MappingUtil
 *  org.json.JSONObject
 */
package com.jiuqi.nr.mapping2.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.nr.mapping2.dto.NrMappingSchemeDTO;
import com.jiuqi.nr.mapping2.provider.NrMappingParam;
import com.jiuqi.nvwa.mapping.bean.MappingScheme;
import com.jiuqi.nvwa.mapping.common.MappingUtil;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

public class NrMappingUtil {
    private static final Logger logger = LoggerFactory.getLogger(NrMappingUtil.class);

    public static NrMappingParam getNrMappingParam(MappingScheme scheme) {
        return NrMappingUtil.getNrMappingParam(MappingUtil.getMappingSchemeProperty((MappingScheme)scheme, (String)"NrTask"));
    }

    public static NrMappingParam getNrMappingParam(String nrTask) {
        NrMappingParam nrMappingParam = null;
        if (!StringUtils.hasText(nrTask)) {
            return null;
        }
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            nrMappingParam = (NrMappingParam)objectMapper.readValue(nrTask, NrMappingParam.class);
        }
        catch (JsonProcessingException e) {
            logger.info("\u83b7\u53d6nr\u6620\u5c04\u65b9\u6848\u53c2\u6570\u5f02\u5e38\uff0c\u53c2\u6570={}", (Object)nrTask, (Object)e);
        }
        return nrMappingParam;
    }

    public static void updateSchemeNrParam(MappingScheme scheme, NrMappingParam nrParam) {
        String ext = scheme.getExtParam();
        JSONObject jsonObject = null;
        jsonObject = StringUtils.hasText(ext) ? new JSONObject(ext) : new JSONObject();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            if (!StringUtils.hasText(nrParam.getType())) {
                logger.info("\u6620\u5c04\u65b9\u6848\u7c7b\u578b\u4e3a\u7a7a\uff0c\u65b9\u6848code\uff1a{} \uff0c\u65b9\u6848\u6807\u9898\uff1a{} \uff0c\u65b9\u6848\u5386\u53f2extParam\uff1a{}", scheme.getCode(), scheme.getTitle(), scheme.getExtParam());
            }
            jsonObject.put("NrTask", (Object)objectMapper.writeValueAsString((Object)nrParam));
            scheme.setExtParam(jsonObject.toString());
        }
        catch (JsonProcessingException e) {
            logger.info("\u66f4\u65b0\u6620\u5c04\u65b9\u6848nr\u53c2\u6570\u5931\u8d25\uff0c\u65b9\u6848code\uff1a{} \uff0c\u65b9\u6848\u6807\u9898\uff1a{} \uff0c\u65b9\u6848\u5386\u53f2extParam\uff1a{}", new Object[]{scheme.getCode(), scheme.getTitle(), scheme.getExtParam(), e});
        }
    }

    public static List<String> getJioSources() {
        ArrayList<String> res = new ArrayList<String>();
        res.add("ORG");
        res.add("BASEDATA");
        res.add("NR-MAPPING-FACTORY");
        return res;
    }

    public static MappingScheme parseNRMappingToNvwa(NrMappingSchemeDTO dto) {
        MappingScheme scheme = new MappingScheme();
        BeanUtils.copyProperties((Object)dto, scheme);
        if (StringUtils.hasText(dto.getTask()) && StringUtils.hasText(dto.getFormScheme())) {
            NrMappingParam nrMappingParam = new NrMappingParam();
            nrMappingParam.setTaskKey(dto.getTask());
            nrMappingParam.setFormSchemeKey(dto.getFormScheme());
            nrMappingParam.setType(dto.getType());
            NrMappingUtil.updateSchemeNrParam(scheme, nrMappingParam);
        }
        return scheme;
    }
}

