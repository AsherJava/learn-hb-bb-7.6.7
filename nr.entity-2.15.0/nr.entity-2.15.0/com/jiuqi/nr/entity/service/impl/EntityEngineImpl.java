/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.entity.service.impl;

import com.jiuqi.nr.entity.adapter.IEntityAdapter;
import com.jiuqi.nr.entity.internal.service.AdapterService;
import com.jiuqi.nr.entity.model.IEntityCategory;
import com.jiuqi.nr.entity.service.EntityEngine;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

public class EntityEngineImpl
implements EntityEngine {
    private static final Logger log = LoggerFactory.getLogger(EntityEngineImpl.class);
    private AdapterService adapterService;

    public void check() {
        this.entityCategoryRepeatCheck();
    }

    @Autowired
    public void setAdapterService(AdapterService adapterService) {
        this.adapterService = adapterService;
        this.check();
    }

    private void entityCategoryRepeatCheck() {
        List<IEntityAdapter> adapterList = this.adapterService.getAdapterList();
        Optional<IEntityAdapter> findEmptyIdAdapter = adapterList.stream().filter(e -> StringUtils.isEmpty(e.getId())).findAny();
        if (findEmptyIdAdapter.isPresent()) {
            throw new RuntimeException(new StringBuffer("\u5b9e\u4f53\u6846\u67b6\u9002\u914d\u5668ID\u4e3a\u7a7a\uff1a").append(findEmptyIdAdapter.get().getClass().getName()).toString());
        }
        Map<String, List<IEntityAdapter>> adapterIdMap = adapterList.stream().collect(Collectors.groupingBy(IEntityCategory::getId));
        if (adapterIdMap.size() == adapterList.size()) {
            return;
        }
        for (String key : adapterIdMap.keySet()) {
            List<IEntityAdapter> baseAdapters = adapterIdMap.get(key);
            if (baseAdapters.size() <= 1) continue;
            StringBuffer messageInfo = new StringBuffer("\u7cfb\u7edf\u4e2d\u5b9e\u73b0\u7684\u5b9e\u4f53\u6846\u67b6\u9002\u914d\u5668\u5b58\u5728\u91cd\u590d\u7684\u9002\u914d\u5668ID,\u8bf7\u68c0\u67e5:");
            for (IEntityAdapter baseAdapter : baseAdapters) {
                messageInfo.append(baseAdapter.getClass().getName()).append(":").append(baseAdapter.getId()).append(".");
            }
            throw new RuntimeException(messageInfo.toString());
        }
    }
}

