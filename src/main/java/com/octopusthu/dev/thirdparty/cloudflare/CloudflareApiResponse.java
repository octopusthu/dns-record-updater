package com.octopusthu.dev.thirdparty.cloudflare;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author ZHANG Yu
 */
@Data
public class CloudflareApiResponse<T> {
    private boolean success;
    private List<Map<String, String>> errors;
    private T result;
}
