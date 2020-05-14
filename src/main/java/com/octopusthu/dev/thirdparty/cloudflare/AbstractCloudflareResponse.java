package com.octopusthu.dev.thirdparty.cloudflare;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author ZHANG Yu
 */
@Data
public abstract class AbstractCloudflareResponse implements Serializable {
    protected boolean success;
    protected List<Map<String, String>> errors;
}
