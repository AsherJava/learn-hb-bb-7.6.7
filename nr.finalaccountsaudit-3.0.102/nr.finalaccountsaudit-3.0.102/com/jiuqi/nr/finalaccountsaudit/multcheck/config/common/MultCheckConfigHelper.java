/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  com.jiuqi.nr.definitionext.taskExtConfig.controller.IConfigModel
 *  com.jiuqi.nr.definitionext.taskExtConfig.controller.IConfigModelController
 */
package com.jiuqi.nr.finalaccountsaudit.multcheck.config.common;

import com.google.gson.Gson;
import com.jiuqi.nr.definitionext.taskExtConfig.controller.IConfigModel;
import com.jiuqi.nr.definitionext.taskExtConfig.controller.IConfigModelController;
import com.jiuqi.nr.finalaccountsaudit.multcheck.config.common.MultCheckConfigBean;
import com.jiuqi.nr.finalaccountsaudit.multcheck.service.rest.MultCheckConfigService;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MultCheckConfigHelper
implements IConfigModelController {
    private static final Logger logger = LoggerFactory.getLogger(MultCheckConfigHelper.class);
    public static final String MENU_TYPE = "taskextension-multcheck";
    @Autowired
    MultCheckConfigService multCheckServiceAPI;

    public boolean handled(String type) {
        return Objects.equals(type, MENU_TYPE);
    }

    public IConfigModel getModel(String taskKey, String formSchemeKey, String data) {
        MultCheckConfigBean multCheckConfigBean = new MultCheckConfigBean();
        multCheckConfigBean = this.multCheckConifgDataProduce(taskKey, formSchemeKey, data);
        return multCheckConfigBean;
    }

    public String initData(String taskkey, String formSchemeKey) {
        String result = "";
        Gson gson = new Gson();
        MultCheckConfigBean multCheckConfigBean = new MultCheckConfigBean();
        result = gson.toJson((Object)multCheckConfigBean);
        return result;
    }

    public void updateOperation(String taskKey, String schemaKey, Object extInfoModel) {
        MultCheckConfigBean multCheckConfigBean = (MultCheckConfigBean)extInfoModel;
    }

    public MultCheckConfigBean multCheckConifgDataProduce(String taskKey, String formSchemeKey, String data) {
        MultCheckConfigBean multCheckConfigBean = new MultCheckConfigBean();
        Gson gson = new Gson();
        multCheckConfigBean = (MultCheckConfigBean)gson.fromJson(data, MultCheckConfigBean.class);
        return multCheckConfigBean;
    }
}

