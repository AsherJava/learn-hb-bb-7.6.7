/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.DateUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.subject.impl.subject.dto.SubjectDTO
 *  com.jiuqi.common.subject.impl.subject.service.SubjectService
 *  com.jiuqi.dc.base.common.enums.SubjectClassEnum
 *  com.jiuqi.dc.datamapping.client.dto.DataRefAutoMatchDTO
 *  com.jiuqi.dc.datamapping.client.vo.DataRefAutoMatchVO
 *  com.jiuqi.dc.datamapping.impl.gather.impl.AutoMatchServiceGather
 *  com.jiuqi.dc.datamapping.impl.service.AutoMatchService
 *  com.jiuqi.dc.datamapping.impl.service.DataRefConfigureService
 *  com.jiuqi.dc.mappingscheme.client.dto.BaseDataMappingDefineDTO
 *  com.jiuqi.dc.mappingscheme.client.dto.DataSchemeDTO
 *  com.jiuqi.dc.mappingscheme.impl.common.RuleType
 *  com.jiuqi.dc.mappingscheme.impl.service.DataSchemeService
 *  com.jiuqi.va.domain.basedata.BaseDataDO
 *  com.jiuqi.va.domain.basedata.handle.BaseDataBatchOptDTO
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.feign.client.BaseDataClient
 */
package com.jiuqi.dc.integration.execute.impl.handler;

