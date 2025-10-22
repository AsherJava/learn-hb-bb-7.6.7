/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IColumnModelFinder
 *  com.jiuqi.np.dataengine.intf.IDataModelLinkFinder
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.period.IPeriodAdapter
 *  com.jiuqi.np.period.PeriodConsts
 *  com.jiuqi.np.period.PeriodModifier
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskLinkDefine
 *  com.jiuqi.nr.definition.facade.TaskLinkEntityMappingRule
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 *  com.jiuqi.nr.period.internal.adapter.IPeriodProvider
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 */
package com.jiuqi.nr.data.logic.internal.obj;

import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IColumnModelFinder;
import com.jiuqi.np.dataengine.intf.IDataModelLinkFinder;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.period.IPeriodAdapter;
import com.jiuqi.np.period.PeriodConsts;
import com.jiuqi.np.period.PeriodModifier;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.nr.data.logic.internal.obj.EntityData;
import com.jiuqi.nr.data.logic.internal.util.BeanHelper;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskLinkDefine;
import com.jiuqi.nr.definition.facade.TaskLinkEntityMappingRule;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nr.period.internal.adapter.IPeriodProvider;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

public class NodeInfoQueryCache {
    private final Map<String, FormDefine> formMapById = new HashMap<String, FormDefine>();
    private final Map<String, DataLinkDefine> dataLinkMap = new HashMap<String, DataLinkDefine>();
    private final Map<String, FieldDefine> fieldDefineMapByColId = new HashMap<String, FieldDefine>();
    private final Map<String, FieldDefine> fieldDefineMapByFieldKey = new HashMap<String, FieldDefine>();
    private final Map<String, DataRegionDefine> regionMap = new HashMap<String, DataRegionDefine>();
    private final Map<String, List<String>> regionBizKeyMap = new HashMap<String, List<String>>();
    private final Map<String, String> nodeShowMap = new HashMap<String, String>();
    private final Map<String, String> maskCodeMap = new HashMap<String, String>();
    private final ExecutorContext executorContext;
    private ReportFmlExecEnvironment fmlExecEnvironment;
    private final List<String> formSchemeEntityNames;
    private final BeanHelper beanHelper;
    private Map<String, RelatedTaskInfo> relatedTaskMap;
    private static final Logger log = LoggerFactory.getLogger(NodeInfoQueryCache.class);

    public NodeInfoQueryCache(ExecutorContext executorContext, List<String> formSchemeEntityNames, BeanHelper beanHelper) {
        this.executorContext = executorContext;
        this.formSchemeEntityNames = formSchemeEntityNames;
        this.beanHelper = beanHelper;
    }

    public FormDefine queryFormById(IRunTimeViewController runTimeViewController, String formKey) {
        if (this.formMapById.containsKey(formKey)) {
            return this.formMapById.get(formKey);
        }
        FormDefine formDefine = runTimeViewController.queryFormById(formKey);
        this.formMapById.put(formKey, formDefine);
        return formDefine;
    }

    public DataLinkDefine queryDataLinkDefineByUniqueCode(IRunTimeViewController runTimeViewController, String formKey, String dataLinkCode) {
        String key = formKey + dataLinkCode;
        if (this.dataLinkMap.containsKey(key)) {
            return this.dataLinkMap.get(key);
        }
        DataLinkDefine dataLinkDefine = runTimeViewController.queryDataLinkDefineByUniquecode(formKey, dataLinkCode);
        this.dataLinkMap.put(key, dataLinkDefine);
        return dataLinkDefine;
    }

    public FieldDefine findFieldDefine(IColumnModelFinder columnModelFinder, ColumnModelDefine columnModelDefine) throws Exception {
        if (this.fieldDefineMapByColId.containsKey(columnModelDefine.getID())) {
            return this.fieldDefineMapByColId.get(columnModelDefine.getID());
        }
        FieldDefine fieldDefine = columnModelFinder.findFieldDefine(columnModelDefine);
        this.fieldDefineMapByColId.put(columnModelDefine.getID(), fieldDefine);
        return fieldDefine;
    }

    public DataRegionDefine queryDataRegionDefine(IRunTimeViewController runTimeViewController, String dataRegionKey) {
        if (this.regionMap.containsKey(dataRegionKey)) {
            return this.regionMap.get(dataRegionKey);
        }
        DataRegionDefine dataRegionDefine = runTimeViewController.queryDataRegionDefine(dataRegionKey);
        this.regionMap.put(dataRegionKey, dataRegionDefine);
        return dataRegionDefine;
    }

    public Map<String, List<String>> getRegionBizKeyMap() {
        return this.regionBizKeyMap;
    }

    public FieldDefine queryFieldDefine(IRunTimeViewController runTimeViewController, String fieldKey) throws Exception {
        if (this.fieldDefineMapByFieldKey.containsKey(fieldKey)) {
            return this.fieldDefineMapByFieldKey.get(fieldKey);
        }
        FieldDefine fieldDefine = runTimeViewController.queryFieldDefine(fieldKey);
        this.fieldDefineMapByFieldKey.put(fieldKey, fieldDefine);
        return fieldDefine;
    }

    public Map<String, String> getNodeShowMap() {
        return this.nodeShowMap;
    }

    public List<String> getFormSchemeEntityNames() {
        return this.formSchemeEntityNames;
    }

    public String getDataFieldMaskCodeByColId(String colId) {
        if (this.maskCodeMap.containsKey(colId)) {
            return this.maskCodeMap.get(colId);
        }
        DataField dataFieldByColumnKey = this.beanHelper.getDataSchemeService().getDataFieldByColumnKey(colId);
        String maskCode = dataFieldByColumnKey == null ? null : dataFieldByColumnKey.getDataMaskCode();
        this.maskCodeMap.put(colId, maskCode);
        return maskCode;
    }

