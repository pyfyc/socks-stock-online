package pro.sky.socksstockonline.service;

import pro.sky.socksstockonline.model.Socks;

public interface SocksService {
    Integer getSocksStock(String color, String operation, Integer cottonPart);
    Socks socksIncome(Socks socks);
    Socks socksOutcome(Socks socks);
}
