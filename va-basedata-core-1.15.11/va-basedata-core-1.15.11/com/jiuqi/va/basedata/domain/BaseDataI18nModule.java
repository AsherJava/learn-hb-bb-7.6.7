/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDO
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDTO
 *  com.jiuqi.va.domain.datamodel.DataModelColumn
 *  com.jiuqi.va.domain.datamodel.DataModelDO
 *  com.jiuqi.va.domain.datamodel.DataModelDTO
 *  com.jiuqi.va.feign.client.DataModelClient
 *  com.jiuqi.va.i18n.domain.VaI18nResourceItem
 *  com.jiuqi.va.i18n.intf.VaI18nModuleIntf
 */
package com.jiuqi.va.basedata.domain;

import com.jiuqi.va.basedata.service.BaseDataDefineService;
import com.jiuqi.va.domain.basedata.BaseDataDefineDO;
import com.jiuqi.va.domain.basedata.BaseDataDefineDTO;
import com.jiuqi.va.domain.datamodel.DataModelColumn;
import com.jiuqi.va.domain.datamodel.DataModelDO;
import com.jiuqi.va.domain.datamodel.DataModelDTO;
import com.jiuqi.va.feign.client.DataModelClient;
import com.jiuqi.va.i18n.domain.VaI18nResourceItem;
import com.jiuqi.va.i18n.intf.VaI18nModuleIntf;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class BaseDataI18nModule
extends VaI18nModuleIntf {
    private static final long serialVersionUID = 1L;
    @Autowired
    private BaseDataDefineService baseDataDefineService;
    @Autowired
    private DataModelClient dataModelClient;

    public String getName() {
        return "basedata";
    }

    public String getTitle() {
        return "\u57fa\u7840\u6570\u636e\u5b9a\u4e49";
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
        if (parentId.endsWith("#basedata")) {
            List defineList = this.baseDataDefineService.list(new BaseDataDefineDTO()).getRows();
            for (BaseDataDefineDO baseDataDefine : defineList) {
                VaI18nResourceItem defineItem = new VaI18nResourceItem();
                defineItem.setName("defines#" + baseDataDefine.getName());
                defineItem.setTitle(baseDataDefine.getTitle());
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
        if (parentId.endsWith("#basedata")) {
            List defineList = this.baseDataDefineService.list(new BaseDataDefineDTO()).getRows();
            for (BaseDataDefineDO baseDataDefine : defineList) {
                VaI18nResourceItem defineItem = new VaI18nResourceItem();
                defineItem.setName("defines#" + baseDataDefine.getName() + "#defineTitle#" + baseDataDefine.getName());
                defineItem.setTitle(baseDataDefine.getTitle());
                defineItem.setCategoryFlag(true);
                resourceList.add(defineItem);
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
        }
        return resourceList;
    }
}

