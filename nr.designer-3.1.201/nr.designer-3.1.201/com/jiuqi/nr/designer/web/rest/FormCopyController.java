/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.nr.common.itree.INode
 *  com.jiuqi.nr.common.itree.ITree
 *  com.jiuqi.nr.definition.common.FormulaSchemeType
 *  com.jiuqi.nr.definition.common.TaskType
 *  com.jiuqi.nr.definition.facade.DesignFormDefine
 *  com.jiuqi.nr.definition.facade.DesignFormGroupDefine
 *  com.jiuqi.nr.definition.facade.DesignFormSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignFormulaSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignPrintTemplateSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 *  com.jiuqi.nr.definition.internal.controller.NRDesignTimeController
 *  com.jiuqi.xlib.utils.CollectionUtils
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
import com.jiuqi.nr.common.itree.INode;
import com.jiuqi.nr.common.itree.ITree;
import com.jiuqi.nr.definition.common.FormulaSchemeType;
import com.jiuqi.nr.definition.common.TaskType;
import com.jiuqi.nr.definition.facade.DesignFormDefine;
import com.jiuqi.nr.definition.facade.DesignFormGroupDefine;
import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignFormulaSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignPrintTemplateSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.definition.internal.controller.NRDesignTimeController;
import com.jiuqi.nr.designer.common.NrDesingerErrorEnum;
import com.jiuqi.nr.designer.formcopy.FormCopyAttSchemeMap;
import com.jiuqi.nr.designer.formcopy.FormCopyParams;
import com.jiuqi.nr.designer.formcopy.FormCopyRecord;
import com.jiuqi.nr.designer.formcopy.FormSyncParams;
import com.jiuqi.nr.designer.formcopy.FormulaSyncResult;
import com.jiuqi.nr.designer.formcopy.FormulaSyncResultAll;
import com.jiuqi.nr.designer.formcopy.IDesignFormCopyService;
import com.jiuqi.nr.designer.formcopy.bean.IFormCopyAttSchemeInfo;
import com.jiuqi.nr.designer.formcopy.bean.IFormCopyInfo;
import com.jiuqi.nr.designer.formcopy.common.SchemeType;
import com.jiuqi.nr.designer.web.facade.FormCopyAttSchemeVO;
import com.jiuqi.nr.designer.web.facade.FormCopyCheck;
import com.jiuqi.nr.designer.web.facade.FormCopyFormTreeNode;
import com.jiuqi.nr.designer.web.facade.FormCopyInfoParams;
import com.jiuqi.nr.designer.web.facade.FormCopyInfoVO;
import com.jiuqi.nr.designer.web.facade.FormCopyLinksVO;
import com.jiuqi.nr.designer.web.facade.FormCopyOptions;
import com.jiuqi.nr.designer.web.facade.FormCopyParamsVO;
import com.jiuqi.nr.designer.web.facade.FormCopySchemeVO;
import com.jiuqi.nr.designer.web.facade.FormCopyTaskTreeNode;
import com.jiuqi.nr.designer.web.facade.FormDoCopyParams;
import com.jiuqi.nr.designer.web.facade.FormSyncFormParamsVO;
import com.jiuqi.nr.designer.web.facade.FormSyncParamsVO;
import com.jiuqi.nr.designer.web.facade.FormSyncRecordVO;
import com.jiuqi.nr.designer.web.facade.FormSyncVO;
import com.jiuqi.nr.designer.web.facade.SyncCoverForm;
import com.jiuqi.xlib.utils.CollectionUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@JQRestController
@RequestMapping(value={"api/v1/designer/"})
@Api(tags={"\u62a5\u8868\u590d\u5236\u53ca\u540c\u6b65\u6a21\u5757"})
public class FormCopyController {
    @Autowired
    private IDesignFormCopyService iDesignFormCopyService;
    @Autowired
    private NRDesignTimeController nrDesignTimeController;
    private static final String FORMULA = "FORMULA";
    private static final String FIFORMULA = "FIFORMULA";
    private static final String PRINT = "PRINT";

    @ApiOperation(value="\u83b7\u53d6\u4efb\u52a1\u548c\u5bf9\u5e94\u62a5\u8868\u65b9\u6848\u7684\u6811\u5f62")
    @RequestMapping(value={"/get-tasktree"}, method={RequestMethod.GET})
    public List<ITree<FormCopyTaskTreeNode>> getTaskTree() throws JQException {
        ArrayList<ITree<FormCopyTaskTreeNode>> tree_Task = new ArrayList<ITree<FormCopyTaskTreeNode>>();
        List listDesignTask = this.nrDesignTimeController.getAllTaskDefinesByType(TaskType.TASK_TYPE_DEFAULT);
        if (listDesignTask != null) {
            listDesignTask.forEach(task -> tree_Task.add(this.getGroupTreeNode((DesignTaskDefine)task)));
        }
        this.taskSchemeTree(tree_Task);
        return tree_Task;
    }

