/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.bpm.businesskey.BusinessKey
 *  com.jiuqi.nr.bpm.businesskey.BusinessKeyImpl
 *  com.jiuqi.nr.bpm.businesskey.MasterEntityImpl
 *  com.jiuqi.nr.bpm.businesskey.MasterEntityInfo
 *  com.jiuqi.nr.bpm.impl.common.NrParameterUtils
 *  com.jiuqi.nr.bpm.service.IBatchQueryUploadStateService
 *  com.jiuqi.nr.data.access.service.IDataAccessServiceProvider
 *  com.jiuqi.nr.datacrud.IMetaData
 *  com.jiuqi.nr.datacrud.ReturnRes
 *  com.jiuqi.nr.datacrud.impl.out.CrudOperateException
 *  com.jiuqi.nr.dataservice.core.access.ResouceType
 *  com.jiuqi.nr.dataservice.core.common.IProviderStore
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeFormSchemeService
 *  com.jiuqi.nr.fielddatacrud.FieldSaveInfo
 *  com.jiuqi.nr.fielddatacrud.ImpMode
 *  com.jiuqi.nr.fielddatacrud.TableUpdater
 *  com.jiuqi.nr.fielddatacrud.api.IFieldDataService
 *  com.jiuqi.nr.fielddatacrud.impl.DefaultFieldDataServiceFactory
 *  com.jiuqi.nr.fielddatacrud.spi.IParamDataProvider
 *  com.jiuqi.nr.fmdm.FMDMDataDTO
 *  com.jiuqi.nr.fmdm.IFMDMDataService
 *  com.jiuqi.nr.fmdm.exception.FMDMUpdateException
 */
