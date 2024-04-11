package org.lsh.protocolTcp;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 协议包
 */
@Data
@Accessors(chain = true)
public class MessageProtocol {
    private int len;
    private byte[] content;
}
