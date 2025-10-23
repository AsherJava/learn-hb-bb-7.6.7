/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.nr.quantity.bean.QuantityCategory
 *  com.jiuqi.nr.quantity.bean.QuantityInfo
 *  com.jiuqi.nr.quantity.bean.QuantityUnit
 *  com.jiuqi.nr.quantity.service.IQuantityService
 *  com.jiuqi.nvwa.resourceview.query.IResourceDataProvider
 *  com.jiuqi.nvwa.resourceview.query.ResourceData
 *  com.jiuqi.nvwa.resourceview.query.ResourceGroup
 */
package com.jiuqi.nr.resourceview.quantity.provider;

import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.nr.quantity.bean.QuantityCategory;
import com.jiuqi.nr.quantity.bean.QuantityInfo;
import com.jiuqi.nr.quantity.bean.QuantityUnit;
import com.jiuqi.nr.quantity.service.IQuantityService;
import com.jiuqi.nr.resourceview.quantity.util.QuantityConvert;
import com.jiuqi.nvwa.resourceview.query.IResourceDataProvider;
import com.jiuqi.nvwa.resourceview.query.ResourceData;
import com.jiuqi.nvwa.resourceview.query.ResourceGroup;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class QuantityProvider
implements IResourceDataProvider {
    private final Logger logger = LoggerFactory.getLogger(QuantityProvider.class);
    @Autowired
    private IQuantityService quantityService;

    public List<ResourceGroup> getGroupsForTree(String parentId) {
        try {
            if (parentId.startsWith("QI")) {
                return this.quantityService.getQuantityCategoryByQuanId(QuantityConvert.getRealId(parentId)).stream().map(qc -> QuantityConvert.RD2RG(QuantityConvert.QC2RD(qc))).collect(Collectors.toList());
            }
        }
        catch (JQException e) {
            this.logger.error(e.getMessage(), e);
        }
        return Collections.emptyList();
    }

    public List<ResourceGroup> getGroupsForTable(String parentId) {
        return Collections.emptyList();
    }

    public List<ResourceGroup> getRootGroupsForTree() {
        try {
            return this.quantityService.getAllQuantityInfo().stream().map(qi -> QuantityConvert.RD2RG(QuantityConvert.QI2RD(qi))).collect(Collectors.toList());
        }
        catch (JQException e) {
            this.logger.error(e.getMessage(), e);
            return null;
        }
    }

    public List<ResourceGroup> getRootGroupsForTable() {
        return Collections.emptyList();
    }

    public List<ResourceData> getResourceDatasForTree(String groupId) {
        return Collections.emptyList();
    }

    public List<ResourceData> getResourceDatasForTable(String groupId) {
        try {
            if (groupId.startsWith("QI")) {
                return this.quantityService.getQuantityCategoryByQuanId(QuantityConvert.getRealId(groupId)).stream().map(QuantityConvert::QC2RD).collect(Collectors.toList());
            }
            if (groupId.startsWith("QC")) {
                return this.quantityService.getQuantityUnitByCategoryId(QuantityConvert.getRealId(groupId)).stream().map(QuantityConvert::QU2RD).collect(Collectors.toList());
            }
        }
        catch (JQException e) {
            this.logger.error(e.getMessage(), e);
        }
        return Collections.emptyList();
    }

    public List<ResourceData> getRootResourceDatasForTable() {
        try {
            return this.quantityService.getAllQuantityInfo().stream().map(QuantityConvert::QI2RD).collect(Collectors.toList());
        }
        catch (JQException e) {
            this.logger.error(e.getMessage(), e);
            return Collections.emptyList();
        }
    }

    public List<ResourceData> getRootResourceDatasForTree() {
        return Collections.emptyList();
    }

    public ResourceGroup getGroup(String groupId) {
        ResourceData rd = this.getResourceData(groupId);
        if (rd != null) {
            return QuantityConvert.RD2RG(rd);
        }
        return null;
    }

    public ResourceData getResourceData(String resourceId) {
        try {
            QuantityUnit qu;
            if (resourceId.startsWith("QI")) {
                QuantityInfo qi = this.quantityService.getQuantityInfoById(QuantityConvert.getRealId(resourceId));
                if (qi != null) {
                    return QuantityConvert.QI2RD(qi);
                }
            } else if (resourceId.startsWith("QC")) {
                QuantityCategory qc = this.quantityService.getQuantityCategoryById(QuantityConvert.getRealId(resourceId));
                if (qc != null) {
                    return QuantityConvert.QC2RD(qc);
                }
            } else if (resourceId.startsWith("QU") && (qu = this.quantityService.getQuantityUnitById(QuantityConvert.getRealId(resourceId))) != null) {
                return QuantityConvert.QU2RD(qu);
            }
        }
        catch (JQException e) {
            this.logger.error(e.getMessage(), e);
        }
        return null;
    }

    public boolean hasResourceRootGroup() {
        return true;
    }
}

