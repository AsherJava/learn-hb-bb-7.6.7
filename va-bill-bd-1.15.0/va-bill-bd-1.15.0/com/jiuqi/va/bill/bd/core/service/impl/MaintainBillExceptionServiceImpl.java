/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.bill.impl.BillContextImpl
 *  com.jiuqi.va.bill.impl.BillModelImpl
 *  com.jiuqi.va.bill.utils.VerifyUtils
 *  com.jiuqi.va.biz.intf.model.ModelContext
 *  com.jiuqi.va.biz.intf.model.ModelDefineService
 *  com.jiuqi.va.domain.bill.BillVerifyDTO
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.OrderNumUtil
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  org.springframework.amqp.core.AmqpTemplate
 */
package com.jiuqi.va.bill.bd.core.service.impl;

import com.jiuqi.va.bill.bd.bill.dao.MaintainBillExceptionDao;
import com.jiuqi.va.bill.bd.bill.domain.CreateBillEntry;
import com.jiuqi.va.bill.bd.bill.domain.CreateBillExceptionDTO;
import com.jiuqi.va.bill.bd.bill.domain.MapInfoDTO;
import com.jiuqi.va.bill.bd.bill.service.WriteBackService;
import com.jiuqi.va.bill.bd.bill.service.impl.WriteBackServiceImpl;
import com.jiuqi.va.bill.bd.core.domain.ApplyRegMapDO;
import com.jiuqi.va.bill.bd.core.domain.BillMasterDTO;
import com.jiuqi.va.bill.bd.core.service.ApplyRegMapService;
import com.jiuqi.va.bill.bd.core.service.MaintainBillExceptionService;
import com.jiuqi.va.bill.impl.BillContextImpl;
import com.jiuqi.va.bill.impl.BillModelImpl;
import com.jiuqi.va.bill.utils.VerifyUtils;
import com.jiuqi.va.biz.intf.model.ModelContext;
import com.jiuqi.va.biz.intf.model.ModelDefineService;
import com.jiuqi.va.domain.bill.BillVerifyDTO;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.OrderNumUtil;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.common.ShiroUtil;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MaintainBillExceptionServiceImpl
implements MaintainBillExceptionService {
    private static final Logger logger = LoggerFactory.getLogger(WriteBackServiceImpl.class);
    @Autowired
    public MaintainBillExceptionDao applyRegExceptionHanderDao;
    @Autowired
    public ApplyRegMapService applyRegMapService;
    @Autowired
    public AmqpTemplate rabbitTemplate;
    @Autowired
    public ModelDefineService modelDefineService;
    @Autowired
    public WriteBackService writeBackService;

    @Override
    public R republish(List<CreateBillExceptionDTO> dataList) {
        try {
            BillContextImpl context = new BillContextImpl();
            context.setDisableVerify(true);
            context.setTenantName(ShiroUtil.getTenantName());
            if (dataList != null && dataList.size() > 0) {
                ApplyRegMapDO mapdo = new ApplyRegMapDO();
                mapdo.setStartflag(1);
                List<ApplyRegMapDO> maps = this.applyRegMapService.getMap(mapdo);
                dataList.stream().forEach(exceInfo -> maps.stream().filter(o -> o.getName().equals(exceInfo.getConfigname())).forEach(map -> {
                    if (map.getCreatetype() == 2 || map.getCreatetype() == 4) {
                        this.createBillByMaster((ApplyRegMapDO)((Object)((Object)map)), (CreateBillExceptionDTO)((Object)exceInfo), context);
                    } else if (map.getCreatetype() == 3 || map.getCreatetype() == 5) {
                        List mapinfos = JSONUtil.parseMapArray((String)map.getMapinfos());
                        MapInfoDTO mapinfo = new MapInfoDTO();
                        for (int i = 0; i < mapinfos.size(); ++i) {
                            mapinfo = (MapInfoDTO)JSONUtil.parseObject((String)JSONUtil.toJSONString(mapinfos.get(i)), MapInfoDTO.class);
                            this.createBillBydetail((ApplyRegMapDO)((Object)((Object)map)), mapinfo, (CreateBillExceptionDTO)((Object)exceInfo), context);
                        }
                    }
                }));
            }
        }
        catch (Exception e) {
            logger.error("\u91cd\u65b0\u751f\u5355\u5931\u8d25", e);
            throw e;
        }
        return R.ok((String)"\u53d1\u5e03\u751f\u5355\u6d88\u606f\u6210\u529f\uff0c\u8bf7\u7a0d\u540e\u5237\u65b0");
    }

    @Override
    public List<CreateBillExceptionDTO> queryData(CreateBillExceptionDTO requrstDTO) {
        List<CreateBillExceptionDTO> list = this.applyRegExceptionHanderDao.queryData(requrstDTO);
        this.genVerifyCode(list);
        return list;
    }

    @Override
    public List<CreateBillExceptionDTO> queryLatestData(CreateBillExceptionDTO exceptionInfo) {
        return this.applyRegExceptionHanderDao.queryLatestData(exceptionInfo);
    }

    @Override
    public R queryAndInsert(CreateBillExceptionDTO exceptionInfo, String exceInfo) {
        List<Object> dataList = new ArrayList();
        R r = R.ok();
        try {
            if (exceptionInfo.getId() != null) {
                CreateBillExceptionDTO temp = new CreateBillExceptionDTO();
                temp.setId(exceptionInfo.getId());
                dataList = this.queryData(temp);
            }
            if (exceInfo != null) {
                exceptionInfo.setMemo(exceInfo);
            }
            if (null != dataList && dataList.size() > 0) {
                r = this.updateData(exceptionInfo);
                r.put("exceptionid", (Object)exceptionInfo.getId());
            } else {
                r = this.insertData(exceptionInfo);
            }
        }
        catch (Exception e) {
            return R.error((String)e.getMessage());
        }
        return r;
    }

    @Override
    public R insertData(CreateBillExceptionDTO requrstDTO) {
        R r = new R();
        r.put("exceptionid", (Object)UUID.randomUUID().toString());
        requrstDTO.setId(r.get((Object)"exceptionid").toString());
        requrstDTO.setVer(OrderNumUtil.getOrderNumByCurrentTimeMillis());
        requrstDTO.setOrdinal(OrderNumUtil.getOrderNumByCurrentTimeMillis());
        requrstDTO.setCreatetime(new Date());
        if (this.applyRegExceptionHanderDao.insertData(requrstDTO) > 0) {
            return r;
        }
        r.put("exceptionid", (Object)"");
        return r;
    }

    @Override
    public R updateData(CreateBillExceptionDTO requrstDTO) {
        requrstDTO.setVer(OrderNumUtil.getOrderNumByCurrentTimeMillis());
        return this.applyRegExceptionHanderDao.updateData(requrstDTO) > 0 ? R.ok() : R.error();
    }

    @Override
    public List<CreateBillExceptionDTO> queryExceptionData(CreateBillExceptionDTO requrstDTO) {
        requrstDTO.setCreatebillstate(1);
        List<CreateBillExceptionDTO> list = this.applyRegExceptionHanderDao.queryExceptionData(requrstDTO);
        this.genVerifyCode(list);
        return list;
    }

    private void genVerifyCode(List<CreateBillExceptionDTO> changeList) {
        ArrayList billCodes = new ArrayList();
        changeList.stream().forEach(o -> billCodes.add(o.getSrcbillcode()));
        if (billCodes == null || billCodes.size() == 0) {
            return;
        }
        BillVerifyDTO dto = new BillVerifyDTO();
        dto.setBillCodes(billCodes);
        dto.setAuth(1);
        List verifyCodes = VerifyUtils.genVerifyCode((BillVerifyDTO)dto);
        int[] i = new int[1];
        changeList.forEach(o -> {
            o.setSrcverifycode((String)verifyCodes.get(i[0]));
            i[0] = i[0] + 1;
        });
    }

    private void createBillByMaster(ApplyRegMapDO define, CreateBillExceptionDTO exceInfo, BillContextImpl context) {
        if (exceInfo == null) {
            return;
        }
        BillModelImpl srcBillModal = null;
        try {
            srcBillModal = (BillModelImpl)this.modelDefineService.createModel((ModelContext)context, define.getSrcbilldefinecode());
            srcBillModal.getRuler().getRulerExecutor().setEnable(true);
            srcBillModal.loadByCode(exceInfo.getSrcbillcode());
        }
        catch (Exception e) {
            this.queryAndInsert(exceInfo, "\u52a0\u8f7d\u6e90\u5355\u5355\u636e\u5931\u8d25\uff0c" + e.getMessage());
            return;
        }
        CreateBillEntry cnEntry = new CreateBillEntry();
        cnEntry.setTenantName(ShiroUtil.getTenantName());
        cnEntry.setUser(ShiroUtil.getUser());
        cnEntry.setBillcode(srcBillModal.getMaster().getString("BILLCODE"));
        cnEntry.setDefine(define);
        cnEntry.setMasterid(srcBillModal.getMaster().getId().toString());
        if (exceInfo.getCreatebillstate() == 2 && (exceInfo.getCreatetype() == 2 || exceInfo.getCreatetype() == 4)) {
            return;
        }
        if (exceInfo.getBillcode() != null && exceInfo.getCreatebillstate() == 3 && exceInfo.getCreatetype() == 2) {
            try {
                this.writeBackService.writeBackToApplyBill(srcBillModal.getMasterTable().getName(), define.getWritebackname(), exceInfo.getBillcode(), exceInfo.getSrcmasterid());
            }
            catch (Exception e) {
                exceInfo.setCreatebillstate(3);
                this.queryAndInsert(exceInfo, "\u5904\u7406\u56de\u5199\u5931\u8d25" + e.getMessage());
                return;
            }
            exceInfo.setCreatebillstate(2);
            this.queryAndInsert(exceInfo, "\u5904\u7406\u56de\u5199\u6210\u529f");
            return;
        }
        if (exceInfo.getCreatebillstate() == 4) {
            R r;
            if (exceInfo.getCreatetype() == 4) {
                exceInfo.setCreatebillstate(0);
                r = this.queryAndInsert(exceInfo, "\u91cd\u65b0\u53d1\u5e03\u4e3b\u8868\u53d8\u66f4\u6d88\u606f\u6210\u529f");
                if (r.getCode() == 0) {
                    cnEntry.setExceptionid(r.get((Object)"exceptionid").toString());
                    this.rabbitTemplate.convertAndSend("va.bill.bd.changebill", (Object)JSONUtil.toJSONString((Object)cnEntry));
                } else {
                    this.queryAndInsert(exceInfo, "\u91cd\u65b0\u53d1\u5e03\u4e3b\u8868\u53d8\u66f4\u6d88\u606f\u5931\u8d25");
                }
            }
            if (exceInfo.getCreatetype() == 2) {
                exceInfo.setCreatebillstate(0);
                r = new R();
                r = this.queryAndInsert(exceInfo, "\u91cd\u65b0\u53d1\u5e03\u4e3b\u8868\u751f\u5355\u6d88\u606f\u6210\u529f");
                if (r.getCode() == 0) {
                    cnEntry.setExceptionid(r.get((Object)"exceptionid").toString());
                    this.rabbitTemplate.convertAndSend("va.bill.bd.createbill", (Object)JSONUtil.toJSONString((Object)cnEntry));
                } else {
                    this.queryAndInsert(exceInfo, "\u91cd\u65b0\u53d1\u5e03\u4e3b\u8868\u751f\u5355\u6d88\u606f\u5931\u8d25");
                }
            }
        }
        if (exceInfo.getCreatebillstate() == 5) {
            BillMasterDTO billMasterDTO = new BillMasterDTO();
            billMasterDTO.setBillcode(exceInfo.getBillcode());
            billMasterDTO.setDefinecode(exceInfo.getDefinecode());
            billMasterDTO.setTenantName(ShiroUtil.getTenantName());
            this.rabbitTemplate.convertAndSend("va.bill.bd.createbd", (Object)JSONUtil.toJSONString((Object)((Object)billMasterDTO)));
        }
    }

    private void createBillBydetail(ApplyRegMapDO define, MapInfoDTO mapinfo, CreateBillExceptionDTO exceInfo, BillContextImpl context) {
        BillModelImpl srcBillModal = null;
        if (exceInfo == null) {
            return;
        }
        try {
            srcBillModal = (BillModelImpl)this.modelDefineService.createModel((ModelContext)context, define.getSrcbilldefinecode());
            srcBillModal.getRuler().getRulerExecutor().setEnable(true);
            srcBillModal.loadByCode(exceInfo.getSrcbillcode());
        }
        catch (Exception e) {
            this.queryAndInsert(exceInfo, "\u52a0\u8f7d\u6e90\u5355\u5355\u636e\u5b9a\u4e49\u5931\u8d25\uff1a" + e.getMessage());
            return;
        }
        try {
            String unitcode = srcBillModal.getMaster().getString("UNITCODE");
            String masterTablename = srcBillModal.getMasterTable().getName();
            Map srcBillDatas = srcBillModal.getData().getTablesData();
            for (Map.Entry entry : srcBillDatas.entrySet()) {
                if (!mapinfo.getSrctablename().equals(entry.getKey()) || masterTablename.equals(entry.getKey())) continue;
                List applyitemValues = (List)entry.getValue();
                for (Map applyitemValue : applyitemValues) {
                    R r;
                    if (!applyitemValue.get("ID").toString().equals(exceInfo.getSrcdetailbillid())) continue;
                    CreateBillEntry cnEntry = new CreateBillEntry();
                    cnEntry.setTenantName(ShiroUtil.getTenantName());
                    cnEntry.setUser(ShiroUtil.getUser());
                    cnEntry.setDefine(define);
                    cnEntry.setUnitcode(unitcode);
                    cnEntry.setTablename((String)entry.getKey());
                    cnEntry.setApplyitemValue(applyitemValue);
                    cnEntry.setAlterdefinecode(srcBillModal.getDefine().getName());
                    cnEntry.setBillcode(srcBillModal.getMaster().getString("BILLCODE"));
                    cnEntry.setMasterid(srcBillModal.getMaster().getString("ID"));
                    if (exceInfo.getCreatebillstate() == 2 && (exceInfo.getCreatetype() == 3 || exceInfo.getCreatetype() == 5)) continue;
                    if (exceInfo.getCreatebillstate() == 3 && (exceInfo.getCreatetype() == 3 || exceInfo.getCreatetype() == 5)) {
                        try {
                            r = this.writeBackService.writeBackToApplyBill((String)entry.getKey(), define.getWritebackname(), exceInfo.getBillcode(), exceInfo.getSrcmasterid());
                            if (r.getCode() == 0) {
                                exceInfo.setCreatebillstate(2);
                                this.queryAndInsert(exceInfo, "\u5904\u7406\u5b50\u8868\u56de\u5199\u6210\u529f");
                                continue;
                            }
                            exceInfo.setCreatebillstate(3);
                            this.queryAndInsert(exceInfo, "\u5904\u7406\u5b50\u8868\u56de\u5199\u5931\u8d25");
                        }
                        catch (Exception e) {
                            exceInfo.setCreatebillstate(3);
                            this.queryAndInsert(exceInfo, "\u5904\u7406\u5b50\u8868\u56de\u5199\u5931\u8d25" + e.getMessage());
                        }
                        continue;
                    }
                    if (exceInfo.getCreatebillstate() == 4 || exceInfo.getCreatebillstate() == 1) {
                        if (exceInfo.getCreatetype() == 5) {
                            exceInfo.setCreatebillstate(0);
                            r = this.queryAndInsert(exceInfo, "\u91cd\u65b0\u53d1\u5e03\u5b50\u8868\u53d8\u66f4\u6d88\u606f\u6210\u529f");
                            if (r.getCode() == 0) {
                                cnEntry.setExceptionid(r.get((Object)"exceptionid").toString());
                                this.rabbitTemplate.convertAndSend("va.bill.bd.changebill", (Object)JSONUtil.toJSONString((Object)cnEntry));
                            } else {
                                throw new RuntimeException(r.getMsg());
                            }
                        }
                        if (exceInfo.getCreatetype() == 3) {
                            exceInfo.setCreatebillstate(0);
                            r = this.queryAndInsert(exceInfo, "\u91cd\u65b0\u53d1\u5e03\u5b50\u8868\u751f\u5355\u6d88\u606f\u6210\u529f");
                            if (r.getCode() == 0) {
                                cnEntry.setExceptionid(r.get((Object)"exceptionid").toString());
                                this.rabbitTemplate.convertAndSend("va.bill.bd.createbill", (Object)JSONUtil.toJSONString((Object)cnEntry));
                            } else {
                                throw new RuntimeException(r.getMsg());
                            }
                        }
                    }
                    if (exceInfo.getCreatebillstate() != 5) continue;
                    BillMasterDTO billMasterDTO = new BillMasterDTO();
                    billMasterDTO.setBillcode(exceInfo.getBillcode());
                    billMasterDTO.setDefinecode(exceInfo.getDefinecode());
                    billMasterDTO.setTenantName(ShiroUtil.getTenantName());
                    this.rabbitTemplate.convertAndSend("va.bill.bd.createbd", (Object)JSONUtil.toJSONString((Object)((Object)billMasterDTO)));
                }
            }
        }
        catch (Exception e) {
            this.queryAndInsert(exceInfo, "\u91cd\u65b0\u53d1\u5e03\u5b50\u8868\u751f\u5355\u6d88\u606f\u5931\u8d25" + e);
        }
    }
}

