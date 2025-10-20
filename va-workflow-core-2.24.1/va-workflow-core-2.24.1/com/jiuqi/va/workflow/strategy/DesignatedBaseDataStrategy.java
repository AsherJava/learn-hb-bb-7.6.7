/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.JsonNode
 *  com.fasterxml.jackson.databind.node.ArrayNode
 *  com.jiuqi.va.biz.intf.strategy.Strategy
 *  com.jiuqi.va.domain.basedata.BaseDataDO
 *  com.jiuqi.va.domain.basedata.BaseDataDTO
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.datamodel.DataModelColumn
 *  com.jiuqi.va.domain.datamodel.DataModelDO
 *  com.jiuqi.va.domain.datamodel.DataModelDTO
 *  com.jiuqi.va.feign.client.BaseDataClient
 *  com.jiuqi.va.feign.client.DataModelClient
 */
package com.jiuqi.va.workflow.strategy;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.jiuqi.va.biz.intf.strategy.Strategy;
import com.jiuqi.va.domain.basedata.BaseDataDO;
import com.jiuqi.va.domain.basedata.BaseDataDTO;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.datamodel.DataModelColumn;
import com.jiuqi.va.domain.datamodel.DataModelDO;
import com.jiuqi.va.domain.datamodel.DataModelDTO;
import com.jiuqi.va.feign.client.BaseDataClient;
import com.jiuqi.va.feign.client.DataModelClient;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Component
public class DesignatedBaseDataStrategy
implements Strategy {
    private static final Logger LOG = LoggerFactory.getLogger(DesignatedBaseDataStrategy.class);
    @Autowired
    private BaseDataClient baseDataClient;
    @Autowired
    private DataModelClient dataModelClient;

    public String getName() {
        return "designatedBaseData";
    }

    public String getTitle() {
        return "\u6307\u5b9a\u57fa\u7840\u6570\u636e\u53c2\u6570\u5173\u8054\u7528\u6237";
    }

    public String getOrder() {
        return "006";
    }

    public String getStrategyModule() {
        return "general";
    }

    public Set<String> execute(Object params) {
        LinkedHashSet<String> users = new LinkedHashSet<String>();
        Map paramMap = (Map)params;
        ArrayNode assignParamNode = (ArrayNode)paramMap.get("assignParam");
        if (assignParamNode != null && !assignParamNode.isEmpty()) {
            for (JsonNode jsonNode : assignParamNode) {
                JsonNode baseDataInfo = jsonNode.get("value");
                String processParamName = baseDataInfo.findValue("processParamName").asText();
                String baseDataName = baseDataInfo.findValue("baseDataName").asText();
                String columnName = baseDataInfo.findValue("columnName").asText();
                Map variables = (Map)paramMap.get("variables");
                String paramValue = (String)variables.get(processParamName);
                if (!StringUtils.hasText(paramValue)) {
                    LOG.error("\u6307\u5b9a\u57fa\u7840\u6570\u636e\u53c2\u6570\u5173\u8054\u7528\u6237\u53c2\u4e0e\u8005\u7b56\u7565\uff1a\u672a\u627e\u5230\u6d41\u7a0b\u53c2\u6570\u5bf9\u5e94\u7684\u4e1a\u52a1\u4e0e\u5de5\u4f5c\u6d41\u7ed1\u5b9a\u53c2\u6570\u7684\u503c");
                    return users;
                }
                DataModelDTO dataModelDTO = new DataModelDTO();
                dataModelDTO.setName(baseDataName);
                DataModelDO dataModelDO = this.dataModelClient.get(dataModelDTO);
                if (dataModelDO == null) {
                    LOG.error("\u6307\u5b9a\u57fa\u7840\u6570\u636e\u53c2\u6570\u5173\u8054\u7528\u6237\u53c2\u4e0e\u8005\u7b56\u7565\uff1a\u57fa\u7840\u6570\u636e\u4e0d\u5b58\u5728");
                    return users;
                }
                Optional<DataModelColumn> columnOptional = dataModelDO.getColumns().stream().filter(item -> Objects.equals(columnName, item.getColumnName())).findFirst();
                if (!columnOptional.isPresent()) continue;
                LinkedHashSet<String> objectCodeList = new LinkedHashSet<String>();
                if (paramValue.startsWith("{")) {
                    Object data = JSONUtil.parseMap((String)paramValue).get("data");
                    if (data instanceof List) {
                        for (Object o : (List)data) {
                            if (o instanceof String) {
                                objectCodeList.addAll((List)data);
                                continue;
                            }
                            if (!(o instanceof List)) continue;
                            objectCodeList.addAll((List)o);
                        }
                    }
                } else {
                    objectCodeList.add(paramValue);
                }
                BaseDataDTO baseDataDTO = new BaseDataDTO();
                baseDataDTO.setStopflag(Integer.valueOf(-1));
                baseDataDTO.setRecoveryflag(Integer.valueOf(-1));
                baseDataDTO.setTableName(baseDataName);
                for (String objectCode : objectCodeList) {
                    baseDataDTO.setObjectcode(objectCode);
                    List baseDataDOList = this.baseDataClient.list(baseDataDTO).getRows();
                    if (CollectionUtils.isEmpty(baseDataDOList)) {
                        LOG.error("\u6307\u5b9a\u57fa\u7840\u6570\u636e\u53c2\u6570\u5173\u8054\u7528\u6237\u53c2\u4e0e\u8005\u7b56\u7565\uff1a\u57fa\u7840\u6570\u636e{}\u6ca1\u6709obejctcode\u4e3a{}\u7684\u6570\u636e", (Object)baseDataName, (Object)objectCode);
                        continue;
                    }
                    Object baseDataFieldValue = ((BaseDataDO)baseDataDOList.get(0)).get((Object)columnName.toLowerCase());
                    String mapping = columnOptional.get().getMapping();
                    if ("MD_STAFF.OBJECTCODE".equals(mapping)) {
                        this.getUserByStaff(users, baseDataFieldValue);
                        continue;
                    }
                    if (!(baseDataFieldValue instanceof String)) continue;
                    users.add((String)baseDataFieldValue);
                }
            }
        }
        return users;
    }

    private void getUserByStaff(Set<String> users, Object baseDataFieldValue) {
        if (baseDataFieldValue instanceof String) {
            this.addUser((String)baseDataFieldValue, users);
        }
        if (baseDataFieldValue instanceof List) {
            List staffObjectCodeList = (List)baseDataFieldValue;
            for (String staffObjectCode : staffObjectCodeList) {
                this.addUser(staffObjectCode, users);
            }
        }
    }

    private void addUser(String staffObjectCode, Set<String> users) {
        if (!StringUtils.hasText(staffObjectCode)) {
            return;
        }
        BaseDataDTO baseDataDTO = new BaseDataDTO();
        baseDataDTO.setTableName("MD_STAFF");
        baseDataDTO.setObjectcode(staffObjectCode);
        PageVO list = this.baseDataClient.list(baseDataDTO);
        List rows = list.getRows();
        if (!CollectionUtils.isEmpty(rows)) {
            String userId = (String)((BaseDataDO)rows.get(0)).get((Object)"linkuser");
            users.add(userId);
        }
    }
}

