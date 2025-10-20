/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.jiuqi.bde.bizmodel.execute.assist.IAssistProvider
 *  com.jiuqi.bde.common.util.Variable
 *  com.jiuqi.bde.common.util.VariableParseUtil
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.DateUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.dc.mappingscheme.impl.service.DataSourceService
 *  org.apache.commons.io.IOUtils
 *  org.springframework.dao.DataAccessException
 *  org.springframework.jdbc.core.ResultSetExtractor
 */
package com.jiuqi.bde.plugin.gs_cloud.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.jiuqi.bde.bizmodel.execute.assist.IAssistProvider;
import com.jiuqi.bde.common.util.Variable;
import com.jiuqi.bde.common.util.VariableParseUtil;
import com.jiuqi.bde.plugin.gs_cloud.BdeGsCloudPluginType;
import com.jiuqi.bde.plugin.gs_cloud.util.AssistPojo;
import com.jiuqi.bde.plugin.gs_cloud.util.GsCloudFetchUtil;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.DateUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.dc.mappingscheme.impl.service.DataSourceService;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

@Component
public class GsCloudAssistProvider
implements IAssistProvider<AssistPojo> {
    private static Logger logger = LoggerFactory.getLogger(GsCloudFetchUtil.class);
    @Autowired
    private BdeGsCloudPluginType pluginType;
    @Autowired
    private DataSourceService dataSourceService;

    public String getPluginType() {
        return this.pluginType.getSymbol();
    }

    public List<AssistPojo> listAssist(String dataSourceCode) {
        HashMap assistPojoMap = CollectionUtils.newHashMap();
        List assistPojoList = (List)assistPojoMap.get(dataSourceCode);
        if (assistPojoList != null) {
            return assistPojoList;
        }
        ArrayList initAssistPojoList = CollectionUtils.newArrayList();
        initAssistPojoList.addAll(this.queryJsonAssist());
        initAssistPojoList.addAll(this.queryAssist(dataSourceCode));
        assistPojoMap.put(dataSourceCode, initAssistPojoList);
        return (List)assistPojoMap.get(dataSourceCode);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private List<AssistPojo> queryJsonAssist() {
        ClassPathResource classPathResource = new ClassPathResource("template/GsCloudAssistDefine.json");
        try (InputStream inputStream = classPathResource.getInputStream();){
            Variable variable = new Variable();
            String yearString = Integer.toString(DateUtils.getYearOfDate((Date)new Date()));
            variable.put("YEAR", yearString);
            String replaceSql = IOUtils.toString((InputStream)inputStream, (Charset)StandardCharsets.UTF_8);
            String assistJson = VariableParseUtil.parse((String)replaceSql, (Map)variable.getVariableMap());
            List list = (List)JsonUtils.readValue((String)assistJson, (TypeReference)new TypeReference<List<AssistPojo>>(){});
            return list;
        }
        catch (IOException e) {
            logger.error("\u6d6a\u6f6eGS_CLOUD\u63d2\u4ef6\u5904\u7406\u56fa\u5316\u8f85\u52a9\u9879JSON\u6587\u4ef6\u65f6\u51fa\u9519", e);
            throw new BusinessRuntimeException("\u6d6a\u6f6eGS_CLOUD\u63d2\u4ef6\u9ed8\u8ba4\u521d\u59cb\u5316\u65b9\u6848\u52a0\u8f7d\u5931\u8d25", (Throwable)e);
        }
    }

    private List<AssistPojo> queryAssist(String dataSourceCode) {
        String tableName = String.format("BFCUSTOMITEMCATEGORY%s", DateUtils.getYearOfDate((Date)new Date()));
        return (List)this.dataSourceService.query(dataSourceCode, String.format("SELECT ID AS ID, CODE AS CODE, NAME_CHS AS NAME FROM %1$s", tableName), new Object[0], (ResultSetExtractor)new ResultSetExtractor<List<AssistPojo>>(){

            public List<AssistPojo> extractData(ResultSet rs) throws SQLException, DataAccessException {
                ArrayList assistList = CollectionUtils.newArrayList();
                while (rs.next()) {
                    AssistPojo assistPojo = new AssistPojo();
                    assistPojo.setId(rs.getString(1));
                    assistPojo.setCode("SPECATEID" + rs.getString(2));
                    assistPojo.setName(rs.getString(3));
                    assistPojo.setOdsTableName("BFCUSTOMITEM");
                    assistPojo.setAssField("SPECATEID" + rs.getString(2));
                    assistPojo.setAssSql(String.format("SELECT ID AS ID, CUSITEMCODE AS CODE, CUSITEMFULLNAME_CHS AS NAME FROM BFCUSTOMITEM%s  WHERE 1=1 AND CUSITEMCATEGORY = '%s'", DateUtils.getYearOfDate((Date)new Date()), assistPojo.getId()));
                    assistList.add(assistPojo);
                }
                return assistList;
            }
        });
    }
}

