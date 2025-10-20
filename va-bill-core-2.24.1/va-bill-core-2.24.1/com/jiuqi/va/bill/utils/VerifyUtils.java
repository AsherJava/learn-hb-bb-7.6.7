/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.biz.utils.Env
 *  com.jiuqi.va.biz.utils.Utils
 *  com.jiuqi.va.domain.bill.BillVerifyDTO
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.feign.client.BillVerifyClient
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 */
package com.jiuqi.va.bill.utils;

import com.jiuqi.va.bill.impl.BillContextImpl;
import com.jiuqi.va.bill.intf.BillException;
import com.jiuqi.va.bill.intf.BillModel;
import com.jiuqi.va.bill.utils.BillCoreI18nUtil;
import com.jiuqi.va.biz.utils.Env;
import com.jiuqi.va.biz.utils.Utils;
import com.jiuqi.va.domain.bill.BillVerifyDTO;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.feign.client.BillVerifyClient;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class VerifyUtils {
    public static final int VERIFY_ERROR = -1;
    public static final int VERIFY_AUTH = -2;
    public static final int VERIFY_TIMEOUT = -3;
    public static final int AUTH_ALL = 15;
    public static final int AUTH_AUTO = 0;
    public static final int AUTH_VIEW = 1;
    public static final int AUTH_EDIT = 2;

    public static String genVerifyCode(BillModel model, int auth) {
        Object createUserId = model.getMaster().getValue("CREATEUSER");
        if (createUserId == null) {
            return null;
        }
        String billCode = model.getMaster().getString("BILLCODE");
        return VerifyUtils.genVerifyCode(billCode, auth);
    }

    public static List<String> genVerifyCode(BillVerifyDTO dto) {
        BillVerifyClient billVerifyClient = (BillVerifyClient)ApplicationContextRegister.getBean(BillVerifyClient.class);
        R r = billVerifyClient.encodeBillCode(dto);
        if (r.get((Object)"data") != null) {
            return (List)r.get((Object)"data");
        }
        return Collections.EMPTY_LIST;
    }

    private static String genVerifyCode(String billCode, int auth) {
        BillVerifyClient billVerifyClient = (BillVerifyClient)ApplicationContextRegister.getBean(BillVerifyClient.class);
        BillVerifyDTO billVerifyDTO = new BillVerifyDTO();
        billVerifyDTO.setBillCode(billCode);
        billVerifyDTO.setAuth(auth);
        R r = billVerifyClient.encodeBillCode(billVerifyDTO);
        if (r.get((Object)"data") != null) {
            return String.valueOf(r.get((Object)"data"));
        }
        return null;
    }

    public static void verifyBill(BillModel model, int auth) {
        Object o = model.getContext().getCustomContext().get("X--superEdit");
        Object loadChangeData = model.getContext().getCustomContext().get("X--loadChangeData");
        if (o != null && ((Boolean)o).booleanValue() || loadChangeData != null && ((Boolean)loadChangeData).booleanValue()) {
            return;
        }
        VerifyUtils.doVerifyBill(model, auth);
        if (Boolean.TRUE.booleanValue()) {
            int r = VerifyUtils.doVerifyBill(model, auth);
            String verifyCode = r == 0 ? null : VerifyUtils.genVerifyCode(model, r);
            ((BillContextImpl)model.getContext()).setVerifyCode(verifyCode);
        }
    }

    private static int doVerifyBill(BillModel model, int auth) {
        if (model.getContext().isDisableVerify()) {
            return 0;
        }
        String createUserId = model.getMaster().getString("CREATEUSER");
        if (createUserId == null) {
            return 0;
        }
        String verifyCode = model.getContext().getVerifyCode();
        if (Utils.isEmpty((String)verifyCode)) {
            if (Objects.equals(createUserId, Env.getUserId())) {
                return 0;
            }
            return VerifyUtils.check(-1);
        }
        int r = VerifyUtils.doVerify(model.getMaster().getString("BILLCODE"), auth, verifyCode);
        if (r < 0) {
            if (Objects.equals(createUserId, Env.getUserId())) {
                return 0;
            }
            return VerifyUtils.check(r);
        }
        return r;
    }

    private static int doVerify(String billCode, int auth, String verifyCode) {
        if (Utils.isEmpty((String)verifyCode)) {
            return -1;
        }
        BillVerifyClient billVerifyClient = (BillVerifyClient)ApplicationContextRegister.getBean(BillVerifyClient.class);
        BillVerifyDTO billVerifyDTO = new BillVerifyDTO();
        billVerifyDTO.setBillCode(billCode);
        billVerifyDTO.setAuth(auth);
        billVerifyDTO.setVerifyCode(verifyCode);
        R r = billVerifyClient.VerifyBill(billVerifyDTO);
        return (Integer)r.get((Object)"VerifyResult");
    }

    private static int check(int r) {
        if (r == -2 || r == -1) {
            throw new BillException(BillCoreI18nUtil.getMessage("va.billcore.verifyutil.userdifferentnotauthorized"), 1);
        }
        if (r < 0) {
            throw new BillException(BillCoreI18nUtil.getMessage("va.billcore.verifyutil.notauthorized"), 1);
        }
        return r;
    }
}

