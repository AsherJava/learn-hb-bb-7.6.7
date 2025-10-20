/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.bi.util.OrderGenerator
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.definition.common.UUIDUtils
 *  com.jiuqi.nr.definition.common.FormulaSchemeType
 *  com.jiuqi.nr.definition.exception.NrDefinitionErrorEnum
 *  com.jiuqi.nr.definition.facade.DesignFormDefine
 *  com.jiuqi.nr.definition.facade.DesignFormGroupDefine
 *  com.jiuqi.nr.definition.facade.DesignFormSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignFormulaDefine
 *  com.jiuqi.nr.definition.facade.DesignFormulaSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignPrintTemplateDefine
 *  com.jiuqi.nr.definition.facade.DesignPrintTemplateSchemeDefine
 *  com.jiuqi.nr.definition.facade.formula.DesignFormulaConditionLink
 *  com.jiuqi.nr.definition.internal.controller.NRDesignTimeController
 *  com.jiuqi.nr.definition.internal.impl.DesignFormDefineGetterImpl
 *  com.jiuqi.nr.definition.internal.impl.DesignFormGroupLink
 *  com.jiuqi.nr.definition.internal.impl.DesignFormSchemeDefineGetterImpl
 *  com.jiuqi.nr.definition.util.ServeCodeService
 *  com.jiuqi.util.StringUtils
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.nr.designer.web.service;

import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.definition.common.UUIDUtils;
import com.jiuqi.nr.definition.common.FormulaSchemeType;
import com.jiuqi.nr.definition.exception.NrDefinitionErrorEnum;
import com.jiuqi.nr.definition.facade.DesignFormDefine;
import com.jiuqi.nr.definition.facade.DesignFormGroupDefine;
import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignFormulaDefine;
import com.jiuqi.nr.definition.facade.DesignFormulaSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignPrintTemplateDefine;
import com.jiuqi.nr.definition.facade.DesignPrintTemplateSchemeDefine;
import com.jiuqi.nr.definition.facade.formula.DesignFormulaConditionLink;
import com.jiuqi.nr.definition.internal.controller.NRDesignTimeController;
import com.jiuqi.nr.definition.internal.impl.DesignFormDefineGetterImpl;
import com.jiuqi.nr.definition.internal.impl.DesignFormGroupLink;
import com.jiuqi.nr.definition.internal.impl.DesignFormSchemeDefineGetterImpl;
import com.jiuqi.nr.definition.util.ServeCodeService;
import com.jiuqi.nr.designer.common.NrDesingerErrorEnum;
import com.jiuqi.nr.designer.planpublish.service.TaskPlanPublishExternalService;
import com.jiuqi.nr.designer.web.facade.FormulaConditionLinkObj;
import com.jiuqi.nr.designer.web.facade.FormulaObj;
import com.jiuqi.nr.designer.web.rest.vo.MoveFormVO;
import com.jiuqi.nr.designer.web.rest.vo.PrintAttributeVo;
import com.jiuqi.util.StringUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

@Service
public class TaskDesignerService {
    @Autowired
    private NRDesignTimeController nrDesignTimeController;
    @Autowired
    private TaskPlanPublishExternalService taskPlanPublishExternalService;
    @Autowired
    private ServeCodeService serveCodeService;
    private static final String DEFAULT_PRINTSCHEME_TITLE = "\u9ed8\u8ba4\u6253\u5370\u65b9\u6848";

