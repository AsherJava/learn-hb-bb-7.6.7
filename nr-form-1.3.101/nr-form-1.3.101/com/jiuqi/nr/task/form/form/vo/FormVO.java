/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.common.FormType
 */
package com.jiuqi.nr.task.form.form.vo;

import com.jiuqi.nr.definition.common.FormType;
import com.jiuqi.nr.task.form.form.dto.FormDTO;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.BeanUtils;

public class FormVO
extends FormDTO {
    private int formTypeInt;
    private boolean isFirst;
    private boolean isLast;
    private String formTypeString;
    private boolean deleteRefEntity;

    public void setFormTypeByInt() {
        for (FormType value : FormType.values()) {
            if (value.getValue() != this.formTypeInt) continue;
            this.setFormType(value);
        }
    }

    public int getFormTypeInt() {
        return this.formTypeInt;
    }

    public void setFormTypeInt(int formTypeInt) {
        this.formTypeInt = formTypeInt;
    }

    public boolean isFirst() {
        return this.isFirst;
    }

    public void setFirst(boolean first) {
        this.isFirst = first;
    }

    public boolean isLast() {
        return this.isLast;
    }

    public void setLast(boolean last) {
        this.isLast = last;
    }

    public String getFormTypeString() {
        return this.formTypeString;
    }

    public void setFormTypeString(String formTypeString) {
        this.formTypeString = formTypeString;
    }

    public static FormVO getInstance(FormDTO Dto) {
        FormVO vo = new FormVO();
        BeanUtils.copyProperties(Dto, vo);
        return vo;
    }

    public static List<FormVO> getListInstance(List<FormDTO> dataDOS) {
        ArrayList<FormVO> vos = new ArrayList<FormVO>(dataDOS.size());
        for (FormDTO dataDO : dataDOS) {
            FormVO vo = new FormVO();
            BeanUtils.copyProperties(dataDO, vo);
            vos.add(vo);
        }
        return vos;
    }

    public boolean isDeleteRefEntity() {
        return this.deleteRefEntity;
    }

    public void setDeleteRefEntity(boolean deleteRefEntity) {
        this.deleteRefEntity = deleteRefEntity;
    }
}

