/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.np.definition.progress.ProgressCacheService
 *  com.jiuqi.np.definition.progress.ProgressItem
 *  com.jiuqi.nr.common.itree.INode
 *  com.jiuqi.nr.common.itree.ITree
 *  com.jiuqi.nr.definition.common.FormulaSchemeType
 *  com.jiuqi.nr.definition.common.TaskType
 *  com.jiuqi.nr.definition.controller.IDesignTimeViewController
 *  com.jiuqi.nr.definition.facade.DesignFormDefine
 *  com.jiuqi.nr.definition.facade.DesignFormGroupDefine
 *  com.jiuqi.nr.definition.facade.DesignFormSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignFormulaSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignPrintTemplateSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.internal.controller.NRDesignTimeController
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  org.springframework.transaction.annotation.Transactional
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestMethod
 */
package com.jiuqi.nr.designer.web.rest;

import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.np.definition.progress.ProgressCacheService;
import com.jiuqi.np.definition.progress.ProgressItem;
import com.jiuqi.nr.common.itree.INode;
import com.jiuqi.nr.common.itree.ITree;
import com.jiuqi.nr.definition.common.FormulaSchemeType;
import com.jiuqi.nr.definition.common.TaskType;
import com.jiuqi.nr.definition.controller.IDesignTimeViewController;
import com.jiuqi.nr.definition.facade.DesignFormDefine;
import com.jiuqi.nr.definition.facade.DesignFormGroupDefine;
import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignFormulaSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignPrintTemplateSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.internal.controller.NRDesignTimeController;
import com.jiuqi.nr.designer.common.NrDesignLogHelper;
import com.jiuqi.nr.designer.common.NrDesingerErrorEnum;
import com.jiuqi.nr.designer.formcopy.FormCopyAttSchemeMap;
import com.jiuqi.nr.designer.formcopy.FormCopyParams;
import com.jiuqi.nr.designer.formcopy.FormCopyRecordPush;
import com.jiuqi.nr.designer.formcopy.FormSyncParams;
import com.jiuqi.nr.designer.formcopy.FormSyncPushResult;
import com.jiuqi.nr.designer.formcopy.FormulaSyncResult;
import com.jiuqi.nr.designer.formcopy.FormulaSyncResultAll;
import com.jiuqi.nr.designer.formcopy.IDesignFormCopyService;
import com.jiuqi.nr.designer.formcopy.bean.IFormCopyAttSchemeInfo;
import com.jiuqi.nr.designer.formcopy.bean.IFormCopyInfo;
import com.jiuqi.nr.designer.formcopy.common.SchemeType;
import com.jiuqi.nr.designer.planpublish.model.TaskPlanPublishObj;
import com.jiuqi.nr.designer.planpublish.service.TaskPlanPublishExternalService;
import com.jiuqi.nr.designer.planpublish.service.TaskPlanPublishService;
import com.jiuqi.nr.designer.planpublish.web.TaskPlanPublishController;
import com.jiuqi.nr.designer.service.impl.DeployManager;
import com.jiuqi.nr.designer.web.facade.FormCopyCheck;
import com.jiuqi.nr.designer.web.facade.FormCopyFormTreeNode;
import com.jiuqi.nr.designer.web.facade.FormCopyInfoCheckVO;
import com.jiuqi.nr.designer.web.facade.FormCopyInfoVO;
import com.jiuqi.nr.designer.web.facade.FormCopyLinksVO;
import com.jiuqi.nr.designer.web.facade.FormCopyOptions;
import com.jiuqi.nr.designer.web.facade.FormCopyParamsVO;
import com.jiuqi.nr.designer.web.facade.FormCopyPushLinkVO;
import com.jiuqi.nr.designer.web.facade.FormCopyPushPublishResult;
import com.jiuqi.nr.designer.web.facade.FormCopySchemeVO;
import com.jiuqi.nr.designer.web.facade.FormDoCopyParams;
import com.jiuqi.nr.designer.web.facade.FormSyncPushExecuteVO;
import com.jiuqi.nr.designer.web.facade.FormSyncPushSchemeVO;
import com.jiuqi.nr.designer.web.facade.FormSyncPushVO;
import com.jiuqi.nr.designer.web.facade.FormSyncRecordPushVO;
import com.jiuqi.nr.designer.web.facade.vo.ProgressVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@JQRestController
@RequestMapping(value={"api/v1/designer/"})
@Api(tags={"\u63a8\u9001\u62a5\u8868\u590d\u5236\u53ca\u540c\u6b65\u6a21\u5757"})
public class FormCopyPushController {
    @Autowired
    private IDesignFormCopyService iDesignFormCopyService;
    @Autowired
    private NRDesignTimeController nrDesignTimeController;
    @Autowired
    private DeployManager deployManager;
    @Autowired
    private ProgressCacheService progressCacheService;
    @Autowired
    private TaskPlanPublishService taskPlanPublishService;
    @Autowired
    private TaskPlanPublishExternalService taskPlanPublishExternalService;
    @Autowired
    private IDesignTimeViewController designTimeViewService;
    private static final String DONT_COPY_KEY = "0000-0000";
    private static final String DONT_COPY_TITLE = "\u4e0d\u590d\u5236";
    private static final String FORMULA = "FORMULA";
    private static final String FIFORMULA = "FIFORMULA";
    private static final String PRINT = "PRINT";
    private static final Logger logger = LoggerFactory.getLogger(FormCopyPushController.class);

