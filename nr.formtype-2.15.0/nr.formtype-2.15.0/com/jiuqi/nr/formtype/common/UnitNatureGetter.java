/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.org.OrgDO
 *  com.jiuqi.va.domain.org.ZB
 */
package com.jiuqi.nr.formtype.common;

import com.jiuqi.nr.formtype.common.FormTypeUtils;
import com.jiuqi.nr.formtype.common.UnitNature;
import com.jiuqi.nr.formtype.facade.FormTypeDataDefine;
import com.jiuqi.nr.formtype.service.IFormTypeApplyService;
import com.jiuqi.va.domain.org.OrgDO;
import com.jiuqi.va.domain.org.ZB;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.util.StringUtils;

public class UnitNatureGetter {
    private Map<String, FormTypeDataDefine> formTypeDataMap;
    private List<FormTypeDataDefine> needAddFormTypes;
    private IFormTypeApplyService iFormTypeApplyService;

    public UnitNatureGetter(IFormTypeApplyService iFormTypeApplyService, List<FormTypeDataDefine> allFormTypes) {
        this.iFormTypeApplyService = iFormTypeApplyService;
        this.formTypeDataMap = new HashMap<String, FormTypeDataDefine>();
        this.needAddFormTypes = new ArrayList<FormTypeDataDefine>();
        ArrayList<UnitNature> needAddUnitNatures = new ArrayList<UnitNature>();
        for (FormTypeDataDefine formType : allFormTypes) {
            this.formTypeDataMap.put(formType.getCode(), formType);
            if (UnitNature.JCFHB != formType.getUnitNatrue() && UnitNature.JTCEB != formType.getUnitNatrue() || needAddUnitNatures.contains((Object)formType.getUnitNatrue())) continue;
            needAddUnitNatures.add(formType.getUnitNatrue());
            this.needAddFormTypes.add(formType);
        }
    }

    public List<FormTypeDataDefine> getNeedAddFormTypes() {
        return this.needAddFormTypes;
    }

    public UnitNature getUnitNature(String formTypeDataCode) {
        FormTypeDataDefine formTypeData = this.formTypeDataMap.get(formTypeDataCode);
        if (null == formTypeData) {
            return null;
        }
        return formTypeData.getUnitNatrue();
    }

    public UnitNature getUnitNature(Map<String, Object> orgData, String formTypeFieldName) {
        Object object = orgData.get(formTypeFieldName);
        if (null != object) {
            return this.getUnitNature(object.toString());
        }
        return null;
    }

    public UnitNature getUnitNature(OrgDO orgData, ZB formTypeZb) {
        return this.getUnitNature(FormTypeUtils.getZbValue(orgData, formTypeZb));
    }

    public boolean isAutoGenUnitCode(Map<String, Object> orgData, String codeName, String parentName, String bblxName) {
        String codeValue = (String)orgData.get(codeName);
        String bblxValue = (String)orgData.get(bblxName);
        String parentValue = (String)orgData.get(parentName);
        if (!(StringUtils.hasText(codeValue) && StringUtils.hasText(parentValue) && StringUtils.hasText(bblxValue))) {
            return false;
        }
        FormTypeDataDefine formTypeDataDefine = this.formTypeDataMap.get(bblxValue);
        if (null == formTypeDataDefine) {
            return false;
        }
        String autoGenUnitCode = this.iFormTypeApplyService.getAutoGenUnitCode(parentValue, formTypeDataDefine.getUnitNatrue());
        return codeValue.equals(autoGenUnitCode);
    }

    public boolean isAutoGenUnitCode(OrgDO orgData, ZB formTypeZb) {
        String autoGenUnitCode;
        if (!StringUtils.hasText(orgData.getParentcode())) {
            return false;
        }
        UnitNature unitNature = this.getUnitNature(orgData, formTypeZb);
        return (UnitNature.JTCEB == unitNature || UnitNature.JCFHB == unitNature) && (autoGenUnitCode = this.iFormTypeApplyService.getAutoGenUnitCode(orgData.getParentcode(), unitNature)).equals(orgData.getCode());
    }
}

