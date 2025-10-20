/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.gcreport.common.InclusionRelationEnum
 *  com.jiuqi.gcreport.definition.impl.sqlutil.IdTemporaryTableUtils
 *  com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils
 *  com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils$Relation
 *  com.jiuqi.gcreport.financialcheckapi.dataentry.vo.DataInputConditionVO
 *  com.jiuqi.gcreport.financialcheckcore.check.enums.CheckStateEnum
 *  com.jiuqi.gcreport.financialcheckcore.utils.OrgUtils
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 */
package com.jiuqi.gcreport.financialcheckImpl.dataentry.sql;

import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.gcreport.common.InclusionRelationEnum;
import com.jiuqi.gcreport.definition.impl.sqlutil.IdTemporaryTableUtils;
import com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils;
import com.jiuqi.gcreport.financialcheckapi.dataentry.vo.DataInputConditionVO;
import com.jiuqi.gcreport.financialcheckcore.check.enums.CheckStateEnum;
import com.jiuqi.gcreport.financialcheckcore.utils.OrgUtils;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

public class GcFinancialCheckDataEntryQuerySQL {
    private DataInputConditionVO condition;
    protected Set<String> localUnitIds;
    protected Set<String> oppUnitIds;
    protected Boolean localUnitGroup;
    protected Boolean oppUnitGroup;
    protected InclusionRelationEnum relation = InclusionRelationEnum.NONE;
    private static final int INMAXNUM = 280;
    protected Boolean localUseTemporaryTable;
    protected Boolean oppUseTemporaryTable;
    protected String localGroupId;
    protected String oppGroupId;

    public GcFinancialCheckDataEntryQuerySQL(DataInputConditionVO condition) {
        this.condition = condition;
        this.initUnitInfo();
    }

    private String getOrgVer() {
        String dateStr = String.format("%04d", this.condition.getAcctYear()) + String.format("%02d", this.condition.getAcctPeriod()) + "01";
        return dateStr;
    }

    private void initUnitInfo() {
        this.condition.setUnitIds(OrgUtils.getAllLevelsChildrenAndSelf((String)this.condition.getUnitId(), (String)this.getOrgVer(), (GcAuthorityType)GcAuthorityType.ACCESS, (String)this.condition.getOrgType()));
        this.condition.setOppUnitIds(OrgUtils.getAllLevelsChildrenAndSelf((String)this.condition.getOppUnitId(), (String)this.getOrgVer(), (GcAuthorityType)GcAuthorityType.NONE, (String)this.condition.getOrgType()));
        this.localUnitIds = new HashSet<String>(this.condition.getUnitIds());
        if (CollectionUtils.isEmpty(this.condition.getOppUnitIds())) {
            this.condition.setOppUnitIds(OrgUtils.getAllUnitId((String)this.getOrgVer(), (GcAuthorityType)GcAuthorityType.NONE, (String)this.condition.getOrgType()));
            this.oppUnitIds = new HashSet<String>(this.condition.getOppUnitIds());
            this.oppUnitGroup = true;
        } else {
            this.oppUnitIds = new HashSet<String>(this.condition.getOppUnitIds());
            this.oppUnitGroup = OrgUtils.isGruop((String)this.condition.getOppUnitId(), (String)this.condition.getOrgVer(), (String)this.condition.getOrgType());
        }
        this.relation = InclusionRelationEnum.getInclusionRelation(this.localUnitIds, this.oppUnitIds);
        this.localUnitGroup = this.relation == InclusionRelationEnum.EQUAL && this.oppUnitGroup != false ? Boolean.valueOf(true) : Boolean.valueOf(false);
        this.localUseTemporaryTable = false;
        this.oppUseTemporaryTable = false;
        if (this.localUnitIds.size() > 280 && !this.localUnitGroup.booleanValue()) {
            this.localUseTemporaryTable = true;
        }
        if (this.oppUnitIds.size() > 280 && !this.oppUnitGroup.booleanValue()) {
            this.oppUseTemporaryTable = true;
        }
        this.insertTempStr();
    }

    private void insertTempStr() {
        if (this.localUseTemporaryTable.booleanValue()) {
            this.localGroupId = UUIDUtils.newUUIDStr();
            IdTemporaryTableUtils.insertTempStr((String)this.localGroupId, this.localUnitIds);
        } else {
            this.localGroupId = null;
        }
        if (this.oppUseTemporaryTable.booleanValue()) {
            this.oppGroupId = UUIDUtils.newUUIDStr();
            IdTemporaryTableUtils.insertTempStr((String)this.oppGroupId, this.oppUnitIds);
        } else {
            this.oppGroupId = null;
        }
    }

