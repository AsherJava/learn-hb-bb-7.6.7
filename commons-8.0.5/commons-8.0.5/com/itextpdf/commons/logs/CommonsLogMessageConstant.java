/*
 * Decompiled with CFR 0.152.
 */
package com.itextpdf.commons.logs;

public final class CommonsLogMessageConstant {
    public static final String BASE_64_EXCEPTION = "Exception during base64 encoding or decoding.";
    public static final String INVALID_STATISTICS_NAME = "Statistics name {0} is invalid. Cannot find corresponding statistics aggregator.";
    public static final String LOCAL_FILE_COMPRESSION_FAILED = "Cannot archive files into zip. Exception message: {0}.";
    public static final String RATIO_IS_HIGHLY_SUSPICIOUS = "Ratio between compressed and uncompressed data is highly suspicious, looks like a Zip Bomb Attack. Threshold ratio is {0}.";
    public static final String TOO_MUCH_ENTRIES_IN_ARCHIVE = "Too much entries in this archive, can lead to inodes exhaustion of the system, looks like a Zip Bomb Attack. Threshold number of file entries is {0}.";
    public static final String UNABLE_TO_DESERIALIZE_JSON = "Unable to deserialize json. Exception {0} was thrown with the message: {1}.";
    public static final String UNABLE_TO_SERIALIZE_OBJECT = "Unable to serialize object. Exception {0} was thrown with the message: {1}.";
    public static final String UNCOMPRESSED_DATA_SIZE_IS_TOO_MUCH = "The uncompressed data size is too much for the application resource capacity, looks like a Zip Bomb Attack. Threshold size is {0}.";
    public static final String UNKNOWN_PLACEHOLDER_WAS_IGNORED = "Unknown placeholder {0} was ignored";
    public static final String UNREPORTED_EVENT = "Event for the product {0} with type {1} attempted to be confirmed but it had not been reported yet. Probably appropriate process fail";

    private CommonsLogMessageConstant() {
    }
}

