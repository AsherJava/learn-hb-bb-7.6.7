/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.va.domain.org.OrgDO
 *  com.jiuqi.va.domain.org.ZB
 */
package com.jiuqi.nr.formtype.org.extend;

import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.nr.formtype.common.FormTypeUtils;
import com.jiuqi.nr.formtype.common.UnitNature;
import com.jiuqi.nr.formtype.common.UnitNatureGetter;
import com.jiuqi.nr.formtype.org.extend.FormTypeOrgDataHelper;
import com.jiuqi.nr.formtype.org.extend.exception.ExceptionConsumer;
import com.jiuqi.nr.formtype.org.extend.exception.FormTypeOrgExtendError;
import com.jiuqi.nr.formtype.org.extend.exception.FormTypeOrgExtendException;
import com.jiuqi.nr.formtype.service.IFormTypeApplyService;
import com.jiuqi.va.domain.org.OrgDO;
import com.jiuqi.va.domain.org.ZB;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Component
public class FormTypeOrgDataCheckService {
    @Autowired
    private IFormTypeApplyService iFormTypeApplyService;
    @Autowired
    private FormTypeOrgDataHelper formTypeOrgHelper;
    private final ExceptionConsumer<OrgDO, ErrorEnum> defaultExceptionConsumer = (orgData, error) -> {
        throw new FormTypeOrgExtendException((ErrorEnum)error);
    };

    protected static boolean skip(OrgDO orgData) {
        return Boolean.TRUE.equals(orgData.get((Object)"_ORG_EXTEND_CHECKED")) || orgData.containsKey((Object)"ignoreCategoryAdd") && (Boolean)orgData.get((Object)"ignoreCategoryAdd") != false;
    }

    protected boolean isAutoGenUnit(UnitNatureGetter unitNatureGetter, ZB formTypeZb, OrgDO org, OrgDO parentOrg) {
        if (null == parentOrg) {
            return false;
        }
        return UnitNature.JTHZB == unitNatureGetter.getUnitNature(parentOrg, formTypeZb) && org.getOrgcode().equals(parentOrg.getOrgcode()) && unitNatureGetter.isAutoGenUnitCode(org, formTypeZb);
    }

    public void doAddUnitCheck(ZB formTypeZb, List<OrgDO> orgDatas, ExceptionConsumer<OrgDO, ErrorEnum> errorConsumer) {
        if (null == errorConsumer) {
            errorConsumer = this.defaultExceptionConsumer;
        }
        UnitNatureGetter unitNatureGetter = this.iFormTypeApplyService.getUnitNatureGetter(formTypeZb.getReltablename());
        for (OrgDO unitData : orgDatas) {
            if (FormTypeOrgDataCheckService.skip(unitData)) continue;
            this.doAddUnitCheck(formTypeZb, unitNatureGetter, this.formTypeOrgHelper.getParentGetter(orgDatas), unitData, errorConsumer);
        }
    }

    public void doAddUnitCheck(ZB formTypeZb, OrgDO orgData, ExceptionConsumer<OrgDO, ErrorEnum> errorConsumer) {
        if (FormTypeOrgDataCheckService.skip(orgData)) {
            return;
        }
        if (null == errorConsumer) {
            errorConsumer = this.defaultExceptionConsumer;
        }
        UnitNatureGetter unitNatureGetter = this.iFormTypeApplyService.getUnitNatureGetter(formTypeZb.getReltablename());
        this.doAddUnitCheck(formTypeZb, unitNatureGetter, this.formTypeOrgHelper.getParentGetter(null), orgData, errorConsumer);
    }

