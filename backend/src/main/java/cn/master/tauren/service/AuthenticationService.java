package cn.master.tauren.service;

import cn.master.tauren.payload.request.AuthenticationRequest;
import cn.master.tauren.payload.response.AuthenticationResponse;

/**
 * @author Created by 11's papa on 12/02/2024
 **/
public interface AuthenticationService {
    AuthenticationResponse authenticate(AuthenticationRequest request);
}
