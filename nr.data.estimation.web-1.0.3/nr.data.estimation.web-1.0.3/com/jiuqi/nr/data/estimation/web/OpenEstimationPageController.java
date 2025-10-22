/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.authz2.service.SystemIdentityService
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.data.estimation.common.exception.EstimationRuntimeException
 *  com.jiuqi.nr.data.estimation.service.IEstimationSchemeUserService
 *  com.jiuqi.nr.data.estimation.storage.entity.IEstimationScheme
 *  com.jiuqi.nr.data.estimation.storage.enumeration.EstimationFormType
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.FormulaSchemeDefine
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nr.period.modal.IPeriodEntity
 *  com.jiuqi.util.StringUtils
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  javax.annotation.Resource
 *  javax.validation.Valid
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.ResponseBody
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.nr.data.estimation.web;

import com.jiuqi.np.authz2.service.SystemIdentityService;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.data.estimation.common.exception.EstimationRuntimeException;
import com.jiuqi.nr.data.estimation.service.IEstimationSchemeUserService;
import com.jiuqi.nr.data.estimation.storage.entity.IEstimationScheme;
import com.jiuqi.nr.data.estimation.storage.enumeration.EstimationFormType;
import com.jiuqi.nr.data.estimation.web.enumeration.DataEntryToolBarMenus;
import com.jiuqi.nr.data.estimation.web.ext.dataentry.DataEntryOpenFuncParam;
import com.jiuqi.nr.data.estimation.web.request.ActionOfCheckFormsParam;
import com.jiuqi.nr.data.estimation.web.request.OpenEstimationPageFuncPara;
import com.jiuqi.nr.data.estimation.web.response.EstimationFormulaSchemeInfo;
import com.jiuqi.nr.data.estimation.web.service.EstimationSchemeDataInputService;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import com.jiuqi.util.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/api/v1/estimation/func-para"})
@Api(tags={"what-if\u6d4b\u7b97\u529f\u80fd\u5165\u53e3-API"})
public class OpenEstimationPageController {
    @Resource
    private SystemIdentityService identityService;
    @Resource
    private IEntityMetaService metaService;
    @Resource
    public IPeriodEntityAdapter periodAdapter;
    @Resource
    private IRunTimeViewController rtViewService;
    @Resource
    private IEstimationSchemeUserService estimationSchemeUserService;
    @Resource
    private EstimationSchemeDataInputService dataInputService;

    @ResponseBody
    @ApiOperation(value="\u6253\u5f00\u6d4b\u7b97\u529f\u80fd")
    @PostMapping(value={"/open-page"})
    public DataEntryOpenFuncParam openDataEntryPage(@Valid @RequestBody OpenEstimationPageFuncPara openedInfo) {
        IEstimationScheme estimationScheme;
        String currentUserId = NpContextHolder.getContext().getUserId();
        boolean isAdmin = this.identityService.isSystemIdentity(currentUserId);
        if (isAdmin) {
            throw new EstimationRuntimeException("\u7ba1\u7406\u5458\u7528\u6237\u65e0\u6cd5\u8fdb\u5165\u8be5\u529f\u80fd\uff01");
        }
        FormSchemeDefine formScheme = this.rtViewService.getFormScheme(openedInfo.getFormSchemeId());
        if (formScheme == null) {
            throw new EstimationRuntimeException("\u65e0\u6548\u7684\u529f\u80fd\u53c2\u6570\uff0c\u62a5\u8868\u65b9\u6848\u4e0d\u80fd\u4e3a\u7a7a\uff01");
        }
        Map<String, DimensionValue> dimValueSet = openedInfo.getDimValueSet();
        if (dimValueSet == null || dimValueSet.isEmpty()) {
            throw new EstimationRuntimeException("\u65e0\u6548\u7684\u529f\u80fd\u53c2\u6570\uff0c\u7ef4\u5ea6\u4fe1\u606f\u4e0d\u80fd\u4e3a\u7a7a\uff01");
        }
        if (DataEntryToolBarMenus.newEstimationMenu.code.equals(openedInfo.getActionId())) {
            estimationScheme = this.estimationSchemeUserService.newEstimationScheme(openedInfo.getFormSchemeId(), openedInfo.getFormIds(), openedInfo.getDimValueSet());
            this.dataInputService.restoreFromOriginal(this.getActionOfCheckFormsParam(estimationScheme, dimValueSet));
        } else if (DataEntryToolBarMenus.oldEstimationMenu.code.equals(openedInfo.getActionId())) {
            estimationScheme = this.estimationSchemeUserService.oldEstimationScheme(openedInfo.getFormSchemeId(), openedInfo.getDimValueSet());
        } else {
            throw new EstimationRuntimeException("\u65e0\u6548\u7684\u529f\u80fd\u53c2\u6570\uff1a" + openedInfo.getActionId());
        }
        DataEntryOpenFuncParam funcParam = new DataEntryOpenFuncParam();
        funcParam.setPeriod(this.getPeriod(formScheme, dimValueSet));
        funcParam.setPeriodAllowChange(false);
        funcParam.setTaskId(formScheme.getTaskKey());
        funcParam.setUnitViewEntityRange(this.getMainDimIds(formScheme, dimValueSet));
        funcParam.setAccessFormulaSchemes(this.getAccessFormulaScheme(estimationScheme));
        funcParam.setVariableMap(new HashMap<String, Object>());
        funcParam.getVariableMap().put("estimationScheme", estimationScheme.getKey());
        funcParam.setFormulaSchemeIds(this.getDisplayFormulaSchemeIds(estimationScheme, openedInfo.getContext()));
        return funcParam;
    }