    private void doAddUnitCheck(ZB formTypeZb, UnitNatureGetter unitNatureGetter, Function<OrgDO, OrgDO> parentGetter, OrgDO orgData, ExceptionConsumer<OrgDO, ErrorEnum> errorConsumer) {
        OrgDO parentOrgData;
        UnitNature unitNature = unitNatureGetter.getUnitNature(orgData, formTypeZb);
        if (null == unitNature) {
            errorConsumer.accept(orgData, FormTypeOrgExtendError.ERROR_NO_NATURE);
        }
        if (null == (parentOrgData = parentGetter.apply(orgData))) {
            if (UnitNature.JTCEB == unitNature) {
                errorConsumer.accept(orgData, FormTypeOrgExtendError.ERROR_AUTO_GEN_CE);
            }
            return;
        }
        UnitNature parentUnitNature = unitNatureGetter.getUnitNature(parentOrgData, formTypeZb);
        if (null == parentUnitNature) {
            errorConsumer.accept(orgData, FormTypeOrgExtendError.ERROR_NO_NATURE);
            return;
        }
        switch (parentUnitNature) {
            case JCFHB: 
            case JTCEB: {
                errorConsumer.accept(orgData, FormTypeOrgExtendError.ERROR_DISABLE_CHILDREN);
                break;
            }
            case BZHZB: {
                if (UnitNature.JTCEB != unitNature) break;
                errorConsumer.accept(orgData, FormTypeOrgExtendError.ERROR_DISABLE_CE);
                break;
            }
            case JTHZB: {
                if (this.isAutoGenUnit(unitNatureGetter, formTypeZb, orgData, parentOrgData)) {
                    errorConsumer.accept(orgData, FormTypeOrgExtendError.ERROR_ADD_GEN_DISABLE);
                    break;
                }
                if (UnitNature.JTCEB != unitNature) break;
                errorConsumer.accept(orgData, FormTypeOrgExtendError.ERROR_AUTO_GEN_CE);
                break;
            }
            default: {
                errorConsumer.accept(orgData, FormTypeOrgExtendError.ERROR_UNKNOWN_NATURE);
            }
        }
    }

    public void doUpdateUnitCheck(ZB formTypeZb, List<OrgDO> orgDatas, ExceptionConsumer<OrgDO, ErrorEnum> errorConsumer) {
        if (null == errorConsumer) {
            errorConsumer = this.defaultExceptionConsumer;
        }
        UnitNatureGetter unitNatureGetter = this.iFormTypeApplyService.getUnitNatureGetter(formTypeZb.getReltablename());
        for (OrgDO orgData : orgDatas) {
            if (FormTypeOrgDataCheckService.skip(orgData)) continue;
            OrgDO oldOrgData = this.formTypeOrgHelper.getOldOrgData(orgData);
            if (null == oldOrgData) {
                this.doAddUnitCheck(formTypeZb, unitNatureGetter, this.formTypeOrgHelper.getParentGetter(orgDatas), orgData, errorConsumer);
                continue;
            }
            this.doUpdateUnitCheck(formTypeZb, unitNatureGetter, this.formTypeOrgHelper.getParentGetter(orgDatas), this.formTypeOrgHelper.getChildrenGetter(orgDatas), orgData, oldOrgData, errorConsumer);
        }
    }

    public void doUpdateUnitCheck(ZB formTypeZb, OrgDO orgData, ExceptionConsumer<OrgDO, ErrorEnum> errorConsumer) {
        if (FormTypeOrgDataCheckService.skip(orgData)) {
            return;
        }
        if (null == errorConsumer) {
            errorConsumer = this.defaultExceptionConsumer;
        }
        UnitNatureGetter unitNatureGetter = this.iFormTypeApplyService.getUnitNatureGetter(formTypeZb.getReltablename());
        OrgDO oldOrgData = this.formTypeOrgHelper.getOldOrgData(orgData);
        if (null == oldOrgData) {
            this.doAddUnitCheck(formTypeZb, unitNatureGetter, this.formTypeOrgHelper.getParentGetter(null), orgData, errorConsumer);
        } else {
            this.doUpdateUnitCheck(formTypeZb, unitNatureGetter, this.formTypeOrgHelper.getParentGetter(null), this.formTypeOrgHelper.getChildrenGetter(null), orgData, oldOrgData, errorConsumer);
        }
    }

