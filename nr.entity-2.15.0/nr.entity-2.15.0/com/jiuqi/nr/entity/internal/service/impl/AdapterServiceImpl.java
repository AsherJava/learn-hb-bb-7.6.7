/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.common.EntityUtils
 *  com.jiuqi.np.definition.common.StringUtils
 */
package com.jiuqi.nr.entity.internal.service.impl;

import com.jiuqi.np.definition.common.EntityUtils;
import com.jiuqi.np.definition.common.StringUtils;
import com.jiuqi.nr.entity.adapter.IEntityAdapter;
import com.jiuqi.nr.entity.adapter.IEntityAuthorityAdapter;
import com.jiuqi.nr.entity.common.Utils;
import com.jiuqi.nr.entity.exception.GroupAnalysisException;
import com.jiuqi.nr.entity.internal.service.AdapterService;
import com.jiuqi.nr.entity.model.IEntityCategory;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;

public class AdapterServiceImpl
implements AdapterService {
    public static final String MD_ORG_START = "MD_ORG_";
    public static final String DEFAULT_MD_ORG = "MD_ORG";
    public static final String MD_BASE_DATA_START = "MD_";
    public static final String EM_ENUM_DATA_START = "EM_";
    private final List<String> adapterIds = new ArrayList<String>();
    private List<IEntityAdapter> adapterList;
    @Autowired
    private List<IEntityAuthorityAdapter> entityAuthorityAdapters;

    @Override
    public IEntityAdapter getGroupAdapter(String groupId) {
        String groupCategory = Utils.getGroupCategory(groupId);
        if (StringUtils.isEmpty((String)groupCategory) || !this.judgementGroupId(groupId)) {
            throw new GroupAnalysisException(String.format("\u9519\u8bef\u7684\u5206\u7ec4id\u683c\u5f0f: %s\uff0c\u65e0\u6cd5\u89e3\u6790\u5206\u7ec4\u7c7b\u578b\u3002", groupId));
        }
        return this.getEntityAdapterByCategory(groupCategory);
    }

    @Override
    public IEntityAdapter getEntityAdapter(String entityId) {
        IEntityAdapter findAdapter = this.adapterList.stream().filter(e -> e.getId().equals(EntityUtils.getCategory((String)entityId))).findFirst().orElse(null);
        if (findAdapter == null) {
            findAdapter = this.getAdapter(EntityUtils.getId((String)entityId));
        }
        return findAdapter;
    }

    @Override
    public IEntityAdapter getEntityAdapterByCode(String entityCode) {
        return this.getAdapter(entityCode);
    }

    @Override
    public IEntityAdapter getEntityAdapterByCategory(String category) {
        return this.adapterList.stream().filter(e -> e.getId().equals(category)).findFirst().orElse(null);
    }

    @Override
    public List<IEntityAdapter> getAdapterList() {
        return this.adapterList;
    }

    @Override
    public IEntityAuthorityAdapter getEntityAuthorityAdapter(String entityId) {
        return this.entityAuthorityAdapters.stream().filter(e -> e.getId().equals(EntityUtils.getCategory((String)entityId))).findFirst().orElse(null);
    }

    @Override
    public boolean judgementEntityId(String entityId) {
        return !StringUtils.isEmpty((String)entityId) && entityId.contains("@") && this.exitCategory(Utils.getCategory(entityId));
    }

    @Override
    public boolean judgementGroupId(String groupId) {
        return !StringUtils.isEmpty((String)groupId) && groupId.contains("_") && this.exitCategory(Utils.getGroupCategory(groupId));
    }

    private boolean exitCategory(String category) {
        return this.adapterIds.stream().anyMatch(e -> e.equals(category));
    }

    public IEntityAdapter getAdapter(String entityId) {
        Optional<Object> entityAdapter = Optional.empty();
        if (entityId.startsWith(MD_ORG_START) || entityId.equalsIgnoreCase(DEFAULT_MD_ORG)) {
            entityAdapter = this.adapterList.stream().filter(e -> e.getId().equals("ORG")).findFirst();
        } else if (entityId.startsWith(MD_BASE_DATA_START)) {
            entityAdapter = this.adapterList.stream().filter(e -> e.getId().equals("BASE")).findFirst();
        } else if (entityId.startsWith(EM_ENUM_DATA_START)) {
            entityAdapter = this.adapterList.stream().filter(e -> e.getId().equals("ENUM")).findFirst();
        }
        return entityAdapter.orElse(null);
    }

    @Autowired
    public void setAdapterList(List<IEntityAdapter> adapterList) {
        this.adapterList = adapterList;
        this.adapterIds.addAll(adapterList.stream().map(IEntityCategory::getId).collect(Collectors.toList()));
    }
}

