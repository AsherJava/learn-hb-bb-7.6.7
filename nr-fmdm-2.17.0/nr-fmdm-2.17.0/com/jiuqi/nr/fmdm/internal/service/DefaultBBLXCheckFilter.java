/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.entity.model.IEntityAttribute
 *  com.jiuqi.nr.entity.model.IEntityModel
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.formtype.common.UnitNature
 *  com.jiuqi.nr.formtype.common.UnitNatureGetter
 *  com.jiuqi.nr.formtype.org.extend.FormTypeOrgDataHelper
 *  com.jiuqi.nr.formtype.service.IFormTypeApplyService
 *  com.jiuqi.va.domain.org.OrgDO
 *  com.jiuqi.va.domain.org.ZB
 */
package com.jiuqi.nr.fmdm.internal.service;

import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.fmdm.service.ICheckFilter;
import com.jiuqi.nr.formtype.common.UnitNature;
import com.jiuqi.nr.formtype.common.UnitNatureGetter;
import com.jiuqi.nr.formtype.org.extend.FormTypeOrgDataHelper;
import com.jiuqi.nr.formtype.service.IFormTypeApplyService;
import com.jiuqi.va.domain.org.OrgDO;
import com.jiuqi.va.domain.org.ZB;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
public class DefaultBBLXCheckFilter
implements ICheckFilter {
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private IFormTypeApplyService iFormTypeApplyService;
    @Autowired
    private FormTypeOrgDataHelper formTypeOrgHelper;

    @Override
    public boolean check(Map<String, Object> data, String entityId, Date date) {
        if (!this.iFormTypeApplyService.enableNrFormTypeMgr()) {
            return Boolean.TRUE;
        }
        String formTypeCode = this.iFormTypeApplyService.getEntityFormTypeCode(entityId);
        if (!StringUtils.hasLength(formTypeCode) || !this.iFormTypeApplyService.isFormType(formTypeCode)) {
            return Boolean.TRUE;
        }
        IEntityModel entityModel = this.entityMetaService.getEntityModel(entityId);
        IEntityAttribute bblxField = entityModel.getBblxField();
        if (null == bblxField) {
            return Boolean.TRUE;
        }
        UnitNatureGetter unitNatureGetter = this.iFormTypeApplyService.getUnitNatureGetter(formTypeCode);
        String categoryname = this.entityMetaService.getEntityCode(entityModel.getEntityId());
        ZB formTypeZb = this.iFormTypeApplyService.getFormTypeZb(categoryname);
        return this.verifyDatas(categoryname, entityModel, formTypeZb, unitNatureGetter, data, date);
    }

    @Override
    public List<Boolean> check(List<Map<String, Object>> datas, String entityId, Date date) {
        ArrayList<Boolean> result = new ArrayList<Boolean>(datas.size());
        if (!this.iFormTypeApplyService.enableNrFormTypeMgr()) {
            return DefaultBBLXCheckFilter.allTrue(result, datas.size());
        }
        String formTypeCode = this.iFormTypeApplyService.getEntityFormTypeCode(entityId);
        if (!StringUtils.hasLength(formTypeCode) || !this.iFormTypeApplyService.isFormType(formTypeCode)) {
            return DefaultBBLXCheckFilter.allTrue(result, datas.size());
        }
        IEntityModel entityModel = this.entityMetaService.getEntityModel(entityId);
        IEntityAttribute bblxField = entityModel.getBblxField();
        if (null == bblxField) {
            return DefaultBBLXCheckFilter.allTrue(result, datas.size());
        }
        UnitNatureGetter unitNatureGetter = this.iFormTypeApplyService.getUnitNatureGetter(formTypeCode);
        String categoryname = this.entityMetaService.getEntityCode(entityModel.getEntityId());
        ZB formTypeZb = this.iFormTypeApplyService.getFormTypeZb(categoryname);
        for (Map<String, Object> data : datas) {
            result.add(this.verifyDatas(categoryname, entityModel, formTypeZb, unitNatureGetter, data, date));
        }
        return result;
    }

    public static List<Boolean> allTrue(List<Boolean> result, int size) {
        for (int i = 0; i < size; ++i) {
            result.add(Boolean.TRUE);
        }
        return result;
    }

    private boolean verifyDatas(String categoryname, IEntityModel entityModel, ZB formTypeZb, UnitNatureGetter unitNatureGetter, Map<String, Object> data, Date date) {
        String orgcodeValue = (String)data.get(entityModel.getCodeField().getName());
        if (!StringUtils.hasLength(orgcodeValue) || !data.containsKey(entityModel.getBizKeyField().getName())) {
            return Boolean.TRUE;
        }
        List oldList = this.formTypeOrgHelper.getOrgDataByOrgCodes(categoryname, date, Collections.singletonList(orgcodeValue));
        return this.verifyDatas(entityModel, formTypeZb, unitNatureGetter, data, oldList);
    }

    private boolean verifyDatas(IEntityModel entityModel, ZB formTypeZb, UnitNatureGetter unitNatureGetter, Map<String, Object> data, List<OrgDO> orgDatas) {
        if (CollectionUtils.isEmpty(orgDatas)) {
            return Boolean.TRUE;
        }
        String parentCode = (String)data.get(entityModel.getParentField().getName());
        if (!StringUtils.hasText(parentCode)) {
            return Boolean.TRUE;
        }
        IEntityAttribute codeField = entityModel.getBizKeyField();
        String codeValue = (String)data.get(codeField.getName());
        orgDatas.removeIf(o -> o.getCode().equals(codeValue));
        if (CollectionUtils.isEmpty(orgDatas)) {
            return Boolean.TRUE;
        }
        IEntityAttribute bblxField = entityModel.getBblxField();
        UnitNature unitNature = unitNatureGetter.getUnitNature((String)data.get(bblxField.getName()));
        String code = null;
        switch (unitNature) {
            case BZHZB: {
                return Boolean.TRUE;
            }
            case JCFHB: 
            case JTCEB: {
                code = parentCode;
                break;
            }
            case JTHZB: {
                code = (String)data.get(entityModel.getBizKeyField().getName());
                break;
            }
            default: {
                return Boolean.TRUE;
            }
        }
        ArrayList<String> canRepeatCodes = new ArrayList<String>();
        canRepeatCodes.add(code);
        canRepeatCodes.add(this.iFormTypeApplyService.getAutoGenUnitCode(code, UnitNature.JCFHB));
        canRepeatCodes.add(this.iFormTypeApplyService.getAutoGenUnitCode(code, UnitNature.JTCEB));
        for (OrgDO orgData : orgDatas) {
            if (canRepeatCodes.contains(orgData.getCode())) continue;
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }
}

