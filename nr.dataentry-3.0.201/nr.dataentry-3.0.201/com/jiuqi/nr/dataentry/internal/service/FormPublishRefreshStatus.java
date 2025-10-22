/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.controller.ITaskOptionController
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 */
package com.jiuqi.nr.dataentry.internal.service;

import com.jiuqi.nr.dataentry.bean.DataPublishParam;
import com.jiuqi.nr.dataentry.service.IDataPublishService;
import com.jiuqi.nr.dataentry.service.IRefreshStatus;
import com.jiuqi.nr.dataentry.util.Consts;
import com.jiuqi.nr.definition.controller.ITaskOptionController;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FormPublishRefreshStatus
implements IRefreshStatus {
    @Autowired
    private IDataPublishService dataPublishService;
    @Autowired
    private ITaskOptionController iTaskOptionController;

    @Override
    public boolean getEnable(String taskKey, String formSchemeKey) {
        String dataPubObj = this.iTaskOptionController.getValue(taskKey, "DATA_PUBLISHING");
        return "1".equals(dataPubObj);
    }

    @Override
    public String getName() {
        return "formPublish";
    }

    @Override
    public Consts.RefreshStatusType getType() {
        return Consts.RefreshStatusType.UNIT;
    }

    public Object getStatus(JtableContext context) throws Exception {
        DataPublishParam dataPublishParam = new DataPublishParam();
        dataPublishParam.setContext(context);
        List<String> publishedFormKeys = this.dataPublishService.getPublishedFormKeys(dataPublishParam);
        return publishedFormKeys;
    }
}

