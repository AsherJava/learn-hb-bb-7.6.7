/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.api.IDesignTimeViewController
 *  com.jiuqi.nr.definition.auth.DefinitionAuthorityProvider
 *  com.jiuqi.nr.definition.facade.DesignFormGroupDefine
 *  com.jiuqi.nr.definition.internal.impl.DesignFormGroupLink
 */
package com.jiuqi.nr.task.form.form.service.impl;

import com.jiuqi.nr.definition.api.IDesignTimeViewController;
import com.jiuqi.nr.definition.auth.DefinitionAuthorityProvider;
import com.jiuqi.nr.definition.facade.DesignFormGroupDefine;
import com.jiuqi.nr.definition.internal.impl.DesignFormGroupLink;
import com.jiuqi.nr.task.form.dto.ItemOrderMoveDTO;
import com.jiuqi.nr.task.form.form.dto.FormDTO;
import com.jiuqi.nr.task.form.form.dto.FormGroupDTO;
import com.jiuqi.nr.task.form.form.exception.FormGroupRunTimeException;
import com.jiuqi.nr.task.form.form.service.IFormGroupService;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
public class FormGroupServiceImpl
implements IFormGroupService {
    @Autowired
    private IDesignTimeViewController designTimeViewController;
    @Autowired
    private DefinitionAuthorityProvider definitionAuthority;

    @Override
    public String insertGroup(FormGroupDTO formGroup) {
        Assert.notNull((Object)formGroup.getTitle(), "\u62a5\u8868\u5206\u7ec4\u7684\u540d\u79f0\u4e0d\u80fd\u4e3a\u7a7a");
        Assert.notNull((Object)formGroup.getFormSchemeKey(), "\u62a5\u8868\u5206\u7ec4\u7684\u6240\u5c5e\u62a5\u8868\u65b9\u6848\u4e0d\u80fd\u4e3a\u7a7a");
        DesignFormGroupDefine groupDefine = this.designTimeViewController.initFormGroup();
        groupDefine.setTitle(formGroup.getTitle());
        groupDefine.setFormSchemeKey(formGroup.getFormSchemeKey());
        groupDefine.setCondition(formGroup.getCondition());
        this.designTimeViewController.insertFormGroup(groupDefine);
        return groupDefine.getKey();
    }

    @Override
    public String insertDefaultGroup(String formSchemeKey) {
        Assert.notNull((Object)formSchemeKey, "formSchemeKey must not be null");
        DesignFormGroupDefine newFormGroup = this.designTimeViewController.initFormGroup();
        newFormGroup.setTitle("\u8868\u5355\u5206\u7ec41");
        newFormGroup.setFormSchemeKey(formSchemeKey);
        this.designTimeViewController.insertFormGroup(newFormGroup);
        return newFormGroup.getKey();
    }

    @Override
    public boolean checkHasGroup(FormGroupDTO formGroup) {
        String formSchemeKey = formGroup.getFormSchemeKey();
        String key = formGroup.getKey();
        String title = formGroup.getTitle();
        List designFormGroupDefines = this.designTimeViewController.listFormGroupByFormSchemeAndTitle(formSchemeKey, title);
        if (StringUtils.hasText(key)) {
            if (!CollectionUtils.isEmpty(designFormGroupDefines)) {
                List sameTitleDifKey = designFormGroupDefines.stream().filter(a -> !a.getKey().equals(key)).collect(Collectors.toList());
                return sameTitleDifKey.size() > 0;
            }
            return false;
        }
        return !CollectionUtils.isEmpty(designFormGroupDefines);
    }

    @Override
    public void insertGroupLink(FormDTO formDTO) {
        DesignFormGroupLink designFormGroupLink = this.designTimeViewController.initFormGroupLink();
        designFormGroupLink.setFormKey(formDTO.getKey());
        designFormGroupLink.setGroupKey(formDTO.getFormGroupKey());
        designFormGroupLink.setFormOrder(formDTO.getOrder());
        DesignFormGroupLink[] designFormGroupLinks = new DesignFormGroupLink[]{designFormGroupLink};
        this.designTimeViewController.insertFormGroupLink(designFormGroupLinks);
    }

    @Override
    public void updateGroup(FormGroupDTO formGroup) {
        DesignFormGroupDefine groupDefine = this.designTimeViewController.getFormGroup(formGroup.getKey());
        groupDefine.setTitle(formGroup.getTitle());
        groupDefine.setCondition(formGroup.getCondition());
        this.designTimeViewController.updateFormGroup(groupDefine);
    }

    @Override
    public void deleteGroup(String groupKey) {
        List designFormDefines = this.designTimeViewController.listFormByGroup(groupKey);
        if (designFormDefines.size() > 0) {
            throw new FormGroupRunTimeException("\u62a5\u8868\u5206\u7ec4\u4e0b\u6709\u62a5\u8868\uff0c\u65e0\u6cd5\u5220\u9664");
        }
        String[] del = new String[]{groupKey};
        this.designTimeViewController.deleteFormGroup(del);
    }

    @Override
    public void moveGroup(ItemOrderMoveDTO itemMove) {
        Integer way = itemMove.getWay();
        List designFormGroupDefineForScheme = this.designTimeViewController.listFormGroupByFormScheme(itemMove.getFormSchemeKey());
        DesignFormGroupDefine targetFormGroup = null;
        DesignFormGroupDefine sourceFormGroup = null;
        if (CollectionUtils.isEmpty(designFormGroupDefineForScheme)) {
            throw new FormGroupRunTimeException("\u62a5\u8868\u65b9\u6848\u4e0b\u7684\u62a5\u8868\u5206\u7ec4\u4e3a\u7a7a\uff0c\u8bf7\u68c0\u67e5\u62a5\u8868\u65b9\u6848");
        }
        try {
            for (int i = 0; i < designFormGroupDefineForScheme.size(); ++i) {
                if (!itemMove.getSourceKey().equals(((DesignFormGroupDefine)designFormGroupDefineForScheme.get(i)).getKey())) continue;
                sourceFormGroup = (DesignFormGroupDefine)designFormGroupDefineForScheme.get(i);
                targetFormGroup = way == 1 ? (DesignFormGroupDefine)designFormGroupDefineForScheme.get(i + 1) : (DesignFormGroupDefine)designFormGroupDefineForScheme.get(i - 1);
                break;
            }
        }
        catch (Exception e) {
            throw new FormGroupRunTimeException("\u62a5\u8868\u5206\u7ec4\u67e5\u8be2\u5f02\u5e38\uff0c\u8bf7\u68c0\u67e5\u62a5\u8868\u5206\u7ec4");
        }
        if (targetFormGroup == null || sourceFormGroup == null) {
            throw new FormGroupRunTimeException("\u62a5\u8868\u5206\u7ec4\u4e0d\u5b58\u5728\uff0c\u8bf7\u68c0\u67e5\u62a5\u8868\u5206\u7ec4");
        }
        String sourceOrder = sourceFormGroup.getOrder();
        sourceFormGroup.setOrder(targetFormGroup.getOrder());
        targetFormGroup.setOrder(sourceOrder);
        this.designTimeViewController.updateFormGroup(sourceFormGroup);
        this.designTimeViewController.updateFormGroup(targetFormGroup);
    }

    @Override
    public List<FormGroupDTO> initGroupTree(String formSchemeKey) {
        Assert.notNull((Object)formSchemeKey, "\u62a5\u8868\u65b9\u6848Key\u4e0d\u80fd\u4e3a\u7a7a");
        ArrayList<FormGroupDTO> formGroups = new ArrayList<FormGroupDTO>();
        List formGroupDefines = this.designTimeViewController.listFormGroupByFormScheme(formSchemeKey);
        for (DesignFormGroupDefine groupDefine : formGroupDefines) {
            formGroups.add(FormGroupDTO.getInstance(groupDefine));
        }
        return formGroups;
    }

    @Override
    public FormGroupDTO query(String formGroupKey) {
        Assert.notNull((Object)formGroupKey, "\u62a5\u8868\u5206\u7ec4Key\u4e0d\u80fd\u4e3a\u7a7a");
        DesignFormGroupDefine formGroup = this.designTimeViewController.getFormGroup(formGroupKey);
        return FormGroupDTO.getInstance(formGroup);
    }

    @Override
    public List<FormGroupDTO> loadGroupChildren(String groupKey, String formSchemeKey) {
        return null;
    }

    @Override
    public List<FormGroupDTO> listFormGroupByScheme(String formSchemeKey) {
        List designFormGroupDefines = this.designTimeViewController.listFormGroupByFormScheme(formSchemeKey);
        ArrayList<FormGroupDTO> list = new ArrayList<FormGroupDTO>();
        for (DesignFormGroupDefine groupDefine : designFormGroupDefines) {
            list.add(FormGroupDTO.getInstance(groupDefine));
        }
        return list;
    }

    @Override
    public FormGroupDTO getFormGroupByFrom(String formKey) {
        List groups = this.designTimeViewController.listFormGroupByForm(formKey);
        if (groups.size() > 0) {
            DesignFormGroupDefine groupDefine = (DesignFormGroupDefine)groups.get(0);
            return FormGroupDTO.getInstance(groupDefine);
        }
        return null;
    }

    @Override
    public boolean existFormInGroup(String groupKey) {
        return !CollectionUtils.isEmpty(this.designTimeViewController.listFormByGroup(groupKey));
    }

    @Override
    public List<FormGroupDTO> queryRoot(String formSchemeKey) {
        return this.initGroupTree(formSchemeKey);
    }
}