    public StringBuilder allVchrSQL() {
        StringBuilder sql = new StringBuilder();
        sql.append("\tselect item.*                                                                         \n");
        sql.append("\t  from GC_RELATED_ITEM  item                                                                 \n");
        this.appendUnitCondition(sql);
        sql.append("\t    and item.acctYear = " + this.condition.getAcctYear() + "                                           \n");
        sql.append("\t    and item.acctPeriod = " + this.condition.getAcctPeriod() + "                                       \n");
        this.appendCommonCondition(sql);
        sql.append("\t   order by item.createTime\t\t\t\t\t\t\t                                          \n");
        return sql;
    }

    public StringBuilder allItemCountSQL() {
        StringBuilder sql = new StringBuilder();
        sql.append("\tselect count(1)  totalNum from GC_RELATED_ITEM  item                                       \n");
        this.appendUnitCondition(sql);
        sql.append("\t   and item.acctYear = " + this.condition.getAcctYear() + "                                            \n");
        sql.append("\t   and item.acctPeriod = " + this.condition.getAcctPeriod() + "                                        \n");
        this.appendCommonCondition(sql);
        return sql;
    }

    public StringBuilder uncheckVchrSQL(boolean isLocal) {
        StringBuilder sql = new StringBuilder();
        sql.append("\tselect item.*                                                                         \n");
        sql.append("\t  from GC_RELATED_ITEM  item                                                                 \n");
        if (isLocal) {
            this.appendUnitCondition(sql);
        } else {
            this.appendOppUnitCondition(sql);
        }
        sql.append("\t    and item.acctYear = " + this.condition.getAcctYear() + "                                           \n");
        sql.append("\t    and item.acctPeriod = " + this.condition.getAcctPeriod() + "                                       \n");
        sql.append("\t    and item.chkState = '" + CheckStateEnum.UNCHECKED + "'                                         \n");
        this.appendCommonCondition(sql);
        sql.append("\t   order by item.createTime\t\t\t\t\t\t\t                                          \n");
        return sql;
    }

    public StringBuilder uncheckVchrCountSQL(boolean isLocal) {
        StringBuilder sql = new StringBuilder();
        sql.append("\tselect count(1)  totalNum from GC_RELATED_ITEM  item                                       \n");
        if (isLocal) {
            this.appendUnitCondition(sql);
        } else {
            this.appendOppUnitCondition(sql);
        }
        sql.append("\t   and item.acctYear = " + this.condition.getAcctYear() + "                                            \n");
        sql.append("\t   and item.acctPeriod = " + this.condition.getAcctPeriod() + "                                        \n");
        sql.append("\t   and item.chkState = '" + CheckStateEnum.UNCHECKED + "'                                          \n");
        this.appendCommonCondition(sql);
        return sql;
    }

    public StringBuilder checkedVchrSQL() {
        StringBuilder sql = new StringBuilder();
        sql.append("\tselect item.*                                                                   \n");
        sql.append("\t  from GC_RELATED_ITEM  item                                                                 \n");
        this.appendUnitCondition(sql);
        sql.append("\t    and item.acctYear = " + this.condition.getAcctYear() + "                                           \n");
        sql.append("\t    and item.acctPeriod = " + this.condition.getAcctPeriod() + "                                       \n");
        sql.append("\t    and item.chkState <> '" + CheckStateEnum.UNCHECKED + "'                                          \n");
        this.appendCommonCondition(sql);
        sql.append("\t   order by item.createTime\t\t\t\t\t\t\t                                          \n");
        return sql;
    }

    public StringBuilder checkedVchrCountSQL() {
        StringBuilder sql = new StringBuilder();
        sql.append("\tselect count(1)  totalNum from GC_RELATED_ITEM  item                                       \n");
        this.appendUnitCondition(sql);
        sql.append("\t   and item.acctYear = " + this.condition.getAcctYear() + "                                            \n");
        sql.append("\t   and item.acctPeriod = " + this.condition.getAcctPeriod() + "                                        \n");
        sql.append("\t   and item.chkState <> '" + CheckStateEnum.UNCHECKED + "'                                           \n");
        this.appendCommonCondition(sql);
        return sql;
    }

    private void appendUnitCondition(StringBuilder sql) {
        if (this.localUnitGroup.booleanValue() && this.oppUnitGroup.booleanValue()) {
            sql.append("       where 1 = 1 \n");
            return;
        }
        if (this.localUseTemporaryTable.booleanValue()) {
            sql.append("        join GC_IDTEMPORARY  bfTemp \n");
            sql.append("\t      on bfTemp.GROUP_ID = '" + this.localGroupId + "' \n");
            sql.append("         and bfTemp.tbCode = item.unitId \n");
        }
        if (this.oppUseTemporaryTable.booleanValue()) {
            sql.append("        join GC_IDTEMPORARY  dfTemp                                                  \n");
            sql.append("\t      on dfTemp.GROUP_ID = '" + this.oppGroupId + "' \n");
            sql.append("         and dfTemp.tbCode = item.oppUnitId \n");
        }
        sql.append("       where 1 = 1                                                                          \n");
        if (!this.localUseTemporaryTable.booleanValue() && !this.localUnitGroup.booleanValue()) {
            sql.append("     and " + SqlUtils.getConditionOfIdsUseOr((Collection)this.condition.getUnitIds(), (String)"item.unitId"));
        }
        if (!this.oppUseTemporaryTable.booleanValue() && !this.oppUnitGroup.booleanValue()) {
            sql.append("     and " + SqlUtils.getConditionOfIdsUseOr((Collection)this.condition.getOppUnitIds(), (String)"item.oppUnitId"));
        }
    }

