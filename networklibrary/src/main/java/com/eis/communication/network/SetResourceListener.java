package com.eis.communication.network;

public interface SetResourceListener<RK, RV>{

    void onResourceSet(RK key, RV value);

    void onResourceSetFail(RK key, RV value, FailReason reason);

}
