/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.OrderGenerator
 *  com.jiuqi.nr.datascheme.api.DataScheme
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.core.Ordered
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nvwa.definition.common.UUIDUtils
 *  javax.validation.ConstraintViolation
 *  javax.validation.Validator
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.nr.dataresource.service.impl;

import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.nr.dataresource.DataResource;
import com.jiuqi.nr.dataresource.DataResourceKind;
import com.jiuqi.nr.dataresource.NodeType;
import com.jiuqi.nr.dataresource.ResourceNode;
import com.jiuqi.nr.dataresource.authority.DataResourceAuthorityService;
import com.jiuqi.nr.dataresource.common.DataResourceConvert;
import com.jiuqi.nr.dataresource.common.DataResourceEnum;
import com.jiuqi.nr.dataresource.common.DataResourceException;
import com.jiuqi.nr.dataresource.dao.IDataResourceDao;
import com.jiuqi.nr.dataresource.dao.IDimAttributeDao;
import com.jiuqi.nr.dataresource.dao.IResourceLinkDao;
import com.jiuqi.nr.dataresource.entity.DataResourceDO;
import com.jiuqi.nr.dataresource.loader.DataResourceLevelLoader;
import com.jiuqi.nr.dataresource.service.IDataResourceService;
import com.jiuqi.nr.dataresource.service.impl.DeleteNodeVisitor;
import com.jiuqi.nr.datascheme.api.DataScheme;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.core.Ordered;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nvwa.definition.common.UUIDUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
@Transactional(rollbackFor={Exception.class})
public class DataResourceServiceImpl
implements IDataResourceService {
    @Autowired
    private IDataResourceDao resourceDao;
    @Autowired
    private IResourceLinkDao linkDao;
    @Autowired
    private IDimAttributeDao attributeDao;
    @Autowired
    private DataResourceLevelLoader levelLoader;
    @Autowired
    private Validator validator;
    @Autowired
    private IRuntimeDataSchemeService service;
    @Autowired
    private DataResourceAuthorityService auth;

    @Override
    @Transactional(readOnly=true)
    public DataResource init() {
        DataResourceDO resourceDO = new DataResourceDO();
        resourceDO.setKey(UUIDUtils.getKey());
        resourceDO.setResourceKind(DataResourceKind.RESOURCE_GROUP);
        return resourceDO;
    }

    @Override
    public String insert(DataResource entity) {
        Assert.notNull((Object)entity, "entity must not be null.");
        if (entity.getKey() == null) {
            entity.setKey(UUIDUtils.getKey());
        }
        DataResourceDO dg2Do = DataResourceConvert.iDr2Do(entity);
        this.checkDataResource(dg2Do);
        return this.resourceDao.insert(dg2Do);
    }

    private void checkDataResource(DataResource entity) {
        if (!this.auth.canWrite(entity.getResourceDefineKey(), NodeType.TREE.getValue())) {
            throw new DataResourceException(DataResourceEnum.DATA_RESOURCE_DG_3_3.getMessage());
        }
        Assert.notNull((Object)entity, "entity must not be null.");
        if (!StringUtils.hasText(entity.getTitle())) {
            throw new DataResourceException(DataResourceEnum.DATA_RESOURCE_DR_1_1.getMessage());
        }
        if (entity.getOrder() == null) {
            entity.setOrder(OrderGenerator.newOrder());
        }
        if (entity.getResourceDefineKey().equals(entity.getParentKey())) {
            entity.setParentKey(null);
        }
        if (entity.getResourceKind() != DataResourceKind.RESOURCE_GROUP && entity.getResourceKind() != DataResourceKind.MD_INFO) {
            String dimKey = entity.getDimKey();
            Assert.notNull((Object)dimKey, "dimKey must not be null.");
        }
        this.validate(entity);
        Optional first = this.resourceDao.getByConditions(entity.getResourceDefineKey(), entity.getParentKey(), entity.getTitle()).stream().findFirst();
        if (first.isPresent() && !entity.getKey().equals(((DataResourceDO)first.get()).getKey())) {
            throw new DataResourceException(DataResourceEnum.DATA_RESOURCE_DD_1_1.getMessage());
        }
    }

    @Override
    public void delete(String key) {
        Assert.notNull((Object)key, "key must not be null.");
        DataResourceDO resourceDO = (DataResourceDO)this.resourceDao.get(key);
        if (resourceDO == null) {
            return;
        }
        if (!this.auth.canWrite(resourceDO.getResourceDefineKey(), NodeType.TREE.getValue())) {
            throw new DataResourceException(DataResourceEnum.DATA_RESOURCE_DG_3_3.getMessage());
        }
        this.deleteByParentKey(key);
        this.resourceDao.delete(key);
        this.linkDao.delete(key);
    }

    @Override
    public void update(DataResource entity) {
        Assert.notNull((Object)entity, "entity must not be null.");
        Assert.notNull((Object)entity.getKey(), "entity.getKey must not be null.");
        DataResourceDO resourceDO = DataResourceConvert.iDr2Do(entity);
        this.checkDataResource(resourceDO);
        this.resourceDao.update(resourceDO);
    }

    @Override
    @Transactional(readOnly=true)
    public DataResource get(String key) {
        Assert.notNull((Object)key, "key must not be null.");
        return (DataResource)this.resourceDao.get(key);
    }

    @Override
    public String[] insert(List<DataResource> entity) {
        Assert.notNull(entity, "entity must not be null.");
        ArrayList<DataResourceDO> list = new ArrayList<DataResourceDO>(entity.size());
        for (DataResource dataResource : entity) {
            Assert.notNull((Object)dataResource, "Collection should not contain null.");
            DataResourceDO resourceDO = DataResourceConvert.iDr2Do(dataResource);
            this.checkDataResource(resourceDO);
            list.add(resourceDO);
        }
        return this.resourceDao.insert(list);
    }

    @Override
    public void delete(List<String> keys) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void update(List<DataResource> resourceTrees) {
        Assert.notNull(resourceTrees, "resourceTrees must not be null.");
        ArrayList<DataResourceDO> list = new ArrayList<DataResourceDO>();
        for (DataResource resourceTree : resourceTrees) {
            Assert.notNull((Object)resourceTree, "resourceTree must not be null.");
            Assert.notNull((Object)resourceTree.getKey(), "resourceTree.getKey must not be null.");
            DataResourceDO resourceDO = DataResourceConvert.iDr2Do(resourceTree);
            this.checkDataResource(resourceDO);
            list.add(resourceDO);
        }
        this.resourceDao.update(list);
    }

    @Override
    @Transactional(readOnly=true)
    public List<DataResource> get(List<String> keys) {
        Assert.notNull(keys, "keys must not be null.");
        List list = this.resourceDao.get(keys);
        return new ArrayList<DataResource>(list);
    }

    @Override
    @Transactional(readOnly=true)
    public List<DataResource> getByParentKey(String key) {
        Assert.notNull((Object)key, "key must not be null.");
        DataResourceDO resourceDO = (DataResourceDO)this.resourceDao.get(key);
        if (resourceDO == null) {
            return Collections.emptyList();
        }
        List<Object> list = Collections.emptyList();
        DataResourceKind resourceKind = resourceDO.getResourceKind();
        if (resourceKind == DataResourceKind.RESOURCE_GROUP) {
            list = this.resourceDao.getByParent(key);
        }
        return new ArrayList<DataResource>(list);
    }

    @Override
    @Transactional(readOnly=true)
    public List<DataResource> getBy(String defineKey, int resourceKind, List<String> dimKey) {
        List<DataResourceDO> list = this.resourceDao.getByConditions(defineKey, resourceKind, dimKey);
        return new ArrayList<DataResource>(list);
    }

    private List<DataResourceDO> buildScheme(List<DataScheme> dataSchemes, List<DataTable> dataTables, String dimKey) {
        HashMap<String, DataTable> dataTableMap = new HashMap<String, DataTable>(dataTables.size());
        for (DataTable dataTable : dataTables) {
            dataTableMap.put(dataTable.getDataSchemeKey(), dataTable);
        }
        ArrayList<DataResourceDO> list = new ArrayList<DataResourceDO>(dataSchemes.size());
        for (DataScheme dataScheme : dataSchemes) {
            DataResourceDO resourceDO = new DataResourceDO();
            DataTable dataTable = (DataTable)dataTableMap.get(dataScheme.getKey());
            resourceDO.setKey(dataTable.getKey());
            resourceDO.setTitle(dataScheme.getTitle());
            resourceDO.setDimKey(dimKey);
            resourceDO.setResourceKind(DataResourceKind.DIM_FMDM_GROUP);
            resourceDO.setOrder(dataScheme.getOrder());
            list.add(resourceDO);
        }
        return list;
    }

    @Override
    public void deleteByParentKey(String key) {
        Assert.notNull((Object)key, "key must not be null.");
        DataResourceDO resourceDO = (DataResourceDO)this.resourceDao.get(key);
        if (resourceDO == null) {
            return;
        }
        DeleteNodeVisitor visitor = new DeleteNodeVisitor(this.resourceDao, this.linkDao, this.attributeDao);
        ResourceNode voidResourceNode = new ResourceNode(key, resourceDO.getResourceKind().getValue());
        this.levelLoader.walkDataResourceTree(voidResourceNode, visitor);
    }

    @Override
    @Transactional(readOnly=true)
    public List<DataResource> searchBy(String defineKey, String keyword) {
        Assert.notNull((Object)defineKey, "defineKey must not be null.");
        Assert.notNull((Object)keyword, "keyword must not be null.");
        List<DataResourceDO> list = this.resourceDao.searchBy(defineKey, keyword);
        return new ArrayList<DataResource>(list);
    }

    @Override
    public List<DataResource> getByDefineKey(String resourceTreeKey) {
        List<DataResourceDO> resourceS = this.resourceDao.getByDefineKey(resourceTreeKey);
        return resourceS.stream().filter(r -> this.auth.canRead(r.getKey(), NodeType.RESOURCE_GROUP.getValue())).collect(Collectors.toList());
    }

    @Override
    public List<DataResourceDO> getByParent(String resourceTreeKey, String parentKey) {
        List<DataResourceDO> resourceDOS = this.resourceDao.getByParent(resourceTreeKey, parentKey);
        if (!CollectionUtils.isEmpty(resourceDOS)) {
            return resourceDOS.stream().filter(r -> this.auth.canRead(r.getKey(), NodeType.RESOURCE_GROUP.getValue())).collect(Collectors.toList());
        }
        return null;
    }

    @Override
    public DataResourceDO getByKey(String key) {
        DataResourceDO dataResourceDO = (DataResourceDO)this.resourceDao.get(key);
        if (dataResourceDO != null && this.auth.canRead(dataResourceDO.getKey(), NodeType.RESOURCE_GROUP.getValue())) {
            return dataResourceDO;
        }
        return null;
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
}

