/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.internal.BeanUtil
 */
package com.jiuqi.nr.data.excel.service.impl;

import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.nr.data.excel.service.internal.IFormFinder;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.internal.BeanUtil;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class SchemeFormFinder
implements IFormFinder {
    private final List<FormDefine> allForms;
    private final Map<String, FormDefine> keyCache;
    private final Map<String, FormDefine> codeCache;
    private final Map<String, List<FormDefine>> titleCache;
    private final Map<String, List<FormDefine>> serialNumCache;

    public SchemeFormFinder(String formSchemeKey) {
        IRunTimeViewController runTimeViewController = (IRunTimeViewController)BeanUtil.getBean(IRunTimeViewController.class);
        this.allForms = runTimeViewController.queryAllFormDefinesByFormScheme(formSchemeKey);
        this.keyCache = this.allForms.stream().collect(Collectors.toMap(IBaseMetaItem::getKey, Function.identity()));
        this.codeCache = this.allForms.stream().collect(Collectors.toMap(FormDefine::getFormCode, Function.identity()));
        this.titleCache = this.allForms.stream().collect(Collectors.groupingBy(IBaseMetaItem::getTitle));
        this.serialNumCache = this.allForms.stream().collect(Collectors.groupingBy(FormDefine::getSerialNumber));
    }

    @Override
    public FormDefine findByKey(String formKey) {
        return this.keyCache.get(formKey);
    }

    @Override
    public FormDefine findByCode(String formCode) {
        return this.codeCache.get(formCode);
    }

    @Override
    public List<FormDefine> findByTitle(String formTitle) {
        return this.titleCache.get(formTitle);
    }

    @Override
    public List<FormDefine> findBySerialNum(String formSerialNum) {
        return this.serialNumCache.get(formSerialNum);
    }
}

