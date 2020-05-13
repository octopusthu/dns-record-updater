package com.octopusthu.dev.net.dnsrecordupdater;

import com.octopusthu.dev.net.NetworkingService;
import com.octopusthu.dev.thirdparty.cloudflare.CloudflareApiResponse;
import com.octopusthu.dev.thirdparty.cloudflare.CloudflareDnsService;
import com.octopusthu.dev.thirdparty.cloudflare.CloudflareDnsServiceProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
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
    public Mono<CloudflareApiResponse> dnsRecordDetails() throws Exception {
        return cloudflareDnsService.dnsRecordDetails(
                cloudflareDnsServiceProperties.getBearerToken(),
                cloudflareDnsServiceProperties.getZoneId(),
                cloudflareDnsServiceProperties.getRecordId());
    }

}
