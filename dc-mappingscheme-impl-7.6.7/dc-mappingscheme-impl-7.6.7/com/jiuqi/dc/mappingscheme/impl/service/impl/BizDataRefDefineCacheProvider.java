/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.dc.base.common.cache.intf.ICacheDefine
 *  com.jiuqi.dc.base.common.cache.intf.impl.CacheDefine
 *  com.jiuqi.dc.base.common.cache.proider.ConcurrentMapCacheProvider
 *  com.jiuqi.dc.base.common.utils.BeanConvertUtil
 *  com.jiuqi.dc.mappingscheme.client.dto.DataMappingDefineDTO
 *  com.jiuqi.dc.mappingscheme.client.dto.DataSchemeDTO
 *  com.jiuqi.dc.mappingscheme.client.dto.FieldMappingDefineDTO
 */
package com.jiuqi.dc.mappingscheme.impl.service.impl;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.dc.base.common.cache.intf.ICacheDefine;
import com.jiuqi.dc.base.common.cache.intf.impl.CacheDefine;
import com.jiuqi.dc.base.common.cache.proider.ConcurrentMapCacheProvider;
import com.jiuqi.dc.base.common.utils.BeanConvertUtil;
import com.jiuqi.dc.mappingscheme.client.dto.DataMappingDefineDTO;
import com.jiuqi.dc.mappingscheme.client.dto.DataSchemeDTO;
import com.jiuqi.dc.mappingscheme.client.dto.FieldMappingDefineDTO;
import com.jiuqi.dc.mappingscheme.impl.dao.DataMappingDefineDao;
import com.jiuqi.dc.mappingscheme.impl.dao.FieldMappingDefineDao;
import com.jiuqi.dc.mappingscheme.impl.domain.DataMappingDefineDO;
import com.jiuqi.dc.mappingscheme.impl.domain.FieldMappingDefineDO;
import com.jiuqi.dc.mappingscheme.impl.service.DataSchemeService;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BizDataRefDefineCacheProvider
extends ConcurrentMapCacheProvider<DataMappingDefineDTO> {
    @Autowired
    private DataMappingDefineDao dataMappingDao;
    @Autowired
    private FieldMappingDefineDao fieldMappingDao;
    @Autowired
    private DataSchemeService schemeService;

    public ICacheDefine getCacheDefine() {
        return new CacheDefine("BizDataRefDefine", "\u4e1a\u52a1\u6570\u636e\u6620\u5c04\u5b9a\u4e49");
    }

    public Collection<DataMappingDefineDTO> loadCache() {
        return CollectionUtils.newArrayList();
    }

    private DataMappingDefineDTO convert2Dto(Map<String, DataSchemeDTO> schemeMap, DataMappingDefineDO item, Map<String, List<FieldMappingDefineDO>> itemGroup) {
        DataMappingDefineDTO defineDto = (DataMappingDefineDTO)BeanConvertUtil.convert((Object)item, DataMappingDefineDTO.class, (String[])new String[0]);
        if (schemeMap.get(defineDto.getDataSchemeCode()) != null) {
            defineDto.setPluginType(schemeMap.get(defineDto.getDataSchemeCode()).getPluginType());
        }
        if (itemGroup.get(defineDto.getId()) == null) {
            defineDto.setItems((List)CollectionUtils.newArrayList());
            return defineDto;
        }
        List<FieldMappingDefineDO> itemlist = itemGroup.get(defineDto.getId());
        defineDto.setItems(Collections.unmodifiableList(itemlist.stream().map(defineItem -> this.convertItem2Dto((FieldMappingDefineDO)defineItem)).collect(Collectors.toList())));
        return defineDto;
    }

    private FieldMappingDefineDTO convertItem2Dto(FieldMappingDefineDO defineItem) {
        return (FieldMappingDefineDTO)BeanConvertUtil.convert((Object)defineItem, FieldMappingDefineDTO.class, (String[])new String[0]);
    }
}

