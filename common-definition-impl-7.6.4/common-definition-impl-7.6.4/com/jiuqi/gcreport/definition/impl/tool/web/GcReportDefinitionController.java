/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.gcreport.definition.api.GcReportDefinitionClient
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.datamodel.DataModelDO
 *  com.jiuqi.va.domain.datamodel.DataModelDTO
 *  com.jiuqi.va.feign.client.DataModelClient
 *  com.jiuqi.va.mapper.common.JDialectUtil
 *  com.jiuqi.va.mapper.domain.TableColumnDO
 *  javax.servlet.http.HttpServletResponse
 *  org.apache.commons.lang3.StringUtils
 *  org.apache.shiro.util.ThreadContext
 *  org.springframework.web.bind.annotation.CrossOrigin
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.gcreport.definition.impl.tool.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.definition.api.GcReportDefinitionClient;
import com.jiuqi.gcreport.definition.impl.basic.base.define.DefinitionTableV;
import com.jiuqi.gcreport.definition.impl.basic.base.provider.impl.EntFieldDefine;
import com.jiuqi.gcreport.definition.impl.basic.base.provider.impl.EntTableDefine;
import com.jiuqi.gcreport.definition.impl.basic.init.table.service.TableDefinitionService;
import com.jiuqi.gcreport.definition.impl.basic.init.table.va.service.EntVaModelDataInitService;
import com.jiuqi.gcreport.definition.impl.basic.intf.IEntTableDefineProvider;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.datamodel.DataModelDO;
import com.jiuqi.va.domain.datamodel.DataModelDTO;
import com.jiuqi.va.feign.client.DataModelClient;
import com.jiuqi.va.mapper.common.JDialectUtil;
import com.jiuqi.va.mapper.domain.TableColumnDO;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.util.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@Primary
public class GcReportDefinitionController
implements GcReportDefinitionClient {
    @Autowired
    private TableDefinitionService tableDefinitionService;
    @Autowired
    private EntVaModelDataInitService vaBaseDataInitService;
    @Autowired
    private IEntTableDefineProvider entTableDefineProvider;
    @Autowired
    private DataModelClient vaService;

    public BusinessResponseEntity<String> initTableDefine(@RequestBody String data) {
        if (StringUtils.isBlank((CharSequence)data)) {
            return BusinessResponseEntity.error((String)"\u6ca1\u6709\u63a5\u53d7\u5230\u5355\u636e\u5b9a\u4e49JSON\u3002");
        }
        ObjectMapper om = new ObjectMapper();
        try {
            DefinitionTableV[] nodes = (DefinitionTableV[])om.readValue(data, DefinitionTableV[].class);
            this.tableDefinitionService.initTableDefines(nodes);
            return BusinessResponseEntity.ok((Object)"\u8868\u5b9a\u4e49\u521d\u59cb\u6210\u529f");
        }
        catch (Exception e) {
            e.printStackTrace();
            return BusinessResponseEntity.error((Throwable)e);
        }
    }

    public BusinessResponseEntity<String> initVaTable(String tenant) {
        this.vaBaseDataInitService.init(true, tenant);
        return BusinessResponseEntity.ok();
    }

    public BusinessResponseEntity<String> initGcTable(String tableName) {
        try {
            this.tableDefinitionService.initTableDefineByTableName(tableName);
            return BusinessResponseEntity.ok();
        }
        catch (Exception e) {
            e.printStackTrace();
            return BusinessResponseEntity.error((Throwable)e);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public BusinessResponseEntity<String> checkTableFields(HttpServletResponse response, String tableName, String tenant) {
        ThreadContext.put((Object)"SECURITY_TENANT_KEY", (Object)this.getCurrTenant(tenant));
        ThreadContext.put((Object)"NONE_AUTH_KEY", (Object)"true");
        try {
            DataModelDTO param = new DataModelDTO();
            param.setName(tableName);
            PageVO vaTable = this.vaService.list(param);
            if (vaTable.getTotal() > 0) {
                response.setContentType("text/html; charset=UTF-8");
                response.setHeader("Charset", "UTF-8");
                response.setHeader("Content-Type", "application/force-download");
                response.setHeader("Content-disposition", "attachment; filename=" + URLEncoder.encode("data.csv", "UTF-8"));
                response.flushBuffer();
                PrintWriter outputStream = response.getWriter();
                for (DataModelDO table : vaTable.getRows()) {
                    JDialectUtil jdUtil;
                    List oldCols;
                    if (table.getColumns() == null) continue;
                    outputStream.println(",\u8868" + table.getName() + " \u68c0\u67e5\u7ed3\u679c:,\n");
                    List collect = table.getColumns().stream().map(v -> v.getColumnName()).collect(Collectors.toList());
                    EntTableDefine tableDefine = this.entTableDefineProvider.getTableDefine(table.getName());
                    List<EntFieldDefine> nvwaFields = tableDefine.getAllFields();
                    if (nvwaFields != null) {
                        outputStream.println(",,\u5973\u5a32\u6570\u636e\u5efa\u6a21\u5bf9\u6bd4VA\u5b9a\u4e49\u591a\u4f59\u5b57\u6bb5:,\n");
                        for (EntFieldDefine v2 : nvwaFields) {
                            if (collect.contains(v2.getName())) continue;
                            outputStream.println(",,," + v2.getName() + ",\n");
                        }
                    }
                    if ((oldCols = (jdUtil = JDialectUtil.getInstance()).getTableColumns(param.getTenantName(), table.getName())) == null) continue;
                    outputStream.println(",,\u7269\u7406\u8868\u5bf9\u6bd4VA\u5b9a\u4e49\u591a\u4f59\u5b57\u6bb5:,\n");
                    for (TableColumnDO m : oldCols) {
                        if (collect.contains(m.getColumnName())) continue;
                        outputStream.println(",,," + m.getColumnName() + ",\n");
                    }
                }
                outputStream.flush();
                outputStream.close();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            ThreadContext.remove((Object)"SECURITY_TENANT_KEY");
            ThreadContext.remove((Object)"NONE_AUTH_KEY");
        }
        return BusinessResponseEntity.error();
    }

    private String getCurrTenant(String tenant) {
        NpContext context;
        String tenantId = tenant;
        if (StringUtils.isEmpty((CharSequence)tenantId) && StringUtils.isEmpty((CharSequence)(tenantId = (context = NpContextHolder.getContext()).getTenant()))) {
            tenantId = "__default_tenant__";
        }
        return tenantId;
    }
}

