/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.np.definition.common.Consts
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.nr.datascheme.api.DataDimension
 *  com.jiuqi.nr.datascheme.api.type.DimensionType
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.engine.setting.AuthorityType
 *  com.jiuqi.nr.period.internal.adapter.IPeriodProvider
 */
package com.jiuqi.nr.dataservice.core.dimension.provider;

import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.np.definition.common.Consts;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.nr.datascheme.api.DataDimension;
import com.jiuqi.nr.datascheme.api.type.DimensionType;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.dataservice.core.dimension.DimensionContext;
import com.jiuqi.nr.dataservice.core.dimension.DimensionEnvironment;
import com.jiuqi.nr.dataservice.core.dimension.FixedDimensionValue;
import com.jiuqi.nr.dataservice.core.dimension.VariableDimensionValue;
import com.jiuqi.nr.dataservice.core.dimension.VariableDimensionValueProvider;
import com.jiuqi.nr.dataservice.core.dimension.provider.AllNodeDimensionProvider;
import com.jiuqi.nr.dataservice.core.dimension.provider.DimensionProviderData;
import com.jiuqi.nr.dataservice.core.dimension.provider.DimensionProviderUtil;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.engine.setting.AuthorityType;
import com.jiuqi.nr.period.internal.adapter.IPeriodProvider;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

