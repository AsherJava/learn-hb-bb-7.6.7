/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.biz.impl.data.DataTableImpl
 *  com.jiuqi.va.biz.intf.ActionCategory
 *  com.jiuqi.va.biz.intf.action.ActionRequest
 *  com.jiuqi.va.biz.intf.action.ActionResponse
 *  com.jiuqi.va.biz.intf.action.ActionReturnUtil
 *  com.jiuqi.va.biz.intf.data.DataTarget
 *  com.jiuqi.va.biz.intf.model.Model
 *  com.jiuqi.va.biz.ruler.common.consts.FormulaType
 *  com.jiuqi.va.biz.ruler.impl.RulerImpl
 *  com.jiuqi.va.biz.ruler.intf.CheckResult
 *  com.jiuqi.va.biz.utils.Utils
 *  com.jiuqi.va.feign.util.LogUtil
 */
package com.jiuqi.va.bill.action;

import com.jiuqi.va.bill.action.NextStepAction;
import com.jiuqi.va.bill.impl.BillActionBase;
import com.jiuqi.va.bill.intf.BillConsts;
import com.jiuqi.va.bill.intf.BillException;
import com.jiuqi.va.bill.intf.BillModel;
import com.jiuqi.va.bill.utils.BillCoreI18nUtil;
import com.jiuqi.va.biz.impl.data.DataTableImpl;
import com.jiuqi.va.biz.intf.ActionCategory;
import com.jiuqi.va.biz.intf.action.ActionRequest;
import com.jiuqi.va.biz.intf.action.ActionResponse;
import com.jiuqi.va.biz.intf.action.ActionReturnUtil;
import com.jiuqi.va.biz.intf.data.DataTarget;
import com.jiuqi.va.biz.intf.model.Model;
import com.jiuqi.va.biz.ruler.common.consts.FormulaType;
import com.jiuqi.va.biz.ruler.impl.RulerImpl;
import com.jiuqi.va.biz.ruler.intf.CheckResult;
import com.jiuqi.va.biz.utils.Utils;
import com.jiuqi.va.feign.util.LogUtil;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

