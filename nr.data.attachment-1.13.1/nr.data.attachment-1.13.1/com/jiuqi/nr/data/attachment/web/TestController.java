/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.data.common.param.CommonParams
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollectionBuilder
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.internal.runtime.controller.RuntimeViewController
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.nr.data.attachment.web;

import com.jiuqi.nr.data.attachment.param.ExpParams;
import com.jiuqi.nr.data.attachment.param.ImpParams;
import com.jiuqi.nr.data.attachment.service.ExpFileService;
import com.jiuqi.nr.data.attachment.service.ImpFileService;
import com.jiuqi.nr.data.common.param.CommonParams;
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
    private ExpFileService expFileService;
    @Autowired
    private ImpFileService impFileService;
    @Autowired
    private RuntimeViewController viewController;
    private static final String UUID = "2e3556ac-2bb3-477c-ac5a-ca3df02c8d89";

    @GetMapping(value={"/test/dataio/attachment"})
    public void attachment() throws Exception {
        ExpParams params = new ExpParams();
        FormSchemeDefine formScheme = this.viewController.getFormScheme(UUID);
        DimensionCollectionBuilder builder = new DimensionCollectionBuilder();
        builder.setDWValue("MD_ORG", formScheme.getDw(), new Object[]{"13000001"});
        builder.setEntityValue("DATATIME", "Y", new Object[]{"2019Y0012"});
        params.setDimensions(builder.getCollection());
        params.setFilePath("D://temp");
        ArrayList<String> formKeys = new ArrayList<String>();
        formKeys.add("df09ad55-4c45-461a-923e-871e1c6b9333");
        params.setFormKeys(formKeys);
        params.setFormSchemeKey(UUID);
        String downloadFiles = this.expFileService.downloadFiles(params);
        CommonParams commonParams = new CommonParams();
        ImpParams params1 = new ImpParams();
        params1.setFilePath(downloadFiles);
        params1.setFormSchemeKey(UUID);
        params1.setOptions(null);
        this.impFileService.uploadFileds(params1, commonParams);
    }
}

