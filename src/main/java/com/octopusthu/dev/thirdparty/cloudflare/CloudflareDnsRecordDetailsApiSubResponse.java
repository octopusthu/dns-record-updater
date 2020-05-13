package com.octopusthu.dev.thirdparty.cloudflare;

import lombok.Data;

import java.time.ZonedDateTime;

/**
 * @author ZHANG Yu
 */
@Data
public class CloudflareDnsRecordDetailsApiSubResponse {
    private String id;
    private String type;
    private String name;
    private String content;
    private Boolean proxiable;
    private Boolean proxied;
    private int ttl;
    private Boolean locked;
    private String zone_id;
    private String zone_name;
    private ZonedDateTime created_on;
    private ZonedDateTime modified_on;
}
