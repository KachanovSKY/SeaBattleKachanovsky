import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class Frame extends JFrame {
    private JMenuBar menuBar;
    private JMenu menuGame;
    private JMenu menuFile;
    private JMenu menuGameStart;
    private JMenu difficultyGame;
    public JMenuItem menuFileSave;
    public JMenuItem menuFileLoad;
    public JMenuItem itemEasy;
    public JMenuItem itemMedium;
    public JMenuItem itemHard;
    private JMenuItem itemStartAuto;
    private JMenuItem itemStartRast;
    private JMenuItem itemExit;
    String path = new String();
    Frame() {
        super("Морской бой");
        MyPanel pole=new MyPanel();
        menuBar=new JMenuBar();
        menuGame = new JMenu("Игра");
        menuFile = new JMenu("Файл");
        difficultyGame = new JMenu("Сложность");
        menuGameStart = new JMenu("Новая игра");

        itemStartAuto =new JMenuItem("Авторасстановка");
        itemStartAuto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pole.start();
            }
        });
        itemStartRast = new JMenuItem("Расставить корабли");
        itemStartRast.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pole.startRasstanovka();
            }
        });
        itemExit=new JMenuItem("Выход");
        itemExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pole.exit();
            }
        });
        menuFileSave = new JMenuItem("Сохранить");
        menuFileSave.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                JFileChooser saveFile = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
                // restrict the user to select files of all types
                saveFile.setAcceptAllFileFilterUsed(false);
                // set a title for the dialog
                saveFile.setDialogTitle("Select a .txt file");
                // only allow files of .txt extension
                FileNameExtensionFilter restrict = new FileNameExtensionFilter("Text File (.txt)", ".txt");
                saveFile.addChoosableFileFilter(restrict);

                int r = saveFile.showSaveDialog(null);
                if (r == JFileChooser.APPROVE_OPTION) {
                    path = (saveFile.getSelectedFile().getAbsolutePath() + ".txt"); //Путь сохранения файлов
                    try {
                        SaveProgress.SaveFile(path);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });

        menuFileLoad = new JMenuItem("Загрузить");
        menuFileLoad.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser openFile = new JFileChooser("/Users/vladislavkacanovskij/Documents/Программы/IdeaProjects/seaBattle-master/saves");
                int r = openFile.showOpenDialog(null);
                if (r == JFileChooser.APPROVE_OPTION) {
                    path = openFile.getSelectedFile().getAbsolutePath(); //Пусть загрузки файлов
                    try {
                        SaveProgress.LoadFile(path);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });

        itemEasy=new JMenuItem("Easy");
        itemEasy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pole.setEasy(); }
        });
        itemMedium=new JMenuItem("Medium");
        itemMedium.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pole.setMedium();
            }
        });
        itemHard=new JMenuItem("Hard");
        itemHard.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pole.setHard();
            }
        });

        menuGameStart.add(itemStartAuto);
        menuGameStart.add(itemStartRast);
        menuGame.add(menuGameStart);
        menuGame.add(itemExit);
        menuFile.add(menuFileSave);
        menuFile.add(menuFileLoad);
        difficultyGame.add(itemEasy);
        difficultyGame.add(itemMedium);
        difficultyGame.add(itemHard);
        menuBar.add(menuGame);
        menuBar.add(menuFile);
        menuBar.add(difficultyGame);
        setJMenuBar(menuBar);
        Container container=getContentPane();
        container.add(pole);
        setSize(pole.getSize());
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        try {
            setIconImage(ImageIO.read(getClass().getResource("image/icon.jpeg")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        setVisible(true);
    }
}

