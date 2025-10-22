/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.entity.engine.result.EntityResultSet
 */
package com.jiuqi.nr.calibre2.internal.adapter;

import com.jiuqi.nr.calibre2.ICalibreDataService;
import com.jiuqi.nr.calibre2.ICalibreDefineService;
import com.jiuqi.nr.calibre2.common.CalibreDataOption;
import com.jiuqi.nr.calibre2.common.Result;
import com.jiuqi.nr.calibre2.domain.CalibreDataDO;
import com.jiuqi.nr.calibre2.domain.CalibreDataDTO;
import com.jiuqi.nr.calibre2.domain.CalibreDefineDTO;
import com.jiuqi.nr.entity.engine.result.EntityResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.util.StringUtils;

public class CalibreResultSet
extends EntityResultSet {
    protected List<CalibreDataDTO> list;
    protected String tableCode;
    protected List<String> keys;
    protected ICalibreDataService calibreDataService;
    protected ICalibreDefineService calibreDefineService;
    protected Boolean isTree;
    protected Set<String> hasChildrenKey = new HashSet<String>();
    protected Set<String> leafChildrenKey = new HashSet<String>();

    public CalibreResultSet(String tableCode, List<CalibreDataDTO> list, ICalibreDataService calibreDataService, ICalibreDefineService calibreDefineService) {
        super(list.size());
        this.list = list;
        this.tableCode = tableCode;
        this.calibreDataService = calibreDataService;
        this.calibreDefineService = calibreDefineService;
    }

    public List<String> getAllKeys() {
        if (this.keys != null) {
            return this.keys;
        }
        return this.list.stream().filter(Objects::nonNull).map(CalibreDataDO::getCode).collect(Collectors.toList());
    }

    protected Object getColumnObject(int index, String columnCode) {
        CalibreDataDTO calibreDataDTO = this.list.get(index);
        if (calibreDataDTO == null) {
            return null;
        }
        return calibreDataDTO.getValue(columnCode);
    }

    protected String getKey(int index) {
        CalibreDataDTO calibreDataDTO = this.list.get(index);
        if (calibreDataDTO == null) {
            return null;
        }
        return calibreDataDTO.getCode();
    }

    protected String getCode(int index) {
        CalibreDataDTO calibreDataDTO = this.list.get(index);
        if (calibreDataDTO == null) {
            return null;
        }
        return calibreDataDTO.getCode();
    }

    protected String getTitle(int index) {
        CalibreDataDTO calibreDataDTO = this.list.get(index);
        if (calibreDataDTO == null) {
            return null;
        }
        return calibreDataDTO.getName();
    }

    protected String getParent(int index) {
        CalibreDataDTO calibreDataDTO = this.list.get(index);
        if (calibreDataDTO == null) {
            return null;
        }
        return calibreDataDTO.getParent();
    }

    protected Object getOrder(int index) {
        CalibreDataDTO calibreDataDTO = this.list.get(index);
        if (calibreDataDTO == null) {
            return null;
        }
        return calibreDataDTO.getOrder();
    }

    protected String[] getParents(int index) {
        CalibreDataDTO calibreDataDTO = this.list.get(index);
        if (calibreDataDTO == null) {
            return null;
        }
        ArrayList<String> path = new ArrayList<String>();
        this.getParentPath(path, calibreDataDTO.getCode());
        Collections.reverse(path);
        return path.toArray(new String[0]);
    }

    private void getParentPath(List<String> parent, String code) {
        String queryParent = this.getParent(code);
        if (StringUtils.hasText(queryParent)) {
            parent.add(queryParent);
            this.getParentPath(parent, queryParent);
        }
    }

    protected String getParent(String code) {
        CalibreDataDTO calibreDataDTO = new CalibreDataDTO();
        calibreDataDTO.setCalibreCode(this.tableCode);
        calibreDataDTO.setCode(code);
        Result<List<CalibreDataDTO>> list = this.calibreDataService.list(calibreDataDTO);
        List<CalibreDataDTO> data = list.getData();
        if (data == null) {
            return null;
        }
        return data.get(0).getParent();
    }

    public List<CalibreDataDTO> getList() {
        return this.list;
    }

    public String getTableCode() {
        return this.tableCode;
    }

    public ICalibreDataService getCalibreDataService() {
        return this.calibreDataService;
    }

    public ICalibreDefineService getCalibreDefineService() {
        return this.calibreDefineService;
    }

    public int append(EntityResultSet rs) {
        if (rs instanceof CalibreResultSet) {
            CalibreResultSet calibreResultSet = (CalibreResultSet)rs;
            List<CalibreDataDTO> calibreList = calibreResultSet.getList();
            if (calibreList != null) {
                this.list.addAll(calibreList);
            }
            return this.list == null ? 0 : this.list.size();
        }
        return -1;
    }

    public boolean hasChildren(int index) {
        if (!this.isTree()) {
            return true;
        }
        String code = this.list.get(index).getCode();
        if (this.hasChildrenKey.contains(code)) {
            return true;
        }
        if (this.leafChildrenKey.contains(code)) {
            return false;
        }
        boolean hasChildren = false;
        if (this.hasChildren(code)) {
            this.hasChildrenKey.add(code);
            hasChildren = true;
        } else {
            this.leafChildrenKey.add(code);
        }
        return hasChildren;
    }

    public boolean isLeaf(int index) {
        if (!this.isTree()) {
            return true;
        }
        String code = this.list.get(index).getCode();
        if (this.hasChildrenKey.contains(code)) {
            return false;
        }
        if (this.leafChildrenKey.contains(code)) {
            return true;
        }
        boolean isLeaf = false;
        if (this.hasChildren(code)) {
            this.hasChildrenKey.add(code);
        } else {
            this.leafChildrenKey.add(code);
            isLeaf = true;
        }
        return isLeaf;
    }

    private boolean isTree() {
        if (this.isTree == null) {
            CalibreDefineDTO defineDTO = new CalibreDefineDTO();
            defineDTO.setCode(this.tableCode);
            Result<CalibreDefineDTO> calibreDefine = this.calibreDefineService.get(defineDTO);
            if (calibreDefine.getCode() == 1) {
                this.isTree = calibreDefine.getData().getStructType() == 1;
            }
        }
        return this.isTree;
    }

    private boolean hasChildren(String code) {
        CalibreDataDTO calibreDataDTO = new CalibreDataDTO();
        calibreDataDTO.setCalibreCode(this.tableCode);
        calibreDataDTO.setDataTreeType(CalibreDataOption.DataTreeType.DIRECT_CHILDREN);
        calibreDataDTO.setCode(code);
        Result<List<CalibreDataDTO>> list = this.calibreDataService.list(calibreDataDTO);
        return !list.getData().isEmpty();
    }
}

