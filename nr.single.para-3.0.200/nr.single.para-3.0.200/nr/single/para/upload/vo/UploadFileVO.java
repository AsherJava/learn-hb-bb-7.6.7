/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 */
package nr.single.para.upload.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import nr.single.para.upload.domain.UploadFileDTO;

@JsonInclude(value=JsonInclude.Include.NON_NULL)
public class UploadFileVO
extends UploadFileDTO {
}

