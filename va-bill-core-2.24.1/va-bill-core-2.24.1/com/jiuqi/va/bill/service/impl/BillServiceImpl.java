/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.DeserializationFeature
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.va.biz.intf.model.ModelDefine
 *  com.jiuqi.va.biz.intf.model.ModelManager
 *  com.jiuqi.va.biz.intf.model.ModelType
 *  com.jiuqi.va.biz.utils.Utils
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.common.ShiroUtil
 */
package com.jiuqi.va.bill.service.impl;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.va.bill.dao.BillDao;
import com.jiuqi.va.bill.domain.BillDTO;
import com.jiuqi.va.bill.impl.BillContextImpl;
import com.jiuqi.va.bill.intf.BillDataService;
import com.jiuqi.va.bill.intf.BillDefine;
import com.jiuqi.va.bill.intf.BillEditService;
import com.jiuqi.va.bill.intf.BillIncEditService;
import com.jiuqi.va.bill.service.BillService;
import com.jiuqi.va.bill.utils.BillCoreI18nUtil;
import com.jiuqi.va.biz.intf.model.ModelDefine;
import com.jiuqi.va.biz.intf.model.ModelManager;
import com.jiuqi.va.biz.intf.model.ModelType;
import com.jiuqi.va.biz.utils.Utils;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.common.ShiroUtil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BillServiceImpl
implements BillService {
    private final int END_QUERY_OFFSET = -1;
    private final int FIRST_QUERY_OFFSET = 0;
    @Autowired
    private BillDataService billDataService;
    @Autowired
    private BillEditService billEditService;
    @Autowired
    private BillIncEditService billIncEditService;
    @Autowired
    private BillDao billDao;
    @Autowired
    private ModelManager modelManager;

    @Override
    public PageVO<Map<String, List<Map<String, Object>>>> getBill(BillDTO billDTO) {
        PageVO result = new PageVO();
        billDTO.setUserCode(ShiroUtil.getUser().getId().toString());
        billDTO.setPagination(false);
        int total = this.billDao.count(billDTO);
        if (total == 0) {
            R r = R.ok((String)BillCoreI18nUtil.getMessage("va.billcore.billservice.nodata"));
            result.setRs(r);
            return result;
        }
        billDTO.setPagination(true);
        billDTO.setSort("CREATETIME");
        billDTO.setLimit(1);
        int offset = billDTO.getOffset();
        if (offset > total - 1) {
            R r = R.ok((String)BillCoreI18nUtil.getMessage("va.billcore.billservice.lastbill"));
            result.setRs(r);
            return result;
        }
        if (offset == -1) {
            billDTO.setOrder("asc");
            billDTO.setOffset(0);
        } else {
            billDTO.setOrder("desc");
        }
        List<String> billcodes = this.billDao.selectBillCode(billDTO);
        if (billcodes != null && billcodes.size() > 0) {
            String currentBillcode;
            ArrayList<Map<String, List<Map<String, Object>>>> rows = new ArrayList<Map<String, List<Map<String, Object>>>>();
            String billcode = billcodes.get(0);
            if (billcode.equals(currentBillcode = billDTO.getBillcode())) {
                if (offset == -1) {
                    R r = R.ok((String)BillCoreI18nUtil.getMessage("va.billcore.billservice.lastbill"));
                    result.setRs(r);
                    return result;
                }
                if (offset == 0) {
                    R r = R.ok((String)BillCoreI18nUtil.getMessage("va.billcore.billservice.firstbill"));
                    result.setRs(r);
                    return result;
                }
            }
            if (Utils.isEmpty((String)billDTO.getViewName())) {
                Map<String, List<Map<String, Object>>> bill = this.billDataService.load(new BillContextImpl(), billDTO.getDefineCode(), billcode);
                rows.add(bill);
                result.setRows(rows);
            } else {
                Map<String, Object> editData;
                BillContextImpl contextImpl = new BillContextImpl();
                contextImpl.setTriggerOrigin(billDTO.getTriggerOrigin());
                if (billDTO.getDefine() != null) {
                    contextImpl.setPreview(true);
                    ObjectMapper mapper = new ObjectMapper();
                    mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
                    mapper.enable(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL);
                    BillDefine billDefine = (BillDefine)mapper.convertValue(billDTO.getDefine(), BillDefine.class);
                    String billType = billDefine.getModelType();
                    ModelType modelType = (ModelType)this.modelManager.find(billType);
                    modelType.initModelDefine((ModelDefine)billDefine, billDefine.getName());
                    editData = billDTO.isUseInc() ? this.billIncEditService.load(contextImpl, billDefine, billcode, billDTO.getViewName()) : this.billEditService.load(contextImpl, billDefine, billcode, billDTO.getViewName());
                } else {
                    editData = billDTO.isUseInc() ? this.billIncEditService.load(contextImpl, billDTO.getDefineCode(), billDTO.getDefineVer(), billcode, billDTO.getViewName()) : this.billEditService.load(contextImpl, billDTO.getDefineCode(), billDTO.getDefineVer(), billcode, billDTO.getViewName());
                }
                HashMap<String, List<Map>> bill = new HashMap<String, List<Map>>();
                bill.put("editData", Arrays.asList(editData));
                rows.add(bill);
                result.setRows(rows);
            }
        } else {
            R r = new R();
            result.setRs(r);
            return result;
        }
        result.setTotal(total);
        return result;
    }
}

