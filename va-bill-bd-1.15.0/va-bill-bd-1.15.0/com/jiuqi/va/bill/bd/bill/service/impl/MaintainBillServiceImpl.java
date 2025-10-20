/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.bill.action.SaveAction
 *  com.jiuqi.va.bill.impl.BillContextImpl
 *  com.jiuqi.va.bill.impl.BillModelImpl
 *  com.jiuqi.va.bill.intf.BillException
 *  com.jiuqi.va.bill.intf.BillState
 *  com.jiuqi.va.bill.utils.VaSyncDataUtils
 *  com.jiuqi.va.biz.impl.data.DataFieldDefineImpl
 *  com.jiuqi.va.biz.impl.data.DataFieldImpl
 *  com.jiuqi.va.biz.intf.action.Action
 *  com.jiuqi.va.biz.intf.action.ActionRequest
 *  com.jiuqi.va.biz.intf.action.ActionResponse
 *  com.jiuqi.va.biz.intf.data.DataFieldDefine
 *  com.jiuqi.va.biz.intf.data.DataRow
 *  com.jiuqi.va.biz.intf.model.Model
 *  com.jiuqi.va.biz.intf.model.ModelDefineService
 *  com.jiuqi.va.datasync.domain.SyncDataFeedbackEnum
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.OrderNumUtil
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.user.UserLoginDTO
 *  com.jiuqi.va.trans.domain.VaTransMessageDO
 *  org.springframework.amqp.core.AmqpTemplate
 */
package com.jiuqi.va.bill.bd.bill.service.impl;

