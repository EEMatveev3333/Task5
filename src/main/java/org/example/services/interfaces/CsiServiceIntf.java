package org.example.services.interfaces;

import org.example.request.CreateCsiRequest;
import org.example.response.CsiResponse;

public interface CsiServiceIntf {
    CsiResponse createCsi(CreateCsiRequest csiRequest);
}
