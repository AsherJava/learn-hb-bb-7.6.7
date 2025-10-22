/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.definition.common.StringUtils
 */
package com.jiuqi.nr.query.transfer.query.service.impl;

import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.definition.common.StringUtils;
import com.jiuqi.nr.query.dao.IQueryBlockDefineDao;
import com.jiuqi.nr.query.dao.IQueryModalDefineDao;
import com.jiuqi.nr.query.dao.IQueryModalGroupDao;
import com.jiuqi.nr.query.querymodal.QueryModalDefine;
import com.jiuqi.nr.query.querymodal.QueryModalGroup;
import com.jiuqi.nr.query.querymodal.QueryModelType;
import com.jiuqi.nr.query.transfer.query.service.IQueryService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QueryServiceImpl
implements IQueryService {
    private static final Logger log = LoggerFactory.getLogger(QueryServiceImpl.class);
    @Autowired
    private IQueryModalGroupDao iQueryModalGroupDao;
    @Autowired
    private IQueryModalDefineDao iQueryModalDefineDao;
    @Autowired
    private IQueryBlockDefineDao queryBlockDefineDao;

    @Override
    public List<QueryModalGroup> getGroup(QueryModelType type) {
        return this.iQueryModalGroupDao.GetAllQueryModalGroups(type);
    }

    @Override
    public QueryModalGroup getGroup(String groupId) {
        return this.iQueryModalGroupDao.GetQueryModalGroupById(groupId);
    }

    @Override
    public List<QueryModalGroup> getChildrenGroup(String parentId) {
        return this.iQueryModalGroupDao.getModalGroupByParentId(parentId);
    }

    @Override
    public String[] getGroupPath(String groupId) {
        ArrayList<String> groups = new ArrayList<String>();
        groups.add(groupId);
        if ("b8079ac0-dc15-11e8-969b-64006a6432d8".equals(groupId)) {
            return groups.toArray(new String[1]);
        }
        QueryModalGroup modalGroup = this.iQueryModalGroupDao.GetQueryModalGroupById(groupId);
        if (modalGroup == null) {
            return groups.toArray(new String[1]);
        }
        List<QueryModalGroup> allGroups = this.iQueryModalGroupDao.GetAllQueryModalGroups(modalGroup.getModelType());
        HashMap<String, String> pathMap = new HashMap<String, String>();
        allGroups.forEach(e -> pathMap.put(e.getGroupId(), e.getParentGroupId()));
        this.buildGroupPath(groups, pathMap);
        String[] path = new String[groups.size()];
        int index = 0;
        Iterator iterator = groups.iterator();
        while (iterator.hasNext()) {
            String group;
            path[index] = group = (String)iterator.next();
            ++index;
        }
        return path;
    }

    void buildGroupPath(List<String> groups, Map<String, String> pathMap) {
        String parent;
        String group = groups.get(groups.size() - 1);
        if (!"b8079ac0-dc15-11e8-969b-64006a6432d8".equals(group) && !StringUtils.isEmpty((String)(parent = pathMap.get(group)))) {
            groups.add(parent);
            this.buildGroupPath(groups, pathMap);
        }
    }

    @Override
    public Boolean insertTemplate(QueryModalDefine template) throws JQException {
        return this.iQueryModalDefineDao.insertQueryModalDefine(template);
    }

    @Override
    public Boolean insertGroup(QueryModalGroup group) throws JQException {
        return this.iQueryModalGroupDao.InsertQueryModalGroup(group);
    }
}

