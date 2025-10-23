/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.bpm.common.UploadRecordNew
 *  com.jiuqi.nr.bpm.dataflow.service.IQueryUploadStateService
 *  com.jiuqi.nr.bpm.de.dataflow.bean.ActionStateBean
 *  com.jiuqi.nr.bpm.upload.UploadState
 *  com.jiuqi.nr.data.access.service.IDataAccessServiceProvider
 *  com.jiuqi.nr.datacrud.IQueryInfo
 *  com.jiuqi.nr.datacrud.IRegionDataSet
 *  com.jiuqi.nr.datacrud.IRowData
 *  com.jiuqi.nr.datacrud.QueryInfoBuilder
 *  com.jiuqi.nr.datacrud.api.IDataQueryService
 *  com.jiuqi.nr.dataservice.core.common.IProviderStore
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeFormService
 *  com.jiuqi.nr.entity.engine.data.AbstractData
 *  com.jiuqi.nr.fielddatacrud.FieldQueryInfoBuilder
 *  com.jiuqi.nr.fielddatacrud.IFieldQueryInfo
 *  com.jiuqi.nr.fielddatacrud.api.IFieldDataService
 *  com.jiuqi.nr.fielddatacrud.impl.DefaultFieldDataServiceFactory
 *  com.jiuqi.nr.fielddatacrud.spi.IDataReader
 *  com.jiuqi.nr.fielddatacrud.spi.IParamDataProvider
 *  com.jiuqi.nr.fmdm.FMDMAttributeDTO
 *  com.jiuqi.nr.fmdm.FMDMDataDTO
 *  com.jiuqi.nr.fmdm.IFMDMAttribute
 *  com.jiuqi.nr.fmdm.IFMDMData
 *  com.jiuqi.nr.fmdm.IFMDMDataService
 *  com.jiuqi.nr.fmdm.service.impl.RunTimeFMDMAttributeServiceImpl
 *  com.jiuqi.nvwa.authority.user.service.NvwaUserService
 *  com.jiuqi.nvwa.authority.user.vo.UserDetailsRes
 */
