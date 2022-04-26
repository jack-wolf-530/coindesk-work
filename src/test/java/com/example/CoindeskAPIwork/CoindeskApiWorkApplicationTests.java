package com.example.CoindeskAPIwork;

import com.example.CoindeskAPIwork.entities.Coin;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class CoindeskApiWorkApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	/**
	 * 測試呼叫查詢幣別對應表資料API，並顯示其內容
	 * @throws Exception
	 */
	@Test
	void testGetCoins() throws Exception {
		ResultActions resultActions = mockMvc.perform(get("/findAll"));
		resultActions.andReturn().getResponse().setCharacterEncoding("UTF-8");
		resultActions.andExpect(status().isOk()).andDo(print());
		String result = resultActions.andReturn().getResponse().getContentAsString();
		System.out.println("Test Result: " + result);
	}

	/**
	 * 測試呼叫新增幣別對應表資料API
	 * @throws Exception
	 */
	@Test
	void testCreateCoin() throws Exception {
		Coin coin = new Coin();
		coin.setChName("新臺幣");
		coin.setEnName("NTD");
		ObjectMapper mapper = new ObjectMapper();
		String request = mapper.writeValueAsString(coin);
		ResultActions resultActions = mockMvc.perform(post("/createCoins")
				.contentType(MediaType.APPLICATION_JSON)
				.content(request));
		resultActions.andReturn().getResponse().setCharacterEncoding("UTF-8");
		resultActions.andExpect(status().isOk()).andDo(print());
	}

	/**
	 * 測試呼叫更新幣別對應表資料API，並顯示其內容。
	 * @throws Exception
	 */
	@Test
	void testUpdateCoin() throws Exception {
		Coin coin = new Coin();
		coin.setChName("測試");
		coin.setEnName("USD");
		ObjectMapper mapper = new ObjectMapper();
		String request = mapper.writeValueAsString(coin);
		ResultActions resultActions = mockMvc.perform(put("/updateCoins")
				.contentType(MediaType.APPLICATION_JSON)
				.content(request));
		resultActions.andReturn().getResponse().setCharacterEncoding("UTF-8");
		resultActions.andExpect(status().isOk()).andDo(print());
		String result = resultActions.andReturn().getResponse().getContentAsString();
		System.out.println("Test Result: " + result);
	}

	/**
	 * 測試呼叫刪除幣別對應表資料API。
	 * @throws Exception
	 */
	@Test
	void testDeleteCoin() throws Exception {
		ResultActions resultActions = mockMvc.perform(delete("/USD"));
		resultActions.andReturn().getResponse().setCharacterEncoding("UTF-8");
		resultActions.andExpect(status().isNoContent()).andDo(print());
	}


	/**
	 * 測試呼叫coindesk API，並顯示其內容。
	 * @throws Exception
	 */
	@Test
	void testGetOrgAPI() throws Exception {
		ResultActions resultActions = mockMvc.perform(get("/getOrgAPI"));
		resultActions.andReturn().getResponse().setCharacterEncoding("UTF-8");
		resultActions.andDo(print()).andExpect(status().isOk());
		String result = resultActions.andReturn().getResponse().getContentAsString();
		System.out.println("Test Result: " + result);
	}

	/**
	 * 測試呼叫資料轉換的API，並顯示其內容。
	 * @throws Exception
	 */
	@Test
	void testConvertCoinDesk() throws Exception {
		ResultActions resultActions = mockMvc.perform(get("/getNewAPI"));
		resultActions.andReturn().getResponse().setCharacterEncoding("UTF-8");
		resultActions.andExpect(status().isOk()).andDo(print());
		String result = resultActions.andReturn().getResponse().getContentAsString();
		System.out.println("Test Result: " + result);
	}
}
