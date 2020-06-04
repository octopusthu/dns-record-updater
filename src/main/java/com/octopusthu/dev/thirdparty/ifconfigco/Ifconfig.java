package com.octopusthu.dev.thirdparty.ifconfigco;

import lombok.Data;

/**
 * Default data format fetched from https://ifconfig.co
 *
 * @author ZHANG Yu
 */
@Data
public class Ifconfig {
    private String ip;
    private long ip_decimal;
    private String country;
    private String country_iso;
    private String region_name;
    private String region_code;
    private String city;
    private String time_zone;
    private String asn;
    private String asn_org;
}
