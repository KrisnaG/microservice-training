package com.dnb.accountservice.repo;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.dnb.accountservice.dto.Account;

@Repository
public interface AccountRespository extends CrudRepository<Account, String> {

	// select * from Account where account.customerId = ?
	public Optional<Account> findByCustomerId(Integer customerId);
}