    public void copyPrintTelement(String formSchemeKey, String oldKey, String formKey, String targetGroupKey) throws Exception {
        DesignFormGroupDefine formGroup = this.nrDesignTimeController.queryFormGroup(targetGroupKey);
        String targetSchemeKey = formGroup.getFormSchemeKey();
        List targetPrintScheme = this.nrDesignTimeController.getAllPrintSchemeByFormScheme(targetSchemeKey);
        List allPrintSchemeByFormScheme = this.nrDesignTimeController.getAllPrintSchemeByFormScheme(formSchemeKey);
        String targetPrintSchemeKey = "";
        for (DesignPrintTemplateSchemeDefine printScheme : allPrintSchemeByFormScheme) {
            targetPrintSchemeKey = printScheme.getKey();
            DesignPrintTemplateDefine printTemplate = this.nrDesignTimeController.queryPrintTemplateDefineBySchemeAndForm(printScheme.getKey(), oldKey);
            if (printTemplate == null) continue;
            List collect = targetPrintScheme.stream().filter(p -> p.getTitle().equals(printScheme.getTitle())).collect(Collectors.toList());
            if (collect.size() > 0) {
                targetPrintSchemeKey = ((DesignPrintTemplateSchemeDefine)collect.get(0)).getKey();
            } else {
                printScheme.setKey(UUIDUtils.getKey());
                printScheme.setFormSchemeKey(targetSchemeKey);
                printScheme.setOrder(OrderGenerator.newOrder());
                targetPrintSchemeKey = printScheme.getKey();
                this.nrDesignTimeController.insertPrintTemplateSchemeDefine(printScheme);
            }
            printTemplate.setKey(UUIDUtils.getKey());
            printTemplate.setFormKey(formKey);
            printTemplate.setPrintSchemeKey(targetPrintSchemeKey);
            this.nrDesignTimeController.insertPrintTemplateDefine(printTemplate);
        }
    }

