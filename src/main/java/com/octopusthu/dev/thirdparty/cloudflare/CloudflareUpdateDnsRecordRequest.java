package com.octopusthu.dev.thirdparty.cloudflare;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author ZHANG Yu
 */
@Data
@AllArgsConstructor
public class CloudflareUpdateDnsRecordRequest {
    private String type;
    private String name;
    private String content;
    private int ttl;
    private boolean proxied;
}
