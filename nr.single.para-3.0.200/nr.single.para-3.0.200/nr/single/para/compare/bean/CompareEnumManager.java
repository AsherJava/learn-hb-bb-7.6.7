/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.StringUtils
 */
package nr.single.para.compare.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nr.single.para.compare.bean.CompareEnumItem;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CompareEnumManager
implements Serializable {
    private static final Logger log = LoggerFactory.getLogger(CompareEnumManager.class);
    private static final long serialVersionUID = 1L;
    private String code;
    private String title;
    private String codeStruct;
    private int codeLen;
    private List<Integer> levelLengths;
    private List<Integer> levelOnlyLens;
    private List<CompareEnumItem> items;
    private List<CompareEnumItem> rootItems;
    private Map<String, List<CompareEnumItem>> rootCodeItems;
    private Map<String, List<CompareEnumItem>> rootTitleItems;
    private Map<Integer, List<CompareEnumItem>> levelItems;
    private Map<String, List<CompareEnumItem>> childItems;
    private Map<String, CompareEnumItem> codeItems;

    public String getCodeStruct() {
        return this.codeStruct;
    }

    public void setCodeStruct(String codeStruct) {
        this.codeStruct = codeStruct;
    }

    public int getCodeLen() {
        return this.codeLen;
    }

    public void setCodeLen(int codeLen) {
        this.codeLen = codeLen;
    }

    public List<Integer> getLevelLengths() {
        if (this.levelLengths == null) {
            this.levelLengths = new ArrayList<Integer>();
        }
        return this.levelLengths;
    }

    public void setLevelLengths(List<Integer> levelLengths) {
        this.levelLengths = levelLengths;
    }

    public List<Integer> getLevelOnlyLens() {
        if (this.levelOnlyLens == null) {
            this.levelOnlyLens = new ArrayList<Integer>();
        }
        return this.levelOnlyLens;
    }

    public void setLevelOnlyLens(List<Integer> levelOnlyLens) {
        this.levelOnlyLens = levelOnlyLens;
    }

    public List<CompareEnumItem> getRootItems() {
        if (this.rootItems == null) {
            this.rootItems = new ArrayList<CompareEnumItem>();
        }
        return this.rootItems;
    }

    public Map<String, List<CompareEnumItem>> getRootCodeItems() {
        if (this.rootCodeItems == null) {
            this.rootCodeItems = new HashMap<String, List<CompareEnumItem>>();
        }
        return this.rootCodeItems;
    }

    public Map<String, List<CompareEnumItem>> getRootTitleItems() {
        if (this.rootTitleItems == null) {
            this.rootTitleItems = new HashMap<String, List<CompareEnumItem>>();
        }
        return this.rootTitleItems;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<CompareEnumItem> getItems() {
        if (this.items == null) {
            this.items = new ArrayList<CompareEnumItem>();
        }
        return this.items;
    }

    public Map<Integer, List<CompareEnumItem>> getLevelItems() {
        if (this.levelItems == null) {
            this.levelItems = new HashMap<Integer, List<CompareEnumItem>>();
        }
        return this.levelItems;
    }

    public List<CompareEnumItem> getItemBysLevel(Integer level) {
        List list = this.getLevelItems().computeIfAbsent(level, f -> new ArrayList());
        return list;
    }

    public Map<String, CompareEnumItem> getCodeItems() {
        if (this.codeItems == null) {
            this.codeItems = new HashMap<String, CompareEnumItem>();
        }
        return this.codeItems;
    }

    public Map<String, List<CompareEnumItem>> getChildItems() {
        if (this.childItems == null) {
            this.childItems = new HashMap<String, List<CompareEnumItem>>();
        }
        return this.childItems;
    }

    public void addItem(CompareEnumItem item) {
        if (this.getCodeItems().containsKey(item.getCode())) {
            log.info("\u679a\u4e3e\u9879\u4ee3\u7801\u5df2\u7ecf\u5b58\u5728\uff1a" + item.getCode());
            return;
        }
        this.getItems().add(item);
        List<CompareEnumItem> list = this.getItemBysLevel(item.getLevel());
        list.add(item);
        if (item.getLevel() == 0) {
            this.getRootItems().add(item);
            List list1 = this.getRootCodeItems().computeIfAbsent(item.getCode(), f -> new ArrayList());
            list1.add(item);
            List list2 = this.getRootTitleItems().computeIfAbsent(item.getTitle(), f -> new ArrayList());
            list2.add(item);
        } else if (StringUtils.isNotEmpty((CharSequence)item.getParentCode())) {
            List list3 = this.getChildItems().computeIfAbsent(item.getParentCode(), f -> new ArrayList());
            list3.add(item);
            if (this.getCodeItems().containsKey(item.getParentCode())) {
                CompareEnumItem parentItem = this.getCodeItems().get(item.getParentCode());
                parentItem.addChildItem(item);
            } else {
                log.info("\u7236\u8282\u70b9\u4e0d\u5b58\u5728:" + item.getParentCode());
            }
        }
        this.getCodeItems().put(item.getCode(), item);
    }

    public CompareEnumItem getItem(String code, String title, String parentCode, int level, String key) {
        CompareEnumItem item = new CompareEnumItem();
        item.setCode(code);
        item.setTitle(title);
        item.setLevel(level);
        String newParentCode = parentCode;
        if (this.levelLengths.size() <= 1) {
            item.setParentCode(newParentCode);
            item.setLevelCode(code);
            item.setParentLevelCode(newParentCode);
        } else if (level == 0) {
            item.setParentCode(null);
        } else if (level > 0) {
            // empty if block
        }
        return item;
    }
}

