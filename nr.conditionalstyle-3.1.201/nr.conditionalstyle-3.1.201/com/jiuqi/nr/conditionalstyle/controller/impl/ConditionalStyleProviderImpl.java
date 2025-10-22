/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.internal.provider.ConditionalStyleProvider
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.nr.conditionalstyle.controller.impl;

import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.nr.conditionalstyle.controller.IDesignConditionalStyleController;
import com.jiuqi.nr.conditionalstyle.controller.impl.ConditionalStyleController;
import com.jiuqi.nr.conditionalstyle.dao.ConditionalStyleDao;
import com.jiuqi.nr.conditionalstyle.facade.ConditionalStyle;
import com.jiuqi.nr.conditionalstyle.service.ConditionStyleService;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.internal.provider.ConditionalStyleProvider;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

@Component
public class ConditionalStyleProviderImpl
implements ConditionalStyleProvider {
    private static final Logger logger = LoggerFactory.getLogger(ConditionalStyleProviderImpl.class);
    @Autowired
    IDesignConditionalStyleController designcontroller;
    @Autowired
    ConditionalStyleController runTimeController;
    @Autowired
    IRunTimeViewController runTimeViewController;
    @Autowired
    ConditionalStyleDao conditionalStyleDao;
    @Autowired
    ConditionStyleService service;

    public void deleteCSInForm(String fromKey) {
        try {
            this.designcontroller.deleteCSInForm(fromKey);
        }
        catch (JQException e) {
            logger.error(e.getErrorMessage());
        }
    }

    @Transactional(rollbackFor={Exception.class})
    public void deployCS(String taskKey) {
        Set<String> designKeys;
        Set<String> runTimeKeys = this.getRunTimeConditionalStyle(taskKey).stream().map(ConditionalStyle::getKey).collect(Collectors.toSet());
        if (!CollectionUtils.isEmpty(runTimeKeys)) {
            this.conditionalStyleDao.deleteConditionalStyles(runTimeKeys, true);
        }
        if (!CollectionUtils.isEmpty(designKeys = this.designcontroller.getCSByTask(taskKey).stream().map(ConditionalStyle::getKey).collect(Collectors.toSet()))) {
            this.conditionalStyleDao.insertConditionalStyles(designKeys, true);
        }
        this.service.onClearCache();
    }

    private List<ConditionalStyle> getRunTimeConditionalStyle(String taskKey) {
        ArrayList<ConditionalStyle> result = new ArrayList<ConditionalStyle>();
        Set<String> collect = this.runTimeViewController.queryAllFormDefinesByTask(taskKey).stream().map(IBaseMetaItem::getKey).collect(Collectors.toSet());
        collect.forEach(formKey -> {
            List<ConditionalStyle> allCSInForm = this.conditionalStyleDao.getCSByForm((String)formKey);
            if (!CollectionUtils.isEmpty(allCSInForm)) {
                result.addAll(allCSInForm);
            }
        });
        return result;
    }
}

