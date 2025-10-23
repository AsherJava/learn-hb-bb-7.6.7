/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.nr.common.itree.INode
 *  com.jiuqi.nr.common.itree.ITree
 *  com.jiuqi.nr.definition.facade.DesignTaskGroupDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 */
package com.jiuqi.nr.mapping2.util;

import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.nr.common.itree.INode;
import com.jiuqi.nr.common.itree.ITree;
import com.jiuqi.nr.definition.facade.DesignTaskGroupDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.mapping2.web.vo.SelectOptionVO;
import com.jiuqi.nr.mapping2.web.vo.TaskTreeNode;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.util.CollectionUtils;

public class MappingSchemeUtil {
    public static ITree<TaskTreeNode> convertTaskTreeNode(TaskDefine task, List<FormSchemeDefine> formSchemes, String taskKey, List<SelectOptionVO> orgLinks) {
        TaskTreeNode r = new TaskTreeNode();
        r.setKey(task.getKey());
        r.setCode(task.getTaskCode());
        r.setTitle(task.getTaskCode() + " | " + task.getTitle());
        r.setType("TASK");
        IEntityMetaService entityMetaService = (IEntityMetaService)SpringBeanUtils.getBean(IEntityMetaService.class);
        r.setOrgName(entityMetaService.queryEntity(task.getDw()).getCode());
        if (!CollectionUtils.isEmpty(formSchemes)) {
            r.setFormSchemes(formSchemes.stream().map(MappingSchemeUtil::convertFormScheme).collect(Collectors.toList()));
        }
        if (!CollectionUtils.isEmpty(orgLinks)) {
            r.setOrgLinks(orgLinks);
        }
        ITree node = new ITree((INode)r);
        node.setIcons(new String[]{"#icon-16_SHU_A_NR_shujufangan"});
        node.setLeaf(true);
        node.setSelected(taskKey.equals(task.getKey()));
        return node;
    }

    public static SelectOptionVO convertFormScheme(FormSchemeDefine formSchemeDefine) {
        SelectOptionVO fvo = new SelectOptionVO();
        fvo.setLabel(formSchemeDefine.getTitle());
        fvo.setValue(formSchemeDefine.getKey());
        return fvo;
    }

    public static ITree<TaskTreeNode> convertGroupTreeNode(DesignTaskGroupDefine group) {
        TaskTreeNode r = new TaskTreeNode();
        r.setKey(group.getKey());
        r.setCode(group.getCode());
        r.setTitle(group.getTitle());
        r.setType("GROUP");
        ITree node = new ITree((INode)r);
        node.setIcons(new String[]{"#icon-16_SHU_A_NR_fenzu"});
        node.setLeaf(true);
        return node;
    }
}

