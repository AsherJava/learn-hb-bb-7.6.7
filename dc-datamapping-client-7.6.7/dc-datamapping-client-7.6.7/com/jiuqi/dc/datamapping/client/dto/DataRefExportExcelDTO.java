/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 */
package com.jiuqi.dc.datamapping.client.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jiuqi.dc.datamapping.client.dto.DataRefListDTO;
import java.io.Serializable;

@JsonInclude(value=JsonInclude.Include.NON_NULL)
public class DataRefExportExcelDTO
extends DataRefListDTO
implements Serializable {
    private static final long serialVersionUID = 9074423465687316063L;
}

