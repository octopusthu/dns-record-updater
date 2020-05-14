package com.octopusthu.dev.thirdparty.cloudflare;

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
