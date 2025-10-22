/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.data.common.CommonMessage
 *  com.jiuqi.nr.data.common.Message
 *  com.jiuqi.nr.data.common.param.CommonParams
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollectionBuilder
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.internal.runtime.controller.RuntimeViewController
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.nr.data.text.web;

import com.jiuqi.nr.data.common.CommonMessage;
import com.jiuqi.nr.data.common.Message;
import com.jiuqi.nr.data.common.param.CommonParams;
import com.jiuqi.nr.data.text.param.TextParams;
import com.jiuqi.nr.data.text.service.ExpTextService;
import com.jiuqi.nr.data.text.service.ImpTextService;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollectionBuilder;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.internal.runtime.controller.RuntimeViewController;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @Autowired
    private ExpTextService expTextService;
    @Autowired
    private ImpTextService impTextService;
    @Autowired
    private RuntimeViewController viewController;

    @GetMapping(value={"/test/dataio/text"})
    public void attachment() throws Exception {
        TextParams params = new TextParams();
        FormSchemeDefine formScheme = this.viewController.getFormScheme("2e3556ac-2bb3-477c-ac5a-ca3df02c8d89");
        DimensionCollectionBuilder builder = new DimensionCollectionBuilder();
        builder.setDWValue("MD_ORG", formScheme.getDw(), new Object[]{"13000001"});
        builder.setEntityValue("DATATIME", "Y", new Object[]{"2019Y0012"});
        params.setDimensionSet(builder.getCollection());
        params.setFilePath("D://temp");
        ArrayList<String> formKeys = new ArrayList<String>();
        formKeys.add("df09ad55-4c45-461a-923e-871e1c6b9333");
        params.setFormKeys(formKeys);
        params.setFormSchemeKey("2e3556ac-2bb3-477c-ac5a-ca3df02c8d89");
        String downloadFiles = this.expTextService.downloadTextData(params);
        params.setFilePath(downloadFiles);
        CommonParams commonParams = new CommonParams();
        Message<CommonMessage> uploadTextData = this.impTextService.uploadTextData(params, commonParams);
    }
}

