
package org.flybit.p2p.outbound;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.flybit.p2p.exception.PeerException;

public class PeerOutboundRequest {

    private final CountDownLatch latch = new CountDownLatch(1);

    private volatile PeerException exception;

    private volatile Object response;

    public Object get(long timeout, TimeUnit unit) {
        try {
            if (!latch.await(timeout, unit)) {
                throw new PeerException("Message read timeout");
            }
        } catch (final InterruptedException e) {
            throw new PeerException(e.getMessage());
        }
        if (exception != null) {
            throw exception;
        }
        return response;
    }

    public void complete(Object response) {
        this.response = response;
        latch.countDown();
    }

    public void complete(PeerException exception) {
        this.exception = exception;
        latch.countDown();
    }
}
