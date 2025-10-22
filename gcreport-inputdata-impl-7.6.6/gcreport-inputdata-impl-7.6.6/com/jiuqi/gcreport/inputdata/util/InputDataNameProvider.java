/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.consolidatedsystem.service.InputDataSchemeCacheService
 *  com.jiuqi.gcreport.consolidatedsystem.vo.InputDataSchemeVO
 *  com.jiuqi.gcreport.i18n.util.GcI18nUtil
 *  com.jiuqi.np.cache.NedisCache
 *  com.jiuqi.np.cache.NedisCacheManager
 *  com.jiuqi.nr.datascheme.api.DataScheme
 *  com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 */
package com.jiuqi.gcreport.inputdata.util;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.consolidatedsystem.service.InputDataSchemeCacheService;
import com.jiuqi.gcreport.consolidatedsystem.vo.InputDataSchemeVO;
import com.jiuqi.gcreport.i18n.util.GcI18nUtil;
import com.jiuqi.np.cache.NedisCache;
import com.jiuqi.np.cache.NedisCacheManager;
import com.jiuqi.nr.datascheme.api.DataScheme;
import com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.util.Collection;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.stereotype.Component;

@Component
public class InputDataNameProvider {
    @Autowired
    private IRunTimeViewController iRunTimeViewController;
    @Autowired
    private DataModelService modelService;
    @Autowired
    private IRuntimeDataSchemeService iRuntimeDataSchemeService;
    @Autowired
    private InputDataSchemeCacheService inputDataSchemeCacheService;
    @Autowired
    private NedisCacheManager cacheManger;
    private static Logger logger = LoggerFactory.getLogger(InputDataNameProvider.class);
    private static final String INPUT_DATA_TABLE_NAME_CACHE_KEY = "inputDataTableNameCache";

    public String getTableNameByTaskId(String taskId) {
        InputDataSchemeVO inputDataScheme = this.getInputDataSchemeByTaskKey(taskId);
        if (null == inputDataScheme || StringUtils.isEmpty((String)inputDataScheme.getTableKey())) {
            TaskDefine taskDefine = this.iRunTimeViewController.queryTaskDefine(taskId);
            logger.error("\u4efb\u52a1\u3010" + taskDefine.getTitle() + "\u3011\u4e2d\u5185\u90e8\u8868\u4e0d\u5b58\u5728.");
            return null;
        }
        return this.getInputDataTableNameByDataTableKey(inputDataScheme.getTableKey());
    }

    public InputDataSchemeVO getInputDataSchemeByTaskKey(String taskId) {
        TaskDefine taskDefine = this.iRunTimeViewController.queryTaskDefine(taskId);
        String dataSchemeKey = taskDefine.getDataScheme();
        return this.inputDataSchemeCacheService.getInputDataSchemeByDataSchemeKey(dataSchemeKey);
    }

    public String getTableNameByDataSchemeKey(String dataSchemeKey) {
        InputDataSchemeVO inputDataScheme = this.inputDataSchemeCacheService.getInputDataSchemeByDataSchemeKey(dataSchemeKey);
        if (null == inputDataScheme || StringUtils.isEmpty((String)inputDataScheme.getTableKey())) {
            DataScheme dataScheme = this.iRuntimeDataSchemeService.getDataScheme(dataSchemeKey);
            Object[] args = new String[]{dataScheme.getTitle()};
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.inputdata.dataentryquery.inputdataservice.notgcinputdatadataschememsg", (Object[])args));
        }
        return this.getInputDataTableNameByDataTableKey(inputDataScheme.getTableKey());
    }

    public String getDataTableKeyByTaskId(String taskId) {
        InputDataSchemeVO inputDataScheme = this.getInputDataSchemeByTaskKey(taskId);
        if (null == inputDataScheme || StringUtils.isEmpty((String)inputDataScheme.getTableKey())) {
            TaskDefine taskDefine = this.iRunTimeViewController.queryTaskDefine(taskId);
            logger.error("\u4efb\u52a1\u3010" + taskDefine.getTitle() + "\u3011\u4e2d\u5185\u90e8\u8868\u4e0d\u5b58\u5728.");
            return null;
        }
        return inputDataScheme.getTableKey();
    }

    public String getTableCodeByTaskId(String taskId) {
        InputDataSchemeVO inputDataScheme = this.getInputDataSchemeByTaskKey(taskId);
        if (null == inputDataScheme || StringUtils.isEmpty((String)inputDataScheme.getTableKey())) {
            TaskDefine taskDefine = this.iRunTimeViewController.queryTaskDefine(taskId);
            logger.error("\u4efb\u52a1\u3010" + taskDefine.getTitle() + "\u3011\u4e2d\u5185\u90e8\u8868\u4e0d\u5b58\u5728.");
            return null;
        }
        return inputDataScheme.getTableCode();
    }

    public String getInputDataTableNameByDataTableKey(String dataTableKey) {
        NedisCache inputDataTableNameCache = this.cacheManger.getCache(INPUT_DATA_TABLE_NAME_CACHE_KEY);
        Cache.ValueWrapper valueWrapper = inputDataTableNameCache.get(dataTableKey);
        String tableName = null;
        if (null == valueWrapper) {
            List dataFieldDeployInfos = this.iRuntimeDataSchemeService.getDeployInfoByDataTableKey(dataTableKey);
            if (CollectionUtils.isEmpty((Collection)dataFieldDeployInfos)) {
                throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.inputdata.dataentryquery.inputdataservice.notgcinputdatatablemodelmsg") + dataTableKey);
            }
            TableModelDefine tableModel = this.modelService.getTableModelDefineById(((DataFieldDeployInfo)dataFieldDeployInfos.get(0)).getTableModelKey());
            if (null == tableModel || StringUtils.isEmpty((String)tableModel.getName())) {
                Object[] args = new String[]{((DataFieldDeployInfo)dataFieldDeployInfos.get(0)).getTableName()};
                throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.inputdata.dataentryquery.inputdataservice.notgcinputdatadataschememsg", (Object[])args));
            }
            tableName = tableModel.getName();
            inputDataTableNameCache.put(dataTableKey, (Object)tableModel.getName());
        } else {
            tableName = (String)valueWrapper.get();
        }
        return tableName;
    }
}

