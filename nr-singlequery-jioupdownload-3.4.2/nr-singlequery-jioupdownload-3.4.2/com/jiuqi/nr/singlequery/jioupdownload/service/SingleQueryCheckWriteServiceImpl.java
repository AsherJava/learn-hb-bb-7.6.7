/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.finalaccountsaudit.common.EntityQueryHelper
 *  com.jiuqi.nr.multcheck2.bean.MCErrorDescription
 *  com.jiuqi.nr.multcheck2.dynamic.IMCErrorInfoService
 *  com.jiuqi.nr.singlequeryimport.bean.QueryModel
 *  com.jiuqi.nr.singlequeryimport.dao.QueryModeleDao
 *  nr.single.client.service.querycheck.bean.QueryModalCheckInfo
 *  nr.single.client.service.querycheck.bean.QueryModalCheckParam
 *  nr.single.client.service.querycheck.bean.QueryModalCheckResult
 *  nr.single.client.service.querycheck.extend.ISingleQueryCheckWriteService
 *  nr.single.map.data.exception.SingleDataException
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.nr.singlequery.jioupdownload.service;

import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.finalaccountsaudit.common.EntityQueryHelper;
import com.jiuqi.nr.multcheck2.bean.MCErrorDescription;
import com.jiuqi.nr.multcheck2.dynamic.IMCErrorInfoService;
import com.jiuqi.nr.singlequeryimport.bean.QueryModel;
import com.jiuqi.nr.singlequeryimport.dao.QueryModeleDao;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nr.single.client.service.querycheck.bean.QueryModalCheckInfo;
import nr.single.client.service.querycheck.bean.QueryModalCheckParam;
import nr.single.client.service.querycheck.bean.QueryModalCheckResult;
import nr.single.client.service.querycheck.extend.ISingleQueryCheckWriteService;
import nr.single.map.data.exception.SingleDataException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SingleQueryCheckWriteServiceImpl
implements ISingleQueryCheckWriteService {
    private static final Logger logger = LoggerFactory.getLogger(SingleQueryCheckWriteServiceImpl.class);
    @Autowired
    private IMCErrorInfoService errorInfoService;
    @Autowired
    private EntityQueryHelper entityQueryHelper;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private QueryModeleDao queryModeleDao;

    public QueryModalCheckResult writeQueryCheckResult(String formSchemeKey, Map<String, DimensionValue> dimensionSet, List<QueryModalCheckInfo> list, QueryModalCheckParam param) throws SingleDataException {
        QueryModalCheckResult result = new QueryModalCheckResult();
        result.setSuccess(true);
        FormSchemeDefine formSchemeDefine = this.runTimeViewController.getFormScheme(formSchemeKey);
        String mainDimName = this.entityQueryHelper.getMainDimName(formSchemeKey);
        String orgCode = dimensionSet.get(mainDimName).getValue();
        String period = dimensionSet.get("DATATIME").getValue();
        String itemType = "querycheck";
        ArrayList errorDescriptions = new ArrayList();
        try {
            String[] orgs = orgCode.split(";");
            for (QueryModalCheckInfo queryModalCheckInfo : list) {
                List tmpList = this.errorInfoService.getByOrg(formSchemeDefine.getTaskKey(), period, itemType, queryModalCheckInfo.getEntityCode());
                logger.info(String.format("%s-%s,\u53d6\u5230\u8bb0\u5f55%d\u6761", queryModalCheckInfo, period, tmpList.size()));
                errorDescriptions.addAll(tmpList);
            }
        }
        catch (Exception e) {
            logger.error(String.format("\u83b7\u53d6List<MCErrorDescription>\u5931\u8d25:%s-%s-%s-%s", formSchemeDefine.getTaskKey(), period, itemType, orgCode));
        }
        if (errorDescriptions == null) {
            result.setSuccess(false);
            result.setMessage("\u83b7\u53d6List<MCErrorDescription>\u5931\u8d25");
            return result;
        }
        List models = null;
        try {
            models = this.queryModeleDao.getByTaskKeyAndSchemeKey(formSchemeDefine.getTaskKey(), formSchemeKey);
        }
        catch (Exception e) {
            logger.error(String.format("\u83b7\u53d6List<QueryModel>\u5931\u8d25: %s-%s", formSchemeDefine.getTaskKey(), formSchemeKey));
        }
        if (models == null) {
            result.setSuccess(false);
            result.setMessage("\u83b7\u53d6List<QueryModel>\u5931\u8d25");
            return result;
        }
        HashMap<String, String> modalTitleKeyMap = new HashMap<String, String>();
        for (QueryModel model : models) {
            modalTitleKeyMap.put(model.getItemTitle(), model.getKey());
        }
        ArrayList<MCErrorDescription> arrayList = new ArrayList<MCErrorDescription>();
        ArrayList<String> existKeys = new ArrayList<String>();
        for (MCErrorDescription errorDescription : errorDescriptions) {
            existKeys.add(errorDescription.getKey());
        }
        for (QueryModalCheckInfo checkInfo : list) {
            if (!modalTitleKeyMap.containsKey(checkInfo.getModalTitle())) {
                logger.error(String.format("%s\u83b7\u53d6\u6a21\u677fKey\u5931\u8d25", checkInfo.getModalTitle()));
                continue;
            }
            MCErrorDescription errorDescription = new MCErrorDescription();
            errorDescription.setTask(formSchemeDefine.getTaskKey());
            errorDescription.setPeriod(period);
            errorDescription.setOrg(checkInfo.getEntityCode());
            errorDescription.setItemType(itemType);
            errorDescription.setResource((String)modalTitleKeyMap.get(checkInfo.getModalTitle()));
            errorDescription.setDescription(checkInfo.getInfo());
            logger.info(String.format("\u83b7\u53d6\u6570\u636e%s-%s-%s", orgCode, checkInfo.getModalTitle() + ":" + (String)modalTitleKeyMap.get(checkInfo.getModalTitle()), checkInfo.getInfo()));
            arrayList.add(errorDescription);
        }
        try {
            this.deleteAndAddErrDescs(existKeys, arrayList, formSchemeDefine.getTaskKey());
        }
        catch (Exception e) {
            logger.error(e.getMessage());
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            return result;
        }
        return result;
    }

    @Transactional
    public void deleteAndAddErrDescs(List<String> existKeys, List<MCErrorDescription> addErrDescs, String taskKey) throws Exception {
        this.errorInfoService.batchDeleteByKeys(existKeys, taskKey);
        this.errorInfoService.batchAdd(addErrDescs, taskKey);
    }
}

