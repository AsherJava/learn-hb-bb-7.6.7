/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.progress.ProgressItem
 *  com.jiuqi.nr.task.api.save.FormSaveContext
 *  com.jiuqi.nr.task.api.tree.UITreeNode
 */
package com.jiuqi.nr.task.form.service;

import com.jiuqi.np.definition.progress.ProgressItem;
import com.jiuqi.nr.task.api.save.FormSaveContext;
import com.jiuqi.nr.task.api.tree.UITreeNode;
import com.jiuqi.nr.task.form.controller.dto.CheckFieldCodeBatchDTO;
import com.jiuqi.nr.task.form.controller.dto.DeleteFormParam;
import com.jiuqi.nr.task.form.controller.dto.FormTreeNode;
import com.jiuqi.nr.task.form.controller.vo.CheckFieldCodeBatchVO;
import com.jiuqi.nr.task.form.controller.vo.CopyFormVO;
import com.jiuqi.nr.task.form.controller.vo.EnumFieldVO;
import com.jiuqi.nr.task.form.controller.vo.FindFieldVO;
import com.jiuqi.nr.task.form.controller.vo.FormTreeParam;
import com.jiuqi.nr.task.form.controller.vo.FormUITreeNode;
import com.jiuqi.nr.task.form.dto.CheckResult;
import com.jiuqi.nr.task.form.dto.DeleteFormDTO;
import com.jiuqi.nr.task.form.dto.FormDesignerDTO;
import com.jiuqi.nr.task.form.dto.FormExtDTO;
import com.jiuqi.nr.task.form.dto.SaveResult;
import com.jiuqi.nr.task.form.dto.SimpleFormDesignerDTO;
import com.jiuqi.nr.task.form.ext.dto.ConfigDTO;
import com.jiuqi.nr.task.form.field.dto.DataFieldSettingDTO;
import com.jiuqi.nr.task.form.form.dto.FormDTO;
import com.jiuqi.nr.task.form.form.dto.FormItemDTO;
import com.jiuqi.nr.task.form.link.dto.DataLinkSettingDTO;
import com.jiuqi.nr.task.form.link.dto.RefEntityLinkConfigDTO;
import com.jiuqi.nr.task.form.region.dto.DataRegionSettingDTO;
import java.util.List;
import java.util.Map;

public interface IFormService {
    public List<FormExtDTO> queryFormExt(String var1);

    public Map<String, String> queryTaskInfo(String var1);

    public String insertDefaultGroup(String var1);

    public void deleteGroup(String var1);

    public List<FormItemDTO> queryFormByGroup(String var1);

    public String insertForm(FormDTO var1);

    public void insertDefaultForm(String var1, String var2);

    public DeleteFormDTO deleteForm(DeleteFormParam var1);

    public void deleteFormAndField(DeleteFormParam var1);

    public void deleteFormBaseData(DeleteFormParam var1);

    public SaveResult saveFormData(FormDesignerDTO var1);

    public List<FormItemDTO> queryFormByScheme(String var1);

    public SimpleFormDesignerDTO querySimpleFormData(String var1, int var2);

    public List<DataRegionSettingDTO> listRegionSettingByForm(String var1);

    public List<DataLinkSettingDTO> listLinkSettingByForm(String var1);

    public List<DataFieldSettingDTO> listFieldSettingByForm(String var1);

    public List<ConfigDTO> listComponentSetting(String var1);

    public DataRegionSettingDTO getRegionSetting(String var1);

    public DataLinkSettingDTO getLinkSetting(String var1);

    public DataFieldSettingDTO getFieldSetting(String var1);

    public CheckResult checkFormData(FormDesignerDTO var1);

    public List<EnumFieldVO> queryShowFields(String var1);

    public List<DataFieldSettingDTO> listFieldSetting(List<String> var1);

    public List<DataLinkSettingDTO> listLinkSetting(List<String> var1);

    public FormDTO getFormDefine(String var1);

    public List<FormTreeNode> formTreeByScheme(String var1);

    public List<UITreeNode<FormUITreeNode>> getFormTreeRoot(FormTreeParam var1);

    public List<UITreeNode<FormUITreeNode>> getFormChildTree(FormTreeParam var1);

    public List<UITreeNode<FormUITreeNode>> locationFormTree(FormTreeParam var1);

    public List<FormItemDTO> fuzzySearch(FormTreeParam var1);

    public boolean codeCheck(String var1, String var2, String var3);

    public boolean checkFormTitle(FormDTO var1);

    public boolean copyForm(CopyFormVO var1);

    public List<DataFieldSettingDTO> listFieldSettingByTable(String var1);

    public ProgressItem getProgress(String var1);

    public Map<String, Boolean> checkTableData(List<String> var1);

    public void getValidationResult(String var1, String var2, Map<String, Object> var3);

    public FindFieldVO findFieldByTableCodeAndFieldCodes(List<String> var1);

    public List<CheckFieldCodeBatchVO> checkFieldCodes(List<CheckFieldCodeBatchDTO> var1);

    public List<UITreeNode<FormUITreeNode>> locateSelectFormTree(String var1, String var2);

    public RefEntityLinkConfigDTO getLinkRefEntityConfig(String var1);

    public Boolean isPrintTemplateNeedChange(FormSaveContext var1);
}

