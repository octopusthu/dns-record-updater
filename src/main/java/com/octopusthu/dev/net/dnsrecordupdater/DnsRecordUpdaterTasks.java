package com.octopusthu.dev.net.dnsrecordupdater;

import com.octopusthu.dev.net.NetworkingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Scheduled;

import java.net.InetAddress;
import java.time.Duration;

/**
 * @author ZHANG Yu
 */
@Slf4j
public class DnsRecordUpdaterTasks {

    private final Environment env;
    private final NetworkingService networkingService;

    public DnsRecordUpdaterTasks(Environment env, NetworkingService networkingService) {
        this.env = env;
        this.networkingService = networkingService;
    }

    @Scheduled(cron = "${dns-record-updater.tasks.cron}")
    public void updateDnsRecord() {
        if (!"true".equals(env.getProperty("dns-record-updater.tasks.enabled"))) {
            log.info("Scheduled tasks are disabled.");
            return;
        }

        InetAddress externalIp;
        try {
            externalIp = networkingService.getExternalIp().block(Duration.ofSeconds(5));
            assert externalIp != null;
        } catch (Exception e) {
            log.warn("Cannot get external IP address!", e);
            return;
        }
        log.info("External IP is: " + externalIp.getHostAddress());

    }

}
