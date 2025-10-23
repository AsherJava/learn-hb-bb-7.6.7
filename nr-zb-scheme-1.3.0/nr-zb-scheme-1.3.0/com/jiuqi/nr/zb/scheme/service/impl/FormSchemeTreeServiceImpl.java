/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.api.IDesignTimeViewController
 */
package com.jiuqi.nr.zb.scheme.service.impl;

import com.jiuqi.nr.definition.api.IDesignTimeViewController;
import com.jiuqi.nr.zb.scheme.internal.tree.FormGroupNode;
import com.jiuqi.nr.zb.scheme.internal.tree.FormNode;
import com.jiuqi.nr.zb.scheme.internal.tree.FormSchemeTreeNode;
import com.jiuqi.nr.zb.scheme.service.IFormSchemeTreeService;
import com.jiuqi.nr.zb.scheme.web.vo.FormSchemeTreeSearchItem;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

@Service
public class FormSchemeTreeServiceImpl
implements IFormSchemeTreeService {
    @Autowired
    private IDesignTimeViewController designTimeViewController;

    @Override
    public List<FormSchemeTreeNode> getRoot(String formSchemeKey) {
        Assert.hasLength(formSchemeKey, "formSchemeKey length is 0");
        List designFormGroupDefines = this.designTimeViewController.listFormGroupByFormScheme(formSchemeKey);
        if (CollectionUtils.isEmpty(designFormGroupDefines)) {
            return Collections.emptyList();
        }
        return designFormGroupDefines.stream().map(FormGroupNode::valueOf).collect(Collectors.toList());
    }

    @Override
    public List<FormSchemeTreeNode> getChildren(String formGroupKey) {
        Assert.hasLength(formGroupKey, "taskKey length is 0");
        List designFormDefines = this.designTimeViewController.listFormByGroup(formGroupKey);
        if (!CollectionUtils.isEmpty(designFormDefines)) {
            return designFormDefines.stream().map(FormNode::valueOf).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    @Override
    public List<FormSchemeTreeSearchItem> search(String formSchemeKey, String keyword) {
        Assert.hasLength(formSchemeKey, "formSchemeKey length is 0");
        Assert.hasLength(keyword, "keyword length is 0");
        ArrayList<FormSchemeTreeSearchItem> result = new ArrayList<FormSchemeTreeSearchItem>();
        List designFormGroupDefines = this.designTimeViewController.listFormGroupByFormScheme(formSchemeKey);
        List designFormDefines = this.designTimeViewController.listFormByFormScheme(formSchemeKey);
        if (designFormGroupDefines != null) {
            designFormGroupDefines.stream().filter(formGroupDefine -> formGroupDefine.getTitle().contains(keyword)).map(FormGroupNode::valueOf).map(FormSchemeTreeSearchItem::buildFromFormGroup).forEach(result::add);
        }
        if (designFormDefines != null) {
            designFormDefines.stream().filter(formDefine -> formDefine.getFormCode().contains(keyword) || formDefine.getTitle().contains(keyword)).map(FormNode::valueOf).forEach(formNode -> this.designTimeViewController.listFormGroupLinkByForm(formNode.getKey()).forEach(formGroupLink -> {
                FormSchemeTreeSearchItem item = FormSchemeTreeSearchItem.buildFromForm(formNode);
                item.addPath(formGroupLink.getGroupKey());
                item.addPath(formNode.getKey());
                result.add(item);
            }));
        }
        return result;
    }
}

