/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.blob.util.BeanUtil
 *  com.jiuqi.np.i18n.ext.I18NResource
 *  com.jiuqi.np.i18n.ext.I18NResourceItem
 */
package com.jiuqi.nr.dataresource.i18n;

import com.jiuqi.np.blob.util.BeanUtil;
import com.jiuqi.np.i18n.ext.I18NResource;
import com.jiuqi.np.i18n.ext.I18NResourceItem;
import com.jiuqi.nr.dataresource.DataResource;
import com.jiuqi.nr.dataresource.DataResourceKind;
import com.jiuqi.nr.dataresource.NodeType;
import com.jiuqi.nr.dataresource.dao.IDataResourceDao;
import com.jiuqi.nr.dataresource.dao.IDataResourceDefineDao;
import com.jiuqi.nr.dataresource.dao.IDataResourceDefineGroupDao;
import com.jiuqi.nr.dataresource.entity.DataResourceDO;
import com.jiuqi.nr.dataresource.entity.ResourceTreeDO;
import com.jiuqi.nr.dataresource.entity.ResourceTreeGroup;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Component
public class ResourceTreeI18NRegister
implements I18NResource {
    private static final long serialVersionUID = -4836566906680676894L;

    public String name() {
        return "\u65b0\u62a5\u8868/\u6307\u6807\u89c6\u56fe";
    }

    public String getNameSpace() {
        return "nr";
    }

    public List<I18NResourceItem> getCategory(String parentId) {
        return this.getResourceItems(parentId, true);
    }

    public List<I18NResourceItem> getResource(String parentId) {
        return this.getResourceItems(parentId, false);
    }

    private List<I18NResourceItem> getResourceItems(String parentId, boolean isCategory) {
        ArrayList<I18NResourceItem> resources;
        block6: {
            IDataResourceDao resourceDao;
            block7: {
                block5: {
                    IDataResourceDefineGroupDao groupDao = (IDataResourceDefineGroupDao)BeanUtil.getBean(IDataResourceDefineGroupDao.class);
                    IDataResourceDefineDao defineDao = (IDataResourceDefineDao)BeanUtil.getBean(IDataResourceDefineDao.class);
                    resourceDao = (IDataResourceDao)BeanUtil.getBean(IDataResourceDao.class);
                    resources = new ArrayList<I18NResourceItem>();
                    if (!StringUtils.hasText(parentId)) {
                        parentId = NodeType.TREE_GROUP.name() + "00000000-0000-0000-0000-000000000000";
                    }
                    if (!parentId.startsWith(NodeType.TREE_GROUP.name())) break block5;
                    parentId = parentId.substring(NodeType.TREE_GROUP.name().length());
                    List<ResourceTreeGroup> byParentKey = groupDao.getByParentNoI18N(parentId);
                    for (ResourceTreeGroup resourceTreeGroup : byParentKey) {
                        resources.add(new I18NResourceItem(NodeType.TREE_GROUP.name() + resourceTreeGroup.getKey(), resourceTreeGroup.getTitle(), isCategory));
                    }
                    List<ResourceTreeDO> byGroupKey = defineDao.getByResourceGroupKeyNoI18N(parentId);
                    for (ResourceTreeDO define : byGroupKey) {
                        resources.add(new I18NResourceItem(NodeType.TREE.name() + define.getKey(), define.getTitle(), isCategory));
                    }
                    break block6;
                }
                if (!parentId.startsWith(NodeType.TREE.name())) break block7;
                parentId = parentId.substring(NodeType.TREE.name().length());
                List<DataResourceDO> byParent = resourceDao.getByParentNoI18N(parentId, null);
                for (DataResource dataResource : byParent) {
                    this.buildResources(isCategory, dataResource, resources, resourceDao);
                }
                break block6;
            }
            if (!parentId.startsWith(NodeType.RESOURCE_GROUP.name())) break block6;
            parentId = parentId.substring(NodeType.RESOURCE_GROUP.name().length());
            List<DataResource> byParentKey = this.getByParentKey(parentId, resourceDao);
            for (DataResource dataResource : byParentKey) {
                this.buildResources(isCategory, dataResource, resources, resourceDao);
            }
        }
        return resources;
    }

    private void buildResources(boolean isCategory, DataResource resourceDO, List<I18NResourceItem> resources, IDataResourceDao resourceDao) {
        NodeType nodeType = NodeType.valueOf(resourceDO.getResourceKind().getValue());
        if (nodeType != NodeType.RESOURCE_GROUP) {
            return;
        }
        if (!isCategory) {
            resources.add(new I18NResourceItem(nodeType.name() + resourceDO.getKey(), resourceDO.getTitle()));
        } else {
            resources.add(new I18NResourceItem(nodeType.name() + resourceDO.getKey(), resourceDO.getTitle(), !CollectionUtils.isEmpty(this.getByParentKey(resourceDO.getKey(), resourceDao))));
        }
    }

    private List<DataResource> getByParentKey(String key, IDataResourceDao resourceDao) {
        DataResourceDO resourceDO = (DataResourceDO)resourceDao.get(key);
        if (resourceDO != null && resourceDO.getResourceKind() == DataResourceKind.RESOURCE_GROUP) {
            return new ArrayList<DataResource>(resourceDao.getByParentNoI18N(key));
        }
        return Collections.emptyList();
    }
}

