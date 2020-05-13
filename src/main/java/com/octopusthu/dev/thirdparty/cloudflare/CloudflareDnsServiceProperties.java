package com.octopusthu.dev.thirdparty.cloudflare;

import lombok.Getter;
import lombok.Setter;

/**
 * @author ZHANG Yu
 */
@Getter
@Setter
public class CloudflareDnsServiceProperties {
    private String bearerToken;
    private String zoneId;
    private String recordId;
    private String recordType;
    private String recordName;
    private String recordTtl;
}
