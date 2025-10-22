/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.org.OrgBatchOptDTO
 *  com.jiuqi.va.domain.org.OrgCategoryDO
 *  com.jiuqi.va.domain.org.OrgDO
 *  com.jiuqi.va.domain.org.OrgDTO
 *  com.jiuqi.va.domain.org.ZB
 *  com.jiuqi.va.extend.OrgCodeUniqueCheckInterceptor
 *  com.jiuqi.va.extend.OrgDataAction
 */
package com.jiuqi.nr.formtype.org.extend;

import com.jiuqi.nr.formtype.common.UnitNature;
import com.jiuqi.nr.formtype.common.UnitNatureGetter;
import com.jiuqi.nr.formtype.org.extend.FormTypeOrgDataCheckService;
import com.jiuqi.nr.formtype.org.extend.FormTypeOrgDataHelper;
import com.jiuqi.nr.formtype.service.IFormTypeApplyService;
import com.jiuqi.va.domain.org.OrgBatchOptDTO;
import com.jiuqi.va.domain.org.OrgCategoryDO;
import com.jiuqi.va.domain.org.OrgDO;
import com.jiuqi.va.domain.org.OrgDTO;
import com.jiuqi.va.domain.org.ZB;
import com.jiuqi.va.extend.OrgCodeUniqueCheckInterceptor;
import com.jiuqi.va.extend.OrgDataAction;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class FormTypeOrgDataCodeCheckService
implements OrgCodeUniqueCheckInterceptor {
    private static final String REPEAT_CODE_MSG = "\u673a\u6784\u7f16\u7801\u91cd\u590d";
    private static final String CE_VERIFICATION_FAILED_MSG = "\u5dee\u989d\u5355\u4f4d\u4e0d\u5408\u6cd5";
    @Autowired
    private IFormTypeApplyService iFormTypeApplyService;
    @Autowired
    private FormTypeOrgDataCheckService formTypeOrgCheckService;
    @Autowired
    private FormTypeOrgDataHelper formTypeOrgDataHelper;

    public void check(OrgBatchOptDTO orgBatchOptDTO, Map<String, String> errorResult, OrgCategoryDO orgCategory, OrgDataAction action) {
        if (OrgDataAction.Remove == action) {
            return;
        }
        if (!this.iFormTypeApplyService.enableNrFormTypeMgr()) {
            return;
        }
        if (this.iFormTypeApplyService.isMdOrg(orgCategory.getName())) {
            this.defaultOrgCodeCheck(orgBatchOptDTO, errorResult);
            return;
        }
        ZB orgFormTypeZb = this.iFormTypeApplyService.getFormTypeZb(orgCategory.getName());
        if (null != orgFormTypeZb) {
            this.orgCodeCheck(orgFormTypeZb, orgBatchOptDTO, errorResult);
        } else {
            this.defaultOrgCodeCheck(orgBatchOptDTO, errorResult);
        }
    }

    private void orgCodeCheck(ZB orgFormTypeZb, OrgBatchOptDTO orgBatchOptDTO, Map<String, String> errorResult) {
        List dataList = orgBatchOptDTO.getDataList();
        ArrayList<String> orgcodes = new ArrayList<String>();
        for (OrgDO org : dataList) {
            orgcodes.add(org.getOrgcode());
        }
        List<OrgDO> oldList = this.getOldList(orgBatchOptDTO, orgcodes);
        UnitNatureGetter unitNatureGetter = this.iFormTypeApplyService.getUnitNatureGetter(orgFormTypeZb.getReltablename());
        HashMap<String, String> orgCodeSet = new HashMap<String, String>();
        HashMap<String, String> autoGenOrgCodeSet = new HashMap<String, String>();
        for (OrgDO org : oldList) {
            if (unitNatureGetter.isAutoGenUnitCode(org, orgFormTypeZb)) {
                String compOrgCode = this.getCompOrgCode(org, orgFormTypeZb, unitNatureGetter);
                autoGenOrgCodeSet.put(compOrgCode, org.getCode());
                continue;
            }
            orgCodeSet.put(org.getOrgcode(), org.getCode());
        }
        Function<OrgDO, OrgDO> parentGetter = this.formTypeOrgDataHelper.getParentGetter(dataList);
        for (OrgDO org : dataList) {
            if (FormTypeOrgDataCheckService.skip(org)) continue;
            OrgDO parentOrg = parentGetter.apply(org);
            if (this.formTypeOrgCheckService.isAutoGenUnit(unitNatureGetter, orgFormTypeZb, org, parentOrg)) {
                String compOrgCode = this.getCompOrgCode(org, orgFormTypeZb, unitNatureGetter);
                if (autoGenOrgCodeSet.containsKey(compOrgCode) && !org.getCode().equals(autoGenOrgCodeSet.get(compOrgCode))) {
                    errorResult.put(org.getCode(), REPEAT_CODE_MSG);
                    continue;
                }
                autoGenOrgCodeSet.put(compOrgCode, org.getCode());
                continue;
            }
            if (UnitNature.JTCEB == unitNatureGetter.getUnitNature(org, orgFormTypeZb)) {
                errorResult.put(org.getCode(), CE_VERIFICATION_FAILED_MSG);
                continue;
            }
            if (orgCodeSet.containsKey(org.getOrgcode()) && !org.getCode().equals(orgCodeSet.get(org.getOrgcode()))) {
                errorResult.put(org.getCode(), REPEAT_CODE_MSG);
                continue;
            }
            orgCodeSet.put(org.getOrgcode(), org.getCode());
        }
    }

    private List<OrgDO> getOldList(OrgBatchOptDTO orgBatchOptDTO, List<String> orgcodes) {
        OrgDTO param = orgBatchOptDTO.getQueryParam();
        return this.formTypeOrgDataHelper.getOrgDataByOrgCodes(param.getCategoryname(), param.getVersionDate(), orgcodes);
    }

    private String getCompOrgCode(OrgDO org, ZB orgFormTypeZb, UnitNatureGetter unitNatureGetter) {
        return org.getOrgcode() + unitNatureGetter.getUnitNature(org, orgFormTypeZb).getValue();
    }

    private void defaultOrgCodeCheck(OrgBatchOptDTO orgBatchOptDTO, Map<String, String> errorResult) {
        List dataList = orgBatchOptDTO.getDataList();
        ArrayList<String> orgcodes = new ArrayList<String>();
        HashMap<String, OrgDO> newDatas = new HashMap<String, OrgDO>();
        for (OrgDO org : dataList) {
            if (!StringUtils.hasText(org.getOrgcode())) continue;
            if (orgcodes.contains(org.getOrgcode())) {
                errorResult.put(org.getCode(), REPEAT_CODE_MSG);
                continue;
            }
            orgcodes.add(org.getOrgcode());
            orgcodes.add(org.getCode());
            newDatas.put(org.getOrgcode(), org);
        }
        List<OrgDO> oldList = this.getOldList(orgBatchOptDTO, orgcodes);
        for (OrgDO orgDO : oldList) {
            if (newDatas.containsKey(orgDO.getOrgcode()) && !orgDO.getCode().equals(((OrgDO)newDatas.get(orgDO.getOrgcode())).getCode())) {
                errorResult.put(orgDO.getCode(), REPEAT_CODE_MSG);
            }
            if (!newDatas.containsKey(orgDO.getCode()) || orgDO.getCode().equals(((OrgDO)newDatas.get(orgDO.getCode())).getCode())) continue;
            errorResult.put(orgDO.getCode(), REPEAT_CODE_MSG);
        }
    }
}

