/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.common.EntityUtils
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 */
package com.jiuqi.nr.entity.service.impl;

import com.jiuqi.np.definition.common.EntityUtils;
import com.jiuqi.nr.entity.adapter.IEntityAdapter;
import com.jiuqi.nr.entity.bo.EntitySearchBO;
import com.jiuqi.nr.entity.common.AssertException;
import com.jiuqi.nr.entity.common.Utils;
import com.jiuqi.nr.entity.exception.IllegalReferException;
import com.jiuqi.nr.entity.ext.filter.IEntityReferFilter;
import com.jiuqi.nr.entity.internal.model.impl.EntityDefineImpl;
import com.jiuqi.nr.entity.internal.model.impl.EntityGroupImpl;
import com.jiuqi.nr.entity.internal.model.impl.EntityModelImpl;
import com.jiuqi.nr.entity.internal.model.impl.EntityReferImpl;
import com.jiuqi.nr.entity.internal.service.AdapterService;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.model.IEntityGroup;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.model.IEntityRefer;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

public class EntityMetaServiceImpl
implements IEntityMetaService {
    private static final Logger logger = LoggerFactory.getLogger(EntityMetaServiceImpl.class);
    @Autowired
    private AdapterService adapterService;
    @Autowired
    private IEntityReferFilter entityReferFilter;

    @Override
    public List<IEntityGroup> getRootEntityGroups() {
        List<IEntityAdapter> adapterList = this.adapterService.getAdapterList();
        ArrayList<IEntityGroup> groups = new ArrayList<IEntityGroup>(adapterList.size());
        for (IEntityAdapter entityAdapter : adapterList) {
            if (!entityAdapter.enableSelect()) continue;
            groups.add(this.convertAdapterToGroup(entityAdapter));
        }
        return groups;
    }

    private IEntityGroup convertAdapterToGroup(IEntityAdapter adapter) {
        EntityGroupImpl group = new EntityGroupImpl();
        group.setId(Utils.generatorGroupId("29b66bd9-e6fc-49f1-8e6a-a5c45e455f11", adapter.getId()));
        group.setTitle(adapter.getTitle());
        return group;
    }

    @Override
    public List<IEntityGroup> getChildrenGroup(String groupId) {
        Assert.notNull((Object)groupId, AssertException.ILLEGAL_ARGUMENT.getMessage("groupId"));
        ArrayList<IEntityGroup> groups = new ArrayList<IEntityGroup>(16);
        IEntityAdapter adapterByCategory = this.adapterService.getGroupAdapter(groupId);
        if (!adapterByCategory.enableSelect()) {
            return groups;
        }
        String realId = Utils.getGroupId(groupId);
        if (realId.equals("29b66bd9-e6fc-49f1-8e6a-a5c45e455f11")) {
            List<IEntityGroup> rootEntityGroups = adapterByCategory.getRootEntityGroups();
            if (!CollectionUtils.isEmpty(rootEntityGroups)) {
                List groupList = rootEntityGroups.stream().map(e -> {
                    EntityGroupImpl group = (EntityGroupImpl)e;
                    group.setId(Utils.generatorGroupId(e.getId(), adapterByCategory.getId()));
                    group.setParentId(groupId);
                    return group;
                }).collect(Collectors.toList());
                groups.addAll(groupList);
            }
        } else {
            List<IEntityGroup> childrenEntityGroups = adapterByCategory.getChildrenEntityGroups(realId);
            if (!CollectionUtils.isEmpty(childrenEntityGroups)) {
                List groupList = childrenEntityGroups.stream().map(e -> {
                    EntityGroupImpl group = (EntityGroupImpl)e;
                    group.setId(Utils.generatorGroupId(e.getId(), adapterByCategory.getId()));
                    group.setParentId(Utils.generatorGroupId(e.getParentId(), adapterByCategory.getId()));
                    return group;
                }).collect(Collectors.toList());
                groups.addAll(groupList);
            }
        }
        return groups;
    }

    @Override
    public List<IEntityDefine> getEntitiesInGroup(String groupId) {
        Assert.notNull((Object)groupId, AssertException.ILLEGAL_ARGUMENT.getMessage("groupId"));
        IEntityAdapter groupAdapter = this.adapterService.getGroupAdapter(groupId);
        if (!groupAdapter.enableSelect()) {
            return null;
        }
        List<IEntityDefine> entitiesInGroup = groupAdapter.getEntitiesInGroup(Utils.getGroupId(groupId));
        if (!CollectionUtils.isEmpty(entitiesInGroup)) {
            return entitiesInGroup.stream().map(e -> this.convertEntity(groupAdapter.getId(), (IEntityDefine)e)).collect(Collectors.toList());
        }
        return null;
    }

    @Override
    public List<IEntityDefine> getEntities(int cumulative) {
        ArrayList<IEntityDefine> entityDefines = new ArrayList<IEntityDefine>();
        List<IEntityAdapter> adapterList = this.adapterService.getAdapterList();
        for (IEntityAdapter entityAdapter : adapterList) {
            List<IEntityDefine> entities;
            if (!entityAdapter.enableSelect() || CollectionUtils.isEmpty(entities = entityAdapter.getEntities(cumulative))) continue;
            entityDefines.addAll(this.convertEntities(entities, entityAdapter.getId()));
        }
        return entityDefines;
    }

    private List<IEntityDefine> convertEntities(List<IEntityDefine> entities, String category) {
        return entities.stream().map(e -> this.convertEntity(category, (IEntityDefine)e)).collect(Collectors.toList());
    }

    private EntityDefineImpl convertEntity(String category, IEntityDefine entityDefine) {
        EntityDefineImpl impl = (EntityDefineImpl)entityDefine;
        impl.setId(EntityUtils.getEntityId((String)entityDefine.getId(), (String)category));
        return impl;
    }

    @Override
    public IEntityDefine queryEntity(String entityId) {
        Assert.notNull((Object)entityId, AssertException.ILLEGAL_ARGUMENT.getMessage("entityId"));
        IEntityAdapter entityAdapter = this.adapterService.getEntityAdapter(entityId);
        if (entityAdapter == null) {
            return null;
        }
        IEntityDefine entity = entityAdapter.getEntity(EntityUtils.getId((String)entityId));
        if (entity != null) {
            return this.convertEntity(entityAdapter.getId(), entity);
        }
        return null;
    }

    @Override
    public IEntityDefine queryEntityByCode(String entityCode) {
        Assert.notNull((Object)entityCode, AssertException.ILLEGAL_ARGUMENT.getMessage("entityCode"));
        IEntityAdapter entityAdapter = this.adapterService.getEntityAdapterByCode(entityCode);
        if (entityAdapter == null) {
            return null;
        }
        IEntityDefine entity = entityAdapter.getEntityByCode(entityCode);
        if (entity != null) {
            return this.convertEntity(entityAdapter.getId(), entity);
        }
        return null;
    }

    @Override
    public IEntityDefine queryBaseTreeEntityBySubTreeEntityId(String subTreeEntityId) {
        Assert.notNull((Object)subTreeEntityId, AssertException.ILLEGAL_ARGUMENT.getMessage("subTreeEntityId"));
        IEntityAdapter entityAdapter = this.adapterService.getEntityAdapter(subTreeEntityId);
        if (entityAdapter == null) {
            return null;
        }
        IEntityDefine baseEntityBySubTreeEntityId = entityAdapter.getBaseEntityBySubTreeEntityId(EntityUtils.getId((String)subTreeEntityId));
        if (baseEntityBySubTreeEntityId != null) {
            return this.convertEntity(entityAdapter.getId(), baseEntityBySubTreeEntityId);
        }
        return null;
    }

    @Override
    public List<IEntityDefine> getSubTreeEntities(String baseTreeEntityId) {
        Assert.notNull((Object)baseTreeEntityId, AssertException.ILLEGAL_ARGUMENT.getMessage("baseTreeEntityId"));
        IEntityAdapter entityAdapter = this.adapterService.getEntityAdapter(baseTreeEntityId);
        if (entityAdapter == null) {
            return null;
        }
        List<IEntityDefine> subTreeEntities = entityAdapter.getSubTreeEntities(EntityUtils.getId((String)baseTreeEntityId));
        if (!CollectionUtils.isEmpty(subTreeEntities)) {
            return this.convertEntities(subTreeEntities, entityAdapter.getId());
        }
        return null;
    }

    @Override
    public List<IEntityDefine> fuzzySearchEntity(EntitySearchBO bo) {
        Assert.notNull((Object)bo.getKeyWords(), AssertException.ILLEGAL_ARGUMENT.getMessage("bo.getKeyWords()"));
        ArrayList<IEntityDefine> entityDefines = new ArrayList<IEntityDefine>();
        for (IEntityAdapter entityAdapter : this.adapterService.getAdapterList()) {
            List<IEntityDefine> searchEntities = this.getSearchEntities(bo, entityAdapter);
            entityDefines.addAll(searchEntities);
        }
        return entityDefines;
    }

    private List<IEntityDefine> getSearchEntities(EntitySearchBO bo, IEntityAdapter entityAdapter) {
        List<IEntityDefine> entityDefines = new ArrayList<IEntityDefine>(16);
        List<IEntityDefine> iEntityDefines = entityAdapter.fuzzySearchEntityByKeyWords(bo);
        if (!CollectionUtils.isEmpty(iEntityDefines)) {
            entityDefines = this.convertEntities(iEntityDefines, entityAdapter.getId());
        }
        return entityDefines;
    }

    @Override
    public TableModelDefine getTableModel(String entityId) {
        Assert.notNull((Object)entityId, AssertException.ILLEGAL_ARGUMENT.getMessage("entityId"));
        IEntityAdapter entityAdapter = this.adapterService.getEntityAdapter(entityId);
        if (entityAdapter == null) {
            return null;
        }
        return entityAdapter.getTableByEntityId(EntityUtils.getId((String)entityId));
    }

    @Override
    public List<IEntityRefer> getEntityRefer(String entityId) {
        Assert.notNull((Object)entityId, AssertException.ILLEGAL_ARGUMENT.getMessage("entityId"));
        IEntityAdapter entityAdapter = this.adapterService.getEntityAdapter(entityId);
        if (entityAdapter == null) {
            return null;
        }
        List<IEntityRefer> entityRefer = entityAdapter.getEntityRefer(EntityUtils.getId((String)entityId));
        ArrayList<IEntityRefer> refers = new ArrayList<IEntityRefer>();
        if (!CollectionUtils.isEmpty(entityRefer)) {
            List<IEntityRefer> filterAttribute = this.entityReferFilter.getFilterAttribute(entityRefer);
            if (filterAttribute == null) {
                return null;
            }
            for (IEntityRefer refer : filterAttribute) {
                EntityReferImpl impl = (EntityReferImpl)refer;
                impl.setOwnEntityId(EntityUtils.getEntityId((String)refer.getOwnEntityId(), (String)entityAdapter.getId()));
                IEntityDefine referEntity = this.queryEntity(refer.getReferEntityId());
                if (referEntity == null) {
                    throw new IllegalReferException(entityId, refer.getOwnField(), refer.getReferEntityId());
                }
                impl.setReferEntityId(referEntity.getId());
                refers.add(impl);
            }
        }
        return refers;
    }

    @Override
    public IEntityModel getEntityModel(String entityId) {
        Assert.notNull((Object)entityId, AssertException.ILLEGAL_ARGUMENT.getMessage("entityId"));
        IEntityAdapter entityAdapter = this.adapterService.getEntityAdapter(entityId);
        if (entityAdapter == null) {
            return null;
        }
        IEntityModel entityModel = entityAdapter.getEntityModel(EntityUtils.getId((String)entityId));
        if (entityModel != null) {
            EntityModelImpl impl = (EntityModelImpl)entityModel;
            impl.setEntityId(EntityUtils.getEntityId((String)entityModel.getEntityId(), (String)entityAdapter.getId()));
            return impl;
        }
        return null;
    }

    @Override
    public List<String> getPath(String entityId) {
        Assert.notNull((Object)entityId, AssertException.ILLEGAL_ARGUMENT.getMessage("entityId"));
        IEntityAdapter entityAdapter = this.adapterService.getEntityAdapter(entityId);
        if (entityAdapter == null) {
            return null;
        }
        List<String> path = entityAdapter.getPath(EntityUtils.getId((String)entityId));
        IEntityGroup rootGroup = this.convertAdapterToGroup(entityAdapter);
        if (CollectionUtils.isEmpty(path)) {
            path = new ArrayList<String>(1);
            path.add(rootGroup.getId());
            return path;
        }
        IEntityDefine entity = entityAdapter.getEntity(EntityUtils.getId((String)entityId));
        if (!CollectionUtils.isEmpty(path)) {
            path = path.stream().map(e -> {
                if (entity.getIncludeSubTreeEntity() == 1) {
                    return Utils.getEntityId(e, entityAdapter.getId());
                }
                return Utils.generatorGroupId(e, entityAdapter.getId());
            }).collect(Collectors.toList());
        }
        Collections.reverse(path);
        path.add(rootGroup.getId());
        Collections.reverse(path);
        return path;
    }

    @Override
    public boolean estimateEntityRefer(String firstEntityId, String secondEntityId) {
        Assert.notNull((Object)firstEntityId, AssertException.NULL_ARGUMENT.getMessage("firstEntityId"));
        Assert.notNull((Object)secondEntityId, AssertException.NULL_ARGUMENT.getMessage("secondEntityId"));
        if (firstEntityId.equals(secondEntityId)) {
            return false;
        }
        boolean hasRefer = this.matchReferEntity(firstEntityId, secondEntityId);
        if (!hasRefer) {
            hasRefer = this.matchReferEntity(secondEntityId, firstEntityId);
        }
        return hasRefer;
    }

    @Override
    public String getDimensionName(String entityId) {
        Assert.notNull((Object)entityId, AssertException.NULL_ARGUMENT.getMessage("entityId"));
        IEntityAdapter entityAdapter = this.adapterService.getEntityAdapter(entityId);
        if (entityAdapter == null) {
            return null;
        }
        return entityAdapter.getDimensionName(EntityUtils.getId((String)entityId));
    }

    @Override
    public String getEntityCode(String entityId) {
        Assert.notNull((Object)entityId, AssertException.NULL_ARGUMENT.getMessage("entityId"));
        IEntityAdapter entityAdapter = this.adapterService.getEntityAdapter(entityId);
        if (entityAdapter == null) {
            return null;
        }
        return entityAdapter.getEntityCode(EntityUtils.getId((String)entityId));
    }

    @Override
    public String getDimensionNameByCode(String entityCode) {
        Assert.notNull((Object)entityCode, AssertException.NULL_ARGUMENT.getMessage("entityCode"));
        IEntityAdapter entityAdapter = this.adapterService.getEntityAdapterByCode(entityCode);
        if (entityAdapter == null) {
            return null;
        }
        return entityAdapter.getDimensionNameByCode(entityCode);
    }

    @Override
    public String getEntityIdByCode(String entityCode) {
        Assert.notNull((Object)entityCode, AssertException.NULL_ARGUMENT.getMessage("entityCode"));
        IEntityAdapter entityAdapter = this.adapterService.getEntityAdapterByCode(entityCode);
        if (entityAdapter == null) {
            return null;
        }
        return EntityUtils.getEntityId((String)entityAdapter.getEntityIdByCode(entityCode), (String)entityAdapter.getId());
    }

    private boolean matchReferEntity(String firstEntityId, String secondEntityId) {
        boolean hasRefer = false;
        try {
            List<IEntityRefer> firstRefer = this.getEntityRefer(firstEntityId);
            if (!CollectionUtils.isEmpty(firstRefer)) {
                hasRefer = firstRefer.stream().anyMatch(e -> e.getReferEntityId().equals(secondEntityId));
            }
        }
        catch (Exception e2) {
            logger.error(e2.getMessage(), e2);
        }
        return hasRefer;
    }
}

