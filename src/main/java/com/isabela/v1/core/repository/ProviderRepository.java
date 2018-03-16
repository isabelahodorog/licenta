package com.isabela.v1.core.repository;

import com.isabela.v1.core.model.Provider;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProviderRepository extends JpaRepository<Provider, Long>{

    Provider findByName(String name);
}