    @ApiOperation(value="\u6839\u636e\u62a5\u8868\u65b9\u6848\u67e5\u8be2\u8868\u5355\u5206\u7ec4\u548c\u62a5\u8868\u6570\u636e\u6811")
    @RequestMapping(value={"/get-formcopy-group-tree/{srcFormSchemeKey}"}, method={RequestMethod.GET})
    public List<ITree<FormCopyFormTreeNode>> getGroupTree(@PathVariable String srcFormSchemeKey) throws JQException {
        try {
            FormCopyCheck formCopyCheck = FormCopyCheck.valueOf("REPEATCOPY");
            for (FormCopyCheck value : FormCopyCheck.values()) {
                System.out.println((Object)value);
                String string = value.name();
            }
            ArrayList tree_formGroup = new ArrayList();
            List formGroupsDefines = this.nrDesignTimeController.queryRootGroupsByFormScheme(srcFormSchemeKey);
            if (formGroupsDefines != null) {
                formGroupsDefines.forEach(formGroup -> {
                    ITree<FormCopyFormTreeNode> formGroupTree = this.getTreeNode((DesignFormGroupDefine)formGroup);
                    ArrayList tree_form = new ArrayList();
                    List formDefine = this.nrDesignTimeController.getAllFormsInGroupWithoutBinaryData(((FormCopyFormTreeNode)formGroupTree.getData()).getKey());
                    formDefine.sort(Comparator.comparing(IBaseMetaItem::getOrder));
                    formDefine.forEach(form -> tree_form.add(this.getTreeNode((DesignFormDefine)form, ((FormCopyFormTreeNode)formGroupTree.getData()).getKey())));
                    formGroupTree.setChildren(tree_form);
                    if (tree_form.size() > 0) {
                        tree_formGroup.add(formGroupTree);
                    }
                });
            }
            ArrayList<ITree<FormCopyFormTreeNode>> result = new ArrayList<ITree<FormCopyFormTreeNode>>();
            DesignFormSchemeDefine designFormSchemeDefine = this.nrDesignTimeController.queryFormSchemeDefine(srcFormSchemeKey);
            ITree rootNode = new ITree((INode)new FormCopyFormTreeNode(srcFormSchemeKey, designFormSchemeDefine.getFormSchemeCode(), designFormSchemeDefine.getTitle()));
            rootNode.setLeaf(false);
            rootNode.setNoDrop(true);
            rootNode.setNoDrag(true);
            rootNode.setExpanded(true);
            rootNode.setDisabled(true);
            rootNode.setChildren(tree_formGroup);
            result.add(rootNode);
            return result;
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_197, (Throwable)e);
        }
    }

    private ITree<FormCopyFormTreeNode> getTreeNode(DesignFormGroupDefine formGroup) {
        ITree node = new ITree((INode)new FormCopyFormTreeNode(formGroup));
        node.setLeaf(false);
        node.setNoDrop(true);
        node.setNoDrag(true);
        return node;
    }

    private ITree<FormCopyFormTreeNode> getTreeNode(DesignFormDefine formDefine, String formGroupKey) {
        ITree node = new ITree((INode)new FormCopyFormTreeNode(formDefine, formGroupKey));
        node.setTitle(formDefine.getFormCode() + " | " + formDefine.getTitle());
        node.setLeaf(true);
        node.setNoDrop(true);
        node.setNoDrag(true);
        return node;
    }

    @ApiOperation(value="\u67e5\u8be2\u62a5\u8868\u65b9\u6848\u4e0b\u7684\u516c\u5f0f\u548c\u6253\u5370\u65b9\u6848\u4fe1\u606f")
    @RequestMapping(value={"/get-formScheme-formula-print/{srcFormSchemeKey}"}, method={RequestMethod.GET})
    public FormCopyLinksVO getSrcScheme(@PathVariable String srcFormSchemeKey) throws JQException {
        FormCopyLinksVO formCopyLinksVO = new FormCopyLinksVO();
        DesignFormSchemeDefine srcFormSchemeDefine = this.nrDesignTimeController.queryFormSchemeDefine(srcFormSchemeKey);
        DesignTaskDefine srcTaskDefine = this.nrDesignTimeController.queryTaskDefine(srcFormSchemeDefine.getTaskKey());
        boolean ifFiFormula = srcTaskDefine.getEfdcSwitch();
        Map<String, List<FormCopySchemeVO>> srcSchemeMap = this.querySchemesInFormScheme(srcFormSchemeKey, ifFiFormula);
        formCopyLinksVO.setSrcFormulaSchemes(srcSchemeMap.get(FORMULA));
        formCopyLinksVO.setSrcFiFormulaSchemes(srcSchemeMap.get(FIFORMULA));
        formCopyLinksVO.setSrcPrintFormulaSchemes(srcSchemeMap.get(PRINT));
        return formCopyLinksVO;
    }

    private Map<String, List<FormCopySchemeVO>> querySchemesInFormScheme(String formSchemeKey, boolean ifFiFormulaOpen) throws JQException {
        HashMap<String, List<FormCopySchemeVO>> schemesInFormSchemeMap = new HashMap<String, List<FormCopySchemeVO>>();
        List allFormulaSchemeDefinesByFormScheme = this.nrDesignTimeController.getAllFormulaSchemeDefinesByFormScheme(formSchemeKey);
        ArrayList<FormCopySchemeVO> formulaSchemes = new ArrayList<FormCopySchemeVO>();
        ArrayList<FormCopySchemeVO> fiFormulaSchemes = new ArrayList<FormCopySchemeVO>();
        for (DesignFormulaSchemeDefine designFormulaSchemeDefine : allFormulaSchemeDefinesByFormScheme) {
            if (designFormulaSchemeDefine.getFormulaSchemeType().equals((Object)FormulaSchemeType.FORMULA_SCHEME_TYPE_REPORT)) {
                formulaSchemes.add(new FormCopySchemeVO(designFormulaSchemeDefine.getKey(), designFormulaSchemeDefine.getTitle()));
                continue;
            }
            if (!ifFiFormulaOpen || !designFormulaSchemeDefine.getFormulaSchemeType().equals((Object)FormulaSchemeType.FORMULA_SCHEME_TYPE_FINANCIAL)) continue;
            fiFormulaSchemes.add(new FormCopySchemeVO(designFormulaSchemeDefine.getKey(), designFormulaSchemeDefine.getTitle()));
        }
        schemesInFormSchemeMap.put(FORMULA, formulaSchemes);
        schemesInFormSchemeMap.put(FIFORMULA, fiFormulaSchemes);
        try {
            List allPrintSchemeByFormScheme = this.nrDesignTimeController.getAllPrintSchemeByFormScheme(formSchemeKey);
            ArrayList<FormCopySchemeVO> printSchemes = new ArrayList<FormCopySchemeVO>();
            for (DesignPrintTemplateSchemeDefine printScheme : allPrintSchemeByFormScheme) {
                printSchemes.add(new FormCopySchemeVO(printScheme.getKey(), printScheme.getTitle()));
            }
            schemesInFormSchemeMap.put(PRINT, printSchemes);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_194, (Throwable)e);
        }
        return schemesInFormSchemeMap;
    }

    @ApiOperation(value="\u6839\u636e\u6765\u6e90\u62a5\u8868\u65b9\u6848\u548c\u76ee\u6807\u62a5\u8868\u65b9\u6848\u67e5\u8be2\u4e0d\u5e26\u6765\u6e90\u65b9\u6848\u4fe1\u606f\u7684\u6620\u5c04\u5173\u7cfb")
    @RequestMapping(value={"/get-links-without-src"}, method={RequestMethod.POST})
    public Map<String, FormCopyLinksVO> getSrcDesLinks(@RequestBody FormCopyPushLinkVO formCopyPushLinkVO) throws JQException {
        boolean isFiFormula = formCopyPushLinkVO.isFiFormula();
        List<String> desFormSchemeKeys = formCopyPushLinkVO.getDesFormSchemeKeys();
        String srcFormSchemeKey = formCopyPushLinkVO.getSrcFormSchemeKey();
        List<String> desFormSchemeNoExistIfFiFormulas = formCopyPushLinkVO.getDesFormSchemeNoExistIfFiFormulas();
        HashMap<String, FormCopyLinksVO> result = new HashMap<String, FormCopyLinksVO>();
        Map<String, List<FormCopySchemeVO>> srcFormSchemeMap = this.querySchemesInFormScheme(srcFormSchemeKey, isFiFormula);
        Map<String, FormCopySchemeVO> srcFormSchemeFormulaMap = srcFormSchemeMap.get(FORMULA).stream().collect(Collectors.toMap(FormCopySchemeVO::getTitle, a -> a));
        Map<String, FormCopySchemeVO> srcFormSchemeFiFormulaMap = srcFormSchemeMap.get(FIFORMULA).stream().collect(Collectors.toMap(FormCopySchemeVO::getTitle, a -> a));
        Map<String, FormCopySchemeVO> srcFormSchemePrintMap = srcFormSchemeMap.get(PRINT).stream().collect(Collectors.toMap(FormCopySchemeVO::getTitle, a -> a));
        for (int i = 0; i < desFormSchemeKeys.size(); ++i) {
            String srcKey;
            String desFormSchemeKey = desFormSchemeKeys.get(i);
            boolean ifFiFormula = desFormSchemeNoExistIfFiFormulas.get(i).equals("1");
            FormCopyLinksVO formCopyLinksVO = new FormCopyLinksVO();
            Map<String, List<FormCopySchemeVO>> desSchemeMap = this.querySchemesInFormScheme(desFormSchemeKey, ifFiFormula && isFiFormula);
            List<FormCopySchemeVO> formFormulaCopySchemeVOS = desSchemeMap.get(FORMULA);
            formFormulaCopySchemeVOS.add(0, new FormCopySchemeVO(DONT_COPY_KEY, DONT_COPY_TITLE));
            List<FormCopySchemeVO> fiFormulaCopySchemeVOS = desSchemeMap.get(FIFORMULA);
            fiFormulaCopySchemeVOS.add(0, new FormCopySchemeVO(DONT_COPY_KEY, DONT_COPY_TITLE));
            List<FormCopySchemeVO> printCopySchemeVOS = desSchemeMap.get(PRINT);
            printCopySchemeVOS.add(0, new FormCopySchemeVO(DONT_COPY_KEY, DONT_COPY_TITLE));
            formCopyLinksVO.setDesFormulaSchemes(formFormulaCopySchemeVOS);
            formCopyLinksVO.setDesFiFormulaSchemes(fiFormulaCopySchemeVOS);
            formCopyLinksVO.setDesPrintFormulaSchemes(printCopySchemeVOS);
            List<IFormCopyAttSchemeInfo> formCopySchemeInfo = this.iDesignFormCopyService.getFormCopySchemeInfo(desFormSchemeKey, srcFormSchemeKey);
            Map<String, Map<String, String>> attSchemeMap = this.decomposeScheme(formCopySchemeInfo, isFiFormula);
            Map<String, String> copyFormulaMap = attSchemeMap.get(FORMULA);
            Map<String, String> copyFiFormulaMap = attSchemeMap.get(FIFORMULA);
            Map<String, String> copyPrintMap = attSchemeMap.get(PRINT);
            for (FormCopySchemeVO formula : formFormulaCopySchemeVOS) {
                if (!srcFormSchemeFormulaMap.containsKey(formula.getTitle()) || copyFormulaMap.containsKey(srcKey = srcFormSchemeFormulaMap.get(formula.getTitle()).getKey())) continue;
                copyFormulaMap.put(srcKey, formula.getKey());
            }
            for (FormCopySchemeVO fiFormula : fiFormulaCopySchemeVOS) {
                if (!srcFormSchemeFiFormulaMap.containsKey(fiFormula.getTitle()) || copyFiFormulaMap.containsKey(srcKey = srcFormSchemeFiFormulaMap.get(fiFormula.getTitle()).getKey())) continue;
                copyFiFormulaMap.put(srcKey, fiFormula.getKey());
            }
            for (FormCopySchemeVO print : printCopySchemeVOS) {
                if (!srcFormSchemePrintMap.containsKey(print.getTitle()) || copyPrintMap.containsKey(srcKey = srcFormSchemePrintMap.get(print.getTitle()).getKey())) continue;
                copyPrintMap.put(srcKey, print.getKey());
            }
            formCopyLinksVO.setFormulaMap(copyFormulaMap);
            formCopyLinksVO.setFiFormulaMap(copyFiFormulaMap);
            formCopyLinksVO.setPrintMap(copyPrintMap);
            result.put(desFormSchemeKey, formCopyLinksVO);
        }
        return result;
    }

    private Map<String, Map<String, String>> decomposeScheme(List<IFormCopyAttSchemeInfo> formCopySchemeInfo, boolean ifFiFormulaOpen) {
        HashMap<String, Map<String, String>> attSchemeMap = new HashMap<String, Map<String, String>>();
        HashMap<String, String> formulaMap = new HashMap<String, String>();
        HashMap<String, String> fiFormulaMap = new HashMap<String, String>();
        HashMap<String, String> printMap = new HashMap<String, String>();
        if (formCopySchemeInfo != null && !formCopySchemeInfo.isEmpty()) {
            for (IFormCopyAttSchemeInfo linkInfo : formCopySchemeInfo) {
                switch (linkInfo.getSchemeType()) {
                    case FORMULA_SCHEME: {
                        formulaMap.put(linkInfo.getSrcSchemeKey(), linkInfo.getSchemeKey());
                        break;
                    }
                    case FIFORMULA_SCHEME: {
                        if (!ifFiFormulaOpen) break;
                        fiFormulaMap.put(linkInfo.getSrcSchemeKey(), linkInfo.getSchemeKey());
                        break;
                    }
                    case PRINT_SCHEME: {
                        printMap.put(linkInfo.getSrcSchemeKey(), linkInfo.getSchemeKey());
                        break;
                    }
                }
            }
        }
        attSchemeMap.put(FORMULA, formulaMap);
        attSchemeMap.put(FIFORMULA, fiFormulaMap);
        attSchemeMap.put(PRINT, printMap);
        return attSchemeMap;
    }

    private boolean isSameDataScheme(String srcDataSchemeKey, String formSchemeKey) {
        DesignFormSchemeDefine formScheme = this.nrDesignTimeController.queryFormSchemeDefine(formSchemeKey);
        DesignTaskDefine task = this.nrDesignTimeController.queryTaskDefine(formScheme.getTaskKey());
        String dataSchemeKey = task.getDataScheme();
        return dataSchemeKey.equals(srcDataSchemeKey);
    }

    @ApiOperation(value="\u62a5\u8868\u590d\u5236\u63a8\u9001\u65f6\u5019\u6279\u91cf\u6267\u884c\u4e0a\u62a5")
    @RequestMapping(value={"/batch-pubilsh-task"}, method={RequestMethod.POST})
    public List<FormCopyPushPublishResult> directPublishWithProtect(@RequestBody List<TaskPlanPublishObj> taskPlanPublishObjs) throws JQException {
        ArrayList<FormCopyPushPublishResult> result = new ArrayList<FormCopyPushPublishResult>();
        HashSet<String> deployDataSchemeTask = new HashSet<String>();
        Map<String, DesignTaskDefine> allTaskDefinesByType = this.nrDesignTimeController.getAllTaskDefinesByType(TaskType.TASK_TYPE_DEFAULT).stream().collect(Collectors.toMap(IBaseMetaItem::getKey, a -> a));
        for (TaskPlanPublishObj taskPlanPublishObj : taskPlanPublishObjs) {
            if (!taskPlanPublishObj.isDeployDataScheme()) continue;
            DesignTaskDefine designTaskDefine = allTaskDefinesByType.get(taskPlanPublishObj.getTaskID());
            if (deployDataSchemeTask.contains(designTaskDefine.getDataScheme())) {
                taskPlanPublishObj.setDeployDataScheme(false);
                continue;
            }
            deployDataSchemeTask.add(designTaskDefine.getDataScheme());
        }
        for (TaskPlanPublishObj taskPlanPublishObj : taskPlanPublishObjs) {
            String logTitle = "\u53d1\u5e03\u4efb\u52a1";
            String taskTitle = "\u672a\u77e5";
            try {
                taskTitle = this.designTimeViewService.queryTaskDefine(taskPlanPublishObj.getTaskID()).getTitle();
                boolean taskCanEdit = this.taskPlanPublishExternalService.taskCanEdit(taskPlanPublishObj.getTaskID());
                if (!taskCanEdit) {
                    result.add(new FormCopyPushPublishResult(true, taskPlanPublishObj.getTaskID(), taskPlanPublishObj.getDeployTaskID(), NrDesingerErrorEnum.NRDESINGER_EXCEPTION_030.getMessage()));
                    continue;
                }
                String res = this.taskPlanPublishService.directProtectedPublish(taskPlanPublishObj);
                NrDesignLogHelper.log(logTitle, taskTitle, NrDesignLogHelper.LOGLEVEL_INFO);
                result.add(new FormCopyPushPublishResult(true, taskPlanPublishObj.getTaskID(), taskPlanPublishObj.getDeployTaskID(), res));
            }
            catch (JQException e) {
                NrDesignLogHelper.log(logTitle, taskTitle, NrDesignLogHelper.LOGLEVEL_ERROR);
                logger.error(e.getMessage(), e);
                result.add(new FormCopyPushPublishResult(false, taskPlanPublishObj.getTaskID(), taskPlanPublishObj.getDeployTaskID(), e.getMessage()));
            }
            catch (Exception e) {
                NrDesignLogHelper.log(logTitle, taskTitle, NrDesignLogHelper.LOGLEVEL_ERROR);
                logger.error(e.getMessage(), e);
                result.add(new FormCopyPushPublishResult(false, taskPlanPublishObj.getTaskID(), taskPlanPublishObj.getDeployTaskID(), NrDesingerErrorEnum.NRDESINGER_EXCEPTION_025.getMessage()));
            }
        }
        return result;
    }

    @ApiOperation(value="\u62a5\u8868\u590d\u5236\u63a8\u9001\u67e5\u627e\u5404\u8981\u590d\u5236\u7684\u62a5\u8868\u5728\u54e5\u54e5\u76ee\u6807\u62a5\u8868\u65b9\u6848\u4e0b\u7684\u7684\u68c0\u67e5\u7ed3\u679c")
    @RequestMapping(value={"/get-form-copy-push-check"}, method={RequestMethod.POST})
    public Map<String, List<FormCopyInfoVO>> getFormCopyPushCheck(@RequestBody FormCopyInfoCheckVO formCopyInfoCheckVO) throws JQException {
        HashMap<String, List<FormCopyInfoVO>> result = new HashMap<String, List<FormCopyInfoVO>>();
        String srcFormSchemeKey = formCopyInfoCheckVO.getSrcFormSchemeKey();
        DesignFormSchemeDefine srcFormScheme = this.nrDesignTimeController.queryFormSchemeDefine(srcFormSchemeKey);
        DesignTaskDefine srcTask = this.nrDesignTimeController.queryTaskDefine(srcFormScheme.getTaskKey());
        String srcDataSchemeKey = srcTask.getDataScheme();
        List<String> srcFormKeys = formCopyInfoCheckVO.getSrcFormKeys();
        List<String> desFormSchemeKeys = formCopyInfoCheckVO.getDesFormSchemeKeys();
        List srcFormDefines = this.nrDesignTimeController.getSimpleFormDefines(srcFormKeys);
        srcFormDefines.sort(Comparator.comparing(IBaseMetaItem::getOrder));
        for (String desFormSchemeKey : desFormSchemeKeys) {
            ArrayList<FormCopyInfoVO> formCopyInfoVOS = new ArrayList<FormCopyInfoVO>();
            List formDefines = this.nrDesignTimeController.getAllFormDefinesInFormSchemeWithoutBinaryData(desFormSchemeKey);
            Map<String, DesignFormDefine> formDefineMaps = formDefines.stream().collect(Collectors.toMap(IBaseMetaItem::getKey, a -> a));
            Map<String, DesignFormDefine> formDefineCodeMaps = formDefines.stream().collect(Collectors.toMap(FormDefine::getFormCode, a -> a));
            List<IFormCopyInfo> formCopyInfos = this.iDesignFormCopyService.getFormCopyInfoBySchemeKey(desFormSchemeKey);
            for (int i = formCopyInfos.size() - 1; i >= 0; --i) {
                if (formDefineMaps.containsKey(formCopyInfos.get(i).getFormKey())) continue;
                this.iDesignFormCopyService.deleteCopyFormInfo(formCopyInfos.get(i).getFormKey());
                formCopyInfos.remove(i);
            }
            Map<String, IFormCopyInfo> formCopyInfoSrcMaps = formCopyInfos.stream().collect(Collectors.toMap(IFormCopyInfo::getSrcFormKey, a -> a));
            for (DesignFormDefine srcFormDefine : srcFormDefines) {
                DesignFormDefine designFormDefine;
                Optional<DesignFormDefine> first;
                FormCopyInfoVO formCopyInfoVO = new FormCopyInfoVO();
                formCopyInfoVO.setSrcFormKey(srcFormDefine.getKey());
                formCopyInfoVO.setSrcFormTitle(srcFormDefine.getTitle());
                formCopyInfoVO.setSrcFormCode(srcFormDefine.getFormCode());
                if ((srcFormDefine.getFormType().getValue() == 9 || srcFormDefine.getFormType().getValue() == 11) && (first = formDefines.stream().filter(a -> a.getFormType().getValue() == 9 || a.getFormType().getValue() == 11).findFirst()).isPresent()) {
                    if (formCopyInfoSrcMaps.get(srcFormDefine.getKey()) == null) {
                        formCopyInfoVO.setCheckResultKey(FormCopyCheck.HASFMDM.getKey());
                        formCopyInfoVO.setCheckResult(FormCopyCheck.HASFMDM.getValue());
                    } else {
                        formCopyInfoVO.setCheckResultKey(FormCopyCheck.COPIEDFMDM.getKey());
                        formCopyInfoVO.setCheckResult(FormCopyCheck.COPIEDFMDM.getValue());
                    }
                    formCopyInfoVO.setOpsResultKey(FormCopyOptions.UPDATE.getKey());
                    DesignFormDefine designFormDefine2 = first.get();
                    formCopyInfoVO.setExistFormKey(designFormDefine2.getKey());
                    formCopyInfoVO.setExistFormCode(designFormDefine2.getFormCode());
                    formCopyInfoVOS.add(formCopyInfoVO);
                    continue;
                }
                boolean flag = true;
                IFormCopyInfo formCopyInfo = formCopyInfoSrcMaps.get(srcFormDefine.getKey());
                if (formCopyInfo != null) {
                    formCopyInfoVO.setCheckResultKey(FormCopyCheck.REPEATCOPY.getKey());
                    formCopyInfoVO.setCheckResult(FormCopyCheck.REPEATCOPY.getValue());
                    formCopyInfoVO.setExistFormKey(formCopyInfo.getFormKey());
                    designFormDefine = formDefineMaps.get(formCopyInfo.getFormKey());
                    if (designFormDefine != null) {
                        formCopyInfoVO.setExistFormCode(designFormDefine.getFormCode());
                    }
                    formCopyInfoVO.setOpsResultKey(FormCopyOptions.UPDATE.getKey());
                    flag = false;
                }
                if (flag && (designFormDefine = formDefineCodeMaps.get(srcFormDefine.getFormCode())) != null) {
                    formCopyInfoVO.setCheckResultKey(FormCopyCheck.REPEATCODE.getKey());
                    formCopyInfoVO.setCheckResult(FormCopyCheck.REPEATCODE.getValue());
                    formCopyInfoVO.setExistFormKey(designFormDefine.getKey());
                    formCopyInfoVO.setExistFormCode(designFormDefine.getFormCode());
                    formCopyInfoVO.setOpsResultKey(FormCopyOptions.NEWCOPY.getKey());
                    String existFormCode = designFormDefine.getFormCode();
                    int i = 1;
                    while (formDefineCodeMaps.containsKey(existFormCode + i)) {
                        ++i;
                    }
                    formCopyInfoVO.setDesFormCode(existFormCode + i);
                    flag = false;
                }
                if (flag) {
                    formCopyInfoVO.setCheckResultKey(FormCopyCheck.NORMAL.getKey());
                    formCopyInfoVO.setCheckResult(FormCopyCheck.NORMAL.getValue());
                    formCopyInfoVO.setOpsResultKey(FormCopyOptions.NEWCOPY.getKey());
                    formCopyInfoVO.setDesFormCode(srcFormDefine.getFormCode());
                }
                formCopyInfoVOS.add(formCopyInfoVO);
            }
            result.put(desFormSchemeKey, formCopyInfoVOS);
        }
        return result;
    }

    @ApiOperation(value="\u62a5\u8868\u590d\u5236\u63a8\u9001\u65f6\u5019\u65b0\u589e\u62a5\u8868\uff0c\u68c0\u67e5code\u5728\u5f53\u524d\u62a5\u8868\u65b9\u6848\u4e0b\u662f\u5426\u5b58\u5728")
    @RequestMapping(value={"/get-form-copy-push-check-form-code/{desFormSchemeKey}/{formCode}"}, method={RequestMethod.GET})
    public boolean checkFormCodeSame(@PathVariable String desFormSchemeKey, @PathVariable String formCode) throws JQException {
        DesignFormDefine designFormDefine = this.nrDesignTimeController.queryFormByCodeInFormScheme(desFormSchemeKey, formCode);
        return designFormDefine != null && StringUtils.hasLength(designFormDefine.getKey());
    }

    @ApiOperation(value="\u62a5\u8868\u590d\u5236\u63a8\u9001\u65f6\u5019\u6279\u91cf\u6267\u884c\u590d\u5236")
    @RequestMapping(value={"/get-form-copy-push-do"}, method={RequestMethod.POST})
    public List<FormSyncPushResult> doFormCopyPush(@RequestBody List<FormDoCopyParams> allFormDoCopyParams) throws JQException {
        ArrayList<FormSyncPushResult> result = new ArrayList<FormSyncPushResult>();
        String srcFormSchemeKey = null;
        HashSet<String> desFormSchemeKeys = new HashSet<String>();
        HashSet<String> srcFormKeys = new HashSet<String>();
        for (FormDoCopyParams formDoCopyParams : allFormDoCopyParams) {
            ArrayList<FormulaSyncResult> formulaSyncResultList;
            FormSyncPushResult formSyncPushResult;
            block20: {
                Set<String> strings;
                formSyncPushResult = new FormSyncPushResult();
                srcFormSchemeKey = formDoCopyParams.getSrcFormSchemeKey();
                String formSchemeKey = formDoCopyParams.getDesFormSchemeKey();
                desFormSchemeKeys.add(formSchemeKey);
                ArrayList formCopyParams = new ArrayList();
                ArrayList<FormSyncParams> formSyncParams = new ArrayList<FormSyncParams>();
                DesignFormGroupDefine firstFormGroupDefine = null;
                if (!StringUtils.hasLength(formDoCopyParams.getDesFormGroupKey())) {
                    firstFormGroupDefine = (DesignFormGroupDefine)this.nrDesignTimeController.queryRootGroupsByFormScheme(formDoCopyParams.getDesFormSchemeKey()).get(0);
                }
                for (FormCopyParamsVO copyParam : formDoCopyParams.getCopyParams()) {
                    if (copyParam.getOpsResultKey() == FormCopyOptions.NEWCOPY.getKey()) {
                        FormCopyParams copyParamsObj = new FormCopyParams();
                        copyParamsObj.setFormCode(copyParam.getDesFormCode());
                        copyParamsObj.setFormGroupKey(StringUtils.hasLength(formDoCopyParams.getDesFormGroupKey()) ? formDoCopyParams.getDesFormGroupKey() : firstFormGroupDefine.getKey());
                        copyParamsObj.setSrcFormKey(copyParam.getSrcFormKey());
                        if (formDoCopyParams.isIfCopyGroup()) {
                            copyParamsObj.setSrcFormGroupKey(copyParam.getSrcFormGroupKey());
                        }
                        formCopyParams.add(copyParamsObj);
                        continue;
                    }
                    if (copyParam.getOpsResultKey() != FormCopyOptions.UPDATE.getKey()) continue;
                    FormSyncParams syncParamsObj = new FormSyncParams(copyParam.getExistFormKey(), copyParam.getSrcFormKey());
                    formSyncParams.add(syncParamsObj);
                }
                FormCopyAttSchemeMap attSchemeMap = new FormCopyAttSchemeMap();
                attSchemeMap.setFormulaSchemeMap(formDoCopyParams.getFormulaAttMap());
                attSchemeMap.setFiFormulaSchemeMap(formDoCopyParams.getFiFormulaAttMap());
                attSchemeMap.setPrintSchemeMap(formDoCopyParams.getPrintAttMap());
                formulaSyncResultList = new ArrayList<FormulaSyncResult>();
                try {
                    if (!formSyncParams.isEmpty()) {
                        FormulaSyncResultAll formulaSyncResultAll = this.iDesignFormCopyService.doSync(srcFormSchemeKey, formSchemeKey, formSyncParams, attSchemeMap, true);
                        if (formulaSyncResultAll != null) {
                            if (!CollectionUtils.isEmpty(formulaSyncResultAll.getNewFormLink())) {
                                strings = formulaSyncResultAll.getNewFormLink().keySet();
                                srcFormKeys.addAll(strings);
                            }
                            if (!CollectionUtils.isEmpty(formulaSyncResultAll.getFormulaSyncResult())) {
                                formulaSyncResultList.addAll(formulaSyncResultAll.getFormulaSyncResult());
                            }
                        }
                        formSyncPushResult.setOneDesFormKey(((FormSyncParams)formSyncParams.get(0)).getFormKey());
                    }
                }
                catch (Exception e) {
                    logger.error(e.getMessage(), e);
                    formSyncPushResult.setHasError(true);
                    formulaSyncResultList.add(new FormulaSyncResult("\u5f02\u5e38", "\u5f02\u5e38\u9519\u8bef", e.getMessage()));
                }
                try {
                    if (formCopyParams.isEmpty()) break block20;
                    FormulaSyncResultAll formulaCopyResults = this.iDesignFormCopyService.doCopy(srcFormSchemeKey, formSchemeKey, formCopyParams, attSchemeMap);
                    if (formulaCopyResults != null) {
                        if (!CollectionUtils.isEmpty(formulaCopyResults.getNewFormLink())) {
                            strings = formulaCopyResults.getNewFormLink().keySet();
                            srcFormKeys.addAll(strings);
                        }
                        if (!CollectionUtils.isEmpty(formulaCopyResults.getFormulaSyncResult())) {
                            formulaSyncResultList.addAll(formulaCopyResults.getFormulaSyncResult());
                        }
                    }
                    if (StringUtils.hasLength(formSyncPushResult.getOneDesFormKey())) break block20;
                    for (String newFormKey : formulaCopyResults.getNewFormLink().values()) {
                        if (!StringUtils.hasLength(newFormKey)) continue;
                        formSyncPushResult.setOneDesFormKey(newFormKey);
                        break;
                    }
                }
                catch (Exception e) {
                    logger.error(e.getMessage(), e);
                    formSyncPushResult.setHasError(true);
                    formulaSyncResultList.add(new FormulaSyncResult("\u5f02\u5e38", "\u5f02\u5e38\u9519\u8bef", e.getMessage()));
                }
            }
            formSyncPushResult.setFormulaSyncResults(formulaSyncResultList);
            formSyncPushResult.setDesFormSchemeKey(formDoCopyParams.getDesFormSchemeKey());
            List formGroupsByFormId = this.nrDesignTimeController.getFormGroupsByFormId(formSyncPushResult.getOneDesFormKey());
            if (!CollectionUtils.isEmpty(formGroupsByFormId)) {
                formSyncPushResult.setOneDesFormGroupKey(((DesignFormGroupDefine)formGroupsByFormId.get(0)).getKey());
            }
            result.add(formSyncPushResult);
        }
        HashMap<String, List<String>> attScheme = new HashMap<String, List<String>>();
        for (int i = 0; i < allFormDoCopyParams.size(); ++i) {
            FormDoCopyParams formDoCopyParams = allFormDoCopyParams.get(i);
            if (i != 0) continue;
            attScheme.put(FORMULA, new ArrayList<String>(formDoCopyParams.getFormulaAttMap().keySet()));
            attScheme.put(FIFORMULA, new ArrayList<String>(formDoCopyParams.getFiFormulaAttMap().keySet()));
            attScheme.put(PRINT, new ArrayList<String>(formDoCopyParams.getPrintAttMap().keySet()));
        }
        FormCopyRecordPush formCopyRecordPush = new FormCopyRecordPush();
        formCopyRecordPush.setKey(null);
        formCopyRecordPush.setUpdateTime(new Date());
        formCopyRecordPush.setAttScheme(attScheme);
        formCopyRecordPush.setSrcFormSchemeKey(srcFormSchemeKey);
        HashMap<String, List<String>> formSchemeMap = new HashMap<String, List<String>>();
        ArrayList desFormSchemeList = new ArrayList(desFormSchemeKeys);
        for (String srcFormKey : srcFormKeys) {
            formSchemeMap.put(srcFormKey, desFormSchemeList);
        }
        formCopyRecordPush.setFormSchemeMap(formSchemeMap);
        int num = this.iDesignFormCopyService.saveFormCopyPushRecord(formCopyRecordPush);
        return result;
    }

    @ApiOperation(value="\u67e5\u8be2\u63a8\u9001\u540c\u6b65\u4fe1\u606f")
    @RequestMapping(value={"/get-push-formsync-info/{srcFormSchemeKey}"}, method={RequestMethod.GET})
    public FormSyncPushExecuteVO getFormSyncInfo(@PathVariable String srcFormSchemeKey) throws JQException {
        List<IFormCopyInfo> iFormCopyInfoList = this.iDesignFormCopyService.getFormCopyInfoBySrcSchemeKey(srcFormSchemeKey);
        Map<String, List<IFormCopyInfo>> formCopyInfoListMapBySrcForm = iFormCopyInfoList.stream().collect(Collectors.groupingBy(IFormCopyInfo::getSrcFormKey));
        ArrayList<FormSyncPushVO> formSyncVOList = new ArrayList<FormSyncPushVO>();
        HashMap<String, FormSyncPushSchemeVO> desFormSchemeKeyToTitle = new HashMap<String, FormSyncPushSchemeVO>();
        List allSrcForms = this.nrDesignTimeController.getSimpleFormDefines(new ArrayList<String>(formCopyInfoListMapBySrcForm.keySet()));
        Map<String, DesignFormDefine> allSrcFormKeyToDefines = allSrcForms.stream().collect(Collectors.toMap(IBaseMetaItem::getKey, a -> a));
        List allDesFormKeys = iFormCopyInfoList.stream().map(IFormCopyInfo::getFormKey).collect(Collectors.toList());
        List allDesFormDefines = this.nrDesignTimeController.getSimpleFormDefines(allDesFormKeys);
        Map<String, DesignFormDefine> allDesFormKeyToDefines = allDesFormDefines.stream().collect(Collectors.toMap(IBaseMetaItem::getKey, a -> a));
        for (Map.Entry<String, List<IFormCopyInfo>> formCopyInfoListOneSrcForm : formCopyInfoListMapBySrcForm.entrySet()) {
            String srcFormKey = formCopyInfoListOneSrcForm.getKey();
            List<IFormCopyInfo> formCopyInfoListSrcForm = formCopyInfoListOneSrcForm.getValue();
            DesignFormDefine formDefineSrc = allSrcFormKeyToDefines.get(srcFormKey);
            FormSyncPushVO formSyncPushVO = new FormSyncPushVO();
            if (formDefineSrc == null || !StringUtils.hasLength(formDefineSrc.getKey())) continue;
            formSyncPushVO.setSrcFormKey(srcFormKey);
            formSyncPushVO.setSrcFormTitle(formDefineSrc.getTitle());
            formSyncPushVO.setSrcFormCode(formDefineSrc.getFormCode());
            formSyncPushVO.setSrcFormOrder(formDefineSrc.getOrder());
            ArrayList<FormSyncPushSchemeVO> desFormScheme = new ArrayList<FormSyncPushSchemeVO>();
            for (IFormCopyInfo iFormCopyInfo : formCopyInfoListSrcForm) {
                FormSyncPushSchemeVO formSyncPushSchemeVO = new FormSyncPushSchemeVO();
                String desFormSchemeKey = iFormCopyInfo.getFormSchemeKey();
                DesignFormDefine formDefineDes = allDesFormKeyToDefines.get(iFormCopyInfo.getFormKey());
                if (formDefineDes == null || !StringUtils.hasText(formDefineDes.getKey())) continue;
                if (!desFormSchemeKeyToTitle.containsKey(desFormSchemeKey)) {
                    DesignTaskDefine designTaskDefine;
                    DesignFormSchemeDefine formSchemeDefineDes = this.nrDesignTimeController.queryFormSchemeDefine(desFormSchemeKey);
                    if (formSchemeDefineDes == null || !StringUtils.hasText(formSchemeDefineDes.getKey()) || (designTaskDefine = this.nrDesignTimeController.queryTaskDefine(formSchemeDefineDes.getTaskKey())) == null || !StringUtils.hasText(designTaskDefine.getKey())) continue;
                    formSyncPushSchemeVO.setDesTaskTitle(designTaskDefine.getTitle());
                    formSyncPushSchemeVO.setDesTaskKey(designTaskDefine.getKey());
                    formSyncPushSchemeVO.setDesTaskOrder(designTaskDefine.getOrder());
                    formSyncPushSchemeVO.setFormSchemeTitle(formSchemeDefineDes.getTitle());
                    formSyncPushSchemeVO.setFormSchemeCode(formSchemeDefineDes.getFormSchemeCode());
                    desFormSchemeKeyToTitle.put(desFormSchemeKey, formSyncPushSchemeVO);
                } else {
                    FormSyncPushSchemeVO formSchemeMessage = (FormSyncPushSchemeVO)desFormSchemeKeyToTitle.get(desFormSchemeKey);
                    formSyncPushSchemeVO.setDesTaskTitle(formSchemeMessage.getDesTaskTitle());
                    formSyncPushSchemeVO.setDesTaskKey(formSchemeMessage.getDesTaskKey());
                    formSyncPushSchemeVO.setDesTaskOrder(formSchemeMessage.getDesTaskOrder());
                    formSyncPushSchemeVO.setFormSchemeTitle(formSchemeMessage.getFormSchemeTitle());
                    formSyncPushSchemeVO.setFormSchemeCode(formSchemeMessage.getFormSchemeTitle());
                }
                formSyncPushSchemeVO.setFormSchemeKey(desFormSchemeKey);
                formSyncPushSchemeVO.setDesFormKey(formDefineDes.getKey());
                formSyncPushSchemeVO.setDesFormCode(formDefineDes.getFormCode());
                formSyncPushSchemeVO.setDesFormTitle(formDefineDes.getTitle());
                if (0 != formDefineDes.getUpdateTime().compareTo(iFormCopyInfo.getUpdateTime())) {
                    formSyncPushSchemeVO.setOpsResult(1);
                    formSyncPushSchemeVO.setCheckResult("\u76ee\u6807\u62a5\u8868\u4fee\u6539");
                }
                desFormScheme.add(formSyncPushSchemeVO);
            }
            if (desFormScheme.size() == 0) continue;
            desFormScheme.sort((o1, o2) -> o1.getDesTaskOrder().compareTo(o2.getDesTaskOrder()));
            formSyncPushVO.setDesFormScheme(desFormScheme);
            formSyncVOList.add(formSyncPushVO);
        }
        formSyncVOList.sort((o1, o2) -> o1.getSrcFormOrder().compareTo(o2.getSrcFormOrder()));
        FormSyncPushExecuteVO formSyncPushExecuteVO = new FormSyncPushExecuteVO();
        formSyncPushExecuteVO.setFormSyncPushVOs(formSyncVOList);
        formSyncPushExecuteVO.setSrcFormSchemeKey(srcFormSchemeKey);
        DesignFormSchemeDefine srcFormSchemeDefine = this.nrDesignTimeController.queryFormSchemeDefine(srcFormSchemeKey);
        DesignTaskDefine srcTaskDefine = this.nrDesignTimeController.queryTaskDefine(srcFormSchemeDefine.getTaskKey());
        boolean ifFiFormulaOpen = srcTaskDefine.getEfdcSwitch();
        List allFormulaSchemeDefinesByFormScheme = this.nrDesignTimeController.getAllFormulaSchemeDefinesByFormScheme(srcFormSchemeKey);
        ArrayList<FormCopySchemeVO> formulaSchemes = new ArrayList<FormCopySchemeVO>();
        ArrayList<FormCopySchemeVO> fiFormulaSchemes = new ArrayList<FormCopySchemeVO>();
        for (DesignFormulaSchemeDefine designFormulaSchemeDefine : allFormulaSchemeDefinesByFormScheme) {
            if (designFormulaSchemeDefine.getFormulaSchemeType().equals((Object)FormulaSchemeType.FORMULA_SCHEME_TYPE_REPORT)) {
                formulaSchemes.add(new FormCopySchemeVO(designFormulaSchemeDefine.getKey(), designFormulaSchemeDefine.getTitle()));
                continue;
            }
            if (!ifFiFormulaOpen || !designFormulaSchemeDefine.getFormulaSchemeType().equals((Object)FormulaSchemeType.FORMULA_SCHEME_TYPE_FINANCIAL)) continue;
            fiFormulaSchemes.add(new FormCopySchemeVO(designFormulaSchemeDefine.getKey(), designFormulaSchemeDefine.getTitle()));
        }
        ArrayList<FormCopySchemeVO> printSchemes = new ArrayList<FormCopySchemeVO>();
        try {
            List allPrintSchemeByFormScheme = this.nrDesignTimeController.getAllPrintSchemeByFormScheme(srcFormSchemeKey);
            for (DesignPrintTemplateSchemeDefine printScheme : allPrintSchemeByFormScheme) {
                printSchemes.add(new FormCopySchemeVO(printScheme.getKey(), printScheme.getTitle()));
            }
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_194, (Throwable)e);
        }
        formSyncPushExecuteVO.setFormulaSchemes(formulaSchemes);
        formSyncPushExecuteVO.setPrintSchemes(printSchemes);
        formSyncPushExecuteVO.setFiFormulaSchemes(fiFormulaSchemes);
        return formSyncPushExecuteVO;
    }

    @ApiOperation(value="\u62a5\u8868\u590d\u5236\u63a8\u9001\u540c\u6b65")
    @RequestMapping(value={"/do-push-formsync"}, method={RequestMethod.POST})
    @Transactional(rollbackFor={Exception.class})
    public List<FormSyncPushResult> doFormPushSync(@RequestBody FormSyncPushExecuteVO formSyncPushExecuteVO) throws JQException {
        String srcFormSchemeKey = formSyncPushExecuteVO.getSrcFormSchemeKey();
        Set<String> formulaSchemes = formSyncPushExecuteVO.getFormulaSchemes().stream().map(FormCopySchemeVO::getKey).collect(Collectors.toSet());
        Set<String> fiFormulaSchemes = formSyncPushExecuteVO.getFiFormulaSchemes().stream().map(FormCopySchemeVO::getKey).collect(Collectors.toSet());
        Set<String> printSchemes = formSyncPushExecuteVO.getPrintSchemes().stream().map(FormCopySchemeVO::getKey).collect(Collectors.toSet());
        ArrayList<FormSyncPushResult> formSyncPushResultList = new ArrayList<FormSyncPushResult>();
        HashMap<String, List> desFormSchemeKeyToForm = new HashMap<String, List>();
        HashMap desFormSchemeKeyToTask = new HashMap();
        HashMap<String, List<String>> formSchemeMap = new HashMap<String, List<String>>();
        for (FormSyncPushVO formSyncPushVO : formSyncPushExecuteVO.getFormSyncPushVOs()) {
            List<FormSyncPushSchemeVO> selectDesFormScheme = formSyncPushVO.getSelectDesFormScheme();
            ArrayList<String> selectDesFormSchemeKeys = new ArrayList<String>();
            for (FormSyncPushSchemeVO formSyncPushSchemeVO : selectDesFormScheme) {
                selectDesFormSchemeKeys.add(formSyncPushSchemeVO.getFormSchemeKey());
                if (formSyncPushSchemeVO.getOpsResult() != 0) continue;
                desFormSchemeKeyToForm.computeIfAbsent(formSyncPushSchemeVO.getFormSchemeKey(), key -> {
                    desFormSchemeKeyToTask.put(formSyncPushSchemeVO.getFormSchemeKey(), formSyncPushSchemeVO);
                    return new ArrayList();
                }).add(new FormSyncParams(formSyncPushSchemeVO.getDesFormKey(), formSyncPushSchemeVO.getDesFormTitle(), formSyncPushSchemeVO.getDesFormCode(), formSyncPushVO.getSrcFormKey()));
            }
            formSchemeMap.put(formSyncPushVO.getSrcFormKey(), selectDesFormSchemeKeys);
        }
        for (Map.Entry entry : desFormSchemeKeyToForm.entrySet()) {
            FormSyncPushResult formSyncPushResult = new FormSyncPushResult();
            List formSyncParams = (List)entry.getValue();
            String desFormSchemeKey = (String)entry.getKey();
            FormCopyAttSchemeMap attSchemeMap = new FormCopyAttSchemeMap();
            List<IFormCopyAttSchemeInfo> formCopySchemeInfo = this.iDesignFormCopyService.getFormCopySchemeInfo(desFormSchemeKey, srcFormSchemeKey);
            for (IFormCopyAttSchemeInfo info : formCopySchemeInfo) {
                if (SchemeType.PRINT_SCHEME == info.getSchemeType()) {
                    if (!printSchemes.contains(info.getSrcSchemeKey())) continue;
                    attSchemeMap.getPrintSchemeMap().put(info.getSrcSchemeKey(), info.getSchemeKey());
                    continue;
                }
                if (SchemeType.FORMULA_SCHEME == info.getSchemeType()) {
                    if (!formulaSchemes.contains(info.getSrcSchemeKey())) continue;
                    attSchemeMap.getFormulaSchemeMap().put(info.getSrcSchemeKey(), info.getSchemeKey());
                    continue;
                }
                if (!fiFormulaSchemes.contains(info.getSrcSchemeKey())) continue;
                attSchemeMap.getFiFormulaSchemeMap().put(info.getSrcSchemeKey(), info.getSchemeKey());
            }
            ArrayList<FormulaSyncResult> formulaSyncResults = new ArrayList<FormulaSyncResult>();
            FormSyncPushSchemeVO formSyncPushSchemeVO = (FormSyncPushSchemeVO)desFormSchemeKeyToTask.get(desFormSchemeKey);
            formSyncPushResult.setDesTaskTitle(formSyncPushSchemeVO.getDesTaskTitle());
            formSyncPushResult.setDesFormSchemeKey(desFormSchemeKey);
            formSyncPushResult.setDesFormSchemeTitle(formSyncPushSchemeVO.getFormSchemeTitle());
            formSyncPushResultList.add(formSyncPushResult);
            try {
                FormulaSyncResultAll formulaSyncResultAll = this.iDesignFormCopyService.doSync(srcFormSchemeKey, desFormSchemeKey, formSyncParams, attSchemeMap, false);
                if (formulaSyncResultAll != null && !CollectionUtils.isEmpty(formulaSyncResultAll.getFormulaSyncResult())) {
                    formulaSyncResults.addAll(formulaSyncResultAll.getFormulaSyncResult());
                }
                formSyncPushResult.setOneDesFormKey(((FormSyncParams)formSyncParams.get(0)).getFormKey());
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
                formSyncPushResult.setHasError(true);
                formulaSyncResults.add(new FormulaSyncResult("\u5f02\u5e38", "\u5f02\u5e38\u9519\u8bef", e.getMessage()));
            }
            List formGroupsByFormId = this.nrDesignTimeController.getFormGroupsByFormId(formSyncPushResult.getOneDesFormKey());
            if (!CollectionUtils.isEmpty(formGroupsByFormId)) {
                formSyncPushResult.setOneDesFormGroupKey(((DesignFormGroupDefine)formGroupsByFormId.get(0)).getKey());
            }
            formSyncPushResult.setFormulaSyncResults(formulaSyncResults);
        }
        this.doSaveFormCopyPushRecord(formulaSchemes, fiFormulaSchemes, printSchemes, srcFormSchemeKey, formSchemeMap);
        return formSyncPushResultList;
    }

    private int doSaveFormCopyPushRecord(Set<String> formulaSchemes, Set<String> fiFormulaSchemes, Set<String> printSchemes, String srcFormSchemeKey, Map<String, List<String>> formSchemeMap) throws JQException {
        HashMap<String, List<String>> attScheme = new HashMap<String, List<String>>();
        attScheme.put(FORMULA, new ArrayList<String>(formulaSchemes));
        attScheme.put(FIFORMULA, new ArrayList<String>(fiFormulaSchemes));
        attScheme.put(PRINT, new ArrayList<String>(printSchemes));
        Date updateTime = new Date();
        FormCopyRecordPush formCopyRecordPush = new FormCopyRecordPush();
        formCopyRecordPush.setKey(null);
        formCopyRecordPush.setUpdateTime(updateTime);
        formCopyRecordPush.setAttScheme(attScheme);
        formCopyRecordPush.setSrcFormSchemeKey(srcFormSchemeKey);
        formCopyRecordPush.setFormSchemeMap(formSchemeMap);
        int num = this.iDesignFormCopyService.saveFormCopyPushRecord(formCopyRecordPush);
        return num;
    }

    @ApiOperation(value="\u62a5\u8868\u590d\u5236\u4e0a\u62a5")
    @RequestMapping(value={"/do-push-tasklistpublishtask"}, method={RequestMethod.POST})
    @Transactional(rollbackFor={Exception.class})
    public String doFormPushPublishSync(@RequestBody TaskPlanPublishObj taskPlanPublishObj) throws JQException {
        TaskPlanPublishController taskPlanPublishController = new TaskPlanPublishController();
        String result = taskPlanPublishController.taskListPublishTask(taskPlanPublishObj);
        return result;
    }

    @ApiOperation(value="\u62a5\u8868\u590d\u5236\u4e0a\u62a5\u8fdb\u5ea6")
    @RequestMapping(value={"/do-push-tasklistpublishtask-process/{id}"}, method={RequestMethod.GET})
    public ProgressVO getprogerss(@PathVariable String id) {
        ProgressVO progressVO;
        ProgressItem progress = null;
        String delployObjID = this.deployManager.getDelployObjID(id);
        if (StringUtils.hasText(delployObjID)) {
            progress = this.progressCacheService.getProgress(delployObjID);
        }
        if ((progressVO = new ProgressVO(progress)).isOver()) {
            progressVO.setCurrentProgess(100);
        } else {
            int displayedProgress = 0;
            int curStep = progressVO.getCurStep();
            int currentProgess = progressVO.getCurrentProgess();
            if (currentProgess > 100) {
                currentProgess = 100;
            }
            if (curStep == 1) {
                displayedProgress = currentProgess / 10;
            } else if (curStep == 2) {
                displayedProgress = 10 + (int)((double)currentProgess * 0.4);
            } else if (curStep == 3) {
                displayedProgress = 50 + (int)((double)currentProgess * 0.4);
            } else if (curStep == 4) {
                displayedProgress = 90 + (int)((double)currentProgess * 0.1);
            } else if (curStep == 5) {
                displayedProgress = 100;
            }
            if (!progressVO.isOver() && displayedProgress >= 100) {
                displayedProgress = 99;
            }
            progressVO.setCurrentProgess(displayedProgress);
        }
        return progressVO;
    }

    @ApiOperation(value="\u67e5\u8be2\u540c\u6b65\u63a8\u9001\u8bb0\u5f55")
    @RequestMapping(value={"/get-formsync-push-records/{srcFormSchemeKey}"}, method={RequestMethod.GET})
    public List<FormSyncRecordPushVO> getFormSyncPushRecord(@PathVariable String srcFormSchemeKey) {
        List<FormCopyRecordPush> formCopyPushRecords = this.iDesignFormCopyService.getFormCopyPushRecords(srcFormSchemeKey);
        if (null == formCopyPushRecords || formCopyPushRecords.isEmpty()) {
            return Collections.emptyList();
        }
        ArrayList<FormSyncRecordPushVO> formCopyRecordPushList = new ArrayList<FormSyncRecordPushVO>();
        for (FormCopyRecordPush formCopyRecordPush : formCopyPushRecords) {
            FormSyncRecordPushVO formSyncRecordPushVO = new FormSyncRecordPushVO(formCopyRecordPush);
            formCopyRecordPushList.add(formSyncRecordPushVO);
        }
        Collections.reverse(formCopyRecordPushList);
        return formCopyRecordPushList;
    }

    @ApiOperation(value="\u67e5\u8be2\u62a5\u8868\u6240\u5c5e\u5206\u7ec4key")
    @RequestMapping(value={"/get-formsync-group-key/{desFormKey}"}, method={RequestMethod.GET})
    public String getFormGroupByForm(@PathVariable String desFormKey) {
        List formGroupsByFormId = this.nrDesignTimeController.getFormGroupsByFormId(desFormKey);
        return ((DesignFormGroupDefine)formGroupsByFormId.get(0)).getKey();
    }
}

