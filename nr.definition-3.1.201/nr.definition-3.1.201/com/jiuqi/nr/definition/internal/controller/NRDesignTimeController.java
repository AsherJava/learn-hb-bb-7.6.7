/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.definition.controller.IDataDefinitionDesignTimeController
 *  com.jiuqi.np.definition.facade.DesignFieldDefine
 *  com.jiuqi.np.definition.facade.DesignTableDefine
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService
 */
package com.jiuqi.nr.definition.internal.controller;

import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.definition.controller.IDataDefinitionDesignTimeController;
import com.jiuqi.np.definition.facade.DesignFieldDefine;
import com.jiuqi.np.definition.facade.DesignTableDefine;
import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.nr.definition.api.IDesignTimePrintController;
import com.jiuqi.nr.definition.common.TaskType;
import com.jiuqi.nr.definition.controller.IDesignTimeViewController;
import com.jiuqi.nr.definition.controller.IFormulaDesignTimeController;
import com.jiuqi.nr.definition.controller.IPrintDesignTimeController;
import com.jiuqi.nr.definition.exception.NrDefinitionErrorEnum;
import com.jiuqi.nr.definition.facade.DesignAnalysisFormParamDefine;
import com.jiuqi.nr.definition.facade.DesignAnalysisSchemeParamDefine;
import com.jiuqi.nr.definition.facade.DesignDataLinkDefine;
import com.jiuqi.nr.definition.facade.DesignDataLinkMappingDefine;
import com.jiuqi.nr.definition.facade.DesignDataRegionDefine;
import com.jiuqi.nr.definition.facade.DesignDimensionFilter;
import com.jiuqi.nr.definition.facade.DesignEntityLinkageDefine;
import com.jiuqi.nr.definition.facade.DesignEnumLinkageSettingDefine;
import com.jiuqi.nr.definition.facade.DesignFormDefine;
import com.jiuqi.nr.definition.facade.DesignFormGroupDefine;
import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignFormulaDefine;
import com.jiuqi.nr.definition.facade.DesignFormulaSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignPrintComTemDefine;
import com.jiuqi.nr.definition.facade.DesignPrintSettingDefine;
import com.jiuqi.nr.definition.facade.DesignPrintTemplateDefine;
import com.jiuqi.nr.definition.facade.DesignPrintTemplateSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignRegionSettingDefine;
import com.jiuqi.nr.definition.facade.DesignReportTagDefine;
import com.jiuqi.nr.definition.facade.DesignReportTemplateDefine;
import com.jiuqi.nr.definition.facade.DesignSchemePeriodLinkDefine;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.definition.facade.DesignTaskGroupDefine;
import com.jiuqi.nr.definition.facade.DesignTaskLinkDefine;
import com.jiuqi.nr.definition.facade.formula.DesignFormulaCondition;
import com.jiuqi.nr.definition.facade.formula.DesignFormulaConditionLink;
import com.jiuqi.nr.definition.facade.formula.FormulaCondition;
import com.jiuqi.nr.definition.facade.formula.FormulaConditionLink;
import com.jiuqi.nr.definition.facade.print.PrintSchemeAttributeDefine;
import com.jiuqi.nr.definition.facade.print.PrintTemplateAttributeDefine;
import com.jiuqi.nr.definition.facade.print.WordLabelDefine;
import com.jiuqi.nr.definition.internal.impl.DesignCaliberGroupLink;
import com.jiuqi.nr.definition.internal.impl.DesignFormGroupLink;
import com.jiuqi.nr.definition.internal.impl.DesignTaskFlowsDefine;
import com.jiuqi.nr.definition.internal.service.DesignDataLinkDefineService;
import com.jiuqi.nr.definition.internal.service.DesignDimensionFilterService;
import com.jiuqi.nr.definition.internal.service.DesignFormDefineService;
import com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Controller
public class NRDesignTimeController {
    @Autowired
    private IDesignTimeViewController designTimeViewController;
    @Autowired
    private IFormulaDesignTimeController formulaDesignTimeController;
    @Autowired
    private IPrintDesignTimeController printDesignTimeController;
    @Autowired
    private IDataDefinitionDesignTimeController dataDefinitionDesignTimeController;
    @Autowired
    private DesignDataLinkDefineService dataLinkService;
    @Autowired
    private DesignFormDefineService formDefineService;
    @Autowired
    private INvwaSystemOptionService iNvwaSystemOptionService;
    @Autowired
    private DesignDimensionFilterService designDimensionFilterService;
    @Autowired
    private IDesignTimePrintController designTimePrintController;

    public DesignTaskDefine createTaskDefine() {
        return this.designTimeViewController.createTaskDefine();
    }

    public String insertTaskDefine(DesignTaskDefine taskDefine) throws JQException {
        return this.designTimeViewController.insertTaskDefine(taskDefine);
    }

    public void updateTaskDefine(DesignTaskDefine taskDefine) throws JQException {
        if (this.isCommonNetwork()) {
            if (this.isSameServeCode(taskDefine.getOwnerLevelAndId())) {
                this.designTimeViewController.updateTaskDefine(taskDefine);
            }
        } else {
            this.designTimeViewController.updateTaskDefine(taskDefine);
        }
    }

    public void deleteTaskDefine(String taskKey, boolean delLinkedParam) throws JQException {
        if (this.isCommonNetwork()) {
            DesignTaskDefine designTaskDefine = this.designTimeViewController.queryTaskDefine(taskKey);
            if (designTaskDefine != null && this.isSameServeCode(designTaskDefine.getOwnerLevelAndId())) {
                this.designTimeViewController.deleteTaskDefine(taskKey, delLinkedParam);
            }
        } else {
            this.designTimeViewController.deleteTaskDefine(taskKey, delLinkedParam);
        }
    }

    public DesignTaskDefine queryTaskDefine(String taskKey) {
        return this.designTimeViewController.queryTaskDefine(taskKey);
    }

    public DesignTaskFlowsDefine queryFlowDefine(String key) throws Exception {
        return this.designTimeViewController.queryFlowDefine(key);
    }

    public DesignTaskDefine queryTaskDefineByCode(String taskCode) {
        return this.designTimeViewController.queryTaskDefineByCode(taskCode);
    }

    public DesignTaskDefine queryTaskDefineByFilePrefix(String filePrefix) {
        return this.designTimeViewController.queryTaskDefineByFilePrefix(filePrefix);
    }

    public List<DesignTaskDefine> getAllTaskDefines() {
        return this.designTimeViewController.getAllTaskDefines();
    }

    public int countTask() {
        return this.designTimeViewController.countTask();
    }

    public int countForm() {
        return this.designTimeViewController.countAllForm();
    }

    public List<DesignTaskDefine> getAllReportTaskDefines() {
        return this.designTimeViewController.getAllReportTaskDefines();
    }

    public List<DesignTaskDefine> getAllTaskDefinesByType(TaskType type) {
        return this.designTimeViewController.getAllTaskDefinesByType(type);
    }

    public DesignFormSchemeDefine createFormSchemeDefine() {
        return this.designTimeViewController.createFormSchemeDefine();
    }

    public String insertFormSchemeDefine(DesignFormSchemeDefine formSchemeDefine) {
        return this.designTimeViewController.insertFormSchemeDefine(formSchemeDefine);
    }

    public void updateFormSchemeDefine(DesignFormSchemeDefine formSchemeDefine) throws JQException {
        if (this.isCommonNetwork()) {
            if (this.isSameServeCode(formSchemeDefine.getOwnerLevelAndId())) {
                this.designTimeViewController.updateFormSchemeDefine(formSchemeDefine);
            }
        } else {
            this.designTimeViewController.updateFormSchemeDefine(formSchemeDefine);
        }
    }

    public void deleteFormSchemeDefine(String formSchemeKey, boolean delLinkedParam) throws JQException {
        if (this.isCommonNetwork()) {
            DesignFormSchemeDefine designFormSchemeDefine = this.queryFormSchemeDefine(formSchemeKey);
            if (designFormSchemeDefine != null && this.isSameServeCode(designFormSchemeDefine.getOwnerLevelAndId())) {
                this.designTimeViewController.deleteFormSchemeDefine(formSchemeKey, delLinkedParam);
            }
        } else {
            this.designTimeViewController.deleteFormSchemeDefine(formSchemeKey, delLinkedParam);
        }
    }

    public List<DesignFormSchemeDefine> queryFormSchemeByTask(String taskKey) throws JQException {
        return this.designTimeViewController.queryFormSchemeByTask(taskKey);
    }

    public DesignFormSchemeDefine queryFormSchemeDefine(String formSchemeKey) {
        return this.designTimeViewController.queryFormSchemeDefine(formSchemeKey);
    }

    public DesignFormSchemeDefine queryFormSchemeDefineByFilePrefix(String filePrefix) {
        return this.designTimeViewController.queryFormSchemeDefineByFilePrefix(filePrefix);
    }

    public DesignFormDefine createFormDefine() {
        return this.designTimeViewController.createFormDefine();
    }

    public String insertFormDefine(DesignFormDefine formDefine) throws JQException {
        return this.designTimeViewController.insertFormDefine(formDefine);
    }

    public String insertFormDefine(DesignFormDefine formDefine, int type) throws JQException {
        return this.designTimeViewController.insertFormDefine(formDefine, type);
    }

    public int addNewFormDefine(DesignFormDefine formDefine, String ownerFormGroupKey) throws JQException {
        return this.designTimeViewController.addNewFormDefine(formDefine, ownerFormGroupKey);
    }

    public void updateFormDefine(DesignFormDefine formDefine) throws JQException {
        if (this.isCommonNetwork()) {
            if (this.isSameServeCode(formDefine.getOwnerLevelAndId())) {
                this.designTimeViewController.updateFormDefine(formDefine);
            }
        } else {
            this.designTimeViewController.updateFormDefine(formDefine);
        }
    }

