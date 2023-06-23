package pro.sky.socksstockonline.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.sky.socksstockonline.model.Socks;
import pro.sky.socksstockonline.service.SocksService;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/socks")
public class SocksController {

    private final SocksService socksService;

    @Operation(
            summary = "Получить общее количество носков на складе",
            parameters = {
                    @Parameter(name = "color", description = "цвет носков"),
                    @Parameter(name = "operation", description = "оператор сравнения значения количества хлопка в составе носков, " +
                            "одно значение из: moreThan, lessThan, equal"),
                    @Parameter(name = "cottonPart", description = "значение процента хлопка в составе носков")
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK"
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad Request"
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal Server Error"
                    )
            },
            tags = "Носки"
    )
    @GetMapping
    public ResponseEntity<Integer> getSocksStock(
            @RequestParam String color,
            @RequestParam String operation,
            @RequestParam Integer cottonPart
    ) {
        return ResponseEntity.ok(socksService.getSocksStock(color, operation, cottonPart));
    }

    @Operation(
            summary = "Зарегистрировать приход носков на склад",
            parameters = {},
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK"
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad Request"
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal Server Error"
                    )
            },
            tags = "Носки"
    )
    @PostMapping("/income")
    public ResponseEntity<Boolean> socksIncome(@RequestBody Socks socks) {
        return ResponseEntity.ok(socksService.socksIncome(socks));
    }

    @Operation(
            summary = "Зарегистрировать отпуск носков со склада",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK"
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad Request"
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal Server Error"
                    )
            },
            tags = "Носки"
    )
    @PostMapping("/outcome")
    public ResponseEntity<Boolean> socksOutcome(@RequestBody Socks socks) {
        return ResponseEntity.ok(socksService.socksOutcome(socks));
    }
}
