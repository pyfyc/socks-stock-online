package pro.sky.socksstockonline.service.impl;

import org.springframework.stereotype.Service;
import pro.sky.socksstockonline.exception.*;
import pro.sky.socksstockonline.model.Socks;
import pro.sky.socksstockonline.repository.SocksRepository;
import pro.sky.socksstockonline.service.SocksService;

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
    public Socks socksIncome(Socks socks) {
        validateCottonPart(socks.getCottonPart());
        validateQuantity(socks.getQuantity());

        Socks socksExist = socksRepository.findByColorAndCottonPart(socks.getColor(), socks.getCottonPart());
        if (socksExist != null) {
            socksExist.setQuantity(socksExist.getQuantity() + socks.getQuantity());
            socks = socksExist;
        }
        socksRepository.save(socks);

        return socks;
    }

    @Override
    public Socks socksOutcome(Socks socks) {
        validateCottonPart(socks.getCottonPart());
        validateQuantity(socks.getQuantity());

        Socks socksExist = socksRepository.findByColorAndCottonPart(socks.getColor(), socks.getCottonPart());
        if (socksExist == null) {
            throw new NoSuchItemInStockException(NO_SUCH_ITEM_IN_STOCK_MSG);
        } else if (socksExist.getQuantity() < socks.getQuantity()) {
            throw new NotEnoughInStockException(NOT_ENOUGH_IN_STOCK_MSG);
        } else {
            socksExist.setQuantity(socksExist.getQuantity() - socks.getQuantity());
            socks = socksExist;
            socksRepository.save(socks);
        }

        return socks;
    }

    private void validateCottonPart(Integer cottonPart) {
        if (cottonPart < 0 || cottonPart > 100) {
            throw new InvalidCottonPartException(INVALID_COTTON_PART_VALUE_MSG);
        }
    }

    private void validateQuantity(Integer quantity) {
        if (quantity < 1) {
            throw new InvalidSocksQuantityException(INVALID_SOCKS_QUANTITY_VALUE_MSG);
        }
    }
}
