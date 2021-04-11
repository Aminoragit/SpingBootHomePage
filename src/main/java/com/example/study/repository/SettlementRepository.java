package com.example.study.repository;

import com.example.study.model.entity.Settlement;
import com.example.study.model.entity.User;
import com.example.study.model.network.Header;
import com.example.study.model.network.response.UserTotalPriceInfoApiResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SettlementRepository extends JpaRepository<Settlement,Long> {
    Optional<Settlement> findByUserId(Long userId);

}
