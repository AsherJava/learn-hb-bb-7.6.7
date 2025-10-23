/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.core.INode
 *  com.jiuqi.nr.datascheme.api.core.ITree
 */
package com.jiuqi.nr.zbquery.extend.impl;

import com.jiuqi.nr.datascheme.api.core.INode;
import com.jiuqi.nr.datascheme.api.core.ITree;
import com.jiuqi.nr.zbquery.bean.impl.ResourceTableAdaptNode;
import com.jiuqi.nr.zbquery.bean.impl.ResourceTreeAdaptNode;
import com.jiuqi.nr.zbquery.extend.IZBQueryAdaptTreeProvider;
import com.jiuqi.nr.zbquery.model.DimensionAttributeField;
import com.jiuqi.nr.zbquery.model.FieldGroup;
import com.jiuqi.nr.zbquery.model.FieldGroupType;
import com.jiuqi.nr.zbquery.model.FormulaField;
import com.jiuqi.nr.zbquery.model.FormulaType;
import com.jiuqi.nr.zbquery.model.QueryObject;
import com.jiuqi.nr.zbquery.model.QueryObjectType;
import com.jiuqi.nr.zbquery.model.ZBQueryModel;
import com.jiuqi.nr.zbquery.rest.param.AdaptTreeParam;
import com.jiuqi.nr.zbquery.util.FullNameWrapper;
import com.jiuqi.nr.zbquery.util.QueryModelFinder;
import com.jiuqi.nr.zbquery.util.ZBQueryI18nUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Component
public class ZBQueryAdaptSelectedTreeProvider
implements IZBQueryAdaptTreeProvider {
    private static final String TYPE = "selectedtree";
    private static final String ROOT_ID = "00000000-0000-0000-0000-000000000000";
    private static final String ZB_VIRTUALGROUP_ID = "00000000-0000-0000-0000-000000000001";

    @Override
    public String getTreeType() {
        return TYPE;
    }

    @Override
    public List<ITree<ResourceTreeAdaptNode>> getRoots(AdaptTreeParam param) {
        ArrayList<ITree<ResourceTreeAdaptNode>> adaptNodes = new ArrayList<ITree<ResourceTreeAdaptNode>>();
        ITree<ResourceTreeAdaptNode> root = this.initRootNode();
        adaptNodes.add(root);
        return adaptNodes;
    }

    @Override
    public List<ITree<ResourceTreeAdaptNode>> getChildren(AdaptTreeParam param) {
        ArrayList<ITree<ResourceTreeAdaptNode>> adaptTreeNodes = new ArrayList<ITree<ResourceTreeAdaptNode>>();
        if (param.getNodeType() == NodeType.ROOT.value) {
            this.buildChildrenTreeNode(ROOT_ID, param.getZbQueryModel(), adaptTreeNodes);
        } else {
            this.buildChildrenTreeNode(param.getParentKey(), param.getZbQueryModel(), adaptTreeNodes);
        }
        return adaptTreeNodes;
    }

    @Override
    public List<ResourceTableAdaptNode> getFields(AdaptTreeParam param) {
        ArrayList<ResourceTableAdaptNode> fields = new ArrayList<ResourceTableAdaptNode>();
        if (param.getNodeType() == NodeType.ROOT.value) {
            this.buildChildrenTableNode(ROOT_ID, param.getZbQueryModel(), fields);
        } else {
            this.buildChildrenTableNode(param.getParentKey(), param.getZbQueryModel(), fields);
        }
        return fields;
    }

    private ITree<ResourceTreeAdaptNode> initRootNode() {
        ResourceTreeAdaptNode nodeData = new ResourceTreeAdaptNode();
        nodeData.setKey(ROOT_ID);
        nodeData.setTitle(ZBQueryI18nUtils.getMessage("zbquery.selectedField", new Object[0]));
        nodeData.setNodeType(NodeType.ROOT.value);
        ITree root = new ITree((INode)nodeData);
        this.setNodeIcon((ITree<ResourceTreeAdaptNode>)root);
        return root;
    }

    private ITree<ResourceTreeAdaptNode> initVirtualZBGroupNode() {
        ResourceTreeAdaptNode nodeData = new ResourceTreeAdaptNode();
        nodeData.setKey(ZB_VIRTUALGROUP_ID);
        nodeData.setTitle(ZBQueryI18nUtils.getMessage("zbquery.zb", new Object[0]));
        nodeData.setNodeType(NodeType.ZB_VIRTUALGROUP.value);
        ITree root = new ITree((INode)nodeData);
        this.setNodeIcon((ITree<ResourceTreeAdaptNode>)root);
        return root;
    }

    private void buildChildrenTreeNode(String parentKey, ZBQueryModel model, List<ITree<ResourceTreeAdaptNode>> adaptTreeNodes) {
        List<QueryObject> queryObjects = null;
        if (ROOT_ID.equals(parentKey)) {
            queryObjects = model.getQueryObjects();
            queryObjects = queryObjects.stream().filter(new VirtualGroupFilterPredicate(true, true)).collect(Collectors.toList());
        } else if (ZB_VIRTUALGROUP_ID.equals(parentKey)) {
            queryObjects = model.getQueryObjects();
            queryObjects = queryObjects.stream().filter(new VirtualGroupFilterPredicate(true, false)).collect(Collectors.toList());
        } else {
            QueryModelFinder modelFinder = new QueryModelFinder(model);
            queryObjects = ((FieldGroup)modelFinder.getQueryObject(parentKey)).getChildren();
        }
        if (!CollectionUtils.isEmpty(queryObjects)) {
            queryObjects.forEach(field -> {
                ITree<ResourceTreeAdaptNode> adaptTreeNode = this.buildAdaptTreeNode(parentKey, (QueryObject)field);
                if (adaptTreeNode != null) {
                    adaptTreeNodes.add(adaptTreeNode);
                }
            });
        }
        if (ROOT_ID.equals(parentKey)) {
            adaptTreeNodes.add(this.initVirtualZBGroupNode());
        }
    }

    private void buildChildrenTableNode(String parentKey, ZBQueryModel model, List<ResourceTableAdaptNode> fields) {
        List<QueryObject> queryObjects = null;
        if (ROOT_ID.equals(parentKey)) {
            queryObjects = model.getQueryObjects();
            queryObjects = queryObjects.stream().filter(new VirtualGroupFilterPredicate(false, true)).collect(Collectors.toList());
        } else if (ZB_VIRTUALGROUP_ID.equals(parentKey)) {
            queryObjects = model.getQueryObjects();
            queryObjects = queryObjects.stream().filter(new VirtualGroupFilterPredicate(false, false)).collect(Collectors.toList());
        } else {
            QueryModelFinder modelFinder = new QueryModelFinder(model);
            queryObjects = ((FieldGroup)modelFinder.getQueryObject(parentKey)).getChildren();
        }
        if (!CollectionUtils.isEmpty(queryObjects)) {
            queryObjects.forEach(field -> {
                ResourceTableAdaptNode adaptTableNode = this.buildAdaptTableNode((QueryObject)field);
                if (adaptTableNode != null) {
                    fields.add(adaptTableNode);
                }
            });
        }
    }

    private ITree<ResourceTreeAdaptNode> buildAdaptTreeNode(String parentKey, QueryObject queryObject) {
        ResourceTreeAdaptNode adaptNodeData = new ResourceTreeAdaptNode();
        if (QueryObjectType.GROUP.equals((Object)queryObject.getType())) {
            List<QueryObject> children;
            ITree adaptTreeNode = null;
            FieldGroup fieldGroup = (FieldGroup)queryObject;
            adaptNodeData.setKey(fieldGroup.getFullName());
            adaptNodeData.setTitle(StringUtils.hasLength(fieldGroup.getAlias()) ? fieldGroup.getAlias() : fieldGroup.getTitle());
            adaptNodeData.setParentKey(parentKey);
            adaptNodeData.setNodeType(NodeType.GROUP.value);
            adaptTreeNode = new ITree((INode)adaptNodeData);
            adaptTreeNode.setLeaf(true);
            if (FieldGroupType.ZB.equals((Object)fieldGroup.getGroupType()) && !CollectionUtils.isEmpty(children = fieldGroup.getChildren())) {
                for (QueryObject c : children) {
                    if (!QueryObjectType.GROUP.equals((Object)c.getType())) continue;
                    adaptTreeNode.setLeaf(false);
                    break;
                }
            }
            this.setNodeIcon((ITree<ResourceTreeAdaptNode>)adaptTreeNode);
            return adaptTreeNode;
        }
        return null;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private ResourceTableAdaptNode buildAdaptTableNode(QueryObject field) {
        ResourceTableAdaptNode adaptTableNode = new ResourceTableAdaptNode();
        adaptTableNode.setTitle(field.getTitle());
        adaptTableNode.setReferFieldKey("field");
        FullNameWrapper fullNameWrapper = null;
        boolean useFullNameWrapper = true;
        if (QueryObjectType.DIMENSIONATTRIBUTE.equals((Object)field.getType()) || QueryObjectType.ZB.equals((Object)field.getType())) {
            fullNameWrapper = new FullNameWrapper(field.getType(), field.getFullName());
        } else if (QueryObjectType.GROUP.equals((Object)field.getType())) {
            FieldGroup fieldGroup = (FieldGroup)field;
            if (!FieldGroupType.CHILDDIMENSION.equals((Object)fieldGroup.getGroupType())) return null;
            DimensionAttributeField originAttribute = fieldGroup.getDimAttribute();
            if (originAttribute != null) {
                fullNameWrapper = new FullNameWrapper(QueryObjectType.DIMENSIONATTRIBUTE, originAttribute.getFullName());
            } else {
                useFullNameWrapper = false;
                adaptTableNode.setCode(fieldGroup.getName());
                adaptTableNode.setTableCode(field.getParent());
            }
        } else {
            if (!QueryObjectType.FORMULA.equals((Object)field.getType())) return null;
            FormulaField formulaField = (FormulaField)field;
            if (!formulaField.getFormulaType().equals((Object)FormulaType.CUSTOM)) return null;
            useFullNameWrapper = false;
            adaptTableNode.setReferFieldKey("entity");
            adaptTableNode.setCode(formulaField.getFormula());
        }
        if (!useFullNameWrapper) return adaptTableNode;
        adaptTableNode.setCode(fullNameWrapper.getFieldName());
        adaptTableNode.setTableCode(fullNameWrapper.getTableName());
        return adaptTableNode;
    }

    private void setNodeIcon(ITree<ResourceTreeAdaptNode> node) {
        node.setIcons("#icon16_DH_A_NW_gongnengfenzushouqi");
    }

    static class VirtualGroupFilterPredicate
    implements Predicate<QueryObject> {
        private boolean tree;
        private boolean root;

        public VirtualGroupFilterPredicate(boolean tree, boolean root) {
            this.tree = tree;
            this.root = root;
        }

        @Override
        public boolean test(QueryObject queryObject) {
            boolean allow = false;
            if (this.tree) {
                if (queryObject.getType().equals((Object)QueryObjectType.GROUP)) {
                    FieldGroup group = (FieldGroup)queryObject;
                    allow = group.getGroupType().equals((Object)FieldGroupType.ZB);
                    if (this.root) {
                        return group.getGroupType().equals((Object)FieldGroupType.DIMENSION);
                    }
                }
                return allow;
            }
            if (!this.root) {
                allow = queryObject.getType().equals((Object)QueryObjectType.ZB) || queryObject.getType().equals((Object)QueryObjectType.FORMULA) && ((FormulaField)queryObject).getFormulaType().equals((Object)FormulaType.CUSTOM);
            }
            return allow;
        }
    }

    static enum NodeType {
        ROOT(0),
        GROUP(1),
        ZB_VIRTUALGROUP(2);

        int value;

        private NodeType(int value) {
            this.value = value;
        }
    }
}

