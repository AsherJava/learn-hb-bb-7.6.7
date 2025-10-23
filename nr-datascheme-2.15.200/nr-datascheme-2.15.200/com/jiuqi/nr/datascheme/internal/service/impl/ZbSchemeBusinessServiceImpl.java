/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.jiuqi.bi.util.OrderGenerator
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.cache.NedisCache
 *  com.jiuqi.np.cache.NedisCacheManager
 *  com.jiuqi.np.cache.NedisCacheProvider
 *  com.jiuqi.nr.datascheme.api.DataDimension
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.DataScheme
 *  com.jiuqi.nr.datascheme.api.DesignDataField
 *  com.jiuqi.nr.datascheme.api.DesignDataScheme
 *  com.jiuqi.nr.datascheme.api.DesignDataTable
 *  com.jiuqi.nr.datascheme.api.core.Basic
 *  com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo
 *  com.jiuqi.nr.datascheme.api.core.DataSchemeCalResult
 *  com.jiuqi.nr.datascheme.api.service.DataFieldView
 *  com.jiuqi.nr.datascheme.api.service.DataSchemeCalcTask
 *  com.jiuqi.nr.datascheme.api.service.IDataSchemeCalcService
 *  com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.service.IZbSchemeBusinessService
 *  com.jiuqi.nr.datascheme.api.type.DataFieldApplyType
 *  com.jiuqi.nr.datascheme.api.type.DataFieldGatherType
 *  com.jiuqi.nr.datascheme.api.type.DataFieldKind
 *  com.jiuqi.nr.datascheme.api.type.DataFieldType
 *  com.jiuqi.nr.datascheme.api.type.DataTableType
 *  com.jiuqi.nr.datascheme.api.type.DimensionType
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.internal.adapter.IPeriodProvider
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nr.period.modal.IPeriodEntity
 *  com.jiuqi.nr.zb.scheme.common.NodeType
 *  com.jiuqi.nr.zb.scheme.common.VersionStatus
 *  com.jiuqi.nr.zb.scheme.common.ZbDataType
 *  com.jiuqi.nr.zb.scheme.common.ZbType
 *  com.jiuqi.nr.zb.scheme.core.ZbInfo
 *  com.jiuqi.nr.zb.scheme.core.ZbSchemeVersion
 *  com.jiuqi.nr.zb.scheme.exception.ZbSchemeException
 *  com.jiuqi.nr.zb.scheme.internal.tree.INode
 *  com.jiuqi.nr.zb.scheme.internal.tree.ITree
 *  com.jiuqi.nr.zb.scheme.internal.tree.TreeNodeQueryParam
 *  com.jiuqi.nr.zb.scheme.internal.tree.ZbInfoNode
 *  com.jiuqi.nr.zb.scheme.service.IZbSchemeService
 *  com.jiuqi.nr.zb.scheme.service.IZbSchemeTreeService
 *  com.jiuqi.nr.zb.scheme.utils.JsonUtils
 *  com.jiuqi.nvwa.definition.common.ProgressItem
 */
