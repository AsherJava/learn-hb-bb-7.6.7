/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.data.access.param.AccessCode
 *  com.jiuqi.nr.data.access.param.AccessItem
 *  com.jiuqi.nr.data.access.param.IAccessMessage
 *  com.jiuqi.nr.data.access.service.IDataExtendAccessItemService
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.definition.controller.ITaskOptionController
 *  org.json.JSONObject
 */
package com.jiuqi.nr.dataentry.readwrite.impl.access;

import com.jiuqi.nr.data.access.param.AccessCode;
import com.jiuqi.nr.data.access.param.AccessItem;
import com.jiuqi.nr.data.access.param.IAccessMessage;
import com.jiuqi.nr.data.access.service.IDataExtendAccessItemService;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.definition.controller.ITaskOptionController;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class DataVersionAccessServiceImpl
implements IDataExtendAccessItemService {
    @Autowired
    private ITaskOptionController taskOptionController;
    public static final String DATAVER_READ_MSG = "\u5feb\u7167\u6570\u636e\uff0c\u65e0\u6cd5\u4fee\u6539";
    private Function<String, String> noAccessReason = code -> Optional.ofNullable(code).filter(e -> e.equals("1")).orElse(DATAVER_READ_MSG);

    public int getOrder() {
        return 9;
    }

    public AccessCode visible(AccessItem param, String formSchemeKey, DimensionCombination masterKey, String formKey) {
        return new AccessCode(this.name());
    }

    public AccessCode readable(AccessItem param, String formSchemeKey, DimensionCombination masterKey, String formKey) {
        return new AccessCode(this.name());
    }

    public AccessCode writeable(AccessItem param, String formSchemeKey, DimensionCombination masterKey, String formKey) {
        boolean isVersionData;
        JSONObject versionData;
        Assert.notNull((Object)param, "param is must not be null!");
        JSONObject json = new JSONObject((Map)((HashMap)param.getParams()));
        if (json.has("versionData") && (versionData = json.getJSONObject("versionData")).has("isVersionData") && versionData.has("isSysVersion") && (isVersionData = versionData.getBoolean("isVersionData"))) {
            return new AccessCode(this.name(), "2");
        }
        return new AccessCode(this.name());
    }

    public String name() {
        return "dataVersion";
    }

    public boolean isEnable(String taskKey, String formSchemeKey) {
        String dataVersionObj = this.taskOptionController.getValue(taskKey, "DATA_VERSION");
        return "1".equals(dataVersionObj);
    }

    public IAccessMessage getAccessMessage() {
        return code -> this.noAccessReason.apply(code);
    }
}

