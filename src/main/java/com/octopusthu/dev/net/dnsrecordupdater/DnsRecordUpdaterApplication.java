package com.octopusthu.dev.net.dnsrecordupdater;

import com.octopusthu.dev.net.DefaultNetworkingServiceImpl;
import com.octopusthu.dev.net.NetworkingService;
import com.octopusthu.dev.thirdparty.cloudflare.CloudflareDnsService;
import com.octopusthu.dev.thirdparty.cloudflare.CloudflareDnsServiceProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
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

    private final Environment env;
    private final WebClient.Builder webClientBuilder;

    public static void main(String[] args) {
        SpringApplication.run(DnsRecordUpdaterApplication.class, args);
    }

    public DnsRecordUpdaterApplication(Environment env, WebClient.Builder webClientBuilder) {
        this.env = env;
        this.webClientBuilder = webClientBuilder;
    }

    @Bean
    DnsRecordUpdaterTasks dnsRecordUpdaterTasks() {
        return new DnsRecordUpdaterTasks(env, dnsRecordUpdaterService());
    }

    @Bean
    DnsRecordUpdaterService dnsRecordUpdaterService() {
        return new DnsRecordUpdaterService(env, networkingService(), cloudflareDnsService, cloudflareDnsServiceProperties);
    }

    @Bean
    NetworkingService networkingService() {
        return new DefaultNetworkingServiceImpl(this.webClientBuilder);
    }

    @Bean
    CloudflareDnsService cloudflareDnsService() {
        return new CloudflareDnsService(this.webClientBuilder, cloudflareDnsServiceProperties());
    }

    @ConfigurationProperties("dns-record-updater.cloudflare")
    @Bean
    CloudflareDnsServiceProperties cloudflareDnsServiceProperties() {
        return new CloudflareDnsServiceProperties();
    }

}
