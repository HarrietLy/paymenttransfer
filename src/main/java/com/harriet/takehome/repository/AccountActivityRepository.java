package com.harriet.takehome.repository;

import com.harriet.takehome.model.AccountActivity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountActivityRepository extends JpaRepository<AccountActivity,Long> {
}
