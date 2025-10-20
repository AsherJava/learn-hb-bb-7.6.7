/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.JsonNode
 *  com.fasterxml.jackson.databind.node.ArrayNode
 *  com.fasterxml.jackson.databind.node.ObjectNode
 *  com.jiuqi.va.basedata.domain.BaseDataVersionDO
 *  com.jiuqi.va.basedata.domain.BaseDataVersionDTO
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDO
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDTO
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.user.UserDO
 *  com.jiuqi.va.domain.user.UserDTO
 *  com.jiuqi.va.domain.user.UserLoginDTO
 *  com.jiuqi.va.feign.client.AuthUserClient
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 */
package com.jiuqi.va.basedata.service.impl.help;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.jiuqi.va.basedata.domain.BaseDataVersionDO;
import com.jiuqi.va.basedata.domain.BaseDataVersionDTO;
import com.jiuqi.va.basedata.service.BaseDataDefineService;
import com.jiuqi.va.basedata.service.BaseDataVersionService;
import com.jiuqi.va.domain.basedata.BaseDataDefineDO;
import com.jiuqi.va.domain.basedata.BaseDataDefineDTO;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.user.UserDO;
import com.jiuqi.va.domain.user.UserDTO;
import com.jiuqi.va.domain.user.UserLoginDTO;
import com.jiuqi.va.feign.client.AuthUserClient;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component(value="vaBaseDataContextService")
public class BaseDataContextService {
    private static Logger logger = LoggerFactory.getLogger(BaseDataContextService.class);
    @Value(value="${va-basedata.define-name-prefix.unfreeze:false}")
    private boolean definePrefixUnfreeze = false;
    @Value(value="${va-basedata.create-define-name.force:false}")
    private boolean createDefineForce = false;
    @Value(value="${va-datamodel-check-tablename-length:true}")
    private boolean checkTableNameLength = true;
    @Value(value="${nvwa.basedata.datasync.batch-size:500}")
    private int batchSubmitSize = 500;
    @Value(value="${nvwa.basedata.bizmgr.allowClearData:false}")
    private boolean bizmgrAllowClear = false;
    @Value(value="${nvwa.basedata.modify-historical-data.allow:false}")
    private boolean modifyHistoricalDataAllow = false;
    @Value(value="${nvwa.basedata.modify-historical-data.defines:}")
    private String modifyHistoricalDataDefines;
    @Value(value="${nvwa.basedata.sensitivity.enhanced:false}")
    private boolean sensitivityEnhanced = false;
    @Autowired
    private AuthUserClient userClient;
    private static Set<String> modifyHistoricalDataDefineSet;
    private BaseDataVersionService baseDataVersionService;
    private BaseDataDefineService baseDataDefineService;

    private BaseDataVersionService getBaseDataVersionService() {
        if (this.baseDataVersionService == null) {
            this.baseDataVersionService = (BaseDataVersionService)ApplicationContextRegister.getBean(BaseDataVersionService.class);
        }
        return this.baseDataVersionService;
    }

    private BaseDataDefineService getBaseDataDefineService() {
        if (this.baseDataDefineService == null) {
            this.baseDataDefineService = (BaseDataDefineService)ApplicationContextRegister.getBean(BaseDataDefineService.class);
        }
        return this.baseDataDefineService;
    }

    public boolean isDefinePrefixUnfreeze() {
        return this.definePrefixUnfreeze;
    }

    public boolean isCreateDefineForce() {
        return this.createDefineForce;
    }

    public boolean isCheckTableNameLength() {
        return this.checkTableNameLength;
    }

    public int getBatchSubmitSize() {
        return this.batchSubmitSize;
    }

    public boolean isBizmgrAllowClear() {
        return this.bizmgrAllowClear;
    }

    public boolean isModifyHistoricalDataAllow() {
        return this.modifyHistoricalDataAllow;
    }

    public String getModifyHistoricalDataDefines() {
        return this.modifyHistoricalDataDefines;
    }

    public boolean isSensitivityEnhanced() {
        return this.sensitivityEnhanced;
    }

