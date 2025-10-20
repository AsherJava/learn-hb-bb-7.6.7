/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.definition.impl.basic.base.define.DefinitionValueV
 *  com.jiuqi.gcreport.definition.impl.basic.init.table.va.intf.FEntVaBaseApplicationInitialization
 *  com.jiuqi.va.domain.basedata.BaseDataDTO
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDTO
 *  com.jiuqi.va.domain.basedata.BaseDataGroupDTO
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.datamodel.DataModelColumn
 *  com.jiuqi.va.domain.datamodel.DataModelDO
 *  com.jiuqi.va.domain.datamodel.DataModelDTO
 *  com.jiuqi.va.feign.client.BaseDataClient
 *  com.jiuqi.va.feign.client.BaseDataDefineClient
 *  com.jiuqi.va.feign.client.DataModelClient
 *  org.apache.commons.io.IOUtils
 */
package com.jiuqi.gcreport.basedata.impl.va.init;

import com.fasterxml.jackson.core.type.TypeReference;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.basedata.impl.va.impl.GcBaseDataFieldDefineDTO;
import com.jiuqi.gcreport.definition.impl.basic.base.define.DefinitionValueV;
import com.jiuqi.gcreport.definition.impl.basic.init.table.va.intf.FEntVaBaseApplicationInitialization;
import com.jiuqi.va.domain.basedata.BaseDataDTO;
import com.jiuqi.va.domain.basedata.BaseDataDefineDTO;
import com.jiuqi.va.domain.basedata.BaseDataGroupDTO;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.datamodel.DataModelColumn;
import com.jiuqi.va.domain.datamodel.DataModelDO;
import com.jiuqi.va.domain.datamodel.DataModelDTO;
import com.jiuqi.va.feign.client.BaseDataClient;
import com.jiuqi.va.feign.client.BaseDataDefineClient;
import com.jiuqi.va.feign.client.DataModelClient;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class GcBaseDataInitService
implements FEntVaBaseApplicationInitialization {
    @Value(value="classpath*:/config/vabasedata/table/*.td.json")
    private Resource[] tableResource;
    @Value(value="classpath*:/config/vabasedata/field/*.fd.json")
    private Resource[] fieldResources;
    @Value(value="classpath:/config/vabasedata/group/GC_BASEDATA.group.json")
    private Resource groupResource;
    @Value(value="classpath*:/config/vabasedata/initdata/*.data.json")
    private Resource[] dataResources;
    @Autowired
    private BaseDataDefineClient defineClient;
    @Autowired
    private DataModelClient dataModelClient;
    @Autowired
    private BaseDataClient bdClient;
    public static final String GROUPNAME = "GC_BASEDATA";
    private final Logger logger = LoggerFactory.getLogger(Logger.class);

    public void init(boolean force, String tenant) {
        this.initGroup();
        this.initBaseDataDefine();
        this.initBaseData();
    }

    private void initGroup() {
        try {
            String groupDefine = IOUtils.toString((InputStream)this.groupResource.getInputStream(), (Charset)StandardCharsets.UTF_8);
            List groups = (List)JsonUtils.readValue((String)groupDefine, (TypeReference)new TypeReference<List<BaseDataGroupDTO>>(){});
            groups.forEach(g -> this.defineClient.add(g));
        }
        catch (IOException e) {
            this.logger.error("\u521d\u59cb\u5316\u57fa\u7840\u6570\u636e\u5206\u7ec4\u5f02\u5e38", e);
        }
    }

    private void initBaseDataDefine() {
        List<BaseDataDefineDTO> tables = this.getBaseDataDefines();
        Map<String, String> tableFields = this.getTableFields();
        tables.forEach(table -> {
            R r1;
            R existsResult = this.defineClient.exist(table);
            if (!((Boolean)existsResult.get((Object)"exist")).booleanValue() && (r1 = this.defineClient.add(table)).getCode() == 1) {
                throw new RuntimeException(table.getName() + r1.getMsg());
            }
            this.updateDefine(table.getName(), (String)tableFields.get(table.getName()));
        });
    }

    private List<BaseDataDefineDTO> getBaseDataDefines() {
        ArrayList<BaseDataDefineDTO> allBaseDatas = new ArrayList<BaseDataDefineDTO>();
        for (Resource res : this.tableResource) {
            try {
                String value = IOUtils.toString((InputStream)res.getInputStream(), (Charset)StandardCharsets.UTF_8).trim();
                if (value.startsWith("[")) {
                    List tables = (List)JsonUtils.readValue((String)value, (TypeReference)new TypeReference<List<BaseDataDefineDTO>>(){});
                    allBaseDatas.addAll(tables);
                    continue;
                }
                if (value.startsWith("{")) {
                    BaseDataDefineDTO table = (BaseDataDefineDTO)JsonUtils.readValue((String)value, BaseDataDefineDTO.class);
                    allBaseDatas.add(table);
                    continue;
                }
                throw new RuntimeException(res.getFilename() + "\u6587\u4ef6\u683c\u5f0f\u6709\u8bef\uff0c\u8bf7\u68c0\u67e5");
            }
            catch (IOException e) {
                this.logger.error(res.getFilename() + "\u8bfb\u53d6\u5f02\u5e38", e);
            }
        }
        return allBaseDatas;
    }

    public Map<String, String> getTableFields() {
        HashMap<String, String> zbs = new HashMap<String, String>();
        for (Resource res : this.fieldResources) {
            try {
                String key = res.getFilename();
                assert (key != null);
                key = key.substring(0, key.indexOf("."));
                String value = IOUtils.toString((InputStream)res.getInputStream(), (Charset)StandardCharsets.UTF_8);
                zbs.put(key, value);
            }
            catch (IOException e) {
                this.logger.error(res.getFilename() + "\u8bfb\u53d6\u5f02\u5e38", e);
            }
        }
        return zbs;
    }

    private void updateDefine(String tableName, String solidFieldDefineJson) {
        if (StringUtils.isEmpty((String)solidFieldDefineJson)) {
            return;
        }
        GcBaseDataFieldDefineDTO solidFieldDefine = (GcBaseDataFieldDefineDTO)JsonUtils.readValue((String)solidFieldDefineJson, GcBaseDataFieldDefineDTO.class);
        if (CollectionUtils.isEmpty(solidFieldDefine.getColumns())) {
            return;
        }
        DataModelDTO dataModelFilter = new DataModelDTO();
        dataModelFilter.setName(tableName);
        DataModelDO oldDataModel = this.dataModelClient.get(dataModelFilter);
        Map oldColumns = oldDataModel.getColumns().stream().collect(Collectors.toMap(DataModelColumn::getColumnName, Function.identity()));
        List needAddColumns = solidFieldDefine.getColumns().stream().filter(column -> !oldColumns.containsKey(column.getColumnName().toUpperCase()) && !oldColumns.containsKey(column.getColumnName().toLowerCase())).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(needAddColumns)) {
            return;
        }
        oldDataModel.getColumns().addAll(needAddColumns);
        this.dataModelClient.push(oldDataModel);
    }

    private void initBaseData() {
        List<DefinitionValueV> data = this.getBdInitData();
        data.forEach(d -> {
            try {
                BaseDataDTO param = new BaseDataDTO();
                param.setTableName(d.getTableName());
                param.setCode((String)d.getFields().get("CODE"));
                R r = this.bdClient.exist(param);
                if (!((Boolean)r.get((Object)"exist")).booleanValue()) {
                    BaseDataDTO dto = new BaseDataDTO();
                    dto.setTableName(d.getTableName());
                    dto.setId(param.getId());
                    d.getFields().forEach((k, v) -> {
                        if (!k.equalsIgnoreCase("id")) {
                            dto.put(k.toLowerCase(), v);
                        }
                    });
                    R r1 = this.bdClient.add(dto);
                    if (r1.getCode() == 1) {
                        throw new RuntimeException(r1.getMsg());
                    }
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private List<DefinitionValueV> getBdInitData() {
        ArrayList<DefinitionValueV> initDatas = new ArrayList<DefinitionValueV>();
        for (Resource res : this.dataResources) {
            try {
                String value = IOUtils.toString((InputStream)res.getInputStream(), (String)"UTF-8").trim();
                List node = (List)JsonUtils.readValue((String)value, (TypeReference)new TypeReference<List<DefinitionValueV>>(){});
                for (DefinitionValueV tab : node) {
                    if (tab == null) continue;
                    initDatas.add(tab);
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        return initDatas;
    }

    public int getSortOrder() {
        return 1;
    }
}

