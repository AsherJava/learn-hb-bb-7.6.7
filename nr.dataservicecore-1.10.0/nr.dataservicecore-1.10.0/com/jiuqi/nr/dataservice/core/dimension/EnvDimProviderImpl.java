/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.internal.dto.DataDimDTO
 *  com.jiuqi.nr.definition.facade.EnvDimParam
 *  com.jiuqi.nr.definition.facade.EnvDimProvider
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 */
package com.jiuqi.nr.dataservice.core.dimension;

import com.jiuqi.nr.datascheme.internal.dto.DataDimDTO;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder;
import com.jiuqi.nr.dataservice.core.dimension.VariableDimensionValue;
import com.jiuqi.nr.dataservice.core.dimension.VariableDimensionValueProvider;
import com.jiuqi.nr.dataservice.core.dimension.provider.DimensionProviderData;
import com.jiuqi.nr.dataservice.core.dimension.provider.DimensionProviderFactory;
import com.jiuqi.nr.definition.facade.EnvDimParam;
import com.jiuqi.nr.definition.facade.EnvDimProvider;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EnvDimProviderImpl
implements EnvDimProvider {
    @Autowired
    private DimensionProviderFactory factory;
    @Autowired
    private IEntityMetaService entityMetaService;

    public Map<String, List<String>> getRelationDimValues(EnvDimParam dimParam) {
        HashMap<String, List<String>> returnMap = new HashMap<String, List<String>>();
        DataDimDTO dwDataDimDTO = dimParam.getDwDataDimDTO();
        DataDimDTO dataDimDTO = dimParam.getDataDimDTO();
        DimensionProviderData providerData = new DimensionProviderData();
        providerData.setDataSchemeKey(dwDataDimDTO.getDataSchemeKey());
        VariableDimensionValueProvider dimensionProvider = this.factory.getDimensionProvider("PROVIDER_FILTERDIMBYDW", providerData);
        List dws = dimParam.getDws();
        for (int i = 0; i < dws.size(); ++i) {
            DimensionCombinationBuilder combinationBuilder = new DimensionCombinationBuilder();
            combinationBuilder.setValue(this.entityMetaService.getDimensionName(dwDataDimDTO.getDimKey()), dwDataDimDTO.getDimKey(), dws.get(i));
            combinationBuilder.setValue("DATATIME", dimParam.getPeriodDimDTO().getDimKey(), dimParam.getPeriod());
            List<String> dims = dimensionProvider.getValues(new VariableDimensionValue(this.entityMetaService.getDimensionName(dataDimDTO.getDimKey()), dataDimDTO.getDimKey(), dimensionProvider), combinationBuilder.getCombination());
            returnMap.put((String)dws.get(i), dims);
        }
        return returnMap;
    }
}

