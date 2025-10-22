/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.annotation.JsonDeserialize
 *  com.fasterxml.jackson.databind.annotation.JsonSerialize
 *  com.jiuqi.np.dataengine.exception.DataTypeException
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.nr.common.itree.INode
 *  com.jiuqi.nr.datascheme.api.core.INode
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 */
package com.jiuqi.nr.query.querymodal;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.jiuqi.np.dataengine.exception.DataTypeException;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nr.common.itree.INode;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.query.deserializer.QueryTreeNodeDeserializer;
import com.jiuqi.nr.query.querymodal.AuthUtil;
import com.jiuqi.nr.query.querymodal.QueryModalDefine;
import com.jiuqi.nr.query.querymodal.QueryModalGroup;
import com.jiuqi.nr.query.querymodal.QueryModelType;
import com.jiuqi.nr.query.serializer.QueryTreeNodeSerializer;
import java.util.Date;

@JsonSerialize(using=QueryTreeNodeSerializer.class)
@JsonDeserialize(using=QueryTreeNodeDeserializer.class)
public class QueryModalTreeNode
implements INode,
com.jiuqi.nr.datascheme.api.core.INode {
    public static final String QUERYMODALTREE_NODE_DATA_ID = "key";
    public static final String QUERYMODALTREE_NODE_DATA_CODE = "code";
    public static final String QUERYMODALTREE_NODE_DATA_TITLE = "title";
    public static final String QUERYMODALTREE_NODE_DATA_PARENTID = "parentid";
    public static final String QUERYMODALTREE_NODE_DATA_UPDATETIME = "updatetime";
    public static final String QUERYMODALTREE_NODE_DATA_ISGROUP = "isgroup";
    public static final String QUERYMODALTREE_NODE_DATA_TASKID = "taskid";
    public static final String QUERYMODALTREE_NODE_DATA_VIEWID = "viewid";
    public static final String QUERYMODALTREE_NODE_DATA_TYPE = "type";
    public static final String QUERYMODALTREE_NODE_MODEL_TYPE = "modeltype";
    public static final String QUERYMODALTREE_NODE_TREE_TYPE = "treetype";
    public static final String QUERYMODALTREE_NODE_ICONS = "icons";
    public static final String QUERYMODALTREE_NODE_AUTH = "auth";
    public static final String QUERYMODALTREE_NODE_MODELCATEGORY = "modelCategory";
    private String id;
    private String code;
    private String title;
    private Long updatetime;
    private String taskid;
    private String viewid;
    private Integer nodetype;
    private QueryModelType modelType;
    private String treeType;
    private String auth;
    private String modelCategory;
    private Boolean isgroup;
    private String parentId;

    public String getAuth() {
        return this.auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    public String getModelCategory() {
        return this.modelCategory;
    }

    public void setModelCategory(String modelCategory) {
        this.modelCategory = modelCategory;
    }

    public Integer getNodeType() {
        return this.nodetype;
    }

    public void setNodeType(Integer nodetype) {
        this.nodetype = nodetype;
    }

    public String getViewid() {
        return this.viewid;
    }

    public void setViewid(String viewid) {
        this.viewid = viewid;
    }

    public String getTaskid() {
        return this.taskid;
    }

    public void setTaskid(String taskid) {
        this.taskid = taskid;
    }

    public Long getUpdatetime() {
        return this.updatetime;
    }

    public void setUpdatetime(Long updatetime) {
        this.updatetime = updatetime;
    }

    public Boolean isIsgroup() {
        return this.isgroup;
    }

    public void setIsgroup(Boolean isgroup) {
        this.isgroup = isgroup;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
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

    public QueryModelType getModelType() {
        return this.modelType;
    }

    public void setModelType(QueryModelType type) {
        this.modelType = type;
    }

    public String getParentId() {
        return this.parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getTreeType() {
        return this.treeType;
    }

    public void setTreeType(String treeType) {
        this.treeType = treeType;
    }

    public static QueryModalTreeNode buildTreeNodeData(QueryModalGroup group) throws RuntimeException, DataTypeException {
        QueryModalTreeNode node = new QueryModalTreeNode();
        node.setCode(group.getGroupId().toString());
        node.setId(group.getGroupId());
        node.setIsgroup(true);
        node.setParentId(group.getParentGroupId());
        node.setTitle(group.getGroupName());
        node.setUpdatetime(group.getUpdateTime().getTime());
        node.setModelType(group.getModelType());
        if (group.getModelType() == QueryModelType.OWNER || group.getModelType() == QueryModelType.DASHOWNER || group.getModelType() == QueryModelType.SIMPLEOWER || group.getModelType() == QueryModelType.CUSTOMINPUT) {
            boolean canEdit = AuthUtil.instance.authority.canWriteModalGroup(group.getGroupId(), group.getModelType());
            String auth = canEdit ? QueryModalTreeNode.judgeWriteModelType(group.getModelType()) : QueryModalTreeNode.judgeReadModelType(group.getModelType());
            node.setAuth(auth);
        }
        return node;
    }

    public static QueryModalTreeNode buildTreeNodeData(QueryModalDefine define) throws DataTypeException {
        QueryModalTreeNode node = new QueryModalTreeNode();
        node.setCode(define.getId().toString());
        node.setId(define.getId());
        node.setIsgroup(false);
        node.setParentId(define.getGroupId());
        node.setTitle(define.getTitle());
        node.setUpdatetime(define.getUpdateTime() == null ? new Date().getTime() : define.getUpdateTime().getTime());
        node.setModelType(define.getModelType());
        if (define.getModelType() == QueryModelType.OWNER || define.getModelType() == QueryModelType.DASHOWNER || define.getModelType() == QueryModelType.SIMPLEOWER || define.getModelType() == QueryModelType.CUSTOMINPUT) {
            boolean canEdit = AuthUtil.instance.authority.canWriteModal(define.getId(), define.getModelType());
            String auth = canEdit ? QueryModalTreeNode.judgeWriteModelType(define.getModelType()) : QueryModalTreeNode.judgeReadModelType(define.getModelType());
            node.setAuth(auth);
        }
        node.setModelCategory(define.getModelCategory().toString());
        return node;
    }

    public static String judgeWriteModelType(QueryModelType type) {
        String auth = "query_model_resource_write";
        switch (type) {
            case OWNER: {
                auth = "query_model_resource_write";
                break;
            }
            case DASHOWNER: {
                auth = "dashboard_model_resource_write";
                break;
            }
            case SIMPLEOWER: {
                auth = "simple_model_resource_write";
                break;
            }
            case CUSTOMINPUT: {
                auth = "custom_model_resource_write";
                break;
            }
        }
        return auth;
    }

    public static String judgeReadModelType(QueryModelType type) {
        String auth = "query_model_resource_read";
        switch (type) {
            case OWNER: {
                auth = "query_model_resource_read";
                break;
            }
            case DASHOWNER: {
                auth = "dashboard_model_resource_read";
                break;
            }
            case SIMPLEOWER: {
                auth = "simple_model_resource_read";
                break;
            }
            case CUSTOMINPUT: {
                auth = "custom_model_resource_read";
                break;
            }
        }
        return auth;
    }

    public static QueryModalTreeNode buildTreeNodeData(FormDefine define) throws DataTypeException {
        QueryModalTreeNode node = new QueryModalTreeNode();
        node.setCode(define.getFormCode().toString());
        node.setId(define.getKey());
        node.setIsgroup(true);
        node.setTitle(define.getTitle());
        node.setUpdatetime(define.getUpdateTime() == null ? new Date().getTime() : define.getUpdateTime().getTime());
        return node;
    }

    public static QueryModalTreeNode buildTreeNodeData(DataRegionDefine define) throws DataTypeException {
        QueryModalTreeNode node = new QueryModalTreeNode();
        node.setId(define.getKey());
        node.setIsgroup(false);
        node.setTitle(define.getTitle());
        node.setUpdatetime(define.getUpdateTime() == null ? new Date().getTime() : define.getUpdateTime().getTime());
        return node;
    }

    public static QueryModalTreeNode buildTreeNodeData(FieldDefine define) throws DataTypeException {
        QueryModalTreeNode node = new QueryModalTreeNode();
        node.setCode(define.getCode().toString());
        node.setId(define.getKey());
        node.setIsgroup(false);
        node.setTitle(define.getTitle());
        node.setUpdatetime(new Date().getTime());
        return node;
    }

    public static QueryModalTreeNode buildTreeNodeData(TaskDefine define) throws DataTypeException {
        QueryModalTreeNode node = new QueryModalTreeNode();
        node.setCode(define.getTaskCode());
        node.setId(define.getKey());
        node.setIsgroup(false);
        node.setTitle(define.getTitle());
        node.setUpdatetime(define.getUpdateTime() == null ? new Date().getTime() : define.getUpdateTime().getTime());
        return node;
    }

    public static QueryModalTreeNode buildTreeNodeData(FormSchemeDefine define) {
        QueryModalTreeNode node = new QueryModalTreeNode();
        node.setId(define.getKey());
        node.setIsgroup(false);
        node.setTitle(define.getTitle());
        node.setUpdatetime(define.getUpdateTime() == null ? new Date().getTime() : define.getUpdateTime().getTime());
        return node;
    }

    public static QueryModalTreeNode buildTreeNodeData(String key, String title, Date updateTime, int type) {
        QueryModalTreeNode node = new QueryModalTreeNode();
        if (key != null) {
            node.setId(key);
        }
        node.setIsgroup(false);
        node.setTitle(title);
        node.setUpdatetime(updateTime == null ? new Date().getTime() : updateTime.getTime());
        node.setNodeType(type);
        return node;
    }

    public String getKey() {
        return this.id.toString();
    }
}

