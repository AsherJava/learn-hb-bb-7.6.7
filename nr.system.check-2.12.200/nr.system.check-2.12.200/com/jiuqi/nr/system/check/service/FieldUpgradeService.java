/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.system.check.service;

import com.jiuqi.nr.system.check.model.response.fieldupgrade.EntityVO;
import com.jiuqi.nr.system.check.model.response.fieldupgrade.TaskInfoVO;
import com.jiuqi.nr.system.check.model.response.fieldupgrade.TreeNode;
import java.util.List;

public interface FieldUpgradeService {
    public List<TreeNode> treeNodeList();

    public List<TaskInfoVO> getTaskInfo(List<String> var1);

    public List<EntityVO> getSelectedEntities(String var1);
}