    @ApiOperation(value="\u6839\u636e\u62a5\u8868\u65b9\u6848\u67e5\u8be2\u8868\u5355\u5206\u7ec4\u548c\u62a5\u8868\u6570\u636e\u6811")
    @RequestMapping(value={"/get-taskschemegrouptree/{srcFormSchemeKey}/{desFormSchemeKey}"}, method={RequestMethod.GET})
    public List<ITree<FormCopyFormTreeNode>> getGroupTree(@PathVariable String srcFormSchemeKey, @PathVariable String desFormSchemeKey) throws JQException {
        try {
            ArrayList<ITree<FormCopyFormTreeNode>> tree_formGroup = new ArrayList<ITree<FormCopyFormTreeNode>>();
            List formGroupsDefines = this.nrDesignTimeController.queryRootGroupsByFormScheme(srcFormSchemeKey);
            boolean sameDataScheme = this.isSameDataScheme(srcFormSchemeKey, desFormSchemeKey);
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
            return tree_formGroup;
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_197, (Throwable)e);
        }
    }

    @ApiOperation(value="\u6839\u636e\u6765\u6e90\u62a5\u8868\u65b9\u6848\u548c\u76ee\u6807\u62a5\u8868\u65b9\u6848\u67e5\u8be2\u6620\u5c04\u5173\u7cfb")
    @RequestMapping(value={"/get-links/{srcFormSchemeKey}/{desFormSchemeKey}"}, method={RequestMethod.GET})
    public FormCopyLinksVO getSrcDesLinks(@PathVariable String srcFormSchemeKey, @PathVariable String desFormSchemeKey) throws JQException {
        FormCopyLinksVO formCopyLinksVO = new FormCopyLinksVO();
        List<IFormCopyAttSchemeInfo> formCopySchemeInfo = this.iDesignFormCopyService.getFormCopySchemeInfo(desFormSchemeKey, srcFormSchemeKey);
        boolean ifFiFormula = this.fiFormulaButton(srcFormSchemeKey, desFormSchemeKey);
        Map<String, List<FormCopySchemeVO>> srcSchemeMap = this.querySchemesInFormScheme(srcFormSchemeKey, ifFiFormula);
        formCopyLinksVO.setSrcFormulaSchemes(srcSchemeMap.get(FORMULA));
        formCopyLinksVO.setSrcFiFormulaSchemes(srcSchemeMap.get(FIFORMULA));
        formCopyLinksVO.setSrcPrintFormulaSchemes(srcSchemeMap.get(PRINT));
        Map<String, List<FormCopySchemeVO>> desSchemeMap = this.querySchemesInFormScheme(desFormSchemeKey, ifFiFormula);
        formCopyLinksVO.setDesFormulaSchemes(desSchemeMap.get(FORMULA));
        formCopyLinksVO.setDesFiFormulaSchemes(desSchemeMap.get(FIFORMULA));
        formCopyLinksVO.setDesPrintFormulaSchemes(desSchemeMap.get(PRINT));
        Map<String, Map<String, String>> attSchemeMap = this.decomposeScheme(formCopySchemeInfo, ifFiFormula);
        formCopyLinksVO.setFormulaMap(attSchemeMap.get(FORMULA));
        formCopyLinksVO.setFiFormulaMap(attSchemeMap.get(FIFORMULA));
        formCopyLinksVO.setPrintMap(attSchemeMap.get(PRINT));
        return formCopyLinksVO;
    }

    @ApiOperation(value="\u67e5\u8be2\u590d\u5236\u62a5\u8868\u7684\u4fe1\u606f")
    @RequestMapping(value={"/get-formcopy_info"}, method={RequestMethod.POST})
    public List<FormCopyInfoVO> getFormCopyInfo(@RequestBody FormCopyInfoParams formCopyInfoParams) throws JQException {
        ArrayList<FormCopyInfoVO> formCopyInfoVOS = new ArrayList<FormCopyInfoVO>();
        List<IFormCopyInfo> formCopyInfos = this.iDesignFormCopyService.getFormCopyInfoBySchemeKey(formCopyInfoParams.getDesFormSchemeKey());
        List formDefines = this.nrDesignTimeController.queryAllFormDefinesByFormScheme(formCopyInfoParams.getDesFormSchemeKey());
        for (int i = 0; i < formCopyInfos.size(); ++i) {
            if (this.nrDesignTimeController.querySoftFormDefine(formCopyInfos.get(i).getFormKey()) != null) continue;
            this.iDesignFormCopyService.deleteCopyFormInfo(formCopyInfos.get(i).getFormKey());
            formCopyInfos.remove(formCopyInfos.get(i));
            --i;
        }
        Map<String, IFormCopyInfo> formCopyInfoSrcMaps = formCopyInfos.stream().collect(Collectors.toMap(IFormCopyInfo::getSrcFormKey, a -> a));
        for (String srcFormKey : formCopyInfoParams.getSrcFormKeys()) {
            Optional<DesignFormDefine> desFMForm;
            FormCopyInfoVO formCopyInfoVO = new FormCopyInfoVO();
            formCopyInfoVO.setSrcFormKey(srcFormKey);
            DesignFormDefine srcFormDefine = this.nrDesignTimeController.querySoftFormDefine(srcFormKey);
            formCopyInfoVO.setSrcFormTitle(srcFormDefine.getTitle());
            formCopyInfoVO.setSrcFormCode(srcFormDefine.getFormCode());
            boolean flag = true;
            if ((srcFormDefine.getFormType().getValue() == 9 || srcFormDefine.getFormType().getValue() == 11) && (desFMForm = formDefines.stream().filter(a -> a.getFormType().getValue() == 9 || a.getFormType().getValue() == 11).findFirst()).isPresent()) {
                if (formCopyInfoSrcMaps.get(srcFormDefine.getKey()) == null) {
                    formCopyInfoVO.setCheckResultKey(FormCopyCheck.HASFMDM.getKey());
                    formCopyInfoVO.setCheckResult(FormCopyCheck.HASFMDM.getValue());
                } else {
                    formCopyInfoVO.setCheckResultKey(FormCopyCheck.COPIEDFMDM.getKey());
                    formCopyInfoVO.setCheckResult(FormCopyCheck.COPIEDFMDM.getValue());
                }
                formCopyInfoVO.setOpsResultKey(FormCopyOptions.UPDATE.getKey());
                DesignFormDefine designFormDefine = desFMForm.get();
                formCopyInfoVO.setExistFormKey(designFormDefine.getKey());
                formCopyInfoVO.setExistFormCode(designFormDefine.getFormCode());
                formCopyInfoVOS.add(formCopyInfoVO);
                continue;
            }
            IFormCopyInfo formCopyInfo = formCopyInfoSrcMaps.get(srcFormKey);
            if (formCopyInfo != null) {
                formCopyInfoVO.setCheckResultKey(FormCopyCheck.REPEATCOPY.getKey());
                formCopyInfoVO.setCheckResult(FormCopyCheck.REPEATCOPY.getValue());
                formCopyInfoVO.setExistFormKey(formCopyInfo.getFormKey());
                for (DesignFormDefine formDefine : formDefines) {
                    if (!formCopyInfo.getFormKey().equals(formDefine.getKey())) continue;
                    formCopyInfoVO.setExistFormCode(formDefine.getFormCode());
                }
                formCopyInfoVO.setOpsResultKey(FormCopyOptions.UPDATE.getKey());
                flag = false;
            }
            if (flag) {
                for (DesignFormDefine formDefine : formDefines) {
                    if (!formDefine.getFormCode().equals(srcFormDefine.getFormCode())) continue;
                    formCopyInfoVO.setCheckResultKey(FormCopyCheck.REPEATCODE.getKey());
                    formCopyInfoVO.setCheckResult(FormCopyCheck.REPEATCODE.getValue());
                    formCopyInfoVO.setExistFormKey(formDefine.getKey());
                    formCopyInfoVO.setExistFormCode(formDefine.getFormCode());
                    formCopyInfoVO.setOpsResultKey(FormCopyOptions.NEWCOPY.getKey());
                    formCopyInfoVO.setDesFormCode(formDefine.getFormCode());
                    flag = false;
                    break;
                }
            }
            if (flag) {
                formCopyInfoVO.setCheckResultKey(FormCopyCheck.NORMAL.getKey());
                formCopyInfoVO.setCheckResult(FormCopyCheck.NORMAL.getValue());
                formCopyInfoVO.setOpsResultKey(FormCopyOptions.NEWCOPY.getKey());
                formCopyInfoVO.setDesFormCode(srcFormDefine.getFormCode());
            }
            formCopyInfoVOS.add(formCopyInfoVO);
        }
        return formCopyInfoVOS;
    }

    @ApiOperation(value="\u6267\u884c\u62a5\u8868\u590d\u5236")
    @RequestMapping(value={"/do-formcopy"}, method={RequestMethod.POST})
    @Transactional(rollbackFor={Exception.class})
    public List<FormulaSyncResult> doFormCopy(@RequestBody FormDoCopyParams formDoCopyParams) throws JQException {
        FormulaSyncResultAll formulaCopyResultAll;
        Collection<String> values;
        FormulaSyncResultAll formulaSyncResultAll;
        String srcFormSchemeKey = formDoCopyParams.getSrcFormSchemeKey();
        String formSchemeKey = formDoCopyParams.getDesFormSchemeKey();
        ArrayList<FormCopyParams> formCopyParams = new ArrayList<FormCopyParams>();
        ArrayList<FormSyncParams> formSyncParams = new ArrayList<FormSyncParams>();
        for (FormCopyParamsVO copyParam : formDoCopyParams.getCopyParams()) {
            if (copyParam.getOpsResultKey() == FormCopyOptions.NEWCOPY.getKey()) {
                FormCopyParams copyParamsObj = new FormCopyParams();
                copyParamsObj.setFormCode(copyParam.getDesFormCode());
                copyParamsObj.setFormGroupKey(formDoCopyParams.getDesFormGroupKey());
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
        ArrayList<FormulaSyncResult> formulaSyncResultList = new ArrayList<FormulaSyncResult>();
        ArrayList<String> newFormKeys = new ArrayList<String>();
        if (!formSyncParams.isEmpty() && (formulaSyncResultAll = this.iDesignFormCopyService.doSync(srcFormSchemeKey, formSchemeKey, formSyncParams, attSchemeMap, true)) != null) {
            if (!CollectionUtils.isEmpty(formulaSyncResultAll.getNewFormLink())) {
                values = formulaSyncResultAll.getNewFormLink().values();
                newFormKeys.addAll(values);
            }
            if (!CollectionUtils.isEmpty(formulaSyncResultAll.getFormulaSyncResult())) {
                formulaSyncResultList.addAll(formulaSyncResultAll.getFormulaSyncResult());
            }
        }
        if (!formCopyParams.isEmpty() && (formulaCopyResultAll = this.iDesignFormCopyService.doCopy(srcFormSchemeKey, formSchemeKey, formCopyParams, attSchemeMap)) != null) {
            if (!CollectionUtils.isEmpty(formulaCopyResultAll.getNewFormLink())) {
                values = formulaCopyResultAll.getNewFormLink().values();
                newFormKeys.addAll(values);
            }
            if (!CollectionUtils.isEmpty(formulaCopyResultAll.getFormulaSyncResult())) {
                formulaSyncResultList.addAll(formulaCopyResultAll.getFormulaSyncResult());
            }
        }
        if (newFormKeys.size() > 0) {
            FormCopyRecord formCopyRecord = new FormCopyRecord();
            formCopyRecord.setKey(null);
            formCopyRecord.setUpdateTime(new Date());
            HashMap<String, FormCopyAttSchemeMap> attScheme = new HashMap<String, FormCopyAttSchemeMap>();
            attScheme.put(srcFormSchemeKey, attSchemeMap);
            formCopyRecord.setAttScheme(attScheme);
            formCopyRecord.setFormSchemeKey(formSchemeKey);
            formCopyRecord.setFormKeys(newFormKeys);
            this.iDesignFormCopyService.saveFormCopyRecord(formCopyRecord);
        }
        return formulaSyncResultList;
    }

    @ApiOperation(value="\u67e5\u8be2\u540c\u6b65\u4fe1\u606f")
    @RequestMapping(value={"/get-formsync-info/{desFormSchemeKey}"}, method={RequestMethod.GET})
    public List<FormSyncVO> getFormSyncInfo(@PathVariable String desFormSchemeKey) throws JQException {
        List<IFormCopyInfo> iFormCopyInfoList = this.iDesignFormCopyService.getFormCopyInfoBySchemeKey(desFormSchemeKey);
        ArrayList<FormSyncVO> formSyncVOList = new ArrayList<FormSyncVO>();
        HashMap<String, FormSyncVO> formulaSchemeMap = new HashMap<String, FormSyncVO>();
        for (IFormCopyInfo iFormCopyInfo : iFormCopyInfoList) {
            String srcformSchemeKey = iFormCopyInfo.getSrcFormSchemeKey();
            String formKey = iFormCopyInfo.getFormKey();
            String srcFormKey = iFormCopyInfo.getSrcFormKey();
            DesignFormDefine formDefineDes = this.nrDesignTimeController.querySoftFormDefine(formKey);
            DesignFormDefine formDefineSrc = this.nrDesignTimeController.querySoftFormDefine(srcFormKey);
            if (null == formDefineDes || null == formDefineSrc) continue;
            FormSyncVO formSyncVO = new FormSyncVO();
            formSyncVO.setDesFormKey(formKey);
            formSyncVO.setDesFormTitle(formDefineDes.getTitle());
            formSyncVO.setDesFormCode(formDefineDes.getFormCode());
            formSyncVO.setDesFormOrder(formDefineDes.getOrder());
            formSyncVO.setSrcFormKey(srcFormKey);
            formSyncVO.setSrcFormTitle(formDefineSrc.getTitle());
            formSyncVO.setSrcFormCode(formDefineSrc.getFormCode());
            if (!formulaSchemeMap.containsKey(srcformSchemeKey)) {
                DesignFormSchemeDefine formSchemeDefineSrc = this.nrDesignTimeController.queryFormSchemeDefine(srcformSchemeKey);
                if (formSchemeDefineSrc == null) {
                    throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_193);
                }
                formSyncVO.setSrcTaskKey(formSchemeDefineSrc.getTaskKey());
                formSyncVO.setSrcFormSchemeTitle(formSchemeDefineSrc.getTitle());
                formSyncVO.setSrcFormSchemeCode(formSchemeDefineSrc.getFormSchemeCode());
                try {
                    DesignTaskDefine taskDefine = this.nrDesignTimeController.queryTaskDefine(formSchemeDefineSrc.getTaskKey());
                    formSyncVO.setSrcTaskTitle(taskDefine.getTitle());
                    formSyncVO.setSrcFormSchemeKey(srcformSchemeKey);
                    formSyncVO.setSrcTaskCode(taskDefine.getTaskCode());
                    this.setAttSchemes(formSyncVO, srcformSchemeKey, desFormSchemeKey);
                }
                catch (Exception e) {
                    throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_194, (Throwable)e);
                }
                formSyncVOList.add(formSyncVO);
                formulaSchemeMap.put(srcformSchemeKey, formSyncVO);
                continue;
            }
            FormSyncVO existformSyncVO = (FormSyncVO)formulaSchemeMap.get(srcformSchemeKey);
            formSyncVO.setSrcTaskKey(existformSyncVO.getSrcTaskKey());
            formSyncVO.setSrcFormSchemeTitle(existformSyncVO.getSrcFormSchemeTitle());
            formSyncVO.setSrcFormSchemeCode(existformSyncVO.getSrcFormSchemeCode());
            formSyncVO.setSrcTaskTitle(existformSyncVO.getSrcTaskTitle());
            formSyncVO.setSrcFormSchemeKey(srcformSchemeKey);
            formSyncVO.setSrcTaskCode(existformSyncVO.getSrcTaskCode());
            formSyncVO.setPrintSchemes(existformSyncVO.getPrintSchemes());
            formSyncVO.setFormulaSchemes(existformSyncVO.getFormulaSchemes());
            formSyncVO.setFiFormulaSchemes(existformSyncVO.getFiFormulaSchemes());
            formSyncVOList.add(formSyncVO);
        }
        formSyncVOList.sort((o1, o2) -> {
            if (o1.getSrcTaskCode().compareTo(o2.getSrcTaskCode()) != 0) {
                return o1.getSrcTaskCode().compareTo(o2.getSrcTaskCode());
            }
            if (o1.getSrcFormSchemeCode().compareTo(o2.getSrcFormSchemeCode()) != 0) {
                return o1.getSrcFormSchemeCode().compareTo(o2.getSrcFormSchemeCode());
            }
            return o1.getDesFormOrder().compareTo(o2.getDesFormOrder());
        });
        return formSyncVOList;
    }

    @ApiOperation(value="\u67e5\u8be2\u540c\u6b65\u8bb0\u5f55")
    @RequestMapping(value={"/get-formsync-records/{desFormSchemeKey}"}, method={RequestMethod.GET})
    public List<FormSyncRecordVO> getFormSyncRecord(@PathVariable String desFormSchemeKey) {
        List<FormCopyRecord> formCopyRecordList = this.iDesignFormCopyService.getFormCopyRecords(desFormSchemeKey);
        if (null == formCopyRecordList || formCopyRecordList.isEmpty()) {
            return Collections.emptyList();
        }
        ArrayList<FormSyncRecordVO> formSyncRecordList = new ArrayList<FormSyncRecordVO>();
        for (FormCopyRecord formCopyRecord : formCopyRecordList) {
            FormSyncRecordVO formSyncRecord = new FormSyncRecordVO(formCopyRecord);
            formSyncRecordList.add(formSyncRecord);
        }
        Collections.reverse(formSyncRecordList);
        return formSyncRecordList;
    }

    @ApiOperation(value="\u62a5\u8868\u590d\u5236\u6267\u884c\u540c\u6b65")
    @RequestMapping(value={"/do-formsync"}, method={RequestMethod.POST})
    @Transactional(rollbackFor={Exception.class})
    public List<FormulaSyncResult> doFormSync(@RequestBody List<FormSyncParamsVO> formSyncParamsVOList) throws JQException {
        if (formSyncParamsVOList == null || formSyncParamsVOList.isEmpty()) {
            throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_195);
        }
        ArrayList<String> formKeys = new ArrayList<String>();
        HashMap<String, FormCopyAttSchemeMap> attScheme = new HashMap<String, FormCopyAttSchemeMap>();
        ArrayList<FormulaSyncResult> formulaSyncResultList = new ArrayList<FormulaSyncResult>();
        for (FormSyncParamsVO copySyncParam : formSyncParamsVOList) {
            String formSchemeKey = copySyncParam.getDesFormSchemeKey();
            String srcFormSchemeKey = copySyncParam.getSrcFormSchemeKey();
            List<FormSyncFormParamsVO> formParams = copySyncParam.getFormParams();
            if (CollectionUtils.isEmpty(formParams)) continue;
            ArrayList<FormSyncParams> formSyncParams = new ArrayList<FormSyncParams>();
            for (FormSyncFormParamsVO formParam : formParams) {
                FormSyncParams formSyncParam = new FormSyncParams(formParam.getDesFormKey(), formParam.getSrcFormKey());
                formSyncParam.setFormTitle(formParam.getDesFormTitle());
                formSyncParam.setFormCode(formParam.getDesFormCode());
                formSyncParams.add(formSyncParam);
                formKeys.add(formParam.getDesFormKey());
            }
            FormCopyAttSchemeMap attSchemeMap = new FormCopyAttSchemeMap();
            attSchemeMap.setFormulaSchemeMap(copySyncParam.getFormulaAttMap());
            attSchemeMap.setFiFormulaSchemeMap(copySyncParam.getFiFormulaAttMap());
            attSchemeMap.setPrintSchemeMap(copySyncParam.getPrintAttMap());
            FormulaSyncResultAll formulaSyncResultAll = this.iDesignFormCopyService.doSync(srcFormSchemeKey, formSchemeKey, formSyncParams, attSchemeMap, false);
            if (formulaSyncResultAll != null && !CollectionUtils.isEmpty(formulaSyncResultAll.getFormulaSyncResult())) {
                formulaSyncResultList.addAll(formulaSyncResultAll.getFormulaSyncResult());
            }
            attScheme.put(srcFormSchemeKey, attSchemeMap);
        }
        String desFormSchemeKey = formSyncParamsVOList.get(0).getDesFormSchemeKey();
        Date updateTime = new Date();
        FormCopyRecord formCopyRecord = new FormCopyRecord();
        formCopyRecord.setKey(null);
        formCopyRecord.setUpdateTime(updateTime);
        formCopyRecord.setAttScheme(attScheme);
        formCopyRecord.setFormSchemeKey(desFormSchemeKey);
        formCopyRecord.setFormKeys(formKeys);
        this.iDesignFormCopyService.saveFormCopyRecord(formCopyRecord);
        return formulaSyncResultList;
    }

    @ApiOperation(value="\u6267\u884c\u540c\u6b65\u8986\u76d6\u63d0\u793a")
    @RequestMapping(value={"/formsync-cover-message"}, method={RequestMethod.POST})
    public List<SyncCoverForm> messageBox(@RequestBody List<String> formKeys) {
        if (null == formKeys || formKeys.isEmpty()) {
            return Collections.emptyList();
        }
        ArrayList<SyncCoverForm> syncCoverFormList = new ArrayList<SyncCoverForm>();
        List<IFormCopyInfo> iFormCopyInfoList = this.iDesignFormCopyService.getFormCopyInfoByFormKeys(formKeys);
        if (null == iFormCopyInfoList || iFormCopyInfoList.isEmpty()) {
            return Collections.emptyList();
        }
        for (IFormCopyInfo iFormCopyInfo : iFormCopyInfoList) {
            SyncCoverForm syncCoverForm = new SyncCoverForm();
            String formKey = iFormCopyInfo.getFormKey();
            DesignFormDefine formDefine = this.nrDesignTimeController.queryFormById(formKey);
            if (0 == formDefine.getUpdateTime().compareTo(iFormCopyInfo.getUpdateTime())) continue;
            syncCoverForm.setKey(formKey);
            syncCoverForm.setCode(formDefine.getFormCode());
            syncCoverForm.setTitle(formDefine.getTitle());
            syncCoverFormList.add(syncCoverForm);
        }
        return syncCoverFormList;
    }

    private ITree<FormCopyTaskTreeNode> getGroupTreeNode(DesignTaskDefine task) {
        ITree node = new ITree((INode)new FormCopyTaskTreeNode(task));
        node.setLeaf(false);
        node.setNoDrop(true);
        node.setNoDrag(false);
        return node;
    }

    private ITree<FormCopyTaskTreeNode> getGroupTreeNode(DesignFormSchemeDefine scheme, String taskTitle, String taskOrder, boolean efdcSwitch) {
        ITree node = new ITree((INode)new FormCopyTaskTreeNode(scheme, taskTitle, taskOrder, efdcSwitch));
        node.setLeaf(true);
        node.setNoDrop(true);
        node.setNoDrag(false);
        return node;
    }

    private void taskSchemeTree(List<ITree<FormCopyTaskTreeNode>> tree_Task) throws JQException {
        for (ITree<FormCopyTaskTreeNode> taskTree : tree_Task) {
            ArrayList tree_Scheme = new ArrayList();
            List listFormSchemes = this.nrDesignTimeController.queryFormSchemeByTask(((FormCopyTaskTreeNode)taskTree.getData()).getKey());
            if (listFormSchemes != null) {
                String taskTitle = ((FormCopyTaskTreeNode)taskTree.getData()).getTitle();
                String taskOrder = ((FormCopyTaskTreeNode)taskTree.getData()).getOrder();
                boolean efdcSwitch = ((FormCopyTaskTreeNode)taskTree.getData()).isEfdcSwitch();
                listFormSchemes.forEach(scheme -> tree_Scheme.add(this.getGroupTreeNode((DesignFormSchemeDefine)scheme, taskTitle, taskOrder, efdcSwitch)));
            }
            taskTree.setChildren(tree_Scheme);
        }
    }

    private ITree<FormCopyFormTreeNode> getTreeNode(DesignFormGroupDefine formGroup) {
        ITree node = new ITree((INode)new FormCopyFormTreeNode(formGroup));
        node.setLeaf(false);
        node.setNoDrop(true);
        node.setNoDrag(true);
        return node;
    }

    private ITree<FormCopyFormTreeNode> getTreeNode(DesignFormDefine formDefine, String parentId) {
        ITree node = new ITree((INode)new FormCopyFormTreeNode(formDefine, parentId));
        node.setLeaf(true);
        node.setNoDrop(true);
        node.setNoDrag(true);
        return node;
    }

    private boolean isSameDataScheme(String srcFormSchemeKey, String formSchemeKey) {
        DesignFormSchemeDefine formScheme = this.nrDesignTimeController.queryFormSchemeDefine(formSchemeKey);
        DesignTaskDefine task = this.nrDesignTimeController.queryTaskDefine(formScheme.getTaskKey());
        String dataSchemeKey = task.getDataScheme();
        DesignFormSchemeDefine srcFormScheme = this.nrDesignTimeController.queryFormSchemeDefine(srcFormSchemeKey);
        DesignTaskDefine srcTask = this.nrDesignTimeController.queryTaskDefine(srcFormScheme.getTaskKey());
        String srcDataSchemeKey = srcTask.getDataScheme();
        return dataSchemeKey.equals(srcDataSchemeKey);
    }

    private void setFormSyncVO(FormSyncVO formSyncVO, String srcFormSchemeKey, Map<String, FormSyncVO> formulaSchemeMap) {
        FormSyncVO existFormSyncVO = formulaSchemeMap.get(srcFormSchemeKey);
        formSyncVO.setSrcTaskKey(existFormSyncVO.getSrcTaskKey());
        formSyncVO.setSrcFormSchemeTitle(existFormSyncVO.getSrcFormSchemeTitle());
        formSyncVO.setSrcFormSchemeCode(existFormSyncVO.getSrcFormSchemeCode());
        formSyncVO.setSrcTaskTitle(existFormSyncVO.getSrcTaskTitle());
        formSyncVO.setSrcFormSchemeKey(srcFormSchemeKey);
        formSyncVO.setPrintSchemes(existFormSyncVO.getPrintSchemes());
        formSyncVO.setFormulaSchemes(existFormSyncVO.getFormulaSchemes());
        formSyncVO.setFiFormulaSchemes(existFormSyncVO.getFiFormulaSchemes());
    }

    private void setAttSchemes(FormSyncVO formSyncVO, String srcFormSchemeKey, String formSchemeKey) throws Exception {
        List<IFormCopyAttSchemeInfo> formCopySchemeInfo = this.iDesignFormCopyService.getFormCopySchemeInfo(formSchemeKey, srcFormSchemeKey);
        if (CollectionUtils.isEmpty(formCopySchemeInfo)) {
            return;
        }
        ArrayList<FormCopyAttSchemeVO> printSchemes = new ArrayList<FormCopyAttSchemeVO>();
        ArrayList<FormCopyAttSchemeVO> formulaSchemes = new ArrayList<FormCopyAttSchemeVO>();
        ArrayList<FormCopyAttSchemeVO> fiFormulaSchemes = new ArrayList<FormCopyAttSchemeVO>();
        for (IFormCopyAttSchemeInfo info : formCopySchemeInfo) {
            FormCopyAttSchemeVO attScheme;
            if (SchemeType.PRINT_SCHEME == info.getSchemeType()) {
                DesignPrintTemplateSchemeDefine printScheme = this.nrDesignTimeController.queryPrintTemplateSchemeDefine(info.getSchemeKey());
                DesignPrintTemplateSchemeDefine srcPrintScheme = this.nrDesignTimeController.queryPrintTemplateSchemeDefine(info.getSrcSchemeKey());
                if (null == printScheme || null == srcPrintScheme) continue;
                attScheme = new FormCopyAttSchemeVO(printScheme.getKey(), srcPrintScheme.getKey(), printScheme.getTitle(), srcPrintScheme.getTitle());
                printSchemes.add(attScheme);
                continue;
            }
            DesignFormulaSchemeDefine srcFormulaScheme = this.nrDesignTimeController.queryFormulaSchemeDefine(info.getSrcSchemeKey());
            DesignFormulaSchemeDefine formulaScheme = this.nrDesignTimeController.queryFormulaSchemeDefine(info.getSchemeKey());
            if (null == srcFormulaScheme || null == formulaScheme) continue;
            attScheme = new FormCopyAttSchemeVO(formulaScheme.getKey(), srcFormulaScheme.getKey(), formulaScheme.getTitle(), srcFormulaScheme.getTitle());
            if (SchemeType.FORMULA_SCHEME == info.getSchemeType()) {
                formulaSchemes.add(attScheme);
                continue;
            }
            if (!this.fiFormulaButton(srcFormSchemeKey, formSchemeKey)) continue;
            fiFormulaSchemes.add(attScheme);
        }
        formSyncVO.setPrintSchemes(printSchemes);
        formSyncVO.setFormulaSchemes(formulaSchemes);
        formSyncVO.setFiFormulaSchemes(fiFormulaSchemes);
    }

    private boolean fiFormulaButton(String srcFormSchemeKey, String desFormSchemeKey) {
        DesignFormSchemeDefine srcFormSchemeDefine = this.nrDesignTimeController.queryFormSchemeDefine(srcFormSchemeKey);
        DesignFormSchemeDefine desFormSchemeDefine = this.nrDesignTimeController.queryFormSchemeDefine(desFormSchemeKey);
        DesignTaskDefine srcTaskDefine = this.nrDesignTimeController.queryTaskDefine(srcFormSchemeDefine.getTaskKey());
        DesignTaskDefine desTaskDefine = this.nrDesignTimeController.queryTaskDefine(desFormSchemeDefine.getTaskKey());
        return srcTaskDefine.getEfdcSwitch() && desTaskDefine.getEfdcSwitch();
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
                    }
                }
            }
        }
        attSchemeMap.put(FORMULA, formulaMap);
        attSchemeMap.put(FIFORMULA, fiFormulaMap);
        attSchemeMap.put(PRINT, printMap);
        return attSchemeMap;
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
}