    public void updateFormDefine(DesignFormDefine formDefine, int type) throws JQException {
        if (this.isCommonNetwork()) {
            if (this.isSameServeCode(formDefine.getOwnerLevelAndId())) {
                this.designTimeViewController.updateFormDefine(formDefine, type);
            }
        } else {
            this.designTimeViewController.updateFormDefine(formDefine, type);
        }
    }

    public void deleteFormDefine(String formKey, boolean delLinkedParam) throws Exception {
        if (this.isCommonNetwork()) {
            DesignFormDefine designFormDefine = this.queryFormById(formKey);
            if (designFormDefine != null && this.isSameServeCode(designFormDefine.getOwnerLevelAndId())) {
                this.designTimeViewController.deleteFormDefine(formKey, delLinkedParam);
            }
        } else {
            this.designTimeViewController.deleteFormDefine(formKey, delLinkedParam);
        }
    }

    public void deleteFormDefine(DesignFormDefine form, boolean deleteRegion, boolean deleteFormula, boolean deletePrintTemp) throws Exception {
        if (this.isCommonNetwork()) {
            if (this.isSameServeCode(form.getOwnerLevelAndId())) {
                this.designTimeViewController.deleteFormDefine(form.getKey(), deleteRegion, deleteFormula, deletePrintTemp);
            }
        } else {
            this.designTimeViewController.deleteFormDefine(form.getKey(), deleteRegion, deleteFormula, deletePrintTemp);
        }
    }

    public void deleteAllFromDefines() {
        this.designTimeViewController.deleteAllFromDefines();
    }

    public DesignFormDefine queryFormById(String formKey) {
        return this.designTimeViewController.queryFormById(formKey);
    }

    public DesignFormDefine queryFormByCodeInFormScheme(String formSchemeKey, String formDefineCode) {
        return this.designTimeViewController.queryFormByCodeInFormScheme(formSchemeKey, formDefineCode);
    }

    public List<DesignFormDefine> queryAllFormDefinesByTask(String taskKey) {
        return this.designTimeViewController.queryAllFormDefinesByTask(taskKey);
    }

    public List<DesignFormDefine> queryAllFormDefinesByFormScheme(String formSchemeKey) {
        return this.designTimeViewController.queryAllFormDefinesByFormScheme(formSchemeKey);
    }

    public void setReportDataToForm(String formKey, byte[] reportData) {
        this.designTimeViewController.setReportDataToForm(formKey, reportData);
    }

    public void setReportDataToFormByLanguage(String formKey, byte[] reportData, int language) {
        this.designTimeViewController.setReportDataToFormByLanguage(formKey, reportData, language);
    }

    public byte[] getReportDataFromForm(String formKey, int language) {
        return this.designTimeViewController.getReportDataFromForm(formKey, language);
    }

    public Map<Integer, byte[]> getReportDataFromForms(String formKey) {
        return this.designTimeViewController.getReportDataFromForms(formKey);
    }

    public List<DesignFormDefine> getAllFormDefinesWithoutBinaryData() {
        return this.designTimeViewController.getAllFormDefinesWithoutBinaryData();
    }

    public List<DesignFormDefine> getAllFormDefinesInTaskWithoutBinaryData(String taskKey) {
        return this.designTimeViewController.getAllFormDefinesInTaskWithoutBinaryData(taskKey);
    }

    public List<DesignFormDefine> getAllFormDefinesInFormSchemeWithoutBinaryData(String formSchemeKey) {
        return this.designTimeViewController.getAllFormDefinesInFormSchemeWithoutBinaryData(formSchemeKey);
    }

    public List<DesignFormDefine> getAllFormsInGroupWithoutBinaryData(String formGroupKey) {
        return this.designTimeViewController.getAllFormsInGroupWithoutBinaryData(formGroupKey);
    }

    public DesignFormDefine queryFormDefineByCodeInFormSchemeWithoutBinaryData(String formSchemeKey, String formDefineCode) throws JQException {
        return this.designTimeViewController.queryFormDefineByCodeInFormSchemeWithoutBinaryData(formSchemeKey, formDefineCode);
    }

    public List<DesignFormDefine> queryFormDefineByCodeWithoutBinaryData(String formDefineCode) {
        return this.designTimeViewController.queryFormDefineByCodeWithoutBinaryData(formDefineCode);
    }

    public DesignFormDefine queryFormDefineByIdWithoutBinaryData(String formKey) {
        return this.designTimeViewController.queryFormDefineByIdWithoutBinaryData(formKey);
    }

    public DesignFormGroupDefine createFormGroup() {
        return this.designTimeViewController.createFormGroup();
    }

    public String insertFormGroup(DesignFormGroupDefine formGroup) {
        return this.designTimeViewController.insertFormGroup(formGroup);
    }

    public int addNewFormGroupToScheme(DesignFormGroupDefine formGroup, String ownerformSchemeKey) {
        return this.designTimeViewController.addNewFormGroupToScheme(formGroup, ownerformSchemeKey);
    }

    public void updateFormGroup(DesignFormGroupDefine formGroup) throws JQException {
        if (this.isCommonNetwork()) {
            if (this.isSameServeCode(formGroup.getOwnerLevelAndId())) {
                this.designTimeViewController.updateFormGroup(formGroup);
            }
        } else {
            this.designTimeViewController.updateFormGroup(formGroup);
        }
    }

    public void deleteFormGroup(String formGroupKey) throws JQException {
        if (this.isCommonNetwork()) {
            DesignFormGroupDefine designFormGroupDefine = this.queryFormGroup(formGroupKey);
            if (designFormGroupDefine != null && this.isSameServeCode(designFormGroupDefine.getOwnerLevelAndId())) {
                this.designTimeViewController.deleteFormGroup(formGroupKey);
            }
        } else {
            this.designTimeViewController.deleteFormGroup(formGroupKey);
        }
    }

    public DesignFormGroupDefine queryFormGroup(String formGroupKey) {
        return this.designTimeViewController.queryFormGroup(formGroupKey);
    }

    public List<DesignFormGroupDefine> queryFormGroupByTitleInFormScheme(String formSchemeKey, String formGroupTitle) {
        return this.designTimeViewController.queryFormGroupByTitleInFormScheme(formSchemeKey, formGroupTitle);
    }

    public List<DesignFormGroupDefine> queryRootGroupsByFormScheme(String formSchemeKey) {
        return this.designTimeViewController.queryRootGroupsByFormScheme(formSchemeKey);
    }

    public List<DesignFormGroupDefine> getFormGroupsByFormId(String formKey) {
        return this.designTimeViewController.getFormGroupsByFormId(formKey);
    }

    public List<DesignFormGroupLink> getFormGroupLinksByFormId(String formKey) {
        return this.designTimeViewController.getFormGroupLinksByFormId(formKey);
    }

    public List<DesignFormGroupLink> getFormGroupLinksByGroupId(String groupKey) {
        return this.designTimeViewController.getFormGroupLinksByGroupId(groupKey);
    }

    public DesignFormGroupLink getFormGroupLinksByFormIdAndGroupId(String formKey, String groupKey) throws Exception {
        return this.designTimeViewController.getFormGroupLinksByFormIdAndGroupId(formKey, groupKey);
    }

    public void updateDesignFormGroupLink(DesignFormGroupLink designFormGroupLink) throws Exception {
        this.designTimeViewController.updateDesignFormGroupLink(designFormGroupLink);
    }

    public List<DesignFormGroupDefine> getChildFormGroups(String formGroupKey) {
        return this.designTimeViewController.getChildFormGroups(formGroupKey);
    }

    public void addFormToGroup(String formKey, String formGroupKey) {
        this.designTimeViewController.addFormToGroup(formKey, formGroupKey);
    }

    public void removeFormFromGroup(String formKey, String formGroupKey) {
        this.designTimeViewController.removeFormFromGroup(formKey, formGroupKey);
    }

    public List<DesignFormDefine> getAllFormsInGroup(String formGroupKey, boolean isRecursion) {
        return this.designTimeViewController.getAllFormsInGroup(formGroupKey, isRecursion);
    }

    public List<DesignFormDefine> getAllFormsInGroupLazy(String formGroupKey, boolean isRecursion) {
        return this.designTimeViewController.getAllFormsInGroupLazy(formGroupKey, isRecursion);
    }

    public List<DesignFormDefine> getAllFormsInGroupWithoutBinaryData(String formGroupKey, boolean isRecursion) {
        return this.designTimeViewController.getAllFormsInGroupWithoutBinaryData(formGroupKey, isRecursion);
    }

    public void exchangeFormGroup(String groupKey1, String groupKey2) {
        this.designTimeViewController.exchangeFormGroup(groupKey1, groupKey2);
    }

    public List<DesignFormGroupDefine> getAllFormGroups() {
        return this.designTimeViewController.getAllFormGroups();
    }

    public DesignDataRegionDefine createDataRegionDefine() {
        return this.designTimeViewController.createDataRegionDefine();
    }

    public String insertDataRegionDefine(DesignDataRegionDefine dataRegionDefine) {
        return this.designTimeViewController.insertDataRegionDefine(dataRegionDefine);
    }

    public void updateDataRegionDefine(DesignDataRegionDefine dataRegionDefine) throws JQException {
        if (this.isCommonNetwork()) {
            if (this.isSameServeCode(dataRegionDefine.getOwnerLevelAndId())) {
                this.designTimeViewController.updateDataRegionDefine(dataRegionDefine);
            }
        } else {
            this.designTimeViewController.updateDataRegionDefine(dataRegionDefine);
        }
    }

    public void deleteDataRegionDefine(String dataRegionKey, boolean delLinkedParam) throws Exception {
        if (this.isCommonNetwork()) {
            DesignDataRegionDefine designDataRegionDefine = this.queryDataRegionDefine(dataRegionKey);
            if (designDataRegionDefine != null && this.isSameServeCode(designDataRegionDefine.getOwnerLevelAndId())) {
                this.designTimeViewController.deleteDataRegionDefine(dataRegionKey, delLinkedParam);
            }
        } else {
            this.designTimeViewController.deleteDataRegionDefine(dataRegionKey, delLinkedParam);
        }
    }

