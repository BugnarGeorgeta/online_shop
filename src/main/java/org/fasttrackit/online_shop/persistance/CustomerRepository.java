package org.fasttrackit.online_shop.persistance;

import org.fasttrackit.online_shop.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer,Long> {
}