package com.jiuqi.nr.migration.transferdata.dbservice.service.impl;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.bpm.businesskey.BusinessKey;
import com.jiuqi.nr.bpm.businesskey.BusinessKeyImpl;
import com.jiuqi.nr.bpm.businesskey.MasterEntityImpl;
import com.jiuqi.nr.bpm.businesskey.MasterEntityInfo;
import com.jiuqi.nr.bpm.impl.common.NrParameterUtils;
import com.jiuqi.nr.bpm.service.IBatchQueryUploadStateService;
import com.jiuqi.nr.data.access.service.IDataAccessServiceProvider;
import com.jiuqi.nr.datacrud.IMetaData;
import com.jiuqi.nr.datacrud.ReturnRes;
import com.jiuqi.nr.datacrud.impl.out.CrudOperateException;
import com.jiuqi.nr.dataservice.core.access.ResouceType;
import com.jiuqi.nr.dataservice.core.common.IProviderStore;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeFormSchemeService;
import com.jiuqi.nr.fielddatacrud.FieldSaveInfo;
import com.jiuqi.nr.fielddatacrud.ImpMode;
import com.jiuqi.nr.fielddatacrud.TableUpdater;
import com.jiuqi.nr.fielddatacrud.api.IFieldDataService;
import com.jiuqi.nr.fielddatacrud.impl.DefaultFieldDataServiceFactory;
import com.jiuqi.nr.fielddatacrud.spi.IParamDataProvider;
import com.jiuqi.nr.fmdm.FMDMDataDTO;
import com.jiuqi.nr.fmdm.IFMDMDataService;
import com.jiuqi.nr.fmdm.exception.FMDMUpdateException;
import com.jiuqi.nr.migration.transferdata.bean.DimInfo;
import com.jiuqi.nr.migration.transferdata.bean.FetchSaveDataParam;
import com.jiuqi.nr.migration.transferdata.bean.TransMemo;
import com.jiuqi.nr.migration.transferdata.bean.upload_audit_confirm_copy;
import com.jiuqi.nr.migration.transferdata.bean.upload_audit_confirm_hi_copy;
import com.jiuqi.nr.migration.transferdata.common.TransferUtils;
import com.jiuqi.nr.migration.transferdata.dbservice.DBServiceUtil;
import com.jiuqi.nr.migration.transferdata.dbservice.TransferParamDataProviderImpl;
import com.jiuqi.nr.migration.transferdata.dbservice.service.ISaveDataService;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SaveDataServiceImpl
implements ISaveDataService {
    private static final Logger logger = LoggerFactory.getLogger(SaveDataServiceImpl.class);
    private static String Logger_Prefix = "JQR\u6570\u636e\u5305\u5bfc\u5165\uff1a\u5f00\u59cb\u5199\u5165\u6570\u636e\uff1a";
    private static final String IGNOREACCESSITEM = "formCondition";
    @Autowired
    private IDataAccessServiceProvider iDataAccessServiceProvider;
    @Autowired
    private IFMDMDataService ifmdmDataService;
    @Autowired
    private DefaultFieldDataServiceFactory fieldDataServiceFactory;
    @Autowired
    private NrParameterUtils nrParameterUtils;
    @Autowired
    private IBatchQueryUploadStateService batchQueryUploadStateService;
    @Autowired
    private IRuntimeFormSchemeService runtimeFormSchemeService;

    @Override
    public void storageOneData(List<DimInfo> dims, String taskKey, String formSchemeKey, String dataTableCode, List<IMetaData> fields, List<Object> values) throws Exception {
        logger.info("JQR\u6570\u636e\u5305\u5bfc\u5165\uff1a\u5f00\u59cb\u5199\u5165\u6570\u636e\uff1a\u7269\u7406\u8868\uff1a" + dataTableCode + ", \u65f6\u671f\uff1a " + dims.get(0).getValue() + ", \u5355\u4f4d\uff1a" + dims.get(1).getValue());
        TableUpdater tableUpdater = this.getTableUpdater(dims, taskKey, formSchemeKey, fields);
        ReturnRes returnRes = tableUpdater.addRow(values);
        try {
            tableUpdater.commit();
        }
        catch (Exception e) {
            logger.error("JQR\u6570\u636e\u5305\u5bfc\u5165\uff1a\u56fa\u5b9a\u884c\u6570\u636e\u5bfc\u5165\u5931\u8d25\u3002\u5355\u4f4d\uff1a" + dims.get(1).getValue(), e);
            throw new Exception();
        }
        if (!returnRes.isSuccess()) {
            logger.error("JQR\u6570\u636e\u5305\u5bfc\u5165\uff1a\u56fa\u5b9a\u884c\u6570\u636e\u5199\u5165\u5931\u8d25\uff1a\u9519\u8bef\u7801\uff1a" + returnRes.getCode() + "\uff0c \u9519\u8bef\u4fe1\u606f\uff1a" + returnRes.getMessage());
            throw new Exception("JQR\u6570\u636e\u5305\u5bfc\u5165\uff1a\u56fa\u5b9a\u884c\u6570\u636e\u5199\u5165\u5931\u8d25\uff1a\u9519\u8bef\u7801\uff1a" + returnRes.getCode() + "\uff0c \u9519\u8bef\u4fe1\u606f\uff1a" + returnRes.getMessage());
        }
        logger.info("JQR\u6570\u636e\u5305\u5bfc\u5165\uff1a\u56fa\u5b9a\u884c\u6570\u636e\u5199\u5165\u6210\u529f\u3002");
    }

    @Override
    public int storageFloatDatas(List<DimInfo> dims, String taskKey, String formSchemeKey, String dataTableCode, List<IMetaData> fields, List<List<Object>> floatValues) throws Exception {
        TableUpdater tableUpdater = this.getTableUpdater(dims, taskKey, formSchemeKey, fields);
        int successRows = 0;
        int rowCounter = 0;
        for (List<Object> values : floatValues) {
            ++rowCounter;
            if (fields.size() == values.size()) {
                try {
                    ReturnRes returnRes = tableUpdater.addRow(values);
                    if (returnRes.isSuccess()) {
                        ++successRows;
                        continue;
                    }
                    logger.error("JQR\u6570\u636e\u5305\u5bfc\u5165\uff1a\u6d6e\u52a8\u884c\u6570\u636e\u5bfc\u5165\u5931\u8d25\uff1a\u9519\u8bef\u7801\uff1a" + returnRes.getCode() + "\uff0c \u9519\u8bef\u4fe1\u606f\uff1a" + returnRes.getMessage());
                    throw new Exception("JQR\u6570\u636e\u5305\u5bfc\u5165\uff1a\u6d6e\u52a8\u884c\u6570\u636e\u5bfc\u5165\u5931\u8d25\uff1a\u9519\u8bef\u7801\uff1a" + returnRes.getCode() + "\uff0c \u9519\u8bef\u4fe1\u606f\uff1a" + returnRes.getMessage());
                }
                catch (CrudOperateException e) {
                    logger.error("JQR\u6570\u636e\u5305\u5bfc\u5165\uff1a\u6d6e\u52a8\u884c\u6570\u636e\u5bfc\u5165\u5931\u8d25\u3002\u5355\u4f4d\uff1a" + dims.get(1).getValue() + ", \u884c\u6570" + rowCounter, e);
                    throw new Exception("JQR\u6570\u636e\u5305\u5bfc\u5165\uff1a\u6d6e\u52a8\u884c\u6570\u636e\u5bfc\u5165\u5931\u8d25\u3002\u5355\u4f4d\uff1a" + dims.get(1).getValue() + ", \u884c\u6570" + rowCounter);
                }
            }
            logger.error("JQR\u6570\u636e\u5305\u5bfc\u5165\uff1a\u6d6e\u52a8\u884c\u6570\u636e\u5bfc\u5165\u5931\u8d25\u3002\u5355\u4f4d\uff1a" + dims.get(1).getValue() + ", \u884c\u6570" + rowCounter + "\u503c\u7684\u957f\u5ea6\u4e0e\u5b57\u6bb5\u957f\u5ea6\u4e0d\u4e00\u81f4\u3002");
            throw new Exception("JQR\u6570\u636e\u5305\u5bfc\u5165\uff1a\u6d6e\u52a8\u884c\u6570\u636e\u5bfc\u5165\u5931\u8d25\u3002\u5355\u4f4d\uff1a" + dims.get(1).getValue() + ", \u884c\u6570" + rowCounter + "\u503c\u7684\u957f\u5ea6\u4e0e\u5b57\u6bb5\u957f\u5ea6\u4e0d\u4e00\u81f4\u3002");
        }
        try {
            tableUpdater.commit();
        }
        catch (Exception e) {
            logger.error("JQR\u6570\u636e\u5305\u5bfc\u5165\uff1a\u6d6e\u52a8\u884c\u6570\u636e\u5bfc\u5165\u5931\u8d25\u3002\u5355\u4f4d\uff1a" + dims.get(1).getValue() + ", \u884c\u6570" + rowCounter, e);
            throw new Exception("JQR\u6570\u636e\u5305\u5bfc\u5165\uff1a\u6d6e\u52a8\u884c\u6570\u636e\u5bfc\u5165\u5931\u8d25\u3002\u5355\u4f4d\uff1a" + dims.get(1).getValue() + ", \u884c\u6570" + rowCounter);
        }
        return successRows;
    }

    @Override
    public void storageFmdmData(List<DimInfo> dims, String key, String formSchemeKey, List<String> fmdmZbCodes, Map<String, Object> zbCode2ValueMap) throws FMDMUpdateException {
        FMDMDataDTO fmdmDataDTO = new FMDMDataDTO();
        DimensionCollection dimensionValueSets = TransferUtils.buildDimensionCollection(dims);
        List dimensionCombinations = dimensionValueSets.getDimensionCombinations();
        fmdmDataDTO.setDimensionCombination((DimensionCombination)dimensionCombinations.get(0));
        fmdmDataDTO.setFormSchemeKey(formSchemeKey);
        for (DimInfo dim : dims) {
            if ("DATATIME".equals(dim.getEntityId())) continue;
            fmdmDataDTO.setEntityValue(dim.getName(), (Object)dim.getValue());
        }
        for (String code : fmdmZbCodes) {
            fmdmDataDTO.setDataValue(code, zbCode2ValueMap.get(code));
        }
        this.ifmdmDataService.add(fmdmDataDTO);
    }

    @Override
    public void storageDataState(String formSchemeKey, DimensionValueSet masterKeys, int jqrDataState, boolean isForce) {
        FormSchemeDefine formSchemeDefine = this.runtimeFormSchemeService.getFormScheme(formSchemeKey);
        if (jqrDataState == 0) {
            return;
        }
        upload_audit_confirm_copy prevEventByJqrDataState = this.getPrevEventByJqrDataState(jqrDataState);
        if (prevEventByJqrDataState == null) {
            return;
        }
        String prevEvent = prevEventByJqrDataState.actionCode;
        String curNode = prevEventByJqrDataState.nodeCode;
        boolean force = false;
        String taskId = prevEventByJqrDataState.nodeCode;
        this.nrParameterUtils.commitStateQuery(formSchemeDefine, masterKeys, prevEvent, curNode, Boolean.valueOf(force), taskId);
    }

    private upload_audit_confirm_copy getPrevEventByJqrDataState(int jqrDataState) {
        switch (jqrDataState) {
            case 0: {
                return upload_audit_confirm_copy.start;
            }
            case 1: {
                return upload_audit_confirm_copy.act_upload;
            }
            case 3: {
                return upload_audit_confirm_copy.act_reject;
            }
            case 2: {
                return upload_audit_confirm_copy.act_confirm;
            }
        }
        return upload_audit_confirm_copy.start;
    }

    private upload_audit_confirm_hi_copy getDataStateHiByJqrDataState(int jqrDataState) {
        switch (jqrDataState) {
            case 1: {
                return upload_audit_confirm_hi_copy.act_upload;
            }
            case 3: {
                return upload_audit_confirm_hi_copy.act_reject;
            }
            case 2: {
                return upload_audit_confirm_hi_copy.act_confirm;
            }
        }
        return null;
    }

    private void deleteWorkFlowHi(String formSchemeKey, List<DimInfo> dimInfos) {
        BusinessKeyImpl businessKey = new BusinessKeyImpl();
        businessKey.setFormSchemeKey(formSchemeKey);
        for (DimInfo dimInfo : dimInfos) {
            if ("DATATIME".equals(dimInfo.getName())) {
                businessKey.setPeriod(dimInfo.getValue());
                continue;
            }
            MasterEntityImpl masterEntityInfo = new MasterEntityImpl();
            String tableName = dimInfo.getEntityId().split("@")[0];
            if (dimInfo.getValues().size() == 0) {
                masterEntityInfo.setMasterEntityDimessionValue(tableName, dimInfo.getValue());
            }
            businessKey.setMasterEntity((MasterEntityInfo)masterEntityInfo);
        }
        this.batchQueryUploadStateService.deleteUploadRecord((BusinessKey)businessKey);
    }

    @Override
    public void storageWorkFlowHi(String formSchemeKey, DimensionValueSet masterKeys, List<DimInfo> dimInfos, List<TransMemo> memos) {
        this.deleteWorkFlowHi(formSchemeKey, dimInfos);
        for (TransMemo memo : memos) {
            upload_audit_confirm_hi_copy prevEventByJqrDataState = this.getDataStateHiByJqrDataState(Integer.parseInt(memo.getState()));
            if (prevEventByJqrDataState == null) {
                return;
            }
            FormSchemeDefine formSchemeDefine = this.runtimeFormSchemeService.getFormScheme(formSchemeKey);
            String curEvent = prevEventByJqrDataState.actionCode;
            String curNode = prevEventByJqrDataState.nodeCode;
            Date date = new Date(Long.parseLong(memo.getTime()));
            this.nrParameterUtils.commitHiQuery(formSchemeDefine, masterKeys, curEvent, curNode, memo.getContent(), memo.getUser(), UUID.randomUUID().toString(), date, curNode);
        }
    }

    private TableUpdater getTableUpdater(List<DimInfo> dims, String taskKey, String formSchemeKey, List<IMetaData> fields) {
        FieldSaveInfo saveInfo = this.getFieldSaveInfo(dims, fields);
        FetchSaveDataParam saveQueryDataParam = new FetchSaveDataParam();
        saveQueryDataParam.setTaskKey(taskKey);
        saveQueryDataParam.setFormSchemeKey(formSchemeKey);
        IProviderStore providerStore = DBServiceUtil.getProviderStore(this.iDataAccessServiceProvider);
        TransferParamDataProviderImpl provider = new TransferParamDataProviderImpl(saveQueryDataParam);
        IFieldDataService iFieldDataService = this.fieldDataServiceFactory.getFieldDataFileService(providerStore, (IParamDataProvider)provider);
        TableUpdater tableUpdater = iFieldDataService.getTableUpdater(saveInfo);
        tableUpdater.installParseStrategy();
        return tableUpdater;
    }

    private FieldSaveInfo getFieldSaveInfo(List<DimInfo> dims, List<IMetaData> fields) {
        FieldSaveInfo saveInfo = new FieldSaveInfo();
        saveInfo.setMode(ImpMode.FULL);
        saveInfo.setMasterKey(TransferUtils.buildDimensionCollection(dims));
        saveInfo.setFields(fields);
        saveInfo.setAuthMode(ResouceType.ZB);
        return saveInfo;
    }
}

