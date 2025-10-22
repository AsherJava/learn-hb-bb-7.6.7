/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.rate.client.vo.CommonRateSchemeVO
 *  com.jiuqi.gcreport.basedata.impl.bean.GcBaseData
 *  com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool
 *  com.jiuqi.gcreport.conversion.common.GcConversionIndexRateInfo
 *  com.jiuqi.gcreport.conversion.common.GcConversionOrgAndFormContextEnv
 *  com.jiuqi.gcreport.conversion.common.GcConversionResult
 *  com.jiuqi.gcreport.i18n.util.GcI18nUtil
 *  com.jiuqi.gcreport.rate.impl.consts.RateTypeEnum
 *  com.jiuqi.gcreport.rate.impl.service.CommonRateSchemeService
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.np.dataengine.definitions.TableModelRunInfo
 *  com.jiuqi.np.dataengine.exception.DataTypeException
 *  com.jiuqi.np.dataengine.intf.IDataRow
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.nr.definition.common.DataRegionKind
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 */
package com.jiuqi.gcreport.conversion.executor;

import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.rate.client.vo.CommonRateSchemeVO;
import com.jiuqi.gcreport.basedata.impl.bean.GcBaseData;
import com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool;
import com.jiuqi.gcreport.conversion.common.GcConversionIndexRateInfo;
import com.jiuqi.gcreport.conversion.common.GcConversionOrgAndFormContextEnv;
import com.jiuqi.gcreport.conversion.common.GcConversionResult;
import com.jiuqi.gcreport.conversion.conversionrate.service.ConversionRateService;
import com.jiuqi.gcreport.conversion.conversionsystem.dao.ConversionSystemItemDao;
import com.jiuqi.gcreport.conversion.conversionsystem.dao.ConversionSystemTaskDao;
import com.jiuqi.gcreport.conversion.conversionsystem.entity.ConversionSystemItemEO;
import com.jiuqi.gcreport.conversion.conversionsystem.entity.ConversionSystemTaskEO;
import com.jiuqi.gcreport.conversion.executor.ConversionFormExecutorDispatcher;
import com.jiuqi.gcreport.conversion.utils.GcConversionUtils;
import com.jiuqi.gcreport.i18n.util.GcI18nUtil;
import com.jiuqi.gcreport.rate.impl.consts.RateTypeEnum;
import com.jiuqi.gcreport.rate.impl.service.CommonRateSchemeService;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.definitions.TableModelRunInfo;
import com.jiuqi.np.dataengine.exception.DataTypeException;
import com.jiuqi.np.dataengine.intf.IDataRow;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.nr.definition.common.DataRegionKind;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractConversionFormExecutor {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConversionFormExecutorDispatcher.class);
    @Autowired
    private ConversionSystemTaskDao taskSchemeDao;
    @Autowired
    private ConversionSystemItemDao itemDao;
    @Autowired
    private ConversionRateService conversionRateService;
    @Autowired
    private CommonRateSchemeService commonRateSchemeService;

    public GcConversionResult conversion(GcConversionOrgAndFormContextEnv formContextEnv) {
        GcConversionResult conversionResult;
        ConversionSystemTaskEO taskSchemeEO = this.getConversionSystemTaskEO(formContextEnv.getTaskId(), formContextEnv.getSchemeId());
        formContextEnv.setRateSchemeCode(taskSchemeEO.getRateSchemeCode());
        TableModelRunInfo tableInfo = null;
        try {
            tableInfo = GcConversionUtils.getDefinitionsCache().getDataModelDefinitionsCache().getTableInfo(formContextEnv.getTableDefine().getName());
        }
        catch (ParseException e) {
            throw new BusinessRuntimeException((Throwable)e);
        }
        List<ConversionSystemItemEO> systemItems = this.getSystemItems(taskSchemeEO, formContextEnv, tableInfo);
        Map<String, BigDecimal> rateValueMap = this.conversionRateService.getRateInfos(taskSchemeEO.getRateSchemeCode(), formContextEnv.getSchemeId(), formContextEnv.getBeforeCurrencyCode(), formContextEnv.getAfterCurrencyCode(), formContextEnv.getPeriodStr());
        if (rateValueMap == null || rateValueMap.size() == 0) {
            GcBaseData beforeCurrency = GcBaseDataCenterTool.getInstance().queryBaseDataSimpleItem("MD_CURRENCY", formContextEnv.getBeforeCurrencyCode());
            GcBaseData afterCurrency = GcBaseDataCenterTool.getInstance().queryBaseDataSimpleItem("MD_CURRENCY", formContextEnv.getAfterCurrencyCode());
            CommonRateSchemeVO rateSchemeVO = this.commonRateSchemeService.queryRateScheme(taskSchemeEO.getRateSchemeCode());
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.coversion.executor.abstract.ratenotfound.error", (Object[])new Object[]{rateSchemeVO.getName(), formContextEnv.getPeriodStr(), formContextEnv.getTableDefine().getCode(), beforeCurrency.getTitle(), afterCurrency.getTitle()}));
        }
        List<GcConversionIndexRateInfo> indexRateInfos = GcConversionUtils.getConversionIndexRateInfos(formContextEnv, taskSchemeEO, systemItems, rateValueMap);
        List needConversionInfos = indexRateInfos.stream().filter(v -> !v.getRateTypeCode().equals(RateTypeEnum.NOTCONV.getCode())).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(needConversionInfos)) {
            LOGGER.warn("\u5355\u4f4d: [{}],\u8868\u5355\uff1a[{}],\u8df3\u8fc7\u5b58\u50a8\u8868[{}]\u6570\u636e\u6298\u7b97\uff0c\u8be6\u60c5:{}", formContextEnv.getOrgTitle(), formContextEnv.getFormDefine().getTitle(), formContextEnv.getTableDefine().getCode(), "\u6ca1\u6709\u9700\u8981\u6298\u7b97\u7684\u6307\u6807\u3002");
            return null;
        }
        if (formContextEnv.getOrg() == null || formContextEnv.getFormDefine() == null || formContextEnv.getDataRegionDefine() == null || formContextEnv.getTableDefine() == null) {
            LOGGER.error("\u6298\u7b97\u4efb\u52a1\u5355\u4f4d\u3001\u62a5\u8868\u8868\u5355\u3001\u62a5\u8868\u533a\u57df\u3001\u5b58\u50a8\u8868\u5b9a\u4e49\u4e3a\u7a7a\uff0c\u8df3\u8fc7\u8be5\u7ec4\u5408\u7ef4\u5ea6\u7684\u6298\u7b97\u4efb\u52a1\u3002");
            return null;
        }
        try {
            HashSet<String> conversionFieldNames = new HashSet<String>();
            tableInfo.getColumnFieldMap().forEach((columnModelDefine, fieldDefine) -> conversionFieldNames.add(columnModelDefine.getName()));
            LOGGER.info("\u5355\u4f4d: [{}] ,\u8868\u5355\uff1a[{}], \u5f00\u59cb\u5b58\u50a8\u8868[{}]\u6570\u636e\u6298\u7b97, \u7531[{}]\u6298\u7b97\u6210[{}]", formContextEnv.getOrgTitle(), formContextEnv.getFormDefine().getTitle(), formContextEnv.getTableDefine().getCode(), formContextEnv.getBeforeCurrencyCode(), formContextEnv.getAfterCurrencyCode());
            conversionResult = this.conversion(formContextEnv, taskSchemeEO, systemItems, indexRateInfos, tableInfo, conversionFieldNames);
            if (conversionResult != null) {
                LOGGER.info("\u5355\u4f4d: [{}],\u8868\u5355\uff1a[{}],\u5b8c\u6210\u5b58\u50a8\u8868[{}]\u6570\u636e\u6298\u7b97\uff0c\u5220\u9664\u5386\u53f2\u6298\u7b97\u540e\u8bb0\u5f55\u6570[{}], \u672c\u6b21\u65b0\u589e\u6298\u7b97\u540e\u8bb0\u5f55\u6570[{}], \u672c\u6b21\u66f4\u65b0\u6298\u7b97\u540e\u8bb0\u5f55\u6570[{}]\u3002", formContextEnv.getOrgTitle(), formContextEnv.getFormDefine().getTitle(), formContextEnv.getTableDefine().getCode(), conversionResult.getDeleteCount(), conversionResult.getInsertCount(), conversionResult.getUpdateCount());
            }
        }
        catch (Exception e) {
            LOGGER.error("\u5355\u4f4d: [{}],\u8868\u5355\uff1a[{}],\u4e2d\u65ad\u5b58\u50a8\u8868[{}]\u6570\u636e\u6298\u7b97\uff0c\u8be6\u60c5:{}", formContextEnv.getOrgTitle(), formContextEnv.getFormDefine().getTitle(), formContextEnv.getTableDefine().getCode(), e.getMessage());
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.coversion.executor.abstract.error", (Object[])new Object[]{formContextEnv.getOrgTitle(), formContextEnv.getFormDefine().getTitle(), formContextEnv.getTableDefine().getCode(), e.getMessage()}), (Throwable)e);
        }
        return conversionResult;
    }

    protected List<ConversionSystemItemEO> getSystemItems(ConversionSystemTaskEO taskSchemeEO, GcConversionOrgAndFormContextEnv env, TableModelRunInfo tableModelRunInfo) {
        Set<String> tableFieldKeys = tableModelRunInfo.getColumnFieldMap().values().stream().map(IBaseMetaItem::getKey).collect(Collectors.toSet());
        List<ConversionSystemItemEO> itemEOs = DataRegionKind.DATA_REGION_SIMPLE.equals((Object)env.getDataRegionDefine().getRegionKind()) ? this.itemDao.getSystemItemsByIndexIds(tableFieldKeys) : this.itemDao.batchGetSystemItemsByFormIdAndIndexIds(env.getFormDefine().getKey(), tableFieldKeys);
        return itemEOs;
    }

    protected final Map<String, BigDecimal> getConversionRateInfos(GcConversionOrgAndFormContextEnv formContextEnv, ConversionSystemTaskEO taskSchemeEO, List<ConversionSystemItemEO> systemItems) {
        String beforeCurrencyCode = formContextEnv.getBeforeCurrencyCode();
        String afterCurrencyCode = formContextEnv.getAfterCurrencyCode();
        String periodStr = formContextEnv.getPeriodStr();
        Map<String, BigDecimal> rateValueMap = this.conversionRateService.getRateInfos(taskSchemeEO.getRateSchemeCode(), formContextEnv.getSchemeId(), beforeCurrencyCode, afterCurrencyCode, periodStr);
        if (rateValueMap == null || rateValueMap.size() == 0) {
            GcBaseData beforeCurrency = GcBaseDataCenterTool.getInstance().queryBaseDataSimpleItem("MD_CURRENCY", beforeCurrencyCode);
            GcBaseData afterCurrency = GcBaseDataCenterTool.getInstance().queryBaseDataSimpleItem("MD_CURRENCY", afterCurrencyCode);
            CommonRateSchemeVO rateSchemeVO = this.commonRateSchemeService.queryRateScheme(taskSchemeEO.getRateSchemeCode());
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.coversion.executor.abstract.ratenotfound.error", (Object[])new Object[]{rateSchemeVO.getName(), periodStr, formContextEnv.getTableDefine().getCode(), beforeCurrency.getTitle(), afterCurrency.getTitle()}));
        }
        return rateValueMap;
    }

    protected abstract GcConversionResult conversion(GcConversionOrgAndFormContextEnv var1, ConversionSystemTaskEO var2, List<ConversionSystemItemEO> var3, List<GcConversionIndexRateInfo> var4, TableModelRunInfo var5, Set<String> var6);

    protected Set<String> getConversionFieldDefines(List<GcConversionIndexRateInfo> indexRateInfos, TableModelRunInfo tableInfo) {
        HashSet<String> conversionFieldNames = new HashSet<String>();
        List needConversionInfos = indexRateInfos.stream().filter(v -> !v.getRateTypeCode().equals(RateTypeEnum.NOTCONV.getCode())).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(needConversionInfos)) {
            return conversionFieldNames;
        }
        tableInfo.getColumnFieldMap().forEach((columnModelDefine, fieldDefine) -> {
            Optional<GcConversionIndexRateInfo> matchItem = indexRateInfos.stream().filter(item -> {
                boolean feildKeyEquals = fieldDefine.getKey().equals(item.getIndexId());
                return feildKeyEquals;
            }).findFirst();
            conversionFieldNames.add(columnModelDefine.getName());
        });
        return conversionFieldNames;
    }

    protected Collection<FieldDefine> getConverisonFieldDefines(TableModelRunInfo tableInfo, Set<String> converisonFieldNames) {
        List<FieldDefine> converisonFieldDefines = converisonFieldNames.stream().map(converisonFieldName -> {
            ColumnModelDefine column = tableInfo.getFieldByName(converisonFieldName);
            FieldDefine fieldDefine = (FieldDefine)tableInfo.getColumnFieldMap().get(column);
            return fieldDefine;
        }).collect(Collectors.toList());
        return converisonFieldDefines;
    }

    protected Set<String> getDimFieldNames(TableModelRunInfo tableInfo) {
        Set<String> dimFeildNames = tableInfo.getDimFields().stream().map(ColumnModelDefine::getName).collect(Collectors.toSet());
        return dimFeildNames;
    }

    protected FieldDefine getFieldDefineByFieldName(TableModelRunInfo tableInfo, String fieldName) {
        ColumnModelDefine columnModelDefine = tableInfo.getFieldByName(fieldName);
        FieldDefine fieldDefine = (FieldDefine)tableInfo.getColumnFieldMap().get(columnModelDefine);
        return fieldDefine;
    }

    private StringBuilder buildWarnMsg(GcConversionOrgAndFormContextEnv formContextEnv) {
        StringBuilder warnMsg = new StringBuilder();
        warnMsg.append("\u672a\u627e\u5230\u6298\u7b97\u4f53\u7cfb\u6307\u6807\u6c47\u7387\u7c7b\u578b\uff0c\u76f4\u63a5\u8fdb\u884c\u6298\u7b97\u6570\u636e\u590d\u5236\uff0c\u7ef4\u5ea6\u8be6\u60c5\uff1a");
        warnMsg.append("\u4efb\u52a1ID\uff1a[").append(formContextEnv.getTaskId()).append("]\uff0c");
        warnMsg.append("\u65b9\u6848ID\uff1a[").append(formContextEnv.getSchemeId()).append("]\uff0c");
        warnMsg.append("\u8868\u5355\uff1a[").append(formContextEnv.getFormDefine().getTitle()).append("]\uff0c");
        warnMsg.append("\u5355\u4f4d\uff1a[").append(formContextEnv.getOrgTitle()).append("]\uff0c");
        warnMsg.append("\u65f6\u671f\uff1a[").append(formContextEnv.getPeriodStr()).append("]\uff0c");
        warnMsg.append("\u6298\u7b97\u524d\u5e01\u79cd\uff1a[").append(formContextEnv.getBeforeCurrencyCode()).append("]\uff0c");
        warnMsg.append("\u6298\u7b97\u540e\u5e01\u79cd\uff1a[").append(formContextEnv.getAfterCurrencyCode()).append("]\u3002");
        return warnMsg;
    }

    private ConversionSystemTaskEO getConversionSystemTaskEO(String taskId, String schemeId) {
        ConversionSystemTaskEO taskSchemeEO = this.taskSchemeDao.queryByTaskAndScheme(taskId, schemeId);
        if (taskSchemeEO == null) {
            taskSchemeEO = new ConversionSystemTaskEO();
            taskSchemeEO.setTaskId(taskId);
            taskSchemeEO.setSchemeId(schemeId);
        }
        return taskSchemeEO;
    }

    protected Object getAfterDimensionFieldValue(TableModelRunInfo tableInfo, IDataRow beforeItem, IDataRow afterItem, FieldDefine fieldDefine) throws DataTypeException {
        String dimensionName;
        DimensionValueSet masterKeys = afterItem.getRowKeys();
        Object masterKeyValue = masterKeys.getValue(dimensionName = tableInfo.getDimensionName(fieldDefine.getCode()));
        if (masterKeyValue != null) {
            return masterKeyValue;
        }
        AbstractData value = beforeItem.getValue(fieldDefine);
        if (value.isNull) {
            return null;
        }
        return value.getAsObject();
    }

    protected abstract boolean isMatch(GcConversionOrgAndFormContextEnv var1);

    protected int matchOrder() {
        return 0;
    }
}

