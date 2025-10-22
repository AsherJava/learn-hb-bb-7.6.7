/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.data.access.service.IDataAccessServiceProvider
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.definition.common.DataRegionKind
 *  com.jiuqi.nr.definition.common.FormType
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeExpressionService
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  com.jiuqi.nvwa.encryption.desensitization.common.DesensitizedEncryptor
 */
package com.jiuqi.nr.datacrud.impl.crud.inner;

import com.jiuqi.nr.data.access.service.IDataAccessServiceProvider;
import com.jiuqi.nr.datacrud.IQueryInfo;
import com.jiuqi.nr.datacrud.RegionGradeInfo;
import com.jiuqi.nr.datacrud.common.SplitUtil;
import com.jiuqi.nr.datacrud.impl.RegionDataSetFactory;
import com.jiuqi.nr.datacrud.impl.RegionRelation;
import com.jiuqi.nr.datacrud.impl.RegionRelationFactory;
import com.jiuqi.nr.datacrud.impl.crud.IRegionDataSetStrategy;
import com.jiuqi.nr.datacrud.impl.crud.IRegionDataSetUpdatableStrategy;
import com.jiuqi.nr.datacrud.impl.crud.strategy.FMDMRegionDataSetStrategy;
import com.jiuqi.nr.datacrud.impl.crud.strategy.FloatGroupRegionDataStrategy;
import com.jiuqi.nr.datacrud.impl.crud.strategy.FloatRegionDataStrategy;
import com.jiuqi.nr.datacrud.impl.crud.strategy.FloatRegionDataUpdatableStrategy;
import com.jiuqi.nr.datacrud.impl.crud.strategy.FloatRegionGradeStrategy;
import com.jiuqi.nr.datacrud.impl.crud.strategy.NestedFloatRegionStrategy;
import com.jiuqi.nr.datacrud.impl.crud.strategy.SimpleRegionDataStrategy;
import com.jiuqi.nr.datacrud.impl.crud.strategy.SimpleRegionDataUpdatableStrategy;
import com.jiuqi.nr.datacrud.impl.loggger.DataServiceLogWrapper;
import com.jiuqi.nr.datacrud.impl.service.DataEngineService;
import com.jiuqi.nr.datacrud.spi.FillDataProvider;
import com.jiuqi.nr.datacrud.spi.IEntityTableFactory;
import com.jiuqi.nr.datacrud.spi.IExecutorContextFactory;
import com.jiuqi.nr.datacrud.util.DataValueProcessorFactory;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.definition.common.DataRegionKind;
import com.jiuqi.nr.definition.common.FormType;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeExpressionService;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nvwa.definition.service.DataModelService;
import com.jiuqi.nvwa.encryption.desensitization.common.DesensitizedEncryptor;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
public class RegionDataSetStrategyFactory {
    @Autowired
    private FMDMRegionDataSetStrategy fmdmRegionDataSetStrategy;
    @Autowired
    private RegionRelationFactory regionRelationFactory;
    @Autowired
    private DataEngineService dataEngineService;
    @Autowired
    private IDataAccessServiceProvider dataAccessServiceProvider;
    @Autowired
    private IRuntimeExpressionService expressionService;
    @Autowired
    private IRuntimeDataSchemeService runtimeDataSchemeService;
    @Autowired
    private FillDataProvider fillDataProvider;
    @Autowired
    private DataServiceLogWrapper dataServiceLogWrapper;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private IExecutorContextFactory executorContextFactory;
    @Autowired
    private DataValueProcessorFactory dataValueProcessorFactory;
    @Autowired
    private RegionDataSetFactory regionDataSetFactory;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private DataModelService dataModelService;
    @Autowired
    private IEntityTableFactory entityTableFactory;
    @Autowired
    private DesensitizedEncryptor encryptor;

