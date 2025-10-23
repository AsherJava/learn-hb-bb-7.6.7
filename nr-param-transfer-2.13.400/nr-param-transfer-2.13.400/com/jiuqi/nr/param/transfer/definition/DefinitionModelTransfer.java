/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.JsonDeserializer
 *  com.fasterxml.jackson.databind.JsonSerializer
 *  com.fasterxml.jackson.databind.Module
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.fasterxml.jackson.databind.module.SimpleModule
 *  com.jiuqi.bi.transfer.engine.ex.TransferException
 *  com.jiuqi.bi.transfer.engine.intf.IExportContext
 *  com.jiuqi.bi.transfer.engine.intf.IImportContext
 *  com.jiuqi.bi.transfer.engine.intf.IModelTransfer
 *  com.jiuqi.bi.transfer.engine.model.MetaExportModel
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.core.context.ContextUser
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.definition.common.UUIDUtils
 *  com.jiuqi.np.definition.exception.DBParaException
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.np.definition.internal.log.Log
 *  com.jiuqi.nr.conditionalstyle.controller.impl.DesignConditionalStyleController
 *  com.jiuqi.nr.conditionalstyle.facade.DesignConditionalStyle
 *  com.jiuqi.nr.conditionalstyle.facade.impl.DesignConditionalStyleImpl
 *  com.jiuqi.nr.datascheme.api.DesignDataField
 *  com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService
 *  com.jiuqi.nr.datascheme.common.jackson.InstantJsonDeserializer
 *  com.jiuqi.nr.datascheme.common.jackson.InstantJsonSerializer
 *  com.jiuqi.nr.datascheme.i18n.language.LanguageType
 *  com.jiuqi.nr.definition.api.IDesignTimePrintController
 *  com.jiuqi.nr.definition.auth.DefinitionAuthorityProvider
 *  com.jiuqi.nr.definition.common.FormType
 *  com.jiuqi.nr.definition.common.TaskLinkExpressionType
 *  com.jiuqi.nr.definition.common.TaskLinkExpressionTypeDeserialize
 *  com.jiuqi.nr.definition.common.TaskLinkExpressionTypeSerialize
 *  com.jiuqi.nr.definition.common.TaskLinkMatchingType
 *  com.jiuqi.nr.definition.controller.IDesignTimeViewController
 *  com.jiuqi.nr.definition.controller.IFormulaDesignTimeController
 *  com.jiuqi.nr.definition.controller.IPrintDesignTimeController
 *  com.jiuqi.nr.definition.controller.ITaskOptionController
 *  com.jiuqi.nr.definition.exception.DefinitonException
 *  com.jiuqi.nr.definition.exception.DesignCheckException
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nr.definition.facade.DesignAnalysisFormParamDefine
 *  com.jiuqi.nr.definition.facade.DesignAnalysisSchemeParamDefine
 *  com.jiuqi.nr.definition.facade.DesignDataLinkDefine
 *  com.jiuqi.nr.definition.facade.DesignDataLinkMappingDefine
 *  com.jiuqi.nr.definition.facade.DesignDataRegionDefine
 *  com.jiuqi.nr.definition.facade.DesignFormDefine
 *  com.jiuqi.nr.definition.facade.DesignFormFoldingDefine
 *  com.jiuqi.nr.definition.facade.DesignFormGroupDefine
 *  com.jiuqi.nr.definition.facade.DesignFormSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignFormulaDefine
 *  com.jiuqi.nr.definition.facade.DesignFormulaSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignPrintComTemDefine
 *  com.jiuqi.nr.definition.facade.DesignPrintSettingDefine
 *  com.jiuqi.nr.definition.facade.DesignPrintTemplateDefine
 *  com.jiuqi.nr.definition.facade.DesignPrintTemplateSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignRegionSettingDefine
 *  com.jiuqi.nr.definition.facade.DesignRegionTabSettingDefine
 *  com.jiuqi.nr.definition.facade.DesignReportTagDefine
 *  com.jiuqi.nr.definition.facade.DesignSchemePeriodLinkDefine
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 *  com.jiuqi.nr.definition.facade.DesignTaskGroupDefine
 *  com.jiuqi.nr.definition.facade.DesignTaskLinkDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormGroupDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.FormulaDefine
 *  com.jiuqi.nr.definition.facade.FormulaSchemeDefine
 *  com.jiuqi.nr.definition.facade.PrintComTemDefine
 *  com.jiuqi.nr.definition.facade.PrintSettingDefine
 *  com.jiuqi.nr.definition.facade.PrintTemplateDefine
 *  com.jiuqi.nr.definition.facade.PrintTemplateSchemeDefine
 *  com.jiuqi.nr.definition.facade.RegionSettingDefine
 *  com.jiuqi.nr.definition.facade.RegionTabSettingDefine
 *  com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.facade.TaskFlowsDefine
 *  com.jiuqi.nr.definition.facade.TaskLinkOrgMappingRule
 *  com.jiuqi.nr.definition.facade.TaskOrgLinkDefine
 *  com.jiuqi.nr.definition.facade.analysis.ColAttribute
 *  com.jiuqi.nr.definition.facade.analysis.DimensionAttribute
 *  com.jiuqi.nr.definition.facade.analysis.DimensionConfig
 *  com.jiuqi.nr.definition.facade.analysis.DimensionInfo
 *  com.jiuqi.nr.definition.facade.analysis.FloatListConfig
 *  com.jiuqi.nr.definition.facade.analysis.LineCaliber
 *  com.jiuqi.nr.definition.facade.formula.DesignFormulaCondition
 *  com.jiuqi.nr.definition.facade.formula.DesignFormulaConditionLink
 *  com.jiuqi.nr.definition.facade.report.TransformReportDefine
 *  com.jiuqi.nr.definition.internal.dao.DesignBigDataTableDao
 *  com.jiuqi.nr.definition.internal.dao.DesignFormGroupLinkDao
 *  com.jiuqi.nr.definition.internal.dao.DesignTaskGroupLinkDao
 *  com.jiuqi.nr.definition.internal.impl.AnalysisFormParamDefineImpl
 *  com.jiuqi.nr.definition.internal.impl.AnalysisSchemeParamDefineImpl
 *  com.jiuqi.nr.definition.internal.impl.DesignBigDataTable
 *  com.jiuqi.nr.definition.internal.impl.DesignFormDefineBigDataUtil
 *  com.jiuqi.nr.definition.internal.impl.DesignFormFoldingDefineImpl
 *  com.jiuqi.nr.definition.internal.impl.DesignFormGroupLink
 *  com.jiuqi.nr.definition.internal.impl.DesignReportTagDefineImpl
 *  com.jiuqi.nr.definition.internal.impl.DesignSchemePeriodLink
 *  com.jiuqi.nr.definition.internal.impl.DesignTaskFlowsDefine
 *  com.jiuqi.nr.definition.internal.impl.DesignTaskGroupLink
 *  com.jiuqi.nr.definition.internal.impl.RegionTabSettingData
 *  com.jiuqi.nr.definition.internal.impl.TaskOrgLinkDefineImpl
 *  com.jiuqi.nr.definition.internal.impl.anslysis.ColAttributeImpl
 *  com.jiuqi.nr.definition.internal.impl.anslysis.DimensionAttributeImpl
 *  com.jiuqi.nr.definition.internal.impl.anslysis.DimensionConfigImpl
 *  com.jiuqi.nr.definition.internal.impl.anslysis.DimensionInfoImpl
 *  com.jiuqi.nr.definition.internal.impl.anslysis.FloatListConfigImpl
 *  com.jiuqi.nr.definition.internal.impl.anslysis.LineCaliberImpl
 *  com.jiuqi.nr.definition.internal.service.DesignFormDefineService
 *  com.jiuqi.nr.definition.internal.service.DesignFormFoldingService
 *  com.jiuqi.nr.definition.internal.service.DesignFormulaVariableDefineService
 *  com.jiuqi.nr.definition.internal.service.DesignTaskGroupCacheService
 *  com.jiuqi.nr.definition.internal.service.TaskOrgLinkService
 *  com.jiuqi.nr.definition.option.core.TaskOption
 *  com.jiuqi.nr.definition.paramio.CustomBusiness
 *  com.jiuqi.nr.definition.paramlanguage.common.LanguageResourceType
 *  com.jiuqi.nr.definition.paramlanguage.dao.DesParamLanguageDao
 *  com.jiuqi.nr.definition.paramlanguage.entity.DesParamLanguage
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.filterTemplate.facade.FilterTemplateDO
 *  com.jiuqi.nr.filterTemplate.facade.FilterTemplateDTO
 *  com.jiuqi.nr.filterTemplate.service.IFilterTemplateService
 *  com.jiuqi.nr.multcheck2.bean.MCSchemeParam
 *  com.jiuqi.nr.multcheck2.bean.MultcheckScheme
 *  com.jiuqi.nr.multcheck2.service.IMCParamService
 *  com.jiuqi.nvwa.grid2.Grid2Data
 *  com.jiuqi.nvwa.grid2.GridCellData
 *  com.jiuqi.nvwa.grid2.json.Grid2DataDeserialize
 *  com.jiuqi.nvwa.grid2.json.Grid2DataSerialize
 *  com.jiuqi.nvwa.grid2.json.GridCellDataDeserialize
 *  com.jiuqi.nvwa.grid2.json.GridCellDataSerialize
 *  com.jiuqi.nvwa.subsystem.core.manage.IParamLevelManager
 *  com.jiuqi.util.OrderGenerator
 *  nr.single.map.configurations.bean.ISingleMappingConfig
 *  nr.single.map.configurations.bean.SingleMappingConfig
 *  nr.single.map.data.facade.SingleFileEnumInfo
 *  nr.single.map.data.facade.SingleFileEnumItem
 *  nr.single.map.data.facade.SingleFileFormulaItem
 *  nr.single.map.data.facade.SingleFileTaskInfo
 *  nr.single.map.data.internal.SingleFileEnumInfoImpl
 *  nr.single.map.data.internal.SingleFileEnumItemImpl
 *  nr.single.map.data.internal.SingleFileFormulaItemImpl
 *  nr.single.map.data.internal.SingleFileTaskInfoImpl
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.nr.param.transfer.definition;

