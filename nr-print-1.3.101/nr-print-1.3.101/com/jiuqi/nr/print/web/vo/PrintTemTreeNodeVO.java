/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.common.FormType
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormGroupDefine
 *  com.jiuqi.nr.definition.facade.PrintComTemDefine
 *  com.jiuqi.nr.definition.facade.PrintTemplateDefine
 *  com.jiuqi.nr.task.api.tree.TreeData
 *  com.jiuqi.nr.task.api.tree.UITreeNode
 */
package com.jiuqi.nr.print.web.vo;

import com.jiuqi.nr.definition.common.FormType;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormGroupDefine;
import com.jiuqi.nr.definition.facade.PrintComTemDefine;
import com.jiuqi.nr.definition.facade.PrintTemplateDefine;
import com.jiuqi.nr.task.api.tree.TreeData;
import com.jiuqi.nr.task.api.tree.UITreeNode;
import java.util.Date;

public class PrintTemTreeNodeVO
implements TreeData {
    private String key;
    private String code;
    private String title;
    private String parent;
    private NodeType type;
    private String templateKey;
    private String formKey;
    private boolean customGrid;
    private boolean customGridChanged;
    private String commonCode;

    public static UITreeNode<PrintTemTreeNodeVO> getCoverNode() {
        PrintTemTreeNodeVO nodeData = new PrintTemTreeNodeVO();
        nodeData.setKey("coverTem");
        nodeData.setCode(null);
        nodeData.setTitle("\u5c01\u9762\u8bbe\u8ba1");
        nodeData.setParent(null);
        nodeData.setType(NodeType.COVER);
        nodeData.setFormKey("coverTem");
        UITreeNode node = new UITreeNode((TreeData)nodeData);
        node.setOrder("0");
        node.setIcon("icon-table");
        node.setLeaf(true);
        return node;
    }

    public static UITreeNode<PrintTemTreeNodeVO> getCommonGroupNode() {
        PrintTemTreeNodeVO nodeData = new PrintTemTreeNodeVO();
        nodeData.setKey("commonTem");
        nodeData.setCode(null);
        nodeData.setTitle("\u6bcd\u7248\u8bbe\u7f6e");
        nodeData.setParent(null);
        nodeData.setType(NodeType.GROUP);
        nodeData.setFormKey(null);
        UITreeNode node = new UITreeNode((TreeData)nodeData);
        node.setOrder("1");
        node.setIcon("icon-folder");
        node.setLeaf(false);
        return node;
    }

    public static UITreeNode<PrintTemTreeNodeVO> getCommonDefaultNode(String printSchemeKey) {
        PrintTemTreeNodeVO nodeData = new PrintTemTreeNodeVO();
        nodeData.setKey(printSchemeKey);
        nodeData.setCode("DEFAULT");
        nodeData.setTitle("\u9ed8\u8ba4\u6bcd\u7248");
        nodeData.setParent("commonTem");
        nodeData.setType(NodeType.COMMON);
        nodeData.setFormKey("commonTem");
        nodeData.setCommonCode(null);
        nodeData.setTemplateKey(printSchemeKey);
        UITreeNode node = new UITreeNode((TreeData)nodeData);
        node.setOrder("0");
        node.setIcon("icon-table");
        node.setLeaf(true);
        return node;
    }

    public static UITreeNode<PrintTemTreeNodeVO> getCommonNode(PrintComTemDefine define) {
        PrintTemTreeNodeVO nodeData = new PrintTemTreeNodeVO();
        nodeData.setKey(define.getKey());
        nodeData.setCode(define.getCode());
        nodeData.setTitle(define.getTitle());
        nodeData.setParent("commonTem");
        nodeData.setType(NodeType.COMMON);
        nodeData.setFormKey("commonTem");
        nodeData.setCommonCode(null);
        nodeData.setTemplateKey(define.getKey());
        UITreeNode node = new UITreeNode((TreeData)nodeData);
        node.setOrder(define.getOrder());
        node.setIcon("icon-table");
        node.setLeaf(true);
        return node;
    }

    public static UITreeNode<PrintTemTreeNodeVO> getTemplateGroupNode() {
        PrintTemTreeNodeVO nodeData = new PrintTemTreeNodeVO();
        nodeData.setKey("formRootNode");
        nodeData.setCode(null);
        nodeData.setTitle("\u8868\u5355\u8bbe\u7f6e");
        nodeData.setParent(null);
        nodeData.setType(NodeType.GROUP);
        nodeData.setFormKey(null);
        UITreeNode node = new UITreeNode((TreeData)nodeData);
        node.setOrder("2");
        node.setIcon("icon-folder");
        node.setLeaf(false);
        return node;
    }

    public static UITreeNode<PrintTemTreeNodeVO> getTemplateGroupNode(FormGroupDefine group) {
        PrintTemTreeNodeVO nodeData = new PrintTemTreeNodeVO();
        nodeData.setKey(group.getKey());
        nodeData.setCode(group.getCode());
        nodeData.setTitle(group.getTitle());
        nodeData.setParent("formRootNode");
        nodeData.setType(NodeType.GROUP);
        nodeData.setFormKey(null);
        UITreeNode node = new UITreeNode((TreeData)nodeData);
        node.setOrder(group.getOrder());
        node.setIcon("icon-folder");
        node.setLeaf(false);
        return node;
    }

    public static UITreeNode<PrintTemTreeNodeVO> getTemplateNode(FormGroupDefine group, FormDefine form, Date formStyleUpdateTime, PrintTemplateDefine template) {
        PrintTemTreeNodeVO nodeData = new PrintTemTreeNodeVO();
        nodeData.setKey(form.getKey());
        nodeData.setCode(form.getFormCode());
        nodeData.setTitle(form.getTitle());
        nodeData.setParent(group.getKey());
        nodeData.setType(NodeType.TEMPLATE);
        nodeData.setFormKey(form.getKey());
        if (null != template) {
            nodeData.setCommonCode(template.getComTemCode());
            nodeData.setTemplateKey(template.getKey());
            nodeData.setCustomGrid(!template.isAutoRefreshForm());
            if (!template.isAutoRefreshForm() && null != template.getFormUpdateTime() && null != formStyleUpdateTime) {
                nodeData.setCustomGridChanged(formStyleUpdateTime.after(template.getFormUpdateTime()));
            }
        } else {
            nodeData.setCommonCode("DEFAULT");
        }
        UITreeNode node = new UITreeNode((TreeData)nodeData);
        node.setOrder(form.getOrder());
        node.setIcon(PrintTemTreeNodeVO.getFormIcon(form));
        node.setLeaf(true);
        return node;
    }

    private static String getFormIcon(FormDefine form) {
        if (form.getFormType() == FormType.FORM_TYPE_FLOAT) {
            return "icon-J_GJ_A_NR_fudongbiao";
        }
        if (form.getFormType() == FormType.FORM_TYPE_SURVEY) {
            return "icon-J_GJ_A_NR_wenjuan";
        }
        if (form.getFormType() == FormType.FORM_TYPE_INSERTANALYSIS) {
            return "icon-J_GJ_A_NR_fenxibiao";
        }
        if (form.getFormType() == FormType.FORM_TYPE_ACCOUNT) {
            return "icon-J_GJ_A_NR_taizhang";
        }
        if (form.getFormType() == FormType.FORM_TYPE_NEWFMDM) {
            return "icon-J_GJ_A_NR_fengmiandaima";
        }
        return "icon-J_GJ_A_NR_gudingbiao";
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getParent() {
        return this.parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public NodeType getType() {
        return this.type;
    }

    public void setType(NodeType type) {
        this.type = type;
    }

    public String getFormKey() {
        return this.formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    public boolean isCustomGrid() {
        return this.customGrid;
    }

    public void setCustomGrid(boolean customGrid) {
        this.customGrid = customGrid;
    }

    public boolean isCustomGridChanged() {
        return this.customGridChanged;
    }

    public void setCustomGridChanged(boolean customGridChanged) {
        this.customGridChanged = customGridChanged;
    }

    public String getCommonCode() {
        return this.commonCode;
    }

    public void setCommonCode(String commonCode) {
        this.commonCode = commonCode;
    }

    public String getTemplateKey() {
        return this.templateKey;
    }

    public void setTemplateKey(String templateKey) {
        this.templateKey = templateKey;
    }

    public static enum NodeType {
        GROUP,
        COVER,
        COMMON,
        TEMPLATE;

    }
}

