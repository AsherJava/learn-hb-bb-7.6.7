/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.context.cxt.DsContextHolder
 *  com.jiuqi.nr.dataentry.service.ICopyDesDimensionService
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 */
package com.jiuqi.gcreport.nr.impl.service.impl;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.nr.impl.service.impl.GcDimensionProcessor;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.context.cxt.DsContextHolder;
import com.jiuqi.nr.dataentry.service.ICopyDesDimensionService;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GcCopyDesDimensionServiceImpl
implements ICopyDesDimensionService {
    @Autowired
    private IRunTimeViewController iRunTimeViewController;
    @Autowired
    private IEntityMetaService entityMetaService;
    private static Logger logger = LoggerFactory.getLogger(GcCopyDesDimensionServiceImpl.class);

    public List<DimensionValueSet> getSourceDimensionValueList(String targetTaskKey, String sourceTaskKey, Map<String, List<String>> targetDimValues) {
        String orgCategory;
        HashMap<String, List<String>> sourceDimensionMap = new HashMap<String, List<String>>(targetDimValues);
        String contextEntityId = DsContextHolder.getDsContext().getContextEntityId();
        if (StringUtils.isEmpty((String)contextEntityId)) {
            orgCategory = this.getEntitieTableByTaskKey(sourceTaskKey, sourceDimensionMap);
        } else {
            IEntityMetaService entityMetaService = (IEntityMetaService)SpringContextUtils.getBean(IEntityMetaService.class);
            TableModelDefine tableModelDefine = entityMetaService.getTableModel(contextEntityId);
            orgCategory = tableModelDefine.getName();
        }
        if (StringUtils.isEmpty((String)orgCategory)) {
            logger.error("\u83b7\u53d6\u7ec4\u7ec7\u673a\u6784\u8868\u4e0d\u80fd\u4e3a\u7a7a\uff0csourceTaskKey\uff1a" + sourceTaskKey);
            return null;
        }
        String periodStr = this.getPeriodStr(sourceDimensionMap);
        String formSchemeKey = this.getFormSchemeKey(sourceTaskKey, periodStr);
        return GcDimensionProcessor.getInstance().listDimensionValueSet(formSchemeKey, sourceDimensionMap, orgCategory);
    }

    private String getEntitieTableByTaskKey(String taskKey, Map<String, List<String>> dimensionMap) {
        TaskDefine taskDefine = this.iRunTimeViewController.queryTaskDefine(taskKey);
        if (taskDefine == null || StringUtils.isEmpty((String)taskDefine.getDw())) {
            logger.error("\u83b7\u53d6\u62a5\u8868\u65b9\u6848\u4fe1\u606f\u4e3a\u7a7a\uff0cformSchemeKey\uff1a" + taskKey);
            return null;
        }
        TableModelDefine tableDefine = this.getEntity(taskDefine.getDw());
        if (!StringUtils.isEmpty((String)taskDefine.getDims())) {
            String[] dimEntityIds;
            for (String dimEntityId : dimEntityIds = taskDefine.getDims().split(";")) {
                TableModelDefine dimTableModelDefine = this.getEntity(dimEntityId);
                if (dimTableModelDefine == null || StringUtils.isEmpty((String)dimTableModelDefine.getName()) || dimensionMap.containsKey(dimTableModelDefine.getName())) continue;
                dimensionMap.put(dimTableModelDefine.getName(), null);
            }
        }
        return tableDefine.getName();
    }

    private TableModelDefine getEntity(String entityId) {
        if (StringUtils.isEmpty((String)entityId)) {
            return null;
        }
        TableModelDefine tableDefine = this.entityMetaService.getTableModel(entityId);
        return tableDefine;
    }

    private String getFormSchemeKey(String taskId, String periodStr) {
        try {
            SchemePeriodLinkDefine schemePeriodLinkDefine = this.iRunTimeViewController.querySchemePeriodLinkByPeriodAndTask(periodStr, taskId);
            return schemePeriodLinkDefine.getSchemeKey();
        }
        catch (Exception e) {
            throw new BusinessRuntimeException((Throwable)e);
        }
    }

    private String getPeriodStr(Map<String, List<String>> sourceDimensionMap) {
        List<String> periodStrs = sourceDimensionMap.get("DATATIME");
        return periodStrs.get(0);
    }
}

