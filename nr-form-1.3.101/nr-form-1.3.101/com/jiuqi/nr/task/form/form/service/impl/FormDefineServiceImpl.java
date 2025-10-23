/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.nr.definition.api.IDesignTimeViewController
 *  com.jiuqi.nr.definition.common.FormType
 *  com.jiuqi.nr.definition.facade.DesignFormDefine
 *  com.jiuqi.nr.definition.facade.DesignFormGroupDefine
 *  com.jiuqi.nr.definition.internal.impl.DesignFormGroupLink
 *  com.jiuqi.util.OrderGenerator
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.nr.task.form.form.service.impl;

import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.nr.definition.api.IDesignTimeViewController;
import com.jiuqi.nr.definition.common.FormType;
import com.jiuqi.nr.definition.facade.DesignFormDefine;
import com.jiuqi.nr.definition.facade.DesignFormGroupDefine;
import com.jiuqi.nr.definition.internal.impl.DesignFormGroupLink;
import com.jiuqi.nr.task.form.dto.ItemOrderMoveDTO;
import com.jiuqi.nr.task.form.form.dto.FormDTO;
import com.jiuqi.nr.task.form.form.dto.FormItemDTO;
import com.jiuqi.nr.task.form.form.exception.FormGroupRunTimeException;
import com.jiuqi.nr.task.form.form.exception.FormRunTimeException;
import com.jiuqi.nr.task.form.form.service.IFormDefineService;
import com.jiuqi.util.OrderGenerator;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
public class FormDefineServiceImpl
implements IFormDefineService {
    @Autowired
    private IDesignTimeViewController designTimeViewController;

    @Override
    public List<FormItemDTO> queryFormByGroup(String group) {
        ArrayList<FormItemDTO> items = new ArrayList<FormItemDTO>();
        Assert.notNull((Object)group, "\u5206\u7ec4Key\u4e0d\u80fd\u4e3a\u7a7a");
        List defines = this.designTimeViewController.listFormByGroup(group);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (DesignFormDefine define : defines) {
            FormItemDTO instance = FormItemDTO.getInstance(define, sdf);
            items.add(instance);
        }
        return items;
    }

    @Override
    public FormDTO getForm(String formKey) {
        DesignFormDefine form = this.designTimeViewController.getForm(formKey);
        FormDTO formDTO = new FormDTO(form);
        List groups = this.designTimeViewController.listFormGroupByForm(formKey);
        if (!CollectionUtils.isEmpty(groups)) {
            formDTO.setFormGroupKey(((DesignFormGroupDefine)groups.get(0)).getKey());
        }
        return formDTO;
    }

    @Override
    public List<DesignFormDefine> getFormFuzzy(String formSchemeKey, String title) {
        String keyWords = StringUtils.hasText(title) ? title.toUpperCase(Locale.ROOT) : title;
        return this.designTimeViewController.listFormByFormScheme(formSchemeKey).stream().filter(a -> a.getTitle().toLowerCase(Locale.ROOT).contains(keyWords) || a.getFormCode().toLowerCase(Locale.ROOT).contains(keyWords)).collect(Collectors.toList());
    }

    @Override
    public boolean updateForm(FormDTO formDTO) {
        if (!StringUtils.hasText(formDTO.getKey())) {
            throw new FormRunTimeException("\u62a5\u8868\u6ca1\u6709\u4e3b\u952e\uff0c\u4fee\u6539\u5f02\u5e38\uff01");
        }
        if (!formDTO.checkProperty()) {
            throw new FormRunTimeException("\u62a5\u8868\u5b58\u5728\u5c5e\u6027\u4e3a\u7a7a\uff0c\u8bf7\u68c0\u67e5\u53c2\u6570\uff01");
        }
        DesignFormDefine formDefine = this.designTimeViewController.getForm(formDTO.getKey());
        formDTO.toDesignFormDefine(formDefine);
        formDefine.setUpdateUser(NpContextHolder.getContext().getUserName());
        this.designTimeViewController.updateForm(formDefine);
        return true;
    }

    @Override
    public void insertForm(FormDTO formDTO) {
        DesignFormDefine designFormDefine = this.designTimeViewController.initForm();
        formDTO.toDesignFormDefine(designFormDefine);
        formDTO.setKey(designFormDefine.getKey());
        String formOrder = this.getFormOrder(formDTO);
        designFormDefine.setOrder(formOrder);
        formDTO.setOrder(formOrder);
        this.designTimeViewController.insertForm(designFormDefine);
    }

    private String getFormOrder(FormDTO formDTO) {
        Optional<DesignFormDefine> first;
        List designFormDefines;
        if (Objects.equals(formDTO.getFormType(), FormType.FORM_TYPE_NEWFMDM) && (designFormDefines = this.designTimeViewController.listFormByGroup(formDTO.getFormGroupKey())) != null && (first = designFormDefines.stream().min(Comparator.comparing(IBaseMetaItem::getOrder))).isPresent()) {
            return first.get().getOrder().substring(0, 6).concat("00");
        }
        return OrderGenerator.newOrder();
    }

    @Override
    @Transactional
    public boolean deleteForm(String formKey) {
        String[] delKey = new String[]{formKey};
        try {
            this.designTimeViewController.deleteForm(delKey);
        }
        catch (Exception e) {
            throw new FormGroupRunTimeException(e.getMessage());
        }
        return true;
    }

    @Override
    public boolean moveForm(ItemOrderMoveDTO itemMove) {
        DesignFormDefine designFormDefine;
        int i;
        Integer way = itemMove.getWay();
        List designFormDefinesForGroup = this.designTimeViewController.listFormByGroup(itemMove.getFormGroupKey());
        designFormDefinesForGroup.sort(Comparator.comparing(IBaseMetaItem::getOrder));
        HashMap<String, DesignFormDefine> needMoveForm = new HashMap<String, DesignFormDefine>();
        List<String> sourceKeyLists = itemMove.getSourceKeyLists();
        if (CollectionUtils.isEmpty(sourceKeyLists)) {
            throw new FormGroupRunTimeException("\u6240\u9009\u62a5\u8868\u4e3a\u7a7a\uff0c\u8bf7\u91cd\u65b0\u9009\u62e9\uff01");
        }
        HashSet<String> sourceKeySets = new HashSet<String>(sourceKeyLists);
        if (way == 0) {
            for (i = 0; i < designFormDefinesForGroup.size(); ++i) {
                designFormDefine = (DesignFormDefine)designFormDefinesForGroup.get(i);
                if (sourceKeySets.contains(designFormDefine.getKey())) {
                    if (i == 0) {
                        throw new FormGroupRunTimeException("\u5206\u7ec4\u4e0b\u7b2c\u4e00\u4e2a\u62a5\u8868\u5728\u6240\u9009\u62a5\u8868\u4e2d\uff0c\u4e0a\u79fb\u5931\u8d25\uff01");
                    }
                    DesignFormDefine lastFormDefine = (DesignFormDefine)designFormDefinesForGroup.get(i - 1);
                    String lastOrder = lastFormDefine.getOrder();
                    lastFormDefine.setOrder(designFormDefine.getOrder());
                    designFormDefine.setOrder(lastOrder);
                    designFormDefinesForGroup.set(i, lastFormDefine);
                    designFormDefinesForGroup.set(i - 1, designFormDefine);
                    needMoveForm.put(lastFormDefine.getKey(), lastFormDefine);
                    needMoveForm.put(designFormDefine.getKey(), designFormDefine);
                    sourceKeySets.remove(designFormDefine.getKey());
                }
                if (sourceKeySets.size() != 0) {
                    continue;
                }
                break;
            }
        } else if (way == 1) {
            for (i = designFormDefinesForGroup.size() - 1; i >= 0; --i) {
                designFormDefine = (DesignFormDefine)designFormDefinesForGroup.get(i);
                if (sourceKeySets.contains(designFormDefine.getKey())) {
                    if (i == designFormDefinesForGroup.size() - 1) {
                        throw new FormGroupRunTimeException("\u5206\u7ec4\u4e0b\u6700\u540e\u4e00\u4e2a\u62a5\u8868\u5728\u6240\u9009\u62a5\u8868\u4e2d\uff0c\u4e0b\u79fb\u5931\u8d25\uff01");
                    }
                    DesignFormDefine nextFormDefine = (DesignFormDefine)designFormDefinesForGroup.get(i + 1);
                    String nextOrder = nextFormDefine.getOrder();
                    nextFormDefine.setOrder(designFormDefine.getOrder());
                    designFormDefine.setOrder(nextOrder);
                    designFormDefinesForGroup.set(i, nextFormDefine);
                    designFormDefinesForGroup.set(i + 1, designFormDefine);
                    needMoveForm.put(nextFormDefine.getKey(), nextFormDefine);
                    needMoveForm.put(designFormDefine.getKey(), designFormDefine);
                    sourceKeySets.remove(designFormDefine.getKey());
                }
                if (sourceKeySets.size() != 0) {
                    continue;
                }
                break;
            }
        }
        ArrayList<DesignFormGroupLink> formGroupLinks = new ArrayList<DesignFormGroupLink>();
        for (Map.Entry entry : needMoveForm.entrySet()) {
            DesignFormDefine value = (DesignFormDefine)entry.getValue();
            DesignFormGroupLink formGroupLink = this.designTimeViewController.initFormGroupLink();
            formGroupLink.setFormKey(value.getKey());
            formGroupLink.setGroupKey(itemMove.getFormGroupKey());
            formGroupLink.setFormOrder(value.getOrder());
            formGroupLinks.add(formGroupLink);
        }
        this.designTimeViewController.updateFormGroupLink(formGroupLinks.toArray(new DesignFormGroupLink[formGroupLinks.size()]));
        return true;
    }

    @Override
    @Transactional
    public boolean changeFormGroup(ItemOrderMoveDTO formGroupDTO) {
        String groupKey = formGroupDTO.getFormGroupKey();
        List<String> formKeys = formGroupDTO.getSourceKeyLists();
        if (!CollectionUtils.isEmpty(formKeys) && StringUtils.hasLength(groupKey)) {
            List formGroupDefinesForDel = this.designTimeViewController.listFormGroupByForm(formKeys.get(0));
            DesignFormGroupLink[] insertDesignFormGroupLinks = new DesignFormGroupLink[formKeys.size()];
            DesignFormGroupLink[] delDesignFormGroupLinks = new DesignFormGroupLink[formKeys.size()];
            for (int i = 0; i < formKeys.size(); ++i) {
                String formkey = formKeys.get(i);
                DesignFormGroupLink insertDesignFormGroupLink = this.designTimeViewController.initFormGroupLink();
                insertDesignFormGroupLink.setFormKey(formkey);
                insertDesignFormGroupLink.setGroupKey(groupKey);
                insertDesignFormGroupLinks[i] = insertDesignFormGroupLink;
                DesignFormGroupLink delDesignFormGroupLink = this.designTimeViewController.initFormGroupLink();
                delDesignFormGroupLink.setFormKey(formkey);
                delDesignFormGroupLink.setGroupKey(((DesignFormGroupDefine)formGroupDefinesForDel.get(0)).getKey());
                delDesignFormGroupLinks[i] = delDesignFormGroupLink;
            }
            this.designTimeViewController.deleteFormGroupLink(delDesignFormGroupLinks);
            this.designTimeViewController.insertFormGroupLink(insertDesignFormGroupLinks);
        }
        return true;
    }

    @Override
    public List<FormItemDTO> queryFormByScheme(String formSchemeKey) {
        ArrayList<FormItemDTO> items = new ArrayList<FormItemDTO>();
        List defines = this.designTimeViewController.listFormByFormScheme(formSchemeKey);
        defines.sort(Comparator.comparing(IBaseMetaItem::getOrder));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (DesignFormDefine define : defines) {
            FormItemDTO instance = FormItemDTO.getInstance(define, sdf);
            items.add(instance);
        }
        return items;
    }

    @Override
    public boolean existFMDM(String formScheme) {
        Assert.notNull((Object)formScheme, "formScheme must not be null");
        List designFormDefines = this.designTimeViewController.listFormByFormScheme(formScheme);
        if (designFormDefines != null) {
            return designFormDefines.stream().anyMatch(define -> define.getFormType() == FormType.FORM_TYPE_NEWFMDM);
        }
        return false;
    }
}

