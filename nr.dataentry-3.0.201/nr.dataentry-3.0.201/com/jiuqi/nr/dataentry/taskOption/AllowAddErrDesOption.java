/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.AuditType
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.facade.TaskFlowsDefine
 *  com.jiuqi.nr.definition.internal.service.AuditTypeDefineService
 *  com.jiuqi.nr.definition.option.core.OptionEditMode
 *  com.jiuqi.nr.definition.option.core.OptionItem
 *  com.jiuqi.nr.definition.option.spi.TaskOptionDefine
 */
package com.jiuqi.nr.dataentry.taskOption;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.dataentry.taskOption.ErrorDesGroup;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.AuditType;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.facade.TaskFlowsDefine;
import com.jiuqi.nr.definition.internal.service.AuditTypeDefineService;
import com.jiuqi.nr.definition.option.core.OptionEditMode;
import com.jiuqi.nr.definition.option.core.OptionItem;
import com.jiuqi.nr.definition.option.spi.TaskOptionDefine;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AllowAddErrDesOption
implements TaskOptionDefine {
    public static final String ALLOW_ADD_ERROR_DES = "ALLOW_ADD_ERROR_DES";
    public static final String ALLOW_ADD_ERROR_DES_TITLE = "\u5141\u8bb8\u6dfb\u52a0\u51fa\u9519\u8bf4\u660e\u7684\u5ba1\u6838\u7c7b\u578b";
    @Autowired
    private AuditTypeDefineService auditTypeDefineService;
    @Autowired
    private IRunTimeViewController runtimeController;

    public String getKey() {
        return ALLOW_ADD_ERROR_DES;
    }

    public String getTitle() {
        return "";
    }

    public String getDefaultValue() {
        return null;
    }

    public String getDefaultValue(String taskKey) {
        TaskDefine taskDefine = this.runtimeController.queryTaskDefine(taskKey);
        if (taskDefine == null) {
            return null;
        }
        TaskFlowsDefine taskFlowsDefine = taskDefine.getFlowsSetting();
        ArrayList<String> types = new ArrayList<String>();
        for (String key : taskFlowsDefine.getErroStatus().split(";")) {
            if (!StringUtils.isNotEmpty((String)key)) continue;
            types.add(key);
        }
        for (String key : taskFlowsDefine.getPromptStatus().split(";")) {
            if (!StringUtils.isNotEmpty((String)key)) continue;
            types.add(key);
        }
        try {
            return new ObjectMapper().writeValueAsString((Object)types.toArray());
        }
        catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public OptionEditMode getOptionEditMode() {
        return OptionEditMode.OPTION_CHECK_BOX;
    }

    public List<OptionItem> getOptionItems() {
        List auditTypes = new ArrayList();
        try {
            auditTypes = this.auditTypeDefineService.queryAllAuditType();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        ArrayList<OptionItem> list = new ArrayList<OptionItem>(auditTypes.size());
        for (final AuditType auditType : auditTypes) {
            list.add(new OptionItem(){

                public String getTitle() {
                    return auditType.getTitle();
                }

                public String getValue() {
                    return auditType.getCode().toString();
                }
            });
        }
        return list;
    }

    public String getRegex() {
        return null;
    }

    public double getOrder() {
        return 50.0;
    }

    public String getPageTitle() {
        return new ErrorDesGroup().getPageTitle();
    }

    public String getGroupTitle() {
        return new ErrorDesGroup().getTitle();
    }
}

