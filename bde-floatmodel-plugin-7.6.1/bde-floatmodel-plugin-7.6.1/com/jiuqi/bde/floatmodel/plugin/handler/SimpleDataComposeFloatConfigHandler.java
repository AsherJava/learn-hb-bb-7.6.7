/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.jiuqi.bde.bizmodel.client.dto.FinBizModelDTO
 *  com.jiuqi.bde.bizmodel.execute.datamodel.AbstractFinBizDataModelLoader
 *  com.jiuqi.bde.bizmodel.execute.datamodel.FinBizModelLoaderGather
 *  com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition
 *  com.jiuqi.bde.bizmodel.impl.model.service.impl.FinBizModelManageServiceImpl
 *  com.jiuqi.bde.common.constant.ComputationModelEnum
 *  com.jiuqi.bde.common.constant.FetchFixedFieldEnum
 *  com.jiuqi.bde.common.dto.DimensionValue
 *  com.jiuqi.bde.common.dto.QueryConfigInfo
 *  com.jiuqi.bde.common.dto.SimpleComposeDateDTO
 *  com.jiuqi.bde.common.dto.SimpleCustomComposePluginDataVO
 *  com.jiuqi.bde.common.intf.Dimension
 *  com.jiuqi.bde.common.intf.FetchFloatRowResult
 *  com.jiuqi.bde.common.intf.FetchTaskContext
 *  com.jiuqi.bde.floatmodel.impl.gather.FloatConfigHandler
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.DateCommonFormatEnum
 *  com.jiuqi.common.base.util.DateUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.query.fetch.vo.FetchQueryFiledVO
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.bde.floatmodel.plugin.handler;

import com.fasterxml.jackson.core.type.TypeReference;
import com.jiuqi.bde.bizmodel.client.dto.FinBizModelDTO;
import com.jiuqi.bde.bizmodel.execute.datamodel.AbstractFinBizDataModelLoader;
import com.jiuqi.bde.bizmodel.execute.datamodel.FinBizModelLoaderGather;
import com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition;
import com.jiuqi.bde.bizmodel.impl.model.service.impl.FinBizModelManageServiceImpl;
import com.jiuqi.bde.common.constant.ComputationModelEnum;
import com.jiuqi.bde.common.constant.FetchFixedFieldEnum;
import com.jiuqi.bde.common.dto.DimensionValue;
import com.jiuqi.bde.common.dto.QueryConfigInfo;
import com.jiuqi.bde.common.dto.SimpleComposeDateDTO;
import com.jiuqi.bde.common.dto.SimpleCustomComposePluginDataVO;
import com.jiuqi.bde.common.intf.Dimension;
import com.jiuqi.bde.common.intf.FetchFloatRowResult;
import com.jiuqi.bde.common.intf.FetchTaskContext;
import com.jiuqi.bde.floatmodel.impl.gather.FloatConfigHandler;
import com.jiuqi.bde.floatmodel.plugin.util.ArgsValidUtil;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.DateCommonFormatEnum;
import com.jiuqi.common.base.util.DateUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.query.fetch.vo.FetchQueryFiledVO;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.transaction.annotation.Transactional;

