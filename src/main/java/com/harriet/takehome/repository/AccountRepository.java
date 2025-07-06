package com.harriet.takehome.repository;

import com.harriet.takehome.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account,Long> {
}
