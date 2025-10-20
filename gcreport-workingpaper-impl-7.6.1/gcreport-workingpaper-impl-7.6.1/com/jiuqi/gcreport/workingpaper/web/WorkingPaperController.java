/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext
 *  com.jiuiqi.nr.unit.treebase.context.IUnitTreeContextBuilder
 *  com.jiuiqi.nr.unit.treebase.context.IUnitTreeContextCache
 *  com.jiuiqi.nr.unit.treebase.context.impl.UnitTreeContextData
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.consolidatedsystem.service.option.ConsolidatedOptionService
 *  com.jiuqi.gcreport.consolidatedsystem.vo.option.ConsolidatedOptionVO
 *  com.jiuqi.gcreport.offsetitem.enums.OffSetSrcTypeEnum
 *  com.jiuqi.gcreport.offsetitem.vo.DesignFieldDefineVO
 *  com.jiuqi.gcreport.offsetitem.vo.Pagination
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.enums.GcOrgKindEnum
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  com.jiuqi.gcreport.workingpaper.api.WorkingPaperClient
 *  com.jiuqi.gcreport.workingpaper.vo.ArbitrarilyMergeDelParamsVO
 *  com.jiuqi.gcreport.workingpaper.vo.ArbitrarilyMergeInputAdjustQueryCondi
 *  com.jiuqi.gcreport.workingpaper.vo.ArbitrarilyMergeInputAdjustVO
 *  com.jiuqi.gcreport.workingpaper.vo.ArbitrarilyMergeOrgFilterCustomFormulaSettingVO
 *  com.jiuqi.gcreport.workingpaper.vo.ArbitrarilyMergeOrgFilterSettingVO
 *  com.jiuqi.gcreport.workingpaper.vo.ArbitrarilyMergeOrgParamVO
 *  com.jiuqi.gcreport.workingpaper.vo.ArbitrarilyMergeQuerySchemeVO
 *  com.jiuqi.gcreport.workingpaper.vo.SubjectZbPentrationParamsVO
 *  com.jiuqi.gcreport.workingpaper.vo.WorkingPaperPentrationQueryCondtion
 *  com.jiuqi.gcreport.workingpaper.vo.WorkingPaperQueryCondition
 *  com.jiuqi.gcreport.workingpaper.vo.WorkingPaperQueryWayItemVO
 *  com.jiuqi.gcreport.workingpaper.vo.WorkingPaperQueryWayVO
 *  com.jiuqi.gcreport.workingpaper.vo.WorkingPaperTableHeaderVO
 *  com.jiuqi.gcreport.workingpaper.vo.WorkingPaperTableVO
 *  com.jiuqi.nr.unit.treecommon.exception.UnitTreeRuntimeException
 *  com.jiuqi.nr.unit.uselector.cacheset.USelectorResultSet
 *  com.jiuqi.nr.unit.uselector.checker.IFilterCheckValues
 *  com.jiuqi.nr.unit.uselector.checker.IFilterCheckValuesImpl
 *  com.jiuqi.nr.unit.uselector.checker.IRowCheckExecutor
 *  com.jiuqi.nr.unit.uselector.checker.IRowChecker
 *  com.jiuqi.nr.unit.uselector.checker.IRowCheckerHelper
 *  com.jiuqi.nr.unit.uselector.source.IUSelectorDataSource
 *  com.jiuqi.nr.unit.uselector.source.IUSelectorDataSourceHelper
 *  com.jiuqi.nr.unit.uselector.source.IUSelectorEntityRowProvider
 *  com.jiuqi.nr.unit.uselector.web.request.FilteringRequestParam
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.OrderNumUtil
 *  javax.annotation.Resource
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  org.springframework.transaction.annotation.Transactional
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.gcreport.workingpaper.web;

