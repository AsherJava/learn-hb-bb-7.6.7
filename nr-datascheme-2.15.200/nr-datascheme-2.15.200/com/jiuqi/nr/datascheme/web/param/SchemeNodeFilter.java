/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DataGroup
 *  com.jiuqi.nr.datascheme.api.DataScheme
 *  com.jiuqi.nr.datascheme.api.core.ISchemeNode
 *  com.jiuqi.nr.datascheme.api.core.NodeType
 *  com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.spi.NodeFilter
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 */
package com.jiuqi.nr.datascheme.web.param;

import com.jiuqi.nr.datascheme.api.DataGroup;
import com.jiuqi.nr.datascheme.api.DataScheme;
import com.jiuqi.nr.datascheme.api.core.ISchemeNode;
import com.jiuqi.nr.datascheme.api.core.NodeType;
import com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.spi.NodeFilter;
import com.jiuqi.nr.datascheme.internal.tree.RuntimeDataSchemeNodeDTO;
import com.jiuqi.nr.datascheme.web.param.DataSchemeTreeQuery;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.util.StringUtils;

public class SchemeNodeFilter
implements NodeFilter {
    private final Set<String> path = new HashSet<String>(12);
    private final Set<String> schemes = new HashSet<String>(6);
    private final List<DataScheme> schemesData = new ArrayList<DataScheme>(6);
    private final List<DataGroup> groupData = new ArrayList<DataGroup>(6);
    private final String period;

    public SchemeNodeFilter(IDesignDataSchemeService designService, IRuntimeDataSchemeService service, IEntityMetaService metaService, DataSchemeTreeQuery<RuntimeDataSchemeNodeDTO> param) {
        this(designService, service, metaService, param, false);
    }

    public SchemeNodeFilter(IDesignDataSchemeService designService, IRuntimeDataSchemeService service, IEntityMetaService metaService, DataSchemeTreeQuery<RuntimeDataSchemeNodeDTO> param, boolean force) {
        List subTreeEntities;
        if (param == null) {
            throw new NullPointerException("param is null");
        }
        String dimKey = param.getDimKey();
        if (service == null) {
            throw new NullPointerException("service is null");
        }
        if (designService == null) {
            throw new NullPointerException("designService is null");
        }
        if (dimKey == null) {
            throw new NullPointerException("dimKey is null");
        }
        this.period = param.getPeriod();
        boolean hasPeriod = StringUtils.hasLength(this.period);
        RuntimeDataSchemeNodeDTO dataSchemeNode = param.getDataSchemeNode();
        if (dataSchemeNode != null && !force && (NodeType.SCHEME_GROUP.getValue() & dataSchemeNode.getType()) == 0) {
            return;
        }
        List dataSchemes = null;
        IEntityDefine iEntityDefine = metaService.queryEntity(dimKey);
        if (iEntityDefine.getIncludeSubTreeEntity() > 0 && (subTreeEntities = metaService.getSubTreeEntities(dimKey)) != null) {
            dataSchemes = service.getDataSchemeByDimKey((String[])subTreeEntities.stream().map(IEntityDefine::getId).toArray(String[]::new));
        }
        if (dataSchemes == null) {
            dataSchemes = service.getDataSchemeByDimKey(new String[]{dimKey});
        }
        HashSet<String> parent = new HashSet<String>();
        for (DataScheme dataScheme : dataSchemes) {
            if (this.zbSchemeFilter(dataScheme, hasPeriod)) continue;
            this.path.add(dataScheme.getKey());
            this.schemes.add(dataScheme.getKey());
            this.schemesData.add(dataScheme);
            String groupKey = dataScheme.getDataGroupKey();
            this.path.add(groupKey);
            if ("00000000-0000-0000-0000-000000000000".equals(groupKey) || "00000000-0000-0000-0000-111111111111".equals(groupKey)) continue;
            parent.add(groupKey);
        }
        HashSet<String> loop = new HashSet<String>(32);
        while (!parent.isEmpty()) {
            List groups = designService.getDataGroups(new ArrayList(parent));
            parent.clear();
            for (DataGroup dataGroup : groups) {
                String parentKey = dataGroup.getParentKey();
                this.path.add(parentKey);
                this.groupData.add(dataGroup);
                if ("00000000-0000-0000-0000-000000000000".equals(parentKey) || "00000000-0000-0000-0000-111111111111".equals(parentKey)) continue;
                parent.add(parentKey);
                loop.add(parentKey);
            }
        }
    }

    public boolean test(ISchemeNode dto) {
        if (((NodeType.SCHEME.getValue() | NodeType.SCHEME_GROUP.getValue()) & dto.getType()) != 0) {
            return this.path.contains(dto.getKey());
        }
        return true;
    }

    public Set<String> getPath() {
        return this.path;
    }

    public Set<String> getSchemes() {
        return this.schemes;
    }

    public List<DataScheme> getSchemesData() {
        return this.schemesData;
    }

    public List<DataGroup> getGroupData() {
        return this.groupData;
    }

    private boolean zbSchemeFilter(DataScheme dataScheme, boolean hasPeriod) {
        return hasPeriod && !StringUtils.hasLength(dataScheme.getZbSchemeKey()) && !StringUtils.hasLength(dataScheme.getZbSchemeVersion());
    }

    public String getPeriod() {
        return this.period;
    }
}

