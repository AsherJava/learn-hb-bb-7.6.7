/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.datasource.GcBizJdbcTemplate
 *  com.jiuqi.common.base.datasource.OuterTransaction
 *  com.jiuqi.common.base.util.OuterDataSourceUtils
 *  com.jiuqi.dc.base.common.intf.impl.ServiceConfigProperties
 *  com.jiuqi.gcreport.dimension.executor.PreDimensionUpgrade
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 *  org.springframework.dao.DataAccessException
 */
package com.jiuqi.bde.bizmodel.impl.adaptor.update;

import com.jiuqi.common.base.datasource.GcBizJdbcTemplate;
import com.jiuqi.common.base.datasource.OuterTransaction;
import com.jiuqi.common.base.util.OuterDataSourceUtils;
import com.jiuqi.dc.base.common.intf.impl.ServiceConfigProperties;
import com.jiuqi.gcreport.dimension.executor.PreDimensionUpgrade;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

@Component
public class BdeCustUpdateExecute
implements PreDimensionUpgrade {
    private static final Logger logger = LoggerFactory.getLogger("com.jiuqi.dc.mappingscheme.impl.service.tableCheckAndSchemeUpdate");

    public void doBefore(String dataSourceCode) {
        dataSourceCode = "jiuqi.gcreport.mdd.datasource";
        try {
            this.doUpdate(dataSourceCode);
            logger.info("BDE\u81ea\u5b9a\u4e49\u5347\u7ea7\u5b8c\u6210");
        }
        catch (Exception e) {
            logger.error("BDE\u81ea\u5b9a\u4e49\u5347\u7ea7\u51fa\u73b0\u9519\u8bef\uff0c\u8be6\u7ec6\u4fe1\u606f\uff1a", (Object)e.getMessage(), (Object)e);
        }
    }

    @OuterTransaction
    public void doUpdate(String dataSourceCode) {
        StringBuffer sql;
        GcBizJdbcTemplate bizJdbcTemplate = null;
        try {
            bizJdbcTemplate = OuterDataSourceUtils.getJdbcTemplate((String)dataSourceCode);
            sql = new StringBuffer();
            sql.append("UPDATE GC_DIMTABLEREL SET EFFECTSCOPE = EFFECTTABLENAME WHERE SYSCODE = 'bde';  \n");
            bizJdbcTemplate = OuterDataSourceUtils.getJdbcTemplate((String)dataSourceCode);
            bizJdbcTemplate.execute(sql.toString());
            logger.info("BDE\u81ea\u5b9a\u4e49\u5347\u7ea7\u7ef4\u5ea6\u5f71\u54cd\u8868\u8303\u56f4\uff0c\u6267\u884cSQL{}", (Object)sql);
        }
        catch (DataAccessException e) {
            logger.info("BDE\u81ea\u5b9a\u4e49\u5347\u7ea7\u7ef4\u5ea6\u5f71\u54cd\u8868\u8303\u56f4\u51fa\u73b0\u9519\u8bef\uff1a{}\uff0c\u81ea\u52a8\u8df3\u8fc7", (Object)e.getMessage(), (Object)e);
        }
        try {
            sql = new StringBuffer();
            sql.append("UPDATE DC_REF_DATAMAPINGDFINE SET CODE= 'BDE_VOUCHER'  \n");
            sql.append(" WHERE DATASCHEMECODE IN (SELECT CODE FROM DC_REF_DATASCHEME WHERE PLUGINTYPE = 'SAP') AND CODE = 'BDE_BSEG'  \n");
            bizJdbcTemplate.execute(sql.toString());
            logger.info("BDE\u81ea\u5b9a\u4e49\u5347\u7ea7SAP\u4e1a\u52a1\u6620\u5c04\u5b9a\u4e49\u8303\u56f4\uff0c\u6267\u884cSQL{}", (Object)sql);
        }
        catch (DataAccessException e) {
            logger.info("BDE\u81ea\u5b9a\u4e49\u5347\u7ea7SAP\u4e1a\u52a1\u6620\u5c04\u5b9a\u4e49\u8303\u56f4\u51fa\u73b0\u9519\u8bef\uff1a{}\uff0c\u81ea\u52a8\u8df3\u8fc7", (Object)e.getMessage(), (Object)e);
        }
        try {
            sql = new StringBuffer();
            sql.append("UPDATE DC_REF_FIELDMAPINGDFINE SET TABLENAME = 'BDE_VOUCHER'  \n");
            sql.append(" WHERE DATASCHEMECODE IN (SELECT CODE FROM DC_REF_DATASCHEME WHERE PLUGINTYPE = 'SAP') AND TABLENAME = 'BDE_BSEG'  \n");
            bizJdbcTemplate.execute(sql.toString());
            logger.info("BDE\u81ea\u5b9a\u4e49\u5347\u7ea7SAP\u4e1a\u52a1\u6620\u5c04\u5b9a\u4e49\u5b50\u8868\u8303\u56f4\uff0c\u6267\u884cSQL{}", (Object)sql);
        }
        catch (DataAccessException e) {
            logger.info("BDE\u81ea\u5b9a\u4e49\u5347\u7ea7SAP\u4e1a\u52a1\u6620\u5c04\u5b9a\u4e49\u5b50\u8868\u8303\u56f4\u51fa\u73b0\u9519\u8bef\uff1a{}\uff0c\u81ea\u52a8\u8df3\u8fc7", (Object)e.getMessage(), (Object)e);
        }
        try {
            sql = new StringBuffer();
            sql.append("DELETE FROM DC_REF_FIELDMAPINGDFINE  \n");
            sql.append(" WHERE 1 = 1  \n");
            sql.append("   AND (DATASCHEMECODE, DATAMAPPINGID, TABLENAME, FIELDNAME, ORDINAL) IN  \n");
            sql.append("       (SELECT DATASCHEMECODE, DATAMAPPINGID, TABLENAME, FIELDNAME, ORDINAL  \n");
            sql.append("          FROM (SELECT DATASCHEMECODE,  \n");
            sql.append("                       DATAMAPPINGID,  \n");
            sql.append("                       TABLENAME,  \n");
            sql.append("                       FIELDNAME,  \n");
            sql.append("                       MIN(ORDINAL) ORDINAL  \n");
            sql.append("                  FROM DC_REF_FIELDMAPINGDFINE  \n");
            sql.append("                 WHERE 1 = 1  \n");
            sql.append("                   AND DATASCHEMECODE IN  \n");
            sql.append("                       (SELECT CODE  \n");
            sql.append("                          FROM DC_REF_DATASCHEME  \n");
            sql.append("                         WHERE PLUGINTYPE = 'SAP')  \n");
            sql.append("                   AND TABLENAME LIKE 'BDE_%'  \n");
            sql.append("                 GROUP BY DATASCHEMECODE, DATAMAPPINGID, TABLENAME, FIELDNAME  \n");
            sql.append("                HAVING COUNT(1) > 1) u)  \n");
            logger.info("BDE\u81ea\u5b9a\u4e49\u5347\u7ea7SAP\u4e1a\u52a1\u6620\u5c04\u91cd\u590d\u6570\u636e\u8303\u56f4\uff0c\u6267\u884cSQL{}", (Object)sql);
        }
        catch (DataAccessException e) {
            logger.info("BDE\u81ea\u5b9a\u4e49\u5347\u7ea7SAP\u4e1a\u52a1\u6620\u5c04\u91cd\u590d\u6570\u636e\u8303\u56f4\u51fa\u73b0\u9519\u8bef\uff1a{}\uff0c\u81ea\u52a8\u8df3\u8fc7", (Object)e.getMessage(), (Object)e);
        }
        try {
            sql = new StringBuffer();
            sql.append("UPDATE DC_REF_FIELDMAPINGDFINE SET TABLENAME = 'BDE_VOUCHER'  \n");
            sql.append(" WHERE DATASCHEMECODE IN (SELECT CODE FROM DC_REF_DATASCHEME WHERE PLUGINTYPE = 'SAP') AND TABLENAME = 'BDE_BSEG'  \n");
            bizJdbcTemplate.execute(sql.toString());
            logger.info("BDE\u81ea\u5b9a\u4e49\u5347\u7ea7SAP\u4e1a\u52a1\u6620\u5c04\u5b9a\u4e49\u5b50\u8868\u8303\u56f4\uff0c\u6267\u884cSQL{}", (Object)sql);
        }
        catch (DataAccessException e) {
            logger.info("BDE\u81ea\u5b9a\u4e49\u5347\u7ea7SAP\u4e1a\u52a1\u6620\u5c04\u5b9a\u4e49\u5b50\u8868\u8303\u56f4\u51fa\u73b0\u9519\u8bef\uff1a{}\uff0c\u81ea\u52a8\u8df3\u8fc7", (Object)e.getMessage(), (Object)e);
        }
        ServiceConfigProperties serviceConfigProperties = (ServiceConfigProperties)ApplicationContextRegister.getBean(ServiceConfigProperties.class);
        if (serviceConfigProperties == null || "BDE".equalsIgnoreCase(serviceConfigProperties.getServiceName())) {
            try {
                sql = new StringBuffer();
                sql.append("UPDATE DC_REF_FIELDMAPINGDFINE SET ODS_FIELDNAME = replace(ODS_FIELDNAME,'trim(','TRIM(')  \n");
                sql.append(" WHERE DATASCHEMECODE IN (SELECT CODE FROM DC_REF_DATASCHEME WHERE PLUGINTYPE = 'SAP')  \n");
                bizJdbcTemplate.execute(sql.toString());
                logger.info("BDE\u81ea\u5b9a\u4e49\u5347\u7ea7SAP\u4e1a\u52a1\u6620\u5c04\u5b9a\u4e49\u5b50\u8868\u8303\u56f4\uff0c\u6267\u884cSQL{}", (Object)sql);
            }
            catch (DataAccessException e) {
                logger.info("BDE\u81ea\u5b9a\u4e49\u5347\u7ea7SAP\u4e1a\u52a1\u6620\u5c04\u5b9a\u4e49\u5b50\u8868\u8303\u56f4\u51fa\u73b0\u9519\u8bef\uff1a{}\uff0c\u81ea\u52a8\u8df3\u8fc7", (Object)e.getMessage(), (Object)e);
            }
        }
    }
}

