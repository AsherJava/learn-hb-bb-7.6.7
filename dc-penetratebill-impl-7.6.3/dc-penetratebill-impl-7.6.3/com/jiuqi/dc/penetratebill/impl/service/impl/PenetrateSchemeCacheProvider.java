/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.dc.base.common.cache.intf.ICacheDefine
 *  com.jiuqi.dc.base.common.cache.intf.impl.CacheDefine
 *  com.jiuqi.dc.base.common.cache.proider.ConcurrentMapCacheProvider
 *  com.jiuqi.dc.base.common.utils.BeanConvertUtil
 *  com.jiuqi.dc.penetratebill.client.dto.PenetrateSchemeDTO
 */
package com.jiuqi.dc.penetratebill.impl.service.impl;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.dc.base.common.cache.intf.ICacheDefine;
import com.jiuqi.dc.base.common.cache.intf.impl.CacheDefine;
import com.jiuqi.dc.base.common.cache.proider.ConcurrentMapCacheProvider;
import com.jiuqi.dc.base.common.utils.BeanConvertUtil;
import com.jiuqi.dc.penetratebill.client.dto.PenetrateSchemeDTO;
import com.jiuqi.dc.penetratebill.impl.dao.PenetrateBillDao;
import com.jiuqi.dc.penetratebill.impl.entity.PenetrateSchemeEO;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PenetrateSchemeCacheProvider
extends ConcurrentMapCacheProvider<PenetrateSchemeDTO> {
    @Autowired
    private PenetrateBillDao dao;

    public ICacheDefine getCacheDefine() {
        return new CacheDefine("PenetrateBillScheme", "\u8054\u67e5\u5355\u636e\u65b9\u6848");
    }

    public Collection<PenetrateSchemeDTO> loadCache() {
        List<PenetrateSchemeEO> listAll = this.dao.selectAll();
        if (CollectionUtils.isEmpty(listAll)) {
            return CollectionUtils.newArrayList();
        }
        return listAll.stream().map(this::convert2Dto).collect(Collectors.toList());
    }

    private PenetrateSchemeDTO convert2Dto(PenetrateSchemeEO schemeEO) {
        return (PenetrateSchemeDTO)BeanConvertUtil.convert((Object)((Object)schemeEO), PenetrateSchemeDTO.class, (String[])new String[0]);
    }
}

