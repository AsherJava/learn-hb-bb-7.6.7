/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.dataengine.var.VariableManager
 *  com.jiuqi.nr.batch.summary.storage.condition.CustomConditionHelper
 *  com.jiuqi.nr.batch.summary.storage.condition.CustomConditionRowProvider
 *  com.jiuqi.nr.batch.summary.storage.entity.SummaryScheme
 *  com.jiuqi.nr.batch.summary.storage.enumeration.TargetDimType
 *  com.jiuqi.nr.calibre2.ICalibreDefineService
 *  com.jiuqi.nr.calibre2.common.Result
 *  com.jiuqi.nr.calibre2.domain.CalibreDefineDTO
 *  com.jiuqi.nr.calibre2.internal.adapter.CalibreAdapter
 *  com.jiuqi.nr.entity.adapter.impl.basedata.data.BaseDataQuery
 *  com.jiuqi.nr.entity.adapter.impl.org.data.DefaultOrgDataExecutor
 *  com.jiuqi.nr.entity.adapter.provider.IDataQueryProvider
 *  com.jiuqi.nr.entity.adapter.provider.ProviderMethodEnum
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.param.IEntityQueryParam
 *  com.jiuqi.nr.entity.param.impl.EntityQueryParam
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.va.feign.client.BaseDataClient
 *  com.jiuqi.va.feign.client.BaseDataDefineClient
 *  com.jiuqi.va.feign.client.OrgCategoryClient
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.batch.summary.service.ext.entityframe;

import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.dataengine.var.VariableManager;
import com.jiuqi.nr.batch.summary.service.ext.entityframe.ConditionSubDataProvider;
import com.jiuqi.nr.batch.summary.service.ext.entityframe.CorporateEntityData;
import com.jiuqi.nr.batch.summary.service.ext.entityframe.EntityFrameExtendHelper;
import com.jiuqi.nr.batch.summary.service.ext.entityframe.OrgGatherSubDataBaseProvider;
import com.jiuqi.nr.batch.summary.storage.condition.CustomConditionHelper;
import com.jiuqi.nr.batch.summary.storage.condition.CustomConditionRowProvider;
import com.jiuqi.nr.batch.summary.storage.entity.SummaryScheme;
import com.jiuqi.nr.batch.summary.storage.enumeration.TargetDimType;
import com.jiuqi.nr.calibre2.ICalibreDefineService;
import com.jiuqi.nr.calibre2.common.Result;
import com.jiuqi.nr.calibre2.domain.CalibreDefineDTO;
import com.jiuqi.nr.calibre2.internal.adapter.CalibreAdapter;
import com.jiuqi.nr.entity.adapter.impl.basedata.data.BaseDataQuery;
import com.jiuqi.nr.entity.adapter.impl.org.data.DefaultOrgDataExecutor;
import com.jiuqi.nr.entity.adapter.provider.IDataQueryProvider;
import com.jiuqi.nr.entity.adapter.provider.ProviderMethodEnum;
import com.jiuqi.nr.entity.engine.executors.ExecutorContext;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.param.IEntityQueryParam;
import com.jiuqi.nr.entity.param.impl.EntityQueryParam;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.va.feign.client.BaseDataClient;
import com.jiuqi.va.feign.client.BaseDataDefineClient;
import com.jiuqi.va.feign.client.OrgCategoryClient;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class OrgGatherSubDataBaseExecutor
extends DefaultOrgDataExecutor {
    @Resource
    private OrgCategoryClient orgCategoryClient;
    @Resource
    private BaseDataDefineClient baseDataDefineClient;
    @Resource
    private BaseDataClient baseDataClient;
    @Resource
    private CalibreAdapter calibreAdapter;
    @Resource
    private IEntityMetaService entityMetaService;
    @Resource
    private ICalibreDefineService calibreDefineService;
    @Resource
    private CustomConditionHelper conditionHelper;
    @Resource
    private EntityFrameExtendHelper entityFrameExtendHelper;

    public boolean isEnable(IEntityQueryParam queryParam, ProviderMethodEnum providerEnum) {
        return this.entityFrameExtendHelper.isBatchSummaryEnv(queryParam, providerEnum);
    }

    public double getOrder() {
        return 4.0;
    }

    public IDataQueryProvider getDataQueryProvider(IEntityQueryParam queryParam) {
        ExecutorContext context = queryParam.getContext();
        SummaryScheme summaryScheme = this.entityFrameExtendHelper.getSummarySchemeFromEnv((IContext)context);
        if (summaryScheme != null) {
            VariableManager variableManager = this.entityFrameExtendHelper.getVariableManager((IContext)queryParam.getContext());
            Object dimensionValue = this.entityFrameExtendHelper.getVariableValue((IContext)context, variableManager, "dimValue");
            Map<String, CorporateEntityData> corporateColumn2Value = this.entityFrameExtendHelper.getCorporateColumn2Value(summaryScheme);
            TargetDimType targetDimType = summaryScheme.getTargetDim().getTargetDimType();
            EntityQueryParam newQueryParam = new EntityQueryParam();
            BeanUtils.copyProperties(queryParam, newQueryParam);
            switch (targetDimType) {
                case BASE_DATA: {
                    return this.madeBaseDataQuery(dimensionValue.toString(), newQueryParam, corporateColumn2Value);
                }
                case CALIBRE: {
                    return this.madeCalibreDataQuery(dimensionValue.toString(), newQueryParam, corporateColumn2Value);
                }
                case CONDITION: {
                    return this.madeConditionDataQuery(summaryScheme.getKey(), newQueryParam, corporateColumn2Value);
                }
            }
        }
        return super.getDataQueryProvider(queryParam);
    }

    public CalibreDefineDTO queryCalibreDefine(String calibreDefineId) {
        CalibreDefineDTO calibreDefineDTO = new CalibreDefineDTO();
        calibreDefineDTO.setCode(calibreDefineId);
        Result rs = this.calibreDefineService.get(calibreDefineDTO);
        return (CalibreDefineDTO)rs.getData();
    }

    private IDataQueryProvider madeBaseDataQuery(String dimensionValue, EntityQueryParam queryParam, Map<String, CorporateEntityData> corporateColumn2Value) {
        IEntityDefine iEntityDefine = this.entityMetaService.queryEntity(dimensionValue);
        queryParam.setEntityId(iEntityDefine.getCode());
        return new OrgGatherSubDataBaseProvider((IEntityQueryParam)queryParam, (IDataQueryProvider)new BaseDataQuery(this.baseDataDefineClient, (IEntityQueryParam)queryParam, this.baseDataClient), corporateColumn2Value);
    }

    private IDataQueryProvider madeCalibreDataQuery(String dimensionValue, EntityQueryParam queryParam, Map<String, CorporateEntityData> corporateColumn2Value) {
        CalibreDefineDTO calibreDefineDTO = this.queryCalibreDefine(dimensionValue);
        queryParam.setEntityId(calibreDefineDTO.getCode());
        return new OrgGatherSubDataBaseProvider((IEntityQueryParam)queryParam, this.calibreAdapter, corporateColumn2Value);
    }

    private IDataQueryProvider madeConditionDataQuery(String schemeKey, EntityQueryParam queryParam, Map<String, CorporateEntityData> corporateColumn2Value) {
        CustomConditionRowProvider treeProvider = this.conditionHelper.getTreeProvider(schemeKey);
        return new ConditionSubDataProvider(treeProvider, corporateColumn2Value, (IEntityQueryParam)queryParam);
    }
}