    public DesignDataRegionDefine queryDataRegionDefine(String dataRegionKey) {
        return this.designTimeViewController.queryDataRegionDefine(dataRegionKey);
    }

    public List<DesignDataRegionDefine> getAllRegionsInForm(String formKey) {
        return this.designTimeViewController.getAllRegionsInForm(formKey);
    }

    public List<String> insertDataRegionDefines(DesignDataRegionDefine[] dataRegionDefines) {
        return this.designTimeViewController.insertDataRegionDefines(dataRegionDefines);
    }

    public void updateDataRegionDefines(DesignDataRegionDefine[] dataRegionDefines) throws JQException {
        if (this.isCommonNetwork()) {
            ArrayList<DesignDataRegionDefine> filterList = new ArrayList<DesignDataRegionDefine>();
            if (dataRegionDefines != null && dataRegionDefines.length > 0) {
                for (DesignDataRegionDefine dataRegionDefine : dataRegionDefines) {
                    if (!this.isSameServeCode(dataRegionDefine.getOwnerLevelAndId())) continue;
                    filterList.add(dataRegionDefine);
                }
            }
            this.designTimeViewController.updateDataRegionDefines(filterList.toArray(new DesignDataRegionDefine[0]));
        } else {
            this.designTimeViewController.updateDataRegionDefines(dataRegionDefines);
        }
    }

    public void deleteDataRegionDefines(String[] dataRegionKeys, boolean delLinkedParam) throws JQException {
        if (this.isCommonNetwork()) {
            ArrayList<String> list = new ArrayList<String>();
            if (dataRegionKeys != null && dataRegionKeys.length > 0) {
                for (String key : dataRegionKeys) {
                    DesignDataRegionDefine designDataRegionDefine = this.designTimeViewController.queryDataRegionDefine(key);
                    if (designDataRegionDefine == null || !this.isSameServeCode(designDataRegionDefine.getOwnerLevelAndId())) continue;
                    list.add(key);
                }
            }
            this.designTimeViewController.deleteDataRegionDefines(list.toArray(new String[0]), delLinkedParam);
        } else {
            this.designTimeViewController.deleteDataRegionDefines(dataRegionKeys, delLinkedParam);
        }
    }

    public List<DesignDataRegionDefine> createDataRegionDefines(int count) {
        return this.designTimeViewController.createDataRegionDefines(count);
    }

    public DesignDataLinkDefine createDataLinkDefine() {
        return this.designTimeViewController.createDataLinkDefine();
    }

    public String insertDataLinkDefine(DesignDataLinkDefine dataLinkDefine) {
        return this.designTimeViewController.insertDataLinkDefine(dataLinkDefine);
    }

    public void updateDataLinkDefine(DesignDataLinkDefine dataLinkDefine) throws JQException {
        if (this.isCommonNetwork()) {
            if (this.isSameServeCode(dataLinkDefine.getOwnerLevelAndId())) {
                this.designTimeViewController.updateDataLinkDefine(dataLinkDefine);
            }
        } else {
            this.designTimeViewController.updateDataLinkDefine(dataLinkDefine);
        }
    }

    public void deleteDataLinkDefine(String dataLinkKey) throws JQException {
        if (this.isCommonNetwork()) {
            DesignDataLinkDefine designDataLinkDefine = this.queryDataLinkDefine(dataLinkKey);
            if (designDataLinkDefine != null && this.isSameServeCode(designDataLinkDefine.getOwnerLevelAndId())) {
                this.designTimeViewController.deleteDataLinkDefine(dataLinkKey);
            }
        } else {
            this.designTimeViewController.deleteDataLinkDefine(dataLinkKey);
        }
    }

    public void deleteDataLinkByRegionId(String dataRegionKey) throws Exception {
        this.designTimeViewController.deleteDataLinkByRegionId(dataRegionKey);
    }

    public DesignDataLinkDefine queryDataLinkDefine(String dataLinkKey) {
        return this.designTimeViewController.queryDataLinkDefine(dataLinkKey);
    }

    public List<DesignDataLinkDefine> getAllLinksInForm(String formKey) {
        return this.designTimeViewController.getAllLinksInForm(formKey);
    }

    public List<DesignDataLinkDefine> getAllLinksInRegion(String dataRegionKey) {
        return this.designTimeViewController.getAllLinksInRegion(dataRegionKey);
    }

    public List<DesignFieldDefine> getAllFieldsByLinksInRegion(String dataRegionKey) throws JQException {
        return this.designTimeViewController.getAllFieldsByLinksInRegion(dataRegionKey);
    }

    public List<DesignFieldDefine> getAllFieldsByLinksInForm(String formKey) throws JQException {
        return this.designTimeViewController.getAllFieldsByLinksInForm(formKey);
    }

    public List<DesignTableDefine> getAllTableDefineInRegion(String dataRegionKey) {
        return this.designTimeViewController.getAllTableDefineInRegion(dataRegionKey);
    }

    public DesignDataLinkDefine queryDataLinkDefine(String formKey, int posX, int posY) {
        return this.designTimeViewController.queryDataLinkDefine(formKey, posX, posY);
    }

    public List<DesignDataLinkDefine> getReferencedDataLinkByField(DesignFieldDefine field) {
        return this.designTimeViewController.getReferencedDataLinkByField(field);
    }

    public List<String> insertDataLinkDefines(DesignDataLinkDefine[] dataLinkDefines) {
        return this.designTimeViewController.insertDataLinkDefines(dataLinkDefines);
    }

    public void updateDataLinkDefines(DesignDataLinkDefine[] dataLinkDefines) throws JQException {
        if (this.isCommonNetwork()) {
            ArrayList<DesignDataLinkDefine> filterList = new ArrayList<DesignDataLinkDefine>();
            if (dataLinkDefines != null && dataLinkDefines.length > 0) {
                for (DesignDataLinkDefine designDataLinkDefine : dataLinkDefines) {
                    if (!this.isSameServeCode(designDataLinkDefine.getOwnerLevelAndId())) continue;
                    filterList.add(designDataLinkDefine);
                }
            }
            this.designTimeViewController.updateDataLinkDefines(filterList.toArray(new DesignDataLinkDefine[0]));
        } else {
            this.designTimeViewController.updateDataLinkDefines(dataLinkDefines);
        }
    }

    public void deleteDataLinkDefines(String[] dataLinkKeys) throws JQException {
        if (this.isCommonNetwork()) {
            ArrayList<String> list = new ArrayList<String>();
            if (dataLinkKeys != null && dataLinkKeys.length > 0) {
                for (String key : dataLinkKeys) {
                    DesignDataLinkDefine designDataLinkDefine = this.designTimeViewController.queryDataLinkDefine(key);
                    if (designDataLinkDefine == null || !this.isSameServeCode(designDataLinkDefine.getOwnerLevelAndId())) continue;
                    list.add(key);
                }
            }
            this.designTimeViewController.deleteDataLinkDefines(list.toArray(new String[0]));
        } else {
            this.designTimeViewController.deleteDataLinkDefines(dataLinkKeys);
        }
    }

    public List<DesignDataLinkDefine> createDataLinkDefines(int count) {
        return this.designTimeViewController.createDataLinkDefines(count);
    }

    public void deleteDataLinkDefinesByFieldKey(String fieldKey) throws Exception {
        if (this.isCommonNetwork()) {
            ArrayList<String> filterList = new ArrayList<String>();
            List<DesignDataLinkDefine> list = this.dataLinkService.getDefinesByFieldKey(fieldKey);
            if (list != null && list.size() > 0) {
                for (DesignDataLinkDefine designDataLinkDefine : list) {
                    if (!this.isSameServeCode(designDataLinkDefine.getOwnerLevelAndId())) continue;
                    filterList.add(designDataLinkDefine.getKey());
                }
            }
            this.designTimeViewController.deleteDataLinkDefines(filterList.toArray(new String[0]));
        } else {
            this.designTimeViewController.deleteDataLinkDefinesByFieldKey(fieldKey);
        }
    }

    public void deleteDataLinkDefinesByFieldKeys(String[] fieldKeys) throws Exception {
        if (this.isCommonNetwork()) {
            ArrayList<String> filterList = new ArrayList<String>();
            if (fieldKeys != null && fieldKeys.length > 0) {
                for (String fieldKey : fieldKeys) {
                    List<DesignDataLinkDefine> list = this.dataLinkService.getDefinesByFieldKey(fieldKey);
                    if (list == null || list.size() <= 0) continue;
                    for (DesignDataLinkDefine designDataLinkDefine : list) {
                        if (!this.isSameServeCode(designDataLinkDefine.getOwnerLevelAndId())) continue;
                        filterList.add(designDataLinkDefine.getKey());
                    }
                }
            }
            this.designTimeViewController.deleteDataLinkDefines(filterList.toArray(new String[0]));
        } else {
            this.designTimeViewController.deleteDataLinkDefinesByFieldKeys(fieldKeys);
        }
    }

    public List<DesignDataLinkDefine> getAllLinks() {
        return this.designTimeViewController.getAllLinks();
    }

    public void removeRegionSetting(String dataRegionKey) {
        this.designTimeViewController.removeRegionSetting(dataRegionKey);
    }

    public void removeEnumLinkage(String dataLinkKey) {
        this.designTimeViewController.removeEnumLinkage(dataLinkKey);
    }

    public void removeEnumLinkage(String[] dataLinkKeys) {
        this.designTimeViewController.removeEnumLinkage(dataLinkKeys);
    }

    public DesignRegionSettingDefine createRegionSetting() {
        return this.designTimeViewController.createRegionSetting();
    }