package com.jiuqi.nr.migration.transferdata.dbservice.service.impl;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.bpm.common.UploadRecordNew;
import com.jiuqi.nr.bpm.dataflow.service.IQueryUploadStateService;
import com.jiuqi.nr.bpm.de.dataflow.bean.ActionStateBean;
import com.jiuqi.nr.bpm.upload.UploadState;
import com.jiuqi.nr.data.access.service.IDataAccessServiceProvider;
import com.jiuqi.nr.datacrud.IQueryInfo;
import com.jiuqi.nr.datacrud.IRegionDataSet;
import com.jiuqi.nr.datacrud.IRowData;
import com.jiuqi.nr.datacrud.QueryInfoBuilder;
import com.jiuqi.nr.datacrud.api.IDataQueryService;
import com.jiuqi.nr.dataservice.core.common.IProviderStore;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeFormService;
import com.jiuqi.nr.entity.engine.data.AbstractData;
import com.jiuqi.nr.fielddatacrud.FieldQueryInfoBuilder;
import com.jiuqi.nr.fielddatacrud.IFieldQueryInfo;
import com.jiuqi.nr.fielddatacrud.api.IFieldDataService;
import com.jiuqi.nr.fielddatacrud.impl.DefaultFieldDataServiceFactory;
import com.jiuqi.nr.fielddatacrud.spi.IDataReader;
import com.jiuqi.nr.fielddatacrud.spi.IParamDataProvider;
import com.jiuqi.nr.fmdm.FMDMAttributeDTO;
import com.jiuqi.nr.fmdm.FMDMDataDTO;
import com.jiuqi.nr.fmdm.IFMDMAttribute;
import com.jiuqi.nr.fmdm.IFMDMData;
import com.jiuqi.nr.fmdm.IFMDMDataService;
import com.jiuqi.nr.fmdm.service.impl.RunTimeFMDMAttributeServiceImpl;
import com.jiuqi.nr.migration.transferdata.bean.DimInfo;
import com.jiuqi.nr.migration.transferdata.bean.FetchDataParam;
import com.jiuqi.nr.migration.transferdata.bean.TransMainBody;
import com.jiuqi.nr.migration.transferdata.bean.TransMemo;
import com.jiuqi.nr.migration.transferdata.bean.TransZbValue;
import com.jiuqi.nr.migration.transferdata.common.DataTransUtil;
import com.jiuqi.nr.migration.transferdata.common.TransferUtils;
import com.jiuqi.nr.migration.transferdata.dbservice.DBServiceUtil;
import com.jiuqi.nr.migration.transferdata.dbservice.TransferDataReaderImpl;
import com.jiuqi.nr.migration.transferdata.dbservice.TransferParamDataProviderImpl;
import com.jiuqi.nr.migration.transferdata.dbservice.service.IQueryDataService;
import com.jiuqi.nvwa.authority.user.service.NvwaUserService;
import com.jiuqi.nvwa.authority.user.vo.UserDetailsRes;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QueryDataServiceImpl
implements IQueryDataService {
    private static final Logger logger = LoggerFactory.getLogger(QueryDataServiceImpl.class);
    @Autowired
    private IDataAccessServiceProvider iDataAccessServiceProvider;
    @Autowired
    private RunTimeFMDMAttributeServiceImpl iCommonFMDMAttributeService;
    @Autowired
    private IFMDMDataService ifmdmDataService;
    @Autowired
    private DefaultFieldDataServiceFactory fieldDataServiceFactory;
    @Autowired
    private IRuntimeFormService iRuntimeFormService;
    @Autowired
    private IQueryUploadStateService iQueryUploadStateService;
    @Autowired
    private IDataQueryService iDataQueryService;
    @Autowired
    private NvwaUserService nvwaUserService;

    @Override
    public TransMainBody queryFMDMData(FetchDataParam fetchDataParam) {
        TransMainBody mb = new TransMainBody();
        mb.setIdx(0);
        mb.setFloat(false);
        mb.setFormKey(fetchDataParam.getFormKey());
        ArrayList<TransZbValue> zbList = new ArrayList<TransZbValue>();
        List<IFMDMData> ifmdmData = this.queryFMDMData(fetchDataParam.getFormSchemeKey(), fetchDataParam.getDimInfos());
        if (ifmdmData.size() == 0) {
            logger.warn("\u5c01\u9762\u4ee3\u7801\u8868\u6570\u636e\u4e3a\u7a7a\u3002");
            return mb;
        }
        logger.info("\u5c01\u9762\u4ee3\u7801\u8868\u53d6\u6570\u7ed3\u675f\u3002");
        FMDMAttributeDTO dto = new FMDMAttributeDTO();
        dto.setFormSchemeKey(fetchDataParam.getFormSchemeKey());
        List attributes = this.iCommonFMDMAttributeService.list(dto);
        if (attributes.size() == 0) {
            logger.warn("\u5c01\u9762\u4ee3\u7801\u8868\u5c5e\u6027\u4e3a\u7a7a");
        }
        for (int idx = 0; idx < attributes.size(); ++idx) {
            IFMDMAttribute attr = (IFMDMAttribute)attributes.get(idx);
            TransZbValue zbValue = new TransZbValue();
            zbValue.setZbCode(attr.getCode());
            zbValue.setIdx(idx++);
            zbValue.setZbType(DataTransUtil.getJqrZbType(attr.getColumnType().getValue()));
            Optional<IFMDMData> first = ifmdmData.stream().filter(e -> e.getFMDMKey().equals(attr.getZBKey())).findFirst();
            if (first.isPresent()) {
                AbstractData dataValue = first.get().getDataValue(attr.getCode());
                zbValue.setValue(DataTransUtil.getFieldValue(dataValue));
            }
            zbList.add(zbValue);
        }
        mb.addFieldValues(zbList);
        return mb;
    }

    @Override
    public List<IRowData> queryAllReportData(String regionKey, List<DimInfo> dimInfos) {
        DimensionCollection dimensionValueSets = TransferUtils.buildDimensionCollection(dimInfos);
        List dimensionCombinations = dimensionValueSets.getDimensionCombinations();
        if (dimensionCombinations.size() > 0) {
            QueryInfoBuilder queryInfoBuilder = new QueryInfoBuilder(regionKey, (DimensionCombination)dimensionCombinations.get(0)).whereRegionFilter();
            queryInfoBuilder.setFormulaSchemeKey(null);
            IQueryInfo queryInfo = queryInfoBuilder.build();
            IRegionDataSet iRegionDataSet = this.iDataQueryService.queryRegionData(queryInfo);
            return iRegionDataSet.getRowData();
        }
        return new ArrayList<IRowData>();
    }

    @Deprecated
    private List<IRowData> queryReportData(FetchDataParam fetchDataParam) {
        IFieldQueryInfo queryInfo = this.buildIFieldQueryInfo(fetchDataParam);
        TransferDataReaderImpl dataReader = new TransferDataReaderImpl();
        TransferParamDataProviderImpl paramDataProviderImpl = new TransferParamDataProviderImpl(fetchDataParam);
        IFieldDataService iFieldDataService = this.fieldDataServiceFactory.getFieldDataFileService((IParamDataProvider)paramDataProviderImpl);
        iFieldDataService.queryTableData(queryInfo, (IDataReader)dataReader);
        return dataReader.getRowDatas();
    }

    @Override
    public List<IRowData> batchQueryReportData(FetchDataParam fetchDataParam) {
        IFieldQueryInfo queryInfo = this.buildIFieldQueryInfo(fetchDataParam);
        TransferDataReaderImpl dataReader = new TransferDataReaderImpl();
        TransferParamDataProviderImpl paramDataProviderImpl = new TransferParamDataProviderImpl(fetchDataParam);
        IProviderStore providerStore = DBServiceUtil.getProviderStore(this.iDataAccessServiceProvider);
        IFieldDataService iFieldDataService = this.fieldDataServiceFactory.getFieldDataFileService(providerStore, (IParamDataProvider)paramDataProviderImpl);
        iFieldDataService.queryTableData(queryInfo, (IDataReader)dataReader);
        return dataReader.getRowDatas();
    }

    @Override
    public int queryDataState(String formSchemeKey, DimensionValueSet dimensionValueSet) {
        ActionStateBean actionStateBean = this.iQueryUploadStateService.queryActionState(formSchemeKey, dimensionValueSet);
        if (actionStateBean == null || actionStateBean.getCode() == null) {
            return 0;
        }
        switch (UploadState.valueOf((String)actionStateBean.getCode())) {
            case ORIGINAL: 
            case ORIGINAL_UPLOAD: 
            case ORIGINAL_SUBMIT: {
                return 0;
            }
            case UPLOADED: {
                return 1;
            }
            case CONFIRMED: {
                return 2;
            }
            case REJECTED: {
                return 3;
            }
            case PART_UPLOADED: {
                return 4;
            }
        }
        return 0;
    }

    @Override
    public List<TransMemo> queryHistoryMemos(String formSchemeKey, DimensionValueSet dimensionValueSet, boolean isForce) throws ParseException {
        ArrayList<TransMemo> memos = new ArrayList<TransMemo>();
        List uploadRecordNews = this.iQueryUploadStateService.queryUploadHistoryStates(formSchemeKey, dimensionValueSet, null);
        for (UploadRecordNew uploadRecordNew : uploadRecordNews) {
            TransMemo memo = new TransMemo();
            memo.setRpCode(uploadRecordNew.getFormKey());
            memo.setUser(this.getUserNameById(uploadRecordNew.getOperator()));
            memo.setTime(this.timeToLong(uploadRecordNew.getTime()));
            String wfStatusByOperateCode = this.getWFStatusByOperateCode(uploadRecordNew.getAction());
            memo.setStatus(String.valueOf(isForce ? 1 : 0));
            memo.setState(wfStatusByOperateCode);
            memo.setOperateCode(this.getOperateCode(uploadRecordNew.getAction()));
            memo.setContent(uploadRecordNew.getCmt());
            memos.add(memo);
        }
        return memos;
    }

    @Override
    public List<TransMemo> queryWorkFlowDataCommitByForm(String formSchemeKey, List<String> formKeys, DimensionValueSet dimensionValueSet) throws ParseException {
        ArrayList<TransMemo> memos = new ArrayList<TransMemo>();
        List uploadRecordNews = this.iQueryUploadStateService.queryUploadHistoryStates(formSchemeKey, dimensionValueSet, formKeys);
        if (uploadRecordNews != null && uploadRecordNews.size() > 0) {
            for (UploadRecordNew uploadRecordNew : uploadRecordNews) {
                FormDefine formDefine = this.iRuntimeFormService.queryForm(uploadRecordNew.getFormKey());
                if (formDefine == null) continue;
                TransMemo memo = new TransMemo();
                memo.setRpCode(formDefine.getFormCode());
                memo.setUser(this.getUserNameById(uploadRecordNew.getOperator()));
                memo.setTime(this.timeToLong(uploadRecordNew.getTime()));
                String wfStatusByOperateCode = this.getWFStatusByOperateCode(uploadRecordNew.getAction());
                memo.setStatus(wfStatusByOperateCode);
                memo.setOperateCode(wfStatusByOperateCode);
                memo.setContent(uploadRecordNew.getCmt());
                memos.add(memo);
            }
        }
        return memos;
    }

    private String getOperateCode(String operationId) {
        switch (operationId) {
            case "act_reject": 
            case "cus_reject": {
                return String.valueOf(3);
            }
            case "act_confirm": 
            case "cus_confirm": {
                return String.valueOf(2);
            }
            case "act_submit": 
            case "cus_submit": {
                return String.valueOf(11);
            }
            case "act_return": 
            case "cus_return": {
                return String.valueOf(22);
            }
            case "act_upload": 
            case "cus_upload": {
                return String.valueOf(1);
            }
            case "act_retrieve": {
                return String.valueOf(4);
            }
        }
        return "\u65e0\u72b6\u6001";
    }

    private String getWFStatusByOperateCode(String operationId) {
        switch (operationId) {
            case "act_reject": 
            case "cus_reject": {
                return String.valueOf(3);
            }
            case "act_confirm": 
            case "cus_confirm": {
                return String.valueOf(2);
            }
            case "act_submit": 
            case "cus_submit": 
            case "act_upload": 
            case "cus_upload": 
            case "act_retrieve": {
                return String.valueOf(1);
            }
            case "act_return": 
            case "cus_return": {
                return String.valueOf(0);
            }
        }
        return "\u65e0\u72b6\u6001";
    }

    private List<IFMDMData> queryFMDMData(String formSchemeKey, List<DimInfo> dims) {
        FMDMDataDTO fmdmDataDTO = new FMDMDataDTO();
        fmdmDataDTO.setFormSchemeKey(formSchemeKey);
        DimensionCollection dimCollection = TransferUtils.buildDimensionCollection(dims);
        return this.ifmdmDataService.list(fmdmDataDTO, dimCollection);
    }

    private IFieldQueryInfo buildIFieldQueryInfo(FetchDataParam vo) {
        DimensionCollection dimCollection = TransferUtils.buildDimensionCollection(vo.getDimInfos());
        FieldQueryInfoBuilder builder = FieldQueryInfoBuilder.create((DimensionCollection)dimCollection);
        for (String dataFieldKey : vo.getDataFieldKeys()) {
            builder.select(dataFieldKey);
        }
        return builder.build();
    }

    private String timeToLong(String wfTime) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return String.valueOf(formatter.parse(wfTime).getTime());
    }

    private String getUserNameById(String userId) {
        if ("00000000-0000-0000-0000-000000000000".equals(userId)) {
            return "admin";
        }
        UserDetailsRes userDetailsRes = this.nvwaUserService.userDetails(userId);
        if (userDetailsRes != null) {
            return userDetailsRes.getName();
        }
        return userId;
    }
}

