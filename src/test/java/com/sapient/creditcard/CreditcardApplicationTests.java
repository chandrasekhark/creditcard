package com.sapient.creditcard;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.isA;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sapient.creditcard.dto.ApiErrorResponse;
import com.sapient.creditcard.model.CreditCard;
import com.sapient.creditcard.repository.CreditCardRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureMockMvc
class CreditcardApplicationTests {

	ObjectMapper objectMapper = new ObjectMapper();
	
	@Autowired
	private CreditCardRepository creditCardRepository;
	
	@Autowired
    private MockMvc mockMvc;
	
	Map<String, CreditCard> testData;
	
	@BeforeEach
	public void setup() {
		creditCardRepository.deleteAll();
		testData = getTestData();
	}
	
	@Test
    public void testCreditCardCreationValidData() throws Exception {
		CreditCard expectedRecord = testData.get("creditCard_01");
		CreditCard actualRecord = objectMapper.readValue(mockMvc.perform(post("/creditcard")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(expectedRecord)))
                .andDo(print())
                .andExpect(jsonPath("$.id", greaterThan(0)))
                .andExpect(status().isCreated()).andReturn().getResponse().getContentAsString(), CreditCard.class);
		
		Assert.assertTrue(new ReflectionEquals(expectedRecord, "id").matches(actualRecord));
        assertEquals(true, creditCardRepository.findById(actualRecord.getId()).isPresent());
        
        expectedRecord = testData.get("creditCard_02");
		actualRecord = objectMapper.readValue(mockMvc.perform(post("/creditcard")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(expectedRecord)))
                .andDo(print())
                .andExpect(jsonPath("$.id", greaterThan(0)))
                .andExpect(status().isCreated()).andReturn().getResponse().getContentAsString(), CreditCard.class);
		
		Assert.assertTrue(new ReflectionEquals(expectedRecord, "id").matches(actualRecord));
        assertEquals(true, creditCardRepository.findById(actualRecord.getId()).isPresent());
	}
	
	@Test
    public void testCreditCardCreationInValidData() throws Exception {
		
		CreditCard creditCard = new CreditCard("Rams", 11112222333344445L, new BigDecimal(-1045), new BigDecimal(2000));
		
		ApiErrorResponse expectedRecord = new ApiErrorResponse("error-0001", "Invalid credit card number : 11112222333344445");
		
		ApiErrorResponse actualRecord = objectMapper.readValue(mockMvc.perform(post("/creditcard")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(creditCard)))
                .andDo(print())
                .andExpect(status().isBadRequest()).andReturn().getResponse().getContentAsString(), ApiErrorResponse.class);
		
		Assert.assertTrue(new ReflectionEquals(actualRecord, "message").matches(expectedRecord));
	}
	
	@Test
    public void testGetAllCreditCards() throws Exception {
        Map<String, CreditCard> testData = getTestData().entrySet().stream().filter(kv -> !"creditCard_01".contains(kv.getKey())).collect(Collectors.toMap(kv -> kv.getKey(), kv -> kv.getValue()));

        List<CreditCard> expectedRecords = new ArrayList<>();
        for (Map.Entry<String, CreditCard> kv : testData.entrySet()) {
            expectedRecords.add(objectMapper.readValue(mockMvc.perform(post("/creditcard")
                    .contentType("application/json")
                    .content(objectMapper.writeValueAsString(kv.getValue())))
                    .andDo(print())
                    .andExpect(status().isCreated()).andReturn().getResponse().getContentAsString(), CreditCard.class));
        }
        Collections.sort(expectedRecords, Comparator.comparing(CreditCard::getId));

        List<CreditCard> actualRecords = objectMapper.readValue(mockMvc.perform(get("/creditcards"))
                .andDo(print())
                .andExpect(jsonPath("$.*", isA(ArrayList.class)))
                .andExpect(jsonPath("$.*", hasSize(expectedRecords.size())))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString(), new TypeReference<List<CreditCard>>() {
        });

        for (int i = 0; i < expectedRecords.size(); i++) {
            Assert.assertTrue(new ReflectionEquals(expectedRecords.get(i)).matches(actualRecords.get(i)));
        }
    }
	
	private Map<String, CreditCard> getTestData() {
		Map<String, CreditCard> data = new HashMap<>();
		
		CreditCard creditCard_01 = new CreditCard("Alice", 1111222233334444L, BigDecimal.ZERO, new BigDecimal(2000).setScale(2, RoundingMode.HALF_EVEN));
		data.put("creditCard_01", creditCard_01);
		
		CreditCard creditCard_02 = new CreditCard("Bob", 4444333322221111L, new BigDecimal(10.24).setScale(2, RoundingMode.HALF_EVEN), new BigDecimal(5000).setScale(2, RoundingMode.HALF_EVEN));
		data.put("creditCard_02", creditCard_02);
		
		CreditCard creditCard_03 = new CreditCard("James", 1111222233334444L, new BigDecimal(-1045).setScale(2, RoundingMode.HALF_EVEN), new BigDecimal(2000).setScale(2, RoundingMode.HALF_EVEN));
		data.put("creditCard_03", creditCard_03);
		
		return data;
	}

}
