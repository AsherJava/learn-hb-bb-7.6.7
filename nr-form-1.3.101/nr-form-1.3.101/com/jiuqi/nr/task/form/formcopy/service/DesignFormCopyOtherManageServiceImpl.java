/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.nr.definition.api.IDesignTimeViewController
 *  com.jiuqi.nr.definition.facade.DesignFormDefine
 */
package com.jiuqi.nr.task.form.formcopy.service;

import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.nr.definition.api.IDesignTimeViewController;
import com.jiuqi.nr.definition.facade.DesignFormDefine;
import com.jiuqi.nr.task.form.formcopy.IDesignFormCopyOtherManageService;
import com.jiuqi.nr.task.form.formcopy.IDesignFormCopyService;
import com.jiuqi.nr.task.form.formcopy.bean.IFormCopyInfo;
import com.jiuqi.nr.task.form.formcopy.common.FormCopyRecordType;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DesignFormCopyOtherManageServiceImpl
implements IDesignFormCopyOtherManageService {
    @Autowired
    private IDesignFormCopyService iDesignFormCopyService;
    @Autowired
    private IDesignTimeViewController designTimeViewController;

    @Override
    public FormCopyRecordType getFormCopyType(String formSchemeKey) {
        int value = 0;
        List<IFormCopyInfo> formCopyPullInfoList = this.iDesignFormCopyService.getFormCopyInfoBySchemeKey(formSchemeKey);
        List desFormKeys = formCopyPullInfoList.stream().map(IFormCopyInfo::getFormKey).collect(Collectors.toList());
        List srcFormKeys = formCopyPullInfoList.stream().map(IFormCopyInfo::getSrcFormKey).collect(Collectors.toList());
        Map<String, DesignFormDefine> keyToDesFormDefineMaps = this.designTimeViewController.listForm(desFormKeys).stream().collect(Collectors.toMap(IBaseMetaItem::getKey, a -> a));
        Map<String, DesignFormDefine> keyToSrcFormDefineMaps = this.designTimeViewController.listForm(srcFormKeys).stream().collect(Collectors.toMap(IBaseMetaItem::getKey, a -> a));
        for (IFormCopyInfo iFormCopyInfo : formCopyPullInfoList) {
            String formKey = iFormCopyInfo.getFormKey();
            String srcFormKey = iFormCopyInfo.getSrcFormKey();
            DesignFormDefine formDefineDes = keyToDesFormDefineMaps.get(formKey);
            DesignFormDefine formDefineSrc = keyToSrcFormDefineMaps.get(srcFormKey);
            if (null == formDefineDes || null == formDefineSrc) continue;
            ++value;
            break;
        }
        List<IFormCopyInfo> formCopyPushInfoList = this.iDesignFormCopyService.getFormCopyInfoBySrcSchemeKey(formSchemeKey);
        List allSrcFormKeys = formCopyPushInfoList.stream().map(IFormCopyInfo::getSrcFormKey).collect(Collectors.toList());
        Map<String, DesignFormDefine> keyToSrcPullFormDefineMaps = this.designTimeViewController.listForm(allSrcFormKeys).stream().collect(Collectors.toMap(IBaseMetaItem::getKey, a -> a));
        List allDesFormKeys = formCopyPushInfoList.stream().map(IFormCopyInfo::getFormKey).collect(Collectors.toList());
        Map<String, DesignFormDefine> keyToDesPushFormDefineMaps = this.designTimeViewController.listForm(allDesFormKeys).stream().collect(Collectors.toMap(IBaseMetaItem::getKey, a -> a));
        for (IFormCopyInfo iFormCopyInfo : formCopyPushInfoList) {
            String formKey = iFormCopyInfo.getFormKey();
            String srcFormKey = iFormCopyInfo.getSrcFormKey();
            DesignFormDefine formDefineDes = keyToDesPushFormDefineMaps.get(formKey);
            DesignFormDefine formDefineSrc = keyToSrcPullFormDefineMaps.get(srcFormKey);
            if (null == formDefineDes || null == formDefineSrc) continue;
            value += 2;
            break;
        }
        return FormCopyRecordType.valueOf(value);
    }
}

