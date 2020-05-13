package com.octopusthu.dev.net.dnsrecordupdater;

import lombok.Getter;
import lombok.Setter;

/**
 * @author ZHANG Yu
 */
@Getter
@Setter
public class DnsRecordUpdaterProperties {
    private int blockInSeconds;

    private final Tasks tasks = new Tasks();

    @Getter
    @Setter
    public static class Tasks {
        private boolean enabled;
    }
}
