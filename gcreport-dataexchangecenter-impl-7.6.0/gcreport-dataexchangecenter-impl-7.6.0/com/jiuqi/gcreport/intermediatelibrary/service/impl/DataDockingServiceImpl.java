/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.ConverterUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.gcreport.basedata.impl.bean.GcBaseData
 *  com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool
 *  com.jiuqi.gcreport.common.GCAdjTypeEnum
 *  com.jiuqi.gcreport.common.util.DimensionUtils
 *  com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity
 *  com.jiuqi.gcreport.intermediatelibrary.dto.DataDockingBlockDTO
 *  com.jiuqi.gcreport.intermediatelibrary.vo.DataDockingBlockVO
 *  com.jiuqi.gcreport.intermediatelibrary.vo.DataDockingFormVO
 *  com.jiuqi.gcreport.intermediatelibrary.vo.DataDockingQueryVO
 *  com.jiuqi.gcreport.intermediatelibrary.vo.DataDockingResponse
 *  com.jiuqi.gcreport.intermediatelibrary.vo.DataDockingTaskDataVO
 *  com.jiuqi.gcreport.intermediatelibrary.vo.DataDockingTaskVO
 *  com.jiuqi.gcreport.intermediatelibrary.vo.DataDockingVO
 *  com.jiuqi.gcreport.nr.impl.uploadstate.util.FormUploadStateTool
 *  com.jiuqi.gcreport.nr.impl.uploadstate.vo.DimensionParamsVO
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.period.YearPeriodDO
 *  com.jiuqi.gcreport.org.api.period.YearPeriodUtil
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  com.jiuqi.np.core.context.ContextIdentity
 *  com.jiuqi.np.core.context.ContextUser
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.core.context.impl.NpContextIdentity
 *  com.jiuqi.np.core.context.impl.NpContextImpl
 *  com.jiuqi.np.core.context.impl.NpContextUser
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.dataengine.QueryEnvironment
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IDataQuery
 *  com.jiuqi.np.dataengine.intf.IDataRow
 *  com.jiuqi.np.dataengine.intf.IDataTable
 *  com.jiuqi.np.definition.common.FieldType
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.definition.facade.TableDefine
 *  com.jiuqi.np.definition.facade.UniversalTableDefine
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.np.user.SystemUser
 *  com.jiuqi.np.user.User
 *  com.jiuqi.np.user.service.SystemUserService
 *  com.jiuqi.np.user.service.UserService
 *  com.jiuqi.nr.attachment.input.AombstoneFileInfo
 *  com.jiuqi.nr.attachment.input.FileUploadContext
 *  com.jiuqi.nr.attachment.message.FileInfo
 *  com.jiuqi.nr.attachment.service.FileOperationService
 *  com.jiuqi.nr.attachment.service.FilePoolService
 *  com.jiuqi.nr.attachment.tools.AttachmentFileAreaService
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.common.util.DimensionValueSetUtil
 *  com.jiuqi.nr.dataentry.attachment.service.AttachmentOperationService
 *  com.jiuqi.nr.dataentry.attachment.service.IAttachmentService
 *  com.jiuqi.nr.dataentry.paramInfo.AttachmentInfo
 *  com.jiuqi.nr.dataentry.paramInfo.FilesUploadInfo
 *  com.jiuqi.nr.dataentry.readwrite.ReadWriteAccessDesc
 *  com.jiuqi.nr.datascheme.api.DataDimension
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DimensionType
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder
 *  com.jiuqi.nr.definition.common.DataRegionKind
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormulaSchemeDefine
 *  com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.efdc.service.DataCenterService
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.fileupload.FilesUploadReturnInfo
 *  com.jiuqi.nr.fileupload.service.CheckUploadFileService
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nr.jtable.params.input.EntityQueryByKeyInfo
 *  com.jiuqi.nr.jtable.params.output.EntityByKeyReturnInfo
 *  com.jiuqi.nr.jtable.params.output.EntityData
 *  com.jiuqi.nr.jtable.service.IJtableDataEngineService
 *  com.jiuqi.nr.jtable.service.IJtableEntityService
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.openapi.OpenApiValidateTokenDTO
 *  com.jiuqi.va.feign.client.OpenApiRunClient
 *  com.jiuqi.va.openapi.service.VaOpenApiService
 *  io.jsonwebtoken.Claims
 *  javax.servlet.http.HttpServletRequest
 *  org.apache.commons.lang3.ArrayUtils
 *  org.apache.shiro.codec.Base64
 *  org.springframework.transaction.annotation.Transactional
 *  org.springframework.transaction.interceptor.TransactionAspectSupport
 *  org.springframework.web.context.request.RequestContextHolder
 *  org.springframework.web.context.request.ServletRequestAttributes
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.gcreport.intermediatelibrary.service.impl;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.gcreport.basedata.impl.bean.GcBaseData;
import com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool;
import com.jiuqi.gcreport.common.GCAdjTypeEnum;
import com.jiuqi.gcreport.common.util.DimensionUtils;
import com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity;
import com.jiuqi.gcreport.intermediatelibrary.dao.DataDockingFormDataChangeDao;
import com.jiuqi.gcreport.intermediatelibrary.dto.DataDockingBlockDTO;
import com.jiuqi.gcreport.intermediatelibrary.entity.DataDockingFormDataChangeEO;
import com.jiuqi.gcreport.intermediatelibrary.service.DataDockingFileFetchService;
import com.jiuqi.gcreport.intermediatelibrary.service.DataDockingOrgMappingService;
import com.jiuqi.gcreport.intermediatelibrary.service.DataDockingService;
import com.jiuqi.gcreport.intermediatelibrary.task.DataDockingProcessor;
import com.jiuqi.gcreport.intermediatelibrary.vo.DataDockingBlockVO;
import com.jiuqi.gcreport.intermediatelibrary.vo.DataDockingFormVO;
import com.jiuqi.gcreport.intermediatelibrary.vo.DataDockingQueryVO;
import com.jiuqi.gcreport.intermediatelibrary.vo.DataDockingResponse;
import com.jiuqi.gcreport.intermediatelibrary.vo.DataDockingTaskDataVO;
import com.jiuqi.gcreport.intermediatelibrary.vo.DataDockingTaskVO;
import com.jiuqi.gcreport.intermediatelibrary.vo.DataDockingVO;
import com.jiuqi.gcreport.nr.impl.uploadstate.util.FormUploadStateTool;
import com.jiuqi.gcreport.nr.impl.uploadstate.vo.DimensionParamsVO;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.period.YearPeriodDO;
import com.jiuqi.gcreport.org.api.period.YearPeriodUtil;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.np.core.context.ContextIdentity;
import com.jiuqi.np.core.context.ContextUser;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.core.context.impl.NpContextIdentity;
import com.jiuqi.np.core.context.impl.NpContextImpl;
import com.jiuqi.np.core.context.impl.NpContextUser;
import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.QueryEnvironment;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IDataQuery;
import com.jiuqi.np.dataengine.intf.IDataRow;
import com.jiuqi.np.dataengine.intf.IDataTable;
import com.jiuqi.np.definition.common.FieldType;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.definition.facade.TableDefine;
import com.jiuqi.np.definition.facade.UniversalTableDefine;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.np.user.SystemUser;
import com.jiuqi.np.user.User;
import com.jiuqi.np.user.service.SystemUserService;
import com.jiuqi.np.user.service.UserService;
import com.jiuqi.nr.attachment.input.AombstoneFileInfo;
import com.jiuqi.nr.attachment.input.FileUploadContext;
import com.jiuqi.nr.attachment.message.FileInfo;
import com.jiuqi.nr.attachment.service.FileOperationService;
import com.jiuqi.nr.attachment.service.FilePoolService;
import com.jiuqi.nr.attachment.tools.AttachmentFileAreaService;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.common.util.DimensionValueSetUtil;
import com.jiuqi.nr.dataentry.attachment.service.AttachmentOperationService;
import com.jiuqi.nr.dataentry.attachment.service.IAttachmentService;
import com.jiuqi.nr.dataentry.paramInfo.AttachmentInfo;
import com.jiuqi.nr.dataentry.paramInfo.FilesUploadInfo;
import com.jiuqi.nr.dataentry.readwrite.ReadWriteAccessDesc;
import com.jiuqi.nr.datascheme.api.DataDimension;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DimensionType;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder;
import com.jiuqi.nr.definition.common.DataRegionKind;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.efdc.service.DataCenterService;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.fileupload.FilesUploadReturnInfo;
import com.jiuqi.nr.fileupload.service.CheckUploadFileService;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.params.input.EntityQueryByKeyInfo;
import com.jiuqi.nr.jtable.params.output.EntityByKeyReturnInfo;
import com.jiuqi.nr.jtable.params.output.EntityData;
import com.jiuqi.nr.jtable.service.IJtableDataEngineService;
import com.jiuqi.nr.jtable.service.IJtableEntityService;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.openapi.OpenApiValidateTokenDTO;
import com.jiuqi.va.feign.client.OpenApiRunClient;
import com.jiuqi.va.openapi.service.VaOpenApiService;
import io.jsonwebtoken.Claims;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.shiro.codec.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

