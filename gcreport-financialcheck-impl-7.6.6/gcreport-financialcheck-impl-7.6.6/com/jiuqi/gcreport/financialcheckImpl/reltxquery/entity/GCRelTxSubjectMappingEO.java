/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn$DBType
 *  com.jiuqi.gcreport.definition.impl.anno.DBIndex
 *  com.jiuqi.gcreport.definition.impl.anno.DBTable
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 *  com.jiuqi.gcreport.definition.impl.enums.TemporaryTableTypeEnum
 *  com.jiuqi.nvwa.definition.common.TableModelKind
 */
package com.jiuqi.gcreport.financialcheckImpl.reltxquery.entity;

import com.jiuqi.gcreport.definition.impl.anno.DBColumn;
import com.jiuqi.gcreport.definition.impl.anno.DBIndex;
import com.jiuqi.gcreport.definition.impl.anno.DBTable;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import com.jiuqi.gcreport.definition.impl.enums.TemporaryTableTypeEnum;
import com.jiuqi.nvwa.definition.common.TableModelKind;

@DBTable(name="GC_RELTX_SUBJECT_MAPPING", title="\u65b9\u6848\u79d1\u76ee\u6620\u5c04\u4e34\u65f6\u8868", indexs={@DBIndex(name="IDX_RELTX_SUBMAP_SUBJECTCODE", columnsFields={"SUBJECTCODE"})}, dataSource="jiuqi.gcreport.mdd.datasource", primaryRequired=false, kind=TableModelKind.SYSTEM_EXTEND, tempTableType=TemporaryTableTypeEnum.TRANSACTION)
public class GCRelTxSubjectMappingEO
extends DefaultTableEntity {
    public static final String TABLENAME = "GC_RELTX_SUBJECT_MAPPING";
    @DBColumn(nameInDB="SUBJECTCODE", title="\u79d1\u76ee", dbType=DBColumn.DBType.NVarchar, length=200, isRequired=true)
    private String subjectCode;
    @DBColumn(nameInDB="CHECkPROJECTDIRECTION", title="\u5bf9\u8d26\u9879\u76ee\u65b9\u5411", dbType=DBColumn.DBType.Int)
    private Integer checkProjectDirection;
    @DBColumn(nameInDB="BUSINESSROLE", title="\u4e1a\u52a1\u89d2\u8272", dbType=DBColumn.DBType.Int)
    private Integer businessRole;
    @DBColumn(nameInDB="CHECKATTRIBUTE", title="\u5bf9\u8d26\u4e1a\u52a1", dbType=DBColumn.DBType.Varchar, length=50)
    private String checkAttribute;
    @DBColumn(nameInDB="CHECKPROJECT", title="\u5bf9\u8d26\u9879\u76ee", dbType=DBColumn.DBType.NVarchar, length=36)
    private String checkProject;

    public String getSubjectCode() {
        return this.subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public Integer getCheckProjectDirection() {
        return this.checkProjectDirection;
    }

    public void setCheckProjectDirection(Integer checkProjectDirection) {
        this.checkProjectDirection = checkProjectDirection;
    }

    public Integer getBusinessRole() {
        return this.businessRole;
    }

    public void setBusinessRole(Integer businessRole) {
        this.businessRole = businessRole;
    }

    public String getCheckAttribute() {
        return this.checkAttribute;
    }

    public void setCheckAttribute(String checkAttribute) {
        this.checkAttribute = checkAttribute;
    }

    public String getCheckProject() {
        return this.checkProject;
    }

    public void setCheckProject(String checkProject) {
        this.checkProject = checkProject;
    }

    public String toString() {
        return "GCRelTxSubjectMappingEO{subjectCode='" + this.subjectCode + '\'' + ", checkProjectDirection=" + this.checkProjectDirection + ", businessRole=" + this.businessRole + ", checkAttribute='" + this.checkAttribute + '\'' + ", checkProject='" + this.checkProject + '\'' + '}';
    }
}

