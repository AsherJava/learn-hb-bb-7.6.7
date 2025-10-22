/*
 * Decompiled with CFR 0.152.
 */
package nr.single.para.compare.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CompareEnumItem
implements Serializable {
    private static final long serialVersionUID = 1L;
    private String key;
    private String code;
    private String levelCode;
    private String parentLevelCode;
    private String title;
    private String parentCode;
    private Integer level;
    private Map<String, String> datas;
    private List<CompareEnumItem> childItems;
    private Map<String, List<CompareEnumItem>> titleItems;
    private Map<String, List<CompareEnumItem>> codeItems;

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLevelCode() {
        return this.levelCode;
    }

    public void setLevelCode(String levelCode) {
        this.levelCode = levelCode;
    }

    public String getParentLevelCode() {
        return this.parentLevelCode;
    }

    public void setParentLevelCode(String parentLevelCode) {
        this.parentLevelCode = parentLevelCode;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getParentCode() {
        return this.parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public Integer getLevel() {
        return this.level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Map<String, String> getDatas() {
        return this.datas;
    }

    public void setDatas(Map<String, String> datas) {
        this.datas = datas;
    }

    public List<CompareEnumItem> getChildItems() {
        if (this.childItems == null) {
            this.childItems = new ArrayList<CompareEnumItem>();
        }
        return this.childItems;
    }

    public Map<String, List<CompareEnumItem>> getTitleItems() {
        if (this.titleItems == null) {
            this.titleItems = new HashMap<String, List<CompareEnumItem>>();
        }
        return this.titleItems;
    }

    public Map<String, List<CompareEnumItem>> getCodeItems() {
        return this.codeItems;
    }

    public void addChildItem(CompareEnumItem child) {
        this.getChildItems().add(child);
        List list1 = this.getTitleItems().computeIfAbsent(child.getTitle(), f -> new ArrayList());
        list1.add(child);
        List list2 = this.getTitleItems().computeIfAbsent(child.getCode(), f -> new ArrayList());
        list2.add(child);
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}

