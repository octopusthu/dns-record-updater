package com.octopusthu.dev.net.dnsrecordupdater;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * @author ZHANG Yu
 */
@Slf4j
public class DnsRecordUpdaterTasks {
    DnsRecordUpdaterProperties properties;
    private final DnsRecordUpdaterService service;

    public DnsRecordUpdaterTasks(DnsRecordUpdaterProperties properties, DnsRecordUpdaterService service) {
        this.properties = properties;
        this.service = service;
    }

    @Scheduled(cron = "${dns-record-updater.tasks.cron}")
    public void updateDnsRecord() {
        if (!properties.getTasks().isEnabled()) {
            log.debug("Scheduled tasks are disabled.");
            return;
        }

        try {
            service.updateDnsRecord();
        } catch (Exception e) {
            log.warn("Error updating DNS record!", e);
        }
    }

}
