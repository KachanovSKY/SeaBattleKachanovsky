
import javax.swing.JOptionPane; // вывода диалогового окна о победе/проигрыше

public class Game {
    public static int[][] masPlay; // массив игрока
    public static int[][] masPlayOrientational; // массив игрока ориентация (1 - вправо, 0 - вниз)
    public static int[][] masComp; // массив компьютера
    public static int endGame=3; // 0-игра идет 1-Выиграл игрок 2- Выиграл компьютер
    public static int P1;
    public static int P2;
    public static int P3;
    public static int P4; // Показывает количество кораблей игрока
    public static int C1;
    public static int C2;
    public static int C3;
    public static int C4; // Показывает количество кораблей компьютера
    public static int countPlayerMove;
    public static int countComputerMove;
    public static boolean PvE; // автоигра или нет
    public final int pause=300; // время паузы при выстреле компьютера
    public static boolean myMove; // true - если сейчас ход игрока
    public static boolean computerMove;
    Thread thread=new Thread(); // Все атаки компьютера будут происходить в новом потоке
    Game() { // Конструктор двух полей
        masPlay = new int[10][10];
        masPlayOrientational = new int[10][10];
        masComp = new int[10][10];
    }

    public void start() { // Происходит обнуление массивов и расстановка кораблей

        while (thread.isAlive()) PvE = false; // Ждем если комп не сходил

        countComputerMove =0;
        countPlayerMove =0;

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                masPlay[i][j] = 0;
                masPlayOrientational[i][j] = 2;
                masComp[i][j] = 0;
            }
        }

        PvE =false;
        myMove =true; //ход игрока
        computerMove =false;
        endGame=0;// игра идет

        countDeathComputerShip(masComp);
        countDeathPlayerShip(masPlay);

        if (MyPanel.rasstanovka==false) {
            setPalubsPlayer();
        }

        setPalubsComputer();
    }

    public void attack(int mas[][],int i,int j) { // Атака игрока

        countPlayerMove++;
        mas[i][j] += 7;

        ifDeath(mas, i, j);
        ifEndGame();

        thread =new Thread(new Runnable() {
            @Override
            public void run() {
                //если промах
                if (masComp[i][j] < 8) {
                    myMove = false;
                    computerMove = true; //передаем ход компьютеру
                    // Ходит компьютер - пока попадает в цель
                    while (computerMove == true) {
                        try {
                            Thread.sleep(pause);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        computerMove = computerMove(masPlay);
                    }
                    myMove = true;//передаем ход игроку после промаха компьютера
                }
            }
        });
        thread.start();
    }

    public void ifEndGame(){ // проверка на конец игры
        // Сумма массива, когда все корабли убиты, равна 15*4+16*2*3+17*3*2+18*4 = 330
        // Суммируем элементы массива, и если сумма равна 330, то заканчиваем игру
        if (endGame==0){

        int sumEnd=330; //когда все корабли убиты
        int sumPlay=0; // Сумма убитых палуб игрока
        int sumComp=0; // Сумма убитых палуб компьютера

        countDeathComputerShip(masComp);
        countDeathPlayerShip(masPlay);

            if (endGame==0) {
                for (int i = 0; i < 10; i++) {
                    for (int j = 0; j < 10; j++) {
                    // Суммируем подбитые палубы
                    if (masPlay[i][j] >= 15) sumPlay += masPlay[i][j];
                    if (masComp[i][j] >= 15) sumComp += masComp[i][j];
                }
            }
            if (sumPlay == sumEnd) {
                endGame = 2;
                //выводим диалоговое окно игроку
                JOptionPane.showMessageDialog(null,
                        "Вы проиграли! Попробуйте еще раз",
                        "Вы проиграли", JOptionPane.INFORMATION_MESSAGE);

            } else if (sumComp == sumEnd) {
                endGame = 1;
                //выводим диалоговое окно игроку
                JOptionPane.showMessageDialog(null,
                        "Поздравляю! Вы выиграли!",
                        "Вы выиграли", JOptionPane.INFORMATION_MESSAGE);
            }
            }
        }
    }

    public void countDeathComputerShip(int[][]mas){ // Подсчитываем количество убитых кораблей компьютера
        P4=0;P3=0;P2=0;P1=0;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (mas[i][j]==18) P4 = (P4 + 1);
                if (mas[i][j]==17) P3 = (P3 + 1);
                if (mas[i][j]==16) P2 = (P2 + 1);
                if (mas[i][j]==15) P1 = (P1 + 1);
            }
        }
        P4/=4;P3/=3;P2/=2;
    }

    public void countDeathPlayerShip(int[][]mas) { // Считаем убитые корабли игрока

        C4 = 0;C3 = 0;C2 = 0;C1 = 0;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (mas[i][j] == 18) C4 = (C4 + 1);
                if (mas[i][j] == 17) C3 = (C3 + 1);
                if (mas[i][j] == 16) C2 = (C2 + 1);
                if (mas[i][j] == 15) C1 = (C1 + 1);
            }
        }
        C4/=4;C3/=3;C2/=2;
    }

    private void ifDeath(int mas[][], int i, int j){ // Метод проверяет убита ли палуба
        if (mas[i][j]==8) { //Если однопалубный
            mas[i][j] += 7; //прибавляем к убитому +7
            deathRing(mas,i,j);//Уменьшаем окружение убитого на 1
        }
        else if (mas[i][j]==9){
            analizDeath(mas,i,j,2);
        }
        else if (mas[i][j]==10){
            analizDeath(mas,i,j,3);
        }
        else if (mas[i][j]==11){
            analizDeath(mas,i,j,4);
        }
    }


    private void analizDeath(int[][] mas, int i, int j, int countPalubs) { // Анализ, убит ли корабль
        //Количество раненых палуб
        int countInjured=0;
        //Выполняем подсчет раненых палуб
        for (int x=i-(countPalubs-1);x<=i+(countPalubs-1);x++) {
            for (int y=j-(countPalubs-1);y<=j+(countPalubs-1);y++) {
                // Если это палуба раненого корабля
                if (ifMasOut(x, y)&&(mas[x][y]==countPalubs+7)) countInjured++;
            }
        }
        // Если количество раненых палуб совпадает с количеством палуб
        // корабля, то он убит - прибавляем число 7
        if (countInjured==countPalubs) {
            for (int x=i-(countPalubs-1);x<=i+(countPalubs-1);x++) {
                for (int y=j-(countPalubs-1);y<=j+(countPalubs-1);y++) {
                // Если это палуба раненого корабля
                    if (ifMasOut(x, y)&&(mas[x][y]==countPalubs+7)) {
                        // помечаем палубой убитого корабля
                        mas[x][y]+=7;
                        // уменьшаем на 1 окружение убитого корабля
                        deathRing(mas, x, y);
                    }
                }
            }
        }
    }

    public void setDeathRing(int mas[][], int i, int j){ // Метод уменьшает на 1 значение массива, если значения равны -1 или 6
        if (ifMasOut(i, j)){
            if (mas[i][j]==-1 || mas[i][j]==6){
                mas[i][j]--;
            }
        }
    }

    private void deathRing(int[][] mas, int i, int j) { // метод, который уменьшает на 1 окружение всего убитого корабля
        setDeathRing(mas, i - 1, j - 1); // сверху слева
        setDeathRing(mas, i - 1, j); // сверху
        setDeathRing(mas, i - 1, j + 1); // сверху справа
        setDeathRing(mas, i, j + 1); // справа
        setDeathRing(mas, i + 1, j + 1); // снизу справа
        setDeathRing(mas, i + 1, j); // снизу
        setDeathRing(mas, i + 1, j - 1); // снизу слева
        setDeathRing(mas, i, j - 1); // слева
    }

    boolean computerMove(int mas[][]) { // Выстрел комьютера
        // если попал, иначе false
        //если идет автоигра или ход компьютера
        if ((endGame == 0 && PvE) || (computerMove && !PvE)) {
            //увеличиваем на 1 количество ходов
            if (PvE == false) countComputerMove++;
            // Признак попадания в цель
            boolean hit = false;
            // Признак выстрела в раненый корабль
            boolean injured = false;
            //признак направления корабля
            boolean horizontal = false;
            _for1:
            // break метка
            // Пробегаем все игровое поле игрока
            for (int i = 0; i < 10; i++)
                for (int j = 0; j < 10; j++)
                    if ((mas[i][j] >= 9) && (mas[i][j] <= 11)) { //если находим раненую палубу
                        injured = true;
                        //ищем подбитую палубу слева и справа
                        if ((ifMasOut(i - 3, j) && mas[i - 3][j] >= 9 && (mas[i - 3][j] <= 11))
                                || (ifMasOut(i - 2, j) && mas[i - 2][j] >= 9 && (mas[i - 2][j] <= 11))
                                || (ifMasOut(i - 1, j) && mas[i - 1][j] >= 9 && (mas[i - 1][j] <= 11))
                                || (ifMasOut(i + 3, j) && mas[i + 3][j] >= 9 && (mas[i + 3][j] <= 11))
                                || (ifMasOut(i + 2, j) && mas[i + 2][j] >= 9 && (mas[i + 2][j] <= 11))
                                || (ifMasOut(i + 1, j) && mas[i + 1][j] >= 9 && (mas[i + 1][j] <= 11))) {
                            horizontal = true;
                        } else if ((ifMasOut(i, j + 3) && mas[i][j + 3] >= 9 && (mas[i][j + 3] <= 11))
                                //ищем подбитую палубу сверху и снизу
                                || (ifMasOut(i, j + 2) && mas[i][j + 2] >= 9 && (mas[i][j + 2] <= 11))
                                || (ifMasOut(i, j + 1) && mas[i][j + 1] >= 9 && (mas[i][j + 1] <= 11))
                                || (ifMasOut(i, j - 3) && mas[i][j - 3] >= 9 && (mas[i][j - 3] <= 11))
                                || (ifMasOut(i, j - 2) && mas[i][j - 2] >= 9 && (mas[i][j - 2] <= 11))
                                || (ifMasOut(i, j - 1) && mas[i][j - 1] >= 9 && (mas[i][j - 1] <= 11))) {
                            horizontal = false;
                        }
                        //если не удалось определить направление корабля, то выбираем произвольное направление для обстрела
                        else for (int x = 0; x < 50; x++) {
                            int napr = (int) (Math.random() * 4);
                            if (napr == 0 && ifMasOut(i - 1, j) && (mas[i - 1][j] <= 4) && (mas[i - 1][j] != -2)) {
                                mas[i - 1][j] += 7;
                                //проверяем, убили или нет
                                ifDeath(mas, i - 1, j);
                                if (mas[i - 1][j] >= 8) hit = true;
                                //прерываем цикл
                                break _for1;
                            } else if (napr == 1 && ifMasOut(i + 1, j) && (mas[i + 1][j] <= 4) && (mas[i + 1][j] != -2)) {
                                mas[i + 1][j] += 7;
                                ifDeath(mas, i + 1, j);
                                if (mas[i + 1][j] >= 8) hit = true;
                                break _for1;
                            } else if (napr == 2 && ifMasOut(i, j - 1) && (mas[i][j - 1] <= 4) && (mas[i][j - 1] != -2)) {
                                mas[i][j - 1] += 7;
                                ifDeath(mas, i, j - 1);
                                if (mas[i][j - 1] >= 8) hit = true;
                                break _for1;
                            } else if (napr == 3 && ifMasOut(i, j + 1) && (mas[i][j + 1] <= 4) && (mas[i][j + 1] != -2)) {
                                mas[i][j + 1] += 7;
                                ifDeath(mas, i, j + 1);
                                if (mas[i][j + 1] >= 8) hit = true;
                                break _for1;
                            }
                        }
                        //если определили направление, то производим выстрел только в этом напрвлении
                        if (horizontal) { //по горизонтали
                            if (ifMasOut(i - 1, j) && (mas[i - 1][j] <= 4) && (mas[i - 1][j] != -2)) {
                                mas[i - 1][j] += 7;
                                ifDeath(mas, i - 1, j);
                                if (mas[i - 1][j] >= 8) hit = true;
                                break _for1;
                            } else if (ifMasOut(i + 1, j) && (mas[i + 1][j] <= 4) && (mas[i + 1][j] != -2)) {
                                mas[i + 1][j] += 7;
                                ifDeath(mas, i + 1, j);
                                if (mas[i + 1][j] >= 8) hit = true;
                                break _for1;
                            }
                        }//по вертикали
                        else if (ifMasOut(i, j - 1) && (mas[i][j - 1] <= 4) && (mas[i][j - 1] != -2)) {
                            mas[i][j - 1] += 7;
                            ifDeath(mas, i, j - 1);
                            if (mas[i][j - 1] >= 8) hit = true;
                            break _for1;
                        } else if (ifMasOut(i, j + 1) && (mas[i][j + 1] <= 4) && (mas[i][j + 1] != -2)) {
                            mas[i][j + 1] += 7;
                            ifDeath(mas, i, j + 1);
                            if (mas[i][j + 1] >= 8) hit = true;
                            break _for1;
                        }
                    }

            // если нет ранненых кораблей
            if (injured == false) {

                // делаем 100 случайных попыток выстрела
                // в случайное место
                for (int l = 1; l <= 100; l++) {
                    // Находим случайную позицию на игровом поле
                    int i = (int) (Math.random() * 10);
                    int j = (int) (Math.random() * 10);
                    //Проверяем, что можно сделать выстрел
                    if ((mas[i][j] <= 4) && (mas[i][j] != -2)) {
                        //делаем выстрел
                        mas[i][j] += 7;
                        //проверяем, что убит
                        ifDeath(mas, i, j);
                        // если произошло попадание
                        if (mas[i][j] >= 8)
                            hit = true;
                        //выстрел произошел
                        injured = true;
                        //прерываем цикл
                        break;
                    }
                }
            }
            // проверяем конец игры
            ifEndGame();
            // возвращаем результат
            return hit; //true, если попал, иначе false
        }else return false;
    }

    private boolean ifMasOut(int i, int j) { // проверка выхода за пределы массива
        if (((i >= 0) && (i <= 9)) && ((j >= 0) && (j <= 9))) {
            return true;
        } else return false;
    }

    private void setOkr(int[][] mas, int i, int j, int val) { // Вспомогательный метод для устаноки окружения корабля,
        // Контролирует выход за пределы массива
        if (ifMasOut(i, j) && mas[i][j] == 0) {
            mas[i][j] = val;
        }
    }

    private void okrBegin(int[][] mas, int i, int j, int val) { // перебирает все ячейки вокруг и устанавливает в них нужное значение.
        //setOkr() - !выход за границы массива.
        //val значение, которое нужно установить
        setOkr(mas, i - 1, j - 1, val);
        setOkr(mas, i - 1, j, val);
        setOkr(mas, i - 1, j + 1, val);
        setOkr(mas, i, j + 1, val);
        setOkr(mas, i, j - 1, val);
        setOkr(mas, i + 1, j + 1, val);
        setOkr(mas, i + 1, j, val);
        setOkr(mas, i + 1, j - 1, val);
    }

    public boolean handSetPalubs(int i, int j, int countPalubs, boolean napr){ // Ручная установка кораблей
        // napr направление расстановки
        //признак установки палубы
        boolean flag = false;
        // Если можно расположить палубу
        if (ifNewPalubs(masPlay, i, j) == true) {
            if (napr==false){ // вправо
                if (ifNewPalubs(masPlay, i, j + (countPalubs - 1)) == true)
                    flag = true;
            }
            else if (napr){ // вниз
                if (ifNewPalubs(masPlay, i + (countPalubs - 1), j) == true)
                    flag = true;
            }
        }
        if (flag == true) {
            //Помещаем в ячейку число палуб
            masPlay[i][j] = countPalubs;
            // Окружаем минус двойками
            okrBegin(masPlay, i, j, -2);
            if (napr==false){ // вправо
                masPlayOrientational[i][j] = 1; // вправо
                for (int k = countPalubs - 1; k >= 1; k--) {
                    masPlay[i][j + k] = countPalubs;
                    okrBegin(masPlay, i, j + k, -2);
                }
            }
            else if (napr){ // вниз
                masPlayOrientational[i ][j] = 0; // вниз
                for (int k = countPalubs - 1; k >= 1; k--) {
                    masPlay[i + k][j] = countPalubs;
                    okrBegin(masPlay, i + k, j, -2);
                }
            }
        }
        okrEnd(masPlay); //заменяем -2 на -1
        return flag; // возвращает true, если палуба расставлена
    }

    private boolean ifNewPalubs(int [][]mas, int i, int j) { // выполняет проверку выхода за границы массива и значение в ячейке,в которой мы хотим разместить новую палубу.
        //Если это значения 0 или -2, то мы можем разместить там новую палубу.
        if (ifMasOut(i, j) == false) return false;
        if ((mas[i][j] == 0) || (mas[i][j] == -2)) return true;
        else return false;
    }

    private void okrEnd(int[][] mas) { // Если значение элемента массива -2, то заменяем его на -1

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (mas[i][j] == -2)
                    mas[i][j] = -1;
            }
        }
    }

    private void autoSetPalubs(int [][]mas, int countPalubs, int status){ // Генерация палуб
        // Выбираем случайное направление построения корабля. 0 - вверх, 1 - вправо, 2 - вниз, 3 - влево
        int i = 0, j = 0;
        while (true) {
            boolean flag = false;
            i = (int) (Math.random() * 10);
            j = (int) (Math.random() * 10);
            int napr = (int) (Math.random() * 2); // 0 - вверх. 1 - вправо. 2 - вниз. 3 - влево

            // Если можно расположить палубу
            if (ifNewPalubs(mas, i, j) == true) {
                if (napr == 0) { //вверх
                    if (ifNewPalubs(mas, i -(countPalubs - 1), j) == true)  //если можно расположить палубу вверх, то flag = true
                        flag = true;
                }
                else if (napr == 1){ // вправо
                    if (ifNewPalubs(mas, i, j + (countPalubs - 1)) == true)
                        flag = true;
                }
                else if (napr == 2){ // вниз
                    if (ifNewPalubs(mas, i + (countPalubs - 1), j) == true)
                        flag = true;
                }
                else if (napr == 3){ // влево
                    if (ifNewPalubs(mas, i, j -(countPalubs - 1)) == true)
                        flag = true;
                }
            }
            if (flag == true) {
                //Помещаем в ячейку число палуб
                mas[i][j] = countPalubs;
                // Окружаем минус двойками
                okrBegin(mas, i, j, -2);
                if (napr == 0) {// вверх
                    int k = countPalubs - 1;
                    if (status == 0)
                        masPlayOrientational[i - k][j] = 0; // вверх
                    for (; k >= 1; k--) {
                        mas[i - k][j] = countPalubs;
                        okrBegin(mas, i - k, j, -2);
                    }

                }
                else if (napr == 1){ // вправо
                    if (status == 0)
                        masPlayOrientational[i][j] = 1; // вправо
                    for (int k = countPalubs - 1; k >= 1; k--) {
                        mas[i][j + k] = countPalubs;
                        okrBegin(mas, i, j + k, -2);
                    }
                }
                else if (napr == 2){ // вниз
                    if (status == 0)
                        masPlayOrientational[i][j] = 0; // вниз
                    for (int k = countPalubs - 1; k >= 1; k--) {
                     mas[i + k][j] = countPalubs;
                     okrBegin(mas, i + k, j, -2);
                    }
                }
                else if (napr == 3){ // влево
                    int k = countPalubs - 1;
                    if (status == 0)
                        masPlayOrientational[i][j - k] = 1; // влево
                    for (; k >= 1; k--) {
                        mas[i][j - k] = countPalubs;
                        okrBegin(mas, i, j - k, -2);
                    }

                }
                //прерываем цикл
                break;
            }
        }
        okrEnd(mas); //заменяем -2 на -1
    }

    private void setPalubsPlayer(){ // Метод для расстаноки всех кораблей для игрока

        autoSetPalubs(masPlay, 4, 0);
        for (int i = 1; i <= 2; i++) {
            autoSetPalubs(masPlay, 3, 0);
        }
        for (int i = 1; i <= 3; i++) {
            autoSetPalubs(masPlay, 2, 0);
        }
        for (int i = 1;i<= 4;i++){
            autoSetPalubs(masPlay,1, 0);
        }
    }

    private void setPalubsComputer(){ // Метод для расстаноки всех кораблей для компа
        autoSetPalubs(masComp, 4, 1);
        for (int i = 1; i <= 2; i++) {
            autoSetPalubs(masComp, 3, 1);
        }
        for (int i = 1; i <= 3; i++) {
            autoSetPalubs(masComp, 2, 1);
        }
        for (int i = 1;i<= 4;i++){
            autoSetPalubs(masComp,1, 1);
        }
    }
}