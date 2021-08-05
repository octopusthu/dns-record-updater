package com.octopusthu.dev.net.providers.cloudflare;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * @author ZHANG Yu
 */
@Data
@AllArgsConstructor
public class CloudflareUpdateDnsRecordRequest implements Serializable {
    private String type;
    private String name;
    private String content;
    private int ttl;
    private boolean proxied;
}
