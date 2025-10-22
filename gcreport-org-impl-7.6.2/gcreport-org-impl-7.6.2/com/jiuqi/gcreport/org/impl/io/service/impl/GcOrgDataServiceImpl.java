/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.DateUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.common.expimp.progress.common.ProgressDataImpl
 *  com.jiuqi.gcreport.org.api.event.GcOrgDataCacheBaseEvent
 *  com.jiuqi.gcreport.org.api.event.GcOrgDataItemChangeEvent
 *  com.jiuqi.gcreport.org.api.vo.FrontEndParams
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.OrgToJsonVO
 *  com.jiuqi.gcreport.org.api.vo.field.ExportConditionVO
 *  com.jiuqi.gcreport.org.api.vo.field.ExportMessageVO
 *  com.jiuqi.gcreport.org.api.vo.field.GcOrgFieldVO
 *  com.jiuqi.gcreport.org.api.vo.field.OrgFiledComponentVO
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  com.jiuqi.va.domain.basedata.BaseDataDO
 *  com.jiuqi.va.domain.basedata.BaseDataDTO
 *  com.jiuqi.va.domain.basedata.BaseDataOption$AuthType
 *  com.jiuqi.va.domain.basedata.BaseDataOption$QueryDataStructure
 *  com.jiuqi.va.domain.common.OrderNumUtil
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.org.OrgDTO
 *  com.jiuqi.va.feign.client.BaseDataClient
 *  com.jiuqi.va.organization.service.impl.help.OrgDataCacheService
 *  javax.annotation.Resource
 *  org.apache.commons.lang3.StringUtils
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.gcreport.org.impl.io.service.impl;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.DateUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.common.expimp.progress.common.ProgressDataImpl;
import com.jiuqi.gcreport.org.api.event.GcOrgDataCacheBaseEvent;
import com.jiuqi.gcreport.org.api.event.GcOrgDataItemChangeEvent;
import com.jiuqi.gcreport.org.api.vo.FrontEndParams;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.OrgToJsonVO;
import com.jiuqi.gcreport.org.api.vo.field.ExportConditionVO;
import com.jiuqi.gcreport.org.api.vo.field.ExportMessageVO;
import com.jiuqi.gcreport.org.api.vo.field.GcOrgFieldVO;
import com.jiuqi.gcreport.org.api.vo.field.OrgFiledComponentVO;
import com.jiuqi.gcreport.org.impl.base.GcOrgCodeConfig;
import com.jiuqi.gcreport.org.impl.base.InspectOrgUtils;
import com.jiuqi.gcreport.org.impl.base.LamdbaUtils;
import com.jiuqi.gcreport.org.impl.cache.dao.FGcOrgQueryDao;
import com.jiuqi.gcreport.org.impl.cache.dao.FGcOrgTypeVersionDao;
import com.jiuqi.gcreport.org.impl.cache.impl.GcOrgParam;
import com.jiuqi.gcreport.org.impl.cache.listener.GcOrgCacheListener;
import com.jiuqi.gcreport.org.impl.check.enums.FieldComponentEnum;
import com.jiuqi.gcreport.org.impl.fieldManager.service.GcFieldManagerService;
import com.jiuqi.gcreport.org.impl.io.dto.UploadContext;
import com.jiuqi.gcreport.org.impl.io.service.GcOrgDataService;
import com.jiuqi.gcreport.org.impl.io.utils.FormatValidationUtil;
import com.jiuqi.gcreport.org.impl.util.base.GcOrgCenterBase;
import com.jiuqi.gcreport.org.impl.util.base.OrgParamParse;
import com.jiuqi.gcreport.org.impl.util.internal.GcOrgBaseTool;
import com.jiuqi.gcreport.org.impl.util.internal.GcOrgMangerCenterTool;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import com.jiuqi.va.domain.basedata.BaseDataDO;
import com.jiuqi.va.domain.basedata.BaseDataDTO;
import com.jiuqi.va.domain.basedata.BaseDataOption;
import com.jiuqi.va.domain.common.OrderNumUtil;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.org.OrgDTO;
import com.jiuqi.va.feign.client.BaseDataClient;
import com.jiuqi.va.organization.service.impl.help.OrgDataCacheService;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFDataFormatter;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class GcOrgDataServiceImpl
implements GcOrgDataService {
    private static final String IGNORE_UPLOAD = "IGNORE_UPLOAD";
    private static final String ROWNUM = "ROWNUM";
    private static final Logger logger = LoggerFactory.getLogger(GcOrgDataServiceImpl.class);
    @Autowired
    private GcFieldManagerService gcFieldManagerService;
    @Autowired
    private OrgDataCacheService cacheService;
    @Resource
    private GcOrgCacheListener messageService;
    @Autowired
    IDataDefinitionRuntimeController runtimeController;
    @Autowired
    private DataModelService dataModelService;
    @Autowired
    private BaseDataClient baseDataClient;
    @Autowired
    private FGcOrgQueryDao gcOrgQueryDao;

    @Override
    public List<ExportMessageVO> uploadOrgData(MultipartFile uploadFile, ExportConditionVO conditionVO) {
        String fileName = uploadFile.getOriginalFilename();
        String type = fileName.substring(fileName.lastIndexOf("."));
        InputStream is = null;
        Workbook workbook = this.getWorkbook(uploadFile, type, is);
        return this.uploadOrgData(workbook, conditionVO);
    }

    @Override
    public List<ExportMessageVO> uploadOrgData(Workbook workbook, ExportConditionVO conditionVO) {
        return this.uploadOrgData(workbook, conditionVO, false);
    }

    @Override
    public List<ExportMessageVO> uploadOrgData(Workbook workbook, ExportConditionVO conditionVO, boolean isAllField) {
        if (StringUtils.isEmpty((CharSequence)conditionVO.getSn())) {
            conditionVO.setSn(UUIDUtils.newUUIDStr());
        }
        UploadContext uploadContext = new UploadContext();
        try {
            FrontEndParams params = new FrontEndParams(conditionVO.getSn(), 10, new ProgressDataImpl(conditionVO.getSn()));
            uploadContext.setFrontEnd(params);
            uploadContext.getFrontEnd().getProgressData().addProgressValueAndRefresh(5.0);
            List<OrgFiledComponentVO> fieldComponent = this.gcFieldManagerService.getFieldComponent(conditionVO.getTableName());
            Map<String, OrgFiledComponentVO> filedComponentMap = fieldComponent.stream().collect(Collectors.toMap(OrgFiledComponentVO::getCode, o -> o));
            if (filedComponentMap.containsKey("BBLX") && StringUtils.isEmpty((CharSequence)filedComponentMap.get("BBLX").getRefTableName())) {
                throw new BusinessRuntimeException("\u62a5\u8868\u7c7b\u578b\u672a\u5173\u8054\u57fa\u7840\u6570\u636e\uff0c\u4e0d\u5141\u8bb8\u5bfc\u5165");
            }
            if (filedComponentMap.containsKey("CURRENCYID") && StringUtils.isEmpty((CharSequence)filedComponentMap.get("CURRENCYID").getRefTableName())) {
                throw new BusinessRuntimeException("\u5e01\u79cd\u672a\u5173\u8054\u57fa\u7840\u6570\u636e\uff0c\u4e0d\u5141\u8bb8\u5bfc\u5165");
            }
            if (filedComponentMap.containsKey("CURRENCYIDS") && StringUtils.isEmpty((CharSequence)filedComponentMap.get("CURRENCYIDS").getRefTableName())) {
                throw new BusinessRuntimeException("\u62a5\u8868\u5e01\u79cd\u672a\u5173\u8054\u57fa\u7840\u6570\u636e\uff0c\u4e0d\u5141\u8bb8\u5bfc\u5165");
            }
            if (!this.clearOrgCache(conditionVO)) {
                throw new BusinessRuntimeException("\u5bfc\u5165\u524d\u6e05\u7a7a\u5355\u4f4d\u7f13\u5b58\u5931\u8d25\uff0c\u8bf7\u91cd\u8bd5");
            }
            List<Map<String, Object>> updateList = this.getUpdateList(workbook, conditionVO, isAllField).stream().filter(stringObjectMap -> StringUtils.isNotEmpty((CharSequence)stringObjectMap.get("CODE").toString())).collect(Collectors.toList());
            Assert.isNotEmpty(updateList, (String)"\u5bfc\u5165\u6587\u4ef6\u4e3a\u7a7a", (Object[])new Object[0]);
            params.setTotalCount(updateList.size());
            uploadContext.getFrontEnd().getProgressData().addProgressValueAndRefresh(3.0);
            HashMap<String, BaseDataDO> baseDataCache = new HashMap<String, BaseDataDO>(16);
            HashMap<String, OrgToJsonVO> orgDataCache = new HashMap<String, OrgToJsonVO>(16);
            this.initBaseDataCache(baseDataCache, fieldComponent);
            this.initOrgDataCache(orgDataCache, conditionVO);
            uploadContext.getFrontEnd().getProgressData().addProgressValueAndRefresh(4.0);
            uploadContext.setBaseDataCache(baseDataCache);
            uploadContext.setOrgDataCache(orgDataCache);
            uploadContext.setConditionVO(conditionVO);
            uploadContext.setSecondUploadData(new ArrayList<OrgToJsonVO>());
            uploadContext.setFiledComponentMap(filedComponentMap);
            uploadContext.setAutoCalcField(InspectOrgUtils.checkEnableAutoCalc(conditionVO.getOrgType()));
            this.checkOrgCodeLength(updateList);
            uploadContext.getFrontEnd().getProgressData().addProgressValueAndRefresh(5.0);
            uploadContext.getFrontEnd().getProgressData().setResult((Object)"\u5f00\u59cb\u5bfc\u5165");
            uploadContext.setProcessStartTime(System.currentTimeMillis());
            List<OrgToJsonVO> updateTree = this.listToTree(updateList);
            LogHelper.info((String)"\u5408\u5e76-\u5408\u5e76\u5355\u4f4d\u7ba1\u7406", (String)("\u5bfc\u5165-\u673a\u6784\u7c7b\u578b" + conditionVO.getOrgType() + "-\u7248\u672c" + conditionVO.getOrgVer()), (String)"");
            this.processOrgData(updateTree, uploadContext);
            uploadContext.setEnableTransformOrgField(true);
            uploadContext.getConditionVO().setExecuteOnDuplicate(Boolean.valueOf(true));
            List<OrgToJsonVO> secondUploadData = uploadContext.getSecondUploadData();
            if (!CollectionUtils.isEmpty(secondUploadData)) {
                uploadContext.getFrontEnd().getProgressData().setResult((Object)"\u9700\u8981\u4e8c\u6b21\u5bfc\u5165\uff0c\u5f00\u59cb\u5904\u7406");
                ArrayList<OrgToJsonVO> list = new ArrayList<OrgToJsonVO>(secondUploadData);
                this.processOrgData(list, uploadContext);
            }
            uploadContext.getFrontEnd().getProgressData().setProgressValue(100.0);
            uploadContext.getFrontEnd().getProgressData().setSuccessFlag(true);
        }
        catch (Exception e) {
            logger.error("\u5bfc\u5165\u5931\u8d25", e);
            LogHelper.error((String)"\u5408\u5e76-\u5408\u5e76\u5355\u4f4d\u7ba1\u7406", (String)("\u5bfc\u5165-\u673a\u6784\u7c7b\u578b" + conditionVO.getOrgType() + "-\u7248\u672c" + conditionVO.getOrgVer() + "\u5f02\u5e38"), (String)e.getMessage());
            uploadContext.getFrontEnd().getProgressData().setSuccessFlagAndRefresh(false);
            throw new BusinessRuntimeException(e.getMessage());
        }
        InspectOrgUtils.syncOrgCache(conditionVO.getOrgType());
        return uploadContext.getResultList();
    }

    private boolean clearOrgCache(ExportConditionVO conditionVO) {
        boolean flag = true;
        try {
            String orgType = conditionVO.getOrgType();
            GcOrgParam param = OrgParamParse.createDefaultParam(orgType, new Date(), vo -> {});
            this.cacheService.syncCache((OrgDTO)param, true);
            this.messageService.publishRefreshMessage((GcOrgDataCacheBaseEvent)new GcOrgDataItemChangeEvent(orgType));
        }
        catch (Exception e) {
            logger.error("\u6e05\u7a7a\u7f13\u5b58\u5931\u8d25", e);
            flag = false;
        }
        return flag;
    }

    private void initOrgDataCache(Map<String, OrgToJsonVO> orgDataCache, ExportConditionVO conditionVO) {
        List<OrgToJsonVO> orgToJsonVOS;
        if (conditionVO.getOrgType().equalsIgnoreCase("MD_ORG")) {
            GcOrgBaseTool baseTool = GcOrgBaseTool.getInstance();
            orgToJsonVOS = baseTool.listOrg();
        } else {
            GcOrgMangerCenterTool instance = GcOrgMangerCenterTool.getInstance(conditionVO.getOrgType(), conditionVO.getOrgVer());
            orgToJsonVOS = instance.listOrg();
        }
        if (CollectionUtils.isEmpty(orgToJsonVOS)) {
            orgToJsonVOS = new ArrayList<OrgToJsonVO>();
        }
        Map<String, OrgToJsonVO> codeMap = orgToJsonVOS.stream().collect(Collectors.toMap(i -> conditionVO.getOrgType() + i.getCode(), i -> i, (existing, replacement) -> existing));
        Map<String, OrgToJsonVO> titleMap = orgToJsonVOS.stream().collect(Collectors.toMap(i -> conditionVO.getOrgType() + i.getTitle(), i -> i, (existing, replacement) -> existing));
        orgDataCache.putAll(codeMap);
        orgDataCache.putAll(titleMap);
    }

    private void processOrgData(List<OrgToJsonVO> updateTree, UploadContext uploadContext) {
        for (int i = 0; i < updateTree.size(); ++i) {
            OrgToJsonVO parent;
            OrgToJsonVO orgToJsonVO = updateTree.get(i);
            int rowNum = (Integer)orgToJsonVO.getFieldValue(ROWNUM);
            if (StringUtils.isNotEmpty((CharSequence)orgToJsonVO.getParentid()) && (parent = uploadContext.getOrgDataCache().get(uploadContext.getConditionVO().getOrgType() + orgToJsonVO.getParentid())) == null) {
                uploadContext.getResultList().add(new ExportMessageVO().setWarn("\u7b2c" + rowNum + "\u884c\u4e0a\u7ea7\u5355\u4f4d\u4e0d\u5b58\u5728, \u672c\u6761\u8bb0\u5f55\u4e0d\u5bfc\u5165", rowNum));
                int totalCount = uploadContext.getFrontEnd().getTotalCount();
                uploadContext.getFrontEnd().getProgressData().addProgressValueAndRefresh(1.0 / (double)totalCount * 85.0);
                continue;
            }
            boolean enableTransformOrgField = uploadContext.isEnableTransformOrgField();
            boolean uploadAgain = (Boolean)orgToJsonVO.getFieldValue("UPLOAD_AGAIN");
            if (!uploadAgain && enableTransformOrgField) continue;
            this.importOrgData(orgToJsonVO, uploadContext);
            int totalCount = uploadContext.getFrontEnd().getTotalCount();
            if (uploadAgain) {
                uploadContext.getFrontEnd().setTotalCount(totalCount++);
            }
            uploadContext.getFrontEnd().getProgressData().addProgressValueAndRefresh(1.0 / (double)totalCount * 85.0);
            if (CollectionUtils.isEmpty((Collection)orgToJsonVO.getChildren())) continue;
            this.processOrgData(orgToJsonVO.getChildren(), uploadContext);
        }
    }

    private void importOrgData(OrgToJsonVO orgToJsonVO, UploadContext uploadContext) {
        ExportConditionVO conditionVO = uploadContext.getConditionVO();
        Map fieldValues = orgToJsonVO.getDatas();
        HashMap<String, Boolean> oldFieldValues = new HashMap<String, Boolean>(fieldValues);
        GcOrgMangerCenterTool instance = GcOrgMangerCenterTool.getInstance(conditionVO.getOrgType(), conditionVO.getOrgVer());
        OrgToJsonVO existOrg = instance.getOrgByCode(orgToJsonVO.getCode());
        int i = (Integer)orgToJsonVO.getFieldValue(ROWNUM);
        fieldValues.put("NO_CACHE_EVENT", true);
        if (System.currentTimeMillis() - uploadContext.getProcessStartTime() > 300000L) {
            fieldValues.put("NO_CACHE_EVENT", false);
            uploadContext.setProcessStartTime(System.currentTimeMillis());
        }
        if (existOrg != null) {
            if (conditionVO.getExecuteOnDuplicate().booleanValue()) {
                this.transformData(fieldValues, uploadContext);
                if (!((Boolean)fieldValues.get(IGNORE_UPLOAD)).booleanValue()) {
                    this.updateSingleData(uploadContext, fieldValues);
                }
            } else {
                uploadContext.getResultList().add(new ExportMessageVO().setWarn("\u7b2c" + i + "\u884c\u5df2\u5b58\u5728\uff0c\u8df3\u8fc7", i));
            }
        } else {
            boolean flag;
            boolean inBaseOrg;
            if (!conditionVO.getOrgType().equalsIgnoreCase("MD_ORG") && !(inBaseOrg = this.checkInBaseOrg(fieldValues, uploadContext))) {
                uploadContext.getResultList().add(new ExportMessageVO().setWarn("\u7b2c" + i + "\u884c\u57fa\u7840\u7ec4\u7ec7\u4e2d\u4e0d\u5b58\u5728\u8be5\u5355\u4f4d\uff0c\u8df3\u8fc7", i));
                return;
            }
            this.transformData(fieldValues, uploadContext);
            if (!((Boolean)fieldValues.get(IGNORE_UPLOAD)).booleanValue() && (flag = this.saveSingleData(fieldValues, uploadContext))) {
                OrgToJsonVO orgByCode = GcOrgBaseTool.getInstance().getOrgByCode((String)fieldValues.get("CODE"));
                uploadContext.getOrgDataCache().put(conditionVO.getOrgType() + (String)fieldValues.get("CODE"), orgByCode);
                uploadContext.getOrgDataCache().put(conditionVO.getOrgType() + orgByCode.getTitle(), orgByCode);
            }
        }
        if (((Boolean)fieldValues.get("UPLOAD_AGAIN")).booleanValue()) {
            OrgToJsonVO orgToJsonNew = new OrgToJsonVO();
            oldFieldValues.put(IGNORE_UPLOAD, false);
            oldFieldValues.put("UPLOAD_AGAIN", true);
            orgToJsonNew.setDatas(oldFieldValues);
            uploadContext.getSecondUploadData().add(orgToJsonNew);
        }
    }

    private List<OrgToJsonVO> listToTree(List<Map<String, Object>> updateList) {
        ArrayList tree = CollectionUtils.newArrayList();
        List<OrgToJsonVO> list = updateList.stream().map(o -> {
            OrgToJsonVO orgToJsonVO = new OrgToJsonVO();
            orgToJsonVO.setDatas(o);
            orgToJsonVO.setCode((String)o.get("CODE"));
            orgToJsonVO.setParentid((String)o.get("PARENTCODE"));
            orgToJsonVO.setFieldValue(ROWNUM, o.get(ROWNUM));
            orgToJsonVO.setFieldValue("UPLOAD_AGAIN", (Object)false);
            orgToJsonVO.setFieldValue(IGNORE_UPLOAD, (Object)false);
            return orgToJsonVO;
        }).collect(Collectors.toList());
        Map datas = list.stream().collect(Collectors.toMap(OrgToJsonVO::getCode, Function.identity(), (o1, o2) -> o2));
        list.forEach(org -> {
            OrgToJsonVO pobj = (OrgToJsonVO)datas.get(org.getParentid());
            if (pobj != null) {
                pobj.getChildren().add(org);
            } else {
                tree.add(org);
            }
        });
        return tree;
    }

    private List<Map<String, Object>> getUpdateList(Workbook workbook, ExportConditionVO conditionVO, boolean isAllField) {
        GcOrgCodeConfig gcOrgCodeConfig = ((FGcOrgTypeVersionDao)SpringContextUtils.getBean(FGcOrgTypeVersionDao.class)).getGcOrgCodeConfig();
        int orgCodeLen = gcOrgCodeConfig.getCodeLength();
        boolean variableLength = gcOrgCodeConfig.isVariableLength();
        ArrayList<Map<String, Object>> updateDataList = new ArrayList<Map<String, Object>>();
        Map<String, String> titleCodeMap = null;
        try {
            if (!isAllField) {
                titleCodeMap = this.gcFieldManagerService.queryAllFieldsByTableName(conditionVO.getTableName()).stream().collect(Collectors.toMap(GcOrgFieldVO::getCode, GcOrgFieldVO::getCode));
            } else {
                TableModelDefine define = this.dataModelService.getTableModelDefineByCode("MD_ORG");
                Assert.isNotNull((Object)define, (String)"\u672a\u627e\u5230\u76f8\u5173\u8868\u5b9a\u4e49[\u8868\u6807\u8bc6\uff1aMD_ORG]\uff01", (Object[])new Object[0]);
                titleCodeMap = this.dataModelService.getColumnModelDefinesByTable(define.getID()).stream().collect(Collectors.toMap(ColumnModelDefine::getName, ColumnModelDefine::getName));
            }
        }
        catch (Exception e) {
            logger.error("\u83b7\u53d6\u8868\u5b9a\u4e49\u5f02\u5e38", e);
        }
        HashMap<Integer, String> updateCode = new HashMap<Integer, String>();
        Sheet sheetAt = workbook.getSheetAt(0);
        Row row = sheetAt.getRow(0);
        int allColumns = row.getPhysicalNumberOfCells();
        int allRows = sheetAt.getPhysicalNumberOfRows();
        for (int i = 0; i < allColumns; ++i) {
            Cell cell = row.getCell(i);
            boolean contains = cell.getStringCellValue().trim().contains("|");
            if (!contains) {
                throw new BusinessRuntimeException("\u7b2c" + (i + 1) + "\u5217\u4e0d\u80fd\u8bc6\u522b,\u8bf7\u786e\u8ba4\u683c\u5f0f\u662f\u5426\u6b63\u786e");
            }
            String code = cell.getStringCellValue().trim().split("\\|")[1];
            String s = titleCodeMap.get(code);
            Assert.isNotEmpty((String)s, (String)("\u7b2c" + (i + 1) + "\u5217\u4e0d\u80fd\u8bc6\u522b,\u8bf7\u786e\u8ba4\u4ee3\u7801\u662f\u5426\u6b63\u786e"), (Object[])new Object[0]);
            updateCode.put(i, code);
        }
        boolean code = updateCode.containsValue("CODE");
        boolean containOrdinal = updateCode.containsValue("ORDINAL");
        Assert.isTrue((boolean)code, (String)"\u5355\u4f4d\u4ee3\u7801\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
        for (int i = 1; i < allRows; ++i) {
            Row row1 = sheetAt.getRow(i);
            HashMap<String, Object> data = new HashMap<String, Object>(16);
            data.put(ROWNUM, i);
            if (!containOrdinal) {
                data.put("ORDINAL", OrderNumUtil.getOrderNumByCurrentTimeMillis());
            }
            for (int j = 0; j < allColumns; ++j) {
                if (((String)updateCode.get(j)).equalsIgnoreCase("VER")) continue;
                if (row1.getCell(j) != null) {
                    String cellValue = new HSSFDataFormatter().formatCellValue(row1.getCell(j));
                    if (((String)updateCode.get(j)).equalsIgnoreCase("PARENTCODE") || ((String)updateCode.get(j)).equalsIgnoreCase("CODE")) {
                        boolean orgCode;
                        String reg;
                        String parentCode = cellValue.split("\\|")[0];
                        if (StringUtils.isEmpty((CharSequence)parentCode) || "-".equals(parentCode)) {
                            data.put(((String)updateCode.get(j)).toUpperCase(), "");
                            continue;
                        }
                        if (!variableLength) {
                            reg = "^(?:(?!(,|;|/)).){" + orgCodeLen + "," + orgCodeLen + "}$";
                            orgCode = FormatValidationUtil.isMatch(reg, parentCode);
                            Assert.isTrue((boolean)orgCode, (String)("\u7b2c" + (i + 1) + "\u884c\uff0c" + (String)updateCode.get(j) + "\u4e0d\u5141\u8bb8\u5305\u542b\u7279\u6b8a\u5b57\u7b26 ; , /\uff0c\u4e14\u957f\u5ea6\u53ea\u80fd\u4e3a" + orgCodeLen), (Object[])new Object[0]);
                        } else {
                            reg = "^(?:(?!(,|;|/)).){1,50}$";
                            orgCode = FormatValidationUtil.isMatch(reg, parentCode);
                            Assert.isTrue((boolean)orgCode, (String)("\u7b2c" + (i + 1) + "\u884c\uff0c" + (String)updateCode.get(j) + "\u4e0d\u5141\u8bb8\u5305\u542b\u7279\u6b8a\u5b57\u7b26"), (Object[])new Object[0]);
                        }
                        data.put(((String)updateCode.get(j)).toUpperCase(), parentCode);
                        continue;
                    }
                    if (((String)updateCode.get(j)).equalsIgnoreCase("STOPFLAG")) {
                        Integer stopFlag = "\u662f".equals(cellValue) || "1".equals(cellValue) ? Integer.valueOf(1) : Integer.valueOf(0);
                        data.put(((String)updateCode.get(j)).toUpperCase(), stopFlag);
                    } else if (((String)updateCode.get(j)).equalsIgnoreCase("RECOVERYFLAG") || ((String)updateCode.get(j)).equalsIgnoreCase("ORDINAL") && StringUtils.isEmpty((CharSequence)cellValue)) continue;
                    data.put(((String)updateCode.get(j)).toUpperCase(), cellValue);
                    continue;
                }
                data.put(((String)updateCode.get(j)).toUpperCase(), "");
            }
            updateDataList.add(data);
        }
        return updateDataList;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private Workbook getWorkbook(MultipartFile uploadFile, String type, InputStream is) {
        Workbook workbook = null;
        try {
            is = uploadFile.getInputStream();
            if (".xls".equals(type)) {
                workbook = new HSSFWorkbook(is);
            } else if (".xlsx".equals(type)) {
                workbook = new XSSFWorkbook(is);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (is != null) {
                try {
                    is.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return workbook;
    }

    private void checkOrgCodeLength(List<Map<String, Object>> updateList) {
        GcOrgCodeConfig gcOrgCodeConfig = ((FGcOrgTypeVersionDao)SpringContextUtils.getBean(FGcOrgTypeVersionDao.class)).getGcOrgCodeConfig();
        int orgCodeLen = gcOrgCodeConfig.getCodeLength();
        boolean variableLength = gcOrgCodeConfig.isVariableLength();
        if (variableLength) {
            return;
        }
        Optional<Map> matchNode = updateList.stream().filter(stringObjectMap -> {
            String code = String.valueOf(stringObjectMap.get("CODE"));
            return code.length() != orgCodeLen;
        }).findAny();
        if (matchNode.isPresent()) {
            Map stringObjectMap2 = matchNode.get();
            throw new BusinessRuntimeException(stringObjectMap2.get("CODE") + " \u957f\u5ea6\u5e94\u4e3a\uff1a" + orgCodeLen);
        }
    }

    private void initBaseDataCache(Map<String, BaseDataDO> baseDataCache, List<OrgFiledComponentVO> componentVOS) {
        componentVOS.stream().filter(orgFiledComponentVO -> StringUtils.isNotEmpty((CharSequence)orgFiledComponentVO.getRefTableName()) && !orgFiledComponentVO.getRefTableName().startsWith("MD_ORG")).filter(LamdbaUtils.distinctByKey(OrgFiledComponentVO::getRefTableName)).forEach(componentVO -> {
            List<BaseDataDO> iBaseData = this.getBaseDatasByTable(componentVO.getRefTableName());
            Map<String, BaseDataDO> codeMap = iBaseData.stream().collect(Collectors.toMap(i -> componentVO.getRefTableName() + i.getCode(), i -> i, (existing, replacement) -> existing));
            Map<String, BaseDataDO> titleMap = iBaseData.stream().collect(Collectors.toMap(i -> componentVO.getRefTableName() + i.getName(), i -> i, (existing, replacement) -> existing));
            baseDataCache.putAll(codeMap);
            baseDataCache.putAll(titleMap);
        });
    }

    private List<BaseDataDO> getBaseDatasByTable(String tableName) {
        BaseDataDTO queryParam = new BaseDataDTO();
        queryParam.setTableName(tableName);
        queryParam.setStopflag(Integer.valueOf(-1));
        queryParam.setRecoveryflag(Integer.valueOf(-1));
        queryParam.setQueryDataStructure(BaseDataOption.QueryDataStructure.ALL);
        queryParam.setIgnoreShareFields(Boolean.valueOf(true));
        queryParam.setAuthType(BaseDataOption.AuthType.NONE);
        PageVO list = this.baseDataClient.list(queryParam);
        if (list.getRs().getCode() == R.ok().getCode()) {
            return list.getRows();
        }
        throw new RuntimeException("\u83b7\u53d6\u57fa\u7840\u6570\u636e" + tableName + "\u5931\u8d25" + list.getRs().getMsg());
    }

    private boolean checkInBaseOrg(Map<String, Object> fieldValues, UploadContext uploadContext) {
        String code = String.valueOf(fieldValues.get("CODE"));
        OrgToJsonVO orgToJsonVO = GcOrgBaseTool.getInstance().getOrgByCode(code);
        return null != orgToJsonVO;
    }

    private void transformData(Map<String, Object> fieldValues, UploadContext uploadContext) {
        int rowNum = (Integer)fieldValues.get(ROWNUM);
        Set<String> fields = fieldValues.keySet();
        for (String fieldCode : fields) {
            boolean isBolleanField;
            OrgFiledComponentVO field = uploadContext.getFiledComponentMap().get(fieldCode);
            if (null == field) continue;
            if (fieldCode.equalsIgnoreCase("CODE")) {
                fieldValues.put(fieldCode, fieldValues.get(fieldCode).toString());
                continue;
            }
            String refTableName = field.getRefTableName();
            String orginalVal = fieldValues.get(fieldCode).toString();
            if (StringUtils.isNotEmpty((CharSequence)refTableName)) {
                if (refTableName.startsWith("MD_ORG")) {
                    OrgToJsonVO vo;
                    List<OrgToJsonVO> orgDataMuilt;
                    if (StringUtils.isEmpty((CharSequence)orginalVal)) {
                        fieldValues.put(fieldCode, "");
                        continue;
                    }
                    if (fieldCode.equalsIgnoreCase("PARENTCODE")) {
                        orgDataMuilt = this.getBaseDataValFromCache(uploadContext.getOrgDataCache(), refTableName, orginalVal);
                        if (CollectionUtils.isEmpty(orgDataMuilt)) {
                            uploadContext.getResultList().add(new ExportMessageVO().setWarn("\u7b2c" + rowNum + "\u884c" + field.getLabel() + "\u627e\u4e0d\u5230\u5bf9\u5e94\u7684\u5355\u4f4d\uff0c\u8be5\u6761\u8bb0\u5f55\u8bf7\u91cd\u65b0\u5bfc\u5165", rowNum));
                            continue;
                        }
                        vo = orgDataMuilt.get(0);
                        fieldValues.put(fieldCode, vo.getCode());
                        continue;
                    }
                    orgDataMuilt = this.getBaseDataValFromCache(uploadContext.getOrgDataCache(), refTableName, orginalVal);
                    if (CollectionUtils.isEmpty(orgDataMuilt)) {
                        if (!uploadContext.isEnableTransformOrgField()) {
                            fieldValues.put(fieldCode, "");
                            fieldValues.put("UPLOAD_AGAIN", true);
                        } else {
                            uploadContext.getResultList().add(new ExportMessageVO().setWarn("\u7b2c" + rowNum + "\u884c" + field.getLabel() + "\u627e\u4e0d\u5230\u5bf9\u5e94\u7684\u5355\u4f4d\uff0c\u8be5\u6761\u8bb0\u5f55\u8bf7\u91cd\u65b0\u5bfc\u5165", rowNum));
                        }
                    } else {
                        vo = orgDataMuilt.get(0);
                        fieldValues.put(fieldCode, vo.getCode());
                    }
                } else {
                    List<BaseDataDO> baseDatas = this.getBaseDataValFromCache(uploadContext.getBaseDataCache(), refTableName, orginalVal);
                    if (StringUtils.isEmpty((CharSequence)orginalVal)) {
                        fieldValues.put(fieldCode, "");
                    } else {
                        if (baseDatas.contains(null)) {
                            uploadContext.getResultList().add(new ExportMessageVO().setWarn("\u7b2c" + rowNum + "\u884c" + field.getLabel() + "\u627e\u4e0d\u5230\u5bf9\u5e94\u7684\u57fa\u7840\u6570\u636e", rowNum));
                            fieldValues.put(IGNORE_UPLOAD, true);
                            break;
                        }
                        String val = baseDatas.stream().map(BaseDataDO::getCode).collect(Collectors.joining(";"));
                        fieldValues.put(fieldCode, val);
                    }
                }
            }
            if (!(isBolleanField = FieldComponentEnum.RADIOGROUP.getCode().equalsIgnoreCase(field.getComponentType())) && !"\u662f".equalsIgnoreCase(orginalVal) && !"\u5426".equalsIgnoreCase(orginalVal)) continue;
            fieldValues.put(fieldCode, "\u662f".equalsIgnoreCase(orginalVal) ? 1 : 0);
        }
    }

    private <T> List<T> getBaseDataValFromCache(Map<String, T> cache, String refTableName, String val) {
        String splitWord;
        String string = splitWord = val.contains(";") ? ";" : ",";
        if (StringUtils.isNotEmpty((CharSequence)val)) {
            ArrayList vals = CollectionUtils.newArrayList((Object[])val.split(splitWord));
            return vals.stream().map(s -> {
                String[] split = s.split("\\|");
                return cache.get(refTableName + split[0]);
            }).collect(Collectors.toList());
        }
        return CollectionUtils.newArrayList();
    }

    private boolean updateSingleData(UploadContext uploadContext, Map<String, Object> fieldValues) {
        boolean flag = true;
        int i = (Integer)fieldValues.get(ROWNUM);
        OrgToJsonVO vo = new OrgToJsonVO();
        HashMap<String, Object> map = new HashMap<String, Object>(fieldValues);
        vo.setDatas(map);
        vo.setFieldValue("UPDATETIME", (Object)DateUtils.now());
        try {
            OrgToJsonVO updateDataVO = this.getUpdateDataVO(fieldValues, uploadContext);
            ExportConditionVO conditionVO = uploadContext.getConditionVO();
            if (conditionVO.getOrgType().equalsIgnoreCase("MD_ORG")) {
                GcOrgBaseTool instance = GcOrgBaseTool.getInstance();
                instance.updateBaseUnit(updateDataVO);
            } else {
                GcOrgMangerCenterTool instance = GcOrgMangerCenterTool.getInstance(conditionVO.getOrgType(), conditionVO.getOrgVer());
                List<OrgToJsonVO> hbs = instance.diffUnitExist(vo.getDiffUnitId());
                if (!CollectionUtils.isEmpty(hbs) && !hbs.get(0).getCode().equals(vo.getFieldValue("CODE"))) {
                    uploadContext.getResultList().add(new ExportMessageVO().setERROR("\u7b2c" + i + "\u884c" + instance.getOrgByID(vo.getDiffUnitId()).getTitle() + " \u5df2\u88ab\u8bbe\u7f6e\u4e3a[" + hbs.get(0).getTitle() + "]\u7684\u5dee\u989d\u5355\u4f4d", i));
                    return false;
                }
                instance.update(updateDataVO);
            }
            uploadContext.getResultList().add(new ExportMessageVO().setINFO("\u7b2c" + i + "\u884c\u66f4\u65b0\u6210\u529f", i));
            logger.info("\u7ec4\u7ec7\u673a\u6784\u5bfc\u5165\u6570\u636e,\u66f4\u65b0:" + vo.getFieldValue("CODE"));
        }
        catch (Exception e) {
            e.printStackTrace();
            flag = false;
            uploadContext.getResultList().add(new ExportMessageVO().setWarn("\u7b2c" + i + "\u884c\u66f4\u65b0\u5931\u8d25:" + e.getMessage(), i));
        }
        return flag;
    }

    private OrgToJsonVO getUpdateDataVO(Map<String, Object> fieldValues, UploadContext uploadContext) {
        OrgToJsonVO orgByCode;
        Object instance;
        ExportConditionVO conditionVO = uploadContext.getConditionVO();
        if (conditionVO.getOrgType().equalsIgnoreCase("MD_ORG")) {
            instance = GcOrgBaseTool.getInstance();
            orgByCode = ((GcOrgBaseTool)instance).getOrgByCode(fieldValues.get("CODE").toString());
        } else {
            instance = GcOrgMangerCenterTool.getInstance(conditionVO.getOrgType(), conditionVO.getOrgVer());
            orgByCode = ((GcOrgCenterBase)instance).getOrgByCode(fieldValues.get("CODE").toString());
        }
        Set<String> strings = fieldValues.keySet();
        String fnParentcode = "PARENTCODE";
        OrgToJsonVO finalOrgByCode = orgByCode;
        strings.forEach(s -> {
            if (s.equalsIgnoreCase(fnParentcode)) {
                finalOrgByCode.setParentid(StringUtils.isEmpty((CharSequence)fieldValues.get(s).toString()) ? "-" : fieldValues.get(s).toString());
            } else {
                finalOrgByCode.setFieldValue(s, fieldValues.get(s));
                if ("SHORTNAME".equals(s)) {
                    finalOrgByCode.setSimpletitle(fieldValues.get(s) != null ? (String)fieldValues.get(s) : null);
                }
            }
        });
        orgByCode.setTitle(String.valueOf(fieldValues.get("NAME")));
        return orgByCode;
    }

    private boolean saveSingleData(Map<String, Object> fieldValues, UploadContext uploadContext) {
        boolean flag = true;
        ExportConditionVO conditionVO = uploadContext.getConditionVO();
        int i = (Integer)fieldValues.get(ROWNUM);
        try {
            OrgToJsonVO vo = new OrgToJsonVO();
            HashMap<String, Object> map = new HashMap<String, Object>(fieldValues);
            vo.setDatas(map);
            if (conditionVO.getOrgType().equalsIgnoreCase("MD_ORG")) {
                GcOrgBaseTool instance = GcOrgBaseTool.getInstance();
                vo.setFieldValue("CREATETIME", (Object)DateUtils.now());
                instance.addBaseUnit(vo);
            } else {
                GcOrgMangerCenterTool instance = GcOrgMangerCenterTool.getInstance(conditionVO.getOrgType(), conditionVO.getOrgVer());
                if (!uploadContext.isAutoCalcField()) {
                    vo.setFieldValue("IGNORE_CALC_ORGTYPE", (Object)true);
                }
                if (Optional.ofNullable(vo.getFieldValue("BBLX")).isPresent()) {
                    vo.setFieldValue("IGNORE_CALC_BBLX", (Object)true);
                }
                instance.add(vo);
            }
            uploadContext.getResultList().add(new ExportMessageVO().setINFO("\u7b2c" + i + "\u884c\u65b0\u589e\u6210\u529f", i));
            logger.info("\u7ec4\u7ec7\u673a\u6784\u5bfc\u5165\u6570\u636e,\u65b0\u589e:" + vo.getFieldValue("CODE"));
        }
        catch (Exception e) {
            flag = false;
            e.printStackTrace();
            uploadContext.getResultList().add(new ExportMessageVO().setWarn("\u7b2c" + i + "\u884c\u65b0\u589e\u5931\u8d25:" + e.getMessage(), i));
        }
        return flag;
    }

    @Override
    public List<GcOrgCacheVO> list(GcOrgParam param) {
        List<GcOrgCacheVO> data = this.gcOrgQueryDao.list(param, GcOrgCacheVO.class);
        return data == null ? Collections.emptyList() : data;
    }
}

