/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.definition.common.Consts
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
import com.jiuqi.np.definition.common.Consts;
import com.jiuqi.nr.datascheme.api.DataDimension;
import com.jiuqi.nr.datascheme.api.type.DimensionType;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.dataservice.core.dimension.DimensionContext;
import com.jiuqi.nr.dataservice.core.dimension.DimensionEnvironment;
import com.jiuqi.nr.dataservice.core.dimension.FixedDimensionValue;
import com.jiuqi.nr.dataservice.core.dimension.VariableDimensionValue;
import com.jiuqi.nr.dataservice.core.dimension.provider.BaseCurrencyProvider;
import com.jiuqi.nr.dataservice.core.dimension.provider.DimensionProviderData;
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
import org.springframework.util.StringUtils;

public class PBaseCurrencyProvider
extends BaseCurrencyProvider {
    private static final long serialVersionUID = 1L;
    private final Logger log = LoggerFactory.getLogger(PBaseCurrencyProvider.class);
    private transient Map<String, String> parentCacheKeyMap;
    public static final String PROVIDER_PBASECURRENCY = "PROVIDER_PBASECURRENCY";
    private final transient Map<String, IEntityTable> entityTableMap = new HashMap<String, IEntityTable>();

    public PBaseCurrencyProvider(DimensionProviderData dimensionUnfoldData) {
        super(dimensionUnfoldData);
    }

    @Override
    public List<String> getValues(DimensionContext context, VariableDimensionValue variableDimensionValue, DimensionCombination dimensionCombination) {
        if (!variableDimensionValue.getName().equalsIgnoreCase("MD_CURRENCY")) {
            this.log.error("\u5f53\u524d\u7ef4\u5ea6\u4e0d\u662f\u5e01\u79cd\u7ef4\u5ea6\uff0c\u65e0\u6cd5\u5c55\u5f00\u672c\u4f4d\u5e01\uff01");
            return Collections.emptyList();
        }
        List dimensions = DimensionEnvironment.getDataSchemeService().getDataSchemeDimension(this.dimensionProviderData.getDataSchemeKey());
        Optional<DataDimension> dwOptional = dimensions.stream().filter(d -> d.getDimensionType() == DimensionType.UNIT).findFirst();
        if (!dwOptional.isPresent()) {
            return Collections.emptyList();
        }
        DataDimension schemeDWDimension = dwOptional.get();
        String dwDimensionName = DimensionEnvironment.getEntityMetaService().getDimensionName(schemeDWDimension.getDimKey());
        FixedDimensionValue dwDimension = dimensionCombination.getFixedDimensionValue(dwDimensionName);
        if (dwDimension == null) {
            return Collections.emptyList();
        }
        FixedDimensionValue periodDimension = dimensionCombination.getPeriodDimensionValue();
        String periodStr = periodDimension != null ? (String)periodDimension.getValue() : "NOPERIOD";
        String cacheKey = dwDimension.getValue() + periodStr;
        if (this.parentCacheKeyMap == null) {
            this.parentCacheKeyMap = new HashMap<String, String>();
        }
        if (this.parentCacheKeyMap.containsKey(cacheKey)) {
            return (List)this.valueCache.get(this.parentCacheKeyMap.get(cacheKey));
        }
        String parentDWValue = this.getParentDWValue(dimensionCombination, schemeDWDimension, dwDimension, periodStr);
        String parentCacheKey = parentDWValue + periodStr;
        this.parentCacheKeyMap.put(cacheKey, parentCacheKey);
        if (this.hasKey(parentCacheKey)) {
            return (List)this.valueCache.get(parentCacheKey);
        }
        List<String> result = new ArrayList<String>();
        String dimAttribute = "CURRENCYID";
        try {
            IEntityTable mdEntityTable = this.entityTableMap.get(periodStr);
            IEntityRow entityRow = mdEntityTable.findByEntityKey(parentDWValue);
            String dimValue = entityRow.getAsString(dimAttribute);
            List<String> dims = Arrays.stream(dimValue.split(";")).collect(Collectors.toList());
            result = this.filterValues(dims);
            if (result.isEmpty()) {
                List<String> choosedValues = this.dimensionProviderData.getChoosedValues();
                List<String> excludeValues = this.dimensionProviderData.getExcludeValues();
                this.log.info("\u672a\u7b5b\u9009\u51fa\u4e0a\u7ea7\u672c\u4f4d\u5e01\u3002dim:{},chooseValue:{},excludeValue:{}", dimValue, CollectionUtils.isEmpty(choosedValues) ? "" : String.join((CharSequence)",", choosedValues), CollectionUtils.isEmpty(excludeValues) ? "" : String.join((CharSequence)",", excludeValues));
            }
            this.valueCache.put(parentCacheKey, result);
        }
        catch (Exception e) {
            this.valueCache.put(parentCacheKey, Collections.emptyList());
            this.log.error("\u67e5\u8be2\u5b9e\u4f53\u6570\u636e\u51fa\u9519\u3002", e);
        }
        return result;
    }

    private String getParentDWValue(DimensionCombination dimensionCombination, DataDimension schemeDWDimension, FixedDimensionValue dwDimension, String periodStr) {
        String entityID = StringUtils.hasText(dwDimension.getEntityID()) ? dwDimension.getEntityID() : schemeDWDimension.getDimKey();
        IEntityTable mdEntityTable = this.entityTableMap.get(periodStr);
        if (mdEntityTable == null) {
            IEntityQuery dwEntityQuery = this.getDWEntityQuery(dimensionCombination, entityID);
            try {
                mdEntityTable = dwEntityQuery.executeFullBuild((IContext)this.getProviderUtil().getExecutorContext());
                this.entityTableMap.put(periodStr, mdEntityTable);
            }
            catch (Exception e) {
                this.log.error("\u7ef4\u5ea6\u5c55\u5f00\u67e5\u627e\u7236\u8282\u70b9\u51fa\u73b0\u5f02\u5e38\uff01", e);
                return (String)dwDimension.getValue();
            }
        }
        try {
            IEntityRow dwEntityRow = mdEntityTable.findByEntityKey((String)dwDimension.getValue());
            IEntityRow parentEntityRow = mdEntityTable.findByEntityKey(dwEntityRow.getParentEntityKey());
            if (parentEntityRow != null) {
                return parentEntityRow.getEntityKeyData();
            }
            return (String)dwDimension.getValue();
        }
        catch (Exception e) {
            this.log.error("\u7ef4\u5ea6\u5c55\u5f00\u67e5\u627e\u7236\u8282\u70b9\u51fa\u73b0\u5f02\u5e38\uff01", e);
            return (String)dwDimension.getValue();
        }
    }

    protected IEntityQuery getDWEntityQuery(DimensionCombination dimensionCombination, String entityID) {
        IEntityQuery entityQuery = DimensionEnvironment.getEntityDataService().newEntityQuery();
        entityQuery.setEntityView(DimensionEnvironment.getEntityViewRunTimeController().buildEntityView(entityID));
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
    protected String getCacheKey(VariableDimensionValue variableDimensionValue, String dw, String period) {
        return this.parentCacheKeyMap.get(dw + period);
    }

    @Override
    public String getID() {
        return PROVIDER_PBASECURRENCY;
    }

    @Override
    public String toString() {
        return "PBaseCurrencyProvider{dimensionProviderData=" + this.dimensionProviderData + '}';
    }
}

