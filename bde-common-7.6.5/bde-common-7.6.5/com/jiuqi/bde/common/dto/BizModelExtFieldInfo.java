/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.va.domain.common.MD5Util
 *  org.apache.commons.lang3.StringUtils
 */
package com.jiuqi.bde.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jiuqi.bde.common.dto.BizModelExtFieldDeclareGather;
import com.jiuqi.bde.common.dto.IBizModelExtFieldDeclare;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.va.domain.common.MD5Util;
import java.util.ArrayList;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;

@JsonInclude(value=JsonInclude.Include.NON_NULL)
public class BizModelExtFieldInfo {
    private Map<String, String> fieldMap;
    private String id;
    private String showName;

    public Map<String, String> getFieldMap() {
        return this.fieldMap;
    }

    public void setFieldMap(Map<String, String> fieldMap) {
        this.fieldMap = fieldMap;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getShowName() {
        return this.showName;
    }

    public void setShowName(String showName) {
        this.showName = showName;
    }

    public <T> void generateId() {
        BizModelExtFieldDeclareGather gather = (BizModelExtFieldDeclareGather)SpringContextUtils.getBean(BizModelExtFieldDeclareGather.class);
        StringBuilder id = new StringBuilder();
        ArrayList declareList = new ArrayList();
        for (Map.Entry<String, String> entry : this.fieldMap.entrySet()) {
            String code = entry.getKey();
            IBizModelExtFieldDeclare<?> declare = gather.getBizModelExtFieldDeclareByCode(code);
            declareList.add(declare);
        }
        declareList.sort((o1, o2) -> {
            int i = Integer.compare(o1.getOrder(), o2.getOrder());
            if (i == 0) {
                return StringUtils.compare((String)o1.getCode(), (String)o2.getCode());
            }
            return i;
        });
        for (IBizModelExtFieldDeclare iBizModelExtFieldDeclare : declareList) {
            id.append(iBizModelExtFieldDeclare.generateId(this.fieldMap.get(iBizModelExtFieldDeclare.getCode())));
        }
        String encrypt = MD5Util.encrypt((String)id.toString());
        this.setId(encrypt);
    }
}

