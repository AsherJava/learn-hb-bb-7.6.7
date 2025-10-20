/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.basedata.custsupp.domain.CustSuppAccRelDO
 *  com.jiuqi.va.basedata.custsupp.domain.CustSuppAccRelDTO
 *  com.jiuqi.va.basedata.custsupp.feign.client.CustSuppAccRelClient
 *  com.jiuqi.va.domain.basedata.BaseDataDO
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDO
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.datamodel.DataModelDO
 *  com.jiuqi.va.domain.datamodel.DataModelDTO
 *  com.jiuqi.va.feign.client.DataModelClient
 *  com.jiuqi.va.formula.domain.Utils
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 */
package com.jiuqi.va.bill.bd.bill.model;

import com.jiuqi.va.basedata.custsupp.domain.CustSuppAccRelDO;
import com.jiuqi.va.basedata.custsupp.domain.CustSuppAccRelDTO;
import com.jiuqi.va.basedata.custsupp.feign.client.CustSuppAccRelClient;
import com.jiuqi.va.bill.bd.bill.model.BillAlterModel;
import com.jiuqi.va.domain.basedata.BaseDataDO;
import com.jiuqi.va.domain.basedata.BaseDataDefineDO;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.datamodel.DataModelDO;
import com.jiuqi.va.domain.datamodel.DataModelDTO;
import com.jiuqi.va.feign.client.DataModelClient;
import com.jiuqi.va.formula.domain.Utils;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

public class CommonMethodModel {
    private static final Logger logger = LoggerFactory.getLogger(BillAlterModel.class);
    public static final String BASEDATANAME = "BASEDATANAME";
    public static final String CODE = "CODE";
    public static final String NAME = "NAME";
    public static final String PARENTCODE = "PARENTCODE";
    private static CustSuppAccRelClient custSuppAccRelClient;

    private static CustSuppAccRelClient getCustSuppAccRelClient() {
        if (custSuppAccRelClient == null) {
            custSuppAccRelClient = (CustSuppAccRelClient)ApplicationContextRegister.getBean(CustSuppAccRelClient.class);
        }
        return custSuppAccRelClient;
    }

    public static void createCustSuppAccDatas(BaseDataDO baseData, BaseDataDefineDO define, Map<String, CustSuppAccRelDO> custRelMap) {
        CustSuppAccRelDTO rel = new CustSuppAccRelDTO();
        rel.setId(UUID.randomUUID());
        rel.setModifytime(new Date());
        String custsuppcode = "";
        if (baseData.get((Object)"custsuppcode") != null && Utils.isNotEmpty((String)baseData.get((Object)"custsuppcode").toString())) {
            custsuppcode = baseData.get((Object)"custsuppcode").toString();
        } else if (baseData.get((Object)"sourcecode") != null && Utils.isNotEmpty((String)baseData.get((Object)"sourcecode").toString())) {
            custsuppcode = baseData.get((Object)"sourcecode").toString();
        } else {
            return;
        }
        String unitcode = ShiroUtil.getUser().getLoginUnit();
        String custCodeKey = "";
        String custsuppcodeKey = custsuppcode.toUpperCase();
        if (define.getSharetype() != 0 && StringUtils.hasText(unitcode)) {
            custCodeKey = (baseData.getCode() + "||" + unitcode).toUpperCase();
            custsuppcodeKey = (custsuppcode + "||" + unitcode).toUpperCase();
            rel.setCustsuppcode(custsuppcodeKey);
            rel.setAccountcode(custCodeKey);
        } else {
            rel.setCustsuppcode(custsuppcodeKey);
            custCodeKey = baseData.getCode().toUpperCase();
            rel.setAccountcode(custCodeKey);
        }
        if (baseData.get((Object)"defaultflag") != null) {
            rel.setDefaultflag((Integer)baseData.get((Object)"defaultflag"));
        } else {
            rel.setDefaultflag(Integer.valueOf(0));
        }
        custRelMap.put(custCodeKey + "##" + custsuppcodeKey, (CustSuppAccRelDO)rel);
    }

    public static DataModelDO getDataModelDefine(String bdtablename) {
        DataModelClient dataModelClient = (DataModelClient)ApplicationContextRegister.getBean(DataModelClient.class);
        DataModelDTO dataModelParam = new DataModelDTO();
        dataModelParam.setName(bdtablename);
        return dataModelClient.get(dataModelParam);
    }

    public static void updateRel(Map<String, CustSuppAccRelDO> custRelMap) {
        CustSuppAccRelDTO custSuppAccRelDTO = new CustSuppAccRelDTO();
        HashMap<String, String> defaultCustMap = new HashMap<String, String>();
        for (CustSuppAccRelDO relDo : custRelMap.values()) {
            String cust = relDo.getCustsuppcode();
            if (relDo.getDefaultflag() != null && relDo.getDefaultflag() == 1 && !defaultCustMap.containsKey(cust)) {
                defaultCustMap.put(cust, relDo.getAccountcode());
            }
            custSuppAccRelDTO.setId(UUID.randomUUID());
            custSuppAccRelDTO.setCustsuppcode(cust);
            custSuppAccRelDTO.setAccountcode(relDo.getAccountcode());
            custSuppAccRelDTO.setDefaultflag(Integer.valueOf(0));
            try {
                R add = CommonMethodModel.getCustSuppAccRelClient().add(custSuppAccRelDTO);
                if (add.getCode() == 0) continue;
                logger.error("\u66f4\u65b0\u5ba2\u5546\u5173\u7cfb\uff1a" + add.getMsg());
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
        CustSuppAccRelDTO custDtoParam = new CustSuppAccRelDTO();
        CustSuppAccRelDTO oldParam = new CustSuppAccRelDTO();
        for (String cust : defaultCustMap.keySet()) {
            custDtoParam.setCustsuppcode(cust);
            List custSuppAccRels = CommonMethodModel.getCustSuppAccRelClient().list(custDtoParam);
            if (custSuppAccRels.isEmpty()) continue;
            for (CustSuppAccRelDO oldDO : custSuppAccRels) {
                if (!oldDO.getAccountcode().equals(defaultCustMap.get(cust)) && oldDO.getDefaultflag() == 0) continue;
                oldParam.setId(oldDO.getId());
                if (oldDO.getAccountcode().equals(defaultCustMap.get(cust))) {
                    oldParam.setDefaultflag(Integer.valueOf(1));
                } else {
                    oldParam.setDefaultflag(Integer.valueOf(0));
                }
                oldParam.setCustsuppcode(oldDO.getCustsuppcode());
                oldParam.setAccountcode(oldDO.getAccountcode());
                R update = CommonMethodModel.getCustSuppAccRelClient().update(oldParam);
                if (update.getCode() == 0) continue;
                logger.error("\u66f4\u65b0\u5ba2\u5546\u5173\u7cfb\uff1a" + update.getMsg());
            }
        }
    }
}

