/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.common.StringUtils
 */
package com.jiuqi.nr.definition.internal.service;

import com.jiuqi.np.definition.common.StringUtils;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.BigDataDefine;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.facade.RegionSettingDefine;
import com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeBigDataService;
import com.jiuqi.nr.definition.util.ExtentStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RunTimeExtentStyleService {
    private static final Logger logger = LoggerFactory.getLogger(RunTimeExtentStyleService.class);
    @Autowired
    private IRuntimeBigDataService runtimeBigDataService;
    @Autowired
    private IRunTimeViewController iRunTimeViewController;

    public ExtentStyle getExtentStyle(String regionKey) {
        String baseDataKey;
        ExtentStyle extentStyle = null;
        DataRegionDefine region = this.iRunTimeViewController.queryDataRegionDefine(regionKey);
        String regionSettingKey = region.getRegionSettingKey();
        RegionSettingDefine regionSetting = this.iRunTimeViewController.getRegionSetting(regionKey);
        String string = baseDataKey = regionSetting == null ? null : regionSetting.getDictionaryFillLinks();
        if (StringUtils.isEmpty((String)baseDataKey)) {
            return extentStyle;
        }
        try {
            byte[] bigData;
            BigDataDefine queryigDataDefine = this.runtimeBigDataService.getBigDataDefineFromForm(regionSettingKey, "EXTENTSTYLE");
            if (queryigDataDefine != null && (bigData = queryigDataDefine.getData()) != null) {
                extentStyle = ExtentStyle.bytesToTaskFlowsData(bigData);
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return extentStyle;
    }
}

