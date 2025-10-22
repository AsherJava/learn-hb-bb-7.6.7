/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.np.definition.exception.DBParaException
 *  com.jiuqi.nvwa.systemoption.extend.ISystemOptionItemValue
 *  com.jiuqi.nvwa.systemoption.extend.impl.SystemOptionOperator
 *  com.jiuqi.nvwa.systemoption.vo.ResultObject
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.nr.definition.option.systemoperator;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.np.definition.exception.DBParaException;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.dao.RunTimeTaskDefineDao;
import com.jiuqi.nr.definition.option.common.ReportCacheOptionType;
import com.jiuqi.nr.definition.option.common.ReportCacheUtil;
import com.jiuqi.nr.definition.option.dao.ReportCacheOptionDao;
import com.jiuqi.nr.definition.option.dto.ReportCacheConfig;
import com.jiuqi.nr.definition.option.dto.ReportCacheOption;
import com.jiuqi.nr.definition.service.IParamCacheManagerService;
import com.jiuqi.nvwa.systemoption.extend.ISystemOptionItemValue;
import com.jiuqi.nvwa.systemoption.extend.impl.SystemOptionOperator;
import com.jiuqi.nvwa.systemoption.vo.ResultObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

@Component
public class ReportCacheOptionOperator
extends SystemOptionOperator {
    @Autowired
    private ReportCacheOptionDao cacheOptionDao;
    @Autowired
    private RunTimeTaskDefineDao taskDefineDao;
    @Autowired
    private IParamCacheManagerService paramCacheManagerService;

    public ResultObject save(List<ISystemOptionItemValue> itemValues) {
        if (CollectionUtils.isEmpty(itemValues)) {
            return new ResultObject();
        }
        ResultObject res = this.doSave(itemValues);
        this.paramCacheManagerService.reloadSetting();
        return res;
    }

    @Transactional(rollbackFor={Exception.class})
    public ResultObject doSave(List<ISystemOptionItemValue> itemValues) {
        ISystemOptionItemValue permanentDetailValue = null;
        ISystemOptionItemValue preloadValue = null;
        for (ISystemOptionItemValue itemValue : itemValues) {
            if ("report_cache_permanent_detail".equals(itemValue.getKey())) {
                permanentDetailValue = itemValue;
            }
            if (!"report_cache_preload_detail".equals(itemValue.getKey())) continue;
            preloadValue = itemValue;
        }
        List<ReportCacheOption> permanentOptions = null;
        if (null != permanentDetailValue) {
            itemValues.remove(permanentDetailValue);
            permanentOptions = this.getOptions(permanentDetailValue.getValue());
        }
        List<ReportCacheOption> preloadOptions = null;
        if (null != preloadValue) {
            itemValues.remove(preloadValue);
            preloadOptions = this.getOptions(preloadValue.getValue());
        }
        try {
            this.cacheOptionDao.deleteByOptionType(ReportCacheOptionType.CACHE_EXPIRATION.getValue());
            this.cacheOptionDao.deleteByOptionType(ReportCacheOptionType.CACHE_PRELOAD.getValue());
            if (!CollectionUtils.isEmpty(permanentOptions)) {
                this.cacheOptionDao.insert(permanentOptions.toArray(new ReportCacheOption[0]));
            }
            if (!CollectionUtils.isEmpty(preloadOptions)) {
                this.cacheOptionDao.insert(preloadOptions.toArray(new ReportCacheOption[0]));
            }
        }
        catch (DBParaException e) {
            throw new RuntimeException("\u66f4\u65b0\u7f13\u5b58\u5e38\u9a7b\u89c4\u5219\u5931\u8d25", e);
        }
        return super.save(itemValues);
    }

    public List<String> query(List<String> optionItemKeys) {
        ArrayList<String> res = new ArrayList<String>();
        for (String optionItemKey : optionItemKeys) {
            res.add(this.query(optionItemKey));
        }
        return res;
    }

    public String query(String optionItemKey) {
        switch (optionItemKey) {
            case "report_cache_permanent_detail": {
                return this.getRules(this.cacheOptionDao, ReportCacheOptionType.CACHE_EXPIRATION);
            }
            case "report_cache_preload_detail": {
                return this.getRules(this.cacheOptionDao, ReportCacheOptionType.CACHE_PRELOAD);
            }
        }
        return super.query(optionItemKey);
    }

    private List<ReportCacheOption> getOptions(String value) {
        ArrayList<ReportCacheOption> options = new ArrayList<ReportCacheOption>();
        List<ReportCacheConfig> optionConfigs = ReportCacheUtil.configRevert(value);
        for (ReportCacheConfig optionConfig : optionConfigs) {
            ReportCacheOption cacheOption = new ReportCacheOption();
            optionConfig.toOptions(cacheOption);
            options.add(cacheOption);
        }
        return options;
    }

    public String getRules(ReportCacheOptionDao cacheOptionDao, ReportCacheOptionType type) {
        List<TaskDefine> taskDefines = this.taskDefineDao.list();
        List<ReportCacheOption> optionDetail = cacheOptionDao.listByOptionType(type.getValue());
        Map<String, ReportCacheOption> detailMap = optionDetail.stream().collect(Collectors.toMap(ReportCacheOption::getTask, v -> v));
        ArrayList<ReportCacheConfig> rightOptionDetail = new ArrayList<ReportCacheConfig>();
        for (TaskDefine taskDefine : taskDefines) {
            if (!detailMap.containsKey(taskDefine.getKey())) continue;
            rightOptionDetail.add(ReportCacheConfig.valueOf(detailMap.get(taskDefine.getKey()), taskDefine));
        }
        ObjectMapper objectMapper = new ObjectMapper();
        String optionStr = null;
        try {
            optionStr = objectMapper.writeValueAsString(rightOptionDetail);
        }
        catch (JsonProcessingException e) {
            throw new RuntimeException("\u7f13\u5b58\u914d\u7f6e\u7ec6\u8282\u5e8f\u5217\u5316\u51fa\u9519", e);
        }
        return optionStr;
    }
}

