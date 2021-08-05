package com.octopusthu.dev.net.providers.ifconfigme;

import lombok.Data;

/**
 * Default data format fetched from https://ifconfig.me
 *
 * @author ZHANG Yu
 */
@Data
public class Ifconfig {
    private String ip_addr;
    private String remote_host;
    private String user_agent;
    private int port;
    private String language;
    private String method;
    private String encoding;
    private String via;
    private String forwarded;
}
