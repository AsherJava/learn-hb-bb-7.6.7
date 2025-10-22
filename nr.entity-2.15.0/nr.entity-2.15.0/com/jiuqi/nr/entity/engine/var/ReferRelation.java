/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 */
package com.jiuqi.nr.entity.engine.var;

import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.model.IEntityRefer;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.springframework.util.CollectionUtils;

public class ReferRelation
implements Serializable {
    private EntityViewDefine viewDefine;
    private IEntityModel entityModel;
    private List<String> range;
    private IEntityRefer refer;

    public EntityViewDefine getViewDefine() {
        return this.viewDefine;
    }

    public void setViewDefine(EntityViewDefine viewDefine) {
        this.viewDefine = viewDefine;
    }

    public IEntityModel getEntityModel() {
        return this.entityModel;
    }

    public void setEntityModel(IEntityModel entityModel) {
        this.entityModel = entityModel;
    }

    public List<String> getRange() {
        return this.range;
    }

    public void setRange(List<String> range) {
        this.range = range;
    }

    public IEntityRefer getRefer() {
        return this.refer;
    }

    public void setRefer(IEntityRefer refer) {
        this.refer = refer;
    }

    public ReferRelation addRange(String entityKey) {
        if (CollectionUtils.isEmpty(this.range)) {
            this.range = new ArrayList<String>();
        }
        this.range.add(entityKey);
        return this;
    }
}