    private void appendOppUnitCondition(StringBuilder sql) {
        if (this.relation == InclusionRelationEnum.EQUAL) {
            sql.append("       where 1 <> 1                                                                          \n");
            return;
        }
        if (this.localUseTemporaryTable.booleanValue()) {
            sql.append("        join GC_IDTEMPORARY  dfTemp           \n");
            sql.append("\t      on dfTemp.GROUP_ID = '" + this.localGroupId + "' \n");
            sql.append("         and dfTemp.tbCode = item.oppUnitId     \n");
            sql.append("         and  not exists ( select 1 from GC_IDTEMPORARY temp  where temp.GROUP_ID = '" + this.localGroupId + "' and   temp.tbCode = item.unitId  )     \n");
        }
        sql.append("       where 1 = 1                                                                          \n");
        if (!this.localUseTemporaryTable.booleanValue()) {
            sql.append("     and " + SqlUtils.getConditionOfIdsUseOr(this.localUnitIds, (String)"item.oppUnitId"));
            sql.append("     and " + SqlUtils.getConditionOfIdsUseOr(this.localUnitIds, (String)"item.unitId", (SqlUtils.Relation)SqlUtils.Relation.NEGATIVE));
        }
    }

    private void appendCommonCondition(StringBuilder sql) {
        String currencyCode;
        if (this.condition.getSubjectCodes() != null && this.condition.getSubjectCodes().size() > 0) {
            sql.append(" and ").append(SqlUtils.getConditionOfMulStrUseOr((Collection)this.condition.getSubjectCodes(), (String)"item.subjectCode"));
        }
        if (!StringUtils.isEmpty(this.condition.getGcNumber())) {
            sql.append(" and item.gcNumber = '").append(this.condition.getGcNumber()).append("'  \n");
        }
        if (!StringUtils.isEmpty(this.condition.getVchrType())) {
            sql.append(" and item.vchrType = '").append(this.condition.getVchrType()).append("'  \n");
        }
        if (!StringUtils.isEmpty(this.condition.getVchrNum())) {
            sql.append(" and item.vchrNum in (").append(this.getStringForSqlInCondition(this.condition.getVchrNum())).append(") \n");
        }
        String string = currencyCode = StringUtils.isEmpty(this.condition.getCurrencyCode()) ? "CNY" : this.condition.getCurrencyCode();
        if (this.condition.getDebitCNYFrom() != null) {
            sql.append(" and item.debit").append(currencyCode).append(" >=").append(this.condition.getDebitCNYFrom()).append(" \n");
        }
        if (this.condition.getDebitCNYTo() != null) {
            sql.append(" and item.debit").append(currencyCode).append(" <=").append(this.condition.getDebitCNYTo()).append(" \n");
        }
        if (this.condition.getCreditCNYFrom() != null) {
            sql.append(" and item.credit").append(currencyCode).append(" >=").append(this.condition.getCreditCNYFrom()).append(" \n");
        }
        if (this.condition.getCreditCNYTo() != null) {
            sql.append(" and item.credit").append(currencyCode).append(" <=").append(this.condition.getCreditCNYTo()).append(" \n");
        }
        sql.append(" and item.INPUTWAY = 'BATCHINPUT'  \n");
        if (!StringUtils.isEmpty(this.condition.getDigest())) {
            sql.append(" and item.digest contains ").append(this.condition.getDigest()).append("  \n");
        }
        if (!StringUtils.isEmpty(this.condition.getCreateUser())) {
            sql.append(" and item.createUser in (").append(this.getStringForSqlInCondition(this.condition.getCreateUser())).append(") \n");
        }
    }

    private String getStringForSqlInCondition(String str) {
        if (StringUtils.isEmpty(str)) {
            return "";
        }
        StringBuilder builder = new StringBuilder();
        String[] strArr = null;
        strArr = str.split("[,\uff0c]");
        for (int i = 0; i < strArr.length; ++i) {
            builder.append("'" + strArr[i] + "',");
        }
        return builder.substring(0, builder.length() - 1);
    }

    public String getLocalGroupId() {
        return this.localGroupId;
    }

    public String getOppGroupId() {
        return this.oppGroupId;
    }
}

