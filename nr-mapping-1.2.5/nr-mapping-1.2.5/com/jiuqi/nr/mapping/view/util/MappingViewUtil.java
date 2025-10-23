/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.facade.DesignTaskGroupDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.controller.RunTimeAuthViewController
 *  com.jiuqi.nvwa.resourceview.query.NodeType
 *  com.jiuqi.nvwa.resourceview.query.ResourceData
 *  com.jiuqi.nvwa.resourceview.query.ResourceGroup
 */
package com.jiuqi.nr.mapping.view.util;

import com.jiuqi.nr.definition.facade.DesignTaskGroupDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.controller.RunTimeAuthViewController;
import com.jiuqi.nr.mapping.bean.MappingScheme;
import com.jiuqi.nvwa.resourceview.query.NodeType;
import com.jiuqi.nvwa.resourceview.query.ResourceData;
import com.jiuqi.nvwa.resourceview.query.ResourceGroup;
import java.text.SimpleDateFormat;
import java.util.HashMap;

public class MappingViewUtil {
    public static ResourceGroup convertDesignTaskGroup(DesignTaskGroupDefine group) {
        ResourceGroup rg = new ResourceGroup();
        rg.setId("G@" + group.getKey());
        rg.setName(group.getCode());
        rg.setTitle(group.getTitle());
        rg.setGroup(group.getParentKey());
        rg.setIcon("#icon-16_SHU_A_NR_fenzu");
        return rg;
    }

    public static ResourceGroup convertTaskDefine(TaskDefine task, String group) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ResourceGroup rg = new ResourceGroup();
        rg.setId("T@" + task.getKey());
        rg.setName(task.getTaskCode());
        rg.setTitle(task.getTitle());
        rg.setGroup(group);
        rg.setIcon("#icon-16_SHU_A_NR_shujufangan");
        rg.setModifyTime(dateFormat.format(task.getUpdateTime()));
        return rg;
    }

    public static ResourceGroup convertFormScheme(FormSchemeDefine formScheme) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ResourceGroup rg = new ResourceGroup();
        rg.setId("F@" + formScheme.getKey());
        rg.setName(formScheme.getFormSchemeCode());
        rg.setTitle(formScheme.getTitle());
        rg.setGroup(formScheme.getTaskKey());
        rg.setIcon("#icon-16_SHU_A_NR_fengmiandaimabiao");
        rg.setModifyTime(dateFormat.format(formScheme.getUpdateTime()));
        return rg;
    }

    public static ResourceData convertMappingScheme(MappingScheme scheme, RunTimeAuthViewController runTime) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ResourceData rd = new ResourceData();
        rd.setId(scheme.getKey());
        rd.setName(scheme.getCode());
        rd.setTitle(scheme.getTitle());
        rd.setGroup(scheme.getFormScheme());
        rd.setModifyTime(dateFormat.format(scheme.getUpdateTime()));
        rd.setResourceType("com.jiuqi.nr.mappingScheme");
        rd.setResourceTypeTitle("\u6620\u5c04\u65b9\u6848");
        rd.setType(NodeType.NODE_DATA);
        HashMap<String, String> custom = new HashMap<String, String>();
        try {
            TaskDefine task = runTime.queryTaskDefine(scheme.getTask());
            FormSchemeDefine formScheme = runTime.getFormScheme(scheme.getFormScheme());
            custom.put("task", String.format("%s\u3010%s\u3011", task.getTitle(), formScheme.getTitle()));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        rd.setCustomValues(custom);
        return rd;
    }
}

