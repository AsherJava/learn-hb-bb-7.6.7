/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 */
package com.jiuqi.nr.datacrud.impl.crud.inner;

import com.jiuqi.nr.datacrud.IQueryInfo;
import com.jiuqi.nr.datacrud.IRegionDataSet;
import com.jiuqi.nr.datacrud.MultiDimensionalDataSet;
import com.jiuqi.nr.datacrud.RegionGradeInfo;
import com.jiuqi.nr.datacrud.api.IDataQueryService;
import com.jiuqi.nr.datacrud.impl.RegionRelation;
import com.jiuqi.nr.datacrud.impl.RegionRelationFactory;
import com.jiuqi.nr.datacrud.impl.crud.IRegionDataSetStrategy;
import com.jiuqi.nr.datacrud.impl.crud.inner.RegionDataSetStrategyFactory;
import com.jiuqi.nr.datacrud.impl.out.CrudException;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DataQueryServiceImpl
implements IDataQueryService {
    @Autowired
    private RegionDataSetStrategyFactory factory;
    @Autowired
    private RegionRelationFactory regionRelationFactory;

    @Override
    public IRegionDataSet queryRegionData(IQueryInfo queryInfo) {
        return this.queryRegionData(queryInfo, null);
    }

    @Override
    public IRegionDataSet queryRegionData(IQueryInfo queryInfo, RegionGradeInfo regionGradeInfo) {
        RegionRelation relation = queryInfo.getRegionRelation();
        if (relation == null) {
            relation = this.regionRelationFactory.getRegionRelation(queryInfo.getRegionKey());
        }
        if (regionGradeInfo != null) {
            relation.setGradeInfo(regionGradeInfo);
        } else {
            RegionGradeInfo gradeInfo = relation.getGradeInfo();
            relation.setGradeInfo(gradeInfo);
        }
        relation.setFormulaSchemeKey(queryInfo.getFormulaSchemeKey());
        IRegionDataSetStrategy strategy = this.factory.getStrategy(queryInfo);
        return strategy.queryData(queryInfo, relation);
    }

    @Override
    public int queryRegionDataCount(IQueryInfo queryInfo) {
        return this.queryRegionDataCount(queryInfo, null);
    }

    @Override
    public int queryRegionDataCount(IQueryInfo queryInfo, RegionGradeInfo regionGradeInfo) throws CrudException {
        RegionRelation relation = queryInfo.getRegionRelation();
        if (relation == null) {
            relation = this.regionRelationFactory.getRegionRelation(queryInfo.getRegionKey());
        }
        if (regionGradeInfo != null) {
            relation.setGradeInfo(regionGradeInfo);
        } else {
            RegionGradeInfo gradeInfo = relation.getGradeInfo();
            relation.setGradeInfo(gradeInfo);
        }
        relation.setFormulaSchemeKey(queryInfo.getFormulaSchemeKey());
        IRegionDataSetStrategy strategy = this.factory.getStrategy(queryInfo);
        return strategy.queryDataCount(queryInfo, relation);
    }

    @Override
    public int queryDataIndex(IQueryInfo queryInfo, String bizKeyOrder) {
        RegionRelation relation = queryInfo.getRegionRelation();
        if (relation == null) {
            relation = this.regionRelationFactory.getRegionRelation(queryInfo.getRegionKey());
        }
        relation.setFormulaSchemeKey(queryInfo.getFormulaSchemeKey());
        IRegionDataSetStrategy strategy = this.factory.getStrategy(queryInfo);
        return strategy.queryDataIndex(queryInfo, relation, bizKeyOrder);
    }

    @Override
    public IRegionDataSet dataLocate(IQueryInfo queryInfo, String bizKeyOrder) {
        return this.dataLocate(queryInfo, bizKeyOrder, 0);
    }

    @Override
    public IRegionDataSet dataLocate(IQueryInfo queryInfo, String bizKeyOrder, int offset) {
        RegionRelation relation = queryInfo.getRegionRelation();
        if (relation == null) {
            relation = this.regionRelationFactory.getRegionRelation(queryInfo.getRegionKey());
        }
        relation.setFormulaSchemeKey(queryInfo.getFormulaSchemeKey());
        IRegionDataSetStrategy strategy = this.factory.getStrategy(queryInfo);
        return strategy.regionDataLocate(queryInfo, relation, bizKeyOrder, offset);
    }

    @Override
    public int queryDataIndex(IQueryInfo queryInfo, DimensionCombination rowDim) throws CrudException {
        RegionRelation relation = queryInfo.getRegionRelation();
        if (relation == null) {
            relation = this.regionRelationFactory.getRegionRelation(queryInfo.getRegionKey());
        }
        relation.setFormulaSchemeKey(queryInfo.getFormulaSchemeKey());
        IRegionDataSetStrategy strategy = this.factory.getStrategy(queryInfo);
        return strategy.queryDataIndex(queryInfo, relation, rowDim);
    }

    @Override
    public IRegionDataSet dataLocate(IQueryInfo queryInfo, DimensionCombination rowDim) throws CrudException {
        return this.dataLocate(queryInfo, null, rowDim, 0);
    }

    @Override
    public IRegionDataSet dataLocate(IQueryInfo queryInfo, RegionGradeInfo regionGradeInfo, DimensionCombination rowDim) throws CrudException {
        return this.dataLocate(queryInfo, regionGradeInfo, rowDim, 0);
    }

    public IRegionDataSet dataLocate(IQueryInfo queryInfo, RegionGradeInfo regionGradeInfo, DimensionCombination rowDim, int offset) throws CrudException {
        RegionRelation relation = queryInfo.getRegionRelation();
        if (relation == null) {
            relation = this.regionRelationFactory.getRegionRelation(queryInfo.getRegionKey());
        }
        if (regionGradeInfo != null) {
            relation.setGradeInfo(regionGradeInfo);
        }
        relation.setFormulaSchemeKey(queryInfo.getFormulaSchemeKey());
        IRegionDataSetStrategy strategy = this.factory.getStrategy(queryInfo);
        return strategy.regionDataLocate(queryInfo, relation, rowDim, offset);
    }

    @Override
    public IRegionDataSet dataLocate(IQueryInfo queryInfo, DimensionCombination rowDim, int offset) throws CrudException {
        return this.dataLocate(queryInfo, null, rowDim, offset);
    }

    @Override
    public MultiDimensionalDataSet queryMultiDimRegionData(IQueryInfo queryInfo) throws CrudException {
        return this.queryMultiDimRegionData(queryInfo, null);
    }

    @Override
    public MultiDimensionalDataSet queryMultiDimRegionData(IQueryInfo queryInfo, RegionGradeInfo regionGradeInfo) throws CrudException {
        RegionRelation relation = queryInfo.getRegionRelation();
        if (relation == null) {
            relation = this.regionRelationFactory.getRegionRelation(queryInfo.getRegionKey());
        }
        relation.setFormulaSchemeKey(queryInfo.getFormulaSchemeKey());
        IRegionDataSetStrategy strategy = this.factory.getStrategy(queryInfo);
        return strategy.queryMultiDimensionalData(queryInfo, relation);
    }
}

