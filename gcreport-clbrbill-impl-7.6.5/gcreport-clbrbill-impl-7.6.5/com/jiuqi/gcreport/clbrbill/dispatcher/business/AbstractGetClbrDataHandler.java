/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.ConverterUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.basedata.impl.bean.GcBaseData
 *  com.jiuqi.gcreport.clbrbill.enums.ClbrBillTypeEnum
 *  com.jiuqi.gcreport.clbrbill.enums.ClbrStatesEnum
 *  com.jiuqi.gcreport.clbrbill.enums.ClbrThirdOptionsEnum
 *  com.jiuqi.np.core.context.ContextIdentity
 *  com.jiuqi.np.core.context.ContextUser
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.core.context.impl.NpContextIdentity
 *  com.jiuqi.np.core.context.impl.NpContextImpl
 *  com.jiuqi.np.core.context.impl.NpContextUser
 *  com.jiuqi.np.user.SystemUser
 *  com.jiuqi.np.user.service.SystemUserService
 *  com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService
 *  com.jiuqi.va.billlist.controller.BillListController
 *  com.jiuqi.va.billlist.model.BillListDefine
 *  com.jiuqi.va.biz.intf.model.ModelDefineService
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.user.UserLoginDTO
 */
package com.jiuqi.gcreport.clbrbill.dispatcher.business;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.basedata.impl.bean.GcBaseData;
import com.jiuqi.gcreport.clbrbill.dao.ClbrOrgDao;
import com.jiuqi.gcreport.clbrbill.dispatcher.business.ClbrBusinessHandler;
import com.jiuqi.gcreport.clbrbill.dto.ClbrQueryBillDataDTO;
import com.jiuqi.gcreport.clbrbill.dto.ClbrQueryBillDataResultDTO;
import com.jiuqi.gcreport.clbrbill.enums.ClbrBillTypeEnum;
import com.jiuqi.gcreport.clbrbill.enums.ClbrStatesEnum;
import com.jiuqi.gcreport.clbrbill.enums.ClbrThirdOptionsEnum;
import com.jiuqi.gcreport.clbrbill.utils.ClbrBillUtils;
import com.jiuqi.np.core.context.ContextIdentity;
import com.jiuqi.np.core.context.ContextUser;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.core.context.impl.NpContextIdentity;
import com.jiuqi.np.core.context.impl.NpContextImpl;
import com.jiuqi.np.core.context.impl.NpContextUser;
import com.jiuqi.np.user.SystemUser;
import com.jiuqi.np.user.service.SystemUserService;
import com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService;
import com.jiuqi.va.billlist.controller.BillListController;
import com.jiuqi.va.billlist.model.BillListDefine;
import com.jiuqi.va.biz.intf.model.ModelDefineService;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.user.UserLoginDTO;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

