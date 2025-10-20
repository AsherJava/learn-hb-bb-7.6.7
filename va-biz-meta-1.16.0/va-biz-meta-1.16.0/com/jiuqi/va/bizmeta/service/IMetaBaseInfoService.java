/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.biz.MetaDataDTO
 *  com.jiuqi.va.domain.biz.ModelDTO
 */
package com.jiuqi.va.bizmeta.service;

import com.jiuqi.va.domain.biz.MetaDataDTO;
import com.jiuqi.va.domain.biz.ModelDTO;
import java.util.List;
import java.util.Map;

public interface IMetaBaseInfoService {
    public List<ModelDTO> gatherModels(ModelDTO var1);

    public MetaDataDTO gatherMetaData(MetaDataDTO var1);

    public List<ModelDTO> gatherModelsAll();

    public List<Map<String, Object>> gatherPluginsAll(String var1, String var2);
}

