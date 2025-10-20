/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.JsonNode
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.fasterxml.jackson.databind.node.ObjectNode
 *  com.jiuqi.bi.util.OrderGenerator
 *  com.jiuqi.common.base.util.ConverterUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.gcreport.carryover.entity.CarryOverConfigEO
 *  com.jiuqi.gcreport.carryover.enums.CarryOverTypeEnum
 *  com.jiuqi.gcreport.consolidatedsystem.entity.ConsolidatedSystemEO
 *  com.jiuqi.gcreport.consolidatedsystem.entity.option.ConsolidatedOptionEO
 *  com.jiuqi.gcreport.consolidatedsystem.vo.option.ConsolidatedOptionVO
 *  com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 *  com.jiuqi.gcreport.offsetitem.init.carryover.vo.CarryOverOffsetConfigVO
 *  com.jiuqi.gcreport.offsetitem.init.carryover.vo.CarryOverOffsetRuleVO
 *  com.jiuqi.gcreport.offsetitem.init.carryover.vo.CarryOverOffsetSubjectMappingVO
 *  com.jiuqi.gcreport.offsetitem.init.carryover.vo.CarryOverOffsetSubjectVO
 *  com.jiuqi.np.sql.CustomClassExecutor
 *  jcifs.util.Base64
 *  org.json.JSONObject
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.gcreport.offsetitem.init.carryover.upgrade;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.gcreport.carryover.entity.CarryOverConfigEO;
import com.jiuqi.gcreport.carryover.enums.CarryOverTypeEnum;
import com.jiuqi.gcreport.consolidatedsystem.entity.ConsolidatedSystemEO;
import com.jiuqi.gcreport.consolidatedsystem.entity.option.ConsolidatedOptionEO;
import com.jiuqi.gcreport.consolidatedsystem.vo.option.ConsolidatedOptionVO;
import com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import com.jiuqi.gcreport.offsetitem.init.carryover.vo.CarryOverOffsetConfigVO;
import com.jiuqi.gcreport.offsetitem.init.carryover.vo.CarryOverOffsetRuleVO;
import com.jiuqi.gcreport.offsetitem.init.carryover.vo.CarryOverOffsetSubjectMappingVO;
import com.jiuqi.gcreport.offsetitem.init.carryover.vo.CarryOverOffsetSubjectVO;
import com.jiuqi.np.sql.CustomClassExecutor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import javax.sql.DataSource;
import jcifs.util.Base64;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

