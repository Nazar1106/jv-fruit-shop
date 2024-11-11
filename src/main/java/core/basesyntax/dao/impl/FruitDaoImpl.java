package core.basesyntax.dao.impl;

import static core.basesyntax.db.Storage.storage;

import core.basesyntax.dao.FruitDao;

public class FruitDaoImpl implements FruitDao {
    private static final String MESSAGE_FRUIT_NOT_AVAILABLE = "Sorry, but we don't have ";
    private static final String MESSAGE_NOT_ENOUGH_FRUIT = "Sorry, but we don't have enough fruit ";
    private static final String EXCEPTION_MESSAGE = "Quantity is not valid";

    public void supplyFruit(String fruit, int quantity) {
        var balanceOfQuantity = storage.get(fruit);
        if (storage.containsKey(fruit)) {
            storage.replace(fruit, balanceOfQuantity + quantity);
        } else {
            storage.put(fruit, quantity);
        }
    }

    @Override
    public void boughtFruit(String fruit, int quantity) {
        if (quantity < 0) {
            throw new RuntimeException(EXCEPTION_MESSAGE);
        }
        if (!storage.containsKey(fruit)) {
            System.out.println(MESSAGE_FRUIT_NOT_AVAILABLE + fruit);
            return;
        }
        var balanceOfQuantity = storage.get(fruit);
        if (balanceOfQuantity < quantity) {
            System.out.println(MESSAGE_NOT_ENOUGH_FRUIT + fruit);
        }
        if (balanceOfQuantity >= quantity) {
            storage.replace(fruit, balanceOfQuantity - quantity);
        }
    }

    @Override
    public void returnFruit(String fruit, int quantity) {
        supplyFruit(fruit, quantity);
    }
}
