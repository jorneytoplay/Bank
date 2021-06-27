package BANK;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class bank {
    public static void main(String[] args) {
        Simulation start = new Simulation();
        start.listStart();
    }
}


class Users {
    private final ArrayList<Integer> listId = new ArrayList<>(); // список имеющихся айди для сравнение
    private final HashMap<Integer, depositAction> action = new HashMap<>(); //Ссылка депозитов
    HashMap<Integer, String> idAndClients = new HashMap<>(); // поля данных

    public int randIdAdd(String name) {
        System.out.println("Операция добавления пользователя:");
        Random random = new Random();
        int id;
        id = random.nextInt(10000);
        for (int i = 0; i < listId.size(); i++) {
            if (id == listId.get(i)) {
                id = random.nextInt(10000);
                i--;
            }
        }
        listId.add(id);
        idAndClients.put(id, name);
        action.put(id, new depositAction());
        System.out.println("Пользователь: " + idAndClients.get(id) + " ID: " + id);
        System.out.println("....................успешно");
        System.out.println();

        return id;
    }

    public void deleteUser(int id) {
        System.out.println("Операция удаления пользователя:");
        if (idAndClients.get(id) == null) {
            System.out.println("Пользователь с ID: " + id + " не существует");
            System.out.println("....................ошибка");
            System.out.println();
        } else {
            System.out.println("Пользователь: " + idAndClients.get(id) + " ID: " + id);
            idAndClients.remove(id);
            action.remove(id);
            System.out.println("....................успешно");
            System.out.println();
        }
    }

    public void takeOn(int id, int depIndex, int cash) { //Создаем массив депозитов для отедьного пользователя для пополнения
        depIndex += 1;
        System.out.println("Операция пополнение депозита:");
        System.out.println("Пользователь " + idAndClients.get(id) + " требует пополнения " + depIndex + " депозита на сумму " + cash);
        if (idAndClients.get(id) == null) {
            System.out.println("Пользователь с ID: " + id + " не существует");
            System.out.println("....................ошибка\n");
        } else {
            depositAction d = action.get(id);
            d.takeOnMoney(depIndex, cash);// depindex - случайный номер депозита
        }
    }

    public void takeOff(int id, int depIndex, int cash) {
        depIndex += 1;
        System.out.println("Операция снятия средств с депозита:");
        System.out.println("Пользователь " + idAndClients.get(id) + " требует снятия средств с " + depIndex + " депозита на сумму " + cash);
        if (idAndClients.get(id) == null) {
            System.out.println("Пользователь с ID: " + id + " не существует");
            System.out.println("....................ошибка\n");
        } else {
            depositAction d = action.get(id);
            d.takeOffMoney(depIndex, cash);// depindex - случайный номер депозита
        }
    }

    public void createDep(int id, int dep) {
        System.out.println("Операция  по созданию депозит(а/ов):");
        if (idAndClients.get(id) == null) {
            System.out.println("Пользователь с ID: " + id + " не существует");
            System.out.println("....................ошибка\n");
        } else if (dep >= 6) {
            System.out.println("Пользователь с ID: " + id + " создаёт недопустимое количество депозитов");
            System.out.println("....................ошибка\n");
        } else {
            System.out.println("Пользователь: " + idAndClients.get(id) + " ID: " + id + " требует создания " + (dep + 1) + " депозитов");
            depositAction d = action.get(id);
            d.addDeposit(dep);
        }
    }

    public void deleteDep(int id, int dep) {
        System.out.println("Операция  по удалению депозита:");
        if (idAndClients.get(id) == null) {
            System.out.println("Пользователь с ID: " + id + " не существует");
            System.out.println("....................ошибка\n");
        } else {
            System.out.println("Пользователь: " + idAndClients.get(id) + " ID: " + id + " требует удаление " + (dep + 1) + " депозита");
            depositAction d = action.get(id);
            d.deleteDeposit(dep);
        }
    }

    public void interestAccrual(int id) {
        System.out.println("Операция  по начислению процентов на депозит:");
        if (idAndClients.get(id) == null) {
            System.out.println("Пользователь с ID: " + id + " не существует");
            System.out.println("....................ошибка\n");
        } else {
            depositAction d = action.get(id);
            d.Accrual();
        }
    }
}

class depositAction extends Currency {
    private final ArrayList<Double> deposits = new ArrayList<Double>(); //Список депозитов пользователя

    public void addDeposit(int dep) {
        dep += 1;
        if (dep <= 5) {
            for (int i = 0; i < dep; i++) {
                deposits.add(0.0);
            }
            System.out.println("....................успешно\n");
        } else {
            System.out.println("Пользователь требует создание более 5 депозитов");
            System.out.println("....................ошибка\n");
        }
    }

    public void deleteDeposit(int dep) {
        dep += 1;
        if (deposits.size() < dep || deposits.get(dep) == null || dep == 5) {
            System.out.println("Данный депозит отсутствует");
            System.out.println("....................ошибка\n");
        } else {
            deposits.remove(dep);
            System.out.println(dep + " депозит был удалён");
            System.out.println("....................успешно\n");
        }
    }

    public void takeOnMoney(int indexDeposit, double cash) {
        if (deposits.size() > indexDeposit) {
            cash += deposits.get(indexDeposit);
            deposits.set(indexDeposit, cash);
            System.out.println("Баланс: " + deposits.get(indexDeposit));
            System.out.println("....................успешно");
            System.out.println();
        } else {
            System.out.println("Данный депозит отстутсвует");
            System.out.println("....................ошибка\n");

        }
    }

