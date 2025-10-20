/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.transfer.engine.ResItem
 *  com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchSchemeVO
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormGroupDefine
 *  com.jiuqi.nr.param.transfer.definition.spi.IFormSchemeRelateBusiness
 */
package com.jiuqi.gcreport.bde.fetchsetting.impl.transfermodule;

import com.jiuqi.bi.transfer.engine.ResItem;
import com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchSchemeVO;
import com.jiuqi.gcreport.bde.fetchsetting.impl.service.FetchSchemeNrService;
import com.jiuqi.gcreport.bde.fetchsetting.impl.transfermodule.FetchSettingTransferModule;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormGroupDefine;
import com.jiuqi.nr.param.transfer.definition.spi.IFormSchemeRelateBusiness;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FetchSettingFormSchemeRelateBusiness
implements IFormSchemeRelateBusiness {
    private static final Logger LOGGER = LoggerFactory.getLogger(FetchSettingFormSchemeRelateBusiness.class);
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private FetchSchemeNrService fetchSchemeService;

    public String getCode() {
        return "BDE_FETCH_SETTING";
    }

    public String getTitle() {
        return "BDE\u53d6\u6570\u8bbe\u7f6e";
    }

    public List<ResItem> getRelatedBusinessForFormScheme(String formSchemeId) {
        ArrayList<ResItem> fetchSettingResItems = new ArrayList<ResItem>();
        ArrayList<String> formIds = new ArrayList<String>();
        try {
            List allFormGroupsInFormScheme = this.runTimeViewController.getAllFormGroupsInFormScheme(formSchemeId);
            for (FormGroupDefine formGroupDefine : allFormGroupsInFormScheme) {
                List formDefinesInGroup = this.runTimeViewController.getAllFormsInGroup(formGroupDefine.getKey());
                for (FormDefine formDefine : formDefinesInGroup) {
                    formIds.add(formDefine.getKey());
                }
            }
        }
        catch (Exception e) {
            LOGGER.error("\u67e5\u8be2\u62a5\u8868\u65b9\u6848\u5173\u8054\u7684\u6240\u6709\u8868\u5355\u5b9a\u4e49\u5217\u8868\u5931\u8d25\uff01", e);
            throw new RuntimeException("\u67e5\u8be2\u62a5\u8868\u65b9\u6848\u5173\u8054\u7684\u6240\u6709\u8868\u5355\u5b9a\u4e49\u5217\u8868\u5931\u8d25\uff01", e);
        }
        List<FetchSchemeVO> fetchSchemes = this.fetchSchemeService.listFetchScheme(formSchemeId);
        for (FetchSchemeVO fetchScheme : fetchSchemes) {
            String fetchSchemeId = fetchScheme.getId();
            for (String formId : formIds) {
                String guid = FetchSettingTransferModule.buildNodeId(FetchSettingTransferModule.Level.REPORT, fetchSchemeId + ":" + formId);
                ResItem resItem = new ResItem(guid, "bde-fetchsetting", "CATEGORY_BDE_FETCHSETTING");
                fetchSettingResItems.add(resItem);
            }
        }
        return fetchSettingResItems;
    }
}

