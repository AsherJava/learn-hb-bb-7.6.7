/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.dc.mappingscheme.impl.service.DataSourceService
 *  com.jiuqi.va.query.datasource.enumerate.DataSourceEnum
 *  com.jiuqi.va.query.datasource.service.DynamicDataSourceService
 *  com.jiuqi.va.query.template.dao.impl.QueryTemplateDataSourceSetDaoImpl
 *  com.jiuqi.va.query.template.vo.TemplateDataSourceSetVO
 */
package com.jiuqi.bde.bizmodel.define.adaptor.database;

import com.jiuqi.dc.mappingscheme.impl.service.DataSourceService;
import com.jiuqi.va.query.datasource.enumerate.DataSourceEnum;
import com.jiuqi.va.query.datasource.service.DynamicDataSourceService;
import com.jiuqi.va.query.template.dao.impl.QueryTemplateDataSourceSetDaoImpl;
import com.jiuqi.va.query.template.vo.TemplateDataSourceSetVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

@Primary
@Repository
public class BdeQueryTemplateDataSourceSetDaoImpl
extends QueryTemplateDataSourceSetDaoImpl {
    @Autowired
    private DynamicDataSourceService dataSourceService;
    @Autowired
    private DataSourceService dsService;

    public TemplateDataSourceSetVO getByTemplateId(String templateId) {
        String dbType = this.dsService.getDbType(DataSourceService.CURRENT);
        if (!"polardb".equalsIgnoreCase(dbType) && !"postgresql".equalsIgnoreCase(dbType)) {
            return super.getByTemplateId(templateId);
        }
        String sql = " select id,defineSql from dc_query_template_sql where templateId = ? ";
        return (TemplateDataSourceSetVO)this.dataSourceService.pageQuery(DataSourceEnum.CURRENT.getName(), sql, 1, 1, new Object[]{templateId}, rs -> {
            if (rs.next()) {
                TemplateDataSourceSetVO dataSourceSetVO = new TemplateDataSourceSetVO();
                dataSourceSetVO.setTemplateId(templateId);
                dataSourceSetVO.setId(rs.getString(1));
                dataSourceSetVO.setDefineSql(rs.getString(2));
                return dataSourceSetVO;
            }
            return null;
        });
    }
}