    public IRegionDataSetStrategy getStrategy(IQueryInfo queryInfo) {
        List<String> split;
        if (queryInfo == null) {
            return null;
        }
        RegionRelation relation = queryInfo.getRegionRelation();
        if (relation == null) {
            return null;
        }
        FormDefine formDefine = relation.getFormDefine();
        if (formDefine.getFormType() == FormType.FORM_TYPE_NEWFMDM) {
            return this.fmdmRegionDataSetStrategy;
        }
        DataRegionDefine regionDefine = relation.getRegionDefine();
        if (regionDefine.getRegionKind() == DataRegionKind.DATA_REGION_SIMPLE) {
            return new SimpleRegionDataStrategy(this);
        }
        if (queryInfo.groupItr() != null) {
            return new FloatGroupRegionDataStrategy(this);
        }
        String displayLevel = regionDefine.getDisplayLevel();
        if (StringUtils.hasText(displayLevel) && !CollectionUtils.isEmpty(split = SplitUtil.split(displayLevel)) && split.size() > 1) {
            return new NestedFloatRegionStrategy(this);
        }
        RegionGradeInfo gradeInfo = relation.getGradeInfo();
        if (gradeInfo.isGrade() || gradeInfo.isQuerySummary()) {
            return new FloatRegionGradeStrategy(this);
        }
        return new FloatRegionDataStrategy(this);
    }

    public IRegionDataSetUpdatableStrategy getUpdatableStrategy(String regionKey) {
        RegionRelation relation = this.regionRelationFactory.getRegionRelation(regionKey);
        return this.getUpdatableStrategy(relation);
    }

    public IRegionDataSetUpdatableStrategy getUpdatableStrategy(RegionRelation relation) {
        if (relation.getFormDefine().getFormType() == FormType.FORM_TYPE_NEWFMDM) {
            return this.fmdmRegionDataSetStrategy;
        }
        if (relation.getRegionDefine().getRegionKind() == DataRegionKind.DATA_REGION_SIMPLE) {
            return new SimpleRegionDataUpdatableStrategy(this.regionRelationFactory, this.executorContextFactory, this.dataEngineService, this.dataAccessServiceProvider, this.expressionService, this.dataServiceLogWrapper, this.entityMetaService);
        }
        return new FloatRegionDataUpdatableStrategy(this.regionRelationFactory, this.executorContextFactory, this.dataEngineService, this.dataAccessServiceProvider, this.expressionService, this.dataServiceLogWrapper, this.entityMetaService);
    }

    public FMDMRegionDataSetStrategy getFmdmRegionDataSetStrategy() {
        return this.fmdmRegionDataSetStrategy;
    }

    public RegionRelationFactory getRegionRelationFactory() {
        return this.regionRelationFactory;
    }

    public DataEngineService getDataEngineService() {
        return this.dataEngineService;
    }

    public IDataAccessServiceProvider getDataAccessServiceProvider() {
        return this.dataAccessServiceProvider;
    }

    public IRuntimeExpressionService getExpressionService() {
        return this.expressionService;
    }

    public IRuntimeDataSchemeService getRuntimeDataSchemeService() {
        return this.runtimeDataSchemeService;
    }

    public FillDataProvider getFillDataProvider() {
        return this.fillDataProvider;
    }

    public DataServiceLogWrapper getDataServiceLogWrapper() {
        return this.dataServiceLogWrapper;
    }

    public IRunTimeViewController getRunTimeViewController() {
        return this.runTimeViewController;
    }

    public IExecutorContextFactory getExecutorContextFactory() {
        return this.executorContextFactory;
    }

    public DataValueProcessorFactory getDataValueProcessorFactory() {
        return this.dataValueProcessorFactory;
    }

    public RegionDataSetFactory getRegionDataSetFactory() {
        return this.regionDataSetFactory;
    }

    public DataModelService getDataModelService() {
        return this.dataModelService;
    }

    public IEntityTableFactory getEntityTableFactory() {
        return this.entityTableFactory;
    }

    public DesensitizedEncryptor getDesensitizedEncryptor() {
        return this.encryptor;
    }

    public IEntityMetaService getEntityMetaService() {
        return this.entityMetaService;
    }
}

