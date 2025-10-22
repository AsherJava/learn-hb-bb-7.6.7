/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.google.common.collect.Maps
 *  com.jiuqi.bde.common.dto.FixedAdaptSettingVO
 *  com.jiuqi.bde.common.dto.FixedFetchSourceRowSettingVO
 *  com.jiuqi.bde.common.dto.FloatQueryFieldVO
 *  com.jiuqi.bde.common.dto.QueryConfigInfo
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.bde.fetchsetting.impl.dao.impl.FetchFloatSettingDaoImpl
 *  com.jiuqi.gcreport.bde.fetchsetting.impl.dao.impl.FetchFloatSettingDesDaoImpl
 *  com.jiuqi.gcreport.bde.fetchsetting.impl.dao.impl.FetchSettingDaoImpl
 *  com.jiuqi.gcreport.bde.fetchsetting.impl.dao.impl.FetchSettingDesDaoImpl
 *  com.jiuqi.gcreport.bde.fetchsetting.impl.entity.FetchFloatSettingDesEO
 *  com.jiuqi.gcreport.bde.fetchsetting.impl.entity.FetchFloatSettingEO
 *  com.jiuqi.gcreport.bde.fetchsetting.impl.entity.FetchSettingDesEO
 *  com.jiuqi.gcreport.bde.fetchsetting.impl.entity.FetchSettingEO
 *  com.jiuqi.np.sql.CustomClassExecutor
 *  com.jiuqi.va.domain.common.JSONUtil
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.bde.fetchsetting.impl.callback;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.collect.Maps;
import com.jiuqi.bde.common.dto.FixedAdaptSettingVO;
import com.jiuqi.bde.common.dto.FixedFetchSourceRowSettingVO;
import com.jiuqi.bde.common.dto.FloatQueryFieldVO;
import com.jiuqi.bde.common.dto.QueryConfigInfo;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.bde.fetchsetting.impl.dao.impl.FetchFloatSettingDaoImpl;
import com.jiuqi.gcreport.bde.fetchsetting.impl.dao.impl.FetchFloatSettingDesDaoImpl;
import com.jiuqi.gcreport.bde.fetchsetting.impl.dao.impl.FetchSettingDaoImpl;
import com.jiuqi.gcreport.bde.fetchsetting.impl.dao.impl.FetchSettingDesDaoImpl;
import com.jiuqi.gcreport.bde.fetchsetting.impl.entity.FetchFloatSettingDesEO;
import com.jiuqi.gcreport.bde.fetchsetting.impl.entity.FetchFloatSettingEO;
import com.jiuqi.gcreport.bde.fetchsetting.impl.entity.FetchSettingDesEO;
import com.jiuqi.gcreport.bde.fetchsetting.impl.entity.FetchSettingEO;
import com.jiuqi.np.sql.CustomClassExecutor;
import com.jiuqi.va.domain.common.JSONUtil;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

