/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.mappingscheme.impl.enums;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public enum SchemeBaseDataRefType {
    ORG("MD_ORG", "\u7ec4\u7ec7\u673a\u6784", "MD_ORG", 0),
    ACCTSUBJECT("MD_ACCTSUBJECT", "\u79d1\u76ee", "SUBJECTCODE", 1),
    CURRENCY("MD_CURRENCY", "\u5e01\u522b", "CURRENCYCODE", 2),
    CFITEM("MD_CFITEM", "\u73b0\u6d41\u9879\u76ee", "CFITEMCODE", 3);

    private String code;
    private String name;
    private String bizMappingName;
    private Integer order;

    private SchemeBaseDataRefType(String code, String name, String bizMappingName, Integer order) {
        this.code = code;
        this.name = name;
        this.bizMappingName = bizMappingName;
        this.order = order;
    }

    public String getCode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }

    public String getBizMappingName() {
        return this.bizMappingName;
    }

    public Integer getOrder() {
        return this.order;
    }

    public static List<String> getSchemeBaseDataRefList() {
        return Arrays.stream(SchemeBaseDataRefType.values()).map(SchemeBaseDataRefType::getCode).collect(Collectors.toList());
    }

    public static Map<String, String> getSchemeBaseDataRefMap() {
        HashMap<String, String> baseDataRefMap = new HashMap<String, String>();
        for (SchemeBaseDataRefType refType : SchemeBaseDataRefType.values()) {
            baseDataRefMap.put(refType.getCode(), refType.getBizMappingName());
        }
        return baseDataRefMap;
    }
}