    public String addRegionSetting(DesignRegionSettingDefine regionSettingDefine) {
        return this.designTimeViewController.addRegionSetting(regionSettingDefine);
    }

    public DesignRegionSettingDefine getRegionSetting(String regionSettingKey) {
        return this.designTimeViewController.getRegionSetting(regionSettingKey);
    }

    public void updateRegionSetting(DesignRegionSettingDefine regionSettingDefine) throws JQException {
        if (this.isCommonNetwork()) {
            if (this.isSameServeCode(regionSettingDefine.getOwnerLevelAndId())) {
                this.designTimeViewController.updateRegionSetting(regionSettingDefine);
            }
        } else {
            this.designTimeViewController.updateRegionSetting(regionSettingDefine);
        }
    }

    public String addEnumLinkage(String dataLinkKey, DesignEnumLinkageSettingDefine linkageDefine) {
        return this.designTimeViewController.addEnumLinkage(dataLinkKey, linkageDefine);
    }

    public DesignEnumLinkageSettingDefine getEnumLinkage(String dataLinkKey) {
        return this.designTimeViewController.getEnumLinkage(dataLinkKey);
    }

    public void updateEnumLinkage(DesignEnumLinkageSettingDefine linkageDefine) throws JQException {
        if (this.isCommonNetwork()) {
            if (this.isSameServeCode(linkageDefine.getOwnerLevelAndId())) {
                this.designTimeViewController.updateEnumLinkage(linkageDefine);
            }
        } else {
            this.designTimeViewController.updateEnumLinkage(linkageDefine);
        }
    }

    public List<DesignEnumLinkageSettingDefine> getEnumLinkages(String[] dataLinkKeys) {
        return this.designTimeViewController.getEnumLinkages(dataLinkKeys);
    }

    public DesignTaskGroupDefine createTaskGroup() {
        return this.designTimeViewController.createTaskGroup();
    }

    public String insertTaskGroupDefine(DesignTaskGroupDefine taskGroup) {
        return this.designTimeViewController.insertTaskGroupDefine(taskGroup);
    }

    public void updateTaskGroupDefine(DesignTaskGroupDefine taskGroup) throws JQException {
        if (this.isCommonNetwork()) {
            if (this.isSameServeCode(taskGroup.getOwnerLevelAndId())) {
                this.designTimeViewController.updateTaskGroupDefine(taskGroup);
            }
        } else {
            this.designTimeViewController.updateTaskGroupDefine(taskGroup);
        }
    }

    public void deleteTaskGroupDefine(String taskGroupKey) throws JQException {
        if (this.isCommonNetwork()) {
            DesignTaskGroupDefine designTaskGroupDefine = this.queryTaskGroupDefine(taskGroupKey);
            if (designTaskGroupDefine != null && this.isSameServeCode(designTaskGroupDefine.getOwnerLevelAndId())) {
                this.designTimeViewController.deleteTaskGroupDefine(taskGroupKey);
            }
        } else {
            this.designTimeViewController.deleteTaskGroupDefine(taskGroupKey);
        }
    }

    public DesignTaskGroupDefine queryTaskGroupDefine(String taskGroupKey) {
        return this.designTimeViewController.queryTaskGroupDefine(taskGroupKey);
    }

    public List<DesignTaskGroupDefine> getChildTaskGroups(String taskGroupKey, boolean isRecursion) {
        return this.designTimeViewController.getChildTaskGroups(taskGroupKey, isRecursion);
    }

    public List<DesignTaskGroupDefine> getAllTaskGroup() {
        return this.designTimeViewController.getAllTaskGroup();
    }

    public List<DesignTaskGroupDefine> getGroupByTask(String taskKey) {
        return this.designTimeViewController.getGroupByTask(taskKey);
    }

    public List<DesignTaskDefine> getAllTasksInGroup(String taskGroupKey, boolean isRecursion) {
        return this.designTimeViewController.getAllTasksInGroup(taskGroupKey, isRecursion);
    }

    public void exchangeTaskGroup(String taskGroupkey1, String taskGroupkey2) {
        this.designTimeViewController.exchangeTaskGroup(taskGroupkey1, taskGroupkey2);
    }

    public void addTaskToGroup(String taskKey, String taskGroupKey) {
        this.designTimeViewController.addTaskToGroup(taskKey, taskGroupKey);
    }

    public void removeTaskFromGroup(String taskKey, String taskGroupKey) {
        this.designTimeViewController.removeTaskFromGroup(taskKey, taskGroupKey);
    }

    public DesignTaskLinkDefine createTaskLinkDefine() {
        return this.designTimeViewController.createTaskLinkDefine();
    }

    public String insertTaskLinkDefine(DesignTaskLinkDefine taskLinkDefine) {
        return this.designTimeViewController.insertTaskLinkDefine(taskLinkDefine);
    }

    public DesignTaskLinkDefine queryDesignByKey(String key) {
        return this.designTimeViewController.queryDesignByKey(key);
    }

    public List<String> insertTaskLinkDefines(List<DesignTaskLinkDefine> taskLinkDefines) {
        return this.designTimeViewController.insertTaskLinkDefines(taskLinkDefines);
    }

    public void deleteTaskLinkDefine(String taskLinkDefineKey) throws JQException {
        if (this.isCommonNetwork()) {
            DesignTaskLinkDefine designTaskLinkDefine = this.queryDesignByKey(taskLinkDefineKey);
            if (designTaskLinkDefine != null && this.isSameServeCode(designTaskLinkDefine.getOwnerLevelAndId())) {
                this.designTimeViewController.deleteTaskLinkDefine(taskLinkDefineKey);
            }
        } else {
            this.designTimeViewController.deleteTaskLinkDefine(taskLinkDefineKey);
        }
    }

    public void deleteTaskLinkDefineByCurrentFormScheme(String currentFormSchemeKey) {
        this.designTimeViewController.deleteTaskLinkDefineByCurrentFormScheme(currentFormSchemeKey);
    }

    public void deleteTaskLinkDefineByCurrentFormSchemeAndNum(String currentFormSchemeKey, String serialNumber) {
        this.designTimeViewController.deleteTaskLinkDefineByCurrentFormSchemeAndNum(currentFormSchemeKey, serialNumber);
    }

    public void updateTaskLinkDefine(DesignTaskLinkDefine taskLinkDefine) throws JQException {
        if (this.isCommonNetwork()) {
            if (this.isSameServeCode(taskLinkDefine.getOwnerLevelAndId())) {
                this.designTimeViewController.updateTaskLinkDefine(taskLinkDefine);
            }
        } else {
            this.designTimeViewController.updateTaskLinkDefine(taskLinkDefine);
        }
    }

    public void updateTaskLinkDefines(List<DesignTaskLinkDefine> taskLinkDefines) {
        this.designTimeViewController.updateTaskLinkDefines(taskLinkDefines);
    }

    public List<DesignTaskLinkDefine> queryLinksByCurrentFormScheme(String currentFormSchemeKey) {
        return this.designTimeViewController.queryLinksByCurrentFormScheme(currentFormSchemeKey);
    }

    public DesignTaskLinkDefine queryLinkByCurrentFormSchemeAndNum(String currentFormSchemeKey, String serialNumber) {
        return this.designTimeViewController.queryLinkByCurrentFormSchemeAndNum(currentFormSchemeKey, serialNumber);
    }

    public List<DesignTaskLinkDefine> queryLinksByRelatedTaskCode(String relatedTaskCode) {
        return this.designTimeViewController.queryLinksByRelatedTaskCode(relatedTaskCode);
    }

    public DesignTaskDefine queryTaskDefineByTaskTitle(String taskTitle) {
        return this.designTimeViewController.queryTaskDefineByTaskTitle(taskTitle);
    }

    public String getFormSchemeEntity(String formSchemeKey) throws JQException {
        return this.designTimeViewController.getFormSchemeEntity(formSchemeKey);
    }

    public List<DesignTableDefine> queryAllTableDefineInRegion(String dataRegionKey, boolean containsEnum) throws JQException {
        return this.designTimeViewController.queryAllTableDefineInRegion(dataRegionKey, containsEnum);
    }

    public DesignEntityLinkageDefine createEntityLinkageDefine() {
        return this.designTimeViewController.createEntityLinkageDefine();
    }

    public String insertDesignerEntityeLinkageDefine(DesignEntityLinkageDefine define) {
        return this.designTimeViewController.insertDesignerEntityeLinkageDefine(define);
    }

    public DesignEntityLinkageDefine queryDesignEntityLinkageDefineByKey(String taskKey) {
        return this.designTimeViewController.queryDesignEntityLinkageDefineByKey(taskKey);
    }

    public void updateEntityLinkageDefine(DesignEntityLinkageDefine define) throws JQException {
        if (this.isCommonNetwork()) {
            if (this.isSameServeCode(define.getOwnerLevelAndId())) {
                this.designTimeViewController.updateEntityLinkageDefine(define);
            }
        } else {
            this.designTimeViewController.updateEntityLinkageDefine(define);
        }
    }

    public List<DesignFormGroupDefine> queryAllGroupsByFormScheme(String formSchemeKey) throws JQException {
        return this.designTimeViewController.queryAllGroupsByFormScheme(formSchemeKey);
    }

    public DesignDataLinkDefine queryDataLinkDefineByColRow(String reportKey, int colIndex, int rowIndex) {
        return this.designTimeViewController.queryDataLinkDefineByColRow(reportKey, colIndex, rowIndex);
    }

    public DesignDataLinkDefine queryDataLinkDefineByUniquecode(String reportKey, String dataLinkCode) {
        return this.designTimeViewController.queryDataLinkDefineByUniquecode(reportKey, dataLinkCode);
    }

    public List<DesignDataLinkDefine> getLinksInFormByField(String formKey, String fieldKey) {
        return this.designTimeViewController.getLinksInFormByField(formKey, fieldKey);
    }

    public DesignFormSchemeDefine getFormschemeByCode(String code) throws Exception {
        return this.designTimeViewController.getFormschemeByCode(code);
    }

