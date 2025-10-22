/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.va.domain.org.OrgCategoryDO
 *  com.jiuqi.va.domain.org.ZB
 *  com.jiuqi.va.organization.service.OrgCategoryService
 */
package com.jiuqi.gcreport.org.impl.init;

import com.fasterxml.jackson.core.type.TypeReference;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.gcreport.org.impl.init.impl.OrgTypeTempObj;
import com.jiuqi.va.domain.org.OrgCategoryDO;
import com.jiuqi.va.domain.org.ZB;
import com.jiuqi.va.organization.service.OrgCategoryService;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GcOrgAdjustTypeFieldProcessor {
    @Autowired
    private OrgCategoryService orgCategoryService;
    private static final String ORG_ADJTYPEIDS = "ORG_ADJTYPEIDS";

    public void addAdjTypeField(List<OrgTypeTempObj> types, Map<String, String> orgFields) {
        Optional<OrgTypeTempObj> orgCorporateTypeOptional = types.stream().filter(type -> "MD_ORG_CORPORATE".equals(type.getCode())).findFirst();
        Optional<OrgTypeTempObj> orgOptional = types.stream().filter(type -> "MD_ORG".equals(type.getCode())).findFirst();
        if (orgOptional.isPresent() && orgCorporateTypeOptional.isPresent() && !orgFields.isEmpty()) {
            OrgTypeTempObj orgTypeTempObj = orgOptional.get();
            OrgTypeTempObj orgCorporateType = orgCorporateTypeOptional.get();
            String adjTypeZbs = orgFields.get(ORG_ADJTYPEIDS);
            boolean corporateAdjTypeFieldFlag = this.isExistAdjTypeField("MD_ORG_CORPORATE", orgCorporateType);
            boolean orgAdjTypeFieldFlag = this.isExistAdjTypeField("MD_ORG", orgTypeTempObj);
            if (corporateAdjTypeFieldFlag && !orgAdjTypeFieldFlag) {
                this.addOrgAdjTypeField(orgTypeTempObj, adjTypeZbs);
            }
        }
    }

    private boolean isExistAdjTypeField(String orgTableName, OrgTypeTempObj typeTempObj) {
        List zbs;
        OrgCategoryDO orgCategoryDO = new OrgCategoryDO();
        orgCategoryDO.setTenantName("__default_tenant__");
        orgCategoryDO.setName(orgTableName);
        orgCategoryDO.setVersionflag(Integer.valueOf(typeTempObj.isMutliversion() ? 1 : 0));
        OrgCategoryDO OrgCategoryDO2 = this.orgCategoryService.get(orgCategoryDO);
        return orgCategoryDO != null && !CollectionUtils.isEmpty(zbs = OrgCategoryDO2.getAllZbs().stream().filter(zb -> "ADJTYPEIDS".equals(zb.getName())).collect(Collectors.toList()));
    }

    private void addOrgAdjTypeField(OrgTypeTempObj orgTypeTempObj, String adjTypeZbs) {
        OrgCategoryDO orgCategoryDO = new OrgCategoryDO();
        orgCategoryDO.setTenantName("__default_tenant__");
        orgCategoryDO.setName("MD_ORG");
        orgCategoryDO.setVersionflag(Integer.valueOf(orgTypeTempObj.isMutliversion() ? 1 : 0));
        OrgCategoryDO oldOrgCategoryDO = this.orgCategoryService.get(orgCategoryDO);
        boolean changed = this.syncZb(oldOrgCategoryDO, adjTypeZbs);
        if (changed) {
            this.orgCategoryService.update(oldOrgCategoryDO);
        }
    }

    private boolean syncZb(OrgCategoryDO org, String solidZbs) {
        boolean changed = false;
        List newZbs = (List)JsonUtils.readValue((String)solidZbs, (TypeReference)new TypeReference<List<ZB>>(){});
        for (ZB z : newZbs) {
            ZB oldZ = org.getZbByName(z.getName());
            if (oldZ == null) {
                org.syncZb(z);
                changed = true;
                continue;
            }
            if (oldZ.getPrecision() >= z.getPrecision()) continue;
            oldZ.setPrecision(z.getPrecision());
            changed = true;
        }
        return changed;
    }
}