public class FloatTitleToNameUpdate
implements CustomClassExecutor {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private static final String FIELD_PREFIX_STR = "${";
    private static final String FORMULA_PREFIX_STR = "FLOAT[";

    @Transactional(rollbackFor={Exception.class})
    public void execute(DataSource dataSource) {
        this.logger.info("\u5f00\u59cb\u4fee\u590dBDE\u8bbe\u8ba1\u671f\u6570\u636e");
        this.handleDesData();
        this.logger.info("\u8bbe\u8ba1\u671f\u6570\u636e\u4fee\u590d\u5b8c\u6210");
        this.logger.info("\u5f00\u59cb\u4fee\u590d\u8fd0\u884c\u671f\u6570\u636e");
        this.handleRunTimeData();
        this.logger.info("\u8fd0\u884c\u671f\u6570\u636e\u4fee\u590d\u5b8c\u6210");
    }

    private void handleRunTimeData() {
        this.logger.info("\u5f00\u59cb\u4fee\u590dBDE\u8bbe\u8ba1\u671f\u6d6e\u52a8\u8868\u6570\u636e");
        FetchFloatSettingDaoImpl floatDao = (FetchFloatSettingDaoImpl)SpringContextUtils.getBean(FetchFloatSettingDaoImpl.class);
        HashMap<String, Map<String, String>> fetchSchemeIdAndRegionIdToFieldTitleToNameMap = new HashMap<String, Map<String, String>>(128);
        List fetchFloatSettingDesList = floatDao.loadAll();
        HashMap desIdToQueryConfigInfoMap = Maps.newHashMapWithExpectedSize((int)fetchFloatSettingDesList.size());
        for (FetchFloatSettingEO eo : fetchFloatSettingDesList) {
            this.repairFloatData(fetchSchemeIdAndRegionIdToFieldTitleToNameMap, desIdToQueryConfigInfoMap, eo.getId(), eo.getQueryConfigInfo(), eo.getFetchSchemeId(), eo.getRegionId());
        }
        if (desIdToQueryConfigInfoMap.size() > 0) {
            floatDao.batchUpdateQueryConfigInfoById((Map)desIdToQueryConfigInfoMap);
        }
        this.logger.info("BDE\u8fd0\u884c\u671f\u6d6e\u52a8\u8868\u6570\u636e\u4fee\u590d\u5b8c\u6210");
        this.logger.info("\u5f00\u59cb\u4fee\u590dBDE\u8fd0\u884c\u671f\u56fa\u5b9a\u8868\u6570\u636e");
        FetchSettingDaoImpl runTimeDao = (FetchSettingDaoImpl)SpringContextUtils.getBean(FetchSettingDaoImpl.class);
        List fetchSettingList = runTimeDao.listAllFloatFetchSetting();
        for (FetchSettingEO fetchSetting : fetchSettingList) {
            if (!fetchSchemeIdAndRegionIdToFieldTitleToNameMap.containsKey(fetchSetting.getFetchSchemeId() + "|" + fetchSetting.getRegionId())) continue;
            Map fieldTitleToNameMap = (Map)fetchSchemeIdAndRegionIdToFieldTitleToNameMap.get(fetchSetting.getFetchSchemeId() + "|" + fetchSetting.getRegionId());
            String fixedSettingDataStr = fetchSetting.getFixedSettingData();
            List adaptSettingList = (List)JsonUtils.readValue((String)fixedSettingDataStr, (TypeReference)new TypeReference<List<FixedAdaptSettingVO>>(){});
            for (FixedAdaptSettingVO adaptSettingVO : adaptSettingList) {
                adaptSettingVO.setLogicFormula(this.replaceTitleToName(adaptSettingVO.getLogicFormula(), FORMULA_PREFIX_STR, fieldTitleToNameMap));
                Map bizModelFormula = adaptSettingVO.getBizModelFormula();
                for (Map.Entry bizModelFormulaEntry : bizModelFormula.entrySet()) {
                    for (FixedFetchSourceRowSettingVO fetchSourceRowSettingVO : (List)bizModelFormulaEntry.getValue()) {
                        fetchSourceRowSettingVO.setSubjectCode(this.replaceTitleToName(fetchSourceRowSettingVO.getSubjectCode(), FIELD_PREFIX_STR, fieldTitleToNameMap));
                        fetchSourceRowSettingVO.setExcludeSubjectCode(this.replaceTitleToName(fetchSourceRowSettingVO.getExcludeSubjectCode(), FIELD_PREFIX_STR, fieldTitleToNameMap));
                        fetchSourceRowSettingVO.setCashCode(this.replaceTitleToName(fetchSourceRowSettingVO.getCashCode(), FIELD_PREFIX_STR, fieldTitleToNameMap));
                        fetchSourceRowSettingVO.setReclassSubjCode(this.replaceTitleToName(fetchSourceRowSettingVO.getReclassSubjCode(), FIELD_PREFIX_STR, fieldTitleToNameMap));
                        fetchSourceRowSettingVO.setReclassSrcSubjCode(this.replaceTitleToName(fetchSourceRowSettingVO.getReclassSrcSubjCode(), FIELD_PREFIX_STR, fieldTitleToNameMap));
                        fetchSourceRowSettingVO.setDimensionSetting(this.replaceTitleToName(fetchSourceRowSettingVO.getDimensionSetting(), FIELD_PREFIX_STR, fieldTitleToNameMap));
                    }
                }
            }
            fetchSetting.setFixedSettingData(JSONUtil.toJSONString((Object)adaptSettingList));
        }
        if (!CollectionUtils.isEmpty((Collection)fetchSettingList)) {
            runTimeDao.updateBatch(fetchSettingList);
        }
        this.logger.info("BDE\u8fd0\u884c\u671f\u56fa\u5b9a\u8868\u6570\u636e\u4fee\u590d\u5b8c\u6210");
    }

    private void handleDesData() {
        this.logger.info("\u5f00\u59cb\u4fee\u590dBDE\u8bbe\u8ba1\u671f\u6d6e\u52a8\u8868\u6570\u636e");
        FetchFloatSettingDesDaoImpl floatDesDao = (FetchFloatSettingDesDaoImpl)SpringContextUtils.getBean(FetchFloatSettingDesDaoImpl.class);
        HashMap<String, Map<String, String>> fetchSchemeIdAndRegionIdToFieldTitleToNameMap = new HashMap<String, Map<String, String>>(128);
        List fetchFloatSettingDesList = floatDesDao.loadAll();
        HashMap desIdToQueryConfigInfoMap = Maps.newHashMapWithExpectedSize((int)fetchFloatSettingDesList.size());
        for (FetchFloatSettingDesEO eo : fetchFloatSettingDesList) {
            this.repairFloatData(fetchSchemeIdAndRegionIdToFieldTitleToNameMap, desIdToQueryConfigInfoMap, eo.getId(), eo.getQueryConfigInfo(), eo.getFetchSchemeId(), eo.getRegionId());
        }
        if (desIdToQueryConfigInfoMap.size() > 0) {
            floatDesDao.batchUpdateQueryConfigInfoById((Map)desIdToQueryConfigInfoMap);
        }
        this.logger.info("BDE\u8bbe\u8ba1\u671f\u6d6e\u52a8\u8868\u6570\u636e\u4fee\u590d\u5b8c\u6210");
        this.logger.info("\u5f00\u59cb\u4fee\u590dBDE\u8bbe\u8ba1\u671f\u56fa\u5b9a\u8868\u6570\u636e");
        FetchSettingDesDaoImpl desDao = (FetchSettingDesDaoImpl)SpringContextUtils.getBean(FetchSettingDesDaoImpl.class);
        List fetchSettingDesList = desDao.listAllFloatFetchSetting();
        for (FetchSettingDesEO fetchSettingDes : fetchSettingDesList) {
            if (!fetchSchemeIdAndRegionIdToFieldTitleToNameMap.containsKey(fetchSettingDes.getFetchSchemeId() + "|" + fetchSettingDes.getRegionId())) continue;
            Map fieldTitleToNameMap = (Map)fetchSchemeIdAndRegionIdToFieldTitleToNameMap.get(fetchSettingDes.getFetchSchemeId() + "|" + fetchSettingDes.getRegionId());
            String fixedSettingDataStr = fetchSettingDes.getFixedSettingData();
            List adaptSettingList = (List)JsonUtils.readValue((String)fixedSettingDataStr, (TypeReference)new TypeReference<List<FixedAdaptSettingVO>>(){});
            for (FixedAdaptSettingVO adaptSettingVO : adaptSettingList) {
                adaptSettingVO.setLogicFormula(this.replaceTitleToName(adaptSettingVO.getLogicFormula(), FORMULA_PREFIX_STR, fieldTitleToNameMap));
                Map bizModelFormula = adaptSettingVO.getBizModelFormula();
                for (Map.Entry bizModelFormulaEntry : bizModelFormula.entrySet()) {
                    for (FixedFetchSourceRowSettingVO fetchSourceRowSettingVO : (List)bizModelFormulaEntry.getValue()) {
                        fetchSourceRowSettingVO.setSubjectCode(this.replaceTitleToName(fetchSourceRowSettingVO.getSubjectCode(), FIELD_PREFIX_STR, fieldTitleToNameMap));
                        fetchSourceRowSettingVO.setExcludeSubjectCode(this.replaceTitleToName(fetchSourceRowSettingVO.getExcludeSubjectCode(), FIELD_PREFIX_STR, fieldTitleToNameMap));
                        fetchSourceRowSettingVO.setCashCode(this.replaceTitleToName(fetchSourceRowSettingVO.getCashCode(), FIELD_PREFIX_STR, fieldTitleToNameMap));
                        fetchSourceRowSettingVO.setReclassSubjCode(this.replaceTitleToName(fetchSourceRowSettingVO.getReclassSubjCode(), FIELD_PREFIX_STR, fieldTitleToNameMap));
                        fetchSourceRowSettingVO.setReclassSrcSubjCode(this.replaceTitleToName(fetchSourceRowSettingVO.getReclassSrcSubjCode(), FIELD_PREFIX_STR, fieldTitleToNameMap));
                        fetchSourceRowSettingVO.setDimensionSetting(this.replaceTitleToName(fetchSourceRowSettingVO.getDimensionSetting(), FIELD_PREFIX_STR, fieldTitleToNameMap));
                    }
                }
            }
            fetchSettingDes.setFixedSettingData(JSONUtil.toJSONString((Object)adaptSettingList));
        }
        if (!CollectionUtils.isEmpty((Collection)fetchSettingDesList)) {
            desDao.updateBatch(fetchSettingDesList);
        }
        this.logger.info("BDE\u8bbe\u8ba1\u671f\u56fa\u5b9a\u8868\u6570\u636e\u4fee\u590d\u5b8c\u6210");
    }

    private void repairFloatData(Map<String, Map<String, String>> fetchSchemeIdAndRegionIdToFieldTitleToNameMap, Map<String, String> desIdToQueryConfigInfoMap, String id, String queryConfigInfoStr, String fetchSchemeId, String regionId) {
        if (StringUtils.isEmpty((String)queryConfigInfoStr)) {
            return;
        }
        QueryConfigInfo queryConfigInfo = (QueryConfigInfo)JSONUtil.parseObject((String)queryConfigInfoStr, QueryConfigInfo.class);
        if (CollectionUtils.isEmpty((Collection)queryConfigInfo.getQueryFields())) {
            return;
        }
        List queryFieldList = queryConfigInfo.getQueryFields().stream().filter(item -> !item.getName().equals(item.getTitle())).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(queryFieldList)) {
            return;
        }
        Map<String, String> fieldTitleToNameMap = queryFieldList.stream().collect(Collectors.toMap(FloatQueryFieldVO::getTitle, FloatQueryFieldVO::getName, (o1, o2) -> o1));
        List usedFieldList = queryConfigInfo.getUsedFields();
        ArrayList<String> newUsedFieldList = new ArrayList<String>(usedFieldList.size());
        for (String usedField : usedFieldList) {
            newUsedFieldList.add(fieldTitleToNameMap.containsKey(usedField) ? fieldTitleToNameMap.get(usedField) : usedField);
        }
        queryConfigInfo.setUsedFields(newUsedFieldList);
        queryConfigInfoStr = JSONUtil.toJSONString((Object)queryConfigInfo);
        desIdToQueryConfigInfoMap.put(id, this.replaceTitleToName(queryConfigInfoStr, FIELD_PREFIX_STR, fieldTitleToNameMap));
        fetchSchemeIdAndRegionIdToFieldTitleToNameMap.put(fetchSchemeId + "|" + regionId, fieldTitleToNameMap);
    }

    private String replaceTitleToName(String srcString, String startStr, Map<String, String> fieldTitleToNameMap) {
        if (StringUtils.isEmpty((String)srcString)) {
            return srcString;
        }
        if (!srcString.contains(startStr)) {
            return srcString;
        }
        Set<String> titleSet = fieldTitleToNameMap.keySet();
        StringBuilder resultString = new StringBuilder(srcString.length());
        for (int srcStrIndex = 0; srcStrIndex < srcString.length(); ++srcStrIndex) {
            char ch = srcString.charAt(srcStrIndex);
            if (this.notContains(srcString, srcStrIndex, startStr)) {
                resultString.append(ch);
                continue;
            }
            boolean replaceFlag = false;
            for (String title : titleSet) {
                if (this.notContains(srcString, srcStrIndex + startStr.length(), title)) continue;
                replaceFlag = true;
                resultString.append(startStr).append(fieldTitleToNameMap.get(title));
                srcStrIndex += startStr.length() + title.length() - 1;
                break;
            }
            if (replaceFlag) continue;
            resultString.append(ch);
        }
        return resultString.toString();
    }

    private boolean notContains(String srcStr, int beginIndex, String needMatchedStr) {
        if (beginIndex + needMatchedStr.length() > srcStr.length()) {
            return true;
        }
        return !needMatchedStr.equalsIgnoreCase(srcStr.substring(beginIndex, beginIndex + needMatchedStr.length()));
    }
}