    public DesignFormDefine queryFormByLanguageType(String formKey, int type) {
        return this.designTimeViewController.queryFormByLanguageType(formKey, type);
    }

    public DesignFormDefine querySoftFormDefine(String formKey) {
        return this.designTimeViewController.querySoftFormDefine(formKey);
    }

    public DesignFormDefine queryFormByIdWithoutFormData(String formKey) {
        return this.designTimeViewController.queryFormByIdWithoutFormData(formKey);
    }

    public byte[] getFillGuide(String formKey) {
        return this.designTimeViewController.getFillGuide(formKey);
    }

    public void setFillGuide(String formKey, byte[] fillGuide) {
        this.designTimeViewController.setFillGuide(formKey, fillGuide);
    }

    public byte[] getFrontScript(String formKey) {
        return this.designTimeViewController.getFrontScript(formKey);
    }

    public void setFrontScript(String formKey, byte[] frontScript) {
        this.designTimeViewController.setFrontScript(formKey, frontScript);
    }

    public byte[] getSurveyData(String formKey) {
        return this.designTimeViewController.getSurveyData(formKey);
    }

    public void setSurveyData(String formKey, byte[] surveyData) {
        this.designTimeViewController.setSurveyData(formKey, surveyData);
    }

    public List<DesignCaliberGroupLink> getCaliberGroupByCaliberKey(String caliberKey) throws Exception {
        return this.designTimeViewController.getCaliberGroupByCaliberKey(caliberKey);
    }

    public void updateCaliberLink(DesignCaliberGroupLink designCaliberGroupLink) {
        this.designTimeViewController.updateCaliberLink(designCaliberGroupLink);
    }

    public void deleteCaliberLink(DesignCaliberGroupLink caliberGroupLink) {
        this.designTimeViewController.deleteCaliberLink(caliberGroupLink);
    }

    public DesignFormulaSchemeDefine createFormulaSchemeDefine() {
        return this.formulaDesignTimeController.createFormulaSchemeDefine();
    }

    public String insertFormulaSchemeDefine(DesignFormulaSchemeDefine formulaSchemeDefine) {
        return this.formulaDesignTimeController.insertFormulaSchemeDefine(formulaSchemeDefine);
    }

    public void updateFormulaSchemeDefine(DesignFormulaSchemeDefine formulaSchemeDefine) throws JQException {
        if (this.isCommonNetwork()) {
            if (this.isSameServeCode(formulaSchemeDefine.getOwnerLevelAndId())) {
                this.formulaDesignTimeController.updateFormulaSchemeDefine(formulaSchemeDefine);
            }
        } else {
            this.formulaDesignTimeController.updateFormulaSchemeDefine(formulaSchemeDefine);
        }
    }

    public void deleteFormulaSchemeDefine(String formulaSchemeID) throws JQException {
        if (this.isCommonNetwork()) {
            DesignFormulaSchemeDefine designFormulaSchemeDefine = this.queryFormulaSchemeDefine(formulaSchemeID);
            if (designFormulaSchemeDefine != null && this.isSameServeCode(designFormulaSchemeDefine.getOwnerLevelAndId())) {
                this.formulaDesignTimeController.deleteFormulaSchemeDefine(formulaSchemeID);
            }
        } else {
            this.formulaDesignTimeController.deleteFormulaSchemeDefine(formulaSchemeID);
        }
    }

    public void checkFormulaTitle(DesignFormulaSchemeDefine formulaSchemeDefine) throws JQException {
        this.formulaDesignTimeController.checkFormulaTitle(formulaSchemeDefine);
    }

    public void exchangeFormulaSchemeOrder(String orinFormulaSchemeKey, String targetFormulaSchemeKey) {
        this.formulaDesignTimeController.exchangeFormulaSchemeOrder(orinFormulaSchemeKey, targetFormulaSchemeKey);
    }

    public DesignFormulaSchemeDefine queryFormulaSchemeDefine(String formulaSchemeKey) {
        return this.formulaDesignTimeController.queryFormulaSchemeDefine(formulaSchemeKey);
    }

    public List<DesignFormulaSchemeDefine> getAllFormulaSchemeDefines(String taskKey) {
        return this.formulaDesignTimeController.getAllFormulaSchemeDefines(taskKey);
    }

    public List<DesignFormulaSchemeDefine> getAllFormulaSchemeDefinesByFormScheme(String fromSchemeKey) {
        return this.formulaDesignTimeController.getAllFormulaSchemeDefinesByFormScheme(fromSchemeKey);
    }

    public DesignFormulaSchemeDefine getDefaultFormulaSchemeInFormScheme(String fromSchemeKey) {
        return this.formulaDesignTimeController.getDefaultFormulaSchemeInFormScheme(fromSchemeKey);
    }

    public DesignFormulaDefine createFormulaDefine() {
        return this.formulaDesignTimeController.createFormulaDefine();
    }

    public String insertFormulaDefine(DesignFormulaDefine formulaDefine) throws JQException {
        return this.formulaDesignTimeController.insertFormulaDefine(formulaDefine);
    }

    public void updateFormulaDefine(DesignFormulaDefine formulaDefine) throws JQException {
        if (this.isCommonNetwork()) {
            if (this.isSameServeCode(formulaDefine.getOwnerLevelAndId())) {
                this.formulaDesignTimeController.updateFormulaDefine(formulaDefine);
            }
        } else {
            this.formulaDesignTimeController.updateFormulaDefine(formulaDefine);
        }
    }

    public void deleteFormulaDefine(String formulaKey) throws JQException {
        if (this.isCommonNetwork()) {
            DesignFormulaDefine designFormulaDefine = this.queryFormulaDefine(formulaKey);
            if (designFormulaDefine != null && this.isSameServeCode(designFormulaDefine.getOwnerLevelAndId())) {
                this.formulaDesignTimeController.deleteFormulaDefine(formulaKey);
            }
        } else {
            this.formulaDesignTimeController.deleteFormulaDefine(formulaKey);
        }
    }

    public void exchangeFormulaOrder(String orinFormulaKey, String targetFormulaKey) {
        this.formulaDesignTimeController.exchangeFormulaOrder(orinFormulaKey, targetFormulaKey);
    }

    public DesignFormulaDefine queryFormulaDefine(String formulaKey) throws JQException {
        return this.formulaDesignTimeController.queryFormulaDefine(formulaKey);
    }

    public DesignFormulaDefine findFormulaDefineInFormulaScheme(String formulaDefineCode, String formulaSchemeKey) throws JQException {
        return this.formulaDesignTimeController.findFormulaDefineInFormulaScheme(formulaDefineCode, formulaSchemeKey);
    }

    public List<DesignFormulaDefine> findRepeatFormulaDefineFormOutSchemes(String formulaDefineCode, String formKey, String formulaSchemeKey) throws JQException {
        return this.formulaDesignTimeController.findRepeatFormulaDefineFormOutSchemes(formulaDefineCode, formKey, formulaSchemeKey);
    }

    public List<DesignFormulaDefine> getAllFormulasInScheme(String formulaSchemeKey) throws JQException {
        return this.formulaDesignTimeController.getAllFormulasInScheme(formulaSchemeKey);
    }

    public List<DesignFormulaDefine> getAllFormulas() throws JQException {
        return this.formulaDesignTimeController.getAllFormulas();
    }

    public List<DesignFormulaDefine> getCalculateFormulasInScheme(String formulaSchemeKey) throws JQException {
        return this.formulaDesignTimeController.getCalculateFormulasInScheme(formulaSchemeKey);
    }

    public List<DesignFormulaDefine> getCheckFormulasInScheme(String formulaSchemeKey) throws JQException {
        return this.formulaDesignTimeController.getCheckFormulasInScheme(formulaSchemeKey);
    }

    public List<DesignFormulaDefine> getBalanceFormulasInScheme(String formulaSchemeKey) throws JQException {
        return this.formulaDesignTimeController.getBalanceFormulasInScheme(formulaSchemeKey);
    }

    public List<DesignFormulaDefine> getAllFormulasInForm(String formulaSchemeKey, String formKey) throws JQException {
        return this.formulaDesignTimeController.getAllFormulasInForm(formulaSchemeKey, formKey);
    }

    public List<DesignFormulaDefine> getCalculateFormulasInForm(String formulaSchemekey, String formkey) throws JQException {
        return this.formulaDesignTimeController.getCalculateFormulasInForm(formulaSchemekey, formkey);
    }

    public List<DesignFormulaDefine> getCheckFormulasInForm(String formulaSchemekey, String formkey) throws JQException {
        return this.formulaDesignTimeController.getCheckFormulasInForm(formulaSchemekey, formkey);
    }

    public List<DesignFormulaDefine> getBalanceFormulasInForm(String formulaSchemekey, String formkey) throws JQException {
        return this.formulaDesignTimeController.getBalanceFormulasInForm(formulaSchemekey, formkey);
    }

    public List<String> insertFormulaDefines(DesignFormulaDefine[] formulaDefines) throws JQException {
        return this.formulaDesignTimeController.insertFormulaDefines(formulaDefines);
    }

    public List<String> insertFormulasNotAnalysis(DesignFormulaDefine[] formulaDefines) throws JQException {
        return this.formulaDesignTimeController.insertFormulasNotAnalysis(formulaDefines);
    }

    public void updateFormulaDefines(DesignFormulaDefine[] formulaDefines) throws JQException {
        if (this.isCommonNetwork()) {
            ArrayList<DesignFormulaDefine> filterList = new ArrayList<DesignFormulaDefine>();
            if (formulaDefines != null && formulaDefines.length > 0) {
                for (DesignFormulaDefine designFormulaDefine : formulaDefines) {
                    if (!this.isSameServeCode(designFormulaDefine.getOwnerLevelAndId())) continue;
                    filterList.add(designFormulaDefine);
                }
            }
            this.formulaDesignTimeController.updateFormulaDefines(filterList.toArray(new DesignFormulaDefine[0]));
        } else {
            this.formulaDesignTimeController.updateFormulaDefines(formulaDefines);
        }
    }

