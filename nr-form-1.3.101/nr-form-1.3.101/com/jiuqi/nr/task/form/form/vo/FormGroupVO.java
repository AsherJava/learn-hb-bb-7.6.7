/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.task.form.form.vo;

import com.jiuqi.nr.task.form.form.dto.FormGroupDTO;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.BeanUtils;

public class FormGroupVO
extends FormGroupDTO {
    private boolean isFirst;
    private boolean isLast;

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

    public static FormGroupVO getInstance(FormGroupDTO Dto) {
        FormGroupVO vo = new FormGroupVO();
        BeanUtils.copyProperties(Dto, vo);
        return vo;
    }

    public static List<FormGroupVO> getListInstance(List<FormGroupDTO> dataDOS) {
        ArrayList<FormGroupVO> vos = new ArrayList<FormGroupVO>(dataDOS.size());
        for (FormGroupDTO dataDO : dataDOS) {
            FormGroupVO vo = new FormGroupVO();
            BeanUtils.copyProperties(dataDO, vo);
            vos.add(vo);
        }
        return vos;
    }
}

