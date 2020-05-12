package com.octopusthu.dev.net.dnsrecordupdater;

import com.octopusthu.dev.net.NetworkingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;

import java.net.InetAddress;

/**
 * @author ZHANG Yu
 */
@Slf4j
public class DnsRecordUpdaterTasks {

    private final NetworkingService networkingService;

    public DnsRecordUpdaterTasks(NetworkingService networkingService) {
        this.networkingService = networkingService;
    }

    @Scheduled(cron = "${dns-record-updater.cron}")
    public void updateDnsRecord() {

        InetAddress externalIp;
        try {
            externalIp = networkingService.getExternalIp();
        } catch (Exception e) {
            log.warn("Cannot get external IP address!", e);
            return;
        }
        log.info("External IP is: " + externalIp.getHostAddress());

    }

}
