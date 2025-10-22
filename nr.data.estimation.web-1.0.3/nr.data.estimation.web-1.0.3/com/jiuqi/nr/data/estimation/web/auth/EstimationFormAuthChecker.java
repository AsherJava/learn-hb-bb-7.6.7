/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.data.access.common.AccessLevel$FormAccessLevel
 *  com.jiuqi.nr.data.access.param.AccessFormParam
 *  com.jiuqi.nr.data.access.param.DimensionAccessFormInfo
 *  com.jiuqi.nr.data.access.service.IDataAccessFormService
 *  com.jiuqi.nr.data.access.service.IDataAccessServiceProvider
 *  com.jiuqi.nr.data.estimation.storage.entity.IEstimationScheme
 *  com.jiuqi.nr.data.estimation.storage.entity.IEstimationSchemeTemplate
 *  com.jiuqi.nr.data.estimation.storage.enumeration.EstimationFormType
 *  com.jiuqi.nr.datascheme.api.DataDimension
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DimensionType
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollectionBuilder
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.data.estimation.web.auth;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.data.access.common.AccessLevel;
import com.jiuqi.nr.data.access.param.AccessFormParam;
import com.jiuqi.nr.data.access.param.DimensionAccessFormInfo;
import com.jiuqi.nr.data.access.service.IDataAccessFormService;
import com.jiuqi.nr.data.access.service.IDataAccessServiceProvider;
import com.jiuqi.nr.data.estimation.storage.entity.IEstimationScheme;
import com.jiuqi.nr.data.estimation.storage.entity.IEstimationSchemeTemplate;
import com.jiuqi.nr.data.estimation.storage.enumeration.EstimationFormType;
import com.jiuqi.nr.data.estimation.web.auth.IEstimationFormAuthChecker;
import com.jiuqi.nr.datascheme.api.DataDimension;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DimensionType;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollectionBuilder;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class EstimationFormAuthChecker
implements IEstimationFormAuthChecker {
    @Resource
    private IEntityMetaService entityMetaService;
    @Resource
    public IPeriodEntityAdapter periodEntityAdapter;
    @Resource
    private IRuntimeDataSchemeService dataSchemeService;
    @Resource
    private IDataAccessServiceProvider dataAccessServiceProvider;

    @Override
    public List<String> getCanWriteInputForms(IEstimationScheme estimationScheme) {
        List inputFormKeys = estimationScheme.getEstimationForms().stream().filter(ef -> ef.getFormType() == EstimationFormType.inputForm).map(form -> form.getFormDefine().getKey()).collect(Collectors.toList());
        AccessFormParam accessFormParam = this.getAccessFormParam(estimationScheme);
        accessFormParam.setFormKeys(inputFormKeys);
        accessFormParam.setFormAccessLevel(AccessLevel.FormAccessLevel.FORM_DATA_WRITE);
        return this.executeAndReturnAccessFormKeys(accessFormParam);
    }

    @Override
    public List<String> getCanReadForms(IEstimationSchemeTemplate estimationSchemeTemplate, DimensionValueSet dimValueSet) {
        List esFormKeys = estimationSchemeTemplate.getEstimationForms().stream().map(e -> e.getFormDefine().getKey()).collect(Collectors.toList());
        AccessFormParam accessFormParam = this.getAccessFormParam(estimationSchemeTemplate, dimValueSet);
        accessFormParam.setFormKeys(esFormKeys);
        accessFormParam.setFormAccessLevel(AccessLevel.FormAccessLevel.FORM_READ);
        return this.executeAndReturnAccessFormKeys(accessFormParam);
    }

    private List<String> executeAndReturnAccessFormKeys(AccessFormParam accessFormParam) {
        IDataAccessFormService dataAccessFormService = this.dataAccessServiceProvider.getDataAccessFormService();
        DimensionAccessFormInfo dimensionAccessFormInfo = dataAccessFormService.getBatchAccessForms(accessFormParam);
        List accessForms = dimensionAccessFormInfo.getAccessForms();
        ArrayList<String> accessKeys = new ArrayList<String>();
        if (accessForms != null && !accessForms.isEmpty()) {
            accessForms.forEach(accessFormInfo -> accessKeys.addAll(accessFormInfo.getFormKeys()));
        }
        return accessKeys;
    }

    private AccessFormParam getAccessFormParam(IEstimationSchemeTemplate estimationSchemeTemplate, DimensionValueSet dimValueSet) {
        TaskDefine taskDefine = estimationSchemeTemplate.getTaskDefine();
        FormSchemeDefine formSchemeDefine = estimationSchemeTemplate.getFormSchemeDefine();
        return this.createAccessFormParam(taskDefine, formSchemeDefine, dimValueSet);
    }

    private AccessFormParam getAccessFormParam(IEstimationScheme estimationScheme) {
        TaskDefine taskDefine = estimationScheme.getTaskDefine();
        FormSchemeDefine formSchemeDefine = estimationScheme.getFormSchemeDefine();
        DimensionValueSet dimValueSet = estimationScheme.getDimValueSet();
        return this.createAccessFormParam(taskDefine, formSchemeDefine, dimValueSet);
    }

    private AccessFormParam createAccessFormParam(TaskDefine taskDefine, FormSchemeDefine formSchemeDefine, DimensionValueSet dimValueSet) {
        DimensionCollectionBuilder dimensionBuilder = this.getDimensionBuilder(taskDefine, dimValueSet);
        DimensionCollection dimensionCollection = dimensionBuilder.getCollection();
        AccessFormParam accessFormParam = new AccessFormParam();
        accessFormParam.setCollectionMasterKey(dimensionCollection);
        accessFormParam.setTaskKey(taskDefine.getKey());
        accessFormParam.setFormSchemeKey(formSchemeDefine.getKey());
        return accessFormParam;
    }

    private DimensionCollectionBuilder getDimensionBuilder(TaskDefine taskDefine, DimensionValueSet dimValueSet) {
        List dataSchemeDimension = this.dataSchemeService.getDataSchemeDimension(taskDefine.getDataScheme());
        DimensionCollectionBuilder dimensionBuilder = new DimensionCollectionBuilder();
        for (DataDimension dimension : dataSchemeDimension) {
            if ("ADJUST".equals(dimension.getDimKey())) {
                dimensionBuilder.setEntityValue("ADJUST", dimension.getDimKey(), new Object[]{dimValueSet.getValue("ADJUST")});
                continue;
            }
            String dimensionName = this.getDimensionName(dimension.getDimKey());
            if (DimensionType.UNIT == dimension.getDimensionType()) {
                dimensionBuilder.setEntityValue(dimensionName, dimension.getDimKey(), new Object[]{dimValueSet.getValue(dimensionName)});
                continue;
            }
            if (DimensionType.PERIOD == dimension.getDimensionType()) {
                dimensionBuilder.setEntityValue(dimensionName, dimension.getDimKey(), new Object[]{dimValueSet.getValue(dimensionName)});
                continue;
            }
            if (DimensionType.DIMENSION != dimension.getDimensionType()) continue;
            dimensionBuilder.setEntityValue(dimensionName, dimension.getDimKey(), new Object[]{dimValueSet.getValue(dimensionName)});
        }
        return dimensionBuilder;
    }

    private String getDimensionName(String entityId) {
        if (this.periodEntityAdapter.isPeriodEntity(entityId)) {
            return this.periodEntityAdapter.getPeriodEntity(entityId).getDimensionName();
        }
        return this.entityMetaService.queryEntity(entityId).getDimensionName();
    }
}

