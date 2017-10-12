package org.spree.culture.exception;

public class CanNotGetCultureEvents extends RuntimeException {
    public CanNotGetCultureEvents() {
        super();
    }

    public CanNotGetCultureEvents(String s) {
        super(s);
    }

    public CanNotGetCultureEvents(String s, Throwable throwable) {
        super(s, throwable);
    }

    public CanNotGetCultureEvents(Throwable throwable) {
        super(throwable);
    }

    protected CanNotGetCultureEvents(String s, Throwable throwable, boolean b, boolean b1) {
        super(s, throwable, b, b1);
    }
}
