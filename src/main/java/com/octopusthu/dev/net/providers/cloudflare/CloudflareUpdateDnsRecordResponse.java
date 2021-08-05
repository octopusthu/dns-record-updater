package com.octopusthu.dev.net.providers.cloudflare;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author ZHANG Yu
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CloudflareUpdateDnsRecordResponse extends CloudflareDnsRecordDetailsResponse implements Serializable {
}
