import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class MyPanel extends JPanel {
    private final int DXY = 60;
    private final int H = 34;
    private String number[] = {"А", "Б", "В", "Г", "В", "Е", "Ж", "З", "И", "К"};
    private Game game; // для реализации логики игры
    private int mX, mY; //коорд мыши
    private Timer timer; //Таймер отрисовки и изменения логики игры
    //Изображения, используемые в игре
    private BufferedImage  ranen, boom,killed,paluba, water,x4Ship, x3Ship, x2Ship, x1Ship,y4Ship, y3Ship, y2Ship, y1Ship; //Изображения, используемые в игре
    private Image background;
    private Rectangle2D line4,line3,line2,line1;
    private boolean isSelectP4=false;
    private boolean isSelectP3=false;
    private boolean isSelectP2=false;
    private boolean isSelectP1=false;
    private int p4,p3,p2,p1;
    public boolean vert=true; //направление расстановки
    private JButton checkNapr;
    public static boolean rasstanovka;


    public MyPanel() {
        addMouseListener(new Mouse());
        addMouseMotionListener(new Mouse());
        setFocusable(true);
        game = new Game();
        setSize(1200,800);
        try {
            ranen = ImageIO.read(getClass().getResource("image/ranen.png"));
            boom = ImageIO.read(getClass().getResource("image/boom.png"));
            killed = ImageIO.read(getClass().getResource("image/killed.png"));
            paluba = ImageIO.read(getClass().getResource("image/paluba.png"));
            background = ImageIO.read(getClass().getResource("image/bg.png"));
            water = ImageIO.read(getClass().getResource("image/water.png"));

            x4Ship = ImageIO.read(getClass().getResource("image/4xShipX.png"));
            x3Ship = ImageIO.read(getClass().getResource("image/3xShipX.png"));
            x2Ship = ImageIO.read(getClass().getResource("image/2xShipX.png"));
            x1Ship = ImageIO.read(getClass().getResource("image/1xShipX.png"));

            y4Ship = ImageIO.read(getClass().getResource("image/4xShipY.png"));
            y3Ship = ImageIO.read(getClass().getResource("image/3xShipY.png"));
            y2Ship = ImageIO.read(getClass().getResource("image/2xShipY.png"));
            y1Ship = ImageIO.read(getClass().getResource("image/1xShipY.png"));

        } catch (IOException e) {e.printStackTrace();}
        //Создаем, настраиваем таймер отрисовки
        timer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                repaint();
            }
        });
        setLayout(null);
        checkNapr=new JButton("Повернуть");
//        checkNapr.setBackground();
        checkNapr.setBounds(DXY+24*H,DXY+8*H,7*H,H);
        checkNapr.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (vert)vert=false;
                else vert=true;
            }
        });
        add(checkNapr);
        checkNapr.setVisible(false);
    }


    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2= (Graphics2D) g;
        g2.setStroke(new BasicStroke(1));
