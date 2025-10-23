/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.nr.definition.api.IDesignTimeFormulaController
 *  com.jiuqi.nr.definition.api.IDesignTimePrintController
 *  com.jiuqi.nr.definition.api.IDesignTimeViewController
 *  com.jiuqi.nr.definition.common.TaskType
 *  com.jiuqi.nr.definition.facade.DesignFormDefine
 *  com.jiuqi.nr.definition.facade.DesignFormGroupDefine
 *  com.jiuqi.nr.definition.facade.DesignFormSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignFormulaSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignPrintTemplateSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.task.api.tree.TreeData
 *  com.jiuqi.nr.task.api.tree.UITreeNode
 *  com.jiuqi.xlib.utils.CollectionUtils
 */
package com.jiuqi.nr.task.form.formcopy.service;

import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.nr.definition.api.IDesignTimeFormulaController;
import com.jiuqi.nr.definition.api.IDesignTimePrintController;
import com.jiuqi.nr.definition.api.IDesignTimeViewController;
import com.jiuqi.nr.definition.common.TaskType;
import com.jiuqi.nr.definition.facade.DesignFormDefine;
import com.jiuqi.nr.definition.facade.DesignFormGroupDefine;
import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignFormulaSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignPrintTemplateSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.task.api.tree.TreeData;
import com.jiuqi.nr.task.api.tree.UITreeNode;
import com.jiuqi.nr.task.form.common.NrFormCopyErrorEnum;
import com.jiuqi.nr.task.form.dto.FormCopyAttSchemeVO;
import com.jiuqi.nr.task.form.dto.FormCopyCheck;
import com.jiuqi.nr.task.form.dto.FormCopyInfoParams;
import com.jiuqi.nr.task.form.dto.FormCopyInfoVO;
import com.jiuqi.nr.task.form.dto.FormCopyLinksVO;
import com.jiuqi.nr.task.form.dto.FormCopyOptions;
import com.jiuqi.nr.task.form.dto.FormCopyParamsVO;
import com.jiuqi.nr.task.form.dto.FormCopySchemeVO;
import com.jiuqi.nr.task.form.dto.FormCopyTaskTreeNode;
import com.jiuqi.nr.task.form.dto.FormDoCopyParams;
import com.jiuqi.nr.task.form.dto.FormSyncFormParamsVO;
import com.jiuqi.nr.task.form.dto.FormSyncParamsVO;
import com.jiuqi.nr.task.form.dto.FormSyncRecordVO;
import com.jiuqi.nr.task.form.dto.FormSyncVO;
import com.jiuqi.nr.task.form.dto.SyncCoverForm;
import com.jiuqi.nr.task.form.formcopy.FormCopyAttSchemeMap;
import com.jiuqi.nr.task.form.formcopy.FormCopyParams;
import com.jiuqi.nr.task.form.formcopy.FormCopyRecord;
import com.jiuqi.nr.task.form.formcopy.FormSyncParams;
import com.jiuqi.nr.task.form.formcopy.FormSyncResult;
import com.jiuqi.nr.task.form.formcopy.FormulaSyncResult;
import com.jiuqi.nr.task.form.formcopy.FormulaSyncResultAll;
import com.jiuqi.nr.task.form.formcopy.IDesignFormCopyManageService;
import com.jiuqi.nr.task.form.formcopy.IDesignFormCopyService;
import com.jiuqi.nr.task.form.formcopy.bean.IFormCopyAttSchemeInfo;
import com.jiuqi.nr.task.form.formcopy.bean.IFormCopyInfo;
import com.jiuqi.nr.task.form.formcopy.common.SchemeType;
import com.jiuqi.nr.task.form.formcopy.utils.FormCopyUtil;
import com.jiuqi.xlib.utils.CollectionUtils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class DesignFormCopyManageService
implements IDesignFormCopyManageService {
    private static final Logger logger = LoggerFactory.getLogger(DesignFormCopyManageService.class);
    @Autowired
    private IDesignFormCopyService iDesignFormCopyService;
    @Autowired
    private IDesignTimeViewController designTimeViewController;
    @Autowired
    private IDesignTimePrintController designTimePrintController;
    @Autowired
    private IDesignTimeFormulaController designTimeFormulaController;

    @Override
    public List<UITreeNode<FormCopyTaskTreeNode>> getTaskTree() {
        ArrayList<UITreeNode<FormCopyTaskTreeNode>> tree_Task = new ArrayList<UITreeNode<FormCopyTaskTreeNode>>();
        List listDesignTask = this.designTimeViewController.listTaskByType(TaskType.TASK_TYPE_DEFAULT);
        if (listDesignTask != null) {
            listDesignTask.forEach(task -> tree_Task.add(this.getGroupTreeNode((DesignTaskDefine)task)));
        }
        this.taskSchemeTree(tree_Task);
        return tree_Task;
    }

    private UITreeNode<FormCopyTaskTreeNode> getGroupTreeNode(DesignTaskDefine task) {
        UITreeNode node = new UITreeNode((TreeData)new FormCopyTaskTreeNode(task));
        node.setLeaf(false);
        node.setKey(task.getKey());
        node.setTitle(task.getTitle());
        node.setDisabled(true);
        return node;
    }

    private void taskSchemeTree(List<UITreeNode<FormCopyTaskTreeNode>> tree_Task) {
        for (UITreeNode<FormCopyTaskTreeNode> taskTree : tree_Task) {
            ArrayList tree_Scheme = new ArrayList();
            List listFormSchemes = this.designTimeViewController.listFormSchemeByTask(((FormCopyTaskTreeNode)taskTree.getData()).getKey());
            if (listFormSchemes != null) {
                String taskTitle = ((FormCopyTaskTreeNode)taskTree.getData()).getTitle();
                String taskOrder = ((FormCopyTaskTreeNode)taskTree.getData()).getOrder();
                listFormSchemes.forEach(scheme -> tree_Scheme.add(this.getGroupTreeNode((DesignFormSchemeDefine)scheme, taskTitle, taskOrder)));
            }
            taskTree.setChildren(tree_Scheme);
        }
    }

    private UITreeNode<FormCopyTaskTreeNode> getGroupTreeNode(DesignFormSchemeDefine scheme, String taskTitle, String taskOrder) {
        UITreeNode node = new UITreeNode((TreeData)new FormCopyTaskTreeNode(scheme, taskTitle, taskOrder));
        node.setLeaf(true);
        node.setChildren(null);
        node.setKey(scheme.getKey());
        node.setTitle(scheme.getTitle());
        return node;
    }

    @Override
    public FormCopyLinksVO getSchemeCopyLinks(String srcFormSchemeKey, String desFormSchemeKey) {
        FormCopyLinksVO formCopyLinksVO = new FormCopyLinksVO();
        Map<String, List<FormCopySchemeVO>> srcSchemeMap = FormCopyUtil.querySchemesInFormScheme(srcFormSchemeKey);
        formCopyLinksVO.setSrcFormulaSchemes(srcSchemeMap.get("FORMULA"));
        formCopyLinksVO.setSrcPrintFormulaSchemes(srcSchemeMap.get("PRINT"));
        Map<String, List<FormCopySchemeVO>> desSchemeMap = FormCopyUtil.querySchemesInFormScheme(desFormSchemeKey);
        formCopyLinksVO.setDesFormulaSchemes(desSchemeMap.get("FORMULA"));
        formCopyLinksVO.setDesPrintFormulaSchemes(desSchemeMap.get("PRINT"));
        List<IFormCopyAttSchemeInfo> formCopySchemeInfo = this.iDesignFormCopyService.getFormCopySchemeInfo(desFormSchemeKey, srcFormSchemeKey);
        Map<String, Map<String, String>> attSchemeMap = FormCopyUtil.decomposeScheme(formCopySchemeInfo);
        formCopyLinksVO.setFormulaMap(attSchemeMap.get("FORMULA"));
        formCopyLinksVO.setPrintMap(attSchemeMap.get("PRINT"));
        return formCopyLinksVO;
    }

    @Override
    public List<FormCopyInfoVO> getFormCopyInfo(FormCopyInfoParams formCopyInfoParams) throws Exception {
        ArrayList<FormCopyInfoVO> formCopyInfoVOs = new ArrayList<FormCopyInfoVO>();
        List formsForDesFormScheme = this.designTimeViewController.listFormByFormScheme(formCopyInfoParams.getDesFormSchemeKey());
        Map<String, DesignFormDefine> keyToFormsForDesFormScheme = formsForDesFormScheme.stream().collect(Collectors.toMap(IBaseMetaItem::getKey, a -> a));
        Map<String, DesignFormDefine> codeToFormsForDesFormScheme = formsForDesFormScheme.stream().collect(Collectors.toMap(FormDefine::getFormCode, a -> a));
        List<IFormCopyInfo> formCopyInfos = this.iDesignFormCopyService.getFormCopyInfoBySchemeKey(formCopyInfoParams.getDesFormSchemeKey());
        for (int i = formCopyInfos.size() - 1; i >= 0; --i) {
            if (keyToFormsForDesFormScheme.get(formCopyInfos.get(i).getFormKey()) != null) continue;
            this.iDesignFormCopyService.deleteCopyFormInfo(formCopyInfos.get(i).getFormKey());
            formCopyInfos.remove(i);
        }
        Map<String, IFormCopyInfo> srcKeyToFormCopyInfo = formCopyInfos.stream().collect(Collectors.toMap(IFormCopyInfo::getSrcFormKey, a -> a));
        List srcFormDefines = this.designTimeViewController.listForm(formCopyInfoParams.getSrcFormKeys());
        for (DesignFormDefine srcFormDefine : srcFormDefines) {
            DesignFormDefine designFormDefine;
            Optional<DesignFormDefine> desFMForm;
            String srcFormKey = srcFormDefine.getKey();
            FormCopyInfoVO formCopyInfoVO = new FormCopyInfoVO();
            formCopyInfoVO.setSrcFormKey(srcFormKey);
            formCopyInfoVO.setSrcFormTitle(srcFormDefine.getTitle());
            formCopyInfoVO.setSrcFormCode(srcFormDefine.getFormCode());
            boolean flag = true;
            if ((srcFormDefine.getFormType().getValue() == 9 || srcFormDefine.getFormType().getValue() == 11) && (desFMForm = formsForDesFormScheme.stream().filter(a -> a.getFormType().getValue() == 9 || a.getFormType().getValue() == 11).findFirst()).isPresent()) {
                if (srcKeyToFormCopyInfo.get(srcFormDefine.getKey()) == null) {
                    formCopyInfoVO.setCheckResultKey(FormCopyCheck.HASFMDM.getKey());
                    formCopyInfoVO.setCheckResult(FormCopyCheck.HASFMDM.getValue());
                } else {
                    formCopyInfoVO.setCheckResultKey(FormCopyCheck.COPIEDFMDM.getKey());
                    formCopyInfoVO.setCheckResult(FormCopyCheck.COPIEDFMDM.getValue());
                }
                formCopyInfoVO.setOpsResultKey(FormCopyOptions.UPDATE.getKey());
                designFormDefine = desFMForm.get();
                formCopyInfoVO.setExistFormKey(designFormDefine.getKey());
                formCopyInfoVO.setExistFormCode(designFormDefine.getFormCode());
                formCopyInfoVOs.add(formCopyInfoVO);
                continue;
            }
            IFormCopyInfo iFormCopyInfo = srcKeyToFormCopyInfo.get(srcFormKey);
            if (iFormCopyInfo != null) {
                formCopyInfoVO.setCheckResultKey(FormCopyCheck.REPEATCOPY.getKey());
                formCopyInfoVO.setCheckResult(FormCopyCheck.REPEATCOPY.getValue());
                formCopyInfoVO.setExistFormKey(iFormCopyInfo.getFormKey());
                designFormDefine = keyToFormsForDesFormScheme.get(iFormCopyInfo.getFormKey());
                if (designFormDefine != null) {
                    formCopyInfoVO.setExistFormCode(designFormDefine.getFormCode());
                }
                formCopyInfoVO.setOpsResultKey(FormCopyOptions.UPDATE.getKey());
                flag = false;
            }
            if (flag && (designFormDefine = codeToFormsForDesFormScheme.get(srcFormDefine.getFormCode())) != null) {
                formCopyInfoVO.setCheckResultKey(FormCopyCheck.REPEATCODE.getKey());
                formCopyInfoVO.setCheckResult(FormCopyCheck.REPEATCODE.getValue());
                formCopyInfoVO.setExistFormKey(designFormDefine.getKey());
                formCopyInfoVO.setExistFormCode(designFormDefine.getFormCode());
                formCopyInfoVO.setOpsResultKey(FormCopyOptions.NEWCOPY.getKey());
                formCopyInfoVO.setDesFormCode(designFormDefine.getFormCode());
                flag = false;
            }
            if (flag) {
                formCopyInfoVO.setCheckResultKey(FormCopyCheck.NORMAL.getKey());
                formCopyInfoVO.setCheckResult(FormCopyCheck.NORMAL.getValue());
                formCopyInfoVO.setOpsResultKey(FormCopyOptions.NEWCOPY.getKey());
                formCopyInfoVO.setDesFormCode(srcFormDefine.getFormCode());
            }
            formCopyInfoVOs.add(formCopyInfoVO);
        }
        return formCopyInfoVOs;
    }

    @Override
    public List<FormulaSyncResult> doFormCopy(FormDoCopyParams formDoCopyParams, AsyncTaskMonitor monitor, StringBuilder allFormCopyMessage) throws Exception {
        Collection<String> values;
        DesignFormGroupDefine formGroup;
        FormCopyUtil.setProgressAndMessage(monitor, 0.01, "\u62a5\u8868\u540c\u6b65\u5373\u5c06\u5f00\u59cb");
        String srcFormSchemeKey = formDoCopyParams.getSrcFormSchemeKey();
        String formSchemeKey = formDoCopyParams.getDesFormSchemeKey();
        ArrayList<FormCopyParams> formCopyParams = new ArrayList<FormCopyParams>();
        ArrayList<FormSyncParams> formSyncParams = new ArrayList<FormSyncParams>();
        if (StringUtils.hasText(formDoCopyParams.getDesFormGroupKey()) && (formGroup = this.designTimeViewController.getFormGroup(formDoCopyParams.getDesFormGroupKey())) == null) {
            formDoCopyParams.setDesFormGroupKey(null);
        }
        if (!StringUtils.hasText(formDoCopyParams.getDesFormGroupKey())) {
            List designFormGroupDefines = this.designTimeViewController.listFormGroupByFormScheme(formDoCopyParams.getDesFormSchemeKey());
            Iterator iterator = designFormGroupDefines.iterator();
            while (iterator.hasNext()) {
                DesignFormGroupDefine formGroupDefine = (DesignFormGroupDefine)iterator.next();
                if (!"\u9ed8\u8ba4\u8868\u5355\u5206\u7ec4".equals(formGroupDefine.getTitle())) continue;
                formDoCopyParams.setDesFormGroupKey(formGroupDefine.getKey());
            }
            if (!StringUtils.hasText(formDoCopyParams.getDesFormGroupKey())) {
                formDoCopyParams.setDesFormGroupKey(((DesignFormGroupDefine)designFormGroupDefines.get(0)).getKey());
            }
        }
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
        attSchemeMap.setPrintSchemeMap(formDoCopyParams.getPrintAttMap());
        ArrayList<FormulaSyncResult> formulaSyncResultList = new ArrayList<FormulaSyncResult>();
        ArrayList<String> newFormKeys = new ArrayList<String>();
        if (!formSyncParams.isEmpty()) {
            FormulaSyncResultAll formulaSyncResultAll = this.iDesignFormCopyService.doSync(srcFormSchemeKey, formSchemeKey, formSyncParams, attSchemeMap, true, null, 0.0, 0.0);
            allFormCopyMessage.append((CharSequence)formulaSyncResultAll.getFormCopyMessage());
            if (formulaSyncResultAll != null) {
                if (!CollectionUtils.isEmpty(formulaSyncResultAll.getNewFormLink())) {
                    values = formulaSyncResultAll.getNewFormLink().values();
                    newFormKeys.addAll(values);
                }
                if (!CollectionUtils.isEmpty(formulaSyncResultAll.getFormulaSyncResult())) {
                    formulaSyncResultList.addAll(formulaSyncResultAll.getFormulaSyncResult());
                }
            }
        }
        if (!formCopyParams.isEmpty()) {
            FormulaSyncResultAll formulaCopyResultAll = this.iDesignFormCopyService.doCopy(srcFormSchemeKey, formSchemeKey, formCopyParams, attSchemeMap);
            allFormCopyMessage.append((CharSequence)formulaCopyResultAll.getFormCopyMessage());
            if (formulaCopyResultAll != null) {
                if (!CollectionUtils.isEmpty(formulaCopyResultAll.getNewFormLink())) {
                    values = formulaCopyResultAll.getNewFormLink().values();
                    newFormKeys.addAll(values);
                }
                if (!CollectionUtils.isEmpty(formulaCopyResultAll.getFormulaSyncResult())) {
                    formulaSyncResultList.addAll(formulaCopyResultAll.getFormulaSyncResult());
                }
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
        FormCopyUtil.setFinish(monitor, "\u62a5\u8868\u540c\u6b65\u5b8c\u6210", formulaSyncResultList);
        return formulaSyncResultList;
    }

    @Override
    public List<FormSyncVO> getFormSyncInfo(String desFormSchemeKey) throws Exception {
        List<IFormCopyInfo> iFormCopyInfoList = this.iDesignFormCopyService.getFormCopyInfoBySchemeKey(desFormSchemeKey);
        ArrayList<FormSyncVO> formSyncVOList = new ArrayList<FormSyncVO>();
        HashMap<String, FormSyncVO> formulaSchemeMap = new HashMap<String, FormSyncVO>();
        List desFormKeys = iFormCopyInfoList.stream().map(IFormCopyInfo::getFormKey).collect(Collectors.toList());
        List srcFormKeys = iFormCopyInfoList.stream().map(IFormCopyInfo::getSrcFormKey).collect(Collectors.toList());
        Map<String, DesignFormDefine> keyToDesFormDefineMaps = this.designTimeViewController.listForm(desFormKeys).stream().collect(Collectors.toMap(IBaseMetaItem::getKey, a -> a));
        Map<String, DesignFormDefine> keyToSrcFormDefineMaps = this.designTimeViewController.listForm(srcFormKeys).stream().collect(Collectors.toMap(IBaseMetaItem::getKey, a -> a));
        for (IFormCopyInfo iFormCopyInfo : iFormCopyInfoList) {
            String srcformSchemeKey = iFormCopyInfo.getSrcFormSchemeKey();
            String formKey = iFormCopyInfo.getFormKey();
            String srcFormKey = iFormCopyInfo.getSrcFormKey();
            DesignFormDefine formDefineDes = keyToDesFormDefineMaps.get(formKey);
            DesignFormDefine formDefineSrc = keyToSrcFormDefineMaps.get(srcFormKey);
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
                DesignFormSchemeDefine formSchemeDefineSrc = this.designTimeViewController.getFormScheme(srcformSchemeKey);
                if (formSchemeDefineSrc == null) {
                    throw new JQException((ErrorEnum)NrFormCopyErrorEnum.NRDESINGER_EXCEPTION_193);
                }
                formSyncVO.setSrcTaskKey(formSchemeDefineSrc.getTaskKey());
                formSyncVO.setSrcFormSchemeTitle(formSchemeDefineSrc.getTitle());
                formSyncVO.setSrcFormSchemeCode(formSchemeDefineSrc.getFormSchemeCode());
                try {
                    DesignTaskDefine taskDefine = this.designTimeViewController.getTask(formSchemeDefineSrc.getTaskKey());
                    formSyncVO.setSrcTaskTitle(taskDefine.getTitle());
                    formSyncVO.setSrcFormSchemeKey(srcformSchemeKey);
                    formSyncVO.setSrcTaskCode(taskDefine.getTaskCode());
                    this.setAttSchemes(formSyncVO, srcformSchemeKey, desFormSchemeKey);
                }
                catch (Exception e) {
                    throw new JQException((ErrorEnum)NrFormCopyErrorEnum.NRDESINGER_EXCEPTION_194, (Throwable)e);
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

    private void setAttSchemes(FormSyncVO formSyncVO, String srcFormSchemeKey, String formSchemeKey) throws Exception {
        Map<String, IFormCopyAttSchemeInfo> srcFormulaSchemeRecordMap;
        List<IFormCopyAttSchemeInfo> formCopySchemeInfo = this.iDesignFormCopyService.getFormCopySchemeInfo(formSchemeKey, srcFormSchemeKey);
        if (CollectionUtils.isEmpty(formCopySchemeInfo)) {
            return;
        }
        ArrayList<FormCopyAttSchemeVO> printSchemes = new ArrayList<FormCopyAttSchemeVO>();
        ArrayList<FormCopyAttSchemeVO> formulaSchemes = new ArrayList<FormCopyAttSchemeVO>();
        Map<String, IFormCopyAttSchemeInfo> srcPrintSchemeRecordMap = formCopySchemeInfo.stream().filter(a -> SchemeType.PRINT_SCHEME == a.getSchemeType()).collect(Collectors.toMap(IFormCopyAttSchemeInfo::getSrcSchemeKey, a -> a, (k1, k2) -> k1));
        if (!CollectionUtils.isEmpty(srcPrintSchemeRecordMap)) {
            Set<String> srcPrintSchemeRecordKeys = srcPrintSchemeRecordMap.keySet();
            List srcPrintSchemeRecordDefines = this.designTimePrintController.listPrintTemplateSchemeByFormScheme(srcFormSchemeKey).stream().filter(a -> a != null && srcPrintSchemeRecordKeys.contains(a.getKey())).sorted((o1, o2) -> o1.getOrder().compareTo(o2.getOrder())).collect(Collectors.toList());
            for (DesignPrintTemplateSchemeDefine srcPrintScheme : srcPrintSchemeRecordDefines) {
                IFormCopyAttSchemeInfo iFormCopyAttSchemeInfo = srcPrintSchemeRecordMap.get(srcPrintScheme.getKey());
                DesignPrintTemplateSchemeDefine printScheme = this.designTimePrintController.getPrintTemplateScheme(iFormCopyAttSchemeInfo.getSchemeKey());
                if (null == printScheme) continue;
                FormCopyAttSchemeVO attScheme = new FormCopyAttSchemeVO(printScheme.getKey(), srcPrintScheme.getKey(), printScheme.getTitle(), srcPrintScheme.getTitle());
                printSchemes.add(attScheme);
            }
        }
        if (!CollectionUtils.isEmpty(srcFormulaSchemeRecordMap = formCopySchemeInfo.stream().filter(a -> SchemeType.PRINT_SCHEME != a.getSchemeType()).collect(Collectors.toMap(IFormCopyAttSchemeInfo::getSrcSchemeKey, a -> a, (k1, k2) -> k1)))) {
            Set<String> srcFormulaSchemeRecordKeys = srcFormulaSchemeRecordMap.keySet();
            List srcFormulaSchemeRecordDefines = this.designTimeFormulaController.listFormulaSchemeByFormScheme(srcFormSchemeKey).stream().filter(a -> a != null && srcFormulaSchemeRecordKeys.contains(a.getKey())).sorted((o1, o2) -> o1.getOrder().compareTo(o2.getOrder())).collect(Collectors.toList());
            for (DesignFormulaSchemeDefine srcFormulaScheme : srcFormulaSchemeRecordDefines) {
                IFormCopyAttSchemeInfo iFormCopyAttSchemeInfo = srcFormulaSchemeRecordMap.get(srcFormulaScheme.getKey());
                DesignFormulaSchemeDefine formulaScheme = this.designTimeFormulaController.getFormulaScheme(iFormCopyAttSchemeInfo.getSchemeKey());
                if (null == formulaScheme) continue;
                FormCopyAttSchemeVO attScheme = new FormCopyAttSchemeVO(formulaScheme.getKey(), srcFormulaScheme.getKey(), formulaScheme.getTitle(), srcFormulaScheme.getTitle());
                formulaSchemes.add(attScheme);
            }
        }
        formSyncVO.setPrintSchemes(printSchemes);
        formSyncVO.setFormulaSchemes(formulaSchemes);
    }

    @Override
    public List<FormSyncRecordVO> getFormSyncRecord(String desFormSchemeKey) {
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

    @Override
    public List<FormSyncResult> doFormSync(List<FormSyncParamsVO> formSyncParamsVOList, AsyncTaskMonitor monitor, StringBuilder allFormCopyMessage) throws Exception {
        if (formSyncParamsVOList == null || formSyncParamsVOList.isEmpty()) {
            throw new JQException((ErrorEnum)NrFormCopyErrorEnum.NRDESINGER_EXCEPTION_195);
        }
        FormCopyUtil.setProgressAndMessage(monitor, 0.01, "\u62a5\u8868\u540c\u6b65\u5373\u5c06\u5f00\u59cb");
        ArrayList<String> formKeys = new ArrayList<String>();
        HashMap<String, FormCopyAttSchemeMap> attScheme = new HashMap<String, FormCopyAttSchemeMap>();
        ArrayList<FormSyncResult> result = new ArrayList<FormSyncResult>();
        double thisProgress = 0.95 / (double)formSyncParamsVOList.size();
        double startProgress = 0.01;
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
            attSchemeMap.setPrintSchemeMap(copySyncParam.getPrintAttMap());
            FormulaSyncResultAll formulaSyncResultAll = this.iDesignFormCopyService.doSync(srcFormSchemeKey, formSchemeKey, formSyncParams, attSchemeMap, false, monitor, startProgress, thisProgress);
            allFormCopyMessage.append((CharSequence)formulaSyncResultAll.getFormCopyMessage());
            List<FormulaSyncResult> formulaSyncResults = formulaSyncResultAll.getFormulaSyncResult();
            if (formulaSyncResults != null && !formulaSyncResults.isEmpty()) {
                FormSyncResult formSyncResult = new FormSyncResult(srcFormSchemeKey, formSchemeKey, formulaSyncResults);
                result.add(formSyncResult);
            }
            attScheme.put(srcFormSchemeKey, attSchemeMap);
            startProgress += thisProgress;
        }
        FormCopyUtil.setProgressAndMessage(monitor, 0.95, "\u62a5\u8868\u540c\u6b65\u8fc7\u7a0b\u5b8c\u6210\uff0c\u751f\u6210\u5386\u53f2\u8bb0\u5f55\u4e2d");
        String desFormSchemeKey = formSyncParamsVOList.get(0).getDesFormSchemeKey();
        Date updateTime = new Date();
        FormCopyRecord formCopyRecord = new FormCopyRecord();
        formCopyRecord.setKey(null);
        formCopyRecord.setUpdateTime(updateTime);
        formCopyRecord.setAttScheme(attScheme);
        formCopyRecord.setFormSchemeKey(desFormSchemeKey);
        formCopyRecord.setFormKeys(formKeys);
        this.iDesignFormCopyService.saveFormCopyRecord(formCopyRecord);
        return result;
    }

    @Override
    public List<SyncCoverForm> messageBox(List<String> formKeys) {
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
            DesignFormDefine formDefine = this.designTimeViewController.getForm(formKey);
            if (0 == formDefine.getUpdateTime().compareTo(iFormCopyInfo.getUpdateTime())) continue;
            syncCoverForm.setKey(formKey);
            syncCoverForm.setCode(formDefine.getFormCode());
            syncCoverForm.setTitle(formDefine.getTitle());
            syncCoverFormList.add(syncCoverForm);
        }
        return syncCoverFormList;
    }
}

