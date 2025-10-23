/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.nr.fielddatacrud.RegionPO
 *  com.jiuqi.nr.fielddatacrud.spi.ParamProvider
 */
package com.jiuqi.nr.migration.transferdata.dbservice;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.fielddatacrud.RegionPO;
import com.jiuqi.nr.fielddatacrud.spi.ParamProvider;
import com.jiuqi.nr.migration.transferdata.bean.FetchSaveDataParam;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TransferParamProviderImpl
implements ParamProvider {
    private FetchSaveDataParam vo;

    public TransferParamProviderImpl(FetchSaveDataParam vo) {
        this.vo = vo;
    }

    public Set<RegionPO> getRegions(String dataTableCode, List<String> dataFieldCodes) {
        HashSet<RegionPO> curSet = new HashSet<RegionPO>();
        RegionPO po = new RegionPO();
        if (StringUtils.isNotEmpty((String)this.vo.getTaskKey())) {
            po.setTaskKey(this.vo.getTaskKey());
        }
        if (StringUtils.isNotEmpty((String)this.vo.getFormKey())) {
            po.setFormKey(this.vo.getFormKey());
        }
        if (StringUtils.isNotEmpty((String)this.vo.getFormSchemeKey())) {
            po.setFormSchemeKey(this.vo.getFormSchemeKey());
        }
        curSet.add(po);
        return curSet;
    }
}

