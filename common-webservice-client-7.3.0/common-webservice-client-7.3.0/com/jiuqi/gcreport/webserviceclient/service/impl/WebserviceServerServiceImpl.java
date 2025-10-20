/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.basedata.impl.bean.GcBaseData
 *  com.jiuqi.gcreport.basedata.impl.service.GcBaseDataService
 *  com.jiuqi.va.query.datasource.service.DynamicDataSourceService
 *  javax.jws.WebService
 *  org.json.JSONObject
 */
package com.jiuqi.gcreport.webserviceclient.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.basedata.impl.bean.GcBaseData;
import com.jiuqi.gcreport.basedata.impl.service.GcBaseDataService;
import com.jiuqi.gcreport.webserviceclient.gather.IWsDataHandlerGather;
import com.jiuqi.gcreport.webserviceclient.service.WebserviceServerService;
import com.jiuqi.gcreport.webserviceclient.task.WebserviceClientExecuter;
import com.jiuqi.gcreport.webserviceclient.vo.RequestGcWsDataParam;
import com.jiuqi.gcreport.webserviceclient.vo.saptozjk.SapToZjkParam;
import com.jiuqi.gcreport.webserviceclient.vo.saptozjk.SapToZjkResult;
import com.jiuqi.va.query.datasource.service.DynamicDataSourceService;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import javax.jws.WebService;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@WebService(targetNamespace="urn:jiuqi:document:gcreport:functions", name="GCREPORT_WS_SERVER", serviceName="GCREPORT_WS_SERVER", portName="GCREPORT_WS_SERVER", endpointInterface="com.jiuqi.gcreport.webserviceclient.service.WebserviceServerService")
public class WebserviceServerServiceImpl
implements WebserviceServerService {
    private static Logger LOGGER = LoggerFactory.getLogger(WebserviceServerServiceImpl.class);
    private Lock lock = new ReentrantLock();
    @Autowired
    private DynamicDataSourceService dynamicDataSourceService;
    @Autowired
    private IWsDataHandlerGather iGcWsServiceGather;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public SapToZjkResult sapDataToZjk(SapToZjkParam sapToZjkParam) {
        this.lock.lock();
        SapToZjkResult response = this.getSapToZjkResult(sapToZjkParam);
        try {
            LOGGER.info("\u63a5\u6536\u5230SAP\u6570\u636e\u63a8\u9001\u62a5\u6587\u62a5\u6587\uff1a{}", (Object)JsonUtils.writeValueAsString((Object)sapToZjkParam));
            String datasourcecode = this.getDatasourcecode(sapToZjkParam);
            String tableName = sapToZjkParam.getHeader().getType();
            if (StringUtils.isEmpty((String)tableName)) {
                throw new BusinessRuntimeException("\u4e2d\u95f4\u5e93\u8868\u540d\u4e3a\u7a7a");
            }
            String queryJson = sapToZjkParam.getQueryJson();
            List sapDataRows = (List)JsonUtils.readValue((String)new JSONObject(queryJson).getJSONArray("TEXT").toString(), (TypeReference)new TypeReference<List<Map<String, Object>>>(){});
            if (!CollectionUtils.isEmpty((Collection)sapDataRows)) {
                this.batchInsertSapDatas(datasourcecode, tableName, sapDataRows);
            }
            SapToZjkResult sapToZjkResult = response;
            return sapToZjkResult;
        }
        catch (Exception e) {
            String errorMsg = String.format("SAP\u6570\u636e\u63a8\u9001\u5931\u8d25:%1$s", e.getMessage());
            LOGGER.error(errorMsg, e);
            response.setMsgType("E");
            response.setMsg(errorMsg);
            SapToZjkResult sapToZjkResult = response;
            return sapToZjkResult;
        }
        finally {
            this.lock.unlock();
        }
    }

    @Override
    public String requestGcWsData(RequestGcWsDataParam requestGcWsDataParam) {
        return this.iGcWsServiceGather.getWsDataHandler(requestGcWsDataParam.getZifno()).doHandle(requestGcWsDataParam);
    }

    private void batchInsertSapDatas(String datasourcecode, String tableName, List<Map<String, Object>> sapDataRows) {
        ArrayList<String> fieldNames = new ArrayList<String>(sapDataRows.get(0).keySet());
        if (CollectionUtils.isEmpty(fieldNames)) {
            return;
        }
        String sql = this.getInsertSql(tableName, fieldNames);
        ArrayList<Object[]> batchDatas = new ArrayList<Object[]>();
        for (Map<String, Object> sapDataRow : sapDataRows) {
            Object[] dataRow = new Object[fieldNames.size()];
            for (int i = 0; i < fieldNames.size(); ++i) {
                if (sapDataRow == null) continue;
                dataRow[i] = sapDataRow.get(fieldNames.get(i));
            }
            batchDatas.add(dataRow);
        }
        this.dynamicDataSourceService.batchUpdate(datasourcecode, sql, batchDatas);
    }

    private String getDatasourcecode(SapToZjkParam sapToZjkParam) {
        SapToZjkParam.HEADER header = sapToZjkParam.getHeader();
        String taskId = header.getId();
        String wsBaseDataCode = WebserviceClientExecuter.taskId2WsClientCode.get(taskId.toUpperCase());
        if (StringUtils.isEmpty((String)wsBaseDataCode)) {
            throw new BusinessRuntimeException("\u672a\u83b7\u53d6\u5230\u4efb\u52a1Id\u5bf9\u5e94\u7684\u57fa\u7840\u6570\u636e\u9879");
        }
        GcBaseData baseData = ((GcBaseDataService)SpringContextUtils.getBean(GcBaseDataService.class)).queryBasedataByCode("MD_WS_CLIENT", wsBaseDataCode);
        if (baseData == null) {
            throw new BusinessRuntimeException(String.format("\u672a\u67e5\u8be2\u5230\u57fa\u7840\u6570\u636e\u3010MD_WS_CLIENT\u3011\u4ee3\u7801\u4e3a\u3010%1$s\u3011\u7684\u57fa\u7840\u6570\u636e\u9879", wsBaseDataCode));
        }
        String datasourcecode = (String)baseData.getFieldVal("DATASOURCECODE");
        if (StringUtils.isEmpty((String)datasourcecode)) {
            throw new BusinessRuntimeException(String.format("\u57fa\u7840\u6570\u636e\u3010MD_WS_CLIENT\u3011\u4ee3\u7801\u4e3a\u3010%1$s\u3011\u7684\u57fa\u7840\u6570\u636e\u9879\u4e2d\u95f4\u5e93\u6570\u636e\u6e90\u5b57\u6bb5\u4e3a\u7a7a", wsBaseDataCode));
        }
        if (header.getPackageNum() == header.getPackageSum()) {
            WebserviceClientExecuter.taskId2WsClientCode.remove(taskId);
        }
        return datasourcecode;
    }

    private String getInsertSql(String tableName, List<String> fieldNames) {
        int i;
        StringBuilder sql = new StringBuilder();
        sql = sql.append("INSERT INTO ").append(tableName).append(" (");
        for (i = 0; i < fieldNames.size(); ++i) {
            sql = sql.append(fieldNames.get(i));
            if (i == fieldNames.size() - 1) continue;
            sql = sql.append(",");
        }
        sql.append(") VALUES(");
        for (i = 0; i < fieldNames.size(); ++i) {
            sql = sql.append("?");
            if (i == fieldNames.size() - 1) continue;
            sql = sql.append(",");
        }
        sql.append(")");
        return sql.toString();
    }

    private SapToZjkResult getSapToZjkResult(SapToZjkParam sapToZjkParam) {
        SapToZjkResult response = new SapToZjkResult();
        response.setId(sapToZjkParam.getHeader().getId());
        response.setPackageNum(sapToZjkParam.getHeader().getPackageNum());
        response.setMsgType("S");
        response.setMsg("SAP\u6570\u636e\u63a8\u9001\u6210\u529f");
        return response;
    }
}

