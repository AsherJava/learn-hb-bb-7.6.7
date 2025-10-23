/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.nr.definition.api.IDesignTimeFormulaController
 *  com.jiuqi.nr.definition.api.IDesignTimePrintController
 *  com.jiuqi.nr.definition.api.IDesignTimeViewController
 *  com.jiuqi.nr.definition.facade.DesignFormDefine
 *  com.jiuqi.nr.definition.facade.DesignFormGroupDefine
 *  com.jiuqi.nr.definition.facade.DesignFormSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignPrintTemplateSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 */
package com.jiuqi.nr.task.form.formcopy.service;

import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.nr.definition.api.IDesignTimeFormulaController;
import com.jiuqi.nr.definition.api.IDesignTimePrintController;
import com.jiuqi.nr.definition.api.IDesignTimeViewController;
import com.jiuqi.nr.definition.facade.DesignFormDefine;
import com.jiuqi.nr.definition.facade.DesignFormGroupDefine;
import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignPrintTemplateSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.task.form.common.NrFormCopyErrorEnum;
import com.jiuqi.nr.task.form.dto.FormCopyCheck;
import com.jiuqi.nr.task.form.dto.FormCopyFormCodeSameCheckVO;
import com.jiuqi.nr.task.form.dto.FormCopyInfoCheckVO;
import com.jiuqi.nr.task.form.dto.FormCopyInfoVO;
import com.jiuqi.nr.task.form.dto.FormCopyLinksVO;
import com.jiuqi.nr.task.form.dto.FormCopyOptions;
import com.jiuqi.nr.task.form.dto.FormCopyParamsVO;
import com.jiuqi.nr.task.form.dto.FormCopyPushLinkVO;
import com.jiuqi.nr.task.form.dto.FormCopySchemeVO;
import com.jiuqi.nr.task.form.dto.FormDoCopyParams;
import com.jiuqi.nr.task.form.dto.FormSyncPushExecuteVO;
import com.jiuqi.nr.task.form.dto.FormSyncPushSchemeVO;
import com.jiuqi.nr.task.form.dto.FormSyncPushVO;
import com.jiuqi.nr.task.form.dto.FormSyncRecordPushVO;
import com.jiuqi.nr.task.form.formcopy.FormCopyAttSchemeMap;
import com.jiuqi.nr.task.form.formcopy.FormCopyParams;
import com.jiuqi.nr.task.form.formcopy.FormCopyRecordPush;
import com.jiuqi.nr.task.form.formcopy.FormSyncParams;
import com.jiuqi.nr.task.form.formcopy.FormSyncPushResult;
import com.jiuqi.nr.task.form.formcopy.FormulaSyncResult;
import com.jiuqi.nr.task.form.formcopy.FormulaSyncResultAll;
import com.jiuqi.nr.task.form.formcopy.IDesignFormCopyPushManageService;
import com.jiuqi.nr.task.form.formcopy.IDesignFormCopyService;
import com.jiuqi.nr.task.form.formcopy.bean.IFormCopyAttSchemeInfo;
import com.jiuqi.nr.task.form.formcopy.bean.IFormCopyInfo;
import com.jiuqi.nr.task.form.formcopy.common.SchemeType;
import com.jiuqi.nr.task.form.formcopy.utils.FormCopyUtil;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
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
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Component
public class DesignFormCopyPushManageService
implements IDesignFormCopyPushManageService {
    @Autowired
    private IDesignFormCopyService iDesignFormCopyService;
    @Autowired
    private IDesignTimeViewController designTimeViewController;
    @Autowired
    private IDesignTimePrintController designTimePrintController;
    @Autowired
    private IDesignTimeFormulaController designTimeFormulaController;
    private static final String DONTCOPYKEY = "0000-0000";
    private static final String DONTCOPYTITLE = "\u4e0d\u590d\u5236";
    private static final Logger logger = LoggerFactory.getLogger(DesignFormCopyPushManageService.class);

    @Override
    public FormCopyLinksVO getSrcScheme(String srcFormSchemeKey) throws Exception {
        FormCopyLinksVO formCopyLinksVO = new FormCopyLinksVO();
        Map<String, List<FormCopySchemeVO>> srcSchemeMap = FormCopyUtil.querySchemesInFormScheme(srcFormSchemeKey);
        formCopyLinksVO.setSrcFormulaSchemes(srcSchemeMap.get("FORMULA"));
        formCopyLinksVO.setSrcPrintFormulaSchemes(srcSchemeMap.get("PRINT"));
        return formCopyLinksVO;
    }

    @Override
    public Map<String, FormCopyLinksVO> getSrcDesLinks(FormCopyPushLinkVO formCopyPushLinkVO) {
        List<String> desFormSchemeKeys = formCopyPushLinkVO.getDesFormSchemeKeys();
        String srcFormSchemeKey = formCopyPushLinkVO.getSrcFormSchemeKey();
        HashMap<String, FormCopyLinksVO> result = new HashMap<String, FormCopyLinksVO>();
        Map<String, List<FormCopySchemeVO>> srcFormSchemeMap = FormCopyUtil.querySchemesInFormScheme(srcFormSchemeKey);
        Map<String, FormCopySchemeVO> srcFormSchemeFormulaMap = srcFormSchemeMap.get("FORMULA").stream().collect(Collectors.toMap(FormCopySchemeVO::getTitle, a -> a));
        Map<String, FormCopySchemeVO> srcFormSchemePrintMap = srcFormSchemeMap.get("PRINT").stream().collect(Collectors.toMap(FormCopySchemeVO::getTitle, a -> a));
        for (String desFormSchemeKey : desFormSchemeKeys) {
            String srcKey;
            FormCopyLinksVO formCopyLinksVO = new FormCopyLinksVO();
            Map<String, List<FormCopySchemeVO>> desSchemeMap = FormCopyUtil.querySchemesInFormScheme(desFormSchemeKey);
            List<FormCopySchemeVO> formFormulaCopySchemeVOs = desSchemeMap.get("FORMULA");
            formFormulaCopySchemeVOs.add(0, new FormCopySchemeVO(DONTCOPYKEY, DONTCOPYTITLE));
            List<FormCopySchemeVO> printCopySchemeVOs = desSchemeMap.get("PRINT");
            printCopySchemeVOs.add(0, new FormCopySchemeVO(DONTCOPYKEY, DONTCOPYTITLE));
            formCopyLinksVO.setDesFormulaSchemes(formFormulaCopySchemeVOs);
            formCopyLinksVO.setDesPrintFormulaSchemes(printCopySchemeVOs);
            List<IFormCopyAttSchemeInfo> formCopySchemeInfo = this.iDesignFormCopyService.getFormCopySchemeInfo(desFormSchemeKey, srcFormSchemeKey);
            Map<String, Map<String, String>> attSchemeMap = FormCopyUtil.decomposeScheme(formCopySchemeInfo);
            Map<String, String> copyFormulaMap = attSchemeMap.get("FORMULA");
            Map<String, String> copyPrintMap = attSchemeMap.get("PRINT");
            for (FormCopySchemeVO formula : formFormulaCopySchemeVOs) {
                if (!srcFormSchemeFormulaMap.containsKey(formula.getTitle()) || copyFormulaMap.containsKey(srcKey = srcFormSchemeFormulaMap.get(formula.getTitle()).getKey())) continue;
                copyFormulaMap.put(srcKey, formula.getKey());
            }
            for (FormCopySchemeVO print : printCopySchemeVOs) {
                if (!srcFormSchemePrintMap.containsKey(print.getTitle()) || copyPrintMap.containsKey(srcKey = srcFormSchemePrintMap.get(print.getTitle()).getKey())) continue;
                copyPrintMap.put(srcKey, print.getKey());
            }
            formCopyLinksVO.setFormulaMap(copyFormulaMap);
            formCopyLinksVO.setPrintMap(copyPrintMap);
            result.put(desFormSchemeKey, formCopyLinksVO);
        }
        return result;
    }

    @Override
    public Map<String, List<FormCopyInfoVO>> getFormCopyPushCheck(FormCopyInfoCheckVO formCopyInfoCheckVO) throws Exception {
        HashMap<String, List<FormCopyInfoVO>> result = new HashMap<String, List<FormCopyInfoVO>>();
        List<String> srcFormKeys = formCopyInfoCheckVO.getSrcFormKeys();
        List<String> desFormSchemeKeys = formCopyInfoCheckVO.getDesFormSchemeKeys();
        List srcFormDefines = this.designTimeViewController.listForm(srcFormKeys);
        srcFormDefines.sort(Comparator.comparing(IBaseMetaItem::getOrder));
        for (String desFormSchemeKey : desFormSchemeKeys) {
            ArrayList<FormCopyInfoVO> formCopyInfoVOS = new ArrayList<FormCopyInfoVO>();
            List formDefines = this.designTimeViewController.listFormByFormScheme(desFormSchemeKey);
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

    @Override
    public boolean checkFormCodeSame(String desFormSchemeKey, String formCode) {
        DesignFormDefine designFormDefine = this.designTimeViewController.getFormByFormSchemeAndCode(desFormSchemeKey, formCode);
        return designFormDefine != null && StringUtils.hasLength(designFormDefine.getKey());
    }

    @Override
    public List<String> checkFormCodeSames(FormCopyFormCodeSameCheckVO formCopyFormCodeSameCheckVO) throws Exception {
        String desFormSchemeKey = formCopyFormCodeSameCheckVO.getDesFormSchemeKey();
        if (!StringUtils.hasText(desFormSchemeKey)) {
            throw new JQException((ErrorEnum)NrFormCopyErrorEnum.NRDESINGER_EXCEPTION_200);
        }
        List<String> formCodes = formCopyFormCodeSameCheckVO.getFormCodes();
        List desForms = this.designTimeViewController.listFormByFormScheme(desFormSchemeKey);
        ArrayList<String> results = new ArrayList<String>();
        if (!CollectionUtils.isEmpty(desForms)) {
            for (DesignFormDefine desForm : desForms) {
                if (!formCodes.contains(desForm.getFormCode())) continue;
                results.add(desForm.getFormCode());
            }
        }
        return results;
    }

    @Override
    public List<FormSyncPushResult> doFormCopyPush(List<FormDoCopyParams> allFormDoCopyParams) throws Exception {
        ArrayList<FormSyncPushResult> result = new ArrayList<FormSyncPushResult>();
        String srcFormSchemeKey = null;
        HashSet<String> desFormSchemeKeys = new HashSet<String>();
        HashSet<String> srcFormKeys = new HashSet<String>();
        for (FormDoCopyParams formDoCopyParams : allFormDoCopyParams) {
            ArrayList<FormulaSyncResult> formulaSyncResultList;
            FormSyncPushResult formSyncPushResult;
            block22: {
                Set<String> strings;
                formSyncPushResult = new FormSyncPushResult();
                srcFormSchemeKey = formDoCopyParams.getSrcFormSchemeKey();
                String formSchemeKey = formDoCopyParams.getDesFormSchemeKey();
                desFormSchemeKeys.add(formSchemeKey);
                ArrayList formCopyParams = new ArrayList();
                ArrayList<FormSyncParams> formSyncParams = new ArrayList<FormSyncParams>();
                if (!StringUtils.hasLength(formDoCopyParams.getDesFormGroupKey())) {
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
                formulaSyncResultList = new ArrayList<FormulaSyncResult>();
                try {
                    if (!formSyncParams.isEmpty()) {
                        FormulaSyncResultAll formulaSyncResultAll = this.iDesignFormCopyService.doSync(srcFormSchemeKey, formSchemeKey, formSyncParams, attSchemeMap, true, null, 0.0, 0.0);
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
                    if (formCopyParams.isEmpty()) break block22;
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
                    if (StringUtils.hasLength(formSyncPushResult.getOneDesFormKey())) break block22;
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
            List formGroupsByFormId = this.designTimeViewController.listFormGroupByForm(formSyncPushResult.getOneDesFormKey());
            if (!CollectionUtils.isEmpty(formGroupsByFormId)) {
                formSyncPushResult.setOneDesFormGroupKey(((DesignFormGroupDefine)formGroupsByFormId.get(0)).getKey());
            }
            result.add(formSyncPushResult);
        }
        HashMap<String, List<String>> attScheme = new HashMap<String, List<String>>();
        for (int i = 0; i < allFormDoCopyParams.size(); ++i) {
            FormDoCopyParams formDoCopyParams = allFormDoCopyParams.get(i);
            if (i != 0) continue;
            attScheme.put("FORMULA", new ArrayList<String>(formDoCopyParams.getFormulaAttMap().keySet()));
            attScheme.put("PRINT", new ArrayList<String>(formDoCopyParams.getPrintAttMap().keySet()));
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

    @Override
    public FormSyncPushExecuteVO getFormSyncInfo(String srcFormSchemeKey) throws Exception {
        List<IFormCopyInfo> iFormCopyInfoList = this.iDesignFormCopyService.getFormCopyInfoBySrcSchemeKey(srcFormSchemeKey);
        Map<String, List<IFormCopyInfo>> formCopyInfoListMapBySrcForm = iFormCopyInfoList.stream().collect(Collectors.groupingBy(IFormCopyInfo::getSrcFormKey));
        ArrayList<FormSyncPushVO> formSyncVOList = new ArrayList<FormSyncPushVO>();
        HashMap<String, FormSyncPushSchemeVO> desFormSchemeKeyToTitle = new HashMap<String, FormSyncPushSchemeVO>();
        List allSrcForms = this.designTimeViewController.listForm(formCopyInfoListMapBySrcForm.keySet());
        Map<String, DesignFormDefine> allSrcFormKeyToDefines = allSrcForms.stream().collect(Collectors.toMap(IBaseMetaItem::getKey, a -> a));
        List allDesFormKeys = iFormCopyInfoList.stream().map(IFormCopyInfo::getFormKey).collect(Collectors.toList());
        List allDesFormDefines = this.designTimeViewController.listForm(allDesFormKeys);
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
                    DesignFormSchemeDefine formSchemeDefineDes = this.designTimeViewController.getFormScheme(desFormSchemeKey);
                    if (formSchemeDefineDes == null || !StringUtils.hasText(formSchemeDefineDes.getKey()) || (designTaskDefine = this.designTimeViewController.getTask(formSchemeDefineDes.getTaskKey())) == null || !StringUtils.hasText(designTaskDefine.getKey())) continue;
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
                formSyncPushSchemeVO.setDesFormKey(desFormSchemeKey);
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
            desFormScheme.sort(Comparator.comparing(FormSyncPushSchemeVO::getDesTaskOrder));
            formSyncPushVO.setDesFormScheme(desFormScheme);
            formSyncVOList.add(formSyncPushVO);
        }
        formSyncVOList.sort(Comparator.comparing(FormSyncPushVO::getSrcFormOrder));
        FormSyncPushExecuteVO formSyncPushExecuteVO = new FormSyncPushExecuteVO();
        formSyncPushExecuteVO.setFormSyncPushVOs(formSyncVOList);
        formSyncPushExecuteVO.setSrcFormSchemeKey(srcFormSchemeKey);
        List<FormCopySchemeVO> formulaSchemes = FormCopyUtil.getFormulaSchemes(srcFormSchemeKey, this.designTimeFormulaController);
        ArrayList<FormCopySchemeVO> printSchemes = new ArrayList<FormCopySchemeVO>();
        try {
            List allPrintSchemeByFormScheme = this.designTimePrintController.listPrintTemplateSchemeByFormScheme(srcFormSchemeKey);
            for (DesignPrintTemplateSchemeDefine printScheme : allPrintSchemeByFormScheme) {
                printSchemes.add(new FormCopySchemeVO(printScheme.getKey(), printScheme.getTitle()));
            }
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrFormCopyErrorEnum.NRDESINGER_EXCEPTION_194, (Throwable)e);
        }
        formSyncPushExecuteVO.setFormulaSchemes(formulaSchemes);
        formSyncPushExecuteVO.setPrintSchemes(printSchemes);
        return formSyncPushExecuteVO;
    }

    @Override
    public List<FormSyncPushResult> doFormPushSync(FormSyncPushExecuteVO formSyncPushExecuteVO) throws Exception {
        String srcFormSchemeKey = formSyncPushExecuteVO.getSrcFormSchemeKey();
        Set<String> formulaSchemes = formSyncPushExecuteVO.getFormulaSchemes().stream().map(FormCopySchemeVO::getKey).collect(Collectors.toSet());
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
                if (SchemeType.FORMULA_SCHEME != info.getSchemeType() || !formulaSchemes.contains(info.getSrcSchemeKey())) continue;
                attSchemeMap.getFormulaSchemeMap().put(info.getSrcSchemeKey(), info.getSchemeKey());
            }
            ArrayList<FormulaSyncResult> formulaSyncResults = new ArrayList<FormulaSyncResult>();
            FormSyncPushSchemeVO formSyncPushSchemeVO = (FormSyncPushSchemeVO)desFormSchemeKeyToTask.get(desFormSchemeKey);
            formSyncPushResult.setDesTaskTitle(formSyncPushSchemeVO.getDesTaskTitle());
            formSyncPushResult.setDesFormSchemeKey(desFormSchemeKey);
            formSyncPushResult.setDesFormSchemeTitle(formSyncPushSchemeVO.getFormSchemeTitle());
            formSyncPushResultList.add(formSyncPushResult);
            try {
                FormulaSyncResultAll formulaSyncResultAll = this.iDesignFormCopyService.doSync(srcFormSchemeKey, desFormSchemeKey, formSyncParams, attSchemeMap, false, null, 0.0, 0.0);
                List<FormulaSyncResult> formulaSyncResults1 = formulaSyncResultAll.getFormulaSyncResult();
                if (!CollectionUtils.isEmpty(formulaSyncResults1)) {
                    formulaSyncResults.addAll(formulaSyncResults1);
                }
                formSyncPushResult.setOneDesFormKey(((FormSyncParams)formSyncParams.get(0)).getFormKey());
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
                formSyncPushResult.setHasError(true);
                formulaSyncResults.add(new FormulaSyncResult("\u5f02\u5e38", "\u5f02\u5e38\u9519\u8bef", e.getMessage()));
            }
            List formGroupsByFormId = this.designTimeViewController.listFormGroupByForm(formSyncPushResult.getOneDesFormKey());
            if (!CollectionUtils.isEmpty(formGroupsByFormId)) {
                formSyncPushResult.setOneDesFormGroupKey(((DesignFormGroupDefine)formGroupsByFormId.get(0)).getKey());
            }
            formSyncPushResult.setFormulaSyncResults(formulaSyncResults);
        }
        this.doSaveFormCopyPushRecord(formulaSchemes, printSchemes, srcFormSchemeKey, formSchemeMap);
        return formSyncPushResultList;
    }

    private void doSaveFormCopyPushRecord(Set<String> formulaSchemes, Set<String> printSchemes, String srcFormSchemeKey, Map<String, List<String>> formSchemeMap) throws JQException {
        HashMap<String, List<String>> attScheme = new HashMap<String, List<String>>();
        attScheme.put("FORMULA", new ArrayList<String>(formulaSchemes));
        attScheme.put("PRINT", new ArrayList<String>(printSchemes));
        Date updateTime = new Date();
        FormCopyRecordPush formCopyRecordPush = new FormCopyRecordPush();
        formCopyRecordPush.setKey(null);
        formCopyRecordPush.setUpdateTime(updateTime);
        formCopyRecordPush.setAttScheme(attScheme);
        formCopyRecordPush.setSrcFormSchemeKey(srcFormSchemeKey);
        formCopyRecordPush.setFormSchemeMap(formSchemeMap);
        int num = this.iDesignFormCopyService.saveFormCopyPushRecord(formCopyRecordPush);
    }

    @Override
    public List<FormSyncRecordPushVO> getFormSyncPushRecord(String srcFormSchemeKey) {
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
}

