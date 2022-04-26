package com.example.CoindeskAPIwork.repository;

import com.example.CoindeskAPIwork.entities.Coin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoinRepository extends JpaRepository<Coin, String>{
}