public class UpgradeConfigData
implements CustomClassExecutor {
    private static final Logger logger = LoggerFactory.getLogger(UpgradeConfigData.class);
    private JdbcTemplate jdbcTemplate;

    public void execute(DataSource dataSource) throws Exception {
        this.jdbcTemplate = (JdbcTemplate)SpringContextUtils.getBean(JdbcTemplate.class);
        EntNativeSqlDefaultDao consolidatedSystemDao = EntNativeSqlDefaultDao.newInstance((String)"GC_CONSSYSTEM", ConsolidatedSystemEO.class);
        List consolidatedSystemEOS = consolidatedSystemDao.selectEntity("SELECT * FROM GC_CONSSYSTEM", new Object[0]);
        Set systemIds = consolidatedSystemEOS.stream().map(DefaultTableEntity::getId).collect(Collectors.toSet());
        Map<String, Map<String, String>> allSystemIdSubjectCode2TitleMap = this.getSubjectCode2TitleMap(new ArrayList<String>(systemIds));
        ArrayList<CarryOverConfigEO> configEOS = new ArrayList<CarryOverConfigEO>(consolidatedSystemEOS.size());
        try {
            for (ConsolidatedSystemEO eo : consolidatedSystemEOS) {
                CarryOverOffsetConfigVO configVO = this.getConfigDataBySystemId(eo.getId(), allSystemIdSubjectCode2TitleMap);
                if (ObjectUtils.isEmpty(configVO)) continue;
                CarryOverConfigEO carryOverConfigEO = UpgradeConfigData.initEO(eo);
                String optionData = JsonUtils.writeValueAsString((Object)configVO);
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode rootNode = objectMapper.readTree(optionData);
                ((ObjectNode)rootNode).put("boundSystemId", eo.getId());
                String updatedJsonString = objectMapper.writeValueAsString((Object)rootNode);
                carryOverConfigEO.setOptionData(updatedJsonString);
                configEOS.add(carryOverConfigEO);
            }
            EntNativeSqlDefaultDao.newInstance((String)"GC_CARRYOVER_CONFIG", CarryOverConfigEO.class).addBatch(configEOS);
            logger.info("\u5e74\u7ed3\u6a21\u578b\u91cd\u6784\uff0c\u8fc1\u79fb\u5386\u53f2\u6570\u636e\u6210\u529f\u3002");
        }
        catch (Exception e) {
            logger.error("\u5e74\u7ed3\u6a21\u578b\u91cd\u6784\uff0c\u8fc1\u79fb\u5386\u53f2\u6570\u636e\u5f02\u5e38\uff1a" + e.getMessage(), e);
        }
    }

    private static CarryOverConfigEO initEO(ConsolidatedSystemEO systemEO) {
        CarryOverConfigEO eo = new CarryOverConfigEO();
        eo.setId(UUIDUtils.newUUIDStr());
        eo.setCreator("admin");
        eo.setCreateTime(new Date());
        eo.setOrdinal(new Double(OrderGenerator.newOrderID()));
        eo.setParentId(UUIDUtils.emptyUUIDStr());
        eo.setLeafFlag(Integer.valueOf(1));
        eo.setTitle(systemEO.getSystemName());
        eo.setTypeCode(CarryOverTypeEnum.OFFSET.getCode());
        return eo;
    }

    private CarryOverOffsetConfigVO getConfigDataBySystemId(String systemId, Map<String, Map<String, String>> allSystemIdSubjectCode2TitleMap) {
        ConsolidatedOptionVO optionData = this.getConsolidatedOptionBySystemId(systemId);
        if (optionData == null) {
            return null;
        }
        if (this.checkOptionData(optionData)) {
            CarryOverOffsetConfigVO carryOverOffsetConfigVO = this.convertOption2VO(systemId, optionData, allSystemIdSubjectCode2TitleMap);
            return carryOverOffsetConfigVO;
        }
        return null;
    }

    private boolean checkOptionData(ConsolidatedOptionVO optionData) {
        List carryOverSubjectCodes = optionData.getCarryOverSubjectCodes();
        List ruleIds = optionData.getCarryOverRuleIds();
        List carryOverSumRuleIds = optionData.getCarryOverSumRuleIds();
        Map carryOverSubjectCodeMapping = optionData.getCarryOverSubjectCodeMapping();
        Boolean carryOverConformRuleAdjustOffsets = optionData.getCarryOverConformRuleAdjustOffsets();
        String carryOverUndisProfitSubjectCode = optionData.getCarryOverUndisProfitSubjectCode();
        List carryOverSumColumns = optionData.getCarryOverSumColumns();
        return !CollectionUtils.isEmpty(carryOverSubjectCodes) || !CollectionUtils.isEmpty(ruleIds) || !CollectionUtils.isEmpty(carryOverSumRuleIds) || !CollectionUtils.isEmpty(carryOverSumColumns) || !StringUtils.isEmpty((String)carryOverUndisProfitSubjectCode) || !ObjectUtils.isEmpty(carryOverSubjectCodeMapping) || carryOverConformRuleAdjustOffsets != false;
    }

    private ConsolidatedOptionVO getConsolidatedOptionBySystemId(String systemId) {
        EntNativeSqlDefaultDao consolidatedOptionDao = EntNativeSqlDefaultDao.newInstance((String)"GC_CONSOPTION", ConsolidatedOptionEO.class);
        List optionList = consolidatedOptionDao.selectEntity("select * from GC_CONSOPTION where SYSTEMID = ? ", new Object[]{systemId});
        if (optionList.isEmpty()) {
            return null;
        }
        ConsolidatedOptionEO optionEO = (ConsolidatedOptionEO)optionList.get(0);
        String jsonString = new String(Base64.decode((String)optionEO.getData()));
        ConsolidatedOptionVO optionVO = null;
        try {
            optionVO = (ConsolidatedOptionVO)JsonUtils.readValue((String)jsonString, ConsolidatedOptionVO.class);
        }
        catch (Exception e) {
            JSONObject jsonObject = new JSONObject(jsonString);
            if (jsonObject.has("carryOverSubjectCodeMapping") && jsonObject.get("carryOverSubjectCodeMapping") != null) {
                jsonObject.remove("carryOverSubjectCodeMapping");
                optionVO = (ConsolidatedOptionVO)JsonUtils.readValue((String)jsonObject.toString(), ConsolidatedOptionVO.class);
            }
            logger.error(e.getMessage(), e);
        }
        return optionVO;
    }

    private CarryOverOffsetConfigVO convertOption2VO(String systemId, ConsolidatedOptionVO optionData, Map<String, Map<String, String>> allSystemIdSubjectCode2TitleMap) {
        Map<String, String> ruleId2TitleMap = this.getRuleId2TitleMap(systemId);
        Map<String, String> subjectCode2TitleMap = allSystemIdSubjectCode2TitleMap.get(systemId);
        CarryOverOffsetConfigVO vo = new CarryOverOffsetConfigVO();
        vo.setCarryOverRuleVos(this.initRuleVO(optionData.getCarryOverRuleIds(), ruleId2TitleMap));
        vo.setCarryOverConformRuleAdjustOffsets(optionData.getCarryOverConformRuleAdjustOffsets());
        vo.setCarryOverSubjectVos(this.initSubjectVO(optionData.getCarryOverSubjectCodes(), subjectCode2TitleMap));
        if (!StringUtils.isEmpty((String)optionData.getCarryOverUndisProfitSubjectCode())) {
            vo.setUndistributedProfitSubjectVo(this.initSubjectVO(Arrays.asList(optionData.getCarryOverUndisProfitSubjectCode()), subjectCode2TitleMap).get(0));
        }
        vo.setSubjectMappingSet(this.initSubjectMapping(systemId, optionData.getCarryOverSubjectCodeMapping(), subjectCode2TitleMap, allSystemIdSubjectCode2TitleMap));
        vo.setCarryOverSumColumns(optionData.getCarryOverSumColumns());
        vo.setCarryOverSumRuleIds(this.initRuleVO(optionData.getCarryOverSumRuleIds(), ruleId2TitleMap));
        return vo;
    }

    private Map<String, List<CarryOverOffsetSubjectMappingVO>> initSubjectMapping(String systemId, Map<String, Map<String, String>> mapping, Map<String, String> subjectCode2TitleMap, Map<String, Map<String, String>> allSystemIdSubjectCode2TitleMap) {
        HashMap<String, List<CarryOverOffsetSubjectMappingVO>> subjectMapping = new HashMap<String, List<CarryOverOffsetSubjectMappingVO>>();
        for (String srcSubjectCode : mapping.keySet()) {
            if (!subjectCode2TitleMap.containsKey(srcSubjectCode)) continue;
            Map<String, String> map = mapping.get(srcSubjectCode);
            for (String destSystemId : map.keySet()) {
                String destSubjectCode = map.get(destSystemId);
                CarryOverOffsetSubjectVO subjectVO = this.getSubjectVOByCodeAndSystemId(destSubjectCode, destSystemId, allSystemIdSubjectCode2TitleMap);
                if (ObjectUtils.isEmpty(subjectVO)) continue;
                CarryOverOffsetSubjectMappingVO vo = new CarryOverOffsetSubjectMappingVO();
                vo.setSrcSubjectCode(srcSubjectCode);
                vo.setSrcSubjectTitle(subjectCode2TitleMap.get(srcSubjectCode));
                vo.setDestSubjectCode(destSubjectCode);
                vo.setDestSubjectTitle(subjectVO);
                List subjectMappingVOs = subjectMapping.containsKey(destSystemId) ? (List)subjectMapping.get(destSystemId) : new ArrayList();
                subjectMappingVOs.add(vo);
                subjectMapping.put(destSystemId, subjectMappingVOs);
            }
        }
        return subjectMapping;
    }

    private CarryOverOffsetSubjectVO getSubjectVOByCodeAndSystemId(String subjectCode, String systemId, Map<String, Map<String, String>> allSystemIdSubjectCode2TitleMap) {
        if (!allSystemIdSubjectCode2TitleMap.containsKey(systemId)) {
            return null;
        }
        Map<String, String> map = allSystemIdSubjectCode2TitleMap.get(systemId);
        if (!map.containsKey(subjectCode)) {
            return null;
        }
        return this.initSubjectVO(subjectCode, map);
    }

    private List<CarryOverOffsetSubjectVO> initSubjectVO(List<String> subjectCodes, Map<String, String> subjectCode2TitleMap) {
        ArrayList<CarryOverOffsetSubjectVO> vos = new ArrayList<CarryOverOffsetSubjectVO>();
        for (String code : subjectCodes) {
            if (!subjectCode2TitleMap.containsKey(code)) continue;
            CarryOverOffsetSubjectVO vo = this.initSubjectVO(code, subjectCode2TitleMap);
            vos.add(vo);
        }
        return vos;
    }

    private CarryOverOffsetSubjectVO initSubjectVO(String code, Map<String, String> subjectCode2TitleMap) {
        CarryOverOffsetSubjectVO vo = new CarryOverOffsetSubjectVO();
        vo.setCode(code);
        vo.setTitle(subjectCode2TitleMap.get(code));
        return vo;
    }

    private List<CarryOverOffsetRuleVO> initRuleVO(List<String> ruleIds, Map<String, String> ruleId2TitleMap) {
        ArrayList<CarryOverOffsetRuleVO> ruleVOs = new ArrayList<CarryOverOffsetRuleVO>();
        for (String ruleId : ruleIds) {
            if (!ruleId2TitleMap.containsKey(ruleId)) continue;
            CarryOverOffsetRuleVO ruleVO = new CarryOverOffsetRuleVO();
            ruleVO.setId(ruleId);
            ruleVO.setTitle(ruleId2TitleMap.get(ruleId));
            ruleVOs.add(ruleVO);
        }
        return ruleVOs;
    }

    private Map<String, String> getRuleId2TitleMap(String systemId) {
        HashMap<String, String> ruleId2TitleMap = new HashMap<String, String>();
        List<Map<String, Object>> ruleList = this.getRuleListBySystem(systemId);
        ruleList.stream().forEach(rule -> ruleId2TitleMap.put(ConverterUtils.getAsString(rule.get("ID")), ConverterUtils.getAsString(rule.get("TITLE"))));
        return ruleId2TitleMap;
    }

    private Map<String, Map<String, String>> getSubjectCode2TitleMap(List<String> systemIds) {
        HashMap<String, Map<String, String>> subjectCode2TitleMap = new HashMap<String, Map<String, String>>();
        systemIds.forEach(systemId -> {
            List<Map<String, Object>> allSubject = this.getAllSubjectBySystemId((String)systemId);
            HashMap code2TitleMap = new HashMap();
            allSubject.stream().forEach(rule -> code2TitleMap.put(ConverterUtils.getAsString(rule.get("CODE")), ConverterUtils.getAsString(rule.get("NAME"))));
            if (!ObjectUtils.isEmpty(code2TitleMap)) {
                subjectCode2TitleMap.put((String)systemId, code2TitleMap);
            }
        });
        return subjectCode2TitleMap;
    }

    private List<Map<String, Object>> getRuleListBySystem(String reportSystemId) {
        String sql = "  select id, title from GC_UNIONRULE  scheme \n  where scheme.reportsystem = ? \n";
        return this.jdbcTemplate.queryForList(sql, new Object[]{reportSystemId});
    }

    private List<Map<String, Object>> getAllSubjectBySystemId(String systemId) {
        String sql = "  select code, name from MD_GCSUBJECT  e \n  where e.SYSTEMID = ? \n";
        return this.jdbcTemplate.queryForList(sql, new Object[]{systemId});
    }
}