public abstract class AbstractGetClbrDataHandler
implements ClbrBusinessHandler<ClbrQueryBillDataDTO, ClbrQueryBillDataResultDTO> {
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractGetClbrDataHandler.class);
    private static final String CUSTOM_QUERY_FIELD_PREFIX = "CQ_BILL_VIEW_";
    private static final String UNITCODE = "UNITCODE";
    private static final String OPPUNITCODE = "OPPUNITCODE";
    @Autowired
    ModelDefineService modelDefineService;
    @Autowired
    BillListController billListController;
    @Autowired
    ClbrOrgDao clbrOrgDao;
    @Autowired
    SystemUserService sysUserService;

    @Override
    public final String getBusinessCode() {
        return "GETCLBRDATA";
    }

    @Override
    public ClbrQueryBillDataDTO beforeHandler(Object content) {
        ClbrQueryBillDataDTO dto = new ClbrQueryBillDataDTO();
        Map map = (Map)content;
        Map<String, Object> upperKeyMap = map.entrySet().stream().collect(Collectors.toMap(entry -> ((String)entry.getKey()).toUpperCase(Locale.ROOT), Map.Entry::getValue));
        String unitCode = ConverterUtils.getAsString((Object)upperKeyMap.get(UNITCODE));
        if (StringUtils.isEmpty((String)unitCode)) {
            LOGGER.info("\u534f\u540c\u4e1a\u52a1\u7c7b\u578b\uff1a{}\uff0c\u8bf7\u6c42\u53c2\u6570\u4e2d\u672a\u5305\u542bunitCode\u3002", (Object)this.getBusinessCode());
            throw new BusinessRuntimeException("\u8bf7\u6c42\u53c2\u6570\u4e2d\u5236\u5355\u5355\u4f4d[unitCode]\u4e0d\u80fd\u4e3a\u7a7a\u3002");
        }
        dto.setUnitCode(unitCode);
        String oppUnitCode = ConverterUtils.getAsString((Object)upperKeyMap.get(OPPUNITCODE), (String)"");
        dto.setOppUnitCode(oppUnitCode);
        String srcBillDefine = ConverterUtils.getAsString((Object)upperKeyMap.get("SRCBILLDEFINE"), (String)"");
        dto.setSrcBillDefine(srcBillDefine);
        String srcBusinessType = ConverterUtils.getAsString((Object)upperKeyMap.get("SRCBUSINESSTYPE"), (String)"");
        dto.setSrcBusinessType(srcBusinessType);
        Integer pageSize = ConverterUtils.getAsInteger((Object)upperKeyMap.get("PAGESIZE"), (Integer)100);
        dto.setLimit(pageSize);
        Integer pageNum = ConverterUtils.getAsInteger((Object)upperKeyMap.get("PAGENUM"), (Integer)0);
        dto.setOffset((pageSize + 1) * pageNum);
        for (Map.Entry<String, Object> entry2 : upperKeyMap.entrySet()) {
            String key = entry2.getKey();
            if (key.equals("SRCBILLDEFINE") || key.equals("SRCBUSINESSTYPE") || key.equals(UNITCODE) || key.equals(OPPUNITCODE) || key.equals("PAGESIZE") || key.equals("PAGENUM")) continue;
            dto.putExtendedFields(key, upperKeyMap.get(key));
        }
        return dto;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public final ClbrQueryBillDataResultDTO handler(ClbrQueryBillDataDTO dto) {
        this.changeSystemUserContext();
        try {
            String unitCode = this.clbrOrgDao.findOrgCodeBySrcOrgCode(dto.getUnitCode());
            Assert.notNull((Object)unitCode, "\u5236\u5355\u5355\u4f4d\u4e0d\u5b58\u5728\u3002");
            String oppUnitCode = this.clbrOrgDao.findOrgCodeBySrcOrgCode(dto.getOppUnitCode());
            Map<String, Object> params = this.getQueryParams(dto, unitCode, oppUnitCode);
            LOGGER.info("\u534f\u540c\u4e1a\u52a1\u7c7b\u578b\uff1a{}\uff0c\u67e5\u8be2\u5355\u636e\u5217\u8868\u7684\u53c2\u6570\uff1a{}", (Object)this.getBusinessCode(), (Object)JsonUtils.writeValueAsString(params));
            PageVO pageVO = this.billListController.list(params);
            List<Map<String, Object>> rows = pageVO.getRows().stream().filter(row -> row.containsKey("BILLCODE")).map(map -> {
                HashMap newMap = new HashMap(map);
                if (newMap.containsKey("BILLCODE")) {
                    newMap.put("CLBRCODE", newMap.get("BILLCODE"));
                    newMap.remove("BILLCODE");
                }
                newMap.remove("verifyCode");
                newMap.remove("ID");
                newMap.remove("DEFINECODE");
                for (Map.Entry entry : newMap.entrySet()) {
                    if (!(entry.getValue() instanceof Map)) continue;
                    newMap.replace((String)entry.getKey(), ((Map)entry.getValue()).get("name"));
                }
                double amt = 0.0;
                double noVerifyAmt = 0.0;
                if (unitCode.equals(ConverterUtils.getAsString(newMap.get("INITIATEORG")))) {
                    amt = ConverterUtils.getAsDouble(newMap.get("INITIATEAMT"));
                    noVerifyAmt = ConverterUtils.getAsDouble(newMap.get("INITIATEAMT")) - ConverterUtils.getAsDouble(newMap.get("INITIATEQUOTEAMT"));
                } else if (unitCode.equals(ConverterUtils.getAsString(newMap.get("RECEIVEORG")))) {
                    amt = ConverterUtils.getAsDouble(newMap.get("RECEIVEAMT"));
                    noVerifyAmt = ConverterUtils.getAsDouble(newMap.get("RECEIVEAMT")) - ConverterUtils.getAsDouble(newMap.get("RECEIVEQUOTEAMT"));
                } else if (unitCode.equals(ConverterUtils.getAsString(newMap.get("THIRDORG")))) {
                    amt = ConverterUtils.getAsDouble(newMap.get("THIRDAMT"));
                    noVerifyAmt = ConverterUtils.getAsDouble(newMap.get("THIRDAMT")) - ConverterUtils.getAsDouble(newMap.get("THIRDQUOTEAMT"));
                }
                newMap.put("AMT", amt);
                newMap.put("NOVERIFYAMT", noVerifyAmt);
                newMap.remove("INITIATEAMT");
                newMap.remove("INITIATEQUOTEAMT");
                newMap.remove("RECEIVEAMT");
                newMap.remove("RECEIVEQUOTEAMT");
                newMap.remove("THIRDAMT");
                newMap.remove("THIRDQUOTEAMT");
                if (newMap.containsKey("ISTRIPARTITE") && "0".equals(newMap.get("ISTRIPARTITE"))) {
                    newMap.remove("THIRDORG");
                    newMap.remove("THIRDUSER");
                    newMap.remove("ISTRIPARTITE");
                }
                return newMap;
            }).collect(Collectors.toList());
            ClbrQueryBillDataResultDTO result = new ClbrQueryBillDataResultDTO();
            result.setTotal(pageVO.getTotal());
            result.setRows(rows);
            ClbrQueryBillDataResultDTO clbrQueryBillDataResultDTO = result;
            return clbrQueryBillDataResultDTO;
        }
        finally {
            ShiroUtil.unbindUser();
        }
    }

    @Override
    public ClbrQueryBillDataResultDTO afterHandler(ClbrQueryBillDataDTO content, ClbrQueryBillDataResultDTO result) {
        return result;
    }

    private Map<String, Object> getQueryParams(ClbrQueryBillDataDTO dto, String unitCode, String oppUnitCode) {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("offset", dto.getOffset());
        params.put("limit", dto.getLimit());
        params.put("defineName", "GCBILL_L_GC_CLBR_QUERYCLBRBILLDATALIST");
        BillListDefine billListDefine = (BillListDefine)this.modelDefineService.getDefine("GCBILL_L_GC_CLBR_QUERYCLBRBILLDATALIST");
        GcBaseData billMapping = ClbrBillUtils.queryBillMappingBaseData(dto.getSrcBillDefine(), dto.getSrcBusinessType());
        if (null == billMapping) {
            LOGGER.info("\u534f\u540c\u4e1a\u52a1\u7c7b\u578b\uff1a{}\uff0c\u672a\u627e\u5230\u5355\u636e\u6620\u5c04\u4fe1\u606f\uff0csrcBillDefine\uff1a{}\uff0csrcBusinessType\uff1a{}", this.getBusinessCode(), dto.getSrcBillDefine(), dto.getSrcBusinessType());
            throw new BusinessRuntimeException("\u534f\u540c\u5e73\u53f0\u751f\u5355\u6620\u5c04\u4e2d\u672a\u627e\u5230\u5355\u636e\u6620\u5c04\u4fe1\u606f\u3002srcBillDefine\uff1a" + dto.getSrcBillDefine() + "\uff0csrcBusinessType\uff1a" + dto.getSrcBusinessType() + "\u3002");
        }
        String clbrBillType = ConverterUtils.getAsString((Object)billMapping.getFieldVal("CLBRBILLTYPE")).toUpperCase(Locale.ROOT);
        String clbrBillDefine = ConverterUtils.getAsString((Object)billMapping.getFieldVal("CLBRBILLDEFINE")).toUpperCase(Locale.ROOT);
        String prefix = "CQBillListModelType".equals(billListDefine.getModelType()) ? CUSTOM_QUERY_FIELD_PREFIX : "GC_CLBRBILL_";
        params.put(prefix + "DEFINECODE", "GCBILL_B_" + clbrBillDefine);
        params.put(prefix + UNITCODE, unitCode);
        params.put(prefix + OPPUNITCODE, oppUnitCode);
        params.put(prefix + "ISINITIATE", ClbrBillTypeEnum.INITIATE.name().equals(clbrBillType) ? "1" : "0");
        params.put(prefix + "CLBRSTATE", ClbrStatesEnum.CONFIRM.name());
        INvwaSystemOptionService systemOptionService = (INvwaSystemOptionService)SpringContextUtils.getBean(INvwaSystemOptionService.class);
        String thirdEnableStr = systemOptionService.findValueById("GCREPORT_CLBR_THIRD_ENABLE");
        boolean thirdEnable = ClbrThirdOptionsEnum.YES.getCode().toString().equals(thirdEnableStr);
        params.put(prefix + "THIRDENABLE", thirdEnable ? "1" : "0");
        Map<String, Object> extendedFields = dto.getExtendedFields();
        if (!ObjectUtils.isEmpty(extendedFields)) {
            for (Map.Entry<String, Object> entry : extendedFields.entrySet()) {
                String key = entry.getKey();
                params.put(prefix + key, extendedFields.get(key));
            }
        }
        return params;
    }

    private void changeSystemUserContext() {
        SystemUser user = (SystemUser)this.sysUserService.getUsers().stream().findFirst().orElseThrow(() -> new BusinessRuntimeException("\u7cfb\u7edf\u4e2d\u672a\u627e\u5230\u53ef\u7528\u7684\u7ba1\u7406\u5458\u7528\u6237"));
        NpContextImpl contextImpl = new NpContextImpl();
        NpContextUser contextUser = new NpContextUser();
        contextUser.setName(user.getName());
        contextUser.setId(user.getId());
        contextUser.setNickname(user.getNickname());
        contextUser.setOrgCode(user.getOrgCode());
        contextUser.setDescription(user.getDescription());
        contextUser.setType(user.getUserType());
        contextImpl.setUser((ContextUser)contextUser);
        NpContextIdentity contextIdentity = new NpContextIdentity();
        contextIdentity.setId(user.getId());
        contextIdentity.setTitle(user.getName());
        contextImpl.setIdentity((ContextIdentity)contextIdentity);
        NpContextHolder.setContext((NpContext)contextImpl);
        UserLoginDTO userDTO = new UserLoginDTO();
        userDTO.setId(user.getId());
        userDTO.setTenantName("__default_tenant__");
        userDTO.setName(user.getName());
        userDTO.setUsername(user.getName());
        userDTO.setLoginDate(new Date());
        ShiroUtil.bindUser((UserLoginDTO)userDTO);
    }
}

