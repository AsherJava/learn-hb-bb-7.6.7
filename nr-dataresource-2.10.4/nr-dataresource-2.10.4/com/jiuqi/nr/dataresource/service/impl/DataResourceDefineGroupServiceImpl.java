/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.OrderGenerator
 *  com.jiuqi.np.authz2.service.SystemIdentityService
 *  com.jiuqi.nr.datascheme.api.core.Ordered
 *  com.jiuqi.nvwa.definition.common.UUIDUtils
 *  javax.validation.ConstraintViolation
 *  javax.validation.Validator
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.nr.dataresource.service.impl;

import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.np.authz2.service.SystemIdentityService;
import com.jiuqi.nr.dataresource.DataResourceDefineGroup;
import com.jiuqi.nr.dataresource.NodeType;
import com.jiuqi.nr.dataresource.authority.DataResourceAuthorityService;
import com.jiuqi.nr.dataresource.common.DataResourceConvert;
import com.jiuqi.nr.dataresource.common.DataResourceEnum;
import com.jiuqi.nr.dataresource.common.DataResourceException;
import com.jiuqi.nr.dataresource.dao.IDataResourceDefineDao;
import com.jiuqi.nr.dataresource.dao.IDataResourceDefineGroupDao;
import com.jiuqi.nr.dataresource.entity.ResourceTreeDO;
import com.jiuqi.nr.dataresource.entity.ResourceTreeGroup;
import com.jiuqi.nr.dataresource.service.IDataResourceDefineGroupService;
import com.jiuqi.nr.datascheme.api.core.Ordered;
import com.jiuqi.nvwa.definition.common.UUIDUtils;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.Stack;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Service
@Transactional(rollbackFor={Exception.class})
public class DataResourceDefineGroupServiceImpl
implements IDataResourceDefineGroupService {
    @Autowired
    private IDataResourceDefineGroupDao groupDao;
    @Autowired
    private IDataResourceDefineDao defineDao;
    @Autowired
    private Validator validator;
    @Autowired
    private DataResourceAuthorityService auth;
    @Autowired
    private SystemIdentityService systemIdentityService;

    @Override
    @Transactional(readOnly=true)
    public DataResourceDefineGroup init() {
        ResourceTreeGroup resourceTreeGroup = new ResourceTreeGroup();
        resourceTreeGroup.setKey(UUIDUtils.getKey());
        return resourceTreeGroup;
    }

    @Override
    public String insert(DataResourceDefineGroup entity) {
        ResourceTreeGroup dg2Do;
        Assert.notNull((Object)entity, "entity must not be null.");
        if (entity.getKey() == null) {
            entity.setKey(UUIDUtils.getKey());
        }
        if ((dg2Do = DataResourceConvert.iDg2Do(entity)).getParentKey().equals("00000000-0000-0000-0000-000000000000") && !this.systemIdentityService.isAdmin()) {
            throw new DataResourceException(DataResourceEnum.DATA_RESOURCE_DG_3_5.getMessage());
        }
        this.checkDataGroup(dg2Do);
        String key = this.groupDao.insert(dg2Do);
        return key;
    }

    private void checkDataGroup(DataResourceDefineGroup entity) {
        if (!this.auth.canWrite(entity.getParentKey(), NodeType.TREE_GROUP.getValue())) {
            throw new DataResourceException(DataResourceEnum.DATA_RESOURCE_DG_3_1.getMessage());
        }
        if (entity.getTitle() == null) {
            throw new DataResourceException(DataResourceEnum.DATA_RESOURCE_DG_1_1.getMessage());
        }
        if (entity.getOrder() == null) {
            entity.setOrder(OrderGenerator.newOrder());
        }
        if (entity.getParentKey() == null) {
            entity.setParentKey("00000000-0000-0000-0000-000000000000");
        }
        this.validate(entity);
        Optional first = this.groupDao.getByConditions(entity.getParentKey(), entity.getTitle()).stream().findFirst();
        if (first.isPresent() && !entity.getKey().equals(((ResourceTreeGroup)first.get()).getKey())) {
            throw new DataResourceException(DataResourceEnum.DATA_RESOURCE_DG_1_1.getMessage());
        }
    }

    @Override
    public void delete(String key) {
        String next;
        Assert.notNull((Object)key, "key must not be null.");
        if (!"00000000-0000-0000-0000-000000000000".equals(key)) {
            ResourceTreeGroup resourceTreeGroup = (ResourceTreeGroup)this.groupDao.get(key);
            if (resourceTreeGroup == null) {
                return;
            }
            if (!this.auth.canWrite(key, NodeType.TREE_GROUP.getValue())) {
                throw new DataResourceException(DataResourceEnum.DATA_RESOURCE_DG_3_2.getMessage());
            }
        }
        LinkedList<String> delete = new LinkedList<String>();
        Stack<String> stack = new Stack<String>();
        stack.push(key);
        while (!stack.isEmpty() && (next = (String)stack.pop()) != null) {
            delete.add(next);
            List<ResourceTreeDO> defines = this.defineDao.getByResourceGroupKey(next);
            if (!defines.isEmpty()) {
                throw new DataResourceException(DataResourceEnum.DATA_RESOURCE_DG_2_1.getMessage());
            }
            List<ResourceTreeGroup> byParent = this.groupDao.getByParent(next);
            byParent.forEach(e -> stack.add(e.getKey()));
            if (stack.size() <= 100) continue;
            throw new RuntimeException("\u5206\u7ec4\u7ea7\u6b21\u5b58\u5728\u73af\u5f62\u6570\u636e");
        }
        this.groupDao.delete(delete);
    }

    @Override
    public void update(DataResourceDefineGroup entity) {
        Assert.notNull((Object)entity, "entity must not be null.");
        Assert.notNull((Object)entity.getKey(), "entity.getKey must not be null.");
        if (!this.auth.canWrite(entity.getKey(), NodeType.TREE_GROUP.getValue())) {
            throw new DataResourceException(DataResourceEnum.DATA_RESOURCE_DG_3_2.getMessage());
        }
        DataResourceDefineGroup odlGroup = (DataResourceDefineGroup)this.groupDao.get(entity.getKey());
        if (!odlGroup.getParentKey().equals(entity.getParentKey()) && entity.getParentKey().equals("00000000-0000-0000-0000-000000000000") && !this.systemIdentityService.isAdmin()) {
            throw new DataResourceException(DataResourceEnum.DATA_RESOURCE_DG_3_5.getMessage());
        }
        this.checkDataGroup(entity);
        ResourceTreeGroup dg2Do = DataResourceConvert.iDg2Do(entity);
        this.groupDao.update(dg2Do);
    }

    @Override
    @Transactional(readOnly=true)
    public DataResourceDefineGroup get(String key) {
        Assert.notNull((Object)key, "key must not be null.");
        return (DataResourceDefineGroup)this.groupDao.get(key);
    }

    @Override
    public String[] insert(List<DataResourceDefineGroup> entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(List<String> keys) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void update(List<DataResourceDefineGroup> resourceTrees) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Transactional(readOnly=true)
    public List<DataResourceDefineGroup> get(List<String> keys) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Transactional(readOnly=true)
    public List<DataResourceDefineGroup> getByParentKey(String key) {
        Assert.notNull((Object)key, "key must not be null.");
        List<ResourceTreeGroup> byParent = this.groupDao.getByParent(key);
        return new ArrayList<DataResourceDefineGroup>(byParent);
    }

    @Override
    public List<ResourceTreeGroup> fuzzySearch(String fuzzyKey) {
        List<ResourceTreeGroup> searchList = this.groupDao.fuzzySearch(fuzzyKey);
        return new ArrayList<ResourceTreeGroup>(searchList);
    }

    @Override
    public void deleteByParentKey(String key) {
        Assert.notNull((Object)key, "key must not be null.");
        this.groupDao.deleteByParent(key);
    }

    private void validate(Ordered dataResource) throws DataResourceException {
        Iterator iterator;
        Set validate = this.validator.validate((Object)dataResource, new Class[0]);
        if (validate != null && !validate.isEmpty() && (iterator = validate.iterator()).hasNext()) {
            ConstraintViolation item = (ConstraintViolation)iterator.next();
            String message = item.getMessage();
            throw new DataResourceException(message);
        }
    }

    @Override
    @Transactional(readOnly=true)
    public List<DataResourceDefineGroup> searchBy(String defineKey, String keyword) {
        return null;
    }
}