public class BaseCurrencyProvider
extends AllNodeDimensionProvider
implements VariableDimensionValueProvider {
    private static final long serialVersionUID = 1L;
    private Logger log = LoggerFactory.getLogger(BaseCurrencyProvider.class);
    protected final DimensionProviderData dimensionProviderData;
    protected transient DimensionProviderUtil providerUtil;
    public static final String PROVIDER_BASECURRENCY = "PROVIDER_BASECURRENCY";
    private final transient Map<String, IEntityTable> entityTableMap = new HashMap<String, IEntityTable>();

    public BaseCurrencyProvider(DimensionProviderData dimensionProviderData) {
        this.dimensionProviderData = dimensionProviderData;
    }

    @Override
    public List<String> getValues(DimensionContext context, VariableDimensionValue variableDimensionValue, DimensionCombination dimensionCombination) {
        FixedDimensionValue periodDimension;
        String periodStr;
        if (!variableDimensionValue.getName().equalsIgnoreCase("MD_CURRENCY")) {
            this.log.error("\u5f53\u524d\u7ef4\u5ea6\u4e0d\u662f\u5e01\u79cd\u7ef4\u5ea6\uff0c\u65e0\u6cd5\u5c55\u5f00\u672c\u4f4d\u5e01\uff01");
            return Collections.emptyList();
        }
        FixedDimensionValue dwDimension = dimensionCombination.getDWDimensionValue();
        if (dwDimension == null) {
            List dimensions = DimensionEnvironment.getDataSchemeService().getDataSchemeDimension(this.dimensionProviderData.getDataSchemeKey());
            Optional<DataDimension> dwOptional = dimensions.stream().filter(d -> d.getDimensionType() == DimensionType.UNIT).findFirst();
            if (!dwOptional.isPresent()) {
                return Collections.emptyList();
            }
            DataDimension schemeDWDimension = dwOptional.get();
            String dwDimensionName = DimensionEnvironment.getEntityMetaService().getDimensionName(schemeDWDimension.getDimKey());
            dwDimension = dimensionCombination.getFixedDimensionValue(dwDimensionName);
        }
        if (dwDimension == null) {
            return Collections.emptyList();
        }
        String dwValue = (String)dwDimension.getValue();
        String cacheKey = this.getCacheKey(variableDimensionValue, dwValue, periodStr = (periodDimension = dimensionCombination.getPeriodDimensionValue()) != null ? (String)periodDimension.getValue() : "NOPERIOD");
        if (this.hasKey(cacheKey)) {
            return (List)this.valueCache.get(cacheKey);
        }
        List<String> result = new ArrayList<String>();
        String dimAttribute = "CURRENCYID";
        try {
            IEntityRow entityRow;
            String dimValue;
            List<String> dims;
            IEntityTable entityTable = this.entityTableMap.get(periodStr);
            if (entityTable == null) {
                IEntityQuery dwEntityQuery = this.getDWEntityQuery(context, dwDimension, dimensionCombination);
                entityTable = dwEntityQuery.executeFullBuild((IContext)this.getProviderUtil().getExecutorContext());
                this.entityTableMap.put(periodStr, entityTable);
            }
            if ((result = this.filterValues(dims = Arrays.stream((dimValue = (entityRow = entityTable.findByEntityKey((String)dwDimension.getValue())).getAsString(dimAttribute)).split(";")).collect(Collectors.toList()))).isEmpty()) {
                List<String> choosedValues = this.dimensionProviderData.getChoosedValues();
                List<String> excludeValues = this.dimensionProviderData.getExcludeValues();
                this.log.info("\u672a\u7b5b\u9009\u51fa\u672c\u4f4d\u5e01\u3002dim:{},chooseValue:{},excludeValue:{}", dimValue, CollectionUtils.isEmpty(choosedValues) ? "" : String.join((CharSequence)",", choosedValues), CollectionUtils.isEmpty(excludeValues) ? "" : String.join((CharSequence)",", excludeValues));
            }
            this.valueCache.put(cacheKey, result);
        }
        catch (Exception e) {
            this.valueCache.put(cacheKey, Collections.emptyList());
            this.log.error("\u67e5\u8be2\u5b9e\u4f53\u6570\u636e\u51fa\u9519\u3002", e);
        }
        return result;
    }

    protected IEntityQuery getDWEntityQuery(DimensionContext context, FixedDimensionValue dwDimension, DimensionCombination dimensionCombination) {
        IEntityQuery entityQuery = DimensionEnvironment.getEntityDataService().newEntityQuery();
        EntityViewDefine entityViewDefine = this.getProviderUtil().getEntityViewDefine(context, dwDimension, this.dimensionProviderData);
        entityQuery.setEntityView(entityViewDefine);
        FixedDimensionValue periodDimension = dimensionCombination.getPeriodDimensionValue();
        if (periodDimension == null) {
            entityQuery.setQueryVersionDate(Consts.DATE_VERSION_FOR_ALL);
        } else {
            try {
                IPeriodProvider periodProvider = DimensionEnvironment.getPeriodEntityAdapter().getPeriodProvider(periodDimension.getEntityID());
                entityQuery.setQueryVersionDate(periodProvider.getPeriodDateRegion((String)periodDimension.getValue())[1]);
            }
            catch (Exception e) {
                entityQuery.setQueryVersionDate(Consts.DATE_VERSION_FOR_ALL);
                this.log.error("\u83b7\u53d6\u65f6\u671f\u5931\u8d25" + e.getMessage());
            }
        }
        entityQuery.setAuthorityOperations(AuthorityType.None);
        return entityQuery;
    }

    @Override
    public boolean isDynamicByDW(VariableDimensionValue variableDimensionValue) {
        return true;
    }

    @Override
    public String getID() {
        return PROVIDER_BASECURRENCY;
    }

    @Override
    protected DimensionProviderUtil getProviderUtil() {
        if (this.providerUtil == null) {
            this.providerUtil = (DimensionProviderUtil)SpringBeanUtils.getBean(DimensionProviderUtil.class);
        }
        return this.providerUtil;
    }

    @Override
    protected String getCacheKey(VariableDimensionValue variableDimensionValue, String dw, String period) {
        return dw + period;
    }

    @Override
    public DimensionProviderData getDimensionProviderData() {
        return this.dimensionProviderData;
    }

    @Override
    public String toString() {
        return "BaseCurrencyProvider{dimensionProviderData=" + this.dimensionProviderData + '}';
    }
}

