/*
 * Decompiled with CFR 0.152.
 */
package nr.single.para.compare.internal.defintion;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Objects;
import nr.single.para.compare.definition.ICompareMapField;
import nr.single.para.compare.definition.exception.DBAnno;
import nr.single.para.compare.internal.defintion.CompareInfoDO;
import org.springframework.beans.BeanUtils;

@DBAnno.DBTable(dbTable="NR_SINGLE_COMPARE_MAPFIELD")
public class CompareMapFieldDO
implements ICompareMapField {
    private static final long serialVersionUID = 1L;
    @DBAnno.DBField(dbField="MF_KEY", isPk=true)
    protected String key;
    @DBAnno.DBField(dbField="MF_FIELDKEY")
    protected String fieldKey;
    @DBAnno.DBField(dbField="MF_DATASCHEME_KEY")
    protected String dataSchemeKey;
    @DBAnno.DBField(dbField="MF_MATCHTITLE")
    protected String matchTitle;
    @DBAnno.DBField(dbField="MF_UPDATETIME", tranWith="transTimeStamp", dbType=Timestamp.class, appType=Instant.class, autoDate=true)
    protected Instant updateTime;

    @Override
    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String getFieldKey() {
        return this.fieldKey;
    }

    @Override
    public String getDataSchemeKey() {
        return this.dataSchemeKey;
    }

    @Override
    public String getMatchTitle() {
        return this.matchTitle;
    }

    public void setFieldKey(String fieldKey) {
        this.fieldKey = fieldKey;
    }

    public void setDataSchemeKey(String dataSchemeKey) {
        this.dataSchemeKey = dataSchemeKey;
    }

    public void setMatchTitle(String matchTitle) {
        this.matchTitle = matchTitle;
    }

    @Override
    public Instant getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Instant updateTime) {
        this.updateTime = updateTime;
    }

    public CompareInfoDO clone() {
        try {
            return (CompareInfoDO)super.clone();
        }
        catch (CloneNotSupportedException e) {
            throw new RuntimeException("\u6df1\u5ea6\u590d\u5236\u6570\u636e\u51fa\u9519");
        }
    }

    public String toString() {
        return "CompareMapFieldDO{key='" + this.key + '\'' + ", fieldKey='" + this.fieldKey + '\'' + ", dataSchemeKey='" + this.dataSchemeKey + '\'' + ", matchTitle='" + this.matchTitle + '\'' + ", updateTime=" + this.updateTime + '}';
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        CompareMapFieldDO that = (CompareMapFieldDO)o;
        return Objects.equals(this.key, that.getKey());
    }

    public int hashCode() {
        return this.key != null ? this.key.hashCode() : 0;
    }

    public static CompareMapFieldDO valueOf(CompareMapFieldDO o) {
        if (o == null) {
            return null;
        }
        CompareMapFieldDO t = new CompareMapFieldDO();
        BeanUtils.copyProperties(o, t);
        return t;
    }
}

