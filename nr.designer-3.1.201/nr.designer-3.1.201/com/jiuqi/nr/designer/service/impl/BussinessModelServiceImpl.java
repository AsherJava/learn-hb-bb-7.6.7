/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.definition.facade.DesignTableDefine
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.nr.definition.facade.DesignFormulaDefine
 *  com.jiuqi.nr.definition.facade.formula.DesignFormulaCondition
 *  com.jiuqi.nr.definition.facade.formula.DesignFormulaConditionLink
 *  com.jiuqi.nr.definition.facade.formula.FormulaConditionLink
 *  com.jiuqi.nr.definition.internal.controller.NRDesignTimeController
 *  com.jiuqi.nr.definition.util.ServeCodeService
 */
package com.jiuqi.nr.designer.service.impl;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.definition.facade.DesignTableDefine;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.nr.definition.facade.DesignFormulaDefine;
import com.jiuqi.nr.definition.facade.formula.DesignFormulaCondition;
import com.jiuqi.nr.definition.facade.formula.DesignFormulaConditionLink;
import com.jiuqi.nr.definition.facade.formula.FormulaConditionLink;
import com.jiuqi.nr.definition.internal.controller.NRDesignTimeController;
import com.jiuqi.nr.definition.util.ServeCodeService;
import com.jiuqi.nr.designer.common.IDesignerEntityUpgrader;
import com.jiuqi.nr.designer.service.IBussinessModelService;
import com.jiuqi.nr.designer.web.facade.FormulaConditionLinkObj;
import com.jiuqi.nr.designer.web.facade.FormulaDataVO;
import com.jiuqi.nr.designer.web.facade.FormulaObj;
import com.jiuqi.nr.designer.web.rest.param.FormulaSearchPM;
import com.jiuqi.nr.designer.web.rest.vo.LightFieldVo;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class BussinessModelServiceImpl
implements IBussinessModelService {
    private static final Logger log = LoggerFactory.getLogger(BussinessModelServiceImpl.class);
    @Autowired
    private NRDesignTimeController nrDesignTimeController;
    @Autowired
    private ServeCodeService serveCodeService;
    @Autowired
    private IDesignerEntityUpgrader iDesignerEntityUpgrader;

    @Override
    public String getFieldByTableID(String tableid) {
        return this.iDesignerEntityUpgrader.getFieldByTableID(tableid);
    }

    @Override
    public List<FormulaObj> getFormulaData(String formulascheme, String formKey) throws JQException {
        if (StringUtils.isEmpty((String)formulascheme)) {
            return Collections.emptyList();
        }
        List allFormulaInForm = this.nrDesignTimeController.getAllFormulasInForm(formulascheme, StringUtils.isEmpty((String)formKey) ? null : formKey);
        Map<String, List<FormulaConditionLinkObj>> formulaConditionMap = this.getFormulaConditions(formulascheme);
        return allFormulaInForm.stream().map(define -> this.initFormulaObj((DesignFormulaDefine)define, (List)formulaConditionMap.get(define.getKey()))).collect(Collectors.toList());
    }

    private Map<String, List<FormulaConditionLinkObj>> getFormulaConditions(String formulaScheme) {
        List formulaConditionLinks = this.nrDesignTimeController.queryFormulaConditionLinks(formulaScheme);
        if (CollectionUtils.isEmpty(formulaConditionLinks)) {
            return Collections.emptyMap();
        }
        Map<String, DesignFormulaCondition> conditionMap = this.nrDesignTimeController.queryFormulaConditions(formulaConditionLinks.stream().map(FormulaConditionLink::getConditionKey).distinct().collect(Collectors.toList())).stream().collect(Collectors.toMap(IBaseMetaItem::getKey, f -> f));
        return formulaConditionLinks.stream().map(l -> this.initConditionDto((DesignFormulaConditionLink)l, (DesignFormulaCondition)conditionMap.get(l.getConditionKey()))).filter(Objects::nonNull).collect(Collectors.groupingBy(FormulaConditionLinkObj::getFormulaKey));
    }

    private FormulaConditionLinkObj initConditionDto(DesignFormulaConditionLink link, DesignFormulaCondition designFormulaCondition) {
        if (link != null && designFormulaCondition != null) {
            FormulaConditionLinkObj dto = new FormulaConditionLinkObj();
            dto.setKey(link.getConditionKey());
            dto.setFormulaKey(link.getFormulaKey());
            dto.setSchemeKey(link.getFormulaSchemeKey());
            dto.setCode(designFormulaCondition.getCode());
            dto.setTitle(designFormulaCondition.getTitle());
            return dto;
        }
        return null;
    }

    @Override
    public List<FormulaObj> getFormulaDataNew(String formulaschemeKey, String formKey) throws JQException {
        if (StringUtils.isEmpty((String)formulaschemeKey)) {
            return Collections.emptyList();
        }
        List allFormulaInForm = this.nrDesignTimeController.getAllFormulasInForm(formulaschemeKey, StringUtils.isEmpty((String)formKey) ? null : formKey);
        return allFormulaInForm.stream().map(define -> this.initFormulaObjNew(formulaschemeKey, (DesignFormulaDefine)define)).collect(Collectors.toList());
    }

    private FormulaObj initFormulaObjNew(String formulaschemeKey, DesignFormulaDefine designFormulaDefine) {
        FormulaObj formulaObj = new FormulaObj();
        formulaObj.setId(designFormulaDefine.getKey());
        formulaObj.setCode(designFormulaDefine.getCode());
        formulaObj.setExpression(designFormulaDefine.getExpression());
        formulaObj.setDescription(designFormulaDefine.getDescription());
        formulaObj.setCheckType(designFormulaDefine.getCheckType());
        formulaObj.setOrder(designFormulaDefine.getOrder());
        formulaObj.setUseCalculate(designFormulaDefine.getUseCalculate());
        formulaObj.setUseCheck(designFormulaDefine.getUseCheck());
        formulaObj.setUseBalance(designFormulaDefine.getUseBalance());
        formulaObj.setFormKey(designFormulaDefine.getFormKey());
        formulaObj.setSchemeKey(formulaschemeKey);
        formulaObj.setOwnerLevelAndId(designFormulaDefine.getOwnerLevelAndId());
        formulaObj.setBalanceZBExp(designFormulaDefine.getBalanceZBExp());
        return formulaObj;
    }

    private FormulaObj initFormulaObj(DesignFormulaDefine designFormulaDefine, List<FormulaConditionLinkObj> linkObjs) {
        FormulaObj formulaObj = new FormulaObj();
        formulaObj.setId(designFormulaDefine.getKey());
        formulaObj.setCode(designFormulaDefine.getCode());
        formulaObj.setExpression(designFormulaDefine.getExpression());
        formulaObj.setDescription(designFormulaDefine.getDescription());
        formulaObj.setCheckType(designFormulaDefine.getCheckType());
        formulaObj.setOrder(designFormulaDefine.getOrder());
        formulaObj.setUseCalculate(designFormulaDefine.getUseCalculate());
        formulaObj.setUseCheck(designFormulaDefine.getUseCheck());
        formulaObj.setUseBalance(designFormulaDefine.getUseBalance());
        formulaObj.setSchemeKey(designFormulaDefine.getFormulaSchemeKey());
        formulaObj.setFormKey(designFormulaDefine.getFormKey());
        formulaObj.setOwnerLevelAndId(designFormulaDefine.getOwnerLevelAndId());
        formulaObj.setBalanceZBExp(designFormulaDefine.getBalanceZBExp());
        try {
            formulaObj.setSameServeCode(this.serveCodeService.isSameServeCode(designFormulaDefine.getOwnerLevelAndId()));
        }
        catch (JQException e) {
            log.error(e.getMessage(), e);
        }
        formulaObj.setFormulaConditions(linkObjs);
        return formulaObj;
    }

    @Override
    public List<LightFieldVo> getNoBizFields(String tableid) throws JQException {
        if (StringUtils.isEmpty((String)tableid)) {
            return Collections.emptyList();
        }
        String[] bizKeyFieldsID = null;
        DesignTableDefine queryTableDefine = this.nrDesignTimeController.queryTableDefine(tableid);
        if (queryTableDefine != null) {
            bizKeyFieldsID = queryTableDefine.getBizKeyFieldsID();
        }
        List bizFields = Arrays.stream(bizKeyFieldsID).collect(Collectors.toList());
        List fields = this.nrDesignTimeController.getAllFieldsInTable(tableid);
        return fields.stream().map(field -> new LightFieldVo((FieldDefine)field)).filter(field -> !bizFields.contains(field.getKey())).collect(Collectors.toList());
    }

    @Override
    public FormulaDataVO getFormulaData(FormulaSearchPM searchPM) throws JQException {
        List<FormulaObj> formulaData = this.getFormulaData(searchPM.getFormulascheme(), searchPM.getFormKey());
        FormulaDataVO res = new FormulaDataVO(formulaData.size(), formulaData.stream().map(FormulaObj::getCode).max(String::compareTo).orElse(null));
        ArrayList<FormulaObj> formulaObjs = new ArrayList<FormulaObj>(formulaData.size());
        res.setData(formulaObjs);
        Set<Integer> auditType = searchPM.getAuditType();
        Set<Integer> formulaType = searchPM.getFormulaType();
        Set<String> conditionCode = searchPM.getConditionCode();
        for (FormulaObj formula : formulaData) {
            boolean flag = auditType.isEmpty() || auditType.contains(formula.getCheckType());
            flag = flag && (formulaType.isEmpty() || this.checkFormulaType(formulaType, formula));
            if (!(flag = flag && (conditionCode.isEmpty() || this.checkConditionCode(formula.getFormulaConditions(), conditionCode)))) continue;
            formulaObjs.add(formula);
        }
        return res;
    }

    private boolean checkConditionCode(List<FormulaConditionLinkObj> formulaConditions, Set<String> conditionCode) {
        if (formulaConditions == null) {
            return false;
        }
        for (FormulaConditionLinkObj formulaCondition : formulaConditions) {
            if (!conditionCode.contains(formulaCondition.getCode())) continue;
            return true;
        }
        return false;
    }

    private boolean checkFormulaType(Set<Integer> formulaType, FormulaObj x) {
        HashSet<Integer> formulaTypes = new HashSet<Integer>();
        formulaTypes.add(x.isUseCalculate() ? 1 : -1);
        formulaTypes.add(x.isUseCheck() ? 2 : -2);
        formulaTypes.add(x.isUseBalance() ? 4 : -4);
        return formulaTypes.stream().anyMatch(formulaType::contains);
    }
}