import com.jiuqi.va.bill.action.SaveAction;
import com.jiuqi.va.bill.bd.bill.common.BillAlterModelUpdateConst;
import com.jiuqi.va.bill.bd.bill.domain.CreateBillEntry;
import com.jiuqi.va.bill.bd.bill.domain.MapInfoDTO;
import com.jiuqi.va.bill.bd.bill.domain.MultiValueDTO;
import com.jiuqi.va.bill.bd.bill.model.BasedataApplyBillModel;
import com.jiuqi.va.bill.bd.bill.model.BillAlterModel;
import com.jiuqi.va.bill.bd.bill.model.RegistrationBillModel;
import com.jiuqi.va.bill.bd.bill.service.MaintainBillService;
import com.jiuqi.va.bill.bd.bill.service.MultiValueService;
import com.jiuqi.va.bill.bd.bill.service.WriteBackService;
import com.jiuqi.va.bill.bd.core.domain.ApplyRegMapDO;
import com.jiuqi.va.bill.bd.core.domain.BillChangeRecordDO;
import com.jiuqi.va.bill.bd.core.service.BillChangeRecordService;
import com.jiuqi.va.bill.bd.core.service.MaintainBillExceptionService;
import com.jiuqi.va.bill.bd.utils.BillBdI18nUtil;
import com.jiuqi.va.bill.impl.BillContextImpl;
import com.jiuqi.va.bill.impl.BillModelImpl;
import com.jiuqi.va.bill.intf.BillException;
import com.jiuqi.va.bill.intf.BillState;
import com.jiuqi.va.bill.utils.VaSyncDataUtils;
import com.jiuqi.va.biz.impl.data.DataFieldDefineImpl;
import com.jiuqi.va.biz.impl.data.DataFieldImpl;
import com.jiuqi.va.biz.intf.action.Action;
import com.jiuqi.va.biz.intf.action.ActionRequest;
import com.jiuqi.va.biz.intf.action.ActionResponse;
import com.jiuqi.va.biz.intf.data.DataFieldDefine;
import com.jiuqi.va.biz.intf.data.DataRow;
import com.jiuqi.va.biz.intf.model.Model;
import com.jiuqi.va.biz.intf.model.ModelDefineService;
import com.jiuqi.va.datasync.domain.SyncDataFeedbackEnum;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.OrderNumUtil;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.user.UserLoginDTO;
import com.jiuqi.va.trans.domain.VaTransMessageDO;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
public class MaintainBillServiceImpl
implements MaintainBillService {
    private static final Logger logger = LoggerFactory.getLogger(MaintainBillServiceImpl.class);
    @Autowired
    MultiValueService multiValueService;
    @Autowired
    ModelDefineService modelDefineService;
    @Autowired
    WriteBackService writeBackService;
    @Autowired
    MaintainBillExceptionService exceptionHanderService;
    @Autowired
    BillChangeRecordService billChangeRecordService;
    @Autowired
    AmqpTemplate rabbitTemplate;
    @Autowired
    SaveAction saveAction;

    @Override
    public void createBillByMatser(MapInfoDTO mapinfo, BillModelImpl regBillModal, Map<String, List<Map<String, Object>>> applyBillModal) {
        DataRow regBillMasterData = regBillModal.getMaster();
        regBillMasterData.getString("UNITCODE");
        if (mapinfo.isMaster()) {
            String applyuserId = applyBillModal.get(mapinfo.getSrctablename()).get(0).get("CREATEUSER") == null ? "" : applyBillModal.get(mapinfo.getSrctablename()).get(0).get("CREATEUSER").toString();
            regBillMasterData.setValue("CREATEUSER", (Object)applyuserId);
            regBillMasterData.setValue("UNITCODE", applyBillModal.get(mapinfo.getSrctablename()).get(0).get("UNITCODE"));
        }
        ArrayList listData = new ArrayList();
        HashMap multiregitemDataAll = new HashMap();
        try {
            Map<String, Object> maps = applyBillModal.get(mapinfo.getSrctablename()).get(0);
            regBillMasterData.setValue("CREATEUSER", maps.get("CREATEUSER"));
            for (Map.Entry<String, List<Map<String, Object>>> entry : applyBillModal.entrySet()) {
                DataFieldDefineImpl fieldDefine;
                if (mapinfo.getSrctablename().equals(entry.getKey()) && mapinfo.isMaster()) {
                    BillChangeRecordDO record = new BillChangeRecordDO();
                    record.setBilltable(mapinfo.getTablename());
                    record.setChangeuser(ShiroUtil.getUser().getName());
                    record.setBillcode(regBillMasterData.getString("BILLCODE"));
                    record.setBilldefine(regBillModal.getDefine().getName());
                    record.setChangetype(3);
                    Map<String, Object> masterValue = entry.getValue().get(0);
                    for (MapInfoDTO.Filedmap filedmap : mapinfo.getFiledmaps()) {
                        if (filedmap.getSrcfiledname() == null || "".equals(filedmap.getSrcfiledname()) || BillAlterModelUpdateConst.getUnAssignFields().contains(filedmap.getFiledname()) || !masterValue.containsKey(filedmap.getSrcfiledname()) || masterValue.get(filedmap.getSrcfiledname()) == null) continue;
                        try {
                            fieldDefine = ((DataFieldImpl)regBillModal.getMasterTable().getFields().find(filedmap.getFiledname())).getDefine();
                        }
                        catch (Exception e) {
                            throw new RuntimeException(BillBdI18nUtil.getMessage("va.billbd.check.mapping.field.exist") + regBillModal.getMasterTable().getName() + "\uff1a" + filedmap.getFiledname());
                        }
                        if (fieldDefine.isMultiChoice()) {
                            List<MultiValueDTO> multiValues = this.getMultiValue(masterValue.get("ID"), null, masterValue.get(filedmap.getSrcfiledname()), mapinfo.getSrctablename());
                            String newBindingid = UUID.randomUUID().toString();
                            HashMap<String, List<Map<String, Object>>> multiregitemData = this.initMultiRow(null, regBillMasterData.getId().toString(), newBindingid, mapinfo.getTablename(), multiValues);
                            if (multiregitemData != null) {
                                listData.addAll(multiregitemData.get(mapinfo.getTablename() + "_M"));
                                regBillMasterData.setValue(filedmap.getFiledname(), (Object)newBindingid);
                                continue;
                            }
                            regBillMasterData.setValue(filedmap.getFiledname(), null);
                            continue;
                        }
                        regBillMasterData.setValue(filedmap.getFiledname(), masterValue.get(filedmap.getSrcfiledname()));
                    }
                    multiregitemDataAll.put(mapinfo.getTablename() + "_M", listData);
                    regBillModal.getData().setTablesData(multiregitemDataAll);
                    if (StringUtils.hasText(regBillMasterData.getString("CODE"))) {
                        record.setChangefiledname(regBillMasterData.getString("CODE"));
                    } else {
                        record.setChangefiledname(regBillMasterData.getString("BILLCODE"));
                    }
                    this.billChangeRecordService.add(record);
                }
                if (!mapinfo.getSrctablename().equals(entry.getKey()) || mapinfo.isMaster()) continue;
                ArrayList regitemListData = new ArrayList();
                HashMap regitemDataAll = new HashMap();
                ArrayList regitemValues = new ArrayList();
                List<Map<String, Object>> applyitemValues = entry.getValue();
                for (Map<String, Object> applyitemValue : applyitemValues) {
                    UUID groupid = UUID.randomUUID();
                    HashMap<String, Object> regitemValue = new HashMap<String, Object>();
                    regitemValue.put("ID", groupid);
                    BigDecimal ver = BigDecimal.valueOf(OrderNumUtil.getOrderNumByCurrentTimeMillis().doubleValue());
                    regitemValue.put("VER", ver);
                    regitemValue.put("MASTERID", regBillMasterData.getId());
                    regitemValue.put("BILLCODE", regBillMasterData.getValue("BILLCODE"));
                    for (MapInfoDTO.Filedmap filedmap : mapinfo.getFiledmaps()) {
                        if (filedmap.getSrcfiledname() == null || "".equals(filedmap.getSrcfiledname()) || applyitemValue.get(filedmap.getSrcfiledname()) == null) continue;
                        try {
                            fieldDefine = ((DataFieldImpl)regBillModal.getTable(mapinfo.getTablename()).getFields().find(filedmap.getFiledname())).getDefine();
                        }
                        catch (Exception e) {
                            throw new RuntimeException(BillBdI18nUtil.getMessage("va.billbd.check.mapping.field.exist") + mapinfo.getTablename() + "\uff1a" + filedmap.getFiledname());
                        }
                        if (fieldDefine.isMultiChoice()) {
                            List<MultiValueDTO> multiValues = this.getMultiValue(applyitemValue.get("MASTERID"), applyitemValue.get("ID"), applyitemValue.get(filedmap.getSrcfiledname()), mapinfo.getSrctablename());
                            UUID newBindingid = UUID.randomUUID();
                            HashMap<String, List<Map<String, Object>>> regitemData = this.initMultiRow(null, regBillMasterData.getId().toString(), newBindingid.toString(), mapinfo.getTablename(), multiValues);
                            if (regitemData != null) {
                                regitemListData.addAll(regitemData.get(mapinfo.getTablename() + "_M"));
                                regitemDataAll.put(mapinfo.getTablename() + "_M", regitemListData);
                                regitemValue.put(filedmap.getFiledname(), newBindingid);
                                continue;
                            }
                            regitemValue.put(filedmap.getFiledname(), null);
                            continue;
                        }
                        regitemValue.put(filedmap.getFiledname(), applyitemValue.get(filedmap.getSrcfiledname()));
                    }
                    regitemValues.add(regitemValue);
                }
                regitemDataAll.put(mapinfo.getTablename(), regitemValues);
                regBillModal.getData().setTablesData(regitemDataAll);
            }
        }
        catch (Exception e) {
            logger.error("\u4e3b\u8868\u751f\u5355\u5931\u8d25", e);
            throw e;
        }
    }

    @Override
    public void setValuesDetail(RegistrationBillModel regBillModal, Map<String, Object> applyitemValue, Map<String, List<Map<String, Object>>> applyBillModal, ApplyRegMapDO define) {
        regBillModal.add();
        DataRow regBillMasterData = regBillModal.getMaster();
        Map applyMasterValue = (Map)((List)((Map.Entry)applyBillModal.entrySet().stream().findFirst().get()).getValue()).get(0);
        regBillMasterData.setValue("CREATEUSER", applyMasterValue.get("CREATEUSER"));
        regBillMasterData.setValue("UNITCODE", applyMasterValue.get("UNITCODE"));
        String masterid = ((DataRow)regBillModal.getMasterTable().getRows().get(0)).getUUID("ID").toString();
        DataFieldDefineImpl fieldDefine = new DataFieldDefineImpl();
        ArrayList regitemListData = new ArrayList();
        HashMap regitemDataAll = new HashMap();
        List mapinfos = JSONUtil.parseMapArray((String)define.getMapinfos());
        try {
            for (int i = 0; i < mapinfos.size(); ++i) {
                HashMap<String, List<Map<String, Object>>> regitemData;
                MapInfoDTO mapinfo = (MapInfoDTO)JSONUtil.parseObject((String)JSONUtil.toJSONString(mapinfos.get(i)), MapInfoDTO.class);
                if (mapinfo.isMaster()) {
                    for (MapInfoDTO.Filedmap filedmap : mapinfo.getFiledmaps()) {
                        if (filedmap.getSrcfiledname() == null || "".equals(filedmap.getSrcfiledname()) || applyitemValue.get(filedmap.getSrcfiledname()) == null || BillAlterModelUpdateConst.getUnAssignFields().contains(filedmap.getFiledname())) continue;
                        try {
                            fieldDefine = ((DataFieldImpl)regBillModal.getMasterTable().getFields().find(filedmap.getFiledname())).getDefine();
                        }
                        catch (Exception e) {
                            throw new BillException(e.getMessage());
                        }
                        if (fieldDefine.isMultiChoice()) {
                            List<MultiValueDTO> multiValues = this.getMultiValue(applyitemValue.get("MASTERID"), applyitemValue.get("ID"), applyitemValue.get(filedmap.getSrcfiledname()), mapinfo.getSrctablename());
                            String newBindingid = UUID.randomUUID().toString();
                            regitemData = this.initMultiRow(null, masterid, newBindingid, mapinfo.getTablename(), multiValues);
                            if (regitemData != null) {
                                regitemListData.addAll(regitemData.get(mapinfo.getTablename() + "_M"));
                                regitemDataAll.put(mapinfo.getTablename() + "_M", regitemListData);
                                regBillMasterData.setValue(filedmap.getFiledname(), (Object)newBindingid);
                                regBillModal.getData().setTablesData(regitemDataAll);
                                continue;
                            }
                            regBillMasterData.setValue(filedmap.getFiledname(), null);
                            continue;
                        }
                        regBillMasterData.setValue(filedmap.getFiledname(), applyitemValue.get(filedmap.getSrcfiledname()));
                    }
                }
                if (mapinfo.isMaster()) continue;
                ArrayList regitemValues = new ArrayList();
                HashMap<String, Object> regitemValue = new HashMap<String, Object>();
                regitemValue.put("ID", UUID.randomUUID());
                BigDecimal ver = BigDecimal.valueOf(OrderNumUtil.getOrderNumByCurrentTimeMillis().doubleValue());
                regitemValue.put("VER", ver);
                regitemValue.put("MASTERID", regBillMasterData.getId());
                regitemValue.put("BILLCODE", regBillMasterData.getValue("BILLCODE"));
                regitemValue.put("ORDINAL", applyitemValue.get("ORDINAL"));
                ArrayList regitemListDataItem = new ArrayList();
                HashMap regitemDataAllItem = new HashMap();
                for (MapInfoDTO.Filedmap filedmap : mapinfo.getFiledmaps()) {
                    if (filedmap.getSrcfiledname() == null || "".equals(filedmap.getSrcfiledname())) continue;
                    try {
                        fieldDefine = ((DataFieldImpl)regBillModal.getTable(mapinfo.getTablename()).getFields().find(filedmap.getFiledname())).getDefine();
                    }
                    catch (Exception e) {
                        logger.error("\u68c0\u67e5\u6620\u5c04\u914d\u7f6e\u4e2d\u662f\u5426\u5b58\u5728\u76ee\u6807\u5355\u4e0a\u5df2\u5220\u9664\u7684\u5b57\u6bb5," + mapinfo.getTablename() + ":" + filedmap.getFiledname(), e);
                        throw new BillException(e.getMessage());
                    }
                    if (fieldDefine.isMultiChoice()) {
                        List<MultiValueDTO> multiValues = this.getMultiValue(applyitemValue.get("MASTERID"), applyitemValue.get("ID"), applyitemValue.get(filedmap.getSrcfiledname()), mapinfo.getSrctablename());
                        UUID newBindingid = UUID.randomUUID();
                        regitemData = this.initMultiRow(null, masterid, newBindingid.toString(), mapinfo.getTablename(), multiValues);
                        if (regitemData != null) {
                            regitemListDataItem.addAll(regitemData.get(mapinfo.getTablename() + "_M"));
                            regitemDataAllItem.put(mapinfo.getTablename() + "_M", regitemListDataItem);
                            regitemValue.put(filedmap.getFiledname(), newBindingid);
                            continue;
                        }
                        regitemValue.put(filedmap.getFiledname(), null);
                        continue;
                    }
                    regitemValue.put(filedmap.getFiledname(), applyitemValue.get(filedmap.getSrcfiledname()));
                }
                regitemValues.add(regitemValue);
                regitemDataAllItem.put(mapinfo.getTablename(), regitemValues);
                regBillModal.getData().setTablesData(regitemDataAllItem);
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new BillException(e.getMessage());
        }
    }

    @Override
    public void createBillByDetail(RegistrationBillModel regBillModal, String messageId, CreateBillEntry createBillEntry, Map<String, Object> applyitemValue, Map<String, List<Map<String, Object>>> applyBillModal) {
        BillContextImpl context = new BillContextImpl();
        context.setDisableVerify(true);
        context.setTenantName(regBillModal.getContext().getTenantName());
        String tablename = createBillEntry.getTablename();
        ApplyRegMapDO define = createBillEntry.getDefine();
        regBillModal.getContext().getUnitCode();
        regBillModal.add();
        DataRow regBillMasterData = regBillModal.getMaster();
        Map applyMasterValue = (Map)((List)((Map.Entry)applyBillModal.entrySet().stream().findFirst().get()).getValue()).get(0);
        regBillMasterData.setValue("CREATEUSER", applyMasterValue.get("CREATEUSER"));
        regBillMasterData.setValue("UNITCODE", applyMasterValue.get("UNITCODE"));
        String masterid = ((DataRow)regBillModal.getMasterTable().getRows().get(0)).getUUID("ID").toString();
        ArrayList regitemListData = new ArrayList();
        HashMap regitemDataAll = new HashMap();
        List mapinfos = JSONUtil.parseMapArray((String)define.getMapinfos());
        MapInfoDTO mapinfo = null;
        try {
            for (int i = 0; i < mapinfos.size(); ++i) {
                HashMap<String, List<Map<String, Object>>> regitemData;
                DataFieldDefineImpl fieldDefine;
                mapinfo = (MapInfoDTO)JSONUtil.parseObject((String)JSONUtil.toJSONString(mapinfos.get(i)), MapInfoDTO.class);
                if (mapinfo.getSrctablename().equals(tablename) && mapinfo.isMaster()) {
                    BillChangeRecordDO record = new BillChangeRecordDO();
                    record.setBillcode(regBillMasterData.getString("BILLCODE"));
                    record.setBilldefine(regBillModal.getDefine().getName());
                    record.setSrcbillcode((String)applyitemValue.get("BILLCODE"));
                    record.setSrcbilldefine(createBillEntry.getAlterdefinecode());
                    record.setChangeuser(ShiroUtil.getUser().getName());
                    record.setChangetype(4);
                    record.setBilltable(mapinfo.getTablename());
                    for (MapInfoDTO.Filedmap filedmap : mapinfo.getFiledmaps()) {
                        if (filedmap.getSrcfiledname() == null || "".equals(filedmap.getSrcfiledname()) || applyitemValue.get(filedmap.getSrcfiledname()) == null || BillAlterModelUpdateConst.getUnAssignFields().contains(filedmap.getFiledname())) continue;
                        try {
                            fieldDefine = ((DataFieldImpl)regBillModal.getMasterTable().getFields().find(filedmap.getFiledname())).getDefine();
                        }
                        catch (Exception e) {
                            throw new RuntimeException(BillBdI18nUtil.getMessage("va.billbd.check.mapping.field.exist") + regBillModal.getMasterTable().getName() + "\uff1a" + filedmap.getFiledname());
                        }
                        if (fieldDefine.isMultiChoice()) {
                            List<MultiValueDTO> multiValues = this.getMultiValue(applyitemValue.get("MASTERID"), applyitemValue.get("ID"), applyitemValue.get(filedmap.getSrcfiledname()), mapinfo.getSrctablename());
                            String newBindingid = UUID.randomUUID().toString();
                            regitemData = this.initMultiRow(null, masterid, newBindingid, mapinfo.getTablename(), multiValues);
                            if (regitemData != null) {
                                regitemListData.addAll(regitemData.get(mapinfo.getTablename() + "_M"));
                                regitemDataAll.put(mapinfo.getTablename() + "_M", regitemListData);
                                regBillMasterData.setValue(filedmap.getFiledname(), (Object)newBindingid);
                                regBillModal.getData().setTablesData(regitemDataAll);
                                continue;
                            }
                            regBillMasterData.setValue(filedmap.getFiledname(), null);
                            continue;
                        }
                        regBillMasterData.setValue(filedmap.getFiledname(), applyitemValue.get(filedmap.getSrcfiledname()));
                    }
                    if (StringUtils.hasText(regBillMasterData.getString("CODE"))) {
                        record.setChangefiledname(regBillMasterData.getString("CODE"));
                    } else {
                        record.setChangefiledname(regBillMasterData.getString("BILLCODE"));
                    }
                    this.billChangeRecordService.add(record);
                }
                if (!mapinfo.getSrctablename().equals(tablename) || mapinfo.isMaster()) continue;
                ArrayList regitemValues = new ArrayList();
                HashMap<String, Object> regitemValue = new HashMap<String, Object>();
                regitemValue.put("ID", UUID.randomUUID());
                BigDecimal ver = BigDecimal.valueOf(OrderNumUtil.getOrderNumByCurrentTimeMillis().doubleValue());
                regitemValue.put("VER", ver);
                regitemValue.put("MASTERID", regBillMasterData.getId());
                regitemValue.put("BILLCODE", regBillMasterData.getValue("BILLCODE"));
                regitemValue.put("ORDINAL", applyitemValue.get("ORDINAL"));
                ArrayList regitemListDataItem = new ArrayList();
                HashMap regitemDataAllItem = new HashMap();
                for (MapInfoDTO.Filedmap filedmap : mapinfo.getFiledmaps()) {
                    if (filedmap.getSrcfiledname() == null || "".equals(filedmap.getSrcfiledname())) continue;
                    try {
                        fieldDefine = ((DataFieldImpl)regBillModal.getTable(mapinfo.getTablename()).getFields().find(filedmap.getFiledname())).getDefine();
                    }
                    catch (Exception e) {
                        throw new RuntimeException(BillBdI18nUtil.getMessage("va.billbd.check.mapping.field.exist") + regBillModal.getTable(mapinfo.getTablename()).getName() + "\uff1a" + filedmap.getFiledname());
                    }
                    if (fieldDefine.isMultiChoice()) {
                        List<MultiValueDTO> multiValues = this.getMultiValue(applyitemValue.get("MASTERID"), applyitemValue.get("ID"), applyitemValue.get(filedmap.getSrcfiledname()), mapinfo.getSrctablename());
                        UUID newBindingid = UUID.randomUUID();
                        regitemData = this.initMultiRow(null, masterid, newBindingid.toString(), mapinfo.getTablename(), multiValues);
                        if (regitemData != null) {
                            regitemListDataItem.addAll(regitemData.get(mapinfo.getTablename() + "_M"));
                            regitemDataAllItem.put(mapinfo.getTablename() + "_M", regitemListDataItem);
                            regitemValue.put(filedmap.getFiledname(), newBindingid);
                            continue;
                        }
                        regitemValue.put(filedmap.getFiledname(), null);
                        continue;
                    }
                    regitemValue.put(filedmap.getFiledname(), applyitemValue.get(filedmap.getSrcfiledname()));
                }
                regitemValues.add(regitemValue);
                regitemDataAllItem.put(mapinfo.getTablename(), regitemValues);
                regBillModal.getData().setTablesData(regitemDataAllItem);
            }
        }
        catch (Exception e) {
            throw new RuntimeException("\u76ee\u6807\u5355\u636e\u8d4b\u503c\u5931\u8d25" + e.getMessage());
        }
        try {
            regBillModal.init(true, mapinfo.getSrctablename(), createBillEntry.getDefine().getWritebackname(), applyitemValue.get("ID").toString());
            regBillModal.setMsgId(messageId);
            ActionRequest actionRequest = new ActionRequest();
            actionRequest.setParams(new HashMap());
            regBillModal.executeAction((Action)this.saveAction, actionRequest, new ActionResponse());
        }
        catch (Exception e) {
            throw new RuntimeException("\u5b50\u8868\u751f\u5355\uff0c\u767b\u8bb0\u5355\u4fdd\u5b58\u5931\u8d25" + e.getMessage());
        }
    }

    @Override
    public void changeBillByMatser(MapInfoDTO mapinfo, BillModelImpl regBillModal, Map<String, List<Map<String, Object>>> tableData) {
        HashMap multiregitemDataAll = new HashMap();
        ArrayList<BillChangeRecordDO> records = new ArrayList<BillChangeRecordDO>();
        try {
            DataRow regBillMasterData = regBillModal.getMaster();
            for (Map.Entry<String, List<Map<String, Object>>> entry : tableData.entrySet()) {
                DataFieldDefineImpl fieldDefine;
                if (mapinfo.getSrctablename().equals(entry.getKey()) && mapinfo.isMaster()) {
                    Map<String, Object> masterData = entry.getValue().get(0);
                    List listData = (List)regBillModal.getData().getTablesData(false).get(mapinfo.getTablename() + "_M");
                    for (MapInfoDTO.Filedmap filedmap : mapinfo.getFiledmaps()) {
                        if (filedmap.getSrcfiledname() == null || "".equals(filedmap.getSrcfiledname()) || BillAlterModelUpdateConst.getUnAssignFields().contains(filedmap.getFiledname()) || !masterData.containsKey(filedmap.getSrcfiledname())) continue;
                        BillChangeRecordDO record = new BillChangeRecordDO();
                        record.setBillcode(regBillMasterData.getString("BILLCODE"));
                        record.setBilldefine(regBillModal.getDefine().getName());
                        record.setBilltable(mapinfo.getTablename());
                        record.setChangefiledname(filedmap.getFiledname());
                        record.setChangefiledtitle(filedmap.getFiledtitle());
                        record.setSrcbillcode((String)masterData.get("BILLCODE"));
                        record.setSrcbilldefine((String)masterData.get("DEFINECODE"));
                        try {
                            fieldDefine = ((DataFieldImpl)regBillModal.getMasterTable().getFields().find(filedmap.getFiledname())).getDefine();
                        }
                        catch (Exception e) {
                            throw new RuntimeException(BillBdI18nUtil.getMessage("va.billbd.check.mapping.field.exist") + regBillModal.getMasterTable().getName() + ":" + filedmap.getFiledname());
                        }
                        if (fieldDefine.isMultiChoice()) {
                            List<MultiValueDTO> multiValuesAfter;
                            List<MultiValueDTO> multiValuesBefore = this.getMultiValue(regBillMasterData.getString("ID"), null, regBillMasterData.getString(filedmap.getFiledname()), mapinfo.getTablename());
                            if (!CollectionUtils.isEmpty(multiValuesBefore)) {
                                listData = listData.stream().filter(o -> !((MultiValueDTO)((Object)((Object)multiValuesBefore.get(0)))).getBindingid().equals(o.get("BINDINGID").toString())).collect(Collectors.toList());
                                record.setChangebefore(multiValuesBefore.stream().map(MultiValueDTO::getBindingvalue).collect(Collectors.joining(",")));
                            }
                            if (!CollectionUtils.isEmpty(multiValuesAfter = this.getMultiValue(masterData.getOrDefault("ID", ""), null, masterData.get(filedmap.getSrcfiledname()), mapinfo.getSrctablename()))) {
                                record.setChangeafter(multiValuesAfter.stream().map(MultiValueDTO::getBindingvalue).collect(Collectors.joining(",")));
                            }
                            if (MaintainBillServiceImpl.isListEqual(multiValuesBefore, multiValuesAfter)) {
                                if (multiValuesBefore == null || multiValuesBefore.size() <= 0) continue;
                                String newBindingidBefore = UUID.randomUUID().toString();
                                HashMap<String, List<Map<String, Object>>> multiregitemDataBefore = this.initMultiRow(null, regBillMasterData.getId().toString(), newBindingidBefore, mapinfo.getTablename(), multiValuesBefore);
                                listData.addAll((Collection)multiregitemDataBefore.get(mapinfo.getTablename() + "_M"));
                                regBillMasterData.setValue(filedmap.getFiledname(), (Object)newBindingidBefore);
                                continue;
                            }
                            String newBindingid = UUID.randomUUID().toString();
                            HashMap<String, List<Map<String, Object>>> multiregitemData = this.initMultiRow(null, regBillMasterData.getId().toString(), newBindingid, mapinfo.getTablename(), multiValuesAfter);
                            if (multiregitemData != null) {
                                listData.addAll((Collection)multiregitemData.get(mapinfo.getTablename() + "_M"));
                                regBillMasterData.setValue(filedmap.getFiledname(), (Object)newBindingid);
                            } else {
                                regBillMasterData.setValue(filedmap.getFiledname(), null);
                            }
                        } else {
                            record.setChangebefore(regBillMasterData.getString(filedmap.getFiledname()));
                            regBillMasterData.setValue(filedmap.getFiledname(), masterData.get(filedmap.getSrcfiledname()));
                            record.setChangeafter(regBillMasterData.getString(filedmap.getFiledname()));
                        }
                        record.setChangeuser(ShiroUtil.getUser().getName());
                        record.setChangetype(1);
                        records.add(record);
                    }
                    multiregitemDataAll.put(mapinfo.getTablename() + "_M", listData);
                    regBillModal.getData().setTablesData(multiregitemDataAll);
                }
                if (!mapinfo.getSrctablename().equals(entry.getKey()) || mapinfo.isMaster()) continue;
                ArrayList regitemListData = new ArrayList();
                HashMap regitemDataAll = new HashMap();
                ArrayList regitemValues = new ArrayList();
                List<Map<String, Object>> alteritemValues = entry.getValue();
                String regBillcode = (String)regBillMasterData.getValue("BILLCODE");
                for (Map<String, Object> alteritemValue : alteritemValues) {
                    HashMap<String, Object> regitemValue = new HashMap<String, Object>();
                    regitemValue.put("ID", UUID.randomUUID());
                    BigDecimal ver = BigDecimal.valueOf(OrderNumUtil.getOrderNumByCurrentTimeMillis().doubleValue());
                    regitemValue.put("VER", ver);
                    regitemValue.put("MASTERID", regBillMasterData.getId());
                    regitemValue.put("BILLCODE", regBillMasterData.getValue("BILLCODE"));
                    for (MapInfoDTO.Filedmap filedmap : mapinfo.getFiledmaps()) {
                        if (filedmap.getSrcfiledname() == null || "".equals(filedmap.getSrcfiledname())) continue;
                        try {
                            fieldDefine = ((DataFieldImpl)regBillModal.getTable(mapinfo.getTablename()).getFields().find(filedmap.getFiledname())).getDefine();
                        }
                        catch (Exception e) {
                            throw new RuntimeException(BillBdI18nUtil.getMessage("va.billbd.check.mapping.field.exist") + mapinfo.getTablename() + ":" + filedmap.getFiledname());
                        }
                        if (fieldDefine.isMultiChoice()) {
                            List<MultiValueDTO> multiValues = this.getMultiValue(alteritemValue.get("MASTERID"), alteritemValue.get("ID"), alteritemValue.get(filedmap.getSrcfiledname()), mapinfo.getSrctablename());
                            UUID newBindingid = UUID.randomUUID();
                            HashMap<String, List<Map<String, Object>>> regitemData = this.initMultiRow(null, regBillMasterData.getId().toString(), newBindingid.toString(), mapinfo.getTablename(), multiValues);
                            if (regitemData != null) {
                                regitemListData.addAll(regitemData.get(mapinfo.getTablename() + "_M"));
                                regitemDataAll.put(mapinfo.getTablename() + "_M", regitemListData);
                                regitemValue.put(filedmap.getFiledname(), newBindingid);
                                continue;
                            }
                            regitemValue.put(filedmap.getFiledname(), null);
                            continue;
                        }
                        regitemValue.put(filedmap.getFiledname(), alteritemValue.get(filedmap.getSrcfiledname()));
                    }
                    regitemValues.add(regitemValue);
                }
                regitemDataAll.put(mapinfo.getTablename(), regitemValues);
                List<Object> regCollect = regBillModal.getTable(mapinfo.getTablename()).getRows().stream().filter(f -> f.getValue("BILLCODE").equals(regBillcode)).map(DataRow::getId).collect(Collectors.toList());
                regCollect.forEach(f -> regBillModal.getTable(mapinfo.getTablename()).deleteRowById(f));
                regBillModal.getData().setTablesData(regitemDataAll);
            }
        }
        catch (Exception e) {
            throw new RuntimeException("\u4e3b\u8868\u53d8\u66f4\u5931\u8d25," + e.getMessage());
        }
        R r = this.billChangeRecordService.batchAdd(records);
        if (r.getCode() != 0) {
            throw new RuntimeException("\u4e3b\u8868\u53d8\u66f4\u8bb0\u5f55\u63d2\u5165\u5931\u8d25," + r.getMsg());
        }
    }

    @Override
    public void changeBillByDetail(RegistrationBillModel regBillModal, String messageId, CreateBillEntry createBillEntry, Map<String, Object> alterItemValue) {
        String tablename = createBillEntry.getTablename();
        ApplyRegMapDO define = createBillEntry.getDefine();
        ArrayList<BillChangeRecordDO> records = new ArrayList<BillChangeRecordDO>();
        try {
            regBillModal.loadByCode((String)alterItemValue.get(define.getWritebackname()));
        }
        catch (Exception e) {
            logger.error("\u52a0\u8f7d\u76ee\u6807\u5355\u636e\u6570\u636e\u5931\u8d25\uff0c\u8bf7\u68c0\u67e5REGBILLCODE\u5b57\u6bb5\u503c", e);
            throw new RuntimeException("\u52a0\u8f7d\u76ee\u6807\u5355\u636e\u6570\u636e\u5931\u8d25\uff0c\u8bf7\u68c0\u67e5REGBILLCODE\u5b57\u6bb5\u503c");
        }
        regBillModal.getMaster().setValue("BILLSTATE", (Object)BillState.AUDITPASSEDCANEDIT);
        regBillModal.edit();
        HashMap multiregitemDataAll = new HashMap();
        ArrayList regitemListDataDetail = new ArrayList();
        HashMap regitemDataAllDetail = new HashMap();
        List mapinfos = JSONUtil.parseMapArray((String)define.getMapinfos());
        try {
            for (Map stringObjectMap : mapinfos) {
                HashMap<String, List<Map<String, Object>>> regitemData;
                DataFieldDefineImpl fieldDefine;
                MapInfoDTO mapinfo = (MapInfoDTO)JSONUtil.parseObject((String)JSONUtil.toJSONString((Object)stringObjectMap), MapInfoDTO.class);
                String mastergroupid = regBillModal.getMaster().getString("ID");
                if (mapinfo.getSrctablename().equals(tablename) && mapinfo.isMaster()) {
                    List regitemListData = (List)regBillModal.getData().getTablesData(false).get(mapinfo.getTablename() + "_M");
                    for (MapInfoDTO.Filedmap filedmap : mapinfo.getFiledmaps()) {
                        if (filedmap.getSrcfiledname() == null || "".equals(filedmap.getSrcfiledname()) || BillAlterModelUpdateConst.getUnAssignFields().contains(filedmap.getFiledname())) continue;
                        BillChangeRecordDO record = new BillChangeRecordDO();
                        record.setBilltable(mapinfo.getTablename());
                        record.setBillcode((String)alterItemValue.get("REGBILLCODE"));
                        record.setBilldefine(regBillModal.getDefine().getName());
                        record.setSrcbillcode((String)alterItemValue.get("BILLCODE"));
                        record.setSrcbilldefine(createBillEntry.getAlterdefinecode());
                        record.setChangefiledname(filedmap.getFiledname());
                        record.setChangefiledtitle(filedmap.getFiledtitle());
                        try {
                            fieldDefine = ((DataFieldImpl)regBillModal.getMasterTable().getFields().find(filedmap.getFiledname())).getDefine();
                        }
                        catch (Exception e) {
                            logger.error("\u68c0\u67e5\u6620\u5c04\u914d\u7f6e\u4e2d\u662f\u5426\u5b58\u5728\u76ee\u6807\u5355\u4e0a\u5df2\u5220\u9664\u7684\u5b57\u6bb5," + regBillModal.getMasterTable().getName() + ":" + filedmap.getFiledname(), e);
                            throw new RuntimeException(BillBdI18nUtil.getMessage("va.billbd.check.mapping.field.exist") + regBillModal.getMasterTable().getName() + ":" + filedmap.getFiledname());
                        }
                        if (fieldDefine.isMultiChoice()) {
                            List<MultiValueDTO> multiValuesAfter;
                            List<MultiValueDTO> multiValuesBefore = this.getMultiValue(regBillModal.getMaster().getString("ID"), null, regBillModal.getMaster().getString(filedmap.getFiledname()), mapinfo.getTablename());
                            if (!CollectionUtils.isEmpty(multiValuesBefore)) {
                                regitemListData = regitemListData.stream().filter(o -> !((MultiValueDTO)((Object)((Object)multiValuesBefore.get(0)))).getBindingid().equals(o.get("BINDINGID").toString())).collect(Collectors.toList());
                                record.setChangebefore(multiValuesBefore.stream().map(MultiValueDTO::getBindingvalue).collect(Collectors.joining(",")));
                            }
                            if (!CollectionUtils.isEmpty(multiValuesAfter = this.getMultiValue(alterItemValue.get("MASTERID"), alterItemValue.get("ID"), alterItemValue.get(filedmap.getSrcfiledname()), mapinfo.getSrctablename()))) {
                                record.setChangeafter(multiValuesAfter.stream().map(MultiValueDTO::getBindingvalue).collect(Collectors.joining(",")));
                            }
                            if (MaintainBillServiceImpl.isListEqual(multiValuesBefore, multiValuesAfter)) {
                                if (multiValuesBefore == null || multiValuesBefore.size() <= 0) continue;
                                String newBindingidBefore = UUID.randomUUID().toString();
                                HashMap<String, List<Map<String, Object>>> multiregitemDataBefore = this.initMultiRow(mastergroupid, regBillModal.getMaster().getId().toString(), newBindingidBefore, mapinfo.getTablename(), multiValuesBefore);
                                regitemListData.addAll((Collection)multiregitemDataBefore.get(mapinfo.getTablename() + "_M"));
                                regBillModal.getMaster().setValue(filedmap.getFiledname(), (Object)newBindingidBefore);
                                continue;
                            }
                            String newBindingid = UUID.randomUUID().toString();
                            regitemData = this.initMultiRow(mastergroupid, regBillModal.getMaster().getString("ID"), newBindingid, mapinfo.getTablename(), multiValuesAfter);
                            if (regitemData != null) {
                                regitemListData.addAll((Collection)regitemData.get(mapinfo.getTablename() + "_M"));
                                regBillModal.getMaster().setValue(filedmap.getFiledname(), (Object)newBindingid);
                            } else {
                                regBillModal.getMaster().setValue(filedmap.getFiledname(), null);
                            }
                        } else {
                            record.setChangebefore(regBillModal.getMaster().getString(filedmap.getFiledname()));
                            regBillModal.getMaster().setValue(filedmap.getFiledname(), alterItemValue.get(filedmap.getSrcfiledname()));
                            record.setChangeafter(regBillModal.getMaster().getString(filedmap.getFiledname()));
                        }
                        record.setChangeuser(ShiroUtil.getUser().getName());
                        record.setChangetype(2);
                        records.add(record);
                    }
                    multiregitemDataAll.put(mapinfo.getTablename() + "_M", regitemListData);
                    regBillModal.getData().setTablesData(multiregitemDataAll);
                }
                if (!mapinfo.getSrctablename().equals(tablename) || mapinfo.isMaster()) continue;
                ArrayList regitemValues = new ArrayList();
                HashMap<String, Object> regitemValue = new HashMap<String, Object>();
                UUID groupid = UUID.randomUUID();
                regitemValue.put("ID", groupid);
                BigDecimal ver = BigDecimal.valueOf(OrderNumUtil.getOrderNumByCurrentTimeMillis().doubleValue());
                regitemValue.put("VER", ver);
                regitemValue.put("MASTERID", regBillModal.getMaster().getId());
                regitemValue.put("BILLCODE", regBillModal.getMaster().getValue("BILLCODE"));
                regitemValue.put("ORDINAL", alterItemValue.get("ORDINAL"));
                for (MapInfoDTO.Filedmap filedmap : mapinfo.getFiledmaps()) {
                    if (filedmap.getSrcfiledname() == null || "".equals(filedmap.getSrcfiledname())) continue;
                    try {
                        fieldDefine = ((DataFieldImpl)regBillModal.getTable(mapinfo.getTablename()).getFields().find(filedmap.getFiledname())).getDefine();
                    }
                    catch (Exception e) {
                        logger.error("\u68c0\u67e5\u6620\u5c04\u914d\u7f6e\u4e2d\u662f\u5426\u5b58\u5728\u76ee\u6807\u5355\u4e0a\u5df2\u5220\u9664\u7684\u5b57\u6bb5," + mapinfo.getTablename() + ":" + filedmap.getFiledname(), e);
                        throw new RuntimeException(BillBdI18nUtil.getMessage("va.billbd.check.mapping.field.exist") + mapinfo.getTablename() + ":" + filedmap.getFiledname());
                    }
                    if (fieldDefine.isMultiChoice()) {
                        List<MultiValueDTO> multiValues = this.getMultiValue(alterItemValue.get("MASTERID"), alterItemValue.get("ID"), alterItemValue.get(filedmap.getSrcfiledname()), mapinfo.getSrctablename());
                        UUID newBindingid = UUID.randomUUID();
                        regitemData = this.initMultiRow(null, regBillModal.getMaster().getId().toString(), newBindingid.toString(), mapinfo.getTablename(), multiValues);
                        if (regitemData != null) {
                            regitemListDataDetail.addAll(regitemData.get(mapinfo.getTablename() + "_M"));
                            regitemDataAllDetail.put(mapinfo.getTablename() + "_M", regitemListDataDetail);
                            regitemValue.put(filedmap.getFiledname(), newBindingid);
                            continue;
                        }
                        regitemValue.put(filedmap.getFiledname(), null);
                        continue;
                    }
                    regitemValue.put(filedmap.getFiledname(), alterItemValue.get(filedmap.getSrcfiledname()));
                }
                regitemValues.add(regitemValue);
                regitemDataAllDetail.put(mapinfo.getTablename(), regitemValues);
                regBillModal.getData().setTablesData(regitemDataAllDetail);
            }
        }
        catch (Exception e) {
            logger.error("\u5b50\u8868\u53d8\u66f4\u5931\u8d25\uff0c\u76ee\u6807\u5355\u636e\u8d4b\u503c\u5931\u8d25," + e.getMessage(), e);
            throw new RuntimeException("\u5b50\u8868\u53d8\u66f4\u5931\u8d25\uff0c\u76ee\u6807\u5355\u636e\u8d4b\u503c\u5931\u8d25," + e.getMessage());
        }
        R addRecord = this.billChangeRecordService.batchAdd(records);
        if (addRecord.getCode() == 0) {
            try {
                regBillModal.setMsgId(messageId);
                ActionRequest actionRequest = new ActionRequest();
                actionRequest.setParams(new HashMap());
                regBillModal.executeAction((Action)this.saveAction, actionRequest, new ActionResponse());
            }
            catch (Exception e) {
                logger.error("\u5b50\u8868\u53d8\u66f4\u5931\u8d25\uff0c\u76ee\u6807\u5355\u636e\u8d4b\u503c\u5931\u8d25," + e.getMessage(), e);
                throw new RuntimeException("\u5b50\u8868\u53d8\u66f4\u5931\u8d25\uff0c\u76ee\u6807\u5355\u636e\u8d4b\u503c\u5931\u8d25," + e.getMessage());
            }
        } else {
            throw new RuntimeException("\u5b50\u8868\u53d8\u66f4\u5931\u8d25\uff0csaveAction," + addRecord.getMsg());
        }
    }

    private List<MultiValueDTO> getMultiValue(Object masterid, Object groupid, Object bindingid, String srcTablename) {
        MultiValueDTO mo = new MultiValueDTO();
        if (masterid != null) {
            mo.setMasterid(masterid.toString());
        }
        if (groupid != null) {
            mo.setGroupid(groupid.toString());
        }
        if (bindingid != null) {
            mo.setBindingid(bindingid.toString());
        }
        mo.setSrcTablename(srcTablename);
        return this.multiValueService.getMultiValue(mo);
    }

    public static <E> boolean isListEqual(List<E> list1, List<E> list2) {
        if (list1 == list2) {
            return true;
        }
        if (list1 == null && list2.size() == 0 || list2 == null && list1.size() == 0) {
            return true;
        }
        if (list1 == null || list2 == null) {
            return false;
        }
        if (list1.size() != list2.size()) {
            return false;
        }
        return list1.containsAll(list2);
    }

    private HashMap<String, List<Map<String, Object>>> initMultiRow(String groupid, String masterid, String newBindingid, String tableName, List<MultiValueDTO> multiValues) {
        HashMap<String, List<Map<String, Object>>> regitemData = new HashMap<String, List<Map<String, Object>>>();
        if (multiValues.size() == 0) {
            return null;
        }
        ArrayList regitemValues = new ArrayList();
        for (MultiValueDTO multiValueDTO : multiValues) {
            HashMap<String, Object> regitemValue = new HashMap<String, Object>();
            regitemValue.put("ID", UUID.randomUUID());
            regitemValue.put("GROUPID", groupid);
            regitemValue.put("MASTERID", masterid);
            regitemValue.put("BINDINGID", newBindingid);
            regitemValue.put("BINDINGVALUE", multiValueDTO.getBindingvalue());
            regitemValue.put("ORDERNUM", multiValueDTO.getOrdernum());
            regitemValues.add(regitemValue);
        }
        regitemData.put(tableName + "_M", regitemValues);
        return regitemData;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    @Async
    public void afterApprovalCreateBill(Model model, List<ApplyRegMapDO> applyRegMapDOs, UserLoginDTO userLoginDTO) {
        BasedataApplyBillModel basedataApplyBillModel = (BasedataApplyBillModel)model;
        ShiroUtil.unbindUser();
        try {
            DataFieldDefine bizsource;
            ShiroUtil.bindUser((UserLoginDTO)userLoginDTO);
            Map<String, Map<String, List<String>>> check = basedataApplyBillModel.check(applyRegMapDOs);
            basedataApplyBillModel.createBill(applyRegMapDOs, check);
            VaTransMessageDO vaTransMessageDO = basedataApplyBillModel.getTransMessage();
            if (vaTransMessageDO != null) {
                logger.info("\u5f00\u59cb\u5ba1\u6279\u901a\u8fc7\u540e\u751f\u5355:" + vaTransMessageDO.getBizcode());
                R r = basedataApplyBillModel.getVaTransMessageService().sendAndReceive(vaTransMessageDO, JSONUtil.parseMap((String)vaTransMessageDO.getInputparam()));
                if (r.getCode() == 1) {
                    logger.error("\u5ba1\u6279\u901a\u8fc7\u540e\u751f\u5355\u5f02\u5e38\uff1a" + r.getMsg());
                }
            }
            if ((bizsource = (DataFieldDefine)basedataApplyBillModel.getMasterTable().getDefine().getFields().find("BIZSOURCE")) != null) {
                HashMap<String, String> billMsg = new HashMap<String, String>();
                billMsg.put("BILLID", basedataApplyBillModel.getMaster().getString("ID"));
                billMsg.put("DEFINECODE", basedataApplyBillModel.getDefine().getName());
                billMsg.put("BILLCODE", basedataApplyBillModel.getMaster().getString("BILLCODE"));
                billMsg.put("BIZSOURCE", basedataApplyBillModel.getMaster().getString("BIZSOURCE"));
                VaSyncDataUtils.sendAsyncDataFeedback((SyncDataFeedbackEnum)SyncDataFeedbackEnum.WORKFLOWFINISH, (String)"\u5de5\u4f5c\u6d41\u5b8c\u6210", billMsg);
            }
        }
        finally {
            ShiroUtil.unbindUser();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    @Async
    public void afterApprovalChangeBill(Model model, List<ApplyRegMapDO> applyRegMapDOs, UserLoginDTO userLoginDTO) {
        BillAlterModel billAlterModel = (BillAlterModel)model;
        ShiroUtil.unbindUser();
        try {
            ShiroUtil.bindUser((UserLoginDTO)userLoginDTO);
            billAlterModel.changeBill(applyRegMapDOs);
            VaTransMessageDO vaTransMessageDO = billAlterModel.getTransMessage();
            if (vaTransMessageDO != null) {
                logger.info("\u5f00\u59cb\u5ba1\u6279\u901a\u8fc7\u540e\u53d8\u66f4\u5355\u636e:" + vaTransMessageDO.getBizcode());
                R r = billAlterModel.getVaTransMessageService().sendAndReceive(vaTransMessageDO, JSONUtil.parseMap((String)vaTransMessageDO.getInputparam()));
                if (r.getCode() == 1) {
                    logger.error("\u5ba1\u6279\u901a\u8fc7\u540e\u53d8\u66f4\u5355\u5f02\u5e38\uff1a" + r.getMsg());
                }
            }
        }
        finally {
            ShiroUtil.unbindUser();
        }
    }
}

