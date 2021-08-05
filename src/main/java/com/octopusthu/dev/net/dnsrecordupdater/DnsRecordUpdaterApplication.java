package com.octopusthu.dev.net.dnsrecordupdater;

import com.octopusthu.dev.net.InetAddressService;
import com.octopusthu.dev.net.providers.cloudflare.CloudflareDnsService;
import com.octopusthu.dev.net.providers.cloudflare.CloudflareDnsServiceProperties;
import com.octopusthu.dev.net.providers.ipify.IpifyInetAddressService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * DnsRecordUpdaterApplication
 *
 * @author ZHANG Yu
 */
@SpringBootApplication
@EnableScheduling
public class DnsRecordUpdaterApplication {

    private final WebClient.Builder webClientBuilder;

    public static void main(String[] args) {
        SpringApplication.run(DnsRecordUpdaterApplication.class, args);
    }

    public DnsRecordUpdaterApplication(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    @Bean
    DnsRecordUpdaterTasks dnsRecordUpdaterTasks() {
        return new DnsRecordUpdaterTasks(dnsRecordUpdaterProperties(), dnsRecordUpdaterService());
    }

    @Bean
    DnsRecordUpdaterService dnsRecordUpdaterService() {
        return new DnsRecordUpdaterService(dnsRecordUpdaterProperties(), networkingService(), cloudflareDnsService(), cloudflareDnsServiceProperties());
    }

    @ConfigurationProperties("dns-record-updater")
    @Bean
    DnsRecordUpdaterProperties dnsRecordUpdaterProperties() {
        return new DnsRecordUpdaterProperties();
    }

    @Bean
    InetAddressService networkingService() {
        return new IpifyInetAddressService(this.webClientBuilder);
    }

    @Bean
    CloudflareDnsService cloudflareDnsService() {
        return new CloudflareDnsService(this.webClientBuilder);
    }

    @ConfigurationProperties("dns-record-updater.cloudflare")
    @Bean
    CloudflareDnsServiceProperties cloudflareDnsServiceProperties() {
        return new CloudflareDnsServiceProperties();
    }

}
