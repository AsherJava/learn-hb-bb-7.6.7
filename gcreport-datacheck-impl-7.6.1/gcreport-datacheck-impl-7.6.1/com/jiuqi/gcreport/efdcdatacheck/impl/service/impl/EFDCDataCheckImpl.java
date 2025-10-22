/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.syntax.reportparser.ReportFormulaParser
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.ConverterUtils
 *  com.jiuqi.common.base.util.NumberUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet
 *  com.jiuqi.gcreport.basedata.impl.bean.GcBaseData
 *  com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool
 *  com.jiuqi.gcreport.common.GCOrgTypeEnum
 *  com.jiuqi.gcreport.common.util.DimensionUtils
 *  com.jiuqi.gcreport.common.util.JsonUtils
 *  com.jiuqi.gcreport.common.util.MapUtils
 *  com.jiuqi.gcreport.common.util.UUIDOrderUtils
 *  com.jiuqi.gcreport.efdcdatacheck.client.vo.ConversionSystemItemInfoVO
 *  com.jiuqi.gcreport.efdcdatacheck.client.vo.EfdcCheckConfigFormInfo
 *  com.jiuqi.gcreport.efdcdatacheck.client.vo.EfdcCheckConfigVO
 *  com.jiuqi.gcreport.efdcdatacheck.client.vo.EfdcCheckResultGroupVO
 *  com.jiuqi.gcreport.efdcdatacheck.client.vo.EfdcCheckResultUnitVO
 *  com.jiuqi.gcreport.efdcdatacheck.client.vo.EfdcCheckResultVO
 *  com.jiuqi.gcreport.efdcdatacheck.client.vo.FormVo
 *  com.jiuqi.gcreport.efdcdatacheck.client.vo.GcBatchEfdcCheckInfo
 *  com.jiuqi.gcreport.efdcdatacheck.client.vo.GcBatchEfdcQueryParam
 *  com.jiuqi.gcreport.efdcdatacheck.client.vo.GcFormOperationInfo
 *  com.jiuqi.gcreport.efdcdatacheck.client.vo.TaskFormGroupFieldsInfoVO
 *  com.jiuqi.gcreport.efdcdatacheck.client.vo.TaskFormInfoVO
 *  com.jiuqi.gcreport.efdcdatacheck.env.GcFetchDataEnvContext
 *  com.jiuqi.gcreport.efdcdatacheck.env.impl.GcFetchDataEnvContextImpl
 *  com.jiuqi.gcreport.efdcdatacheck.vo.GcFetchDataInfo
 *  com.jiuqi.gcreport.efdcdatacheck.vo.GcFetchDataResultInfo
 *  com.jiuqi.gcreport.i18n.util.GcI18nUtil
 *  com.jiuqi.gcreport.listedcompanyauthz.service.FListedCompanyAuthzService
 *  com.jiuqi.gcreport.nr.impl.service.GCFormTabSelectService
 *  com.jiuqi.gcreport.nr.impl.service.impl.GCFormTabSelectServiceImpl
 *  com.jiuqi.gcreport.nr.impl.util.GcOrgTypeUtils
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.core.context.ContextUser
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.np.dataengine.data.FloatData
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IDataQuery
 *  com.jiuqi.np.dataengine.intf.IDataRow
 *  com.jiuqi.np.dataengine.intf.IDataTable
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.dataengine.node.DynamicDataNode
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.np.definition.common.FieldType
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.np.definition.facade.TableDefine
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.np.util.tree.Tree
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.dataentry.paramInfo.BatchDimensionValueFormInfo
 *  com.jiuqi.nr.dataentry.paramInfo.FormGroupData
 *  com.jiuqi.nr.dataentry.paramInfo.NoAccessFormInfo
 *  com.jiuqi.nr.dataentry.service.IDataEntryParamService
 *  com.jiuqi.nr.dataentry.tree.FormTree
 *  com.jiuqi.nr.dataentry.tree.FormTreeItem
 *  com.jiuqi.nr.datascheme.internal.dto.DataFieldDTO
 *  com.jiuqi.nr.definition.common.DataLinkType
 *  com.jiuqi.nr.definition.common.DataRegionKind
 *  com.jiuqi.nr.definition.controller.IDesignTimeViewController
 *  com.jiuqi.nr.definition.controller.IFormulaRunTimeController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nr.definition.facade.DesignDataLinkDefine
 *  com.jiuqi.nr.definition.facade.DesignDataRegionDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormGroupDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.FormulaDefine
 *  com.jiuqi.nr.definition.facade.FormulaSchemeDefine
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 *  com.jiuqi.nr.efdc.extract.impl.request.ExpressionListing
 *  com.jiuqi.nr.efdc.extract.impl.request.FixExpression
 *  com.jiuqi.nr.efdc.extract.impl.response.FixExpResult
 *  com.jiuqi.nr.efdc.internal.pojo.QueryObjectImpl
 *  com.jiuqi.nr.efdc.service.IEFDCConfigService
 *  com.jiuqi.nr.jtable.params.base.EntityViewData
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nr.jtable.service.IJtableParamService
 *  com.jiuqi.nr.jtable.util.DimensionValueSetUtil
 *  com.jiuqi.nr.period.common.utils.PeriodUtils
 *  com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.org.OrgDO
 *  com.jiuqi.va.domain.org.OrgDTO
 *  com.jiuqi.va.domain.org.OrgDataOption$AuthType
 *  com.jiuqi.va.domain.org.OrgDataOption$QueryDataStructure
 *  com.jiuqi.va.organization.service.impl.help.OrgDataQueryService
 *  javax.servlet.http.HttpServletResponse
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.efdcdatacheck.impl.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.syntax.reportparser.ReportFormulaParser;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.common.base.util.NumberUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet;
import com.jiuqi.gcreport.basedata.impl.bean.GcBaseData;
import com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool;
import com.jiuqi.gcreport.common.GCOrgTypeEnum;
import com.jiuqi.gcreport.common.util.DimensionUtils;
import com.jiuqi.gcreport.common.util.JsonUtils;
import com.jiuqi.gcreport.common.util.MapUtils;
import com.jiuqi.gcreport.common.util.UUIDOrderUtils;
import com.jiuqi.gcreport.efdcdatacheck.client.vo.ConversionSystemItemInfoVO;
import com.jiuqi.gcreport.efdcdatacheck.client.vo.EfdcCheckConfigFormInfo;
import com.jiuqi.gcreport.efdcdatacheck.client.vo.EfdcCheckConfigVO;
import com.jiuqi.gcreport.efdcdatacheck.client.vo.EfdcCheckResultGroupVO;
import com.jiuqi.gcreport.efdcdatacheck.client.vo.EfdcCheckResultUnitVO;
import com.jiuqi.gcreport.efdcdatacheck.client.vo.EfdcCheckResultVO;
import com.jiuqi.gcreport.efdcdatacheck.client.vo.FormVo;
import com.jiuqi.gcreport.efdcdatacheck.client.vo.GcBatchEfdcCheckInfo;
import com.jiuqi.gcreport.efdcdatacheck.client.vo.GcBatchEfdcQueryParam;
import com.jiuqi.gcreport.efdcdatacheck.client.vo.GcFormOperationInfo;
import com.jiuqi.gcreport.efdcdatacheck.client.vo.TaskFormGroupFieldsInfoVO;
import com.jiuqi.gcreport.efdcdatacheck.client.vo.TaskFormInfoVO;
import com.jiuqi.gcreport.efdcdatacheck.env.GcFetchDataEnvContext;
import com.jiuqi.gcreport.efdcdatacheck.env.impl.GcFetchDataEnvContextImpl;
import com.jiuqi.gcreport.efdcdatacheck.impl.dao.DataCheckConfigDAO;
import com.jiuqi.gcreport.efdcdatacheck.impl.dao.DataCheckResultDAO;
import com.jiuqi.gcreport.efdcdatacheck.impl.dto.GcDataCheckResultDTO;
import com.jiuqi.gcreport.efdcdatacheck.impl.entity.DataCheckConfigEO;
import com.jiuqi.gcreport.efdcdatacheck.impl.entity.EfdcCheckResultEO;
import com.jiuqi.gcreport.efdcdatacheck.impl.service.EfdcDataCheckService;
import com.jiuqi.gcreport.efdcdatacheck.impl.service.GcBatchCheckParallelMonitor;
import com.jiuqi.gcreport.efdcdatacheck.impl.service.GcFetchDataService;
import com.jiuqi.gcreport.efdcdatacheck.impl.utils.GcEfdcDataCheckSemaphoreUtil;
import com.jiuqi.gcreport.efdcdatacheck.impl.utils.GcEfdcDataCheckUtils;
import com.jiuqi.gcreport.efdcdatacheck.vo.GcFetchDataInfo;
import com.jiuqi.gcreport.efdcdatacheck.vo.GcFetchDataResultInfo;
import com.jiuqi.gcreport.i18n.util.GcI18nUtil;
import com.jiuqi.gcreport.listedcompanyauthz.service.FListedCompanyAuthzService;
import com.jiuqi.gcreport.nr.impl.service.GCFormTabSelectService;
import com.jiuqi.gcreport.nr.impl.service.impl.GCFormTabSelectServiceImpl;
import com.jiuqi.gcreport.nr.impl.util.GcOrgTypeUtils;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.core.context.ContextUser;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.data.FloatData;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IDataQuery;
import com.jiuqi.np.dataengine.intf.IDataRow;
import com.jiuqi.np.dataengine.intf.IDataTable;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.dataengine.node.DynamicDataNode;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.definition.common.FieldType;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.np.definition.facade.TableDefine;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.np.util.tree.Tree;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.dataentry.paramInfo.BatchDimensionValueFormInfo;
import com.jiuqi.nr.dataentry.paramInfo.FormGroupData;
import com.jiuqi.nr.dataentry.paramInfo.NoAccessFormInfo;
import com.jiuqi.nr.dataentry.service.IDataEntryParamService;
import com.jiuqi.nr.dataentry.tree.FormTree;
import com.jiuqi.nr.dataentry.tree.FormTreeItem;
import com.jiuqi.nr.datascheme.internal.dto.DataFieldDTO;
import com.jiuqi.nr.definition.common.DataLinkType;
import com.jiuqi.nr.definition.common.DataRegionKind;
import com.jiuqi.nr.definition.controller.IDesignTimeViewController;
import com.jiuqi.nr.definition.controller.IFormulaRunTimeController;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.facade.DesignDataLinkDefine;
import com.jiuqi.nr.definition.facade.DesignDataRegionDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormGroupDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.FormulaDefine;
import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nr.efdc.extract.impl.request.ExpressionListing;
import com.jiuqi.nr.efdc.extract.impl.request.FixExpression;
import com.jiuqi.nr.efdc.extract.impl.response.FixExpResult;
import com.jiuqi.nr.efdc.internal.pojo.QueryObjectImpl;
import com.jiuqi.nr.efdc.service.IEFDCConfigService;
import com.jiuqi.nr.jtable.params.base.EntityViewData;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.service.IJtableParamService;
import com.jiuqi.nr.jtable.util.DimensionValueSetUtil;
import com.jiuqi.nr.period.common.utils.PeriodUtils;
import com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.org.OrgDO;
import com.jiuqi.va.domain.org.OrgDTO;
import com.jiuqi.va.domain.org.OrgDataOption;
import com.jiuqi.va.organization.service.impl.help.OrgDataQueryService;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StopWatch;