import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.jiuqi.bi.transfer.engine.ex.TransferException;
import com.jiuqi.bi.transfer.engine.intf.IExportContext;
import com.jiuqi.bi.transfer.engine.intf.IImportContext;
import com.jiuqi.bi.transfer.engine.intf.IModelTransfer;
import com.jiuqi.bi.transfer.engine.model.MetaExportModel;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.core.context.ContextUser;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.definition.common.UUIDUtils;
import com.jiuqi.np.definition.exception.DBParaException;
import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.np.definition.internal.log.Log;
import com.jiuqi.nr.conditionalstyle.controller.impl.DesignConditionalStyleController;
import com.jiuqi.nr.conditionalstyle.facade.DesignConditionalStyle;
import com.jiuqi.nr.conditionalstyle.facade.impl.DesignConditionalStyleImpl;
import com.jiuqi.nr.datascheme.api.DesignDataField;
import com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService;
import com.jiuqi.nr.datascheme.common.jackson.InstantJsonDeserializer;
import com.jiuqi.nr.datascheme.common.jackson.InstantJsonSerializer;
import com.jiuqi.nr.datascheme.i18n.language.LanguageType;
import com.jiuqi.nr.definition.api.IDesignTimePrintController;
import com.jiuqi.nr.definition.auth.DefinitionAuthorityProvider;
import com.jiuqi.nr.definition.common.FormType;
import com.jiuqi.nr.definition.common.TaskLinkExpressionType;
import com.jiuqi.nr.definition.common.TaskLinkExpressionTypeDeserialize;
import com.jiuqi.nr.definition.common.TaskLinkExpressionTypeSerialize;
import com.jiuqi.nr.definition.common.TaskLinkMatchingType;
import com.jiuqi.nr.definition.controller.IDesignTimeViewController;
import com.jiuqi.nr.definition.controller.IFormulaDesignTimeController;
import com.jiuqi.nr.definition.controller.IPrintDesignTimeController;
import com.jiuqi.nr.definition.controller.ITaskOptionController;
import com.jiuqi.nr.definition.exception.DefinitonException;
import com.jiuqi.nr.definition.exception.DesignCheckException;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.facade.DesignAnalysisFormParamDefine;
import com.jiuqi.nr.definition.facade.DesignAnalysisSchemeParamDefine;
import com.jiuqi.nr.definition.facade.DesignDataLinkDefine;
import com.jiuqi.nr.definition.facade.DesignDataLinkMappingDefine;
import com.jiuqi.nr.definition.facade.DesignDataRegionDefine;
import com.jiuqi.nr.definition.facade.DesignFormDefine;
import com.jiuqi.nr.definition.facade.DesignFormFoldingDefine;
import com.jiuqi.nr.definition.facade.DesignFormGroupDefine;
import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignFormulaDefine;
import com.jiuqi.nr.definition.facade.DesignFormulaSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignPrintComTemDefine;
import com.jiuqi.nr.definition.facade.DesignPrintSettingDefine;
import com.jiuqi.nr.definition.facade.DesignPrintTemplateDefine;
import com.jiuqi.nr.definition.facade.DesignPrintTemplateSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignRegionSettingDefine;
import com.jiuqi.nr.definition.facade.DesignRegionTabSettingDefine;
import com.jiuqi.nr.definition.facade.DesignReportTagDefine;
import com.jiuqi.nr.definition.facade.DesignSchemePeriodLinkDefine;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.definition.facade.DesignTaskGroupDefine;
import com.jiuqi.nr.definition.facade.DesignTaskLinkDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormGroupDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.FormulaDefine;
import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import com.jiuqi.nr.definition.facade.PrintComTemDefine;
import com.jiuqi.nr.definition.facade.PrintSettingDefine;
import com.jiuqi.nr.definition.facade.PrintTemplateDefine;
import com.jiuqi.nr.definition.facade.PrintTemplateSchemeDefine;
import com.jiuqi.nr.definition.facade.RegionSettingDefine;
import com.jiuqi.nr.definition.facade.RegionTabSettingDefine;
import com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.facade.TaskFlowsDefine;
import com.jiuqi.nr.definition.facade.TaskLinkOrgMappingRule;
import com.jiuqi.nr.definition.facade.TaskOrgLinkDefine;
import com.jiuqi.nr.definition.facade.analysis.ColAttribute;
import com.jiuqi.nr.definition.facade.analysis.DimensionAttribute;
import com.jiuqi.nr.definition.facade.analysis.DimensionConfig;
import com.jiuqi.nr.definition.facade.analysis.DimensionInfo;
import com.jiuqi.nr.definition.facade.analysis.FloatListConfig;
import com.jiuqi.nr.definition.facade.analysis.LineCaliber;
import com.jiuqi.nr.definition.facade.formula.DesignFormulaCondition;
import com.jiuqi.nr.definition.facade.formula.DesignFormulaConditionLink;
import com.jiuqi.nr.definition.facade.report.TransformReportDefine;
import com.jiuqi.nr.definition.internal.dao.DesignBigDataTableDao;
import com.jiuqi.nr.definition.internal.dao.DesignFormGroupLinkDao;
import com.jiuqi.nr.definition.internal.dao.DesignTaskGroupLinkDao;
import com.jiuqi.nr.definition.internal.impl.AnalysisFormParamDefineImpl;
import com.jiuqi.nr.definition.internal.impl.AnalysisSchemeParamDefineImpl;
import com.jiuqi.nr.definition.internal.impl.DesignBigDataTable;
import com.jiuqi.nr.definition.internal.impl.DesignFormDefineBigDataUtil;
import com.jiuqi.nr.definition.internal.impl.DesignFormFoldingDefineImpl;
import com.jiuqi.nr.definition.internal.impl.DesignFormGroupLink;
import com.jiuqi.nr.definition.internal.impl.DesignReportTagDefineImpl;
import com.jiuqi.nr.definition.internal.impl.DesignSchemePeriodLink;
import com.jiuqi.nr.definition.internal.impl.DesignTaskFlowsDefine;
import com.jiuqi.nr.definition.internal.impl.DesignTaskGroupLink;
import com.jiuqi.nr.definition.internal.impl.RegionTabSettingData;
import com.jiuqi.nr.definition.internal.impl.TaskOrgLinkDefineImpl;
import com.jiuqi.nr.definition.internal.impl.anslysis.ColAttributeImpl;
import com.jiuqi.nr.definition.internal.impl.anslysis.DimensionAttributeImpl;
import com.jiuqi.nr.definition.internal.impl.anslysis.DimensionConfigImpl;
import com.jiuqi.nr.definition.internal.impl.anslysis.DimensionInfoImpl;
import com.jiuqi.nr.definition.internal.impl.anslysis.FloatListConfigImpl;
import com.jiuqi.nr.definition.internal.impl.anslysis.LineCaliberImpl;
import com.jiuqi.nr.definition.internal.service.DesignFormDefineService;
import com.jiuqi.nr.definition.internal.service.DesignFormFoldingService;
import com.jiuqi.nr.definition.internal.service.DesignFormulaVariableDefineService;
import com.jiuqi.nr.definition.internal.service.DesignTaskGroupCacheService;
import com.jiuqi.nr.definition.internal.service.TaskOrgLinkService;
import com.jiuqi.nr.definition.option.core.TaskOption;
import com.jiuqi.nr.definition.paramio.CustomBusiness;
import com.jiuqi.nr.definition.paramlanguage.common.LanguageResourceType;
import com.jiuqi.nr.definition.paramlanguage.dao.DesParamLanguageDao;
import com.jiuqi.nr.definition.paramlanguage.entity.DesParamLanguage;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.filterTemplate.facade.FilterTemplateDO;
import com.jiuqi.nr.filterTemplate.facade.FilterTemplateDTO;
import com.jiuqi.nr.filterTemplate.service.IFilterTemplateService;
import com.jiuqi.nr.multcheck2.bean.MCSchemeParam;
import com.jiuqi.nr.multcheck2.bean.MultcheckScheme;
import com.jiuqi.nr.multcheck2.service.IMCParamService;
import com.jiuqi.nr.param.transfer.ChangeObj;
import com.jiuqi.nr.param.transfer.DateJsonDeserializer;
import com.jiuqi.nr.param.transfer.DateJsonSerializer;
import com.jiuqi.nr.param.transfer.FormChangeObj;
import com.jiuqi.nr.param.transfer.FormulaChangeObj;
import com.jiuqi.nr.param.transfer.definition.FormulaGuidParse;
import com.jiuqi.nr.param.transfer.definition.TransferGuid;
import com.jiuqi.nr.param.transfer.definition.TransferGuidParse;
import com.jiuqi.nr.param.transfer.definition.TransferNodeType;
import com.jiuqi.nr.param.transfer.definition.check.CheckImportService;
import com.jiuqi.nr.param.transfer.definition.custom.CustomHelper;
import com.jiuqi.nr.param.transfer.definition.dao.AttachmentRuleDTO;
import com.jiuqi.nr.param.transfer.definition.dto.DesParamLanguageDTO;
import com.jiuqi.nr.param.transfer.definition.dto.form.AnalysisFormDTO;
import com.jiuqi.nr.param.transfer.definition.dto.form.AnalysisSchemeDTO;
import com.jiuqi.nr.param.transfer.definition.dto.form.ConditionalStyleDTO;
import com.jiuqi.nr.param.transfer.definition.dto.form.DataLinkDTO;
import com.jiuqi.nr.param.transfer.definition.dto.form.DataLinkMappingDTO;
import com.jiuqi.nr.param.transfer.definition.dto.form.EntityViewDTO;
import com.jiuqi.nr.param.transfer.definition.dto.form.FormDTO;
import com.jiuqi.nr.param.transfer.definition.dto.form.FormFoldingDTO;
import com.jiuqi.nr.param.transfer.definition.dto.form.FormInfoDTO;
import com.jiuqi.nr.param.transfer.definition.dto.form.FormStyleDTO;
import com.jiuqi.nr.param.transfer.definition.dto.form.ReginInfoDTO;
import com.jiuqi.nr.param.transfer.definition.dto.form.RegionTabSettingDTO;
import com.jiuqi.nr.param.transfer.definition.dto.formgroup.FormGroupDTO;
import com.jiuqi.nr.param.transfer.definition.dto.formgroup.FormGroupInfoDTO;
import com.jiuqi.nr.param.transfer.definition.dto.formgroup.FormGroupLinkDTO;
import com.jiuqi.nr.param.transfer.definition.dto.formscheme.FormSchemeDTO;
import com.jiuqi.nr.param.transfer.definition.dto.formscheme.FormSchemeInfoDTO;
import com.jiuqi.nr.param.transfer.definition.dto.formscheme.FormulaVariableDTO;
import com.jiuqi.nr.param.transfer.definition.dto.formscheme.MultCheckParamDTO;
import com.jiuqi.nr.param.transfer.definition.dto.formscheme.SchemePeriodLinkDTO;
import com.jiuqi.nr.param.transfer.definition.dto.formula.FormulaConditionLinkDTO;
import com.jiuqi.nr.param.transfer.definition.dto.formula.FormulaDTO;
import com.jiuqi.nr.param.transfer.definition.dto.formula.FormulaSchemeDTO;
import com.jiuqi.nr.param.transfer.definition.dto.formula.FormulaSchemeInfoDTO;
import com.jiuqi.nr.param.transfer.definition.dto.print.PrintComTemDTO;
import com.jiuqi.nr.param.transfer.definition.dto.print.PrintSettingDTO;
import com.jiuqi.nr.param.transfer.definition.dto.print.PrintTemplateDTO;
import com.jiuqi.nr.param.transfer.definition.dto.print.PrintTemplateSchemeDTO;
import com.jiuqi.nr.param.transfer.definition.dto.print.PrintTemplateSchemeInfoDTO;
import com.jiuqi.nr.param.transfer.definition.dto.singlemap.SingleMappingDTO;
import com.jiuqi.nr.param.transfer.definition.dto.task.DimensionFilterDTO;
import com.jiuqi.nr.param.transfer.definition.dto.task.FormulaConditionDTO;
import com.jiuqi.nr.param.transfer.definition.dto.task.TaskDTO;
import com.jiuqi.nr.param.transfer.definition.dto.task.TaskInfoDTO;
import com.jiuqi.nr.param.transfer.definition.dto.task.TaskOrgLinkDTO;
import com.jiuqi.nr.param.transfer.definition.dto.taskgroup.TaskGroupDTO;
import com.jiuqi.nr.param.transfer.definition.dto.taskgroup.TaskLinkDTO;
import com.jiuqi.nr.param.transfer.definition.service.AttachmentService;
import com.jiuqi.nr.param.transfer.definition.service.SingleMappingService;
import com.jiuqi.nr.param.transfer.definition.spi.TaskTransfer;
import com.jiuqi.nvwa.grid2.Grid2Data;
import com.jiuqi.nvwa.grid2.GridCellData;
import com.jiuqi.nvwa.grid2.json.Grid2DataDeserialize;
import com.jiuqi.nvwa.grid2.json.Grid2DataSerialize;
import com.jiuqi.nvwa.grid2.json.GridCellDataDeserialize;
import com.jiuqi.nvwa.grid2.json.GridCellDataSerialize;
import com.jiuqi.nvwa.subsystem.core.manage.IParamLevelManager;
import com.jiuqi.util.OrderGenerator;
import java.io.IOException;
import java.lang.invoke.LambdaMetafactory;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import nr.single.map.configurations.bean.ISingleMappingConfig;
import nr.single.map.configurations.bean.SingleMappingConfig;
import nr.single.map.data.facade.SingleFileEnumInfo;
import nr.single.map.data.facade.SingleFileEnumItem;
import nr.single.map.data.facade.SingleFileFormulaItem;
import nr.single.map.data.facade.SingleFileTaskInfo;
import nr.single.map.data.internal.SingleFileEnumInfoImpl;
import nr.single.map.data.internal.SingleFileEnumItemImpl;
import nr.single.map.data.internal.SingleFileFormulaItemImpl;
import nr.single.map.data.internal.SingleFileTaskInfoImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Component
public class DefinitionModelTransfer
implements IModelTransfer {
    private static final Logger logger = LoggerFactory.getLogger(DefinitionModelTransfer.class);
    @Autowired
    private IDesignTimeViewController designTimeViewController;
    @Autowired
    private DesignBigDataTableDao bigDataDao;
    @Autowired
    private ITaskOptionController iTaskOptionController;
    @Autowired
    private AttachmentService attachmentService;
    @Autowired
    private DesignTaskGroupCacheService taskGroupCacheService;
    @Autowired
    private DesignTaskGroupLinkDao groupLinkDao;
    @Autowired
    private DesignFormGroupLinkDao formGroupLinkDao;
    @Autowired
    private IFormulaDesignTimeController formulaDesignTimeController;
    @Autowired
    private IPrintDesignTimeController printDesignTimeController;
    @Autowired
    private IDesignTimePrintController designTimePrintController;
    @Autowired
    private DesignFormDefineService designFormDefineService;
    @Autowired
    private SingleMappingService singleMappingService;
    @Autowired(required=false)
    private List<TaskTransfer> taskTransfers;
    @Autowired
    private IParamLevelManager paramLevelManager;
    @Autowired
    private DesignConditionalStyleController conditionalStyleController;
    @Autowired
    private DefinitionAuthorityProvider definitionAuthority;
    @Autowired
    private IFilterTemplateService filterTemplateService;
    @Autowired
    private IDesignDataSchemeService designDataSchemeService;
    @Autowired
    private IEntityMetaService iEntityMetaService;
    @Autowired(required=false)
    private CheckImportService checkImportService;
    @Autowired(required=false)
    private List<CustomBusiness> customBusiness;
    @Autowired
    private DesParamLanguageDao desParamLanguageDao;
    @Autowired(required=false)
    private IMCParamService iMCParamService;
    @Autowired(required=false)
    private TaskOrgLinkService taskOrgLinkService;
    @Autowired
    private DesignFormulaVariableDefineService designFormulaVariableDefineService;
    private final Map<String, TaskTransfer> taskTransferMap = new ConcurrentHashMap<String, TaskTransfer>(3);
    public static final ObjectMapper objectMapper = new ObjectMapper();
    private static final String PARAM_LANGUAGE_ENGLISH = "2";
    private static final int NOT_LEVEL = 0;
    private static final String S_NOT_LEVEL = "0";
    @Autowired
    private DesignFormFoldingService formFoldingService;
    private List<FormulaChangeObj> formChangeFormulaObjs = new ArrayList<FormulaChangeObj>();
    private Map<String, FormChangeObj> formChangeObj = new HashMap<String, FormChangeObj>();
    public static final int STEP = 500;

    public List<FormulaChangeObj> getFormChangeFormulaObjs() {
        return this.formChangeFormulaObjs;
    }

    public void setFormChangeFormulaObjs(List<FormulaChangeObj> formChangeFormulaObjs) {
        this.formChangeFormulaObjs = formChangeFormulaObjs;
    }

    public void cleanFormChangeFormulaObjs() {
        this.formChangeFormulaObjs = new ArrayList<FormulaChangeObj>();
    }

    public Map<String, FormChangeObj> getFormChangeObj() {
        return this.formChangeObj;
    }

    public void setFormChangeObj(Map<String, FormChangeObj> formChangeObj) {
        this.formChangeObj = formChangeObj;
    }

    public void cleanFormChangeObj() {
        this.formChangeObj = new HashMap<String, FormChangeObj>();
    }

    public void addFormChangeObj(ChangeObj changeObj) {
        FormChangeObj formChangeObj = this.formChangeObj.get(changeObj.getSchemeKey());
        if (formChangeObj == null) {
            DesignFormSchemeDefine designFormSchemeDefine = this.designTimeViewController.queryFormSchemeDefine(changeObj.getSchemeKey());
            FormChangeObj updateChangeObj = new FormChangeObj(changeObj.getSchemeKey(), designFormSchemeDefine.getTitle());
            updateChangeObj.addInsertForms(changeObj);
            this.formChangeObj.put(changeObj.getSchemeKey(), updateChangeObj);
        } else {
            formChangeObj.addUpdateForms(changeObj);
        }
    }

    public void addUpdateFormChangeObj(ChangeObj changeObj) {
        FormChangeObj formChangeObj = this.formChangeObj.get(changeObj.getSchemeKey());
        if (formChangeObj == null) {
            DesignFormSchemeDefine designFormSchemeDefine = this.designTimeViewController.queryFormSchemeDefine(changeObj.getSchemeKey());
            FormChangeObj updateChangeObj = new FormChangeObj(changeObj.getSchemeKey(), designFormSchemeDefine.getTitle());
            updateChangeObj.addUpdateForms(changeObj);
            this.formChangeObj.put(changeObj.getSchemeKey(), updateChangeObj);
        } else {
            formChangeObj.addUpdateForms(changeObj);
        }
    }

    public DefinitionModelTransfer() {
        DefinitionModelTransfer.moduleRegister(objectMapper);
    }

    private TaskTransfer getTaskTransfer(String id) {
        TaskTransfer taskTransfer = this.taskTransferMap.get(id);
        if (taskTransfer == null) {
            for (TaskTransfer transfer : this.taskTransfers) {
                if (!id.equals(transfer.getId())) continue;
                this.taskTransferMap.put(id, transfer);
                return transfer;
            }
        }
        return taskTransfer;
    }

    public static void moduleRegister(ObjectMapper objectMapper) {
        SimpleModule module = new SimpleModule();
        module.addAbstractTypeMapping(TaskFlowsDefine.class, DesignTaskFlowsDefine.class);
        module.addAbstractTypeMapping(ISingleMappingConfig.class, SingleMappingConfig.class);
        module.addAbstractTypeMapping(SingleFileTaskInfo.class, SingleFileTaskInfoImpl.class);
        module.addAbstractTypeMapping(SingleFileFormulaItem.class, SingleFileFormulaItemImpl.class);
        module.addAbstractTypeMapping(SingleFileEnumInfo.class, SingleFileEnumInfoImpl.class);
        module.addAbstractTypeMapping(SingleFileEnumItem.class, SingleFileEnumItemImpl.class);
        module.addAbstractTypeMapping(DesignReportTagDefine.class, DesignReportTagDefineImpl.class);
        module.addDeserializer(Instant.class, (JsonDeserializer)new InstantJsonDeserializer());
        module.addSerializer(Instant.class, (JsonSerializer)new InstantJsonSerializer());
        module.addSerializer(Grid2Data.class, (JsonSerializer)new Grid2DataSerialize());
        module.addDeserializer(Grid2Data.class, (JsonDeserializer)new Grid2DataDeserialize());
        module.addSerializer(TaskLinkExpressionType.class, (JsonSerializer)new TaskLinkExpressionTypeSerialize());
        module.addDeserializer(TaskLinkExpressionType.class, (JsonDeserializer)new TaskLinkExpressionTypeDeserialize());
        module.addSerializer(GridCellData.class, (JsonSerializer)new GridCellDataSerialize());
        module.addDeserializer(GridCellData.class, (JsonDeserializer)new GridCellDataDeserialize());
        module.addSerializer(Date.class, (JsonSerializer)new DateJsonSerializer());
        module.addDeserializer(Date.class, (JsonDeserializer)new DateJsonDeserializer());
        module.addAbstractTypeMapping(DimensionConfig.class, DimensionConfigImpl.class);
        module.addAbstractTypeMapping(DimensionInfo.class, DimensionInfoImpl.class);
        module.addAbstractTypeMapping(LineCaliber.class, LineCaliberImpl.class);
        module.addAbstractTypeMapping(ColAttribute.class, ColAttributeImpl.class);
        module.addAbstractTypeMapping(FloatListConfig.class, FloatListConfigImpl.class);
        module.addAbstractTypeMapping(DimensionAttribute.class, DimensionAttributeImpl.class);
        objectMapper.registerModule((Module)module);
    }

    @Transactional(rollbackFor={Exception.class})
    public void importModel(IImportContext context, byte[] bytes) throws TransferException {
        String targetGuid = context.getTargetGuid();
        TransferGuid parse = TransferGuidParse.parseId(targetGuid);
        String key = parse.getKey();
        TransferNodeType transferNodeType = parse.getTransferNodeType();
        if (transferNodeType == null) {
            logger.info("\u5bfc\u5165\u53c2\u6570\uff0cguid {} \u7684\u53c2\u6570\u7c7b\u578b\u5728\u5f53\u524d\u670d\u52a1\u4e0d\u5b58\u5728\uff0c\u5ffd\u7565\u5bfc\u5165", (Object)targetGuid);
            return;
        }
        try {
            switch (transferNodeType) {
                case TASK_GROUP: {
                    this.importTaskGroupBusiness(context, key, bytes);
                    break;
                }
                case TASK: {
                    this.importTaskBusiness(context, key, bytes);
                    break;
                }
                case FORM_SCHEME: {
                    this.importFormSchemeBusiness(context, key, bytes);
                    break;
                }
                case FORM_GROUP: {
                    this.importFormGroupBusiness(context, key, bytes);
                    break;
                }
                case FORM: {
                    this.importFormBusiness(context, key, bytes);
                    break;
                }
                case FORMULA_SCHEME: {
                    this.importFormulaSchemeBusiness(context, key, bytes);
                    break;
                }
                case PRINT_SCHEME: {
                    this.importPrintSchemeBusiness(context, key, bytes);
                    break;
                }
                case FORMULA_FORM: {
                    this.importFormulaFormBusiness(context, key, bytes);
                    break;
                }
                case PRINT_TEMPLATE: {
                    this.importPrintTemplateBusiness(context, key, bytes);
                    break;
                }
                case PRINT_COMTEM: {
                    this.importPrintComTemBusiness(context, key, bytes);
                    break;
                }
                case PRINT_SETTING: {
                    this.importPrintSettingBusiness(context, key, bytes);
                    break;
                }
                case MAPPING_DEFINE: {
                    this.importMappingDefineBusiness(context, key, bytes);
                    break;
                }
                case CUSTOM_DATA: {
                    this.importCustomData(context, key, bytes);
                    break;
                }
                default: {
                    logger.info("\u5bfc\u5165\u53c2\u6570\uff0cguid {} \u7684\u53c2\u6570\u7c7b\u578b\u5728\u5f53\u524d\u670d\u52a1\u4e0d\u5b58\u5728\uff0c\u5ffd\u7565\u5bfc\u5165", (Object)targetGuid);
                    break;
                }
            }
        }
        catch (IOException e) {
            throw new TransferException("\u89e3\u6790\u5931\u8d25", (Throwable)e);
        }
        catch (TransferException e) {
            throw e;
        }
        catch (Exception e) {
            throw new TransferException("\u5bfc\u5165\u8d44\u6e90\u5931\u8d25", (Throwable)e);
        }
    }

    public MetaExportModel exportModel(IExportContext context, String s) throws TransferException {
        TransferGuid parse = TransferGuidParse.parseId(s);
        String key = parse.getKey();
        TransferNodeType transferNodeType = parse.getTransferNodeType();
        MetaExportModel model = new MetaExportModel();
        try {
            switch (transferNodeType) {
                case TASK_GROUP: {
                    model.setData(this.getTaskGroupBusiness(context, key));
                    break;
                }
                case TASK: {
                    model.setData(this.getTaskBusiness(context, key));
                    break;
                }
                case FORM_SCHEME: {
                    model.setData(this.getFormSchemeBusiness(context, key));
                    break;
                }
                case FORM_GROUP: {
                    model.setData(this.getFormGroupBusiness(context, key));
                    break;
                }
                case FORM: {
                    model.setData(this.getFormBusiness(context, key));
                    break;
                }
                case FORMULA_SCHEME: {
                    model.setData(this.getFormulaSchemeBusiness(context, key));
                    break;
                }
                case PRINT_SCHEME: {
                    model.setData(this.getPrintSchemeBusiness(context, key));
                    break;
                }
                case FORMULA_FORM: {
                    model.setData(this.getFormulaFormBusiness(context, key));
                    break;
                }
                case PRINT_TEMPLATE: {
                    model.setData(this.getPrintTemplateBusiness(context, key));
                    break;
                }
                case PRINT_COMTEM: {
                    model.setData(this.getPrintComTemBusiness(context, key));
                    break;
                }
                case PRINT_SETTING: {
                    model.setData(this.getPrintSettingBusiness(context, key));
                    break;
                }
                case MAPPING_DEFINE: {
                    model.setData(this.getMappingDefineBusiness(key));
                    break;
                }
                case CUSTOM_DATA: {
                    model.setData(this.getCustomData(key));
                    break;
                }
                default: {
                    throw new TransferException("\u67e5\u8be2\u8d44\u6e90\u5931\u8d25" + (Object)((Object)transferNodeType));
                }
            }
        }
        catch (TransferException e) {
            throw e;
        }
        catch (Exception e) {
            throw new TransferException("\u6253\u5305\u8d44\u6e90\u5931\u8d25", (Throwable)e);
        }
        if (model.getData() == null) {
            throw new TransferException("\u67e5\u8be2\u8d44\u6e90\u5931\u8d25" + parse);
        }
        return model;
    }

    public byte[] getCustomData(String key) throws TransferException {
        String[] strings = CustomHelper.splitKey(key);
        if (null == strings || strings.length != 2) {
            return null;
        }
        String parentDataKey = strings[0];
        String dataKey = strings[1];
        if (null != this.customBusiness && !this.customBusiness.isEmpty()) {
            for (CustomBusiness business : this.customBusiness) {
                if (null == business || !business.checkBusiness(dataKey, parentDataKey)) continue;
                return business.exportData(dataKey, parentDataKey);
            }
        }
        return null;
    }

    public byte[] getMappingDefineBusiness(String key) throws TransferException {
        SingleMappingDTO mapping = this.getMappingDefineBusinessDTO(key);
        try {
            return mapping.toBytes(objectMapper);
        }
        catch (IOException e) {
            throw new TransferException("\u6253\u5305\u8d44\u6e90\u5931\u8d25", (Throwable)e);
        }
    }

    public SingleMappingDTO getMappingDefineBusinessDTO(String key) {
        return this.singleMappingService.getMapping(key);
    }

    public byte[] getPrintTemplateBusiness(IExportContext context, String key) throws TransferException, IOException {
        PrintTemplateDTO printTemplateBusinessDTO = this.getPrintTemplateBusinessDTO(context, key);
        if (printTemplateBusinessDTO != null) {
            return printTemplateBusinessDTO.toBytes(objectMapper);
        }
        return null;
    }

    public PrintTemplateDTO getPrintTemplateBusinessDTO(IExportContext context, String key) throws TransferException {
        try {
            DesignPrintTemplateDefine templateDefine = this.printDesignTimeController.queryPrintTemplateDefine(key);
            return PrintTemplateDTO.valueOf((PrintTemplateDefine)templateDefine);
        }
        catch (Exception e) {
            throw new TransferException("\u67e5\u8be2\u8d44\u6e90\u5931\u8d25", (Throwable)e);
        }
    }

    public byte[] getPrintComTemBusiness(IExportContext context, String key) throws TransferException, IOException {
        DesignPrintComTemDefine define = this.designTimePrintController.getPrintComTem(key);
        if (null != define) {
            PrintComTemDTO dto = PrintComTemDTO.valueOf((PrintComTemDefine)define);
            return dto.toBytes(objectMapper);
        }
        return null;
    }

    public byte[] getPrintSettingBusiness(IExportContext context, String key) throws TransferException, IOException {
        String formKey;
        String[] args = FormulaGuidParse.parseKey(key);
        String printSchemeKey = args[0];
        DesignPrintSettingDefine setting = this.designTimePrintController.getPrintSettingDefine(printSchemeKey, formKey = args[1]);
        if (null != setting) {
            PrintSettingDTO dto = PrintSettingDTO.valueOf((PrintSettingDefine)setting);
            return dto.toBytes(objectMapper);
        }
        return null;
    }

    public byte[] getFormulaFormBusiness(IExportContext context, String key) throws JQException, IOException, TransferException {
        FormulaSchemeDTO formulaFormBusinessDTO = this.getFormulaFormBusinessDTO(context, key);
        return formulaFormBusinessDTO.toBytes(objectMapper);
    }

    public FormulaSchemeDTO getFormulaFormBusinessDTO(IExportContext context, String key) throws JQException, TransferException {
        String[] args = FormulaGuidParse.parseKey(key);
        String formulaSchemeKey = args[0];
        String formKey = args[1];
        if (FormulaGuidParse.INTER_TABLE_FORMULA_KEY.equals(formKey)) {
            formKey = null;
        }
        List allFormulasInForm = this.formulaDesignTimeController.getAllFormulasInForm(formulaSchemeKey, formKey);
        FormulaSchemeDTO formulaSchemeDTO = new FormulaSchemeDTO();
        if (!CollectionUtils.isEmpty(allFormulasInForm)) {
            Map<String, DesParamLanguage> formulaLanguageMaps;
            List<FormulaDTO> formulaDTOs = allFormulasInForm.stream().map(FormulaDTO::valueOf).collect(Collectors.toList());
            List formulaKeys = formulaDTOs.stream().map(FormulaDTO::getKey).collect(Collectors.toList());
            List desParamLanguages = this.desParamLanguageDao.queryLanguageByResKeys(formulaKeys);
            if (desParamLanguages.size() > 0 && (formulaLanguageMaps = desParamLanguages.stream().filter(a -> PARAM_LANGUAGE_ENGLISH.equals(a.getLanguageType())).collect(Collectors.toMap(DesParamLanguage::getResourceKey, a -> a, (k1, k2) -> k1))).size() > 0) {
                for (FormulaDTO formulaDTO : formulaDTOs) {
                    DesParamLanguage desParamLanguage = formulaLanguageMaps.get(formulaDTO.getKey());
                    if (desParamLanguage == null) continue;
                    formulaDTO.setLanguageInfo(desParamLanguage.getLanguageInfo());
                }
            }
            formulaSchemeDTO.setFormulas(formulaDTOs);
            List designFormulaConditionLinks = this.formulaDesignTimeController.listConditionLinkByScheme(formulaSchemeKey);
            if (!CollectionUtils.isEmpty(designFormulaConditionLinks)) {
                Set keySet = allFormulasInForm.stream().map(IBaseMetaItem::getKey).collect(Collectors.toSet());
                formulaSchemeDTO.setFormulaConditionLinks(designFormulaConditionLinks.stream().filter(l -> keySet.contains(l.getFormulaKey())).map(FormulaConditionLinkDTO::toDto).collect(Collectors.toList()));
            }
        }
        return formulaSchemeDTO;
    }

    public byte[] getPrintSchemeBusiness(IExportContext context, String key) throws IOException, TransferException {
        PrintTemplateSchemeDTO printSchemeBusinessDTO = this.getPrintSchemeBusinessDTO(context, key);
        return printSchemeBusinessDTO.toBytes(objectMapper);
    }

    public PrintTemplateSchemeDTO getPrintSchemeBusinessDTO(IExportContext context, String key) throws IOException, TransferException {
        PrintTemplateSchemeDTO templateSchemeDTO = new PrintTemplateSchemeDTO();
        try {
            DesignPrintTemplateSchemeDefine schemeDefine = this.printDesignTimeController.queryPrintTemplateSchemeDefine(key);
            if (schemeDefine == null) {
                throw new TransferException("\u67e5\u8be2\u8d44\u6e90\u5931\u8d25");
            }
            PrintTemplateSchemeInfoDTO infoDTO = PrintTemplateSchemeInfoDTO.valueOf((PrintTemplateSchemeDefine)schemeDefine);
            templateSchemeDTO.setPrintTemplateSchemeInfo(infoDTO);
            DesParamLanguageDTO desParamLanguage = this.getDesParamLanguage(key, LanguageResourceType.PRINTSCHEMETITLE, PARAM_LANGUAGE_ENGLISH);
            if (desParamLanguage != null) {
                templateSchemeDTO.setDesParamLanguageDTO(desParamLanguage);
            }
        }
        catch (Exception e) {
            throw new TransferException("\u67e5\u8be2\u8d44\u6e90\u5931\u8d25", (Throwable)e);
        }
        return templateSchemeDTO;
    }

    public byte[] getFormulaSchemeBusiness(IExportContext context, String key) throws IOException, TransferException {
        FormulaSchemeDTO formulaSchemeBusinessDTO = this.getFormulaSchemeBusinessDTO(context, key);
        return formulaSchemeBusinessDTO.toBytes(objectMapper);
    }

    public FormulaSchemeDTO getFormulaSchemeBusinessDTO(IExportContext context, String key) throws IOException, TransferException {
        DesignFormulaSchemeDefine formulaScheme = this.formulaDesignTimeController.queryFormulaSchemeDefine(key);
        if (formulaScheme == null) {
            throw new TransferException("\u67e5\u8be2\u8d44\u6e90\u5931\u8d25");
        }
        FormulaSchemeInfoDTO formulaSchemeInfo = FormulaSchemeInfoDTO.valueOf((FormulaSchemeDefine)formulaScheme);
        FormulaSchemeDTO formulaSchemeDTO = new FormulaSchemeDTO();
        formulaSchemeDTO.setFormulaSchemeInfo(formulaSchemeInfo);
        List formulaConditions = this.formulaDesignTimeController.listFormulaConditionByScheme(key);
        formulaSchemeDTO.setFormulaConditions(formulaConditions.stream().map(FormulaConditionDTO::toDto).collect(Collectors.toList()));
        DesParamLanguageDTO desParamLanguage = this.getDesParamLanguage(key, LanguageResourceType.FORMULASCHEMETITLE, PARAM_LANGUAGE_ENGLISH);
        if (desParamLanguage != null) {
            formulaSchemeDTO.setDesParamLanguageDTO(desParamLanguage);
        }
        return formulaSchemeDTO;
    }

    public byte[] getFormBusiness(IExportContext context, String key) throws IOException, TransferException {
        FormDTO formDTO = this.getFormBusinessDTO(context, key);
        return formDTO.toBytes(objectMapper);
    }

    public FormDTO getFormBusinessDTO(IExportContext context, String key) throws IOException, TransferException {
        List formFoldings;
        List conditionalStyles;
        List linkMappings;
        List links;
        ArrayList<FilterTemplateDO> arrayList;
        List regionDefines;
        FormDTO formDTO = new FormDTO();
        DesignFormDefine formDefine = this.designTimeViewController.querySoftFormDefine(key);
        if (formDefine == null) {
            throw new TransferException("\u67e5\u8be2\u8d44\u6e90\u5931\u8d25");
        }
        if (formDefine.isAnalysisForm()) {
            try {
                DesignAnalysisFormParamDefine define = this.designTimeViewController.queryAnalysisFormParamDefine(formDefine.getKey());
                formDTO.setAnalysisFormDTO(new ArrayList<AnalysisFormDTO>());
                AnalysisFormDTO analysisFormDTO = new AnalysisFormDTO();
                AnalysisFormParamDefineImpl impl = new AnalysisFormParamDefineImpl();
                BeanUtils.copyProperties(define, impl);
                analysisFormDTO.setDefine(impl);
                formDTO.getAnalysisFormDTO().add(analysisFormDTO);
            }
            catch (Exception define) {
                // empty catch block
            }
        }
        FormInfoDTO formInfo = FormInfoDTO.valueOf((FormDefine)formDefine);
        byte[] fillGuide = this.designTimeViewController.getFillGuide(key);
        formInfo.setFillGuide(fillGuide);
        formDTO.setFormInfo(formInfo);
        List groupLinks = this.designTimeViewController.getFormGroupLinksByFormId(key);
        if (!CollectionUtils.isEmpty(groupLinks)) {
            List<FormGroupLinkDTO> links2 = groupLinks.stream().map(FormGroupLinkDTO::valueOf).collect(Collectors.toList());
            formDTO.setFormGroupLinks(links2);
        }
        List<Object> bigData = new ArrayList();
        try {
            bigData.addAll(this.bigDataDao.queryigDataDefines(key, "FORM_DATA"));
            if (formDefine.getFormType() == FormType.FORM_TYPE_SURVEY) {
                bigData.addAll(this.bigDataDao.queryigDataDefines(key, "BIG_SURVEY_DATA"));
            }
        }
        catch (Exception e) {
            throw new DefinitonException("\u83b7\u53d6\u8868\u6837\u5931\u8d25", (Throwable)e);
        }
        bigData = bigData.stream().filter(a -> a.getData() != null).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(bigData)) {
            ArrayList<FormStyleDTO> list = new ArrayList<FormStyleDTO>();
            formDTO.setFormStyles(list);
            for (DesignBigDataTable designBigDataTable : bigData) {
                FormStyleDTO formStyle = new FormStyleDTO();
                formStyle.setCode(designBigDataTable.getCode());
                if (designBigDataTable.getCode().equals("FORM_DATA")) {
                    Grid2Data grid2Data = Grid2Data.bytesToGrid((byte[])designBigDataTable.getData());
                    formStyle.setGrid2Data(grid2Data);
                } else {
                    formStyle.setJsonData(DesignFormDefineBigDataUtil.bytesToString((byte[])designBigDataTable.getData()));
                }
                formStyle.setFormKey(key);
                formStyle.setLanguageType(designBigDataTable.getLang());
                list.add(formStyle);
            }
        }
        if (!CollectionUtils.isEmpty(regionDefines = this.designTimeViewController.getAllRegionsInForm(key))) {
            ArrayList<ReginInfoDTO> regionList = new ArrayList<ReginInfoDTO>();
            for (Object regionDefine : regionDefines) {
                DesignRegionSettingDefine regionSetting = this.designTimeViewController.getRegionSetting(regionDefine.getRegionSettingKey());
                ReginInfoDTO reginInfoDTO = ReginInfoDTO.valueOf((DataRegionDefine)regionDefine, (RegionSettingDefine)regionSetting);
                if (null != regionSetting) {
                    try {
                        List bigDataTables = this.bigDataDao.queryigDataDefines(regionSetting.getKey(), "REGION_TAB");
                        ArrayList<RegionTabSettingDTO> containLangs = new ArrayList<RegionTabSettingDTO>();
                        if (null != bigDataTables) {
                            for (DesignBigDataTable bigDataTable : bigDataTables) {
                                if (null == bigDataTable.getData()) continue;
                                List tabSettingDefines = RegionTabSettingData.bytesToRegionTabSettingData((byte[])bigDataTable.getData());
                                for (DesignRegionTabSettingDefine tabSettingDefine : tabSettingDefines) {
                                    RegionTabSettingDTO regionTabSettingDTO = RegionTabSettingDTO.valueOf((RegionTabSettingDefine)tabSettingDefine);
                                    regionTabSettingDTO.setLanguageType(bigDataTable.getLang());
                                    containLangs.add(regionTabSettingDTO);
                                }
                            }
                        }
                        if (containLangs.size() != 0) {
                            reginInfoDTO.getRegionSetting().setRegionTabSettings(containLangs);
                        }
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                regionList.add(reginInfoDTO);
            }
            formDTO.setReginInfo(regionList);
        }
        if (!CollectionUtils.isEmpty(arrayList = new ArrayList<FilterTemplateDO>(this.getEntityViewBusiness(links = this.designTimeViewController.getAllLinksInForm(key))))) {
            formDTO.setEntityViews(arrayList.stream().map(EntityViewDTO::valueOf).collect(Collectors.toList()));
        }
        if (!CollectionUtils.isEmpty(links)) {
            formDTO.setDataLinks(links.stream().map(DataLinkDTO::valueOf).collect(Collectors.toList()));
            for (DataLinkDTO dto : formDTO.getDataLinks()) {
                FilterTemplateDTO filterTemplateDTO;
                if (dto.getEntityViewID() == null || (filterTemplateDTO = this.filterTemplateService.getFilterTemplate(dto.getEntityViewID())) == null) continue;
                dto.setFilterExpression(filterTemplateDTO.getFilterContent());
            }
        }
        if (!CollectionUtils.isEmpty(linkMappings = this.designTimeViewController.queryDataLinkMappingByFormKey(key))) {
            formDTO.setDataLinkMappings(linkMappings.stream().map(DataLinkMappingDTO::valueOf).collect(Collectors.toList()));
        }
        if (!CollectionUtils.isEmpty(conditionalStyles = this.conditionalStyleController.getAllCSInForm(key))) {
            formDTO.setConditionalStyles(conditionalStyles.stream().map(ConditionalStyleDTO::valueOf).collect(Collectors.toList()));
        }
        if (!CollectionUtils.isEmpty(formFoldings = this.formFoldingService.getByFormKey(key))) {
            formDTO.setFormFoldings(formFoldings.stream().map(FormFoldingDTO::valueOf).collect(Collectors.toList()));
        }
        List<AttachmentRuleDTO> attachments = this.attachmentService.getByFormKey(key);
        formDTO.setAttachmentRules(attachments);
        DesParamLanguageDTO desParamLanguage = this.getDesParamLanguage(key, LanguageResourceType.FORMTITLE, PARAM_LANGUAGE_ENGLISH);
        if (desParamLanguage != null) {
            formDTO.setDesParamLanguageDTO(desParamLanguage);
        }
        return formDTO;
    }

    public byte[] getFormGroupBusiness(IExportContext context, String key) throws IOException, TransferException {
        FormGroupDTO formGroupBusinessDTO = this.getFormGroupBusinessDTO(context, key);
        return formGroupBusinessDTO.toBytes(objectMapper);
    }

    public FormGroupDTO getFormGroupBusinessDTO(IExportContext context, String key) throws IOException, TransferException {
        DesignFormGroupDefine formGroupDefine = this.designTimeViewController.queryFormGroup(key);
        if (formGroupDefine == null) {
            throw new TransferException("\u67e5\u8be2\u8d44\u6e90\u5931\u8d25");
        }
        FormGroupDTO formGroupDTO = new FormGroupDTO();
        FormGroupInfoDTO formGroupInfo = FormGroupInfoDTO.valueOf((FormGroupDefine)formGroupDefine);
        formGroupDTO.setFormGroupInfo(formGroupInfo);
        DesParamLanguageDTO desParamLanguage = this.getDesParamLanguage(key, LanguageResourceType.FORMGROUPTITLE, PARAM_LANGUAGE_ENGLISH);
        if (desParamLanguage != null) {
            formGroupDTO.setDesParamLanguageDTO(desParamLanguage);
        }
        return formGroupDTO;
    }

    public byte[] getFormSchemeBusiness(IExportContext context, String key) throws Exception {
        FormSchemeDTO formSchemeBusinessDTO = this.getFormSchemeBusinessDTO(context, key);
        return formSchemeBusinessDTO.toBytes(objectMapper);
    }

    public FormSchemeDTO getFormSchemeBusinessDTO(IExportContext context, String key) throws Exception {
        List mcSchemeParams;
        DesParamLanguageDTO desParamLanguage;
        List formulaVars;
        DesignFormSchemeDefine formScheme = this.designTimeViewController.queryFormSchemeDefine(key);
        if (formScheme == null) {
            throw new TransferException("\u67e5\u8be2\u8d44\u6e90\u5931\u8d25");
        }
        FormSchemeDTO formSchemeDTO = new FormSchemeDTO();
        DesignAnalysisSchemeParamDefine designAnalysisSchemeParamDefine = this.designTimeViewController.queryAnalysisSchemeParamDefine(key);
        if (designAnalysisSchemeParamDefine != null) {
            try {
                AnalysisSchemeDTO analysisSchemeDTO = new AnalysisSchemeDTO();
                AnalysisSchemeParamDefineImpl analysis = new AnalysisSchemeParamDefineImpl();
                BeanUtils.copyProperties(designAnalysisSchemeParamDefine, analysis);
                analysisSchemeDTO.setAnalysisScheme(analysis);
                formSchemeDTO.setAnalysisSchemeDTO(analysisSchemeDTO);
            }
            catch (Exception e) {
                logger.error("\u5bfc\u51fa\u62a5\u8868\u65b9\u6848 " + key + "\u65f6\u5d4c\u5165\u5f0f\u5206\u6790\u53c2\u6570\u5bfc\u51fa\u5931\u8d25\uff1a " + e.getMessage());
            }
        }
        FormSchemeInfoDTO formSchemeInfo = FormSchemeInfoDTO.valueOf((FormSchemeDefine)formScheme);
        List taskLinkDefineList = this.designTimeViewController.queryLinksByCurrentFormScheme(key);
        formSchemeDTO.setFormSchemeInfo(formSchemeInfo);
        if (!CollectionUtils.isEmpty(taskLinkDefineList)) {
            List<TaskLinkDTO> links = taskLinkDefineList.stream().map(TaskLinkDTO::valueOf).collect(Collectors.toList());
            formSchemeDTO.setTaskLinks(links);
        }
        if (!CollectionUtils.isEmpty(formulaVars = this.designTimeViewController.queryAllFormulaVariable(key))) {
            List<FormulaVariableDTO> links = formulaVars.stream().map(FormulaVariableDTO::valueOf).collect(Collectors.toList());
            formSchemeDTO.setFormulaVariables(links);
        }
        TransformReportDefine transformReportDefine = this.designTimeViewController.exportReport(key);
        formSchemeDTO.setTransformReportDefine(transformReportDefine);
        List periods = this.designTimeViewController.querySchemePeriodLinkByScheme(key);
        if (!CollectionUtils.isEmpty(periods)) {
            List<SchemePeriodLinkDTO> links = periods.stream().map(SchemePeriodLinkDTO::valueOf).collect(Collectors.toList());
            formSchemeDTO.setPeriodLinks(links);
        }
        if ((desParamLanguage = this.getDesParamLanguage(key, LanguageResourceType.SCHEMETITLE, PARAM_LANGUAGE_ENGLISH)) != null) {
            formSchemeDTO.setDesParamLanguageDTO(desParamLanguage);
        }
        if (this.iMCParamService != null && (mcSchemeParams = this.iMCParamService.exportMCSchemeParams(key)) != null && mcSchemeParams.size() > 0) {
            ArrayList<MultCheckParamDTO> multCheckParamDTOS = new ArrayList<MultCheckParamDTO>();
            for (MCSchemeParam mcSchemeParam : mcSchemeParams) {
                MultCheckParamDTO multCheckParamDTO = MultCheckParamDTO.valueOf(mcSchemeParam);
                if (multCheckParamDTO != null) {
                    multCheckParamDTOS.add(multCheckParamDTO);
                }
                formSchemeDTO.setMultCheckParamDTOS(multCheckParamDTOS);
            }
        }
        return formSchemeDTO;
    }

    public byte[] getTaskBusiness(IExportContext context, String key) throws Exception {
        List byTask;
        DesParamLanguageDTO desParamLanguage;
        TaskDTO taskDTO;
        block12: {
            FilterTemplateDTO entityViewDefine;
            taskDTO = new TaskDTO();
            DesignTaskDefine taskDefine = this.designTimeViewController.queryTaskDefine(key);
            if (taskDefine == null) {
                throw new TransferException("\u67e5\u8be2\u8d44\u6e90\u5931\u8d25");
            }
            TaskInfoDTO taskInfo = TaskInfoDTO.valueOf((TaskDefine)taskDefine);
            if (taskInfo.getEntityViewID() != null && (entityViewDefine = this.filterTemplateService.getFilterTemplate(taskInfo.getEntityViewID())) != null) {
                taskInfo.setFilterExpression(entityViewDefine.getFilterContent());
                EntityViewDTO entityViewDTO = EntityViewDTO.valueOf(new FilterTemplateDO(entityViewDefine));
                ArrayList<EntityViewDTO> entityViewDTOS = new ArrayList<EntityViewDTO>();
                entityViewDTOS.add(entityViewDTO);
                taskDTO.setEntityViews(entityViewDTOS);
            }
            taskDTO.setTaskInfo(taskInfo);
            DesignBigDataTable runTimeBigDataTable = this.bigDataDao.queryigDataDefine(key, "FLOWSETTING");
            if (runTimeBigDataTable != null) {
                taskDTO.setTaskFlowsBigData(runTimeBigDataTable.getData());
            }
            List options = this.iTaskOptionController.getOptions(key);
            taskDTO.setTaskOptions(options);
            List periods = this.designTimeViewController.querySchemePeriodLinkByTask(key);
            if (!CollectionUtils.isEmpty(periods)) {
                List<SchemePeriodLinkDTO> links = periods.stream().map(SchemePeriodLinkDTO::valueOf).collect(Collectors.toList());
                taskDTO.setPeriodLinks(links);
            }
            if (this.taskTransfers != null) {
                try {
                    HashMap<String, byte[]> params = new HashMap<String, byte[]>(this.taskTransfers.size());
                    for (TaskTransfer taskTransfer : this.taskTransfers) {
                        byte[] data = taskTransfer.exportTaskData(context, key);
                        params.put(taskTransfer.getId(), data);
                    }
                    taskDTO.setParams(params);
                }
                catch (Exception e) {
                    Logger logger = context.getLogger();
                    if (logger == null) break block12;
                    logger.warn("\u83b7\u53d6\u4efb\u52a1\u6269\u5c55\u8d44\u6e90\u5931\u8d25,\u8df3\u8fc7", e);
                }
            }
        }
        List filters = this.designTimeViewController.getDimensionFilterByTaskKey(key);
        taskDTO.setDimensionFilters(DimensionFilterDTO.convertDTO(filters));
        List formulaConditions = this.formulaDesignTimeController.listFormulaConditionByTask(key);
        if (!CollectionUtils.isEmpty(formulaConditions)) {
            taskDTO.setFormulaConditions(formulaConditions.stream().map(FormulaConditionDTO::toDto).collect(Collectors.toList()));
        }
        if ((desParamLanguage = this.getDesParamLanguage(key, LanguageResourceType.TASKTITLE, PARAM_LANGUAGE_ENGLISH)) != null) {
            taskDTO.setDesParamLanguageDTO(desParamLanguage);
        }
        if (this.taskOrgLinkService != null && !CollectionUtils.isEmpty(byTask = this.taskOrgLinkService.getByTask(key))) {
            ArrayList<TaskOrgLinkDTO> taskOrgLinkDTOs = new ArrayList<TaskOrgLinkDTO>();
            for (TaskOrgLinkDefine taskOrgLinkDefine : byTask) {
                TaskOrgLinkDTO taskOrgLinkDTO = TaskOrgLinkDTO.valueOf(taskOrgLinkDefine);
                taskOrgLinkDTOs.add(taskOrgLinkDTO);
            }
            taskDTO.setTaskOrgLinkDTOs(taskOrgLinkDTOs);
        }
        return taskDTO.toBytes(objectMapper);
    }

    public byte[] getTaskGroupBusiness(IExportContext context, String key) throws IOException, TransferException {
        DesignTaskGroupDefine taskGroupDefine = this.designTimeViewController.queryTaskGroupDefine(key);
        if (taskGroupDefine == null) {
            throw new TransferException("\u67e5\u8be2\u8d44\u6e90\u5931\u8d25");
        }
        TaskGroupDTO taskGroupDTO = TaskGroupDTO.valueOf(taskGroupDefine);
        List links = this.designTimeViewController.getGroupLinkByGroupKey(key);
        if (!CollectionUtils.isEmpty(links)) {
            List<String> taskKeys = links.stream().map(DesignTaskGroupLink::getTaskKey).collect(Collectors.toList());
            taskGroupDTO.setTaskKeys(taskKeys);
        }
        return taskGroupDTO.toBytes(objectMapper);
    }

    public void importMappingDefineBusiness(IImportContext context, String key, byte[] bytes) throws TransferException {
        try {
            SingleMappingDTO singleMappingDTO = SingleMappingDTO.valueOf(bytes, objectMapper);
            this.importMappingDefineBusinessObj(context, key, singleMappingDTO);
        }
        catch (IOException e) {
            throw new TransferException("\u5bfc\u5165\u8d44\u6e90\u5931\u8d25", (Throwable)e);
        }
    }

    public void importMappingDefineBusinessObj(IImportContext context, String key, SingleMappingDTO singleMappingDTO) throws TransferException {
        this.getSingleMappingDTOMessage(singleMappingDTO != null, key);
        if (singleMappingDTO != null) {
            ISingleMappingConfig singleMappingConfig = singleMappingDTO.getSingleMappingConfig();
            if (singleMappingConfig != null && StringUtils.hasLength(singleMappingConfig.getSchemeKey())) {
                String schemeKey = singleMappingConfig.getSchemeKey();
                this.importPropertyMessage(TransferNodeType.MAPPING_DEFINE.getTitle(), "singleMappingConfig\u7684\u83b7\u53d6\u62a5\u8868\u65b9\u6848\u7684key", schemeKey);
                if (this.designTimeViewController.queryFormSchemeDefine(schemeKey) == null) {
                    throw new TransferException("\u5bfc\u5165\u8d44\u6e90\u5931\u8d25\uff1a\u672a\u627e\u5230\u62a5\u8868\u65b9\u6848");
                }
                this.singleMappingService.setMapping(singleMappingDTO);
            } else {
                String message = "";
                if (singleMappingDTO.getConfigInfo() != null) {
                    message = "\u4efb\u52a1key\u662f\uff1a " + singleMappingDTO.getConfigInfo().getTaskKey() + " \uff0c\u6620\u5c04\u65b9\u6848\u540d\u79f0\u662f\uff1a " + singleMappingDTO.getConfigInfo().getConfigName();
                }
                logger.error(String.format("\u53c2\u6570\u5bfc\u5165\uff0c\u4e3b\u952ekey\u4e3a %s \u7684\u6620\u5c04\u65b9\u6848\u5bfc\u5165\u5931\u8d25\uff0c \u5176 %s ", key, message));
            }
        }
    }

    public void importPrintTemplateBusiness(IImportContext context, String key, byte[] bytes) throws IOException, TransferException {
        PrintTemplateDTO printTemplateDTO = PrintTemplateDTO.valueOf(bytes, objectMapper);
        this.importPrintTemplateBusinessObj(context, key, printTemplateDTO, true);
    }

    public void importPrintTemplateBusinessObj(IImportContext context, String key, PrintTemplateDTO printTemplateDTO, boolean isSetLevel) throws TransferException {
        String printSchemeKey = printTemplateDTO.getPrintSchemeKey();
        String formKey = printTemplateDTO.getFormKey();
        this.getMessage(TransferNodeType.PRINT_TEMPLATE.getTitle(), key, printTemplateDTO.getKey(), printTemplateDTO.getTitle());
        DesignFormDefine formDefine = this.designTimeViewController.querySoftFormDefine(formKey);
        if (formDefine == null) {
            throw new TransferException("\u5bfc\u5165\u8d44\u6e90\u5931\u8d25\uff1a\u672a\u627e\u5230\u6253\u5370\u6a21\u7248\u6240\u5c5e\u8868\u5355");
        }
        try {
            DesignPrintTemplateSchemeDefine oldScheme = this.printDesignTimeController.queryPrintTemplateSchemeDefine(printSchemeKey);
            if (oldScheme == null) {
                throw new TransferException("\u5bfc\u5165\u8d44\u6e90\u5931\u8d25\uff1a\u672a\u627e\u5230\u4e0a\u7ea7\u6253\u5370\u65b9\u6848");
            }
            DesignPrintTemplateDefine printTemplateDefine = this.printDesignTimeController.createPrintTemplateDefine();
            this.printDesignTimeController.deletePrintTemplateDefine(printTemplateDTO.getPrintSchemeKey(), new String[]{printTemplateDTO.getFormKey()});
            boolean openParamLevel = this.paramLevelManager.isOpenParamLevel();
            if (openParamLevel && isSetLevel && !this.isSet(printTemplateDTO.getOwnerLevelAndId())) {
                String ownerLevelAndId = String.valueOf(context.getSrcPacketLevel());
                printTemplateDTO.setOwnerLevelAndId(ownerLevelAndId);
                this.setOwnerLevelAndIdDebugMessage(TransferNodeType.PRINT_TEMPLATE.getTitle(), ownerLevelAndId);
            }
            printTemplateDTO.value2Define(printTemplateDefine);
            this.printDesignTimeController.insertPrintTemplateDefine(printTemplateDefine);
        }
        catch (TransferException e) {
            throw e;
        }
        catch (Exception e) {
            throw new TransferException(e.getMessage(), (Throwable)e);
        }
    }

    public void importPrintComTemBusiness(IImportContext context, String key, byte[] bytes) throws IOException, TransferException {
        PrintComTemDTO dto = PrintComTemDTO.valueOf(bytes, objectMapper);
        this.importPrintComTemBusiness(context, key, dto, true);
    }

    public void importPrintComTemBusiness(IImportContext context, String key, PrintComTemDTO dto, boolean isSetLevel) throws TransferException {
        this.getMessage(TransferNodeType.PRINT_SETTING.getTitle(), key, dto.getKey(), dto.getTitle());
        DesignPrintTemplateSchemeDefine printScheme = this.designTimePrintController.getPrintTemplateScheme(dto.getPrintSchemeKey());
        if (null == printScheme) {
            throw new TransferException("\u5bfc\u5165\u8d44\u6e90\u5931\u8d25\uff1a\u672a\u627e\u5230\u6240\u5c5e\u6253\u5370\u65b9\u6848");
        }
        boolean openParamLevel = this.paramLevelManager.isOpenParamLevel();
        DesignPrintComTemDefine define = this.designTimePrintController.initPrintComTem();
        dto.value2Define(define);
        if (openParamLevel && isSetLevel && !this.isSet(printScheme.getOwnerLevelAndId())) {
            dto.setOwnerLevelAndId(String.valueOf(context.getSrcPacketLevel()));
        }
        this.designTimePrintController.deletePrintComTem(define.getKey());
        this.designTimePrintController.insertPrintComTem(define);
    }

    public void importPrintSettingBusiness(IImportContext context, String key, byte[] bytes) throws IOException, TransferException {
        PrintSettingDTO dto = PrintSettingDTO.valueOf(bytes, objectMapper);
        this.importPrintSettingBusinessObj(context, key, dto, true);
    }

    public void importPrintSettingBusinessObj(IImportContext context, String key, PrintSettingDTO printSettingDTO, boolean isSetLevel) throws TransferException {
        String[] args = FormulaGuidParse.parseKey(key);
        String printSchemeKey = args[0];
        String formKey = args[1];
        this.getMessage(TransferNodeType.PRINT_SETTING.getTitle(), "printSchemeKey", printSchemeKey, "formKey", formKey);
        DesignFormDefine form = this.designTimeViewController.querySoftFormDefine(formKey);
        if (null == form) {
            throw new TransferException("\u5bfc\u5165\u8d44\u6e90\u5931\u8d25\uff1a\u672a\u627e\u5230\u6240\u5c5e\u8868\u5355");
        }
        DesignPrintTemplateSchemeDefine printScheme = this.designTimePrintController.getPrintTemplateScheme(printSchemeKey);
        if (null == printScheme) {
            throw new TransferException("\u5bfc\u5165\u8d44\u6e90\u5931\u8d25\uff1a\u672a\u627e\u5230\u6240\u5c5e\u6253\u5370\u65b9\u6848");
        }
        boolean openParamLevel = this.paramLevelManager.isOpenParamLevel();
        DesignPrintSettingDefine setting = this.designTimePrintController.createDesignPrintSettingDefine();
        printSettingDTO.value2Define(setting);
        if (openParamLevel && isSetLevel && !this.isSet(printSettingDTO.getOwnerLevelAndId())) {
            setting.setOwnerLevelAndId(String.valueOf(context.getSrcPacketLevel()));
        }
        this.designTimePrintController.deletePrintSettingDefine(printSchemeKey, formKey);
        this.designTimePrintController.insertPrintSettingDefine(setting);
    }

    public void importFormulaFormBusiness(IImportContext context, String key, byte[] bytes) throws IOException, TransferException {
        FormulaSchemeDTO formulaSchemeDTO = FormulaSchemeDTO.valueOf(bytes, objectMapper);
        this.importFormulaFormBusinessObj(context, key, formulaSchemeDTO, true);
    }

    /*
     * Unable to fully structure code
     * Could not resolve type clashes
     */
    public void importFormulaFormBusinessObj(IImportContext context, String key, FormulaSchemeDTO formulaSchemeDTO, boolean isSetLevel) throws TransferException {
        collectImportDetail = context.isCollectImportDetail();
        args = FormulaGuidParse.parseKey(key);
        formulaSchemeKey = args[0];
        formKey = args[1];
        deleteFormulaKeys /* !! */  = new HashSet<String>();
        designFormulaSchemeDefine = this.formulaDesignTimeController.queryFormulaSchemeDefine(formulaSchemeKey);
        if (designFormulaSchemeDefine == null) {
            throw new TransferException("\u5bfc\u5165\u8d44\u6e90\u5931\u8d25\uff1a\u672a\u627e\u5230\u4e0a\u7ea7\u516c\u5f0f\u65b9\u6848");
        }
        this.getMessage(TransferNodeType.FORMULA_FORM.getTitle(), "formulaSchemeKey", formulaSchemeKey, "formKey", formKey);
        formTitle = "";
        if (!FormulaGuidParse.INTER_TABLE_FORMULA_KEY.equals(formKey)) {
            formDefine = this.designTimeViewController.querySoftFormDefine(formKey);
            if (formDefine == null) {
                throw new TransferException("\u5bfc\u5165\u8d44\u6e90\u5931\u8d25\uff1a\u672a\u627e\u5230\u516c\u5f0f\u6240\u5c5e\u8868\u5355");
            }
            formTitle = formDefine.getTitle();
        } else {
            formKey = null;
            formTitle = "\u8868\u95f4\u516c\u5f0f";
        }
        allFormulasInForm = new ArrayList<E>();
        openParamLevel = this.paramLevelManager.isOpenParamLevel();
        formulas = formulaSchemeDTO.getFormulas();
        this.importPropertyNumMessage(TransferNodeType.FORMULA_FORM.getTitle(), "\u8868\u5355\u4e0b\u516c\u5f0fformulas", formulas != null ? formulas.size() : 0);
        if (null != this.checkImportService) {
            if (collectImportDetail) {
                try {
                    allFormulasInForm = this.formulaDesignTimeController.getAllFormulasInForm(formulaSchemeKey, formKey);
                }
                catch (Exception e) {
                    throw new TransferException(e.getMessage(), (Throwable)e);
                }
            }
            if (null != formulas) {
                checkResult = this.checkImportService.checkImportFormula(formulas, formulaSchemeKey, formKey);
                if (null == checkResult || formulas.size() == checkResult.size()) {
                    this.importLinkMessage("\u8981\u5bfc\u5165\u7684\u516c\u5f0f\u5148\u5220\u96641\uff0c\u5220\u9664\u7684", formulas.stream().map((Function<FormulaDTO, String>)LambdaMetafactory.metafactory(null, null, null, (Ljava/lang/Object;)Ljava/lang/Object;, getKey(), (Lcom/jiuqi/nr/param/transfer/definition/dto/formula/FormulaDTO;)Ljava/lang/String;)()).collect(Collectors.toList()));
                    this.deleteFormulaByKeys(formulas);
                    deleteFormulaKeys /* !! */  = formulas.stream().map((Function<FormulaDTO, String>)LambdaMetafactory.metafactory(null, null, null, (Ljava/lang/Object;)Ljava/lang/Object;, lambda$importFormulaFormBusinessObj$5(com.jiuqi.nr.param.transfer.definition.dto.formula.FormulaDTO ), (Lcom/jiuqi/nr/param/transfer/definition/dto/formula/FormulaDTO;)Ljava/lang/String;)()).collect(Collectors.toSet());
                } else {
                    DefinitionModelTransfer.logger.info("\u5916\u90e8\u63a5\u53e3\u5224\u65ad\u516c\u5f0f\u662f\u5426\u5141\u8bb8\u5bfc\u5165:" + this.checkImportService.getClass());
                    formulas = checkResult;
                    deleteKeys = formulas.stream().map((Function<FormulaDTO, String>)LambdaMetafactory.metafactory(null, null, null, (Ljava/lang/Object;)Ljava/lang/Object;, lambda$importFormulaFormBusinessObj$6(com.jiuqi.nr.param.transfer.definition.dto.formula.FormulaDTO ), (Lcom/jiuqi/nr/param/transfer/definition/dto/formula/FormulaDTO;)Ljava/lang/String;)()).collect(Collectors.toList()).toArray(new String[0]);
                    this.importLinkMessage("\u8981\u5bfc\u5165\u7684\u516c\u5f0f\u5148\u5220\u96642\uff0c\u5220\u9664\u7684", formulas.stream().map((Function<FormulaDTO, String>)LambdaMetafactory.metafactory(null, null, null, (Ljava/lang/Object;)Ljava/lang/Object;, getKey(), (Lcom/jiuqi/nr/param/transfer/definition/dto/formula/FormulaDTO;)Ljava/lang/String;)()).collect(Collectors.toList()));
                    this.formulaDesignTimeController.deleteFormulaDefines(deleteKeys);
                    deleteFormulaKeys /* !! */  = formulas.stream().map((Function<FormulaDTO, String>)LambdaMetafactory.metafactory(null, null, null, (Ljava/lang/Object;)Ljava/lang/Object;, lambda$importFormulaFormBusinessObj$7(com.jiuqi.nr.param.transfer.definition.dto.formula.FormulaDTO ), (Lcom/jiuqi/nr/param/transfer/definition/dto/formula/FormulaDTO;)Ljava/lang/String;)()).collect(Collectors.toSet());
                }
            }
        } else if (openParamLevel) {
            try {
                allFormulasInForm = this.formulaDesignTimeController.getAllFormulasInForm(formulaSchemeKey, formKey);
                hasLevel = allFormulasInForm.stream().filter((Predicate<DesignFormulaDefine>)LambdaMetafactory.metafactory(null, null, null, (Ljava/lang/Object;)Z, lambda$importFormulaFormBusinessObj$8(com.jiuqi.nr.definition.facade.DesignFormulaDefine ), (Lcom/jiuqi/nr/definition/facade/DesignFormulaDefine;)Z)((DefinitionModelTransfer)this)).collect(Collectors.toList());
                deleteKeys = new HashSet<E>();
                if (!CollectionUtils.isEmpty(formulas)) {
                    srcFormulaCode = new HashSet<E>();
                    formulas.forEach((Consumer<FormulaDTO>)LambdaMetafactory.metafactory(null, null, null, (Ljava/lang/Object;)V, lambda$importFormulaFormBusinessObj$9(java.util.Set java.util.Set com.jiuqi.nr.param.transfer.definition.dto.formula.FormulaDTO ), (Lcom/jiuqi/nr/param/transfer/definition/dto/formula/FormulaDTO;)V)((Set)deleteKeys, srcFormulaCode));
                    sameCodeFormulaKey = allFormulasInForm.stream().filter((Predicate<DesignFormulaDefine>)LambdaMetafactory.metafactory(null, null, null, (Ljava/lang/Object;)Z, lambda$importFormulaFormBusinessObj$10(java.util.Set com.jiuqi.nr.definition.facade.DesignFormulaDefine ), (Lcom/jiuqi/nr/definition/facade/DesignFormulaDefine;)Z)(srcFormulaCode)).map((Function<DesignFormulaDefine, String>)LambdaMetafactory.metafactory(null, null, null, (Ljava/lang/Object;)Ljava/lang/Object;, getKey(), (Lcom/jiuqi/nr/definition/facade/DesignFormulaDefine;)Ljava/lang/String;)()).collect(Collectors.toList());
                    deleteKeys.addAll(sameCodeFormulaKey);
                }
                if (!CollectionUtils.isEmpty(hasLevel)) {
                    hasLevel.forEach((Consumer<DesignFormulaDefine>)LambdaMetafactory.metafactory(null, null, null, (Ljava/lang/Object;)V, lambda$importFormulaFormBusinessObj$11(java.util.Set com.jiuqi.nr.definition.facade.DesignFormulaDefine ), (Lcom/jiuqi/nr/definition/facade/DesignFormulaDefine;)V)((Set)deleteKeys));
                }
                if (deleteKeys.isEmpty()) ** GOTO lbl75
                array = deleteKeys.toArray(new String[0]);
                this.importLinkMessage("\u8981\u5bfc\u5165\u7684\u516c\u5f0f\u5148\u5220\u96643\uff0c\u5220\u9664\u7684", (Collection)deleteKeys);
                this.formulaDesignTimeController.deleteFormulaDefines(array);
                deleteFormulaKeys /* !! */ .addAll((Collection<String>)deleteKeys);
            }
            catch (Exception e) {
                throw new TransferException("\u5bfc\u5165\u8d44\u6e90\u5931\u8d25\uff1a\u4e0b\u7ea7\u8d44\u6e90\u5220\u9664\u5931\u8d25", (Throwable)e);
            }
        } else {
            if (collectImportDetail) {
                try {
                    allFormulasInForm = this.formulaDesignTimeController.getAllFormulasInForm(formulaSchemeKey, formKey);
                    deleteFormulaKeys /* !! */  = allFormulasInForm.stream().map((Function<DesignFormulaDefine, String>)LambdaMetafactory.metafactory(null, null, null, (Ljava/lang/Object;)Ljava/lang/Object;, lambda$importFormulaFormBusinessObj$12(com.jiuqi.nr.definition.facade.DesignFormulaDefine ), (Lcom/jiuqi/nr/definition/facade/DesignFormulaDefine;)Ljava/lang/String;)()).collect(Collectors.toSet());
                }
                catch (Exception e) {
                    throw new TransferException(e.getMessage(), (Throwable)e);
                }
            }
            this.deleteFormula(formulaSchemeKey, formKey);
            this.formulaDesignTimeController.deleteFormulaDefinesByFormInScheme(formulaSchemeKey, formKey);
        }
lbl75:
        // 5 sources

        if (collectImportDetail) {
            this.setFormulaChange(allFormulasInForm, formulas, deleteFormulaKeys /* !! */ , formulaSchemeKey, designFormulaSchemeDefine.getTitle(), formKey, formTitle);
        }
        this.importPropertyNumMessage(TransferNodeType.FORMULA_FORM.getTitle(), "\u8868\u5355\u4e0b\u516c\u5f0fformulas\u8fc7\u6ee4\u540e", formulas != null ? formulas.size() : 0);
        if (formulas != null) {
            list = new ArrayList<DesignFormulaDefine>();
            for (FormulaDTO formula : formulas) {
                formulaDefine = this.formulaDesignTimeController.createFormulaDefine();
                formula.value2Define(formulaDefine);
                if (openParamLevel && isSetLevel && !this.isSet(formulaDefine.getOwnerLevelAndId())) {
                    formulaDefine.setOwnerLevelAndId(String.valueOf(context.getSrcPacketLevel()));
                }
                list.add(formulaDefine);
            }
            try {
                this.formulaDesignTimeController.insertFormulasNotAnalysis(list.toArray(new DesignFormulaDefine[0]));
            }
            catch (Exception e) {
                throw new TransferException(e.getMessage(), (Throwable)e);
            }
            if (context.isImportMultiLanguage() && (formulaLanguageMaps = formulas.stream().filter((Predicate<FormulaDTO>)LambdaMetafactory.metafactory(null, null, null, (Ljava/lang/Object;)Z, lambda$importFormulaFormBusinessObj$13(com.jiuqi.nr.param.transfer.definition.dto.formula.FormulaDTO ), (Lcom/jiuqi/nr/param/transfer/definition/dto/formula/FormulaDTO;)Z)()).collect(Collectors.toMap((Function<FormulaDTO, String>)LambdaMetafactory.metafactory(null, null, null, (Ljava/lang/Object;)Ljava/lang/Object;, getKey(), (Lcom/jiuqi/nr/param/transfer/definition/dto/formula/FormulaDTO;)Ljava/lang/String;)(), (Function<FormulaDTO, String>)LambdaMetafactory.metafactory(null, null, null, (Ljava/lang/Object;)Ljava/lang/Object;, getLanguageInfo(), (Lcom/jiuqi/nr/param/transfer/definition/dto/formula/FormulaDTO;)Ljava/lang/String;)(), (BinaryOperator)LambdaMetafactory.metafactory(null, null, null, (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;, lambda$importFormulaFormBusinessObj$14(java.lang.String java.lang.String ), (Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;)()))).size() > 0) {
                try {
                    this.setFormulaLanguage(formulaLanguageMaps);
                }
                catch (Exception e) {
                    throw new TransferException(key + " \u7684\u62a5\u8868\u516c\u5f0f\u591a\u8bed\u8a00\u5bfc\u5165\u5931\u8d25\uff1a" + e.getMessage(), (Throwable)e);
                }
            }
        }
        if (!CollectionUtils.isEmpty(formulaConditionLinks = formulaSchemeDTO.getFormulaConditionLinks())) {
            links = new ArrayList<DesignFormulaConditionLink>();
            for (FormulaConditionLinkDTO linkDTO : formulaConditionLinks) {
                link = this.formulaDesignTimeController.initDesignFormulaConditionLink();
                linkDTO.toDefine(link);
                links.add(link);
            }
            try {
                this.formulaDesignTimeController.insertFormulaConditionLinks(links);
            }
            catch (Exception e) {
                throw new TransferException(e.getMessage(), (Throwable)e);
            }
        }
    }

    private void setFormulaChange(List<DesignFormulaDefine> allFormulasInForm, List<FormulaDTO> formulas, Set<String> deleteFormulaKeys, String formulaSchemeKey, String formulaSchemeTitle, String formKey, String formTitle) {
        Map<String, FormulaDefine> allFormulasInFormMaps = allFormulasInForm.stream().collect(Collectors.toMap(IBaseMetaItem::getKey, a -> a, (k1, k2) -> k1));
        ArrayList<FormulaDTO> updateFormulas = new ArrayList<FormulaDTO>();
        ArrayList<FormulaDTO> addFormulas = new ArrayList<FormulaDTO>();
        ArrayList<FormulaDefine> deleteFormulas = new ArrayList<FormulaDefine>();
        if (formulas != null) {
            for (FormulaDTO formulaDTO : formulas) {
                if (deleteFormulaKeys.contains(formulaDTO.getKey()) && allFormulasInFormMaps.get(formulaDTO.getKey()) != null) {
                    FormulaDefine thenformulaDefine = allFormulasInFormMaps.get(formulaDTO.getKey());
                    if (formulaDTO.getUpdateTime().compareTo(thenformulaDefine.getUpdateTime()) <= 0) continue;
                    updateFormulas.add(formulaDTO);
                    continue;
                }
                addFormulas.add(formulaDTO);
            }
        }
        HashSet addFormulaKeys = formulas != null ? formulas.stream().map(e -> e.getKey()).collect(Collectors.toSet()) : new HashSet();
        for (String deleteFormulaKey : deleteFormulaKeys) {
            FormulaDefine formulaDefine;
            if (addFormulaKeys.contains(deleteFormulaKey) || (formulaDefine = allFormulasInFormMaps.get(deleteFormulaKey)) == null) continue;
            deleteFormulas.add(formulaDefine);
        }
        if (!(CollectionUtils.isEmpty(updateFormulas) && CollectionUtils.isEmpty(addFormulas) && CollectionUtils.isEmpty(deleteFormulas))) {
            FormulaChangeObj formulaChangeObj = this.getFormChangeFormulaObj(formulaSchemeKey, formulaSchemeTitle, formKey, formTitle, addFormulas, updateFormulas, deleteFormulas);
            this.formChangeFormulaObjs.add(formulaChangeObj);
        }
    }

    public FormulaChangeObj getFormChangeFormulaObj(String formulaSchemeKey, String formulaSchemeTitle, String formKey, String formTitle, List<FormulaDTO> addFormulas, List<FormulaDTO> updateFormulas, List<FormulaDefine> deleteFormulas) {
        FormulaChangeObj result = new FormulaChangeObj();
        result.setFormulaSchemeKey(formulaSchemeKey);
        result.setFormulaSchemeTitle(formulaSchemeTitle);
        result.setFormKey(formKey);
        result.setFormTitle(formTitle);
        ArrayList<ChangeObj> addFormulaObjs = new ArrayList<ChangeObj>();
        ArrayList<ChangeObj> updateFormulaObjs = new ArrayList<ChangeObj>();
        ArrayList<ChangeObj> deleteFormulaObjs = new ArrayList<ChangeObj>();
        for (FormulaDTO addFormula : addFormulas) {
            addFormulaObjs.add(ChangeObj.getChangeObj(addFormula, S_NOT_LEVEL));
        }
        result.setAddFormulas(addFormulaObjs);
        for (FormulaDTO updateFormula : updateFormulas) {
            updateFormulaObjs.add(ChangeObj.getChangeObj(updateFormula, "1"));
        }
        result.setUpdateFormulas(updateFormulaObjs);
        for (FormulaDefine deleteFormula : deleteFormulas) {
            deleteFormulaObjs.add(ChangeObj.getChangeObj(deleteFormula, PARAM_LANGUAGE_ENGLISH));
        }
        result.setDeleteFormulas(deleteFormulaObjs);
        return result;
    }

    private void deleteFormulaByKeys(List<FormulaDTO> formulas) throws TransferException {
        List<String> deleteKeys = formulas.stream().map(e -> e.getKey()).collect(Collectors.toList());
        List<List<String>> lists = this.splitList(deleteKeys);
        for (List<String> list : lists) {
            if (null == list || list.size() == 0) continue;
            this.formulaDesignTimeController.deleteFormulaDefines((String[])list.stream().toArray(String[]::new));
        }
    }

    public List<List<String>> splitList(final List<String> list) {
        if (list.size() <= 500) {
            return new ArrayList<List<String>>(){
                {
                    this.add(list);
                }
            };
        }
        ArrayList<List<String>> rsList = new ArrayList<List<String>>();
        int maxStep = 0;
        maxStep = list.size() % 500 == 0 ? list.size() / 500 : list.size() / 500 + 1;
        for (int i = 0; i < maxStep; ++i) {
            if (i != list.size() / 500) {
                rsList.add(list.subList(500 * i, (i + 1) * 500));
                continue;
            }
            rsList.add(list.subList(500 * i, list.size()));
        }
        return rsList;
    }

    public void importPrintSchemeBusiness(IImportContext context, String key, byte[] bytes) throws IOException, TransferException {
        PrintTemplateSchemeDTO printTemplateSchemeDTO = PrintTemplateSchemeDTO.valueOf(bytes, objectMapper);
        this.importPrintSchemeBusinessObj(context, key, printTemplateSchemeDTO, true);
    }

    public void importPrintSchemeBusinessObj(IImportContext context, String key, PrintTemplateSchemeDTO printTemplateSchemeDTO, boolean isSetLevel) throws TransferException {
        PrintTemplateSchemeInfoDTO printTemplateSchemeInfo = printTemplateSchemeDTO.getPrintTemplateSchemeInfo();
        String formSchemeKey = printTemplateSchemeInfo.getFormSchemeKey();
        this.getMessage(TransferNodeType.PRINT_SCHEME.getTitle(), key, printTemplateSchemeInfo.getKey(), printTemplateSchemeInfo.getTitle());
        if (this.designTimeViewController.queryFormSchemeDefine(formSchemeKey) == null) {
            throw new TransferException("\u5bfc\u5165\u8d44\u6e90\u5931\u8d25\uff1a\u672a\u627e\u5230\u62a5\u8868\u65b9\u6848");
        }
        try {
            DesignPrintTemplateSchemeDefine oldDefine;
            boolean openParamLevel = this.paramLevelManager.isOpenParamLevel();
            if (openParamLevel && isSetLevel && !this.isSet(printTemplateSchemeInfo.getOwnerLevelAndId())) {
                String ownerLevelAndId = String.valueOf(context.getSrcPacketLevel());
                printTemplateSchemeInfo.setOwnerLevelAndId(ownerLevelAndId);
                this.setOwnerLevelAndIdDebugMessage(TransferNodeType.PRINT_SCHEME.getTitle(), ownerLevelAndId);
            }
            this.oldResourceExistMessage((oldDefine = this.printDesignTimeController.queryPrintTemplateSchemeDefine(key)) != null, TransferNodeType.PRINT_SCHEME.getTitle(), oldDefine != null ? oldDefine.getTitle() : "");
            DesignPrintTemplateSchemeDefine printTemplateSchemeDefine = this.printDesignTimeController.createPrintTemplateSchemeDefine();
            printTemplateSchemeInfo.valueOf(printTemplateSchemeDefine);
            if (oldDefine == null) {
                this.printDesignTimeController.insertPrintTemplateSchemeDefine(printTemplateSchemeDefine);
            } else {
                this.printDesignTimeController.updatePrintTemplateSchemeDefine(printTemplateSchemeDefine);
            }
            if (null != printTemplateSchemeInfo.getCommonTemplate()) {
                DesignPrintComTemDefine common = this.designTimePrintController.initPrintComTem();
                common.setKey(printTemplateSchemeInfo.getKey());
                common.setPrintSchemeKey(printTemplateSchemeInfo.getKey());
                common.setCode("DEFAULT");
                common.setTitle("\u9ed8\u8ba4\u6bcd\u7248");
                common.setTemplateData(printTemplateSchemeInfo.getCommonTemplate());
                if (null == oldDefine) {
                    this.designTimePrintController.insertPrintComTem(common);
                } else {
                    this.designTimePrintController.updatePrintComTem(common);
                }
            }
        }
        catch (DesignCheckException e) {
            throw new TransferException(e.getMessage(), (Throwable)e);
        }
        catch (Exception e) {
            throw new TransferException("\u5bfc\u5165\u8d44\u6e90\u5931\u8d25", (Throwable)e);
        }
        DesParamLanguageDTO desParamLanguageDTO = printTemplateSchemeDTO.getDesParamLanguageDTO();
        if (context.isImportMultiLanguage() && desParamLanguageDTO != null) {
            try {
                this.setDesParamLanguage(desParamLanguageDTO);
            }
            catch (Exception e) {
                throw new TransferException(key + " \u7684\u6253\u5370\u65b9\u6848\u591a\u8bed\u8a00\u5bfc\u5165\u5931\u8d25\uff1a" + e.getMessage(), (Throwable)e);
            }
        }
    }

    public void importFormulaSchemeBusiness(IImportContext context, String key, byte[] bytes) throws IOException, TransferException {
        FormulaSchemeDTO formulaSchemeDTO = FormulaSchemeDTO.valueOf(bytes, objectMapper);
        this.importFormulaSchemeBusinessObj(context, key, formulaSchemeDTO, true);
    }

    public void importFormulaSchemeBusinessObj(IImportContext context, String key, FormulaSchemeDTO formulaSchemeDTO, boolean isSetLevel) throws TransferException {
        FormulaSchemeInfoDTO formulaSchemeInfo = formulaSchemeDTO.getFormulaSchemeInfo();
        String formSchemeKey = formulaSchemeInfo.getFormSchemeKey();
        this.getMessage(TransferNodeType.FORMULA_SCHEME.getTitle(), key, formulaSchemeInfo.getKey(), formulaSchemeInfo.getTitle());
        if (this.designTimeViewController.queryFormSchemeDefine(formSchemeKey) == null) {
            throw new TransferException("\u5bfc\u5165\u8d44\u6e90\u5931\u8d25\uff1a\u672a\u627e\u5230\u62a5\u8868\u65b9\u6848");
        }
        DesignFormulaSchemeDefine formulaSchemeDefine = this.formulaDesignTimeController.createFormulaSchemeDefine();
        boolean openParamLevel = this.paramLevelManager.isOpenParamLevel();
        if (openParamLevel && isSetLevel && !this.isSet(formulaSchemeInfo.getOwnerLevelAndId())) {
            String ownerLevelAndId = String.valueOf(context.getSrcPacketLevel());
            formulaSchemeInfo.setOwnerLevelAndId(ownerLevelAndId);
            this.setOwnerLevelAndIdDebugMessage(TransferNodeType.FORMULA_SCHEME.getTitle(), ownerLevelAndId);
        }
        formulaSchemeInfo.value2Define(formulaSchemeDefine);
        DesignFormulaSchemeDefine oldDefine = this.formulaDesignTimeController.queryFormulaSchemeDefine(key);
        this.oldResourceExistMessage(oldDefine != null, TransferNodeType.FORMULA_SCHEME.getTitle(), oldDefine != null ? oldDefine.getTitle() : "");
        try {
            if (oldDefine == null) {
                this.formulaDesignTimeController.insertFormulaSchemeDefine(formulaSchemeDefine);
            } else {
                this.formulaDesignTimeController.updateFormulaSchemeDefine(formulaSchemeDefine);
            }
        }
        catch (DesignCheckException e) {
            throw new TransferException(e.getMessage(), (Throwable)e);
        }
        List<FormulaConditionDTO> srcFormulaConditions = formulaSchemeDTO.getFormulaConditions();
        if (!CollectionUtils.isEmpty(srcFormulaConditions)) {
            List designFormulaConditions = this.formulaDesignTimeController.listFormulaConditionByKey(srcFormulaConditions.stream().map(FormulaConditionDTO::getKey).collect(Collectors.toList()));
            ArrayList<DesignFormulaCondition> updateFormulaConditions = new ArrayList<DesignFormulaCondition>();
            Map<String, DesignFormulaCondition> formulaConditionMap = designFormulaConditions.stream().collect(Collectors.toMap(IBaseMetaItem::getKey, a -> a));
            for (FormulaConditionDTO srcFormulaCondition : srcFormulaConditions) {
                DesignFormulaCondition designFormulaCondition = formulaConditionMap.get(srcFormulaCondition.getKey());
                if (designFormulaCondition != null && designFormulaCondition.getUpdateTime().equals(srcFormulaCondition.getUpdateTime())) continue;
                DesignFormulaCondition condition = this.formulaDesignTimeController.initFormulaCondition();
                srcFormulaCondition.toDefine(condition);
                updateFormulaConditions.add(condition);
            }
            try {
                if (updateFormulaConditions.size() > 0) {
                    this.formulaDesignTimeController.deleteFormulaConditions(updateFormulaConditions.stream().map(IBaseMetaItem::getKey).collect(Collectors.toList()));
                    this.formulaDesignTimeController.insertFormulaConditions(updateFormulaConditions);
                }
            }
            catch (Exception e) {
                throw new TransferException("\u516c\u5f0f\u65b9\u6848\u5bfc\u5165\u9002\u5e94\u6761\u4ef6\u51fa\u9519\uff1a " + e.getMessage(), (Throwable)e);
            }
        }
        DesParamLanguageDTO desParamLanguageDTO = formulaSchemeDTO.getDesParamLanguageDTO();
        if (context.isImportMultiLanguage() && desParamLanguageDTO != null) {
            try {
                this.setDesParamLanguage(desParamLanguageDTO);
            }
            catch (Exception e) {
                throw new TransferException(key + " \u7684\u516c\u5f0f\u65b9\u6848\u591a\u8bed\u8a00\u5bfc\u5165\u5931\u8d25\uff1a" + e.getMessage(), (Throwable)e);
            }
        }
    }

    public void importFormBusiness(IImportContext context, String key, byte[] bytes) throws IOException, TransferException {
        FormDTO formDTO = FormDTO.valueOf(bytes, objectMapper);
        this.importFormBusinessObj(context, key, formDTO, true);
    }

    public void importFormBusinessObj(IImportContext context, String key, FormDTO formDTO, boolean isSetLevel) throws TransferException {
        List<EntityViewDTO> entityViews;
        List<DataLinkDTO> dataLinks;
        List<FormStyleDTO> formStyles;
        boolean collectImportDetail = context.isCollectImportDetail();
        FormInfoDTO formInfo = formDTO.getFormInfo();
        this.getMessage(TransferNodeType.FORM.getTitle(), key, formInfo.getKey(), formInfo.getTitle());
        boolean openParamLevel = this.paramLevelManager.isOpenParamLevel();
        if (openParamLevel && isSetLevel && !this.isSet(formInfo.getOwnerLevelAndId())) {
            String ownerLevelAndId = String.valueOf(context.getSrcPacketLevel());
            formInfo.setOwnerLevelAndId(ownerLevelAndId);
            this.setOwnerLevelAndIdDebugMessage(TransferNodeType.FORM.getTitle(), ownerLevelAndId);
        }
        DesignFormDefine formDefine = this.designTimeViewController.createFormDefine();
        formInfo.value2Define(formDefine);
        ContextUser user = NpContextHolder.getContext().getUser();
        formDefine.setUpdateUser(user.getName());
        List<FormGroupLinkDTO> groupLinks = formDTO.getFormGroupLinks();
        if (groupLinks == null) {
            throw new TransferException("\u5bfc\u5165\u8d44\u6e90\u5931\u8d25\uff1a\u62a5\u8868 " + formInfo.getTitle() + "[" + formInfo.getFormCode() + "]\u8d44\u6e90\u91cc\u8868\u5355\u5206\u7ec4\u4e3a\u7a7a");
        }
        Optional first = groupLinks.stream().findFirst();
        if (first.isPresent()) {
            DesignFormGroupDefine groupDefine = this.designTimeViewController.queryFormGroup(((FormGroupLinkDTO)first.get()).getGroupKey());
            if (groupDefine == null) {
                throw new TransferException("\u5bfc\u5165\u8d44\u6e90\u5931\u8d25\uff1a\u62a5\u8868 " + formInfo.getTitle() + "[" + formInfo.getFormCode() + "]\u8d44\u6e90\u91cc\u7684\u8868\u5355\u5206\u7ec4\u5728\u5f53\u524d\u670d\u52a1\u4e0d\u5b58\u5728");
            }
        } else {
            throw new TransferException("\u5bfc\u5165\u8d44\u6e90\u5931\u8d25\uff1a\u62a5\u8868 " + formInfo.getTitle() + "[" + formInfo.getFormCode() + "]\u8d44\u6e90\u91cc\u8868\u5355\u5206\u7ec4\u4e3a\u7a7a");
        }
        DesignFormDefine designFormDefine = this.designTimeViewController.querySoftFormDefine(key);
        this.oldResourceExistMessage(designFormDefine != null, TransferNodeType.FORM.getTitle(), designFormDefine != null ? designFormDefine.getTitle() : "");
        try {
            if (designFormDefine != null) {
                if (collectImportDetail && formInfo.getUpdateTime().compareTo(formDefine.getUpdateTime()) > 0) {
                    this.addUpdateFormChangeObj(ChangeObj.getChangeObj((FormDefine)formDefine, "1"));
                }
                this.designTimeViewController.updateFormDefine(formDefine);
            } else {
                if (collectImportDetail) {
                    this.addFormChangeObj(ChangeObj.getChangeObj((FormDefine)formDefine, S_NOT_LEVEL));
                }
                this.designTimeViewController.insertFormDefine(formDefine);
            }
            byte[] fillGuide = formInfo.getFillGuide();
            this.designTimeViewController.setFillGuide(formInfo.getKey(), fillGuide);
        }
        catch (JQException | DesignCheckException e) {
            throw new TransferException(e.getMessage(), e);
        }
        if (!groupLinks.isEmpty()) {
            Object[] links = (DesignFormGroupLink[])groupLinks.stream().map(FormGroupLinkDTO::value2Define).toArray(DesignFormGroupLink[]::new);
            try {
                this.importLinkMessage("\u66f4\u65b0\u540e\u7684\u62a5\u8868\u5206\u7ec4", groupLinks.stream().map(FormGroupLinkDTO::getGroupKey).collect(Collectors.toList()));
                this.formGroupLinkDao.deleteLinkByForm(key);
                this.formGroupLinkDao.insert(links);
            }
            catch (Exception e) {
                throw new TransferException(e.getMessage(), (Throwable)e);
            }
        }
        if ((formStyles = formDTO.getFormStyles()) != null && !formStyles.isEmpty()) {
            this.importPropertyNumMessage(TransferNodeType.FORM.getTitle(), "\u8868\u6837formStyles", formStyles.size());
            for (FormStyleDTO formStyle : formStyles) {
                Grid2Data grid2Data = formStyle.getGrid2Data();
                if (!context.isImportMultiLanguage() && formStyle.getLanguageType() != LanguageType.CHINESE.getValue()) continue;
                if (grid2Data != null) {
                    if ((formInfo.getFormType() == FormType.FORM_TYPE_FMDM || formInfo.getFormType() == FormType.FORM_TYPE_NEWFMDM) && grid2Data.isColumnAutoWidth(1)) {
                        grid2Data.setColumnAutoWidth(1, false);
                    }
                    if (null != this.checkImportService && !this.checkImportService.checkImportLanguage() && formStyle.getLanguageType() != LanguageType.CHINESE.getValue()) {
                        logger.info("\u5916\u90e8\u63a5\u53e3\u5224\u65ad\u591a\u8bed\u8a00\u8868\u6837\u7981\u6b62\u5bfc\u5165:" + this.checkImportService.getClass());
                        continue;
                    }
                    this.designTimeViewController.setReportDataToFormByLanguage(formStyle.getFormKey(), Grid2Data.gridToBytes((Grid2Data)grid2Data), formStyle.getLanguageType());
                    continue;
                }
                if (!StringUtils.hasText(formStyle.getJsonData()) || "FORM_DATA".equals(formStyle.getCode())) continue;
                try {
                    byte[] bytes = DesignFormDefineBigDataUtil.StringToBytes((String)formStyle.getJsonData());
                    this.designFormDefineService.updateBigDataDefine(formStyle.getFormKey(), formStyle.getCode(), formStyle.getLanguageType(), bytes);
                }
                catch (Exception e) {
                    Log.error((Exception)e);
                }
            }
        }
        List<ReginInfoDTO> reginInfo = formDTO.getReginInfo();
        List oldRegions = this.designTimeViewController.getAllRegionsInForm(key);
        ArrayList allOriginDataLinks = new ArrayList();
        if (oldRegions != null) {
            try {
                this.importLinkMessage("\u65e7\u7684\u62a5\u8868\u4e0b\u533a\u57df\uff0c\u5176\u8981\u5220\u9664", oldRegions.stream().map(IBaseMetaItem::getKey).collect(Collectors.toList()));
                for (DesignDataRegionDefine oldRegion : oldRegions) {
                    List dataLinkDefines = this.designTimeViewController.getAllLinksInRegion(oldRegion.getKey());
                    allOriginDataLinks.addAll(dataLinkDefines);
                    this.designTimeViewController.deleteDataRegionDefine(oldRegion.getKey());
                }
            }
            catch (Exception e) {
                throw new TransferException(e.getMessage(), (Throwable)e);
            }
        }
        if (reginInfo != null) {
            this.importPropertyNumMessage(TransferNodeType.FORM.getTitle(), "\u533a\u57dfreginInfo", reginInfo.size());
            this.importLinkMessage("\u65b0\u589e\u7684\u62a5\u8868\u533a\u57df", reginInfo.stream().map(ReginInfoDTO::getKey).collect(Collectors.toList()));
            for (ReginInfoDTO reginInfoDTO : reginInfo) {
                DesignDataRegionDefine regionDefine = this.designTimeViewController.createDataRegionDefine();
                DesignRegionSettingDefine designRegionSettingDefine = reginInfoDTO.value2Define(regionDefine);
                if (openParamLevel && isSetLevel && !this.isSet(regionDefine.getOwnerLevelAndId())) {
                    regionDefine.setOwnerLevelAndId(String.valueOf(context.getSrcPacketLevel()));
                }
                this.designTimeViewController.insertDataRegionDefine(regionDefine);
                if (designRegionSettingDefine == null) continue;
                if (openParamLevel && isSetLevel && !this.isSet(designRegionSettingDefine.getOwnerLevelAndId())) {
                    designRegionSettingDefine.setOwnerLevelAndId(String.valueOf(context.getSrcPacketLevel()));
                }
                try {
                    List<RegionTabSettingDTO> regionTabSettings = reginInfoDTO.getRegionSetting().getRegionTabSettings();
                    if (!CollectionUtils.isEmpty(regionTabSettings)) {
                        HashMap hashMap = new HashMap();
                        for (RegionTabSettingDTO regionTabSetting : regionTabSettings) {
                            if (null != hashMap.get(regionTabSetting.getLanguageType())) {
                                ((List)hashMap.get(regionTabSetting.getLanguageType())).add(regionTabSetting.value2Define());
                                continue;
                            }
                            hashMap.put(regionTabSetting.getLanguageType(), new ArrayList());
                            ((List)hashMap.get(regionTabSetting.getLanguageType())).add(regionTabSetting.value2Define());
                        }
                        for (Integer integer : hashMap.keySet()) {
                            List regionTabSettingDefines;
                            if (integer == 1) {
                                regionTabSettingDefines = (List)hashMap.get(integer);
                                designRegionSettingDefine.setRegionTabSetting(regionTabSettingDefines);
                                continue;
                            }
                            if (!context.isImportMultiLanguage()) continue;
                            if (null != this.checkImportService && !this.checkImportService.checkImportLanguage()) {
                                logger.info("\u5916\u90e8\u63a5\u53e3\u5224\u65ad\u591a\u8bed\u8a00\u8868\u6837\u7981\u6b62\u5bfc\u5165:" + this.checkImportService.getClass());
                                continue;
                            }
                            regionTabSettingDefines = (List)hashMap.get(integer);
                            byte[] bytes1 = RegionTabSettingData.regionTabSettingDataToBytes((List)regionTabSettingDefines);
                            if (null == bytes1) continue;
                            DesignBigDataTable designBigDataTable = new DesignBigDataTable();
                            designBigDataTable.setKey(designRegionSettingDefine.getKey());
                            designBigDataTable.setCode("REGION_TAB");
                            designBigDataTable.setLang(integer.intValue());
                            designBigDataTable.setData(bytes1);
                            this.bigDataDao.deleteBigData(designBigDataTable.getKey(), designBigDataTable.getCode(), designBigDataTable.getLang());
                            this.bigDataDao.insert((Object)designBigDataTable);
                        }
                    }
                }
                catch (Exception e) {
                    logger.info("\u53c2\u6570\u5bfc\u5165\u5230\u5904\u591a\u8bed\u8a00\u62a5\u8868\u533a\u57df\u9875\u7b7e\u5bfc\u5165\u5931\u8d25\uff0c\u62a5\u8868key\u4e3a\uff1a{}, \u533a\u57dfkey\u4e3a\uff1a{}", (Object)formInfo.getKey(), (Object)reginInfoDTO.getKey());
                    logger.error(String.format("\u53c2\u6570\u5bfc\u5165\u5230\u5904\u591a\u8bed\u8a00\u62a5\u8868\u533a\u57df\u9875\u7b7e\u5bfc\u5165\u5931\u8d25\uff0c\u62a5\u8868key\u4e3a\uff1a%s, \u533a\u57dfkey\u4e3a\uff1a%s, ", formInfo.getKey(), reginInfoDTO.getKey()), e);
                }
                this.designTimeViewController.removeRegionSetting(designRegionSettingDefine.getKey());
                this.designTimeViewController.addRegionSetting(designRegionSettingDefine);
            }
        }
        if ((dataLinks = formDTO.getDataLinks()) != null) {
            this.importPropertyNumMessage(TransferNodeType.FORM.getTitle(), "\u6570\u636e\u94fe\u63a5dataLinks", dataLinks.size());
            this.importLinkMessage("\u65e7\u7684\u62a5\u8868\u4e0b\u94fe\u63a5", allOriginDataLinks.stream().map(IBaseMetaItem::getKey).collect(Collectors.toList()));
            Map<Object, Object> originDataLinkMap = new HashMap();
            if (allOriginDataLinks != null && allOriginDataLinks.size() > 0) {
                originDataLinkMap = allOriginDataLinks.stream().collect(Collectors.toMap(IBaseMetaItem::getKey, v -> v));
            }
            Map<String, DataLinkDTO> targetDataLinkMap = dataLinks.stream().collect(Collectors.toMap(DataLinkDTO::getKey, v -> v));
            LinkedHashSet<String> sameLinkKeys = this.judgeUpdate(originDataLinkMap.keySet(), targetDataLinkMap.keySet());
            if (!CollectionUtils.isEmpty(sameLinkKeys)) {
                for (String string : sameLinkKeys) {
                    DesignDataLinkDefine originDataLink = (DesignDataLinkDefine)originDataLinkMap.get(string);
                    DataLinkDTO targetDataLink = targetDataLinkMap.get(string);
                    this.updateLinkFilterType(originDataLink, targetDataLink);
                }
            }
            ArrayList<DesignDataLinkDefine> linkDefines = new ArrayList<DesignDataLinkDefine>();
            for (DataLinkDTO dataLink : dataLinks) {
                DesignDataLinkDefine dataLinkDefine = this.designTimeViewController.createDataLinkDefine();
                dataLink.value2Define(dataLinkDefine);
                if (openParamLevel && isSetLevel && !this.isSet(dataLinkDefine.getOwnerLevelAndId())) {
                    dataLinkDefine.setOwnerLevelAndId(String.valueOf(context.getSrcPacketLevel()));
                }
                linkDefines.add(dataLinkDefine);
            }
            this.importLinkMessage("\u65b0\u589e\u7684\u62a5\u8868\u4e0b\u94fe\u63a5", linkDefines.stream().map(IBaseMetaItem::getKey).collect(Collectors.toList()));
            this.designTimeViewController.insertDataLinkDefines(linkDefines.toArray(new DesignDataLinkDefine[0]));
        }
        if ((entityViews = formDTO.getEntityViews()) != null) {
            this.importPropertyNumMessage(TransferNodeType.FORM.getTitle(), "\u8fc7\u6ee4\u6a21\u677fentityViews", entityViews.size());
            this.insertEntityView(entityViews);
        }
        try {
            this.designTimeViewController.deleteDataLinkMapping(key);
            List<DataLinkMappingDTO> dataLinkMappings = formDTO.getDataLinkMappings();
            if (dataLinkMappings != null) {
                this.importPropertyNumMessage(TransferNodeType.FORM.getTitle(), "\u679a\u4e3e\u8054\u52a8dataLinkMappings", dataLinkMappings.size());
                for (DataLinkMappingDTO dataLinkMapping : dataLinkMappings) {
                    DesignDataLinkMappingDefine designDataLinkMappingDefine = this.designTimeViewController.createDataLinkMappingDefine();
                    dataLinkMapping.value2Define(designDataLinkMappingDefine);
                    this.designTimeViewController.insertDataLinkMappingDefine(designDataLinkMappingDefine);
                }
            }
            this.conditionalStyleController.deleteCSInForm(key);
            List<ConditionalStyleDTO> csDTOs = formDTO.getConditionalStyles();
            if (csDTOs != null) {
                ArrayList<DesignConditionalStyleImpl> result = new ArrayList<DesignConditionalStyleImpl>();
                for (ConditionalStyleDTO csDTO : csDTOs) {
                    DesignConditionalStyleImpl designConditionalStyle = new DesignConditionalStyleImpl();
                    csDTO.value2Define((DesignConditionalStyle)designConditionalStyle);
                    result.add(designConditionalStyle);
                }
                this.importPropertyNumMessage(TransferNodeType.FORM.getTitle(), "\u6761\u4ef6\u6837\u5f0fconditionalStyles", csDTOs.size());
                this.conditionalStyleController.insertCS(result);
            }
            this.formFoldingService.deleteByFormKey(key);
            List<FormFoldingDTO> formFoldings = formDTO.getFormFoldings();
            if (!CollectionUtils.isEmpty(formFoldings)) {
                ArrayList<DesignFormFoldingDefineImpl> arrayList = new ArrayList<DesignFormFoldingDefineImpl>();
                for (FormFoldingDTO formFolding : formFoldings) {
                    DesignFormFoldingDefineImpl formFoldingDefine = new DesignFormFoldingDefineImpl();
                    formFolding.value2Define((DesignFormFoldingDefine)formFoldingDefine);
                    arrayList.add(formFoldingDefine);
                }
                this.importPropertyNumMessage(TransferNodeType.FORM.getTitle(), "\u62a5\u8868\u6298\u53e0formFolding", formFoldings.size());
                this.formFoldingService.insert(arrayList.toArray(new DesignFormFoldingDefine[formFoldings.size()]));
            }
            List<AttachmentRuleDTO> list = formDTO.getAttachmentRules();
            this.attachmentService.setByAttachmentRuleDTO(list);
        }
        catch (Exception e) {
            throw new TransferException(e.getMessage(), (Throwable)e);
        }
        try {
            List<AnalysisFormDTO> analysisFormDTO;
            if (formDefine.isAnalysisForm() && null != (analysisFormDTO = formDTO.getAnalysisFormDTO()) && analysisFormDTO.size() != 0) {
                AnalysisFormDTO dto = analysisFormDTO.get(0);
                AnalysisFormParamDefineImpl define = dto.getDefine();
                this.importPropertyMessage(TransferNodeType.FORM.getTitle(), "\u5206\u6790\u53c2\u6570analysisFormDTO");
                if (null != define) {
                    this.designTimeViewController.updataAnalysisFormParamDefine(formDefine.getKey(), (DesignAnalysisFormParamDefine)define);
                }
            }
        }
        catch (Exception e) {
            throw new TransferException(e.getMessage(), (Throwable)e);
        }
        DesParamLanguageDTO desParamLanguageDTO = formDTO.getDesParamLanguageDTO();
        if (context.isImportMultiLanguage() && desParamLanguageDTO != null) {
            try {
                this.setDesParamLanguage(desParamLanguageDTO);
            }
            catch (Exception e) {
                throw new TransferException(key + " \u7684\u62a5\u8868\u591a\u8bed\u8a00\u5bfc\u5165\u5931\u8d25\uff1a" + e.getMessage(), (Throwable)e);
            }
        }
    }

    public void importFormGroupBusiness(IImportContext context, String key, byte[] bytes) throws IOException, TransferException {
        FormGroupDTO formGroupDTO = FormGroupDTO.valueOf(bytes, objectMapper);
        this.importFormGroupBusinessObj(context, key, formGroupDTO, true);
    }

    public void importFormGroupBusinessObj(IImportContext context, String key, FormGroupDTO formGroupDTO, boolean isSetLevel) throws TransferException {
        FormGroupInfoDTO formGroupInfo = formGroupDTO.getFormGroupInfo();
        this.getMessage(TransferNodeType.FORM_GROUP.getTitle(), key, formGroupInfo.getKey(), formGroupInfo.getTitle());
        DesignFormGroupDefine formGroup = this.designTimeViewController.createFormGroup();
        boolean openParamLevel = this.paramLevelManager.isOpenParamLevel();
        if (openParamLevel && isSetLevel && !this.isSet(formGroupInfo.getOwnerLevelAndId())) {
            String ownerLevelAndId = String.valueOf(context.getSrcPacketLevel());
            formGroupInfo.setOwnerLevelAndId(ownerLevelAndId);
            this.setOwnerLevelAndIdDebugMessage(TransferNodeType.FORM_GROUP.getTitle(), ownerLevelAndId);
        }
        formGroupInfo.value2Define(formGroup);
        String formSchemeKey = formGroup.getFormSchemeKey();
        if (this.designTimeViewController.queryFormSchemeDefine(formSchemeKey) == null) {
            throw new TransferException("\u5bfc\u5165\u8d44\u6e90\u5931\u8d25\uff1a\u672a\u627e\u5230\u62a5\u8868\u65b9\u6848");
        }
        DesignFormGroupDefine oldGroup = this.designTimeViewController.queryFormGroup(key);
        this.oldResourceExistMessage(oldGroup != null, TransferNodeType.FORM_GROUP.getTitle(), oldGroup != null ? oldGroup.getTitle() : "");
        try {
            if (oldGroup == null) {
                this.designTimeViewController.insertFormGroup(formGroup);
            } else {
                this.designTimeViewController.updateFormGroup(formGroup);
            }
        }
        catch (DesignCheckException e) {
            throw new TransferException(e.getMessage(), (Throwable)e);
        }
        DesParamLanguageDTO desParamLanguageDTO = formGroupDTO.getDesParamLanguageDTO();
        if (context.isImportMultiLanguage() && desParamLanguageDTO != null) {
            try {
                this.setDesParamLanguage(desParamLanguageDTO);
            }
            catch (Exception e) {
                throw new TransferException(key + " \u7684\u62a5\u8868\u5206\u7ec4\u591a\u8bed\u8a00\u5bfc\u5165\u5931\u8d25\uff1a" + e.getMessage(), (Throwable)e);
            }
        }
    }

    public void importFormSchemeBusiness(IImportContext context, String key, byte[] bytes) throws IOException, TransferException {
        FormSchemeDTO formSchemeDTO = FormSchemeDTO.valueOf(bytes, objectMapper);
        this.importFormSchemeBusinessObj(context, key, formSchemeDTO, true);
    }

    public void importFormSchemeBusinessObj(IImportContext context, String key, FormSchemeDTO formSchemeDTO, boolean isSetLevel) throws TransferException {
        List<MultCheckParamDTO> multCheckParamDTOS;
        FormSchemeInfoDTO formSchemeInfo = formSchemeDTO.getFormSchemeInfo();
        String taskKey = formSchemeInfo.getTaskKey();
        this.getMessage(TransferNodeType.FORM_SCHEME.getTitle(), key, formSchemeInfo.getKey(), formSchemeInfo.getTitle());
        DesignTaskDefine designTaskDefine = this.designTimeViewController.queryTaskDefine(taskKey);
        if (designTaskDefine == null) {
            throw new TransferException("\u5bfc\u5165\u8d44\u6e90\u5931\u8d25\uff1a\u672a\u627e\u5230\u4e0a\u7ea7\u4efb\u52a1");
        }
        boolean openParamLevel = this.paramLevelManager.isOpenParamLevel();
        if (openParamLevel && isSetLevel && !this.isSet(formSchemeInfo.getOwnerLevelAndId())) {
            String ownerLevelAndId = String.valueOf(context.getSrcPacketLevel());
            formSchemeInfo.setOwnerLevelAndId(ownerLevelAndId);
            this.setOwnerLevelAndIdDebugMessage(TransferNodeType.FORM_SCHEME.getTitle(), ownerLevelAndId);
        }
        DesignFormSchemeDefine formSchemeDefine = this.designTimeViewController.createFormSchemeDefine();
        formSchemeInfo.valueDefine(formSchemeDefine);
        DesignFormSchemeDefine schemeDefine = this.designTimeViewController.queryFormSchemeDefine(key);
        this.oldResourceExistMessage(schemeDefine != null, TransferNodeType.FORM_SCHEME.getTitle(), schemeDefine != null ? schemeDefine.getTitle() : "");
        try {
            if (schemeDefine == null) {
                this.designTimeViewController.insertFormSchemeDefine(formSchemeDefine);
            } else {
                this.designTimeViewController.updateFormSchemeDefine(formSchemeDefine);
            }
        }
        catch (DesignCheckException e) {
            throw new TransferException(e.getMessage(), (Throwable)e);
        }
        List<TaskLinkDTO> taskLinks = formSchemeDTO.getTaskLinks();
        if (!CollectionUtils.isEmpty(taskLinks)) {
            this.importPropertyNumMessage(TransferNodeType.FORM_SCHEME.getTitle(), "\u5173\u8054\u4efb\u52a1taskLinks", taskLinks.size());
            for (TaskLinkDTO taskLinkDTO : taskLinks) {
                DesignTaskLinkDefine taskLinkDefine = this.designTimeViewController.createTaskLinkDefine();
                if (openParamLevel && isSetLevel && !this.isSet(taskLinkDTO.getOwnerLevelAndId())) {
                    taskLinkDTO.setOwnerLevelAndId(String.valueOf(context.getSrcPacketLevel()));
                }
                taskLinkDTO.value2Define(taskLinkDefine);
                this.setOrgMappingRule(taskLinkDefine, designTaskDefine);
                if (!CollectionUtils.isEmpty(taskLinkDefine.getOrgMappingRules())) {
                    boolean hasEmptyOrgMappingRule = false;
                    for (TaskLinkOrgMappingRule orgMappingRule : taskLinkDefine.getOrgMappingRules()) {
                        if (StringUtils.hasLength(orgMappingRule.getSourceEntity()) && StringUtils.hasLength(orgMappingRule.getTargetEntity()) && orgMappingRule.getMatchingType() != null && StringUtils.hasLength(orgMappingRule.getOrder())) continue;
                        hasEmptyOrgMappingRule = true;
                    }
                    if (hasEmptyOrgMappingRule) continue;
                }
                this.designTimeViewController.deleteTaskLinkDefine(taskLinkDefine.getKey());
                this.designTimeViewController.insertTaskLinkDefine(taskLinkDefine);
            }
        }
        try {
            TransformReportDefine transformReportDefine;
            List formulaVars = this.designTimeViewController.queryAllFormulaVariable(key);
            for (Object formulaVar : formulaVars) {
                this.formulaDesignTimeController.deleteFormulaVariable(formulaVar.getKey());
            }
            List<FormulaVariableDTO> list = formSchemeDTO.getFormulaVariables();
            if (!CollectionUtils.isEmpty(list)) {
                this.importPropertyNumMessage(TransferNodeType.FORM_SCHEME.getTitle(), "\u516c\u5f0f\u53d8\u91cfformulaVariables", list.size());
                for (FormulaVariableDTO formulaVariable : list) {
                    if (openParamLevel && isSetLevel && !this.isSet(formulaVariable.getOwnerLevelAndId())) {
                        formulaVariable.setOwnerLevelAndId(String.valueOf(context.getSrcPacketLevel()));
                    }
                    this.formulaDesignTimeController.addFormulaVariable(formulaVariable.value2Define());
                }
            }
            if ((transformReportDefine = formSchemeDTO.getTransformReportDefine()) == null) {
                this.designTimeViewController.deleteReport(key);
            } else {
                this.importPropertyMessage(TransferNodeType.FORM_SCHEME.getTitle(), "\u62a5\u544a\u76f8\u5173\u5c5e\u6027transformReportDefine");
                this.designTimeViewController.importReport(transformReportDefine, Boolean.valueOf(true));
            }
        }
        catch (JQException e) {
            throw new TransferException(e.getMessage(), (Throwable)e);
        }
        List<SchemePeriodLinkDTO> periodLinks = formSchemeDTO.getPeriodLinks();
        if (!CollectionUtils.isEmpty(periodLinks)) {
            List list = periodLinks.stream().map(r -> {
                DesignSchemePeriodLink designSchemePeriodLink = new DesignSchemePeriodLink();
                r.value2Define((DesignSchemePeriodLinkDefine)designSchemePeriodLink);
                return designSchemePeriodLink;
            }).collect(Collectors.toList());
            try {
                this.designTimeViewController.deleteSchemePeriodLinkByScheme(formSchemeInfo.getKey());
                this.importPropertyNumMessage(TransferNodeType.FORM_SCHEME.getTitle(), "\u65b9\u6848\u65f6\u671f\u6620\u5c04periodLinks", periodLinks.size());
                this.designTimeViewController.inserSchemePeriodLink(list);
            }
            catch (Exception e) {
                throw new TransferException(e.getMessage(), (Throwable)e);
            }
        }
        try {
            AnalysisSchemeParamDefineImpl analysisScheme;
            AnalysisSchemeDTO analysisSchemeDTO = formSchemeDTO.getAnalysisSchemeDTO();
            if (analysisSchemeDTO != null && (analysisScheme = analysisSchemeDTO.getAnalysisScheme()) != null) {
                this.importPropertyMessage(TransferNodeType.FORM_SCHEME.getTitle(), "\u5206\u6790\u53c2\u6570analysisSchemeDTO");
                this.designTimeViewController.updataAnalysisSchemeParamDefine(key, (DesignAnalysisSchemeParamDefine)analysisScheme);
            }
        }
        catch (Exception exception) {
            logger.error("\u5bfc\u5165\u62a5\u8868\u65b9\u6848 " + key + "\u65f6\u5d4c\u5165\u5f0f\u5206\u6790\u53c2\u6570\u5bfc\u5165\u5931\u8d25\uff1a " + exception.getMessage());
            throw new TransferException(exception.getMessage(), (Throwable)exception);
        }
        DesParamLanguageDTO desParamLanguageDTO = formSchemeDTO.getDesParamLanguageDTO();
        if (context.isImportMultiLanguage() && desParamLanguageDTO != null) {
            try {
                this.setDesParamLanguage(desParamLanguageDTO);
            }
            catch (Exception e) {
                throw new TransferException(key + " \u7684\u62a5\u8868\u65b9\u6848\u591a\u8bed\u8a00\u5bfc\u5165\u5931\u8d25\uff1a" + e.getMessage(), (Throwable)e);
            }
        }
        if (this.iMCParamService != null && (multCheckParamDTOS = formSchemeDTO.getMultCheckParamDTOS()) != null && multCheckParamDTOS.size() > 0) {
            try {
                ArrayList<MCSchemeParam> updateMultCheckParams = new ArrayList<MCSchemeParam>();
                ArrayList<MCSchemeParam> addMultCheckParams = new ArrayList<MCSchemeParam>();
                List existMultCheckScheme = this.iMCParamService.query(key);
                Set existMultCheckSchemeKeySets = existMultCheckScheme.stream().map(MultcheckScheme::getKey).collect(Collectors.toSet());
                for (MultCheckParamDTO multCheckParamDTO : multCheckParamDTOS) {
                    if (openParamLevel && isSetLevel && !this.isSet(multCheckParamDTO.getLevel())) {
                        String ownerLevelAndId = String.valueOf(context.getSrcPacketLevel());
                        multCheckParamDTO.setLevel(ownerLevelAndId);
                    }
                    MCSchemeParam mCSchemeParam = new MCSchemeParam();
                    multCheckParamDTO.valueDefine(mCSchemeParam);
                    if (existMultCheckSchemeKeySets.contains(multCheckParamDTO.getKey())) {
                        updateMultCheckParams.add(mCSchemeParam);
                        continue;
                    }
                    addMultCheckParams.add(mCSchemeParam);
                }
                this.iMCParamService.batchAddMCSParams(addMultCheckParams);
                this.iMCParamService.batchModifyMCSParams(updateMultCheckParams);
            }
            catch (Exception e) {
                logger.error("\u53c2\u6570\u5bfc\u5165\u5bfc\u51fa\u5bfc\u5165\u62a5\u8868\u65b9\u6848 {} \u65f6\u7efc\u5408\u5ba1\u6838\u53c2\u6570\u5bfc\u5165\u5931\u8d25\uff1a {}", (Object)key, (Object)e.getMessage());
            }
        }
    }

    public void setOrgMappingRule(DesignTaskLinkDefine taskLinkDefine, DesignTaskDefine designTaskDefine) {
        if (CollectionUtils.isEmpty(taskLinkDefine.getOrgMappingRules())) {
            TaskLinkOrgMappingRule taskLinkOrgMappingRule = new TaskLinkOrgMappingRule();
            taskLinkOrgMappingRule.setTaskLinkKey(taskLinkDefine.getKey());
            DesignTaskDefine relatedTask = this.designTimeViewController.queryTaskDefine(taskLinkDefine.getRelatedTaskKey());
            if (relatedTask != null) {
                taskLinkOrgMappingRule.setSourceEntity(relatedTask.getDw());
            } else {
                DesignTaskDefine designTaskDefine1;
                DesignFormSchemeDefine designFormSchemeDefine = this.designTimeViewController.queryFormSchemeDefine(taskLinkDefine.getRelatedFormSchemeKey());
                if (designFormSchemeDefine != null && (designTaskDefine1 = this.designTimeViewController.queryTaskDefine(designFormSchemeDefine.getTaskKey())) != null) {
                    taskLinkOrgMappingRule.setSourceEntity(designTaskDefine1.getDw());
                }
            }
            taskLinkOrgMappingRule.setTargetEntity(designTaskDefine.getDw());
            taskLinkOrgMappingRule.setMatchingType(taskLinkDefine.getMatchingType());
            if (taskLinkDefine.getMatchingType() == TaskLinkMatchingType.FORM_TYPE_EXPRESSION) {
                taskLinkOrgMappingRule.setSourceFormula(taskLinkDefine.getRelatedFormula());
                taskLinkOrgMappingRule.setTargetFormula(taskLinkDefine.getCurrentFormula());
                taskLinkOrgMappingRule.setExpressionType(taskLinkDefine.getExpressionType());
            }
            taskLinkOrgMappingRule.setOrder(OrderGenerator.newOrder());
            ArrayList<TaskLinkOrgMappingRule> orgMappingRule = new ArrayList<TaskLinkOrgMappingRule>();
            orgMappingRule.add(taskLinkOrgMappingRule);
            taskLinkDefine.setOrgMappingRule(orgMappingRule);
        }
    }

    public void importCustomData(IImportContext context, String key, byte[] bytes) throws IOException, TransferException {
        this.importPropertyMessage(TransferNodeType.CUSTOM_DATA.getTitle(), "key", key);
        String[] strings = CustomHelper.splitKey(key);
        if (null == strings || strings.length != 2) {
            throw new TransferException("\u5bfc\u5165\u8d44\u6e90\u5931\u8d25");
        }
        String parentDataKey = strings[0];
        String dataKey = strings[1];
        if (null != this.customBusiness && this.customBusiness.size() != 0) {
            for (CustomBusiness business : this.customBusiness) {
                if (null == business || !business.checkBusiness(dataKey, parentDataKey)) continue;
                this.importPropertyMessage(TransferNodeType.CUSTOM_DATA.getTitle(), "dataKey \u548c  parentDataKey", dataKey + " " + parentDataKey);
                business.importData(dataKey, parentDataKey, bytes);
            }
        }
    }

    /*
     * WARNING - void declaration
     */
    public void importTaskBusiness(IImportContext context, String key, byte[] bytes) throws IOException, TransferException {
        TaskInfoDTO taskInfo;
        TaskDTO taskDTO;
        block48: {
            List<FormulaConditionDTO> formulaConditions;
            List<DimensionFilterDTO> dimensionFilters;
            taskDTO = TaskDTO.valueOf(bytes, objectMapper);
            taskInfo = taskDTO.getTaskInfo();
            this.getMessage(TransferNodeType.TASK.getTitle(), key, taskInfo.getKey(), taskInfo.getTitle());
            if (this.paramLevelManager.isOpenParamLevel() && !this.isSet(taskInfo.getOwnerLevelAndId())) {
                String ownerLevelAndId = String.valueOf(context.getSrcPacketLevel());
                taskInfo.setOwnerLevelAndId(ownerLevelAndId);
                this.setOwnerLevelAndIdDebugMessage(TransferNodeType.TASK.getTitle(), ownerLevelAndId);
            }
            DesignTaskDefine taskDefine = this.designTimeViewController.createTaskDefine();
            taskInfo.value2Define(taskDefine);
            DesignTaskDefine define = this.designTimeViewController.queryTaskDefine(key);
            this.oldResourceExistMessage(define != null, TransferNodeType.TASK.getTitle(), define != null ? define.getTitle() : "");
            List<EntityViewDTO> entityViews = taskDTO.getEntityViews();
            if (entityViews == null) {
                entityViews = new ArrayList<EntityViewDTO>();
            }
            if (taskDefine.getFilterExpression() != null && define != null && define.getFilterTemplate() != null) {
                List filterTemplates = this.filterTemplateService.getByEntity(taskDefine.getDw());
                if (!CollectionUtils.isEmpty(filterTemplates)) {
                    for (FilterTemplateDTO filterTemplate : filterTemplates) {
                        if (!taskDefine.getFilterExpression().equals(filterTemplate.getFilterContent())) continue;
                        taskDefine.setFilterTemplate(filterTemplate.getFilterTemplateID());
                        taskDefine.setFilterExpression(null);
                        this.importPropertyMessage(TransferNodeType.TASK.getTitle(), "\u4e3b\u7ef4\u5ea6\u8fc7\u6ee4\u6a21\u677ffilterTemplates", filterTemplate.getFilterTemplateID());
                        break;
                    }
                }
                if (!StringUtils.hasText(taskDefine.getFilterTemplate())) {
                    FilterTemplateDTO entityView = this.filterTemplateService.init();
                    entityView.setEntityID(taskDefine.getDw());
                    entityView.setFilterContent(taskDefine.getFilterExpression());
                    entityView.setFilterTemplateTitle(this.getNewEntityViewTitle(taskDefine.getDw()));
                    EntityViewDTO entityViewDTO = EntityViewDTO.valueOf(new FilterTemplateDO(entityView));
                    entityViews.add(entityViewDTO);
                    taskDefine.setFilterTemplate(entityView.getFilterTemplateID());
                    this.importPropertyMessage(TransferNodeType.TASK.getTitle(), "\u4e3b\u7ef4\u5ea6\u8fc7\u6ee4\u6a21\u677fentityView.getFilterTemplateID()", entityView.getFilterTemplateID());
                    taskDefine.setFilterExpression(null);
                }
            } else if (!CollectionUtils.isEmpty(entityViews) && entityViews.size() == 1) {
                taskDefine.setFilterTemplate(entityViews.get(0).getEntityViewKey());
                this.importPropertyMessage(TransferNodeType.TASK.getTitle(), "\u4e3b\u7ef4\u5ea6\u8fc7\u6ee4\u6a21\u677fentityViews.get(0).getEntityViewKey()", entityViews.get(0).getEntityViewKey());
                taskDefine.setFilterExpression(null);
            }
            if (!CollectionUtils.isEmpty(entityViews)) {
                this.importPropertyNumMessage(TransferNodeType.TASK.getTitle(), "entityViews", entityViews.size());
                this.insertEntityView(entityViews);
            }
            DesignTaskFlowsDefine taskFlowsDefine = taskDTO.getTaskFlowsDefine();
            taskDefine.setFlowsSetting(taskFlowsDefine);
            ContextUser user = NpContextHolder.getContext().getUser();
            String userName = "\u7ba1\u7406\u5458";
            if (user != null && !"admin".equals(user.getName())) {
                userName = user.getName();
            }
            taskDefine.setCreateUserName(userName);
            try {
                if (define != null) {
                    taskDefine.setVersion(define.getVersion());
                    this.designTimeViewController.updateTaskDefine(taskDefine);
                } else {
                    if (!"2.0".equals(taskDefine.getVersion())) {
                        taskDefine.setVersion("1.0");
                    }
                    this.designTimeViewController.insertTaskDefine(taskDefine);
                }
            }
            catch (JQException | DesignCheckException e) {
                throw new TransferException(e.getMessage(), e);
            }
            String identityId = NpContextHolder.getContext().getIdentityId();
            try {
                if (identityId != null) {
                    this.definitionAuthority.grantAllPrivileges(taskDefine.getKey());
                }
            }
            catch (Exception e) {
                context.getLogger().error("[" + taskDefine.getKey() + "]\u4efb\u52a1\u6743\u9650\u6dfb\u52a0\u5931\u8d25\uff01");
            }
            List<TaskOption> taskOptions = taskDTO.getTaskOptions();
            if (taskOptions != null) {
                this.importPropertyNumMessage(TransferNodeType.TASK.getTitle(), "\u4efb\u52a1\u9009\u9879\u6279\u91cf\u8bbe\u7f6etaskOptions", taskOptions.size());
                this.iTaskOptionController.setOptions(taskOptions);
            }
            try {
                this.designTimeViewController.deleteSchemePeriodLinkByTask(key);
            }
            catch (Exception e) {
                throw new TransferException(e.getMessage(), (Throwable)e);
            }
            List<SchemePeriodLinkDTO> periodLinks = taskDTO.getPeriodLinks();
            if (!CollectionUtils.isEmpty(periodLinks)) {
                List collect = periodLinks.stream().map(r -> {
                    DesignSchemePeriodLink designSchemePeriodLink = new DesignSchemePeriodLink();
                    r.value2Define((DesignSchemePeriodLinkDefine)designSchemePeriodLink);
                    return designSchemePeriodLink;
                }).collect(Collectors.toList());
                List schemeKeys = collect.stream().map(SchemePeriodLinkDefine::getSchemeKey).distinct().collect(Collectors.toList());
                try {
                    for (Object schemeKey : schemeKeys) {
                        this.designTimeViewController.deleteSchemePeriodLinkByScheme((String)schemeKey);
                    }
                    this.importPropertyNumMessage(TransferNodeType.TASK.getTitle(), "\u65b9\u6848\u65f6\u671f\u6620\u5c04periodLinks", collect.size());
                    this.designTimeViewController.inserSchemePeriodLink(collect);
                }
                catch (Exception e) {
                    throw new TransferException(e.getMessage(), (Throwable)e);
                }
            }
            if ((dimensionFilters = taskDTO.getDimensionFilters()) != null && !dimensionFilters.isEmpty()) {
                try {
                    this.designTimeViewController.deleteDimensionFilter(key);
                    this.designTimeViewController.insertDimensionFilters(DimensionFilterDTO.convertDefinition(dimensionFilters));
                    this.importPropertyNumMessage(TransferNodeType.TASK.getTitle(), "\u60c5\u666f\u8fc7\u6ee4\u53c2\u6570dimensionFilters", dimensionFilters.size());
                }
                catch (JQException e) {
                    throw new TransferException(e.getMessage(), (Throwable)e);
                }
            }
            if (!CollectionUtils.isEmpty(formulaConditions = taskDTO.getFormulaConditions())) {
                try {
                    this.formulaDesignTimeController.deleteFormulaConditions(formulaConditions.stream().map(FormulaConditionDTO::getKey).collect(Collectors.toList()));
                    ArrayList<DesignFormulaCondition> conditions = new ArrayList<DesignFormulaCondition>();
                    for (FormulaConditionDTO formulaConditionDTO : formulaConditions) {
                        DesignFormulaCondition condition = this.formulaDesignTimeController.initFormulaCondition();
                        formulaConditionDTO.toDefine(condition);
                        conditions.add(condition);
                    }
                    this.formulaDesignTimeController.insertFormulaConditions(conditions);
                    this.importPropertyNumMessage(TransferNodeType.TASK.getTitle(), "\u516c\u5f0f\u9002\u7528\u6761\u4ef6\u53c2\u6570formulaConditions", formulaConditions.size());
                }
                catch (Exception e) {
                    throw new TransferException(e.getMessage(), (Throwable)e);
                }
            }
            if (this.taskTransfers != null) {
                try {
                    Logger log = context.getLogger();
                    Map<String, byte[]> params = taskDTO.getParams();
                    if (params != null) {
                        for (Map.Entry<String, byte[]> entry : params.entrySet()) {
                            TaskTransfer taskTransfer = this.getTaskTransfer(entry.getKey());
                            if (taskTransfer == null) {
                                if (log == null) continue;
                                log.warn("\u672a\u627e\u5230\u6269\u5c55\u8d44\u6e90\u5904\u7406\u5668\u3010" + entry.getKey() + "\u3011\u8df3\u8fc7");
                                continue;
                            }
                            if (logger.isDebugEnabled()) {
                                logger.debug(String.format("\u5bfc\u5165\u4efb\u52a1\u5176\u4ed6\u53c2\u6570\uff0c\u6269\u5c55\u8d44\u6e90\u5904\u7406\u5668id\u662f\uff1a %s ", entry.getKey()));
                            }
                            taskTransfer.importTaskData(context, key, entry.getValue());
                        }
                    }
                }
                catch (Exception e) {
                    Logger logger = context.getLogger();
                    if (logger == null) break block48;
                    logger.warn("\u5bfc\u5165\u4efb\u52a1\u6269\u5c55\u8d44\u6e90\u5931\u8d25\uff0c\u8df3\u8fc7", e);
                }
            }
        }
        DesParamLanguageDTO desParamLanguageDTO = taskDTO.getDesParamLanguageDTO();
        if (context.isImportMultiLanguage() && desParamLanguageDTO != null) {
            try {
                this.setDesParamLanguage(desParamLanguageDTO);
            }
            catch (Exception e) {
                throw new TransferException(key + " \u7684\u4efb\u52a1\u591a\u8bed\u8a00\u5bfc\u5165\u5931\u8d25\uff1a" + e.getMessage(), (Throwable)e);
            }
        }
        List<TaskOrgLinkDTO> taskOrgLinkDTOs = taskDTO.getTaskOrgLinkDTOs();
        if (this.taskOrgLinkService != null) {
            Object var19_30 = null;
            if (!CollectionUtils.isEmpty(taskOrgLinkDTOs)) {
                TaskOrgLinkDefine[] taskOrgLinkDefineArray = new TaskOrgLinkDefine[taskOrgLinkDTOs.size()];
                for (int i = 0; i < taskOrgLinkDTOs.size(); ++i) {
                    TaskOrgLinkDTO taskOrgLinkDTO = taskOrgLinkDTOs.get(i);
                    TaskOrgLinkDefineImpl taskOrgLinkDefine = new TaskOrgLinkDefineImpl();
                    taskOrgLinkDTO.valueToDefine(taskOrgLinkDefine);
                    taskOrgLinkDefineArray[i] = taskOrgLinkDefine;
                }
            } else {
                TaskOrgLinkDefine[] taskOrgLinkDefineArray = new TaskOrgLinkDefine[1];
                TaskOrgLinkDefineImpl taskOrgLinkDefine = new TaskOrgLinkDefineImpl();
                taskOrgLinkDefine.setKey(UUIDUtils.getKey());
                taskOrgLinkDefine.setTask(taskInfo.getKey());
                taskOrgLinkDefine.setEntity(taskInfo.getDw());
                taskOrgLinkDefine.setEntityAlias(null);
                taskOrgLinkDefine.setOrder(OrderGenerator.newOrder());
                taskOrgLinkDefineArray[0] = taskOrgLinkDefine;
            }
            try {
                void var19_33;
                this.taskOrgLinkService.deleteTaskOrgLinkByTask(key);
                this.taskOrgLinkService.insertTaskOrgLink((TaskOrgLinkDefine[])var19_33);
            }
            catch (Exception e) {
                throw new TransferException("\u4efb\u52a1 " + taskInfo.getTitle() + " \u7684\u5355\u4f4d\u53e3\u5f84\u5bfc\u5165\u5931\u8d25\uff01");
            }
        }
    }

    public void importTaskGroupBusiness(IImportContext context, String key, byte[] bytes) throws IOException, TransferException {
        TaskGroupDTO taskGroupDTO = TaskGroupDTO.valueOf(bytes, objectMapper);
        this.getMessage(TransferNodeType.TASK_GROUP.getTitle(), key, taskGroupDTO.getKey(), taskGroupDTO.getTitle());
        if (this.paramLevelManager.isOpenParamLevel() && !this.isSet(taskGroupDTO.getOwnerLevelAndId())) {
            String ownerLevelAndId = String.valueOf(context.getSrcPacketLevel());
            taskGroupDTO.setOwnerLevelAndId(ownerLevelAndId);
            this.setOwnerLevelAndIdDebugMessage(TransferNodeType.TASK_GROUP.getTitle(), ownerLevelAndId);
        }
        DesignTaskGroupDefine taskGroupDefine = this.designTimeViewController.createTaskGroup();
        List<DesignTaskGroupLink> links = taskGroupDTO.value2Define(taskGroupDefine);
        DesignTaskGroupDefine taskGroup = this.designTimeViewController.queryTaskGroupDefine(key);
        this.oldResourceExistMessage(taskGroup != null, TransferNodeType.TASK_GROUP.getTitle(), taskGroup != null ? taskGroup.getTitle() : "");
        try {
            if (taskGroup == null) {
                this.designTimeViewController.insertTaskGroupDefine(taskGroupDefine);
            } else {
                this.designTimeViewController.updateTaskGroupDefine(taskGroupDefine);
            }
        }
        catch (DesignCheckException e) {
            throw new TransferException(e.getMessage(), (Throwable)e);
        }
        List oldLinks = this.designTimeViewController.getGroupLinkByGroupKey(key);
        Set taskSet = oldLinks.stream().map(DesignTaskGroupLink::getTaskKey).collect(Collectors.toSet());
        if (links != null) {
            this.importLinkMessage(TransferNodeType.TASK_GROUP.getTitle() + "\u4e0b\u76f8\u5173\u94fe\u63a5", links.stream().map(DesignTaskGroupLink::getTaskKey).collect(Collectors.toList()), taskSet);
            if (!taskSet.isEmpty()) {
                links.removeIf(r -> taskSet.contains(r.getTaskKey()));
            }
            if (!links.isEmpty()) {
                try {
                    int[] insert = this.groupLinkDao.insert((Object[])links.toArray(new DesignTaskGroupLink[0]));
                    this.importNumMessage(TransferNodeType.TASK_GROUP.getTitle(), links.size(), Arrays.stream(insert).sum());
                }
                catch (DBParaException e) {
                    throw new TransferException(e.getMessage(), (Throwable)e);
                }
            } else if (logger.isDebugEnabled()) {
                logger.debug("\u53c2\u6570\u5bfc\u5165\u4efb\u52a1\u5206\u7ec4\u4fe1\u606f\uff0c\u9700\u8981\u5bfc\u5165\u7684groupLink\u7ecf\u8fc7\u8fc7\u6ee4\u662f\u7a7a");
            }
        }
        this.taskGroupCacheService.refresh();
    }

    public Set<FilterTemplateDO> getEntityViewBusiness(List<DesignDataLinkDefine> dataLinkDefines) {
        HashSet<FilterTemplateDO> entityViews = new HashSet<FilterTemplateDO>();
        for (DesignDataLinkDefine dataLinkDefine : dataLinkDefines) {
            FilterTemplateDTO entityViewDefine;
            if (dataLinkDefine.getFilterTemplate() == null || (entityViewDefine = this.filterTemplateService.getFilterTemplate(dataLinkDefine.getFilterTemplate())) == null) continue;
            dataLinkDefine.setFilterExpression(entityViewDefine.getFilterContent());
            entityViews.add(new FilterTemplateDO(entityViewDefine));
        }
        return entityViews;
    }

    public void updateLinkFilterType(DesignDataLinkDefine originDataLink, DataLinkDTO targetDataLink) {
        DesignDataField refDataField;
        String originEntityView = originDataLink.getFilterTemplate();
        String targetFilterExpression = targetDataLink.getFilterExpression();
        String targetEntityView = targetDataLink.getEntityViewID();
        if (targetEntityView != null) {
            targetFilterExpression = null;
        }
        if (targetFilterExpression != null && originEntityView != null && (refDataField = this.designDataSchemeService.getDataField(targetDataLink.getLinkExpression())) != null && refDataField.getRefDataEntityKey() != null) {
            List filterTemplates = this.filterTemplateService.getByEntity(refDataField.getRefDataEntityKey());
            if (!CollectionUtils.isEmpty(filterTemplates)) {
                for (FilterTemplateDTO filterTemplate : filterTemplates) {
                    if (!targetFilterExpression.equals(filterTemplate.getFilterContent())) continue;
                    targetDataLink.setEntityViewID(filterTemplate.getFilterTemplateID());
                    targetDataLink.setFilterExpression(null);
                }
            }
            if (!StringUtils.hasText(targetDataLink.getEntityViewID())) {
                FilterTemplateDTO entityViewDefine = new FilterTemplateDTO();
                entityViewDefine.setEntityID(refDataField.getRefDataEntityKey());
                entityViewDefine.setFilterContent(targetFilterExpression);
                entityViewDefine.setFilterTemplateTitle(this.getNewEntityViewTitle(refDataField.getRefDataEntityKey()));
                String newEntityViewKey = this.filterTemplateService.insert(entityViewDefine);
                targetDataLink.setEntityViewID(newEntityViewKey);
                targetDataLink.setFilterExpression(null);
            }
        }
    }

    private boolean isSet(String level) {
        if (level == null || S_NOT_LEVEL.equals(level)) {
            return false;
        }
        return StringUtils.hasLength(level);
    }

    public LinkedHashSet<String> judgeUpdate(Set<String> originKeys, Set<String> targetKeys) {
        LinkedHashSet<String> result = new LinkedHashSet<String>();
        result.addAll(originKeys);
        result.retainAll(targetKeys);
        return result;
    }

    public String getNewEntityViewTitle(String entityID) {
        int index = 0;
        String newBaseTitle = "";
        IEntityDefine entityDefine = this.iEntityMetaService.queryEntity(entityID);
        if (entityDefine != null) {
            newBaseTitle = entityDefine.getTitle() + "_\u8fc7\u6ee4\u6a21\u677f";
        }
        String newTitle = newBaseTitle;
        List entityViewDefineDatas = this.filterTemplateService.listAll();
        if (entityViewDefineDatas != null && entityViewDefineDatas.size() > 0) {
            boolean flag = true;
            while (flag) {
                int oldIndex = index;
                for (FilterTemplateDTO viewDefineData : entityViewDefineDatas) {
                    String title = viewDefineData.getFilterTemplateTitle();
                    if (!newTitle.equals(title)) continue;
                    newTitle = newBaseTitle + ++index;
                    break;
                }
                if (oldIndex != index) continue;
                break;
            }
        }
        return newTitle;
    }

    public void insertEntityView(List<EntityViewDTO> entityViews) {
        if (entityViews != null) {
            ArrayList<FilterTemplateDTO> needUpdate = new ArrayList<FilterTemplateDTO>();
            ArrayList<FilterTemplateDTO> needInsert = new ArrayList<FilterTemplateDTO>();
            Map<String, EntityViewDTO> viewMap = entityViews.stream().filter(entityViewDTO -> entityViewDTO.getEntityID() != null).collect(Collectors.toMap(EntityViewDTO::getEntityViewKey, v -> v));
            for (String entityViewKey : viewMap.keySet()) {
                FilterTemplateDTO entityView = this.filterTemplateService.getFilterTemplate(entityViewKey);
                if (entityView.getFilterTemplateID() != null) {
                    needUpdate.add(viewMap.get(entityViewKey).dto2Define());
                    continue;
                }
                needInsert.add(viewMap.get(entityViewKey).dto2Define());
            }
            if (!CollectionUtils.isEmpty(needInsert)) {
                this.filterTemplateService.batchInsert(needInsert);
            }
            if (!CollectionUtils.isEmpty(needUpdate)) {
                this.filterTemplateService.batchUpdate(needUpdate);
            }
        }
    }

    private void importNumMessage(String resourceTypeName, int importNum, int successNum) {
        if (logger.isDebugEnabled()) {
            logger.debug(String.format("\u53c2\u6570\u5bfc\u5165%s\u4fe1\u606f\uff0c\u9700\u8981\u5bfc\u5165\u7684\u4e0b\u7ea7\u94fe\u63a5\u7684\u6570\u91cf\u662f\uff1a%d\uff0c\u5bfc\u5165\u6210\u529f\u7684\u6570\u636e\u662f\uff1a%d\uff0c", resourceTypeName, importNum, successNum));
        }
    }

    private void importLinkMessage(String resourceTypeName, Collection resource, Collection hasExist) {
        if (logger.isDebugEnabled()) {
            logger.debug(String.format("\u53c2\u6570\u5305\u91cc%s\u6709\uff1a", resourceTypeName) + resource);
            logger.debug(String.format("\u5f53\u524d\u670d\u52a1%s\u6709\uff1a", resourceTypeName) + hasExist);
        }
    }

    private void importLinkMessage(String resourceTypeName, Collection resource) {
        if (logger.isDebugEnabled()) {
            logger.debug(String.format("%s\u6709\uff1a", resourceTypeName) + resource);
        }
    }

    private void importPropertyNumMessage(String resourceTypeName, String propertyName, int num) {
        if (logger.isDebugEnabled()) {
            logger.debug(String.format("\u53c2\u6570\u5bfc\u5165 %s \u7684 %s \u5c5e\u6027\uff0c\u5176\u6570\u91cf\u662f\uff1a%d \uff01", resourceTypeName, propertyName, num));
        }
    }

    private void setOwnerLevelAndIdDebugMessage(String resourceTypeName, String ownerLevelAndId) {
        if (logger.isDebugEnabled()) {
            logger.debug(String.format("\u53c2\u6570\u5bfc\u5165 %s \u4fe1\u606f\uff0c\u8bbe\u7f6e\u4e86\u7ea7\u6b21\uff0c\u5176\u503c\u662f\uff1a%s \uff01", resourceTypeName, ownerLevelAndId));
        }
    }

    private void importPropertyMessage(String resourceTypeName, String propertyName, String propertyValue) {
        if (logger.isDebugEnabled()) {
            logger.debug(String.format("\u53c2\u6570\u5bfc\u5165 %s \u7684 %s \u5c5e\u6027\uff0c\u5176\u503c\u662f\uff1a%s \uff01", resourceTypeName, propertyName, propertyValue));
        }
    }

    private void importPropertyMessage(String resourceTypeName, String propertyName) {
        if (logger.isDebugEnabled()) {
            logger.debug(String.format("\u53c2\u6570\u5bfc\u5165 %s \u7684 %s \u5c5e\u6027\u4e0d\u4e3a\u7a7a\uff0c\u8d70\u65b0\u589e\u6216\u8005\u66f4\u65b0\uff01", resourceTypeName, propertyName));
        }
    }

    private void oldResourceExistMessage(boolean oldResourceNotEmpty, String resourceTypeName, String messageTitle) {
        if (logger.isDebugEnabled()) {
            if (oldResourceNotEmpty) {
                logger.debug(String.format("\u6839\u636e\u5165\u53c2\u7684key\u67e5\u8be2%s\u5b58\u5728\uff0c\u5bfc\u5165\u8d70\u66f4\u65b0\uff0c\u5176title\u662f\uff1a %s", resourceTypeName, messageTitle));
            } else {
                logger.debug(String.format("\u6839\u636e\u5165\u53c2\u7684key\u67e5\u8be2%s\u4e0d\u5b58\u5728\uff0c\u5bfc\u5165\u8d70\u65b0\u589e\uff01", resourceTypeName));
            }
        }
    }

    private void getSingleMappingDTOMessage(boolean isNotEmpty, String key) {
        if (logger.isDebugEnabled()) {
            if (isNotEmpty) {
                logger.debug(String.format("\u53c2\u6570\u5bfc\u5165\u6620\u5c04\u65b9\u6848\u4fe1\u606f\uff0c\u5165\u53c2key\u662f\uff1a%s \uff0c\u89e3\u6790\u51fa\u6765\u7684\u6570\u636e\u5bf9\u8c61\u4e0d\u4e3a\u7a7a", key));
            } else {
                logger.debug(String.format("\u53c2\u6570\u5bfc\u5165\u6620\u5c04\u65b9\u6848\u4fe1\u606f\uff0c\u5165\u53c2key\u662f\uff1a%s \uff0c\u89e3\u6790\u51fa\u6765\u7684\u6570\u636e\u5bf9\u8c61\u662f\u7a7a\uff01", key));
            }
        }
    }

    private void getMessage(String resourceTypeName, String key, String resourceKey, String resourceTitle) {
        if (logger.isDebugEnabled()) {
            String message = String.format("\u53c2\u6570\u5bfc\u5165 %s \u4fe1\u606f\uff0c\u5165\u53c2key\u662f\uff1a%s \uff0c\u89e3\u6790\u51fa\u6765\u7684\u6570\u636e\u5bf9\u8c61\u7684key\u662f\uff1a%s \uff0ctitle\u662f\uff1a%s \uff01", resourceTypeName, key, resourceKey, resourceTitle);
            logger.debug(message);
            if (!resourceKey.equals(key)) {
                logger.debug("\u5f53\u524d\u8d44\u6e90\u5bfc\u5165\u5165\u53c2\u7684key\u548c\u53c2\u6570\u5305\u89e3\u6790\u51fa\u6765\u7684key\u4e0d\u4e00\u6837\uff0c\u8bf7\u6838\u5bf9\u51fa\u9519\u539f\u56e0");
            }
        }
    }

    private void getMessage(String resourceTypeName, String keyName1, String key1, String keyName2, String key2) {
        if (logger.isDebugEnabled()) {
            logger.debug(String.format("\u53c2\u6570\u5bfc\u5165%s\u4fe1\u606f\uff0c\u5165\u53c2%s\u662f\uff1a%s \uff0c%s\u662f\uff1a%s \uff01", resourceTypeName, keyName1, key1, keyName2, key2));
        }
    }

    private void deleteFormula(String formulaSchemeKey, String formKey) {
        if (logger.isDebugEnabled()) {
            logger.info(String.format("\u8981\u5bfc\u5165\u7684\u516c\u5f0f\u5148\u5220\u96644\uff0c\u5220\u9664\u516c\u5f0f\u65b9\u6848\uff1a%s \u4e0b\u62a5\u8868\uff1a%s\u7684\u516c\u5f0f", formulaSchemeKey, formKey));
        }
    }

    private DesParamLanguageDTO getDesParamLanguage(String resourceKey, LanguageResourceType resourceType, String languageType) {
        List desParamLanguages = this.desParamLanguageDao.queryLanguage(resourceKey, resourceType, languageType);
        if (desParamLanguages.size() > 0) {
            Optional<DesParamLanguage> first = desParamLanguages.stream().filter(a -> PARAM_LANGUAGE_ENGLISH.equals(a.getLanguageType())).findFirst();
            return first.map(DesParamLanguageDTO::valueOf).orElse(null);
        }
        return null;
    }

    private void setDesParamLanguage(DesParamLanguageDTO desParamLanguageDTO) throws DBParaException {
        List desParamLanguages = this.desParamLanguageDao.queryLanguage(desParamLanguageDTO.getResourceKey(), desParamLanguageDTO.getResourceType(), desParamLanguageDTO.getLanguageType());
        ArrayList<String[]> needDeleteList = new ArrayList<String[]>();
        if (desParamLanguages.size() > 0) {
            String[] delete = new String[]{((DesParamLanguage)desParamLanguages.get(0)).getResourceKey(), ((DesParamLanguage)desParamLanguages.get(0)).getLanguageType()};
            needDeleteList.add(delete);
        }
        if (needDeleteList.size() > 0) {
            this.desParamLanguageDao.batchDelete(needDeleteList);
        }
        DesParamLanguage desParamLanguage = new DesParamLanguage();
        desParamLanguageDTO.value2Define(desParamLanguage);
        this.desParamLanguageDao.insert(desParamLanguage);
    }

    private void setFormulaLanguage(Map<String, String> formulaLanguageMaps) throws DBParaException {
        ArrayList<String> formulaKeys = new ArrayList<String>(formulaLanguageMaps.keySet());
        List desParamLanguages = this.desParamLanguageDao.queryLanguageByResKeys(formulaKeys);
        ArrayList<String[]> needDeleteList = new ArrayList<String[]>();
        if (desParamLanguages.size() > 0) {
            String[] delete = new String[]{((DesParamLanguage)desParamLanguages.get(0)).getResourceKey(), ((DesParamLanguage)desParamLanguages.get(0)).getLanguageType()};
            needDeleteList.add(delete);
        }
        if (needDeleteList.size() > 0) {
            this.desParamLanguageDao.batchDelete(needDeleteList);
        }
        ArrayList<DesParamLanguage> srcFormulaLanguages = new ArrayList<DesParamLanguage>();
        for (Map.Entry<String, String> stringStringEntry : formulaLanguageMaps.entrySet()) {
            srcFormulaLanguages.add(this.getFormulaLanguage(stringStringEntry.getKey(), stringStringEntry.getValue()));
        }
        DesParamLanguage[] srcFormulaLanguageAdds = srcFormulaLanguages.toArray(new DesParamLanguage[0]);
        this.desParamLanguageDao.batchInsert(srcFormulaLanguageAdds);
    }

    private DesParamLanguage getFormulaLanguage(String resourceKey, String languageInfo) {
        DesParamLanguage desParamLanguage = new DesParamLanguage();
        desParamLanguage.setKey(UUIDUtils.getKey());
        desParamLanguage.setResourceKey(resourceKey);
        desParamLanguage.setResourceType(LanguageResourceType.FORMULADESCRIPTION);
        desParamLanguage.setLanguageType(PARAM_LANGUAGE_ENGLISH);
        desParamLanguage.setLanguageInfo(languageInfo);
        return desParamLanguage;
    }

    private static /* synthetic */ String lambda$importFormulaFormBusinessObj$14(String k1, String k2) {
        return k1;
    }

    private static /* synthetic */ boolean lambda$importFormulaFormBusinessObj$13(FormulaDTO a) {
        return StringUtils.hasLength(a.getLanguageInfo());
    }

    private static /* synthetic */ String lambda$importFormulaFormBusinessObj$12(DesignFormulaDefine e) {
        return e.getKey();
    }

    private static /* synthetic */ void lambda$importFormulaFormBusinessObj$11(Set deleteKeys, DesignFormulaDefine e) {
        deleteKeys.add(e.getKey());
    }

    private static /* synthetic */ boolean lambda$importFormulaFormBusinessObj$10(Set srcFormulaCode, DesignFormulaDefine a) {
        return srcFormulaCode.contains(a.getCode());
    }

    private static /* synthetic */ void lambda$importFormulaFormBusinessObj$9(Set deleteKeys, Set srcFormulaCode, FormulaDTO e) {
        deleteKeys.add(e.getKey());
        srcFormulaCode.add(e.getCode());
    }

    private /* synthetic */ boolean lambda$importFormulaFormBusinessObj$8(DesignFormulaDefine e) {
        return this.isSet(e.getOwnerLevelAndId());
    }

    private static /* synthetic */ String lambda$importFormulaFormBusinessObj$7(FormulaDTO e) {
        return e.getKey();
    }

    private static /* synthetic */ String lambda$importFormulaFormBusinessObj$6(FormulaDTO e) {
        return e.getKey();
    }

    private static /* synthetic */ String lambda$importFormulaFormBusinessObj$5(FormulaDTO e) {
        return e.getKey();
    }
}

