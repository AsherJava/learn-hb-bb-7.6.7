/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.DatabaseManager
 *  com.jiuqi.bi.database.IDatabase
 */
package com.jiuqi.nvwa.sf.adapter.spring.datasource.dynamic.service.impl;

import com.jiuqi.bi.database.DatabaseManager;
import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.nvwa.sf.adapter.spring.datasource.dynamic.DynamicDataSource;
import com.jiuqi.nvwa.sf.adapter.spring.datasource.dynamic.bean.NvwaDataSourceBasicInfo;
import com.jiuqi.nvwa.sf.adapter.spring.datasource.dynamic.bean.NvwaDataSourceGroup;
import com.jiuqi.nvwa.sf.adapter.spring.datasource.dynamic.bean.NvwaDataSourceProperties;
import com.jiuqi.nvwa.sf.adapter.spring.datasource.dynamic.bean.NvwaExtDataSourceProperties;
import com.jiuqi.nvwa.sf.adapter.spring.datasource.dynamic.config.DynamicDatasourceConfigurationProperties;
import com.jiuqi.nvwa.sf.adapter.spring.datasource.dynamic.creator.IDataSourceLifecycleAdaptor;
import com.jiuqi.nvwa.sf.adapter.spring.datasource.dynamic.dto.NvwaDataSourceInfoDto;
import com.jiuqi.nvwa.sf.adapter.spring.datasource.dynamic.exception.DataSourceCreationException;
import com.jiuqi.nvwa.sf.adapter.spring.datasource.dynamic.exception.DataSourceNotFoundException;
import com.jiuqi.nvwa.sf.adapter.spring.datasource.dynamic.service.IDataSourceInfoFilter;
import com.jiuqi.nvwa.sf.adapter.spring.datasource.dynamic.service.IDynamicDataSourceManager;
import com.jiuqi.nvwa.sf.adapter.spring.dsproxy.NvwaDataSourceDelegator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

