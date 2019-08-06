package com.tenhawks.redeem.repository;

import com.tenhawks.redeem.model.Redeem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Created by Ahmed on 7/31/2019.
 */
@Repository
public interface RedeemRepository extends JpaRepository<Redeem, Long> {

    Optional<Redeem> findByRedeemToken(String redeemToken);
}
