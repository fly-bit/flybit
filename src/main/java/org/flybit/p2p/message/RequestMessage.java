
package org.flybit.p2p.message;

public class RequestMessage{

    private long requestId;

    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }

    public Long getRequestId() {
        return requestId;
    }

}
