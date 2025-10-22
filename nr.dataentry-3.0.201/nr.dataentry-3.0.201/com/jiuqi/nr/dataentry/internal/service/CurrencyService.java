/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.common.util.DimensionValueSetUtil
 *  com.jiuqi.nr.data.access.param.EntityDimData
 *  com.jiuqi.nr.data.access.util.DataAccesslUtil
 *  com.jiuqi.nr.data.access.util.DimensionValueSetUtil
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.dataservice.core.dimension.VariableDimensionValue
 *  com.jiuqi.nr.dataservice.core.dimension.VariableDimensionValueProvider
 *  com.jiuqi.nr.dataservice.core.dimension.provider.DimensionProviderData
 *  com.jiuqi.nr.dataservice.core.dimension.provider.DimensionProviderFactory
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 */
package com.jiuqi.nr.dataentry.internal.service;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.common.util.DimensionValueSetUtil;
import com.jiuqi.nr.data.access.param.EntityDimData;
import com.jiuqi.nr.data.access.util.DataAccesslUtil;
import com.jiuqi.nr.dataentry.service.ICurrencyService;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.dataservice.core.dimension.VariableDimensionValue;
import com.jiuqi.nr.dataservice.core.dimension.VariableDimensionValueProvider;
import com.jiuqi.nr.dataservice.core.dimension.provider.DimensionProviderData;
import com.jiuqi.nr.dataservice.core.dimension.provider.DimensionProviderFactory;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CurrencyService
implements ICurrencyService {
    @Autowired
    private DimensionProviderFactory dimensionProviderFactory;
    @Autowired
    private IRunTimeViewController iRunTimeViewController;
    @Autowired
    private DataAccesslUtil dataAccesslUtil;

    @Override
    public List<String> getCurrencyInfo(JtableContext jtableContext, String currencyType) {
        TaskDefine taskDefine = this.iRunTimeViewController.queryTaskDefine(jtableContext.getTaskKey());
        DimensionProviderData providerData = new DimensionProviderData();
        providerData.setDataSchemeKey(taskDefine.getDataScheme());
        ArrayList<String> dwList = new ArrayList<String>();
        dwList.add(((DimensionValue)jtableContext.getDimensionSet().get("MD_CURRENCY")).getValue());
        VariableDimensionValueProvider dimensionProvider = this.dimensionProviderFactory.getDimensionProvider(currencyType, providerData);
        Map dimensionSet = jtableContext.getDimensionSet();
        DimensionValueSet dimensionValueSet = DimensionValueSetUtil.getDimensionValueSet((Map)dimensionSet);
        DimensionCombination collection = com.jiuqi.nr.data.access.util.DimensionValueSetUtil.buildDimensionCombination((DimensionValueSet)dimensionValueSet, (String)jtableContext.getFormSchemeKey());
        List dimEntities = this.dataAccesslUtil.getDimEntityDimData(jtableContext.getFormSchemeKey());
        EntityDimData entityDimDataOfCurrency = null;
        for (EntityDimData entityDimData : dimEntities) {
            if (!"MD_CURRENCY".equals(entityDimData.getDimensionName())) continue;
            entityDimDataOfCurrency = entityDimData;
        }
        if (entityDimDataOfCurrency != null) {
            VariableDimensionValue variableDimensionValue = new VariableDimensionValue(entityDimDataOfCurrency.getDimensionName(), entityDimDataOfCurrency.getEntityId(), dimensionProvider);
            return dimensionProvider.getValues(variableDimensionValue, collection);
        }
        return null;
    }
}

