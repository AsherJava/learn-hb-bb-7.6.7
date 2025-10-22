/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.query.service.impl;

import com.jiuqi.nr.query.auth.QueryModelAuthorityProvider;
import com.jiuqi.nr.query.dao.IQueryModalDefineDao;
import com.jiuqi.nr.query.dao.IQueryModalGroupDao;
import com.jiuqi.nr.query.querymodal.QueryModalDefine;
import com.jiuqi.nr.query.querymodal.QueryModalGroup;
import com.jiuqi.nr.query.querymodal.QueryModelType;
import com.jiuqi.nr.query.service.IQueryAuthority;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QueryAuthorityImpl
implements IQueryAuthority {
    private static final Logger log = LoggerFactory.getLogger(QueryAuthorityImpl.class);
    @Autowired
    private QueryModelAuthorityProvider authority;
    @Autowired
    private IQueryModalDefineDao modelDao;
    @Autowired
    private IQueryModalGroupDao groupDao;

    @Override
    public List<QueryModalDefine> getModalsByCondition(String modalCondition, String[] values, QueryModelType type, String authType) {
        try {
            List<QueryModalDefine> modals = this.modelDao.getModalsByCondition(modalCondition, null);
            List<QueryModalDefine> queryModalDefines = this.modelCheckAuthority(modals, type, authType);
            return queryModalDefines;
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    @Override
    public List<QueryModalGroup> getGroupsByCondition(String groupCondition, String[] values, QueryModelType type, String authType) {
        List<QueryModalGroup> groups = this.groupDao.getGroupsByCondition(groupCondition, null);
        List<QueryModalGroup> queryModalGroups = this.groupCheckAuthority(groups, type, authType);
        return queryModalGroups;
    }

    @Override
    public List<QueryModalDefine> getModalsByGroupId(String groupId, QueryModelType type, String authType) {
        try {
            this.modelDao.getModalsByGroupId(groupId);
            List<QueryModalDefine> modals = this.modelDao.getModalsByGroupId(groupId);
            List<QueryModalDefine> queryModalDefines = this.modelCheckAuthority(modals, type, authType);
            return queryModalDefines;
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    @Override
    public List<QueryModalGroup> getModalGroupByParentId(String groupId, QueryModelType type, String authType) {
        List<QueryModalGroup> groups = this.groupDao.getModalGroupByParentId(groupId);
        List<QueryModalGroup> queryModalGroups = this.groupCheckAuthority(groups, type, authType);
        return queryModalGroups;
    }

    public List<QueryModalDefine> modelCheckAuthority(List<QueryModalDefine> list, QueryModelType type, String authType) {
        if (type == QueryModelType.OWNER) {
            ArrayList<QueryModalDefine> queryModals = new ArrayList<QueryModalDefine>();
            for (QueryModalDefine queryModal : list) {
                switch (authType) {
                    case "query_model_resource_read": {
                        if (!this.authority.canReadModal(queryModal.getId(), "QueryModel")) break;
                        queryModals.add(queryModal);
                        break;
                    }
                    case "query_model_resource_write": {
                        if (!this.authority.canWriteModal(queryModal.getId(), "QueryModel")) break;
                        queryModals.add(queryModal);
                        break;
                    }
                    case "query_model_resource_delete": {
                        if (!this.authority.canDeleteModal(queryModal.getId(), "QueryModel")) break;
                        queryModals.add(queryModal);
                        break;
                    }
                }
            }
            return queryModals;
        }
        if (type == QueryModelType.DASHOWNER) {
            ArrayList<QueryModalDefine> queryModals = new ArrayList<QueryModalDefine>();
            for (QueryModalDefine queryModal : list) {
                switch (authType) {
                    case "dashboard_model_resource_read": 
                    case "query_model_resource_read": {
                        if (!this.authority.canReadModal(queryModal.getId(), "DashBoard")) break;
                        queryModals.add(queryModal);
                        break;
                    }
                    case "dashboard_model_resource_write": 
                    case "query_model_resource_write": {
                        if (!this.authority.canWriteModal(queryModal.getId(), "DashBoard")) break;
                        queryModals.add(queryModal);
                        break;
                    }
                    case "dashboard_model_resource_delete": 
                    case "query_model_resource_delete": {
                        if (!this.authority.canDeleteModal(queryModal.getId(), "DashBoard")) break;
                        queryModals.add(queryModal);
                        break;
                    }
                }
            }
            return queryModals;
        }
        if (type == QueryModelType.SIMPLEOWER) {
            ArrayList<QueryModalDefine> queryModals = new ArrayList<QueryModalDefine>();
            for (QueryModalDefine queryModal : list) {
                switch (authType) {
                    case "query_model_resource_read": 
                    case "simple_model_resource_read": {
                        if (!this.authority.canReadModal(queryModal.getId(), "SimpleQueryModel")) break;
                        queryModals.add(queryModal);
                        break;
                    }
                    case "query_model_resource_write": 
                    case "simple_model_resource_write": {
                        if (!this.authority.canWriteModal(queryModal.getId(), "SimpleQueryModel")) break;
                        queryModals.add(queryModal);
                        break;
                    }
                }
            }
            return queryModals;
        }
        if (type == QueryModelType.CUSTOMINPUT) {
            ArrayList<QueryModalDefine> queryModals = new ArrayList<QueryModalDefine>();
            for (QueryModalDefine queryModal : list) {
                switch (authType) {
                    case "query_model_resource_read": 
                    case "custom_model_resource_read": {
                        if (!this.authority.canReadModal(queryModal.getId(), "CustomInputModel")) break;
                        queryModals.add(queryModal);
                        break;
                    }
                    case "query_model_resource_write": 
                    case "custom_model_resource_write": {
                        if (!this.authority.canWriteModal(queryModal.getId(), "CustomInputModel")) break;
                        queryModals.add(queryModal);
                        break;
                    }
                }
            }
            return queryModals;
        }
        return list;
    }

    public List<QueryModalGroup> groupCheckAuthority(List<QueryModalGroup> list, QueryModelType type, String authType) {
        ArrayList<QueryModalGroup> queryModalGroups = new ArrayList<QueryModalGroup>();
        if (type == QueryModelType.OWNER) {
            for (QueryModalGroup queryModalGroup : list) {
                switch (authType) {
                    case "query_model_resource_read": {
                        if (!this.authority.canReadModal(queryModalGroup.getGroupId(), "queryModelGroup")) break;
                        queryModalGroups.add(queryModalGroup);
                        break;
                    }
                    case "query_model_resource_write": {
                        if (!this.authority.canWriteModal(queryModalGroup.getGroupId(), "queryModelGroup")) break;
                        queryModalGroups.add(queryModalGroup);
                        break;
                    }
                    case "query_model_resource_delete": {
                        if (!this.authority.canDeleteModal(queryModalGroup.getGroupId(), "queryModelGroup")) break;
                        queryModalGroups.add(queryModalGroup);
                        break;
                    }
                }
            }
            return queryModalGroups;
        }
        if (type == QueryModelType.DASHOWNER) {
            for (QueryModalGroup queryModalGroup : list) {
                switch (authType) {
                    case "dashboard_model_resource_read": 
                    case "query_model_resource_read": {
                        if (!this.authority.canReadModal(queryModalGroup.getGroupId(), "dashBoardGroup")) break;
                        queryModalGroups.add(queryModalGroup);
                        break;
                    }
                    case "dashboard_model_resource_write": 
                    case "query_model_resource_write": {
                        if (!this.authority.canWriteModal(queryModalGroup.getGroupId(), "dashBoardGroup")) break;
                        queryModalGroups.add(queryModalGroup);
                        break;
                    }
                    case "dashboard_model_resource_delete": 
                    case "query_model_resource_delete": {
                        if (!this.authority.canDeleteModal(queryModalGroup.getGroupId(), "dashBoardGroup")) break;
                        queryModalGroups.add(queryModalGroup);
                        break;
                    }
                }
            }
            return queryModalGroups;
        }
        if (type == QueryModelType.SIMPLEOWER) {
            for (QueryModalGroup queryModalGroup : list) {
                switch (authType) {
                    case "simple_model_resource_read": 
                    case "query_model_resource_read": {
                        if (!this.authority.canReadModal(queryModalGroup.getGroupId(), "simpleQueryModelGroup")) break;
                        queryModalGroups.add(queryModalGroup);
                        break;
                    }
                    case "simple_model_resource_write": 
                    case "query_model_resource_write": {
                        if (!this.authority.canWriteModal(queryModalGroup.getGroupId(), "simpleQueryModelGroup")) break;
                        queryModalGroups.add(queryModalGroup);
                        break;
                    }
                }
            }
            return queryModalGroups;
        }
        if (type == QueryModelType.CUSTOMINPUT) {
            for (QueryModalGroup queryModalGroup : list) {
                switch (authType) {
                    case "custom_model_resource_read": 
                    case "query_model_resource_read": {
                        if (!this.authority.canReadModal(queryModalGroup.getGroupId(), "CustomInputModelGroup")) break;
                        queryModalGroups.add(queryModalGroup);
                        break;
                    }
                    case "custom_model_resource_write": 
                    case "query_model_resource_write": {
                        if (!this.authority.canWriteModal(queryModalGroup.getGroupId(), "CustomInputModelGroup")) break;
                        queryModalGroups.add(queryModalGroup);
                        break;
                    }
                }
            }
            return queryModalGroups;
        }
        return list;
    }

    @Override
    public boolean canWriteModal(String queryModalKey, QueryModelType modelType) {
        String type = "QueryModel";
        switch (modelType) {
            case OWNER: {
                type = "QueryModel";
                break;
            }
            case DASHOWNER: {
                type = "DashBoard";
                break;
            }
            case SIMPLEOWER: {
                type = "SimpleQueryModel";
                break;
            }
            case CUSTOMINPUT: {
                type = "CustomInputModel";
                break;
            }
        }
        return this.authority.canWriteModal(queryModalKey, type);
    }

    @Override
    public boolean canWriteModalGroup(String queryModalGroupKey, QueryModelType modelType) {
        String type = "queryModelGroup";
        switch (modelType) {
            case OWNER: {
                type = "queryModelGroup";
                break;
            }
            case DASHOWNER: {
                type = "dashBoardGroup";
                break;
            }
            case SIMPLEOWER: {
                type = "simpleQueryModelGroup";
                break;
            }
            case CUSTOMINPUT: {
                type = "CustomInputModelGroup";
                break;
            }
        }
        return this.authority.canWriteModal(queryModalGroupKey, type);
    }

    @Override
    public boolean canDeleteModal(String queryModalKey, QueryModelType modelType) {
        String type = "QueryModel";
        switch (modelType) {
            case OWNER: {
                type = "QueryModel";
                break;
            }
            case DASHOWNER: {
                type = "DashBoard";
                break;
            }
            case CUSTOMINPUT: {
                type = "CustomInputModel";
                break;
            }
        }
        return this.authority.canWriteModal(queryModalKey, type);
    }

    @Override
    public boolean canDeleteModalGroup(String queryModalGroupKey, QueryModelType modelType) {
        String type = "queryModelGroup";
        switch (modelType) {
            case OWNER: {
                type = "queryModelGroup";
                break;
            }
            case DASHOWNER: {
                type = "dashBoardGroup";
                break;
            }
            case CUSTOMINPUT: {
                type = "CustomInputModelGroup";
                break;
            }
        }
        return this.authority.canWriteModal(queryModalGroupKey, type);
    }

    @Override
    public void grantAllPrivilegesForQueryModel(String resourceId, QueryModelType modelType) {
        String type = "QueryModel";
        switch (modelType) {
            case OWNER: {
                type = "QueryModel";
                break;
            }
            case SIMPLEOWER: {
                type = "SimpleQueryModel";
                break;
            }
            case CUSTOMINPUT: {
                type = "CustomInputModel";
                break;
            }
        }
        this.authority.grantAllPrivileges(resourceId, type, modelType);
    }

    @Override
    public void grantAllPrivilegesForQueryModelGroup(String resourceId, QueryModelType modelType) {
        String type = "queryModelGroup";
        switch (modelType) {
            case OWNER: {
                type = "queryModelGroup";
                break;
            }
            case SIMPLEOWER: {
                type = "simpleQueryModelGroup";
                break;
            }
            case CUSTOMINPUT: {
                type = "CustomInputModelGroup";
                break;
            }
        }
        this.authority.grantAllPrivileges(resourceId, type, modelType);
    }
}