    public void updateFormulasNotAnalysis(DesignFormulaDefine[] formulaDefines) throws JQException {
        if (this.isCommonNetwork()) {
            ArrayList<DesignFormulaDefine> filterList = new ArrayList<DesignFormulaDefine>();
            if (formulaDefines != null && formulaDefines.length > 0) {
                for (DesignFormulaDefine designFormulaDefine : formulaDefines) {
                    if (!this.isSameServeCode(designFormulaDefine.getOwnerLevelAndId())) continue;
                    filterList.add(designFormulaDefine);
                }
            }
            this.formulaDesignTimeController.updateFormulasNotAnalysis(filterList.toArray(new DesignFormulaDefine[0]));
        } else {
            this.formulaDesignTimeController.updateFormulasNotAnalysis(formulaDefines);
        }
    }

    public void deleteFormulaDefines(String[] formulaKey) throws JQException {
        if (this.isCommonNetwork()) {
            ArrayList<String> list = new ArrayList<String>();
            if (formulaKey != null && formulaKey.length > 0) {
                for (String key : formulaKey) {
                    DesignFormulaDefine designFormulaDefine = this.formulaDesignTimeController.queryFormulaDefine(key);
                    if (designFormulaDefine == null || !this.isSameServeCode(designFormulaDefine.getOwnerLevelAndId())) continue;
                    list.add(designFormulaDefine.getKey());
                }
            }
            this.formulaDesignTimeController.deleteFormulaDefines(list.toArray(new String[0]));
        } else {
            this.formulaDesignTimeController.deleteFormulaDefines(formulaKey);
        }
    }

    public List<DesignFormulaDefine> createFormulaDefines(int count) {
        return this.formulaDesignTimeController.createFormulaDefines(count);
    }

    public List<DesignFormulaDefine> findFormulaDefinesInScheme(String[] formulaDefineCodes, String formulaSchemekey) throws JQException {
        return this.formulaDesignTimeController.findFormulaDefinesInScheme(formulaDefineCodes, formulaSchemekey);
    }

    public List<DesignFormulaDefine> getAllFormulasInForms(String formulaSchemeKey, String[] formKeys) {
        return this.formulaDesignTimeController.getAllFormulasInForms(formulaSchemeKey, formKeys);
    }

    public void deleteFormulaDefinesByForm(String formKey) {
        this.formulaDesignTimeController.deleteFormulaDefinesByForm(formKey);
    }

    public void deleteFormulaDefinesByFormInScheme(String formulaSchemeKey, String formKey) {
        this.formulaDesignTimeController.deleteFormulaDefinesByFormInScheme(formulaSchemeKey, formKey);
    }

    public void deleteFormulaDefinesByScheme(String formulaSchemeKey) {
        this.formulaDesignTimeController.deleteFormulaDefinesByScheme(formulaSchemeKey);
    }

    public void deleteFormulaDefinesByTask(String taskKey) {
        this.formulaDesignTimeController.deleteFormulaDefinesByTask(taskKey);
    }

    public Map<String, Integer> getFormulaCodeCountByScheme(String formulaSchemeKey) throws JQException {
        return this.formulaDesignTimeController.getFormulaCodeCountByScheme(formulaSchemeKey);
    }

    public List<DesignFormulaDefine> getAllSoftFormulasInScheme(String formulaSchemeKey) throws JQException {
        return this.formulaDesignTimeController.getAllSoftFormulasInScheme(formulaSchemeKey);
    }

    public List<DesignFormulaDefine> getAllSoftFormulasInForm(String formulaSchemeKey, String formKey) throws JQException {
        return this.formulaDesignTimeController.getAllSoftFormulasInForm(formulaSchemeKey, formKey);
    }

    public DesignFormulaDefine querySoftFormulaDefine(String formulaKey) throws JQException {
        return this.formulaDesignTimeController.querySoftFormulaDefine(formulaKey);
    }

    public List<DesignFormulaSchemeDefine> getAllFormulaSchemeDefines() {
        return this.formulaDesignTimeController.getAllFormulaSchemeDefines();
    }

    public int getBJFormulaCountByFormulaSchemeKey(String formulaSchemeKey, String formKey) {
        return this.formulaDesignTimeController.getBJFormulaCountByFormulaSchemeKey(formulaSchemeKey, formKey);
    }

    public DesignPrintTemplateSchemeDefine createPrintTemplateSchemeDefine() throws Exception {
        return this.printDesignTimeController.createPrintTemplateSchemeDefine();
    }

    public DesignPrintTemplateSchemeDefine copyPrintTemplateSchemeDefine(DesignPrintTemplateSchemeDefine source, String targetTask, String targetFormScheme) {
        return this.designTimePrintController.copyPrintTemplateScheme(source, targetTask, targetFormScheme);
    }

    public String insertPrintTemplateSchemeDefine(DesignPrintTemplateSchemeDefine printTemplateSchemeDefine) throws Exception {
        return this.printDesignTimeController.insertPrintTemplateSchemeDefine(printTemplateSchemeDefine);
    }

    public void updatePrintTemplateSchemeDefine(DesignPrintTemplateSchemeDefine printTemplateSchemeDefine) throws Exception {
        if (this.isCommonNetwork()) {
            if (this.isSameServeCode(printTemplateSchemeDefine.getOwnerLevelAndId())) {
                this.printDesignTimeController.updatePrintTemplateSchemeDefine(printTemplateSchemeDefine);
            }
        } else {
            this.printDesignTimeController.updatePrintTemplateSchemeDefine(printTemplateSchemeDefine);
        }
    }

    public void deletePrintTemplateSchemeDefine(String printTemplateSchemeKey) throws Exception {
        this.printDesignTimeController.deletePrintTemplateSchemeDefine(printTemplateSchemeKey);
    }

    public void exchangePrintTemplateSchemeOrder(String orinPrintTemplateSchemeKey, String targetPrintTemplateSchemeKey) throws Exception {
        this.printDesignTimeController.exchangePrintTemplateSchemeOrder(orinPrintTemplateSchemeKey, targetPrintTemplateSchemeKey);
    }

    public List<DesignPrintTemplateSchemeDefine> getAllPrintScheme() throws Exception {
        return this.printDesignTimeController.getAllPrintScheme();
    }

    public DesignPrintTemplateSchemeDefine queryPrintTemplateSchemeDefine(String printSchemeKey) throws Exception {
        return this.printDesignTimeController.queryPrintTemplateSchemeDefine(printSchemeKey);
    }

    public List<DesignPrintTemplateSchemeDefine> getAllPrintSchemeByTask(String taskKey) throws Exception {
        return this.printDesignTimeController.getAllPrintSchemeByTask(taskKey);
    }

    public List<DesignPrintTemplateSchemeDefine> getAllPrintSchemeByFormScheme(String formSchemeKey) throws Exception {
        return this.printDesignTimeController.getAllPrintSchemeByFormScheme(formSchemeKey);
    }

    public DesignPrintTemplateDefine createPrintTemplateDefine() {
        return this.printDesignTimeController.createPrintTemplateDefine();
    }

    public DesignPrintTemplateDefine copyPrintTemplateDefine(DesignPrintTemplateDefine source, String targetScheme, String targetForm) {
        return this.designTimePrintController.copyPrintTemplate(source, targetScheme, targetForm);
    }

    public String insertPrintTemplateDefine(DesignPrintTemplateDefine printTemplateDefine) throws Exception {
        return this.printDesignTimeController.insertPrintTemplateDefine(printTemplateDefine);
    }

    public void updatePrintTemplateDefine(DesignPrintTemplateDefine printTemplateDefine) throws Exception {
        if (this.isCommonNetwork()) {
            if (this.isSameServeCode(printTemplateDefine.getOwnerLevelAndId())) {
                this.printDesignTimeController.updatePrintTemplateDefine(printTemplateDefine);
            }
        } else {
            this.printDesignTimeController.updatePrintTemplateDefine(printTemplateDefine);
        }
    }

    public void deletePrintTemplateDefine(String printTemplateKey) throws Exception {
        if (this.isCommonNetwork()) {
            DesignPrintTemplateDefine designPrintTemplateDefine = this.queryPrintTemplateDefine(printTemplateKey);
            if (designPrintTemplateDefine != null && this.isSameServeCode(designPrintTemplateDefine.getOwnerLevelAndId())) {
                this.printDesignTimeController.deletePrintTemplateDefine(printTemplateKey);
            }
        } else {
            this.printDesignTimeController.deletePrintTemplateDefine(printTemplateKey);
        }
    }

    public void deletePrintTemplateDefine(String printTemplateKey, String ... formKeys) throws Exception {
        if (this.isCommonNetwork()) {
            for (String formKey : formKeys) {
                DesignPrintTemplateDefine designPrintTemplateDefine = this.queryPrintTemplateDefineBySchemeAndForm(printTemplateKey, formKey);
                if (designPrintTemplateDefine == null || !this.isSameServeCode(designPrintTemplateDefine.getOwnerLevelAndId())) continue;
                this.printDesignTimeController.deletePrintTemplateDefine(printTemplateKey);
            }
        } else {
            this.printDesignTimeController.deletePrintTemplateDefine(printTemplateKey, formKeys);
        }
    }

    public void deletePrintTemplateDefineByScheme(String printSchemeKey) throws Exception {
        this.printDesignTimeController.deletePrintTemplateDefineByScheme(printSchemeKey);
    }

    public DesignPrintTemplateDefine queryPrintTemplateDefine(String printTemplateKey) throws Exception {
        return this.printDesignTimeController.queryPrintTemplateDefine(printTemplateKey);
    }