//        g.setColor(new Color(0, 248, 255));
        g.drawImage(background, 0,0,this);
        //g.fillRect(0, 0, getWidth(), getHeight());
        g.setFont(new Font("Times New Roman", 0, H - 10));
        g.setColor(new Color(0xDE9B6B));
        if (rasstanovka) {
            g2.setStroke(new BasicStroke(2));
            if (vert) {
                line4 = new Rectangle2D.Double(DXY + 24 * H, DXY, 4 * H, H);
                line3 = new Rectangle2D.Double(DXY + 24 * H, DXY + 2 * H, 3 * H, H);
                line2 = new Rectangle2D.Double(DXY + 24 * H, DXY + 4 * H, 2 * H, H);
                line1 = new Rectangle2D.Double(DXY + 24 * H, DXY + 6 * H, 1 * H, H);
            } else {
                line4 = new Rectangle2D.Double(DXY + 24 * H, DXY, H, 4 * H);
                line3 = new Rectangle2D.Double(DXY + 26 * H, DXY, H, 3 * H);
                line2 = new Rectangle2D.Double(DXY + 28 * H, DXY, H, 2 * H);
                line1 = new Rectangle2D.Double(DXY + 30 * H, DXY, H, 1 * H);
            }

            if (p4 != 0) {
                ((Graphics2D) g).draw(line4);
                if (vert)
                    g.drawImage(x4Ship, DXY + 24 * H,  DXY, 4 * H, H, null);
                else
                    g.drawImage(y4Ship, DXY + 24 * H, DXY, H, 4 * H, null);
            }
            if (p3 != 0) {
                ((Graphics2D) g).draw(line3);
                if (vert)
                    g.drawImage(x3Ship, DXY + 24 * H, DXY + 2 * H, 3 * H, H, null);
                else
                    g.drawImage(y3Ship, DXY + 26 * H, DXY, H, 3 * H, null);
            }
            if (p2 != 0) {
                ((Graphics2D) g).draw(line2);
                if (vert)
                    g.drawImage(x2Ship, DXY + 24 * H, DXY + 4 * H, 2 * H, H, null);
                else
                    g.drawImage(y2Ship, DXY + 28 * H, DXY, H, 2 * H, null);
            }
            if (p1 != 0) {
                ((Graphics2D) g).draw(line1);
                if (vert)
                    g.drawImage(x1Ship, DXY + 24 * H, DXY + 6 * H, 1 * H, H, null);
                else
                    g.drawImage(y1Ship, DXY + 30 * H, DXY, H, 1 * H, null);
            }
            if ((p1+p2+p3+p4)!=0) {
                g.drawString("Расставьте корабли", DXY + 24 * H, DXY-H);
                checkNapr.setVisible(true);
            }
            else {
                checkNapr.setVisible(false);
            }
        }
        //Выведение надписей
        g.drawString("Игрок", DXY + 4 * H, DXY - H);
        g.drawString("Компьютер", DXY + 16 * H, DXY - H);
        g.drawString("Ходов игрока: ", DXY + 24 * H, DXY + 18 * H - (H/4));
        g.drawString(String.valueOf(game.countPlayerMove), DXY + 29 * H, DXY + 18 * H - (H / 4));
        g.drawString("Ходов комьютера: ", DXY + 24 * H, DXY + 19 * H - (H/4));
        g.drawString(String.valueOf(game.countComputerMove), DXY + 30 * H + (H / 2), DXY + 19 * H - (H / 4));

        //Выводим цифры и буквы
        for (int i = 1; i <= 10; i++) {
            //12345678910
            g.drawString(String.valueOf(i), DXY - H, DXY + i * H - (H / 4));
            g.drawString(String.valueOf(i), DXY + 12 * H, DXY + i * H - (H / 4));
            //абвгдежзик
            g.drawString(number[i-1], DXY + (i-1) * H + (H / 4), DXY - 3);
            g.drawString(number[i-1], 13 * H + DXY + (i-1) * H + (H / 4), DXY - 3);
        }

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                g.drawImage(water, DXY + 13 * H + H * i, DXY + H * j, H, H, null);
                g.drawImage(water, DXY + H * i, DXY + H * j, H, H, null);
            }
        }
        //отрисовка игрового поля на основании массива
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                //корабли противника
                if (game.masComp[i][j]!=0) {
                    //если игра пк против пк, то показываем палубы комьютера
                    if ((game.masComp[i][j] >= 1) && (game.masComp[i][j] <= 4 && Game.PvE))
                        g.drawImage(paluba, DXY + 13 * H + H * i, DXY + H * j, H, H, null);
                    //Если это палуба раненного корабля, то выводим соотвествующее изображение
                    else if ((game.masComp[i][j] >= 8) && (game.masComp[i][j] <= 11))
                        g.drawImage(ranen, DXY + 13 * H + H * i, DXY + H * j, H, H, null);
                    else if ((game.masComp[i][j] >= 15))
                        //рисуем палубу убитого корабля
                        g.drawImage(killed, DXY + 13 * H + H * i, DXY + H * j, H, H, null);
                     else if ((game.masComp[i][j] >= 5 && game.masComp[i][j] < 8 || game.masComp[i][j] == -2))
                        //если выстрел мимо и это окружение убитого корабля
                        g.drawImage(boom, DXY + 13 * H + H * i, DXY + H * j, H, H, null);
                    else if (Game.endGame != 0 && (game.masComp[i][j] >= 1 && game.masComp[i][j] <= 4)) {
                        //показываем корабли после конца игры
                        g.drawImage(paluba, DXY + 13 * H + H * i, DXY + H * j, H, H, null);
                        g.setColor(new Color(0x8B0000));
                        g.drawRect(DXY + 13 * H + H * i, DXY + H * j, H, H);
                    }
                }
                //корабли игрока
                if (game.masPlay[i][j]!=0){
                    if ((game.masPlay[i][j] >= 1) && (game.masPlay[i][j] <= 4) || (game.masPlay[i][j] >= 8) && (game.masPlay[i][j] <= 11)) {
                        //палуба
//                        g.drawImage(paluba, DXY + H * i, DXY + H * j, H, H, null);
                        if ((game.masPlay[i][j] == 1 || game.masPlay[i][j] == 8)&& game.masPlayOrientational[i][j] == 1){
                            g.drawImage(y1Ship, DXY + H * i, DXY + H * j, H, H, null);
                            if (game.masPlay[i][j] == 8)
                                g.drawImage(ranen, DXY + H * i, DXY + H * j, H, H, null);

                        }else if ((game.masPlay[i][j] == 2 || game.masPlay[i][j] == 9) && game.masPlayOrientational[i][j] == 1){
                            g.drawImage(y2Ship, DXY + H * i, DXY + H * j, H, H * 2, null);
                            if (game.masPlay[i][j] == 9)
                                g.drawImage(ranen, DXY + H * i, DXY + H * j, H, H, null);

                        }else if ((game.masPlay[i][j] == 3 || game.masPlay[i][j] == 10) && game.masPlayOrientational[i][j] == 1){
                            g.drawImage(y3Ship, DXY + H * i, DXY + H * j, H, H * 3, null);
                            if (game.masPlay[i][j] == 10)
                                g.drawImage(ranen, DXY + H * i, DXY + H * j, H, H, null);

                        }else if ((game.masPlay[i][j] == 4 || game.masPlay[i][j] == 11) && game.masPlayOrientational[i][j] == 1){
                            g.drawImage(y4Ship, DXY + H * i, DXY + H * j, H, H * 4, null);
                            if (game.masPlay[i][j] == 11)
                                g.drawImage(ranen, DXY + H * i, DXY + H * j, H, H, null);

                        }else if ((game.masPlay[i][j] == 1 || game.masPlay[i][j] == 8) && game.masPlayOrientational[i][j] == 0){
                            g.drawImage(x1Ship, DXY + H * i, DXY + H * j, H, H, null);
                            if (game.masPlay[i][j] == 8)
                                g.drawImage(ranen, DXY + + H * i, DXY + H * j, H, H, null);

                        }else if ((game.masPlay[i][j] == 2 || game.masPlay[i][j] == 9) && game.masPlayOrientational[i][j] == 0){
                            g.drawImage(x2Ship, DXY + H * i, DXY + H * j, H * 2, H, null);
                            if (game.masPlay[i][j] == 9)
                                g.drawImage(ranen, DXY + H * i, DXY + H * j, H, H, null);

                        }else if ((game.masPlay[i][j] == 3 || game.masPlay[i][j] == 10) && game.masPlayOrientational[i][j] == 0){
                            g.drawImage(x3Ship, DXY + H * i, DXY + H * j, H * 3, H, null);
                            if (game.masPlay[i][j] == 10)
                                g.drawImage(ranen, DXY + H * i, DXY + H * j, H, H, null);

                        }else if ((game.masPlay[i][j] == 4 || game.masPlay[i][j] == 11) && game.masPlayOrientational[i][j] == 0){
                            g.drawImage(x4Ship, DXY + H * i, DXY + H * j, H * 4, H, null);
                            if (game.masPlay[i][j] == 11)
                                g.drawImage(ranen, DXY + H * i, DXY + H * j, H, H, null);
                        }
//                    }else if ((game.masPlay[i][j] >= 8) && (game.masPlay[i][j] <= 11)) {
//                        //ранен
//                        g.drawImage(ranen, DXY + + H * i, DXY + H * j, H, H, null);
                    }else if ((game.masPlay[i][j] >= 15)) {
                        //убит
                        g.drawImage(killed, DXY + H * i, DXY + H * j, H, H, null);
                    }else if ((game.masPlay[i][j] >= 5) && game.masPlay[i][j]<8) {
                        //мимо
                        g.drawImage(boom, DXY +  + H * i, DXY + H * j, H, H, null);
                    }else if (Game.PvE && game.masPlay[i][j] ==-2){
                        //окружения убитого в автоигре
                        g.drawImage(boom, DXY +  + H * i, DXY + H * j, H, H, null);
                    }
                }
            }
        }


        //линии
        for (int i = DXY; i <= DXY + 10 * H; i += H) {
            g2.setStroke(new BasicStroke(1));
            g.setColor(new Color(108, 178, 203));
            g.drawLine(DXY, i, DXY + 10 * H, i); // ----
            g.drawLine(i, DXY, i, DXY + 10 * H);
            g.drawLine(DXY + 13 * H, i, DXY + 23 * H, i); //бот ---
            g.drawLine(i + 13 * H, DXY, i + 13 * H, DXY + 10 * H);

            g2.setStroke(new BasicStroke(2));
            g.setColor(new Color(0xFFFFFF));
            g.drawRect(DXY, DXY, 10 * H, 10 * H);
            g.drawRect(DXY+13*H,DXY,10 * H,10 * H);
        }

        g.setFont(new Font("Times New Roman", 0, H));
        g.setColor(new Color(0xFFFFFF));

        //количество кораблей игрока
        g.drawRect(DXY, DXY + 11 * H, 4 * H, H);
        g.drawImage(x4Ship, DXY, DXY + 11 * H, 4 * H, H, null);
        ((Graphics2D) g).drawString(String.valueOf(1 - game.C4), DXY + 5 * H, DXY + 12 * H - (H / 4));
        g.drawRect(DXY, DXY + 12 * H + 10, 3 * H, H);
        g.drawImage(x3Ship, DXY, DXY + 12 * H + 10, 3 * H, H, null);
        ((Graphics2D) g).drawString(String.valueOf(2 - game.C3), DXY + 4 * H, DXY + 13 * H + 10);
        g.drawRect(DXY, DXY + 13 * H + 20, 2 * H, H);
        g.drawImage(x2Ship, DXY, DXY + 13 * H + 20, 2 * H, H, null);
        ((Graphics2D) g).drawString(String.valueOf(3 - game.C2), DXY + 3 * H, DXY + 14 * H + 20);
        g.drawRect(DXY, DXY + 14 * H + 30, H, H);
        g.drawImage(x1Ship, DXY, DXY + 14 * H + 30, 1 * H, H, null);
        ((Graphics2D) g).drawString(String.valueOf(4 - game.C1), DXY + 2 * H, DXY + 15 * H + 30);

        g.drawRect(DXY + 13 * H, DXY + 11 * H, 4 * H, H);//4 палуб
        g.drawImage(x4Ship, DXY + 13 * H, DXY + 11 * H, 4 * H, H, null);
        ((Graphics2D) g).drawString(String.valueOf(1 - game.P4), DXY + 18 * H, DXY + 12 * H - (H / 4));
        g.drawRect(DXY + 13 * H, DXY + 12 * H + 10, 3 * H, H);  //3
        g.drawImage(x3Ship, DXY + 13 * H, DXY + 12 * H + 10, 3 * H, H, null);
        ((Graphics2D) g).drawString(String.valueOf(2 - game.P3), DXY + 17 * H, DXY + 13 * H + 10);
        g.drawRect(DXY + 13 * H, DXY + 13 * H + 20, 2 * H, H);//2
        g.drawImage(x2Ship, DXY + 13 * H, DXY + 13 * H + 20, 2 * H, H, null);
        ((Graphics2D) g).drawString(String.valueOf(3 - game.P2), DXY + 16 * H, DXY + 14 * H + 20);
        g.drawRect(DXY + 13 * H, DXY + 14 * H + 30, 1 * H, H);
        g.drawImage(x1Ship, DXY + 13 * H, DXY + 14 * H + 30, 1 * H, H, null);
        ((Graphics2D) g).drawString(String.valueOf(4 - game.P1), DXY + 15 * H, DXY + 15 * H + 30);

        if (Game.endGame==0 && (p1+p2+p3+p4)==0 && rasstanovka || Game.endGame==0 && !rasstanovka){
            g.setFont(new Font("Times New Roman", 0, H - 5));
            if (game.myMove) {
                g.setColor(Color.green);
                g.drawString("Ход игрока", DXY + 24 * H, DXY + 17 * H - (H / 4));
            }
            else {
                g.setColor(Color.red);
                g.drawString("Ходит компьютер", DXY + 24 * H, DXY + 17 * H - (H / 4));
            }
        }if (Game.endGame == 1) {
            timer.stop();
        }if (Game.endGame == 2) {
            timer.stop();
        }
    }

    public void setEasy(){
        //Доработка алгоритма
    }
    public void setMedium(){
        //Доработка алгоритма
    }
    public void setHard(){
        //Доработка алгоритма
    }

    public void start() {
        rasstanovka = false;
        Game.PvE = false;
        checkNapr.setVisible(false);
        timer.start();
        game.start();
    }

    public void startRasstanovka(){
        rasstanovka = true;
        timer.start();
        game.start();
        p1 = 4;
        p2 = 3;
        p3 = 2;
        p4 = 1;
    }

    public void exit() {
        System.exit(0);
    }

    public class Mouse implements MouseListener,MouseMotionListener {
        @Override
        public void mouseClicked(MouseEvent e) {

        }

        @Override
        public void mousePressed(MouseEvent e) {
            // Если сделано одиночное нажатие левой клавишей мыши
            if ((e.getButton() == 1) && (e.getClickCount() == 1)) {
                mX = e.getX();
                mY = e.getY();
                if ((rasstanovka && p1+p2+p3+p4==0) || !rasstanovka && !Game.PvE
                        && mX > (DXY + 13 * H) && mY > (DXY) && mX < (DXY + 23 * H) && mY < DXY + 10 * H) {
                    //если внутри поля бота и если не конец игры и ход игрока
                    if (game.myMove && Game.endGame ==0 && !game.computerMove){
                        //то вычисляем элемент массива:
                       int i=(mX-(DXY+13*H))/H;
                       int j=(mY-DXY)/H;
                        if ((i>=0 && i<=9) && (j>=0 && j<=9)) {
                            // System.out.println("Мы нажали на " + i+ " " +j);
                            if (game.masComp[i][j] <= 4 && game.masComp[i][j] >= -1) {
                                //-1 это окружение не убитого корабля
                                game.attack(game.masComp, i, j);
                            }
                        }
                    }
                }
            }
            if (rasstanovka){
                if (line4.contains(e.getPoint())){
                    isSelectP4 =true;isSelectP3 =false;isSelectP2 =false;isSelectP1 =false;
                }if (line3.contains(e.getPoint())){
                    isSelectP4 =false;isSelectP3 =true;isSelectP2 =false;isSelectP1 =false;
                }if (line2.contains(e.getPoint())){
                    isSelectP4 =false;isSelectP3 =false;isSelectP2 =true;isSelectP1 =false;
                }if (line1.contains(e.getPoint())){
                    isSelectP4 =false;isSelectP3 =false;isSelectP2 =false;isSelectP1 =true;
                }
            }
        }

        @Override
        /**
         * Клавиша мыши отпущена
         */
        public void mouseReleased(MouseEvent e) {
            if (rasstanovka) {
                mX = e.getX();
                mY = e.getY();
                int i = (mX - (DXY)) / H;
                int j = (mY - DXY) / H;
                if (p4 != 0 && isSelectP4 && mX > (DXY) && mY > (DXY) && mX < (DXY + 10 * H) && mY < DXY + 10 * H) {
                    isSelectP4 = false;
                    //line4 = new Rectangle2D.Double(DXY + 24 * H, DXY, 4 * H, H);
                    if (game.handSetPalubs(i, j, 4, vert)) {
                        p4--;
                    }

                } else if (p3 != 0 && isSelectP3 && mX > (DXY) && mY > (DXY) && mX < (DXY + 10 * H) && mY < DXY + 10 * H) {
                    isSelectP3 = false;
                   // line3 = new Rectangle2D.Double(DXY + 24 * H, DXY + 2 * H, 3 * H, H);
                    if (game.handSetPalubs(i, j, 3, vert)) {
                        p3--;
                    }

                } else if (p2 != 0 && isSelectP2 && mX > (DXY) && mY > (DXY) && mX < (DXY + 10 * H) && mY < DXY + 10 * H) {
                    isSelectP2 = false;
                    //line2 = new Rectangle2D.Double(DXY + 24 * H, DXY + 4 * H, 2 * H, H);
                    if (game.handSetPalubs(i, j, 2, vert)) {
                        p2--;
                    }

                } else if (p1 != 0 && isSelectP1 && mX > (DXY) && mY > (DXY) && mX < (DXY + 10 * H) && mY < DXY + 10 * H) {
                    isSelectP1 = false;
                    //line1 = new Rectangle2D.Double(DXY + 24 * H, DXY + 6 * H, 1 * H, H);
                    if (game.handSetPalubs(i, j, 1, vert)) {
                        p1--;
                    }
                }
            }
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }

        @Override
        public void mouseDragged(MouseEvent e) {
        }

        @Override
        public void mouseMoved(MouseEvent e) {
        }
    }
}