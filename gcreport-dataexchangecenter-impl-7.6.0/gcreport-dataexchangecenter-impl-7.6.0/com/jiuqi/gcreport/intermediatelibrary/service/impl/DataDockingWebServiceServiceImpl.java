/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.JsonNode
 *  com.fasterxml.jackson.databind.node.JsonNodeType
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.nr.dto.GcMidStoreTableDataDTO
 *  com.jiuqi.gcreport.nr.dto.GcMidstoreSyncDTO
 *  com.jiuqi.gcreport.nr.impl.service.GcMidstoreSyncService
 *  javax.jws.WebService
 */
package com.jiuqi.gcreport.intermediatelibrary.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.intermediatelibrary.service.DataDockingWebServiceService;
import com.jiuqi.gcreport.nr.dto.GcMidStoreTableDataDTO;
import com.jiuqi.gcreport.nr.dto.GcMidstoreSyncDTO;
import com.jiuqi.gcreport.nr.impl.service.GcMidstoreSyncService;
import java.util.ArrayList;
import java.util.Iterator;
import javax.jws.WebService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@WebService(targetNamespace="urn:jiuqi:document:gcreport:functions", endpointInterface="com.jiuqi.gcreport.intermediatelibrary.service.DataDockingWebServiceService")
public class DataDockingWebServiceServiceImpl
implements DataDockingWebServiceService {
    private final Logger LOGGER = LoggerFactory.getLogger(DataDockingWebServiceServiceImpl.class);
    @Autowired
    private GcMidstoreSyncService midstoreSyncService;

    @Override
    public String saveData(String jsonData) {
        GcMidstoreSyncDTO gcMidstoreSyncDTO = this.buildDataDockingParamDTOByJsonData(jsonData);
        this.midstoreSyncService.saveData2MidStore(gcMidstoreSyncDTO);
        return "\u4fdd\u5b58\u6210\u529f:" + jsonData;
    }

    private GcMidstoreSyncDTO buildDataDockingParamDTOByJsonData(String jsonData) {
        GcMidstoreSyncDTO gcMidstoreSyncDTO = new GcMidstoreSyncDTO();
        if (StringUtils.isEmpty((String)jsonData)) {
            return null;
        }
        JsonNode jsonNode = JsonUtils.readTree((String)jsonData);
        String taskCode = jsonNode.get("taskCode").asText();
        if (StringUtils.isEmpty((String)taskCode)) {
            this.LOGGER.error("\u4efb\u52a1\u6807\u8bc6\u3010taskCode\u3011\u4e3a\u7a7a");
            throw new BusinessRuntimeException("\u4efb\u52a1\u6807\u8bc6\u3010taskCode\u3011\u4e3a\u7a7a");
        }
        String midStoreSchemeCode = jsonNode.get("midStoreSchemeCode").asText();
        if (StringUtils.isEmpty((String)midStoreSchemeCode)) {
            this.LOGGER.error("\u4e2d\u95f4\u5e93\u65b9\u6848\u6807\u8bc6\u3010midStoreSchemeCode\u3011\u4e3a\u7a7a");
            throw new BusinessRuntimeException("\u4e2d\u95f4\u5e93\u65b9\u6848\u6807\u8bc6\u3010midStoreSchemeCode\u3011\u4e3a\u7a7a");
        }
        gcMidstoreSyncDTO.setTaskCode(taskCode);
        gcMidstoreSyncDTO.setMidStoreSchemeCode(midStoreSchemeCode);
        JsonNode dataNode = jsonNode.get("data");
        Iterator iterator = dataNode.iterator();
        ArrayList<GcMidStoreTableDataDTO> tableDataList = new ArrayList<GcMidStoreTableDataDTO>();
        while (iterator.hasNext()) {
            JsonNode tableDataDTONode = (JsonNode)iterator.next();
            Iterator tableDataIter = tableDataDTONode.iterator();
            GcMidStoreTableDataDTO gcMidStoreTableDataDTO = new GcMidStoreTableDataDTO();
            if (tableDataIter.hasNext()) {
                JsonNode zbNamesIter = (JsonNode)tableDataIter.next();
                ArrayList<String> zbNames = new ArrayList<String>();
                if (zbNamesIter.getNodeType() == JsonNodeType.ARRAY) {
                    Iterator zbNameIter = zbNamesIter.iterator();
                    while (zbNameIter.hasNext()) {
                        zbNames.add(((JsonNode)zbNameIter.next()).asText());
                    }
                }
                gcMidStoreTableDataDTO.setZbNames(zbNames);
                ArrayList zbValues = new ArrayList();
                while (tableDataIter.hasNext()) {
                    JsonNode zbValuesIter = (JsonNode)tableDataIter.next();
                    if (zbValuesIter.getNodeType() != JsonNodeType.ARRAY) continue;
                    Iterator zbValueIter = zbValuesIter.iterator();
                    ArrayList<Object> zbValue = new ArrayList<Object>();
                    while (zbValueIter.hasNext()) {
                        JsonNode zbVal = (JsonNode)zbValueIter.next();
                        if (zbVal.isTextual()) {
                            zbValue.add(zbVal.asText());
                            continue;
                        }
                        if (zbVal.isBoolean()) {
                            zbValue.add(zbVal.asBoolean());
                            continue;
                        }
                        if (zbVal.isInt()) {
                            zbValue.add(zbVal.asInt());
                            continue;
                        }
                        if (zbVal.isDouble()) {
                            zbValue.add(zbVal.asDouble());
                            continue;
                        }
                        if (zbVal.isLong()) {
                            zbValue.add(zbVal.asLong());
                            continue;
                        }
                        if (zbVal.isNull()) {
                            zbValue.add(null);
                            continue;
                        }
                        this.LOGGER.error("\u672a\u77e5\u6570\u636e\u7c7b\u578b\uff1a\u3010{}\u3011\uff0c\u503c\uff1a{}", (Object)zbVal.getNodeType(), (Object)zbVal);
                        throw new BusinessRuntimeException("\u672a\u77e5\u6570\u636e\u7c7b\u578b\uff1a\u3010" + zbVal.getNodeType() + "\u3011");
                    }
                    zbValues.add(zbValue);
                }
                gcMidStoreTableDataDTO.setZbValues(zbValues);
            }
            tableDataList.add(gcMidStoreTableDataDTO);
        }
        gcMidstoreSyncDTO.setTableDataList(tableDataList);
        return gcMidstoreSyncDTO;
    }
}