    public void takeOffMoney(int indexDeposit, double cash) {
        if (deposits.size() > indexDeposit) {

            double timeCash = cash; //переменная для снятия денег с банка
            while (timeCash != 0) { //цикл отнмания купюр у банка
                if ((timeCash / 1000) > 1) {
                    timeCash -= 1000;
                    valuesCurrency[6] -= 1;
                } else if ((timeCash / 500) > 1) {
                    timeCash -= 500;
                    valuesCurrency[5] -= 1;
                } else if ((timeCash / 100) > 1) {
                    timeCash -= 100;
                    valuesCurrency[4] -= 1;
                } else if ((timeCash / 50) > 1) {
                    timeCash -= 50;
                    valuesCurrency[3] -= 1;
                } else if ((timeCash / 10) > 1) {
                    timeCash -= 10;
                    valuesCurrency[2] -= 1;
                } else if ((timeCash / 5) > 1) {
                    timeCash -= 5;
                    valuesCurrency[1] -= 1;
                } else {
                    timeCash -= 1;
                    valuesCurrency[0] -= 1;
                }
                if (valuesCurrency[0] < 0 || //если у банка закончились купюры какого то номинала то пополнить все на 1000
                        valuesCurrency[1] < 0 ||
                        valuesCurrency[2] < 0 ||
                        valuesCurrency[3] < 0 ||
                        valuesCurrency[4] < 0 ||
                        valuesCurrency[5] < 0 ||
                        valuesCurrency[6] < 0) {
                    addBankMoney();
                }
            }
            cash = deposits.get(indexDeposit) - cash;
            deposits.set(indexDeposit, cash);
            System.out.println("Баланс: " + deposits.get(indexDeposit));
            System.out.println("....................успешно");
            System.out.println();
            System.out.println("Наличие купюр банка:");
            for (int i = 0; i < num.length; i++) {
                System.out.println(valuesCurrency[i]);
            }
            System.out.println();
        } else {
            System.out.println("Данный депозит отсутствует");
            System.out.println("....................ошибка\n");
        }
    }

    public void Accrual() { //Каждый депозит составкой 0.2 в месяц
        System.out.println("Производиться начисление процентов на депозиты за месяц");
        double a;
        for (int i = 1; i < deposits.size(); i++) {
            a = deposits.get(i);
            a *= 1.2;
            deposits.set(i, a);
        }
        System.out.println("....................успешно\n");
    }
}


class Currency {

    public int[] num = {1, 5, 10, 50, 100, 500, 1000};
    public int[] valuesCurrency = { //Запасы банка
            1000000000,
            100000000,
            10000000,
            1000000,
            100000,
            10000,
            1000
    };

    public void addBankMoney() { // Если закончилась какая то валюта пополнить её
        for (int i = 0; i < valuesCurrency.length; i++) {
            valuesCurrency[i] += 1000;
        }
    }

}

class Simulation {
    Random random = new Random();
    Users actions = new Users();

    public void listStart() {
        ArrayList<Integer> idList = new ArrayList<>();
        ArrayList<String> possibleUsers = new ArrayList<>();
        idList.add(actions.randIdAdd("Экремика")); //Список начальныъ пользователей
        idList.add(actions.randIdAdd("Катя Самбука"));
        idList.add(actions.randIdAdd("Сказачное Бали"));
        idList.add(actions.randIdAdd("Фифти-Фифти"));
        idList.add(actions.randIdAdd("Чебурек"));
        idList.add(actions.randIdAdd("Дырчик"));
        idList.add(actions.randIdAdd("Сивый"));

        possibleUsers.add("Мегатрон");  //Список возможных пользователей
        possibleUsers.add("Набор лего космонавты");
        possibleUsers.add("Спинер");
        possibleUsers.add("Сок 'Красавчик'");
        possibleUsers.add("Телепузик");
        possibleUsers.add("Мама");

        for (int i = 0; i < 12; i++) { // Представим что i=1 это 1 месяц
            actions.interestAccrual(idList.get(random.nextInt(idList.size())));
            for (int j = 0; j < 720; j += random.nextInt(120) + 1) { //Представим что j=1 это 1 час
                int selection = random.nextInt(150);
                if (selection >= 1 && selection < 50) {
                    actions.randIdAdd(possibleUsers.get(random.nextInt(6)));
                } else if (selection >= 50 && selection < 60) {
                    actions.deleteUser(idList.get(random.nextInt(idList.size())));
                } else if (selection >= 60 && selection < 100) {
                    actions.createDep(idList.get(random.nextInt(idList.size())), random.nextInt(6));
                } else if (selection >= 100 && selection < 130) {
                    actions.takeOn(idList.get(random.nextInt(idList.size())), random.nextInt(6), random.nextInt(100000));
                } else if (selection >= 130 && selection <= 140) {
                    actions.takeOff(idList.get(random.nextInt(idList.size())), random.nextInt(6), random.nextInt(10000)); // Сниматт будем поменьше чтоб не было ошибок :)
                } else if (selection >= 140) {
                    actions.deleteDep(idList.get(random.nextInt(idList.size())), random.nextInt(6));
                }

            }

        }
    }
}

