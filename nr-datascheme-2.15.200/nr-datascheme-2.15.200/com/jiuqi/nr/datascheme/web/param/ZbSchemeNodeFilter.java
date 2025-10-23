/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DataScheme
 *  com.jiuqi.nr.datascheme.api.core.ISchemeNode
 *  com.jiuqi.nr.datascheme.api.core.NodeType
 *  com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.spi.NodeFilter
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.zb.scheme.core.ZbInfo
 *  com.jiuqi.nr.zb.scheme.core.ZbSchemeVersion
 *  com.jiuqi.nr.zb.scheme.service.IZbSchemeService
 *  org.jetbrains.annotations.NotNull
 */
package com.jiuqi.nr.datascheme.web.param;

import com.jiuqi.nr.datascheme.api.DataScheme;
import com.jiuqi.nr.datascheme.api.core.ISchemeNode;
import com.jiuqi.nr.datascheme.api.core.NodeType;
import com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.spi.NodeFilter;
import com.jiuqi.nr.datascheme.internal.entity.DataSchemeDO;
import com.jiuqi.nr.datascheme.internal.tree.RuntimeDataSchemeNodeDTO;
import com.jiuqi.nr.datascheme.web.param.DataSchemeTreeQuery;
import com.jiuqi.nr.datascheme.web.param.SchemeNodeFilter;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.zb.scheme.core.ZbInfo;
import com.jiuqi.nr.zb.scheme.core.ZbSchemeVersion;
import com.jiuqi.nr.zb.scheme.service.IZbSchemeService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;

public class ZbSchemeNodeFilter
extends SchemeNodeFilter {
    private final IZbSchemeService zbSchemeService;
    private Map<String, Map<String, ZbInfo>> zbInfoMap;
    private Predicate<? super ISchemeNode> and;

    public ZbSchemeNodeFilter(IDesignDataSchemeService designService, IRuntimeDataSchemeService service, IEntityMetaService metaService, DataSchemeTreeQuery<RuntimeDataSchemeNodeDTO> param, IZbSchemeService zbSchemeService) {
        this(designService, service, metaService, param, false, zbSchemeService);
    }

    public ZbSchemeNodeFilter(IDesignDataSchemeService designService, IRuntimeDataSchemeService service, IEntityMetaService metaService, DataSchemeTreeQuery<RuntimeDataSchemeNodeDTO> param, boolean force, IZbSchemeService zbSchemeService) {
        super(designService, service, metaService, param, force);
        this.zbSchemeService = zbSchemeService;
        this.init();
    }

    private void init() {
        List<DataScheme> dataSchemes = this.getSchemesData();
        this.zbInfoMap = new HashMap<String, Map<String, ZbInfo>>(dataSchemes.size());
        String period = super.getPeriod();
        for (DataScheme dataScheme : dataSchemes) {
            ZbSchemeVersion zbSchemeVersion = this.zbSchemeService.getZbSchemeVersion(dataScheme.getZbSchemeKey(), period);
            if (zbSchemeVersion == null) continue;
            List zbInfos = this.zbSchemeService.listZbInfoByVersion(zbSchemeVersion.getKey());
            this.zbInfoMap.put(dataScheme.getKey(), zbInfos.stream().collect(Collectors.toMap(ZbInfo::getCode, z -> z, (o1, o2) -> o1)));
        }
    }

    @Override
    public boolean test(ISchemeNode schemeNode) {
        return super.test(schemeNode) && this.doTest(schemeNode) && (this.and == null || this.and.test((ISchemeNode)schemeNode));
    }

    private boolean doTest(ISchemeNode schemeNode) {
        Object data;
        int type = schemeNode.getType();
        if (NodeType.SCHEME.getValue() == type && (data = schemeNode.getData()) instanceof DataSchemeDO) {
            return this.test((DataScheme)((DataSchemeDO)data));
        }
        return true;
    }

    public <T extends DataScheme> boolean test(T data) {
        return data.getZbSchemeKey() != null && data.getZbSchemeVersion() != null;
    }

    public void filterByPeriod(String dataSchemeKey, RuntimeDataSchemeNodeDTO node) {
        Map<String, ZbInfo> zbInfos;
        ZbInfo zbInfo;
        if (this.zbInfoMap.containsKey(dataSchemeKey) && (zbInfo = (zbInfos = this.zbInfoMap.get(dataSchemeKey)).get(node.getCode())) != null) {
            node.setTitle(zbInfo.getTitle());
        }
    }

    public Map<String, Map<String, ZbInfo>> getZbInfoMap() {
        return this.zbInfoMap;
    }

    @NotNull
    public NodeFilter and(Predicate<? super ISchemeNode> other) {
        this.and = this.and == null ? other : t -> this.test((ISchemeNode)t) && other.test((ISchemeNode)t);
        return this;
    }
}