import com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext;
import com.jiuiqi.nr.unit.treebase.context.IUnitTreeContextBuilder;
import com.jiuiqi.nr.unit.treebase.context.IUnitTreeContextCache;
import com.jiuiqi.nr.unit.treebase.context.impl.UnitTreeContextData;
import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.consolidatedsystem.service.option.ConsolidatedOptionService;
import com.jiuqi.gcreport.consolidatedsystem.vo.option.ConsolidatedOptionVO;
import com.jiuqi.gcreport.offsetitem.enums.OffSetSrcTypeEnum;
import com.jiuqi.gcreport.offsetitem.vo.DesignFieldDefineVO;
import com.jiuqi.gcreport.offsetitem.vo.Pagination;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.enums.GcOrgKindEnum;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.gcreport.workingpaper.api.WorkingPaperClient;
import com.jiuqi.gcreport.workingpaper.enums.ArbitrarilyMergeOrgFilterControlTypeEnum;
import com.jiuqi.gcreport.workingpaper.enums.ArbitrarilyMergeOrgFilterDataSourceTypeEnum;
import com.jiuqi.gcreport.workingpaper.enums.ArbitrarilyMergeOrgFilterDataTypeEnum;
import com.jiuqi.gcreport.workingpaper.enums.JzdfWorkingpaperColumnSelectEnum;
import com.jiuqi.gcreport.workingpaper.enums.WorkingPaperDxsTypeEnum;
import com.jiuqi.gcreport.workingpaper.enums.WorkingPaperQmsTypeEnum;
import com.jiuqi.gcreport.workingpaper.enums.WorkingPaperQueryTypeEnum;
import com.jiuqi.gcreport.workingpaper.enums.WorkingPaperType;
import com.jiuqi.gcreport.workingpaper.service.ArbitrarilyMergeService;
import com.jiuqi.gcreport.workingpaper.service.WorkingPaperService;
import com.jiuqi.gcreport.workingpaper.utils.AssemblyFormulaUtil;
import com.jiuqi.gcreport.workingpaper.utils.WorkingPaperUtil;
import com.jiuqi.gcreport.workingpaper.vo.ArbitrarilyMergeDelParamsVO;
import com.jiuqi.gcreport.workingpaper.vo.ArbitrarilyMergeInputAdjustQueryCondi;
import com.jiuqi.gcreport.workingpaper.vo.ArbitrarilyMergeInputAdjustVO;
import com.jiuqi.gcreport.workingpaper.vo.ArbitrarilyMergeOrgFilterCustomFormulaSettingVO;
import com.jiuqi.gcreport.workingpaper.vo.ArbitrarilyMergeOrgFilterSettingVO;
import com.jiuqi.gcreport.workingpaper.vo.ArbitrarilyMergeOrgParamVO;
import com.jiuqi.gcreport.workingpaper.vo.ArbitrarilyMergeQuerySchemeVO;
import com.jiuqi.gcreport.workingpaper.vo.SubjectZbPentrationParamsVO;
import com.jiuqi.gcreport.workingpaper.vo.WorkingPaperPentrationQueryCondtion;
import com.jiuqi.gcreport.workingpaper.vo.WorkingPaperQueryCondition;
import com.jiuqi.gcreport.workingpaper.vo.WorkingPaperQueryWayItemVO;
import com.jiuqi.gcreport.workingpaper.vo.WorkingPaperQueryWayVO;
import com.jiuqi.gcreport.workingpaper.vo.WorkingPaperTableHeaderVO;
import com.jiuqi.gcreport.workingpaper.vo.WorkingPaperTableVO;
import com.jiuqi.nr.unit.treecommon.exception.UnitTreeRuntimeException;
import com.jiuqi.nr.unit.uselector.cacheset.USelectorResultSet;
import com.jiuqi.nr.unit.uselector.checker.IFilterCheckValues;
import com.jiuqi.nr.unit.uselector.checker.IFilterCheckValuesImpl;
import com.jiuqi.nr.unit.uselector.checker.IRowCheckExecutor;
import com.jiuqi.nr.unit.uselector.checker.IRowChecker;
import com.jiuqi.nr.unit.uselector.checker.IRowCheckerHelper;
import com.jiuqi.nr.unit.uselector.source.IUSelectorDataSource;
import com.jiuqi.nr.unit.uselector.source.IUSelectorDataSourceHelper;
import com.jiuqi.nr.unit.uselector.source.IUSelectorEntityRowProvider;
import com.jiuqi.nr.unit.uselector.web.request.FilteringRequestParam;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.OrderNumUtil;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.zip.GZIPOutputStream;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Primary
public class WorkingPaperController
implements WorkingPaperClient {
    @Autowired
    private WorkingPaperService workingPaperService;
    @Autowired
    private ArbitrarilyMergeService arbitrarilyMergeService;
    @Autowired
    private AssemblyFormulaUtil assemblyFormulaUtil;
    @Resource
    private USelectorResultSet contextHelper;
    @Resource
    private IRowCheckerHelper checkerHelper;
    @Resource
    private IUSelectorDataSourceHelper sourceHelper;
    @Resource
    private IUnitTreeContextBuilder contextBuilder;
    @Resource
    private IUnitTreeContextCache contextCache;
    @Autowired
    private ConsolidatedOptionService consolidatedOptionService;
    @Resource
    private USelectorResultSet uSelectorResultSet;

    @Transactional(rollbackFor={Exception.class})
    public BusinessResponseEntity<WorkingPaperTableVO> getWorkingPaperHeaderVO(WorkingPaperQueryCondition condition) {
        List<WorkingPaperTableHeaderVO> titles = this.workingPaperService.createHeaderVO(condition);
        WorkingPaperTableVO vo = new WorkingPaperTableVO();
        vo.setTitles(titles);
        return BusinessResponseEntity.ok((Object)vo);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void getWorkingPaperDataVO(HttpServletRequest request, HttpServletResponse response, WorkingPaperQueryCondition condition) throws IOException {
        String data = null;
        try {
            if (null == condition.getCurrency() && StringUtils.isNull((String)condition.getCurrencyCode())) {
                condition.setCurrencyCode("CNY");
            }
            WorkingPaperTableVO workPagerMLDVO = new WorkingPaperTableVO();
            workPagerMLDVO.setDatas(this.workingPaperService.getDataVO(condition));
            data = JsonUtils.writeValueAsString((Object)BusinessResponseEntity.ok((Object)workPagerMLDVO));
        }
        catch (Throwable e) {
            try {
                data = JsonUtils.writeValueAsString((Object)BusinessResponseEntity.error((Throwable)e));
            }
            catch (Throwable throwable) {
                byte[] compress = this.compress(data);
                response.getOutputStream().write(compress);
                throw throwable;
            }
            byte[] compress = this.compress(data);
            response.getOutputStream().write(compress);
        }
        byte[] compress = this.compress(data);
        response.getOutputStream().write(compress);
    }

    private byte[] compress(String data) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try (GZIPOutputStream gzipOutputStream = new GZIPOutputStream(byteArrayOutputStream);){
            gzipOutputStream.write(data.getBytes("UTF-8"));
        }
        return byteArrayOutputStream.toByteArray();
    }

    public BusinessResponseEntity<WorkingPaperQueryWayVO> getWorkPaperQueryWays(String workingPaperTypeCode) {
        WorkingPaperType workingPaperType = WorkingPaperType.getEnumByCode(workingPaperTypeCode);
        WorkingPaperQueryWayVO vo = new WorkingPaperQueryWayVO();
        List<WorkingPaperQueryWayItemVO> wayItemVOS = this.workingPaperService.getWorkingPaperQueryWays(workingPaperType);
        vo.setWays(wayItemVOS);
        vo.setQmsTypes(WorkingPaperQmsTypeEnum.toWorkPaperTypeOptionVOs());
        vo.setDxsTypes(WorkingPaperDxsTypeEnum.toWorkPaperTypeOptionVOs());
        vo.setQueryTypes(WorkingPaperQueryTypeEnum.toWorkPaperTypeOptionVOs(workingPaperType));
        return BusinessResponseEntity.ok((Object)vo);
    }

    public BusinessResponseEntity<WorkingPaperQueryWayItemVO> addOrUpdateWorkPaperQueryWay(WorkingPaperQueryWayItemVO vo) {
        WorkingPaperQueryWayItemVO resultVO = this.workingPaperService.addOrUpdateWorkPaperQueryWay(vo);
        return BusinessResponseEntity.ok((Object)resultVO);
    }

    public BusinessResponseEntity<Boolean> deleteWorkPaperQueryWay(String currWayId) {
        Boolean isSuccess = this.workingPaperService.deleteWorkPaperQueryWay(currWayId);
        return BusinessResponseEntity.ok((Object)isSuccess);
    }

    public BusinessResponseEntity<WorkingPaperQueryWayItemVO> queryWorkPaperQueryWay(String currWayId) {
        WorkingPaperQueryWayItemVO queryWayItemVO = this.workingPaperService.queryWorkPaperQueryWay(currWayId);
        return BusinessResponseEntity.ok((Object)queryWayItemVO);
    }

    public BusinessResponseEntity<Boolean> exchangeSortWorkPaperQueryWay(String currWayId, String exchangeWayId) {
        Boolean isSuccess = this.workingPaperService.exchangeSortWorkPaperQueryWay(currWayId, exchangeWayId);
        return BusinessResponseEntity.ok((Object)isSuccess);
    }

    @Transactional(rollbackFor={Exception.class})
    public BusinessResponseEntity<Object> getWorkPaperPentrationDatas(HttpServletRequest request, HttpServletResponse response, WorkingPaperPentrationQueryCondtion pentrationQueryCondtion) {
        Object pentrationDatas = this.workingPaperService.getWorkPaperPentrationDatas(request, response, pentrationQueryCondtion);
        return BusinessResponseEntity.ok((Object)pentrationDatas);
    }

    public BusinessResponseEntity<SubjectZbPentrationParamsVO> getSubjectZbPentrateParams(String zbBoundIndexPath, WorkingPaperQueryCondition condition) {
        SubjectZbPentrationParamsVO subjectZbPentrateParams = this.workingPaperService.getSubjectZbPentrateParams(zbBoundIndexPath, condition);
        return BusinessResponseEntity.ok((Object)subjectZbPentrateParams);
    }

    @Transactional(rollbackFor={Exception.class})
    public BusinessResponseEntity<Pagination<Map<String, Object>>> getWorkPaperRyPentrationDatas(WorkingPaperPentrationQueryCondtion penInfo) {
        return BusinessResponseEntity.ok(this.arbitrarilyMergeService.listRyPentrationDatas(penInfo));
    }

    public BusinessResponseEntity<Pagination<Map<String, Object>>> getWorkPaperUnOffsetPentrationDatas(HttpServletRequest request, HttpServletResponse response, WorkingPaperPentrationQueryCondtion penInfo) {
        return BusinessResponseEntity.ok(this.arbitrarilyMergeService.getUnOffsetPentrationDatas(penInfo));
    }

    @Transactional(rollbackFor={Exception.class})
    public BusinessResponseEntity<Pagination<Map<String, Object>>> getWorkPaperDxAndRyPentrationDatas(HttpServletRequest request, HttpServletResponse response, WorkingPaperPentrationQueryCondtion penInfo) {
        Pagination dxDatas = (Pagination)this.workingPaperService.getWorkPaperPentrationDatas(request, response, penInfo);
        Pagination<Map<String, Object>> ryDatas = this.arbitrarilyMergeService.listRyPentrationDatas(penInfo);
        ArrayList resultData = new ArrayList();
        resultData.addAll(dxDatas.getContent());
        resultData.addAll(ryDatas.getContent());
        Pagination result = new Pagination();
        result.setContent(resultData);
        result.setTotalElements(Integer.valueOf(dxDatas.getTotalElements() + ryDatas.getTotalElements()));
        result.setCurrentPage(dxDatas.getCurrentPage());
        result.setPageSize(dxDatas.getPageSize());
        return BusinessResponseEntity.ok((Object)result);
    }

    public BusinessResponseEntity<String> checkOrgIdSupSubRelation(WorkingPaperQueryCondition info) {
        return BusinessResponseEntity.ok((Object)this.arbitrarilyMergeService.checkOrgIdSupSubRelation(info));
    }

    public BusinessResponseEntity<List<DesignFieldDefineVO>> queryJzdfWorkpaperColumnSelect() {
        ArrayList<DesignFieldDefineVO> columns = new ArrayList<DesignFieldDefineVO>();
        for (JzdfWorkingpaperColumnSelectEnum columnSelectEnum : JzdfWorkingpaperColumnSelectEnum.values()) {
            DesignFieldDefineVO defineVO = new DesignFieldDefineVO();
            defineVO.setDefaultShow(false);
            defineVO.setKey(columnSelectEnum.getColumnCode());
            defineVO.setLabel(columnSelectEnum.getColumnTitle());
            columns.add(defineVO);
        }
        return BusinessResponseEntity.ok(columns);
    }

    public BusinessResponseEntity<WorkingPaperTableVO> getSystemId(WorkingPaperQueryCondition info) {
        WorkingPaperTableVO workPagerMLDVO = new WorkingPaperTableVO();
        String systemId = WorkingPaperUtil.getSystemID(info);
        workPagerMLDVO.setSystemId(systemId);
        return BusinessResponseEntity.ok((Object)workPagerMLDVO);
    }

    public BusinessResponseEntity<ConsolidatedOptionVO> getSystemIdBySchemeID(String schemeId, String periodStr) {
        WorkingPaperQueryCondition queryCondition = new WorkingPaperQueryCondition();
        queryCondition.setSchemeID(schemeId);
        queryCondition.setPeriodStr(periodStr);
        String systemId = WorkingPaperUtil.getSystemID(queryCondition);
        ConsolidatedOptionVO optionData = this.consolidatedOptionService.getOptionData(systemId);
        return BusinessResponseEntity.ok((Object)optionData);
    }

    public BusinessResponseEntity<String> getViewKeyByTaskIdAndOrgType(String taskId, String orgType) {
        return BusinessResponseEntity.ok((Object)this.arbitrarilyMergeService.getViewKeyByTaskIdAndOrgType(taskId, orgType));
    }

    @Transactional(rollbackFor={Exception.class})
    public BusinessResponseEntity<String> addQueryScheme(ArbitrarilyMergeQuerySchemeVO vo) {
        String uuid = UUID.randomUUID().toString();
        vo.setId(uuid);
        vo.setOrderNum(Double.valueOf(OrderNumUtil.getOrderNumByCurrentTimeMillis().doubleValue()));
        String settingJsonString = JSONUtil.toJSONString((Object)vo.getSettingList());
        vo.setSettingJson(settingJsonString);
        vo.setResourceId(vo.getResourceId());
        String finalFormula = this.assemblyFormulaUtil.assemblyFormula(vo.getSettingList());
        vo.setFinalFormula(finalFormula);
        this.arbitrarilyMergeService.addQueryScheme(vo);
        return BusinessResponseEntity.ok((Object)"\u4fdd\u5b58\u6210\u529f");
    }

    @Transactional(rollbackFor={Exception.class})
    public BusinessResponseEntity<String> updateQueryScheme(ArbitrarilyMergeQuerySchemeVO vo) {
        String settingJsonString = JSONUtil.toJSONString((Object)vo.getSettingList()).toString();
        vo.setSettingJson(settingJsonString);
        String finalFormula = this.assemblyFormulaUtil.assemblyFormula(vo.getSettingList());
        vo.setFinalFormula(finalFormula);
        this.arbitrarilyMergeService.updateQueryScheme(vo);
        return BusinessResponseEntity.ok((Object)"\u66f4\u65b0\u6210\u529f");
    }

    @Transactional(rollbackFor={Exception.class})
    public BusinessResponseEntity<List<ArbitrarilyMergeQuerySchemeVO>> getQuerySchemeByResourceId(String resourceId) {
        List<ArbitrarilyMergeQuerySchemeVO> list = this.arbitrarilyMergeService.getQuerySchemeByResourceId(resourceId);
        return BusinessResponseEntity.ok(list);
    }

    @Transactional(rollbackFor={Exception.class})
    public BusinessResponseEntity<String> delQuerySchemeById(String id) {
        this.arbitrarilyMergeService.deleteQuerySchemeById(id);
        return BusinessResponseEntity.ok((Object)"\u5220\u9664\u6210\u529f");
    }

    @Transactional(rollbackFor={Exception.class})
    public BusinessResponseEntity<ArbitrarilyMergeQuerySchemeVO> getQuerySchemeById(String id) {
        ArbitrarilyMergeQuerySchemeVO vo = this.arbitrarilyMergeService.getQuerySchemeById(id);
        if (vo != null) {
            String settingJsonString = vo.getSettingJson();
            List settingList = JSONUtil.parseArray((String)settingJsonString, ArbitrarilyMergeOrgFilterSettingVO.class);
            vo.setSettingList(settingList);
        }
        return BusinessResponseEntity.ok((Object)vo);
    }

    @Transactional(rollbackFor={Exception.class})
    public BusinessResponseEntity<String> assemblyFormula(List<ArbitrarilyMergeOrgFilterSettingVO> dataList) {
        String finalFormula = this.assemblyFormulaUtil.assemblyFormula(dataList);
        return BusinessResponseEntity.ok((Object)finalFormula);
    }

    public BusinessResponseEntity<Set<String>> executeFormula(FilteringRequestParam reParam) {
        IRowChecker checker = this.isVaildChecker(reParam.getKeyword());
        IFilterCheckValuesImpl values = reParam.getCheckValues();
        IRowCheckExecutor executor = this.isValidExecuter(checker, reParam.getSelector());
        UnitTreeContextData context = this.contextCache.getUnitTreeContextData(reParam.getSelector());
        IUnitTreeContext treeContext = this.contextBuilder.createTreeContext(context);
        IUSelectorDataSource dataSource = this.sourceHelper.getBaseTreeDataSource(context.getDataSourceId());
        IUSelectorEntityRowProvider entityRowProvider = dataSource.getUSelectorEntityRowProvider(treeContext);
        List filterSet = executor.executeCheck((IFilterCheckValues)values, entityRowProvider);
        String period = context.getPeriod();
        String entityId = context.getEntityId();
        String[] entitySplits = entityId.split("@");
        String categoryName = entitySplits[0];
        YearPeriodObject yp = new YearPeriodObject(null, period);
        GcOrgCenterService orgTool = GcOrgPublicTool.getInstance((String)categoryName, (GcAuthorityType)GcAuthorityType.ACCESS, (YearPeriodObject)yp);
        LinkedHashSet<String> codeAndTitleSet = new LinkedHashSet<String>();
        for (String orgCode : filterSet) {
            GcOrgCacheVO orgCacheVO = orgTool.getOrgByCode(orgCode);
            String title = orgCacheVO.getTitle();
            codeAndTitleSet.add(orgCode + "|" + title);
        }
        return BusinessResponseEntity.ok(codeAndTitleSet);
    }

    @Transactional(rollbackFor={Exception.class})
    public BusinessResponseEntity<String> addOrgFilterSetting(ArbitrarilyMergeOrgFilterSettingVO vo) {
        String uuid = UUID.randomUUID().toString();
        vo.setId(uuid);
        vo.setDataType(ArbitrarilyMergeOrgFilterDataTypeEnum.CUSTOM.getType());
        vo.setOrderNum(Double.valueOf(OrderNumUtil.getOrderNumByCurrentTimeMillis().doubleValue()));
        this.arbitrarilyMergeService.addOrgFilterSetting(vo);
        if (!CollectionUtils.isEmpty(vo.getFormulaList())) {
            this.arbitrarilyMergeService.addBatchFormulaSettingByDataId(vo.getFormulaList(), vo.getId());
        }
        return BusinessResponseEntity.ok((Object)"\u6dfb\u52a0\u6210\u529f");
    }

    @Transactional(rollbackFor={Exception.class})
    public BusinessResponseEntity<String> updateOrgFilterSetting(ArbitrarilyMergeOrgFilterSettingVO vo) {
        this.arbitrarilyMergeService.updateOrgFilterSetting(vo);
        this.arbitrarilyMergeService.deleteFormulaSettingByDataId(vo.getId());
        if (!CollectionUtils.isEmpty(vo.getFormulaList())) {
            this.arbitrarilyMergeService.addBatchFormulaSettingByDataId(vo.getFormulaList(), vo.getId());
        }
        return BusinessResponseEntity.ok((Object)"\u66f4\u65b0\u6210\u529f");
    }

    @Transactional(rollbackFor={Exception.class})
    public BusinessResponseEntity<String> delOrgFilterSettingById(String id) {
        this.arbitrarilyMergeService.deleteOrgFilterSetting(id);
        this.arbitrarilyMergeService.deleteFormulaSettingByDataId(id);
        return BusinessResponseEntity.ok((Object)"\u5220\u9664\u6210\u529f");
    }

    @Transactional(rollbackFor={Exception.class})
    public BusinessResponseEntity<ArbitrarilyMergeOrgFilterSettingVO> getOrgFilterSettingById(String id) {
        ArbitrarilyMergeOrgFilterSettingVO vo = this.arbitrarilyMergeService.getOrgFilterSettingById(id);
        if (vo != null) {
            List<ArbitrarilyMergeOrgFilterCustomFormulaSettingVO> formulaList = this.arbitrarilyMergeService.getFormulaSettingByDataId(id);
            vo.setDataType_(ArbitrarilyMergeOrgFilterDataTypeEnum.getDescByType(vo.getDataType()));
            vo.setControlType_(ArbitrarilyMergeOrgFilterControlTypeEnum.getDescByType(vo.getControlType()));
            vo.setSourceType_(ArbitrarilyMergeOrgFilterDataSourceTypeEnum.getDescByType(vo.getSourceType()));
            vo.setFormulaList(formulaList);
        }
        return BusinessResponseEntity.ok((Object)vo);
    }

    @Transactional(rollbackFor={Exception.class})
    public BusinessResponseEntity<List<ArbitrarilyMergeOrgFilterSettingVO>> getOrgFilterSettingAll() {
        List<Object> data = this.arbitrarilyMergeService.getOrgFilterSettingList();
        for (ArbitrarilyMergeOrgFilterSettingVO vo : data) {
            if (vo == null) continue;
            List<ArbitrarilyMergeOrgFilterCustomFormulaSettingVO> formulaList = this.arbitrarilyMergeService.getFormulaSettingByDataId(vo.getId());
            vo.setDataType_(ArbitrarilyMergeOrgFilterDataTypeEnum.getDescByType(vo.getDataType()));
            vo.setControlType_(ArbitrarilyMergeOrgFilterControlTypeEnum.getDescByType(vo.getControlType()));
            vo.setSourceType_(ArbitrarilyMergeOrgFilterDataSourceTypeEnum.getDescByType(vo.getSourceType()));
            vo.setFormulaList(formulaList);
        }
        data = data.stream().sorted(Comparator.comparing(ArbitrarilyMergeOrgFilterSettingVO::getOrderNum)).collect(Collectors.toList());
        return BusinessResponseEntity.ok(data);
    }

    @Transactional(rollbackFor={Exception.class})
    public BusinessResponseEntity<List<ArbitrarilyMergeOrgFilterSettingVO>> getEnableList() {
        List<Object> data = this.arbitrarilyMergeService.getEnableList();
        for (ArbitrarilyMergeOrgFilterSettingVO vo : data) {
            List<ArbitrarilyMergeOrgFilterCustomFormulaSettingVO> formulaList = this.arbitrarilyMergeService.getFormulaSettingByDataId(vo.getId());
            vo.setDataType_(ArbitrarilyMergeOrgFilterDataTypeEnum.getDescByType(vo.getDataType()));
            vo.setControlType_(ArbitrarilyMergeOrgFilterControlTypeEnum.getDescByType(vo.getControlType()));
            vo.setSourceType_(ArbitrarilyMergeOrgFilterDataSourceTypeEnum.getDescByType(vo.getSourceType()));
            vo.setFormulaList(formulaList);
        }
        data = data.stream().sorted(Comparator.comparing(ArbitrarilyMergeOrgFilterSettingVO::getOrderNum)).collect(Collectors.toList());
        return BusinessResponseEntity.ok(data);
    }

    @Transactional(rollbackFor={Exception.class})
    public BusinessResponseEntity<String> getEnableList(List<ArbitrarilyMergeOrgFilterSettingVO> orderList) {
        for (ArbitrarilyMergeOrgFilterSettingVO vo : orderList) {
            vo.setOrderNum(Double.valueOf(OrderNumUtil.getOrderNumByCurrentTimeMillis().doubleValue()));
            this.arbitrarilyMergeService.updateOrgFilterSetting(vo);
        }
        return BusinessResponseEntity.ok((Object)"\u64cd\u4f5c\u6210\u529f");
    }

    @Transactional(rollbackFor={Exception.class})
    public BusinessResponseEntity<List<ArbitrarilyMergeOrgFilterCustomFormulaSettingVO>> getExpressList(String id) {
        List<Object> list = this.arbitrarilyMergeService.getFormulaSettingByDataId(id);
        list = list.stream().sorted(Comparator.comparing(ArbitrarilyMergeOrgFilterCustomFormulaSettingVO::getOrderNum)).collect(Collectors.toList());
        return BusinessResponseEntity.ok(list);
    }

    public BusinessResponseEntity<Object> getDataType() {
        Map<String, String> map = ArbitrarilyMergeOrgFilterDataTypeEnum.getAllStatusCode();
        return BusinessResponseEntity.ok(map);
    }

    public BusinessResponseEntity<Object> getDataControlType() {
        Map<String, String> map = ArbitrarilyMergeOrgFilterControlTypeEnum.getAllStatusCode();
        return BusinessResponseEntity.ok(map);
    }

    public BusinessResponseEntity<Object> getDataSourceType() {
        Map<String, String> map = ArbitrarilyMergeOrgFilterDataSourceTypeEnum.getAllStatusCode();
        return BusinessResponseEntity.ok(map);
    }

    @Transactional(rollbackFor={Exception.class})
    public BusinessResponseEntity<Object> addRyInputAdjust(List<List<ArbitrarilyMergeInputAdjustVO>> batchlist) {
        this.arbitrarilyMergeService.addRyInputAdjust(batchlist);
        return BusinessResponseEntity.ok();
    }

    @Transactional(rollbackFor={Exception.class})
    public BusinessResponseEntity<String> deleteRyOffsetEntrysByMrecid(ArbitrarilyMergeDelParamsVO arbitrarilyMergeDelParamsVO) {
        List mrecids = arbitrarilyMergeDelParamsVO.getMrecids();
        this.arbitrarilyMergeService.deleteRyAdjustInputByMredid(arbitrarilyMergeDelParamsVO.getTaskId(), mrecids, arbitrarilyMergeDelParamsVO.getAcctYear(), 1, arbitrarilyMergeDelParamsVO.getAcctPeriod(), null, arbitrarilyMergeDelParamsVO.getCurrency(), OffSetSrcTypeEnum.RYHB_MODIFIED_INPUT.getSrcTypeValue(), arbitrarilyMergeDelParamsVO.getSelectAdjustCode());
        return BusinessResponseEntity.ok((Object)"\u53d6\u6d88\u6210\u529f.");
    }

    public BusinessResponseEntity<List<ArbitrarilyMergeInputAdjustVO>> queryRyDetailByMrecid(ArbitrarilyMergeInputAdjustQueryCondi ryCondition) {
        return BusinessResponseEntity.ok(this.arbitrarilyMergeService.queryRyDetailByMrecid(ryCondition));
    }

    @Transactional(rollbackFor={Exception.class})
    public BusinessResponseEntity<Pagination<Map<String, Object>>> getRyOffsetEntry(WorkingPaperPentrationQueryCondtion penInfo) {
        return BusinessResponseEntity.ok(this.arbitrarilyMergeService.listRyPentrationDatas(penInfo));
    }

    @Transactional(rollbackFor={Exception.class})
    public BusinessResponseEntity<Pagination<Map<String, Object>>> getRyOffsetEntryOther(WorkingPaperPentrationQueryCondtion ryPenInfo) {
        return BusinessResponseEntity.ok(this.arbitrarilyMergeService.listRyPentrationDatasOther(ryPenInfo));
    }

    public BusinessResponseEntity<List<GcOrgCacheVO>> getMergeRangeOrgsWithOutCE(ArbitrarilyMergeOrgParamVO orgParamVo) {
        List<GcOrgCacheVO> orgDetilByOrgCodes = this.arbitrarilyMergeService.getOrgByOrgCodes(orgParamVo);
        List cacheVOS = orgDetilByOrgCodes.stream().filter(org -> !GcOrgKindEnum.DIFFERENCE.equals((Object)org.getOrgKind())).collect(Collectors.toList());
        return BusinessResponseEntity.ok(cacheVOS);
    }

    private IUnitTreeContext isRuntimeSelector(String selector) {
        IUnitTreeContext context = this.contextHelper.getRunContext(selector);
        if (null == context) {
            throw new UnitTreeRuntimeException("\u65e0\u6548\u7684\u8fd0\u884c\u5668\u5355\u4f4d\u9009\u62e9\u5668\uff01\u3010" + selector + "\u3011");
        }
        return context;
    }

    private IRowCheckExecutor isValidExecuter(IRowChecker checker, String selector) {
        IUnitTreeContext context = this.uSelectorResultSet.getRunContext(selector);
        IRowCheckExecutor executer = checker.getExecutor(context);
        if (null == executer) {
            throw new UnitTreeRuntimeException("\u65e0\u6548\u7684\u7b5b\u9009\u5b9e\u73b0\uff01\u3010" + checker.getClass().getName() + "\u3011");
        }
        return executer;
    }

    private IRowChecker isVaildChecker(String checkerKeyword) {
        IRowChecker checker = this.checkerHelper.getChecker(checkerKeyword);
        if (null == checker) {
            throw new UnitTreeRuntimeException("\u65e0\u6548\u7684\u7b5b\u9009key\uff01\u3010" + checkerKeyword + "\u3011");
        }
        return checker;
    }
}