    public void moveForm(MoveFormVO moveFormVo) throws JQException {
        if (moveFormVo == null || StringUtils.isEmpty((String)moveFormVo.formKey)) {
            return;
        }
        DesignFormDefine form = this.nrDesignTimeController.querySoftFormDefine(moveFormVo.formKey);
        if (form == null) {
            throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_005);
        }
        this.setFormMasterEntityKey(moveFormVo, form);
        this.doFormGroup(moveFormVo);
    }

    private void setFormMasterEntityKey(MoveFormVO moveFormVo, DesignFormDefine form) throws JQException {
        DesignFormGroupDefine formGroup;
        DesignFormSchemeDefineGetterImpl targetFormScheme;
        String schemeViewKeys;
        DesignFormDefineGetterImpl formDefineGetterImpl;
        String formEntitiesKey;
        if (StringUtils.isEmpty((String)form.getMasterEntitiesKey()) && !(formEntitiesKey = (formDefineGetterImpl = new DesignFormDefineGetterImpl(form)).getMasterEntitiesKey()).equals(schemeViewKeys = (targetFormScheme = new DesignFormSchemeDefineGetterImpl(this.nrDesignTimeController.queryFormSchemeDefine((formGroup = this.nrDesignTimeController.queryFormGroup(moveFormVo.targetGroupKey)).getFormSchemeKey()))).getMasterEntitiesKey())) {
            boolean isExtend = true;
            List<String> formEntityKeys = this.getEntityKeys(formEntitiesKey);
            List<String> schemeEntityViews = this.getEntityKeys(schemeViewKeys);
            if (formEntityKeys.size() == schemeEntityViews.size() && formEntityKeys.get(0).equals(schemeEntityViews.get(0))) {
                for (int i = 1; i < formEntityKeys.size() && isExtend; ++i) {
                    if (schemeEntityViews.contains(formEntityKeys.get(i))) continue;
                    isExtend = false;
                }
            } else {
                isExtend = false;
            }
            if (!isExtend) {
                form.setMasterEntitiesKey(formEntitiesKey);
                this.nrDesignTimeController.updateFormDefine(form);
            }
        }
    }

    private List<String> getEntityKeys(String formEntitiesKey) {
        String[] split = formEntitiesKey.split(";");
        return Arrays.stream(split).collect(Collectors.toList());
    }

    public void doFormGroup(MoveFormVO moveFormVo) throws JQException {
        this.nrDesignTimeController.removeFormFromGroup(moveFormVo.formKey, moveFormVo.sourceGroupKey);
        this.nrDesignTimeController.addFormToGroup(moveFormVo.formKey, moveFormVo.targetGroupKey);
        this.updatePublicFormulaSchemeKey(moveFormVo);
    }

    public void doFormGroup(String formKey, String ownGroupKey) throws JQException {
        String[] groupIdsArray = new String[]{};
        if (!StringUtils.isEmpty((String)ownGroupKey)) {
            groupIdsArray = ownGroupKey.split(";");
        }
        ArrayList<String> delGroupId = new ArrayList<String>();
        HashSet<String> groupSet = new HashSet<String>(Arrays.asList(groupIdsArray));
        List designFormGroupLinks = this.nrDesignTimeController.getFormGroupLinksByFormId(formKey);
        for (DesignFormGroupLink formGroupLink : designFormGroupLinks) {
            String groupID = formGroupLink.getGroupKey();
            if (groupSet.remove(groupID.toString())) continue;
            this.nrDesignTimeController.removeFormFromGroup(formKey, groupID);
            delGroupId.add(groupID);
        }
        for (String groupID : groupSet) {
            if (!StringUtils.isNotEmpty((String)groupID)) continue;
            this.nrDesignTimeController.addFormToGroup(formKey, groupID);
        }
        if (groupIdsArray.length == 1) {
            DesignFormDefine designFormDefine = this.nrDesignTimeController.queryFormById(formKey);
            designFormDefine.setQuoteType(false);
            this.nrDesignTimeController.updateFormDefine(designFormDefine);
        }
        this.updatePublicFormulaSchemeKey(formKey, delGroupId, new HashSet<String>(Arrays.asList(groupIdsArray)));
    }

    private void updatePublicFormulaSchemeKey(String formKey, List<String> delGroupId, Set<String> groupSet) throws JQException {
        if (delGroupId.size() > 0) {
            HashMap<String, List<String>> map = this.getGroupKeyByFormSchemeKey(groupSet);
            ArrayList<String> delFormulakeys = new ArrayList<String>();
            ArrayList<DesignFormulaDefine> updateList = new ArrayList<DesignFormulaDefine>();
            for (String delGroupKey : delGroupId) {
                DesignFormGroupDefine groupDefine = this.nrDesignTimeController.queryFormGroup(delGroupKey);
                String currFormSchemeKey = groupDefine.getFormSchemeKey();
                List<String> groups = map.get(currFormSchemeKey);
                boolean formulaNeedUpdate = groups == null || groups.size() == 0;
                if (!formulaNeedUpdate) continue;
                List allFormulaSchemeDefines = this.nrDesignTimeController.getAllFormulaSchemeDefinesByFormScheme(currFormSchemeKey);
                for (DesignFormulaSchemeDefine designFormulaSchemeDefine : allFormulaSchemeDefines) {
                    String newFormulaSchemeKey = this.getNewFormulaSchemeKey(map, currFormSchemeKey, designFormulaSchemeDefine);
                    List formulaList = this.nrDesignTimeController.getAllSoftFormulasInForm(designFormulaSchemeDefine.getKey(), formKey);
                    for (DesignFormulaDefine designFormulaDefine : formulaList) {
                        if (StringUtils.isNotEmpty((String)newFormulaSchemeKey)) {
                            designFormulaDefine.setFormulaSchemeKey(newFormulaSchemeKey);
                            updateList.add(designFormulaDefine);
                            continue;
                        }
                        delFormulakeys.add(designFormulaDefine.getKey());
                    }
                }
            }
            if (delFormulakeys.size() > 0) {
                this.nrDesignTimeController.deleteFormulaDefines(delFormulakeys.toArray(new String[0]));
            }
            if (updateList.size() > 0) {
                this.nrDesignTimeController.updateFormulaDefines(updateList.toArray(new DesignFormulaDefine[0]));
            }
        }
    }

    private void updatePublicFormulaSchemeKey(MoveFormVO moveFormVo) throws JQException {
        boolean formulaNeedUpdate;
        HashSet<String> groupSet = new HashSet<String>();
        groupSet.add(moveFormVo.sourceGroupKey);
        HashMap<String, List<String>> map = this.getGroupKeyByFormSchemeKey(groupSet);
        ArrayList<String> delFormulakeys = new ArrayList<String>();
        ArrayList<DesignFormulaDefine> updateList = new ArrayList<DesignFormulaDefine>();
        DesignFormGroupDefine groupDefine = this.nrDesignTimeController.queryFormGroup(moveFormVo.sourceGroupKey);
        String currFormSchemeKey = groupDefine.getFormSchemeKey();
        List<String> groups = map.get(currFormSchemeKey);
        boolean bl = formulaNeedUpdate = groups == null || groups.size() == 0;
        if (formulaNeedUpdate) {
            List allFormulaSchemeDefines = this.nrDesignTimeController.getAllFormulaSchemeDefinesByFormScheme(currFormSchemeKey);
            for (DesignFormulaSchemeDefine designFormulaSchemeDefine : allFormulaSchemeDefines) {
                String newFormulaSchemeKey = this.getNewFormulaSchemeKey(map, currFormSchemeKey, designFormulaSchemeDefine);
                List formulaList = this.nrDesignTimeController.getAllSoftFormulasInForm(designFormulaSchemeDefine.getKey(), moveFormVo.formKey);
                for (DesignFormulaDefine designFormulaDefine : formulaList) {
                    if (StringUtils.isNotEmpty((String)newFormulaSchemeKey)) {
                        designFormulaDefine.setFormulaSchemeKey(newFormulaSchemeKey);
                        updateList.add(designFormulaDefine);
                        continue;
                    }
                    delFormulakeys.add(designFormulaDefine.getKey());
                }
            }
        }
        if (delFormulakeys.size() > 0) {
            this.nrDesignTimeController.deleteFormulaDefines(delFormulakeys.toArray(new String[0]));
        }
        if (updateList.size() > 0) {
            this.nrDesignTimeController.updateFormulaDefines(updateList.toArray(new DesignFormulaDefine[0]));
        }
    }

    private String getNewFormulaSchemeKey(HashMap<String, List<String>> map, String formSchemeKey, DesignFormulaSchemeDefine designFormulaSchemeDefine) {
        String newFormulaSchemeKey = null;
        block0: for (Map.Entry<String, List<String>> entry : map.entrySet()) {
            if (entry.getKey().equals(formSchemeKey)) continue;
            List otherFormulaSchemeDefines = this.nrDesignTimeController.getAllFormulaSchemeDefinesByFormScheme(entry.getKey());
            for (DesignFormulaSchemeDefine formulaSchemeDefine : otherFormulaSchemeDefines) {
                if (!formulaSchemeDefine.getTitle().equals(designFormulaSchemeDefine.getTitle()) || formulaSchemeDefine.getFormulaSchemeType() != designFormulaSchemeDefine.getFormulaSchemeType()) continue;
                newFormulaSchemeKey = formulaSchemeDefine.getKey();
                break block0;
            }
        }
        return newFormulaSchemeKey;
    }

    private HashMap<String, List<String>> getGroupKeyByFormSchemeKey(Set<String> groupSet) {
        HashMap<String, List<String>> map = new HashMap<String, List<String>>();
        for (String formGroupKey : groupSet) {
            DesignFormGroupDefine designFormGroupDefine = this.nrDesignTimeController.queryFormGroup(formGroupKey);
            if (designFormGroupDefine == null) continue;
            String formSchemeKey = designFormGroupDefine.getFormSchemeKey();
            List<String> groups = map.get(formSchemeKey);
            if (groups == null) {
                groups = new ArrayList<String>();
                map.put(formSchemeKey, groups);
            }
            groups.add(formGroupKey);
        }
        return map;
    }

    @Transactional(rollbackFor={Exception.class})
    public void saveFormulas(FormulaObj[] formulas) throws JQException, ParseException {
        if (formulas == null) {
            return;
        }
        ArrayList<DesignFormulaDefine> needCreateFormula = new ArrayList<DesignFormulaDefine>();
        ArrayList<String> needCreateFormulaCode = new ArrayList<String>();
        ArrayList<DesignFormulaDefine> needUpdateFormula = new ArrayList<DesignFormulaDefine>();
        ArrayList<DesignFormulaDefine> needDeleteFormulas = new ArrayList<DesignFormulaDefine>();
        ArrayList<String> needUpdateFormulaCode = new ArrayList<String>();
        ArrayList<String> needDeleteFormula = new ArrayList<String>();
        ArrayList<String> needDeleteFormulaCode = new ArrayList<String>();
        HashSet<String> formSet = new HashSet<String>();
        HashMap<String, DesignFormulaDefine> formulaMap = new HashMap<String, DesignFormulaDefine>();
        HashMap<String, List<String>> linkMap = new HashMap<String, List<String>>();
        HashMap<String, Boolean> canEditMap = new HashMap<String, Boolean>();
        String formulaScheme = null;
        for (FormulaObj formulaObj : formulas) {
            if (formulaObj == null) continue;
            if (formulaScheme == null) {
                formulaScheme = formulaObj.getSchemeKey();
            }
            this.addFormulaConditions(linkMap, formulaObj);
            Boolean taskCanEdit = (Boolean)canEditMap.get(formulaObj.getSchemeKey());
            if (null == taskCanEdit) {
                DesignFormSchemeDefine formSchemeDefine;
                DesignFormulaSchemeDefine designFormulaSchemeDefine;
                canEditMap.put(formulaObj.getSchemeKey(), false);
                if (StringUtils.isNotEmpty((String)formulaObj.getSchemeKey()) && null != (designFormulaSchemeDefine = this.nrDesignTimeController.queryFormulaSchemeDefine(formulaObj.getSchemeKey())) && StringUtils.isNotEmpty((String)designFormulaSchemeDefine.getFormSchemeKey()) && null != (formSchemeDefine = this.nrDesignTimeController.queryFormSchemeDefine(designFormulaSchemeDefine.getFormSchemeKey())) && StringUtils.isNotEmpty((String)formSchemeDefine.getTaskKey())) {
                    boolean b = this.taskPlanPublishExternalService.taskCanEdit(formSchemeDefine.getTaskKey());
                    canEditMap.put(formulaObj.getSchemeKey(), b);
                }
                if (!((Boolean)canEditMap.get(formulaObj.getSchemeKey())).booleanValue()) {
                    throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_214);
                }
            } else if (!taskCanEdit.booleanValue()) {
                throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_215);
            }
            String formulaID = formulaObj.getId();
            if (formulaObj.isIsDeleted()) {
                if (formulaID == null) continue;
                needDeleteFormula.add(formulaID);
                needDeleteFormulaCode.add(formulaObj.getCode());
                DesignFormulaDefine deleteTemp = this.nrDesignTimeController.createFormulaDefine();
                deleteTemp.setKey(formulaID);
                deleteTemp.setCode(formulaObj.getCode());
                needDeleteFormulas.add(deleteTemp);
                continue;
            }
            if (formulaObj.isIsNew()) {
                DesignFormulaDefine designFormulaDefine = this.nrDesignTimeController.createFormulaDefine();
                designFormulaDefine.setKey(formulaID);
                formulaObj.setOwnerLevelAndId(this.serveCodeService.getServeCode());
                this.initFormulaDefine(designFormulaDefine, formulaObj);
                needCreateFormula.add(designFormulaDefine);
                needCreateFormulaCode.add(designFormulaDefine.getCode());
                continue;
            }
            if (!formulaObj.isIsDirty()) continue;
            String formKey = formulaObj.getFormKey();
            String string = formKey = formKey == null ? "BJ" : formKey;
            if (formSet.add(formKey)) {
                List formulasInForm = this.nrDesignTimeController.getAllSoftFormulasInForm(formulaObj.getSchemeKey(), formulaObj.getFormKey());
                for (DesignFormulaDefine formula : formulasInForm) {
                    formulaMap.put(formula.getKey(), formula);
                }
            }
            DesignFormulaDefine designFormulaDefine = (DesignFormulaDefine)formulaMap.get(formulaObj.getId());
            this.initFormulaDefine(designFormulaDefine, formulaObj);
            designFormulaDefine.setUpdateTime(new Date());
            needUpdateFormula.add(designFormulaDefine);
            needUpdateFormulaCode.add(designFormulaDefine.getCode());
        }
        if (needDeleteFormula.size() > 0) {
            this.nrDesignTimeController.deleteFormulaDefines(needDeleteFormula.toArray(new String[0]));
        }
        List<String> checkFormulaCode = this.checkFormulaCode(needCreateFormula, needUpdateFormula);
        if (needDeleteFormula.size() > 0) {
            String logTitle = "\u5220\u9664\u516c\u5f0f";
            if (checkFormulaCode.size() > 0) {
                throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_023, checkFormulaCode);
            }
        }
        if (checkFormulaCode.size() > 0) {
            throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_023, checkFormulaCode);
        }
        if (needUpdateFormula.size() > 0) {
            String logTitle = "\u4fee\u6539\u516c\u5f0f";
            this.nrDesignTimeController.updateFormulaDefines(needUpdateFormula.toArray(new DesignFormulaDefine[0]));
        }
        if (needCreateFormula.size() > 0) {
            String logTitle = "\u65b0\u589e\u516c\u5f0f";
            this.nrDesignTimeController.insertFormulaDefines(needCreateFormula.toArray(new DesignFormulaDefine[0]));
        }
        this.saveFormulaConditions(formulaScheme, linkMap);
    }

    private void addFormulaConditions(Map<String, List<String>> linkMap, FormulaObj formulaObj) {
        if (formulaObj != null) {
            List<FormulaConditionLinkObj> formulaConditions = formulaObj.getFormulaConditions();
            if (formulaObj.isIsDeleted()) {
                linkMap.put(formulaObj.getId(), Collections.emptyList());
            } else if (CollectionUtils.isEmpty(formulaConditions)) {
                linkMap.put(formulaObj.getId(), Collections.emptyList());
            } else {
                linkMap.put(formulaObj.getId(), formulaConditions.stream().filter(x -> !Boolean.FALSE.equals(x.getBinding())).map(FormulaConditionLinkObj::getKey).collect(Collectors.toList()));
            }
        }
    }

    private void saveFormulaConditions(String formulaScheme, Map<String, List<String>> linkMap) throws JQException {
        if (formulaScheme == null) {
            return;
        }
        this.nrDesignTimeController.deleteFormulaConditionLinkByFormula(linkMap.keySet().toArray(new String[0]));
        ArrayList<DesignFormulaConditionLink> inserts = new ArrayList<DesignFormulaConditionLink>();
        for (Map.Entry<String, List<String>> entry : linkMap.entrySet()) {
            for (String s : entry.getValue()) {
                inserts.add(this.convertLink(formulaScheme, entry.getKey(), s));
            }
        }
        try {
            DesignFormulaSchemeDefine schemeDefine = this.nrDesignTimeController.queryFormulaSchemeDefine(formulaScheme);
            schemeDefine.setUpdateTime(new Date());
            this.nrDesignTimeController.updateFormulaSchemeDefine(schemeDefine);
            this.nrDesignTimeController.insertFormulaConditionLinks(inserts);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_224, (Throwable)e);
        }
    }

    private DesignFormulaConditionLink convertLink(String formulaScheme, String key, String s) {
        DesignFormulaConditionLink link = this.nrDesignTimeController.createDesignFormulaConditionLink();
        link.setConditionKey(s);
        link.setFormulaKey(key);
        link.setFormulaSchemeKey(formulaScheme);
        return link;
    }

    private String calcFormulaLog(List<DesignFormulaDefine> formulas) {
        String codeStr = "\u516c\u5f0f\u7f16\u53f7\uff1a";
        String keyStr = "\u516c\u5f0f\u4e3b\u952e\uff1a";
        ArrayList<String> logs = new ArrayList<String>();
        for (DesignFormulaDefine formula : formulas) {
            StringBuffer sbf = new StringBuffer(codeStr);
            sbf.append(formula.getCode()).append(",");
            sbf.append(keyStr).append(formula.getKey());
            logs.add(sbf.toString());
        }
        return StringUtils.join(logs.iterator(), (String)";");
    }

    private Map<String, DesignFormulaDefine> getKeyDefineMap(String formulaSchemeKey) throws JQException {
        List formulaDefines = this.nrDesignTimeController.querySimpleFormulaDefineByScheme(formulaSchemeKey);
        HashMap<String, DesignFormulaDefine> map = new HashMap<String, DesignFormulaDefine>();
        for (DesignFormulaDefine formulaDefine : formulaDefines) {
            map.put(formulaDefine.getKey(), formulaDefine);
        }
        return map;
    }

    private List<String> checkFormulaCode(List<DesignFormulaDefine> needCreateFormula, List<DesignFormulaDefine> needUpdateFormula) throws JQException {
        ArrayList<String> errorFormulaCode = new ArrayList<String>();
        String formulaSchemeKey = needCreateFormula.size() > 0 ? needCreateFormula.get(0).getFormulaSchemeKey() : (needUpdateFormula.size() > 0 ? needUpdateFormula.get(0).getFormulaSchemeKey() : null);
        Map codeCountMap = this.nrDesignTimeController.getFormulaCodeCountByScheme(formulaSchemeKey);
        Map<String, DesignFormulaDefine> keyDefineMap = this.getKeyDefineMap(formulaSchemeKey);
        for (DesignFormulaDefine updateFormula : needUpdateFormula) {
            if (updateFormula.getCode() == null) {
                throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_024);
            }
            DesignFormulaDefine formula = keyDefineMap.get(updateFormula.getKey());
            Integer oldf = (Integer)codeCountMap.get(formula.getCode());
            oldf = oldf - 1;
            codeCountMap.put(formula.getCode(), oldf);
            this.addCodeValue(codeCountMap, updateFormula);
        }
        if (needCreateFormula.size() > 0) {
            for (DesignFormulaDefine createFormula : needCreateFormula) {
                if (createFormula.getCode() == null) {
                    throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_024);
                }
                this.addCodeValue(codeCountMap, createFormula);
            }
        }
        ArrayList<DesignFormulaDefine> allFormulas = new ArrayList<DesignFormulaDefine>();
        allFormulas.addAll(needCreateFormula);
        allFormulas.addAll(needUpdateFormula);
        for (DesignFormulaDefine formula : allFormulas) {
            Integer i = (Integer)codeCountMap.get(formula.getCode());
            if (i <= 1) continue;
            errorFormulaCode.add(formula.getCode());
        }
        return errorFormulaCode;
    }

    private void addCodeValue(Map<String, Integer> m, DesignFormulaDefine define) {
        Integer newf = m.get(define.getCode());
        if (newf == null) {
            newf = 0;
        }
        newf = newf + 1;
        m.put(define.getCode(), newf);
    }

    private DesignFormulaDefine initFormulaDefine(DesignFormulaDefine designFormulaDefine, FormulaObj formulaObj) {
        designFormulaDefine.setCode(formulaObj.getCode());
        designFormulaDefine.setExpression(formulaObj.getExpression());
        if (!StringUtils.isEmpty((String)formulaObj.getOrder())) {
            designFormulaDefine.setOrder(formulaObj.getOrder());
        }
        int checkType = formulaObj.getCheckType();
        designFormulaDefine.setCheckType(checkType);
        designFormulaDefine.setDescription(formulaObj.getDescription());
        designFormulaDefine.setUseCalculate(formulaObj.isUseCalculate());
        designFormulaDefine.setUseCheck(formulaObj.isUseCheck());
        designFormulaDefine.setUseBalance(formulaObj.isUseBalance());
        designFormulaDefine.setBalanceZBExp(formulaObj.getBalanceZBExp());
        if (!StringUtils.isEmpty((String)formulaObj.getSchemeKey())) {
            if (formulaObj.isIsNew()) {
                designFormulaDefine.setFormulaSchemeKey(formulaObj.getSchemeKey());
            }
            if (formulaObj.isIsDirty()) {
                designFormulaDefine.setFormulaSchemeKey(formulaObj.getSchemeKey());
            }
        }
        if (!StringUtils.isEmpty((String)formulaObj.getFormKey())) {
            designFormulaDefine.setFormKey(formulaObj.getFormKey());
        }
        designFormulaDefine.setOwnerLevelAndId(formulaObj.getOwnerLevelAndId());
        return designFormulaDefine;
    }

    public String addFormSchemeInTask(String taskKey, String formSchemeTitle) throws Exception {
        DesignFormSchemeDefine formSchemeDefine = this.nrDesignTimeController.createFormSchemeDefine();
        formSchemeDefine.setTaskKey(taskKey);
        formSchemeDefine.setTitle(formSchemeTitle);
        formSchemeDefine.setFormSchemeCode(OrderGenerator.newOrder());
        formSchemeDefine.setOwnerLevelAndId(this.serveCodeService.getServeCode());
        String schemeKey = this.nrDesignTimeController.insertFormSchemeDefine(formSchemeDefine);
        this.addFormGroupInScheme(schemeKey);
        this.addFormulaSchemeInScheme(schemeKey);
        this.createDefaultPrintScheme(taskKey, schemeKey);
        return schemeKey;
    }

    @Transactional(rollbackFor={Exception.class})
    public String createDefaultPrintScheme(String taskKey, String fromSchemeKey) throws Exception {
        DesignPrintTemplateSchemeDefine designPirntSchemeDefine = this.nrDesignTimeController.createPrintTemplateSchemeDefine();
        designPirntSchemeDefine.setTitle(DEFAULT_PRINTSCHEME_TITLE);
        designPirntSchemeDefine.setOrder(OrderGenerator.newOrder());
        designPirntSchemeDefine.setFormSchemeKey(fromSchemeKey);
        designPirntSchemeDefine.setTaskKey(taskKey);
        designPirntSchemeDefine.setOwnerLevelAndId(this.serveCodeService.getServeCode());
        this.nrDesignTimeController.setPrintSchemeAttribute(designPirntSchemeDefine, PrintAttributeVo.defaultAttributeDefine());
        return this.nrDesignTimeController.insertPrintTemplateSchemeDefine(designPirntSchemeDefine);
    }

    private void addFormulaSchemeInScheme(String schemeKey) throws JQException {
        DesignFormulaSchemeDefine formulaSchemeDefine = this.nrDesignTimeController.createFormulaSchemeDefine();
        formulaSchemeDefine.setTitle("\u9ed8\u8ba4\u516c\u5f0f\u65b9\u6848");
        formulaSchemeDefine.setFormSchemeKey(schemeKey);
        formulaSchemeDefine.setDefault(true);
        formulaSchemeDefine.setOwnerLevelAndId(this.serveCodeService.getServeCode());
        formulaSchemeDefine.setFormulaSchemeType(FormulaSchemeType.FORMULA_SCHEME_TYPE_REPORT);
        this.nrDesignTimeController.insertFormulaSchemeDefine(formulaSchemeDefine);
    }

    private void addFormGroupInScheme(String schemeKey) throws JQException {
        DesignFormGroupDefine formGroup = this.nrDesignTimeController.createFormGroup();
        formGroup.setFormSchemeKey(schemeKey);
        formGroup.setCode(OrderGenerator.newOrder());
        formGroup.setOrder(OrderGenerator.newOrder());
        formGroup.setOwnerLevelAndId(this.serveCodeService.getServeCode());
        formGroup.setTitle("\u9ed8\u8ba4\u8868\u5355\u5206\u7ec4");
        this.nrDesignTimeController.insertFormGroup(formGroup);
    }

    public String getSchemeTitle(String taskKey) throws JQException {
        List formSchemeDefines = this.nrDesignTimeController.queryFormSchemeByTask(taskKey);
        String schemeTitle = "";
        int idx = 1;
        boolean exists = false;
        block0: do {
            exists = false;
            schemeTitle = "\u62a5\u8868\u65b9\u6848" + idx++;
            for (DesignFormSchemeDefine scheme : formSchemeDefines) {
                if (scheme.getTitle() == null || !scheme.getTitle().equals(schemeTitle)) continue;
                exists = true;
                continue block0;
            }
        } while (exists);
        return schemeTitle;
    }
}

