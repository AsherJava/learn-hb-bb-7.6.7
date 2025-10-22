/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.oss.ObjectInfo
 *  com.jiuqi.bi.security.PathUtils
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.authz2.service.EntityIdentityService
 *  com.jiuqi.np.authz2.service.RoleService
 *  com.jiuqi.np.authz2.service.SystemIdentityService
 *  com.jiuqi.np.blob.util.BeanUtil
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.i18n.helper.I18nHelper
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.np.period.DefaultPeriodAdapter
 *  com.jiuqi.np.period.PeriodModifier
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.np.util.tree.Tree
 *  com.jiuqi.nr.annotation.service.IAnnotationTypeService
 *  com.jiuqi.nr.attachment.service.FilePoolService
 *  com.jiuqi.nr.common.cache.remote.CacheObjectResourceRemote
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.context.cxt.DsContextHolder
 *  com.jiuqi.nr.data.access.util.DimensionValueSetUtil
 *  com.jiuqi.nr.data.logic.internal.obj.EntityData
 *  com.jiuqi.nr.data.logic.internal.util.entity.EntityUtil
 *  com.jiuqi.nr.data.text.param.TextParams
 *  com.jiuqi.nr.data.text.param.TextType
 *  com.jiuqi.nr.data.text.service.ExpTextService
 *  com.jiuqi.nr.dataentity.entity.IDataEntity
 *  com.jiuqi.nr.dataentity.entity.IDataEntityRow
 *  com.jiuqi.nr.dataentity.param.DataEntityContext
 *  com.jiuqi.nr.dataentity.service.DataEntityService
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DataFieldKind
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.diminfo.facade.service.IDimInfoService
 *  com.jiuqi.nr.definition.api.IRunTimeViewController
 *  com.jiuqi.nr.definition.common.DataLinkType
 *  com.jiuqi.nr.definition.common.DataRegionKind
 *  com.jiuqi.nr.definition.common.FormType
 *  com.jiuqi.nr.definition.controller.IFormulaRunTimeController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.controller.ITaskOptionController
 *  com.jiuqi.nr.definition.facade.AnalysisSchemeParamDefine
 *  com.jiuqi.nr.definition.facade.AuditType
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormGroupDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.FormulaSchemeDefine
 *  com.jiuqi.nr.definition.facade.FormulaVariDefine
 *  com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.facade.TaskGroupDefine
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nr.definition.internal.impl.FillDateType
 *  com.jiuqi.nr.definition.internal.runtime.controller.IRunTimeFormulaVariableService
 *  com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeFormulaSchemeService
 *  com.jiuqi.nr.definition.internal.service.AuditTypeDefineService
 *  com.jiuqi.nr.definition.internal.stream.param.TaskOrgLinkListStream
 *  com.jiuqi.nr.definition.option.core.OptionWrapper
 *  com.jiuqi.nr.definition.service.IFormSchemeService
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.file.FileService
 *  com.jiuqi.nr.fileupload.service.FileUploadOssService
 *  com.jiuqi.nr.holiday.manager.bean.HolidayDefine
 *  com.jiuqi.nr.holiday.manager.service.IHolidayManagerService
 *  com.jiuqi.nr.io.params.base.TableContext
 *  com.jiuqi.nr.io.params.input.OptTypes
 *  com.jiuqi.nr.io.service.impl.CsvExportServiceImpl
 *  com.jiuqi.nr.io.service.impl.TxtExportServiceImpl
 *  com.jiuqi.nr.io.util.FileUtil
 *  com.jiuqi.nr.jtable.exception.NotFoundFormSchemeException
 *  com.jiuqi.nr.jtable.exception.NotFoundTaskException
 *  com.jiuqi.nr.jtable.params.base.EntityViewData
 *  com.jiuqi.nr.jtable.params.base.FieldData
 *  com.jiuqi.nr.jtable.params.base.FormData
 *  com.jiuqi.nr.jtable.params.base.FormulaData
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nr.jtable.params.base.MeasureViewData
 *  com.jiuqi.nr.jtable.params.base.RegionData
 *  com.jiuqi.nr.jtable.params.input.EntityQueryByViewInfo
 *  com.jiuqi.nr.jtable.params.input.FieldQueryInfo
 *  com.jiuqi.nr.jtable.params.output.DescriptionInfo
 *  com.jiuqi.nr.jtable.params.output.EntityData
 *  com.jiuqi.nr.jtable.params.output.EntityReturnInfo
 *  com.jiuqi.nr.jtable.params.output.FormTableFields
 *  com.jiuqi.nr.jtable.params.output.FormTables
 *  com.jiuqi.nr.jtable.params.output.FormulaCheckResultInfo
 *  com.jiuqi.nr.jtable.params.output.FormulaCheckReturnInfo
 *  com.jiuqi.nr.jtable.params.output.FormulaNodeInfo
 *  com.jiuqi.nr.jtable.params.output.SecretLevelItem
 *  com.jiuqi.nr.jtable.service.IJtableEntityService
 *  com.jiuqi.nr.jtable.service.IJtableParamService
 *  com.jiuqi.nr.jtable.service.IJtableResourceService
 *  com.jiuqi.nr.jtable.service.ISecretLevelService
 *  com.jiuqi.nr.jtable.util.DimensionValueSetUtil
 *  com.jiuqi.nr.jtable.util.JsonUtil
 *  com.jiuqi.nr.period.common.utils.PeriodUtils
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nr.period.internal.adapter.IPeriodProvider
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nr.period.modal.IPeriodEntity
 *  com.jiuqi.nr.period.modal.IPeriodRow
 *  com.jiuqi.nr.period.web.IrregularPeriodController
 *  com.jiuqi.nr.time.setting.de.HolidayRange
 *  com.jiuqi.nvwa.definition.service.DesignDataModelService
 *  com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService
 *  javax.annotation.Resource
 *  org.springframework.transaction.annotation.Propagation
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.nr.dataentry.internal.service;

