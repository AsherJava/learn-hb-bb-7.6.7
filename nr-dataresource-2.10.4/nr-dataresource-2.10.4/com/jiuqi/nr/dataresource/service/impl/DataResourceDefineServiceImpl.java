/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.OrderGenerator
 *  com.jiuqi.np.authz2.service.SystemIdentityService
 *  com.jiuqi.nr.datascheme.api.core.Ordered
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nvwa.definition.common.UUIDUtils
 *  javax.validation.ConstraintViolation
 *  javax.validation.Validator
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.nr.dataresource.service.impl;

import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.np.authz2.service.SystemIdentityService;
import com.jiuqi.nr.dataresource.DataResource;
import com.jiuqi.nr.dataresource.DataResourceDefine;
import com.jiuqi.nr.dataresource.DataResourceKind;
import com.jiuqi.nr.dataresource.DimType;
import com.jiuqi.nr.dataresource.NodeType;
import com.jiuqi.nr.dataresource.authority.DataResourceAuthorityService;
import com.jiuqi.nr.dataresource.common.DataResourceConvert;
import com.jiuqi.nr.dataresource.common.DataResourceEnum;
import com.jiuqi.nr.dataresource.common.DataResourceException;
import com.jiuqi.nr.dataresource.dao.IDataResourceDao;
import com.jiuqi.nr.dataresource.dao.IDataResourceDefineDao;
import com.jiuqi.nr.dataresource.dao.IDimAttributeDao;
import com.jiuqi.nr.dataresource.dao.IResourceLinkDao;
import com.jiuqi.nr.dataresource.entity.DataResourceDO;
import com.jiuqi.nr.dataresource.entity.DataResourceLinkDO;
import com.jiuqi.nr.dataresource.entity.DimAttributeDO;
import com.jiuqi.nr.dataresource.entity.ResourceTreeDO;
import com.jiuqi.nr.dataresource.service.IDataResourceDefineService;
import com.jiuqi.nr.dataresource.service.IDataResourceService;
import com.jiuqi.nr.datascheme.api.core.Ordered;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nvwa.definition.common.UUIDUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Service
@Transactional(rollbackFor={Exception.class})
public class DataResourceDefineServiceImpl
implements IDataResourceDefineService {
    @Autowired
    private IDataResourceDefineDao defineDao;
    @Autowired
    private IDataResourceDao resourceDao;
    @Autowired
    private IResourceLinkDao linkDao;
    @Autowired
    private IDimAttributeDao attributeDao;
    @Autowired
    private Validator validator;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private PeriodEngineService periodEngineService;
    @Autowired
    private DataResourceAuthorityService auth;
    @Autowired
    private SystemIdentityService systemIdentityService;
    @Autowired
    private IDataResourceService dataResourceService;

    @Override
    @Transactional(readOnly=true)
    public DataResourceDefine init() {
        return new ResourceTreeDO(UUIDUtils.getKey());
    }

    @Override
    public String insert(DataResourceDefine entity) {
        DataResourceDO resourceDO;
        ResourceTreeDO dg2Do;
        Assert.notNull((Object)entity, "entity must not be null.");
        if (entity.getKey() == null) {
            entity.setKey(UUIDUtils.getKey());
        }
        if ((dg2Do = DataResourceConvert.iDd2Do(entity)).getGroupKey().equals("00000000-0000-0000-0000-000000000000") && !this.systemIdentityService.isAdmin()) {
            throw new DataResourceException(DataResourceEnum.DATA_RESOURCE_DG_3_5.getMessage());
        }
        this.checkDataDefine(dg2Do);
        String insert = this.defineDao.insert(dg2Do);
        String dimKey = dg2Do.getDimKey();
        try {
            IEntityDefine iEntityDefine = this.entityMetaService.queryEntity(dimKey);
            resourceDO = this.buildResource(iEntityDefine);
        }
        catch (Exception e) {
            throw new DataResourceException(" \u5b9e\u4f53 " + dimKey + " \u672a\u627e\u5230\u6216\u5df2\u4e22\u5931", e);
        }
        resourceDO.setResourceDefineKey(insert);
        resourceDO.setDimKey(dimKey);
        this.resourceDao.insert(resourceDO);
        return insert;
    }

    private DataResourceDO buildResource(IEntityDefine iEntityDefine) {
        DataResourceDO resourceDO = new DataResourceDO();
        resourceDO.setKey(UUIDUtils.getKey());
        resourceDO.setResourceKind(DataResourceKind.DIM_GROUP);
        resourceDO.setOrder(OrderGenerator.newOrder());
        resourceDO.setTitle(iEntityDefine.getTitle());
        return resourceDO;
    }

    private void checkDataDefineWithoutAuth(DataResourceDefine entity) {
        if (entity.getTitle() == null) {
            throw new DataResourceException(DataResourceEnum.DATA_RESOURCE_DD_1_2.getMessage());
        }
        if (entity.getOrder() == null) {
            entity.setOrder(OrderGenerator.newOrder());
        }
        if (entity.getGroupKey() == null) {
            entity.setGroupKey("00000000-0000-0000-0000-000000000000");
        }
        this.validate(entity);
        Optional first = this.defineDao.getByConditions(entity.getGroupKey(), entity.getTitle()).stream().findFirst();
        if (first.isPresent() && !entity.getKey().equals(((ResourceTreeDO)first.get()).getKey())) {
            throw new DataResourceException(DataResourceEnum.DATA_RESOURCE_DD_1_1.getMessage());
        }
    }

    private void checkDataDefine(DataResourceDefine entity) {
        if (!this.auth.canWrite(entity.getGroupKey(), NodeType.TREE_GROUP.getValue())) {
            throw new DataResourceException(DataResourceEnum.DATA_RESOURCE_DG_3_1.getMessage());
        }
        this.checkDataDefineWithoutAuth(entity);
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
    public void delete(String key) {
        Assert.notNull((Object)key, "key must not be null.");
        if (!this.auth.canWrite(key, NodeType.TREE.getValue())) {
            throw new DataResourceException(DataResourceEnum.DATA_RESOURCE_DG_3_3.getMessage());
        }
        this.defineDao.delete(key);
        this.resourceDao.deleteByDefineKey(key);
        this.linkDao.deleteByDefineKey(key);
        this.attributeDao.delete(key);
    }

    @Override
    public void update(DataResourceDefine entity) {
        Assert.notNull((Object)entity, "entity must not be null.");
        Assert.notNull((Object)entity.getKey(), "entity.getKey must not be null.");
        ResourceTreeDO treeDO = DataResourceConvert.iDd2Do(entity);
        if (!this.auth.canWrite(entity.getKey(), NodeType.TREE.getValue())) {
            throw new DataResourceException(DataResourceEnum.DATA_RESOURCE_DG_3_3.getMessage());
        }
        ResourceTreeDO oldTree = (ResourceTreeDO)this.defineDao.get(entity.getKey());
        if (!treeDO.getGroupKey().equals(oldTree.getGroupKey())) {
            this.checkDataDefine(treeDO);
            if (treeDO.getGroupKey().equals("00000000-0000-0000-0000-000000000000") && !this.systemIdentityService.isAdmin()) {
                throw new DataResourceException(DataResourceEnum.DATA_RESOURCE_DG_3_5.getMessage());
            }
        } else {
            this.checkDataDefineWithoutAuth(treeDO);
        }
        this.defineDao.update(treeDO);
    }

    @Override
    public String copy(DataResourceDefine entity, String sourceKey) {
        Assert.notNull((Object)entity, "entity must not be null.");
        if (!this.auth.canRead(sourceKey, NodeType.TREE.getValue())) {
            throw new DataResourceException(DataResourceEnum.DATA_RESOURCE_DG_3_4.getMessage());
        }
        ResourceTreeDO dg2Do = DataResourceConvert.iDd2Do(entity);
        dg2Do.setOrder(OrderGenerator.newOrder());
        if (dg2Do.getKey().equals(sourceKey)) {
            dg2Do.setKey(UUIDUtils.getKey());
        }
        if (dg2Do.getGroupKey().equals("00000000-0000-0000-0000-000000000000") && !this.systemIdentityService.isAdmin()) {
            throw new DataResourceException(DataResourceEnum.DATA_RESOURCE_DG_3_5.getMessage());
        }
        this.checkDataDefine(dg2Do);
        List<DataResource> oldResource = this.dataResourceService.getByDefineKey(sourceKey);
        ArrayList<DataResourceDO> newResources = new ArrayList<DataResourceDO>();
        HashMap<String, String> keyMap = new HashMap<String, String>();
        for (DataResource dataResource : oldResource) {
            String oldKey = dataResource.getKey();
            dataResource.setKey(UUIDUtils.getKey());
            dataResource.setResourceDefineKey(dg2Do.getKey());
            keyMap.put(oldKey, dataResource.getKey());
            newResources.add(DataResourceConvert.iDr2Do(dataResource));
        }
        for (DataResource dataResource : newResources) {
            if (!keyMap.containsKey(dataResource.getParentKey())) continue;
            dataResource.setParentKey((String)keyMap.get(dataResource.getParentKey()));
        }
        List<DataResourceLinkDO> oldLinks = this.linkDao.getByDefineKey(sourceKey);
        ArrayList<DataResourceLinkDO> arrayList = new ArrayList<DataResourceLinkDO>();
        for (DataResourceLinkDO link : oldLinks) {
            if (!keyMap.containsKey(link.getGroupKey())) continue;
            link.setResourceDefineKey(dg2Do.getKey());
            link.setGroupKey((String)keyMap.get(link.getGroupKey()));
            arrayList.add(link);
        }
        List<DimAttributeDO> dimAttributeDOS = this.attributeDao.getByDefineKey(sourceKey);
        for (DimAttributeDO dimAttributeDO : dimAttributeDOS) {
            dimAttributeDO.setResourceDefineKey(dg2Do.getKey());
        }
        this.resourceDao.insert(newResources);
        this.linkDao.insert(arrayList);
        this.attributeDao.insert(dimAttributeDOS);
        return this.defineDao.insert(dg2Do);
    }

    @Override
    @Transactional(readOnly=true)
    public DataResourceDefine get(String key) {
        Assert.notNull((Object)key, "key must not be null.");
        ResourceTreeDO resourceTreeDO = (ResourceTreeDO)this.defineDao.get(key);
        if (resourceTreeDO != null && this.auth.canRead(resourceTreeDO.getKey(), NodeType.TREE.getValue())) {
            return resourceTreeDO;
        }
        return null;
    }

    @Override
    public String[] insert(List<DataResourceDefine> entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(List<String> keys) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void update(List<DataResourceDefine> resourceTrees) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Transactional(readOnly=true)
    public List<DataResourceDefine> get(List<String> keys) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Transactional(readOnly=true)
    public List<DataResourceDefine> getByGroupKey(String key) {
        Assert.notNull((Object)key, "key must not be null.");
        List<ResourceTreeDO> byResourceGroupKey = this.defineDao.getByResourceGroupKey(key);
        byResourceGroupKey = byResourceGroupKey.stream().filter(t -> this.auth.canRead(t.getKey(), NodeType.TREE.getValue())).collect(Collectors.toList());
        return new ArrayList<DataResourceDefine>(byResourceGroupKey);
    }

    @Override
    public List<DataResourceDefine> fuzzySearch(String fuzzyKey) {
        List<ResourceTreeDO> trees = this.defineDao.fuzzySearch(fuzzyKey);
        trees = trees.stream().filter(t -> this.auth.canRead(t.getKey(), NodeType.TREE.getValue())).collect(Collectors.toList());
        return new ArrayList<DataResourceDefine>(trees);
    }

    @Override
    public void deleteByGroupKey(String key) {
        Assert.notNull((Object)key, "key must not be null.");
        throw new UnsupportedOperationException();
    }

    @Override
    @Nullable
    @Transactional(readOnly=true)
    public DimType determineByDimKey(@NonNull String defineKey, @NonNull String dimKey) {
        Assert.notNull((Object)defineKey, "defineKey must not be null.");
        Assert.notNull((Object)dimKey, "dimKey must not be null.");
        ResourceTreeDO treeDO = (ResourceTreeDO)this.defineDao.get(defineKey);
        if (treeDO == null) {
            return null;
        }
        String unitDim = treeDO.getDimKey();
        if (dimKey.equals(unitDim)) {
            return DimType.UNIT;
        }
        boolean periodEntity = this.periodEngineService.getPeriodAdapter().isPeriodEntity(dimKey);
        if (periodEntity) {
            return DimType.PERIOD;
        }
        IEntityDefine define = this.entityMetaService.queryEntity(dimKey);
        if (define == null) {
            return null;
        }
        IEntityDefine unitDefine = this.entityMetaService.queryEntity(unitDim);
        if (unitDefine == null) {
            return null;
        }
        String dimensionName = unitDefine.getDimensionName();
        if (dimensionName == null) {
            return null;
        }
        if (dimensionName.equals(define.getDimensionName())) {
            return DimType.UNIT;
        }
        return DimType.DIMENSION;
    }
}

