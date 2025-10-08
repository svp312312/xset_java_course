package homework1;

public class Programmer {
    // TODO: Создать класс Programmer с полями:
    //       - String name - имя программиста
    //       - int age - возраст программиста
    //       - boolean isHasTask - наличие активной задачи
    //       - int tiredness - уровень усталости
    //       - boolean isKnowJava - знает ли программист Java

    private String name;
    private int age;
    private boolean isHasTask;
    private int tiredness;
    private boolean isKnowJava;

    // TODO: Реализовать конструктор Programmer(String name, int age, boolean isHasTask, int tiredness, boolean isKnowJava):
    //       - Инициализировать все поля переданными значениями

    Programmer(String name, int age, boolean isHasTask, int tiredness, boolean isKnowJava){
        this.name = name;
        this.age = age;
        this.isHasTask = isHasTask;
        this.tiredness = tiredness;
        this.isKnowJava = isKnowJava;
    }

    // TODO: Реализовать метод goWork():
    //       - Если у программиста есть задача, вывести сообщение "[name] работает над задачей." и увеличить tiredness на 1
    //       - Если задачи нет, вывести сообщение "[name] не имеет активных задач, и пошел ее получать" и вызвать getTask()

    public void goWork(){
        if(isHasTask){
            System.out.println(name + " работает над задачей.");
            tiredness++;
        }
        else{
            System.out.println(name + " не имеет активных задач, и пошел ее получать.");
            getTask();
        }
    }

    // TODO: Реализовать метод getTask():
    //       - Установить isHasTask в true

    public void getTask() {
        isHasTask = true;
    }

    // TODO: Реализовать метод finishTask():
    //       - Установить isHasTask в false

    public void finishTask() {
        isHasTask = false;
    }

    // TODO: Реализовать метод goSleep():
    //       - Вывести сообщение "[name] идет спать."
    //       - Уменьшить tiredness на 1

    public void goSleep() {
        System.out.println(name + " идет спать.");
        tiredness--;

    }


    // TODO: Реализовать метод teachJava(Programmer anotherProgrammer):
    //       - Если текущий программист знает Java, а другой нет:
    //         - Обучить другого программиста (установить isKnowJava в true)
    //         - Вывести сообщение "[name] обучил [anotherProgrammer.name] языку Java."
    //       - Если текущий программист не знает Java, вывести "[name] не знает Java и не может обучить [anotherProgrammer.name]."
    //       - Если второй программист уже знает Java, вывести "[anotherProgrammer.name] уже знает Java."

    public void teachJava(Programmer anotherProgrammer){
        if(this.isKnowJava && !anotherProgrammer.isKnowJava){
            anotherProgrammer.isKnowJava = true;
            System.out.println(this.name + " обучил " + anotherProgrammer.name + " языку Java.");
        } else if (!this.isKnowJava) {
            System.out.println(this.name + " не знает Java и не может обучить " + anotherProgrammer.name + ".");
        }else if (anotherProgrammer.isKnowJava){
            System.out.println(anotherProgrammer.name + " уже знает Java.");
        }
    }

    // TODO: Проверить отработку методов в main
    //       - Создать двух программистов с разными характеристиками
    //       - Проверить работу всех методов класса
    public static void main(String[] args) {
        Programmer programmer1 = new Programmer("Роман Тестировщиков", 30,true,1, true);
        Programmer programmer2 = new Programmer("Александр Ким", 26,false,1, false);
        System.out.println("\n===== ПРОГРАММИСТЫ РАБОТАЮТ =====");
        programmer1.goWork();
        programmer2.goWork();
        programmer1.goWork();
        programmer2.goWork();
        programmer1.finishTask();
        programmer2.finishTask();
        programmer1.goWork();
        programmer2.goWork();
        System.out.println("\n===== ПРОГРАММИСТЫ ОБУЧАЮТ ДРУГ ДРУГА =====");
        programmer2.teachJava(programmer1);
        programmer1.teachJava(programmer2);
        programmer1.teachJava(programmer2);
        System.out.println("\n===== РАБОЧИЙ ДЕНЬ ОКОНЧЕН =====");
        programmer1.goSleep();
        programmer2.goSleep();
    }
}


