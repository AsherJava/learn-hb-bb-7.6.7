/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.nr.entity.adapter.impl.org.OrgDataFilter
 *  com.jiuqi.nr.entity.engine.result.CheckFailNodeInfo
 *  com.jiuqi.va.domain.org.OrgBatchOptDTO
 *  com.jiuqi.va.domain.org.OrgDO
 *  com.jiuqi.va.domain.org.OrgDTO
 *  com.jiuqi.va.domain.org.ZB
 *  com.jiuqi.va.extend.OrgDataAction
 *  com.jiuqi.va.extend.OrgDataParamInterceptor
 */
package com.jiuqi.nr.formtype.org.extend;

import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.nr.entity.adapter.impl.org.OrgDataFilter;
import com.jiuqi.nr.entity.engine.result.CheckFailNodeInfo;
import com.jiuqi.nr.formtype.common.FormTypeConsts;
import com.jiuqi.nr.formtype.common.UnitNatureGetter;
import com.jiuqi.nr.formtype.org.extend.FormTypeOrgDataCheckService;
import com.jiuqi.nr.formtype.org.extend.FormTypeOrgDataExtendService;
import com.jiuqi.nr.formtype.org.extend.FormTypeOrgDataHelper;
import com.jiuqi.nr.formtype.org.extend.exception.ExceptionConsumer;
import com.jiuqi.nr.formtype.service.IFormTypeApplyService;
import com.jiuqi.va.domain.org.OrgBatchOptDTO;
import com.jiuqi.va.domain.org.OrgDO;
import com.jiuqi.va.domain.org.OrgDTO;
import com.jiuqi.va.domain.org.ZB;
import com.jiuqi.va.extend.OrgDataAction;
import com.jiuqi.va.extend.OrgDataParamInterceptor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Component
public class FormTypeOrgDataParamInterceptor
implements OrgDataParamInterceptor,
OrgDataFilter {
    @Autowired
    private FormTypeOrgDataCheckService formTypeOrgCheckService;
    @Autowired
    private IFormTypeApplyService iFormTypeApplyService;
    @Autowired
    private FormTypeOrgDataExtendService formTypeOrgDataExtendService;
    @Autowired
    private FormTypeOrgDataHelper formTypeOrgHelper;

    public void modify(OrgDTO param, OrgDataAction action) {
        if (OrgDataAction.Remove == action || null == param) {
            return;
        }
        if (!this.iFormTypeApplyService.enableNrFormTypeMgr()) {
            return;
        }
        if (this.iFormTypeApplyService.isMdOrg(param.getCategoryname())) {
            return;
        }
        ZB formTypeZb = this.iFormTypeApplyService.getFormTypeZb(param.getCategoryname());
        if (null == formTypeZb) {
            return;
        }
        if (OrgDataAction.Add == action) {
            this.formTypeOrgCheckService.doAddUnitCheck(formTypeZb, (OrgDO)param, null);
        } else if (OrgDataAction.Update == action) {
            this.formTypeOrgCheckService.doUpdateUnitCheck(formTypeZb, (OrgDO)param, null);
        } else if (OrgDataAction.Sync == action) {
            this.formTypeOrgCheckService.doUpdateUnitCheck(formTypeZb, (OrgDO)param, null);
        } else if (OrgDataAction.ImportByExcel == action) {
            this.formTypeOrgDataExtendService.doImportExtends(formTypeZb, (OrgDO)param);
            this.formTypeOrgCheckService.doUpdateUnitCheck(formTypeZb, (OrgDO)param, this.getErrorConsumer());
        }
    }

    public void batchModify(OrgBatchOptDTO param, OrgDataAction action) {
        if (param.isHighTrustability()) {
            return;
        }
        List orgDatas = param.getDataList();
        if (OrgDataAction.Remove == action || CollectionUtils.isEmpty(orgDatas)) {
            return;
        }
        if (!this.iFormTypeApplyService.enableNrFormTypeMgr()) {
            return;
        }
        if (this.iFormTypeApplyService.isMdOrg(param.getQueryParam().getCategoryname())) {
            return;
        }
        ZB formTypeZb = this.iFormTypeApplyService.getFormTypeZb(param.getQueryParam().getCategoryname());
        if (null == formTypeZb) {
            return;
        }
        if (OrgDataAction.Add == action) {
            this.formTypeOrgCheckService.doAddUnitCheck(formTypeZb, orgDatas, null);
        } else if (OrgDataAction.Update == action) {
            this.formTypeOrgCheckService.doUpdateUnitCheck(formTypeZb, orgDatas, null);
        } else if (OrgDataAction.Sync == action) {
            this.formTypeOrgCheckService.doUpdateUnitCheck(formTypeZb, orgDatas, null);
        } else if (OrgDataAction.ImportByExcel == action) {
            this.formTypeOrgDataExtendService.doImportExtends(formTypeZb, orgDatas);
            this.formTypeOrgCheckService.doUpdateUnitCheck(formTypeZb, orgDatas, this.getErrorConsumer());
        }
    }

    private ExceptionConsumer<OrgDO, ErrorEnum> getErrorConsumer() {
        return (orgData, error) -> {
            orgData.put("importstate", (Object)FormTypeConsts.ORG_EXTEND_IMPORTSTATE_ERRVALUE);
            orgData.put("importmemo", (Object)error.getMessage());
        };
    }

    public List<CheckFailNodeInfo> checkData(OrgDO orgDO) {
        if (!this.iFormTypeApplyService.enableNrFormTypeMgr()) {
            return Collections.emptyList();
        }
        if (this.iFormTypeApplyService.isMdOrg(orgDO.getCategoryname())) {
            return Collections.emptyList();
        }
        ZB formTypeZb = this.iFormTypeApplyService.getFormTypeZb(orgDO.getCategoryname());
        if (null == formTypeZb) {
            return Collections.emptyList();
        }
        UnitNatureGetter unitNatureGetter = this.iFormTypeApplyService.getUnitNatureGetter(formTypeZb.getReltablename());
        ArrayList<CheckFailNodeInfo> result = new ArrayList<CheckFailNodeInfo>();
        if (!StringUtils.hasText(orgDO.getCode())) {
            this.formTypeOrgCheckService.doAddUnitCheck(formTypeZb, orgDO, (data, error) -> result.add(this.getCheckFailNodeInfo(formTypeZb, unitNatureGetter, (OrgDO)data, null, (ErrorEnum)error)));
        } else {
            OrgDO oldOrgData = this.formTypeOrgHelper.getOldOrgData(orgDO);
            this.formTypeOrgCheckService.doUpdateUnitCheck(formTypeZb, orgDO, (data, error) -> result.add(this.getCheckFailNodeInfo(formTypeZb, unitNatureGetter, (OrgDO)data, oldOrgData, (ErrorEnum)error)));
        }
        orgDO.put("_ORG_EXTEND_CHECKED", (Object)Boolean.TRUE);
        return result;
    }

    private CheckFailNodeInfo getCheckFailNodeInfo(ZB formTypeZb, UnitNatureGetter unitNatureGetter, OrgDO orgDO, OrgDO oldOrgDO, ErrorEnum error) {
        String formTypeZbName = formTypeZb.getName().toLowerCase();
        CheckFailNodeInfo info = new CheckFailNodeInfo();
        info.setCode(orgDO.getCode());
        info.setMessage(error.getMessage());
        if (null == oldOrgDO) {
            info.setAttributeCode(formTypeZbName);
            info.setValue(orgDO.get((Object)formTypeZbName));
        } else if (unitNatureGetter.getUnitNature(orgDO, formTypeZb) != unitNatureGetter.getUnitNature(oldOrgDO, formTypeZb)) {
            info.setAttributeCode(formTypeZbName);
            info.setValue(orgDO.get((Object)formTypeZbName));
        } else if (!orgDO.getParentcode().equals(oldOrgDO.getParentcode())) {
            info.setAttributeCode("parentcode");
            info.setValue((Object)orgDO.getParentcode());
        } else if (!orgDO.getOrgcode().equals(oldOrgDO.getOrgcode())) {
            info.setAttributeCode("orgcode");
            info.setValue((Object)orgDO.getOrgcode());
        }
        return info;
    }
}