    public DesignPrintTemplateDefine queryPrintTemplateDefineBySchemeAndForm(String printSchemeKey, String formKey) throws Exception {
        return this.printDesignTimeController.queryPrintTemplateDefineBySchemeAndForm(printSchemeKey, formKey);
    }

    public List<DesignPrintTemplateDefine> getAllPrintTemplateInScheme(String printSchemeKey) throws Exception {
        return this.printDesignTimeController.getAllPrintTemplateInScheme(printSchemeKey);
    }

    public WordLabelDefine createWordLabelDefine() {
        return this.printDesignTimeController.createWordLabelDefine();
    }

    public PrintSchemeAttributeDefine getPrintSchemeAttribute(DesignPrintTemplateSchemeDefine printScheme) throws Exception {
        return this.printDesignTimeController.getPrintSchemeAttribute(printScheme);
    }

    public void setPrintSchemeAttribute(DesignPrintTemplateSchemeDefine printScheme, PrintSchemeAttributeDefine define) throws Exception {
        this.printDesignTimeController.setPrintSchemeAttribute(printScheme, define);
    }

    public PrintTemplateAttributeDefine getPrintTemplateAttribute(DesignPrintTemplateDefine printTemplate) throws Exception {
        return this.printDesignTimeController.getPrintTemplateAttribute(printTemplate);
    }

    public void setPrintTemplateAttribute(DesignPrintTemplateDefine printTemplate, PrintTemplateAttributeDefine define) throws Exception {
        this.printDesignTimeController.setPrintTemplateAttribute(printTemplate, define);
    }

    public List<DesignPrintTemplateDefine> queryAllPrintTemplate(String printSchemeKey) throws Exception {
        return this.printDesignTimeController.queryAllPrintTemplate(printSchemeKey);
    }

    public int[] insertTemplates(DesignPrintTemplateDefine[] templates) throws Exception {
        return this.printDesignTimeController.insertTemplates(templates);
    }

    public int[] updateTemplates(DesignPrintTemplateDefine[] templates) throws Exception {
        if (this.isCommonNetwork()) {
            ArrayList<DesignPrintTemplateDefine> filterList = new ArrayList<DesignPrintTemplateDefine>();
            if (templates != null && templates.length > 0) {
                for (DesignPrintTemplateDefine designPrintTemplateDefine : templates) {
                    filterList.add(designPrintTemplateDefine);
                }
            }
            return this.printDesignTimeController.updateTemplates(filterList.toArray(new DesignPrintTemplateDefine[0]));
        }
        return this.printDesignTimeController.updateTemplates(templates);
    }

    public DesignTableDefine queryTableDefine(String tableKey) throws JQException {
        return this.dataDefinitionDesignTimeController.queryTableDefine(tableKey);
    }

    public DesignTableDefine queryTableDefinesByCode(String tableCode) throws JQException {
        return this.dataDefinitionDesignTimeController.queryTableDefinesByCode(tableCode);
    }

    public List<DesignTableDefine> queryTableDefines(String[] tableKeys) throws JQException {
        return this.dataDefinitionDesignTimeController.queryTableDefines(tableKeys);
    }

    public DesignFieldDefine queryFieldDefine(String fieldKey) throws JQException {
        return this.dataDefinitionDesignTimeController.queryFieldDefine(fieldKey);
    }

    public DesignFieldDefine queryFieldDefineByCodeInTable(String fieldDefineCode, String tableKey) throws JQException {
        return this.dataDefinitionDesignTimeController.queryFieldDefineByCodeInTable(fieldDefineCode, tableKey);
    }

    public List<DesignFieldDefine> getAllFieldsInTable(String tableKey) throws JQException {
        return this.dataDefinitionDesignTimeController.getAllFieldsInTable(tableKey);
    }

    public List<DesignFieldDefine> queryFieldDefines(String[] fieldKeys) throws JQException {
        return this.dataDefinitionDesignTimeController.queryFieldDefines(fieldKeys);
    }

    private boolean isSameServeCode(String OwnerLevelAndId) throws JQException {
        if (StringUtils.isEmpty(OwnerLevelAndId)) {
            return true;
        }
        String netServerCode = this.iNvwaSystemOptionService.get("other-group", "NET_SERVER_CODE");
        return OwnerLevelAndId.equals(netServerCode);
    }

    private boolean isCommonNetwork() {
        return false;
    }