    public RelatedTaskInfo getTaskByAlias(String alias) {
        if (this.relatedTaskMap == null) {
            this.initRelatedTasks();
        }
        return this.relatedTaskMap.get(alias);
    }

    public IDataModelLinkFinder getDataModelLinkFinder() {
        return this.executorContext.getDataModelLinkFinder();
    }

    public ExecutorContext getExecutorContext() {
        return this.executorContext;
    }

    private void initRelatedTasks() {
        FormSchemeDefine curFormScheme = this.getFmlExecEnvironment().getFormSchemeDefine();
        List taskLinkDefines = this.beanHelper.getRuntimeViewController().queryLinksByCurrentFormScheme(curFormScheme.getKey());
        if (CollectionUtils.isEmpty(taskLinkDefines)) {
            this.relatedTaskMap = Collections.emptyMap();
        } else {
            this.relatedTaskMap = new HashMap<String, RelatedTaskInfo>(taskLinkDefines.size());
            taskLinkDefines.forEach(o -> {
                FormSchemeDefine formScheme = this.beanHelper.getRuntimeViewController().getFormScheme(o.getRelatedFormSchemeKey());
                TaskLinkEntityMappingRule relatedEntity = o.getRelatedEntity(this.beanHelper.getEntityUtil().getContextMainDimId(curFormScheme.getDw()));
                String relatedDw = relatedEntity == null ? formScheme.getDw() : relatedEntity.getSourceEntity();
                EntityData dwEntity = this.beanHelper.getEntityUtil().getEntity(relatedDw);
                EntityData periodEntity = this.beanHelper.getEntityUtil().getPeriodEntity(formScheme.getDateTime());
                List<EntityData> dimEntities = this.beanHelper.getEntityUtil().getDimEntities(formScheme);
                String relatedDateStr = this.getRelatedDateStr(this.executorContext, (TaskLinkDefine)o);
                this.relatedTaskMap.put(o.getLinkAlias(), new RelatedTaskInfo(formScheme.getTaskKey(), dwEntity, periodEntity, dimEntities, relatedDateStr));
            });
        }
    }

    public BeanHelper getBeanHelper() {
        return this.beanHelper;
    }

    public ReportFmlExecEnvironment getFmlExecEnvironment() {
        if (this.fmlExecEnvironment == null && this.executorContext.getEnv() instanceof ReportFmlExecEnvironment) {
            this.fmlExecEnvironment = (ReportFmlExecEnvironment)this.executorContext.getEnv();
        }
        return this.fmlExecEnvironment;
    }

    private String getRelatedDateStr(ExecutorContext context, TaskLinkDefine linkDefine) {
        String result = null;
        FormSchemeDefine formScheme = this.beanHelper.getRuntimeViewController().getFormScheme(linkDefine.getRelatedFormSchemeKey());
        IPeriodProvider periodAdapter = this.beanHelper.getPeriodEntityAdapter().getPeriodProvider(formScheme.getDateTime());
        if (periodAdapter == null) {
            periodAdapter = context.getPeriodAdapter();
        }
        try {
            result = (String)context.getVarDimensionValueSet().getValue(this.beanHelper.getPeriodEntityAdapter().getPeriodDimensionName());
            PeriodType periodType = formScheme.getPeriodType();
            switch (linkDefine.getConfiguration()) {
                case PERIOD_TYPE_OFFSET: {
                    String periodModiferStr = linkDefine.getPeriodOffset();
                    result = periodAdapter.modify(result, PeriodModifier.parse((String)periodModiferStr));
                    break;
                }
                case PERIOD_TYPE_SPECIFIED: {
                    result = linkDefine.getSpecified();
                    break;
                }
                case PERIOD_TYPE_NEXT: {
                    String periodModiferStr = "+1" + (char)PeriodConsts.typeToCode((int)periodType.type());
                    result = periodAdapter.modify(result, PeriodModifier.parse((String)periodModiferStr));
                    break;
                }
                case PERIOD_TYPE_PREVIOUS: {
                    String periodModiferStr = "-1" + (char)PeriodConsts.typeToCode((int)periodType.type());
                    result = periodAdapter.modify(result, PeriodModifier.parse((String)periodModiferStr));
                    break;
                }
                case PERIOD_TYPE_PREYEAR: {
                    FormSchemeDefine targetFormScheme = this.beanHelper.getRuntimeViewController().getFormScheme(linkDefine.getRelatedFormSchemeKey());
                    PeriodModifier periodModifier = PeriodModifier.parse((String)"-1N");
                    periodModifier.union(PeriodModifier.parse((String)"N"));
                    result = periodAdapter.modify(result, periodModifier, (IPeriodAdapter)this.beanHelper.getPeriodEntityAdapter().getPeriodProvider(targetFormScheme.getDateTime()));
                    break;
                }
            }
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return result;
    }

    public static class RelatedTaskInfo {
        String taskKey;
        EntityData dwEntity;
        EntityData periodEntity;
        List<EntityData> dimEntityList;
        String period;

        public RelatedTaskInfo(String taskKey, EntityData dwEntity, EntityData periodEntity, List<EntityData> dimEntityList, String period) {
            this.taskKey = taskKey;
            this.dwEntity = dwEntity;
            this.periodEntity = periodEntity;
            this.dimEntityList = dimEntityList;
            this.period = period;
        }

        public String getTaskKey() {
            return this.taskKey;
        }

        public EntityData getDwEntity() {
            return this.dwEntity;
        }

        public EntityData getPeriodEntity() {
            return this.periodEntity;
        }

        public List<EntityData> getDimEntityList() {
            return this.dimEntityList;
        }

        public String getPeriod() {
            return this.period;
        }
    }
}

