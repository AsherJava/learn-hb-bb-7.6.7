/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.enumdata.EnumDataDO
 *  com.jiuqi.va.domain.enumdata.EnumDataDTO
 *  com.jiuqi.va.i18n.domain.VaI18nResourceItem
 *  com.jiuqi.va.i18n.intf.VaI18nModuleIntf
 */
package com.jiuqi.va.basedata.domain;

import com.jiuqi.va.basedata.service.EnumDataService;
import com.jiuqi.va.domain.enumdata.EnumDataDO;
import com.jiuqi.va.domain.enumdata.EnumDataDTO;
import com.jiuqi.va.i18n.domain.VaI18nResourceItem;
import com.jiuqi.va.i18n.intf.VaI18nModuleIntf;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class EnumDataI18nModule
extends VaI18nModuleIntf {
    private static final long serialVersionUID = 1L;
    @Autowired
    private EnumDataService enumDataService;

    public String getName() {
        return "enumdata";
    }

    public String getTitle() {
        return "\u679a\u4e3e";
    }

    public String getGroupName() {
        return "VA";
    }

    public List<VaI18nResourceItem> getCategory(String parentId) {
        List<EnumDataDO> enumeDataList;
        if (!StringUtils.hasText(parentId)) {
            return null;
        }
        if (parentId.split("#").length < 2) {
            return null;
        }
        ArrayList<VaI18nResourceItem> resourceList = new ArrayList<VaI18nResourceItem>();
        if (parentId.endsWith("#enumdata") && null != (enumeDataList = this.enumDataService.listBiztype(new EnumDataDTO())) && !enumeDataList.isEmpty()) {
            for (EnumDataDO enumData : enumeDataList) {
                VaI18nResourceItem item = new VaI18nResourceItem();
                item.setName(enumData.getBiztype());
                item.setTitle(enumData.getDescription());
                item.setCategoryFlag(true);
                resourceList.add(item);
            }
        }
        return resourceList;
    }

    public List<VaI18nResourceItem> getResource(String parentId) {
        if (!StringUtils.hasText(parentId)) {
            return null;
        }
        if (parentId.split("#").length < 2) {
            return null;
        }
        ArrayList<VaI18nResourceItem> resourceList = new ArrayList<VaI18nResourceItem>();
        if (parentId.endsWith("#enumdata")) {
            List<EnumDataDO> enumeDataList = this.enumDataService.listBiztype(new EnumDataDTO());
            if (null != enumeDataList && !enumeDataList.isEmpty()) {
                for (EnumDataDO enumData : enumeDataList) {
                    VaI18nResourceItem item = new VaI18nResourceItem();
                    item.setName(enumData.getBiztype());
                    item.setTitle(enumData.getDescription());
                    item.setCategoryFlag(true);
                    resourceList.add(item);
                }
            }
            return resourceList;
        }
        String[] parents = parentId.split("#");
        EnumDataDTO enumDataDTO = new EnumDataDTO();
        enumDataDTO.setBiztype(parents[parents.length - 1]);
        List<EnumDataDO> listEnumData = this.enumDataService.list(enumDataDTO);
        for (EnumDataDO enumDataDO : listEnumData) {
            VaI18nResourceItem showColItemResource = new VaI18nResourceItem();
            showColItemResource.setName(enumDataDO.getVal());
            showColItemResource.setTitle(enumDataDO.getTitle());
            resourceList.add(showColItemResource);
        }
        return resourceList;
    }
}

