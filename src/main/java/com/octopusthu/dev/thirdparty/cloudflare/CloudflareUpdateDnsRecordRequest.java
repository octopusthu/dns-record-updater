package com.octopusthu.dev.thirdparty.cloudflare;

import lombok.Data;

/**
 * @author ZHANG Yu
 */
@Data
public class CloudflareUpdateDnsRecordRequest {
    private String type;
    private String name;
    private String content;
    private int ttl;
    private boolean proxied;
}
