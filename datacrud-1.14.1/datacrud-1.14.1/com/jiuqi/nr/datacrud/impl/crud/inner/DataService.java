/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.nr.datacrud.impl.crud.inner;

import com.jiuqi.nr.datacrud.IClearInfo;
import com.jiuqi.nr.datacrud.ISaveInfo;
import com.jiuqi.nr.datacrud.ReturnRes;
import com.jiuqi.nr.datacrud.SaveReturnRes;
import com.jiuqi.nr.datacrud.api.IDataService;
import com.jiuqi.nr.datacrud.impl.RegionRelation;
import com.jiuqi.nr.datacrud.impl.RegionRelationFactory;
import com.jiuqi.nr.datacrud.impl.crud.IRegionDataSetUpdatableStrategy;
import com.jiuqi.nr.datacrud.impl.crud.inner.RegionDataSetStrategyFactory;
import com.jiuqi.nr.datacrud.impl.out.CrudException;
import com.jiuqi.nr.datacrud.impl.out.CrudOperateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DataService
implements IDataService {
    @Autowired
    private RegionDataSetStrategyFactory factory;
    @Autowired
    private RegionRelationFactory regionRelationFactory;

    @Override
    @Transactional(rollbackFor={CrudOperateException.class, CrudException.class})
    public ReturnRes clearRegionData(IClearInfo info) throws CrudOperateException {
        RegionRelation regionRelation = this.regionRelationFactory.getRegionRelation(info.getRegionKey());
        IRegionDataSetUpdatableStrategy updatableStrategy = this.factory.getUpdatableStrategy(regionRelation);
        return updatableStrategy.clearData(info, regionRelation);
    }

    @Override
    @Transactional(rollbackFor={CrudOperateException.class, CrudException.class})
    public SaveReturnRes saveRegionData(ISaveInfo saveInfo) throws CrudOperateException {
        RegionRelation regionRelation = saveInfo.getRegionRelation();
        if (regionRelation == null) {
            regionRelation = this.regionRelationFactory.getRegionRelation(saveInfo.getRegionKey());
        }
        regionRelation.resetMetaData();
        regionRelation.setFormulaSchemeKey(saveInfo.getFormulaSchemeKey());
        IRegionDataSetUpdatableStrategy updatableStrategy = this.factory.getUpdatableStrategy(regionRelation);
        return updatableStrategy.saveData(saveInfo, regionRelation);
    }
}

