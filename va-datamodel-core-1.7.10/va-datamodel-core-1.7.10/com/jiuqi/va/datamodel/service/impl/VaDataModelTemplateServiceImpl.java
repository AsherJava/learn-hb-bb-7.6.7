/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.datamodel.DataModelType$BizType
 *  com.jiuqi.va.extend.DataModelTemplate
 *  com.jiuqi.va.extend.DataModelTemplateDTO
 *  com.jiuqi.va.extend.DataModelTemplateEntity
 */
package com.jiuqi.va.datamodel.service.impl;

import com.jiuqi.va.datamodel.service.VaDataModelTemplateService;
import com.jiuqi.va.domain.datamodel.DataModelType;
import com.jiuqi.va.extend.DataModelTemplate;
import com.jiuqi.va.extend.DataModelTemplateDTO;
import com.jiuqi.va.extend.DataModelTemplateEntity;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class VaDataModelTemplateServiceImpl
implements VaDataModelTemplateService {
    private Map<String, DataModelTemplate> templateAllMap = new HashMap<String, DataModelTemplate>();
    private Map<String, Map<Integer, DataModelTemplate>> templateSubMap = new HashMap<String, Map<Integer, DataModelTemplate>>();

    public VaDataModelTemplateServiceImpl(List<DataModelTemplate> dataModelTemplateList) {
        Collections.sort(dataModelTemplateList, (o1, o2) -> o1.getOrdinal() < o2.getOrdinal() ? -1 : 1);
        String bizTypeName = null;
        for (DataModelTemplate template : dataModelTemplateList) {
            this.templateAllMap.put(template.getName().toLowerCase(), template);
            bizTypeName = template.getBizType().toString().toLowerCase();
            if (!this.templateSubMap.containsKey(bizTypeName)) {
                this.templateSubMap.putIfAbsent(bizTypeName, new LinkedHashMap());
            }
            this.templateSubMap.get(bizTypeName).put(template.getSubBizType(), template);
        }
    }

    @Override
    public DataModelTemplateEntity getTemplateEntity(DataModelTemplateDTO param) {
        DataModelTemplate template = this.getTemplate(param.getTemplateName());
        if (template != null) {
            DataModelTemplateEntity entity = new DataModelTemplateEntity();
            entity.setTemplateFields(template.getTemplateFields());
            entity.setTemplateIndexs(template.getTemplateIndexs(param.getTableName()));
            return entity;
        }
        return null;
    }

    @Override
    public DataModelTemplate getTemplate(String templateName) {
        if (templateName == null) {
            return null;
        }
        return this.templateAllMap.get(templateName.toLowerCase());
    }

    @Override
    public DataModelTemplate getSubTemplate(String bizTypeName, Integer subBizType) {
        if (bizTypeName == null || subBizType == null) {
            return null;
        }
        if (!this.templateSubMap.containsKey(bizTypeName = bizTypeName.toLowerCase())) {
            return this.templateAllMap.get(DataModelType.BizType.OTHER.toString().toLowerCase());
        }
        return this.templateSubMap.get(bizTypeName).get(subBizType);
    }

    @Override
    public List<DataModelTemplate> getAllSubTemplate(String bizTypeName) {
        ArrayList<DataModelTemplate> list = new ArrayList<DataModelTemplate>();
        if (bizTypeName == null) {
            return list;
        }
        if (!this.templateSubMap.containsKey(bizTypeName = bizTypeName.toLowerCase())) {
            list.add(this.templateAllMap.get(DataModelType.BizType.OTHER.toString().toLowerCase()));
        } else {
            list.addAll(this.templateSubMap.get(bizTypeName).values());
        }
        return list;
    }
}

