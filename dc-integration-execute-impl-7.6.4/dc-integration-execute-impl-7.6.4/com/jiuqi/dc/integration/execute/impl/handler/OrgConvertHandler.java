/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.DateUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.dc.datamapping.client.dto.DataRefAutoMatchDTO
 *  com.jiuqi.dc.datamapping.client.vo.DataRefAutoMatchVO
 *  com.jiuqi.dc.datamapping.impl.gather.impl.AutoMatchServiceGather
 *  com.jiuqi.dc.datamapping.impl.service.AutoMatchService
 *  com.jiuqi.dc.datamapping.impl.service.DataRefConfigureService
 *  com.jiuqi.dc.mappingscheme.client.dto.BaseDataMappingDefineDTO
 *  com.jiuqi.dc.mappingscheme.client.dto.DataSchemeDTO
 *  com.jiuqi.dc.mappingscheme.impl.common.RuleType
 *  com.jiuqi.dc.mappingscheme.impl.service.DataSchemeService
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.org.OrgDO
 *  com.jiuqi.va.domain.org.OrgDTO
 *  com.jiuqi.va.domain.org.OrgDataOption$AuthType
 *  com.jiuqi.va.feign.client.OrgDataClient
 */
package com.jiuqi.dc.integration.execute.impl.handler;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.DateUtils;
import com.jiuqi.common.base.util.StringUtils;
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
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.org.OrgDO;
import com.jiuqi.va.domain.org.OrgDTO;
import com.jiuqi.va.domain.org.OrgDataOption;
import com.jiuqi.va.feign.client.OrgDataClient;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.StringJoiner;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(value=0x7FFFFFFF)
public class OrgConvertHandler
extends AbstractBaseDataConvertHandler {
    @Autowired
    private BaseDataConvertDao dao;
    @Autowired
    private OrgDataClient orgDataClient;
    @Autowired
    private DataSchemeService dataSchemeService;
    @Autowired
    private DataRefConfigureService dataRefConfigureService;
    @Autowired
    private AutoMatchServiceGather autoMatchServiceGather;

    @Override
    public String getCode() {
        return "MD_ORG";
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
        logJoiner.add(DateUtils.format((Date)new Date(), (String)"yyyy-MM-dd HH:mm:ss.SSS") + " \u5f85\u8f6c\u6362\u7684\u5355\u4f4d\u57fa\u7840\u6570\u636e\u67e5\u8be2\u5f00\u59cb");
        List<Map<String, Object>> odsUnConvertDataList = this.dao.selectUnConvertData(define, dataSchemeDTO.getDataSourceCode());
        logJoiner.add(DateUtils.format((Date)new Date(), (String)"yyyy-MM-dd HH:mm:ss.SSS") + " \u5f85\u8f6c\u6362\u7684\u5355\u4f4d\u57fa\u7840\u6570\u636e\u67e5\u8be2\u7ed3\u675f,\u6709" + odsUnConvertDataList.size() + "\u884c\u6570\u636e\u5f85\u8f6c\u6362");
        ArrayList<OrgDTO> unConvertData = new ArrayList<OrgDTO>(Math.max(odsUnConvertDataList.size() / 2, 10));
        HashSet<String> codeRecord = new HashSet<String>(Math.max(odsUnConvertDataList.size() / 2, 10));
        OrgDTO data = null;
        for (Map<String, Object> odsUnConvertData : odsUnConvertDataList) {
            data = new OrgDTO();
            data.putAll(odsUnConvertData);
            if (codeRecord.contains(data.getCode())) continue;
            codeRecord.add(data.getCode());
            unConvertData.add(data);
        }
        if (CollectionUtils.isEmpty(unConvertData)) {
            return String.format("\u6570\u636e\u65b9\u6848\u3010%1$s\u3011\u57fa\u7840\u6570\u636e\u3010%2$s\u3011\u6ca1\u6709\u8981\u8f6c\u6362\u7684\u6570\u636e", dataSchemeCode, define.getCode());
        }
        ArrayList<OrgDTO> addOrgList = new ArrayList<OrgDTO>(Math.max(unConvertData.size(), 10));
        ArrayList<OrgDTO> updateOrgList = new ArrayList<OrgDTO>(Math.max(unConvertData.size(), 10));
        logJoiner.add(DateUtils.format((Date)new Date(), (String)"yyyy-MM-dd HH:mm:ss.SSS") + " \u5df2\u5b58\u5728\u7684\u5355\u4f4d\u57fa\u7840\u6570\u636e\u67e5\u8be2\u5f00\u59cb");
        Map<String, OrgDO> existBaseDataMap = this.queryExistOrgDO(define.getCode());
        logJoiner.add(DateUtils.format((Date)new Date(), (String)"yyyy-MM-dd HH:mm:ss.SSS") + " \u5df2\u5b58\u5728\u7684\u5355\u4f4d\u57fa\u7840\u6570\u636e\u67e5\u8be2\u7ed3\u675f,\u6709" + existBaseDataMap.size() + "\u884c\u6570\u636e");
        List fieldNameList = define.getItems().stream().filter(e -> !e.getFieldName().equals("ID")).map(e -> e.getFieldName().toLowerCase()).collect(Collectors.toList());
        logJoiner.add(DateUtils.format((Date)new Date(), (String)"yyyy-MM-dd HH:mm:ss.SSS") + " \u5355\u4f4d\u57fa\u7840\u6570\u636e\u6bd4\u5bf9\u5f00\u59cb");
        Iterator iterator = unConvertData.iterator();
        while (iterator.hasNext()) {
            Object baseData;
            baseData.setShortname(StringUtils.isEmpty((String)(baseData = (OrgDTO)iterator.next()).getShortname()) ? baseData.getCode() : baseData.getShortname());
            OrgDO baseDataDO = existBaseDataMap.get(baseData.getCode());
            if (Objects.nonNull(baseDataDO)) {
                boolean updateFlag = fieldNameList.stream().anyMatch(arg_0 -> OrgConvertHandler.lambda$doConvert$5((OrgDTO)baseData, baseDataDO, arg_0));
                if (!updateFlag) continue;
                baseData.setId(baseDataDO.getId());
                updateOrgList.add((OrgDTO)baseData);
                continue;
            }
            addOrgList.add((OrgDTO)baseData);
        }
        logJoiner.add(DateUtils.format((Date)new Date(), (String)"yyyy-MM-dd HH:mm:ss.SSS") + " \u57fa\u7840\u6570\u636e\u6bd4\u5bf9\u7ed3\u675f,\u65b0\u589e\u961f\u5217" + addOrgList.size() + "\u6761, \u4fee\u6539\u961f\u5217" + updateOrgList.size() + "\u6761");
        int addSuccessCount = 0;
        if (!CollectionUtils.isEmpty(addOrgList)) {
            logJoiner.add(DateUtils.format((Date)new Date(), (String)"yyyy-MM-dd HH:mm:ss.SSS") + " \u5355\u4f4d\u57fa\u7840\u6570\u636e\u65b0\u589e\u5f00\u59cb");
            for (OrgDTO orgDO : addOrgList) {
                R r = this.orgDataClient.add(orgDO);
                if (r.getCode() != 0) continue;
                ++addSuccessCount;
            }
            logJoiner.add(DateUtils.format((Date)new Date(), (String)"yyyy-MM-dd HH:mm:ss.SSS") + " \u5355\u4f4d\u57fa\u7840\u6570\u636e\u65b0\u589e\u7ed3\u675f");
        }
        int updateSuccessCount = 0;
        if (!CollectionUtils.isEmpty(updateOrgList)) {
            logJoiner.add(DateUtils.format((Date)new Date(), (String)"yyyy-MM-dd HH:mm:ss.SSS") + " \u5355\u4f4d\u57fa\u7840\u6570\u636e\u4fee\u6539\u5f00\u59cb");
            for (OrgDTO orgDO : updateOrgList) {
                R r = this.orgDataClient.update(orgDO);
                if (r.getCode() != 0) continue;
                ++updateSuccessCount;
            }
            logJoiner.add(DateUtils.format((Date)new Date(), (String)"yyyy-MM-dd HH:mm:ss.SSS") + " \u5355\u4f4d\u57fa\u7840\u6570\u636e\u4fee\u6539\u7ed3\u675f");
        }
        existBaseDataMap = null;
        return String.format("%5$s \r\n\u6570\u636e\u65b9\u6848\u3010%1$s\u3011\u57fa\u7840\u6570\u636e\u3010%2$s\u3011\u5176\u4e2d\u65b0\u589e\u6210\u529f\u3010%3$d\u3011\u6761\uff0c\u4fee\u6539\u6210\u529f\u3010%4$d\u3011\u6761", dataSchemeCode, define.getCode(), addSuccessCount, updateSuccessCount, logJoiner);
    }

    public Map<String, OrgDO> queryExistOrgDO(String tableName) {
        OrgDTO condi = new OrgDTO();
        condi.setPagination(Boolean.valueOf(false));
        condi.setAuthType(OrgDataOption.AuthType.NONE);
        List baseDatas = this.orgDataClient.list(condi).getRows();
        if (CollectionUtils.isEmpty((Collection)baseDatas)) {
            return new HashMap<String, OrgDO>();
        }
        return baseDatas.stream().collect(Collectors.toMap(OrgDO::getCode, item -> item, (k1, k2) -> k2));
    }

    private static /* synthetic */ boolean lambda$doConvert$5(OrgDTO baseData, OrgDO baseDataDO, String fieldName) {
        return !Objects.equals(Optional.ofNullable(baseData.get((Object)fieldName)).map(o -> o.toString().trim()).orElse(null), Optional.ofNullable(baseDataDO).map(p -> p.get((Object)fieldName)).map(o -> o.toString().trim()).orElse(null));
    }
}

