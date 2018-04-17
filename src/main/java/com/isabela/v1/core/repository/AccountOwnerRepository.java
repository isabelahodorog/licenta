package com.isabela.v1.core.repository;

import com.isabela.v1.core.model.AccountOwner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountOwnerRepository extends JpaRepository<AccountOwner, Long> {

    AccountOwner findByEmail(String email);
}
