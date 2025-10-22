/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.util.tree.Tree
 */
package com.jiuqi.nr.dataentry.gather.impl;

import com.jiuqi.np.util.tree.Tree;
import com.jiuqi.nr.dataentry.gather.ActionItem;
import com.jiuqi.nr.dataentry.gather.ActionItemImpl;
import com.jiuqi.nr.dataentry.gather.ActionType;
import com.jiuqi.nr.dataentry.gather.ITreeGathers;
import com.jiuqi.nr.dataentry.gather.KeyStore;
import com.jiuqi.nr.dataentry.gather.impl.CalcAllFormulaSchemeHoldItemImpl;
import com.jiuqi.nr.dataentry.gather.impl.workflowHoldItemImpl;
import com.jiuqi.nr.dataentry.service.IFunctionAuthorService;
import com.jiuqi.nr.dataentry.util.Consts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component(value="dataentry_actions")
public class ActionsGatherImp
implements ITreeGathers<ActionItem> {
    @Autowired
    private IFunctionAuthorService functionAuthorService;

    @Override
    public Tree<ActionItem> gather() {
        Tree tree = new Tree();
        ActionItemImpl separator = new ActionItemImpl("separator", "\u5206\u5272\u7ebf", "\u5206\u5272\u7ebf", "#icon-_Ttianjia1");
        separator.setActionType(ActionType.SEPARATOR);
        tree.addChild((Object)separator);
        CalcAllFormulaSchemeHoldItemImpl calcAllFormulaScheme = new CalcAllFormulaSchemeHoldItemImpl();
        tree.addChild((Object)calcAllFormulaScheme);
        ActionItemImpl newAction = new ActionItemImpl("new", "\u65b0\u5efa", "\u65b0\u5efa", " iconfont icon-_Ttianjia1 ");
        tree.addChild((Object)newAction);
        ActionItemImpl save = new ActionItemImpl("save", "\u4fdd\u5b58");
        save.setIcon("#icon-_GJTbaocun ");
        KeyStore saveAccel = new KeyStore(83, 2);
        save.setAccelerator(saveAccel);
        save.setDesc("\u4fdd\u5b58");
        save.setParamsDesc("\u4fdd\u5b58\u540e\u6307\u5b9a\u591a\u4e2a\u516c\u5f0f\u65b9\u6848\u8fd0\u7b97\u53c2\u6570\u7ed3\u6784\uff1a{\"taskidxxxxx\":{\"calcFormulaSchemeKeys\":[\"xxx\",\"xxxx\"]}}\n \u53c2\u6570\u793a\u4f8b: {\"11cfda08-e9b0-4e97-8efc-3c8d44f95045\":{\"calcFormulaSchemeKeys\":[\"c22f8c84-16ba-4b8d-9131-763daa549e30\",\"46265c4e-07f4-4a1e-9210-8faa887347f4\"]}} ");
        tree.addChild((Object)save);
        ActionItemImpl analysis = new ActionItemImpl("analysis", "\u5206\u6790", "\u5206\u6790", "#icon-16_GJ_A_NR_piliangfenxi");
        tree.addChild((Object)analysis);
        ActionItemImpl addRow = new ActionItemImpl("addRow", "\u63d2\u884c", "\u63d2\u884c", "#icon-_GJThouchahang ");
        tree.addChild((Object)addRow);
        ActionItemImpl deleteRow = new ActionItemImpl("deleteRow", "\u5220\u884c", "\u5220\u884c", "#icon-_GJTshanhang ");
        tree.addChild((Object)deleteRow);
        ActionItemImpl addCol = new ActionItemImpl("addCol", "\u63d2\u5217", "\u63d2\u5217", "#icon-_GJThouchalie ");
        tree.addChild((Object)addCol);
        ActionItemImpl deleteCol = new ActionItemImpl("deleteCol", "\u5220\u5217", "\u5220\u5217", "#icon-16_GJ_A_NR_houchalie");
        tree.addChild((Object)deleteCol);
        ActionItemImpl refresh = new ActionItemImpl("refresh", "\u5237\u65b0", "\u5237\u65b0", "#icon-16_GJ_A_NR_shanchulie");
        tree.addChild((Object)refresh);
        ActionItemImpl dataTraceBack = new ActionItemImpl("dataTraceBack", "\u6570\u636e\u8ffd\u6eaf", "\u6570\u636e\u8ffd\u6eaf", "#icon-16_GJ_A_NR_shujuzhuizong ");
        tree.addChild((Object)dataTraceBack);
        ActionItemImpl cardInput = new ActionItemImpl("cardInput", "\u5361\u7247\u5f55\u5165", "\u5361\u7247\u5f55\u5165", "#icon-_Wgongnengguanli ");
        tree.addChild((Object)cardInput);
        ActionItemImpl freeze = new ActionItemImpl("freeze", "\u51bb\u7ed3", "\u51bb\u7ed3", "#icon-_GJWdongjiechuangkou ");
        tree.addChild((Object)freeze);
        ActionItemImpl unFreeze = new ActionItemImpl("unFreeze", "\u53d6\u6d88\u51bb\u7ed3", "\u53d6\u6d88\u51bb\u7ed3", "#icon-_GJWdongjiechuangkou ");
        tree.addChild((Object)unFreeze);
        ActionItemImpl sort = new ActionItemImpl("sort", "\u6392\u5e8f", "\u6392\u5e8f", "#icon-_GJTshaixuan ");
        tree.addChild((Object)sort);
        ActionItemImpl clearTable = new ActionItemImpl("clearTable", "\u6574\u8868\u6e05\u9664", "\u6574\u8868\u6e05\u9664", " #icon-_GJTqingchu ");
        clearTable.setActionType(ActionType.GROUP);
        Tree clearTableTree = tree.addChild((Object)clearTable);
        ActionItemImpl batchTable = new ActionItemImpl("batchTable", "\u6279\u91cf\u6e05\u9664", "\u6279\u91cf\u6e05\u9664", " #icon-_GJTqingchu ");
        batchTable.setParentCode(clearTable.getCode());
        clearTableTree.addChild((Object)batchTable);
        ActionItemImpl calc = new ActionItemImpl("calc", "\u8fd0\u7b97", "\u8fd0\u7b97", "#icon-_GJZyunsuan ");
        calc.setActionType(ActionType.GROUP);
        Tree calcTree = tree.addChild((Object)calc);
        ActionItemImpl calcAll = new ActionItemImpl("calcAll", "\u5168\u7b97", "\u5168\u7b97", "#icon-_GJTquansuan ");
        tree.addChild((Object)calcAll);
        ActionItemImpl batchCalc = new ActionItemImpl("batchCalc", "\u6279\u91cf\u8fd0\u7b97", "\u6279\u91cf\u8fd0\u7b97", "#icon-_GJZpiliangyunsuan ");
        batchCalc.setParentCode(calc.getCode());
        calcTree.addChild((Object)batchCalc);
        ActionItemImpl check = new ActionItemImpl("check", "\u5ba1\u6838", "\u5ba1\u6838", "#icon-_GJZshenhe ");
        check.setActionType(ActionType.GROUP);
        Tree checkTree = tree.addChild((Object)check);
        ActionItemImpl checkAll = new ActionItemImpl("checkAll", "\u5168\u5ba1", "\u5168\u5ba1", "#icon-_GJZquanshen ");
        tree.addChild((Object)checkAll);
        ActionItemImpl batchCheck = new ActionItemImpl("batchCheck", "\u6279\u91cf\u5ba1\u6838", "\u6279\u91cf\u5ba1\u6838", "#icon-_GJZpiliangshenhe ");
        batchCheck.setParentCode(check.getCode());
        checkTree.addChild((Object)batchCheck);
        ActionItemImpl dataSumAndCalc = new ActionItemImpl("dataSumAndCalcAll", "\u6c47\u603b\u540e\u5168\u7b97", "\u6c47\u603b\u540e\u5168\u7b97", "#icon16_GJ_A_NW_shoudongzhihang");
        tree.addChild((Object)dataSumAndCalc);
        ActionItemImpl analysisFormBatchCheck = new ActionItemImpl("analysisFormBatchCheck", "\u8d22\u653f\u7edf\u4e00\u6279\u91cf\u5ba1\u6838", "\u8d22\u653f\u7edf\u4e00\u62a5\u8868\u5b9a\u5236\u5f00\u53d1\u7684\u6279\u91cf\u5ba1\u6838", "#icon-_GJZpiliangshenhe ");
        tree.addChild((Object)analysisFormBatchCheck);
        if (0 <= this.functionAuthorService.queryAuthorByModule("selectDataSum")) {
            ActionItemImpl dataSum = new ActionItemImpl("dataSum", "\u6c47\u603b", "\u6c47\u603b", "#icon-_GJZzidingyihuizong ");
            dataSum.setActionType(ActionType.GROUP);
            Tree dataSumTree = tree.addChild((Object)dataSum);
            ActionItemImpl selectDataSum = new ActionItemImpl("selectDataSum", "\u9009\u62e9\u6c47\u603b", "\u9009\u62e9\u6c47\u603b", "#icon-_GJDxuanzehuizong");
            selectDataSum.setParentCode(dataSum.getCode());
            dataSumTree.addChild((Object)selectDataSum);
        }
        if (0 <= this.functionAuthorService.queryAuthorByModule("workflow")) {
            workflowHoldItemImpl workflowHoldItem = new workflowHoldItemImpl();
            tree.addChild((Object)workflowHoldItem);
        }
        if (0 <= this.functionAuthorService.queryAuthorByModule("importFormData")) {
            ActionItemImpl importFormData = new ActionItemImpl("importFormData", "\u5bfc\u5165", "\u5bfc\u5165", "#icon-_GJYexceldaoru");
            tree.addChild((Object)importFormData);
            importFormData.setParamsDesc("\u5bfc\u5165\u53c2\u6570\u7ed3\u6784\uff1a{\"taskidxxxxx\":{\"importTypes\":[\"ZIP\",\"EXCEL\",\"JIO\",\"CSV\"]}}\n \u53c2\u6570\u793a\u4f8b: {\"all\":{\"importTypes\":[\"ZIP\",\"EXCEL\"]}} ");
        }
        if (0 <= this.functionAuthorService.queryAuthorByModule("uploadJIO")) {
            ActionItemImpl uplaodJIO = new ActionItemImpl("uploadJIO", "jio\u5bfc\u5165", "jio\u5bfc\u5165", "#icon-_GJYexceldaoru");
            tree.addChild((Object)uplaodJIO);
        }
        ActionItemImpl export = new ActionItemImpl("export", "\u5bfc\u51fa", "\u5bfc\u51fa", " #icon-_GJTdaochu");
        export.setActionType(ActionType.GROUP);
        Tree exportTree = tree.addChild((Object)export);
        ActionItemImpl batchExport = new ActionItemImpl("batchExport", "\u6279\u91cf\u5bfc\u51fa", "\u6279\u91cf\u5bfc\u51fa", "#icon-_GJZpiliangdaochu");
        batchExport.setParentCode(export.getCode());
        exportTree.addChild((Object)batchExport);
        ActionItemImpl exportFormData = new ActionItemImpl("exportFormData", "\u5bfc\u51fa\u8868\u6837", "\u5bfc\u51fa\u8868\u6837", "#icon-_GJZpiliangdaochu");
        exportFormData.setParentCode(export.getCode());
        exportTree.addChild((Object)exportFormData);
        ActionItemImpl print = new ActionItemImpl("print", "\u6253\u5370", "\u6253\u5370", "#icon-_CXWdayin");
        print.setActionType(ActionType.GROUP);
        Tree printTree = tree.addChild((Object)print);
        ActionItemImpl downloadReport = new ActionItemImpl("downloadReport", "\u4e0b\u8f7d\u62a5\u544a", "\u4e0b\u8f7d\u62a5\u544a", "#icon-_Wxiazai");
        tree.addChild((Object)downloadReport);
        ActionItemImpl sumDataPrint = new ActionItemImpl("sumCoverPrint", "\u6c47\u603b\u5c01\u9762\u6253\u5370", "\u6c47\u603b\u5c01\u9762\u6253\u5370", "#icon-_CXWdayin");
        sumDataPrint.setParentCode(print.getCode());
        printTree.addChild((Object)sumDataPrint);
        ActionItemImpl batchPrint = new ActionItemImpl("batchPrint", "\u6279\u91cf\u6253\u5370", "\u6279\u91cf\u6253\u5370", "#icon-_CXWdayin");
        batchPrint.setParentCode(print.getCode());
        printTree.addChild((Object)batchPrint);
        if (0 <= this.functionAuthorService.queryAuthorByModule("efdcFetch")) {
            ActionItemImpl efdcFetch = new ActionItemImpl("efdcFetch", "EFDC\u53d6\u6570", "EFDC\u53d6\u6570", "#icon-_GJDEFDCqushu");
            efdcFetch.setActionType(ActionType.GROUP);
            Tree efdcFetchTree = tree.addChild((Object)efdcFetch);
            ActionItemImpl batchEfdc = new ActionItemImpl("batchEfdcFetch", "\u6279\u91cfEFDC\u53d6\u6570", "\u6279\u91cfEFDC\u53d6\u6570", "#icon-_GJDEFDCqushu");
            batchEfdc.setParentCode(efdcFetch.getCode());
            efdcFetchTree.addChild((Object)batchEfdc);
        }
        ActionItemImpl search = new ActionItemImpl("search", "\u641c\u7d22", "\u641c\u7d22", "#icon-_Tsousuo");
        tree.addChild((Object)search);
        ActionItemImpl nodeCheck = new ActionItemImpl("nodeCheck", "\u8282\u70b9\u68c0\u67e5", "\u8282\u70b9\u68c0\u67e5", "#icon-_GJZjiedianjiancha");
        tree.addChild((Object)nodeCheck);
        ActionItemImpl zbSelect = new ActionItemImpl("zbSelect", "\u6307\u6807\u9009\u62e9\u5668", "\u6307\u6807\u9009\u62e9\u5668", "#icon-_CXWdayin");
        tree.addChild((Object)zbSelect);
        if (0 <= this.functionAuthorService.queryAuthorByModule("dataPublish")) {
            ActionItemImpl dataPublish = new ActionItemImpl("dataPublish", "\u6570\u636e\u53d1\u5e03", "\u6570\u636e\u53d1\u5e03", "#icon-_GJWshujufabu");
            tree.addChild((Object)dataPublish);
        }
        ActionItemImpl cancelPublish = new ActionItemImpl("cancelPublish", "\u53d6\u6d88\u53d1\u5e03", "\u53d6\u6d88\u53d1\u5e03", "#icon-_GJWquxiaofabu");
        tree.addChild((Object)cancelPublish);
        ActionItemImpl batchCopy = new ActionItemImpl("batchCopy", "\u6279\u91cf\u590d\u5236", "\u6279\u91cf\u590d\u5236", "#icon-_GJZfuzhi");
        tree.addChild((Object)batchCopy);
        ActionItemImpl batchCopyBTWEntity = new ActionItemImpl("batchCopyBTWEntity", "\u8de8\u5355\u4f4d\u53e3\u5f84\u6279\u91cf\u590d\u5236", "\u8de8\u5355\u4f4d\u53e3\u5f84\u6279\u91cf\u590d\u5236", "#icon-_GJZfuzhi");
        tree.addChild((Object)batchCopyBTWEntity);
        ActionItemImpl addDataAnnocation = new ActionItemImpl("addDataAnnocation", "\u6dfb\u52a0\u6279\u6ce8", "\u6dfb\u52a0\u6279\u6ce8", "#icon-_GJZpizhu");
        tree.addChild((Object)addDataAnnocation);
        ActionItemImpl seeDataAnnocation = new ActionItemImpl("seeDataAnnocation", "\u67e5\u770b\u6240\u6709\u6279\u6ce8", "\u67e5\u770b\u6240\u6709\u6279\u6ce8", "#icon-_GJZpizhu");
        tree.addChild((Object)seeDataAnnocation);
        ActionItemImpl processTracking = new ActionItemImpl("processTracking", "\u6d41\u7a0b\u8ddf\u8e2a", "\u6d41\u7a0b\u8ddf\u8e2a", "#icon-16_GJZliuchenggenzong");
        tree.addChild((Object)processTracking);
        ActionItemImpl gradedAggregate = new ActionItemImpl("gradedAggregate", "\u5206\u7ea7\u5408\u8ba1", "\u5206\u7ea7\u5408\u8ba1", "#icon-16_GJZliuchenggenzong");
        tree.addChild((Object)gradedAggregate);
        ActionItemImpl amountConversion = new ActionItemImpl("amountConversion", "\u91d1\u989d\u8f6c\u6362", "\u91d1\u989d\u8f6c\u6362", "#icon-_GJYlianggang ");
        tree.addChild((Object)amountConversion);
        ActionItemImpl millennialDisplay = new ActionItemImpl("millennialDisplay", "\u5343\u5206\u4f4d\u663e\u793a", "\u5343\u5206\u4f4d\u663e\u793a\u8bbe\u7f6e", "#icon-_GJYlianggang ");
        tree.addChild((Object)millennialDisplay);
        ActionItemImpl etlFetch = new ActionItemImpl("etlFetch", "ETL\u53d6\u6570", "ETL\u53d6\u6570", "#icon-16_GJWETLqushu1");
        tree.addChild((Object)etlFetch);
        ActionItemImpl batchDownLoadEnclosure = new ActionItemImpl("batchDownLoadEnclosure", "\u6279\u91cf\u9644\u4ef6\u4e0b\u8f7d", "\u6279\u91cf\u9644\u4ef6\u4e0b\u8f7d", "#icon-_GJWpiliangxiazai");
        batchDownLoadEnclosure.setParamsDesc("\u914d\u7f6e\u6b64\u53c2\u6570\uff1a{ \"all\":{ \"showAllMD\": \"false\"}} \u4e0d\u663e\u793a\u6240\u6709\u5355\u4f4d\u9009\u9879\uff0c\u4e0d\u914d\u7f6e\u9ed8\u8ba4\u663e\u793a\u6240\u6709\u5355\u4f4d\u9009\u9879");
        tree.addChild((Object)batchDownLoadEnclosure);
        ActionItemImpl dataStatus = new ActionItemImpl("dataStatus", "\u66f4\u65b0\u6570\u636e\u72b6\u6001", "\u66f4\u65b0\u6570\u636e\u72b6\u6001", "#icon-16_GJWETLqushu");
        tree.addChild((Object)dataStatus);
        ActionItemImpl hideRow = new ActionItemImpl("hideRow", "\u9690\u85cf\u884c", "\u9690\u85cf\u884c", "#icon-_GJYyincanghang ");
        tree.addChild((Object)hideRow);
        ActionItemImpl hideCol = new ActionItemImpl("hideCol", "\u9690\u85cf\u5217", "\u9690\u85cf\u5217", "#icon-_GJYyincanglie ");
        tree.addChild((Object)hideCol);
        ActionItemImpl showHideRow = new ActionItemImpl("showHideRow", "\u663e\u793a\u9690\u85cf\u884c", "\u663e\u793a\u9690\u85cf\u884c", "#icon-_GJYxianshihang ");
        tree.addChild((Object)showHideRow);
        ActionItemImpl showHideCol = new ActionItemImpl("showHideCol", "\u663e\u793a\u9690\u85cf\u5217", "\u663e\u793a\u9690\u85cf\u5217", "#icon-_GJYxianshilie ");
        tree.addChild((Object)showHideCol);
        ActionItemImpl attachmentManagement = new ActionItemImpl("attachmentManagement", "\u9644\u4ef6\u7ba1\u7406", "\u9644\u4ef6\u7ba1\u7406", "#icon-_GJWpiliangxiazai");
        tree.addChild((Object)attachmentManagement);
        ActionItemImpl copy = new ActionItemImpl("copy", "\u590d\u5236", "\u590d\u5236", "#icon-_GJZfuzhi");
        tree.addChild((Object)copy);
        ActionItemImpl paste = new ActionItemImpl("paste", "\u7c98\u8d34", "\u7c98\u8d34", "#icon-_GJZniantie");
        tree.addChild((Object)paste);
        ActionItemImpl cut = new ActionItemImpl("cut", "\u526a\u5207", "\u526a\u5207", "#icon-_GJZjianqie");
        tree.addChild((Object)cut);
        ActionItemImpl dataTrace = new ActionItemImpl("dataTrace", "\u6570\u636e\u8ffd\u8e2a", "\u6570\u636e\u8ffd\u8e2a", "#icon-16_GJ_A_NR_shujuzhuizong");
        tree.addChild((Object)dataTrace);
        ActionItemImpl fmlDebug = new ActionItemImpl("fmlDebug", "\u516c\u5f0f\u8c03\u8bd5", "\u516c\u5f0f\u8c03\u8bd5", "#icon-16_GJ_A_NR_shujuzhuizong");
        tree.addChild((Object)fmlDebug);
        ActionItemImpl rollBack = new ActionItemImpl("rollBack", "\u53f0\u8d26\u6570\u636e\u56de\u6eda", "\u53f0\u8d26\u6570\u636e\u56de\u6eda", "#icon-16_GJ_A_NR_taizhangshujuhuigun");
        tree.addChild((Object)rollBack);
        ActionItemImpl efdcPierce = new ActionItemImpl("efdcPierce", "EFDC\u7a7f\u900f", "EFDC\u7a7f\u900f", "#icon-_Wshujuchaxun1");
        tree.addChild((Object)efdcPierce);
        ActionItemImpl findAndReplace = new ActionItemImpl("findAndReplace", "\u67e5\u627e", "\u67e5\u627e", "#icon-_Tsousuo");
        tree.addChild((Object)findAndReplace);
        ActionItemImpl insertByEnum = new ActionItemImpl("insertEntityLines", "\u6309\u679a\u4e3e\u63d2\u884c", "\u6309\u679a\u4e3e\u63d2\u884c", "#icon-_GJThouchaduohang");
        tree.addChild((Object)insertByEnum);
        ActionItemImpl clearCell = new ActionItemImpl("clearCell", "\u6e05\u9664\u6240\u9009\u5355\u5143\u683c", "\u6e05\u9664\u6240\u9009\u5355\u5143\u683c", "#icon-_GJTqingchu");
        tree.addChild((Object)clearCell);
        ActionItemImpl copyErrorDesfromLastPeriod = new ActionItemImpl("copyErrorDesfromLastPeriod", "\u540c\u6b65\u4e0a\u671f\u51fa\u9519\u8bf4\u660e", "\u540c\u6b65\u4e0a\u671f\u51fa\u9519\u8bf4\u660e", "#icon-_GJZniantie");
        tree.addChild((Object)copyErrorDesfromLastPeriod);
        ActionItemImpl copyErrorDesToOtherCurrency = new ActionItemImpl("copyErrorDesToOtherCurrency", "\u5e01\u79cd\u95f4\u540c\u6b65\u51fa\u9519\u8bf4\u660e", "\u5e01\u79cd\u95f4\u540c\u6b65\u51fa\u9519\u8bf4\u660e", "#icon-_GJZniantie");
        tree.addChild((Object)copyErrorDesToOtherCurrency);
        ActionItemImpl sensitiveDataShow = new ActionItemImpl("sensitiveDataShow", "\u654f\u611f\u6570\u636e\u67e5\u770b", "\u654f\u611f\u6570\u636e\u67e5\u770b", "#icon-16_GJ_A_NR_yijianshezhiyidu");
        tree.addChild((Object)sensitiveDataShow);
        return tree;
    }

    @Override
    public Consts.GatherType getGatherType() {
        return Consts.GatherType.ACTION;
    }
}

