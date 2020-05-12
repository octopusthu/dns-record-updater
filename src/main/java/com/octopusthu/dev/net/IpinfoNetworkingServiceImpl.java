package com.octopusthu.dev.net;

import com.octopusthu.dev.thirdparty.ipinfo.IpInfo;
import io.netty.channel.ChannelOption;
import org.springframework.core.env.Environment;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

/**
 * @author ZHANG Yu
 */
public class IpinfoNetworkingServiceImpl {

    private final WebClient ipinfoWebClient;
    private final String ipinfoToken;

    public IpinfoNetworkingServiceImpl(Environment env,
                                       WebClient.Builder webClientBuilder) {

        HttpClient httpClient = HttpClient.create()
                .tcpConfiguration(client ->
                        client.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000));
        this.ipinfoWebClient = webClientBuilder
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .baseUrl("https://ipinfo.io")
                .build();

        this.ipinfoToken = env.getProperty("dns-record-updater.ipinfo-token");
    }

    public IpInfo fetchIpInfo() {
        return this.ipinfoWebClient.get().uri("/json")
                .header("Accept", "application/json")
                .header("Authorization", "Bearer " + this.ipinfoToken)
                .retrieve()
                .bodyToMono(IpInfo.class)
                .block();
    }
}
