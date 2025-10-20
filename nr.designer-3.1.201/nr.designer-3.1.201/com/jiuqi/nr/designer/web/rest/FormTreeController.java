/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  com.jiuqi.nr.common.itree.INode
 *  com.jiuqi.nr.common.itree.ITree
 *  com.jiuqi.nr.definition.facade.DesignFormDefine
 *  com.jiuqi.nr.definition.facade.DesignFormGroupDefine
 *  com.jiuqi.nr.definition.facade.DesignFormSchemeDefine
 *  com.jiuqi.nr.definition.internal.controller.NRDesignTimeController
 *  com.jiuqi.nr.definition.internal.impl.DesignFormGroupLink
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.RequestMapping
 */
package com.jiuqi.nr.designer.web.rest;

import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.nr.common.itree.INode;
import com.jiuqi.nr.common.itree.ITree;
import com.jiuqi.nr.definition.facade.DesignFormDefine;
import com.jiuqi.nr.definition.facade.DesignFormGroupDefine;
import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.definition.internal.controller.NRDesignTimeController;
import com.jiuqi.nr.definition.internal.impl.DesignFormGroupLink;
import com.jiuqi.nr.designer.web.rest.vo.FormTreeNode;
import com.jiuqi.nr.designer.web.rest.vo.TaskTreeNode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@JQRestController
@RequestMapping(value={"api/v1/designer/"})
@Api(tags={"\u5efa\u6a21\u8bbe\u8ba1"})
public class FormTreeController {
    @Autowired
    private NRDesignTimeController nrDesignTimeController;

    @ApiOperation(value="\u6839\u636e\u4efb\u52a1\u67e5\u8be2\u4efb\u52a1\u4e0b\u62a5\u8868\u6811\u5f62")
    @GetMapping(value={"tree/task/form/{taskKey}"})
    public List<ITree<INode>> queryFullTaskFormTree(@PathVariable String taskKey) throws JQException {
        ArrayList<ITree<INode>> treeData = new ArrayList<ITree<INode>>();
        List formSchemes = this.nrDesignTimeController.queryFormSchemeByTask(taskKey);
        HashMap<String, Object> schemeTreeMap = new HashMap<String, Object>();
        for (DesignFormSchemeDefine item : formSchemes) {
            ITree<INode> treeNode = this.createTreeNode(item);
            treeData.add(treeNode);
            schemeTreeMap.put(item.getKey(), treeNode);
        }
        List formDefines = this.nrDesignTimeController.queryAllFormDefinesByTask(taskKey);
        HashMap<String, ITree<INode>> formTreeMap = new HashMap<String, ITree<INode>>();
        for (DesignFormDefine item : formDefines) {
            ITree<INode> treeNode = this.createTreeNode(item);
            treeNode.setLeaf(true);
            formTreeMap.put(item.getKey(), treeNode);
        }
        HashMap<String, ITree<INode>> groupTreeMap = new HashMap<String, ITree<INode>>();
        for (DesignFormSchemeDefine item : formSchemes) {
            List formGroupDefines = this.nrDesignTimeController.queryRootGroupsByFormScheme(item.getKey());
            for (DesignFormGroupDefine item1 : formGroupDefines) {
                ITree<INode> treeNode = this.createTreeNode(item1);
                ITree parentTreeNode = (ITree)schemeTreeMap.get(item1.getFormSchemeKey());
                parentTreeNode.appendChild(treeNode);
                List formGroupLinks = this.nrDesignTimeController.getFormGroupLinksByGroupId(item.getKey());
                for (DesignFormGroupLink link : formGroupLinks) {
                    treeNode.appendChild((ITree)formTreeMap.get(link.getFormKey()));
                }
                groupTreeMap.put(item1.getKey(), treeNode);
            }
        }
        return treeData;
    }

    @ApiOperation(value="\u6839\u636e\u4efb\u52a1\u67e5\u8be2\u4efb\u52a1\u4e0b\u62a5\u8868\u5206\u7ec4\u6811\u5f62")
    @GetMapping(value={"tree/task/formgroup/{taskKey}"})
    public List<ITree<INode>> queryFullTaskFormGroupTree(@PathVariable String taskKey) throws JQException {
        ArrayList<ITree<INode>> treeData = new ArrayList<ITree<INode>>();
        List formSchemes = this.nrDesignTimeController.queryFormSchemeByTask(taskKey);
        HashMap<String, ITree<INode>> schemeTreeMap = new HashMap<String, ITree<INode>>();
        for (DesignFormSchemeDefine item : formSchemes) {
            ITree<INode> treeNode = this.createTreeNode(item);
            treeData.add(treeNode);
            schemeTreeMap.put(item.getKey(), treeNode);
        }
        for (DesignFormSchemeDefine item1 : formSchemes) {
            List formGroups = this.nrDesignTimeController.queryRootGroupsByFormScheme(item1.getKey());
            HashMap<String, ITree<INode>> groupTreeMap = new HashMap<String, ITree<INode>>();
            for (DesignFormGroupDefine item : formGroups) {
                ITree<INode> treeNode = this.createTreeNode(item);
                treeNode.setLeaf(true);
                ITree parentTreeNode = (ITree)schemeTreeMap.get(item.getFormSchemeKey());
                parentTreeNode.appendChild(treeNode);
                groupTreeMap.put(item.getKey(), treeNode);
            }
        }
        return treeData;
    }

    public ITree<INode> createTreeNode(DesignFormSchemeDefine formScheme) {
        return new ITree((INode)new TaskTreeNode(formScheme));
    }

    public ITree<INode> createTreeNode(DesignFormGroupDefine formGroup) {
        return new ITree((INode)new FormTreeNode(formGroup));
    }

    public ITree<INode> createTreeNode(DesignFormDefine form) {
        return new ITree((INode)new FormInfoTreeNode(form));
    }

    public class FormInfoTreeNode
    extends FormTreeNode {
        private int formType;

        public FormInfoTreeNode(DesignFormDefine form) {
            super(form);
            this.formType = form.getFormType().getValue();
        }

        public int getFormType() {
            return this.formType;
        }

        public void setFormType(int formType) {
            this.formType = formType;
        }
    }
}

