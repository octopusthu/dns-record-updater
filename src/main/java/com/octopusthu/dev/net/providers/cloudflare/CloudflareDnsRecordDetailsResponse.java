package com.octopusthu.dev.net.providers.cloudflare;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * @author ZHANG Yu
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CloudflareDnsRecordDetailsResponse extends AbstractCloudflareResponse implements Serializable {
    protected Result result;

    @Data
    public static class Result {
        protected String id;
        protected String type;
        protected String name;
        protected String content;
        protected Boolean proxiable;
        protected Boolean proxied;
        protected int ttl;
        protected Boolean locked;
        protected String zone_id;
        protected String zone_name;
        protected ZonedDateTime created_on;
        protected ZonedDateTime modified_on;
    }
}