public class SimpleDataComposeFloatConfigHandler
implements FloatConfigHandler {
    @Autowired
    @Lazy
    private FinBizModelLoaderGather loaderGather;
    @Autowired
    private FinBizModelManageServiceImpl bizModelService;

    public String getCode() {
        return "SIMPLE_FETCHSOURCE";
    }

    public String getTitle() {
        return "\u6807\u51c6\u4e1a\u52a1\u6a21\u578b";
    }

    public String getProdLine() {
        return "@bde";
    }

    public String getAppName() {
        return "bde-floatmodel";
    }

    public Integer getOrder() {
        return 1;
    }

    @Transactional(rollbackFor={Exception.class})
    public FetchFloatRowResult queryFloatRowDatas(FetchTaskContext fetchTaskContext, QueryConfigInfo queryConfigInfo) {
        if (queryConfigInfo == null) {
            return null;
        }
        if (CollectionUtils.isEmpty((Collection)queryConfigInfo.getUsedFields())) {
            throw new BusinessRuntimeException("\u6d6e\u52a8\u884c\u53d6\u6570\u914d\u7f6e\u4e2d\u6ca1\u6709\u7528\u5230\u4efb\u4f55\u67e5\u8be2\u5b9a\u4e49\u7ed3\u679c\u5b57\u6bb5,\u8bf7\u68c0\u67e5\u914d\u7f6e\u4fe1\u606f\uff01");
        }
        SimpleCustomComposePluginDataVO simpleCustomComposePluginDataVO = this.getCustomComposePluginDataVO(queryConfigInfo.getPluginData());
        if (fetchTaskContext.getExtParam() != null && !fetchTaskContext.getExtParam().isEmpty()) {
            for (Dimension dimension : simpleCustomComposePluginDataVO.getDimensionMapping()) {
                if (!fetchTaskContext.getExtParam().containsKey(dimension.getDimCode())) continue;
                dimension.setDimValue((String)fetchTaskContext.getExtParam().get(dimension.getDimCode()));
            }
        }
        BalanceCondition condi = null;
        try {
            condi = new BalanceCondition(fetchTaskContext.getRequestTaskId(), fetchTaskContext.getUnitCode(), DateUtils.createFormatter((DateCommonFormatEnum)DateCommonFormatEnum.FULL_DIGIT_BY_DASH).parse(fetchTaskContext.getStartDateStr()), DateUtils.createFormatter((DateCommonFormatEnum)DateCommonFormatEnum.FULL_DIGIT_BY_DASH).parse(fetchTaskContext.getEndDateStr()), fetchTaskContext.getOrgMapping(), Boolean.valueOf(fetchTaskContext.getIncludeUncharged() == null || fetchTaskContext.getIncludeUncharged() != false));
        }
        catch (ParseException e) {
            throw new BusinessRuntimeException("\u65e5\u671f\u683c\u5f0f\u8f6c\u5316\u5931\u8d25", (Throwable)e);
        }
        condi.setIncludeAdjustVchr(fetchTaskContext.getIncludeAdjustVchr());
        condi.setAssTypeList(simpleCustomComposePluginDataVO.getDimensionMapping());
        condi.setOtherEntity(fetchTaskContext.getOtherEntity());
        condi.setStartAdjustPeriod(fetchTaskContext.getStartAdjustPeriod());
        condi.setEndAdjustPeriod(fetchTaskContext.getEndAdjustPeriod());
        condi.setDimensionValueMap(StringUtils.isEmpty((String)fetchTaskContext.getDimensionSetStr()) ? new HashMap() : (Map)JsonUtils.readValue((String)fetchTaskContext.getDimensionSetStr(), (TypeReference)new TypeReference<Map<String, DimensionValue>>(){}));
        condi.setExtParam(fetchTaskContext.getExtParam());
        condi.setBblX(fetchTaskContext.getBblx());
        condi.setRpUnitType(fetchTaskContext.getRpUnitType());
        SimpleComposeDateDTO simpleComposeDateDTO = new SimpleComposeDateDTO(simpleCustomComposePluginDataVO, queryConfigInfo.getQueryFields(), queryConfigInfo.getUsedFields());
        FinBizModelDTO bizModel = this.bizModelService.getByCode(simpleCustomComposePluginDataVO.getFetchSourceCode());
        FetchFloatRowResult fetchFloatRowResult = new FetchFloatRowResult();
        condi.setComputationModel(bizModel.getComputationModelCode());
        AbstractFinBizDataModelLoader loader = this.loaderGather.getLoader(fetchTaskContext.getOrgMapping().getPluginType(), bizModel.getComputationModelCode());
        fetchFloatRowResult = loader.simpleFloatQuery(condi, simpleComposeDateDTO);
        return fetchFloatRowResult;
    }

    public List<FetchQueryFiledVO> parseFloatRowFields(QueryConfigInfo queryConfigInfo) {
        SimpleCustomComposePluginDataVO simpleCustomComposePluginDataVO = this.getCustomComposePluginDataVO(queryConfigInfo.getPluginData());
        if (simpleCustomComposePluginDataVO == null) {
            throw new BusinessRuntimeException("\u6d6e\u52a8\u884c\u53d6\u6570\u914d\u7f6e\u4e2d\u6ca1\u6709\u68c0\u6d4b\u5230\u914d\u7f6e,\u8bf7\u68c0\u67e5\u914d\u7f6e\u4fe1\u606f");
        }
        if (simpleCustomComposePluginDataVO.getDimensionMapping().stream().anyMatch(dim -> !ArgsValidUtil.isSecuritySql(dim.getDimValue()) || !ArgsValidUtil.isSecuritySql(dim.getExcludeValue()))) {
            throw new BusinessRuntimeException("\u53c2\u6570\u5b58\u5728\u975e\u6cd5\u5b57\u7b26\uff0c\u8bf7\u786e\u8ba4\u540e\u91cd\u65b0\u586b\u5199");
        }
        if (simpleCustomComposePluginDataVO.getDimensionMapping().stream().anyMatch(dim -> !ArgsValidUtil.isSimpleFloatValidated(dim.getDimValue()) || !ArgsValidUtil.isSimpleFloatValidated(dim.getExcludeValue()))) {
            throw new BusinessRuntimeException("\u53c2\u6570\u683c\u5f0f\u9519\u8bef\uff0c\u8bf7\u786e\u8ba4\u540e\u91cd\u65b0\u586b\u5199");
        }
        ArrayList<FetchQueryFiledVO> fetchQueryFiledVOs = new ArrayList<FetchQueryFiledVO>(this.buildConstField(simpleCustomComposePluginDataVO.getFetchSourceCode()));
        Map fieldMap = this.bizModelService.getFetchTypesByCode(simpleCustomComposePluginDataVO.getFetchSourceCode());
        for (Map.Entry entry : fieldMap.entrySet()) {
            FetchQueryFiledVO fetchQueryFiledVO = new FetchQueryFiledVO();
            if ("SUBJECTCODE".equals(entry.getKey()) || "CURRENCYCODE".equals(entry.getKey()) || "MD_ACCTSUBJECT".equals(entry.getKey()) || "MD_CURRENCY".equals(entry.getKey()) || "MD_ASSISTDIM".equals(entry.getKey()) || "MD_CFITEM".equals(entry.getKey())) {
                throw new BusinessRuntimeException("\u4e1a\u52a1\u6a21\u578b\u7ef4\u5ea6\u5f02\u5e38");
            }
            fetchQueryFiledVO.setName((String)entry.getKey());
            fetchQueryFiledVO.setTitle((String)entry.getValue());
            fetchQueryFiledVOs.add(fetchQueryFiledVO);
        }
        return fetchQueryFiledVOs;
    }

    private List<FetchQueryFiledVO> buildConstField(String fetchSourceCode) {
        ArrayList<FetchQueryFiledVO> fetchQueryFiledVOs = new ArrayList<FetchQueryFiledVO>();
        FetchQueryFiledVO subjectField = new FetchQueryFiledVO();
        subjectField.setTitle("\u79d1\u76ee");
        subjectField.setName("SUBJECTCODE");
        fetchQueryFiledVOs.add(subjectField);
        FetchQueryFiledVO currencyField = new FetchQueryFiledVO();
        currencyField.setTitle("\u5e01\u79cd");
        currencyField.setName("CURRENCYCODE");
        fetchQueryFiledVOs.add(currencyField);
        if (ComputationModelEnum.XJLLBALANCE.getCode().equals(this.bizModelService.getByCode(fetchSourceCode).getComputationModelCode())) {
            FetchQueryFiledVO fetchQueryFiledVO = new FetchQueryFiledVO();
            fetchQueryFiledVO.setName("CFITEMCODE");
            fetchQueryFiledVO.setTitle(FetchFixedFieldEnum.CASHCODE.getName());
            fetchQueryFiledVOs.add(fetchQueryFiledVO);
        }
        return fetchQueryFiledVOs;
    }

    private SimpleCustomComposePluginDataVO getCustomComposePluginDataVO(String pluginDataStr) {
        if (StringUtils.isEmpty((String)pluginDataStr)) {
            throw new BusinessRuntimeException("\u6d6e\u52a8\u884c\u53d6\u6570\u914d\u7f6e\u4e2d\u6ca1\u6709\u68c0\u6d4b\u5230\u914d\u7f6e,\u8bf7\u68c0\u67e5\u914d\u7f6e\u4fe1\u606f");
        }
        return (SimpleCustomComposePluginDataVO)JSONUtil.parseObject((String)pluginDataStr, SimpleCustomComposePluginDataVO.class);
    }
}

