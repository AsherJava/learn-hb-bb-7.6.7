/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.client.intf.IBizDataModel
 *  com.jiuqi.bde.bizmodel.define.gather.IBizModelGather
 *  com.jiuqi.bde.bizmodel.execute.assist.AssistMappingBO
 *  com.jiuqi.bde.bizmodel.execute.assist.DataSchemeMappingProvider
 *  com.jiuqi.bde.bizmodel.execute.assist.IAcctAssist
 *  com.jiuqi.bde.bizmodel.execute.assist.impl.BaseAcctAssist
 *  com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition
 *  com.jiuqi.bde.common.intf.Dimension
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.dc.base.common.utils.BeanConvertUtil
 *  com.jiuqi.dc.mappingscheme.client.vo.AdvancedMappingVO
 *  com.jiuqi.dc.mappingscheme.client.vo.AssistMappingVO
 *  com.jiuqi.dc.mappingscheme.client.vo.DimMappingVO
 *  com.jiuqi.gcreport.dimension.vo.DimensionVO
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 */
package com.jiuqi.bde.plugin.sap.util;

import com.jiuqi.bde.bizmodel.client.intf.IBizDataModel;
import com.jiuqi.bde.bizmodel.define.gather.IBizModelGather;
import com.jiuqi.bde.bizmodel.execute.assist.AssistMappingBO;
import com.jiuqi.bde.bizmodel.execute.assist.DataSchemeMappingProvider;
import com.jiuqi.bde.bizmodel.execute.assist.IAcctAssist;
import com.jiuqi.bde.bizmodel.execute.assist.impl.BaseAcctAssist;
import com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition;
import com.jiuqi.bde.common.intf.Dimension;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.dc.base.common.utils.BeanConvertUtil;
import com.jiuqi.dc.mappingscheme.client.vo.AdvancedMappingVO;
import com.jiuqi.dc.mappingscheme.client.vo.AssistMappingVO;
import com.jiuqi.dc.mappingscheme.client.vo.DimMappingVO;
import com.jiuqi.gcreport.dimension.vo.DimensionVO;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import java.util.List;

public class SapDataSchemeMappingProvider
extends DataSchemeMappingProvider<BaseAcctAssist> {
    public SapDataSchemeMappingProvider(BalanceCondition condi) {
        super(condi);
    }

    protected List<BaseAcctAssist> provideAcctAssist() {
        return CollectionUtils.newArrayList();
    }

    public List<AssistMappingBO<BaseAcctAssist>> getAssistMappingList() {
        List assistMappingList = super.getAssistMappingList();
        IBizDataModel dataModel = ((IBizModelGather)ApplicationContextRegister.getBean(IBizModelGather.class)).getBizDataModelByComputationModel(this.getCondi().getComputationModel());
        List advancedMappingList = this.getDataScheme().getDataMapping().getAdvancedMapping();
        for (AdvancedMappingVO assistMapping : advancedMappingList) {
            if (!this.getDimensionMap().containsKey(assistMapping.getCode())) continue;
            if (this.getCondi().isEnableDirectFilter() && this.getExecuteDimMap().get(assistMapping.getCode()) == null) {
                throw new BusinessRuntimeException(String.format("\u6ca1\u6709\u83b7\u53d6\u5230\u7ef4\u5ea6\u3010%1$s|%2$s\u3011\u7684\u57fa\u7840\u914d\u7f6e,\u8bf7\u68c0\u67e5\u6570\u636e\u53d6\u6570\u8bbe\u7f6e\u914d\u7f6e\u662f\u5426\u6b63\u786e", assistMapping.getCode(), assistMapping.getName()));
            }
            if (StringUtils.isEmpty((String)((String)assistMapping.getBizMapping().get((Object)dataModel.getEffectScope())))) continue;
            assistMappingList.add(new AssistMappingBO((DimMappingVO)BeanConvertUtil.convert((Object)assistMapping, AssistMappingVO.class, (String[])new String[0]), (DimensionVO)this.getDimensionMap().get(assistMapping.getCode()), (IAcctAssist)new BaseAcctAssist((String)assistMapping.getBizMapping().get((Object)dataModel.getEffectScope()), (String)assistMapping.getBizMapping().get((Object)dataModel.getEffectScope())), (Dimension)this.getExecuteDimMap().get(assistMapping.getCode())));
        }
        return assistMappingList;
    }
}

