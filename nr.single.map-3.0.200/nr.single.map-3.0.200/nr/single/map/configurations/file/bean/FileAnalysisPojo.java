/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.file.FileInfo
 */
package nr.single.map.configurations.file.bean;

import com.jiuqi.nr.file.FileInfo;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import nr.single.map.configurations.bean.RuleMap;
import nr.single.map.configurations.bean.UnitMapping;
import nr.single.map.configurations.bean.ZbMapping;
import nr.single.map.configurations.internal.bean.IllegalData;
import nr.single.map.data.facade.SingleFileFormulaItem;

public class FileAnalysisPojo
implements Serializable {
    private String mappingKey;
    private String name;
    private FileInfo info;
    private UnitMapping entity;
    private List<SingleFileFormulaItem> formula;
    private List<ZbMapping> zb;
    private List<RuleMap> mapRule;
    private IllegalData errorData;
    private int count;

    public FileAnalysisPojo() {
    }

    public FileAnalysisPojo(String mappingKey, String name, FileInfo info) {
        this.mappingKey = mappingKey;
        this.name = name;
        this.info = info;
    }

    public FileAnalysisPojo(String mappingKey, String name, IllegalData errorData) {
        this.mappingKey = mappingKey;
        this.name = name;
        this.errorData = errorData;
    }

    public FileAnalysisPojo(String mappingKey, FileInfo info, UnitMapping entity, List<SingleFileFormulaItem> formula, List<ZbMapping> zb, List<RuleMap> mapRule) {
        this.mappingKey = mappingKey;
        this.info = info;
        if (entity == null) {
            entity = new UnitMapping();
        }
        this.entity = entity;
        if (formula == null) {
            formula = new ArrayList<SingleFileFormulaItem>();
        }
        this.formula = formula;
        if (zb == null) {
            zb = new ArrayList<ZbMapping>();
        }
        this.zb = zb;
        if (mapRule == null) {
            mapRule = new ArrayList<RuleMap>();
        }
        this.mapRule = mapRule;
    }

    public String getName() {
        return this.name == null ? "\u6620\u5c04\u65b9\u6848" : this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMappingKey() {
        return this.mappingKey;
    }

    public void setMappingKey(String mappingKey) {
        this.mappingKey = mappingKey;
    }

    public FileInfo getInfo() {
        return this.info;
    }

    public void setInfo(FileInfo info) {
        this.info = info;
    }

    public UnitMapping getEntity() {
        return this.entity;
    }

    public void setEntity(UnitMapping entity) {
        this.entity = entity;
    }

    public List<SingleFileFormulaItem> getFormula() {
        return this.formula;
    }

    public void setFormula(List<SingleFileFormulaItem> formula) {
        this.formula = formula;
    }

    public List<ZbMapping> getZb() {
        return this.zb;
    }

    public void setZb(List<ZbMapping> zb) {
        this.zb = zb;
    }

    public List<RuleMap> getMapRule() {
        return this.mapRule;
    }

    public void setMapRule(List<RuleMap> mapRule) {
        this.mapRule = mapRule;
    }

    public IllegalData getErrorData() {
        return this.errorData;
    }

    public void setErrorData(IllegalData errorData) {
        this.errorData = errorData;
    }

    public int getCount() {
        return this.count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}