package com.jiuqi.nr.datascheme.internal.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.cache.NedisCache;
import com.jiuqi.np.cache.NedisCacheManager;
import com.jiuqi.np.cache.NedisCacheProvider;
import com.jiuqi.nr.datascheme.api.DataDimension;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.DataScheme;
import com.jiuqi.nr.datascheme.api.DesignDataField;
import com.jiuqi.nr.datascheme.api.DesignDataScheme;
import com.jiuqi.nr.datascheme.api.DesignDataTable;
import com.jiuqi.nr.datascheme.api.core.Basic;
import com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo;
import com.jiuqi.nr.datascheme.api.core.DataSchemeCalResult;
import com.jiuqi.nr.datascheme.api.service.DataFieldView;
import com.jiuqi.nr.datascheme.api.service.DataSchemeCalcTask;
import com.jiuqi.nr.datascheme.api.service.IDataSchemeCalcService;
import com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.service.IZbSchemeBusinessService;
import com.jiuqi.nr.datascheme.api.type.DataFieldApplyType;
import com.jiuqi.nr.datascheme.api.type.DataFieldGatherType;
import com.jiuqi.nr.datascheme.api.type.DataFieldKind;
import com.jiuqi.nr.datascheme.api.type.DataFieldType;
import com.jiuqi.nr.datascheme.api.type.DataTableType;
import com.jiuqi.nr.datascheme.api.type.DimensionType;
import com.jiuqi.nr.datascheme.exception.DataSchemeException;
import com.jiuqi.nr.datascheme.internal.dao.IDataSchemeCalResultDao;
import com.jiuqi.nr.datascheme.internal.dto.DataSchemeCalResultDTO;
import com.jiuqi.nr.datascheme.internal.dto.ValidationRuleDTO;
import com.jiuqi.nr.datascheme.internal.entity.DataSchemeCalResultDO;
import com.jiuqi.nr.datascheme.internal.job.CalZbAsyncTaskMonitor;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.period.internal.adapter.IPeriodProvider;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import com.jiuqi.nr.zb.scheme.common.NodeType;
import com.jiuqi.nr.zb.scheme.common.VersionStatus;
import com.jiuqi.nr.zb.scheme.common.ZbDataType;
import com.jiuqi.nr.zb.scheme.common.ZbType;
import com.jiuqi.nr.zb.scheme.core.ZbInfo;
import com.jiuqi.nr.zb.scheme.core.ZbSchemeVersion;
import com.jiuqi.nr.zb.scheme.exception.ZbSchemeException;
import com.jiuqi.nr.zb.scheme.internal.tree.INode;
import com.jiuqi.nr.zb.scheme.internal.tree.ITree;
import com.jiuqi.nr.zb.scheme.internal.tree.TreeNodeQueryParam;
import com.jiuqi.nr.zb.scheme.internal.tree.ZbInfoNode;
import com.jiuqi.nr.zb.scheme.service.IZbSchemeService;
import com.jiuqi.nr.zb.scheme.service.IZbSchemeTreeService;
import com.jiuqi.nr.zb.scheme.utils.JsonUtils;
import com.jiuqi.nvwa.definition.common.ProgressItem;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
public class ZbSchemeBusinessServiceImpl
implements IZbSchemeBusinessService {
    private static final Logger log = LoggerFactory.getLogger(ZbSchemeBusinessServiceImpl.class);
    @Autowired
    private IDesignDataSchemeService designDataSchemeService;
    @Autowired
    private IRuntimeDataSchemeService runtimeDataSchemeService;
    @Autowired
    private IZbSchemeService zbSchemeService;
    @Autowired
    private IZbSchemeTreeService zbSchemeTreeService;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private PeriodEngineService periodEngineService;
    @Autowired(required=false)
    private IDataSchemeCalcService dataSchemeCalcService;
    @Autowired
    private IDataSchemeCalResultDao dataSchemeCalResultDao;
    private static final String CACHE_NAME = "cal_zb";
    private static final String LOCK_PREFIX = "lock:";
    private NedisCache cache;

    @Autowired
    private void setCacheManager(NedisCacheProvider cacheProvider) {
        NedisCacheManager cacheManager = cacheProvider.getCacheManager("nr:scheme");
        this.cache = cacheManager.getCache(CACHE_NAME);
    }

    public List<DesignDataScheme> getSyncDataScheme(String zbSchemeKey, String period) {
        List allDataScheme = this.designDataSchemeService.getAllDataScheme();
        ArrayList<DesignDataScheme> res = new ArrayList<DesignDataScheme>();
        for (DesignDataScheme designDataScheme : allDataScheme) {
            ZbSchemeVersion zbSchemeVersion = this.zbSchemeService.getZbSchemeVersion(designDataScheme.getZbSchemeVersion());
            if (!zbSchemeKey.equals(designDataScheme.getZbSchemeKey()) || !period.equals(zbSchemeVersion.getStartPeriod())) continue;
            res.add(designDataScheme);
        }
        return res;
    }

    public List<ITree<INode>> queryZbGroupTree(String tableKey, String location) {
        TreeNodeQueryParam treeNodeQueryParam = this.createTreeNodeQueryParam(tableKey, location, null);
        return this.zbSchemeTreeService.queryZbGroupTree(treeNodeQueryParam);
    }

    private TreeNodeQueryParam createTreeNodeQueryParam(String tableKey, String location, String filter) {
        List fields;
        DesignDataTable dataTable = this.designDataSchemeService.getDataTable(tableKey);
        DesignDataScheme dataScheme = this.designDataSchemeService.getDataScheme(dataTable.getDataSchemeKey());
        switch (dataTable.getDataTableType()) {
            case TABLE: 
            case MD_INFO: {
                fields = this.designDataSchemeService.getAllDataFieldByKind(dataScheme.getKey(), new DataFieldKind[]{DataFieldKind.FIELD_ZB});
                break;
            }
            case DETAIL: 
            case ACCOUNT: {
                fields = this.designDataSchemeService.getDataFieldByTable(tableKey);
                break;
            }
            default: {
                throw new DataSchemeException("\u8be5\u7c7b\u578b\u7684\u6570\u636e\u8868\u4e0d\u652f\u6301\u5f15\u5165\u6307\u6807");
            }
        }
        Set codes = fields.stream().map(Basic::getCode).collect(Collectors.toSet());
        TreeNodeQueryParam treeNodeQueryParam = new TreeNodeQueryParam();
        treeNodeQueryParam.setRemoveEmptyGroup(true);
        treeNodeQueryParam.setSchemeKey(dataScheme.getZbSchemeKey());
        treeNodeQueryParam.setVersionKey(dataScheme.getZbSchemeVersion());
        treeNodeQueryParam.setLocation(location);
        treeNodeQueryParam.setKeyword(filter);
        treeNodeQueryParam.setType(NodeType.ZB_INFO);
        List dimension = this.designDataSchemeService.getDataSchemeDimension(dataTable.getDataSchemeKey(), DimensionType.DIMENSION);
        boolean isMultiDimension = !CollectionUtils.isEmpty(dimension);
        treeNodeQueryParam.setNodeFilter(node -> {
            if (node.getNodeType() == NodeType.ZB_INFO) {
                ZbInfoNode data = (ZbInfoNode)node;
                if (isMultiDimension || dataTable.getDataTableType() != DataTableType.TABLE) {
                    return data.getZbType() == ZbType.GENERAL_ZB;
                }
            }
            return true;
        });
        treeNodeQueryParam.setNodeDisabled(node -> {
            log.debug("filter node: {}\uff0c code: {}", (Object)codes.contains(node.getCode()), (Object)node.getCode());
            return codes.contains(node.getCode());
        });
        return treeNodeQueryParam;
    }

    public List<ITree<INode>> filterZbInfo(String tableKey, String keyword) {
        TreeNodeQueryParam treeNodeQueryParam = this.createTreeNodeQueryParam(tableKey, null, keyword);
        return this.zbSchemeTreeService.filterZbInfo(treeNodeQueryParam);
    }

    public void referZbInfo(String tableKey, List<String> codes) {
        if (CollectionUtils.isEmpty(codes)) {
            return;
        }
        DesignDataTable dataTable = this.designDataSchemeService.getDataTable(tableKey);
        if (dataTable == null) {
            throw new ZbSchemeException("\u6570\u636e\u8868\u4e0d\u5b58\u5728");
        }
        DesignDataScheme dataScheme = this.designDataSchemeService.getDataScheme(dataTable.getDataSchemeKey());
        if (dataScheme == null) {
            throw new ZbSchemeException("\u6570\u636e\u65b9\u6848\u4e0d\u5b58\u5728");
        }
        ZbSchemeVersion zbSchemeVersion = this.zbSchemeService.getZbSchemeVersionByKey(dataScheme.getZbSchemeVersion());
        if (zbSchemeVersion == null) {
            throw new ZbSchemeException("\u6307\u6807\u4f53\u7cfb\u7248\u672c\u4e0d\u5b58\u5728");
        }
        if (!zbSchemeVersion.getStatus().equals((Object)VersionStatus.PUBLISHED)) {
            throw new ZbSchemeException("\u6307\u6807\u4f53\u7cfb\u7248\u672c\u672a\u53d1\u5e03");
        }
        List infos = this.zbSchemeService.listZbInfoBySchemeAndVersion(dataScheme.getZbSchemeKey(), dataScheme.getZbSchemeVersion());
        HashSet<String> selectedCodes = new HashSet<String>(codes);
        Map<String, DesignDataField> oldFieldMap = this.designDataSchemeService.getDataFieldByTable(tableKey).stream().collect(Collectors.toMap(Basic::getCode, f -> f));
        ArrayList<DesignDataField> updateList = new ArrayList<DesignDataField>();
        ArrayList<DesignDataField> insertList = new ArrayList<DesignDataField>();
        for (ZbInfo info : infos) {
            DesignDataField designDataField;
            if (!selectedCodes.contains(info.getCode())) continue;
            if (oldFieldMap.containsKey(info.getCode())) {
                designDataField = oldFieldMap.get(info.getCode());
                this.updateProperties(designDataField, info);
                updateList.add(designDataField);
                continue;
            }
            designDataField = this.initDataField(dataScheme, dataTable, info);
            insertList.add(designDataField);
        }
        if (!updateList.isEmpty()) {
            this.designDataSchemeService.updateDataFields(updateList);
        }
        if (!insertList.isEmpty()) {
            this.designDataSchemeService.insertDataFields(insertList);
        }
    }

    private DesignDataField initDataField(DesignDataScheme dataScheme, DesignDataTable dataTable, ZbInfo info) {
        DesignDataField designDataField = this.designDataSchemeService.initDataField();
        designDataField.setDataSchemeKey(dataScheme.getKey());
        designDataField.setDataTableKey(dataTable.getKey());
        designDataField.setOrder(OrderGenerator.newOrder());
        switch (dataTable.getDataTableType()) {
            case TABLE: 
            case MD_INFO: {
                designDataField.setDataFieldKind(DataFieldKind.FIELD_ZB);
                break;
            }
            case DETAIL: 
            case ACCOUNT: {
                designDataField.setDataFieldKind(DataFieldKind.FIELD);
                break;
            }
            default: {
                throw new DataSchemeException("\u8be5\u8868\u4e0d\u652f\u6301\u5f15\u5165\u6307\u6807");
            }
        }
        this.updateProperties(designDataField, info);
        if (DataTableType.ACCOUNT.equals((Object)dataTable.getDataTableType())) {
            designDataField.setGenerateVersion(Boolean.valueOf(true));
        }
        return designDataField;
    }

    private void updateProperties(DesignDataField designDataField, ZbInfo info) {
        DataFieldType dataFieldType;
        designDataField.setUpdateTime(Instant.now());
        designDataField.setTitle(info.getTitle());
        designDataField.setMeasureUnit(info.getMeasureUnit());
        designDataField.setRefDataEntityKey(info.getRefEntityId());
        designDataField.setAllowUndefinedCode(Boolean.valueOf(info.isAllowUndefinedCode()));
        if (info.getApplyType() != null) {
            designDataField.setDataFieldApplyType(DataFieldApplyType.valueOf((int)info.getApplyType().getValue()));
        }
        designDataField.setPrecision(info.getPrecision());
        designDataField.setDecimal(info.getDecimal());
        designDataField.setDesc(info.getDesc());
        designDataField.setCode(info.getCode());
        designDataField.setZbType(info.getType());
        designDataField.setZbSchemeVersion(info.getVersionKey());
        ZbDataType dataType = info.getDataType();
        if (dataType == null || (dataFieldType = DataFieldType.valueOf((int)dataType.getValue())) == null) {
            throw new DataSchemeException("\u4e0d\u652f\u6301\u5f15\u5165\u6307\u6807\u7c7b\u578b");
        }
        designDataField.setDataFieldType(dataFieldType);
        if (info.getGatherType() != null) {
            designDataField.setDataFieldGatherType(DataFieldGatherType.valueOf((int)info.getGatherType().getValue()));
        }
        designDataField.setNullable(Boolean.valueOf(info.isNullable()));
        designDataField.setLevel(info.getLevel());
        designDataField.setZbSchemeVersion(info.getVersionKey());
        designDataField.setUseAuthority(Boolean.valueOf(false));
        designDataField.setChangeWithPeriod(Boolean.valueOf(false));
        designDataField.setFormatProperties(info.getFormatProperties());
        designDataField.setAllowMultipleSelect(Boolean.valueOf(info.isAllowMultipleSelect()));
        if (!CollectionUtils.isEmpty(info.getValidationRules())) {
            try {
                String json = JsonUtils.toJson((Object)info.getValidationRules());
                List ruleDTOS = (List)JsonUtils.fromJson((String)json, (TypeReference)new TypeReference<List<ValidationRuleDTO>>(){});
                if (ruleDTOS != null) {
                    designDataField.setValidationRules(new ArrayList(ruleDTOS));
                }
            }
            catch (Exception e) {
                log.error("\u6307\u6807\u89c4\u5219\u8f6c\u6362\u5f02\u5e38", e);
            }
        } else {
            designDataField.setValidationRules(null);
        }
    }

    public void pullZbInfo(List<String> dataSchemeKeys) {
        if (CollectionUtils.isEmpty(dataSchemeKeys)) {
            log.debug("\u6ca1\u6709\u6307\u5b9a\u6570\u636e\u65b9\u6848\uff0c\u8df3\u8fc7\u62c9\u53d6\u6307\u6807\u4fe1\u606f");
            return;
        }
        List dataSchemes = this.designDataSchemeService.getDataSchemes(dataSchemeKeys);
        if (dataSchemes.isEmpty()) {
            return;
        }
        for (DesignDataScheme dataScheme : dataSchemes) {
            if (dataScheme.getZbSchemeKey() == null || dataScheme.getZbSchemeVersion() == null) continue;
            ZbSchemeVersion zbSchemeVersion = this.zbSchemeService.getZbSchemeVersionByKey(dataScheme.getZbSchemeVersion());
            if (zbSchemeVersion == null) {
                throw new DataSchemeException("\u6307\u6807\u4f53\u7cfb\u7248\u672c\u4e0d\u5b58\u5728");
            }
            if (VersionStatus.PUBLISHED.equals((Object)zbSchemeVersion.getStatus())) continue;
            throw new DataSchemeException("\u6307\u6807\u4f53\u7cfb\u7248\u672c\u672a\u53d1\u5e03");
        }
        ArrayList<DesignDataField> updateList = new ArrayList<DesignDataField>();
        for (DesignDataScheme dataScheme : dataSchemes) {
            List fields = this.designDataSchemeService.getAllDataFieldByKind(dataScheme.getKey(), new DataFieldKind[]{DataFieldKind.FIELD_ZB, DataFieldKind.FIELD});
            Map<String, ZbInfo> zbInfoMap = this.zbSchemeService.listZbInfoBySchemeAndVersion(dataScheme.getZbSchemeKey(), dataScheme.getZbSchemeVersion()).stream().collect(Collectors.toMap(ZbInfo::getCode, f -> f));
            int count = 0;
            for (DesignDataField field : fields) {
                if (!zbInfoMap.containsKey(field.getCode())) continue;
                ++count;
                field.setZbSchemeVersion(dataScheme.getZbSchemeVersion());
                this.updateProperties(field, zbInfoMap.get(field.getCode()));
                updateList.add(field);
            }
            log.debug("\u6570\u636e\u65b9\u6848\uff1a{}\uff0c\u66f4\u65b0\u6307\u6807\u4fe1\u606f\u6570\u91cf\uff1a{}", (Object)dataScheme.getTitle(), (Object)count);
        }
        if (!CollectionUtils.isEmpty(updateList)) {
            this.designDataSchemeService.updateDataFields(updateList);
            log.debug("\u66f4\u65b0\u6307\u6807\u4fe1\u606f\u6210\u529f\uff1a{}", (Object)updateList.size());
        }
    }

    public String executeCalFormula(DataSchemeCalcTask task) {
        Assert.notNull((Object)task, "\u53c2\u6570\u4e0d\u80fd\u4e3a\u7a7a");
        Assert.notNull((Object)task.getDataSchemeKey(), "\u6570\u636e\u65b9\u6848\u4e0d\u80fd\u4e3a\u7a7a");
        String dataSchemeKey = task.getDataSchemeKey();
        CalZbAsyncTaskMonitor monitor = new CalZbAsyncTaskMonitor(dataSchemeKey, this.cache, this.dataSchemeCalResultDao);
        monitor.progressAndMessage(0.1, "\u6b63\u5728\u6821\u9a8c\u53c2\u6570");
        DataScheme dataScheme = this.runtimeDataSchemeService.getDataScheme(dataSchemeKey);
        if (dataScheme == null) {
            log.debug("\u6570\u636e\u65b9\u6848\u4e0d\u5b58\u5728\u6216\u8005\u672a\u53d1\u5e03\uff0c\u8df3\u8fc7\u8ba1\u7b97\u6307\u6807\uff1a{}", (Object)dataSchemeKey);
            throw new DataSchemeException("\u6570\u636e\u65b9\u6848\u4e0d\u5b58\u5728\u6216\u8005\u672a\u53d1\u5e03");
        }
        if (task.getStartPeriod() == null || task.getEndPeriod() == null) {
            List dataDimensions = this.runtimeDataSchemeService.getDataSchemeDimension(dataSchemeKey, DimensionType.PERIOD);
            DataDimension dimension = (DataDimension)dataDimensions.stream().findFirst().orElseThrow(() -> new DataSchemeException("\u65f6\u671f\u4e0d\u5b58\u5728"));
            IPeriodProvider periodProvider = this.periodEngineService.getPeriodAdapter().getPeriodProvider(dimension.getDimKey());
            String[] periodCodeRegion = periodProvider.getPeriodCodeRegion();
            task.setStartPeriod(periodCodeRegion[0]);
            task.setEndPeriod(periodCodeRegion[1]);
        }
        monitor.progressAndMessage(0.2, "\u6821\u9a8c\u53c2\u6570\u5b8c\u6210");
        log.debug("\u5f00\u59cb\u8ba1\u7b97\u6307\u6807\uff1a{}", (Object)task);
        try {
            if (this.dataSchemeCalcService != null) {
                monitor.progressAndMessage(0.5, "\u51c6\u5907\u5904\u7406");
                this.dataSchemeCalcService.executeCalc(task, (AsyncTaskMonitor)monitor);
            }
        }
        catch (Exception e) {
            log.error("\u8ba1\u7b97\u6307\u6807\u5931\u8d25\uff1a{}", (Object)e.getMessage());
            monitor.finish("\u5904\u7406\u5931\u8d25", e.getMessage());
            throw new DataSchemeException(e);
        }
        return monitor.getId();
    }

    public ProgressItem queryCalZbProgress(String id) {
        return (ProgressItem)this.cache.get(id, ProgressItem.class);
    }

    public boolean checkZbRefer(String zbScheme, String zbCode) {
        Assert.notNull((Object)zbScheme, "zbScheme\u4e0d\u80fd\u4e3a\u7a7a");
        Assert.notNull((Object)zbCode, "zbCode\u4e0d\u80fd\u4e3a\u7a7a");
        List allDataScheme = this.designDataSchemeService.getAllDataScheme();
        List schemeKeys = allDataScheme.stream().filter(scheme -> zbScheme.equals(scheme.getZbSchemeKey())).map(Basic::getKey).collect(Collectors.toList());
        for (String schemeKey : schemeKeys) {
            List fields = this.designDataSchemeService.getAllDataField(schemeKey);
            for (DesignDataField field : fields) {
                if (!zbCode.equals(field.getCode())) continue;
                return true;
            }
        }
        return false;
    }

    public DataSchemeCalResult queryCalResult(String dataSchemeKey) {
        List<DataSchemeCalResultDO> result = this.dataSchemeCalResultDao.getResult(dataSchemeKey);
        if (CollectionUtils.isEmpty(result)) {
            return null;
        }
        return DataSchemeCalResultDTO.valueOf(result.get(result.size() - 1));
    }

    public List<DataFieldView> getDataFieldView(List<String> keys, String period) {
        log.debug("\u67e5\u8be2\u6307\u6807\u4fe1\u606f\uff0cperiod\uff1a{}", (Object)period);
        if (CollectionUtils.isEmpty(keys)) {
            log.debug("keys\u4e3a\u7a7a\uff0c\u8fd4\u56de\u7a7a\u96c6\u5408");
            return Collections.emptyList();
        }
        List dataFields = this.designDataSchemeService.getDataFields(keys);
        List deployInfos = this.runtimeDataSchemeService.getDeployInfoByDataFieldKeys(keys.toArray(new String[0]));
        Map<String, DataFieldDeployInfo> deployInfoMap = deployInfos.stream().collect(Collectors.toMap(DataFieldDeployInfo::getDataFieldKey, f -> f, (o1, o2) -> o1));
        Map<Object, Object> zbMap = StringUtils.hasLength(period) ? this.getSchemeAndZbInfoMap(dataFields, period) : Collections.emptyMap();
        int index = 1;
        ZbInfo zbInfo = null;
        ArrayList<DataFieldView> res = new ArrayList<DataFieldView>(dataFields.size());
        HashMap<String, String> entityMap = new HashMap<String, String>();
        for (DataField dataField : dataFields) {
            Map infoMap;
            if (zbMap.containsKey(dataField.getDataSchemeKey()) && (infoMap = (Map)zbMap.get(dataField.getDataSchemeKey())).containsKey(dataField.getCode())) {
                zbInfo = (ZbInfo)infoMap.get(dataField.getCode());
            }
            DataFieldView view = zbInfo == null ? DataFieldView.valueOf((DataField)dataField, null) : DataFieldView.valueOf((DataField)dataField, zbInfo);
            if (deployInfoMap.containsKey(dataField.getKey())) {
                view.setPhysicalFieldName(deployInfoMap.get(dataField.getKey()).getFieldName());
                view.setPhysicalTableName(deployInfoMap.get(dataField.getKey()).getTableName());
            }
            if (StringUtils.hasLength(view.getRefEntityId())) {
                if (!entityMap.containsKey(view.getRefEntityId())) {
                    if (this.periodEngineService.getPeriodAdapter().isPeriodEntity(view.getRefEntityId())) {
                        IPeriodEntity periodEntity = this.periodEngineService.getPeriodAdapter().getPeriodEntity(view.getRefEntityId());
                        entityMap.put(view.getRefEntityId(), periodEntity.getTitle());
                    } else {
                        IEntityDefine queryEntity = this.entityMetaService.queryEntity(view.getRefEntityId());
                        entityMap.put(view.getRefEntityId(), queryEntity.getTitle());
                    }
                }
                view.setRefEntityTitle((String)entityMap.get(view.getRefEntityId()));
            }
            view.setIndex(Integer.valueOf(index++));
            res.add(view);
        }
        log.debug("\u67e5\u8be2\u6570\u636e\u5b57\u6bb5\u89c6\u56fe\uff0c\u7ed3\u679c\uff1a{}", (Object)res);
        return res;
    }

    private Map<String, Map<String, ZbInfo>> getSchemeAndZbInfoMap(List<DesignDataField> dataFields, String period) {
        HashMap<String, Map<String, ZbInfo>> zbMap = new HashMap<String, Map<String, ZbInfo>>();
        HashSet<String> schemes = new HashSet<String>();
        for (DataField dataField : dataFields) {
            schemes.add(dataField.getDataSchemeKey());
        }
        List dataSchemes = this.runtimeDataSchemeService.getDataSchemes(new ArrayList(schemes));
        for (DataScheme dataScheme : dataSchemes) {
            ZbSchemeVersion version = this.zbSchemeService.getZbSchemeVersion(dataScheme.getZbSchemeKey(), period);
            if (version != null) {
                List zbInfos = this.zbSchemeService.listZbInfoByVersion(version.getKey());
                zbMap.put(dataScheme.getKey(), zbInfos.stream().filter(Objects::nonNull).collect(Collectors.toMap(ZbInfo::getCode, f -> f, (o1, o2) -> o1)));
                continue;
            }
            log.warn("\u672a\u627e\u5230\u7248\u672c\u4fe1\u606f\uff0czbSchemeKey\uff1a{}\uff0cperiod\uff1a{}", (Object)dataScheme.getZbSchemeKey(), (Object)period);
            zbMap.put(dataScheme.getKey(), Collections.emptyMap());
        }
        return zbMap;
    }
}

