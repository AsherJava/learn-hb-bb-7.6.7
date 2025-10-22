/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.context.NpContextHolder
 */
package com.jiuqi.nr.query.dao.impl;

import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.nr.query.auth.QueryModelAuthorityProvider;
import com.jiuqi.nr.query.dao.IQueryModalDefineDao;
import com.jiuqi.nr.query.dao.IQueryModalGroupDao;
import com.jiuqi.nr.query.dao.define.QueryMGroupDao;
import com.jiuqi.nr.query.querymodal.QueryModalDefine;
import com.jiuqi.nr.query.querymodal.QueryModalGroup;
import com.jiuqi.nr.query.querymodal.QueryModelType;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class QueryModalGroupDao
implements IQueryModalGroupDao {
    @Autowired
    private QueryMGroupDao qMDao;
    @Autowired
    private IQueryModalDefineDao modelDao;
    @Autowired
    private QueryModelAuthorityProvider authority;
    private static final Logger logger = LoggerFactory.getLogger(QueryModalGroupDao.class);

    @Override
    public Boolean InsertQueryModalGroup(QueryModalGroup QueryModalGroup2) {
        Assert.notNull((Object)QueryModalGroup2, "'QueryModalGroup' must not be null");
        try {
            this.qMDao.insertMGroup(QueryModalGroup2);
            return true;
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return false;
        }
    }

    @Override
    public Boolean UpdateQueryModalGroup(QueryModalGroup QueryModalGroup2) {
        Assert.notNull((Object)QueryModalGroup2, "'QueryModalGroup' must not be null");
        try {
            int result = this.qMDao.updateMGroup(QueryModalGroup2);
            return result > 0;
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return false;
        }
    }

    @Override
    public Boolean DeleteQueryModalGroupById(String queryModalGroupId, List<QueryModalGroup> groups, List<QueryModalDefine> modals) {
        try {
            int result = this.qMDao.deleteById(queryModalGroupId);
            if (result > 0) {
                if (modals.size() > 0) {
                    for (QueryModalDefine modal : modals) {
                        this.modelDao.deleteQueryModalDefineById(modal.getId());
                    }
                }
                if (groups.size() > 0) {
                    for (QueryModalGroup group : groups) {
                        this.qMDao.deleteById(group.getGroupId());
                    }
                }
                return true;
            }
            return false;
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return false;
        }
    }

    @Override
    public QueryModalGroup GetQueryModalGroupById(String QueryModalGroupId) {
        try {
            return this.qMDao.queryGroupById(QueryModalGroupId);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    @Override
    public List<QueryModalGroup> GetAllQueryModalGroups() {
        try {
            return this.qMDao.list();
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    @Override
    public List<QueryModalGroup> GetAllQueryModalGroups(QueryModelType type) {
        try {
            return this.qMDao.list(type);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    @Override
    public List<QueryModalGroup> getModalGroupByParentId(String parentId) {
        try {
            String userId = NpContextHolder.getContext().getUserId();
            List<QueryModalGroup> groups = this.qMDao.getMGroupByParentId(parentId);
            String finalUserId = userId;
            groups = groups.stream().filter(group -> Objects.equals(group.getCreator(), finalUserId) || this.authority.canReadModal(group.getGroupId(), "queryModelGroup") || this.authority.canReadModal(group.getGroupId(), "simpleQueryModelGroup")).collect(Collectors.toList());
            return groups;
        }
        catch (Exception e) {
            logger.error("\u6839\u636eparentId\u67e5\u8be2\u6240\u6709\u6a21\u677f\u5206\u7ec4\u9519\u8bef:" + e.getMessage(), e);
            return null;
        }
    }

    @Override
    public List<QueryModalGroup> getModalGroupByParentId(String parentId, QueryModelType type) {
        try {
            String userId = NpContextHolder.getContext().getUserId();
            List<QueryModalGroup> groups = this.qMDao.getMGroupByParentIdWithType(parentId, type, null);
            String finalUserId = userId;
            groups = groups.stream().filter(group -> Objects.equals(group.getCreator(), finalUserId) || this.authority.canReadModal(group.getGroupId(), "queryModelGroup") || this.authority.canReadModal(group.getGroupId(), "simpleQueryModelGroup") || this.authority.canReadModal(group.getGroupId(), "CustomInputModelGroup") || this.authority.canReadModal(group.getGroupId(), "dashBoardGroup")).collect(Collectors.toList());
            return groups;
        }
        catch (Exception e) {
            logger.error("\u6839\u636eparentId\u67e5\u8be2\u6240\u6709\u6a21\u677f\u5206\u7ec4\u9519\u8bef:" + e.getMessage(), e);
            return null;
        }
    }

    @Override
    public List<QueryModalGroup> getGroupsByCondition(String condition, String[] values) {
        try {
            return this.qMDao.getGroupsByCondition(condition, values);
        }
        catch (Exception e) {
            logger.error("\u6839\u636e\u6761\u4ef6\u67e5\u8be2\u6240\u6709\u6a21\u677f\u5206\u7ec4\u9519\u8bef:" + e.getMessage(), e);
            return null;
        }
    }

    @Override
    public List<QueryModalGroup> getModalGroupByTitle(String title, String parentId) {
        try {
            return this.qMDao.getMGroupByTitle(title, parentId);
        }
        catch (Exception e) {
            logger.error("\u6839\u636e\u6807\u9898\u67e5\u8be2\u6240\u6709\u6a21\u677f\u5206\u7ec4\u9519\u8bef:" + e.getMessage(), e);
            return null;
        }
    }

    @Override
    public List<QueryModalGroup> getAllChartModalGroups() {
        try {
            return this.qMDao.getAllChartModelGroups();
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }
}

