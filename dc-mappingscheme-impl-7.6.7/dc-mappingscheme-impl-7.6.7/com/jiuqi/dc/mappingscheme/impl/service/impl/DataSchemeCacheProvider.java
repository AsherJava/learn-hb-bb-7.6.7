/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.dc.base.common.cache.intf.ICacheDefine
 *  com.jiuqi.dc.base.common.cache.intf.impl.CacheDefine
 *  com.jiuqi.dc.base.common.cache.proider.ConcurrentMapCacheProvider
 *  com.jiuqi.dc.base.common.utils.BeanConvertUtil
 *  com.jiuqi.dc.mappingscheme.client.dto.DataSchemeDTO
 *  com.jiuqi.dc.mappingscheme.client.vo.DataMappingVO
 */
package com.jiuqi.dc.mappingscheme.impl.service.impl;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.dc.base.common.cache.intf.ICacheDefine;
import com.jiuqi.dc.base.common.cache.intf.impl.CacheDefine;
import com.jiuqi.dc.base.common.cache.proider.ConcurrentMapCacheProvider;
import com.jiuqi.dc.base.common.utils.BeanConvertUtil;
import com.jiuqi.dc.mappingscheme.client.dto.DataSchemeDTO;
import com.jiuqi.dc.mappingscheme.client.vo.DataMappingVO;
import com.jiuqi.dc.mappingscheme.impl.dao.DataSchemeDao;
import com.jiuqi.dc.mappingscheme.impl.domain.DataSchemeDO;
import com.jiuqi.dc.mappingscheme.impl.service.DataSchemeOptionService;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DataSchemeCacheProvider
extends ConcurrentMapCacheProvider<DataSchemeDTO> {
    @Autowired
    private DataSchemeDao dao;
    @Autowired
    private DataSchemeOptionService optionService;

    public ICacheDefine getCacheDefine() {
        return new CacheDefine("DataScheme", "\u6570\u636e\u6620\u5c04\u65b9\u6848");
    }

    public Collection<DataSchemeDTO> loadCache() {
        List<DataSchemeDO> listAll = this.dao.selectAll();
        if (CollectionUtils.isEmpty(listAll)) {
            return CollectionUtils.newArrayList();
        }
        return listAll.stream().map(item -> this.convert2Dto((DataSchemeDO)item)).collect(Collectors.toList());
    }

    private DataSchemeDTO convert2Dto(DataSchemeDO schemeDO) {
        DataSchemeDTO dto = (DataSchemeDTO)BeanConvertUtil.convert((Object)schemeDO, DataSchemeDTO.class, (String[])new String[0]);
        dto.setOptions(this.optionService.getListByDataScheme(dto));
        dto.setDataMapping((DataMappingVO)JsonUtils.readValue((String)schemeDO.getDataMapping(), DataMappingVO.class));
        return dto;
    }
}