import com.jiuqi.common.base.util.DateUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.subject.impl.subject.dto.SubjectDTO;
import com.jiuqi.common.subject.impl.subject.service.SubjectService;
import com.jiuqi.dc.base.common.enums.SubjectClassEnum;
import com.jiuqi.dc.datamapping.client.dto.DataRefAutoMatchDTO;
import com.jiuqi.dc.datamapping.client.vo.DataRefAutoMatchVO;
import com.jiuqi.dc.datamapping.impl.gather.impl.AutoMatchServiceGather;
import com.jiuqi.dc.datamapping.impl.service.AutoMatchService;
import com.jiuqi.dc.datamapping.impl.service.DataRefConfigureService;
import com.jiuqi.dc.integration.execute.impl.dao.BaseDataConvertDao;
import com.jiuqi.dc.integration.execute.impl.handler.AbstractBaseDataConvertHandler;
import com.jiuqi.dc.mappingscheme.client.dto.BaseDataMappingDefineDTO;
import com.jiuqi.dc.mappingscheme.client.dto.DataSchemeDTO;
import com.jiuqi.dc.mappingscheme.impl.common.RuleType;
import com.jiuqi.dc.mappingscheme.impl.service.DataSchemeService;
import com.jiuqi.va.domain.basedata.BaseDataDO;
import com.jiuqi.va.domain.basedata.handle.BaseDataBatchOptDTO;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.feign.client.BaseDataClient;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.StringJoiner;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
@Order(value=0x7FFFFFFF)
public class SubjectConvertHandler
extends AbstractBaseDataConvertHandler {
    @Autowired
    private BaseDataConvertDao dao;
    @Autowired
    private BaseDataClient baseDataClient;
    @Autowired
    private SubjectService subjectService;
    @Autowired
    private DataSchemeService dataSchemeService;
    @Autowired
    private DataRefConfigureService dataRefConfigureService;
    @Autowired
    private AutoMatchServiceGather autoMatchServiceGather;

    @Override
    public String getCode() {
        return "MD_ACCTSUBJECT";
    }

    @Override
    public String doConvert(String dataSchemeCode, BaseDataMappingDefineDTO define) {
        StringJoiner logJoiner = new StringJoiner("\n");
        DataSchemeDTO dataSchemeDTO = this.dataSchemeService.getByCode(define.getDataSchemeCode());
        if (RuleType.isItemByItem((String)define.getRuleType()).booleanValue()) {
            DataRefAutoMatchDTO dto = new DataRefAutoMatchDTO();
            dto.setTableName(define.getCode());
            dto.setDataSchemeCode(define.getDataSchemeCode());
            DataRefAutoMatchVO dataRefAutoMatchVO = this.dataRefConfigureService.autoMatch(dto);
            int refNum = dataRefAutoMatchVO.getCountMap().values().stream().findAny().orElse(0);
            if (refNum != 0) {
                this.dataRefConfigureService.save(dataRefAutoMatchVO.getTempData());
            }
            AutoMatchService autoMatchService = this.autoMatchServiceGather.getServiceByCode(define.getAutoMatchDim());
            return String.format("\u3010%1$s\u3011\u6620\u5c04\u89c4\u5219\u4e3a\u9010\u6761\u914d\u7f6e\uff0c\u6309\u3010%3$s\u3011\u89c4\u5219\u81ea\u52a8\u6620\u5c04\u3010%2$d\u3011\u6761", define.getCode(), refNum, autoMatchService.getName());
        }
        String checkResult = this.requiredShowColumnsCheck(define);
        if (!StringUtils.isEmpty((String)checkResult)) {
            return String.format("\u8bf7\u5728\u6570\u636e\u6620\u5c04\u65b9\u6848\u7684\u201c%1$s\u201d\u6620\u5c04\u5b9a\u4e49\u4e2d\u914d\u7f6e\u201c%2$s\u201d\u7684\u6e90\u8868\u5b57\u6bb5", define.getName(), checkResult);
        }
        int count = this.dao.countUnConvertData(define, dataSchemeDTO.getDataSourceCode());
        if (count < 1) {
            return "\u6ca1\u6709\u8981\u8f6c\u6362\u7684\u6570\u636e";
        }
        logJoiner.add(DateUtils.format((Date)new Date(), (String)"yyyy-MM-dd HH:mm:ss.SSS") + " \u5f85\u8f6c\u6362\u7684\u79d1\u76ee\u57fa\u7840\u6570\u636e\u67e5\u8be2\u5f00\u59cb");
        List<Map<String, Object>> odsUnConvertDataList = this.dao.selectUnConvertData(define, dataSchemeDTO.getDataSourceCode());
        logJoiner.add(DateUtils.format((Date)new Date(), (String)"yyyy-MM-dd HH:mm:ss.SSS") + " \u5f85\u8f6c\u6362\u7684\u79d1\u76ee\u57fa\u7840\u6570\u636e\u67e5\u8be2\u7ed3\u675f,\u6709" + odsUnConvertDataList.size() + "\u884c\u6570\u636e\u5f85\u8f6c\u6362");
        ArrayList<SubjectDTO> unConvertData = new ArrayList<SubjectDTO>(Math.max(odsUnConvertDataList.size() / 2, 10));
        HashSet<String> codeRecord = new HashSet<String>(Math.max(odsUnConvertDataList.size() / 2, 10));
        SubjectDTO data = null;
        for (Map<String, Object> odsUnConvertData : odsUnConvertDataList) {
            data = new SubjectDTO();
            data.putAll(odsUnConvertData);
            data.setTableName(define.getCode());
            data.setShortname(StringUtils.isEmpty((String)data.getShortname()) ? data.getCode() : data.getShortname());
            data.setGeneralType(StringUtils.isEmpty((String)data.getGeneralType()) ? SubjectClassEnum.ASSET.getCode() : data.getGeneralType());
            data.setOrient(data.getOrient());
            data.setCurrency(StringUtils.isEmpty((String)data.getCurrency()) ? "ALL" : data.getCurrency());
            data.setAssType(StringUtils.isEmpty((String)data.getAssType()) ? "{}" : data.getAssType());
            if (codeRecord.contains(data.getCode())) continue;
            codeRecord.add(data.getCode());
            unConvertData.add(data);
        }
        if (CollectionUtils.isEmpty(unConvertData)) {
            return String.format("\u6570\u636e\u65b9\u6848\u3010%1$s\u3011\u57fa\u7840\u6570\u636e\u3010%2$s\u3011\u6ca1\u6709\u8981\u8f6c\u6362\u7684\u6570\u636e", dataSchemeCode, define.getCode());
        }
        BaseDataBatchOptDTO batchAddDto = new BaseDataBatchOptDTO();
        BaseDataBatchOptDTO batchUpdateDto = new BaseDataBatchOptDTO();
        batchAddDto.setTenantName("__default_tenant__");
        batchAddDto.setDataList(new ArrayList(Math.max(unConvertData.size(), 10)));
        batchUpdateDto.setTenantName("__default_tenant__");
        batchUpdateDto.setDataList(new ArrayList(Math.max(unConvertData.size(), 10)));
        logJoiner.add(DateUtils.format((Date)new Date(), (String)"yyyy-MM-dd HH:mm:ss.SSS") + " \u5df2\u5b58\u5728\u7684\u79d1\u76ee\u57fa\u7840\u6570\u636e\u67e5\u8be2\u5f00\u59cb");
        Map<String, BaseDataDO> existBaseDataMap = this.queryExistBaseData(define.getCode(), new ArrayList<String>());
        logJoiner.add(DateUtils.format((Date)new Date(), (String)"yyyy-MM-dd HH:mm:ss.SSS") + " \u5df2\u5b58\u5728\u7684\u79d1\u76ee\u57fa\u7840\u6570\u636e\u67e5\u8be2\u7ed3\u675f,\u6709" + existBaseDataMap.size() + "\u884c\u6570\u636e");
        List fieldNameList = define.getItems().stream().filter(e -> !e.getFieldName().equals("ID")).map(e -> e.getFieldName().toLowerCase()).collect(Collectors.toList());
        logJoiner.add(DateUtils.format((Date)new Date(), (String)"yyyy-MM-dd HH:mm:ss.SSS") + " \u79d1\u76ee\u57fa\u7840\u6570\u636e\u6bd4\u5bf9\u5f00\u59cb");
        for (SubjectDTO baseData : unConvertData) {
            BaseDataDO baseDataDO = existBaseDataMap.get(baseData.getCode());
            if (Objects.nonNull(baseDataDO)) {
                boolean updateFlag = fieldNameList.stream().anyMatch(fieldName -> !Objects.equals(Optional.ofNullable(baseData.get(fieldName)).map(o -> o.toString().trim()).orElse(null), Optional.ofNullable(baseDataDO).map(p -> p.get(fieldName)).map(o -> o.toString().trim()).orElse(null)));
                if (!updateFlag) continue;
                baseData.setId(baseDataDO.getId());
                batchUpdateDto.getDataList().add(baseData);
                continue;
            }
            batchAddDto.getDataList().add(baseData);
        }
        logJoiner.add(DateUtils.format((Date)new Date(), (String)"yyyy-MM-dd HH:mm:ss.SSS") + " \u79d1\u76ee\u57fa\u7840\u6570\u636e\u6bd4\u5bf9\u7ed3\u675f,\u65b0\u589e\u961f\u5217" + batchAddDto.getDataList().size() + "\u6761, \u4fee\u6539\u961f\u5217" + batchUpdateDto.getDataList().size() + "\u6761");
        String addMsg = "";
        if (!CollectionUtils.isEmpty(batchAddDto.getDataList())) {
            logJoiner.add(DateUtils.format((Date)new Date(), (String)"yyyy-MM-dd HH:mm:ss.SSS") + " \u79d1\u76ee\u57fa\u7840\u6570\u636e\u65b0\u589e\u5f00\u59cb");
            R r = this.baseDataClient.batchAdd(batchAddDto);
            logJoiner.add(DateUtils.format((Date)new Date(), (String)"yyyy-MM-dd HH:mm:ss.SSS") + " \u79d1\u76ee\u57fa\u7840\u6570\u636e\u65b0\u589e\u7ed3\u675f");
            addMsg = r.getMsg();
        }
        String updateMsg = "";
        if (!CollectionUtils.isEmpty(batchUpdateDto.getDataList())) {
            logJoiner.add(DateUtils.format((Date)new Date(), (String)"yyyy-MM-dd HH:mm:ss.SSS") + " \u79d1\u76ee\u57fa\u7840\u6570\u636e\u4fee\u6539\u5f00\u59cb");
            R r = this.baseDataClient.batchUpdate(batchUpdateDto);
            logJoiner.add(DateUtils.format((Date)new Date(), (String)"yyyy-MM-dd HH:mm:ss.SSS") + " \u79d1\u76ee\u57fa\u7840\u6570\u636e\u4fee\u6539\u7ed3\u675f");
            updateMsg = r.getMsg();
        }
        this.subjectService.syncCache();
        existBaseDataMap = null;
        return String.format("%7$s\r\n\u6570\u636e\u65b9\u6848\u3010%1$s\u3011\u57fa\u7840\u6570\u636e\u3010%2$s\u3011\u64cd\u4f5c\u5b8c\u6210\r\n\u65b0\u589e\u6267\u884c\u7ed3\u679c\uff1a%3$s \u65b0\u589e\u660e\u7ec6\uff1a%5$d\u884c\r\n\u65b0\u589e\u6267\u884c\u7ed3\u679c\uff1a%4$s \u4fee\u6539\u660e\u7ec6\uff1a%6$d\u884c", dataSchemeCode, define.getCode(), addMsg, updateMsg, batchAddDto.getDataList().size(), batchUpdateDto.getDataList().size(), logJoiner);
    }
}

