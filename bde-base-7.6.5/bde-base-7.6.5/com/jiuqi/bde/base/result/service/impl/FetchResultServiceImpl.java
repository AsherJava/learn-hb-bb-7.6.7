/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.constant.RequestSourceTypeEnum
 *  com.jiuqi.bde.common.dto.FixedFetchSourceRowSettingVO
 *  com.jiuqi.bde.common.dto.fetch.request.FetchRequestContextDTO
 *  com.jiuqi.bde.common.dto.fetch.request.FetchRequestDTO
 *  com.jiuqi.bde.common.dto.fetch.request.FetchRequestFixedSettingDTO
 *  com.jiuqi.bde.common.dto.fetch.result.FetchResultDTO
 *  com.jiuqi.bde.common.dto.fetch.result.FloatRegionResultDTO
 *  com.jiuqi.bde.common.util.BdeCommonUtil
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService
 *  org.springframework.transaction.annotation.Propagation
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.bde.base.result.service.impl;

import com.jiuqi.bde.base.formula.FetchFormulaContext;
import com.jiuqi.bde.base.formula.FetchFormulaPaser;
import com.jiuqi.bde.base.formula.FetchFormulaUtil;
import com.jiuqi.bde.base.result.dao.FetchResultDao;
import com.jiuqi.bde.base.result.service.FetchResultService;
import com.jiuqi.bde.base.util.FloatRegionResponseCaculator;
import com.jiuqi.bde.common.constant.RequestSourceTypeEnum;
import com.jiuqi.bde.common.dto.FixedFetchSourceRowSettingVO;
import com.jiuqi.bde.common.dto.fetch.request.FetchRequestContextDTO;
import com.jiuqi.bde.common.dto.fetch.request.FetchRequestDTO;
import com.jiuqi.bde.common.dto.fetch.request.FetchRequestFixedSettingDTO;
import com.jiuqi.bde.common.dto.fetch.result.FetchResultDTO;
import com.jiuqi.bde.common.dto.fetch.result.FloatRegionResultDTO;
import com.jiuqi.bde.common.util.BdeCommonUtil;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FetchResultServiceImpl
implements FetchResultService {
    @Autowired
    private FetchResultDao fetchResultDao;
    @Autowired
    private INvwaSystemOptionService optionService;
    private final Logger logger = LoggerFactory.getLogger(FetchResultServiceImpl.class);

    @Override
    public FetchResultDTO getFetchResult(FetchRequestDTO fetchRequestDTO) {
        if (RequestSourceTypeEnum.PENETRATE.getCode().equals(fetchRequestDTO.getRequestSourceType()) || RequestSourceTypeEnum.TEST.getCode().equals(fetchRequestDTO.getRequestSourceType())) {
            return this.getSyncFixedResults(fetchRequestDTO);
        }
        FetchResultDTO fetchResult = new FetchResultDTO();
        fetchResult.setFixedResults(this.getFixedResults(fetchRequestDTO));
        if (fetchRequestDTO.getFloatSetting() != null && !StringUtils.isEmpty((String)fetchRequestDTO.getFloatSetting().getQueryType())) {
            fetchResult.setFloatResults(this.getFloatResults(fetchRequestDTO));
        }
        return fetchResult;
    }

    private FetchResultDTO getSyncFixedResults(FetchRequestDTO fetchRequestDTO) {
        FetchResultDTO result = new FetchResultDTO();
        if (RequestSourceTypeEnum.TEST.getCode().equals(fetchRequestDTO.getRequestSourceType())) {
            Map<String, Object> syncFixedResultsByFetchTest = this.fetchResultDao.getSyncFixedResultsByFetchTest(fetchRequestDTO.getRequestTaskId(), fetchRequestDTO.getFetchContext().getFormId(), fetchRequestDTO.getRouteNum());
            result.setFixedResults(syncFixedResultsByFetchTest);
            return result;
        }
        HashSet<String> strFetchSettingSet = new HashSet<String>(10);
        FetchRequestFixedSettingDTO fixedSettingDTO = (FetchRequestFixedSettingDTO)fetchRequestDTO.getFixedSetting().get(0);
        String formula = fixedSettingDTO.getLogicFormula();
        for (Map.Entry bizModelFormula : ((FetchRequestFixedSettingDTO)fetchRequestDTO.getFixedSetting().get(0)).getBizModelFormula().entrySet()) {
            for (FixedFetchSourceRowSettingVO rowSetting : (List)bizModelFormula.getValue()) {
                if (BdeCommonUtil.fieldDefineTypeIsNum((Integer)fixedSettingDTO.getFieldDefineType())) continue;
                strFetchSettingSet.add(rowSetting.getId());
            }
        }
        Map<String, Object> fixedResults = this.fetchResultDao.getSyncFixedResults(fetchRequestDTO.getRequestTaskId(), fetchRequestDTO.getFetchContext().getFormId(), strFetchSettingSet, fetchRequestDTO.getRouteNum());
        if (StringUtils.isEmpty((String)formula)) {
            result.setFixedResults(fixedResults);
            return result;
        }
        FetchFormulaPaser formulaParser = new FetchFormulaPaser();
        Map<String, Object> envParam = FetchFormulaUtil.convtEnvByFetchContext(fetchRequestDTO);
        FetchFormulaContext context = new FetchFormulaContext(envParam);
        context.setFetchResultMap(fixedResults);
        fixedResults.put("logicFormulaVal", formulaParser.evaluate(formula, context));
        result.setFixedResults(fixedResults);
        return result;
    }

    @Override
    public Map<String, Object> getFixedResults(FetchRequestDTO fetchRequestDTO) {
        FetchRequestContextDTO context = fetchRequestDTO.getFetchContext();
        HashMap<String, String> logicFormulaMap = new HashMap<String, String>();
        for (Object fetchRequestFixedSettingDTO : fetchRequestDTO.getFixedSetting()) {
            if (StringUtils.isEmpty((String)fetchRequestFixedSettingDTO.getLogicFormula())) continue;
            logicFormulaMap.put(fetchRequestFixedSettingDTO.getFieldDefineId(), fetchRequestFixedSettingDTO.getLogicFormula());
        }
        Map<String, Object> fixedResults = this.fetchResultDao.getFixedSumResults(fetchRequestDTO.getRequestTaskId(), context.getFormId(), context.getRegionId(), fetchRequestDTO.getRouteNum());
        for (FetchRequestFixedSettingDTO fetchRequestFixedSettingDTO : fetchRequestDTO.getFixedSetting()) {
            if (fixedResults.containsKey(fetchRequestFixedSettingDTO.getFieldDefineId())) continue;
            fixedResults.put(fetchRequestFixedSettingDTO.getFieldDefineId(), 0);
        }
        if (logicFormulaMap.isEmpty()) {
            return fixedResults;
        }
        ArrayList<String> formulaFieldDefineIdList = new ArrayList<String>(logicFormulaMap.keySet());
        Map<String, Map<String, Object>> fixedDetailResults = this.fetchResultDao.getFixedDetailResults(fetchRequestDTO.getRequestTaskId(), context.getFormId(), context.getRegionId(), formulaFieldDefineIdList, fetchRequestDTO.getRouteNum());
        FetchFormulaPaser formulaParser = new FetchFormulaPaser();
        Map<String, Object> envParam = FetchFormulaUtil.convtEnvByFetchContext(fetchRequestDTO);
        for (Map.Entry<String, Map<String, Object>> fieldDefineEntry : fixedDetailResults.entrySet()) {
            String formula = (String)logicFormulaMap.get(fieldDefineEntry.getKey());
            FetchFormulaContext formulaContext = new FetchFormulaContext(envParam);
            formulaContext.setFetchResultMap(fieldDefineEntry.getValue());
            fixedResults.put(fieldDefineEntry.getKey(), formulaParser.evaluate(formula, formulaContext));
        }
        return fixedResults;
    }

    @Override
    public FloatRegionResultDTO getFloatResults(FetchRequestDTO fetchRequestDTO) {
        FloatRegionResponseCaculator floatRegionResultCaculator = new FloatRegionResponseCaculator(fetchRequestDTO, fetchRequestDTO.getRouteNum());
        FloatRegionResultDTO floatRegionResult = floatRegionResultCaculator.doCaculate();
        floatRegionResult = RequestSourceTypeEnum.NR_FETCH.getCode().equals(fetchRequestDTO.getRequestSourceType()) || RequestSourceTypeEnum.BUDGET_FETCH.getCode().equals(fetchRequestDTO.getRequestSourceType()) || RequestSourceTypeEnum.BILL_FETCH.getCode().equals(fetchRequestDTO.getRequestSourceType()) ? this.cleanZeroRecord(floatRegionResult) : floatRegionResult;
        return floatRegionResult;
    }

    private FloatRegionResultDTO cleanZeroRecord(FloatRegionResultDTO floatRegionResult) {
        if (floatRegionResult == null || CollectionUtils.isEmpty((Collection)floatRegionResult.getRowDatas())) {
            return floatRegionResult;
        }
        if (!"1".equals(this.optionService.findValueById("CLEAN_ZERO_RECORDS"))) {
            return floatRegionResult;
        }
        ArrayList<Object[]> rowDatas = new ArrayList<Object[]>(floatRegionResult.getRowDatas().size());
        boolean rowContainsNum = false;
        boolean rowNumAllZero = false;
        for (Object[] row : floatRegionResult.getRowDatas()) {
            rowContainsNum = false;
            rowNumAllZero = true;
            for (Object col : row) {
                rowContainsNum = rowContainsNum || BdeCommonUtil.valIsNum((Object)col);
                rowNumAllZero = rowNumAllZero && BdeCommonUtil.valIsZero((Object)col);
            }
            if (rowContainsNum && rowNumAllZero) continue;
            rowDatas.add(row);
        }
        floatRegionResult.setRowDatas(rowDatas);
        return floatRegionResult;
    }

    @Override
    @Transactional(rollbackFor={Exception.class}, propagation=Propagation.REQUIRES_NEW)
    public void cleanResultTable(String requestTaskId, String formId, String regionId, Integer routeNum, boolean existsFloatFetch) {
        if ("1".equals(this.optionService.findValueById("BDE_IS_DEBUG"))) {
            return;
        }
        try {
            this.fetchResultDao.deleteFixedByRequestTaskId(requestTaskId, formId, regionId, routeNum);
            if (existsFloatFetch) {
                List<String> requestRegionIds = this.fetchResultDao.getRequestRegionIdsByFormId(requestTaskId, formId, regionId, routeNum);
                if (!CollectionUtils.isEmpty(requestRegionIds)) {
                    this.fetchResultDao.deleteFloatColByRequestTaskId(requestRegionIds, routeNum);
                    this.fetchResultDao.deleteFloatRowByRequestTaskId(requestRegionIds, routeNum);
                }
                this.fetchResultDao.deleteFloatDefineByRequestTaskId(requestTaskId, formId, regionId, routeNum);
            }
        }
        catch (Exception e) {
            this.logger.error(String.format("requestTaskId=\u3010%1$s\u3011,formId=\u3010%2$s\u3011,regionId=\u3010%3$s\u3011\u7ed3\u679c\u8868\u6e05\u7406\u5f02\u5e38", requestTaskId, formId, regionId));
        }
    }
}

