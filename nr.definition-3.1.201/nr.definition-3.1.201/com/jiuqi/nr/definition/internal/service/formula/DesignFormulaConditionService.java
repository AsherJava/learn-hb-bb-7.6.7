/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.exception.DBParaException
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.util.OrderGenerator
 */
package com.jiuqi.nr.definition.internal.service.formula;

import com.jiuqi.np.definition.exception.DBParaException;
import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.nr.definition.exception.DefinitonException;
import com.jiuqi.nr.definition.facade.formula.DesignFormulaCondition;
import com.jiuqi.nr.definition.facade.formula.FormulaCondition;
import com.jiuqi.nr.definition.internal.dao.formula.DesignFormulaConditionDao;
import com.jiuqi.util.OrderGenerator;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

@Service
public class DesignFormulaConditionService {
    @Autowired
    private DesignFormulaConditionDao formulaConditionDao;
    private static final String CODE_ERR = "\u9002\u5e94\u6761\u4ef6\u6807\u8bc6\u91cd\u590d";

    public List<DesignFormulaCondition> listFormulaConditionByKey(List<String> keys) {
        if (CollectionUtils.isEmpty(keys)) {
            return Collections.emptyList();
        }
        return this.sortList(this.formulaConditionDao.listFormulaConditions(keys));
    }

    public List<DesignFormulaCondition> listFormulaConditionByTask(String task) {
        if (task == null) {
            return Collections.emptyList();
        }
        return this.sortList(this.formulaConditionDao.listFormulaConditions(task));
    }

    private List<DesignFormulaCondition> sortList(List<DesignFormulaCondition> listFormulaConditions) {
        return listFormulaConditions.stream().sorted(Comparator.comparing(FormulaCondition::getCode)).collect(Collectors.toList());
    }

    public void updateFormulaCondition(DesignFormulaCondition condition) {
        this.checkData(condition);
        DesignFormulaCondition byTaskAndCode = this.formulaConditionDao.getByTaskAndCode(condition.getTaskKey(), condition.getCode());
        if (byTaskAndCode != null && !byTaskAndCode.getKey().equals(condition.getKey())) {
            throw new DefinitonException(CODE_ERR);
        }
        condition.setUpdateTime(new Date());
        try {
            this.formulaConditionDao.update(condition);
        }
        catch (DBParaException e) {
            throw new DefinitonException(e);
        }
    }

    public void deleteFormulaCondition(String key) {
        if (key == null) {
            return;
        }
        try {
            this.formulaConditionDao.delete(key);
        }
        catch (DBParaException e) {
            throw new DefinitonException(e);
        }
    }

    public void insertFormulaCondition(DesignFormulaCondition condition) {
        if (condition == null) {
            return;
        }
        this.checkAndFillData(condition);
        this.checkCode(condition);
        try {
            this.formulaConditionDao.insert(condition);
        }
        catch (DBParaException e) {
            throw new DefinitonException(e);
        }
    }

    public void insertFormulaConditions(List<DesignFormulaCondition> conditions) {
        if (CollectionUtils.isEmpty(conditions)) {
            return;
        }
        DesignFormulaCondition condition = conditions.get(0);
        if (condition == null) {
            throw new DefinitonException("condition must not be null");
        }
        String taskKey = condition.getTaskKey();
        List<DesignFormulaCondition> allConditions = this.listFormulaConditionByTask(taskKey);
        Set codeSet = allConditions.stream().map(FormulaCondition::getCode).collect(Collectors.toSet());
        for (DesignFormulaCondition formulaCondition : conditions) {
            if (codeSet.contains(formulaCondition.getCode())) {
                throw new DefinitonException(CODE_ERR);
            }
            codeSet.add(formulaCondition.getCode());
        }
        conditions.forEach(this::checkAndFillData);
        try {
            this.formulaConditionDao.insert(conditions.toArray());
        }
        catch (DBParaException e) {
            throw new DefinitonException(e);
        }
    }

    private void checkAndFillData(DesignFormulaCondition condition) {
        this.checkData(condition);
        if (condition.getKey() == null) {
            condition.setKey(UUID.randomUUID().toString());
        }
        if (condition.getOrder() == null) {
            condition.setOrder(OrderGenerator.newOrder());
        }
        if (condition.getUpdateTime() == null) {
            condition.setUpdateTime(new Date());
        }
    }

    private void checkCode(DesignFormulaCondition condition) {
        DesignFormulaCondition oldValue = this.formulaConditionDao.getByTaskAndCode(condition.getTaskKey(), condition.getCode());
        if (oldValue != null) {
            throw new DefinitonException(CODE_ERR);
        }
    }

    public void updateFormulaConditions(List<DesignFormulaCondition> collect) {
        Assert.notEmpty(collect, "collect must not be null");
        HashSet<String> codes = new HashSet<String>();
        String taskKey = null;
        for (DesignFormulaCondition condition : collect) {
            this.checkData(condition);
            if (taskKey == null) {
                taskKey = condition.getTaskKey();
            }
            if (codes.contains(condition.getCode())) {
                throw new DefinitonException(CODE_ERR);
            }
            codes.add(condition.getCode());
        }
        List<DesignFormulaCondition> designFormulaConditions = this.formulaConditionDao.listFormulaConditions(taskKey);
        Set keySet = collect.stream().map(IBaseMetaItem::getKey).collect(Collectors.toSet());
        Set oldCodes = designFormulaConditions.stream().filter(f -> !keySet.contains(f.getKey())).map(FormulaCondition::getCode).collect(Collectors.toSet());
        if (oldCodes.stream().anyMatch(codes::contains)) {
            throw new DefinitonException(CODE_ERR);
        }
        try {
            this.formulaConditionDao.update(collect.toArray(new DesignFormulaCondition[0]));
        }
        catch (DBParaException e) {
            throw new DefinitonException("\u4fee\u6539\u9002\u7528\u6761\u4ef6\u5931\u8d25", e);
        }
    }

    private void checkData(DesignFormulaCondition condition) {
        Assert.notNull((Object)condition.getCode(), "condition code must not be null");
        Assert.notNull((Object)condition.getTitle(), "condition title must not be null");
        Assert.notNull((Object)condition.getTaskKey(), "condition taskKey must not be null");
        Assert.notNull((Object)condition.getFormulaCondition(), "formula condition must not be null");
    }

    public void deleteFormulaConditionByTask(String task) {
        if (task == null) {
            return;
        }
        try {
            this.formulaConditionDao.deleteFormulaConditionByTask(task);
        }
        catch (DBParaException e) {
            throw new DefinitonException("\u5220\u9664\u9002\u7528\u6761\u4ef6\u5931\u8d25");
        }
    }

    public void deleteFormulaConditions(List<String> keys) {
        if (CollectionUtils.isEmpty(keys)) {
            return;
        }
        try {
            this.formulaConditionDao.deleteFormulaConditions(keys);
        }
        catch (DBParaException e) {
            throw new DefinitonException("\u5220\u9664\u9002\u7528\u6761\u4ef6\u5931\u8d25");
        }
    }
}

