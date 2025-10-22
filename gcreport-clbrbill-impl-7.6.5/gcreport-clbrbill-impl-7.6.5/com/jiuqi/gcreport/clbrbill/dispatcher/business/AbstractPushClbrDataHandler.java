/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.ConverterUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.gcreport.basedata.impl.bean.GcBaseData
 *  com.jiuqi.gcreport.clbrbill.enums.BillTypeEnum
 *  com.jiuqi.gcreport.clbrbill.enums.ClbrBillTypeEnum
 *  com.jiuqi.gcreport.clbrbill.enums.ClbrStatesEnum
 *  com.jiuqi.np.core.context.ContextIdentity
 *  com.jiuqi.np.core.context.ContextUser
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.core.context.impl.NpContextIdentity
 *  com.jiuqi.np.core.context.impl.NpContextImpl
 *  com.jiuqi.np.core.context.impl.NpContextUser
 *  com.jiuqi.np.user.SystemUser
 *  com.jiuqi.np.user.User
 *  com.jiuqi.np.user.service.SystemUserService
 *  com.jiuqi.np.user.service.UserService
 *  com.jiuqi.va.bill.impl.BillContextImpl
 *  com.jiuqi.va.bill.impl.BillModelImpl
 *  com.jiuqi.va.bill.intf.BillContext
 *  com.jiuqi.va.bill.intf.BillDataService
 *  com.jiuqi.va.bill.intf.BillDefine
 *  com.jiuqi.va.bill.intf.BillDefineService
 *  com.jiuqi.va.bill.intf.BillState
 *  com.jiuqi.va.bizmeta.service.IMetaInfoService
 *  com.jiuqi.va.domain.basedata.BaseDataDO
 *  com.jiuqi.va.domain.basedata.BaseDataDTO
 *  com.jiuqi.va.domain.basedata.BaseDataOption$QueryDataStructure
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.common.RedisLockUtil
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.datamodel.DataModelColumn
 *  com.jiuqi.va.domain.datamodel.DataModelDO
 *  com.jiuqi.va.domain.datamodel.DataModelDTO
 *  com.jiuqi.va.domain.meta.MetaInfoDTO
 *  com.jiuqi.va.domain.user.UserLoginDTO
 *  com.jiuqi.va.feign.client.BaseDataClient
 *  com.jiuqi.va.feign.client.DataModelClient
 *  com.jiuqi.va.mapper.domain.TenantDO
 */
package com.jiuqi.gcreport.clbrbill.dispatcher.business;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.gcreport.basedata.impl.bean.GcBaseData;
import com.jiuqi.gcreport.clbrbill.dao.ClbrBillDao;
import com.jiuqi.gcreport.clbrbill.dao.ClbrOrgDao;
import com.jiuqi.gcreport.clbrbill.dispatcher.business.ClbrBusinessHandler;
import com.jiuqi.gcreport.clbrbill.dto.ClbrBillMasterDTO;
import com.jiuqi.gcreport.clbrbill.dto.ClbrPushDataDTO;
import com.jiuqi.gcreport.clbrbill.dto.ClbrSrcBillDTO;
import com.jiuqi.gcreport.clbrbill.enums.BillTypeEnum;
import com.jiuqi.gcreport.clbrbill.enums.ClbrBillTypeEnum;
import com.jiuqi.gcreport.clbrbill.enums.ClbrStatesEnum;
import com.jiuqi.gcreport.clbrbill.service.ClbrBillDataChecker;
import com.jiuqi.gcreport.clbrbill.service.ClbrBillWorkFlowService;
import com.jiuqi.gcreport.clbrbill.utils.ClbrBillUtils;
import com.jiuqi.np.core.context.ContextIdentity;
import com.jiuqi.np.core.context.ContextUser;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.core.context.impl.NpContextIdentity;
import com.jiuqi.np.core.context.impl.NpContextImpl;
import com.jiuqi.np.core.context.impl.NpContextUser;
import com.jiuqi.np.user.SystemUser;
import com.jiuqi.np.user.User;
import com.jiuqi.np.user.service.SystemUserService;
import com.jiuqi.np.user.service.UserService;
import com.jiuqi.va.bill.impl.BillContextImpl;
import com.jiuqi.va.bill.impl.BillModelImpl;
import com.jiuqi.va.bill.intf.BillContext;
import com.jiuqi.va.bill.intf.BillDataService;
import com.jiuqi.va.bill.intf.BillDefine;
import com.jiuqi.va.bill.intf.BillDefineService;
import com.jiuqi.va.bill.intf.BillState;
import com.jiuqi.va.bizmeta.service.IMetaInfoService;
import com.jiuqi.va.domain.basedata.BaseDataDO;
import com.jiuqi.va.domain.basedata.BaseDataDTO;
import com.jiuqi.va.domain.basedata.BaseDataOption;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.common.RedisLockUtil;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.datamodel.DataModelColumn;
import com.jiuqi.va.domain.datamodel.DataModelDO;
import com.jiuqi.va.domain.datamodel.DataModelDTO;
import com.jiuqi.va.domain.meta.MetaInfoDTO;
import com.jiuqi.va.domain.user.UserLoginDTO;
import com.jiuqi.va.feign.client.BaseDataClient;
import com.jiuqi.va.feign.client.DataModelClient;
import com.jiuqi.va.mapper.domain.TenantDO;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;