import com.jiuqi.bi.oss.ObjectInfo;
import com.jiuqi.bi.security.PathUtils;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.authz2.service.EntityIdentityService;
import com.jiuqi.np.authz2.service.RoleService;
import com.jiuqi.np.authz2.service.SystemIdentityService;
import com.jiuqi.np.blob.util.BeanUtil;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.i18n.helper.I18nHelper;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.np.period.DefaultPeriodAdapter;
import com.jiuqi.np.period.PeriodModifier;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.np.util.tree.Tree;
import com.jiuqi.nr.annotation.service.IAnnotationTypeService;
import com.jiuqi.nr.attachment.service.FilePoolService;
import com.jiuqi.nr.common.cache.remote.CacheObjectResourceRemote;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.context.cxt.DsContextHolder;
import com.jiuqi.nr.data.logic.internal.obj.EntityData;
import com.jiuqi.nr.data.logic.internal.util.entity.EntityUtil;
import com.jiuqi.nr.data.text.param.TextParams;
import com.jiuqi.nr.data.text.param.TextType;
import com.jiuqi.nr.data.text.service.ExpTextService;
import com.jiuqi.nr.dataentity.entity.IDataEntity;
import com.jiuqi.nr.dataentity.entity.IDataEntityRow;
import com.jiuqi.nr.dataentity.param.DataEntityContext;
import com.jiuqi.nr.dataentity.service.DataEntityService;
import com.jiuqi.nr.dataentry.bean.BatchCheckExportInfo;
import com.jiuqi.nr.dataentry.bean.BatchCheckParam;
import com.jiuqi.nr.dataentry.bean.CustomPeriodData;
import com.jiuqi.nr.dataentry.bean.DWorkflowConfig;
import com.jiuqi.nr.dataentry.bean.DataEntityReturnInfo;
import com.jiuqi.nr.dataentry.bean.DataEntryContext;
import com.jiuqi.nr.dataentry.bean.DataEntryInitParam;
import com.jiuqi.nr.dataentry.bean.DataentryEntityQueryInfo;
import com.jiuqi.nr.dataentry.bean.ExportData;
import com.jiuqi.nr.dataentry.bean.ExportParam;
import com.jiuqi.nr.dataentry.bean.ExportPdfType;
import com.jiuqi.nr.dataentry.bean.FormSchemeResult;
import com.jiuqi.nr.dataentry.bean.FormsQueryResult;
import com.jiuqi.nr.dataentry.bean.FormulaAuditType;
import com.jiuqi.nr.dataentry.bean.FormulaVariable;
import com.jiuqi.nr.dataentry.bean.FuncExecResult;
import com.jiuqi.nr.dataentry.bean.InitLinkParam;
import com.jiuqi.nr.dataentry.bean.LogInfo;
import com.jiuqi.nr.dataentry.bean.MeasureViewData;
import com.jiuqi.nr.dataentry.bean.PeriodRegion;
import com.jiuqi.nr.dataentry.bean.SearchFieldItem;
import com.jiuqi.nr.dataentry.bean.facade.FDimensionState;
import com.jiuqi.nr.dataentry.config.SlimButtonsConfig;
import com.jiuqi.nr.dataentry.deploy.util.NvwaDataModelDeployUtil;
import com.jiuqi.nr.dataentry.export.IDataEntryExportService;
import com.jiuqi.nr.dataentry.gather.ActionType;
import com.jiuqi.nr.dataentry.internal.service.FormGroupProvider;
import com.jiuqi.nr.dataentry.internal.service.ICustomCheckResultExcelColumnService;
import com.jiuqi.nr.dataentry.internal.service.util.CheckReviewTransformUtil;
import com.jiuqi.nr.dataentry.internal.service.util.DateTimeUtil;
import com.jiuqi.nr.dataentry.internal.service.util.SchemePeriodHelper;
import com.jiuqi.nr.dataentry.model.DimensionObj;
import com.jiuqi.nr.dataentry.model.FuncExecuteConfig;
import com.jiuqi.nr.dataentry.model.TreeNodeItem;
import com.jiuqi.nr.dataentry.monitor.BatchExportProgressMonitor;
import com.jiuqi.nr.dataentry.monitor.SimpleAsyncProgressMonitor;
import com.jiuqi.nr.dataentry.paramInfo.AllCheckInfo;
import com.jiuqi.nr.dataentry.paramInfo.BatchCheckInfo;
import com.jiuqi.nr.dataentry.paramInfo.BatchCheckStatistics;
import com.jiuqi.nr.dataentry.paramInfo.BatchReturnInfo;
import com.jiuqi.nr.dataentry.paramInfo.FormGroupData;
import com.jiuqi.nr.dataentry.paramInfo.FormSchemeData;
import com.jiuqi.nr.dataentry.paramInfo.FormsParam;
import com.jiuqi.nr.dataentry.paramInfo.TaskData;
import com.jiuqi.nr.dataentry.service.ExportExcelNameService;
import com.jiuqi.nr.dataentry.service.IAllCheckService;
import com.jiuqi.nr.dataentry.service.IBatchCheckResultService;
import com.jiuqi.nr.dataentry.service.IBatchCheckService;
import com.jiuqi.nr.dataentry.service.IDataEntryParamService;
import com.jiuqi.nr.dataentry.service.IFuncExecuteService;
import com.jiuqi.nr.dataentry.service.IPermission;
import com.jiuqi.nr.dataentry.service.IReviewInfoService;
import com.jiuqi.nr.dataentry.service.ITemplateConfigService;
import com.jiuqi.nr.dataentry.service.IWorkflowService;
import com.jiuqi.nr.dataentry.tree.FormTree;
import com.jiuqi.nr.dataentry.tree.FormTreeItem;
import com.jiuqi.nr.dataentry.util.DataEntryUtil;
import com.jiuqi.nr.dataentry.util.LoggerUtil;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DataFieldKind;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.diminfo.facade.service.IDimInfoService;
import com.jiuqi.nr.definition.api.IRunTimeViewController;
import com.jiuqi.nr.definition.common.DataLinkType;
import com.jiuqi.nr.definition.common.DataRegionKind;
import com.jiuqi.nr.definition.common.FormType;
import com.jiuqi.nr.definition.controller.IFormulaRunTimeController;
import com.jiuqi.nr.definition.controller.ITaskOptionController;
import com.jiuqi.nr.definition.facade.AnalysisSchemeParamDefine;
import com.jiuqi.nr.definition.facade.AuditType;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormGroupDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import com.jiuqi.nr.definition.facade.FormulaVariDefine;
import com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.facade.TaskGroupDefine;
import com.jiuqi.nr.definition.internal.impl.FillDateType;
import com.jiuqi.nr.definition.internal.runtime.controller.IRunTimeFormulaVariableService;
import com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeFormulaSchemeService;
import com.jiuqi.nr.definition.internal.service.AuditTypeDefineService;
import com.jiuqi.nr.definition.internal.stream.param.TaskOrgLinkListStream;
import com.jiuqi.nr.definition.option.core.OptionWrapper;
import com.jiuqi.nr.definition.service.IFormSchemeService;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.file.FileService;
import com.jiuqi.nr.fileupload.service.FileUploadOssService;
import com.jiuqi.nr.holiday.manager.bean.HolidayDefine;
import com.jiuqi.nr.holiday.manager.service.IHolidayManagerService;
import com.jiuqi.nr.io.params.base.TableContext;
import com.jiuqi.nr.io.params.input.OptTypes;
import com.jiuqi.nr.io.service.impl.CsvExportServiceImpl;
import com.jiuqi.nr.io.service.impl.TxtExportServiceImpl;
import com.jiuqi.nr.io.util.FileUtil;
import com.jiuqi.nr.jtable.exception.NotFoundFormSchemeException;
import com.jiuqi.nr.jtable.exception.NotFoundTaskException;
import com.jiuqi.nr.jtable.params.base.EntityViewData;
import com.jiuqi.nr.jtable.params.base.FieldData;
import com.jiuqi.nr.jtable.params.base.FormData;
import com.jiuqi.nr.jtable.params.base.FormulaData;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.params.base.RegionData;
import com.jiuqi.nr.jtable.params.input.EntityQueryByViewInfo;
import com.jiuqi.nr.jtable.params.input.FieldQueryInfo;
import com.jiuqi.nr.jtable.params.output.DescriptionInfo;
import com.jiuqi.nr.jtable.params.output.EntityReturnInfo;
import com.jiuqi.nr.jtable.params.output.FormTableFields;
import com.jiuqi.nr.jtable.params.output.FormTables;
import com.jiuqi.nr.jtable.params.output.FormulaCheckResultInfo;
import com.jiuqi.nr.jtable.params.output.FormulaCheckReturnInfo;
import com.jiuqi.nr.jtable.params.output.FormulaNodeInfo;
import com.jiuqi.nr.jtable.params.output.SecretLevelItem;
import com.jiuqi.nr.jtable.service.IJtableEntityService;
import com.jiuqi.nr.jtable.service.IJtableParamService;
import com.jiuqi.nr.jtable.service.IJtableResourceService;
import com.jiuqi.nr.jtable.service.ISecretLevelService;
import com.jiuqi.nr.jtable.util.DimensionValueSetUtil;
import com.jiuqi.nr.jtable.util.JsonUtil;
import com.jiuqi.nr.period.common.utils.PeriodUtils;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nr.period.internal.adapter.IPeriodProvider;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import com.jiuqi.nr.period.modal.IPeriodRow;
import com.jiuqi.nr.period.web.IrregularPeriodController;
import com.jiuqi.nr.time.setting.de.HolidayRange;
import com.jiuqi.nvwa.definition.service.DesignDataModelService;
import com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.annotation.Resource;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@DependsOn(value={"i18nHelperSupport"})
public class FuncExecuteServiceImpl
implements IFuncExecuteService {
    private static final Logger logger = LoggerFactory.getLogger(FuncExecuteServiceImpl.class);
    private static final int MAXIMUM_LENGTH_OF_ERRORMSG = 32667;
    public static final String BETWEENFORMFORMULA = "00000000-0000-0000-0000-000000000000";
    @Resource
    private com.jiuqi.nr.definition.controller.IRunTimeViewController runtimeView;
    @Resource
    private IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    @Resource
    private IEntityViewRunTimeController entityViewRunTimeController;
    @Resource
    private IRunTimeFormulaVariableService formulaRuntimeTimeController;
    @Resource
    private IJtableParamService jtableParamService;
    @Resource
    private IDataAccessProvider dataAccessProvider;
    @Resource
    private ITemplateConfigService templateConfigService;
    @Resource
    private IDataEntryParamService dataEntryParamService;
    @Resource
    private IJtableResourceService jtableResourceService;
    @Resource
    private Map<String, IDataEntryExportService> exportServiceMap;
    @Autowired
    private List<IPermission> permissions;
    @Autowired
    private Map<String, IPermission> permissionsMap;
    @Resource
    private IBatchCheckService batchCheckService;
    @Autowired
    private IAllCheckService allCheckService;
    @Autowired
    private IJtableEntityService jtableEntityService;
    @Autowired
    private EntityIdentityService entityLinkService;
    @Autowired
    private ITaskOptionController iTaskOptionController;
    @Autowired
    private INvwaSystemOptionService iNvwaSystemOptionService;
    @Autowired
    private FormGroupProvider formGroupProvider;
    @Autowired
    private AuditTypeDefineService auditTypeDefineService;
    @Autowired
    private CacheObjectResourceRemote cacheObjectResourceRemote;
    @Autowired
    private TxtExportServiceImpl txtExportService;
    @Autowired
    private CsvExportServiceImpl csvExportService;
    @Autowired
    private IFormulaRunTimeController formulaRunTimeController;
    @Autowired
    private IWorkflowService workflowService;
    @Autowired
    private IrregularPeriodController irregularPeriodController;
    @Autowired
    private IReviewInfoService reviewInfoService;
    @Autowired
    private ISecretLevelService secretLevelService;
    @Autowired
    private ExpTextService expTextService;
    @Autowired
    private IPeriodEntityAdapter periodEntityAdapter;
    @Autowired
    private com.jiuqi.nr.definition.controller.IRunTimeViewController iRunTimeViewController;
    @Autowired
    private PeriodEngineService periodEngineService;
    @Autowired
    private IBatchCheckResultService batchCheckResultService;
    @Autowired
    private IFormSchemeService iFormSchemeService;
    @Autowired
    private IEntityViewRunTimeController iEntityViewRunTimeController;
    @Autowired
    private DataEntityService dataEntityService;
    @Autowired
    private SchemePeriodHelper schemePeriodHelper;
    @Autowired
    @Qualifier(value="nr")
    private I18nHelper i18nHelper;
    @Autowired
    private IDimInfoService iDimInfoService;
    @Autowired
    private IEntityMetaService iEntityMetaService;
    @Autowired
    private ExportExcelNameService exportExcelNameService;
    @Autowired
    private FilePoolService filePoolService;
    @Autowired(required=false)
    private ICustomCheckResultExcelColumnService iCustomCheckResultExcelColumnService;
    @Autowired
    private IRuntimeFormulaSchemeService runtimeFormulaSchemeService;
    @Autowired
    private FileUploadOssService fileUploadOssService;
    @Autowired
    private FileService fileService;
    @Autowired
    private IAnnotationTypeService annotationTypeService;
    @Autowired
    private IRuntimeDataSchemeService iRuntimeDataSchemeService;
    @Autowired
    private NvwaDataModelDeployUtil nvwaDataModelDeployUtil;
    @Autowired
    private DesignDataModelService designDataModelService;
    @Autowired
    private INvwaSystemOptionService nvwaSystemOptionService;
    @Autowired
    private ISecretLevelService iSecretLevelService;
    @Autowired
    private EntityUtil entityUtil;
    @Autowired
    private CheckReviewTransformUtil checkReviewTransformUtil;
    @Autowired
    private SlimButtonsConfig slimButtonsConfig;
    @Autowired
    private RoleService roleService;
    @Autowired
    private IHolidayManagerService iHolidayManagerService;

    @Override
    @Transactional(propagation=Propagation.SUPPORTS, readOnly=true)
    public FuncExecResult getDataEntryEnv(DataEntryInitParam param) throws Exception {
        Date beginDate;
        Date[] periodRegion;
        Optional<TaskGroupDefine> taskGroupDefine;
        FuncExecResult result = new FuncExecResult();
        String functionCode = param.getFunctionCode();
        if (StringUtils.isEmpty((String)functionCode)) {
            functionCode = "dataentry_defaultFuncode";
        }
        FuncExecuteConfig templateConfig = this.templateConfigService.getFuncExecuteConfigByCode(functionCode);
        String taskKey = param.getTaskKey();
        if (StringUtils.isEmpty((String)taskKey)) {
            result.getFuncParam().setHaveTask(false);
            return result;
        }
        List allTaskGroup = this.iRunTimeViewController.getAllTaskGroup();
        if (allTaskGroup != null && allTaskGroup.size() > 0 && (taskGroupDefine = allTaskGroup.stream().filter(e -> e.getKey().equals(taskKey)).findAny()).isPresent()) {
            result.getFuncParam().setHaveTask(false);
            return result;
        }
        if (StringUtils.isNotEmpty((String)this.slimButtonsConfig.getButtons())) {
            List<String> list = Arrays.asList(this.slimButtonsConfig.getButtons().split(","));
            List<TreeNodeItem> chooseButtons = templateConfig.getConfig().getToolbarViewConfig().getChooseButtons();
            chooseButtons = chooseButtons.stream().filter(item -> list.contains(item.getCode())).collect(Collectors.toList());
            for (TreeNodeItem treeNodeItem : chooseButtons) {
                if (treeNodeItem.getChildren() == null || treeNodeItem.getChildren().size() <= 0) continue;
                List<TreeNodeItem> list3 = treeNodeItem.getChildren();
                list3 = list3.stream().filter(item -> list.contains(item.getCode())).collect(Collectors.toList());
                treeNodeItem.setChildren(list3);
            }
            List<TreeNodeItem> menus = templateConfig.getConfig().getGridViewConfig().getMenus();
            if (menus != null && menus.size() > 0) {
                menus = menus.stream().filter(item -> list.contains(item.getCode())).collect(Collectors.toList());
                for (TreeNodeItem treeNodeItem : menus) {
                    if (treeNodeItem.getChildren() == null || treeNodeItem.getChildren().size() <= 0) continue;
                    List<TreeNodeItem> children = treeNodeItem.getChildren();
                    children = children.stream().filter(item -> list.contains(item.getCode())).collect(Collectors.toList());
                    treeNodeItem.setChildren(children);
                }
            }
            templateConfig.getConfig().getGridViewConfig().setMenus(menus);
            templateConfig.getConfig().getToolbarViewConfig().setChooseButtons(chooseButtons);
        }
        result.getFuncParam().setTemplateConfig(templateConfig);
        TaskData taskData = this.dataEntryParamService.getRuntimeTaskByKey(taskKey);
        if (taskData == null) {
            throw new NotFoundTaskException(new String[]{taskKey});
        }
        result.setTask(taskData);
        PeriodWrapper currentPeriod = null;
        IPeriodProvider periodProvider = null;
        Object var9_12 = null;
        for (EntityViewData view : taskData.getEntitys()) {
            if (!this.periodEntityAdapter.isPeriodEntity(view.getKey())) continue;
            String string = view.getKey();
            periodProvider = this.periodEntityAdapter.getPeriodProvider(view.getKey());
            result.setPeriodModifyTitles(periodProvider.getPeriodDataByModifyTitle());
        }
        String string = templateConfig.getConfig().getDataEntryViewConfig().getPeriod();
        List schemePeriodLinkDefineList = null;
        HashMap<String, Integer> periodSchemeMap = new HashMap<String, Integer>();
        schemePeriodLinkDefineList = this.iRunTimeViewController.querySchemePeriodLinkByTask(taskKey);
        List<String> schemeKeyList = schemePeriodLinkDefineList.stream().map(SchemePeriodLinkDefine::getSchemeKey).distinct().collect(Collectors.toList());
        Date date = DateTimeUtil.getDay();
        FillDateType fillingDateType = taskData.getFillingDateType();
        String beginPeriodModify = null;
        Date dateAfterFormat = date;
        if (!fillingDateType.equals((Object)FillDateType.NONE) && taskData.getFillingDateDays() >= 0) {
            ArrayList<HolidayRange> holidayRanges = new ArrayList<HolidayRange>();
            List holidayDefines = this.iHolidayManagerService.doQueryHolidayDefine(String.valueOf(Calendar.getInstance().get(1)));
            if (holidayDefines != null && holidayDefines.size() > 0) {
                for (HolidayDefine holidayDefine : holidayDefines) {
                    holidayRanges.add(new HolidayRange(holidayDefine));
                }
            }
            if (fillingDateType.equals((Object)FillDateType.NATURAL_DAY)) {
                dateAfterFormat = DateTimeUtil.getDateOfBeforeDay(date, taskData.getFillingDateDays() - 1);
            } else if (fillingDateType.equals((Object)FillDateType.WORK_DAY)) {
                dateAfterFormat = DateTimeUtil.getDateOfBeforeWorkDay(date, taskData.getFillingDateDays() - 1, holidayRanges);
            }
            if (taskData.getPeriodType() != PeriodType.CUSTOM.type()) {
                String periodOfFormate = PeriodUtils.getPeriodFromDate((int)taskData.getPeriodType(), (Date)dateAfterFormat);
                beginPeriodModify = periodOfFormate;
                if (taskData.getPeriodOffset() != 0) {
                    PeriodModifier periodModifier = new PeriodModifier();
                    periodModifier.setPeriodModifier(taskData.getPeriodOffset());
                    beginPeriodModify = periodProvider.modify(beginPeriodModify, periodModifier);
                }
                if (StringUtils.isNotEmpty((String)string)) {
                    try {
                        int periodOffset = Integer.parseInt(string);
                        PeriodModifier periodModifierOftemp = new PeriodModifier();
                        periodModifierOftemp.setPeriodModifier(periodOffset);
                        beginPeriodModify = periodProvider.modify(beginPeriodModify, periodModifierOftemp);
                    }
                    catch (Exception e2) {
                        logger.info("\u5f55\u5165\u6a21\u677f\u914d\u7f6e\u7684\u65f6\u671f\u4fe1\u606f\u4e3a\u6307\u5b9a\u65f6\u671f\uff1a" + string + ",\u65e0\u9700\u8fdb\u884c\u65f6\u671f\u504f\u79fb\uff01");
                        throw new Exception("\u5f55\u5165\u6a21\u677f\u914d\u7f6e\u7684\u65f6\u671f\u4fe1\u606f\u4e3a\u6307\u5b9a\u65f6\u671f\uff1a" + string + ",\u65e0\u9700\u8fdb\u884c\u65f6\u671f\u504f\u79fb\uff01");
                    }
                }
            } else if (taskData.getPeriodOffset() != 0 || StringUtils.isNotEmpty((String)string) || taskData.getFillingDateDays() >= 0) {
                if (taskData.getFillingDateDays() >= 0) {
                    if (fillingDateType.equals((Object)FillDateType.NATURAL_DAY)) {
                        dateAfterFormat = DateTimeUtil.getDateOfBeforeDay(date, taskData.getFillingDateDays() - 1);
                    } else if (fillingDateType.equals((Object)FillDateType.WORK_DAY)) {
                        dateAfterFormat = DateTimeUtil.getDateOfBeforeWorkDay(date, taskData.getFillingDateDays() - 1, holidayRanges);
                    }
                }
                for (SchemePeriodLinkDefine schemePeriodLinkDefine : schemePeriodLinkDefineList) {
                    Object modify = schemePeriodLinkDefine.getPeriodKey();
                    if (taskData.getPeriodOffset() != 0) {
                        PeriodModifier periodModifier = new PeriodModifier();
                        periodModifier.setPeriodModifier(taskData.getPeriodOffset());
                        modify = periodProvider.modify((String)modify, periodModifier);
                    }
                    if (StringUtils.isNotEmpty((String)string)) {
                        try {
                            int periodOffset = Integer.parseInt(string);
                            PeriodModifier periodModifierOftemp = new PeriodModifier();
                            periodModifierOftemp.setPeriodModifier(periodOffset);
                            modify = periodProvider.modify((String)modify, periodModifierOftemp);
                        }
                        catch (Exception e3) {
                            logger.info("\u5f55\u5165\u6a21\u677f\u914d\u7f6e\u7684\u65f6\u671f\u4fe1\u606f\u4e3a\u6307\u5b9a\u65f6\u671f\uff1a" + string + ",\u65e0\u9700\u8fdb\u884c\u65f6\u671f\u504f\u79fb\uff01");
                            throw new Exception("\u5f55\u5165\u6a21\u677f\u914d\u7f6e\u7684\u65f6\u671f\u4fe1\u606f\u4e3a\u6307\u5b9a\u65f6\u671f\uff1a" + string + ",\u65e0\u9700\u8fdb\u884c\u65f6\u671f\u504f\u79fb\uff01");
                        }
                    }
                    FormSchemeDefine formScheme = this.runtimeView.getFormScheme(schemePeriodLinkDefine.getSchemeKey());
                    periodRegion = new Date[]{};
                    try {
                        periodRegion = this.periodEngineService.getPeriodAdapter().getPeriodProvider(formScheme.getDateTime()).getPeriodDateRegion((String)modify);
                    }
                    catch (ParseException e4) {
                        logger.info("\u83b7\u53d6\u81ea\u5b9a\u4e49\u65f6\u671f\u8d77\u59cb\u7ed3\u675f\u65f6\u95f4\u5931\u8d25\uff01");
                        throw new Exception("\u83b7\u53d6\u81ea\u5b9a\u4e49\u65f6\u671f\u8d77\u59cb\u7ed3\u675f\u65f6\u95f4\u5931\u8d25\uff01");
                    }
                    beginDate = periodRegion[0];
                    Date endDate = periodRegion[1];
                    if (!DateTimeUtil.isEffectiveDate(dateAfterFormat, beginDate, endDate)) continue;
                    beginPeriodModify = schemePeriodLinkDefine.getPeriodKey();
                    break;
                }
            }
        }
        List schemePeriodLinkDefineListAfterFormat = schemePeriodLinkDefineList;
        if (taskData.getPeriodType() != PeriodType.CUSTOM.type()) {
            schemePeriodLinkDefineListAfterFormat = schemePeriodLinkDefineList.stream().sorted((periodOne, periodTwo) -> PeriodUtils.comparePeriod((String)periodOne.getPeriodKey(), (String)periodTwo.getPeriodKey())).collect(Collectors.toList());
        }
        TaskDefine taskDefine = this.iRunTimeViewController.queryTaskDefine(taskKey);
        IPeriodEntityAdapter periodAdapter = this.periodEngineService.getPeriodAdapter();
        IPeriodEntity iPeriod = periodAdapter.getPeriodEntity(taskDefine.getDateTime());
        for (Object schemePeriodLinkDefineInfo : schemePeriodLinkDefineListAfterFormat) {
            if (beginPeriodModify != null && !fillingDateType.equals((Object)FillDateType.NONE) && taskData.getFillingDateDays() >= 0) {
                if (beginPeriodModify == null || !StringUtils.isNotEmpty((String)schemePeriodLinkDefineInfo.getPeriodKey()) || !StringUtils.isNotEmpty((String)schemePeriodLinkDefineInfo.getSchemeKey())) continue;
                if (PeriodUtils.isPeriod13((String)iPeriod.getCode(), (PeriodType)iPeriod.getPeriodType()) && PeriodUtils.isPeriod13Data((PeriodType)iPeriod.getPeriodType(), (String)schemePeriodLinkDefineInfo.getPeriodKey())) {
                    periodRegion = new Date[]{};
                    try {
                        periodRegion = this.periodEngineService.getPeriodAdapter().getPeriodProvider(taskDefine.getDateTime()).getPeriodDateRegion(schemePeriodLinkDefineInfo.getPeriodKey());
                    }
                    catch (ParseException e5) {
                        logger.info("\u83b7\u53d6\u81ea\u5b9a\u4e49\u65f6\u671f\u8d77\u59cb\u7ed3\u675f\u65f6\u95f4\u5931\u8d25\uff01");
                        throw new Exception("\u83b7\u53d6\u81ea\u5b9a\u4e49\u65f6\u671f\u8d77\u59cb\u7ed3\u675f\u65f6\u95f4\u5931\u8d25\uff01");
                    }
                    beginDate = periodRegion[0];
                    String periodOfFormate = PeriodUtils.getPeriodFromDate((int)taskData.getPeriodType(), (Date)beginDate);
                    if (PeriodUtils.comparePeriod((String)beginPeriodModify, (String)periodOfFormate) < 0) continue;
                    periodSchemeMap.put(schemePeriodLinkDefineInfo.getPeriodKey(), schemeKeyList.indexOf(schemePeriodLinkDefineInfo.getSchemeKey()));
                    continue;
                }
                if (PeriodUtils.comparePeriod((String)beginPeriodModify, (String)schemePeriodLinkDefineInfo.getPeriodKey()) < 0) continue;
                periodSchemeMap.put(schemePeriodLinkDefineInfo.getPeriodKey(), schemeKeyList.indexOf(schemePeriodLinkDefineInfo.getSchemeKey()));
                continue;
            }
            if (!StringUtils.isNotEmpty((String)schemePeriodLinkDefineInfo.getPeriodKey()) || !StringUtils.isNotEmpty((String)schemePeriodLinkDefineInfo.getSchemeKey())) continue;
            periodSchemeMap.put(schemePeriodLinkDefineInfo.getPeriodKey(), schemeKeyList.indexOf(schemePeriodLinkDefineInfo.getSchemeKey()));
        }
        if (periodSchemeMap.size() == 0) {
            String errorMessage = "\u672a\u5f00\u653e\u586b\u62a5\uff01";
            if (StringUtils.isNotEmpty((String)this.i18nHelper.getMessage("NOTOPENDATAENTRY"))) {
                errorMessage = this.i18nHelper.getMessage("NOTOPENDATAENTRY");
            }
            throw new Exception(errorMessage);
        }
        if (taskData.getPeriodType() == PeriodType.CUSTOM.type()) {
            ArrayList<CustomPeriodData> customPeriodDatas = new ArrayList<CustomPeriodData>();
            for (IPeriodRow periodRow : periodProvider.getPeriodItems()) {
                CustomPeriodData data = new CustomPeriodData();
                data.setCode(periodRow.getCode());
                data.setTitle(periodRow.getTitle());
                customPeriodDatas.add(data);
            }
            result.setCustomPeriodDatas(customPeriodDatas);
            IPeriodRow curPeriod = periodProvider.getCurPeriod();
            PeriodModifier periodModifier = new PeriodModifier();
            periodModifier.setPeriodModifier(taskData.getPeriodOffset());
            String modify = periodProvider.modify(curPeriod.getCode(), periodModifier);
            if (StringUtils.isNotEmpty((String)string)) {
                currentPeriod = new PeriodWrapper(modify);
                if (!periodSchemeMap.containsKey((currentPeriod = this.modifyPeriod(periodProvider, currentPeriod, string, taskData)).toString())) {
                    currentPeriod = new PeriodWrapper(modify);
                }
            } else {
                currentPeriod = new PeriodWrapper(modify);
            }
        } else {
            if (taskData.getPeriodType() == 7) {
                String periodOfWeek = PeriodUtils.getPeriodFromDate((int)taskData.getPeriodType(), (Date)new Date());
                currentPeriod = new PeriodWrapper(periodOfWeek);
                currentPeriod = this.modifyPeriod(periodProvider, currentPeriod, Integer.toString(taskData.getPeriodOffset()), taskData);
            } else {
                currentPeriod = DataEntryUtil.getCurrPeriod(taskData.getPeriodType(), taskData.getPeriodOffset(), taskData.getFromPeriod(), taskData.getToPeriod());
            }
            String currentPeriodString = currentPeriod.toString();
            if (StringUtils.isNotEmpty((String)string) && !periodSchemeMap.containsKey((currentPeriod = this.modifyPeriod(periodProvider, currentPeriod, string, taskData)).toString())) {
                currentPeriod = new PeriodWrapper(currentPeriodString);
            }
        }
        String currPeriodString = currentPeriod.toString();
        DefaultPeriodAdapter iPeriodAdapter = new DefaultPeriodAdapter();
        boolean priorPeriod = true;
        while (!periodSchemeMap.containsKey(currPeriodString)) {
            PeriodWrapper periodWrapper;
            if (priorPeriod) {
                periodWrapper = new PeriodWrapper(currPeriodString);
                boolean prevState = iPeriodAdapter.priorPeriod(periodWrapper);
                if (prevState && currPeriodString.compareTo(taskData.getFromPeriod()) >= 0) {
                    currPeriodString = periodWrapper.toString();
                    continue;
                }
                if (currPeriodString.compareTo(taskData.getFromPeriod()) >= 0) continue;
                priorPeriod = false;
                currPeriodString = currentPeriod.toString();
                continue;
            }
            periodWrapper = new PeriodWrapper(currPeriodString);
            boolean prevState = iPeriodAdapter.nextPeriod(periodWrapper);
            if (prevState && currPeriodString.compareTo(taskData.getToPeriod()) <= 0) {
                currPeriodString = periodWrapper.toString();
                continue;
            }
            if (currPeriodString.compareTo(taskData.getToPeriod()) <= 0) continue;
            currPeriodString = currentPeriod.toString();
            break;
        }
        if (StringUtils.isNotEmpty((String)string) && periodSchemeMap.containsKey(string)) {
            currPeriodString = string;
        }
        Map<String, Object> sysParamMap = this.getSysParam(taskData.getKey());
        if (StringUtils.isNotEmpty((String)param.getCatchePeriodInfo()) && ((Boolean)sysParamMap.get("JTABLE_CACHE_DATA_TIME")).booleanValue()) {
            currPeriodString = param.getCatchePeriodInfo();
        }
        if (StringUtils.isNotEmpty((String)param.getPeriodInfo()) && periodSchemeMap.containsKey(param.getPeriodInfo())) {
            currPeriodString = param.getPeriodInfo();
        }
        if (!periodSchemeMap.containsKey(currPeriodString)) {
            throw new Exception("\u6ca1\u6709\u5bf9\u5e94\u586b\u62a5\u671f");
        }
        List schemes = this.iRunTimeViewController.queryFormSchemeByTask(taskKey);
        ArrayList<PeriodRegion> periodRegionList = new ArrayList<PeriodRegion>();
        for (FormSchemeDefine formSchemeDefine : schemes) {
            List schemePeriodLinkDefineListOfScheme = this.iRunTimeViewController.querySchemePeriodLinkByScheme(formSchemeDefine.getKey());
            Iterator<SchemePeriodLinkDefine> schemePeriodLinkDefineListOfSchemeAfterFormat = new ArrayList<SchemePeriodLinkDefine>();
            if (!fillingDateType.equals((Object)FillDateType.NONE) && taskData.getFillingDateDays() >= 0) {
                for (SchemePeriodLinkDefine schemePeriodLinkDefine : schemePeriodLinkDefineListOfScheme) {
                    if (PeriodUtils.isPeriod13((String)iPeriod.getCode(), (PeriodType)iPeriod.getPeriodType()) && PeriodUtils.isPeriod13Data((PeriodType)iPeriod.getPeriodType(), (String)schemePeriodLinkDefine.getPeriodKey())) {
                        Object periodRegion2 = new Date[]{};
                        try {
                            periodRegion2 = this.periodEngineService.getPeriodAdapter().getPeriodProvider(formSchemeDefine.getDateTime()).getPeriodDateRegion(schemePeriodLinkDefine.getPeriodKey());
                        }
                        catch (ParseException e6) {
                            logger.info("\u83b7\u53d6\u81ea\u5b9a\u4e49\u65f6\u671f\u8d77\u59cb\u7ed3\u675f\u65f6\u95f4\u5931\u8d25\uff01");
                            throw new Exception("\u83b7\u53d6\u81ea\u5b9a\u4e49\u65f6\u671f\u8d77\u59cb\u7ed3\u675f\u65f6\u95f4\u5931\u8d25\uff01");
                        }
                        Date beginDate2 = periodRegion2[0];
                        String periodOfFormate = PeriodUtils.getPeriodFromDate((int)taskData.getPeriodType(), (Date)beginDate2);
                        if (PeriodUtils.comparePeriod((String)beginPeriodModify, (String)periodOfFormate) < 0) continue;
                        schemePeriodLinkDefineListOfSchemeAfterFormat.add(schemePeriodLinkDefine);
                        continue;
                    }
                    if (beginPeriodModify == null || PeriodUtils.comparePeriod((String)beginPeriodModify, (String)schemePeriodLinkDefine.getPeriodKey()) < 0) continue;
                    schemePeriodLinkDefineListOfSchemeAfterFormat.add(schemePeriodLinkDefine);
                }
            } else {
                schemePeriodLinkDefineListOfSchemeAfterFormat.addAll(schemePeriodLinkDefineListOfScheme);
            }
            periodRegionList.addAll(this.schemePeriodHelper.unSplitPeriod((List<SchemePeriodLinkDefine>)((Object)schemePeriodLinkDefineListOfSchemeAfterFormat), iPeriod.getKey()));
        }
        result.setPeriodRangeList(periodRegionList);
        result.setCurrentPeriodInfo(currPeriodString);
        String formSchemeKey = (String)schemeKeyList.get((Integer)periodSchemeMap.get(currPeriodString));
        result.setFormSchemeKey(formSchemeKey);
        result.setCurrentPeriodTitle(this.exportExcelNameService.getPeriodTitle(formSchemeKey, currPeriodString));
        result.setPeriodSchemeMap(periodSchemeMap);
        result.setSchemeKeyList(schemeKeyList);
        ArrayList<FormSchemeResult> schemeListResult = new ArrayList<FormSchemeResult>();
        schemeListResult.add(this.getDataEntryFormScheme(taskKey, formSchemeKey, result, templateConfig));
        result.setSchemes(schemeListResult);
        Map<String, String> entityTitleMap = taskData.getEntityTitleMap();
        for (EntityViewData entity : taskData.getEntitys()) {
            if (!entityTitleMap.containsKey(entity.getKey())) continue;
            entity.setTitle(entityTitleMap.get(entity.getKey()));
        }
        for (FormSchemeResult formSchemeResult : schemeListResult) {
            FormSchemeData scheme = formSchemeResult.getScheme();
            for (EntityViewData entity : scheme.getEntitys()) {
                if (!entityTitleMap.containsKey(entity.getKey())) continue;
                entity.setTitle(entityTitleMap.get(entity.getKey()));
            }
            DWorkflowConfig workflowConfig = scheme.getWorkflowConfig();
            if (workflowConfig == null || workflowConfig.getEntitys() == null) continue;
            for (EntityViewData entity : workflowConfig.getEntitys()) {
                if (!entityTitleMap.containsKey(entity.getKey())) continue;
                entity.setTitle(entityTitleMap.get(entity.getKey()));
            }
        }
        this.generateFuncParam(templateConfig, schemeListResult, taskData, result);
        boolean secretLevelEnable = this.iSecretLevelService.secretLevelEnable(taskKey);
        if (secretLevelEnable) {
            result.setSecretLevelItems(this.secretLevelService.getSecretLevelItems());
        } else {
            result.setSecretLevelItems(new ArrayList<SecretLevelItem>());
        }
        if ("2.0".equals(taskDefine.getVersion())) {
            result.getFuncParam().getSysParam().put("TASK2_OPEN", true);
        } else {
            result.getFuncParam().getSysParam().put("TASK2_OPEN", false);
        }
        return result;
    }

    private PeriodWrapper modifyPeriod(IPeriodProvider periodProvider, PeriodWrapper currentPeriod, String period, TaskData taskData) {
        try {
            int periodOffset = Integer.parseInt(period);
            periodProvider.modifyPeriod(currentPeriod, periodOffset);
            PeriodWrapper fromPeriod = new PeriodWrapper(taskData.getFromPeriod());
            PeriodWrapper toPeriod = new PeriodWrapper(taskData.getToPeriod());
            if (periodOffset > 0) {
                if (currentPeriod.compareTo((Object)toPeriod) > 0) {
                    currentPeriod = new PeriodWrapper(taskData.getToPeriod());
                }
            } else if (periodOffset < 0 && currentPeriod.compareTo((Object)fromPeriod) < 0) {
                currentPeriod = new PeriodWrapper(taskData.getFromPeriod());
            }
        }
        catch (Exception e) {
            currentPeriod = new PeriodWrapper(period);
        }
        return currentPeriod;
    }

    @Override
    public FormSchemeDefine queryFormScheme(String taskKey, String periodInfo) {
        FormSchemeDefine formSchemeDefine = null;
        SchemePeriodLinkDefine schemePeriodLinkDefine = null;
        try {
            schemePeriodLinkDefine = this.iRunTimeViewController.querySchemePeriodLinkByPeriodAndTask(periodInfo, taskKey);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        if (schemePeriodLinkDefine != null) {
            String schemeKey = schemePeriodLinkDefine.getSchemeKey();
            formSchemeDefine = this.iRunTimeViewController.getFormScheme(schemeKey);
        }
        return formSchemeDefine;
    }

    private void generateFuncParam(FuncExecuteConfig templateConfig, List<FormSchemeResult> schemeListResult, TaskData taskData, FuncExecResult result) {
        Map grantedEntityKeys = this.entityLinkService.getGrantedEntityKeys(NpContextHolder.getContext().getIdentityId());
        if (grantedEntityKeys.size() > 0) {
            ArrayList entityKeys = new ArrayList();
            for (Map.Entry item : grantedEntityKeys.entrySet()) {
                Iterator it = ((Collection)item.getValue()).iterator();
                while (it.hasNext()) {
                    entityKeys.add(it.next());
                }
            }
            result.getFuncParam().getRuntimeParam().put("userUnit", entityKeys);
        } else {
            result.getFuncParam().getRuntimeParam().put("userUnit", new String[0]);
        }
        Map<String, Object> sysParamMap = this.getSysParam(taskData.getKey());
        if (taskData.getFlowButtonConfig() != null) {
            sysParamMap.put("FLOW_BUTTON_CONFIG", taskData.getFlowButtonConfig());
        }
        if (((Boolean)sysParamMap.get("DATA_VERSION")).booleanValue()) {
            this.addDataSnapshotButtons(templateConfig);
        }
        result.getFuncParam().setSysParam(sysParamMap);
    }

    @Override
    public Map<String, Object> getSysParam(String taskKey) {
        String userId;
        List userIdByRole;
        HashSet users;
        String forceLock;
        HashMap<String, Object> sysParamMap = new HashMap<String, Object>();
        String dataPubObj = this.iTaskOptionController.getValue(taskKey, "DATA_PUBLISHING");
        sysParamMap.put("DATA_PUBLISHING", "1".equals(dataPubObj));
        String selectDataSumScope = this.iNvwaSystemOptionService.get("sum-upload-group", "SELECT_DATASUM_SCOPE");
        ArrayList<String> selectDataSum = new ArrayList<String>();
        if (selectDataSumScope.indexOf("0") > -1) {
            selectDataSum.add("0");
        }
        if (selectDataSumScope.indexOf("1") > -1) {
            selectDataSum.add("1");
        }
        sysParamMap.put("SELECT_DATASUM_SCOPE", selectDataSum);
        String sumDirectionScope = this.iNvwaSystemOptionService.get("sum-upload-group", "NODE_SUM_SUMDIRECTION_DEFAULT");
        String sumDirection = "0";
        if (sumDirectionScope.indexOf("0") > -1) {
            sumDirection = "0";
        }
        if (sumDirectionScope.indexOf("1") > -1) {
            sumDirection = "1";
        }
        if (sumDirectionScope.indexOf("2") > -1) {
            sumDirection = "2";
        }
        sysParamMap.put("NODE_SUM_SUMDIRECTION_DEFAULT", sumDirection);
        String selectDataSumDefalut = this.iNvwaSystemOptionService.get("sum-upload-group", "SELECT_DATASUM_DEFAULT");
        if ("1".equals(selectDataSumDefalut)) {
            sysParamMap.put("SELECT_DATASUM_DEFAULT", "all");
        } else {
            sysParamMap.put("SELECT_DATASUM_DEFAULT", "direct");
        }
        String formLockObj = this.iTaskOptionController.getValue(taskKey, "FORM_LOCK");
        if ("3".equals(formLockObj) || "2".equals(formLockObj) || "1".equals(formLockObj)) {
            sysParamMap.put("FORM_LOCK", true);
        } else {
            sysParamMap.put("FORM_LOCK", false);
        }
        ArrayList<FormulaAuditType> defaultType = new ArrayList<FormulaAuditType>();
        try {
            List auditTypes = this.auditTypeDefineService.queryAllAuditType();
            if (auditTypes == null || auditTypes.size() == 0) {
                throw new Exception();
            }
            for (AuditType auditType : auditTypes) {
                FormulaAuditType formulaAuditType = new FormulaAuditType();
                formulaAuditType.setKey(auditType.getCode());
                formulaAuditType.setIcon(auditType.getIcon());
                formulaAuditType.setTitle(auditType.getTitle());
                formulaAuditType.setGridColor(auditType.getGridColor());
                formulaAuditType.setBackGroundColor(auditType.getBackGroundColor());
                formulaAuditType.setFontColor(auditType.getFontColor());
                defaultType.add(formulaAuditType);
            }
        }
        catch (Exception e) {
            FormulaAuditType hintType = new FormulaAuditType();
            hintType.setKey(1);
            hintType.setIcon("#icon-_Txiaoxitishi");
            hintType.setTitle("\u63d0\u793a\u578b");
            defaultType.add(hintType);
            FormulaAuditType warningType = new FormulaAuditType();
            warningType.setKey(2);
            warningType.setIcon("#icon-_Tjinggaotishi");
            warningType.setTitle("\u8b66\u544a\u578b");
            defaultType.add(warningType);
            FormulaAuditType errorType = new FormulaAuditType();
            errorType.setKey(4);
            errorType.setIcon("#icon-_Tcuowutishi");
            errorType.setTitle("\u9519\u8bef\u578b");
            defaultType.add(errorType);
        }
        sysParamMap.put("FORMULAAUDITING", defaultType);
        String efdc = this.iNvwaSystemOptionService.get("fext-settings-group", "EFDC_GET_VALUE_MODIFY");
        OptionWrapper exit = this.iTaskOptionController.exit(taskKey, "EFDC_GET_VALUE_MODIFY_TASK");
        if (!exit.isExit()) {
            sysParamMap.put("EFDC_GET_VALUE_MODIFY", "1".equals(efdc));
        } else {
            sysParamMap.put("EFDC_GET_VALUE_MODIFY", "1".equals(exit.get()));
        }
        String checkResultShowFormula = this.iNvwaSystemOptionService.get("nr-audit-group", "CHECK_RESULT_SHOW_FORMULA");
        sysParamMap.put("CHECK_RESULT_SHOW_FORMULA", checkResultShowFormula);
        String dataAnnocationObj = this.iTaskOptionController.getValue(taskKey, "DATA_ANNOCATION");
        sysParamMap.put("DATA_ANNOCATION", "1".equals(dataAnnocationObj));
        String viewAllFormAnno = this.iTaskOptionController.getValue(taskKey, "VIEW_ALL_FORM_ANNO_OPTION");
        sysParamMap.put("VIEW_ALL_FORM_ANNO_OPTION", "1".equals(viewAllFormAnno));
        String taskDataSumUploaded = this.iTaskOptionController.getValue(taskKey, "SUMMARY_AFTER_UPLOAD");
        if (null == taskDataSumUploaded) {
            String sysDataSumUploaded = this.iNvwaSystemOptionService.get("sum-upload-group", "SUMMARY_AFTER_UPLOAD");
            if (null == sysDataSumUploaded) {
                sysParamMap.put("SUMMARY_AFTER_UPLOAD", "0");
            } else {
                sysParamMap.put("SUMMARY_AFTER_UPLOAD", sysDataSumUploaded);
            }
        } else {
            sysParamMap.put("SUMMARY_AFTER_UPLOAD", taskDataSumUploaded);
        }
        String dataentryStaObj = this.iTaskOptionController.getValue(taskKey, "DATAENTRY_STATUS");
        sysParamMap.put("DATAENTRY_STATUS", dataentryStaObj);
        String dataentryImportTypeObj = this.iTaskOptionController.getValue(taskKey, "IMPORT_FILE_TYPE");
        sysParamMap.put("IMPORT_FILE_TYPE", dataentryImportTypeObj);
        String dataentryExportTypeObj = this.iTaskOptionController.getValue(taskKey, "EXPORT_FILE_TYPE");
        sysParamMap.put("EXPORT_FILE_TYPE", dataentryExportTypeObj);
        String defaultDecimal = this.iTaskOptionController.getValue(taskKey, "DEFAULT_DECIMAL");
        sysParamMap.put("DEFAULT_DECIMAL", defaultDecimal);
        String fieldShowObj = this.iNvwaSystemOptionService.get("nr-data-entry-group", "JTABLE_FIELDINFO_SHOW");
        sysParamMap.put("JTABLE_FIELDINFO_SHOW", fieldShowObj);
        String checkedErrorTrack = this.iNvwaSystemOptionService.get("nr-data-entry-group", "CHECKED_ERROR_TRACK_JTABLE_SHOW");
        sysParamMap.put("CHECKED_ERROR_TRACK_JTABLE_SHOW", checkedErrorTrack);
        String enableAutoClacColor = this.iNvwaSystemOptionService.get("nr-data-entry-group", "JTABLE_ENABLE_CALC_CELL_COLOR");
        sysParamMap.put("JTABLE_ENABLE_CALC_CELL_COLOR", "1".equals(enableAutoClacColor));
        String autoClacColor = this.iNvwaSystemOptionService.get("nr-data-entry-group", "JTABLE_AUTOCALC_COLOR");
        sysParamMap.put("JTABLE_AUTOCALC_COLOR", autoClacColor);
        String enableEfdcClacColor = this.iNvwaSystemOptionService.get("nr-data-entry-group", "JTABLE_ENABLE_EFDC_CELL_COLOR");
        sysParamMap.put("JTABLE_ENABLE_EFDC_CELL_COLOR", "1".equals(enableEfdcClacColor));
        String efdcClacColor = this.iNvwaSystemOptionService.get("nr-data-entry-group", "JTABLE_EFDC_COLOR");
        sysParamMap.put("JTABLE_EFDC_COLOR", efdcClacColor);
        String periodDataShowInfo = this.iNvwaSystemOptionService.get("nr-data-entry-group", "SYS_TAG_PERIOD_DATA_SHOW_INFO");
        sysParamMap.put("SYS_TAG_PERIOD_DATA_SHOW_INFO", periodDataShowInfo);
        String containsUntVou = this.iNvwaSystemOptionService.get("fext-settings-group", "CONTAINS_ACCOUNTING_VOUCHERS");
        sysParamMap.put("CONTAINS_ACCOUNTING_VOUCHERS", containsUntVou);
        String defaultContainsUntVou = this.iNvwaSystemOptionService.get("fext-settings-group", "DEFAULT_CONTAINS_ACCOUNTING_VOUCHERS");
        sysParamMap.put("DEFAULT_CONTAINS_ACCOUNTING_VOUCHERS", defaultContainsUntVou);
        String jtableFieldInfo = this.iNvwaSystemOptionService.get("nr-data-entry-group", "JTABLE_FIELDINFO_SHOW_INFO");
        sysParamMap.put("JTABLE_FIELDINFO_SHOW_INFO", jtableFieldInfo);
        String showErrorMsg = this.iNvwaSystemOptionService.get("nr-audit-group", "SHOW_ERROR_MSG");
        sysParamMap.put("SHOW_ERROR_MSG", showErrorMsg);
        String charNumOfErrorMsg = this.iNvwaSystemOptionService.get("nr-audit-group", "CHAR_NUMBER_OF_ERROR_MSG");
        sysParamMap.put("CHAR_NUMBER_OF_ERROR_MSG", charNumOfErrorMsg);
        String maxNumOfErrorMsg = this.iNvwaSystemOptionService.get("nr-audit-group", "MAX_NUMBER_OF_ERROR_MSG");
        sysParamMap.put("MAX_NUMBER_OF_ERROR_MSG", maxNumOfErrorMsg);
        String errMsgContainChinese = this.iNvwaSystemOptionService.get("nr-audit-group", "ERROR_MSG_CONTAIN_CHINESE_CHAR");
        sysParamMap.put("ERROR_MSG_CONTAIN_CHINESE_CHAR", errMsgContainChinese);
        String dataMarginSumMessage = this.iTaskOptionController.getValue(taskKey, "DATA_MINUS_SUM");
        sysParamMap.put("MINUS_SUM", dataMarginSumMessage);
        String rowColumnNumShow = this.iTaskOptionController.getValue(taskKey, "ROW_COLUMN_NUM_SHOW");
        sysParamMap.put("ROW_COLUMN_NUM_SHOW", rowColumnNumShow);
        String gridSelectionMode = this.iTaskOptionController.getValue(taskKey, "GRID_SELECTION_MODE");
        sysParamMap.put("GRID_SELECTION_MODE", gridSelectionMode);
        String dataSnapshotObj = this.iTaskOptionController.getValue(taskKey, "DATA_VERSION");
        sysParamMap.put("DATA_VERSION", "1".equals(dataSnapshotObj));
        String dataSnapshotTotalResult = this.iTaskOptionController.getValue(taskKey, "DATASNAPSHOT_SHOWTOTAL");
        sysParamMap.put("DATASNAPSHOT_SHOWTOTAL", "1".equals(dataSnapshotTotalResult));
        String showReminder = this.iNvwaSystemOptionService.get("start-reminder", "START_REMINDER");
        sysParamMap.put("START_REMINDER", showReminder);
        String dataTimeCache = this.iNvwaSystemOptionService.get("nr-data-entry-group", "JTABLE_CACHE_DATA_TIME");
        sysParamMap.put("JTABLE_CACHE_DATA_TIME", "1".equals(dataTimeCache));
        String locateFirstCell = this.iNvwaSystemOptionService.get("nr-data-entry-group", "JTABLE_LOCATE_FIRST_CELL");
        sysParamMap.put("JTABLE_LOCATE_FIRST_CELL", "1".equals(locateFirstCell));
        String exportNullTable = this.iNvwaSystemOptionService.get("nr-data-entry-group", "DEFAULT_EXPORT_NULL_TABLE");
        sysParamMap.put("DEFAULT_EXPORT_NULL_TABLE", "1".equals(exportNullTable));
        String fileUploadWarn = this.iNvwaSystemOptionService.get("nr-data-entry-group", "FILE_UPLOAD_WARN");
        sysParamMap.put("FILE_UPLOAD_WARN", "1".equals(fileUploadWarn));
        List systemOptionItems = this.nvwaSystemOptionService.optionsByKey("nr-data-entry-export", true);
        sysParamMap.put("DATAENTRY_EXPORT", systemOptionItems);
        boolean secretLevel = this.secretLevelService.secretLevelEnable(taskKey);
        sysParamMap.put("SECRET_LEVEL", secretLevel);
        String modifySecretLevel = this.iTaskOptionController.getValue(taskKey, "SECRET_MODIFY");
        sysParamMap.put("SECRET_MODIFY", "1".equals(modifySecretLevel) && secretLevel);
        boolean openAnnotationType = this.annotationTypeService.isOpenAnnotationType();
        sysParamMap.put("OPEN_ANNOTATION_TYPE", openAnnotationType);
        boolean onlyLeafNode = this.annotationTypeService.onlyLeafNode();
        sysParamMap.put("ANNOTATION_ONLY_LEAF_NODE", onlyLeafNode);
        String enumAttributeShowWay = this.iTaskOptionController.getValue(taskKey, "ENUM_ATTRIBUTE_SHOW_WAY");
        sysParamMap.put("ENUM_ATTRIBUTE_SHOW_WAY", enumAttributeShowWay);
        String maxFindNum = this.iNvwaSystemOptionService.get("nr-data-entry-group", "MAX_FIND_NUM");
        sysParamMap.put("MAX_FIND_NUM", maxFindNum);
        String asyncSeconds = this.iNvwaSystemOptionService.get("nr-data-entry-group", "ASYNC_QUERY_SECONDS");
        sysParamMap.put("ASYNC_QUERY_SECONDS", asyncSeconds);
        String gridLineSpace = this.iNvwaSystemOptionService.get("other-group", "GRID_LINE_SPACE");
        sysParamMap.put("JTABLE_GRID_LINE_SPACE", gridLineSpace);
        String quickInputMode = this.iNvwaSystemOptionService.get("nr-data-entry-group", "QUICK_INPUT_MODE");
        sysParamMap.put("QUICK_INPUT_MODE", "1".equals(quickInputMode));
        String numberZeroShow = this.iTaskOptionController.getValue(taskKey, "NUMBER_ZERO_SHOW");
        sysParamMap.put("NUMBER_ZERO_SHOW", numberZeroShow);
        boolean openFileCategory = this.filePoolService.isOpenFileCategory();
        sysParamMap.put("OPEN_FILE_CATEGORY", openFileCategory);
        BigDecimal errorRange = new BigDecimal(0);
        String value = this.iTaskOptionController.getValue(taskKey, "NODE_CHECK_TOLERANCE");
        if (StringUtils.isNotEmpty((String)value)) {
            errorRange = new BigDecimal(value);
        }
        sysParamMap.put("NODE_CHECK_TOLERANCE", errorRange);
        String allowEditErrDesAfterUpload = this.iNvwaSystemOptionService.get("nr-audit-group", "ALLOW_EDIT_ERR_DES_AFTER_UPLOAD");
        sysParamMap.put("ALLOW_EDIT_ERR_DES_AFTER_UPLOAD", "1".equals(allowEditErrDesAfterUpload));
        String isPreviewOptio = this.iNvwaSystemOptionService.get("attachmentManagement", "IS_PREVIEW_OPTIONS");
        sysParamMap.put("IS_PREVIEW_OPTIONS", "1".equals(isPreviewOptio));
        boolean forceUnLockOption = false;
        SystemIdentityService systemIdentityService = (SystemIdentityService)SpringBeanUtils.getBean(SystemIdentityService.class);
        if (systemIdentityService.isAdmin()) {
            forceUnLockOption = true;
        }
        if (StringUtils.isNotEmpty((String)(forceLock = this.iTaskOptionController.getValue(taskKey, "FORCE_FORM_UNLOCK_ROLE"))) && (users = new HashSet(userIdByRole = this.roleService.getUserIdByRole(forceLock))).contains(userId = NpContextHolder.getContext().getUserId())) {
            forceUnLockOption = true;
        }
        sysParamMap.put("FORCE_LOCK", forceUnLockOption);
        String MEMORIZE_CHECKTYPE = this.iNvwaSystemOptionService.get("nr-data-entry-group", "MEMORIZE_CHECKTYPE");
        sysParamMap.put("MEMORIZE_CHECKTYPE", "1".equals(MEMORIZE_CHECKTYPE));
        return sysParamMap;
    }

    private void addDataSnapshotButtons(FuncExecuteConfig templateConfig) {
        List<TreeNodeItem> chooseButtons = templateConfig.getConfig().getToolbarViewConfig().getChooseButtons();
        TreeNodeItem dataSnapshot = new TreeNodeItem();
        dataSnapshot.setCode("dataSnapshot");
        dataSnapshot.setTitle("\u5feb\u7167\u7ba1\u7406");
        dataSnapshot.setIcon("#icon-_Wgongnengguanli");
        dataSnapshot.setActionType(ActionType.GROUP);
        dataSnapshot.setChildren(new ArrayList<TreeNodeItem>());
        chooseButtons.add(dataSnapshot);
        TreeNodeItem dataSnapshotCompare = new TreeNodeItem();
        dataSnapshotCompare.setCode("openDataSnapshotView");
        dataSnapshotCompare.setTitle("\u5feb\u7167\u67e5\u770b");
        dataSnapshotCompare.setActionType(ActionType.BUTTON);
        dataSnapshot.getChildren().add(dataSnapshotCompare);
        TreeNodeItem createDataSnapshot = new TreeNodeItem();
        createDataSnapshot.setCode("createDataSnapshot");
        createDataSnapshot.setTitle("\u751f\u6210\u5feb\u7167");
        createDataSnapshot.setActionType(ActionType.BUTTON);
        dataSnapshot.getChildren().add(createDataSnapshot);
        TreeNodeItem restoreDataSnapshot = new TreeNodeItem();
        restoreDataSnapshot.setCode("restoreDataSnapshot");
        restoreDataSnapshot.setTitle("\u5feb\u7167\u8fd8\u539f");
        restoreDataSnapshot.setActionType(ActionType.BUTTON);
        dataSnapshot.getChildren().add(restoreDataSnapshot);
        TreeNodeItem compareDataSnapshot = new TreeNodeItem();
        compareDataSnapshot.setCode("compareDataSnapshot");
        compareDataSnapshot.setTitle("\u5feb\u7167\u5bf9\u6bd4");
        compareDataSnapshot.setActionType(ActionType.BUTTON);
        dataSnapshot.getChildren().add(compareDataSnapshot);
        TreeNodeItem batchCompareDataSnapshot = new TreeNodeItem();
        batchCompareDataSnapshot.setCode("batchCompareDataSnapshot");
        batchCompareDataSnapshot.setTitle("\u6279\u91cf\u5feb\u7167\u5bf9\u6bd4");
        batchCompareDataSnapshot.setActionType(ActionType.BUTTON);
        dataSnapshot.getChildren().add(batchCompareDataSnapshot);
    }

    private void addDataVersionButtons(FuncExecuteConfig templateConfig) {
        List<TreeNodeItem> chooseButtons = templateConfig.getConfig().getToolbarViewConfig().getChooseButtons();
        TreeNodeItem dataVersion = new TreeNodeItem();
        dataVersion.setCode("dataVersion");
        dataVersion.setTitle("\u6570\u636e\u7248\u672c");
        dataVersion.setIcon("#icon-_Wgongnengguanli");
        dataVersion.setActionType(ActionType.GROUP);
        dataVersion.setChildren(new ArrayList<TreeNodeItem>());
        chooseButtons.add(dataVersion);
        TreeNodeItem dataVersionCompare = new TreeNodeItem();
        dataVersionCompare.setCode("dataVersionCompare");
        dataVersionCompare.setTitle("\u7248\u672c\u67e5\u770b\u6bd4\u8f83");
        dataVersionCompare.setActionType(ActionType.BUTTON);
        dataVersion.getChildren().add(dataVersionCompare);
        TreeNodeItem createDataVersion = new TreeNodeItem();
        createDataVersion.setCode("createDataVersion");
        createDataVersion.setTitle("\u751f\u6210\u6570\u636e\u7248\u672c");
        createDataVersion.setActionType(ActionType.BUTTON);
        dataVersion.getChildren().add(createDataVersion);
    }

    private FormSchemeResult getDataEntryFormScheme(String taskKey, String formSchemekey, FuncExecResult result, FuncExecuteConfig templateConfig) throws Exception {
        JtableContext jtableContext;
        EntityQueryByViewInfo entityQueryInfo;
        DimensionValue value;
        ArrayList schemeList = new ArrayList();
        FormSchemeDefine formScheme = null;
        TaskDefine taskDefine = null;
        if (taskKey != null) {
            try {
                this.runtimeView.initTask(taskKey);
                taskDefine = this.runtimeView.queryTaskDefine(taskKey);
                formScheme = this.runtimeView.getFormScheme(formSchemekey);
            }
            catch (Exception e2) {
                logger.error(e2.getMessage(), e2);
                throw e2;
            }
        }
        FormSchemeData formSchemeData = new FormSchemeData();
        formSchemeData.init(this.formulaRunTimeController, this.dataAccessProvider, formScheme, taskDefine);
        DWorkflowConfig workflowConfig = null;
        workflowConfig = this.workflowService.getWorkflowConfig(formSchemeData.getKey());
        formSchemeData.setWorkflowConfig(workflowConfig);
        try {
            AnalysisSchemeParamDefine queryAnalysisSchemeParamDefine = this.runtimeView.queryAnalysisSchemeParamDefine(formSchemeData.getKey());
            formSchemeData.setAnalysisParamDefine(queryAnalysisSchemeParamDefine);
        }
        catch (Exception e3) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e3.getMessage(), e3);
            throw e3;
        }
        FormSchemeResult schemeResult = new FormSchemeResult(formSchemeData);
        List<EntityViewData> entitys = formSchemeData.getEntitys();
        HashMap<String, DimensionValue> dimensions = new HashMap<String, DimensionValue>();
        for (EntityViewData entity : entitys) {
            if (this.periodEntityAdapter.isPeriodEntity(entity.getKey())) {
                value = new DimensionValue();
                value.setName(entity.getDimensionName());
                if (8 == formSchemeData.getPeriodType()) {
                    value.setType(8);
                    entityQueryInfo = new EntityQueryByViewInfo();
                    jtableContext = new JtableContext();
                    entityQueryInfo.setContext(jtableContext);
                    jtableContext.setFormSchemeKey(formSchemeData.getKey());
                    jtableContext.setTaskKey(taskKey);
                    entityQueryInfo.setEntityViewKey(entity.getKey());
                    EntityReturnInfo queryEntityData = this.jtableEntityService.queryCustomPeriodData(entityQueryInfo);
                    ArrayList<CustomPeriodData> customPeriods = new ArrayList<CustomPeriodData>();
                    String currValue = "";
                    for (com.jiuqi.nr.jtable.params.output.EntityData entityData : queryEntityData.getEntitys()) {
                        CustomPeriodData customPeriod = new CustomPeriodData();
                        customPeriod.setCode(entityData.getCode());
                        customPeriod.setTitle(entityData.getTitle());
                        customPeriods.add(customPeriod);
                        if (!StringUtils.isEmpty((String)currValue)) continue;
                        currValue = entityData.getCode();
                    }
                    schemeResult.setCustomPeriodDatas(customPeriods);
                    if (customPeriods.size() > 0) {
                        if (StringUtils.isNotEmpty((String)currValue)) {
                            value.setValue(currValue);
                        } else {
                            value.setValue(((CustomPeriodData)customPeriods.get(customPeriods.size() - 1)).getCode());
                        }
                        String period = templateConfig.getConfig().getDataEntryViewConfig().getPeriod();
                        if (StringUtils.isNotEmpty((String)period) && customPeriods.stream().filter(item -> item.getCode().equals(period)).count() > 0L) {
                            value.setValue(period);
                        }
                    } else {
                        value.setValue("1900N0001");
                    }
                } else {
                    value.setType(formSchemeData.getPeriodType());
                    PeriodWrapper currentPeriod = DataEntryUtil.getCurrPeriod(formSchemeData.getPeriodType(), formSchemeData.getPeriodOffset(), formSchemeData.getFromPeriod(), formSchemeData.getToPeriod());
                    String period = templateConfig.getConfig().getDataEntryViewConfig().getPeriod();
                    if (StringUtils.isNotEmpty((String)period)) {
                        try {
                            int periodOffset = Integer.parseInt(period);
                            currentPeriod.modifyPeriod(periodOffset);
                            value.setValue(currentPeriod.toString());
                            PeriodWrapper fromPeriod = new PeriodWrapper(formSchemeData.getFromPeriod());
                            PeriodWrapper toPeriod = new PeriodWrapper(formSchemeData.getToPeriod());
                            if (periodOffset > 0) {
                                if (currentPeriod.compareTo((Object)toPeriod) > 0) {
                                    value.setValue(formSchemeData.getToPeriod());
                                }
                            } else if (periodOffset < 0 && currentPeriod.compareTo((Object)fromPeriod) < 0) {
                                value.setValue(formSchemeData.getFromPeriod());
                            }
                        }
                        catch (Exception e4) {
                            value.setValue(period);
                        }
                    } else {
                        value.setValue(currentPeriod.toString());
                    }
                }
                dimensions.put(entity.getDimensionName(), value);
                continue;
            }
            if (!entity.isMasterEntity()) continue;
            value = new DimensionValue();
            value.setName(entity.getDimensionName());
            dimensions.put(entity.getDimensionName(), value);
            value.setValue("");
        }
        for (EntityViewData entity : entitys) {
            if (this.judgementPeriodView(entity.getKey()) || entity.isMasterEntity()) continue;
            value = new DimensionValue();
            value.setName(entity.getDimensionName());
            value.setValue("");
            dimensions.put(entity.getDimensionName(), value);
            entityQueryInfo = new EntityQueryByViewInfo();
            jtableContext = new JtableContext();
            jtableContext.setDimensionSet(dimensions);
            entityQueryInfo.setContext(jtableContext);
            jtableContext.setFormSchemeKey(formSchemeData.getKey());
            jtableContext.setTaskKey(taskKey);
            entityQueryInfo.setEntityViewKey(entity.getKey());
            if (result.getEntityDatas().containsKey(entity.getDimensionName())) continue;
            result.getEntityDatas().put(entity.getDimensionName(), this.jtableEntityService.queryDimEntityData(entityQueryInfo).getEntitys());
        }
        schemeResult.setDimensionSet(dimensions);
        ArrayList<FormulaVariable> formulaVariableList = new ArrayList<FormulaVariable>();
        List queryAllFormulaVariable = this.formulaRuntimeTimeController.queryAllFormulaVariable(formSchemeData.getKey());
        if (queryAllFormulaVariable.isEmpty()) {
            schemeResult.setFormulaVariables(formulaVariableList);
        } else {
            queryAllFormulaVariable.stream().filter(e -> e.getInitType() == 1).forEach(f -> formulaVariableList.add(new FormulaVariable().convertToFormulaVariable((FormulaVariDefine)f)));
            schemeResult.setFormulaVariables(formulaVariableList);
        }
        if (this.iFormSchemeService.enableAdjustPeriod(formSchemeData.getKey())) {
            value = new DimensionValue();
            value.setName("ADJUST");
            value.setValue("0");
            dimensions.put("ADJUST", value);
            schemeResult.setOpenAdJustPeriod(this.iFormSchemeService.enableAdjustPeriod(formSchemeData.getKey()));
        }
        schemeResult.setExistCurrencyAttributes(this.iFormSchemeService.existCurrencyAttributes(formSchemeData.getKey()));
        ArrayList<String> reportDimsList = new ArrayList<String>();
        reportDimsList.addAll(this.iFormSchemeService.getReportDimensionKey(formSchemeData.getKey()));
        schemeResult.setReportDimensionList(reportDimsList);
        return schemeResult;
    }

    @Override
    @Transactional(propagation=Propagation.SUPPORTS, readOnly=true)
    public FormsQueryResult getForms(FormsParam param) {
        List<FormGroupData> runtimeFormList = param.isFmdm() ? this.dataEntryParamService.getRuntimeFMDM(param.getContext()) : this.dataEntryParamService.getRuntimeFormList(param.getContext());
        FormsQueryResult result = new FormsQueryResult();
        FormTree formTree = this.getFormTree(runtimeFormList);
        result.setTree(formTree);
        return result;
    }

    @Override
    @Transactional(propagation=Propagation.SUPPORTS, readOnly=true)
    public FormsQueryResult getFormsWithoutAuth(FormsParam param) {
        ArrayList<FormGroupData> runtimeFormList = new ArrayList<FormGroupData>();
        List rootFormGroups = this.runtimeView.queryRootGroupsByFormScheme(param.getContext().getFormSchemeKey());
        if (rootFormGroups != null) {
            for (FormGroupDefine formGroupDefine : rootFormGroups) {
                FormGroupData formGroup = this.getFormGroupData(formGroupDefine);
                if (formGroup == null || formGroup.getGroups().isEmpty() && formGroup.getReports().isEmpty()) continue;
                runtimeFormList.add(formGroup);
            }
        }
        FormsQueryResult result = new FormsQueryResult();
        FormTree formTree = this.getFormTree(runtimeFormList);
        result.setTree(formTree);
        return result;
    }

    private FormGroupData getFormGroupData(FormGroupDefine formGroupDefine) {
        FormGroupData formGroup = new FormGroupData();
        formGroup.setKey(formGroupDefine.getKey());
        formGroup.setTitle(formGroupDefine.getTitle());
        formGroup.setCode(formGroupDefine.getCode());
        List formGroupDefines = this.runtimeView.getChildFormGroups(formGroupDefine.getKey());
        List<FormGroupData> groups = formGroup.getGroups();
        if (formGroupDefines != null) {
            for (FormGroupDefine childFormGroupDefine : formGroupDefines) {
                FormGroupData formGroupData = this.getFormGroupData(childFormGroupDefine);
                if (formGroupData == null || formGroupData.getGroups().isEmpty() && formGroupData.getReports().isEmpty()) continue;
                groups.add(formGroupData);
            }
        }
        List formDefines = null;
        try {
            formDefines = this.runtimeView.getAllFormsInGroup(formGroupDefine.getKey(), false);
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        if (formDefines != null) {
            for (FormDefine formDefine : formDefines) {
                FormData formInfo = new FormData();
                formInfo.init(formDefine);
                formGroup.addForm(formInfo);
            }
        }
        return formGroup;
    }

    @Override
    public boolean fmdmExist(String formSchemeKey) {
        List formDefines = this.runtimeView.queryAllFormDefinesByFormScheme(formSchemeKey);
        for (FormDefine formDefine : formDefines) {
            if (formDefine.getFormType() != FormType.FORM_TYPE_NEWFMDM) continue;
            return true;
        }
        return false;
    }

    @Override
    public List<DataEntityReturnInfo> queryEntityData(DataentryEntityQueryInfo dataentryEntityQueryInfo) {
        DataEntityContext dataEntityContext = new DataEntityContext();
        dataEntityContext.setFormKey(dataentryEntityQueryInfo.getJtableContext().getFormKey());
        dataEntityContext.setQueryDim(true);
        dataEntityContext.setFormSchemeKey(dataentryEntityQueryInfo.getJtableContext().getFormSchemeKey());
        dataEntityContext.setTaskKey(dataentryEntityQueryInfo.getJtableContext().getTaskKey());
        dataEntityContext.setSorted(true);
        EntityViewDefine entityViewDefine = this.iEntityViewRunTimeController.buildEntityView(dataentryEntityQueryInfo.getEntityViewKey());
        IDataEntity iDataEntity = this.dataEntityService.queryEntityWithDimVal(entityViewDefine, dataEntityContext, true, DimensionValueSetUtil.getDimensionValueSet((Map)dataentryEntityQueryInfo.getJtableContext().getDimensionSet()), null);
        return this.getEntityTree(iDataEntity.getAllRow());
    }

    private List<DataEntityReturnInfo> getEntityTree(IDataEntityRow iDataEntityRow) {
        ArrayList<DataEntityReturnInfo> dataEntityReturnInfoList = new ArrayList<DataEntityReturnInfo>();
        if (iDataEntityRow != null && iDataEntityRow.getRowList().size() > 0) {
            for (IEntityRow iEntityRow : iDataEntityRow.getRowList()) {
                if (!StringUtils.isEmpty((String)iEntityRow.getParentEntityKey())) continue;
                DataEntityReturnInfo dataEntityReturnInfo = new DataEntityReturnInfo(iEntityRow);
            }
        }
        return dataEntityReturnInfoList;
    }

    private boolean judgementPeriodView(String viewKey) {
        return this.periodEntityAdapter.isPeriodEntity(viewKey);
    }

    @Override
    @Transactional(propagation=Propagation.SUPPORTS, readOnly=true)
    public FormTree getFormTree(List<FormGroupData> runtimeFormList) {
        FormTreeItem rootItem = new FormTreeItem();
        rootItem.setCode("root");
        Tree tree = new Tree((Object)rootItem);
        this.addChildren((Tree<FormTreeItem>)tree, runtimeFormList);
        FormTree formTree = new FormTree();
        formTree.setTree((Tree<FormTreeItem>)tree);
        return formTree;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void exportAsync(ExportParam param, AsyncTaskMonitor monitor) {
        ExportData result;
        LogInfo logInfo;
        StringBuilder actionLogInfo;
        block105: {
            if (param.isExportAllForms()) {
                String schemeKey = param.getContext().getFormSchemeKey();
                JtableContext jtableContext = new JtableContext();
                jtableContext.setTaskKey(param.getContext().getTaskKey());
                jtableContext.setFormKey(param.getContext().getFormKey());
                jtableContext.setFormulaSchemeKey(param.getContext().getFormulaSchemeKey());
                jtableContext.setFormSchemeKey(schemeKey);
                jtableContext.setDimensionSet(param.getContext().getDimensionSet());
                jtableContext.setVariableMap(param.getContext().getVariableMap());
                List<FormGroupData> runtimeFormList = this.dataEntryParamService.getRuntimeFormList(jtableContext);
                StringBuilder formKeys = new StringBuilder();
                for (FormGroupData formGroupData : runtimeFormList) {
                    if (formGroupData.getReports().size() <= 0) continue;
                    for (FormData formData : formGroupData.getReports()) {
                        formKeys = formKeys.append(formData.getKey()).append(";");
                    }
                }
                String[] formKeyArray = formKeys.toString().split(";");
                param.getContext().setFormKey(formKeys.toString());
            }
            param.setExportPdfType(ExportPdfType.EXPORT_PDF);
            actionLogInfo = LoggerUtil.buildMessing(param.getContext(), null, "\u5bfc\u51fa\u6587\u4ef6", this.runtimeView, this.jtableParamService, this.periodEngineService, this.jtableEntityService);
            logInfo = new LogInfo();
            logInfo.setActionName("\u5bfc\u51fa\u6587\u4ef6");
            BatchReturnInfo batchReturnInfo = new BatchReturnInfo();
            String type = param.getType();
            IDataEntryExportService exportService = this.exportServiceMap.get(type);
            if (param.getType().equals("EXPORT_OFD")) {
                exportService = this.exportServiceMap.get("EXPORT_PDF");
            }
            result = null;
            BatchExportProgressMonitor batchExportProgressMonitor = new BatchExportProgressMonitor(monitor, 0.9f, 0.0);
            try {
                block104: {
                    if (param.getType().equals("EXPORT_TXT") || param.getType().equals("EXPORT_CSV")) {
                        JtableContext context = param.getContext();
                        DimensionValueSet dimensionSet = new DimensionValueSet();
                        Map dimSetMap = context.getDimensionSet();
                        if (null != dimSetMap) {
                            for (String key : dimSetMap.keySet()) {
                                dimensionSet.setValue(key, (Object)((DimensionValue)dimSetMap.get(key)).getValue());
                            }
                        }
                        TableContext tbContext = new TableContext(context.getTaskKey(), context.getFormSchemeKey(), context.getFormKey(), dimensionSet, OptTypes.FORM, ".txt");
                        tbContext.setSplit(param.getSplitMark().replace("\\t", "\t"));
                        String fileTypeTemp = "TXT";
                        String zipFile = null;
                        if (this.secretLevelService.secretLevelEnable(param.getContext().getTaskKey())) {
                            tbContext.setSecretLevelTitle(this.secretLevelService.getSecretLevel(param.getContext()).getSecretLevelItem().getTitle());
                        }
                        TextParams params = new TextParams();
                        params.setFormSchemeKey(context.getFormSchemeKey());
                        params.setMonitor((AsyncTaskMonitor)batchExportProgressMonitor);
                        params.setDataMasking(true);
                        ArrayList<String> formKeys = new ArrayList<String>();
                        String formKey = context.getFormKey();
                        if (formKey != null && !formKey.equals("") && formKey.contains(";")) {
                            String[] split;
                            for (String string : split = formKey.split(";")) {
                                formKeys.add(string);
                            }
                        } else if (formKey != null && !formKey.equals("")) {
                            formKeys.add(formKey);
                        }
                        params.setFormKeys(formKeys);
                        DimensionCollection buildDimensionCollection = com.jiuqi.nr.data.access.util.DimensionValueSetUtil.buildDimensionCollection((Map)context.getDimensionSet(), (String)context.getFormSchemeKey());
                        params.setDimensionSet(buildDimensionCollection);
                        params.setSecretLevelTitle(tbContext.getSecretLevelTitle());
                        params.setSecretLevelTitleHigher(tbContext.getSecretLevelTitleHigher());
                        String downloadTextData = null;
                        String fileName = "";
                        if (param.getType().equals("EXPORT_CSV")) {
                            fileTypeTemp = "CSV";
                            fileName = "\u5bfc\u51faCSV.zip";
                            params.setTextType(TextType.TEXTTYPE_CSV);
                            downloadTextData = this.expTextService.downloadTextData(params);
                        } else {
                            fileName = "\u5bfc\u51faTXT.zip";
                            params.setTextType(TextType.TEXTTYPE_TXT);
                            params.setSplit(param.getSplitMark().replace("\\t", "\t"));
                            downloadTextData = this.expTextService.downloadTextData(params);
                        }
                        if (downloadTextData != null) {
                            File directory = new File(downloadTextData);
                            File zipDir = new File(directory.getAbsolutePath() + "_1");
                            if (!zipDir.exists()) {
                                zipDir.mkdir();
                            }
                            zipFile = directory.getAbsolutePath() + "_1/" + fileName;
                            try (FileOutputStream fos = new FileOutputStream(new File(zipFile));
                                 BufferedOutputStream bos = new BufferedOutputStream(fos);){
                                ZipOutputStream zos = new ZipOutputStream(bos);
                                FuncExecuteServiceImpl.addFilesToZip(zos, directory, null);
                                zos.close();
                            }
                            catch (IOException e) {
                                logger.error(e.getMessage());
                            }
                            finally {
                                try {
                                    FileUtils.deleteDirectory(directory);
                                }
                                catch (IOException e) {
                                    logger.error(e.getMessage());
                                }
                            }
                        }
                        PathUtils.validatePathManipulation(zipFile);
                        File file = new File(FilenameUtils.normalize(zipFile));
                        result = new ExportData("\u5bfc\u51fa" + fileTypeTemp, this.getBytesFromFile(file));
                        if (zipFile != null) {
                            FileUtil.deleteFiles((String)zipFile.replace("ExportTxtDatas.zip", ""));
                        }
                    } else {
                        result = exportService.export(param, batchExportProgressMonitor);
                    }
                    if (result != null && result.isEmpty()) {
                        if (param.isExportEmptyTable() && param.isExportZero() || param.isExportEmptyTable() && !param.isExportZero()) {
                            monitor.finished("\u6570\u636e\u5bfc\u51fa\u5b8c\u6210", (Object)"EXPORT_NO_ELIGIBLE_DATA");
                        } else {
                            monitor.finished("\u6570\u636e\u5bfc\u51fa\u5b8c\u6210", (Object)"EXPORT_NO_DATA");
                        }
                        return;
                    }
                    ObjectInfo objectInfo = null;
                    String suffix = ".xlsx";
                    if (param.getType().equals("EXPORT_JIO")) {
                        suffix = ".jio";
                    } else if (param.getType().equals("EXPORT_PDF")) {
                        suffix = ".pdf";
                    } else if (param.getType().equals("EXPORT_TXT")) {
                        suffix = ".zip";
                    } else if (param.getType().equals("EXPORT_CSV")) {
                        suffix = ".zip";
                    } else if (param.getType().equals("EXPORT_OFD")) {
                        suffix = ".ofd";
                    }
                    if (param.getType().equals("EXPORT_EXCEL") && param.isExportETFile()) {
                        suffix = ".et";
                    }
                    if (StringUtils.isNotEmpty((String)result.getFileLocation())) {
                        File removeFile = new File(result.getFileLocation());
                        try (FileInputStream uploadInputStream = new FileInputStream(result.getFileLocation());){
                            objectInfo = this.fileUploadOssService.uploadFileStreamToTemp(result.getFileName() + suffix, (InputStream)uploadInputStream);
                            this.cacheObjectResourceRemote.create((Object)(param.getSyncTaskID() + "_fileInfoKey"), (Object)objectInfo.getKey());
                            this.cacheObjectResourceRemote.create((Object)(param.getSyncTaskID() + "_areaInfo"), (Object)"NR_TEMP");
                            this.cacheObjectResourceRemote.create((Object)(param.getSyncTaskID() + "_fileSize"), (Object)removeFile.length());
                            break block104;
                        }
                        catch (Exception e) {
                            throw e;
                        }
                        finally {
                            FileUtils.deleteDirectory(removeFile.getParentFile());
                        }
                    }
                    ByteArrayInputStream uploadInputStream = new ByteArrayInputStream(result.getData());
                    Object object = null;
                    try {
                        objectInfo = this.fileUploadOssService.uploadFileStreamToTemp(result.getFileName() + suffix, (InputStream)uploadInputStream);
                        this.cacheObjectResourceRemote.create((Object)(param.getSyncTaskID() + "_fileInfoKey"), (Object)objectInfo.getKey());
                        this.cacheObjectResourceRemote.create((Object)(param.getSyncTaskID() + "_areaInfo"), (Object)"NR_TEMP");
                        this.cacheObjectResourceRemote.create((Object)(param.getSyncTaskID() + "_fileSize"), (Object)result.getData().length);
                    }
                    catch (Throwable throwable) {
                        object = throwable;
                        throw throwable;
                    }
                    finally {
                        if (uploadInputStream != null) {
                            if (object != null) {
                                try {
                                    ((InputStream)uploadInputStream).close();
                                }
                                catch (Throwable throwable) {
                                    ((Throwable)object).addSuppressed(throwable);
                                }
                            } else {
                                ((InputStream)uploadInputStream).close();
                            }
                        }
                    }
                }
                if (null != monitor) {
                    String objectToJson = JsonUtil.objectToJson((Object)batchReturnInfo);
                    monitor.finish("\u6570\u636e\u5bfc\u51fa\u5b8c\u6210\u3002", (Object)objectToJson);
                }
            }
            catch (Exception e) {
                logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                if (null == monitor) break block105;
                batchReturnInfo.setStatus(2);
                String objectToJson = JsonUtil.objectToJson((Object)batchReturnInfo);
                monitor.error("\u5f02\u5e38:" + objectToJson, (Throwable)e);
            }
        }
        if (result != null && result.getData() != null) {
            logInfo.setLogInfo(actionLogInfo.toString() + "\u6587\u4ef6\u5185\u5bb9\u5927\u5c0f\u4e3a\uff1a" + result.getData().length + "byte");
        } else {
            logInfo.setLogInfo(actionLogInfo.toString() + "\u6587\u4ef6\u5185\u5bb9\u5927\u5c0f\u4e3a\uff1a0byte");
        }
        LogHelper.info((String)"\u6570\u636e\u5f55\u5165", (String)logInfo.getActionName(), (String)logInfo.getLogInfo());
    }

    private Tree<FormTreeItem> addChildren(Tree<FormTreeItem> node, List<FormGroupData> formGroupList) {
        for (FormGroupData formGroup : formGroupList) {
            if (formGroup.getReports().size() <= 0 && formGroup.getGroups().size() <= 0) continue;
            FormTreeItem groupItem = new FormTreeItem();
            groupItem.setKey(formGroup.getKey());
            groupItem.setCode(formGroup.getCode());
            groupItem.setTitle(formGroup.getTitle());
            groupItem.setType("group");
            Tree child = node.addChild((Object)groupItem);
            for (FormData form : formGroup.getReports()) {
                FormTreeItem reportItem = new FormTreeItem();
                reportItem.setKey(form.getKey());
                reportItem.setCode(form.getCode());
                reportItem.setTitle(form.getTitle());
                reportItem.setSerialNumber(form.getSerialNumber());
                reportItem.setFormType(form.getFormType());
                reportItem.setType("form");
                ArrayList<MeasureViewData> measures = new ArrayList<MeasureViewData>();
                List measuresLists = form.getMeasures();
                if (null != measuresLists && !measuresLists.isEmpty()) {
                    for (com.jiuqi.nr.jtable.params.base.MeasureViewData data : measuresLists) {
                        MeasureViewData measure = new MeasureViewData();
                        measure.setKey(data.getKey());
                        measure.setTitle(data.getTitle());
                        measures.add(measure);
                    }
                }
                reportItem.setMeasures(measures);
                Map measureValues = form.getMeasureValues();
                reportItem.setMeasureValues(null == measureValues ? new HashMap() : measureValues);
                reportItem.setDataSum(form.isDataSum());
                reportItem.setGroupKey(formGroup.getKey());
                reportItem.setAnalysisForm(form.isAnalysisForm());
                reportItem.setNeedReload(form.isNeedReload());
                reportItem.setAnalysisReportKey(form.getAnalysisReportKey());
                child.addChild((Object)reportItem);
            }
            if (formGroup.getGroups().size() <= 0) continue;
            this.addChildren((Tree<FormTreeItem>)child, formGroup.getGroups());
        }
        return node;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public ExportData export(ExportParam param) {
        ExportData result;
        LogInfo logInfo;
        StringBuilder actionLogInfo;
        block57: {
            actionLogInfo = LoggerUtil.buildMessing(param.getContext(), null, "\u5bfc\u51fa\u6587\u4ef6", this.runtimeView, this.jtableParamService, this.periodEngineService, this.jtableEntityService);
            logInfo = new LogInfo();
            logInfo.setActionName("\u5bfc\u51fa\u6587\u4ef6");
            BatchReturnInfo batchReturnInfo = new BatchReturnInfo();
            String type = param.getType();
            IDataEntryExportService exportService = this.exportServiceMap.get(type);
            if (param.getType().equals("EXPORT_OFD")) {
                exportService = this.exportServiceMap.get("EXPORT_PDF");
            }
            result = null;
            SimpleAsyncProgressMonitor monitor = null;
            try {
                if (null != param.getSyncTaskID()) {
                    monitor = new SimpleAsyncProgressMonitor(param.getSyncTaskID().toString(), this.cacheObjectResourceRemote);
                    monitor.progressAndMessage(0.01, "\u5f00\u59cb");
                }
                if (param.getType().equals("EXPORT_TXT") || param.getType().equals("EXPORT_CSV")) {
                    JtableContext context = param.getContext();
                    DimensionValueSet dimensionSet = new DimensionValueSet();
                    Map dimSetMap = context.getDimensionSet();
                    if (null != dimSetMap) {
                        for (String key : dimSetMap.keySet()) {
                            dimensionSet.setValue(key, (Object)((DimensionValue)dimSetMap.get(key)).getValue());
                        }
                    }
                    TableContext tbContext = new TableContext(context.getTaskKey(), context.getFormSchemeKey(), context.getFormKey(), dimensionSet, OptTypes.FORM, ".txt");
                    tbContext.setSplit(param.getSplitMark().replace("\\t", "\t"));
                    String fileTypeTemp = "TXT";
                    String zipFile = null;
                    if (this.secretLevelService.secretLevelEnable(param.getContext().getTaskKey())) {
                        tbContext.setSecretLevelTitle(this.secretLevelService.getSecretLevel(param.getContext()).getSecretLevelItem().getTitle());
                    }
                    TextParams params = new TextParams();
                    params.setFormSchemeKey(context.getFormSchemeKey());
                    params.setMonitor((AsyncTaskMonitor)monitor);
                    ArrayList<String> formKeys = new ArrayList<String>();
                    String formKey = context.getFormKey();
                    if (formKey != null && !formKey.equals("") && formKey.contains(";")) {
                        String[] split;
                        for (String string : split = formKey.split(";")) {
                            formKeys.add(string);
                        }
                    } else if (formKey != null && !formKey.equals("")) {
                        formKeys.add(formKey);
                    }
                    params.setFormKeys(formKeys);
                    DimensionCollection buildDimensionCollection = com.jiuqi.nr.data.access.util.DimensionValueSetUtil.buildDimensionCollection((Map)context.getDimensionSet(), (String)context.getFormSchemeKey());
                    params.setDimensionSet(buildDimensionCollection);
                    params.setSecretLevelTitle(tbContext.getSecretLevelTitle());
                    params.setSecretLevelTitleHigher(tbContext.getSecretLevelTitleHigher());
                    String downloadTextData = null;
                    String fileName = "";
                    if (param.getType().equals("EXPORT_CSV")) {
                        fileTypeTemp = "CSV";
                        fileName = "\u5bfc\u51faCSV.zip";
                        params.setTextType(TextType.TEXTTYPE_CSV);
                        downloadTextData = this.expTextService.downloadTextData(params);
                    } else {
                        fileName = "\u5bfc\u51faTXT.zip";
                        params.setTextType(TextType.TEXTTYPE_TXT);
                        params.setSplit(param.getSplitMark().replace("\\t", "\t"));
                        downloadTextData = this.expTextService.downloadTextData(params);
                    }
                    if (downloadTextData != null) {
                        File directory = new File(downloadTextData);
                        File zipDir = new File(directory.getAbsolutePath() + "_1");
                        if (!zipDir.exists()) {
                            zipDir.mkdir();
                        }
                        zipFile = directory.getAbsolutePath() + "_1/" + fileName;
                        try (FileOutputStream fos = new FileOutputStream(new File(zipFile));
                             BufferedOutputStream bos = new BufferedOutputStream(fos);){
                            ZipOutputStream zos = new ZipOutputStream(bos);
                            FuncExecuteServiceImpl.addFilesToZip(zos, directory, null);
                            zos.close();
                        }
                        catch (IOException e) {
                            logger.error(e.getMessage());
                        }
                        finally {
                            try {
                                FileUtils.deleteDirectory(directory);
                            }
                            catch (IOException e) {
                                logger.error(e.getMessage());
                            }
                        }
                    }
                    PathUtils.validatePathManipulation(zipFile);
                    File file = new File(FilenameUtils.normalize(zipFile));
                    result = new ExportData("\u5bfc\u51fa" + fileTypeTemp, this.getBytesFromFile(file));
                    if (zipFile != null) {
                        FileUtil.deleteFiles((String)zipFile.replace("ExportTxtDatas.zip", ""));
                    }
                } else {
                    result = exportService.export(param, monitor);
                }
                if (null != monitor) {
                    String objectToJson = JsonUtil.objectToJson((Object)batchReturnInfo);
                    monitor.finish("\u6570\u636e\u5bfc\u51fa\u5b8c\u6210\u3002", objectToJson);
                }
            }
            catch (Exception e) {
                logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                if (null == monitor) break block57;
                batchReturnInfo.setStatus(2);
                String objectToJson = JsonUtil.objectToJson((Object)batchReturnInfo);
                monitor.error("\u5f02\u5e38:" + objectToJson, e);
            }
        }
        if (result.getData() != null) {
            logInfo.setLogInfo(actionLogInfo.toString() + "\u6587\u4ef6\u5185\u5bb9\u5927\u5c0f\u4e3a\uff1a" + result.getData().length + "byte");
        } else {
            logInfo.setLogInfo(actionLogInfo.toString() + "\u6587\u4ef6\u5185\u5bb9\u5927\u5c0f\u4e3a\uff1a0byte");
        }
        LogHelper.info((String)"\u6570\u636e\u5f55\u5165", (String)logInfo.getActionName(), (String)logInfo.getLogInfo());
        return result;
    }

    private static void addFilesToZip(ZipOutputStream zos, File file, String parentFolderName) throws IOException {
        for (File f : file.listFiles()) {
            if (f.isDirectory()) {
                FuncExecuteServiceImpl.addFilesToZip(zos, f, parentFolderName == null ? f.getName() : parentFolderName + "/" + f.getName());
                continue;
            }
            byte[] buffer = new byte[1024];
            try (FileInputStream fis = new FileInputStream(f);
                 BufferedInputStream bis = new BufferedInputStream(fis);){
                int bytesRead;
                zos.putNextEntry(new ZipEntry((parentFolderName != null ? parentFolderName + "/" : "") + f.getName()));
                while ((bytesRead = bis.read(buffer)) > 0) {
                    zos.write(buffer, 0, bytesRead);
                }
            }
        }
    }

    private byte[] getBytesFromFile(File file) throws IOException {
        int offset;
        long length = file.length();
        if (length > Integer.MAX_VALUE) {
            throw new IOException("\u6587\u4ef6\u8fc7\u5927\uff0c\u4e0d\u80fd\u4f20\u8f93");
        }
        byte[] bytes = null;
        int numRead = 0;
        try (FileInputStream in = new FileInputStream(file);){
            bytes = new byte[(int)length];
            for (offset = 0; offset < bytes.length && (numRead = ((InputStream)in).read(bytes, offset, bytes.length - offset)) >= 0; offset += numRead) {
            }
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        if (offset < bytes.length) {
            throw new IOException("\u4e0d\u80fd\u8f6c\u6362\uff0c");
        }
        return bytes;
    }

    @Override
    public ExportData checkResultExport(BatchCheckExportInfo batchCheckExportInfo) {
        String exportType = batchCheckExportInfo.getExportType();
        SXSSFWorkbook workbook = null;
        List<Integer> allTypes = batchCheckExportInfo.getExportCheckTypes() != null ? batchCheckExportInfo.getExportCheckTypes() : new ArrayList<Integer>(Arrays.asList(1, 2, 4));
        if ("checkResult".equals(exportType)) {
            JtableContext jtableContext = this.transformationFormCheckInfo(batchCheckExportInfo);
            FormulaCheckReturnInfo formulaCheckReturnInfo = this.jtableResourceService.checkForm(jtableContext);
            List<FormulaCheckResultInfo> resultInfos = this.filterCheckResult(batchCheckExportInfo, formulaCheckReturnInfo.getResults());
            workbook = this.createWorkBookByFormulaCheckReturnInfo(resultInfos, null, null, formulaCheckReturnInfo.getDimensionList(), jtableContext);
        } else if ("checkAllResult".equals(exportType)) {
            AllCheckInfo allCheckInfo = new AllCheckInfo();
            allCheckInfo.setAsyncTaskKey(batchCheckExportInfo.getAsyncTaskKey());
            allCheckInfo.setContext(batchCheckExportInfo.getContext());
            allCheckInfo.setFormulas(batchCheckExportInfo.getFormulas());
            allCheckInfo.setFormulaSchemeKeys(batchCheckExportInfo.getFormulaSchemeKeys());
            allCheckInfo.setCheckTypes(allTypes);
            allCheckInfo.setChooseTypes(allTypes);
            List<Integer> uploadCheckTypes = batchCheckExportInfo.getUploadCheckTypes();
            if (!uploadCheckTypes.isEmpty()) {
                allCheckInfo.setUploadCheckTypes(uploadCheckTypes);
                allCheckInfo.setCheckDesNull(batchCheckExportInfo.isCheckDesNull());
            }
            allCheckInfo.setSearchByFormula(batchCheckExportInfo.isSearchByFormula());
            FormulaCheckReturnInfo formulaCheckReturnInfo = this.allCheckService.allCheckResult(allCheckInfo);
            workbook = this.createWorkBookByFormulaCheckReturnInfo(formulaCheckReturnInfo.getResults(), null, null, formulaCheckReturnInfo.getDimensionList(), batchCheckExportInfo.getContext());
        } else {
            Boolean allDW = false;
            EntityViewData dwEntity = this.jtableParamService.getDwEntity(batchCheckExportInfo.getContext().getFormSchemeKey());
            DimensionValue dimensionValue = (DimensionValue)batchCheckExportInfo.getContext().getDimensionSet().get(dwEntity.getDimensionName());
            if (dimensionValue.getValue().equals("") || !batchCheckExportInfo.isFilterExportResult()) {
                allDW = true;
            }
            BatchCheckInfo batchCheckInfo = this.transformationBatchCheckInfo(batchCheckExportInfo);
            batchCheckInfo.setCheckTypes(allTypes);
            batchCheckInfo.setChooseTypes(allTypes);
            FormulaCheckReturnInfo formulaCheckReturnInfo = this.batchCheckResultService.batchCheckResult(batchCheckInfo);
            workbook = this.createWorkBookByFormulaCheckReturnInfo(formulaCheckReturnInfo.getResults(), batchCheckInfo, allDW, formulaCheckReturnInfo.getDimensionList(), batchCheckExportInfo.getContext());
        }
        if (null != workbook) {
            ByteArrayOutputStream os = new ByteArrayOutputStream(0xA00000);
            try {
                workbook.write(os);
            }
            catch (IOException e) {
                logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            }
            byte[] byteArray = os.toByteArray();
            String CheckResult2 = this.i18nHelper.getMessage("Check-Result").equals("") ? "\u5ba1\u6838\u7ed3\u679c" : this.i18nHelper.getMessage("Check-Result");
            ExportData formulaExportData = new ExportData(CheckResult2 + ".xlsx", byteArray);
            return formulaExportData;
        }
        return null;
    }

    private List<FormulaCheckResultInfo> filterCheckResult(BatchCheckExportInfo batchCheckExportInfo, List<FormulaCheckResultInfo> source) {
        ArrayList<FormulaCheckResultInfo> target = new ArrayList<FormulaCheckResultInfo>();
        Integer checkDesNull = 0;
        List<Integer> filterCheckTypes = batchCheckExportInfo.getExportCheckTypes() != null ? batchCheckExportInfo.getExportCheckTypes() : new ArrayList<Integer>(Arrays.asList(1, 2, 4));
        List<Integer> uploadCheckTypes = batchCheckExportInfo.getUploadCheckTypes();
        if (!uploadCheckTypes.isEmpty()) {
            checkDesNull = batchCheckExportInfo.isCheckDesNull() ? 2 : 1;
        }
        for (FormulaCheckResultInfo formulaCheckResultInfo : source) {
            if (!filterCheckTypes.contains(formulaCheckResultInfo.getFormula().getChecktype())) continue;
            if (checkDesNull == 2 && StringUtils.isEmpty((String)formulaCheckResultInfo.getDescriptionInfo().getDescription())) {
                target.add(formulaCheckResultInfo);
            }
            if (checkDesNull == 1 && StringUtils.isNotEmpty((String)formulaCheckResultInfo.getDescriptionInfo().getDescription())) {
                target.add(formulaCheckResultInfo);
            }
            if (checkDesNull != 0) continue;
            target.add(formulaCheckResultInfo);
        }
        return target;
    }

    private String[] createTitle(JtableContext context) {
        ArrayList<String> titleList = new ArrayList<String>();
        String SerialNumber = this.i18nHelper.getMessage("SerialNumber").equals("") ? "\u5e8f\u53f7" : this.i18nHelper.getMessage("SerialNumber");
        String ReviewType = this.i18nHelper.getMessage("Review-Type").equals("") ? "\u5ba1\u6838\u7c7b\u578b" : this.i18nHelper.getMessage("Review-Type");
        String UnitCode = this.i18nHelper.getMessage("Unit-Code").equals("") ? "\u5355\u4f4d\u4ee3\u7801" : this.i18nHelper.getMessage("Unit-Code");
        String UnitTitle = this.i18nHelper.getMessage("Unit-Title").equals("") ? "\u5355\u4f4d\u540d\u79f0" : this.i18nHelper.getMessage("Unit-Title");
        String ReportName = this.i18nHelper.getMessage("ReportName").equals("") ? "\u6240\u5728\u62a5\u8868\u540d\u79f0" : this.i18nHelper.getMessage("ReportName");
        String ReportCode = this.i18nHelper.getMessage("ReportCode").equals("") ? "\u6240\u5728\u62a5\u8868\u6807\u8bc6" : this.i18nHelper.getMessage("ReportCode");
        String FormulaNumber = this.i18nHelper.getMessage("Formula-Number").equals("") ? "\u516c\u5f0f\u7f16\u53f7" : this.i18nHelper.getMessage("Formula-Number");
        String FormulaDescription = this.i18nHelper.getMessage("Formula-Description").equals("") ? "\u516c\u5f0f\u8bf4\u660e" : this.i18nHelper.getMessage("Formula-Description");
        String Formula2 = this.i18nHelper.getMessage("Formula").equals("") ? "\u516c\u5f0f" : this.i18nHelper.getMessage("Formula");
        String Difference = this.i18nHelper.getMessage("Difference").equals("") ? "\u5dee\u989d" : this.i18nHelper.getMessage("Difference");
        String ErrorData = this.i18nHelper.getMessage("ErrorData").equals("") ? "\u9519\u8bef\u6570\u636e" : this.i18nHelper.getMessage("ErrorData");
        String ErrorDescription = this.i18nHelper.getMessage("ErrorDescription").equals("") ? "\u5ba1\u6838\u9519\u8bef\u8bf4\u660e" : this.i18nHelper.getMessage("ErrorDescription");
        List<DimensionObj> dimensionObjList = this.checkReviewTransformUtil.queryDimObj(context);
        titleList.add(SerialNumber);
        titleList.add(ReviewType);
        titleList.add(UnitCode);
        titleList.add(UnitTitle);
        for (DimensionObj dimensionObj : dimensionObjList) {
            titleList.add(dimensionObj.getDimensionTitle());
        }
        titleList.add(ReportName);
        titleList.add(ReportCode);
        if ("1".equals(this.iNvwaSystemOptionService.get("nr-audit-group", "EXPORT_ROW_DIM"))) {
            String RowDim = this.i18nHelper.getMessage("RowDim").equals("") ? "\u6d6e\u52a8\u884c\u7ef4\u5ea6" : this.i18nHelper.getMessage("RowDim");
            titleList.add(RowDim);
        }
        titleList.add(FormulaNumber);
        titleList.add(FormulaDescription);
        titleList.add(Formula2);
        titleList.add(Difference);
        titleList.add(ErrorData);
        titleList.add(ErrorDescription);
        return titleList.toArray(new String[titleList.size()]);
    }

    private String toValue(String value) {
        if (value == null) {
            return " ";
        }
        return value;
    }

    /*
     * WARNING - void declaration
     */
    private SXSSFWorkbook createWorkBookByFormulaCheckReturnInfo(List<FormulaCheckResultInfo> formulaCheckResultInfoLists, BatchCheckInfo batchCheckInfo, Boolean allDW, List<Map<String, DimensionValue>> dimensionList, JtableContext jtableContext) {
        String[] createTitle = null;
        if (this.iCustomCheckResultExcelColumnService != null) {
            createTitle = this.iCustomCheckResultExcelColumnService.getICustomCreateTitle(this.createTitle(jtableContext));
        } else {
            createTitle = this.createTitle(jtableContext);
            logger.info("iCustomCreateTitleService\u65b9\u6cd5\u672a\u5b9e\u73b0,\u65e0\u6cd5\u83b7\u53d6\u7ec4\u4ef6");
        }
        if (null != formulaCheckResultInfoLists && formulaCheckResultInfoLists.size() > 0) {
            BatchCheckStatistics batchCheckStatistics = null;
            if (batchCheckInfo != null) {
                batchCheckStatistics = new BatchCheckStatistics();
                FormSchemeDefine formScheme = this.iRunTimeViewController.getFormScheme(batchCheckInfo.getContext().getFormSchemeKey());
                if (formScheme != null) {
                    TaskDefine taskDefine = this.iRunTimeViewController.queryTaskDefine(formScheme.getTaskKey());
                    batchCheckStatistics.setTaskTitle(taskDefine.getTitle());
                }
                String periodName = this.jtableParamService.getDataTimeEntity(batchCheckInfo.getContext().getFormSchemeKey()).getDimensionName();
                String value = ((DimensionValue)batchCheckInfo.getContext().getDimensionSet().get(periodName)).getValue();
                batchCheckStatistics.setDateTitle(this.exportExcelNameService.getPeriodTitle(batchCheckInfo.getContext().getFormSchemeKey(), value));
                batchCheckStatistics.setDimList(this.checkReviewTransformUtil.queryOtherDims(batchCheckInfo.getContext()));
                StringBuffer sb = new StringBuffer();
                for (String formulaSchemeKey : batchCheckInfo.getFormulaSchemeKeys().split(";")) {
                    if (!StringUtils.isNotEmpty((String)formulaSchemeKey)) continue;
                    FormulaSchemeDefine formulaSchemeDefine = this.runtimeFormulaSchemeService.queryFormulaScheme(formulaSchemeKey);
                    sb.append(formulaSchemeDefine.getTitle()).append("\u3001");
                }
                if (sb.length() > 0) {
                    sb.deleteCharAt(sb.length() - 1);
                }
                batchCheckStatistics.setFormulaSchemeTitle(sb.toString());
                List<Integer> checkTypes = batchCheckInfo.getCheckTypes();
                ArrayList<Integer> sortedCheckTypes = new ArrayList<Integer>();
                List auditTypeList = new ArrayList();
                try {
                    auditTypeList = this.auditTypeDefineService.queryAllAuditType();
                }
                catch (Exception e) {
                    throw new RuntimeException(e);
                }
                HashMap<Integer, String> checkTypeTitle = new HashMap<Integer, String>();
                HashMap<Integer, Integer> checkTypeItemNums = new HashMap<Integer, Integer>();
                HashMap<Integer, Set<String>> checkTypeUnitNums = new HashMap<Integer, Set<String>>();
                for (AuditType auditType : auditTypeList) {
                    if (!checkTypes.contains(auditType.getCode())) continue;
                    sortedCheckTypes.add(auditType.getCode());
                    checkTypeTitle.put(auditType.getCode(), auditType.getTitle());
                    checkTypeItemNums.put(auditType.getCode(), 0);
                    checkTypeUnitNums.put(auditType.getCode(), new HashSet());
                }
                for (FormulaCheckResultInfo formulaCheckResultInfo : formulaCheckResultInfoLists) {
                    FormulaData formulaData = formulaCheckResultInfo.getFormula();
                    int checkType = formulaData.getChecktype();
                    checkTypeItemNums.put(checkType, checkTypeItemNums.get(checkType) + 1);
                    checkTypeUnitNums.get(checkType).add(formulaCheckResultInfo.getUnitCode());
                }
                batchCheckStatistics.setCheckType(sortedCheckTypes);
                batchCheckStatistics.setCheckTypeTitle(checkTypeTitle);
                batchCheckStatistics.setCheckTypeItemNums(checkTypeItemNums);
                batchCheckStatistics.setCheckTypeUnitNums(checkTypeUnitNums);
                batchCheckStatistics.setCheckFormNums(batchCheckInfo.getFormulas().size());
                EntityViewData dwEntity = this.jtableParamService.getDwEntity(batchCheckInfo.getContext().getFormSchemeKey());
                DimensionValue dimensionValue = (DimensionValue)batchCheckInfo.getContext().getDimensionSet().get(dwEntity.getDimensionName());
                if (dimensionValue.getValue().equals("") || allDW.booleanValue()) {
                    batchCheckStatistics.setCheckUnitNums(0);
                } else {
                    batchCheckStatistics.setCheckUnitNums(dimensionValue.getValue().split(";").length);
                }
                IEntityMetaService iEntityMetaService = (IEntityMetaService)BeanUtil.getBean(IEntityMetaService.class);
                IRunTimeViewController definitionRunTimeViewController = (IRunTimeViewController)com.jiuqi.nr.definition.internal.BeanUtil.getBean(IRunTimeViewController.class);
                TaskOrgLinkListStream taskOrgLinkListStream = definitionRunTimeViewController.listTaskOrgLinkStreamByTask(batchCheckInfo.getContext().getTaskKey());
                if (taskOrgLinkListStream.auth().i18n().getList().size() > 1) {
                    String entityId = DsContextHolder.getDsContext().getContextEntityId();
                    for (Iterator<String> taskOrgLinkDefine : taskOrgLinkListStream.auth().i18n().getList()) {
                        void var26_32;
                        if (!entityId.equals(taskOrgLinkDefine.getEntity())) continue;
                        String string = "";
                        if (StringUtils.isNotEmpty((String)taskOrgLinkDefine.getEntityAlias())) {
                            String string2 = taskOrgLinkDefine.getEntityAlias();
                        } else {
                            String string3 = iEntityMetaService.queryEntity(taskOrgLinkDefine.getEntity()).getTitle();
                        }
                        batchCheckStatistics.setUnitCorporateTitle((String)var26_32);
                    }
                }
            }
            ArrayList<List<String>> list = new ArrayList<List<String>>();
            int x = 0;
            HashMap<Integer, String> checkTypeMap = this.checkTypeName();
            HashMap<String, String> fmKCodeMap = new HashMap<String, String>();
            HashMap<String, Object> rowDimMaps = new HashMap<String, Object>();
            ArrayList<String> excludeDimList = new ArrayList<String>();
            boolean exportRowDim = "1".equals(this.iNvwaSystemOptionService.get("nr-audit-group", "EXPORT_ROW_DIM"));
            Map<String, String> dimValueTitleMap = this.checkReviewTransformUtil.queryAllDimTitle(jtableContext);
            List<DimensionObj> dimensionObjList = this.checkReviewTransformUtil.queryDimObj(jtableContext);
            for (FormulaCheckResultInfo formulaCheckResultInfo : formulaCheckResultInfoLists) {
                FormulaNodeInfo formulaNodeInfo;
                Object dimMap;
                String formCode;
                ArrayList<String> oneList = new ArrayList<String>();
                oneList.add(++x + "");
                String thisCheckType = "";
                if (checkTypeMap.containsKey(formulaCheckResultInfo.getFormula().getChecktype())) {
                    thisCheckType = checkTypeMap.get(formulaCheckResultInfo.getFormula().getChecktype());
                }
                oneList.add(thisCheckType);
                oneList.add(formulaCheckResultInfo.getUnitCode());
                oneList.add(formulaCheckResultInfo.getUnitTitle());
                for (DimensionObj dimensionObj : dimensionObjList) {
                    String dimValueTitle = "";
                    String dimValue = dimensionList.get(formulaCheckResultInfo.getDimensionIndex()).get(dimensionObj.getDimensionName()).getValue();
                    if (dimValueTitleMap.containsKey(dimValue)) {
                        dimValueTitle = dimValueTitleMap.get(dimValue);
                    }
                    oneList.add(dimValueTitle);
                }
                oneList.add(formulaCheckResultInfo.getFormula().getFormTitle());
                if (fmKCodeMap.containsKey(formulaCheckResultInfo.getFormula().getFormKey())) {
                    formCode = (String)fmKCodeMap.get(formulaCheckResultInfo.getFormula().getFormKey());
                } else {
                    FormDefine formDefine = this.runtimeView.queryFormById(formulaCheckResultInfo.getFormula().getFormKey());
                    formCode = formDefine == null ? "" : formDefine.getFormCode();
                    fmKCodeMap.put(formulaCheckResultInfo.getFormula().getFormKey(), formCode);
                }
                oneList.add(formCode);
                if (exportRowDim) {
                    String rowDim = "";
                    if (!formulaCheckResultInfo.getFormula().getFormKey().equals(BETWEENFORMFORMULA)) {
                        if (excludeDimList.size() == 0) {
                            excludeDimList.add("CKR_BATCH_ID");
                            excludeDimList.add("ID");
                            FormSchemeDefine formScheme = this.runtimeView.getFormScheme(jtableContext.getFormSchemeKey());
                            List dimEntities = this.entityUtil.getFmSchemeEntities(formScheme);
                            for (EntityData entityData : dimEntities) {
                                excludeDimList.add(entityData.getDimensionName());
                            }
                        }
                        HashMap<String, DimensionValue> curDims = new HashMap<String, DimensionValue>();
                        dimMap = dimensionList.get(formulaCheckResultInfo.getDimensionIndex());
                        for (String string : dimMap.keySet()) {
                            if (excludeDimList.contains(string)) continue;
                            curDims.put(string, new DimensionValue((DimensionValue)dimMap.get(string)));
                        }
                        if (curDims.size() > 0) {
                            Map map;
                            Object dimTitleList;
                            if (!rowDimMaps.containsKey(formulaCheckResultInfo.getFormula().getFormKey())) {
                                dimTitleList = new ArrayList();
                                for (DataRegionDefine dataRegionDefine : this.runtimeView.getAllRegionsInForm(formulaCheckResultInfo.getFormula().getFormKey())) {
                                    if (!dataRegionDefine.getRegionKind().equals((Object)DataRegionKind.DATA_REGION_ROW_LIST) && !dataRegionDefine.getRegionKind().equals((Object)DataRegionKind.DATA_REGION_COLUMN_LIST)) continue;
                                    HashMap regionDim = new HashMap();
                                    String dataLinkKey = "";
                                    for (DataLinkDefine dataLinkDefine : this.runtimeView.getAllLinksInRegion(dataRegionDefine.getKey())) {
                                        if (!dataLinkDefine.getType().equals((Object)DataLinkType.DATA_LINK_TYPE_FIELD)) continue;
                                        dataLinkKey = dataLinkDefine.getLinkExpression();
                                        break;
                                    }
                                    ArrayList<String> title = new ArrayList<String>();
                                    ArrayList<String> referCode = new ArrayList<String>();
                                    ArrayList<String> code = new ArrayList<String>();
                                    HashMap<String, String> repetitiveReferCode = new HashMap<String, String>();
                                    DataField dataField = this.iRuntimeDataSchemeService.getDataField(dataLinkKey);
                                    for (DataField dimsField : this.iRuntimeDataSchemeService.getBizDataFieldByTableKey(dataField.getDataTableKey())) {
                                        if (!dimsField.getDataFieldKind().equals((Object)DataFieldKind.TABLE_FIELD_DIM)) continue;
                                        title.add(dimsField.getTitle());
                                        if (StringUtils.isNotEmpty((String)dimsField.getRefDataEntityKey())) {
                                            String curReferCode = this.iEntityMetaService.queryEntity(dimsField.getRefDataEntityKey()).getDimensionName();
                                            if (referCode.contains(curReferCode) || jtableContext.getDimensionSet().containsKey(curReferCode)) {
                                                repetitiveReferCode.put(curReferCode, "");
                                            }
                                            referCode.add(curReferCode);
                                        } else {
                                            referCode.add("");
                                        }
                                        code.add(dimsField.getCode());
                                    }
                                    for (int i = 0; i < title.size(); ++i) {
                                        String curReferCode = (String)referCode.get(i);
                                        if (curReferCode == "" || repetitiveReferCode.containsKey(curReferCode)) {
                                            regionDim.put(code.get(i), title.get(i));
                                            continue;
                                        }
                                        regionDim.put(curReferCode, title.get(i));
                                    }
                                    if (regionDim.size() <= 0) continue;
                                    dimTitleList.add(regionDim);
                                }
                                rowDimMaps.put(formulaCheckResultInfo.getFormula().getFormKey(), dimTitleList);
                            }
                            dimTitleList = ((List)rowDimMaps.get(formulaCheckResultInfo.getFormula().getFormKey())).iterator();
                            while (dimTitleList.hasNext() && (rowDim = this.checkDimMapMatches(map = (Map)dimTitleList.next(), curDims)) == "") {
                            }
                        }
                    }
                    oneList.add(rowDim);
                }
                oneList.add(formulaCheckResultInfo.getFormula().getCode());
                oneList.add(formulaCheckResultInfo.getFormula().getMeanning());
                oneList.add(formulaCheckResultInfo.getFormula().getFormula());
                if (formulaCheckResultInfo.getDifference() == null) {
                    oneList.add("");
                } else {
                    String difference = "\u5de6\u8fb9=" + this.toValue(formulaCheckResultInfo.getLeft()) + "  \u53f3\u8fb9=" + this.toValue(formulaCheckResultInfo.getRight()) + "  \u5dee\u989d=" + this.toValue(formulaCheckResultInfo.getDifference());
                    oneList.add(difference);
                }
                List formulaNodeList = formulaCheckResultInfo.getNodes();
                StringBuilder errorMsg = new StringBuilder();
                dimMap = formulaNodeList.iterator();
                while (dimMap.hasNext() && !(!StringUtils.isEmpty((String)(formulaNodeInfo = (FormulaNodeInfo)dimMap.next()).getFieldTitle()) ? !this.wrongDataLengthJudgment(errorMsg, formulaNodeInfo.getFieldTitle(), formulaNodeInfo.getValue()) : !this.splicingWhenEmptyIndexTitle(formulaNodeInfo, errorMsg))) {
                }
                oneList.add(errorMsg.toString());
                String descMsg = "";
                DescriptionInfo descriptionInfo = formulaCheckResultInfo.getDescriptionInfo();
                if (null != descriptionInfo && null != descriptionInfo.getDescription()) {
                    descMsg = descriptionInfo.getDescription();
                }
                oneList.add(descMsg);
                list.add(oneList);
            }
            return this.export2007(list, createTitle, batchCheckStatistics, this.createTitle(jtableContext));
        }
        return this.export2007(null, createTitle, null, this.createTitle(jtableContext));
    }

    private String checkDimMapMatches(Map<String, String> dimNameToTitle, Map<String, DimensionValue> curDims) {
        String ret = "";
        if (dimNameToTitle.size() != curDims.size()) {
            return "";
        }
        for (String key : dimNameToTitle.keySet()) {
            if (!curDims.containsKey(key)) {
                return "";
            }
            ret = ret + dimNameToTitle.get(key) + "=" + curDims.get(key).getValue() + " ";
        }
        return ret;
    }

    private boolean wrongDataLengthJudgment(StringBuilder errorMsg, String fieldTitleOfFormulaNodeInfo, String valueOfFormulaNodeInfo) {
        valueOfFormulaNodeInfo = !StringUtils.isEmpty((String)valueOfFormulaNodeInfo) ? "=" + valueOfFormulaNodeInfo + " " : "= ";
        if (errorMsg.length() + fieldTitleOfFormulaNodeInfo.length() + valueOfFormulaNodeInfo.length() < 32667) {
            errorMsg.append(fieldTitleOfFormulaNodeInfo);
            errorMsg.append(valueOfFormulaNodeInfo);
            return true;
        }
        return false;
    }

    private boolean splicingWhenEmptyIndexTitle(FormulaNodeInfo formulaNodeInfo, StringBuilder errorMsg) {
        try {
            FieldDefine fieldDefine;
            if (!StringUtils.isEmpty((String)formulaNodeInfo.getFieldKey()) ? !this.wrongDataLengthJudgment(errorMsg, (fieldDefine = this.dataDefinitionRuntimeController.queryFieldDefine(formulaNodeInfo.getFieldKey())).getCode(), formulaNodeInfo.getValue()) : !this.wrongDataLengthJudgment(errorMsg, formulaNodeInfo.getNodeShow(), formulaNodeInfo.getValue())) {
                return false;
            }
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            return false;
        }
        return true;
    }

    private SXSSFWorkbook export2007(List<List<String>> list, String[] title, BatchCheckStatistics batchCheckStatistics, String[] initTitle) {
        SXSSFWorkbook wb = new SXSSFWorkbook(5000);
        String CheckResult2 = this.i18nHelper.getMessage("Check-Result").equals("") ? "\u5ba1\u6838\u7ed3\u679c" : this.i18nHelper.getMessage("Check-Result");
        SXSSFSheet sheet = wb.createSheet(CheckResult2);
        List<String> initTitleList = Arrays.asList(initTitle);
        DefaultTitle defaultTitle = new DefaultTitle();
        for (int i = 0; i < title.length; ++i) {
            if (title[i].equals(defaultTitle.SerialNumber)) {
                sheet.setColumnWidth(i, 2048);
                continue;
            }
            if (title[i].equals(defaultTitle.ReviewType)) {
                sheet.setColumnWidth(i, 2560);
                continue;
            }
            if (title[i].equals(defaultTitle.UnitCode)) {
                sheet.setColumnWidth(i, 6400);
                continue;
            }
            if (title[i].equals(defaultTitle.UnitTitle)) {
                sheet.setColumnWidth(i, 6400);
                continue;
            }
            if (title[i].equals(defaultTitle.ReportName)) {
                sheet.setColumnWidth(i, 6400);
                continue;
            }
            if (title[i].equals(defaultTitle.ReportCode)) {
                sheet.setColumnWidth(i, 6400);
                continue;
            }
            if (title[i].equals(defaultTitle.FormulaNumber)) {
                sheet.setColumnWidth(i, 6400);
                continue;
            }
            if (title[i].equals(defaultTitle.FormulaDescription)) {
                sheet.setColumnWidth(i, 6400);
                continue;
            }
            if (title[i].equals(defaultTitle.Formula)) {
                sheet.setColumnWidth(i, 12800);
                continue;
            }
            if (title[i].equals(defaultTitle.Difference)) {
                sheet.setColumnWidth(i, 12800);
                continue;
            }
            if (title[i].equals(defaultTitle.ErrorData)) {
                sheet.setColumnWidth(i, 19200);
                continue;
            }
            if (title[i].equals(defaultTitle.ErrorDescription)) {
                sheet.setColumnWidth(i, 19200);
                continue;
            }
            sheet.setColumnWidth(i, 19200);
        }
        int titleIndex = 0;
        SXSSFRow row = sheet.createRow(titleIndex);
        SXSSFCell cell = null;
        CellStyle style = wb.createCellStyle();
        style = this.createCellStyle(style);
        style.setAlignment(HorizontalAlignment.CENTER);
        Font font = wb.createFont();
        font.setFontHeightInPoints((short)11);
        font.setFontName("\u5b8b\u4f53");
        font.setBold(true);
        style.setFont(font);
        if (batchCheckStatistics != null) {
            CellStyle statisticsStyle = wb.createCellStyle();
            statisticsStyle = this.createCellStyle(statisticsStyle);
            statisticsStyle.setFont(font);
            statisticsStyle.setAlignment(HorizontalAlignment.LEFT);
            StringBuffer statistics1 = new StringBuffer();
            StringBuffer statistics2 = new StringBuffer();
            StringBuffer statistics3 = new StringBuffer();
            statistics1.append("\u5ba1\u6838");
            if (StringUtils.isNotEmpty((String)batchCheckStatistics.getTaskTitle())) {
                statistics1.append(batchCheckStatistics.getTaskTitle() + "\uff0c");
            }
            if (StringUtils.isNotEmpty((String)batchCheckStatistics.getDateTitle())) {
                statistics1.append(batchCheckStatistics.getDateTitle() + "\uff0c");
            }
            if (StringUtils.isNotEmpty((String)batchCheckStatistics.getUnitCorporateTitle())) {
                statistics1.append(batchCheckStatistics.getUnitCorporateTitle() + "\uff0c");
            }
            if (batchCheckStatistics.getCheckUnitNums() == 0) {
                statistics1.append("\u6240\u6709\u5355\u4f4d\uff0c");
            } else {
                statistics1.append(batchCheckStatistics.getCheckUnitNums() + "\u5bb6\u5355\u4f4d\uff0c");
            }
            if (batchCheckStatistics.getCheckFormNums() == 0) {
                statistics1.append("\u6240\u6709\u62a5\u8868\uff0c");
            } else {
                statistics1.append(batchCheckStatistics.getCheckFormNums() + "\u5f20\u62a5\u8868\uff0c");
            }
            for (DimensionObj dimensionObj : batchCheckStatistics.getDimList()) {
                if (dimensionObj.getDimensionValue().size() > 0) {
                    statistics1.append(dimensionObj.getDimensionValue().size() + "\u4e2a");
                }
                statistics1.append(dimensionObj.getDimensionTitle() + "\uff0c");
            }
            statistics1.append("\u5ba1\u6838\u516c\u5f0f\u65b9\u6848\u5305\u62ec\uff1a").append(batchCheckStatistics.getFormulaSchemeTitle()).append("\uff0c\u5ba1\u6838\u516c\u5f0f\u7c7b\u578b\u5305\u62ec\uff1a");
            List<Integer> checkTypes = batchCheckStatistics.getCheckType();
            HashMap<Integer, String> checkTypeTitle = batchCheckStatistics.getCheckTypeTitle();
            HashMap<Integer, Integer> checkTypeItemNums = batchCheckStatistics.getCheckTypeItemNums();
            HashMap<Integer, Set<String>> checkTypeUnitNums = batchCheckStatistics.getCheckTypeUnitNums();
            for (int type : checkTypes) {
                statistics1.append(checkTypeTitle.get(type) + "\u3001");
                statistics2.append("\u5b58\u5728" + checkTypeTitle.get(type) + "\u95ee\u9898\u7684\u5355\u4f4d" + checkTypeUnitNums.get(type).size() + "\u4e2a\uff0c");
                statistics3.append(checkTypeTitle.get(type) + "\u95ee\u9898\u5171" + checkTypeItemNums.get(type) + "\u6761\uff0c");
            }
            statistics1.deleteCharAt(statistics1.length() - 1);
            statistics2.deleteCharAt(statistics2.length() - 1);
            statistics3.deleteCharAt(statistics3.length() - 1);
            sheet.addMergedRegion(new CellRangeAddress(titleIndex, titleIndex, 0, title.length - 1));
            row = sheet.createRow(titleIndex);
            cell = row.createCell(0);
            cell.setCellValue(statistics1.toString());
            cell.setCellStyle(statisticsStyle);
            sheet.addMergedRegion(new CellRangeAddress(++titleIndex, titleIndex, 0, title.length - 1));
            row = sheet.createRow(titleIndex);
            cell = row.createCell(0);
            cell.setCellValue(statistics2.toString());
            cell.setCellStyle(statisticsStyle);
            sheet.addMergedRegion(new CellRangeAddress(++titleIndex, titleIndex, 0, title.length - 1));
            row = sheet.createRow(titleIndex);
            ++titleIndex;
            cell = row.createCell(0);
            cell.setCellValue(statistics3.toString());
            cell.setCellStyle(statisticsStyle);
        }
        row = sheet.createRow(titleIndex);
        for (int i = 0; i < title.length; ++i) {
            cell = row.createCell(i);
            cell.setCellValue(title[i]);
            cell.setCellStyle(style);
        }
        List<String> titleList = Arrays.asList(title);
        style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.LEFT);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        CellStyle style2 = wb.createCellStyle();
        style2.setAlignment(HorizontalAlignment.CENTER);
        style2.setBorderBottom(BorderStyle.THIN);
        style2.setBorderLeft(BorderStyle.THIN);
        style2.setBorderTop(BorderStyle.THIN);
        style2.setBorderRight(BorderStyle.THIN);
        if (null != list && list.size() > 0) {
            for (int i = 0; i < list.size(); ++i) {
                int titleNumberByFilter = 0;
                row = sheet.createRow(i + titleIndex + 1);
                List<String> clist = list.get(i);
                for (int n = 0; n < clist.size(); ++n) {
                    String titleSingleByValue = initTitleList.get(n);
                    if (!titleList.contains(titleSingleByValue)) {
                        ++titleNumberByFilter;
                        continue;
                    }
                    String value = clist.get(n);
                    SXSSFCell cellData = row.createCell(n - titleNumberByFilter);
                    cellData.setCellValue(value);
                    if (n == 0) {
                        cellData.setCellStyle(style2);
                        continue;
                    }
                    cellData.setCellStyle(style);
                }
            }
        }
        return wb;
    }

    private CellStyle createCellStyle(CellStyle style) {
        style.setFillForegroundColor(HSSFColor.HSSFColorPredefined.GREY_80_PERCENT.getIndex());
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setFillForegroundColor(HSSFColor.HSSFColorPredefined.GREY_25_PERCENT.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setFillBackgroundColor(HSSFColor.HSSFColorPredefined.GREY_25_PERCENT.getIndex());
        return style;
    }

    private HashMap<Integer, String> checkTypeName() {
        HashMap<Integer, String> map = new HashMap<Integer, String>();
        try {
            List auditTypes = this.auditTypeDefineService.queryAllAuditType();
            if (auditTypes == null || auditTypes.size() == 0) {
                throw new Exception();
            }
            for (AuditType auditType : auditTypes) {
                map.put(auditType.getCode(), auditType.getTitle());
            }
        }
        catch (Exception e) {
            map.put(1, "\u63d0\u793a\u578b");
            map.put(2, "\u8b66\u544a\u578b");
            map.put(3, "\u9519\u8bef\u578b");
        }
        return map;
    }

    private BatchCheckInfo transformationBatchCheckInfo(BatchCheckExportInfo batchCheckExportInfo) {
        BatchCheckInfo batchCheckInfo = new BatchCheckInfo();
        batchCheckInfo.setContext(batchCheckExportInfo.getContext());
        batchCheckInfo.setFormulas(batchCheckExportInfo.getFormulas());
        batchCheckInfo.setOrderField(batchCheckExportInfo.getOrderField());
        batchCheckInfo.setFormulaSchemeKeys(batchCheckExportInfo.getFormulaSchemeKeys());
        batchCheckInfo.setAsyncTaskKey(batchCheckExportInfo.getAsyncTaskKey());
        batchCheckInfo.setAllDimResult(!batchCheckExportInfo.isFilterExportResult());
        batchCheckInfo.setEffectUpload(batchCheckExportInfo.isEffectUpload());
        batchCheckInfo.setCheckDesCheckTypes(batchCheckExportInfo.getCheckDesCheckTypes());
        List<Integer> uploadCheckTypes = batchCheckExportInfo.getUploadCheckTypes();
        if (!uploadCheckTypes.isEmpty()) {
            batchCheckInfo.setUploadCheckTypes(uploadCheckTypes);
            batchCheckInfo.setCheckDesNull(batchCheckExportInfo.isCheckDesNull());
            batchCheckInfo.setCheckDesPass(batchCheckExportInfo.isCheckDesPass());
            batchCheckInfo.setCheckDesMustPass(batchCheckExportInfo.isCheckDesMustPass());
        }
        batchCheckInfo.setDescriptionFilterType(batchCheckExportInfo.getDescriptionFilterType());
        batchCheckInfo.setDescriptionFilterContent(batchCheckExportInfo.getDescriptionFilterContent());
        return batchCheckInfo;
    }

    private JtableContext transformationFormCheckInfo(BatchCheckExportInfo batchCheckInfo) {
        JtableContext jtableContext = batchCheckInfo.getContext();
        jtableContext.setFormKey(jtableContext.getFormKey());
        return jtableContext;
    }

    @Override
    @Transactional(propagation=Propagation.SUPPORTS, readOnly=true)
    public List<SearchFieldItem> searchFieldsInForm(FieldQueryInfo fieldQueryInfo) {
        if (StringUtils.isEmpty((String)fieldQueryInfo.getFormKeys())) {
            JtableContext jtableContext = fieldQueryInfo.getContext();
            FormSchemeDefine formScheme = null;
            try {
                formScheme = this.runtimeView.getFormScheme(jtableContext.getFormSchemeKey());
            }
            catch (Exception e) {
                logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            }
            if (null == formScheme) {
                throw new NotFoundFormSchemeException(new String[]{fieldQueryInfo.getContext().getFormSchemeKey()});
            }
            List<FormGroupData> formGroupList = this.formGroupProvider.getFormGroupList(jtableContext, false);
            ArrayList<FormData> allForms = new ArrayList<FormData>();
            for (FormGroupData formGroupData : formGroupList) {
                allForms.addAll(DataEntryUtil.getAllForms(formGroupData));
            }
            StringBuilder sb = new StringBuilder();
            for (FormData formData : allForms) {
                sb.append(formData.getKey()).append(";");
            }
            if (allForms.size() > 0) {
                sb.setLength(sb.length() - 1);
                fieldQueryInfo.setFormKeys(sb.toString());
            }
        }
        FormTables dataSet = this.jtableParamService.formfields(fieldQueryInfo);
        ArrayList<SearchFieldItem> result = new ArrayList<SearchFieldItem>();
        for (FormTableFields table : dataSet.getTables()) {
            for (FieldData fieldData : table.getFields()) {
                String fieldTitle = fieldData.getFieldTitle();
                if (StringUtils.isNotEmpty((String)fieldTitle) && fieldTitle.length() > 28) {
                    fieldData.setFieldTitle(fieldTitle.substring(0, 28) + "...");
                }
                SearchFieldItem item = new SearchFieldItem(table.getKey(), table.getCode(), table.getTitle(), fieldData.getDataLinkKey(), fieldData.getFieldKey(), fieldData.getFieldCode(), fieldData.getFieldCode(), fieldTitle, fieldData.getRegionKey());
                result.add(item);
            }
        }
        return result;
    }

    @Override
    public String getFMDMFormKey(String taskKey, String formSchemeKey) {
        EntityViewData dwEntity = this.jtableParamService.getDwEntity(formSchemeKey);
        return null;
    }

    @Override
    public List<TaskDefine> getRuntimeTasks() {
        List allTaskDefines = this.runtimeView.getAllReportTaskDefines();
        return allTaskDefines;
    }

    @Override
    @Transactional(propagation=Propagation.SUPPORTS, readOnly=true)
    public boolean getUnitWriteFormDataPerm(JtableContext context) {
        IPermission iPermission = this.permissionsMap.get("DataEntryUnitPermissionImpl");
        if (null != iPermission) {
            FDimensionState writeable = iPermission.isWriteable(context);
            return writeable.isWrite();
        }
        return true;
    }

    @Override
    public FormTree getAllForms(String formSchemeKey) {
        List<FormGroupData> formGroupList = this.formGroupProvider.getFormGroupList(formSchemeKey);
        FormTree formTree = this.getFormTree(formGroupList);
        return formTree;
    }

    @Override
    public boolean haveTask(DataEntryInitParam param) {
        FuncExecuteConfig templateConfig;
        String taskKey;
        String functionCode = param.getFunctionCode();
        if (StringUtils.isEmpty((String)functionCode)) {
            functionCode = "dataentry_defaultFuncode";
        }
        if (StringUtils.isEmpty((String)(taskKey = (templateConfig = this.templateConfigService.getFuncExecuteConfigByCode(functionCode)).getConfig().getDataEntryViewConfig().getTaskKey()))) {
            Optional<TaskGroupDefine> taskGroupDefine;
            taskKey = param.getTaskKey();
            List allTaskGroup = this.runtimeView.getAllTaskGroup();
            if (allTaskGroup != null && allTaskGroup.size() > 0 && (taskGroupDefine = allTaskGroup.stream().filter(e -> e.getKey().equals(param.getTaskKey())).findAny()).isPresent()) {
                taskKey = null;
            }
        }
        return !StringUtils.isEmpty((String)taskKey);
    }

    @Override
    public FormsQueryResult getFormsReadAble(DataEntryContext context) {
        List<FormGroupData> formGroupList = this.formGroupProvider.getFormGroupListReadable(context.getFormSchemeKey());
        FormsQueryResult result = new FormsQueryResult();
        FormTree formTree = this.getFormTree(formGroupList);
        result.setTree(formTree);
        return result;
    }

    @Override
    public BatchCheckParam getBatchCheckParam(JtableContext context) {
        BatchCheckParam batchCheckParam = new BatchCheckParam();
        batchCheckParam.setSysParam(this.getSysParam(context.getTaskKey()));
        batchCheckParam.setEntityList(this.jtableParamService.getEntityList(context.getFormSchemeKey()));
        batchCheckParam.setFormulaSchemeList(this.jtableParamService.getFormulaSchemeDatasByFormScheme(context.getFormSchemeKey()));
        batchCheckParam.setReviewInfoParam(this.reviewInfoService.getReviewInfoTable(context.getFormSchemeKey()));
        return batchCheckParam;
    }

    @Override
    public List<FormSchemeResult> getFormSchemeData(DataEntryInitParam param) {
        String functionCode = param.getFunctionCode();
        if (StringUtils.isEmpty((String)functionCode)) {
            functionCode = "dataentry_defaultFuncode";
        }
        FuncExecuteConfig templateConfig = this.templateConfigService.getFuncExecuteConfigByCode(functionCode);
        String taskKey = param.getTaskKey();
        ArrayList<FormSchemeData> schemeList = new ArrayList<FormSchemeData>();
        List<FormSchemeDefine> queryAllFormSchemeDefines = new ArrayList();
        TaskDefine taskDefine = null;
        if (taskKey != null) {
            try {
                this.runtimeView.initTask(taskKey);
            }
            catch (Exception e2) {
                throw new RuntimeException(e2);
            }
            taskDefine = this.runtimeView.queryTaskDefine(taskKey);
            if (StringUtils.isEmpty((String)param.getFormSchemeKey())) {
                try {
                    queryAllFormSchemeDefines = this.runtimeView.queryFormSchemeByTask(param.getTaskKey());
                }
                catch (Exception e3) {
                    throw new RuntimeException(e3);
                }
            } else {
                FormSchemeDefine formScheme = this.runtimeView.getFormScheme(param.getFormSchemeKey());
                queryAllFormSchemeDefines.add(formScheme);
            }
        }
        if (queryAllFormSchemeDefines == null || queryAllFormSchemeDefines.isEmpty()) {
            throw new NotFoundFormSchemeException(null);
        }
        for (FormSchemeDefine formSchemeDefine : queryAllFormSchemeDefines) {
            FormSchemeData formSchemeData = new FormSchemeData();
            try {
                formSchemeData.init(this.formulaRunTimeController, this.dataAccessProvider, formSchemeDefine, taskDefine);
            }
            catch (Exception e4) {
                throw new RuntimeException(e4);
            }
            DWorkflowConfig workflowConfig = null;
            workflowConfig = this.workflowService.getWorkflowConfig(formSchemeData.getKey());
            formSchemeData.setWorkflowConfig(workflowConfig);
            try {
                AnalysisSchemeParamDefine queryAnalysisSchemeParamDefine = this.runtimeView.queryAnalysisSchemeParamDefine(formSchemeData.getKey());
                formSchemeData.setAnalysisParamDefine(queryAnalysisSchemeParamDefine);
            }
            catch (Exception e5) {
                logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e5.getMessage(), e5);
                throw new RuntimeException(e5);
            }
            schemeList.add(formSchemeData);
        }
        ArrayList<FormSchemeResult> schemeListResult = new ArrayList<FormSchemeResult>(schemeList.size());
        for (int i = 0; i < schemeList.size(); ++i) {
            JtableContext jtableContext;
            EntityQueryByViewInfo entityQueryInfo;
            DimensionValue value;
            FormSchemeData scheme = (FormSchemeData)schemeList.get(i);
            FormSchemeResult schemeResult = new FormSchemeResult(scheme);
            schemeListResult.add(schemeResult);
            List<EntityViewData> entitys = scheme.getEntitys();
            HashMap<String, DimensionValue> dimensions = new HashMap<String, DimensionValue>();
            for (EntityViewData entity : entitys) {
                if (this.periodEntityAdapter.isPeriodEntity(entity.getKey())) {
                    value = new DimensionValue();
                    value.setName(entity.getDimensionName());
                    if (8 == scheme.getPeriodType()) {
                        value.setType(8);
                        entityQueryInfo = new EntityQueryByViewInfo();
                        jtableContext = new JtableContext();
                        entityQueryInfo.setContext(jtableContext);
                        jtableContext.setFormSchemeKey(scheme.getKey());
                        jtableContext.setTaskKey(taskKey);
                        entityQueryInfo.setEntityViewKey(entity.getKey());
                        EntityReturnInfo queryEntityData = this.jtableEntityService.queryCustomPeriodData(entityQueryInfo);
                        ArrayList<CustomPeriodData> customPeriods = new ArrayList<CustomPeriodData>();
                        String currValue = "";
                        for (com.jiuqi.nr.jtable.params.output.EntityData entityData : queryEntityData.getEntitys()) {
                            CustomPeriodData customPeriod = new CustomPeriodData();
                            customPeriod.setCode(entityData.getCode());
                            customPeriod.setTitle(entityData.getTitle());
                            customPeriods.add(customPeriod);
                            if (!StringUtils.isEmpty((String)currValue)) continue;
                            currValue = entityData.getCode();
                        }
                        schemeResult.setCustomPeriodDatas(customPeriods);
                        if (customPeriods.size() > 0) {
                            if (StringUtils.isNotEmpty((String)currValue)) {
                                value.setValue(currValue);
                            } else {
                                value.setValue(((CustomPeriodData)customPeriods.get(customPeriods.size() - 1)).getCode());
                            }
                            String period = templateConfig.getConfig().getDataEntryViewConfig().getPeriod();
                            if (StringUtils.isNotEmpty((String)period) && customPeriods.stream().filter(item -> item.getCode().equals(period)).count() > 0L) {
                                value.setValue(period);
                            }
                        } else {
                            value.setValue("1900N0001");
                        }
                    } else {
                        value.setType(scheme.getPeriodType());
                        PeriodWrapper currentPeriod = DataEntryUtil.getCurrPeriod(scheme.getPeriodType(), scheme.getPeriodOffset(), scheme.getFromPeriod(), scheme.getToPeriod());
                        String period = templateConfig.getConfig().getDataEntryViewConfig().getPeriod();
                        if (StringUtils.isNotEmpty((String)period)) {
                            try {
                                int periodOffset = Integer.parseInt(period);
                                currentPeriod.modifyPeriod(periodOffset);
                                value.setValue(currentPeriod.toString());
                                PeriodWrapper fromPeriod = new PeriodWrapper(scheme.getFromPeriod());
                                PeriodWrapper toPeriod = new PeriodWrapper(scheme.getToPeriod());
                                if (periodOffset > 0) {
                                    if (currentPeriod.compareTo((Object)toPeriod) > 0) {
                                        value.setValue(scheme.getToPeriod());
                                    }
                                } else if (periodOffset < 0 && currentPeriod.compareTo((Object)fromPeriod) < 0) {
                                    value.setValue(scheme.getFromPeriod());
                                }
                            }
                            catch (Exception e6) {
                                value.setValue(period);
                            }
                        } else {
                            value.setValue(currentPeriod.toString());
                        }
                    }
                    dimensions.put(entity.getDimensionName(), value);
                    continue;
                }
                if (!entity.isMasterEntity()) continue;
                value = new DimensionValue();
                value.setName(entity.getDimensionName());
                dimensions.put(entity.getDimensionName(), value);
                value.setValue("");
            }
            for (EntityViewData entity : entitys) {
                if (this.judgementPeriodView(entity.getKey()) || entity.isMasterEntity()) continue;
                value = new DimensionValue();
                value.setName(entity.getDimensionName());
                value.setValue("");
                dimensions.put(entity.getDimensionName(), value);
                entityQueryInfo = new EntityQueryByViewInfo();
                jtableContext = new JtableContext();
                jtableContext.setDimensionSet(dimensions);
                entityQueryInfo.setContext(jtableContext);
                jtableContext.setFormSchemeKey(scheme.getKey());
                jtableContext.setTaskKey(taskKey);
                entityQueryInfo.setEntityViewKey(entity.getKey());
            }
            schemeResult.setDimensionSet(dimensions);
            ArrayList<FormulaVariable> formulaVariableList = new ArrayList<FormulaVariable>();
            List queryAllFormulaVariable = this.formulaRuntimeTimeController.queryAllFormulaVariable(scheme.getKey());
            if (queryAllFormulaVariable.isEmpty()) {
                schemeResult.setFormulaVariables(formulaVariableList);
            } else {
                queryAllFormulaVariable.stream().filter(e -> e.getInitType() == 1).forEach(f -> formulaVariableList.add(new FormulaVariable().convertToFormulaVariable((FormulaVariDefine)f)));
                schemeResult.setFormulaVariables(formulaVariableList);
            }
            if (this.iFormSchemeService.enableAdjustPeriod(scheme.getKey())) {
                value = new DimensionValue();
                value.setName("ADJUST");
                value.setValue("0");
                dimensions.put("ADJUST", value);
                schemeResult.setOpenAdJustPeriod(this.iFormSchemeService.enableAdjustPeriod(scheme.getKey()));
            }
            schemeResult.setExistCurrencyAttributes(this.iFormSchemeService.existCurrencyAttributes(scheme.getKey()));
            ArrayList<String> reportDimsList = new ArrayList<String>();
            reportDimsList.addAll(this.iFormSchemeService.getReportDimensionKey(scheme.getKey()));
            schemeResult.setReportDimensionList(reportDimsList);
        }
        return schemeListResult;
    }

    private PeriodType parsePeriodType(int timeGranularity) {
        switch (timeGranularity) {
            case 0: {
                return PeriodType.YEAR;
            }
            case 1: {
                return PeriodType.HALFYEAR;
            }
            case 2: {
                return PeriodType.SEASON;
            }
            case 3: {
                return PeriodType.MONTH;
            }
            case 4: {
                return PeriodType.TENDAY;
            }
            case 5: {
                return PeriodType.DAY;
            }
        }
        return null;
    }

    private String timeKeyToPeriod(String timekey, PeriodType periodType) {
        Date date;
        if (periodType == null) {
            return timekey;
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        try {
            date = format.parse(timekey);
        }
        catch (ParseException e) {
            return timekey;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        PeriodWrapper pw = periodType.fromCalendar(calendar);
        return pw.toString();
    }

    private String bi_to_nr(String biTimekey, int timeGranularity) {
        PeriodType type = this.parsePeriodType(timeGranularity);
        return this.timeKeyToPeriod(biTimekey, type);
    }

    @Override
    public Map<String, Object> queryInitLinkData(InitLinkParam initLinkParam) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        HashMap<String, Object> dimensionMap = new HashMap<String, Object>();
        map.put("dim", dimensionMap);
        List schemePeriodLinkDefines = new ArrayList();
        try {
            List filter;
            String period = "";
            TaskData taskData = this.dataEntryParamService.getRuntimeTaskByKey(initLinkParam.getTaskKey());
            List<EntityViewData> entitys = taskData.getEntitys();
            Map<String, Object> linkParamMap = initLinkParam.getLinkParamMap();
            TaskDefine taskDefine = this.runtimeView.queryTaskDefine(initLinkParam.getTaskKey());
            int timeGranularity = -1;
            switch (taskDefine.getDateTime()) {
                case "N": {
                    timeGranularity = 0;
                    break;
                }
                case "H": {
                    timeGranularity = 1;
                    break;
                }
                case "J": {
                    timeGranularity = 2;
                    break;
                }
                case "Y": {
                    timeGranularity = 3;
                    break;
                }
                case "X": {
                    timeGranularity = 4;
                    break;
                }
                case "R": {
                    timeGranularity = 5;
                    break;
                }
                case "Z": {
                    timeGranularity = 6;
                    break;
                }
            }
            for (EntityViewData entity : entitys) {
                String dimensionName = entity.getDimensionName();
                String aliName = "P_" + dimensionName;
                if (!linkParamMap.containsKey(aliName)) continue;
                if (entity.isMasterEntity()) {
                    if (linkParamMap.get(aliName) instanceof List) {
                        map.put("md_org", ((List)linkParamMap.get(aliName)).get(0));
                        continue;
                    }
                    map.put("md_org", linkParamMap.get(aliName));
                    continue;
                }
                if ("TABLE_KIND_ENTITY_PERIOD".equals(entity.getKind())) {
                    if (linkParamMap.get(aliName) instanceof List) {
                        period = this.bi_to_nr((String)((List)linkParamMap.get(aliName)).get(0), timeGranularity);
                        map.put("period", period);
                        continue;
                    }
                    period = this.bi_to_nr(linkParamMap.get(aliName).toString(), timeGranularity);
                    map.put("period", period);
                    continue;
                }
                if (linkParamMap.get(aliName) instanceof List) {
                    dimensionMap.put(dimensionName, ((List)linkParamMap.get(aliName)).get(0));
                    continue;
                }
                dimensionMap.put(dimensionName, linkParamMap.get(aliName).toString());
            }
            initLinkParam.setPeriod(period);
            if (StringUtils.isNotEmpty((String)period) && (filter = (schemePeriodLinkDefines = this.runtimeView.querySchemePeriodLinkByTask(initLinkParam.getTaskKey())).stream().filter(item -> item.getPeriodKey().equals(initLinkParam.getPeriod())).collect(Collectors.toList())).size() > 0 && StringUtils.isNotEmpty((String)initLinkParam.getFieldCode())) {
                List formDefines = this.runtimeView.queryAllFormDefinesByFormScheme(((SchemePeriodLinkDefine)filter.get(0)).getSchemeKey());
                List formKeys = formDefines.stream().map(item -> item.getKey()).collect(Collectors.toList());
                List<String> codes = Arrays.asList(initLinkParam.getFieldCode().split("\\."));
                if (codes.size() == 2) {
                    List dataFieldByTableCode = this.iRuntimeDataSchemeService.getDataFieldByTableCode(codes.get(0));
                    List dataFieldFilter = dataFieldByTableCode.stream().filter(item -> item.getCode().equals(codes.get(1))).collect(Collectors.toList());
                    if (dataFieldFilter.size() > 0) {
                        List linkDefines = this.runtimeView.getDataLinksByField(((DataField)dataFieldFilter.get(0)).getKey());
                        for (DataLinkDefine linkDefine : linkDefines) {
                            RegionData region = this.jtableParamService.getRegion(linkDefine.getRegionKey());
                            if (!formKeys.contains(region.getFormKey())) continue;
                            if (region.getType() == DataRegionKind.DATA_REGION_SIMPLE.getValue()) {
                                map.put("dataLinkKey", linkDefine.getKey());
                                map.put("regionId", region.getKey());
                            }
                            map.put("formKey", region.getFormKey());
                        }
                    }
                    return map;
                }
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        return map;
    }

    private class DefaultTitle {
        String SerialNumber;
        String ReviewType;
        String UnitCode;
        String UnitTitle;
        String ReportName;
        String ReportCode;
        String FormulaNumber;
        String FormulaDescription;
        String Formula;
        String Difference;
        String ErrorData;
        String ErrorDescription;

        public DefaultTitle() {
            this.SerialNumber = FuncExecuteServiceImpl.this.i18nHelper.getMessage("SerialNumber").equals("") ? "\u5e8f\u53f7" : FuncExecuteServiceImpl.this.i18nHelper.getMessage("SerialNumber");
            this.ReviewType = FuncExecuteServiceImpl.this.i18nHelper.getMessage("Review-Type").equals("") ? "\u5ba1\u6838\u7c7b\u578b" : FuncExecuteServiceImpl.this.i18nHelper.getMessage("Review-Type");
            this.UnitCode = FuncExecuteServiceImpl.this.i18nHelper.getMessage("Unit-Code").equals("") ? "\u5355\u4f4d\u4ee3\u7801" : FuncExecuteServiceImpl.this.i18nHelper.getMessage("Unit-Code");
            this.UnitTitle = FuncExecuteServiceImpl.this.i18nHelper.getMessage("Unit-Title").equals("") ? "\u5355\u4f4d\u540d\u79f0" : FuncExecuteServiceImpl.this.i18nHelper.getMessage("Unit-Title");
            this.ReportName = FuncExecuteServiceImpl.this.i18nHelper.getMessage("ReportName").equals("") ? "\u6240\u5728\u62a5\u8868\u540d\u79f0" : FuncExecuteServiceImpl.this.i18nHelper.getMessage("ReportName");
            this.ReportCode = FuncExecuteServiceImpl.this.i18nHelper.getMessage("ReportCode").equals("") ? "\u6240\u5728\u62a5\u8868\u6807\u8bc6" : FuncExecuteServiceImpl.this.i18nHelper.getMessage("ReportCode");
            this.FormulaNumber = FuncExecuteServiceImpl.this.i18nHelper.getMessage("Formula-Number").equals("") ? "\u516c\u5f0f\u7f16\u53f7" : FuncExecuteServiceImpl.this.i18nHelper.getMessage("Formula-Number");
            this.FormulaDescription = FuncExecuteServiceImpl.this.i18nHelper.getMessage("Formula-Description").equals("") ? "\u516c\u5f0f\u8bf4\u660e" : FuncExecuteServiceImpl.this.i18nHelper.getMessage("Formula-Description");
            this.Formula = FuncExecuteServiceImpl.this.i18nHelper.getMessage("Formula").equals("") ? "\u516c\u5f0f" : FuncExecuteServiceImpl.this.i18nHelper.getMessage("Formula");
            this.Difference = FuncExecuteServiceImpl.this.i18nHelper.getMessage("Difference").equals("") ? "\u5dee\u989d" : FuncExecuteServiceImpl.this.i18nHelper.getMessage("Difference");
            this.ErrorData = FuncExecuteServiceImpl.this.i18nHelper.getMessage("ErrorData").equals("") ? "\u9519\u8bef\u6570\u636e" : FuncExecuteServiceImpl.this.i18nHelper.getMessage("ErrorData");
            this.ErrorDescription = FuncExecuteServiceImpl.this.i18nHelper.getMessage("ErrorDescription").equals("") ? "\u5ba1\u6838\u9519\u8bef\u8bf4\u660e" : FuncExecuteServiceImpl.this.i18nHelper.getMessage("ErrorDescription");
        }
    }
}

