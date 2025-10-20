/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.function.Parameter
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  com.jiuqi.va.bill.impl.BillModelImpl
 *  com.jiuqi.va.biz.impl.data.DataRowImpl
 *  com.jiuqi.va.biz.impl.data.DataTableImpl
 *  com.jiuqi.va.biz.impl.model.ModelDataContext
 *  com.jiuqi.va.biz.intf.value.ListContainer
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.org.OrgCategoryDO
 *  com.jiuqi.va.feign.client.OrgCategoryClient
 *  com.jiuqi.va.formula.intf.ModelFunction
 *  com.jiuqi.va.formula.intf.TableFieldNode
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.billcore.formula;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.function.Parameter;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.va.bill.impl.BillModelImpl;
import com.jiuqi.va.biz.impl.data.DataRowImpl;
import com.jiuqi.va.biz.impl.data.DataTableImpl;
import com.jiuqi.va.biz.impl.model.ModelDataContext;
import com.jiuqi.va.biz.intf.value.ListContainer;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.org.OrgCategoryDO;
import com.jiuqi.va.feign.client.OrgCategoryClient;
import com.jiuqi.va.formula.intf.ModelFunction;
import com.jiuqi.va.formula.intf.TableFieldNode;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class GetMergeUnitChildCodeFunction
extends ModelFunction {
    private static final String FUNCTION_NAME = "GetMergeUnitChildCode";
    private static Logger logger = LoggerFactory.getLogger(GetMergeUnitChildCodeFunction.class);
    @Autowired
    private OrgCategoryClient orgCategoryClient;

    public GetMergeUnitChildCodeFunction() {
        this.parameters().add(new Parameter("unitType", 6, "\u673a\u6784\u7c7b\u578b", false));
        this.parameters().add(new Parameter("unitCode", 0, "\u672c\u65b9\u5355\u4f4d\u4ee3\u7801\u6240\u5728\u5b57\u6bb5", false));
        this.parameters().add(new Parameter("oppUnitCode", 0, "\u5bf9\u65b9\u5355\u4f4d\u4ee3\u7801\u6240\u5728\u5b57\u6bb5", false));
        this.parameters().add(new Parameter("filed", 6, "\u53d6\u503c\u7c7b\u578b\uff0c1\uff1a\u5408\u5e76\u5355\u4f4d\uff1b2\uff1a\u672c\u90e8\u5355\u4f4d\uff1b3\uff1a\u5dee\u989d\u5355\u4f4d", false));
        this.parameters().add(new Parameter("periodStr", 6, "\u65f6\u671f\u503c(\u9ed8\u8ba4\u4e3a\u5f53\u524d\u65f6\u671f)", true));
    }

    public String name() {
        return FUNCTION_NAME;
    }

    public String title() {
        return "\u83b7\u53d6\u672c\u5bf9\u65b9\u5355\u4f4d\u7684\u5171\u540c\u4e0a\u7ea7\u5355\u4f4d\u7684\u5408\u5e76/\u672c\u90e8/\u5dee\u989d\u5355\u4f4d\u4ee3\u7801";
    }

    public int getResultType(IContext context, List<IASTNode> parameters) {
        return 6;
    }

    public String category() {
        return "\u5408\u5e76\u62a5\u8868\u51fd\u6570";
    }

    @Transactional
    public int validate(IContext context, List<IASTNode> parameters) throws SyntaxException {
        ModelDataContext cxt = (ModelDataContext)context;
        String gcOrgType = (String)parameters.get(0).evaluate((IContext)cxt);
        OrgCategoryDO orgCategoryDO = new OrgCategoryDO();
        orgCategoryDO.setName(gcOrgType);
        PageVO categoryPage = this.orgCategoryClient.list(orgCategoryDO);
        if (categoryPage.getTotal() == 0) {
            throw new BusinessRuntimeException("\u673a\u6784\u7c7b\u578b\u4e0d\u5b58\u5728:" + gcOrgType);
        }
        String filed = (String)parameters.get(3).evaluate(context);
        if (!(filed.equals("1") || filed.equals("2") || filed.equals("3"))) {
            throw new BusinessRuntimeException("\u7b2c\u56db\u4e2a\u53c2\u6570\u4e0d\u7b26\u5408\u89c4\u8303\uff0c\u8bf7\u5728'1','2','3'\u4e2d\u9009\u53d6");
        }
        return super.validate(context, parameters);
    }

    @Transactional
    public Object evalute(IContext context, List<IASTNode> parameters) {
        try {
            ModelDataContext cxt = (ModelDataContext)context;
            String gcOrgType = (String)parameters.get(0).evaluate(context);
            String dataTime = "";
            if (parameters.size() == 5) {
                dataTime = (String)parameters.get(4).evaluate((IContext)cxt);
            }
            YearPeriodObject yp = new YearPeriodObject(null, dataTime);
            GcOrgCenterService tool = GcOrgPublicTool.getInstance((String)gcOrgType, (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
            BillModelImpl model = (BillModelImpl)cxt.model;
            String unitCode = "";
            String oppUnitCode = "";
            TableFieldNode node = (TableFieldNode)parameters.get(1);
            String tableName = node.getTableName();
            String fieldName = node.getFieldName().toUpperCase();
            DataTableImpl detailTable = (DataTableImpl)model.getData().getTables().get(tableName);
            if (detailTable.getRows() != null && detailTable.getRows().size() > 0) {
                ListContainer dataRowList = detailTable.getRows();
                for (int j = 0; j < dataRowList.size(); ++j) {
                    Object value = ((DataRowImpl)dataRowList.get(j)).getValue(fieldName);
                    if (value == null) {
                        throw new BusinessRuntimeException("\u672c\u65b9\u5355\u4f4d\u4ee3\u7801\u6240\u5728\u6307\u6807\u4e3a\u7a7a\uff1a");
                    }
                    unitCode = value.toString();
                }
            }
            TableFieldNode oppnode = (TableFieldNode)parameters.get(2);
            String oppTableName = oppnode.getTableName();
            String oppFieldName = oppnode.getFieldName().toUpperCase();
            DataTableImpl oppdetailTable = (DataTableImpl)model.getData().getTables().get(oppTableName);
            if (oppdetailTable.getRows() != null && oppdetailTable.getRows().size() > 0) {
                ListContainer dataRowList = oppdetailTable.getRows();
                for (int j = 0; j < dataRowList.size(); ++j) {
                    Object value = ((DataRowImpl)dataRowList.get(j)).getValue(oppFieldName);
                    if (value == null) {
                        throw new BusinessRuntimeException("\u5bf9\u65b9\u5355\u4f4d\u4ee3\u7801\u6240\u5728\u6307\u6807\u4e3a\u7a7a\uff1a");
                    }
                    oppUnitCode = value.toString();
                }
            }
            GcOrgCacheVO unitVo = tool.getOrgByCode(unitCode);
            GcOrgCacheVO oppUnitVo = tool.getOrgByCode(oppUnitCode);
            if (unitVo == null) {
                throw new BusinessRuntimeException("\u5408\u5e76\u5355\u4f4d\u7c7b\u578b\uff1a" + gcOrgType + "\u4e2d\u4e0d\u5b58\u5728\u672c\u65b9\u5355\u4f4d\u4ee3\u7801\uff1a" + unitCode);
            }
            if (oppUnitVo == null) {
                throw new BusinessRuntimeException("\u5408\u5e76\u5355\u4f4d\u7c7b\u578b\uff1a" + gcOrgType + "\u4e2d\u4e0d\u5b58\u5728\u5bf9\u65b9\u5355\u4f4d\u4ee3\u7801\uff1a" + oppUnitCode);
            }
            GcOrgCacheVO commonUnit = tool.getCommonUnit(unitVo, oppUnitVo);
            if (commonUnit == null) {
                logger.error("\u83b7\u53d6\u5171\u540c\u4e0a\u7ea7\u5355\u4f4d\u5931\u8d25");
                return null;
            }
            String filed = (String)parameters.get(3).evaluate(context);
            if (filed.equals("1")) {
                return commonUnit.getCode();
            }
            if (filed.equals("2")) {
                return commonUnit.getBaseUnitId();
            }
            return commonUnit.getDiffUnitId();
        }
        catch (Exception e) {
            logger.error("\u83b7\u53d6\u5171\u540c\u4e0a\u7ea7\u5931\u8d25\uff1a", e);
            return null;
        }
    }

    public String addDescribe() {
        return "\u83b7\u53d6\u672c\u5bf9\u65b9\u5355\u4f4d\u7684\u5171\u540c\u4e0a\u7ea7\u5355\u4f4d\u7684\u5408\u5e76/\u672c\u90e8/\u5dee\u989d\u5355\u4f4d\u4ee3\u7801\n\u793a\u4f8b\uff1a\n    \u573a\u666f\uff1a\n        \u53d6\u53d1\u8d77\u65b9\u5355\u4f4d(GC_CLBRBILL[INITIATEORG])\u548c\u63a5\u6536\u65b9\u5355\u4f4d(GC_CLBRBILL[RECEIVEORG])\u5728\u4ea7\u6743\u53e3\u5f84(\"MD_ORG_CORPORATE\")\u4e0b\u5bf9\u5e94\u7684\u5dee\u989d\u5355\u4f4d\u3002\n    \u516c\u5f0f\uff1a\n        GetMergeUnitChildCode(\"MD_ORG_CORPORATE\",GC_CLBRBILL[INITIATEORG],GC_CLBRBILL[RECEIVEORG],\"3\")";
    }
}

