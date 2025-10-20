/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.jiuqi.bde.bizmodel.execute.assist.IAssistProvider
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.dc.mappingscheme.impl.service.DataSourceService
 *  org.apache.commons.io.IOUtils
 *  org.springframework.dao.DataAccessException
 *  org.springframework.jdbc.core.ResultSetExtractor
 */
package com.jiuqi.bde.plugin.gs5.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.jiuqi.bde.bizmodel.execute.assist.IAssistProvider;
import com.jiuqi.bde.plugin.gs5.BdeGs5PluginType;
import com.jiuqi.bde.plugin.gs5.util.AssistPojo;
import com.jiuqi.bde.plugin.gs5.util.Gs5FetchUtil;
import com.jiuqi.bde.plugin.gs5.util.ShareTypeEnum;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.dc.mappingscheme.impl.service.DataSourceService;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
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
public class Gs5AssistProvider
implements IAssistProvider<AssistPojo> {
    private static final Logger logger = LoggerFactory.getLogger(Gs5AssistProvider.class);
    private static String assistJson;
    @Autowired
    private BdeGs5PluginType pluginType;
    @Autowired
    private DataSourceService dataSourceService;

    public String getPluginType() {
        return this.pluginType.getSymbol();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public List<AssistPojo> listAssist(String dataSourceCode) {
        if (assistJson == null) {
            InputStream inputStream = null;
            try {
                ClassPathResource classPathResource = new ClassPathResource("template/Gs5AssistDefine.json");
                inputStream = classPathResource.getInputStream();
                assistJson = IOUtils.toString((InputStream)inputStream, (Charset)StandardCharsets.UTF_8);
            }
            catch (Exception e) {
                try {
                    throw new BusinessRuntimeException("\u6d6a\u6f6eGS5\u63d2\u4ef6\u9ed8\u8ba4\u521d\u59cb\u5316\u65b9\u6848\u52a0\u8f7d\u5931\u8d25", (Throwable)e);
                }
                catch (Throwable throwable) {
                    try {
                        IOUtils.close(inputStream);
                        throw throwable;
                    }
                    catch (IOException e2) {
                        logger.error("\u5173\u95ed\u6587\u4ef6\u6d41\u51fa\u73b0\u9519\u8bef", e2);
                    }
                    throw throwable;
                }
            }
            try {
                IOUtils.close((Closeable)inputStream);
            }
            catch (IOException e) {
                logger.error("\u5173\u95ed\u6587\u4ef6\u6d41\u51fa\u73b0\u9519\u8bef", e);
            }
        }
        final ArrayList<AssistPojo> assistPojoList = new ArrayList<AssistPojo>((Collection)JsonUtils.readValue((String)assistJson, (TypeReference)new TypeReference<List<AssistPojo>>(){}));
        final String tableName = Gs5FetchUtil.getTableName();
        this.dataSourceService.query(dataSourceCode, String.format("SELECT LSXMLB_LBBH AS CODE ,LSXMLB_LBMC AS NAME FROM %1$s", tableName), new Object[0], (ResultSetExtractor)new ResultSetExtractor<Map<String, Integer>>(){

            public Map<String, Integer> extractData(ResultSet rs) throws SQLException, DataAccessException {
                while (rs.next()) {
                    AssistPojo assistPojo = new AssistPojo();
                    assistPojo.setShareTypeEnum(ShareTypeEnum.SHARE);
                    assistPojo.setCode(rs.getString(1));
                    assistPojo.setName(rs.getString(2));
                    assistPojo.setOdsTableName(tableName);
                    assistPojo.setAssField("XMBH");
                    assistPojo.setAssSql(String.format("SELECT LSXMLB_LBBH AS ID,LSXMLB_LBBH AS CODE ,LSXMLB_LBMC AS NAME FROM %1$s", tableName));
                    assistPojoList.add(assistPojo);
                }
                return null;
            }
        });
        return assistPojoList;
    }
}

