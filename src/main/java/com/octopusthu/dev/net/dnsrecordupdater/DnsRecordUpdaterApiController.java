package com.octopusthu.dev.net.dnsrecordupdater;

import com.octopusthu.dev.net.NetworkingService;
import com.octopusthu.dev.thirdparty.cloudflare.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.net.InetAddress;

/**
 * @author ZHANG Yu
 */
@RestController
@Slf4j
public class DnsRecordUpdaterApiController {
    private final NetworkingService networkingService;
    private final CloudflareDnsService cloudflareDnsService;
    private final CloudflareDnsServiceProperties cloudflareDnsServiceProperties;

    public DnsRecordUpdaterApiController(NetworkingService networkingService, CloudflareDnsService cloudflareDnsService, CloudflareDnsServiceProperties cloudflareDnsServiceProperties) {
        this.networkingService = networkingService;
        this.cloudflareDnsService = cloudflareDnsService;
        this.cloudflareDnsServiceProperties = cloudflareDnsServiceProperties;
    }

    @GetMapping("/api/get-external-ip")
    public Mono<InetAddress> getExternalIp() throws Exception {
        return networkingService.getExternalIp();
    }

    @GetMapping("/api/dns-record-details")
    public Mono<CloudflareDnsRecordDetailsResponse> dnsRecordDetails() {
        return cloudflareDnsService.dnsRecordDetails(
                cloudflareDnsServiceProperties.getBearerToken(),
                cloudflareDnsServiceProperties.getZoneId(),
                cloudflareDnsServiceProperties.getRecordId());
    }

    @GetMapping("/api/update-dns-record")
    public Mono<CloudflareUpdateDnsRecordResponse> updateDnsRecord(@RequestParam String content) {
        return cloudflareDnsService.updateDnsRecord(
                cloudflareDnsServiceProperties.getBearerToken(),
                cloudflareDnsServiceProperties.getZoneId(),
                cloudflareDnsServiceProperties.getRecordId(),
                cloudflareDnsServiceProperties.getRecordType(),
                cloudflareDnsServiceProperties.getRecordName(),
                content,
                cloudflareDnsServiceProperties.getRecordTtl(),
                false);
    }

}
