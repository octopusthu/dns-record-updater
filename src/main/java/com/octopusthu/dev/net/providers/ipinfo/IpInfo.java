package com.octopusthu.dev.net.providers.ipinfo;

import lombok.Data;

/**
 * Default data format fetched from https://ipinfo.io
 *
 * @author ZHANG Yu
 */
@Data
public class IpInfo {
    private String ip;
    private String city;
    private String region;
    private String country;
    private String loc;
    private String org;
    private String timezone;
}
