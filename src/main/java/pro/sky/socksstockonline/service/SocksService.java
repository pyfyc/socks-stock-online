package pro.sky.socksstockonline.service;

import pro.sky.socksstockonline.model.Socks;

public interface SocksService {
    Integer getSocksStock(String color, String operation, Integer cottonPart);
    Boolean socksIncome(Socks socks);
    Boolean socksOutcome(Socks socks);
}
