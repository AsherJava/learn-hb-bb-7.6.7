/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.define.gather.IBizModelGather
 *  com.jiuqi.bde.bizmodel.impl.dimension.service.FetchDimensionService
 *  com.jiuqi.bde.common.intf.Dimension
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.dc.mappingscheme.client.dto.DataSchemeDTO
 *  com.jiuqi.dc.mappingscheme.client.vo.AssistMappingVO
 *  com.jiuqi.dc.mappingscheme.client.vo.DimMappingVO
 *  com.jiuqi.dc.mappingscheme.impl.service.DataSchemeService
 *  com.jiuqi.gcreport.dimension.vo.DimensionVO
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 */
package com.jiuqi.bde.bizmodel.execute.assist;

import com.jiuqi.bde.bizmodel.define.gather.IBizModelGather;
import com.jiuqi.bde.bizmodel.execute.assist.AssistMappingBO;
import com.jiuqi.bde.bizmodel.execute.assist.IAcctAssist;
import com.jiuqi.bde.bizmodel.execute.assist.IAssistProviderGather;
import com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition;
import com.jiuqi.bde.bizmodel.execute.util.ModelExecuteUtil;
import com.jiuqi.bde.bizmodel.impl.dimension.service.FetchDimensionService;
import com.jiuqi.bde.common.intf.Dimension;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.dc.mappingscheme.client.dto.DataSchemeDTO;
import com.jiuqi.dc.mappingscheme.client.vo.AssistMappingVO;
import com.jiuqi.dc.mappingscheme.client.vo.DimMappingVO;
import com.jiuqi.dc.mappingscheme.impl.service.DataSchemeService;
import com.jiuqi.gcreport.dimension.vo.DimensionVO;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DataSchemeMappingProvider<AcctAssist extends IAcctAssist> {
    private BalanceCondition condi;
    private DataSchemeDTO dataScheme;
    private Map<String, AcctAssist> acctAssistMap;
    private Map<String, DimensionVO> dimensionMap;
    private Map<String, Dimension> executeDimMap;

    public DataSchemeMappingProvider(BalanceCondition condi) {
        this.condi = condi;
        this.dataScheme = ((DataSchemeService)ApplicationContextRegister.getBean(DataSchemeService.class)).getByCode(condi.getOrgMapping().getDataSchemeCode());
        this.executeDimMap = CollectionUtils.isEmpty(condi.getAssTypeList()) ? new HashMap<String, Dimension>() : condi.getAssTypeList().stream().collect(Collectors.toMap(Dimension::getDimCode, item -> item, (K1, K2) -> K1));
        this.initAssist();
    }

    protected void initAssist() {
        IBizModelGather bizModelGather = (IBizModelGather)ApplicationContextRegister.getBean(IBizModelGather.class);
        List dimensionList = ((FetchDimensionService)ApplicationContextRegister.getBean(FetchDimensionService.class)).listDimensionByDataModel(bizModelGather.getBizDataModelByComputationModel(this.condi.getComputationModel()).getCode());
        if (dimensionList.isEmpty()) {
            this.dimensionMap = new HashMap<String, DimensionVO>();
            this.acctAssistMap = new HashMap<String, AcctAssist>();
            return;
        }
        this.acctAssistMap = this.provideAcctAssist().stream().collect(Collectors.toMap(IAcctAssist::getCode, item -> item, (k1, k2) -> k2));
        if (!this.condi.isEnableDirectFilter()) {
            this.dimensionMap = dimensionList.stream().collect(Collectors.toMap(DimensionVO::getCode, item -> item, (k1, k2) -> k2));
            return;
        }
        this.dimensionMap = dimensionList.stream().filter(item -> this.executeDimMap.containsKey(item.getCode())).collect(Collectors.toMap(DimensionVO::getCode, item -> item, (k1, k2) -> k2));
    }

    protected List<AcctAssist> provideAcctAssist() {
        return ((IAssistProviderGather)ApplicationContextRegister.getBean(IAssistProviderGather.class)).getByPluginType(this.condi.getOrgMapping().getPluginType()).listAssist(this.condi.getOrgMapping().getDataSourceCode());
    }

    protected BalanceCondition getCondi() {
        return this.condi;
    }

    protected DataSchemeDTO getDataScheme() {
        return this.dataScheme;
    }

    protected Map<String, AcctAssist> getAcctAssistMap() {
        return this.acctAssistMap;
    }

    protected Map<String, DimensionVO> getDimensionMap() {
        return this.dimensionMap;
    }

    protected Map<String, Dimension> getExecuteDimMap() {
        return this.executeDimMap;
    }

    public String getOrgMappingType() {
        return this.dataScheme.getDataMapping().getOrgMapping().getOrgMappingType();
    }

    public String getOrgSql() {
        return ModelExecuteUtil.replaceContext(this.dataScheme.getDataMapping().getOrgMapping().getAdvancedSql(), this.condi);
    }

    public String getSubjectSql() {
        return ModelExecuteUtil.replaceContext(this.dataScheme.getDataMapping().getSubjectMapping().getAdvancedSql(), this.condi);
    }

    public String getCurrencySql() {
        return ModelExecuteUtil.replaceContext(this.dataScheme.getDataMapping().getCurrencyMapping().getAdvancedSql(), this.condi);
    }

    public String getCfItemSql() {
        return ModelExecuteUtil.replaceContext(this.dataScheme.getDataMapping().getCfitemMapping().getAdvancedSql(), this.condi);
    }

    public List<AssistMappingBO<AcctAssist>> getAssistMappingList() {
        List assistMappingList = this.dataScheme.getDataMapping().getAssistMapping();
        ArrayList assistMappingRes = CollectionUtils.newArrayList();
        for (AssistMappingVO assistMapping : assistMappingList) {
            if (!this.acctAssistMap.containsKey(assistMapping.getOdsFieldName()) || !this.dimensionMap.containsKey(assistMapping.getCode())) continue;
            if (this.condi.isEnableDirectFilter() && this.executeDimMap.get(assistMapping.getCode()) == null) {
                throw new BusinessRuntimeException(String.format("\u6ca1\u6709\u83b7\u53d6\u5230\u7ef4\u5ea6\u3010%1$s|%2$s\u3011\u7684\u57fa\u7840\u914d\u7f6e,\u8bf7\u68c0\u67e5\u6570\u636e\u53d6\u6570\u8bbe\u7f6e\u914d\u7f6e\u662f\u5426\u6b63\u786e", assistMapping.getCode(), assistMapping.getName()));
            }
            assistMappingRes.add(new AssistMappingBO<IAcctAssist>((DimMappingVO)assistMapping, this.dimensionMap.get(assistMapping.getCode()), (IAcctAssist)this.acctAssistMap.get(assistMapping.getOdsFieldName()), this.executeDimMap.get(assistMapping.getCode())));
        }
        return assistMappingRes;
    }

    public String buildAssistSql(AssistMappingBO<AcctAssist> assistMapping) {
        return ModelExecuteUtil.replaceContext(assistMapping.getAssistSql(), this.condi);
    }
}

