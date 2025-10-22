/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.common.util.DimensionUtils
 *  com.jiuqi.gcreport.organization.api.enums.GcOrgKindEnum
 *  com.jiuqi.gcreport.organization.api.vo.OrgDataParam
 *  com.jiuqi.gcreport.organization.api.vo.OrgDataVO
 *  com.jiuqi.gcreport.organization.impl.bean.OrgDataDO
 *  com.jiuqi.gcreport.organization.impl.bean.OrgDisplaySchemeDO
 *  com.jiuqi.gcreport.organization.impl.enums.BBLXEnum
 *  com.jiuqi.gcreport.organization.impl.service.impl.BaseUnitTreeNodeBuilder
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.bpm.de.dataflow.bean.ActionStateBean
 *  com.jiuqi.nr.bpm.de.dataflow.tree.util.TreeState
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 */
package com.jiuqi.gcreport.nr.impl.uploadstate.provider;

import com.jiuqi.gcreport.common.util.DimensionUtils;
import com.jiuqi.gcreport.nr.impl.uploadstate.util.UploadStateTool;
import com.jiuqi.gcreport.organization.api.enums.GcOrgKindEnum;
import com.jiuqi.gcreport.organization.api.vo.OrgDataParam;
import com.jiuqi.gcreport.organization.api.vo.OrgDataVO;
import com.jiuqi.gcreport.organization.impl.bean.OrgDataDO;
import com.jiuqi.gcreport.organization.impl.bean.OrgDisplaySchemeDO;
import com.jiuqi.gcreport.organization.impl.enums.BBLXEnum;
import com.jiuqi.gcreport.organization.impl.service.impl.BaseUnitTreeNodeBuilder;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.bpm.de.dataflow.bean.ActionStateBean;
import com.jiuqi.nr.bpm.de.dataflow.tree.util.TreeState;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import java.util.Map;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@Primary
public class GcUnitTreeNodeBuilder
extends BaseUnitTreeNodeBuilder {
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private TreeState treeState;

    public OrgDataVO buildTreeNode(OrgDataParam param, OrgDataDO orgDataDO, OrgDisplaySchemeDO displayScheme) {
        OrgDataVO orgDataVO = super.buildTreeNode(param, orgDataDO, displayScheme);
        orgDataVO.setIcons(this.setNodeIcon(param, orgDataDO));
        orgDataVO.setBblx(Objects.toString(orgDataDO.getFieldVal("BBLX"), ""));
        orgDataVO.setOrgKind(this.setOrgKind(param, orgDataDO));
        return orgDataVO;
    }

    private String setNodeIcon(OrgDataParam param, OrgDataDO orgDataDO) {
        Map<DimensionValueSet, ActionStateBean> uploadStates;
        String bblxValue = Objects.toString(orgDataDO.getFieldVal("BBLX"), "");
        if (!StringUtils.hasLength(param.getFormSchemeKey())) {
            return null;
        }
        FormSchemeDefine fromSchemeDefine = this.runTimeViewController.getFormScheme(param.getFormSchemeKey());
        DimensionValueSet dimSet = new DimensionValueSet();
        dimSet.setValue("MD_ORG", (Object)orgDataDO.getCode());
        dimSet.setValue("DATATIME", (Object)param.getOrgVerCode());
        if (DimensionUtils.isExistAdjust((String)fromSchemeDefine.getTaskKey())) {
            dimSet.setValue("ADJUST", (Object)param.getAdjustPeriodCode());
        }
        if ((uploadStates = UploadStateTool.getInstance().getTreeWorkflowUploadState(dimSet, param.getFormSchemeKey())) == null) {
            return bblxValue;
        }
        ActionStateBean actionStateBean = uploadStates.get(dimSet);
        if (actionStateBean == null) {
            return bblxValue;
        }
        String svgKey = GcUnitTreeNodeBuilder.getSVGKey(bblxValue, actionStateBean);
        return bblxValue + ";" + svgKey;
    }

    private GcOrgKindEnum setOrgKind(OrgDataParam param, OrgDataDO orgDataDO) {
        String bblxValue = Objects.toString(orgDataDO.getFieldVal("BBLX"), "");
        if (String.valueOf(BBLXEnum.MERGE.getId()).equals(bblxValue)) {
            return GcOrgKindEnum.UNIONORG;
        }
        if (String.valueOf(BBLXEnum.DEFF.getId()).equals(bblxValue)) {
            return GcOrgKindEnum.DIFFERENCE;
        }
        return GcOrgKindEnum.SINGLE;
    }

    public static String getSVGKey(String baseIconKey, ActionStateBean uploadState) {
        if (uploadState != null && uploadState.getCode() != null) {
            return "state-icon@" + uploadState.getCode();
        }
        return baseIconKey;
    }
}

