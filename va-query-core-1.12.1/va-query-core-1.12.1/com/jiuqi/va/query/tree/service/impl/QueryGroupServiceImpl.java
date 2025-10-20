/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.query.exception.DefinedQueryRuntimeException
 *  com.jiuqi.va.query.tree.vo.GroupVO
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.va.query.tree.service.impl;

import com.jiuqi.va.query.exception.DefinedQueryRuntimeException;
import com.jiuqi.va.query.tree.dao.QueryGroupDao;
import com.jiuqi.va.query.tree.service.QueryGroupService;
import com.jiuqi.va.query.tree.vo.GroupVO;
import com.jiuqi.va.query.util.DCQueryStringHandle;
import com.jiuqi.va.query.util.DCQueryUUIDUtil;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class QueryGroupServiceImpl
implements QueryGroupService {
    @Autowired
    private QueryGroupDao queryGroupDao;

    @Override
    public List<GroupVO> getGroupsByParentGroupId(String parentId) {
        return this.queryGroupDao.getGroupsByParentGroupId(parentId);
    }

    @Override
    public GroupVO getGroupById(String id) {
        return this.queryGroupDao.getGroupById(id);
    }

    @Override
    public boolean hasGroupByCode(String code) {
        return this.queryGroupDao.hasGroupByCode(code);
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void save(GroupVO groupVO) {
        List<GroupVO> groups;
        if (this.queryGroupDao.hasGroupByCode(groupVO.getCode())) {
            throw new DefinedQueryRuntimeException("\u8be5\u5206\u7ec4\u6807\u8bc6\u5df2\u7ecf\u5b58\u5728\uff0c\u4fdd\u5b58\u5931\u8d25\uff01");
        }
        if (DCQueryStringHandle.isEmpty(groupVO.getParentId())) {
            groupVO.setParentId("00000000-0000-0000-0000-000000000000");
        }
        if ((groups = this.queryGroupDao.getGroupsByParentGroupId(groupVO.getParentId())) == null || groups.isEmpty()) {
            groupVO.setSortOrder(1);
        } else {
            groupVO.setSortOrder(groups.get(groups.size() - 1).getSortOrder() + 1);
        }
        if (groupVO.getId() == null) {
            groupVO.setId(DCQueryUUIDUtil.getUUIDStr());
        }
        this.queryGroupDao.save(groupVO);
    }
}

