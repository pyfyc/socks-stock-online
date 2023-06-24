package pro.sky.socksstockonline.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import pro.sky.socksstockonline.exception.*;
import pro.sky.socksstockonline.model.Socks;
import pro.sky.socksstockonline.repository.SocksRepository;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class SocksControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @LocalServerPort
    private int port;

    @MockBean
    SocksRepository socksRepository;

    /**
     * Returns 200 (Ok) when call getSocksStock with "moreThan" operation.
     * @throws Exception
     */
    @Test
    void returnOkWhenGetSocksStockMoreThan() throws Exception {
        String color = "red";
        String operation = "moreThan";
        Integer cottonPart = 50;

        mockMvc.perform(get("http://localhost:" + port + "/api/socks?color=" + color +
                        "&operation=" + operation + "&cottonPart=" + cottonPart))
                .andExpect(status().isOk());
    }

    /**
     * Returns 200 (Ok) when call getSocksStock with "lessThan" operation.
     * @throws Exception
     */
    @Test
    void returnOkWhenGetSocksStockLessThan() throws Exception {
        String color = "red";
        String operation = "lessThan";
        Integer cottonPart = 50;

        mockMvc.perform(get("http://localhost:" + port + "/api/socks?color=" + color +
                        "&operation=" + operation + "&cottonPart=" + cottonPart))
                .andExpect(status().isOk());
    }

    /**
     * Returns 200 (Ok) when call getSocksStock with "equal" operation.
     * @throws Exception
     */
    @Test
    void returnOkWhenGetSocksStockEqual() throws Exception {
        String color = "red";
        String operation = "equal";
        Integer cottonPart = 50;

        mockMvc.perform(get("http://localhost:" + port + "/api/socks?color=" + color +
                        "&operation=" + operation + "&cottonPart=" + cottonPart))
                .andExpect(status().isOk());
    }

    /**
     * Returns 500 (Internal Server Error) when call getSocksStock with "unknown" operation.
     * @throws Exception
     */
    @Test
    void returnInternalServerErrorWhenGetSocksStockUnknown() throws Exception {
        String color = "red";
        String operation = "unknown";
        Integer cottonPart = 50;

        mockMvc.perform(get("http://localhost:" + port + "/api/socks?color=" + color +
                        "&operation=" + operation + "&cottonPart=" + cottonPart))
                .andExpect(status().isInternalServerError())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof UnknownOperationException));
    }

    /**
     * Returns 400 (Bad Request) when call getSocksStock with missing parameter.
     * @throws Exception
     */
    @Test
    void returnBadRequestWhenGetSocksStockWithParamMissing() throws Exception {
        String color = "red";
        String operation = "moreThan";

        mockMvc.perform(get("http://localhost:" + port + "/api/socks?color=" + color +
                        "&operation=" + operation + "&cottonPart="))
                .andExpect(status().isBadRequest());
    }

    /**
     * Returns 500 (Internal Server Error) when call getSocksStock with invalid parameter.
     * @throws Exception
     */
    @Test
    void returnInternalServerErrorWhenGetSocksStockWithInvalidParam() throws Exception {
        String color = "red";
        String operation = "moreThan";
        Integer cottonPart = 150; // invalid parameter

        mockMvc.perform(get("http://localhost:" + port + "/api/socks?color=" + color +
                        "&operation=" + operation + "&cottonPart=" + cottonPart))
                .andExpect(status().isInternalServerError())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof InvalidCottonPartException));
    }

    /**
     * Returns 200 (Ok) when call socksIncome properly.
     * @throws Exception
     */
    @Test
    void returnOkWhenSocksIncome() throws Exception {
        Socks socks = new Socks();
        socks.setId(1L);
        socks.setColor("red");
        socks.setCottonPart(70);
        socks.setQuantity(3);

        mockMvc.perform(post("http://localhost:" + port + "/api/socks/income")
                        .content(objectMapper.writeValueAsString(socks))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(socks.getId()))
                .andExpect(jsonPath("$.color").value(socks.getColor()))
                .andExpect(jsonPath("$.cottonPart").value(socks.getCottonPart()))
                .andExpect(jsonPath("$.quantity").value(socks.getQuantity()));
    }

    /**
     * Returns 500 (Internal Server Error) when call socksIncome with invalid cottonPart.
     * @throws Exception
     */
    @Test
    void returnInternalServerErrorWhenSocksIncomeWithInvalidCottonPart() throws Exception {
        Socks socks = new Socks();
        socks.setId(1L);
        socks.setColor("red");
        socks.setCottonPart(-1); // invalid parameter
        socks.setQuantity(3);

        mockMvc.perform(post("http://localhost:" + port + "/api/socks/income")
                        .content(objectMapper.writeValueAsString(socks))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof InvalidCottonPartException));
    }

    /**
     * Returns 500 (Internal Server Error) when call socksIncome with invalid quantity.
     * @throws Exception
     */
    @Test
    void returnInternalServerErrorWhenSocksIncomeWithInvalidQuantity() throws Exception {
        Socks socks = new Socks();
        socks.setId(1L);
        socks.setColor("red");
        socks.setCottonPart(10);
        socks.setQuantity(-3); // invalid parameter

        mockMvc.perform(post("http://localhost:" + port + "/api/socks/income")
                        .content(objectMapper.writeValueAsString(socks))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof InvalidSocksQuantityException));
    }

    /**
     * Returns 200 (Ok) when call socksOutcome.
     * @throws Exception
     */
    @Test
    void returnOkWhenSocksOutcome() throws Exception{
        Socks socks = new Socks();
        socks.setId(1L);
        socks.setColor("red");
        socks.setCottonPart(70);
        socks.setQuantity(3);

        when(socksRepository.findByColorAndCottonPart(any(String.class), any(Integer.class))).thenReturn(socks);

        mockMvc.perform(post("http://localhost:" + port + "/api/socks/outcome")
                        .content(objectMapper.writeValueAsString(socks))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    /**
     * Returns 500 (Internal Server Error) when call socksOutcome and
     * there is no such item in stock.
     * @throws Exception
     */
    @Test
    void returnInvalidServerErrorWhenSocksOutcomeNoItem() throws Exception{
        Socks socks = new Socks();
        socks.setId(1L);
        socks.setColor("red");
        socks.setCottonPart(70);
        socks.setQuantity(3);

        mockMvc.perform(post("http://localhost:" + port + "/api/socks/outcome")
                        .content(objectMapper.writeValueAsString(socks))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof NoSuchItemInStockException));
    }

    /**
     * Returns 500 (Internal Server Error) when call socksOutcome and there is
     * not enough items in stock for the color and cottonPart.
     * @throws Exception
     */
    @Test
    void returnInvalidServerErrorWhenSocksOutcomeNotEnoughInStock() throws Exception{
        // Requested item
        Socks socks = new Socks();
        socks.setId(1L);
        socks.setColor("red");
        socks.setCottonPart(70);
        socks.setQuantity(3);

        // Item in stock
        Socks socksInStock = new Socks();
        socksInStock.setId(1L);
        socksInStock.setColor("red");
        socksInStock.setCottonPart(70);
        socksInStock.setQuantity(1);

        when(socksRepository.findByColorAndCottonPart(any(String.class), any(Integer.class))).thenReturn(socksInStock);

        mockMvc.perform(post("http://localhost:" + port + "/api/socks/outcome")
                        .content(objectMapper.writeValueAsString(socks))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof NotEnoughInStockException));
    }
}