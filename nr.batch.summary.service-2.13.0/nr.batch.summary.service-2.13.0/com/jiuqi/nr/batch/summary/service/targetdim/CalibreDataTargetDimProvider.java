/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.batch.summary.storage.entity.SummaryScheme
 *  com.jiuqi.nr.calibre2.domain.CalibreDataDO
 *  com.jiuqi.nr.calibre2.domain.CalibreDataRegion
 *  com.jiuqi.nr.calibre2.domain.CalibreDefineDO
 *  com.jiuqi.nr.calibre2.domain.CalibreDefineDTO
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  com.jiuqi.nr.entity.engine.intf.IEntityItem
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 */
package com.jiuqi.nr.batch.summary.service.targetdim;

import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.batch.summary.service.targetdim.TargetDimProvider;
import com.jiuqi.nr.batch.summary.service.targetdim.TargetDimProviderFactoryImpl;
import com.jiuqi.nr.batch.summary.service.targetdim.TargetRangeUnitProvider;
import com.jiuqi.nr.batch.summary.storage.entity.SummaryScheme;
import com.jiuqi.nr.calibre2.domain.CalibreDataDO;
import com.jiuqi.nr.calibre2.domain.CalibreDataRegion;
import com.jiuqi.nr.calibre2.domain.CalibreDefineDO;
import com.jiuqi.nr.calibre2.domain.CalibreDefineDTO;
import com.jiuqi.nr.entity.engine.executors.ExecutorContext;
import com.jiuqi.nr.entity.engine.intf.IEntityItem;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CalibreDataTargetDimProvider
implements TargetDimProvider {
    private SummaryScheme summaryScheme;
    private TargetDimProviderFactoryImpl wrapper;
    private TargetRangeUnitProvider rangeUnitProvider;
    private List<CalibreDataDO> calibreData;
    private Map<String, List<String>> calibre2DetailRows;

    public CalibreDataTargetDimProvider(TargetDimProviderFactoryImpl wrapper, TargetRangeUnitProvider rangeUnitProvider, SummaryScheme summaryScheme) {
        this.wrapper = wrapper;
        this.summaryScheme = summaryScheme;
        this.rangeUnitProvider = rangeUnitProvider;
    }

    @Override
    public boolean isEmptyTargetDim(String period) {
        return this.getTargetDims(period).isEmpty();
    }

    @Override
    public List<String> getTargetDims(String period) {
        if (this.calibreData == null) {
            CalibreDefineDTO calibreDefineDTO = this.wrapper.queryCalibreDefine(this.summaryScheme.getTargetDim().getDimValue());
            this.calibreData = this.wrapper.listCalibreValues(calibreDefineDTO.getKey());
        }
        return this.calibreData.stream().map(CalibreDataDO::getCode).collect(Collectors.toList());
    }

    @Override
    public List<String> getEntityRowKeys(String period, String targetDimKey) {
        if (this.calibre2DetailRows == null) {
            this.calibre2DetailRows = this.initCalibre2DetailRows(period);
        }
        List<String> dimEntities = this.calibre2DetailRows.get(targetDimKey);
        return this.rangeUnitProvider.retainAll(period, dimEntities);
    }

    private Map<String, List<String>> initCalibre2DetailRows(String period) {
        HashMap<String, List<String>> map = new HashMap<String, List<String>>();
        CalibreDefineDTO calibreDefineDTO = this.wrapper.queryCalibreDefine(this.summaryScheme.getTargetDim().getDimValue());
        ExecutorContext executorContext = this.wrapper.newEntityQueryContext(this.summaryScheme.getTask(), period);
        DimensionValueSet dimensionValueSet = new DimensionValueSet();
        dimensionValueSet.setValue(this.wrapper.getPeriodDimName(this.summaryScheme.getTask(), period), (Object)period);
        dimensionValueSet.setValue(this.wrapper.getMainDimName(this.summaryScheme.getTask(), period), this.getAllEntityRow(period));
        List<CalibreDataRegion> dataRegions = this.wrapper.listCalibreDataItems(executorContext, (CalibreDefineDO)calibreDefineDTO, dimensionValueSet);
        for (CalibreDataRegion calibreDataRegion : dataRegions) {
            map.put(calibreDataRegion.getCalibreData().getCode(), calibreDataRegion.getEntityKeys());
        }
        return map;
    }

    private List<String> getAllEntityRow(String period) {
        IEntityQuery query = this.wrapper.newEntityQuery(this.summaryScheme.getTask(), period);
        ExecutorContext queryContext = this.wrapper.newEntityQueryContext(this.summaryScheme.getTask(), period);
        queryContext.setVarDimensionValueSet(query.getMasterKeys());
        List allRows = this.wrapper.executeWithReader(query, (IContext)queryContext).getAllRows();
        return allRows.stream().map(IEntityItem::getEntityKeyData).collect(Collectors.toList());
    }
}