    private void doUpdateUnitCheck(ZB formTypeZb, UnitNatureGetter unitNatureGetter, Function<OrgDO, OrgDO> parentGetter, Function<OrgDO, List<OrgDO>> childrenGetter, OrgDO orgData, OrgDO oldOrgData, ExceptionConsumer<OrgDO, ErrorEnum> errorConsumer) {
        Optional<OrgDO> findAny;
        this.doCompInfo(orgData, oldOrgData, formTypeZb);
        UnitNature unitNatrue = unitNatureGetter.getUnitNature(orgData, formTypeZb);
        UnitNature oldunitNatrue = unitNatureGetter.getUnitNature(oldOrgData, formTypeZb);
        if (unitNatrue == oldunitNatrue && oldOrgData.getOrgcode().equals(orgData.getOrgcode()) && orgData.getParentcode().equals(oldOrgData.getParentcode())) {
            return;
        }
        boolean autoGenUnit = this.isAutoGenUnit(unitNatureGetter, formTypeZb, orgData, parentGetter.apply(orgData));
        boolean oldAutoGenUnit = this.isAutoGenUnit(unitNatureGetter, formTypeZb, oldOrgData, this.formTypeOrgHelper.getOldParentOrgData(oldOrgData));
        if (autoGenUnit && !oldAutoGenUnit) {
            errorConsumer.accept(orgData, FormTypeOrgExtendError.ERROR_ADD_GEN_DISABLE);
        } else if (oldAutoGenUnit) {
            errorConsumer.accept(orgData, FormTypeOrgExtendError.ERROR_AUTO_GEN_DISABLE);
        }
        if (UnitNature.JTCEB == unitNatrue) {
            errorConsumer.accept(orgData, FormTypeOrgExtendError.ERROR_AUTO_GEN_CE);
            return;
        }
        List<OrgDO> children = childrenGetter.apply(orgData);
        if (CollectionUtils.isEmpty(children)) {
            return;
        }
        if (UnitNature.JCFHB == unitNatrue) {
            errorConsumer.accept(orgData, FormTypeOrgExtendError.ERROR_UPDATE_DISABLE);
            return;
        }
        if (UnitNature.BZHZB == unitNatrue && (findAny = children.stream().filter(c -> this.isAutoGenUnit(unitNatureGetter, formTypeZb, (OrgDO)c, oldOrgData)).findAny()).isPresent()) {
            errorConsumer.accept(orgData, FormTypeOrgExtendError.ERROR_DISABLE_CE);
        }
    }

    private void doCompInfo(OrgDO orgDO, OrgDO oldOrgDO, ZB formTypeZb) {
        if (!StringUtils.hasText(orgDO.getOrgcode())) {
            orgDO.setOrgcode(oldOrgDO.getOrgcode());
        }
        if (!StringUtils.hasText(orgDO.getParentcode())) {
            if (!StringUtils.hasText(oldOrgDO.getParentcode())) {
                orgDO.setParentcode("-");
            } else {
                orgDO.setParentcode(oldOrgDO.getParentcode());
            }
        }
        if (!StringUtils.hasText(FormTypeUtils.getZbValue(orgDO, formTypeZb))) {
            orgDO.put(formTypeZb.getName(), (Object)FormTypeUtils.getZbValue(oldOrgDO, formTypeZb));
        }
    }

    public void doCheckOrgCode(ZB formTypeZb, UnitNatureGetter unitNatureGetter, OrgDO orgData, ExceptionConsumer<OrgDO, ErrorEnum> errorConsumer) {
        List<OrgDO> oldList = this.formTypeOrgHelper.getOrgDataByOrgCodes(orgData.getCategoryname(), FormTypeOrgDataHelper.getVersionDate((Map<String, Object>)orgData), Collections.singletonList(orgData.getOrgcode()));
        if (CollectionUtils.isEmpty(oldList)) {
            return;
        }
        OrgDO oldParentOrgData = this.formTypeOrgHelper.getOldParentOrgData(orgData);
        if (this.isAutoGenUnit(unitNatureGetter, formTypeZb, orgData, oldParentOrgData)) {
            return;
        }
        for (OrgDO old : oldList) {
            OrgDO oldParent;
            if (this.isAutoGenUnit(unitNatureGetter, formTypeZb, old, oldParent = this.formTypeOrgHelper.getOldParentOrgData(old))) continue;
            errorConsumer.accept(orgData, FormTypeOrgExtendError.ERROR_ORGCODE);
            return;
        }
    }
}