@Service
@Scope(value="prototype", proxyMode=ScopedProxyMode.TARGET_CLASS)
public class EFDCDataCheckImpl
implements EfdcDataCheckService {
    private static final Logger logger = LoggerFactory.getLogger(EFDCDataCheckImpl.class);
    private static final String CURRENCY_ID = "currencyid";
    private static final String REPORT_CURRENCY_ID = "currencyids";
    private IRunTimeViewController runTimeViewController;
    private IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    private INvwaSystemOptionService systemOptionsService;
    private DataCheckConfigDAO dataCheckConfigDAO;
    private DataCheckResultDAO dataCheckResultDAO;
    private GcBatchCheckParallelMonitor monitor;
    private GCFormTabSelectService formTabSelectService;
    private IJtableParamService jtableParamService;
    private IEFDCConfigService efdcConfigServiceImpl;
    private OrgDataQueryService orgDataQueryService;
    private AtomicInteger checkZbCount = new AtomicInteger(0);
    private AtomicInteger failZbCount = new AtomicInteger(0);
    private Map<String, Integer> form2CheckZbCount = new ConcurrentHashMap<String, Integer>();
    private Map<String, Integer> form2FailZbCount = new ConcurrentHashMap<String, Integer>();
    private Map<String, Integer> form2CheckZbAllOrgCount = new ConcurrentHashMap<String, Integer>();
    private Map<String, Integer> form2FailZbAllOrgCount = new ConcurrentHashMap<String, Integer>();
    private Map<String, Set<String>> reportId2ZbGuidMap;
    private Set<String> errorUnitSet = new CopyOnWriteArraySet<String>();
    private Set<String> errorFormKeySet = new CopyOnWriteArraySet<String>();
    private StringBuffer log = new StringBuffer(512);
    private AsyncTaskMonitor asyncTaskMonitor;
    private IDataEntryParamService dataEntryParamService;
    private IJtableParamService entityService;
    private IDesignTimeViewController designTimeViewController;
    private IDataAccessProvider dataAccessProvider;
    private IEntityViewRunTimeController entityViewRunTimeController;
    private IFormulaRunTimeController formulaRunTimeController;
    private GcFetchDataService fetchDataService;
    private FListedCompanyAuthzService listedCompanyAuthzService;
    private Map<String, String> currencyCodeAndTitle = new HashMap<String, String>(16);

    public EFDCDataCheckImpl() {
        this.runTimeViewController = (IRunTimeViewController)SpringContextUtils.getBean(IRunTimeViewController.class);
        this.systemOptionsService = (INvwaSystemOptionService)SpringContextUtils.getBean(INvwaSystemOptionService.class);
        this.dataCheckConfigDAO = (DataCheckConfigDAO)SpringContextUtils.getBean(DataCheckConfigDAO.class);
        this.dataCheckResultDAO = (DataCheckResultDAO)SpringContextUtils.getBean(DataCheckResultDAO.class);
        this.dataDefinitionRuntimeController = (IDataDefinitionRuntimeController)SpringContextUtils.getBean(IDataDefinitionRuntimeController.class);
        this.dataEntryParamService = (IDataEntryParamService)SpringContextUtils.getBean(IDataEntryParamService.class);
        this.formTabSelectService = (GCFormTabSelectService)SpringContextUtils.getBean(GCFormTabSelectService.class);
        this.entityService = (IJtableParamService)SpringContextUtils.getBean(IJtableParamService.class);
        this.designTimeViewController = (IDesignTimeViewController)SpringContextUtils.getBean(IDesignTimeViewController.class);
        this.dataAccessProvider = (IDataAccessProvider)SpringContextUtils.getBean(IDataAccessProvider.class);
        this.entityViewRunTimeController = (IEntityViewRunTimeController)SpringContextUtils.getBean(IEntityViewRunTimeController.class);
        this.formulaRunTimeController = (IFormulaRunTimeController)SpringContextUtils.getBean(IFormulaRunTimeController.class);
        this.jtableParamService = (IJtableParamService)SpringContextUtils.getBean(IJtableParamService.class);
        this.efdcConfigServiceImpl = (IEFDCConfigService)SpringContextUtils.getBean(IEFDCConfigService.class);
        this.orgDataQueryService = (OrgDataQueryService)SpringContextUtils.getBean(OrgDataQueryService.class);
        this.fetchDataService = (GcFetchDataService)SpringContextUtils.getBean(GcFetchDataService.class);
        this.listedCompanyAuthzService = (FListedCompanyAuthzService)SpringContextUtils.getBean(FListedCompanyAuthzService.class);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Transactional(rollbackFor={Exception.class})
    public List<ExportExcelSheet> exportExcel(GcFormOperationInfo formOperationInfo) {
        NpContext context = NpContextHolder.getContext();
        EfdcCheckResultGroupVO resultGroupVO = this.processEfdcDataCheckResultGroup(context, formOperationInfo);
        ArrayList<ExportExcelSheet> excelSheets = new ArrayList<ExportExcelSheet>();
        CellType[] cellTypes = new CellType[]{CellType.STRING, CellType.STRING, CellType.STRING, CellType.STRING, CellType.STRING, CellType.NUMERIC, CellType.NUMERIC, CellType.NUMERIC};
        ArrayList<Object[]> rowDatas = new ArrayList<Object[]>();
        try {
            String[] titles = new String[]{GcI18nUtil.getMessage((String)"gc.efdcDataCheck.unitNumber"), GcI18nUtil.getMessage((String)"gc.efdcDataCheck.unitName"), GcI18nUtil.getMessage((String)"gc.efdcDataCheck.reportName"), GcI18nUtil.getMessage((String)"gc.efdcDataCheck.reportSubject"), GcI18nUtil.getMessage((String)"gc.efdcDataCheck.currency"), GcI18nUtil.getMessage((String)"gc.efdcDataCheck.reportData"), GcI18nUtil.getMessage((String)"gc.efdcDataCheck.bookData"), GcI18nUtil.getMessage((String)"gc.efdcDataCheck.difference")};
            rowDatas.add(titles);
            NpContextHolder.setContext((NpContext)context);
            String unitId = (String)formOperationInfo.getDimensionValueSet().getValue("MD_ORG");
            String period = (String)formOperationInfo.getDimensionValueSet().getValue("DATATIME");
            String orgType = (String)formOperationInfo.getDimensionValueSet().getValue("MD_GCORGTYPE");
            int sheetIndex = 0;
            YearPeriodObject yp = new YearPeriodObject(null, period);
            GcOrgCenterService tool = GcOrgPublicTool.getInstance((String)orgType, (GcAuthorityType)GcAuthorityType.ACCESS, (YearPeriodObject)yp);
            GcOrgCacheVO orgByCode = tool.getOrgByCode(unitId);
            ExportExcelSheet exportExcelSheet = new ExportExcelSheet(Integer.valueOf(0), GcI18nUtil.getMessage((String)"gc.efdcDataCheck.auditResults"), Integer.valueOf(1));
            for (int i = 0; i < cellTypes.length; ++i) {
                exportExcelSheet.getContentCellTypeCache().put(i, cellTypes[i]);
            }
            for (List checkResultVOs : resultGroupVO.getEfdcCheckResultVOs()) {
                for (EfdcCheckResultVO efdcCheckResultVO : checkResultVOs) {
                    if (rowDatas.size() > 50000) {
                        ++sheetIndex;
                        exportExcelSheet.getRowDatas().addAll(rowDatas);
                        excelSheets.add(exportExcelSheet);
                        rowDatas = new ArrayList();
                        exportExcelSheet = new ExportExcelSheet(Integer.valueOf(sheetIndex), GcI18nUtil.getMessage((String)"gc.efdcDataCheck.auditResults") + sheetIndex, Integer.valueOf(1));
                    }
                    Object[] dataRow = new Object[8];
                    if (null != orgByCode) {
                        dataRow[0] = orgByCode.getCode();
                        dataRow[1] = orgByCode.getTitle();
                    }
                    dataRow[2] = efdcCheckResultVO.getFormTitle();
                    dataRow[3] = efdcCheckResultVO.getZbTitle();
                    if (orgByCode != null) {
                        dataRow[4] = String.valueOf(orgByCode.getTypeFieldValue("CURRENCYID"));
                    }
                    dataRow[5] = Double.valueOf(efdcCheckResultVO.getZbValue().replaceAll(",", ""));
                    dataRow[6] = Double.valueOf(efdcCheckResultVO.getEfdcValue().replaceAll(",", ""));
                    dataRow[7] = Double.valueOf(efdcCheckResultVO.getDiffValue().replaceAll(",", ""));
                    rowDatas.add(dataRow);
                }
            }
            exportExcelSheet.getRowDatas().addAll(rowDatas);
            excelSheets.add(exportExcelSheet);
        }
        catch (Exception e) {
            logger.error("", e);
        }
        finally {
            NpContextHolder.clearContext();
        }
        return excelSheets;
    }

    public void batchResultExportExcel(GcBatchEfdcQueryParam efdcQueryParam, HttpServletResponse response) {
        efdcQueryParam.setPageSize(-1);
        List<EfdcCheckResultVO> efdcCheckResultVOs = this.efdcBatchCheckResult(efdcQueryParam);
        HSSFWorkbook workbook = new HSSFWorkbook();
        try {
            int rowIndex = 0;
            HSSFPalette customPalette = workbook.getCustomPalette();
            customPalette.setColorAtIndex(HSSFColor.HSSFColorPredefined.ORANGE.getIndex(), (byte)-21, (byte)-18, (byte)-11);
            customPalette.setColorAtIndex(HSSFColor.HSSFColorPredefined.BLUE.getIndex(), (byte)-11, (byte)-11, (byte)-11);
            HSSFCellStyle text = GcEfdcDataCheckUtils.getExelCellStyle(workbook, "text");
            HSSFCellStyle amount = GcEfdcDataCheckUtils.getExelCellStyle(workbook, "amount");
            HSSFCellStyle[] styles = new HSSFCellStyle[]{text, text, text, text, text, amount, amount, amount};
            HSSFSheet sheet = workbook.createSheet(GcI18nUtil.getMessage((String)"gc.efdcDataCheck.auditResults"));
            HSSFRow titleRow = sheet.createRow(rowIndex++);
            this.excelHead(sheet, titleRow, false);
            HashMap<String, GcOrgCacheVO> orgCacheMap = new HashMap<String, GcOrgCacheVO>();
            YearPeriodObject yp = new YearPeriodObject(null, "");
            GcOrgCenterService tool = GcOrgPublicTool.getInstance(null, (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
            int sheetIndex = 2;
            for (EfdcCheckResultVO efdcCheckResultVO : efdcCheckResultVOs) {
                if (!orgCacheMap.containsKey(efdcCheckResultVO.getOrgId())) {
                    orgCacheMap.put(efdcCheckResultVO.getOrgId(), tool.getOrgByCode(efdcCheckResultVO.getOrgId()));
                }
                if (rowIndex > 50000) {
                    rowIndex = 0;
                    sheet = workbook.createSheet(GcI18nUtil.getMessage((String)"gc.efdcDataCheck.auditResults") + sheetIndex++);
                }
                HSSFRow dataRow = sheet.createRow(rowIndex++);
                this.excelOneRow(dataRow, styles, efdcCheckResultVO, (GcOrgCacheVO)orgCacheMap.get(efdcCheckResultVO.getOrgId()), false);
            }
        }
        catch (Exception e) {
            logger.error("", e);
        }
        response.setContentType("application/octet-stream");
        try {
            response.flushBuffer();
            workbook.write((OutputStream)response.getOutputStream());
        }
        catch (IOException e) {
            logger.error("", e);
        }
    }

    private void excelHead(HSSFSheet sheet, HSSFRow titleRow, boolean groupByReport) {
        String[] titles;
        int[] widths;
        HSSFCellStyle header = GcEfdcDataCheckUtils.getExelCellStyle(sheet.getWorkbook(), "head");
        if (groupByReport) {
            widths = new int[]{9000, 4000, 9000, 9000, 4000, 4000, 4000, 4000};
            titles = new String[]{GcI18nUtil.getMessage((String)"gc.efdcDataCheck.reportName"), GcI18nUtil.getMessage((String)"gc.efdcDataCheck.unitNumber"), GcI18nUtil.getMessage((String)"gc.efdcDataCheck.unitName"), GcI18nUtil.getMessage((String)"gc.efdcDataCheck.reportSubject"), GcI18nUtil.getMessage((String)"gc.efdcDataCheck.currency"), GcI18nUtil.getMessage((String)"gc.efdcDataCheck.reportData"), GcI18nUtil.getMessage((String)"gc.efdcDataCheck.bookData"), GcI18nUtil.getMessage((String)"gc.efdcDataCheck.difference")};
        } else {
            widths = new int[]{4000, 9000, 9000, 9000, 4000, 4000, 4000, 4000};
            titles = new String[]{GcI18nUtil.getMessage((String)"gc.efdcDataCheck.unitNumber"), GcI18nUtil.getMessage((String)"gc.efdcDataCheck.unitName"), GcI18nUtil.getMessage((String)"gc.efdcDataCheck.reportName"), GcI18nUtil.getMessage((String)"gc.efdcDataCheck.reportSubject"), GcI18nUtil.getMessage((String)"gc.efdcDataCheck.currency"), GcI18nUtil.getMessage((String)"gc.efdcDataCheck.reportData"), GcI18nUtil.getMessage((String)"gc.efdcDataCheck.bookData"), GcI18nUtil.getMessage((String)"gc.efdcDataCheck.difference")};
        }
        for (int i = 0; i < titles.length; ++i) {
            HSSFCell cell = titleRow.createCell(i);
            cell.setCellValue(titles[i]);
            cell.setCellStyle(header);
            sheet.setColumnWidth(i, widths[i]);
        }
    }

    private void excelOneRow(HSSFRow dataRow, HSSFCellStyle[] styles, EfdcCheckResultVO efdcCheckResultVO, GcOrgCacheVO orgByCode, boolean groupByReport) {
        HSSFCell cell = dataRow.createCell(0);
        if (groupByReport) {
            cell.setCellValue(efdcCheckResultVO.getFormTitle());
            cell.setCellStyle(styles[0]);
            if (null != orgByCode) {
                cell = dataRow.createCell(1);
                cell.setCellValue(orgByCode.getCode());
                cell.setCellStyle(styles[1]);
                cell = dataRow.createCell(2);
                cell.setCellValue(orgByCode.getTitle());
                cell.setCellStyle(styles[2]);
            }
        } else {
            if (null != orgByCode) {
                cell.setCellValue(orgByCode.getCode());
                cell.setCellStyle(styles[0]);
                cell = dataRow.createCell(1);
                cell.setCellValue(orgByCode.getTitle());
                cell.setCellStyle(styles[1]);
            }
            cell = dataRow.createCell(2);
            cell.setCellValue(efdcCheckResultVO.getFormTitle());
            cell.setCellStyle(styles[2]);
        }
        cell = dataRow.createCell(3);
        cell.setCellValue(efdcCheckResultVO.getZbTitle());
        cell.setCellStyle(styles[3]);
        if (orgByCode != null) {
            cell = dataRow.createCell(4);
            cell.setCellValue(this.getCurrencyName(efdcCheckResultVO.getCurrency()));
            cell.setCellStyle(styles[4]);
        }
        cell = dataRow.createCell(5);
        cell.setCellValue(Double.valueOf(efdcCheckResultVO.getZbValue().replaceAll(",", "")));
        cell.setCellStyle(styles[5]);
        cell = dataRow.createCell(6);
        cell.setCellValue(Double.valueOf(efdcCheckResultVO.getEfdcValue().replaceAll(",", "")));
        cell.setCellStyle(styles[6]);
        cell = dataRow.createCell(7);
        cell.setCellValue(Double.valueOf(efdcCheckResultVO.getDiffValue().replaceAll(",", "")));
        cell.setCellStyle(styles[7]);
    }

    @Override
    public EfdcCheckResultGroupVO processEfdcDataCheckResultGroup(NpContext context, GcFormOperationInfo formOperationInfo) {
        this.checkZbCount.set(0);
        this.failZbCount.set(0);
        EfdcCheckResultGroupVO efdcCheckResultGroupVO = new EfdcCheckResultGroupVO();
        List<String> formList = this.filterFormList(formOperationInfo.getFormKeys(), formOperationInfo.getFormSchemeKey());
        if (CollectionUtils.isEmpty(formList)) {
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.efdcDataCheck.checkEfdcSetting"));
        }
        this.reconstructReportZbDataMap(formOperationInfo.getReportZbDataMap());
        formOperationInfo.setReportZbDataMap(this.filterReportZbData(formOperationInfo.getReportZbDataMap(), formList));
        this.initDimensionValue(formOperationInfo);
        String orgId = ConverterUtils.getAsString((Object)formOperationInfo.getDimensionValueSet().getValue("MD_ORG"));
        String contextEntityId = formOperationInfo.getContextEntityId();
        boolean penetratePermission = this.listedCompanyAuthzService.checkPenetratePermission(orgId, contextEntityId);
        if (!penetratePermission) {
            String errorMessage = GcI18nUtil.getMessage((String)"gc.efdcDataCheck.checkPenetratePermission", (Object[])new Object[]{orgId});
            logger.info(errorMessage);
            throw new BusinessRuntimeException(errorMessage);
        }
        HashMap<String, String> formulaSchemeKeyMap = new HashMap<String, String>(formList.size());
        GCFormTabSelectServiceImpl formTabSelectService = (GCFormTabSelectServiceImpl)SpringContextUtils.getBean(GCFormTabSelectServiceImpl.class);
        JtableContext jtableContext = new JtableContext();
        jtableContext.setFormSchemeKey(formOperationInfo.getFormSchemeKey());
        jtableContext.setDimensionSet(formOperationInfo.getDimensionSet());
        for (String formKey : formList) {
            boolean formCondition;
            FormDefine formDefine = this.runTimeViewController.queryFormById(formKey);
            if (formDefine == null || !(formCondition = formTabSelectService.isFormCondition(jtableContext, formDefine.getFormCondition(), formOperationInfo.getDimensionValueSet()))) continue;
            formOperationInfo.setFormKey(formKey);
            try {
                GcOrgTypeUtils.setContextEntityId((String)contextEntityId);
                NpContextHolder.setContext((NpContext)context);
                BatchDimensionValueFormInfo batchDimensionValueFormInfo = new BatchDimensionValueFormInfo();
                if (!CollectionUtils.isEmpty((Collection)batchDimensionValueFormInfo.getNoAccessReasons())) {
                    logger.info(GcI18nUtil.getMessage((String)"gc.efdcDataCheck.skippingFormReason", (Object[])new Object[]{((NoAccessFormInfo)batchDimensionValueFormInfo.getNoAccessReasons().get(0)).getFormKey(), ((NoAccessFormInfo)batchDimensionValueFormInfo.getNoAccessReasons().get(0)).getReason()}));
                    continue;
                }
                FormVo formVo = new FormVo();
                BeanUtils.copyProperties(formDefine, formVo);
                DimensionValueSet dimensionValueSet = formOperationInfo.getDimensionValueSet();
                List<OrgDO> allOrgInfo = this.getAllOrgInfoByUnitIds(orgId, contextEntityId, ConverterUtils.getAsString((Object)dimensionValueSet.getValue("DATATIME")));
                OrgDO org = allOrgInfo.get(0);
                ConcurrentHashMap<String, Object> dimensionValueMap = this.getDimensionValueMap(formOperationInfo.getDimensionValueSet());
                FormulaSchemeDefine formulaScheme = this.getFormulaScheme(formOperationInfo, org.getCode());
                if (formulaScheme == null || StringUtils.isEmpty((String)formulaScheme.getKey())) {
                    String errorMsg = this.errorUnitSet.contains(org.getCode()) ? "" : GcI18nUtil.getMessage((String)"gc.efdcDataCheck.noRequiredAudit", (Object[])new Object[]{org.getCode()});
                    logger.error("\u5355\u4f4d\uff1a{} & \u8868\u5355\uff1a{} & \u5e01\u79cd\uff1a{} \u672a\u627e\u5230\u8d22\u52a1\u63d0\u53d6\u65b9\u6848\uff0c\u65e0\u9700\u7a3d\u6838\u3002", org.getCode(), formDefine.getTitle(), ConverterUtils.getAsString((Object)dimensionValueSet.getValue("MD_CURRENCY")));
                    continue;
                }
                formulaSchemeKeyMap.put(formKey, formulaScheme.getKey());
                DimensionValueSet dimensionValueSetCopy = new DimensionValueSet(dimensionValueSet);
                GcDataCheckResultDTO dataCheckResult = this.getDataCheckResult(context, org, formulaScheme, dimensionValueSetCopy, formOperationInfo, dimensionValueMap, formDefine).get();
                if (ObjectUtils.isEmpty(dataCheckResult)) continue;
                List<EfdcCheckResultVO> efdcCheckResultVOS = dataCheckResult.getEfdcCheckResultVOS();
                List<FieldDefine> retEfdcZbFieldDefines = dataCheckResult.getRetEfdcZbFieldDefines();
                this.form2CheckZbCount.put(formKey, retEfdcZbFieldDefines.size());
                this.mapAddValue(this.form2CheckZbAllOrgCount, formKey, retEfdcZbFieldDefines.size());
                this.mapAddValue(this.form2FailZbAllOrgCount, formKey, efdcCheckResultVOS.size());
                this.mapAddMaxValue(this.form2FailZbCount, formKey, efdcCheckResultVOS.size());
                this.addNumbers(this.failZbCount, efdcCheckResultVOS.size());
                this.addNumbers(this.checkZbCount, retEfdcZbFieldDefines.size());
                efdcCheckResultGroupVO.addOneGroup(formVo, efdcCheckResultVOS);
            }
            catch (Exception e) {
                throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.efdcDataCheck.efdcFaile", (Object[])new Object[]{e.getMessage()}), (Throwable)e);
            }
            finally {
                NpContextHolder.clearContext();
            }
        }
        DimensionValueSet dimensionValueSet = formOperationInfo.getDimensionValueSet();
        String period = String.valueOf(dimensionValueSet.getValue("DATATIME"));
        PeriodWrapper periodWrapper = new PeriodWrapper(period);
        efdcCheckResultGroupVO.setLocateFormIndex(0);
        efdcCheckResultGroupVO.setPeriodTitle(periodWrapper.toTitleString());
        String msg = GcI18nUtil.getMessage((String)"gc.efdcDataCheck.skippingFormNumber", (Object[])new Object[]{formOperationInfo.getFormKeys().size(), this.checkZbCount.get(), this.checkZbCount.get() - this.failZbCount.get(), this.failZbCount.get()});
        efdcCheckResultGroupVO.setMsg(msg);
        String address = this.systemOptionsService.get("fext-settings-group", "EFDCURL");
        HashMap<String, Object> options = new HashMap<String, Object>();
        ContextUser user = context.getUser();
        options.put("address", address);
        options.put("userName", user.getName());
        options.put("fetchSchemeKey", formulaSchemeKeyMap);
        efdcCheckResultGroupVO.setOptions(options);
        return efdcCheckResultGroupVO;
    }

    private Map<String, Set<String>> filterReportZbData(Map<String, Set<String>> filterReportZb, List<String> formKeys) {
        HashSet<String> formKeySet = new HashSet<String>(formKeys);
        HashMap<String, Set<String>> reportZbDataMap = new HashMap<String, Set<String>>();
        for (Map.Entry<String, Set<String>> entry : filterReportZb.entrySet()) {
            String formKey = entry.getKey();
            if (!formKeySet.contains(formKey)) continue;
            reportZbDataMap.put(formKey, entry.getValue());
        }
        return reportZbDataMap;
    }

    private List<String> filterFormList(List<String> formKeys, String schemeId) {
        List<DataCheckConfigEO> dataCheckConfigEOs = this.dataCheckConfigDAO.queryBySchemeId(schemeId);
        if (CollectionUtils.isEmpty(dataCheckConfigEOs)) {
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.efdcDataCheck.notAllowEfdcReport"));
        }
        String formsInfoJsonStr = dataCheckConfigEOs.get(0).getFormsInfo();
        List formInfos = (List)JsonUtils.readValue((String)formsInfoJsonStr, (TypeReference)new TypeReference<List<EfdcCheckConfigFormInfo>>(){});
        if (CollectionUtils.isEmpty((Collection)formInfos)) {
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.efdcDataCheck.noEFDCReports"));
        }
        Set formKeySet = formInfos.stream().map(EfdcCheckConfigFormInfo::getFormKey).collect(Collectors.toSet());
        if (formKeySet.contains(UUIDUtils.emptyUUIDStr())) {
            return formKeys;
        }
        HashSet<String> formInfoSet = new HashSet<String>();
        for (EfdcCheckConfigFormInfo form : formInfos) {
            if ("group".equals(form.getFormType())) {
                HashSet<String> groupKeys = new HashSet<String>();
                groupKeys.add(form.getFormKey());
                formInfoSet.addAll(this.getGroupAllChildForm(groupKeys));
                continue;
            }
            formInfoSet.add(form.getFormKey());
        }
        if (formKeys.contains(UUIDUtils.emptyUUIDStr())) {
            return new ArrayList<String>(formInfoSet);
        }
        return formKeys.stream().filter(item -> formInfoSet.contains(item)).collect(Collectors.toList());
    }

    private Set<String> getGroupAllChildForm(Set<String> groupKeys) {
        HashSet<String> result = new HashSet<String>();
        try {
            for (String groupKey : groupKeys) {
                List formGroupDefines;
                List formDefines = this.runTimeViewController.getAllFormsInGroup(groupKey);
                if (!CollectionUtils.isEmpty((Collection)formDefines)) {
                    result.addAll(formDefines.stream().map(IBaseMetaItem::getKey).collect(Collectors.toSet()));
                }
                if (CollectionUtils.isEmpty((Collection)(formGroupDefines = this.runTimeViewController.getChildFormGroups(groupKey)))) continue;
                result.addAll(this.getGroupAllChildForm(formGroupDefines.stream().map(IBaseMetaItem::getKey).collect(Collectors.toSet())));
            }
            return result;
        }
        catch (Exception e) {
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.efdcDataCheck.filterReportsFaile"), (Throwable)e);
        }
    }

    public BusinessResponseEntity<EfdcCheckResultGroupVO> batchEfdcDataCheck(String asynTaskKey, GcBatchEfdcCheckInfo batchCheckInfo) throws ConnectException {
        DimensionValueSet dimensionValueSet = DimensionValueSetUtil.getDimensionValueSet((Map)batchCheckInfo.getDimensionSet());
        return this.batchEfdcDataCheck(asynTaskKey, batchCheckInfo, dimensionValueSet);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public BusinessResponseEntity<EfdcCheckResultGroupVO> batchEfdcDataCheck(String asynTaskKey, GcBatchEfdcCheckInfo batchCheckInfo, DimensionValueSet dimensionValueSet) throws ConnectException {
        this.initProgress(dimensionValueSet);
        try {
            this.reconstructReportZbDataMap(batchCheckInfo.getReportZbDataMap());
            GcFormOperationInfo formOperationInfo = new GcFormOperationInfo();
            BeanUtils.copyProperties(batchCheckInfo, formOperationInfo);
            Calendar calendar = Calendar.getInstance();
            calendar.add(5, -3);
            this.dataCheckResultDAO.deleteBeforeCreateTime(calendar.getTime());
            this.getAllDataCheckResult(asynTaskKey, batchCheckInfo, dimensionValueSet, formOperationInfo);
            BusinessResponseEntity businessResponseEntity = BusinessResponseEntity.ok();
            return businessResponseEntity;
        }
        catch (Exception e) {
            logger.error("", e);
            BusinessResponseEntity businessResponseEntity = BusinessResponseEntity.error((String)e.getMessage());
            return businessResponseEntity;
        }
        finally {
            this.monitor.finish();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void getAllDataCheckResult(String asynTaskKey, GcBatchEfdcCheckInfo batchCheckInfo, DimensionValueSet dimensionValueSet, GcFormOperationInfo formOperationInfo) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Object orgIdValue = dimensionValueSet.getValue("MD_ORG");
        formOperationInfo.setDimensionValueSet(dimensionValueSet);
        String orgType = ConverterUtils.getAsString((Object)dimensionValueSet.getValue("MD_GCORGTYPE"));
        List<OrgDO> allOrgInfo = this.getAllOrgInfoByUnitIds(orgIdValue, orgType, ConverterUtils.getAsString((Object)dimensionValueSet.getValue("DATATIME")));
        List<FormDefine> checkConfigFormDefines = this.getCheckConfigFormDefines(batchCheckInfo, formOperationInfo);
        NpContext context = NpContextHolder.getContext();
        GcOrgTypeUtils.setContextEntityId((String)orgType);
        EFDCDataCheckImpl efdcDataCheckBean = (EFDCDataCheckImpl)SpringContextUtils.getBean(EFDCDataCheckImpl.class);
        ArrayList efdcCheckResultEOs = new ArrayList();
        ArrayList<CompletionStage> futuresList = new ArrayList<CompletionStage>();
        AtomicInteger successCount = new AtomicInteger(0);
        for (OrgDO org : allOrgInfo) {
            boolean penetratePermission = this.listedCompanyAuthzService.checkPenetratePermission(org.getCode(), orgType);
            if (!penetratePermission) {
                String errorMessage = GcI18nUtil.getMessage((String)"gc.efdcDataCheck.checkPenetratePermission", (Object[])new Object[]{org.getCode()});
                logger.info(errorMessage);
                this.getLog().append(errorMessage);
                continue;
            }
            List reportCurrency = (List)org.get((Object)REPORT_CURRENCY_ID);
            formOperationInfo.setDimValue("MD_ORG", (Object)org.getCode());
            List<FormDefine> filterFormDefines = this.filterCheckConfigFormDefines(checkConfigFormDefines, batchCheckInfo, formOperationInfo);
            for (FormDefine formDefine : filterFormDefines) {
                if (null == formDefine) continue;
                formOperationInfo.setFormKey(formDefine.getKey());
                for (String currency : reportCurrency) {
                    formOperationInfo.setDimValue("MD_CURRENCY", (Object)currency);
                    FormulaSchemeDefine formulaScheme = this.getFormulaScheme(formOperationInfo, org.getCode());
                    if (formulaScheme == null || StringUtils.isEmpty((String)formulaScheme.getKey())) {
                        String errorMsg = this.errorUnitSet.contains(org.getCode()) ? "" : GcI18nUtil.getMessage((String)"gc.efdcDataCheck.noRequiredAudit", (Object[])new Object[]{org.getCode()});
                        logger.error("\u5355\u4f4d\uff1a{} & \u8868\u5355\uff1a{} & \u5e01\u79cd\uff1a{} \u672a\u627e\u5230\u8d22\u52a1\u63d0\u53d6\u65b9\u6848\uff0c\u65e0\u9700\u7a3d\u6838\u3002", org.getCode(), formDefine.getTitle(), currency);
                        continue;
                    }
                    DimensionValueSet dimensionValueSetCopy = new DimensionValueSet(dimensionValueSet);
                    ConcurrentHashMap<String, Object> dimensionValueMap = this.getDimensionValueMap(formOperationInfo.getDimensionValueSet());
                    CompletionStage dataCheckResult = null;
                    try {
                        GcEfdcDataCheckSemaphoreUtil.getSemaphore().acquire();
                        dataCheckResult = efdcDataCheckBean.getDataCheckResult(context, org, formulaScheme, dimensionValueSetCopy, formOperationInfo, dimensionValueMap, formDefine).handle((result, throwable) -> {
                            if (throwable != null) {
                                logger.error("\u7a3d\u6838\u5931\u8d25\uff1a\u5355\u4f4d\uff1a{} & \u8868\u5355\uff1a{} & \u5e01\u79cd\uff1a{}", org.getCode(), formDefine.getTitle(), currency, throwable);
                                return null;
                            }
                            successCount.incrementAndGet();
                            logger.info("\u7a3d\u6838\u5b8c\u6210: \u7d22\u5f15\uff1a{} & \u5355\u4f4d\uff1a{} & \u8868\u5355\uff1a{} & \u5e01\u79cd\uff1a{}", successCount, org.getCode(), formDefine.getTitle(), currency);
                            return result;
                        });
                        futuresList.add(dataCheckResult);
                    }
                    catch (Exception e) {
                        this.getLog().append(e.getMessage());
                        logger.error("\u7a3d\u6838\u5931\u8d25\uff1a\u5355\u4f4d\uff1a{} & \u8868\u5355\uff1a{}", org.getCode(), formDefine.getTitle(), e);
                    }
                    finally {
                        GcEfdcDataCheckSemaphoreUtil.getSemaphore().release();
                    }
                }
            }
            this.monitor.step();
        }
        CompletableFuture<Void> allFutures = CompletableFuture.allOf(futuresList.toArray(new CompletableFuture[0]));
        CompletionStage allResultsFuture = allFutures.thenApply(v -> futuresList.stream().map(CompletableFuture::join).collect(Collectors.toList()));
        ((CompletableFuture)((CompletableFuture)allResultsFuture).thenAccept(resultsLists -> {
            Date createTime = new Date();
            for (GcDataCheckResultDTO results : resultsLists) {
                if (results == null) continue;
                String formKey = results.getFormKey();
                String currency = results.getCurrency();
                List<EfdcCheckResultVO> efdcCheckResultVOS = results.getEfdcCheckResultVOS();
                List<FieldDefine> retEfdcZbFieldDefines = results.getRetEfdcZbFieldDefines();
                for (EfdcCheckResultVO vo : efdcCheckResultVOS) {
                    EfdcCheckResultEO efdcCheckResultEO = new EfdcCheckResultEO();
                    BeanUtils.copyProperties(vo, (Object)efdcCheckResultEO);
                    efdcCheckResultEO.setAsynTaskId(asynTaskKey);
                    efdcCheckResultEO.setCreateTime(createTime);
                    efdcCheckResultEO.setCurrency(currency);
                    efdcCheckResultEO.setZbValue(Double.parseDouble(vo.getZbValue().replaceAll(",", "")));
                    efdcCheckResultEO.setEfdcValue(Double.parseDouble(vo.getEfdcValue().replaceAll(",", "")));
                    efdcCheckResultEO.setId(UUIDOrderUtils.newUUIDStr());
                    if (efdcCheckResultEO.getExpression().length() > 900) {
                        efdcCheckResultEO.setExpression(efdcCheckResultEO.getExpression().substring(0, 900) + "...");
                    }
                    efdcCheckResultEOs.add(efdcCheckResultEO);
                    if (efdcCheckResultEOs.size() <= 1000) continue;
                    this.dataCheckResultDAO.addBatch(efdcCheckResultEOs);
                    efdcCheckResultEOs.clear();
                }
                this.form2CheckZbCount.put(formKey, retEfdcZbFieldDefines.size());
                this.mapAddValue(this.form2CheckZbAllOrgCount, formKey, retEfdcZbFieldDefines.size());
                this.mapAddValue(this.form2FailZbAllOrgCount, formKey, efdcCheckResultVOS.size());
                this.mapAddMaxValue(this.form2FailZbCount, formKey, efdcCheckResultVOS.size());
                this.addNumbers(this.failZbCount, efdcCheckResultVOS.size());
                this.addNumbers(this.checkZbCount, retEfdcZbFieldDefines.size());
            }
            if (!efdcCheckResultEOs.isEmpty()) {
                this.dataCheckResultDAO.addBatch(efdcCheckResultEOs);
            }
        })).join();
        stopWatch.stop();
        logger.info("\u6570\u636e\u7a3d\u6838\u5b8c\u6210\uff0c\u5171\u8017\u65f6\uff1a[{}] \u79d2\u3002", (Object)stopWatch.getTotalTimeSeconds());
    }

    private List<FormDefine> filterCheckConfigFormDefines(List<FormDefine> checkConfigFormDefines, GcBatchEfdcCheckInfo batchCheckInfo, GcFormOperationInfo formOperationInfo) {
        JtableContext jtableContext = new JtableContext();
        jtableContext.setFormSchemeKey(batchCheckInfo.getFormSchemeKey());
        jtableContext.setDimensionSet(batchCheckInfo.getDimensionSet());
        List<FormDefine> filterFormDefines = checkConfigFormDefines.stream().filter(formDefine -> this.formTabSelectService.isFormCondition(jtableContext, formDefine.getFormCondition(), formOperationInfo.getDimensionValueSet())).collect(Collectors.toList());
        return filterFormDefines;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Async(value="efdcDataCheckTaskExecutor")
    public CompletableFuture<GcDataCheckResultDTO> getDataCheckResult(NpContext context, OrgDO org, FormulaSchemeDefine formulaScheme, DimensionValueSet dimensionValueSet, GcFormOperationInfo formOperationInfo, ConcurrentHashMap<String, Object> dimensionValueMap, FormDefine formDefine) {
        try {
            NpContextHolder.setContext((NpContext)context);
            Map dimensionSetMap = DimensionUtils.buildDimensionMap((String)formOperationInfo.getTaskKey(), (String)ConverterUtils.getAsString((Object)dimensionValueSet.getValue("MD_CURRENCY")), (String)ConverterUtils.getAsString((Object)dimensionValueSet.getValue("DATATIME")), (String)GCOrgTypeEnum.CORPORATE.getCode(), (String)org.getCode(), (String)ConverterUtils.getAsString((Object)dimensionValueSet.getValue("ADJUST")));
            dimensionValueSet.setValue("MD_ORG", (Object)org.getCode());
            GcFormOperationInfo formOperationInfoCopy = (GcFormOperationInfo)JsonUtils.readValue((String)JsonUtils.writeValueAsString((Object)formOperationInfo), GcFormOperationInfo.class);
            formOperationInfoCopy.setFormKey(formDefine.getKey());
            formOperationInfoCopy.setDimensionValueSet(dimensionValueSet);
            formOperationInfoCopy.setDimensionSet(dimensionSetMap);
            Map<String, Set<String>> zbDataMap = this.getReportZbDataMap(formOperationInfoCopy.getReportZbDataMap());
            GcFetchDataEnvContextImpl envContext = new GcFetchDataEnvContextImpl(org, formDefine, formulaScheme, this.errorFormKeySet, zbDataMap, null, formOperationInfoCopy, dimensionValueMap);
            GcFetchDataInfo fetchDataInfoDTO = null;
            try {
                fetchDataInfoDTO = this.fetchDataService.getFieldDefineList((GcFetchDataEnvContext)envContext);
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
                throw new BusinessRuntimeException(e.getMessage());
            }
            if (CollectionUtils.isEmpty((Collection)fetchDataInfoDTO.getExpressionListing().getFixExpressions()) && CollectionUtils.isEmpty((Collection)fetchDataInfoDTO.getExpressionListing().getFloatExpressions())) {
                CompletableFuture<Object> e = CompletableFuture.completedFuture(null);
                return e;
            }
            envContext.setCwFmlListing(fetchDataInfoDTO.getExpressionListing());
            List<FieldDefine> retEfdcZbFieldDefines = fetchDataInfoDTO.getRetEfdcZbFieldDefines();
            if (!CollectionUtils.isEmpty((Collection)zbDataMap.get(formOperationInfoCopy.getFormKey()))) {
                retEfdcZbFieldDefines = retEfdcZbFieldDefines.stream().filter(item -> ((Set)zbDataMap.get(formOperationInfoCopy.getFormKey())).contains(item.getKey())).collect(Collectors.toList());
            }
            CompletableFuture<GcFetchDataResultInfo> fetchDataFuture = CompletableFuture.completedFuture(this.fetchDataService.fetchData((GcFetchDataEnvContext)envContext));
            CompletableFuture<List<AbstractData>> reportValuesFuture = CompletableFuture.completedFuture(this.queryReportData(formOperationInfoCopy.getDimensionValueSet(), new CopyOnWriteArrayList<FieldDefine>(retEfdcZbFieldDefines)));
            CompletableFuture.allOf(fetchDataFuture, reportValuesFuture).join();
            ArrayList<FixExpResult> fetchDataValues = new ArrayList();
            ArrayList<AbstractData> reportValues = new ArrayList();
            try {
                reportValues = reportValuesFuture.get();
                fetchDataValues = fetchDataFuture.get().getRetEfdcValues();
            }
            catch (Exception e) {
                throw new BusinessRuntimeException((Throwable)e);
            }
            List<EfdcCheckResultVO> resultVOs = this.getCheckResult(fetchDataValues, reportValues, retEfdcZbFieldDefines, formOperationInfoCopy, org, formDefine);
            GcDataCheckResultDTO resultDTO = new GcDataCheckResultDTO();
            resultDTO.setFormKey(formDefine.getKey());
            resultDTO.setCurrency(ConverterUtils.getAsString((Object)dimensionValueSet.getValue("MD_CURRENCY")));
            resultDTO.setEfdcCheckResultVOS(resultVOs);
            resultDTO.setRetEfdcZbFieldDefines(retEfdcZbFieldDefines);
            CompletableFuture<GcDataCheckResultDTO> completableFuture = CompletableFuture.completedFuture(resultDTO);
            return completableFuture;
        }
        finally {
            NpContextHolder.clearContext();
        }
    }

    private List<EfdcCheckResultVO> getCheckResult(List<FixExpResult> fetchDataValues, List<AbstractData> reportValues, List<FieldDefine> efdcZbFieldDefines, GcFormOperationInfo formOperationInfo, OrgDO org, FormDefine formDefine) {
        String orgId = org.getCode();
        String formKey = formDefine.getKey();
        String regionKey = this.getRegionKey(formDefine.getKey());
        ConcurrentMap<String, String> zbId2LinkId = this.getZbId2LinkId(regionKey);
        DimensionValueSet dimensionValueSet = formOperationInfo.getDimensionValueSet();
        StringBuffer dimJsonStr = this.dimensionValueJson(dimensionValueSet);
        ArrayList<EfdcCheckResultVO> resultVOs = new ArrayList<EfdcCheckResultVO>();
        block3: for (int i = 0; i < efdcZbFieldDefines.size(); ++i) {
            FieldDefine reportZb = efdcZbFieldDefines.get(i);
            FixExpResult efdcValue = fetchDataValues.get(i);
            FloatData reportValue = i < reportValues.size() ? reportValues.get(i) : new FloatData(0.0);
            switch (reportZb.getType()) {
                case FIELD_TYPE_FLOAT: 
                case FIELD_TYPE_DECIMAL: 
                case FIELD_TYPE_INTEGER: {
                    double efdcValueDouble = NumberUtils.round((double)Double.parseDouble((String)efdcValue.getValues().get(0)), (int)((DataFieldDTO)reportZb).getDecimal());
                    double reportValueDouble = NumberUtils.round((double)reportValue.getAsFloat(), (int)((DataFieldDTO)reportZb).getDecimal());
                    if (NumberUtils.isZreo((Double)(efdcValueDouble - reportValueDouble))) continue block3;
                    EfdcCheckResultVO resultVO = new EfdcCheckResultVO();
                    resultVO.setZbTitle(reportZb.getTitle());
                    resultVO.setZbCode(reportZb.getCode());
                    resultVO.setZbValue(NumberUtils.doubleToString((double)reportValueDouble));
                    resultVO.setEfdcValue(NumberUtils.doubleToString((double)efdcValueDouble));
                    resultVO.setDiffValue(NumberUtils.doubleToString((double)(reportValueDouble - efdcValueDouble)));
                    resultVO.setExpression(efdcValue.getExpression(0));
                    resultVO.setFormTitle(formDefine.getTitle());
                    resultVO.setOrgId(orgId);
                    resultVO.setFormKey(formKey);
                    resultVO.setRegionKey(regionKey);
                    resultVO.setDataLinkKey((String)zbId2LinkId.get(reportZb.getKey()));
                    resultVO.setFieldKey(reportZb.getKey());
                    resultVO.setDimensionValueSet(dimJsonStr.toString());
                    resultVOs.add(resultVO);
                    continue block3;
                }
                default: {
                    logger.info(reportZb.getTitle());
                }
            }
        }
        return resultVOs;
    }

    private void addNumbers(AtomicInteger num1, int num2) {
        num1.set(num1.get() + num2);
    }

    private String getRegionKey(String formKey) {
        List dataRegionDefines = this.runTimeViewController.getAllRegionsInForm(formKey);
        if (dataRegionDefines.isEmpty()) {
            return null;
        }
        return ((DataRegionDefine)dataRegionDefines.get(0)).getKey();
    }

    private ConcurrentMap<String, String> getZbId2LinkId(String regionKey) {
        ConcurrentMap<String, String> zbId2LinkId = this.designTimeViewController.getAllLinksInRegion(regionKey).stream().filter(item -> DataLinkType.DATA_LINK_TYPE_FIELD.equals((Object)item.getType())).collect(Collectors.toConcurrentMap(DataLinkDefine::getLinkExpression, IBaseMetaItem::getKey, (existing, replacement) -> existing));
        return zbId2LinkId;
    }

    private List<FormDefine> getCheckConfigFormDefines(GcBatchEfdcCheckInfo batchCheckInfo, GcFormOperationInfo formOperationInfo) {
        List formKeys = new ArrayList();
        JtableContext jtableContext = new JtableContext();
        jtableContext.setFormSchemeKey(batchCheckInfo.getFormSchemeKey());
        jtableContext.setDimensionSet(batchCheckInfo.getDimensionSet());
        if (CollectionUtils.isEmpty((Collection)batchCheckInfo.getFormKeys())) {
            List runtimeFormList = this.dataEntryParamService.getRuntimeFormList(jtableContext);
            if (runtimeFormList != null) {
                for (FormGroupData formGroupData : runtimeFormList) {
                    List reports = formGroupData.getReports();
                    if (reports == null) continue;
                    List groupFormKeyList = reports.stream().map(item -> item.getKey()).collect(Collectors.toList());
                    formKeys.addAll(groupFormKeyList);
                }
            }
        } else {
            formKeys = batchCheckInfo.getFormKeys();
        }
        List<FormDefine> checkConfigFormDefines = formKeys.stream().map(formKey -> this.runTimeViewController.queryFormById(formKey)).collect(Collectors.toList());
        return checkConfigFormDefines;
    }

    private List<OrgDO> getAllOrgInfoByUnitIds(Object orgIdValue, String orgType, String periodStr) {
        ArrayList<Object> orgIds = new ArrayList<Object>();
        if (orgIdValue instanceof List) {
            orgIds.addAll((List)orgIdValue);
        } else {
            orgIds.add(orgIdValue);
        }
        List unitIdStrs = orgIds.stream().map(Object::toString).collect(Collectors.toList());
        OrgDTO orgDTO = new OrgDTO();
        orgDTO.setAuthType(OrgDataOption.AuthType.ACCESS);
        orgDTO.setCategoryname(orgType);
        orgDTO.setVersionDate(PeriodUtils.getStartDateOfPeriod((String)periodStr, (boolean)false));
        orgDTO.setOrgOrgcodes(unitIdStrs);
        orgDTO.setStopflag(Integer.valueOf(0));
        orgDTO.setRecoveryflag(Integer.valueOf(0));
        orgDTO.setQueryDataStructure(OrgDataOption.QueryDataStructure.BASIC);
        PageVO list = this.orgDataQueryService.list(orgDTO);
        List orgDOS = list.getRows();
        return orgDOS;
    }

    private StringBuffer dimensionValueJson(DimensionValueSet currDimensionValueSet) {
        StringBuffer dimJsonStr = new StringBuffer(128);
        dimJsonStr.append("{");
        for (int i = 0; i < currDimensionValueSet.size(); ++i) {
            String name = currDimensionValueSet.getName(i);
            String value = String.valueOf(currDimensionValueSet.getValue(i));
            dimJsonStr.append("\"").append(name).append("\"").append(":").append("\"").append(value).append("\",");
        }
        dimJsonStr.setLength(dimJsonStr.length() - 1);
        dimJsonStr.append("}");
        return dimJsonStr;
    }

    private void mapAddMaxValue(Map<String, Integer> map, String key, int value) {
        Integer number = map.get(key);
        if (number == null) {
            number = 0;
            map.put(key, number);
        }
        if (value == 0) {
            return;
        }
        map.put(key, value > number ? value : number);
    }

    private void mapAddValue(Map<String, Integer> map, String key, Integer value) {
        Integer number = map.get(key);
        if (number == null) {
            number = 0;
            map.put(key, number);
        }
        if (null == value || value == 0) {
            return;
        }
        map.put(key, number + value);
    }

    private Map<String, Set<String>> getReportZbDataMap(Map<String, Set<String>> reportZbDataMap) {
        if (this.reportId2ZbGuidMap == null || this.reportId2ZbGuidMap.size() == 0) {
            this.reconstructReportZbDataMap(reportZbDataMap);
        }
        return this.reportId2ZbGuidMap;
    }

    private List<AbstractData> queryReportData(DimensionValueSet dimensionValueSet, List<FieldDefine> efdcZbFieldDefines) {
        ArrayList<AbstractData> retReportValues = new ArrayList<AbstractData>();
        try {
            IDataQuery dataQuery = this.dataAccessProvider.newDataQuery();
            for (FieldDefine fieldDefine : efdcZbFieldDefines) {
                dataQuery.addColumn(fieldDefine);
            }
            dataQuery.setMasterKeys(dimensionValueSet);
            ExecutorContext context = new ExecutorContext(this.dataDefinitionRuntimeController);
            context.setUseDnaSql(false);
            IDataTable dataTable = dataQuery.executeQuery(context);
            int rowCount = dataTable.getCount();
            if (rowCount > 0) {
                IDataRow dataRow = dataTable.getItem(0);
                for (FieldDefine fieldDefine : efdcZbFieldDefines) {
                    AbstractData data = dataRow.getValue(fieldDefine);
                    retReportValues.add(data);
                }
            }
        }
        catch (Exception e) {
            logger.error("", e);
        }
        return retReportValues;
    }

    private void initDimensionValue(GcFormOperationInfo formOperationInfo) {
        DimensionValueSet dimensionValueSet = formOperationInfo.getDimensionValueSet();
        if (null == dimensionValueSet) {
            dimensionValueSet = DimensionValueSetUtil.getDimensionValueSet((Map)formOperationInfo.getDimensionSet());
        }
        formOperationInfo.setDimensionValueSet(dimensionValueSet);
    }

    private ConcurrentHashMap<String, Object> getDimensionValueMap(DimensionValueSet dimensionValueSet) {
        ConcurrentHashMap<String, Object> dimensionValueMap = new ConcurrentHashMap<String, Object>();
        for (int i = 0; i < dimensionValueSet.size(); ++i) {
            dimensionValueMap.put(dimensionValueSet.getName(i), dimensionValueSet.getValue(i));
        }
        return dimensionValueMap;
    }

    private FormulaSchemeDefine getFormulaScheme(GcFormOperationInfo formOperationInfo, String unitKey) {
        EntityViewData dwEntity = this.jtableParamService.getDwEntity(formOperationInfo.getFormSchemeKey());
        List dimEntityList = this.jtableParamService.getDimEntityList(formOperationInfo.getFormSchemeKey());
        DimensionValueSet dimensionValueSet = formOperationInfo.getDimensionValueSet();
        ConcurrentHashMap<String, String> dimMap = new ConcurrentHashMap<String, String>();
        for (EntityViewData entityInfo : dimEntityList) {
            Object dimensionValue = dimensionValueSet.getValue(entityInfo.getDimensionName());
            dimMap.put(entityInfo.getTableName(), ConverterUtils.getAsString((Object)dimensionValue));
        }
        QueryObjectImpl queryObjectImpl = new QueryObjectImpl(formOperationInfo.getTaskKey(), formOperationInfo.getFormSchemeKey(), unitKey);
        return this.efdcConfigServiceImpl.getSoluctionByDimensions(queryObjectImpl, dimMap, dwEntity.getKey());
    }

    private GcFetchDataInfo fillFormulaGroup(String formulaSchemekey, String formKey) {
        List allFormulasDefines = this.formulaRunTimeController.getAllFormulasInForm(formulaSchemekey, formKey);
        try {
            List defines = this.formulaRunTimeController.queryPublicFormulaDefineByScheme(formulaSchemekey, formKey);
            if (!CollectionUtils.isEmpty((Collection)defines)) {
                allFormulasDefines.addAll(defines);
            }
        }
        catch (Exception e) {
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.efdcDataCheck.fetchFormulaError"), (Throwable)e);
        }
        FormulaSchemeDefine formulaSchemeDefine = this.formulaRunTimeController.queryFormulaSchemeDefine(formulaSchemekey);
        FormDefine formDefine = this.runTimeViewController.queryFormById(formKey);
        if (null == formDefine) {
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.efdcDataCheck.notFoundReport", (Object[])new Object[]{formKey}));
        }
        if (allFormulasDefines == null || allFormulasDefines.size() == 0) {
            String msg = this.errorFormKeySet.contains(formDefine.getKey()) ? "" : GcI18nUtil.getMessage((String)"gc.efdcDataCheck.notSettingFormula", (Object[])new Object[]{formDefine.getTitle()});
            this.errorFormKeySet.add(formDefine.getKey());
            throw new BusinessRuntimeException(msg);
        }
        Set<String> fixedFieldKeys = this.getFixedFieldKeys(formKey, formDefine);
        ExpressionListing cwFmlListing = new ExpressionListing();
        ArrayList<FixExpression> fixFmls = new ArrayList<FixExpression>();
        ExecutorContext executorContext = new ExecutorContext(this.dataDefinitionRuntimeController);
        ReportFmlExecEnvironment environment = new ReportFmlExecEnvironment(this.runTimeViewController, this.dataDefinitionRuntimeController, this.entityViewRunTimeController, formulaSchemeDefine.getFormSchemeKey());
        executorContext.setEnv((IFmlExecEnvironment)environment);
        executorContext.setDefaultGroupName(formDefine.getFormCode());
        HashSet<String> hasAddedZbCodes = new HashSet<String>();
        boolean filterZb = !MapUtils.isEmpty(this.reportId2ZbGuidMap);
        Set<String> filterZbGuids = null;
        if (filterZb) {
            filterZbGuids = this.reportId2ZbGuidMap.get(formDefine.getKey());
        }
        if (null == filterZbGuids) {
            filterZb = false;
            filterZbGuids = new HashSet<String>();
        }
        ArrayList<FieldDefine> retEfdcZbFieldDefines = new ArrayList<FieldDefine>();
        try {
            QueryContext qContext = new QueryContext(executorContext, null);
            ReportFormulaParser parser = executorContext.getCache().getFormulaParser(executorContext.isJQReportModel());
            for (int i = 0; i < allFormulasDefines.size(); ++i) {
                FormulaDefine formulaDefine = (FormulaDefine)allFormulasDefines.get(i);
                String expression = formulaDefine.getExpression();
                int equalIndex = expression.indexOf("=");
                String assginExp = expression.substring(0, equalIndex);
                String efdcExp = expression.substring(equalIndex + 1);
                if (assginExp.indexOf("*") >= 0) continue;
                try {
                    IExpression exp = parser.parseEval(assginExp, (IContext)qContext);
                    DynamicDataNode dataNode = null;
                    for (IASTNode child : exp) {
                        if (!(child instanceof DynamicDataNode)) continue;
                        dataNode = (DynamicDataNode)child;
                        break;
                    }
                    FieldDefine fieldDefine = dataNode.getDataLink().getField();
                    FixExpression fml = new FixExpression();
                    fml.setFlag(dataNode.getDataLink().toString());
                    fml.setName(dataNode.getDataLink().getDataLinkCode());
                    fml.setPrecision(2);
                    fml.setExpression(efdcExp);
                    if (!fixedFieldKeys.contains(fieldDefine.getKey()) || filterZb && !filterZbGuids.contains(fieldDefine.getKey()) || hasAddedZbCodes.contains(fieldDefine.getCode())) continue;
                    hasAddedZbCodes.add(fieldDefine.getCode());
                    retEfdcZbFieldDefines.add(fieldDefine);
                    fixFmls.add(fml);
                    continue;
                }
                catch (Exception e) {
                    logger.error("", e);
                }
            }
        }
        catch (Exception e) {
            logger.error("", e);
        }
        cwFmlListing.setFixExpressions(fixFmls);
        cwFmlListing.setFloatExpressions(new ArrayList());
        GcFetchDataInfo gcFetchDataInfo = new GcFetchDataInfo();
        gcFetchDataInfo.setRetEfdcZbFieldDefines(new CopyOnWriteArrayList(retEfdcZbFieldDefines));
        gcFetchDataInfo.setExpressionListing(cwFmlListing);
        return gcFetchDataInfo;
    }

    private Set<String> getFixedFieldKeys(String formKey, FormDefine formDefine) {
        HashSet<String> fixedFieldKeys = new HashSet<String>();
        List dataRegionDefines = this.runTimeViewController.getAllRegionsInForm(formKey);
        if (CollectionUtils.isEmpty((Collection)dataRegionDefines)) {
            String msg = GcI18nUtil.getMessage((String)"gc.efdcDataCheck.notAuditEmptyRegion", (Object[])new Object[]{formDefine.getTitle()});
            logger.info(msg);
            throw new BusinessRuntimeException(msg);
        }
        dataRegionDefines.stream().filter(Objects::nonNull).filter(dataRegionDefine -> dataRegionDefine != null && dataRegionDefine.getRegionKind() == DataRegionKind.DATA_REGION_SIMPLE).forEach(dataRegionDefine -> {
            List fieldKeysInRegion = this.runTimeViewController.getFieldKeysInRegion(dataRegionDefine.getKey());
            if (fieldKeysInRegion != null) {
                fixedFieldKeys.addAll(fieldKeysInRegion);
            }
        });
        return fixedFieldKeys;
    }

    @Override
    public BusinessResponseEntity<List<EfdcCheckConfigVO>> getEfdcDataCheckConfig(String taskId) throws Exception {
        List formSchemeDefines = this.runTimeViewController.queryFormSchemeByTask(taskId);
        if (CollectionUtils.isEmpty((Collection)formSchemeDefines)) {
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.efdcDataCheck.notFoundTask", (Object[])new Object[]{taskId}));
        }
        ArrayList<EfdcCheckConfigVO> configVOs = new ArrayList<EfdcCheckConfigVO>();
        for (FormSchemeDefine formSchemeDefine : formSchemeDefines) {
            EfdcCheckConfigVO configVO = new EfdcCheckConfigVO();
            configVO.setSchemeId(formSchemeDefine.getKey());
            configVO.setSchemeTitle(formSchemeDefine.getTitle());
            FormTree formTree = this.formTabSelectService.getFormTree(formSchemeDefine.getKey(), null);
            configVO.setCanSelectedFormsTree(formTree);
            List<DataCheckConfigEO> dataCheckConfigEOs = this.dataCheckConfigDAO.queryBySchemeId(formSchemeDefine.getKey());
            if (!CollectionUtils.isEmpty(dataCheckConfigEOs)) {
                configVO.setOrgMaxLength(dataCheckConfigEOs.get(0).getOrgMaxLength());
                String formsInfoJsonStr = dataCheckConfigEOs.get(0).getFormsInfo();
                if (!StringUtils.isEmpty((String)formsInfoJsonStr)) {
                    List formInfos = (List)JsonUtils.readValue((String)formsInfoJsonStr, (TypeReference)new TypeReference<List<EfdcCheckConfigFormInfo>>(){});
                    List formKeys = formInfos.stream().map(EfdcCheckConfigFormInfo::getFormKey).collect(Collectors.toList());
                    configVO.setSelectFormInfo(formInfos);
                    configVO.setSelectedFormKeys(formKeys);
                }
            }
            configVOs.add(configVO);
        }
        return BusinessResponseEntity.ok(configVOs);
    }

    @Override
    public void saveEfdcDataCheckConfig(String taskId, List<EfdcCheckConfigVO> efdcCheckConfigVOs) throws Exception {
        ArrayList<DataCheckConfigEO> checkConfigEOs = new ArrayList<DataCheckConfigEO>();
        for (EfdcCheckConfigVO efdcCheckConfigVO : efdcCheckConfigVOs) {
            DataCheckConfigEO checkConfigEO = new DataCheckConfigEO();
            checkConfigEO.setTaskId(taskId);
            checkConfigEO.setSchemeId(efdcCheckConfigVO.getSchemeId());
            checkConfigEO.setOrgMaxLength(efdcCheckConfigVO.getOrgMaxLength());
            ArrayList<EfdcCheckConfigFormInfo> forms = new ArrayList<EfdcCheckConfigFormInfo>();
            if (CollectionUtils.isEmpty((Collection)efdcCheckConfigVO.getSelectedFormKeys())) {
                checkConfigEOs.add(checkConfigEO);
                continue;
            }
            for (String formKey : efdcCheckConfigVO.getSelectedFormKeys()) {
                EfdcCheckConfigFormInfo formInfo = new EfdcCheckConfigFormInfo();
                formInfo.setFormKey(formKey);
                FormDefine formDefine = this.runTimeViewController.queryFormById(formKey);
                if (null != formDefine) {
                    formInfo.setFormType("form");
                } else {
                    FormGroupDefine formGroupDefine = this.runTimeViewController.queryFormGroup(formKey);
                    if (null != formGroupDefine) {
                        formInfo.setFormType("group");
                    }
                }
                forms.add(formInfo);
            }
            checkConfigEO.setFormsInfo(JsonUtils.writeValueAsString(forms));
            checkConfigEOs.add(checkConfigEO);
        }
        this.dataCheckConfigDAO.save(taskId, checkConfigEOs);
    }

    public List<EfdcCheckResultUnitVO> efdcBatchCheckUnits(String asynTaskID) {
        List<EfdcCheckResultUnitVO> checkResultUnitVOs = this.dataCheckResultDAO.queryUnitsByAsynTaskId(asynTaskID);
        YearPeriodObject yp = new YearPeriodObject(null, "");
        GcOrgCenterService tool = GcOrgPublicTool.getInstance((String)"", (GcAuthorityType)GcAuthorityType.ACCESS, (YearPeriodObject)yp);
        for (EfdcCheckResultUnitVO unitVO : checkResultUnitVOs) {
            GcOrgCacheVO org = tool.getOrgByCode(unitVO.getKey());
            if (null == org) continue;
            unitVO.setCode(org.getCode());
            unitVO.setTitle(org.getTitle());
        }
        return checkResultUnitVOs;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public List<EfdcCheckResultVO> efdcBatchCheckResult(GcBatchEfdcQueryParam efdcQueryParam) {
        List<EfdcCheckResultEO> checkResultEOs = this.dataCheckResultDAO.queryResultByAsynTaskId(efdcQueryParam);
        ArrayList<EfdcCheckResultVO> checkResultVOs = new ArrayList<EfdcCheckResultVO>();
        HashMap<String, Map<String, DesignDataLinkDefine>> dataLinkCacheMap = new HashMap<String, Map<String, DesignDataLinkDefine>>();
        try {
            for (EfdcCheckResultEO efdcCheckResultEO : checkResultEOs) {
                FieldDefine fieldDefine;
                FormDefine formDefine;
                EfdcCheckResultVO checkResultVO = new EfdcCheckResultVO();
                BeanUtils.copyProperties((Object)efdcCheckResultEO, checkResultVO);
                String formKey = efdcCheckResultEO.getFormKey();
                String fieldKey = efdcCheckResultEO.getFieldKeyStr();
                double zbValue = efdcCheckResultEO.getZbValue();
                double efdcValue = efdcCheckResultEO.getEfdcValue();
                checkResultVO.setZbValue(NumberUtils.doubleToString((double)zbValue));
                checkResultVO.setEfdcValue(NumberUtils.doubleToString((double)efdcValue));
                checkResultVO.setDiffValue(NumberUtils.doubleToString((double)(zbValue - efdcValue)));
                checkResultVO.setCurrency(efdcCheckResultEO.getCurrency());
                DesignDataLinkDefine dataLinkDefine = this.findDataLinkDefine(formKey, fieldKey, dataLinkCacheMap);
                if (dataLinkDefine != null) {
                    checkResultVO.setDataLinkKey(dataLinkDefine.getKey());
                    checkResultVO.setRegionKey(dataLinkDefine.getRegionKey());
                }
                if (null != (formDefine = this.runTimeViewController.queryFormById(checkResultVO.getFormKey()))) {
                    checkResultVO.setFormTitle(formDefine.getTitle());
                }
                if (null != (fieldDefine = this.dataDefinitionRuntimeController.queryFieldDefine(fieldKey))) {
                    checkResultVO.setZbCode(fieldDefine.getCode());
                    checkResultVO.setZbTitle(fieldDefine.getTitle());
                }
                checkResultVOs.add(checkResultVO);
            }
        }
        catch (Exception e) {
            logger.error("", e);
        }
        finally {
            dataLinkCacheMap.clear();
        }
        return checkResultVOs;
    }

    public Map<String, Integer> queryCountGroupByOrgIdAndCurrency(String asynTaskID) {
        return this.dataCheckResultDAO.queryCountGroupByOrgIdAndCurrency(asynTaskID);
    }

    public List<EfdcCheckResultVO> queryResultByAsynTaskId(GcBatchEfdcQueryParam efdcQueryParam) {
        List<EfdcCheckResultEO> checkResultEOs = this.dataCheckResultDAO.queryResultByAsynTaskId(efdcQueryParam);
        ArrayList<EfdcCheckResultVO> checkResultVos = new ArrayList<EfdcCheckResultVO>();
        try {
            for (EfdcCheckResultEO efdcCheckResultEO : checkResultEOs) {
                EfdcCheckResultVO checkResultVO = new EfdcCheckResultVO();
                FormDefine formDefine = this.runTimeViewController.queryFormById(efdcCheckResultEO.getFormKey());
                if (null == formDefine) continue;
                checkResultVO.setFormTitle(formDefine.getTitle());
                double zbValue = efdcCheckResultEO.getZbValue();
                double efdcValue = efdcCheckResultEO.getEfdcValue();
                checkResultVO.setZbValue(NumberUtils.doubleToString((double)zbValue));
                checkResultVO.setEfdcValue(NumberUtils.doubleToString((double)efdcValue));
                checkResultVO.setDiffValue(NumberUtils.doubleToString((double)(zbValue - efdcValue)));
                checkResultVO.setFormKey(efdcCheckResultEO.getFormKey());
                FieldDefine fieldDefine = this.dataDefinitionRuntimeController.queryFieldDefine(efdcCheckResultEO.getFieldKey());
                checkResultVO.setZbTitle(fieldDefine.getTitle());
                checkResultVO.setOrgId(efdcCheckResultEO.getOrgId());
                checkResultVO.setCurrency(efdcCheckResultEO.getCurrency());
                checkResultVos.add(checkResultVO);
            }
        }
        catch (Exception e) {
            logger.error("", e);
        }
        return checkResultVos;
    }

    public void createExcel(HSSFWorkbook workbook, List<EfdcCheckResultVO> resultVOs, boolean groupByReport) {
        try {
            int rowIndex = 0;
            YearPeriodObject yp = new YearPeriodObject(null, "");
            GcOrgCenterService tool = GcOrgPublicTool.getInstance((String)"", (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
            HSSFPalette customPalette = workbook.getCustomPalette();
            customPalette.setColorAtIndex(HSSFColor.HSSFColorPredefined.ORANGE.getIndex(), (byte)-21, (byte)-18, (byte)-11);
            customPalette.setColorAtIndex(HSSFColor.HSSFColorPredefined.BLUE.getIndex(), (byte)-11, (byte)-11, (byte)-11);
            HSSFCellStyle text = GcEfdcDataCheckUtils.getExelCellStyle(workbook, "text");
            HSSFCellStyle amount = GcEfdcDataCheckUtils.getExelCellStyle(workbook, "amount");
            HSSFCellStyle[] styles = new HSSFCellStyle[]{text, text, text, text, text, amount, amount, amount};
            HSSFSheet sheet = workbook.createSheet(GcI18nUtil.getMessage((String)"gc.efdcDataCheck.auditResults"));
            HSSFRow titleRow = sheet.createRow(rowIndex++);
            this.excelHead(sheet, titleRow, groupByReport);
            HashMap<String, GcOrgCacheVO> orgCacheMap = new HashMap<String, GcOrgCacheVO>();
            int sheetIndex = 2;
            if (groupByReport) {
                Collections.sort(resultVOs, Comparator.comparing(EfdcCheckResultVO::getFormKey).reversed().thenComparing(EfdcCheckResultVO::getOrgId).reversed().thenComparing(EfdcCheckResultVO::getCurrency));
            } else {
                Collections.sort(resultVOs, Comparator.comparing(EfdcCheckResultVO::getOrgId).reversed().thenComparing(EfdcCheckResultVO::getFormKey).reversed().thenComparing(EfdcCheckResultVO::getCurrency));
            }
            for (EfdcCheckResultVO efdcCheckResultVO : resultVOs) {
                if (!orgCacheMap.containsKey(efdcCheckResultVO.getOrgId())) {
                    orgCacheMap.put(efdcCheckResultVO.getOrgId(), tool.getOrgByCode(efdcCheckResultVO.getOrgId()));
                }
                if (rowIndex > 50000) {
                    rowIndex = 0;
                    sheet = workbook.createSheet(GcI18nUtil.getMessage((String)"gc.efdcDataCheck.auditResults") + sheetIndex++);
                }
                HSSFRow dataRow = sheet.createRow(rowIndex++);
                this.excelOneRow(dataRow, styles, efdcCheckResultVO, (GcOrgCacheVO)orgCacheMap.get(efdcCheckResultVO.getOrgId()), groupByReport);
            }
        }
        catch (Exception e) {
            logger.error("", e);
        }
    }

    private DesignDataLinkDefine findDataLinkDefine(String formKey, String fieldKey, Map<String, Map<String, DesignDataLinkDefine>> cacheMap) {
        Map<String, DesignDataLinkDefine> zbId2LinkDefine = cacheMap.get(formKey);
        if (null == zbId2LinkDefine) {
            List dataRegionDefines = this.designTimeViewController.getAllRegionsInForm(formKey);
            if (dataRegionDefines.isEmpty()) {
                return null;
            }
            zbId2LinkDefine = new HashMap<String, DesignDataLinkDefine>();
            for (DesignDataRegionDefine dataRegionDefine : dataRegionDefines) {
                String regionKey = dataRegionDefine.getKey();
                Map<String, DesignDataLinkDefine> zbId2LinkDefineTemp = this.designTimeViewController.getAllLinksInRegion(regionKey).stream().collect(Collectors.toMap(DataLinkDefine::getLinkExpression, DesignDataLinkDefine2 -> DesignDataLinkDefine2));
                if (zbId2LinkDefineTemp == null) continue;
                zbId2LinkDefine.putAll(zbId2LinkDefineTemp);
            }
            cacheMap.put(formKey, zbId2LinkDefine);
        }
        return zbId2LinkDefine.get(fieldKey);
    }

    public List<TaskFormInfoVO> reports(String taskId, String schemeId) {
        List<DataCheckConfigEO> checkConfigEOs = this.dataCheckConfigDAO.queryBySchemeId(schemeId);
        HashMap<String, TaskFormInfoVO> taskFormInfoVOMap = new HashMap<String, TaskFormInfoVO>();
        try {
            List formDefines;
            if (!CollectionUtils.isEmpty(checkConfigEOs)) {
                DataCheckConfigEO checkConfigEO = checkConfigEOs.get(0);
                List formInfos = (List)JsonUtils.readValue((String)checkConfigEO.getFormsInfo(), (TypeReference)new TypeReference<List<EfdcCheckConfigFormInfo>>(){});
                for (EfdcCheckConfigFormInfo formInfo : formInfos) {
                    String formKey = formInfo.getFormKey();
                    if ("group".equals(formInfo.getFormType())) {
                        formDefines = this.runTimeViewController.getAllFormsInGroup(formKey);
                        if (null == formDefines) continue;
                        for (FormDefine formDefine : formDefines) {
                            TaskFormInfoVO taskFormInfoVO = new TaskFormInfoVO();
                            taskFormInfoVO.setId(formDefine.getKey());
                            taskFormInfoVO.setTitle(formDefine.getTitle());
                            taskFormInfoVO.setCode(formDefine.getFormCode());
                            taskFormInfoVOMap.put(taskFormInfoVO.getId(), taskFormInfoVO);
                        }
                        continue;
                    }
                    FormDefine formDefine = this.runTimeViewController.queryFormById(formKey);
                    if (null == formDefine) continue;
                    TaskFormInfoVO taskFormInfoVO = new TaskFormInfoVO();
                    taskFormInfoVO.setId(formDefine.getKey());
                    taskFormInfoVO.setTitle(formDefine.getTitle());
                    taskFormInfoVO.setCode(formDefine.getFormCode());
                    taskFormInfoVOMap.put(taskFormInfoVO.getId(), taskFormInfoVO);
                }
            }
            ArrayList<TaskFormInfoVO> results = new ArrayList<TaskFormInfoVO>();
            List groupDefines = this.runTimeViewController.getAllFormGroupsInFormScheme(schemeId);
            if (null != groupDefines) {
                for (FormGroupDefine formGroupDefine : groupDefines) {
                    TaskFormInfoVO group = new TaskFormInfoVO();
                    group.setId(formGroupDefine.getKey());
                    group.setTitle(formGroupDefine.getTitle());
                    group.setCode(formGroupDefine.getCode());
                    group.setChildren(new ArrayList());
                    formDefines = this.runTimeViewController.getAllFormsInGroup(formGroupDefine.getKey());
                    if (null != formDefines) {
                        for (FormDefine formDefine : formDefines) {
                            TaskFormInfoVO formInfoVO = (TaskFormInfoVO)taskFormInfoVOMap.get(formDefine.getKey());
                            if (null == formInfoVO) continue;
                            group.getChildren().add(formInfoVO);
                        }
                    }
                    if (group.getChildren().isEmpty()) continue;
                    results.add(group);
                }
            }
            return results;
        }
        catch (Exception e) {
            logger.error("", e);
            return new ArrayList<TaskFormInfoVO>();
        }
    }

    public List<TaskFormGroupFieldsInfoVO> efdcCheckZbs(String formId, String cwFormulaSchemeId) throws Exception {
        ArrayList<TaskFormGroupFieldsInfoVO> resultVoList = new ArrayList<TaskFormGroupFieldsInfoVO>();
        List<ConversionSystemItemInfoVO> resultList = this.getAllFormIndexInfo(formId, cwFormulaSchemeId);
        if (resultList != null && resultList.size() > 100) {
            LinkedHashMap fieldListMap = new LinkedHashMap();
            for (ConversionSystemItemInfoVO tempVo : resultList) {
                if (tempVo.getIndexTitle() == null) continue;
                String tempTitle = tempVo.getIndexTitle();
                int tempIndexNum = tempTitle.lastIndexOf(" ");
                String groupTitle = GcI18nUtil.getMessage((String)"gc.efdcDataCheck.other");
                if (tempIndexNum > 0) {
                    groupTitle = tempTitle.substring(tempIndexNum + 1);
                }
                tempVo.setIndexGroup(groupTitle);
                if (groupTitle.contains(".")) {
                    groupTitle = groupTitle.substring(0, groupTitle.indexOf("."));
                }
                List<ConversionSystemItemInfoVO> tempGroupList = new ArrayList();
                if (fieldListMap.containsKey(groupTitle)) {
                    tempGroupList = (List)fieldListMap.get(groupTitle);
                } else {
                    fieldListMap.put(groupTitle, tempGroupList);
                }
                tempGroupList.add(tempVo);
            }
            for (String tempKey : fieldListMap.keySet()) {
                this.addResultVO(resultVoList, tempKey, (List)fieldListMap.get(tempKey));
            }
        } else {
            this.addResultVO(resultVoList, null, resultList);
        }
        return resultVoList;
    }

    public List<ConversionSystemItemInfoVO> getAllFormIndexInfo(String formId, String cwFormulaSchemeId) throws Exception {
        FormDefine formDefine = this.runTimeViewController.queryFormById(formId);
        ArrayList<ConversionSystemItemInfoVO> resultList = new ArrayList<ConversionSystemItemInfoVO>();
        if (null == formDefine) {
            return resultList;
        }
        List<FieldDefine> allFieldDefines = this.fieldDefines(formId, cwFormulaSchemeId);
        if (CollectionUtils.isEmpty(allFieldDefines)) {
            return resultList;
        }
        allFieldDefines.sort((item1, item2) -> {
            if (item1 != null && item2 != null && item1.getCode() != null && item2.getCode() != null) {
                return item1.getCode().compareTo(item2.getCode());
            }
            return 0;
        });
        allFieldDefines.forEach(fieldDefine -> {
            FieldType fieldType = fieldDefine.getType();
            if (!(FieldType.FIELD_TYPE_DECIMAL.equals((Object)fieldType) || FieldType.FIELD_TYPE_FLOAT.equals((Object)fieldType) || FieldType.FIELD_TYPE_INTEGER.equals((Object)fieldType))) {
                return;
            }
            ConversionSystemItemInfoVO tmpVo = new ConversionSystemItemInfoVO();
            tmpVo.setFormId(formId);
            tmpVo.setIndexCode(fieldDefine.getCode());
            tmpVo.setIndexId(fieldDefine.getKey());
            tmpVo.setIndexTitle(fieldDefine.getTitle());
            tmpVo.setRateTypeCode("null");
            resultList.add(tmpVo);
        });
        return resultList;
    }

    private List<FieldDefine> fieldDefines(String formId, String cwFormulaSchemeId) throws Exception {
        if ("00000000-0000-0000-0000-000000000000".equals(cwFormulaSchemeId)) {
            ArrayList<FieldDefine> formAllFieldDefines = new ArrayList<FieldDefine>();
            List allRegionsInForm = this.runTimeViewController.getAllRegionsInForm(formId);
            if (allRegionsInForm != null && allRegionsInForm.size() > 0) {
                for (DataRegionDefine regionDefine : allRegionsInForm) {
                    List fieldDefines = this.dataDefinitionRuntimeController.queryFieldDefinesInRange((Collection)this.runTimeViewController.getFieldKeysInRegion(regionDefine.getKey()));
                    if (CollectionUtils.isEmpty((Collection)fieldDefines)) continue;
                    fieldDefines.remove(null);
                    formAllFieldDefines.addAll(fieldDefines);
                }
            }
            return formAllFieldDefines;
        }
        GcFetchDataInfo gcFetchDataInfo = this.fillFormulaGroup(cwFormulaSchemeId, formId);
        return gcFetchDataInfo.getRetEfdcZbFieldDefines();
    }

    private void addResultVO(List<TaskFormGroupFieldsInfoVO> resultVoList, String groupTitle, List<ConversionSystemItemInfoVO> resultList) {
        if (resultList.size() > 200) {
            LinkedHashMap fieldListMap = new LinkedHashMap();
            boolean existFlag = false;
            for (ConversionSystemItemInfoVO tempVo : resultList) {
                String tempGroupTitle = GcI18nUtil.getMessage((String)"gc.efdcDataCheck.other");
                if (tempVo.getIndexGroup() != null && tempVo.getIndexGroup().contains(".")) {
                    existFlag = true;
                    tempGroupTitle = tempVo.getIndexGroup().substring(tempVo.getIndexGroup().indexOf(".") + 1);
                }
                List<ConversionSystemItemInfoVO> tempGroupList = new ArrayList();
                if (fieldListMap.containsKey(tempGroupTitle)) {
                    tempGroupList = (List)fieldListMap.get(tempGroupTitle);
                } else {
                    fieldListMap.put(tempGroupTitle, tempGroupList);
                }
                tempGroupList.add(tempVo);
            }
            if (existFlag) {
                TaskFormGroupFieldsInfoVO tmpVo = this.getResultVO(groupTitle, null);
                tmpVo.setChildFlag(true);
                ArrayList<TaskFormGroupFieldsInfoVO> children = new ArrayList<TaskFormGroupFieldsInfoVO>();
                for (String tempKey : fieldListMap.keySet()) {
                    children.add(this.getResultVO(tempKey, (List)fieldListMap.get(tempKey)));
                }
                tmpVo.setChildren(children);
                resultVoList.add(tmpVo);
            } else {
                resultVoList.add(this.getResultVO(groupTitle, resultList));
            }
        } else {
            resultVoList.add(this.getResultVO(groupTitle, resultList));
        }
    }

    private TaskFormGroupFieldsInfoVO getResultVO(String groupTitle, List<ConversionSystemItemInfoVO> resultList) {
        TaskFormGroupFieldsInfoVO resultVO = new TaskFormGroupFieldsInfoVO();
        resultVO.setTitle(groupTitle);
        resultVO.setDataList(resultList);
        return resultVO;
    }

    public int getCheckZbCount() {
        return this.checkZbCount.get();
    }

    public int getFailZbCount() {
        return this.failZbCount.get();
    }

    public Map<String, Integer> getForm2CheckZbCount() {
        return this.form2CheckZbCount;
    }

    public Map<String, Integer> getForm2CheckZbAllOrgCount() {
        return this.form2CheckZbAllOrgCount;
    }

    public Map<String, Integer> getForm2FailZbAllOrgCount() {
        return this.form2FailZbAllOrgCount;
    }

    public StringBuffer getLog() {
        return this.log;
    }

    public Set<String> getErrorUnitSet() {
        return this.errorUnitSet;
    }

    @Override
    @Transactional
    public boolean evaluteEfdcFunc(QueryContext queryContext) {
        String unitId;
        DimensionValueSet dimensionValueSet = queryContext.getMasterKeys();
        ExecutorContext exeContext = queryContext.getExeContext();
        String asynTaskKey = unitId = (String)dimensionValueSet.getValue("MD_ORG");
        GcBatchEfdcCheckInfo batchCheckInfo = new GcBatchEfdcCheckInfo();
        ReportFmlExecEnvironment env = (ReportFmlExecEnvironment)exeContext.getEnv();
        String formSchemeKey = env.getFormSchemeKey();
        try {
            String taskId = env.getTaskDefine().getKey();
            batchCheckInfo.setTaskKey(taskId);
        }
        catch (Exception e) {
            logger.error("", e);
        }
        batchCheckInfo.setFormSchemeKey(formSchemeKey);
        batchCheckInfo.setDbTask(false);
        try {
            this.dataCheckResultDAO.deleteByAsynTaskId(asynTaskKey);
            this.batchEfdcDataCheck(asynTaskKey, batchCheckInfo, dimensionValueSet);
        }
        catch (ConnectException e) {
            logger.error("", e);
            return false;
        }
        return this.getFailZbCount() <= 0;
    }

    public void setAsyncTaskMonitor(AsyncTaskMonitor asyncTaskMonitor) {
        this.asyncTaskMonitor = asyncTaskMonitor;
    }

    private void initProgress(DimensionValueSet dimensionValueSet) {
        int totalCircleCount = 1;
        for (int i = 0; i < dimensionValueSet.size(); ++i) {
            Object dimensionValue = dimensionValueSet.getValue(i);
            if (!(dimensionValue instanceof List)) continue;
            totalCircleCount = ((List)dimensionValue).size() * totalCircleCount;
        }
        this.monitor = new GcBatchCheckParallelMonitor(this.asyncTaskMonitor);
        this.monitor.onProgress(0.05);
        this.monitor.setStep(0.95 / (double)totalCircleCount);
    }

    @Override
    public FormTree queryDataCheckConfigForms(String shcemeId, String dataTime) {
        try {
            FormTree formTree = this.formTabSelectService.getFormTree(shcemeId, dataTime);
            List<DataCheckConfigEO> dataCheckConfigEOs = this.dataCheckConfigDAO.queryBySchemeId(shcemeId);
            if (CollectionUtils.isEmpty(dataCheckConfigEOs)) {
                return null;
            }
            String formsInfoJsonStr = dataCheckConfigEOs.get(0).getFormsInfo();
            if (StringUtils.isEmpty((String)formsInfoJsonStr)) {
                return null;
            }
            List formInfos = (List)JsonUtils.readValue((String)formsInfoJsonStr, (TypeReference)new TypeReference<List<EfdcCheckConfigFormInfo>>(){});
            Set<String> formKeySet = formInfos.stream().map(EfdcCheckConfigFormInfo::getFormKey).collect(Collectors.toSet());
            if (formKeySet.contains(UUIDUtils.emptyUUIDStr())) {
                return formTree;
            }
            this.filterFormTree(formTree.getTree().getChildren(), formKeySet, null, null);
            return formTree;
        }
        catch (Exception e) {
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.efdcDataCheck.getReportTreeError"), (Throwable)e);
        }
    }

    @Override
    public FormTree queryDataCheckConfigFormsByReport(String shcemeId, Map<String, DimensionValue> dimensionSetMap) {
        try {
            DimensionValue dataTime = dimensionSetMap.get("DATATIME");
            FormTree formTree = this.formTabSelectService.getFormTree(shcemeId, dataTime.getValue());
            List<DataCheckConfigEO> dataCheckConfigEOs = this.dataCheckConfigDAO.queryBySchemeId(shcemeId);
            if (CollectionUtils.isEmpty(dataCheckConfigEOs)) {
                return null;
            }
            String formsInfoJsonStr = dataCheckConfigEOs.get(0).getFormsInfo();
            if (StringUtils.isEmpty((String)formsInfoJsonStr)) {
                return null;
            }
            List formInfos = (List)JsonUtils.readValue((String)formsInfoJsonStr, (TypeReference)new TypeReference<List<EfdcCheckConfigFormInfo>>(){});
            Set<String> formKeySet = formInfos.stream().map(EfdcCheckConfigFormInfo::getFormKey).collect(Collectors.toSet());
            if (formKeySet.contains(UUIDUtils.emptyUUIDStr())) {
                return formTree;
            }
            DimensionValueSet dimensionValueSet = new DimensionValueSet();
            for (Map.Entry<String, DimensionValue> entry : dimensionSetMap.entrySet()) {
                dimensionValueSet.setValue(entry.getKey(), (Object)entry.getValue().getValue());
            }
            JtableContext jtableContext = new JtableContext();
            jtableContext.setFormSchemeKey(shcemeId);
            jtableContext.setDimensionSet(dimensionSetMap);
            this.filterFormTree(formTree.getTree().getChildren(), formKeySet, jtableContext, dimensionValueSet);
            return formTree;
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void filterFormTree(List<Tree<FormTreeItem>> formTreeItemList, Set<String> formKeySet, JtableContext jtableContext, DimensionValueSet dimensionValueSet) {
        if (formTreeItemList == null || formTreeItemList.size() == 0) {
            return;
        }
        for (int i = formTreeItemList.size() - 1; i >= 0; --i) {
            FormTreeItem data;
            FormDefine formDefine;
            Tree<FormTreeItem> item = formTreeItemList.get(i);
            boolean formCondition = true;
            if (null != jtableContext && !ObjectUtils.isEmpty(formDefine = this.runTimeViewController.queryFormById((data = (FormTreeItem)item.getData()).getKey()))) {
                formCondition = ConverterUtils.getAsBoolean((Object)this.formTabSelectService.isFormCondition(jtableContext, formDefine.getFormCondition(), dimensionValueSet), (Boolean)true);
            }
            if (formKeySet.contains(((FormTreeItem)item.getData()).getKey()) && formCondition) continue;
            if (item.getChildren() != null && item.getChildren().size() > 0) {
                this.filterFormTree(item.getChildren(), formKeySet, jtableContext, dimensionValueSet);
            }
            if (item.getChildren() != null && item.getChildren().size() != 0) continue;
            formTreeItemList.remove(i);
        }
    }

    public void reconstructReportZbDataMap(Map<String, Set<String>> reportZbDataMap) {
        HashMap<String, String> tableCodeToKeyMap = new HashMap<String, String>();
        this.reportId2ZbGuidMap = new HashMap<String, Set<String>>();
        for (Map.Entry<String, Set<String>> entry : reportZbDataMap.entrySet()) {
            HashSet<String> zbGuidSet = new HashSet<String>();
            for (String field : entry.getValue()) {
                try {
                    FieldDefine fieldDefine;
                    String tableKey;
                    int leftBracket = field.indexOf(91);
                    int rightBracket = field.indexOf(93);
                    String tableCode = field.substring(0, leftBracket);
                    String fieldCode = field.substring(leftBracket + 1, rightBracket);
                    if (tableCodeToKeyMap.containsKey(tableCode)) {
                        tableKey = (String)tableCodeToKeyMap.get(tableCode);
                    } else {
                        TableDefine tableDefine = this.dataDefinitionRuntimeController.queryTableDefineByCode(tableCode);
                        if (tableDefine == null) continue;
                        tableKey = tableDefine.getKey();
                        tableCodeToKeyMap.put(tableCode, tableKey);
                    }
                    if ((fieldDefine = this.dataDefinitionRuntimeController.queryFieldByCodeInTable(fieldCode, tableKey)) == null) continue;
                    zbGuidSet.add(fieldDefine.getKey());
                }
                catch (Exception e) {
                    FormDefine formDefine = this.runTimeViewController.queryFormById(entry.getKey().toString());
                    this.getLog().append(GcI18nUtil.getMessage((String)"gc.efdcDataCheck.checkZbSetting", (Object[])new Object[]{formDefine.getTitle(), field}));
                }
            }
            this.reportId2ZbGuidMap.put(entry.getKey(), zbGuidSet);
        }
    }

    private String getCurrencyName(String currencyCode) {
        if (StringUtils.isEmpty((String)currencyCode)) {
            return "";
        }
        return this.currencyCodeAndTitle.computeIfAbsent(currencyCode, key -> {
            GcBaseData currency = GcBaseDataCenterTool.getInstance().queryBasedataByCode("MD_CURRENCY", currencyCode);
            return currency == null ? currencyCode : currency.getTitle();
        });
    }
}

