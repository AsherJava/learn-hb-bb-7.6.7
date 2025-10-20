/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.JavaType
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.va.basedata.service.BaseDataService
 *  com.jiuqi.va.domain.basedata.BaseDataDO
 *  com.jiuqi.va.domain.basedata.BaseDataDTO
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 */
package com.jiuqi.common.subject.impl.subject.data;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.subject.impl.subject.dto.SubjectDTO;
import com.jiuqi.va.basedata.service.BaseDataService;
import com.jiuqi.va.domain.basedata.BaseDataDO;
import com.jiuqi.va.domain.basedata.BaseDataDTO;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SubjectUtil {
    private static final Logger logger = LoggerFactory.getLogger(SubjectUtil.class);
    public static final String FN_ROOT_NODE_LABEL = "\u79d1\u76ee";
    public static final String FN_NODE_LABEL = "%1$s %2$s";
    public static final String INCLUDE_CLASS_NODE = "includeClassNode";

    private SubjectUtil() {
    }

    public static String getNodeLabel(String code, String name) {
        return String.format(FN_NODE_LABEL, code, name);
    }

    public static Map<String, BaseDataDO> getGeneralTypeMap() {
        BaseDataDTO generalTypeCondi = new BaseDataDTO();
        generalTypeCondi.setTableName("MD_ACCTSUBJECTCLASS");
        PageVO pageVo = ((BaseDataService)ApplicationContextRegister.getBean(BaseDataService.class)).list(generalTypeCondi);
        return pageVo.getRows().stream().collect(Collectors.toMap(BaseDataDO::getCode, item -> item, (k1, k2) -> k2));
    }

    public static Map<String, BaseDataDO> getCurrencyMap() {
        BaseDataDTO currencyCondi = new BaseDataDTO();
        currencyCondi.setTableName("MD_CURRENCY");
        PageVO pageVo = ((BaseDataService)ApplicationContextRegister.getBean(BaseDataService.class)).list(currencyCondi);
        return pageVo.getRows().stream().collect(Collectors.toMap(BaseDataDO::getCode, item -> item, (k1, k2) -> k2));
    }

    public static Map<String, Integer> parseAssTypeMap(SubjectDTO dto) {
        Assert.isNotNull((Object)dto);
        ObjectMapper objectMapper = new ObjectMapper();
        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(HashMap.class, new Class[]{String.class, Integer.class});
        String assType = dto.getAssType();
        if (StringUtils.isEmpty((String)assType)) {
            return CollectionUtils.newHashMap();
        }
        Map assTypeMap = CollectionUtils.newHashMap();
        try {
            assTypeMap = (Map)objectMapper.readValue(assType, javaType);
        }
        catch (Exception e) {
            logger.error("\u79d1\u76ee{}\u3010{}\u3011\u8f85\u52a9\u6838\u7b97\u7c7b'{}'\u53cd\u5e8f\u5217\u5316\u51fa\u73b0\u9519\u8bef", dto.getCode(), dto.getName(), assType);
        }
        return assTypeMap;
    }

    public static Map<String, Integer> parseAssTypeMap(String assType) {
        if (StringUtils.isEmpty((String)assType)) {
            return CollectionUtils.newHashMap();
        }
        ObjectMapper objectMapper = new ObjectMapper();
        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(HashMap.class, new Class[]{String.class, Integer.class});
        Map assTypeMap = CollectionUtils.newHashMap();
        try {
            assTypeMap = (Map)objectMapper.readValue(assType, javaType);
        }
        catch (Exception e) {
            logger.error("\u79d1\u76ee\u8f85\u52a9\u6838\u7b97'{}'\u53cd\u5e8f\u5217\u5316\u51fa\u73b0\u9519\u8bef", (Object)assType);
        }
        return assTypeMap;
    }
}

