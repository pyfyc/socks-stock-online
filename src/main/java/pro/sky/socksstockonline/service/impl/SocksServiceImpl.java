package pro.sky.socksstockonline.service.impl;

import org.springframework.stereotype.Service;
import pro.sky.socksstockonline.exception.NoSuchItemInStockException;
import pro.sky.socksstockonline.exception.NotEnoughInStockException;
import pro.sky.socksstockonline.exception.UnknownOperationException;
import pro.sky.socksstockonline.model.Socks;
import pro.sky.socksstockonline.repository.SocksRepository;
import pro.sky.socksstockonline.service.SocksService;

import java.security.InvalidParameterException;

import static java.lang.Boolean.TRUE;
import static pro.sky.socksstockonline.constant.ErrorMessage.*;

@Service
public class SocksServiceImpl implements SocksService {
    private final SocksRepository socksRepository;

    public SocksServiceImpl(SocksRepository socksRepository) {
        this.socksRepository = socksRepository;
    }

    @Override
    public Integer getSocksStock(String color, String operation, Integer cottonPart) {
        validateCottonPart(cottonPart);

        Integer socksCount = null;
        switch (operation) {
            case "moreThan":
                socksCount = socksRepository.getSocksCountByColorAndMoreThanCottonPart(color, cottonPart);
                break;
            case "lessThan":
                socksCount = socksRepository.getSocksCountByColorAndLessThanCottonPart(color, cottonPart);
                break;
            case "equal":
                socksCount = socksRepository.getSocksCountByColorAndEqualCottonPart(color, cottonPart);
                break;
            default:
                throw new UnknownOperationException();
        }

        if (socksCount == null) {
            return 0;
        } else {
            return socksCount;
        }
    }

    @Override
    public Boolean socksIncome(Socks socks) {
        validateCottonPart(socks.getCottonPart());
        validateQuantity(socks.getQuantity());

        Socks socksExist = socksRepository.findByColorAndCottonPart(socks.getColor(), socks.getCottonPart());
        if (socksExist == null) {
            socksRepository.save(socks);
        } else {
            socksExist.setQuantity(socksExist.getQuantity() + socks.getQuantity());
            socksRepository.save(socksExist);
        }

        return TRUE;
    }

    @Override
    public Boolean socksOutcome(Socks socks) {
        validateCottonPart(socks.getCottonPart());
        validateQuantity(socks.getQuantity());

        Socks socksExist = socksRepository.findByColorAndCottonPart(socks.getColor(), socks.getCottonPart());
        if (socksExist == null) {
            throw new NoSuchItemInStockException(NO_SUCH_ITEM_IN_STOCK_MSG);
        } else if (socksExist.getQuantity() < socks.getQuantity()) {
            throw new NotEnoughInStockException(NOT_ENOUGH_IN_STOCK_MSG);
        } else {
            socksExist.setQuantity(socksExist.getQuantity() - socks.getQuantity());
            socksRepository.save(socksExist);
        }

        return TRUE;
    }

    private void validateCottonPart(Integer cottonPart) {
        if (cottonPart < 0 || cottonPart > 100) {
            throw new InvalidParameterException(INVALID_COTTON_PERCENT_VALUE_MSG);
        }
    }

    private void validateQuantity(Integer quantity) {
        if (quantity < 1) {
            throw new InvalidParameterException(INVALID_SOCKS_QUANTITY_VALUE_MSG);
        }
    }
}
