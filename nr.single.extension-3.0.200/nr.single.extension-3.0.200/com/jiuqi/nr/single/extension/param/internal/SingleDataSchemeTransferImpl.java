/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.bi.transfer.engine.ex.TransferException
 *  com.jiuqi.bi.transfer.engine.intf.IExportContext
 *  com.jiuqi.bi.transfer.engine.intf.IImportContext
 *  com.jiuqi.nr.param.transfer.datascheme.spi.DataSchemeTransfer
 *  nr.single.para.compare.definition.CompareMapFieldDTO
 *  nr.single.para.compare.definition.ISingleCompareMapFieldService
 *  nr.single.para.compare.definition.exception.SingleCompareException
 */
package com.jiuqi.nr.single.extension.param.internal;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.bi.transfer.engine.ex.TransferException;
import com.jiuqi.bi.transfer.engine.intf.IExportContext;
import com.jiuqi.bi.transfer.engine.intf.IImportContext;
import com.jiuqi.nr.param.transfer.datascheme.spi.DataSchemeTransfer;
import com.jiuqi.nr.single.extension.entitycheck.internal.SingleExportEntityCheckServiceImpl;
import com.jiuqi.nr.single.extension.param.internal.CompareMapFieldData;
import com.jiuqi.nr.single.extension.param.internal.SingleCompareParamData;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import nr.single.para.compare.definition.CompareMapFieldDTO;
import nr.single.para.compare.definition.ISingleCompareMapFieldService;
import nr.single.para.compare.definition.exception.SingleCompareException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SingleDataSchemeTransferImpl
implements DataSchemeTransfer {
    private static final Logger logger = LoggerFactory.getLogger(SingleExportEntityCheckServiceImpl.class);
    @Autowired
    private ISingleCompareMapFieldService mapFieldService;

    public String getId() {
        return "SingleCompareParamData";
    }

    public byte[] exportTaskData(IExportContext iExportContext, String dataSchemeKey) throws TransferException {
        byte[] contentByte = null;
        CompareMapFieldDTO param = new CompareMapFieldDTO();
        param.setDataSchemeKey(dataSchemeKey);
        List sDataList = this.mapFieldService.list(param);
        if (sDataList != null && !sDataList.isEmpty()) {
            try {
                ArrayList<CompareMapFieldData> ndataList = new ArrayList<CompareMapFieldData>();
                for (CompareMapFieldDTO sfield : sDataList) {
                    CompareMapFieldData nData = new CompareMapFieldData();
                    nData.setKey(sfield.getKey());
                    nData.setFieldKey(sfield.getFieldKey());
                    nData.setMatchTitle(sfield.getMatchTitle());
                    nData.setDataSchemeKey(sfield.getDataSchemeKey());
                    ndataList.add(nData);
                }
                SingleCompareParamData data = new SingleCompareParamData();
                data.setMapFields(ndataList);
                ObjectMapper objectMapper = new ObjectMapper();
                contentByte = objectMapper.writeValueAsBytes((Object)data);
            }
            catch (JsonProcessingException e) {
                logger.error("\u5bfc\u51fa\u65f6\u8f6c\u6362\u6307\u6807\u5339\u914d\u6807\u9898\u51fa\u9519\uff1a" + e.getMessage(), e);
                contentByte = null;
            }
        }
        return contentByte;
    }

    public void importTaskData(IImportContext context, String dataSchemeKey, byte[] data) throws TransferException {
        if (data == null) {
            return;
        }
        List<CompareMapFieldData> sDataList = null;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            SingleCompareParamData paraData = (SingleCompareParamData)objectMapper.readValue(data, SingleCompareParamData.class);
            sDataList = paraData.getMapFields();
        }
        catch (IOException e) {
            sDataList = null;
            logger.error("\u5bfc\u5165\u65f6\u8f6c\u6362\u6307\u6807\u5339\u914d\u6807\u9898\u51fa\u9519\uff1a" + e.getMessage(), e);
        }
        ArrayList<CompareMapFieldDTO> newDataList = new ArrayList<CompareMapFieldDTO>();
        if (sDataList != null && !sDataList.isEmpty()) {
            CompareMapFieldDTO param = new CompareMapFieldDTO();
            param.setDataSchemeKey(dataSchemeKey);
            List oldDataList = this.mapFieldService.list(param);
            HashMap<String, CompareMapFieldDTO> oldMapFields = new HashMap<String, CompareMapFieldDTO>();
            if (oldDataList != null && !oldDataList.isEmpty()) {
                for (CompareMapFieldDTO field : oldDataList) {
                    oldMapFields.put(field.getFieldKey(), field);
                }
            }
            for (CompareMapFieldData sfield : sDataList) {
                CompareMapFieldDTO nData = new CompareMapFieldDTO();
                if (oldMapFields.containsKey(sfield.getFieldKey())) continue;
                nData.setKey(UUID.randomUUID().toString());
                nData.setFieldKey(sfield.getFieldKey());
                nData.setMatchTitle(sfield.getMatchTitle());
                nData.setDataSchemeKey(sfield.getDataSchemeKey());
                newDataList.add(nData);
            }
        }
        try {
            if (!newDataList.isEmpty()) {
                this.mapFieldService.batchAdd(newDataList);
            }
        }
        catch (SingleCompareException e) {
            logger.error("\u5bfc\u5165\u65f6\u4fdd\u5b58\u6307\u6807\u5339\u914d\u6807\u9898\u51fa\u9519\uff1a" + e.getMessage(), e);
        }
    }
}

