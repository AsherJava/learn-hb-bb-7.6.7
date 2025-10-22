/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.auth.DefinitionAuthorityProvider
 *  com.jiuqi.nr.definition.common.FormType
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 */
package com.jiuqi.nr.dataSnapshot.service.impl;

import com.jiuqi.nr.dataSnapshot.param.FormInfo;
import com.jiuqi.nr.dataSnapshot.param.FormSchemeInfo;
import com.jiuqi.nr.dataSnapshot.param.RegionInfo;
import com.jiuqi.nr.dataSnapshot.service.IDataSetService;
import com.jiuqi.nr.definition.auth.DefinitionAuthorityProvider;
import com.jiuqi.nr.definition.common.FormType;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IDataSetServiceImpl
implements IDataSetService {
    private static final Logger logger = LoggerFactory.getLogger(IDataSetServiceImpl.class);
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private DefinitionAuthorityProvider definitionAuthorityProvider;

    @Override
    public List<FormSchemeInfo> getformSchemes(String taskKey) {
        ArrayList<FormSchemeInfo> formSchemeInfos = new ArrayList<FormSchemeInfo>();
        try {
            List formSchemeDefines = this.runTimeViewController.queryFormSchemeByTask(taskKey);
            for (FormSchemeDefine formSchemeDefine : formSchemeDefines) {
                FormSchemeInfo formSchemeInfo = new FormSchemeInfo();
                formSchemeInfo.setFormSchemeKey(formSchemeDefine.getKey());
                formSchemeInfo.setTitle(formSchemeDefine.getTitle());
                formSchemeInfos.add(formSchemeInfo);
            }
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        return formSchemeInfos;
    }

    @Override
    public List<FormInfo> getFormInfo(String formSchemeKey) {
        ArrayList<FormInfo> formInfos = new ArrayList<FormInfo>();
        List formDefines = this.runTimeViewController.queryAllFormDefinesByFormScheme(formSchemeKey);
        if (null != formDefines) {
            for (FormDefine formDefine : formDefines) {
                boolean canRead;
                FormType formType = formDefine.getFormType();
                if (formType.equals((Object)FormType.FORM_TYPE_INTERMEDIATE) || formType.equals((Object)FormType.FORM_TYPE_FMDM) || formType.equals((Object)FormType.FORM_TYPE_ANALYSISREPORT) || formType.equals((Object)FormType.FORM_TYPE_NEWFMDM) || formType.equals((Object)FormType.FORM_TYPE_INSERTANALYSIS) || formType.equals((Object)FormType.FORM_TYPE_ACCOUNT) || !(canRead = this.definitionAuthorityProvider.canReadForm(formDefine.getKey()))) continue;
                FormInfo formInfo = new FormInfo();
                formInfo.setFormKey(formDefine.getKey());
                formInfo.setTitle(formDefine.getTitle());
                formInfos.add(formInfo);
            }
        }
        return formInfos;
    }

    @Override
    public List<RegionInfo> getRegionInfo(String formKey) {
        ArrayList<RegionInfo> regionInfos = new ArrayList<RegionInfo>();
        List regionDefines = this.runTimeViewController.getAllRegionsInForm(formKey);
        if (null != regionDefines) {
            for (DataRegionDefine regionDefine : regionDefines) {
                RegionInfo regionInfo = new RegionInfo();
                regionInfo.setRegionKey(regionDefine.getKey());
                regionInfo.setTitle(regionDefine.getTitle());
                regionInfos.add(regionInfo);
            }
        }
        return regionInfos;
    }
}

