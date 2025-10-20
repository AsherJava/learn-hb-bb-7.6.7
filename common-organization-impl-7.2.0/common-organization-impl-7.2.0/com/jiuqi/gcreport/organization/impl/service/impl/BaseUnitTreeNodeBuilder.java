/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.organization.api.vo.OrgDataParam
 *  com.jiuqi.gcreport.organization.api.vo.OrgDataVO
 */
package com.jiuqi.gcreport.organization.impl.service.impl;

import com.jiuqi.gcreport.organization.api.vo.OrgDataParam;
import com.jiuqi.gcreport.organization.api.vo.OrgDataVO;
import com.jiuqi.gcreport.organization.impl.bean.OrgDataDO;
import com.jiuqi.gcreport.organization.impl.bean.OrgDisplaySchemeDO;
import com.jiuqi.gcreport.organization.impl.extend.UnitTreeNodeBuilder;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class BaseUnitTreeNodeBuilder
implements UnitTreeNodeBuilder {
    @Override
    public OrgDataVO buildTreeNode(OrgDataParam param, OrgDataDO orgDataDO, OrgDisplaySchemeDO displayScheme) {
        OrgDataVO orgDataVO = new OrgDataVO();
        orgDataVO.setKey(orgDataDO.getKey());
        orgDataVO.setCode(orgDataDO.getCode());
        orgDataVO.setTitle(orgDataDO.getName());
        orgDataVO.setLeaf(orgDataDO.isLeaf());
        orgDataVO.setParentcode(orgDataDO.getParentcode());
        orgDataVO.setParents(orgDataDO.getParents());
        orgDataVO.setOrdinal(Objects.toString(orgDataDO.getOrdinal(), ""));
        orgDataVO.setStopFlag(orgDataDO.isStopflag());
        orgDataVO.setRecoveryFlag(orgDataDO.isRecoveryflag());
        orgDataVO.setShowTitle(this.getShowTitle(param, orgDataDO, displayScheme));
        return orgDataVO;
    }

    private String getShowTitle(OrgDataParam param, OrgDataDO orgDataDO, OrgDisplaySchemeDO displayScheme) {
        if (displayScheme == null || displayScheme.getFields() == null || displayScheme.getFields().isEmpty()) {
            return "";
        }
        return displayScheme.getFields().stream().map(field -> {
            Object fieldValue = orgDataDO.getFieldVal((String)field);
            return fieldValue == null ? "" : fieldValue.toString();
        }).filter(value -> !value.isEmpty()).collect(Collectors.joining(" | "));
    }
}