@Component
public class SaveAction
extends BillActionBase {
    private static final List<String> INCLUDES = Arrays.asList("ID", "MASTERID", "BILLCODE", "VER", "$UNSET", "ORDINAL");
    @Autowired
    private NextStepAction nextStepAction;

    public String getName() {
        return "bill-save";
    }

    public String getTitle() {
        return "\u4fdd\u5b58";
    }

    public String getIcon() {
        return "@va/va-iconfont icona-16_GJ_A_VA_baocun";
    }

    public String getActionPriority() {
        return "003";
    }

    public boolean before(Model model, ActionRequest request, ActionResponse response) {
        Object schemeCode = request.getParams().get("schemeCode");
        if (schemeCode != null) {
            return this.nextStepAction.before(model, request, response);
        }
        BillModel billModel = (BillModel)model;
        SaveAction.updateEmptyRow(billModel);
        billModel.getData().getTables().stream().forEach(o -> ((DataTableImpl)o).deleteUnsetRows());
        List checkMessages = ((RulerImpl)billModel.getRuler()).getRulerExecutor().beforeAction("save", Stream.of(FormulaType.WARN).collect(Collectors.toList()), true);
        if (checkMessages != null && !checkMessages.isEmpty()) {
            throw new BillException(BillCoreI18nUtil.getMessage("va.billcore.saveaction.savefailed"), Utils.handleErrorMsg((String)billModel.getMasterTable().getName(), (List)checkMessages));
        }
        return super.before(model, request, response);
    }

    @Override
    public Object executeReturn(BillModel billModel, Map<String, Object> params) {
        Object schemeCode = params.get("schemeCode");
        if (schemeCode != null) {
            return this.nextStepAction.executeReturn(billModel, params);
        }
        Object confirms = params.get("confirms");
        if (confirms != null && ((List)confirms).contains(this.getName())) {
            LogUtil.add((String)"\u5355\u636e", (String)"\u4fdd\u5b58", (String)billModel.getDefine().getName(), (String)billModel.getMaster().getString("BILLCODE"), null);
            List checkMessages = ((RulerImpl)billModel.getRuler()).getRulerExecutor().beforeAction("save", Stream.of(FormulaType.WARN).collect(Collectors.toList()), true);
            if (checkMessages != null && !checkMessages.isEmpty()) {
                throw new BillException(BillCoreI18nUtil.getMessage("va.billcore.saveaction.savefailed"), Utils.handleErrorMsg((String)billModel.getMasterTable().getName(), (List)checkMessages));
            }
            billModel.save();
            return ActionReturnUtil.returnGlobalMessage((String)BillCoreI18nUtil.getMessage("va.billcore.saveaction.savesuccess"), (String)"success");
        }
        List checkMessages = ((RulerImpl)billModel.getRuler()).getRulerExecutor().beforeAction("save", Stream.of(FormulaType.WARN).collect(Collectors.toList()), false);
        if (checkMessages != null && !checkMessages.isEmpty()) {
            List unique = Utils.handleErrorMsg((String)billModel.getMasterTable().getName(), (List)checkMessages);
            return ActionReturnUtil.returnConfirmMessage((List)unique, (String)this.getName());
        }
        LogUtil.add((String)"\u5355\u636e", (String)"\u4fdd\u5b58", (String)billModel.getDefine().getName(), (String)billModel.getMaster().getString("BILLCODE"), null);
        billModel.save();
        return ActionReturnUtil.returnGlobalMessage((String)BillCoreI18nUtil.getMessage("va.billcore.saveaction.savesuccess"), (String)"success");
    }

    private static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor) {
        ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap();
        return t -> concurrentHashMap.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

    public static void handleErrorMsg(BillModel billModel, List<CheckResult> checkMessages) {
        String masterTableName = billModel.getMasterTable().getName();
        List<CheckResult> unique = checkMessages.stream().filter(SaveAction.distinctByKey(o -> o.getCheckMessage() + o.getTargetList().map(target -> {
            if (masterTableName.equals(target.getTableName())) {
                return target.getTableName() + target.getFieldName();
            }
            return target.getTableName() + target.getFieldName() + target.getRowIndex() + target.getRowID();
        }).collect(Collectors.toList()))).collect(Collectors.toList());
        unique.sort((result1, result2) -> {
            Integer n;
            Integer n2;
            String tableName;
            Integer o1 = 1;
            Integer o2 = 1;
            Optional option1 = result1.getTargetList().findAny();
            Optional option2 = result2.getTargetList().findAny();
            if (option1.isPresent() && (tableName = ((DataTarget)option1.get()).getTableName()) != null && tableName.equals(masterTableName)) {
                n2 = o1;
                n = o1 = Integer.valueOf(o1 + 1);
            }
            if (option2.isPresent() && (tableName = ((DataTarget)option2.get()).getTableName()) != null && tableName.equals(masterTableName)) {
                n2 = o2;
                n = o2 = Integer.valueOf(o2 + 1);
            }
            return o2 - o1;
        });
        throw new BillException(BillCoreI18nUtil.getMessage("va.billcore.saveaction.savefailed"), unique);
    }

    public static void updateEmptyRow(BillModel billModel) {
        billModel.getData().getTables().stream().forEach(o -> {
            if (o.getDefine().isSingle()) {
                return;
            }
            if (o.getName().equals(billModel.getMasterTable().getName())) {
                return;
            }
            List rowsData = o.getInsertData();
            if (rowsData == null || rowsData.isEmpty()) {
                return;
            }
            for (Map row : rowsData) {
                if (!row.entrySet().stream().noneMatch(map -> !INCLUDES.contains(map.getKey()) && !ObjectUtils.isEmpty(map.getValue()))) continue;
                o.getRowById(row.get("ID")).setData(Stream.of("$UNSET").collect(Collectors.toMap(m -> m, m -> true)));
            }
        });
    }

    @Override
    public String[] getModelParams() {
        return BillConsts.ACTION_PARAM_EDIT;
    }

    public ActionCategory getActionCategory() {
        return ActionCategory.SAVE;
    }
}