@Service
public class DynamicDataSourceManagementService
implements IDynamicDataSourceManager,
InitializingBean {
    private static final Logger logger = LoggerFactory.getLogger(DynamicDataSourceManagementService.class);
    public static final String SYSTEM_DATASOURCE_CATEGORY = "SYSTEM_DATASOURCE_CATEGORY";
    private final Map<String, NvwaDataSourceProperties> systemDataSourcePropertiesMap = new ConcurrentHashMap<String, NvwaDataSourceProperties>();
    private final Map<String, NvwaExtDataSourceProperties> extDataSourcePropertiesMap = new ConcurrentHashMap<String, NvwaExtDataSourceProperties>();
    private List<IDataSourceLifecycleAdaptor> dataSourceCreators;
    private DynamicDataSource dynamicDataSource;
    private DynamicDatasourceConfigurationProperties dynamicDatasourceConfigurationProperties;
    private DataSourceProperties dataSourceProperties;
    @Value(value="${spring.datasource.type}")
    private Class<? extends DataSource> defaultDataSourceType;
    private NvwaDataSourceDelegator nvwaDataSourceDelegator;

    @Autowired
    public void setNvwaDataSourceDelegator(NvwaDataSourceDelegator nvwaDataSourceDelegator) {
        this.nvwaDataSourceDelegator = nvwaDataSourceDelegator;
    }

    @Autowired
    public void setDynamicDataSource(DynamicDataSource dynamicDataSource) {
        this.dynamicDataSource = dynamicDataSource;
    }

    @Autowired
    public void setDataSourceCreators(List<IDataSourceLifecycleAdaptor> dataSourceCreators) {
        this.dataSourceCreators = dataSourceCreators;
    }

    @Autowired
    public void setDynamicDatasourceConfigurationProperties(DynamicDatasourceConfigurationProperties dynamicDatasourceConfigurationProperties) {
        this.dynamicDatasourceConfigurationProperties = dynamicDatasourceConfigurationProperties;
    }

    @Autowired
    public void setDataSourceProperties(DataSourceProperties dataSourceProperties) {
        this.dataSourceProperties = dataSourceProperties;
    }

    @Autowired(required=false)
    public void setExtDataSourceFilter(List<IDataSourceInfoFilter> extDataSourceFilterList) {
        if (ObjectUtils.isEmpty(extDataSourceFilterList)) {
            return;
        }
        logger.info("\u591a\u6570\u636e\u6e90\u8bbe\u7f6e extDataSourceFilter. \u627e\u5230 {} \u4e2a IDataSourceInfoFilter \u4e2a\u5b9e\u73b0", (Object)extDataSourceFilterList.size());
    }

    @Override
    public void addDataSource(String key, NvwaExtDataSourceProperties dataSourceProperties) throws DataSourceCreationException {
        if (this.isDataSourceExist(key)) {
            throw new IllegalArgumentException("\u6570\u636e\u6e90 " + key + " \u5df2\u5b58\u5728");
        }
        this.validateAndFillDataSourceProperties(dataSourceProperties);
        this.dynamicDataSource.addDataSource(key, this.doCreateDataSource(key, dataSourceProperties));
        this.extDataSourcePropertiesMap.put(key, dataSourceProperties);
    }

    private boolean isDataSourceExist(String key) {
        return this.systemDataSourcePropertiesMap.containsKey(key) || this.extDataSourcePropertiesMap.containsKey(key);
    }

    @Override
    public void updateDataSource(String key, NvwaExtDataSourceProperties dataSourceProperties) throws DataSourceCreationException {
        logger.debug("\u66f4\u65b0\u6570\u636e\u6e90: {}", (Object)key);
        if (this.isSystemDataSource(key)) {
            logger.error("\u6570\u636e\u6e90 {} \u662f\u7cfb\u7edf\u6570\u636e\u6e90\uff0c\u4e0d\u5141\u8bb8\u4fee\u6539", (Object)key);
            throw new IllegalArgumentException("\u7cfb\u7edf\u6570\u636e\u6e90\u9700\u8981\u5728\u914d\u7f6e\u6587\u4ef6\u4e2d\u4fee\u6539");
        }
        this.validateAndFillDataSourceProperties(dataSourceProperties);
        DataSource dataSource = this.doCreateDataSource(key, dataSourceProperties);
        DataSource existingDataSource = this.dynamicDataSource.removeDataSource(key);
        this.dynamicDataSource.addDataSource(key, dataSource);
        this.extDataSourcePropertiesMap.put(key, dataSourceProperties);
        this.shutdownDataSource(key, existingDataSource);
    }

    @Override
    public void deleteDataSource(String key) {
        logger.debug("\u5220\u9664\u6570\u636e\u6e90: {}", (Object)key);
        if (this.isSystemDataSource(key)) {
            logger.error("\u6570\u636e\u6e90 {} \u662f\u7cfb\u7edf\u6570\u636e\u6e90\uff0c\u4e0d\u5141\u8bb8\u5220\u9664", (Object)key);
            throw new IllegalArgumentException("\u7cfb\u7edf\u6570\u636e\u6e90\u9700\u8981\u5728\u914d\u7f6e\u6587\u4ef6\u4e2d\u5220\u9664");
        }
        DataSource dataSource = this.dynamicDataSource.removeDataSource(key);
        this.shutdownDataSource(key, dataSource);
        this.extDataSourcePropertiesMap.remove(key);
    }

    private boolean isSystemDataSource(String key) {
        return this.systemDataSourcePropertiesMap.containsKey(key);
    }

    private void shutdownDataSource(String key, DataSource dataSource) {
        NvwaDataSourceBasicInfo datasourceInfo = this.getDatasourceBasicInfo(key);
        Class<? extends DataSource> datasourceType = datasourceInfo.getType();
        IDataSourceLifecycleAdaptor adaptor = this.findAdaptor(datasourceType);
        if (adaptor != null) {
            adaptor.terminate(dataSource);
        }
    }

    @Override
    public Set<String> getDatasourceKeys() {
        HashSet<String> set = new HashSet<String>(this.systemDataSourcePropertiesMap.keySet());
        set.addAll(this.extDataSourcePropertiesMap.keySet());
        return Collections.unmodifiableSet(set);
    }

    @Override
    @Deprecated
    public NvwaDataSourceBasicInfo getDatasourceBasicInfo(String key) throws DataSourceNotFoundException {
        if (this.systemDataSourcePropertiesMap.containsKey(key)) {
            return NvwaDataSourceBasicInfo.fromDataSourceProperties(this.systemDataSourcePropertiesMap.get(key));
        }
        if (this.extDataSourcePropertiesMap.containsKey(key)) {
            return NvwaDataSourceBasicInfo.fromExtDataSourceProperties(this.extDataSourcePropertiesMap.get(key));
        }
        throw new DataSourceNotFoundException("\u6570\u636e\u6e90: " + key + " \u4e0d\u5b58\u5728");
    }

    @Override
    public boolean containsDataSource(String key) {
        return this.systemDataSourcePropertiesMap.containsKey(key) || this.extDataSourcePropertiesMap.containsKey(key);
    }

    @Override
    @Deprecated
    public List<NvwaDataSourceBasicInfo> getAllDataSourceBasicInfos() {
        ArrayList<NvwaDataSourceBasicInfo> result = new ArrayList<NvwaDataSourceBasicInfo>();
        result.addAll(this.systemDataSourcePropertiesMap.values().stream().map(NvwaDataSourceBasicInfo::fromDataSourceProperties).collect(Collectors.toList()));
        result.addAll(this.extDataSourcePropertiesMap.values().stream().map(NvwaDataSourceBasicInfo::fromExtDataSourceProperties).collect(Collectors.toList()));
        return result;
    }

    @Override
    public void updateDataSourceTitle(String key, String title) {
        NvwaExtDataSourceProperties newProperties = this.extDataSourcePropertiesMap.computeIfPresent(key, (k, v) -> {
            v.setTitle(title);
            return v;
        });
        if (newProperties == null) {
            logger.warn("\u6570\u636e\u6e90 {} \u4e0d\u5b58\u5728, \u6ca1\u6709\u6267\u884c\u66f4\u65b0\u6807\u9898\u64cd\u4f5c", (Object)key);
        }
    }

    @Override
    public void updateDataSourceGroup(String key, NvwaDataSourceGroup group) {
        NvwaExtDataSourceProperties newProperties = this.extDataSourcePropertiesMap.computeIfPresent(key, (k, v) -> {
            v.setGroup(group);
            return v;
        });
        if (newProperties == null) {
            logger.warn("\u6570\u636e\u6e90 {} \u4e0d\u5b58\u5728, \u6ca1\u6709\u6267\u884c\u66f4\u65b0\u5206\u7ec4\u64cd\u4f5c", (Object)key);
        }
    }

    @Override
    public List<NvwaDataSourceInfoDto> listAllDataSourceInfos() {
        ArrayList<NvwaDataSourceInfoDto> result = new ArrayList<NvwaDataSourceInfoDto>();
        result.addAll(this.systemDataSourcePropertiesMap.entrySet().stream().map(entry -> this.convertToDto((String)entry.getKey(), (NvwaDataSourceProperties)entry.getValue())).collect(Collectors.toList()));
        result.addAll(this.extDataSourcePropertiesMap.entrySet().stream().map(entry -> this.convertToDto((String)entry.getKey(), (NvwaExtDataSourceProperties)entry.getValue())).collect(Collectors.toList()));
        return result;
    }

    @Override
    public NvwaDataSourceInfoDto getDataSourceInfo(String key) throws DataSourceNotFoundException {
        if (this.systemDataSourcePropertiesMap.containsKey(key)) {
            return this.convertToDto(key, this.systemDataSourcePropertiesMap.get(key));
        }
        if (this.extDataSourcePropertiesMap.containsKey(key)) {
            return this.convertToDto(key, this.extDataSourcePropertiesMap.get(key));
        }
        throw new DataSourceNotFoundException("\u6570\u636e\u6e90: " + key + " \u4e0d\u5b58\u5728");
    }

    private NvwaDataSourceInfoDto convertToDto(String key, NvwaDataSourceProperties properties) {
        NvwaDataSourceInfoDto dto = new NvwaDataSourceInfoDto();
        dto.setKey(key);
        String url = properties.getUrl();
        if (StringUtils.hasLength(url)) {
            IDatabase database = DatabaseManager.getInstance().findDatabaseByURL(url);
            dto.setDbType(database.getName());
        }
        dto.setName(properties.getName());
        dto.setTitle(properties.getTitle());
        dto.setGroup(NvwaDataSourceGroup.SYSTEM_DATASOURCE_GROUP);
        dto.setCategory(SYSTEM_DATASOURCE_CATEGORY);
        return dto;
    }

    private NvwaDataSourceInfoDto convertToDto(String key, NvwaExtDataSourceProperties properties) {
        NvwaDataSourceInfoDto dto = new NvwaDataSourceInfoDto();
        dto.setKey(key);
        String url = properties.getUrl();
        IDatabase database = DatabaseManager.getInstance().findDatabaseByURL(url);
        dto.setDbType(database.getTitle());
        dto.setName(properties.getName());
        dto.setTitle(properties.getTitle());
        dto.setGroup(properties.getGroup());
        dto.setCategory(properties.getCategory());
        return dto;
    }

    private DataSource doCreateDataSource(String key, NvwaExtDataSourceProperties dataSourceProperties) throws DataSourceCreationException {
        Class<? extends DataSource> dataSourceType = dataSourceProperties.getType();
        IDataSourceLifecycleAdaptor creator = this.findAdaptor(dataSourceType);
        if (creator == null) {
            throw new DataSourceCreationException("\u4e0d\u652f\u6301" + dataSourceType.getName() + "\u7c7b\u578b\u7684\u8fde\u63a5\u6c60");
        }
        DataSource dataSource = creator.createExtDataSource(key, dataSourceProperties);
        if (dataSource == null) {
            logger.error("\u6570\u636e\u6e90 {} \u521b\u5efa\u5931\u8d25: creator {} \u8fd4\u56de\u4e86 null", (Object)key, (Object)creator.getClass().getName());
            throw new DataSourceCreationException("\u6570\u636e\u6e90\u521b\u5efa\u5931\u8d25");
        }
        this.extDataSourcePropertiesMap.put(key, dataSourceProperties);
        return this.nvwaDataSourceDelegator.doDelegate(dataSource);
    }

    private IDataSourceLifecycleAdaptor findAdaptor(Class<? extends DataSource> datasourceType) {
        for (IDataSourceLifecycleAdaptor creator : this.dataSourceCreators) {
            if (!creator.supports(datasourceType)) continue;
            return creator;
        }
        return null;
    }

    @Override
    public void afterPropertiesSet() {
        this.systemDataSourcePropertiesMap.clear();
        NvwaDataSourceProperties mainDataSourceProperties = new NvwaDataSourceProperties();
        BeanUtils.copyProperties(this.dataSourceProperties, mainDataSourceProperties);
        if (mainDataSourceProperties.getName() == null) {
            mainDataSourceProperties.setName("nvwa");
        }
        if (mainDataSourceProperties.getTitle() == null) {
            mainDataSourceProperties.setTitle("\u4e3b\u6570\u636e\u6e90");
        }
        this.systemDataSourcePropertiesMap.put("_default", mainDataSourceProperties);
        DynamicDatasourceConfigurationProperties properties = this.dynamicDatasourceConfigurationProperties;
        Map<String, NvwaDataSourceProperties> systemExtDataSourceProperties = properties.getDatasources();
        if (systemExtDataSourceProperties != null && !systemExtDataSourceProperties.isEmpty()) {
            systemExtDataSourceProperties.forEach((key, value) -> {
                if (value.getName() == null) {
                    value.setName((String)key);
                }
                if (value.getTitle() == null) {
                    value.setTitle(value.getName());
                }
            });
            this.systemDataSourcePropertiesMap.putAll(systemExtDataSourceProperties);
        }
    }

    private void validateAndFillDataSourceProperties(NvwaExtDataSourceProperties dataSourceProperties) throws DataSourceCreationException {
        if (dataSourceProperties == null) {
            throw new DataSourceCreationException("\u6570\u636e\u6e90\u914d\u7f6e\u4fe1\u606f\u4e0d\u80fd\u4e3a\u7a7a");
        }
        if (dataSourceProperties.getType() == null) {
            logger.info("\u6570\u636e\u6e90 {} type \u4e3a\u7a7a\uff0c\u4f7f\u7528\u9ed8\u8ba4\u6570\u636e\u6e90\u7c7b\u578b {}", (Object)dataSourceProperties.getName(), (Object)this.defaultDataSourceType);
            dataSourceProperties.setType(this.defaultDataSourceType);
        }
        if (dataSourceProperties.getUrl() == null) {
            throw new DataSourceCreationException("\u6570\u636e\u6e90 URL \u4e0d\u80fd\u4e3a\u7a7a");
        }
        if (dataSourceProperties.getUsername() == null) {
            throw new DataSourceCreationException("\u6570\u636e\u6e90\u7528\u6237\u540d\u4e0d\u80fd\u4e3a\u7a7a");
        }
        if (dataSourceProperties.getPassword() == null) {
            throw new DataSourceCreationException("\u6570\u636e\u6e90\u5bc6\u7801\u4e0d\u80fd\u4e3a\u7a7a");
        }
        dataSourceProperties.determineDriverClassName();
    }
}