@Service
public class DataDockingServiceImpl
implements DataDockingService {
    private static final Logger logger = LoggerFactory.getLogger(DataDockingServiceImpl.class);
    private static final String LOGGER_PREFIX = "\u7b2c\u4e09\u65b9\u6570\u636e\u5bf9\u63a5-";
    @Autowired
    private IRunTimeViewController iRunTimeViewController;
    @Autowired
    private IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    @Autowired
    private IRuntimeDataSchemeService iRuntimeDataSchemeService;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private IJtableEntityService entityService;
    @Autowired
    private IDataAccessProvider dataAccessProvider;
    @Autowired
    private UserService<User> userService;
    @Autowired
    private SystemUserService sysUserService;
    @Autowired
    private OpenApiRunClient openApiClient;
    @Autowired(required=false)
    private DataDockingOrgMappingService dataDockingOrgMappingService;
    @Autowired
    private VaOpenApiService openApiService;
    @Autowired
    private FilePoolService filePoolService;
    @Autowired
    private FileOperationService fileOperationService;
    @Autowired(required=false)
    private DataDockingFileFetchService dataDockingFileFetchService;
    @Autowired
    private CheckUploadFileService checkUploadFileService;
    @Autowired
    private AttachmentOperationService attachmentOperationService;
    @Autowired
    private IAttachmentService attachmentService;
    @Autowired
    private AttachmentFileAreaService attachmentFileAreaService;
    @Autowired
    private DataDockingFormDataChangeDao dataDockingFormDataChangeDao;
    @Autowired
    private IJtableDataEngineService jtableDataEngineService;
    @Autowired
    private DataCenterService dataService;
    @Value(value="${jiuqi.gc.data-docking.currency:CNY}")
    private String currency;
    @Value(value="${jiuqi.gc.data-docking.validToken:true}")
    private Boolean validToken;
    @Value(value="${jiuqi.gc.data-docking.afterCalculate:false}")
    private Boolean afterCalculate;
    @Value(value="${jiuqi.gc.data-docking.enumMapping:false}")
    private Boolean enumMapping;

    @Override
    public DataDockingResponse saveData(DataDockingVO dataDockingVO) {
        List<DataDockingBlockDTO> dataDockingBlockDTOS;
        String errMsg;
        logger.info("\u7b2c\u4e09\u65b9\u6570\u636e\u5bf9\u63a5-\u8bf7\u6c42\u53c2\u6570={}", (Object)JsonUtils.writeValueAsString((Object)dataDockingVO));
        LogHelper.info((String)LOGGER_PREFIX, (String)("\u4fdd\u5b58\u62a5\u8868\u6570\u636e\u8bf7\u6c42\u53c2\u6570=" + JsonUtils.writeValueAsString((Object)dataDockingVO)));
        ArrayList<String> messages = new ArrayList<String>();
        DataDockingResponse dataDockingResponse = new DataDockingResponse(dataDockingVO.getSn(), dataDockingVO.getRequestTime(), messages);
        if (this.validToken.booleanValue() && !StringUtils.isEmpty((String)(errMsg = this.validToken()))) {
            messages.add("\u63a5\u53e3\u8ba4\u8bc1\u5931\u8d25:" + errMsg);
            return dataDockingResponse;
        }
        List data = dataDockingVO.getData();
        if (CollectionUtils.isEmpty((Collection)data)) {
            logger.error("\u7b2c\u4e09\u65b9\u6570\u636e\u5bf9\u63a5-\u53c2\u6570\u6570\u636e\u4e3a\u7a7a");
            messages.add("\u53c2\u6570\u6570\u636e\u4e3a\u7a7a");
            return dataDockingResponse;
        }
        this.initUser();
        try {
            dataDockingBlockDTOS = this.initParam(data);
        }
        catch (Exception e) {
            logger.error("\u7b2c\u4e09\u65b9\u6570\u636e\u5bf9\u63a5-\u521d\u59cb\u5316\u53c2\u6570\u5f02\u5e38", e);
            messages.add(e.getMessage());
            return dataDockingResponse;
        }
        DataDockingService dataDockingService = (DataDockingService)SpringContextUtils.getBean(DataDockingServiceImpl.class);
        for (DataDockingBlockDTO dataDockingBlockDTO : dataDockingBlockDTOS) {
            String errMsg2 = dataDockingService.saveRegionData(dataDockingBlockDTO);
            if (StringUtils.isEmpty((String)errMsg2)) continue;
            messages.add(errMsg2);
        }
        if (!CollectionUtils.isEmpty(messages)) {
            logger.error("\u7b2c\u4e09\u65b9\u6570\u636e\u5bf9\u63a5-\u63a8\u9001\u5931\u8d25\u8be6\u60c5\u4fe1\u606f\uff1a" + JsonUtils.writeValueAsString((Object)dataDockingResponse));
            return dataDockingResponse;
        }
        return null;
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public String saveRegionData(DataDockingBlockDTO dataDockingBlockDTO) {
        try {
            IDataTable destResult;
            DataRegionKind regionKind = dataDockingBlockDTO.getDataRegionDefine().getRegionKind();
            List fieldDefines = dataDockingBlockDTO.getDataDockingBlockVO().getFieldDefines();
            if (!DataRegionKind.DATA_REGION_SIMPLE.equals((Object)regionKind)) {
                FieldDefine orderDefine = null;
                try {
                    orderDefine = this.dataDefinitionRuntimeController.queryFieldByCodeInTable("FLOATORDER", dataDockingBlockDTO.getTableDefine().getKey());
                }
                catch (Exception e) {
                    logger.error("\u7b2c\u4e09\u65b9\u6570\u636e\u5bf9\u63a5-\u67e5\u8be2\u6d6e\u52a8\u884c\u6392\u5e8f\u6307\u6807\u5931\u8d25", e);
                    throw new BusinessRuntimeException("\u7b2c\u4e09\u65b9\u6570\u636e\u5bf9\u63a5-\u67e5\u8be2\u6d6e\u52a8\u884c\u6392\u5e8f\u6307\u6807\u5931\u8d25");
                }
                fieldDefines.add(orderDefine);
            }
            List fieldRaws = dataDockingBlockDTO.getDataDockingBlockVO().getFieldValues();
            ExecutorContext context = new ExecutorContext(this.dataDefinitionRuntimeController);
            context.setUseDnaSql(false);
            IDataQuery dataQuery = this.getDataQuery(dataDockingBlockDTO, fieldDefines);
            try {
                destResult = dataQuery.executeQuery(context);
            }
            catch (Exception e) {
                LogHelper.info((String)LOGGER_PREFIX, (String)("\u6570\u636e\u5f15\u64ce\u67e5\u8be2\u51fa\u9519\uff1a" + e.getMessage()));
                logger.error("\u6570\u636e\u5f15\u64ce\u67e5\u8be2\u51fa\u9519,\u533a\u57df\u53c2\u6570={}", (Object)JsonUtils.writeValueAsString((Object)dataDockingBlockDTO));
                throw new RuntimeException("\u6570\u636e\u5f15\u64ce\u67e5\u8be2\u51fa\u9519", e);
            }
            if (DataRegionKind.DATA_REGION_SIMPLE.equals((Object)regionKind)) {
                int count = destResult.getCount();
                IDataRow dataRow = count == 0 ? destResult.appendRow(dataDockingBlockDTO.getDimensionValueSet()) : destResult.getItem(0);
                List fieldValueList = (List)fieldRaws.get(0);
                for (int i = 0; i < fieldDefines.size(); ++i) {
                    dataRow.setValue((FieldDefine)fieldDefines.get(i), fieldValueList.get(i));
                }
            } else {
                destResult.deleteAll();
                for (int i = 0; i < fieldRaws.size(); ++i) {
                    List fieldValues = (List)fieldRaws.get(i);
                    DimensionValueSet rowKey = new DimensionValueSet(dataDockingBlockDTO.getDimensionValueSet());
                    rowKey.setValue("RECORDKEY", (Object)UUID.randomUUID());
                    IDataRow dataRow = destResult.appendRow(rowKey);
                    this.saveFloatFields(dataRow, fieldValues, fieldDefines, dataDockingBlockDTO, i + 1);
                }
            }
            destResult.commitChanges(true);
            if (this.afterCalculate.booleanValue()) {
                this.afterCalculate(dataDockingBlockDTO);
            }
            String message = "\u4efb\u52a1\uff1a" + dataDockingBlockDTO.getTaskDefine().getTaskCode() + "-\u8868\u5355\uff1a" + dataDockingBlockDTO.getFormDefine().getFormCode() + "\u7b2c" + (dataDockingBlockDTO.getBlockLocation() + 1) + "\u4e2a\u533a\u57df\u6570\u636e\u66f4\u65b0\u3002";
            LogHelper.info((String)LOGGER_PREFIX, (String)message);
            DataDockingFormDataChangeEO dataDockingFormDataChangeEO = new DataDockingFormDataChangeEO();
            dataDockingFormDataChangeEO.setId(UUIDUtils.newUUIDStr());
            dataDockingFormDataChangeEO.setTaskCode(dataDockingBlockDTO.getTaskDefine().getTaskCode());
            dataDockingFormDataChangeEO.setUnitCode(dataDockingBlockDTO.getDimensionValueSet().getValue("MD_ORG").toString());
            dataDockingFormDataChangeEO.setDataTime(dataDockingBlockDTO.getDimensionValueSet().getValue("DATATIME").toString());
            dataDockingFormDataChangeEO.setFormCode(dataDockingBlockDTO.getFormDefine().getFormCode());
            dataDockingFormDataChangeEO.setTableCode(dataDockingBlockDTO.getTableDefine().getCode());
            dataDockingFormDataChangeEO.setDimStr(JsonUtils.writeValueAsString((Object)dataDockingBlockDTO.getDimensionValueSet().toString()));
            dataDockingFormDataChangeEO.setCreateTime(new Date());
            this.dataDockingFormDataChangeDao.add((BaseEntity)dataDockingFormDataChangeEO);
        }
        catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("\u4efb\u52a1\uff1a").append(dataDockingBlockDTO.getTaskDefine().getTaskCode()).append("-\u8868\u5355\uff1a").append(dataDockingBlockDTO.getFormDefine().getFormCode()).append("\u7b2c").append(dataDockingBlockDTO.getBlockLocation() + 1).append("\u4e2a\u533a\u57df\u6570\u636e\u4fdd\u5b58\u5931\u8d25\uff0c\u5931\u8d25\u8be6\u60c5\uff1a").append(e.getMessage());
            logger.error(stringBuilder.toString(), e);
            return stringBuilder.toString();
        }
        return null;
    }

    @Override
    public DataDockingVO queryData(DataDockingQueryVO dataDockingQueryVO) {
        String errMsg;
        logger.info("\u7b2c\u4e09\u65b9\u6570\u636e\u5bf9\u63a5-\u8bf7\u6c42\u53c2\u6570={}", (Object)JsonUtils.writeValueAsString((Object)dataDockingQueryVO));
        LogHelper.info((String)LOGGER_PREFIX, (String)("\u67e5\u8be2\u62a5\u8868\u6570\u636e\u8bf7\u6c42\u53c2\u6570\uff1a" + JsonUtils.writeValueAsString((Object)dataDockingQueryVO)));
        if (this.validToken.booleanValue() && !StringUtils.isEmpty((String)(errMsg = this.validToken()))) {
            throw new BusinessRuntimeException("\u7b2c\u4e09\u65b9\u6570\u636e\u5bf9\u63a5-\u63a5\u53e3\u8ba4\u8bc1\u5931\u8d25:" + errMsg);
        }
        this.initUser();
        DataDockingProcessor dataDockingProcessor = DataDockingProcessor.newInstance(dataDockingQueryVO);
        return dataDockingProcessor.process();
    }

    private void afterCalculate(DataDockingBlockDTO dataDockingBlockDTO) {
        FormulaSchemeDefine defaultScheme = this.getDefaultScheme(dataDockingBlockDTO.getFormDefine().getFormScheme());
        if (ObjectUtils.isEmpty(defaultScheme)) {
            logger.info("\u672a\u627e\u5230\u9ed8\u8ba4\u7684\u516c\u5f0f\u65b9\u6848\uff0c\u4e0d\u6267\u884c\u4fdd\u5b58\u540e\u81ea\u52a8\u8fd0\u7b97");
            return;
        }
        ConcurrentHashMap<String, DimensionValue> dimensionSetMap = new ConcurrentHashMap<String, DimensionValue>();
        DimensionValueSet dimensionValueSet = dataDockingBlockDTO.getDimensionValueSet();
        for (int i = 0; i < dimensionValueSet.size(); ++i) {
            DimensionValue dimensionValue = new DimensionValue();
            dimensionValue.setName(dimensionValueSet.getName(i));
            dimensionValue.setValue(ConverterUtils.getAsString((Object)dimensionValueSet.getValue(i)));
            dimensionSetMap.put(dimensionValueSet.getName(i), dimensionValue);
        }
        JtableContext jtableContext = new JtableContext();
        jtableContext.setTaskKey(dataDockingBlockDTO.getTaskDefine().getKey());
        jtableContext.setDimensionSet(dimensionSetMap);
        ArrayList<String> formKeys = new ArrayList<String>();
        formKeys.add(dataDockingBlockDTO.getFormDefine().getKey());
        jtableContext.setFormulaSchemeKey(defaultScheme.getKey());
        this.jtableDataEngineService.calculate(jtableContext, formKeys);
    }

    private FormulaSchemeDefine getDefaultScheme(String fromScheme) {
        Optional<FormulaSchemeDefine> findDefault;
        FormulaSchemeDefine define = null;
        List formulaSchemes = this.dataService.getAllRPTFormulaSchemeDefinesByFormScheme(fromScheme);
        if (!formulaSchemes.isEmpty() && (findDefault = formulaSchemes.stream().filter(FormulaSchemeDefine::isDefault).findFirst()).isPresent()) {
            define = findDefault.get();
        }
        return define;
    }

    private void saveFloatFields(IDataRow dataRow, List<String> fieldValues, List<FieldDefine> fieldDefines, DataDockingBlockDTO dataDockingBlockDTO, int floatOrder) {
        for (int i = 0; i < fieldDefines.size(); ++i) {
            FieldDefine fieldDefine = fieldDefines.get(i);
            if (i == fieldDefines.size() - 1) {
                dataRow.setValue(fieldDefine, (Object)floatOrder);
                continue;
            }
            String fieldValue = fieldValues.get(i);
            if (!StringUtils.isEmpty((String)fieldDefine.getEntityKey())) {
                fieldValue = this.getValueByIsolateFieldDefine(fieldValue, fieldDefine, dataDockingBlockDTO);
            }
            dataRow.setValue(fieldDefine, (Object)fieldValue);
        }
    }

    private String getValueByIsolateFieldDefine(String value, FieldDefine fieldDefine, DataDockingBlockDTO dataDockingBlockDTO) {
        IEntityDefine entityDefine = this.entityMetaService.queryEntity(fieldDefine.getEntityKey());
        if (null != entityDefine && 0 != entityDefine.getIsolation()) {
            if (StringUtils.isEmpty((String)value)) {
                throw new BusinessRuntimeException("\u9694\u79bb\u5b57\u6bb5[" + fieldDefine.getCode() + "]\u503c\u4e0d\u80fd\u4e3a\u7a7a\u3002");
            }
            String entityKey = fieldDefine.getEntityKey();
            EntityQueryByKeyInfo entityQueryByKeyInfo = new EntityQueryByKeyInfo();
            entityQueryByKeyInfo.setEntityViewKey(entityKey);
            JtableContext context = new JtableContext();
            context.setTaskKey(dataDockingBlockDTO.getTaskDefine().getKey());
            context.setFormSchemeKey(dataDockingBlockDTO.getFormDefine().getFormScheme());
            Map dimensionSet = DimensionValueSetUtil.getDimensionSet((DimensionValueSet)dataDockingBlockDTO.getDimensionValueSet());
            context.setDimensionSet(dimensionSet);
            entityQueryByKeyInfo.setContext(context);
            entityQueryByKeyInfo.setEntityKey(value);
            EntityByKeyReturnInfo returnInfo = this.entityService.queryEntityDataByKey(entityQueryByKeyInfo);
            if (null == returnInfo || null == returnInfo.getEntity()) {
                throw new BusinessRuntimeException("\u9694\u79bb\u5b57\u6bb5[" + fieldDefine.getCode() + "]\u7684\u503c[" + value + "]\u627e\u4e0d\u5230\u5bf9\u5e94\u7684\u57fa\u7840\u6570\u636e\u3002");
            }
            EntityData entity = returnInfo.getEntity();
            return entity.getId();
        }
        return value;
    }

    private List<DataDockingBlockDTO> initParam(List<DataDockingTaskVO> datas) throws Exception {
        GcBaseDataCenterTool instance = GcBaseDataCenterTool.getInstance();
        List enumMappingBaseData = instance.queryBasedataItems("MD_ENUM_MAPPING");
        Map<Object, List<GcBaseData>> form2FieldMap = enumMappingBaseData.stream().filter(item -> Objects.nonNull(item.getFieldVal("FORMCODE")) && Objects.nonNull(item.getFieldVal("FIELDCODE")) && Objects.nonNull(item.getFieldVal("ENUMNAME"))).collect(Collectors.groupingBy(item -> item.getFieldVal("FORMCODE")));
        ArrayList<DataDockingBlockDTO> dataDockingBlockDTOS = new ArrayList<DataDockingBlockDTO>();
        for (DataDockingTaskVO dataDockingTaskVO : datas) {
            String taskCode = dataDockingTaskVO.getTaskCode();
            List taskDatas = dataDockingTaskVO.getTaskData();
            TaskDefine taskDefine = this.iRunTimeViewController.queryTaskDefineByCode(taskCode);
            if (Objects.isNull(taskDefine)) {
                LogHelper.info((String)LOGGER_PREFIX, (String)("\u83b7\u53d6\u6570\u636e[\u4efb\u52a1\u5b9a\u4e49]\u4e3a\u7a7a\uff0c[taskCode]:" + taskCode));
                logger.error("\u7b2c\u4e09\u65b9\u6570\u636e\u5bf9\u63a5-\u83b7\u53d6\u6570\u636e[\u4efb\u52a1\u5b9a\u4e49]\u4e3a\u7a7a\uff0c[taskCode]:" + taskCode);
                throw new BusinessRuntimeException("\u7b2c\u4e09\u65b9\u6570\u636e\u5bf9\u63a5-\u83b7\u53d6\u6570\u636e[\u4efb\u52a1\u5b9a\u4e49]\u4e3a\u7a7a\uff0c[taskCode]:" + taskCode);
            }
            for (DataDockingTaskDataVO dataDockingTaskDataVO : taskDatas) {
                String unitCode = dataDockingTaskDataVO.getUnitCode();
                if (Objects.nonNull(this.dataDockingOrgMappingService)) {
                    unitCode = this.dataDockingOrgMappingService.orgCodeConvert(unitCode);
                }
                if (StringUtils.isEmpty((String)unitCode)) {
                    LogHelper.info((String)LOGGER_PREFIX, (String)"\u6620\u5c04\u4e45\u5176\u5355\u4f4d\u6807\u8bc6\u4e3a\u7a7a");
                    logger.error("\u7b2c\u4e09\u65b9\u6570\u636e\u5bf9\u63a5-\u6620\u5c04\u4e45\u5176\u5355\u4f4d\u6807\u8bc6\u4e3a\u7a7a");
                    throw new BusinessRuntimeException("\u7b2c\u4e09\u65b9\u6570\u636e\u5bf9\u63a5-\u6620\u5c04\u4e45\u5176\u5355\u4f4d\u6807\u8bc6\u4e3a\u7a7a");
                }
                String year = dataDockingTaskDataVO.getYear();
                String period = dataDockingTaskDataVO.getPeriod();
                String dataTime = this.getPeriodStr(Integer.parseInt(year), Integer.parseInt(period), taskDefine.getDataScheme());
                Map dimension = dataDockingTaskDataVO.getDimension();
                this.validUnitAuth(taskDefine, dataTime, unitCode);
                DimensionValueSet dimensionValueSet = this.getDimensionValueSet(taskDefine, dataTime, unitCode, dimension);
                List forms = dataDockingTaskDataVO.getForms();
                boolean formCheckError = false;
                StringBuilder stringBuilder = new StringBuilder();
                for (DataDockingFormVO dataDockingFormVO : forms) {
                    String formCode = dataDockingFormVO.getFormCode();
                    List datablocks = dataDockingFormVO.getDataBlocks();
                    if (CollectionUtils.isEmpty((Collection)datablocks)) {
                        logger.error("\u7b2c\u4e09\u65b9\u6570\u636e\u5bf9\u63a5-\u53c2\u6570\u533a\u57df\u4e3a\u7a7a\uff0c[formCode]:" + formCode);
                        continue;
                    }
                    FormDefine formDefine = this.getFormDefine(dataTime, taskDefine.getKey(), formCode);
                    if (Objects.isNull(formDefine)) {
                        LogHelper.info((String)LOGGER_PREFIX, (String)("\u83b7\u53d6\u6570\u636e\u8868\u5355\u4fe1\u606f\u4e3a\u7a7a\uff0c[formCode]:" + formCode));
                        logger.error("\u7b2c\u4e09\u65b9\u6570\u636e\u5bf9\u63a5-\u83b7\u53d6\u6570\u636e\u8868\u5355\u4fe1\u606f\u4e3a\u7a7a\uff0c[formCode]:" + formCode);
                        throw new BusinessRuntimeException("\u7b2c\u4e09\u65b9\u6570\u636e\u5bf9\u63a5-\u83b7\u53d6\u6570\u636e\u8868\u5355\u4fe1\u606f\u4e3a\u7a7a\uff0c[formCode]:" + formCode);
                    }
                    DimensionParamsVO dimensionParamsVO = new DimensionParamsVO();
                    dimensionParamsVO.setTaskId(taskDefine.getKey());
                    dimensionParamsVO.setSchemeId(formDefine.getFormScheme());
                    dimensionParamsVO.setOrgId(unitCode);
                    String orgCategory = DimensionUtils.getDwEntitieTableByTaskKey((String)taskDefine.getKey());
                    dimensionParamsVO.setOrgType(Objects.requireNonNull(orgCategory));
                    dimensionParamsVO.setOrgTypeId(String.valueOf(dimensionValueSet.getValue("MD_GCORGTYPE")));
                    dimensionParamsVO.setPeriodStr(String.valueOf(dimensionValueSet.getValue("DATATIME")));
                    dimensionParamsVO.setCurrency(String.valueOf(dimensionValueSet.getValue("MD_CURRENCY")));
                    dimensionParamsVO.setCurrencyId(String.valueOf(dimensionValueSet.getValue("MD_CURRENCY")));
                    dimensionParamsVO.setSelectAdjustCode(String.valueOf(dimensionValueSet.getValue("ADJUST")));
                    String errorMessage = this.checkUnitFormData(dimensionParamsVO, formDefine.getKey());
                    if (!StringUtils.isEmpty((String)errorMessage)) {
                        formCheckError = true;
                        stringBuilder.append(errorMessage).append("-[\u8868\u5355]").append(formCode).append(",[\u5355\u4f4d]").append(unitCode).append(";");
                        LogHelper.info((String)LOGGER_PREFIX, (String)(errorMessage + "-[\u8868\u5355]" + formCode + ",[\u5355\u4f4d]" + unitCode));
                        logger.error(LOGGER_PREFIX + errorMessage + "[\u8868\u5355]" + formCode + ",[\u5355\u4f4d]" + unitCode);
                    }
                    if (formCheckError) continue;
                    List dataRegionDefines = this.iRunTimeViewController.getAllRegionsInForm(formDefine.getKey());
                    for (DataRegionDefine dataRegionDefine : dataRegionDefines) {
                        Set tableCodeSet;
                        List fieldKeys = this.iRunTimeViewController.getFieldKeysInRegion(dataRegionDefine.getKey());
                        if (CollectionUtils.isEmpty((Collection)fieldKeys)) continue;
                        try {
                            List tableDefines = this.dataDefinitionRuntimeController.queryTableDefinesByFields((Collection)fieldKeys);
                            tableCodeSet = tableDefines.stream().map(UniversalTableDefine::getCode).collect(Collectors.toSet());
                        }
                        catch (Exception e) {
                            LogHelper.info((String)LOGGER_PREFIX, (String)("\u67e5\u8be2\u6570\u636e\u6307\u6807\u5b9a\u4e49\u5f02\u5e38\uff0c[formCode]:" + formCode));
                            logger.error("\u7b2c\u4e09\u65b9\u6570\u636e\u5bf9\u63a5-\u83b7\u53d6\u6570\u636e\u8868\u5355\u4fe1\u606f\u4e3a\u7a7a\uff0c[formCode]:" + formCode);
                            throw new BusinessRuntimeException("\u7b2c\u4e09\u65b9\u6570\u636e\u5bf9\u63a5-\u83b7\u53d6\u6570\u636e\u8868\u5355\u4fe1\u606f\u4e3a\u7a7a\uff0c[formCode]:" + formCode);
                        }
                        for (int i = 0; i < datablocks.size(); ++i) {
                            DataDockingBlockVO dataDockingBlockVO = (DataDockingBlockVO)datablocks.get(i);
                            if (this.enumMapping.booleanValue()) {
                                this.handleField(formDefine, dataDockingBlockVO, form2FieldMap);
                            }
                            List fieldCodes = dataDockingBlockVO.getFieldCodes();
                            List fieldValues = dataDockingBlockVO.getFieldValues();
                            if (CollectionUtils.isEmpty((Collection)fieldCodes) || CollectionUtils.isEmpty((Collection)fieldValues)) continue;
                            String fieldCodeTem = (String)fieldCodes.get(0);
                            DataDockingBlockDTO dataDockingBlockDTO = null;
                            if (fieldCodeTem.contains("[") && fieldCodeTem.contains("]")) {
                                int beginIdx = fieldCodeTem.indexOf("[");
                                String tableCode = fieldCodeTem.substring(0, beginIdx);
                                if (tableCodeSet.contains(tableCode)) {
                                    dataDockingBlockDTO = new DataDockingBlockDTO();
                                    dataDockingBlockDTO.setTaskDefine(taskDefine);
                                    dataDockingBlockDTO.setDimensionValueSet(dimensionValueSet);
                                    dataDockingBlockDTO.setFormDefine(formDefine);
                                    dataDockingBlockDTO.setDataDockingBlockVO(dataDockingBlockVO);
                                    dataDockingBlockDTO.setDataRegionDefine(dataRegionDefine);
                                    dataDockingBlockDTO.setBlockLocation(i);
                                    TableDefine tableDefine = this.dataDefinitionRuntimeController.queryTableDefineByCode(tableCode);
                                    dataDockingBlockDTO.setTableDefine(tableDefine);
                                    dataDockingBlockDTOS.add(dataDockingBlockDTO);
                                }
                            } else {
                                FieldDefine fieldDefine = this.dataDefinitionRuntimeController.queryFieldDefineByCodeInRange((Collection)fieldKeys, fieldCodeTem);
                                if (Objects.nonNull(fieldDefine)) {
                                    dataDockingBlockDTO = new DataDockingBlockDTO();
                                    dataDockingBlockDTO.setTaskDefine(taskDefine);
                                    dataDockingBlockDTO.setDimensionValueSet(dimensionValueSet);
                                    dataDockingBlockDTO.setFormDefine(formDefine);
                                    dataDockingBlockDTO.setDataDockingBlockVO(dataDockingBlockVO);
                                    dataDockingBlockDTO.setDataRegionDefine(dataRegionDefine);
                                    dataDockingBlockDTO.setBlockLocation(i);
                                    TableDefine tableDefine = this.dataDefinitionRuntimeController.queryTableDefine(fieldDefine.getOwnerTableKey());
                                    dataDockingBlockDTO.setTableDefine(tableDefine);
                                    dataDockingBlockDTOS.add(dataDockingBlockDTO);
                                }
                            }
                            if (!Objects.nonNull(dataDockingBlockDTO)) continue;
                            try {
                                List<FieldDefine> fieldDefines = this.getFieldDefines(dataDockingBlockDTO);
                                dataDockingBlockVO.setFieldDefines(fieldDefines);
                                this.preHandlerData(dataDockingBlockDTO, taskDefine, dataTime, formDefine, dimensionValueSet);
                                continue;
                            }
                            catch (Exception e) {
                                logger.error("\u7b2c\u4e09\u65b9\u6570\u636e\u5bf9\u63a5-\u6307\u6807\u9884\u5904\u7406\u6267\u884c\u5f02\u5e38\uff1a" + e.getMessage(), e);
                                throw new BusinessRuntimeException((Throwable)e);
                            }
                        }
                    }
                }
                if (stringBuilder.length() <= 0) continue;
                throw new BusinessRuntimeException(stringBuilder.toString());
            }
        }
        return dataDockingBlockDTOS;
    }

    private String getPeriodStr(int year, int period, String dataSchemeKey) {
        List dataDimensions = this.iRuntimeDataSchemeService.getDataSchemeDimension(dataSchemeKey, DimensionType.PERIOD);
        PeriodType periodType = ((DataDimension)dataDimensions.get(0)).getPeriodType();
        YearPeriodDO periodStr = YearPeriodUtil.transform(null, (int)year, (int)periodType.type(), (int)period);
        return periodStr.toString();
    }

    private DimensionValueSet getDimensionValueSet(TaskDefine taskDefine, String dataTime, String mdCode, Map<String, Object> dimension) {
        DimensionValueSet dimensionValueSet = new DimensionValueSet();
        dimensionValueSet.setValue("MD_ORG", (Object)mdCode);
        dimensionValueSet.setValue("DATATIME", (Object)dataTime);
        if (!StringUtils.isEmpty((String)taskDefine.getDims())) {
            String[] dimEntityIds;
            for (String dimEntityId : dimEntityIds = taskDefine.getDims().split(";")) {
                Object dimValue;
                TableModelDefine dimTableModelDefine = this.getEntity(dimEntityId);
                if (dimTableModelDefine == null || StringUtils.isEmpty((String)dimTableModelDefine.getName())) continue;
                String dimCode = dimTableModelDefine.getName();
                Object object = dimValue = Objects.isNull(dimension) || dimension.isEmpty() || Objects.isNull(dimension.get(dimCode)) ? null : dimension.get(dimCode);
                if (Objects.isNull(dimValue)) {
                    if ("MD_CURRENCY".equals(dimCode)) {
                        dimensionValueSet.setValue(dimCode, (Object)this.currency);
                        continue;
                    }
                    if ("MD_GCORGTYPE".equals(dimCode)) {
                        String orgType = this.getOrgTypeByOrgCode(mdCode, taskDefine.getDw(), dataTime);
                        dimensionValueSet.setValue(dimCode, (Object)orgType);
                        continue;
                    }
                    if (!"MD_GCADJTYPE".equals(dimCode)) continue;
                    dimensionValueSet.setValue(dimCode, (Object)GCAdjTypeEnum.BEFOREADJ.getCode());
                    continue;
                }
                dimensionValueSet.setValue(dimCode, dimValue);
            }
        }
        if (DimensionUtils.isExistAdjust((String)taskDefine.getKey())) {
            if (Objects.isNull(dimension) || Objects.isNull(dimension.get("ADJUST"))) {
                dimensionValueSet.setValue("ADJUST", (Object)"0");
            } else {
                dimensionValueSet.setValue("ADJUST", dimension.get("ADJUST"));
            }
        } else {
            dimensionValueSet.setValue("ADJUST", (Object)"0");
        }
        return dimensionValueSet;
    }

    private TableModelDefine getEntity(String entityId) {
        return this.entityMetaService.getTableModel(entityId);
    }

    private String getOrgTypeByOrgCode(String orgCode, String entityId, String dataTime) {
        YearPeriodObject yp;
        String orgTableName = this.getEntity(entityId).getName();
        GcOrgCenterService tool = GcOrgPublicTool.getInstance((String)orgTableName, (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)(yp = new YearPeriodObject(null, dataTime)));
        GcOrgCacheVO cacheVO = tool.getOrgByCode(orgCode);
        if (cacheVO != null && !StringUtils.isEmpty((String)cacheVO.getOrgTypeId())) {
            return cacheVO.getOrgTypeId();
        }
        throw new RuntimeException("\u83b7\u53d6\u6570\u636e\u5355\u4f4d\u4fe1\u606f\u4e3a\u7a7a\uff0c[unitCode]\u7684\u503c[" + orgCode + "]\u627e\u4e0d\u5230\u5bf9\u5e94\u7684\u5355\u4f4d\u4fe1\u606f\uff0c[orgType]:[" + orgTableName + "]\uff0c[\u65f6\u671f]:[" + dataTime + "]\u3002");
    }

    private FormDefine getFormDefine(String periodStr, String taskKey, String formCode) {
        try {
            SchemePeriodLinkDefine schemePeriodLinkDefine = this.iRunTimeViewController.querySchemePeriodLinkByPeriodAndTask(periodStr, taskKey);
            return this.iRunTimeViewController.queryFormByCodeInScheme(schemePeriodLinkDefine.getSchemeKey(), formCode);
        }
        catch (Exception e) {
            logger.error("\u83b7\u53d6\u6570\u636e[\u8868\u5355\u5b9a\u4e49]\u4e3a\u7a7a\uff0c[formCode]\uff1a" + formCode + " [taskKey]:" + taskKey + " [dataTime]:" + periodStr, e);
            return null;
        }
    }

    private String checkUnitFormData(DimensionParamsVO params, String formKey) {
        ReadWriteAccessDesc readWriteAccessDesc = FormUploadStateTool.getInstance().writeable(params, formKey);
        String errorMessage = null;
        if (!Boolean.TRUE.equals(readWriteAccessDesc.getAble())) {
            errorMessage = readWriteAccessDesc.getDesc();
        }
        return errorMessage;
    }

    private void validUnitAuth(TaskDefine taskDefine, String dataTime, String unitCode) {
        YearPeriodObject yp;
        String orgTableName = this.getEntity(taskDefine.getDw()).getName();
        GcOrgCenterService tool = GcOrgPublicTool.getInstance((String)orgTableName, (GcAuthorityType)GcAuthorityType.ACCESS, (YearPeriodObject)(yp = new YearPeriodObject(null, dataTime)));
        GcOrgCacheVO cacheVO = tool.getOrgByCode(unitCode);
        if (Objects.isNull(cacheVO)) {
            LogHelper.info((String)LOGGER_PREFIX, (String)("\u672a\u627e\u5230\u5355\u4f4d\uff1a" + unitCode + ", \u7528\u6237\uff1a" + NpContextHolder.getContext().getUserName() + ",\u673a\u6784\u7c7b\u578b\uff1a" + orgTableName + ",\u65f6\u671f\uff1a" + dataTime));
            logger.error("\u7b2c\u4e09\u65b9\u6570\u636e\u5bf9\u63a5-\u672a\u627e\u5230\u5355\u4f4d\uff1a" + unitCode + " ,\u7528\u6237\uff1a" + NpContextHolder.getContext().getUserName() + ",\u673a\u6784\u7c7b\u578b\uff1a" + orgTableName + ",\u65f6\u671f\uff1a" + dataTime);
            throw new BusinessRuntimeException("\u7b2c\u4e09\u65b9\u6570\u636e\u5bf9\u63a5-\u672a\u627e\u5230\u5355\u4f4d\uff1a" + unitCode + " ,\u7528\u6237\uff1a" + NpContextHolder.getContext().getUserName() + ",\u673a\u6784\u7c7b\u578b\uff1a" + orgTableName + ",\u65f6\u671f\uff1a" + dataTime);
        }
    }

    private List<FieldDefine> getFieldDefines(DataDockingBlockDTO dataDockingBlockDTO) throws Exception {
        List fieldCodes = dataDockingBlockDTO.getDataDockingBlockVO().getFieldCodes();
        ArrayList<FieldDefine> fieldDefines = new ArrayList<FieldDefine>();
        for (String fieldStr : fieldCodes) {
            FieldDefine fieldDefine;
            if (fieldStr.contains("[") && fieldStr.contains("]")) {
                int beginIdx = fieldStr.indexOf("[");
                int endIdx = fieldStr.indexOf("]");
                String tableCode = fieldStr.substring(0, beginIdx);
                String fieldCode = fieldStr.substring(beginIdx + 1, endIdx);
                TableDefine tableDefine = this.dataDefinitionRuntimeController.queryTableDefineByCode(tableCode);
                fieldDefine = this.dataDefinitionRuntimeController.queryFieldByCodeInTable(fieldCode, tableDefine.getKey());
            } else {
                List fieldKeys = this.iRunTimeViewController.getFieldKeysInRegion(dataDockingBlockDTO.getDataRegionDefine().getKey());
                fieldDefine = this.dataDefinitionRuntimeController.queryFieldDefineByCodeInRange((Collection)fieldKeys, fieldStr);
            }
            if (Objects.isNull(fieldDefine)) {
                logger.error("\u6307\u6807\u5b9a\u4e49\u4e3a\u7a7a\uff1a" + fieldStr);
                throw new BusinessRuntimeException("\u6307\u6807\u5b9a\u4e49\u4e3a\u7a7a\uff1a" + fieldStr);
            }
            fieldDefines.add(fieldDefine);
        }
        return fieldDefines;
    }

    public IDataQuery getDataQuery(DataDockingBlockDTO dataDockingBlockDTO, List<FieldDefine> fieldDefines) {
        DataRegionDefine dataRegionDefine = dataDockingBlockDTO.getDataRegionDefine();
        DimensionValueSet dimensionValueSet = dataDockingBlockDTO.getDimensionValueSet();
        return this.getDataQuery(dataRegionDefine, dimensionValueSet, fieldDefines);
    }

    public IDataQuery getDataQuery(DataRegionDefine dataRegionDefine, DimensionValueSet dimensionValueSet, List<FieldDefine> fieldDefines) {
        try {
            FormDefine formDefine = this.iRunTimeViewController.queryFormById(dataRegionDefine.getFormKey());
            QueryEnvironment queryEnvironment = new QueryEnvironment();
            queryEnvironment.setFormSchemeKey(formDefine.getFormScheme());
            queryEnvironment.setRegionKey(dataRegionDefine.getKey());
            queryEnvironment.setFormKey(formDefine.getKey());
            queryEnvironment.setFormCode(formDefine.getFormCode());
            IDataQuery dataQuery = this.dataAccessProvider.newDataQuery(queryEnvironment);
            dataQuery.setMasterKeys(dimensionValueSet);
            if (!CollectionUtils.isEmpty(fieldDefines)) {
                for (FieldDefine fieldDefine : fieldDefines) {
                    dataQuery.addColumn(fieldDefine);
                }
            }
            return dataQuery;
        }
        catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            throw new RuntimeException("\u6839\u636e\u67e5\u8be2\u73af\u5883\u83b7\u53d6\u67e5\u8be2\u63a5\u53e3\u5b9e\u4f8b\u5f02\u5e38", ex);
        }
    }

    private void initUser() {
        Optional sysUser;
        List allUsers = this.sysUserService.getAllUsers();
        if (CollectionUtils.isEmpty((Collection)allUsers)) {
            throw new BusinessRuntimeException("\u672a\u627e\u5230\u7cfb\u7edf\u7528\u6237");
        }
        String userName = ((SystemUser)allUsers.get(0)).getName();
        INvwaSystemOptionService service = (INvwaSystemOptionService)SpringContextUtils.getBean(INvwaSystemOptionService.class);
        String optValue = service.findValueById("DATADOCKING_ENABLE_USER_AUTH");
        if ("1".equals(optValue)) {
            userName = this.parseUserName();
        }
        User user = null;
        Optional userOptional = this.userService.findByUsername(userName);
        if (userOptional.isPresent()) {
            user = (User)userOptional.get();
        }
        if ((sysUser = this.sysUserService.findByUsername(userName)).isPresent()) {
            user = (User)sysUser.get();
        }
        Assert.isTrue(!ObjectUtils.isEmpty(user), "\u7b2c\u4e09\u65b9\u6570\u636e\u5bf9\u63a5-\u627e\u4e0d\u5230\u7528\u6237\u540d\u4e3a[" + userName + "]\u7684\u7528\u6237\u4fe1\u606f\u3002");
        NpContextImpl context = new NpContextImpl();
        NpContextIdentity contextIdentity = new NpContextIdentity();
        contextIdentity.setId(user.getId());
        contextIdentity.setTitle(user.getName());
        context.setIdentity((ContextIdentity)contextIdentity);
        context.setTenant("__default_tenant__");
        NpContextUser contextUser = new NpContextUser();
        contextUser.setId(user.getId());
        contextUser.setName(user.getName());
        contextUser.setNickname(user.getNickname());
        context.setUser((ContextUser)contextUser);
        NpContextHolder.setContext((NpContext)context);
    }

    private String validToken() {
        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        String token = request.getParameter("token");
        if (StringUtils.isEmpty((String)token)) {
            logger.error("\u7b2c\u4e09\u65b9\u6570\u636e\u5bf9\u63a5-token\u503c\u4e3a\u7a7a");
            return "\u7b2c\u4e09\u65b9\u6570\u636e\u5bf9\u63a5-token\u503c\u4e3a\u7a7a";
        }
        OpenApiValidateTokenDTO openApi = new OpenApiValidateTokenDTO();
        openApi.setToken(token);
        openApi.setApiName("DataDocking");
        R vaRs = this.openApiClient.validateToken(openApi);
        if (0 != vaRs.getCode()) {
            logger.error(LOGGER_PREFIX + vaRs.getMsg());
            return LOGGER_PREFIX + vaRs.getMsg();
        }
        return "";
    }

    private String parseUserName() {
        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        String token = request.getParameter("token");
        Claims claims = this.openApiService.parseJWT(token);
        String subject = claims.getSubject();
        String[] strs = subject.split("\\#");
        String username = "";
        if (strs.length > 2) {
            username = Base64.decodeToString((String)strs[2]);
        }
        if (StringUtils.isEmpty((String)username)) {
            throw new BusinessRuntimeException("\u5df2\u542f\u7528\u7528\u6237\u6743\u9650\uff0c\u83b7\u53d6\u8ba4\u8bc1token\u65f6\u7528\u6237[username]\u4e0d\u80fd\u4e3a\u7a7a\u3002");
        }
        return username;
    }

    public void preHandlerData(DataDockingBlockDTO dataDockingBlockDTO, TaskDefine taskDefine, String dataTime, FormDefine formDefine, DimensionValueSet dimensionValueSet) throws Exception {
        DataDockingBlockVO dataDockingBlockVO = dataDockingBlockDTO.getDataDockingBlockVO();
        List fieldValues = dataDockingBlockVO.getFieldValues();
        List fieldDefines = dataDockingBlockVO.getFieldDefines();
        for (int i = 0; i < fieldDefines.size(); ++i) {
            FieldDefine fieldDefine = (FieldDefine)fieldDefines.get(i);
            FieldType type = fieldDefine.getType();
            if (!FieldType.FIELD_TYPE_FILE.equals((Object)type)) continue;
            if (Objects.isNull(this.dataDockingFileFetchService)) {
                logger.error("\u7b2c\u4e09\u65b9\u6570\u636e\u5bf9\u63a5-\u6307\u6807\uff1a" + fieldDefine.getCode() + ",\u4e0d\u652f\u6301\u9644\u4ef6\u7c7b\u578b\u6307\u6807\uff1a\u672a\u627e\u5230\u9644\u4ef6\u83b7\u53d6\u6269\u5c55\u3002");
                throw new BusinessRuntimeException("\u6307\u6807\uff1a" + fieldDefine.getCode() + ",\u4e0d\u652f\u6301\u9644\u4ef6\u7c7b\u578b\u6307\u6807\uff1a\u672a\u627e\u5230\u9644\u4ef6\u83b7\u53d6\u6269\u5c55\u3002");
            }
            FileUploadContext fileUploadContext = new FileUploadContext();
            fileUploadContext.setTaskKey(taskDefine.getKey());
            SchemePeriodLinkDefine schemePeriodLinkDefine = this.iRunTimeViewController.querySchemePeriodLinkByPeriodAndTask(dataTime, taskDefine.getKey());
            DimensionCombinationBuilder dimensionCombinationBuilder = new DimensionCombinationBuilder(dimensionValueSet);
            DimensionCombination dimensionCombination = dimensionCombinationBuilder.getCombination();
            fileUploadContext.setFormSchemeKey(schemePeriodLinkDefine.getSchemeKey());
            fileUploadContext.setDataSchemeKey(taskDefine.getDataScheme());
            fileUploadContext.setFormKey(formDefine.getKey());
            fileUploadContext.setFieldKey(fieldDefine.getKey());
            fileUploadContext.setDimensionCombination(dimensionCombination);
            for (List fieldValue : fieldValues) {
                String value = (String)fieldValue.get(i);
                String groupKey = this.saveFileData(fileUploadContext, value, dataDockingBlockDTO.getDataRegionDefine(), fieldDefine);
                fieldValue.set(i, groupKey);
            }
        }
    }

    private String saveFileData(FileUploadContext fileUploadContext, String value, DataRegionDefine dataRegionDefine, FieldDefine fieldDefine) throws Exception {
        FilesUploadReturnInfo attachment;
        IDataRow item;
        AbstractData val;
        String asString;
        String[] paths = value.split(",");
        MultipartFile[] multipartFiles = null;
        for (String path : paths) {
            Object[] multipartFile = this.dataDockingFileFetchService.fetchFile(path);
            multipartFiles = (MultipartFile[])ArrayUtils.addAll(multipartFiles, (Object[])multipartFile);
        }
        ArrayList<FieldDefine> fieldDefines = new ArrayList<FieldDefine>();
        fieldDefines.add(fieldDefine);
        IDataQuery dataQuery = this.getDataQuery(dataRegionDefine, fileUploadContext.getDimensionCombination().toDimensionValueSet(), fieldDefines);
        ExecutorContext context = new ExecutorContext(this.dataDefinitionRuntimeController);
        context.setUseDnaSql(false);
        IDataTable iDataTable = dataQuery.executeQuery(context);
        if (iDataTable.getCount() > 0 && !StringUtils.isEmpty((String)(asString = (val = (item = iDataTable.getItem(0)).getValue(fieldDefine)).getAsString()))) {
            List fileInfoByGroup = this.fileOperationService.getFileInfoByGroup(asString);
            Set collect = fileInfoByGroup.stream().map(FileInfo::getKey).collect(Collectors.toSet());
            AombstoneFileInfo aombstoneFileInfo = new AombstoneFileInfo();
            aombstoneFileInfo.setDataSchemeKey(fileUploadContext.getDataSchemeKey());
            aombstoneFileInfo.setGroupKey(asString);
            aombstoneFileInfo.setFileKeys(collect);
            this.fileOperationService.deleteFile(aombstoneFileInfo);
        }
        if (!Boolean.TRUE.equals((attachment = this.checkUploadFileService.checkUploadFileInfo(multipartFiles, null, "attachment")).isAllIsSuccess())) {
            Map fileUploadReturnInfoMap = attachment.getFileUploadReturnInfoMap();
            StringBuilder builder = new StringBuilder();
            fileUploadReturnInfoMap.forEach((fileName, res) -> {
                if (!res.isSuccess()) {
                    builder.append("\u9644\u4ef6\uff1a").append((String)fileName).append(res.getMessage());
                }
            });
            throw new BusinessRuntimeException("\u6587\u4ef6\u4e0a\u4f20\u5931\u8d25\uff1a" + builder);
        }
        FilesUploadInfo filesUploadInfo = new FilesUploadInfo();
        filesUploadInfo.setCovered(false);
        filesUploadInfo.setFieldKey(fileUploadContext.getFieldKey());
        JtableContext jtableContext = new JtableContext();
        jtableContext.setTaskKey(fileUploadContext.getTaskKey());
        jtableContext.setFormSchemeKey(fileUploadContext.getFormSchemeKey());
        jtableContext.setDimensionSet(DimensionValueSetUtil.getDimensionSet((DimensionValueSet)fileUploadContext.getDimensionCombination().toDimensionValueSet()));
        jtableContext.setFormKey(fileUploadContext.getFormKey());
        filesUploadInfo.setContext(jtableContext);
        HashMap fileUploadInfoMap = new HashMap();
        filesUploadInfo.setFileUploadInfoMap(fileUploadInfoMap);
        attachment.getFileUploadReturnInfoMap().forEach((fileName, res) -> {
            AttachmentInfo attachmentInfo = new AttachmentInfo();
            attachmentInfo.setCovered(false);
            attachmentInfo.setOssFileKey(res.getFileInfoKey());
            fileUploadInfoMap.put(fileName, attachmentInfo);
        });
        FilesUploadInfo checkFilesUploadInfo = this.attachmentService.uploadVerification(filesUploadInfo);
        if (Objects.nonNull(checkFilesUploadInfo)) {
            filesUploadInfo = checkFilesUploadInfo;
        }
        return this.attachmentOperationService.uploadFiles(filesUploadInfo).getMessage();
    }

    private void handleField(FormDefine formDefine, DataDockingBlockVO dataDockingBlockVO, Map<Object, List<GcBaseData>> form2FieldMap) {
        String formCode = formDefine.getFormCode();
        if (!form2FieldMap.containsKey(formCode)) {
            return;
        }
        List<GcBaseData> gcBaseDataList = form2FieldMap.get(formCode);
        Map fieldCodeMap = gcBaseDataList.stream().collect(Collectors.toMap(item -> item.getFieldVal("FIELDCODE").toString(), Function.identity()));
        List fieldValues = dataDockingBlockVO.getFieldValues();
        List fieldCodes = dataDockingBlockVO.getFieldCodes();
        for (String fieldCode : fieldCodes) {
            if (!fieldCodeMap.containsKey(fieldCode)) continue;
            GcBaseData gcBaseData = (GcBaseData)fieldCodeMap.get(fieldCode);
            String enumname = gcBaseData.getFieldVal("ENUMNAME").toString();
            int i = fieldCodes.indexOf(fieldCode);
            for (List fieldValue : fieldValues) {
                List baseData;
                String value = (String)fieldValue.get(i);
                if (StringUtils.isEmpty((String)value) || CollectionUtils.isEmpty((Collection)(baseData = GcBaseDataCenterTool.getInstance().queryBasedataItems(enumname)))) continue;
                Map<String, String> title2CodeMap = baseData.stream().collect(Collectors.toMap(GcBaseData::getTitle, GcBaseData::getCode, (k1, k2) -> k1));
                if (title2CodeMap.containsKey(value)) {
                    String code = title2CodeMap.get(value);
                    fieldValue.set(i, code);
                    continue;
                }
                logger.error("\u8868\u5355\uff1a{}\uff0c\u6307\u6807\uff1a{}\uff0c\u5173\u8054\u57fa\u7840\u6570\u636e\uff1a{}\uff0c\u503c\uff1a{}\uff0c\u672a\u627e\u5230\u540d\u79f0\u7684\u6570\u636e\u9879", fieldCode, fieldCode, enumname, value);
            }
        }
    }
}

