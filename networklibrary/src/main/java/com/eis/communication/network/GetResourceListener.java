package com.eis.communication.network;

public interface GetResourceListener<RK, RV>{

    void onGetResource(RK key, RV value);

    void onGetResourceFailed(RK key, FailReason reason);

}
