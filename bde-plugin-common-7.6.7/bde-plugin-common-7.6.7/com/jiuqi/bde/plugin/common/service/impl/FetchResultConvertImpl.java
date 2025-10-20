/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.execute.FetchData
 *  com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition
 *  com.jiuqi.bde.bizmodel.execute.util.ModelExecuteUtil
 *  com.jiuqi.bde.common.constant.MatchRuleEnum
 *  com.jiuqi.bde.common.intf.ConditionMatchRule
 *  com.jiuqi.dc.datamapping.client.dto.DataRefDTO
 *  com.jiuqi.dc.datamapping.client.dto.DataRefListDTO
 *  com.jiuqi.dc.datamapping.client.enums.DataRefFilterType
 *  com.jiuqi.dc.datamapping.client.vo.DataRefListVO
 *  com.jiuqi.dc.datamapping.impl.service.DataRefListConfigureService
 *  com.jiuqi.dc.mappingscheme.client.dto.BaseDataMappingDefineDTO
 *  com.jiuqi.dc.mappingscheme.client.dto.DataRefDefineListDTO
 *  com.jiuqi.dc.mappingscheme.impl.common.RuleType
 *  com.jiuqi.dc.mappingscheme.impl.service.BaseDataRefDefineService
 */
package com.jiuqi.bde.plugin.common.service.impl;

import com.jiuqi.bde.bizmodel.execute.FetchData;
import com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition;
import com.jiuqi.bde.bizmodel.execute.util.ModelExecuteUtil;
import com.jiuqi.bde.common.constant.MatchRuleEnum;
import com.jiuqi.bde.common.intf.ConditionMatchRule;
import com.jiuqi.bde.plugin.common.service.FetchResultConvert;
import com.jiuqi.dc.datamapping.client.dto.DataRefDTO;
import com.jiuqi.dc.datamapping.client.dto.DataRefListDTO;
import com.jiuqi.dc.datamapping.client.enums.DataRefFilterType;
import com.jiuqi.dc.datamapping.client.vo.DataRefListVO;
import com.jiuqi.dc.datamapping.impl.service.DataRefListConfigureService;
import com.jiuqi.dc.mappingscheme.client.dto.BaseDataMappingDefineDTO;
import com.jiuqi.dc.mappingscheme.client.dto.DataRefDefineListDTO;
import com.jiuqi.dc.mappingscheme.impl.common.RuleType;
import com.jiuqi.dc.mappingscheme.impl.service.BaseDataRefDefineService;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class FetchResultConvertImpl
implements FetchResultConvert {
    @Autowired
    private BaseDataRefDefineService baseDataRefDefineService;
    @Autowired
    @Qualifier(value="BDEDataRefConfigureService")
    private DataRefListConfigureService dataRefListConfigureService;

    @Override
    public void covertFetchData(FetchData fetchData, BalanceCondition condition) {
        DataRefDefineListDTO dataRefDefineListDTO = new DataRefDefineListDTO();
        dataRefDefineListDTO.setDataSchemeCode(condition.getOrgMapping().getDataSchemeCode());
        List baseDataMappingDefineDTOs = this.baseDataRefDefineService.list(dataRefDefineListDTO).stream().filter(item -> !"MD_ORG".equals(item.getCode())).collect(Collectors.toList());
        for (BaseDataMappingDefineDTO baseDataMapping : baseDataMappingDefineDTOs) {
            if (!RuleType.ITEM_BY_ITEM.getCode().equals(baseDataMapping.getRuleType())) continue;
            this.replaceMappingData(baseDataMapping, fetchData, condition.getOrgMapping().getDataSchemeCode());
        }
    }

    private void replaceMappingData(BaseDataMappingDefineDTO baseDataMapping, FetchData fetchData, String dataSchemeCode) {
        String fieldCode = "MD_ACCTSUBJECT".equals(baseDataMapping.getCode()) ? "SUBJECTCODE" : baseDataMapping.getCode();
        DataRefListDTO dataIsMappingRefList = new DataRefListDTO();
        dataIsMappingRefList.setDataSchemeCode(dataSchemeCode);
        dataIsMappingRefList.setFilterType(DataRefFilterType.HASREF.getCode());
        dataIsMappingRefList.setTableName(baseDataMapping.getCode());
        DataRefListVO dataRefIsMappingListVO = this.dataRefListConfigureService.list(dataIsMappingRefList);
        List dataIsMappingRefDTOs = dataRefIsMappingListVO.getPageVo().getRows();
        Map<String, String> dataIsMappingRefDTOMap = dataIsMappingRefDTOs.stream().collect(Collectors.toMap(DataRefDTO::getOdsId, DataRefDTO::getCode, (K1, K2) -> K1));
        Integer index = (Integer)fetchData.getColumns().get(fieldCode);
        if (index == null) {
            return;
        }
        for (Object[] data : fetchData.getRowDatas()) {
            if (dataIsMappingRefDTOMap.get(String.valueOf(data[index])) != null) {
                data[index.intValue()] = dataIsMappingRefDTOMap.get(String.valueOf(data[index]));
                continue;
            }
            data[index.intValue()] = "#";
        }
    }

    @Override
    public Boolean subjectIsItemMapping(String dataSchemeCode) {
        BaseDataMappingDefineDTO baseDataMappingDefineDTO = this.baseDataRefDefineService.findByCode(dataSchemeCode, "MD_ACCTSUBJECT");
        return baseDataMappingDefineDTO != null && RuleType.ITEM_BY_ITEM.getCode().equals(baseDataMappingDefineDTO.getRuleType());
    }

    @Override
    public ConditionMatchRule coverSubjectCode(String dataSchemeCode, ConditionMatchRule conditionMatchRule) {
        StringBuilder subjectCode = new StringBuilder();
        if (conditionMatchRule.getMatchRule().equals((Object)MatchRuleEnum.RANGE)) {
            subjectCode.append((String)conditionMatchRule.getSubjectCodes().get(0));
            subjectCode.append(":");
            subjectCode.append(((String)conditionMatchRule.getSubjectCodes().get(1)).substring(0, ((String)conditionMatchRule.getSubjectCodes().get(1)).length() - 3));
        } else {
            for (String code : conditionMatchRule.getSubjectCodes()) {
                subjectCode.append(code.substring(0, code.length() - 1)).append(",");
                subjectCode.substring(0, subjectCode.length() - 1);
            }
        }
        List subjectCodes = ModelExecuteUtil.getReductionData((String)subjectCode.toString(), (String)dataSchemeCode, (String)"MD_ACCTSUBJECT");
        ConditionMatchRule matchRule = new ConditionMatchRule(MatchRuleEnum.LIKE, subjectCodes.stream().map(item -> item + "%").collect(Collectors.toList()));
        matchRule.setTbName(conditionMatchRule.getTbName());
        return matchRule;
    }
}

