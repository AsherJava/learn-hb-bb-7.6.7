/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.nvwa.sf.adapter.spring.datasource.dynamic.DynamicDataSource
 *  com.jiuqi.nvwa.sf.adapter.spring.datasource.dynamic.service.IDynamicDataSourceManager
 */
package com.jiuqi.common.base.util;

import com.jiuqi.common.base.datasource.GcBizJdbcTemplate;
import com.jiuqi.common.base.datasource.OuterDataSourceContextHolder;
import com.jiuqi.common.base.util.Pair;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.nvwa.sf.adapter.spring.datasource.dynamic.DynamicDataSource;
import com.jiuqi.nvwa.sf.adapter.spring.datasource.dynamic.service.IDynamicDataSourceManager;
import java.util.Map;
import java.util.Objects;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;

public class OuterDataSourceUtils {
    private static Logger logger = LoggerFactory.getLogger(OuterDataSourceUtils.class);
    private static final String DATASOURCE_PREFIX = "jiuqi.gcreport.";
    private static final String DATASOURCE_SUFFIX = ".datasource";
    private static final String bizDefaultDataSourceCode = SpringContextUtils.getBean(Environment.class).getProperty("jiuqi.gcreport.mdd.datasource");
    private static final String systemDataSourceDbType = SpringContextUtils.getBean(Environment.class).getProperty("spring.datasource.dbType");
    private static final DataSource systemDataSource = SpringContextUtils.getBean(DataSource.class);
    private static final DynamicDataSource dynamicDataSource = SpringContextUtils.getBean(DynamicDataSource.class);
    private static final IDynamicDataSourceManager dynamicDataSourceManager = (IDynamicDataSourceManager)SpringBeanUtils.getBean(IDynamicDataSourceManager.class);

    public static GcBizJdbcTemplate getJdbcTemplate() {
        String outerDataSourceCode = !StringUtils.isEmpty(OuterDataSourceContextHolder.getDataSourceCode()) ? OuterDataSourceContextHolder.getDataSourceCode().trim() : bizDefaultDataSourceCode;
        Pair<DataSource, String> outerDataSourcePair = OuterDataSourceUtils.getOuterDataSourcePair(outerDataSourceCode);
        if (outerDataSourcePair != null) {
            return new GcBizJdbcTemplate(outerDataSourcePair.getFirst(), outerDataSourcePair.getSecond());
        }
        return new GcBizJdbcTemplate(systemDataSource, systemDataSourceDbType);
    }

    public static GcBizJdbcTemplate getJdbcTemplate(String dataSourceCode) {
        Pair<DataSource, String> outerDataSourcePair;
        String outerDataSourceCode = dataSourceCode;
        if (!StringUtils.isEmpty(dataSourceCode) && dataSourceCode.startsWith(DATASOURCE_PREFIX) && dataSourceCode.endsWith(DATASOURCE_SUFFIX)) {
            outerDataSourceCode = SpringContextUtils.getBean(Environment.class).getProperty(dataSourceCode);
        }
        if ((outerDataSourcePair = OuterDataSourceUtils.getOuterDataSourcePair(outerDataSourceCode)) != null) {
            return new GcBizJdbcTemplate(outerDataSourcePair.getFirst(), outerDataSourcePair.getSecond());
        }
        return new GcBizJdbcTemplate(systemDataSource, systemDataSourceDbType);
    }

    public static DataSource getDataSource() {
        String outerDataSourceCode = !StringUtils.isEmpty(OuterDataSourceContextHolder.getDataSourceCode()) ? OuterDataSourceContextHolder.getDataSourceCode().trim() : bizDefaultDataSourceCode;
        Pair<DataSource, String> outerDataSourcePair = OuterDataSourceUtils.getOuterDataSourcePair(outerDataSourceCode);
        return Objects.nonNull(outerDataSourcePair) ? outerDataSourcePair.getFirst() : systemDataSource;
    }

    public static String getOuterDataSourceCode(String dataSourceCode) {
        Pair<DataSource, String> outerDataSourcePair;
        String outerDataSourceCode = dataSourceCode;
        if (!StringUtils.isEmpty(dataSourceCode) && dataSourceCode.startsWith(DATASOURCE_PREFIX) && dataSourceCode.endsWith(DATASOURCE_SUFFIX)) {
            outerDataSourceCode = SpringContextUtils.getBean(Environment.class).getProperty(dataSourceCode);
        }
        return Objects.nonNull(outerDataSourcePair = OuterDataSourceUtils.getOuterDataSourcePair(outerDataSourceCode)) ? outerDataSourceCode : null;
    }

    private static Pair<DataSource, String> getOuterDataSourcePair(String dataSourceCode) {
        if (StringUtils.isEmpty(dataSourceCode)) {
            return null;
        }
        String outerDSCode = dataSourceCode.trim();
        Map resolvedDataSources = dynamicDataSource.getResolvedDataSources();
        if (resolvedDataSources.containsKey(outerDSCode) || dynamicDataSourceManager.getDatasourceKeys().contains(outerDSCode)) {
            String dbType = SpringContextUtils.getBean(Environment.class).getProperty(String.format("jiuqi.nvwa.datasources.%1$s.dbType", outerDSCode));
            if (!StringUtils.isEmpty(dbType)) {
                return new Pair<DataSource, String>(dynamicDataSource.getDataSource(outerDSCode), dbType.trim());
            }
            logger.error(String.format("\u5973\u5a32\u5916\u90e8\u6570\u636e\u6e90\u3010%s\u3011\u5df2\u914d\u7f6e\uff0c\u4f46\u672a\u914d\u7f6e\u6570\u636e\u5e93\u7c7b\u578bdbType\uff0c\u5916\u90e8\u6570\u636e\u6e90\u914d\u7f6e\u65e0\u6548", outerDSCode));
        }
        return null;
    }
}

