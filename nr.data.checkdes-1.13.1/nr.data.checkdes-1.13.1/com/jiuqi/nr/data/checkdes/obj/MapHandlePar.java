/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.data.common.service.ParamsMapping
 *  com.jiuqi.nr.data.logic.internal.util.entity.EntityUtil
 *  com.jiuqi.nr.dataentity.service.DataEntityService
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nr.entity.model.IEntityDefine
 */
package com.jiuqi.nr.data.checkdes.obj;

import com.jiuqi.nr.data.checkdes.obj.CKDImpDetails;
import com.jiuqi.nr.data.common.service.ParamsMapping;
import com.jiuqi.nr.data.logic.internal.util.entity.EntityUtil;
import com.jiuqi.nr.dataentity.service.DataEntityService;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.entity.model.IEntityDefine;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapHandlePar {
    private final ParamsMapping paramsMapping;
    private final FormSchemeDefine formSchemeDefine;
    private List<CKDImpDetails> ckdImpDetails;
    private String mainDimName;
    private String mainDimId;
    private Map<String, String> dims;

    public MapHandlePar(ParamsMapping paramsMapping, FormSchemeDefine formSchemeDefine) {
        this.paramsMapping = paramsMapping;
        this.formSchemeDefine = formSchemeDefine;
        this.initMainDim();
        this.initDims();
    }

    public MapHandlePar(ParamsMapping paramsMapping, FormSchemeDefine formSchemeDefine, List<CKDImpDetails> ckdImpDetails) {
        this.paramsMapping = paramsMapping;
        this.formSchemeDefine = formSchemeDefine;
        this.ckdImpDetails = ckdImpDetails;
        this.initMainDim();
        this.initDims();
    }

    private void initMainDim() {
        DataEntityService dataEntityService = (DataEntityService)BeanUtil.getBean(DataEntityService.class);
        IEntityDefine dw = dataEntityService.getEntityDefineInContext(this.formSchemeDefine.getDw());
        this.mainDimName = dw.getDimensionName();
        this.mainDimId = dw.getId();
    }

    private void initDims() {
        this.dims = new HashMap<String, String>();
        EntityUtil entityUtil = (EntityUtil)BeanUtil.getBean(EntityUtil.class);
        List dimEntities = entityUtil.getDimEntities(this.formSchemeDefine);
        dimEntities.forEach(o -> this.dims.put(o.getDimensionName(), o.getKey()));
    }

    public String getMainDimName() {
        return this.mainDimName;
    }

    public String getMainDimId() {
        return this.mainDimId;
    }

    public Map<String, String> getDims() {
        return this.dims;
    }

    public ParamsMapping getParamsMapping() {
        return this.paramsMapping;
    }

    public FormSchemeDefine getFormSchemeDefine() {
        return this.formSchemeDefine;
    }

    public List<CKDImpDetails> getCkdImpDetails() {
        return this.ckdImpDetails;
    }
}