    public R getCurrEnv(BaseDataDefineDTO param) {
        String mgrFlag = "normal";
        try {
            UserLoginDTO user = ShiroUtil.getUser();
            mgrFlag = user.getMgrFlag();
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        boolean isSuper = "super".equals(mgrFlag);
        R rs = R.ok();
        rs.put("isSuperMan", (Object)isSuper);
        rs.put("allowClear", (Object)(isSuper || this.bizmgrAllowClear && this.isBizMgr() ? 1 : 0));
        rs.put("canModify", (Object)this.canModify(param));
        rs.put("canQuerySensitive", (Object)this.canQuerySensitive(param));
        return rs;
    }

    private boolean isBizMgr() {
        boolean flag = false;
        try {
            UserLoginDTO user = ShiroUtil.getUser();
            if (user != null) {
                Object isAdmin;
                UserDTO param = new UserDTO();
                param.setUsername(user.getUsername());
                param.addExtInfo("onlyNeedBasicInfo", (Object)true);
                param.addExtInfo("judgeBizMgr", (Object)true);
                UserDO userDO = this.userClient.get(param);
                Object isBizMgr = userDO.getExtInfo("isBizMgr");
                if (isBizMgr == null || ((Boolean)isBizMgr).booleanValue()) {
                    flag = true;
                }
                if ((isAdmin = user.getExtInfo("isAdmin")) != null && ((Boolean)isAdmin).booleanValue()) {
                    flag = true;
                }
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return flag;
    }

    private boolean canModify(BaseDataDefineDTO param) {
        param.setDeepClone(Boolean.valueOf(false));
        BaseDataDefineDO define = this.getBaseDataDefineService().get(param);
        if (define != null && define.getVersionflag() != null && define.getVersionflag() == 1) {
            BaseDataVersionDO dataVersion;
            boolean modifyHsiFlag = this.allowModifyHistoricalData(param.getName());
            if (modifyHsiFlag) {
                return true;
            }
            BaseDataVersionDTO verParam = new BaseDataVersionDTO();
            verParam.setTablename(param.getName());
            try {
                verParam.setVersionDate(ShiroUtil.getUser().getLoginDate());
            }
            catch (Exception exception) {
                // empty catch block
            }
            if (verParam.getVersionDate() == null) {
                verParam.setVersionDate(new Date());
            }
            if ((dataVersion = this.getBaseDataVersionService().get(verParam)) != null) {
                Calendar ca = Calendar.getInstance();
                ca.setTime(dataVersion.getInvalidtime());
                return ca.get(1) == 9999;
            }
        }
        return true;
    }

    public boolean allowModifyHistoricalData(String defineName) {
        if (!this.modifyHistoricalDataAllow) {
            return false;
        }
        if (!StringUtils.hasText(this.modifyHistoricalDataDefines)) {
            return true;
        }
        if (modifyHistoricalDataDefineSet == null) {
            modifyHistoricalDataDefineSet = new HashSet<String>();
            for (String name : this.modifyHistoricalDataDefines.split("\\,")) {
                if (!StringUtils.hasText(name)) continue;
                modifyHistoricalDataDefineSet.add(name.toUpperCase());
            }
        }
        return modifyHistoricalDataDefineSet.contains(defineName.toUpperCase());
    }

    public boolean canQuerySensitive(BaseDataDefineDTO defineParam) {
        boolean flag = false;
        try {
            defineParam.setDeepClone(Boolean.valueOf(false));
            BaseDataDefineDO define = this.getBaseDataDefineService().get(defineParam);
            Map<String, String> map = this.getSensitiveFields(define);
            if (map == null || map.isEmpty()) {
                return false;
            }
            UserLoginDTO user = ShiroUtil.getUser();
            if (user == null) {
                return false;
            }
            if ("super".equals(user.getMgrFlag())) {
                return true;
            }
            UserDTO userParam = new UserDTO();
            userParam.setUsername(user.getUsername());
            userParam.setExtInfo(user.getExtInfo());
            userParam.addExtInfo("onlyNeedBasicInfo", (Object)true);
            userParam.addExtInfo("judgeQuerySensitive", (Object)true);
            UserDO userDO = this.userClient.get(userParam);
            Object canQuerySensitive = userDO.getExtInfo("canQuerySensitive");
            if (canQuerySensitive != null && ((Boolean)canQuerySensitive).booleanValue()) {
                flag = true;
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return flag;
    }

    public Map<String, String> getSensitiveFields(BaseDataDefineDO defineDO) {
        String definestr = defineDO.getDefine();
        HashMap<String, String> sensitiveFields = null;
        if (!StringUtils.hasText(definestr)) {
            return sensitiveFields;
        }
        ObjectNode objectNode = JSONUtil.parseObject((String)definestr);
        ArrayNode jsonArray = objectNode.withArray("fieldProps");
        if (jsonArray == null) {
            return sensitiveFields;
        }
        sensitiveFields = new HashMap<String, String>();
        String sensitiveType = null;
        for (JsonNode node : jsonArray) {
            if (!node.has("fieldSensitive") || !StringUtils.hasText(sensitiveType = node.get("fieldSensitive").asText())) continue;
            sensitiveFields.put(node.get("columnName").asText().toLowerCase(), sensitiveType);
        }
        return sensitiveFields;
    }
}

