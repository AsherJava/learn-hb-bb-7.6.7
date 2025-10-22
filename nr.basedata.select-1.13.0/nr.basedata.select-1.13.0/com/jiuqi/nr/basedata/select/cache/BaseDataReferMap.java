/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.entity.model.IEntityRefer
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 */
package com.jiuqi.nr.basedata.select.cache;

import com.jiuqi.nr.entity.model.IEntityRefer;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BaseDataReferMap {
    @Autowired
    private IEntityMetaService entityMetaService;
    private Map<String, List<IEntityRefer>> entityReferMap = new HashMap<String, List<IEntityRefer>>();

    public List<IEntityRefer> getEntityReferList(String entityKey) {
        if (this.entityReferMap != null && this.entityReferMap.size() > 0 && this.entityReferMap.containsKey(entityKey)) {
            return this.entityReferMap.get(entityKey);
        }
        List entityRefer = this.entityMetaService.getEntityRefer(entityKey);
        if (entityRefer != null && entityRefer.size() > 0) {
            if (this.entityReferMap == null) {
                this.entityReferMap = new HashMap<String, List<IEntityRefer>>();
            }
            this.entityReferMap.put(entityKey, entityRefer);
            return this.entityReferMap.get(entityKey);
        }
        return null;
    }
}

