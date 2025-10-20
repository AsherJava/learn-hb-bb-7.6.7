/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.datasource.GcBizJdbcTemplate
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.OuterDataSourceUtils
 *  com.jiuqi.common.base.util.TableParseUtils
 *  com.jiuqi.dc.mappingscheme.impl.service.executor.ICustQueryUpdate
 *  com.jiuqi.va.query.common.ConnectionTypeEnum
 *  com.jiuqi.va.query.common.service.QuerySqlInterceptorUtil
 *  com.jiuqi.va.query.datasource.service.QueryDataSourceService
 *  com.jiuqi.va.query.datasource.vo.DataSourceInfoVO
 */
package com.jiuqi.bde.bizmodel.impl.adaptor.update;

import com.jiuqi.common.base.datasource.GcBizJdbcTemplate;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.OuterDataSourceUtils;
import com.jiuqi.common.base.util.TableParseUtils;
import com.jiuqi.dc.mappingscheme.impl.service.executor.ICustQueryUpdate;
import com.jiuqi.va.query.common.ConnectionTypeEnum;
import com.jiuqi.va.query.common.service.QuerySqlInterceptorUtil;
import com.jiuqi.va.query.datasource.service.QueryDataSourceService;
import com.jiuqi.va.query.datasource.vo.DataSourceInfoVO;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class BdeCustQueryUpdate
implements ICustQueryUpdate {
    private static final Logger logger = LoggerFactory.getLogger(BdeCustQueryUpdate.class);
    @Autowired
    private QueryDataSourceService dataSourceService;

    public void custQueryUpdate(String dataSourceCode) {
        try {
            boolean tableExists = TableParseUtils.tableExist((String)dataSourceCode, (List)CollectionUtils.newArrayList((Object[])new String[]{"DC_QUERY_DATASOURCEINFO"}));
            if (!tableExists) {
                logger.info("\u81ea\u5b9a\u4e49\u67e5\u8be2\u5347\u7ea7-\u5e95\u8868\u6821\u9a8c\u5931\u8d25\uff0c\u8df3\u8fc7\u5347\u7ea7\uff01\uff01\uff01");
                return;
            }
            this.doUpdate(dataSourceCode);
        }
        catch (Exception e) {
            logger.error("\u81ea\u5b9a\u4e49\u67e5\u8be2\u5347\u7ea7\u51fa\u73b0\u9519\u8bef\uff0c\u8be6\u7ec6\u4fe1\u606f\uff1a", (Object)e.getMessage(), (Object)e);
        }
    }

    private void doUpdate(String dataSourceCode) {
        GcBizJdbcTemplate bizJdbcTemplate = OuterDataSourceUtils.getJdbcTemplate((String)dataSourceCode);
        String sql = "select id,code,name,driver,url,userName,pwd,ip,port,dataBaseType,dataBaseName,updateTime,enableTempTable,inParamValueMaxCount,dataBaseParam, connectionType from DC_Query_DataSourceInfo where 1 = 1 order by updateTime desc ";
        List oldDataSourceList = bizJdbcTemplate.query(sql = QuerySqlInterceptorUtil.getInterceptorSqlString((String)sql), (rs, rowNum) -> {
            DataSourceInfoVO dataSource = new DataSourceInfoVO();
            dataSource.setId(rs.getString(1));
            dataSource.setCode(rs.getString(2));
            dataSource.setName(rs.getString(3));
            dataSource.setDriver(rs.getString(4));
            dataSource.setUrl(rs.getString(5));
            dataSource.setUserName(rs.getString(6));
            dataSource.setPassWord(rs.getString(7));
            dataSource.setIp(rs.getString(8));
            dataSource.setPort(rs.getString(9));
            dataSource.setDataBaseType(rs.getString(10));
            dataSource.setDataBaseName(rs.getString(11));
            dataSource.setUpdateTime(Long.valueOf(rs.getTimestamp(12).getTime()));
            dataSource.setEnableTempTable(Boolean.valueOf(rs.getInt(13) == 1));
            dataSource.setInParamValueMaxCount(Integer.valueOf(rs.getInt(14)));
            dataSource.setDataBaseParam(rs.getString(15));
            dataSource.setConnectionType(StringUtils.hasText(rs.getString(16)) ? rs.getString(16) : ConnectionTypeEnum.NORMAL.getName());
            return dataSource;
        });
        if (CollectionUtils.isEmpty((Collection)oldDataSourceList)) {
            logger.info("\u81ea\u5b9a\u4e49\u67e5\u8be2\u5347\u7ea7-\u5386\u53f2\u5e93\u4e0d\u5b58\u5728\u6570\u636e\u6e90\u914d\u7f6e\uff0c\u8df3\u8fc7\u5347\u7ea7\uff01\uff01\uff01");
        }
        Map<String, DataSourceInfoVO> existsDsMap = this.dataSourceService.getAllDataSources().stream().collect(Collectors.toMap(DataSourceInfoVO::getCode, item -> item));
        for (DataSourceInfoVO dataSourceInfoVO : oldDataSourceList) {
            if (existsDsMap.containsKey(dataSourceInfoVO.getCode())) {
                logger.info("\u81ea\u5b9a\u4e49\u67e5\u8be2\u5347\u7ea7-\u81ea\u5b9a\u4e49\u6570\u636e\u6e90{}{}\u5df2\u7ecf\u5b58\u5728\u914d\u7f6e\uff0c\u81ea\u52a8\u8df3\u8fc7", (Object)dataSourceInfoVO.getCode(), (Object)dataSourceInfoVO.getName());
                continue;
            }
            try {
                dataSourceInfoVO.setId(null);
                dataSourceInfoVO.setCode(dataSourceInfoVO.getCode());
                this.dataSourceService.updateDataSource(dataSourceInfoVO);
                logger.info("\u81ea\u5b9a\u4e49\u67e5\u8be2\u5347\u7ea7-\u81ea\u5b9a\u4e49\u6570\u636e\u6e90{}{}-{}\uff0c\u4fdd\u5b58\u5b8c\u6210", dataSourceInfoVO.getCode(), dataSourceInfoVO.getName(), JsonUtils.writeValueAsString((Object)dataSourceInfoVO));
            }
            catch (Exception e) {
                logger.info("\u81ea\u5b9a\u4e49\u67e5\u8be2\u5347\u7ea7-\u81ea\u5b9a\u4e49\u6570\u636e\u6e90{}{}-{}\u4fdd\u5b58\u51fa\u73b0\u9519\u8bef\uff0c\u81ea\u52a8\u8df3\u8fc7", dataSourceInfoVO.getCode(), dataSourceInfoVO.getName(), JsonUtils.writeValueAsString((Object)dataSourceInfoVO), e);
            }
        }
    }
}

