/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.nr.basedata.select.service.IBaseDataIsolateConditionProvider
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.datacrud.IMetaData
 *  com.jiuqi.nr.datacrud.ParamRelation
 *  com.jiuqi.nr.datacrud.impl.service.impl.DefaultEntityTableFactory
 *  com.jiuqi.nr.datacrud.spi.entity.QueryMode
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nr.jtable.service.IEntityIsolateConditionProvider
 */
package com.jiuqi.gcreport.inputdata.entityisolate;

import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.inputdata.entityisolate.GcEntityIsolateProcessor;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.nr.basedata.select.service.IBaseDataIsolateConditionProvider;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.datacrud.IMetaData;
import com.jiuqi.nr.datacrud.ParamRelation;
import com.jiuqi.nr.datacrud.impl.service.impl.DefaultEntityTableFactory;
import com.jiuqi.nr.datacrud.spi.entity.QueryMode;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.service.IEntityIsolateConditionProvider;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class GCEntityIsolateConditionProvider
extends DefaultEntityTableFactory
implements IEntityIsolateConditionProvider,
IBaseDataIsolateConditionProvider {
    private static Logger logger = LoggerFactory.getLogger(GCEntityIsolateConditionProvider.class);

    public String getEntityIsolateCondition(String entityId, JtableContext jtableContext) {
        if (jtableContext == null || jtableContext.getDimensionSet() == null || jtableContext.getDimensionSet().get("DATATIME") == null) {
            return null;
        }
        String periodStr = String.valueOf(((DimensionValue)jtableContext.getDimensionSet().get("DATATIME")).getValue());
        if (!StringUtils.isEmpty((String)jtableContext.getFormSchemeKey())) {
            GcEntityIsolateProcessor.getInstance();
            return GcEntityIsolateProcessor.getSubjectEntityIsolateCondition(entityId, jtableContext.getFormSchemeKey(), periodStr);
        }
        SchemePeriodLinkDefine schemePeriodLinkDefine = null;
        try {
            schemePeriodLinkDefine = this.runTimeViewController.querySchemePeriodLinkByPeriodAndTask(periodStr, jtableContext.getTaskKey());
        }
        catch (Exception e) {
            logger.error("\u6839\u636e\u4efb\u52a1\u65f6\u671f\u83b7\u53d6\u62a5\u8868\u65b9\u6848\u4fe1\u606f\u5f02\u5e38\uff0ctask=" + jtableContext.getTaskKey() + ", \u65f6\u671f=" + periodStr);
            return null;
        }
        GcEntityIsolateProcessor.getInstance();
        return GcEntityIsolateProcessor.getSubjectEntityIsolateCondition(entityId, schemePeriodLinkDefine.getSchemeKey(), periodStr);
    }

    public String getBaseDataIsolateCondition(String entityId, String taskKey, Map<String, DimensionValue> dimensionSet) {
        String periodStr = String.valueOf(dimensionSet.get("DATATIME").getValue());
        SchemePeriodLinkDefine schemePeriodLinkDefine = null;
        try {
            schemePeriodLinkDefine = this.runTimeViewController.querySchemePeriodLinkByPeriodAndTask(periodStr, taskKey);
        }
        catch (Exception e) {
            logger.error("\u6839\u636e\u4efb\u52a1\u65f6\u671f\u83b7\u53d6\u62a5\u8868\u65b9\u6848\u4fe1\u606f\u5f02\u5e38\uff0ctask=" + taskKey + ", \u65f6\u671f=" + periodStr);
            return null;
        }
        GcEntityIsolateProcessor.getInstance();
        return GcEntityIsolateProcessor.getSubjectEntityIsolateCondition(entityId, schemePeriodLinkDefine.getSchemeKey(), periodStr);
    }

    protected IEntityQuery buildEntityQuery(ParamRelation paramRelation, DimensionCombination masterKey, IMetaData metaData, QueryMode mode) {
        DataLinkDefine dataLinkDefine = metaData.getDataLinkDefine();
        String linkDefineKey = dataLinkDefine.getKey();
        EntityViewDefine entityViewDefine = this.runTimeViewController.getViewByLinkDefineKey(linkDefineKey);
        String entityId = entityViewDefine.getEntityId();
        String periodStr = this.getPeriodValue(masterKey);
        if (StringUtils.isEmpty((String)entityId) || !entityId.contains("MD_GCSUBJECT") || StringUtils.isEmpty((String)periodStr)) {
            return super.buildEntityQuery(paramRelation, masterKey, metaData, mode);
        }
        GcEntityIsolateProcessor.getInstance();
        String systemId = GcEntityIsolateProcessor.getSubjectEntityIsolateCondition(entityId, paramRelation.getFormSchemeKey(), periodStr);
        IEntityQuery entityQuery = this.entityDataService.newEntityQuery();
        if (Boolean.TRUE.equals(mode.isDesensitized())) {
            entityQuery.maskedData();
        }
        entityQuery.setEntityView(entityViewDefine);
        entityQuery.setIsolateCondition(systemId);
        DimensionCombination rebuildMasterKey = this.rebuildMasterKey(paramRelation, masterKey, metaData);
        entityQuery.setMasterKeys(rebuildMasterKey.toDimensionValueSet());
        return entityQuery;
    }

    protected String buildParamCacheKey(ParamRelation paramRelation, DimensionCombination masterKey, IMetaData metaData, int mode) {
        String linkKey = metaData.getLinkKey();
        StringBuilder cacheKeyBuild = new StringBuilder(linkKey);
        EntityViewDefine entityViewDefine = this.runTimeViewController.getViewByLinkDefineKey(linkKey);
        String entityId = entityViewDefine.getEntityId();
        String periodStr = this.getPeriodValue(masterKey);
        if (StringUtils.isEmpty((String)entityId) || !entityId.contains("MD_GCSUBJECT") || StringUtils.isEmpty((String)periodStr)) {
            return super.buildParamCacheKey(paramRelation, masterKey, metaData, mode);
        }
        GcEntityIsolateProcessor.getInstance();
        String systemId = GcEntityIsolateProcessor.getSubjectEntityIsolateCondition(entityId, paramRelation.getFormSchemeKey(), periodStr);
        if (!StringUtils.isEmpty((String)systemId)) {
            cacheKeyBuild.append("_").append(systemId);
        }
        if (!StringUtils.isEmpty((String)periodStr)) {
            cacheKeyBuild.append("_").append(periodStr);
        }
        return cacheKeyBuild.toString();
    }
}

