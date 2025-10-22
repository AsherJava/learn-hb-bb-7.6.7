/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.unit.treestore.fmdmdisplay.bean.EntityFiledInfo
 *  com.jiuqi.nr.unit.treestore.fmdmdisplay.bean.FMDMCaptionFieldSettingInfo
 *  com.jiuqi.nr.unit.treestore.fmdmdisplay.bean.FMDMCaptionFieldsSaveInfo
 *  com.jiuqi.nr.unit.treestore.fmdmdisplay.bean.FMDMCaptionFieldsUpdateInfo
 */
package com.jiuqi.nr.unit.treeimpl.web.service;

import com.jiuqi.nr.unit.treestore.fmdmdisplay.bean.EntityFiledInfo;
import com.jiuqi.nr.unit.treestore.fmdmdisplay.bean.FMDMCaptionFieldSettingInfo;
import com.jiuqi.nr.unit.treestore.fmdmdisplay.bean.FMDMCaptionFieldsSaveInfo;
import com.jiuqi.nr.unit.treestore.fmdmdisplay.bean.FMDMCaptionFieldsUpdateInfo;
import java.util.List;

public interface ICaptionFieldsSettingService {
    public FMDMCaptionFieldSettingInfo inquireCaptionFields(String var1);

    public int saveCaptionFieldsScheme(FMDMCaptionFieldsSaveInfo var1);

    public int saveCaptionFieldsScheme(FMDMCaptionFieldsSaveInfo var1, String var2);

    public int updateCaptionFieldsScheme(FMDMCaptionFieldsUpdateInfo var1);

    public List<EntityFiledInfo> querySortFields(String var1);
}

