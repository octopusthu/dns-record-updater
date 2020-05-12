package com.octopusthu.dev.net;

import com.google.common.net.InetAddresses;
import com.octopusthu.dev.thirdparty.ifconfigco.Ifconfig;
import io.netty.channel.ChannelOption;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.net.InetAddress;

/**
 * @author ZHANG Yu
 */
public class DefaultNetworkingServiceImpl implements NetworkingService {

    private final WebClient ipConfigMeWebClient;

    public DefaultNetworkingServiceImpl(WebClient.Builder webClientBuilder) {
        HttpClient httpClient = HttpClient.create()
                .tcpConfiguration(client ->
                        client.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000));
        this.ipConfigMeWebClient = webClientBuilder
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .baseUrl("https://ifconfig.co")
                .build();
    }

    public Ifconfig fetchIfconfig() {
        return this.ipConfigMeWebClient.get().uri("/json")
                .retrieve()
                .bodyToMono(Ifconfig.class)
                .retry(5)
                .block();
    }

    @Override
    public InetAddress getExternalIp() throws Exception {
        Ifconfig ifconfig = this.fetchIfconfig();
        return InetAddresses.forString(ifconfig.getIp());
    }
}