    private List<String> getMainDimIds(FormSchemeDefine formScheme, Map<String, DimensionValue> dimValueSet) {
        ArrayList<String> mainDims = new ArrayList<String>();
        IEntityDefine mainDimEntity = this.metaService.queryEntity(formScheme.getDw());
        String dimValue = this.findDimValueFromMap(mainDimEntity.getDimensionName(), dimValueSet);
        if (StringUtils.isEmpty((String)dimValue)) {
            throw new EstimationRuntimeException("\u65e0\u6548\u7684\u529f\u80fd\u53c2\u6570\uff0c\u4e3b\u7ef4\u5ea6\u4e0d\u80fd\u4e3a\u7a7a\uff01");
        }
        mainDims.add(dimValue);
        return mainDims;
    }

    private String getPeriod(FormSchemeDefine formScheme, Map<String, DimensionValue> dimValueSet) {
        IPeriodEntity periodEntity = this.periodAdapter.getPeriodEntity(formScheme.getDateTime());
        String dimValue = this.findDimValueFromMap(periodEntity.getDimensionName(), dimValueSet);
        if (StringUtils.isEmpty((String)dimValue)) {
            throw new EstimationRuntimeException("\u65e0\u6548\u7684\u529f\u80fd\u53c2\u6570\uff0c\u65f6\u671f\u4e0d\u80fd\u4e3a\u7a7a\uff01");
        }
        return dimValue;
    }

    private String findDimValueFromMap(String dimName, Map<String, DimensionValue> dimValueSet) {
        for (Map.Entry<String, DimensionValue> entry : dimValueSet.entrySet()) {
            if (!dimName.equals(entry.getKey())) continue;
            return entry.getValue().getValue();
        }
        return null;
    }

    private List<EstimationFormulaSchemeInfo> getAccessFormulaScheme(IEstimationScheme estimationScheme) {
        List accessFormulaSchemes = estimationScheme.getAccessFormulaSchemes();
        return accessFormulaSchemes.stream().map(fs -> {
            EstimationFormulaSchemeInfo eFormulaSchemeInfo = new EstimationFormulaSchemeInfo();
            eFormulaSchemeInfo.setFormulaSchemeId(fs.getKey());
            eFormulaSchemeInfo.setFormulaSchemeTitle(fs.getTitle());
            return eFormulaSchemeInfo;
        }).collect(Collectors.toList());
    }

    private ActionOfCheckFormsParam getActionOfCheckFormsParam(IEstimationScheme estimationScheme, Map<String, DimensionValue> dimValueSet) {
        ActionOfCheckFormsParam checkFormsParam = new ActionOfCheckFormsParam();
        checkFormsParam.setEstimationScheme(estimationScheme.getKey());
        checkFormsParam.setDimValueSet(dimValueSet);
        checkFormsParam.setFormIds(estimationScheme.getEstimationForms().stream().filter(e -> e.getFormType() == EstimationFormType.inputForm).map(e -> e.getFormDefine().getKey()).collect(Collectors.toList()));
        return checkFormsParam;
    }

    private List<String> getDisplayFormulaSchemeIds(IEstimationScheme estimationScheme, JtableContext context) {
        ArrayList<String> formulaSchemeIds = new ArrayList<String>();
        List accessFormulaSchemes = estimationScheme.getAccessFormulaSchemes();
        if (!accessFormulaSchemes.isEmpty()) {
            String formulaSchemeKey = context.getFormulaSchemeKey();
            if (accessFormulaSchemes.stream().anyMatch(e -> e.getKey().equals(formulaSchemeKey))) {
                formulaSchemeIds.add(formulaSchemeKey);
                return formulaSchemeIds;
            }
            formulaSchemeIds.add(((FormulaSchemeDefine)accessFormulaSchemes.get(0)).getKey());
        }
        return formulaSchemeIds;
    }
}

