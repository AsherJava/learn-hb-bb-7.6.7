/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.nr.context.cxt.DsContext
 *  com.jiuqi.nr.context.cxt.DsContextHolder
 */
package com.jiuqi.nr.dataservice.core.dimension;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.nr.context.cxt.DsContext;
import com.jiuqi.nr.context.cxt.DsContextHolder;
import com.jiuqi.nr.dataservice.core.dimension.DimensionEnvironment;
import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DimensionContext
implements Serializable {
    private static final long serialVersionUID = -1351321596840053672L;
    private String taskKey;
    private transient Map<String, EntityViewDefine> viewDefineMap;

    public DimensionContext(String taskKey) {
        this.taskKey = taskKey;
    }

    private Map<String, EntityViewDefine> getDimViewDefineMap() {
        if (this.viewDefineMap == null) {
            List viewDefines;
            String dimensionName;
            this.viewDefineMap = new HashMap<String, EntityViewDefine>();
            EntityViewDefine mdView = DimensionEnvironment.getRunTimeViewController().getViewByTaskDefineKey(this.taskKey);
            DsContext dsContext = DsContextHolder.getDsContext();
            String entityId = dsContext.getContextEntityId();
            if (StringUtils.isNotEmpty((String)entityId)) {
                mdView = DimensionEnvironment.getEntityViewRunTimeController().buildEntityView(entityId, dsContext.getContextFilterExpression());
            }
            if (mdView != null && (dimensionName = DimensionEnvironment.getEntityMetaService().getDimensionName(mdView.getEntityId())) != null) {
                this.viewDefineMap.put(dimensionName, mdView);
            }
            if ((viewDefines = DimensionEnvironment.getRunTimeViewController().listDimensionViewsByTask(this.taskKey)) != null && viewDefines.size() > 0) {
                for (EntityViewDefine entityViewDefine : viewDefines) {
                    String dimensionName2 = DimensionEnvironment.getEntityMetaService().getDimensionName(entityViewDefine.getEntityId());
                    if (dimensionName2 == null) continue;
                    this.viewDefineMap.put(dimensionName2, entityViewDefine);
                }
            }
        }
        return Collections.unmodifiableMap(this.viewDefineMap);
    }

    public EntityViewDefine getDimViewDefine(String name) {
        if (this.viewDefineMap == null) {
            this.getDimViewDefineMap();
        }
        return this.viewDefineMap.get(name);
    }
}

