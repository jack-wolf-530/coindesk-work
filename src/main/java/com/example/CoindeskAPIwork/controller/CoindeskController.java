package com.example.CoindeskAPIwork.controller;


import com.example.CoindeskAPIwork.entities.Coin;
import com.example.CoindeskAPIwork.service.CoindeskService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CoindeskController {
    
    @Autowired
    CoindeskService coindeskService;

    /**
     * 取得 Coindesk API 資料
     * @return
     */
    @GetMapping("/getOrgAPI")
    public String getOrgAPI() {
        return coindeskService.getOrgData();
    }

    /**
     * 組成新的API
     * @return
     */
    @GetMapping("/getNewAPI")
    public String getNewAPI() {
        return coindeskService.getNewData();
    }

    /**
     * 查詢
     * @return
     */
    @GetMapping( "/{enName}" )
    public ResponseEntity<String> findSingle(@PathVariable String enName) {
        return ResponseEntity.ok(new Gson().toJson(coindeskService.findSingle(enName)));
    }

    /**
     * 查詢全部　
     * @return
     */
    @GetMapping("/findAll")
    public ResponseEntity<String> findAll() {
        return ResponseEntity.ok(new Gson().toJson(coindeskService.findAll()));
    }

    /**
     * 新增
     * @return
     */
    @PostMapping("/createCoins")
    public ResponseEntity<String> createCoin(@RequestBody Coin coin) {
        Coin resCoin = coindeskService.insert(coin);
        return ResponseEntity.ok(new Gson().toJson(resCoin));
    }

    /**
     * 修改
     * @return
     */
    @PutMapping("/updateCoins")
    public ResponseEntity<String> updateCoin(@RequestBody Coin coin) {
        Coin resCoin = coindeskService.update(coin);
        return ResponseEntity.ok(new Gson().toJson(resCoin));
    }

    /**
     * 刪除
     * @return
     */
    @DeleteMapping("/{enName}")
    public ResponseEntity<String> delete( @PathVariable("enName") String enName ) {
        coindeskService.delete(enName);
        return ResponseEntity.noContent().build();
    }

}
