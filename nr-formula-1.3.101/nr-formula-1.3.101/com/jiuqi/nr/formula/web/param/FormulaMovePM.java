/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.formula.web.param;

import com.jiuqi.nr.formula.dto.FormulaDTO;
import com.jiuqi.nr.formula.web.param.FormulaSavePM;
import java.util.List;

public class FormulaMovePM
extends FormulaSavePM {
    private Integer moveWay;
    private List<String> keys;
    private FormulaDTO last;
    private FormulaDTO next;

    public Integer getMoveWay() {
        return this.moveWay;
    }

    public void setMoveWay(Integer moveWay) {
        this.moveWay = moveWay;
    }

    public List<String> getKeys() {
        return this.keys;
    }

    public void setKeys(List<String> keys) {
        this.keys = keys;
    }

    public FormulaDTO getLast() {
        return this.last;
    }

    public void setLast(FormulaDTO last) {
        this.last = last;
    }

    public FormulaDTO getNext() {
        return this.next;
    }

    public void setNext(FormulaDTO next) {
        this.next = next;
    }
}

