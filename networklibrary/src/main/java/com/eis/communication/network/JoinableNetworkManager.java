package com.eis.communication.network;

import com.eis.communication.Peer;

/**
 * Manager for networks where the join has to be done manually and not automatically.
 *
 * @author Luca Crema, suggested by Enrico Cestaro
 * @since 07/01/2020
 */
public interface JoinableNetworkManager<RK, RV, P extends Peer, FR extends FailReason> extends NetworkManager<RK, RV, P, FR> {

    void join(Invitation<P> invitation);

    void setJoinListener(JoinListener<P> joinListener);

}
