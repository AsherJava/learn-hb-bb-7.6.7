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
 *  com.jiuqi.nr.singlequeryimport.auth.share.bean.ModalNodeInfo
 *  com.jiuqi.nr.singlequeryimport.dao.QueryModeleDao
 *  nr.single.client.service.querycheck.bean.QueryModalCheckInfo
 *  nr.single.client.service.querycheck.bean.QueryModalCheckParam
 *  nr.single.client.service.querycheck.extend.ISingleQueryCheckReadService
 *  nr.single.map.data.exception.SingleDataException
 */
package com.jiuqi.nr.singlequery.jioupdownload.service;

import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.finalaccountsaudit.common.EntityQueryHelper;
import com.jiuqi.nr.multcheck2.bean.MCErrorDescription;
import com.jiuqi.nr.multcheck2.dynamic.IMCErrorInfoService;
import com.jiuqi.nr.singlequeryimport.auth.share.bean.ModalNodeInfo;
import com.jiuqi.nr.singlequeryimport.dao.QueryModeleDao;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nr.single.client.service.querycheck.bean.QueryModalCheckInfo;
import nr.single.client.service.querycheck.bean.QueryModalCheckParam;
import nr.single.client.service.querycheck.extend.ISingleQueryCheckReadService;
import nr.single.map.data.exception.SingleDataException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SingleQueryCheckReadServiceImpl
implements ISingleQueryCheckReadService {
    private static final Logger logger = LoggerFactory.getLogger(SingleQueryCheckReadServiceImpl.class);
    @Autowired
    private IMCErrorInfoService errorInfoService;
    @Autowired
    private EntityQueryHelper entityQueryHelper;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private QueryModeleDao queryModeleDao;

    public List<QueryModalCheckInfo> getQueryCheckResult(String s, Map<String, DimensionValue> map, QueryModalCheckParam queryModalCheckParam) throws SingleDataException {
        ArrayList<QueryModalCheckInfo> result = new ArrayList<QueryModalCheckInfo>();
        FormSchemeDefine formSchemeDefine = this.runTimeViewController.getFormScheme(s);
        String mainDimName = this.entityQueryHelper.getMainDimName(s);
        String orgCode = map.get(mainDimName).getValue();
        String period = map.get("DATATIME").getValue();
        String itemType = "querycheck";
        ArrayList errorDescriptions = new ArrayList();
        try {
            String[] orgs = orgCode.split(";");
            for (String org : orgs) {
                List tmpList = this.errorInfoService.getByOrg(formSchemeDefine.getTaskKey(), period, itemType, org);
                logger.info(String.format("%s-%s,\u53d6\u5230\u8bb0\u5f55%d\u6761", org, period, tmpList.size()));
                errorDescriptions.addAll(tmpList);
            }
        }
        catch (Exception e) {
            logger.error(String.format("\u83b7\u53d6List<MCErrorDescription>\u5931\u8d25:%s-%s-%s-%s", formSchemeDefine.getTaskKey(), period, itemType, orgCode));
        }
        if (errorDescriptions == null) {
            return result;
        }
        try {
            ArrayList<String> modalKeys = new ArrayList<String>();
            for (MCErrorDescription mcErrorDescription : errorDescriptions) {
                if (modalKeys.contains(mcErrorDescription.getResource())) continue;
                modalKeys.add(mcErrorDescription.getResource());
            }
            List modals = this.queryModeleDao.getModalNodeInfosByKeys(modalKeys);
            HashMap<String, String> modalKeyTitleMap = new HashMap<String, String>();
            for (ModalNodeInfo model : modals) {
                modalKeyTitleMap.put(model.getModalId(), model.getTitle());
            }
            for (MCErrorDescription errorDescription : errorDescriptions) {
                if (!modalKeyTitleMap.containsKey(errorDescription.getResource())) {
                    logger.error(String.format("%s\u83b7\u53d6\u6a21\u7248\u5931\u8d25", errorDescription.getResource()));
                    continue;
                }
                QueryModalCheckInfo checkInfo = new QueryModalCheckInfo();
                checkInfo.setEntityCode(errorDescription.getOrg());
                checkInfo.setModalTitle((String)modalKeyTitleMap.get(errorDescription.getResource()));
                checkInfo.setInfo(errorDescription.getDescription());
                result.add(checkInfo);
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage());
        }
        return result;
    }
}

