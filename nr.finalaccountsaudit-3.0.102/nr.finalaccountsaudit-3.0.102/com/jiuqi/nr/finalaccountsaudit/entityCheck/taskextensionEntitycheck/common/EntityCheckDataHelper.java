/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.google.gson.Gson
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.nr.definition.internal.controller.NRDesignTimeController
 *  com.jiuqi.nr.definitionext.taskExtConfig.controller.IConfigModel
 *  com.jiuqi.nr.definitionext.taskExtConfig.controller.IConfigModelController
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  com.jiuqi.util.StringUtils
 */
package com.jiuqi.nr.finalaccountsaudit.entityCheck.taskextensionEntitycheck.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.nr.definition.internal.controller.NRDesignTimeController;
import com.jiuqi.nr.definitionext.taskExtConfig.controller.IConfigModel;
import com.jiuqi.nr.definitionext.taskExtConfig.controller.IConfigModelController;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.finalaccountsaudit.entityCheck.taskextensionEntitycheck.common.EntityCheckConfigData;
import com.jiuqi.nvwa.definition.service.DataModelService;
import com.jiuqi.util.StringUtils;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EntityCheckDataHelper
implements IConfigModelController {
    private static final Logger logger = LoggerFactory.getLogger(EntityCheckDataHelper.class);
    public static final String MENU_TYPE = "taskextension-entitycheck";
    @Autowired
    EntityCheckConfigData entityCheckConfigData;
    @Autowired
    NRDesignTimeController nrDesignTimeController;
    @Autowired
    IEntityMetaService entityMetaService;
    @Autowired
    IDataDefinitionRuntimeController runtimeController;
    @Autowired
    DataModelService dataModelService;

    public boolean handled(String type) {
        return Objects.equals(type, MENU_TYPE);
    }

    public String initData(String taskKey, String formSchemeKey) {
        StringBuffer data = new StringBuffer();
        try {
            data.append("{\"entityCheckEnable\":false,\"configInfos\":[]");
            data.append("}");
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        return data.toString();
    }

    public void updateOperation(String taskKey, String schemaKey, Object extInfoModel) {
    }

    public IConfigModel getModel(String taskKey, String formSchemeKey, String data) {
        try {
            EntityCheckConfigData EntityConifgData = this.entityCheckConfigDataProduce(taskKey, formSchemeKey, data);
            return EntityConifgData;
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    public EntityCheckConfigData entityCheckConfigDataProduce(String taskKey, String formSchemeKey, String data) throws JsonProcessingException {
        this.entityCheckConfigData = new EntityCheckConfigData();
        if (StringUtils.isNotEmpty((String)data)) {
            Gson gson = new Gson();
            this.entityCheckConfigData = (EntityCheckConfigData)gson.fromJson(data, EntityCheckConfigData.class);
        }
        return this.entityCheckConfigData;
    }
}

