/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.common.DataRegionKind
 *  com.jiuqi.nr.definition.common.FormType
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormDefine
 */
package com.jiuqi.nr.io.service.impl;

import com.jiuqi.nr.definition.common.DataRegionKind;
import com.jiuqi.nr.definition.common.FormType;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.io.dataset.IRegionDataSet;
import com.jiuqi.nr.io.dataset.impl.RegionDataSet;
import com.jiuqi.nr.io.fmdm.dataset.impl.FMDMRegionDataSet;
import com.jiuqi.nr.io.params.base.RegionData;
import com.jiuqi.nr.io.params.base.TableContext;
import com.jiuqi.nr.io.sb.dataset.impl.SBRegionDataSet;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RegionsDataSetProvider {
    @Autowired
    IRunTimeViewController runTimeViewController;

    public IRegionDataSet getRegionDataSet(TableContext context, RegionData regionData) {
        FormDefine form = this.runTimeViewController.queryFormById(regionData.getFormKey());
        if (Objects.isNull(form)) {
            return null;
        }
        if (form.getFormType().equals((Object)FormType.FORM_TYPE_NEWFMDM)) {
            return new FMDMRegionDataSet(context, regionData);
        }
        if (form.getFormType().equals((Object)FormType.FORM_TYPE_ACCOUNT) && regionData.getType() != DataRegionKind.DATA_REGION_SIMPLE.getValue()) {
            return new SBRegionDataSet(context, regionData);
        }
        return new RegionDataSet(context, regionData);
    }
}

