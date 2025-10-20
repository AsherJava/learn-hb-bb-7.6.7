/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.datamodel.DataModelColumn
 *  com.jiuqi.va.domain.datamodel.DataModelDO
 *  com.jiuqi.va.domain.datamodel.DataModelDTO
 *  com.jiuqi.va.domain.org.OrgCategoryDO
 *  com.jiuqi.va.feign.client.DataModelClient
 *  com.jiuqi.va.i18n.domain.VaI18nResourceItem
 *  com.jiuqi.va.i18n.intf.VaI18nModuleIntf
 */
package com.jiuqi.va.organization.domain;

import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.datamodel.DataModelColumn;
import com.jiuqi.va.domain.datamodel.DataModelDO;
import com.jiuqi.va.domain.datamodel.DataModelDTO;
import com.jiuqi.va.domain.org.OrgCategoryDO;
import com.jiuqi.va.feign.client.DataModelClient;
import com.jiuqi.va.i18n.domain.VaI18nResourceItem;
import com.jiuqi.va.i18n.intf.VaI18nModuleIntf;
import com.jiuqi.va.organization.service.OrgCategoryService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class OrgI18nModule
extends VaI18nModuleIntf {
    private static final long serialVersionUID = 1L;
    @Autowired
    private OrgCategoryService orgCategoryService;
    @Autowired
    private DataModelClient dataModelClient;

    public String getName() {
        return "orgdata";
    }

    public String getTitle() {
        return "\u7ec4\u7ec7\u673a\u6784\u7c7b\u578b";
    }

    public String getGroupName() {
        return "VA";
    }

    public List<VaI18nResourceItem> getCategory(String parentId) {
        if (!StringUtils.hasText(parentId)) {
            return null;
        }
        if (parentId.split("#").length < 2) {
            return null;
        }
        ArrayList<VaI18nResourceItem> resourceList = new ArrayList<VaI18nResourceItem>();
        if (parentId.endsWith("#orgdata")) {
            List categoryList = this.orgCategoryService.list(new OrgCategoryDO()).getRows();
            for (OrgCategoryDO category : categoryList) {
                VaI18nResourceItem defineItem = new VaI18nResourceItem();
                defineItem.setName("categorys#" + category.getName());
                defineItem.setTitle(category.getTitle());
                defineItem.setCategoryFlag(true);
                resourceList.add(defineItem);
            }
            return resourceList;
        }
        return null;
    }

    public List<VaI18nResourceItem> getResource(String parentId) {
        if (!StringUtils.hasText(parentId)) {
            return null;
        }
        if (parentId.split("#").length < 2) {
            return null;
        }
        ArrayList<VaI18nResourceItem> resourceList = new ArrayList<VaI18nResourceItem>();
        if (parentId.endsWith("#orgdata")) {
            PageVO<OrgCategoryDO> page = this.orgCategoryService.list(new OrgCategoryDO());
            for (OrgCategoryDO currCategory : page.getRows()) {
                VaI18nResourceItem showColItemResource = new VaI18nResourceItem();
                showColItemResource.setName("categorys#" + currCategory.getName() + "#categoryTitle#" + currCategory.getName());
                showColItemResource.setTitle(currCategory.getTitle());
                resourceList.add(showColItemResource);
            }
            return resourceList;
        }
        String[] nodes = parentId.split("#");
        String parentNode = nodes[nodes.length - 1];
        if (parentNode.startsWith("MD_")) {
            DataModelDTO datamodelParam = new DataModelDTO();
            datamodelParam.setName(parentNode);
            DataModelDO datamodel = this.dataModelClient.get(datamodelParam);
            if (datamodel == null || datamodel.getColumns() == null) {
                return resourceList;
            }
            for (DataModelColumn column : datamodel.getColumns()) {
                VaI18nResourceItem showColItemResource = new VaI18nResourceItem();
                showColItemResource.setName("showcol#" + column.getColumnName());
                showColItemResource.setTitle(column.getColumnTitle());
                resourceList.add(showColItemResource);
            }
            return resourceList;
        }
        return null;
    }
}