public abstract class AbstractPushClbrDataHandler
implements ClbrBusinessHandler<ClbrPushDataDTO, String> {
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractPushClbrDataHandler.class);
    private static final String PREFIX_HB_CODE = "HB";
    @Autowired
    BillDefineService billDefineService;
    @Autowired
    ClbrOrgDao clbrOrgDao;
    @Autowired
    ClbrBillDao clbrBillDao;
    @Autowired
    DataModelClient dataModelClient;
    @Autowired
    ClbrBillDataChecker clbrBillDataChecker;
    @Autowired
    ClbrBillWorkFlowService clbrBillWorkFlowService;
    @Autowired
    BaseDataClient baseDataClient;
    @Autowired
    UserService<User> userService;
    @Autowired
    SystemUserService sysUserService;
    @Autowired
    BillDataService billDataService;
    @Autowired
    IMetaInfoService metaInfoService;

    @Override
    public final String getBusinessCode() {
        return "PUSHCLBRDATA";
    }

    @Override
    public ClbrPushDataDTO beforeHandler(Object content) {
        Map billName2ContentMap = (Map)content;
        ClbrPushDataDTO pushDataDTO = new ClbrPushDataDTO();
        for (Map.Entry entry : billName2ContentMap.entrySet()) {
            String billName = (String)entry.getKey();
            Object billContent = entry.getValue();
            if (StringUtils.isEmpty((String)billName)) {
                LOGGER.info("\u5355\u636e\u540d\u79f0\u4e3a\u7a7a\uff0c\u8df3\u8fc7\u8f6c\u6362");
                continue;
            }
            if ("GC_CLBRBILL".equals(billName)) {
                ClbrBillMasterDTO clbrBillMasterDTO = this.convertToMasterDTO(billContent);
                pushDataDTO.setClbrBillMasterDTO(clbrBillMasterDTO);
                continue;
            }
            if ("GC_CLBRSRCBILLITEM".equals(billName)) {
                ClbrSrcBillDTO clbrSrcBillDTO = this.convertToSrcBillItemDTO(billContent);
                pushDataDTO.setClbrSrcBillDTO(clbrSrcBillDTO);
                continue;
            }
            this.convertToExtBill(billName, billContent, pushDataDTO);
        }
        return pushDataDTO;
    }

    @Override
    public final String handler(ClbrPushDataDTO content) {
        ClbrBillMasterDTO clbrBillMasterDTO = content.getClbrBillMasterDTO();
        ClbrSrcBillDTO clbrSrcBillDTO = content.getClbrSrcBillDTO();
        if (ObjectUtils.isEmpty(clbrBillMasterDTO)) {
            LOGGER.info("\u63a8\u9001\u62a5\u6587\u4e2d\u672a\u5305\u542b {} \u8868\u6570\u636e\uff0c\u4fdd\u5b58\u5931\u8d25", (Object)"GC_CLBRBILL");
            throw new BusinessRuntimeException("\u63a8\u9001\u62a5\u6587\u4e2d\u672a\u5305\u542bGC_CLBRBILL\u8868\u6570\u636e\uff0c\u4fdd\u5b58\u5931\u8d25");
        }
        if (ObjectUtils.isEmpty(clbrBillMasterDTO)) {
            LOGGER.info("\u63a8\u9001\u62a5\u6587\u4e2d\u672a\u5305\u542b {} \u8868\u6570\u636e\uff0c\u4fdd\u5b58\u5931\u8d25", (Object)"GC_CLBRSRCBILLITEM");
            throw new BusinessRuntimeException("\u63a8\u9001\u62a5\u6587\u4e2d\u672a\u5305\u542bGC_CLBRBILL\u8868\u6570\u636e\uff0c\u4fdd\u5b58\u5931\u8d25");
        }
        String result = clbrBillMasterDTO.getClbrCode();
        BillTypeEnum billTypeEnum = BillTypeEnum.getEnumByName((String)clbrSrcBillDTO.getSrcBillType());
        if (BillTypeEnum.NORMAL.equals((Object)billTypeEnum)) {
            if (StringUtils.isEmpty((String)clbrBillMasterDTO.getClbrCode())) {
                result = this.createNewBill(content);
            } else {
                this.saveBillData(content);
            }
        } else if (BillTypeEnum.WRITEOFF.equals((Object)billTypeEnum)) {
            this.writeOffBillData(content);
        }
        return result;
    }

    @Override
    public String afterHandler(ClbrPushDataDTO content, String result) {
        return result;
    }

    private String createNewBill(ClbrPushDataDTO pushDataDTO) {
        ClbrBillMasterDTO clbrBillMasterDTO = pushDataDTO.getClbrBillMasterDTO();
        ClbrSrcBillDTO clbrSrcBillDTO = pushDataDTO.getClbrSrcBillDTO();
        String token = ClbrBillUtils.getToken(clbrBillMasterDTO.getInitiateUser());
        this.changeUserContext(clbrBillMasterDTO.getInitiateUser());
        try {
            String oppUnitCode;
            ShiroUtil.bindToken((String)token);
            GcBaseData billMapping = ClbrBillUtils.queryBillMappingBaseData(clbrSrcBillDTO.getSrcBillDefine(), clbrSrcBillDTO.getSrcBusinessType());
            Assert.isNotNull((Object)billMapping, (String)("\u5171\u4eab\u5355\u636e\u5b9a\u4e49\uff1a" + clbrSrcBillDTO.getSrcBillDefine() + " \uff0c\u5171\u4eab\u5355\u636e\u7c7b\u578b\uff1a" + clbrSrcBillDTO.getSrcBusinessType() + "\uff0c\u534f\u540c\u5e73\u53f0\u672a\u7ef4\u62a4\u5355\u636e\u6620\u5c04\u5173\u7cfb\uff0c\u8bf7\u8054\u7cfb\u7ba1\u7406\u5458\u7ef4\u62a4\u3002"), (Object[])new Object[0]);
            MetaInfoDTO metaInfo = this.findMetaInfo(clbrSrcBillDTO.getSrcBillDefine(), clbrSrcBillDTO.getSrcBusinessType(), billMapping);
            BillModelImpl model = (BillModelImpl)this.billDefineService.createModel((BillContext)new BillContextImpl(), metaInfo.getUniqueCode());
            this.setContextValue(model);
            HashMap<String, Object> map = new HashMap<String, Object>(1);
            map.put("SRCBILLCODE", clbrSrcBillDTO.getSrcBillCode());
            map.put("BILLDEFINE", metaInfo.getName());
            String existClbrCode = this.clbrBillDataChecker.findExistSubBillData(map);
            if (!StringUtils.isEmpty((String)existClbrCode)) {
                model.loadByCode(existClbrCode);
                LOGGER.info("\u5355\u636e\u5b9a\u4e49\uff1a\u3010{}\u3011\uff0c\u5171\u4eab\u5355\u636e\u7f16\u53f7\u4e3a\uff1a\u3010{}\u3011\u5df2\u8fdb\u5165\u5173\u8054\u4ea4\u6613\u534f\u540c\u4e14\u534f\u540c\u7801\u4e3a\u3010{}\u3011\uff0c\u76f4\u63a5\u8fd4\u56de\u534f\u540c\u7801\u76f8\u5173\u4fe1\u606f\u3002\u3010\u53ef\u80fd\u662f\u7f51\u7edc\u6296\u52a8\u3001\u91cd\u8bd5\u5bfc\u81f4\u3011", metaInfo.getName(), clbrSrcBillDTO.getSrcBillCode(), existClbrCode);
                String string = existClbrCode;
                return string;
            }
            String unitCode = this.getUnitCode(clbrBillMasterDTO.getInitiateOrg());
            R result = RedisLockUtil.execute(() -> this.lambda$createNewBill$0(unitCode, oppUnitCode = this.getUnitCode(clbrBillMasterDTO.getReceiveOrg()), pushDataDTO, model, metaInfo, billMapping), (String)("AbstractClbrPushDataHandler#createNewBill" + clbrSrcBillDTO.getSrcBillCode()), (long)60000L, (boolean)true);
            if (result.getCode() != 0) {
                LOGGER.info("\u5171\u4eab\u5355\u636e\u7f16\u53f7\u4e3a\uff1a[{}]\u4fdd\u5b58\u5931\u8d25\uff0c{}", (Object)clbrSrcBillDTO.getSrcBillCode(), (Object)result.getMsg());
                throw new BusinessRuntimeException(result.getMsg());
            }
            this.handleWorkflowCommit(model);
            String string = (String)model.getMaster().getValue("BILLCODE", String.class);
            return string;
        }
        catch (Exception e) {
            LOGGER.error("\u521b\u5efa\u534f\u540c\u5355\u51fa\u73b0\u5f02\u5e38\uff0c\u5f02\u5e38\u4fe1\u606f\uff1a{}", (Object)e.getMessage(), (Object)e);
            throw new BusinessRuntimeException(e.getMessage(), (Throwable)e);
        }
        finally {
            ShiroUtil.unbindToken((String)token);
            ShiroUtil.unbindUser();
        }
    }

    private void handleWorkflowCommit(BillModelImpl model) {
        try {
            this.clbrBillWorkFlowService.commitBill(model);
        }
        catch (Exception e) {
            LOGGER.info("\u63d0\u4ea4\u5de5\u4f5c\u6d41\u51fa\u73b0\u5f02\u5e38\uff0c\u5f02\u5e38\u4fe1\u606f\uff1a{}", (Object)e.getMessage(), (Object)e);
        }
    }

    private void saveBillData(ClbrPushDataDTO pushDataDTO) {
        ClbrBillMasterDTO clbrBillMasterDTO = pushDataDTO.getClbrBillMasterDTO();
        ClbrSrcBillDTO clbrSrcBillDTO = pushDataDTO.getClbrSrcBillDTO();
        Map<String, List<Map<String, Object>>> extBillMap = pushDataDTO.getExtBillMap();
        String clbrCodes = clbrBillMasterDTO.getClbrCode();
        ArrayList<String> clbrList = new ArrayList<String>(Arrays.asList(clbrCodes.split(",")));
        this.beforeCheck(clbrList, clbrSrcBillDTO.getSrcBillCode(), clbrSrcBillDTO.getAmt());
        String unitCode = this.getUnitCode(clbrBillMasterDTO.getInitiateOrg());
        for (String clbrCode : clbrList) {
            this.saveBillDataByClbrCode(clbrCode, clbrList, clbrSrcBillDTO, clbrBillMasterDTO, unitCode, extBillMap);
        }
    }

    private void saveBillDataByClbrCode(String clbrCode, List<String> clbrList, ClbrSrcBillDTO clbrSrcBillDTO, ClbrBillMasterDTO clbrBillMasterDTO, String unitCode, Map<String, List<Map<String, Object>>> extBillMap) {
        ClbrBillTypeEnum clbrBillTypeEnum;
        Set srcBillCode;
        BillDefine billDefine = this.loadBillDefine(clbrCode);
        MetaInfoDTO metaInfo = this.metaInfoService.getMetaInfoByUniqueCode(billDefine.getName());
        BillModelImpl model = (BillModelImpl)this.billDefineService.createModel((BillContext)new BillContextImpl(), metaInfo.getUniqueCode());
        this.setContextValue(model);
        model.getRuler().getRulerExecutor().setEnable(true);
        model.loadByCode(clbrCode);
        model.getData().edit();
        String clbrState = (String)model.getMaster().getValue("CLBRSTATE", String.class);
        if (ClbrStatesEnum.ABANDON.name().equals(clbrState)) {
            throw new BusinessRuntimeException("\u534f\u540c\u5355\u5df2\u4f5c\u5e9f\uff0c\u65e0\u6cd5\u5f15\u7528\u3002");
        }
        Map tablesData = model.getData().getTablesData();
        List billItems = (List)tablesData.get("GC_CLBRSRCBILLITEM");
        if (!ObjectUtils.isEmpty(billItems) && (srcBillCode = billItems.stream().map(item -> item.get("SRCBILLCODE").toString()).collect(Collectors.toSet())).contains(clbrSrcBillDTO.getSrcBillCode())) {
            LOGGER.info("\u534f\u540c\u5355[{}]\u4e2d\u5df2\u5b58\u5728\u5171\u4eab\u5355\u636e\u7f16\u53f7[{}]\u7684\u6570\u636e\uff0c\u4e0d\u6267\u884c\u4fdd\u5b58\u3002", (Object)clbrCode, (Object)clbrSrcBillDTO.getSrcBillCode());
            return;
        }
        String initOrg = (String)model.getMaster().getValue("INITIATEORG", String.class);
        String receiveOrg = (String)model.getMaster().getValue("RECEIVEORG", String.class);
        String thirdOrg = (String)model.getMaster().getValue("THIRDORG", String.class);
        if (unitCode.equals(initOrg)) {
            clbrBillTypeEnum = ClbrBillTypeEnum.INITIATE;
            this.quoteByClbrBillType(model, clbrSrcBillDTO, clbrList, clbrCode, "INITIATEAMT", "INITIATEQUOTEAMT", "\u53d1\u8d77\u65b9");
        } else if (unitCode.equals(receiveOrg)) {
            clbrBillTypeEnum = ClbrBillTypeEnum.RECEIVE;
            this.quoteByClbrBillType(model, clbrSrcBillDTO, clbrList, clbrCode, "RECEIVEAMT", "RECEIVEQUOTEAMT", "\u63a5\u6536\u65b9");
        } else if (unitCode.equals(thirdOrg)) {
            clbrBillTypeEnum = ClbrBillTypeEnum.THIRD_PARTY;
            this.quoteByClbrBillType(model, clbrSrcBillDTO, clbrList, clbrCode, "THIRDAMT", "THIRDQUOTEAMT", "\u7b2c\u4e09\u65b9");
        } else {
            throw new BusinessRuntimeException("\u5f15\u7528\u534f\u540c\u5355\u6709\u8bef\uff0c\u534f\u540c\u5355\u672a\u5173\u8054\u6b64\u5355\u4f4d\uff1a" + unitCode + "\uff0c\u5171\u4eab\u5355\u4f4d\u4ee3\u7801\uff1a" + clbrBillMasterDTO.getInitiateOrg());
        }
        if (clbrList.size() > 1 && StringUtils.isEmpty((String)((String)model.getMaster().getValue("PACKAGECODE", String.class)))) {
            String packageCode = PREFIX_HB_CODE + clbrList.get(0);
            model.getMaster().setValue("PACKAGECODE", (Object)packageCode);
        }
        HashMap<String, Object> billRowData = new HashMap<String, Object>();
        billRowData.put("ID", UUIDUtils.newUUIDStr());
        billRowData.put("MASTERID", model.getMaster().getId());
        billRowData.put("VER", System.currentTimeMillis());
        billRowData.put("CREATETIME", new Date());
        billRowData.put("SYSCODE", clbrSrcBillDTO.getSysCode());
        billRowData.put("SRCBILLTYPE", clbrSrcBillDTO.getSrcBillType());
        billRowData.put("SRCBILLDEFINE", clbrSrcBillDTO.getSrcBillDefine());
        billRowData.put("SRCBILLNAME", clbrSrcBillDTO.getSrcBillName());
        billRowData.put("SRCBILLCODE", clbrSrcBillDTO.getSrcBillCode());
        billRowData.put("SRCBUSINESSTYPE", clbrSrcBillDTO.getSrcBusinessType());
        billRowData.put("SRCBILLSTATE", clbrSrcBillDTO.getSrcBillState());
        billRowData.put("CLBRBILLTYPE", clbrBillTypeEnum.name());
        billRowData.put("BILLDEFINE", metaInfo.getName());
        billRowData.put("VCHRACCOUNTPERIOD", clbrSrcBillDTO.getVchrAccountPeriod());
        billRowData.put("VCHRNUM", clbrSrcBillDTO.getVchrNum());
        billRowData.put("VCHRCREATETIME", clbrSrcBillDTO.getVchrCreateTime());
        billRowData.put("AMT", clbrSrcBillDTO.getAmt());
        for (Map.Entry<String, Object> entry : clbrSrcBillDTO.getExtendedFields().entrySet()) {
            billRowData.put(entry.getKey(), entry.getValue());
        }
        model.getTable("GC_CLBRSRCBILLITEM").appendRow(billRowData);
        if (!ObjectUtils.isEmpty(extBillMap)) {
            extBillMap.forEach((tableName, tableDataList) -> {
                if (tablesData.containsKey(tableName)) {
                    Map<String, DataModelColumn> name2ColumnMap = this.getTableColumn((String)tableName);
                    tableDataList.forEach(tableData -> {
                        tableData.keySet().removeIf(columnName -> !name2ColumnMap.containsKey(columnName));
                        tableData.put("ID", UUIDUtils.newUUIDStr());
                        tableData.put("MASTERID", model.getMaster().getId());
                        tableData.put("VER", System.currentTimeMillis());
                        model.getTable(tableName).appendRow(tableData);
                    });
                }
            });
        }
        ClbrBillUtils.saveModel(model);
    }

    private void quoteByClbrBillType(BillModelImpl model, ClbrSrcBillDTO clbrSrcBillDTO, List<String> clbrList, String clbrCode, String amtField, String quoteAmtField, String clbrBillTypeName) {
        Map masterData = (Map)((List)model.getData().getTablesData().get("GC_CLBRBILL")).get(0);
        Double amt = ConverterUtils.getAsDouble(masterData.get(amtField), (Double)0.0);
        Double quoteAmt = ConverterUtils.getAsDouble(masterData.get(quoteAmtField), (Double)0.0);
        if (clbrList.size() > 1) {
            model.getMaster().setValue(quoteAmtField, (Object)amt);
        } else {
            if (clbrSrcBillDTO.getAmt() > amt) {
                throw new BusinessRuntimeException(clbrBillTypeName + "\u5f15\u7528\u534f\u540c\u5355[" + clbrCode + "]\u6709\u8bef\uff0c\u5f15\u7528\u91d1\u989d\u8d85\u8fc7\u534f\u540c\u5355\u603b\u91d1\u989d\u3002\u534f\u540c\u5355\u603b\u91d1\u989d\uff1a" + amt + "\uff0c\u5f15\u7528\u91d1\u989d\uff1a" + clbrSrcBillDTO.getAmt());
            }
            if (clbrSrcBillDTO.getAmt() > amt - quoteAmt) {
                throw new BusinessRuntimeException(clbrBillTypeName + "\u5f15\u7528\u534f\u540c\u5355[" + clbrCode + "]\u6709\u8bef\uff0c\u5f15\u7528\u91d1\u989d\u8d85\u8fc7\u534f\u540c\u5355\u53ef\u5f15\u7528\u91d1\u989d\u3002\u534f\u540c\u5355\u603b\u91d1\u989d\uff1a" + amt + "\uff0c\u534f\u540c\u5355\u53ef\u5f15\u7528\u91d1\u989d\uff1a" + (amt - quoteAmt) + "\uff0c\u5f15\u7528\u91d1\u989d\uff1a" + clbrSrcBillDTO.getAmt());
            }
            model.getMaster().setValue(quoteAmtField, (Object)(quoteAmt + clbrSrcBillDTO.getAmt()));
        }
    }

    private void writeOffBillData(ClbrPushDataDTO pushDataDTO) {
        Double writeAMT;
        ClbrBillTypeEnum clbrBillTypeEnum;
        ClbrBillMasterDTO clbrBillMasterDTO = pushDataDTO.getClbrBillMasterDTO();
        ClbrSrcBillDTO clbrSrcBillDTO = pushDataDTO.getClbrSrcBillDTO();
        String clbrCode = clbrBillMasterDTO.getClbrCode();
        BillDefine billDefine = this.loadBillDefine(clbrCode);
        MetaInfoDTO metaInfo = this.metaInfoService.getMetaInfoByUniqueCode(billDefine.getName());
        BillModelImpl model = (BillModelImpl)this.billDefineService.createModel((BillContext)new BillContextImpl(), metaInfo.getUniqueCode());
        this.setContextValue(model);
        model.getRuler().getRulerExecutor().setEnable(true);
        model.loadByCode(clbrCode);
        model.getData().edit();
        String unitCode = this.getUnitCode(clbrBillMasterDTO.getInitiateOrg());
        String initOrg = (String)model.getMaster().getValue("INITIATEORG", String.class);
        String receiveOrg = (String)model.getMaster().getValue("RECEIVEORG", String.class);
        String thirdOrg = (String)model.getMaster().getValue("THIRDORG", String.class);
        if (unitCode.equals(initOrg)) {
            clbrBillTypeEnum = ClbrBillTypeEnum.INITIATE;
            writeAMT = (Double)model.getMaster().getValue("INITIATEQUOTEAMT", Double.class);
        } else if (unitCode.equals(receiveOrg)) {
            clbrBillTypeEnum = ClbrBillTypeEnum.RECEIVE;
            writeAMT = (Double)model.getMaster().getValue("RECEIVEQUOTEAMT", Double.class);
        } else if (unitCode.equals(thirdOrg)) {
            clbrBillTypeEnum = ClbrBillTypeEnum.THIRD_PARTY;
            writeAMT = (Double)model.getMaster().getValue("THIRDQUOTEAMT", Double.class);
        } else {
            throw new BusinessRuntimeException("\u534f\u540c\u5355\u672a\u5173\u8054\u6b64\u5355\u4f4d\uff1a" + unitCode + "\uff0c\u5171\u4eab\u5355\u4f4d\u4ee3\u7801\uff1a" + clbrBillMasterDTO.getInitiateOrg());
        }
        Map tablesData = model.getData().getTablesData();
        List billList = ((List)tablesData.get("GC_CLBRSRCBILLITEM")).stream().filter(item -> clbrBillTypeEnum.equals(item.get("CLBRBILLTYPE"))).collect(Collectors.toList());
        for (Map stringObjectMap : billList) {
            String srcBillCode = stringObjectMap.get("SRCBILLCODE").toString();
            if (!clbrSrcBillDTO.getSrcBillCode().equals(srcBillCode)) continue;
            throw new BusinessRuntimeException("\u534f\u540c\u5355\u4e2d\uff0c\u5171\u4eab\u5355\u636e\u7f16\u53f7\u4e3a\uff1a\u3010" + srcBillCode + "\u3011\u7684\u6570\u636e\u5df2\u5b58\u5728\uff0c\u8bf7\u52ff\u91cd\u590d\u63d0\u4ea4\u3002");
        }
        Double amt = Math.abs(clbrSrcBillDTO.getAmt());
        if (amt > writeAMT) {
            throw new BusinessRuntimeException("\u5171\u4eab\u5355\u636e\u7f16\u53f7\u4e3a\uff1a\u3010" + clbrSrcBillDTO.getSrcBillCode() + "\u3011\u7684\u5171\u4eab\u91d1\u989d[" + amt + "]\u4e0d\u80fd\u5927\u4e8e\u534f\u540c\u5355\u53ef\u51b2\u9500\u91d1\u989d[" + writeAMT + "]");
        }
        switch (clbrBillTypeEnum) {
            case INITIATE: {
                model.getMaster().setValue("INITIATEQUOTEAMT", (Object)(writeAMT - amt));
                break;
            }
            case RECEIVE: {
                model.getMaster().setValue("RECEIVEQUOTEAMT", (Object)(writeAMT - amt));
                break;
            }
            case THIRD_PARTY: {
                model.getMaster().setValue("THIRDQUOTEAMT", (Object)(writeAMT - amt));
                break;
            }
            default: {
                throw new BusinessRuntimeException("\u534f\u540c\u5355\u7c7b\u578b\u9519\u8bef\u3002");
            }
        }
        HashMap<String, Object> billRowData = new HashMap<String, Object>();
        billRowData.put("ID", UUIDUtils.newUUIDStr());
        billRowData.put("MASTERID", model.getMaster().getId());
        billRowData.put("VER", System.currentTimeMillis());
        billRowData.put("CREATETIME", new Date());
        billRowData.put("SYSCODE", clbrSrcBillDTO.getSysCode());
        billRowData.put("SRCBILLTYPE", clbrSrcBillDTO.getSrcBillType());
        billRowData.put("SRCBILLDEFINE", clbrSrcBillDTO.getSrcBillDefine());
        billRowData.put("SRCBILLNAME", clbrSrcBillDTO.getSrcBillName());
        billRowData.put("SRCBILLCODE", clbrSrcBillDTO.getSrcBillCode());
        billRowData.put("SRCBUSINESSTYPE", clbrSrcBillDTO.getSrcBusinessType());
        billRowData.put("SRCBILLSTATE", clbrSrcBillDTO.getSrcBillState());
        billRowData.put("CLBRBILLTYPE", clbrBillTypeEnum.name());
        billRowData.put("BILLDEFINE", metaInfo.getName());
        billRowData.put("VCHRACCOUNTPERIOD", clbrSrcBillDTO.getVchrAccountPeriod());
        billRowData.put("VCHRNUM", clbrSrcBillDTO.getVchrNum());
        billRowData.put("VCHRCREATETIME", clbrSrcBillDTO.getVchrCreateTime());
        billRowData.put("AMT", clbrSrcBillDTO.getAmt());
        if (!ObjectUtils.isEmpty(clbrSrcBillDTO.getExtendedFields())) {
            for (Map.Entry<String, Object> entry : clbrSrcBillDTO.getExtendedFields().entrySet()) {
                billRowData.put(entry.getKey(), entry.getValue());
            }
        }
        model.getTable("GC_CLBRSRCBILLITEM").appendRow(billRowData);
        ClbrBillUtils.saveModel(model);
    }

    private String getUnitCode(String srcOrgCode) {
        String unitCode = this.clbrOrgDao.findOrgCodeBySrcOrgCode(srcOrgCode);
        Assert.isNotEmpty((String)unitCode, (String)("\u534f\u540c\u5e73\u53f0\u672a\u7ef4\u62a4\u5171\u4eab\u5355\u4f4d[" + srcOrgCode + "]\u7684\u6620\u5c04\u3002"), (Object[])new Object[0]);
        return unitCode;
    }

    private void setContextValue(BillModelImpl model) {
        model.getContext().setContextValue("disableVerify", (Object)true);
    }

    private BillDefine loadBillDefine(String billCode) {
        TenantDO tenantDO = new TenantDO();
        HashMap<String, String> extInfo = new HashMap<String, String>();
        tenantDO.setExtInfo(extInfo);
        extInfo.put("billCode", billCode);
        return this.billDataService.getBillDefineByCode(tenantDO);
    }

    private void beforeCheck(List<String> clbrCodeList, String srcBillCode, Double amt) {
        List<Map<String, Object>> datas = this.clbrBillDao.queryClbrBillByClbrCode(clbrCodeList);
        if (datas.isEmpty()) {
            throw new BusinessRuntimeException("\u5f15\u7528\u534f\u540c\u5355\u5931\u8d25\uff0c\u5171\u4eab\u5355\u636e\u7f16\u53f7\u4e3a\uff1a[" + srcBillCode + "]\u5f15\u7528\u7684\u534f\u540c\u5355" + clbrCodeList + "\u4e0d\u5b58\u5728\u3002");
        }
        if (datas.size() < clbrCodeList.size()) {
            List existClbrCode = datas.stream().map(map -> ConverterUtils.getAsString(map.get("BILLCODE"))).collect(Collectors.toList());
            ArrayList<String> copyClbrCodeList = new ArrayList<String>(clbrCodeList);
            copyClbrCodeList.removeAll(existClbrCode);
            throw new BusinessRuntimeException("\u5f15\u7528\u534f\u540c\u5355\u5931\u8d25\uff0c\u5171\u4eab\u5355\u636e\u7f16\u53f7\u4e3a\uff1a[" + srcBillCode + "]\u5f15\u7528\u4e86[" + clbrCodeList.size() + "]\u5f20\u534f\u540c\u5355" + clbrCodeList + "\uff0c\u5176\u4e2d\u534f\u540c\u5355" + copyClbrCodeList + "\u4e0d\u5b58\u5728\u3002");
        }
        boolean existThird = datas.stream().anyMatch(map -> ConverterUtils.getAsBoolean(map.get("ISTRIPARTITE")));
        if (existThird) {
            this.beforeCheckThirdClbr(clbrCodeList, datas, srcBillCode, amt);
        } else {
            this.beforeCheckDoubleClbr(clbrCodeList, datas, srcBillCode, amt);
        }
    }

    private void beforeCheckDoubleClbr(List<String> clbrCodeList, List<Map<String, Object>> datas, String srcBillCode, Double amt) {
        if (datas.size() == 1) {
            return;
        }
        Double clbrBillSumAmt = 0.0;
        for (Map<String, Object> data : datas) {
            Double initiateAmt = ConverterUtils.getAsDouble((Object)data.get("INITIATEAMT"), (Double)0.0);
            clbrBillSumAmt = clbrBillSumAmt + initiateAmt;
        }
        if (!clbrBillSumAmt.equals(amt)) {
            throw new BusinessRuntimeException("\u5f15\u7528\u534f\u540c\u5355\u5931\u8d25\uff0c\u5171\u4eab\u5355\u636e[" + srcBillCode + "]\u5f15\u7528\u7684\u534f\u540c\u5355" + clbrCodeList + "\u4e2d\uff0c\u534f\u540c\u5355\u91d1\u989d\u603b\u548c\u4e0d\u7b49\u4e8e\u5171\u4eab\u5355\u636e\u91d1\u989d\u3002");
        }
    }

    private void beforeCheckThirdClbr(List<String> clbrCodeList, List<Map<String, Object>> datas, String srcBillCode, Double amt) {
        if (clbrCodeList.size() > 1) {
            throw new BusinessRuntimeException("\u5f15\u7528\u534f\u540c\u5355\u5931\u8d25\uff0c\u5f15\u7528\u534f\u540c\u5355\u4e2d\u5b58\u5728\u4e09\u65b9\u534f\u540c\u5355\u636e\u3002\u4e09\u65b9\u534f\u540c\u573a\u666f\u4e0b\uff0c\u4e0d\u652f\u6301\u5f15\u7528\u591a\u4e2a\u534f\u540c\u5355\uff0c\u5171\u4eab\u5355\u636e\u7f16\u53f7\u4e3a\uff1a[" + srcBillCode + "]\u3002");
        }
        String clbrCode = ConverterUtils.getAsString((Object)datas.get(0).get("BILLCODE"));
        Double initiateAmt = ConverterUtils.getAsDouble((Object)datas.get(0).get("INITIATEAMT"), (Double)0.0);
        Double receiveAmt = ConverterUtils.getAsDouble((Object)datas.get(0).get("RECEIVEAMT"), (Double)0.0);
        Double thirdAmt = ConverterUtils.getAsDouble((Object)datas.get(0).get("THIRDAMT"), (Double)0.0);
        if (!(initiateAmt.equals(amt) && receiveAmt.equals(amt) && thirdAmt.equals(amt))) {
            throw new BusinessRuntimeException("\u5f15\u7528\u534f\u540c\u5355\u5931\u8d25\uff0c\u4e09\u65b9\u534f\u540c\u573a\u666f\u4e0b\uff0c\u4e0d\u652f\u6301\u90e8\u5206\u5f15\u7528\u3002\u5171\u4eab\u5355\u636e[" + srcBillCode + "]\u5f15\u7528\u91d1\u989d\u4e0e\u534f\u540c\u5355[" + clbrCode + "]\u7684\u91d1\u989d\u4e0d\u4e00\u81f4\u3002\u5171\u4eab\u5355\u636e\u91d1\u989d\uff1a[" + amt + "]\uff0c\u534f\u540c\u5355\u91d1\u989d\uff1a[" + initiateAmt + "]\u3002");
        }
    }

    private void createNewBill(String unitCode, String oppUnitCode, ClbrPushDataDTO clbrBillDTO, BillModelImpl model, MetaInfoDTO metaInfo, GcBaseData billMapping) {
        ClbrBillMasterDTO clbrBillMasterDTO = clbrBillDTO.getClbrBillMasterDTO();
        ClbrSrcBillDTO clbrSrcBillDTO = clbrBillDTO.getClbrSrcBillDTO();
        Object clbrBusinessType = billMapping.getFieldVal("CLBRBUSINESSTYPE");
        if (Objects.isNull(clbrBusinessType)) {
            LOGGER.info("\u534f\u540c\u5e73\u53f0\u751f\u5355\u6620\u5c04\u672a\u914d\u7f6e{}\uff0c\u62a5\u8d26\u5355\u636e\u5b9a\u4e49\uff1a{}\uff0c\u62a5\u8d26\u4e1a\u52a1\u7c7b\u578b\uff1a{}", "\u534f\u540c\u4e1a\u52a1\u7c7b\u578b", clbrSrcBillDTO.getSrcBillDefine(), clbrSrcBillDTO.getSrcBusinessType());
        }
        Map<String, List<Map<String, Object>>> extBillMap = clbrBillDTO.getExtBillMap();
        LinkedHashMap data = new LinkedHashMap();
        Map tablesData = model.getData().getTablesData();
        ArrayList masterData = new ArrayList();
        HashMap<String, Object> masterRowData = new HashMap<String, Object>();
        String masterId = UUIDUtils.newUUIDStr();
        masterData.add(masterRowData);
        masterRowData.put("ID", masterId);
        masterRowData.put("UNITCODE", unitCode);
        masterRowData.put("BILLDATE", new Date());
        masterRowData.put("VER", System.currentTimeMillis());
        masterRowData.put("DEFINECODE", model.getDefine().getName());
        masterRowData.put("BILLSTATE", BillState.SAVED.getValue());
        masterRowData.put("CREATEUSER", NpContextHolder.getContext().getUserId());
        masterRowData.put("CREATETIME", new Date());
        masterRowData.put("DISABLESENDMAILFLAG", "0");
        masterRowData.put("GOTOLASTREJECT", "0");
        masterRowData.put("CURRENCY", clbrBillMasterDTO.getCurrency());
        masterRowData.put("CLBRSTATE", ClbrStatesEnum.SUBMIT.name());
        masterRowData.put("INITIATEORG", unitCode);
        masterRowData.put("RECEIVEORG", oppUnitCode);
        masterRowData.put("INITIATEUSER", this.getStaffObjCode(clbrBillMasterDTO.getInitiateUser().toUpperCase(Locale.ROOT), unitCode));
        masterRowData.put("RECEIVEUSER", this.getStaffObjCode(clbrBillMasterDTO.getReceiveUser().toUpperCase(Locale.ROOT), oppUnitCode));
        masterRowData.put("INITIATEAMT", clbrSrcBillDTO.getAmt());
        masterRowData.put("RECEIVEAMT", clbrSrcBillDTO.getAmt());
        masterRowData.put("INITIATEQUOTEAMT", clbrSrcBillDTO.getAmt());
        masterRowData.put("RECEIVEQUOTEAMT", 0.0);
        masterRowData.put("DIGEST", clbrBillMasterDTO.getDigest());
        masterRowData.put("CONTRACTCODE", clbrBillMasterDTO.getContractCode());
        masterRowData.put("CONTRACTNAME", clbrBillMasterDTO.getContractName());
        masterRowData.put("BUSINESSTYPE", clbrBusinessType);
        masterRowData.put("ISTRIPARTITE", false);
        Map<String, Object> masterExtendedFields = clbrBillMasterDTO.getExtendedFields();
        for (Map.Entry<String, Object> entry : masterExtendedFields.entrySet()) {
            masterRowData.put(entry.getKey(), entry.getValue());
        }
        data.put("GC_CLBRBILL", masterData);
        ArrayList billData = new ArrayList();
        HashMap<String, Object> billRowData = new HashMap<String, Object>();
        billData.add(billRowData);
        billRowData.put("ID", UUIDUtils.newUUIDStr());
        billRowData.put("MASTERID", masterId);
        billRowData.put("VER", System.currentTimeMillis());
        billRowData.put("CREATETIME", new Date());
        billRowData.put("SYSCODE", clbrSrcBillDTO.getSysCode());
        billRowData.put("SRCBILLTYPE", clbrSrcBillDTO.getSrcBillType());
        billRowData.put("SRCBILLDEFINE", clbrSrcBillDTO.getSrcBillDefine());
        billRowData.put("SRCBILLNAME", clbrSrcBillDTO.getSrcBillName());
        billRowData.put("SRCBILLCODE", clbrSrcBillDTO.getSrcBillCode());
        billRowData.put("SRCBUSINESSTYPE", clbrSrcBillDTO.getSrcBusinessType());
        billRowData.put("SRCBILLSTATE", clbrSrcBillDTO.getSrcBillState());
        billRowData.put("CLBRBILLTYPE", ClbrBillTypeEnum.INITIATE.name());
        billRowData.put("BILLDEFINE", metaInfo.getName());
        billRowData.put("VCHRACCOUNTPERIOD", clbrSrcBillDTO.getVchrAccountPeriod());
        billRowData.put("VCHRNUM", clbrSrcBillDTO.getVchrNum());
        billRowData.put("VCHRCREATETIME", clbrSrcBillDTO.getVchrCreateTime());
        billRowData.put("AMT", clbrSrcBillDTO.getAmt());
        Map<String, Object> srcExtendedFields = clbrBillMasterDTO.getExtendedFields();
        for (Map.Entry<String, Object> entry : srcExtendedFields.entrySet()) {
            billRowData.put(entry.getKey(), entry.getValue());
        }
        data.put("GC_CLBRSRCBILLITEM", billData);
        if (!ObjectUtils.isEmpty(extBillMap)) {
            extBillMap.forEach((tableName, tableDataList) -> {
                if (tablesData.containsKey(tableName)) {
                    Map<String, DataModelColumn> name2ColumnMap = this.getTableColumn((String)tableName);
                    tableDataList.forEach(tableData -> {
                        tableData.keySet().removeIf(columnName -> !name2ColumnMap.containsKey(columnName));
                        tableData.put("ID", UUIDUtils.newUUIDStr());
                        tableData.put("MASTERID", masterId);
                        tableData.put("VER", System.currentTimeMillis());
                    });
                    data.put((String)tableName, tableDataList);
                }
            });
        }
        model.getData().create();
        model.getData().setTablesData(data);
        ClbrBillUtils.saveModel(model);
    }

    private Map<String, DataModelColumn> getTableColumn(String tableName) {
        DataModelDTO dataModelDTO = new DataModelDTO();
        dataModelDTO.setName(tableName);
        DataModelDO dataModelDO = this.dataModelClient.get(dataModelDTO);
        List columns = dataModelDO.getColumns();
        return columns.stream().collect(Collectors.toMap(DataModelColumn::getColumnName, vo -> vo));
    }

    private MetaInfoDTO findMetaInfo(String srcBillDefine, String srcBusinessType, GcBaseData billMapping) {
        Object defineCode = billMapping.getFieldVal("CLBRBILLDEFINE");
        if (Objects.isNull(defineCode)) {
            LOGGER.info("\u534f\u540c\u5e73\u53f0\u751f\u5355\u6620\u5c04\u672a\u914d\u7f6e\u534f\u540c\u5355\u636e\u5b9a\u4e49\uff0c\u62a5\u8d26\u5355\u636e\u5b9a\u4e49\uff1a{}\uff0c\u62a5\u8d26\u4e1a\u52a1\u7c7b\u578b\uff1a{}", (Object)srcBillDefine, (Object)srcBusinessType);
            throw new BusinessRuntimeException("\u534f\u540c\u5e73\u53f0\u751f\u5355\u6620\u5c04\u672a\u914d\u7f6e\u534f\u540c\u5355\u636e\u5b9a\u4e49");
        }
        return ClbrBillUtils.getMetaInfo(ConverterUtils.getAsString((Object)defineCode));
    }

    private String getStaffObjCode(String code, String unitCode) {
        BaseDataDTO staffParam = new BaseDataDTO();
        staffParam.setTableName("MD_STAFF");
        staffParam.setStopflag(Integer.valueOf(-1));
        staffParam.setRecoveryflag(Integer.valueOf(0));
        staffParam.setUnitcode(unitCode);
        staffParam.setCode(code.toUpperCase(Locale.ROOT));
        staffParam.setQueryDataStructure(BaseDataOption.QueryDataStructure.ALL_WITH_REF);
        PageVO res = this.baseDataClient.list(staffParam);
        if (0 != res.getRs().getCode()) {
            throw new BusinessRuntimeException(res.getRs().getMsg());
        }
        List rows = res.getRows();
        List collect = rows.stream().filter(item -> item.get((Object)"userunitcode").equals(unitCode)).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(collect)) {
            LOGGER.info("\u804c\u5458\u4e0d\u5b58\u5728\uff0c\u68c0\u67e5\u914d\u7f6e\uff0c\u5355\u4f4d\uff1a" + unitCode + ",\u7528\u6237\u4ee3\u7801\uff1a" + code);
            return code;
        }
        if (collect.size() > 1) {
            LOGGER.info("\u804c\u5458\u5b58\u5728\u591a\u6761\uff0c\u68c0\u67e5\u914d\u7f6e\uff0c\u5355\u4f4d\uff1a" + unitCode + ",\u7528\u6237\u4ee3\u7801\uff1a" + code);
            return code;
        }
        return ((BaseDataDO)collect.get(0)).getObjectcode();
    }

    private ClbrBillMasterDTO convertToMasterDTO(Object content) {
        Map map = (Map)content;
        Map<String, Object> upperKeyMap = map.entrySet().stream().collect(Collectors.toMap(entry -> ((String)entry.getKey()).toUpperCase(Locale.ROOT), Map.Entry::getValue));
        ClbrBillMasterDTO dto = new ClbrBillMasterDTO();
        if (ObjectUtils.isEmpty(upperKeyMap)) {
            return dto;
        }
        block22: for (Map.Entry<String, Object> entry2 : upperKeyMap.entrySet()) {
            String key;
            switch (key = entry2.getKey()) {
                case "CLBRCODE": {
                    dto.setClbrCode(ConverterUtils.getAsString((Object)upperKeyMap.get("CLBRCODE")));
                    continue block22;
                }
                case "INITIATEUSER": {
                    dto.setInitiateUser(ConverterUtils.getAsString((Object)upperKeyMap.get("INITIATEUSER")));
                    continue block22;
                }
                case "INITIATEORG": {
                    dto.setInitiateOrg(ConverterUtils.getAsString((Object)upperKeyMap.get("INITIATEORG")));
                    continue block22;
                }
                case "RECEIVEUSER": {
                    dto.setReceiveUser(ConverterUtils.getAsString((Object)upperKeyMap.get("RECEIVEUSER")));
                    continue block22;
                }
                case "RECEIVEORG": {
                    dto.setReceiveOrg(ConverterUtils.getAsString((Object)upperKeyMap.get("RECEIVEORG")));
                    continue block22;
                }
                case "CONTRACTCODE": {
                    dto.setContractCode(ConverterUtils.getAsString((Object)upperKeyMap.get("CONTRACTCODE")));
                    continue block22;
                }
                case "CONTRACTNAME": {
                    dto.setContractName(ConverterUtils.getAsString((Object)upperKeyMap.get("CONTRACTNAME")));
                    continue block22;
                }
                case "CURRENCY": {
                    dto.setCurrency(ConverterUtils.getAsString((Object)upperKeyMap.get("CURRENCY")));
                    continue block22;
                }
                case "DIGEST": {
                    dto.setDigest(ConverterUtils.getAsString((Object)upperKeyMap.get("DIGEST")));
                    continue block22;
                }
            }
            dto.putExtendedFields(key, upperKeyMap.get(key));
        }
        return dto;
    }

    private ClbrSrcBillDTO convertToSrcBillItemDTO(Object content) {
        Map map = (Map)content;
        Map<String, Object> upperKeyMap = map.entrySet().stream().collect(Collectors.toMap(entry -> ((String)entry.getKey()).toUpperCase(Locale.ROOT), Map.Entry::getValue));
        ClbrSrcBillDTO dto = new ClbrSrcBillDTO();
        if (ObjectUtils.isEmpty(upperKeyMap)) {
            return dto;
        }
        block28: for (Map.Entry<String, Object> entry2 : upperKeyMap.entrySet()) {
            String key;
            switch (key = entry2.getKey()) {
                case "SRCBILLTYPE": {
                    dto.setSrcBillType(ConverterUtils.getAsString((Object)upperKeyMap.get("SRCBILLTYPE")));
                    continue block28;
                }
                case "CLBRBILLTYPE": {
                    dto.setClbrBillType(ConverterUtils.getAsString((Object)upperKeyMap.get("CLBRBILLTYPE")));
                    continue block28;
                }
                case "VCHRCREATETIME": {
                    dto.setVchrCreateTime(ConverterUtils.getAsString((Object)upperKeyMap.get("VCHRCREATETIME")));
                    continue block28;
                }
                case "VCHRACCOUNTPERIOD": {
                    dto.setVchrAccountPeriod(ConverterUtils.getAsString((Object)upperKeyMap.get("VCHRACCOUNTPERIOD")));
                    continue block28;
                }
                case "VCHRNUM": {
                    dto.setVchrNum(ConverterUtils.getAsString((Object)upperKeyMap.get("VCHRNUM")));
                    continue block28;
                }
                case "SYSCODE": {
                    dto.setSysCode(ConverterUtils.getAsString((Object)upperKeyMap.get("SYSCODE")));
                    continue block28;
                }
                case "SRCBILLNAME": {
                    dto.setSrcBillName(ConverterUtils.getAsString((Object)upperKeyMap.get("SRCBILLNAME")));
                    continue block28;
                }
                case "SRCBILLSTATE": {
                    dto.setSrcBillState(ConverterUtils.getAsString((Object)upperKeyMap.get("SRCBILLSTATE")));
                    continue block28;
                }
                case "SRCBILLCODE": {
                    dto.setSrcBillCode(ConverterUtils.getAsString((Object)upperKeyMap.get("SRCBILLCODE")));
                    continue block28;
                }
                case "SRCBILLDEFINE": {
                    dto.setSrcBillDefine(ConverterUtils.getAsString((Object)upperKeyMap.get("SRCBILLDEFINE")));
                    continue block28;
                }
                case "SRCBUSINESSTYPE": {
                    dto.setSrcBusinessType(ConverterUtils.getAsString((Object)upperKeyMap.get("SRCBUSINESSTYPE")));
                    continue block28;
                }
                case "AMT": {
                    dto.setAmt(ConverterUtils.getAsDouble((Object)upperKeyMap.get("AMT")));
                    continue block28;
                }
            }
            dto.putExtendedFields(key, upperKeyMap.get(key));
        }
        return dto;
    }

    private void convertToExtBill(String billName, Object content, ClbrPushDataDTO pushDataDTO) {
        if (ObjectUtils.isEmpty(content)) {
            return;
        }
        ArrayList<Map<String, Object>> extData = new ArrayList<Map<String, Object>>();
        for (Map map : (List)content) {
            for (Map.Entry entry : map.entrySet()) {
                if (!StringUtils.isEmpty((String)((String)entry.getKey())) && !ObjectUtils.isEmpty(entry.getValue())) continue;
                map.remove(entry.getKey());
            }
            extData.add(map);
        }
        pushDataDTO.putExtBillMap(billName.toUpperCase(Locale.ROOT), extData);
    }

    private void changeUserContext(String userName) {
        User user = this.userService.findByUsername(userName).orElseGet(() -> (SystemUser)this.sysUserService.findByUsername(userName).orElseThrow(() -> new IllegalArgumentException("\u534f\u540c\u7cfb\u7edf\u627e\u4e0d\u5230\u7528\u6237\u540d\u4e3a[" + userName + "]\u7684\u7528\u6237\u4fe1\u606f\u3002")));
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
        userDTO.setLoginUnit(user.getOrgCode());
        userDTO.setLoginDate(new Date());
        ShiroUtil.bindUser((UserLoginDTO)userDTO);
    }

    private /* synthetic */ void lambda$createNewBill$0(String unitCode, String oppUnitCode, ClbrPushDataDTO pushDataDTO, BillModelImpl model, MetaInfoDTO metaInfo, GcBaseData billMapping) {
        this.createNewBill(unitCode, oppUnitCode, pushDataDTO, model, metaInfo, billMapping);
    }
}

