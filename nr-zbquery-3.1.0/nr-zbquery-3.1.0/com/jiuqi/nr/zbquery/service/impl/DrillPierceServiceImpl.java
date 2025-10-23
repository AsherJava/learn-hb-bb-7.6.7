/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.json.JSONObject
 */
package com.jiuqi.nr.zbquery.service.impl;

import com.jiuqi.nr.zbquery.model.ConditionField;
import com.jiuqi.nr.zbquery.model.ConditionValues;
import com.jiuqi.nr.zbquery.model.FieldGroup;
import com.jiuqi.nr.zbquery.model.QueryDimension;
import com.jiuqi.nr.zbquery.model.QueryDimensionType;
import com.jiuqi.nr.zbquery.model.QueryObject;
import com.jiuqi.nr.zbquery.rest.vo.DrillPierceParamVO;
import com.jiuqi.nr.zbquery.rest.vo.OrgInfoVO;
import com.jiuqi.nr.zbquery.service.DrillPierceService;
import com.jiuqi.nr.zbquery.service.EntityService;
import com.jiuqi.nr.zbquery.util.PeriodUtil;
import com.jiuqi.nr.zbquery.util.QueryModelFinder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DrillPierceServiceImpl
implements DrillPierceService {
    public static final String POS_CODE_CHILDREN = "positionCodeChildren";
    private EntityService entityService;

    public DrillPierceServiceImpl(@Autowired EntityService entityService) {
        this.entityService = entityService;
    }

    @Override
    public String getDrillInfo(DrillPierceParamVO vo) {
        try {
            QueryModelFinder finder = new QueryModelFinder(vo.getQueryModel());
            QueryDimension masterDimension = finder.getMasterDimension();
            if (masterDimension == null) {
                return null;
            }
            String unitFullName = masterDimension.getFullName();
            QueryObject unitQueryObject = finder.getQueryObject(unitFullName);
            if (unitQueryObject instanceof FieldGroup && ((FieldGroup)unitQueryObject).isEnablePierce()) {
                boolean isMasterDimInRow;
                boolean bl = isMasterDimInRow = finder.getLayoutIndexOnRow(unitFullName) > -1;
                if (isMasterDimInRow) {
                    JSONObject result = new JSONObject();
                    int column = this.getUnitDimColumn(vo.getColNames(), unitFullName);
                    Map<String, List<String>> unitDirectChild = this.getUnitDirectChild(finder, vo.getConditionValues(), vo.getRowDims(), unitQueryObject);
                    result.put(POS_CODE_CHILDREN, (Object)this.getPositionCode(vo.getRowDims(), column, unitFullName, unitDirectChild));
                    return result.toString();
                }
            }
        }
        catch (Exception e) {
            e.getCause();
        }
        return null;
    }

    private JSONObject getPositionCode(String _rowDims, int column, String unitFullName, Map<String, List<String>> unitDirectChild) {
        JSONObject posCode = new JSONObject();
        JSONObject rowDims = new JSONObject(_rowDims);
        for (String row : rowDims.keySet()) {
            String unitCode;
            JSONObject dims = new JSONObject(rowDims.get(row).toString());
            if (!dims.has(unitFullName) || !unitDirectChild.containsKey(unitCode = (String)dims.get(unitFullName))) continue;
            posCode.put(row + "_" + column, (Collection)unitDirectChild.get(unitCode));
        }
        return posCode;
    }

    private Map<String, List<String>> getUnitDirectChild(QueryModelFinder finder, ConditionValues conditionValues, String _rowDims, QueryObject unitQueryObject) throws Exception {
        QueryDimension periodDimension = this.getLayoutPeriodDimension(finder);
        ArrayList<String> orgValues = new ArrayList<String>();
        ArrayList<OrgInfoVO> scene2_param = new ArrayList<OrgInfoVO>();
        JSONObject rowDims = new JSONObject(_rowDims);
        for (String row : rowDims.keySet()) {
            String[] dimFullNames;
            String orgValue = "";
            String periodValue = "";
            JSONObject dims = new JSONObject(rowDims.get(row).toString());
            for (String dimFullName : dimFullNames = JSONObject.getNames((JSONObject)dims)) {
                QueryDimension dimension = finder.getLayoutDimension(dimFullName);
                if (dimension == null) continue;
                if (dimension.getDimensionType() == QueryDimensionType.PERIOD) {
                    periodValue = (String)dims.get(dimFullName);
                }
                if (dimension.getDimensionType() != QueryDimensionType.MASTER) continue;
                orgValue = (String)dims.get(dimFullName);
            }
            if ("".equals(periodValue)) {
                orgValues.add(orgValue);
                continue;
            }
            OrgInfoVO orgInfoVO = new OrgInfoVO(unitQueryObject.getSchemeName(), unitQueryObject.getName());
            if (periodDimension != null) {
                orgInfoVO.setPeriodValue(PeriodUtil.toNrPeriod(periodValue, periodDimension.getPeriodType()));
            }
            orgInfoVO.setOrgValue(orgValue);
            scene2_param.add(orgInfoVO);
        }
        if (orgValues.size() > 0) {
            OrgInfoVO scene1_param = new OrgInfoVO(unitQueryObject.getSchemeName(), unitQueryObject.getName());
            if (periodDimension != null) {
                String periodValue_BI = conditionValues.getValue(periodDimension.getFullName());
                scene1_param.setPeriodValue(PeriodUtil.toNrPeriod(periodValue_BI, periodDimension.getPeriodType()));
            }
            scene1_param.setOrgValues(orgValues);
            return this.entityService.getUnitDirectChildrenOnePeriod(scene1_param);
        }
        return this.entityService.getUnitDirectChildren(scene2_param);
    }

    private int getUnitDimColumn(String[] colNames, String unitDimFullName) {
        for (int i = 0; i < colNames.length; ++i) {
            if (!colNames[i].contains(unitDimFullName)) continue;
            return i;
        }
        return -1;
    }

    private QueryDimension getLayoutPeriodDimension(QueryModelFinder finder) {
        for (QueryDimension dim : finder.getLayoutDimensions()) {
            if (dim.getDimensionType() != QueryDimensionType.PERIOD) continue;
            return dim;
        }
        Optional<ConditionField> optional = finder.getQueryModel().getConditions().stream().filter(c -> finder.getQueryDimension(c.getFullName()).getDimensionType() == QueryDimensionType.PERIOD).findFirst();
        if (optional.isPresent()) {
            return finder.getQueryDimension(optional.get().getFullName());
        }
        return finder.getPeriodDimension();
    }
}