    public DesignAnalysisFormParamDefine queryAnalysisFormParamDefine(String formKey) throws JQException {
        try {
            return this.designTimeViewController.queryAnalysisFormParamDefine(formKey);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_2001, e.getMessage());
        }
    }

    public void updataAnalysisFormParamDefine(String formKey, DesignAnalysisFormParamDefine anaParam) throws JQException {
        try {
            this.designTimeViewController.updataAnalysisFormParamDefine(formKey, anaParam);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_2002, e.getMessage());
        }
    }

    public void deleteAnalysisFormParamDefine(String formKey) throws JQException {
        try {
            this.designTimeViewController.deleteAnalysisFormParamDefine(formKey);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_2003, e.getMessage());
        }
    }

    public DesignAnalysisSchemeParamDefine queryAnalysisSchemeParamDefine(String formSchemeKey) throws JQException {
        try {
            return this.designTimeViewController.queryAnalysisSchemeParamDefine(formSchemeKey);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_2004, e.getMessage());
        }
    }

    public void updataAnalysisSchemeParamDefine(String formSchemeKey, DesignAnalysisSchemeParamDefine anaParam) throws JQException {
        try {
            this.designTimeViewController.updataAnalysisSchemeParamDefine(formSchemeKey, anaParam);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_2005, e.getMessage());
        }
    }

    public void deleteAnalysisSchemeParamDefine(String formSchemeKey) throws JQException {
        try {
            this.designTimeViewController.deleteAnalysisSchemeParamDefine(formSchemeKey);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_2006, e.getMessage());
        }
    }

    public boolean enableAnalysisScheme(String formSchemeKey) throws JQException {
        try {
            return this.designTimeViewController.enableAnalysisScheme(formSchemeKey);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_2004, e.getMessage());
        }
    }

    public boolean checkTaskNameAvailable(String taskKey, String taskName) throws Exception {
        List<DesignTaskDefine> taskDefines = this.designTimeViewController.checkTaskNameAvailable(taskKey, taskName);
        return taskDefines == null || taskDefines.size() <= 0;
    }

    public List<DesignSchemePeriodLinkDefine> querySchemePeriodLinkByScheme(String scheme) throws Exception {
        return this.designTimeViewController.querySchemePeriodLinkByScheme(scheme);
    }

    public List<DesignSchemePeriodLinkDefine> querySchemePeriodLinkByTask(String task) throws Exception {
        return this.designTimeViewController.querySchemePeriodLinkByTask(task);
    }

    public DesignSchemePeriodLinkDefine querySchemePeriodLinkByPeriodAndScheme(String period, String scheme) throws Exception {
        return this.designTimeViewController.querySchemePeriodLinkByPeriodAndScheme(period, scheme);
    }

    public DesignSchemePeriodLinkDefine querySchemePeriodLinkByPeriodAndTask(String period, String task) throws Exception {
        return this.designTimeViewController.querySchemePeriodLinkByPeriodAndTask(period, task);
    }

    public void deleteSchemePeriodLinkByScheme(String scheme) throws Exception {
        this.designTimeViewController.deleteSchemePeriodLinkByScheme(scheme);
    }

    public void deleteSchemePeriodLinkByTask(String task) throws Exception {
        this.designTimeViewController.deleteSchemePeriodLinkByTask(task);
    }

    public void deleteSchemePeriodLink(List<DesignSchemePeriodLinkDefine> defines) throws Exception {
        this.designTimeViewController.deleteSchemePeriodLink(defines);
    }

    public void inserSchemePeriodLink(List<DesignSchemePeriodLinkDefine> defines) throws Exception {
        this.designTimeViewController.inserSchemePeriodLink(defines);
    }

    public DesignDataLinkMappingDefine createDataLinkMappingDefine() {
        return this.designTimeViewController.createDataLinkMappingDefine();
    }

    public void insertDataLinkMappingDefine(DesignDataLinkMappingDefine designDataLinkMappingDefine) throws JQException {
        this.designTimeViewController.insertDataLinkMappingDefine(designDataLinkMappingDefine);
    }

    public void updateDataLinkMappingDefine(DesignDataLinkMappingDefine designDataLinkMappingDefine) throws JQException {
        this.designTimeViewController.updateDataLinkMappingDefine(designDataLinkMappingDefine);
    }

    public DesignReportTemplateDefine createReportTemplateDefine() {
        return this.designTimeViewController.createReportTemplateDefine();
    }

    public List<DesignReportTemplateDefine> getReportTemplateByTask(String taskKey) {
        return this.designTimeViewController.getReportTemplateByTask(taskKey);
    }

    public List<DesignReportTemplateDefine> getReportTemplateByScheme(String formSchemeKey) {
        return this.designTimeViewController.getReportTemplateByScheme(formSchemeKey);
    }

    public void insertReportTemplate(DesignReportTemplateDefine template, String originalFileName, InputStream inputStream) throws JQException {
        this.designTimeViewController.insertReportTemplate(template, originalFileName, inputStream);
    }

    public void updateReportTemplate(DesignReportTemplateDefine template) throws JQException {
        this.designTimeViewController.updateReportTemplate(template);
    }

    public void updateReportTemplate(String templateKey, String fileName, String originalFileName, InputStream inputStream) throws JQException {
        this.designTimeViewController.updateReportTemplate(templateKey, fileName, originalFileName, inputStream);
    }

    public void deleteReportTemplate(String ... keys) throws JQException {
        this.designTimeViewController.deleteReportTemplate(keys);
    }

    public void deleteReportTemplateByScheme(String formSchemeKey) throws JQException {
        this.designTimeViewController.deleteReportTemplateByScheme(formSchemeKey);
    }

    public void getReportTemplateFile(String fileKey, OutputStream outputStream) {
        this.designTimeViewController.getReportTemplateFile(fileKey, outputStream);
    }

    public List<DesignFormDefine> getSimpleFormDefines(List<String> formKeys) {
        return this.designTimeViewController.getSimpleFormDefines(formKeys);
    }

    public List<DesignFormGroupLink> getFormGroupLinks(List<String> groups) {
        return this.designTimeViewController.getFormGroupLinks(groups);
    }

    public List<DesignReportTagDefine> queryAllTagsByRptKey(String rptKey) {
        return this.designTimeViewController.queryAllTagsByRptKey(rptKey);
    }

    public void deleteTagByKeys(List<String> keys) throws JQException {
        this.designTimeViewController.deleteTagByKeys(keys);
    }

    public void insertTags(List<DesignReportTagDefine> list) throws JQException {
        this.designTimeViewController.insertTags(list);
    }

    public void deleteTagsByRptKey(String rptKey) throws JQException {
        this.designTimeViewController.deleteTagsByRptKey(rptKey);
    }

    public void saveTag(DesignReportTagDefine reportTagDefine) throws JQException {
        this.designTimeViewController.saveTag(reportTagDefine);
    }

    public List<DesignReportTagDefine> filterCustomTagsInRpt(InputStream is, String rptKey) throws JQException {
        return this.designTimeViewController.filterCustomTagsInRpt(is, rptKey);
    }

    public List<DesignDimensionFilter> getDimensionFilters(String taskKey) {
        if (StringUtils.hasLength(taskKey)) {
            return this.designDimensionFilterService.getDimensionFilterByTaskKey(taskKey);
        }
        return Collections.emptyList();
    }

    public void saveDimensionFilter(DesignDimensionFilter dimensionFilter) {
        this.designDimensionFilterService.saveDimensionFilter(dimensionFilter);
    }

    public void updateDimensionFilters(List<DesignDimensionFilter> dimensionFilters) {
        if (dimensionFilters != null) {
            this.designDimensionFilterService.updateDimensionFilters(dimensionFilters);
        }
    }

    public void insertDimensionFilters(List<DesignDimensionFilter> dimensionFilters) throws JQException {
        if (dimensionFilters != null) {
            this.designDimensionFilterService.insertDimensionFilters(dimensionFilters);
        }
    }

    public List<DesignFormulaDefine> querySimpleFormulaDefineByScheme(String formulaSchemeKey) throws JQException {
        return this.formulaDesignTimeController.querySimpleFormulaDefineByScheme(formulaSchemeKey);
    }

    public List<DesignFormulaConditionLink> queryFormulaConditionLinks(String formulaScheme) {
        return this.formulaDesignTimeController.listConditionLinkByScheme(formulaScheme);
    }

    public Map<String, List<DesignFormulaCondition>> queryFormulaConditions(String formulaScheme) {
        List<DesignFormulaConditionLink> formulaConditionLinks = this.queryFormulaConditionLinks(formulaScheme);
        if (CollectionUtils.isEmpty(formulaConditionLinks)) {
            return Collections.emptyMap();
        }
        Map<String, DesignFormulaCondition> conditionMap = this.queryFormulaConditions(formulaConditionLinks.stream().map(FormulaConditionLink::getConditionKey).distinct().collect(Collectors.toList())).stream().collect(Collectors.toMap(IBaseMetaItem::getKey, f -> f));
        HashMap<String, List<DesignFormulaCondition>> map = new HashMap<String, List<DesignFormulaCondition>>();
        for (DesignFormulaConditionLink conditionLink : formulaConditionLinks) {
            if (!map.containsKey(conditionLink.getFormulaKey())) {
                map.put(conditionLink.getFormulaKey(), new ArrayList());
            }
            List conditions = (List)map.get(conditionLink.getFormulaKey());
            if (!conditionMap.containsKey(conditionLink.getConditionKey())) continue;
            conditions.add(conditionMap.get(conditionLink.getConditionKey()));
        }
        return map;
    }

    public List<DesignFormulaCondition> queryFormulaConditions(List<String> keys) {
        return this.formulaDesignTimeController.listFormulaConditionByKey(keys);
    }

    public DesignFormulaConditionLink createDesignFormulaConditionLink() {
        return this.formulaDesignTimeController.initDesignFormulaConditionLink();
    }

    public void insertFormulaConditionLinks(List<DesignFormulaConditionLink> designFormulaConditionLinks) throws Exception {
        this.formulaDesignTimeController.insertFormulaConditionLinks(designFormulaConditionLinks);
    }

    public Map<String, DesignFormulaCondition> queryFormulaConditionBySchemeAndCode(String schemeKey, String ... codes) {
        DesignFormulaSchemeDefine formulaSchemeDefine = this.formulaDesignTimeController.queryFormulaSchemeDefine(schemeKey);
        if (formulaSchemeDefine == null) {
            return Collections.emptyMap();
        }
        DesignFormSchemeDefine formSchemeDefine = this.designTimeViewController.queryFormSchemeDefine(formulaSchemeDefine.getFormSchemeKey());
        if (formSchemeDefine == null) {
            return Collections.emptyMap();
        }
        List<DesignFormulaCondition> conditions = this.formulaDesignTimeController.listFormulaConditionByTaskAndCode(formSchemeDefine.getTaskKey(), codes);
        return conditions.stream().collect(Collectors.toMap(FormulaCondition::getCode, f -> f));
    }

    public void deleteFormulaConditionLinks(List<DesignFormulaConditionLink> delete) {
        this.formulaDesignTimeController.deleteFormulaConditionLinks(delete);
    }

    public void deleteFormulaConditionLinkByFormula(String[] toArray) {
        this.formulaDesignTimeController.deleteFormulaConditionLinkByFormula(toArray);
    }

    public DesignPrintSettingDefine createDesignPrintSettingDefine() {
        return this.designTimePrintController.initPrintSetting();
    }

    public DesignPrintSettingDefine copyDesignPrintSettingDefine(DesignPrintSettingDefine source, String targetScheme, String targetForm) {
        return this.designTimePrintController.copyPrintSetting(source, targetScheme, targetForm);
    }

    public DesignPrintSettingDefine getPrintSettingDefine(String printSchemeKey, String formKey) {
        return this.designTimePrintController.getPrintSettingDefine(printSchemeKey, formKey);
    }

    public List<DesignPrintSettingDefine> listPrintSettingDefine(String printSchemeKey) {
        return this.designTimePrintController.listPrintSettingDefine(printSchemeKey);
    }

    public void deletePrintSettingDefine(String printSchemeKey) throws JQException {
        if (this.isCommonNetwork()) {
            List<DesignPrintSettingDefine> defines = this.listPrintSettingDefine(printSchemeKey);
            for (DesignPrintSettingDefine define : defines) {
                this.deletePrintSettingDefine(define);
            }
        } else {
            this.designTimePrintController.deletePrintSettingDefine(printSchemeKey);
        }
    }

    public void deletePrintSettingDefine(String printSchemeKey, String formKey) throws JQException {
        if (this.isCommonNetwork()) {
            DesignPrintSettingDefine define = this.getPrintSettingDefine(printSchemeKey, formKey);
            this.deletePrintSettingDefine(define);
        } else {
            this.designTimePrintController.deletePrintSettingDefine(printSchemeKey, formKey);
        }
    }

    public void deletePrintSettingDefine(DesignPrintSettingDefine define) throws JQException {
        if (null == define) {
            return;
        }
        if (this.isCommonNetwork()) {
            if (this.isSameServeCode(define.getOwnerLevelAndId())) {
                this.designTimePrintController.deletePrintSettingDefine(define.getPrintSchemeKey(), define.getFormKey());
            }
        } else {
            this.designTimePrintController.deletePrintSettingDefine(define.getPrintSchemeKey(), define.getFormKey());
        }
    }

    public void insertPrintSettingDefine(DesignPrintSettingDefine designPrintSettingDefine) {
        this.designTimePrintController.insertPrintSettingDefine(designPrintSettingDefine);
    }

    public void insertPrintSettingDefine(List<DesignPrintSettingDefine> designPrintSettingDefines) {
        this.designTimePrintController.insertPrintSettingDefine(designPrintSettingDefines);
    }

    public void updatePrintSettingDefine(DesignPrintSettingDefine designPrintSettingDefine) throws JQException {
        if (null == designPrintSettingDefine) {
            return;
        }
        if (this.isCommonNetwork()) {
            if (this.isSameServeCode(designPrintSettingDefine.getOwnerLevelAndId())) {
                this.designTimePrintController.updatePrintSettingDefine(designPrintSettingDefine);
            }
        } else {
            this.designTimePrintController.updatePrintSettingDefine(designPrintSettingDefine);
        }
    }

    public void updatePrintSettingDefine(List<DesignPrintSettingDefine> designPrintSettingDefines) throws JQException {
        if (null == designPrintSettingDefines) {
            return;
        }
        for (DesignPrintSettingDefine define : designPrintSettingDefines) {
            this.updatePrintSettingDefine(define);
        }
    }

    public DesignPrintComTemDefine copyPrintComTemDefine(DesignPrintComTemDefine copy, String targetPrintScheme) {
        return this.designTimePrintController.copyPrintComTem(copy, targetPrintScheme);
    }

    public List<DesignPrintComTemDefine> listPrintComTemDefine(String printSchemeKey) {
        return this.designTimePrintController.listPrintComTemByScheme(printSchemeKey);
    }

    public void updatePrintComTemDefine(List<DesignPrintComTemDefine> defines) {
        this.designTimePrintController.updatePrintComTem(defines);
    }

    public void insertPrintComTemDefine(List<DesignPrintComTemDefine> defines) {
        this.designTimePrintController.insertPrintComTem(defines);
    }

    public void syncFormStyleUpdateTime(String srcKey, String desKey) {
        this.formDefineService.syncFormStyleUpdateTime(srcKey, desKey);
    }
}

