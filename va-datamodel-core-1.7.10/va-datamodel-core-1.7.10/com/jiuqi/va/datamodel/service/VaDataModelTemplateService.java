/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.extend.DataModelTemplate
 *  com.jiuqi.va.extend.DataModelTemplateDTO
 *  com.jiuqi.va.extend.DataModelTemplateEntity
 */
package com.jiuqi.va.datamodel.service;

import com.jiuqi.va.extend.DataModelTemplate;
import com.jiuqi.va.extend.DataModelTemplateDTO;
import com.jiuqi.va.extend.DataModelTemplateEntity;
import java.util.List;

public interface VaDataModelTemplateService {
    public DataModelTemplateEntity getTemplateEntity(DataModelTemplateDTO var1);

    public DataModelTemplate getTemplate(String var1);

    public DataModelTemplate getSubTemplate(String var1, Integer var2);

    public List<DataModelTemplate> getAllSubTemplate(String var1);
}

