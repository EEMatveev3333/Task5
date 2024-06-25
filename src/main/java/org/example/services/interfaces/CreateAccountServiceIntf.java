package org.example.services.interfaces;

import org.example.request.CreateAccountRequest;
import org.example.response.CreateAccountResponse;

import javax.persistence.NoResultException;

public interface CreateAccountServiceIntf {
    CreateAccountResponse createAccount(CreateAccountRequest accountRequest) throws NoResultException;
}