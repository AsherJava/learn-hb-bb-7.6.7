/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.service.IFormSchemeService
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.jtable.params.base.EntityViewData
 *  com.jiuqi.nr.jtable.service.IJtableParamService
 *  nr.single.map.data.TaskDataContext
 */
package nr.single.client.internal.service.entity;

import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.service.IFormSchemeService;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.jtable.params.base.EntityViewData;
import com.jiuqi.nr.jtable.service.IJtableParamService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import nr.single.client.internal.service.export.ExportJIOServiceImpl;
import nr.single.client.service.entity.SingleEntityTypeService;
import nr.single.map.data.TaskDataContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class SingleEntityTypeServiceImpl
implements SingleEntityTypeService {
    private static final Logger log = LoggerFactory.getLogger(ExportJIOServiceImpl.class);
    @Autowired
    private IRunTimeViewController runtimeView;
    @Autowired
    private IJtableParamService jtableParamService;
    @Autowired
    private IFormSchemeService formSchemeService;
    @Autowired
    private IEntityMetaService entityMetaService;

    @Override
    public void getEntityType(TaskDataContext context, String formSchemeKey, Map<String, DimensionValue> dimValueSet) {
        String dateType = "";
        String companyType = "";
        FormSchemeDefine formScheme = this.runtimeView.getFormScheme(formSchemeKey);
        TaskDefine task = this.runtimeView.queryTaskDefine(formScheme.getTaskKey());
        ArrayList entityList = null;
        try {
            entityList = this.jtableParamService.getEntityList(formSchemeKey);
        }
        catch (Exception e) {
            entityList = new ArrayList();
            log.error(e.getMessage(), e);
        }
        HashSet dimEntityIdList = new HashSet();
        if (com.jiuqi.bi.util.StringUtils.isNotEmpty((String)task.getDims())) {
            String[] dimEntityIds = task.getDims().split(";");
            Collections.addAll(dimEntityIdList, dimEntityIds);
        }
        for (EntityViewData entityInfo : entityList) {
            if (entityInfo.getKey().equalsIgnoreCase(task.getDateTime())) {
                dateType = entityInfo.getDimensionName();
            } else if (entityInfo.isMasterEntity()) {
                companyType = entityInfo.getDimensionName();
            } else if (!entityInfo.isShowDimEntity() && dimEntityIdList.contains(entityInfo.getKey())) {
                context.getDimEntityCache().getEntitySingleDims().add(entityInfo.getDimensionName());
                context.getDimEntityCache().getEntitySingleIds().add(entityInfo.getKey());
                String fieldName = this.getSingleDimFieldName(formSchemeKey, entityInfo.getKey());
                context.getDimEntityCache().getEntitySingleDimAndFields().put(entityInfo.getDimensionName(), fieldName);
                DimensionValue oldDim = dimValueSet.get(entityInfo.getDimensionName());
                DimensionValue newDim = new DimensionValue(oldDim);
                context.getDimEntityCache().getSingleDimValueSet().put(entityInfo.getDimensionName(), newDim);
            }
            context.getDimEntityCache().getEntityDimAndEntityIds().put(entityInfo.getDimensionName(), entityInfo.getKey());
        }
        context.setEntityCompanyType(companyType);
        context.setEntityDateType(dateType);
        String fmdmEntityId = task.getDw();
        context.setDwEntityId(fmdmEntityId);
        this.setOtherDims(context, dimValueSet);
    }

    private void setOtherDims(TaskDataContext context, Map<String, DimensionValue> dimValueSet) {
        String corpCodes;
        String[] corpArray;
        DimensionValue periodDimValue = dimValueSet.get(context.getEntityDateType());
        DimensionValue companyDimValue = dimValueSet.get(context.getEntityCompanyType());
        String corpID = null;
        if (null != companyDimValue && com.jiuqi.bi.util.StringUtils.isNotEmpty((String)companyDimValue.getValue()) && (corpArray = (corpCodes = companyDimValue.getValue()).split(";")).length > 0 && corpArray[0].indexOf(64) < 0) {
            corpID = corpArray[0];
            context.setCurrentEntintyKey(corpID);
        }
        if (null != periodDimValue) {
            context.setNetPeriodCode(periodDimValue.getValue());
        }
        context.getOtherDims().clear();
        for (String dimCode : dimValueSet.keySet()) {
            if (!com.jiuqi.bi.util.StringUtils.isNotEmpty((String)dimCode) || dimCode.equalsIgnoreCase(context.getEntityCompanyType()) || dimCode.equalsIgnoreCase(context.getEntityDateType())) continue;
            context.getOtherDims().put(dimCode, dimValueSet.get(dimCode));
        }
    }

    public boolean getIsSingleDimEntity(String formSchemeKey, String entityID) {
        String dimensionEntity = this.runtimeView.getFormScheme(formSchemeKey).getDw();
        String id = this.formSchemeService.getDimAttributeByReportDim(formSchemeKey, entityID);
        if (StringUtils.hasText(id)) {
            return this.entityMetaService.getEntityModel(dimensionEntity).getAttribute(id).isMultival();
        }
        return true;
    }

    private String getSingleDimFieldName(String formSchemeKey, String entityID) {
        return this.formSchemeService.getDimAttributeByReportDim(formSchemeKey, entityID);
    }
}

