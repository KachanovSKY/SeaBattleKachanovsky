import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class SaveProgress {
    static Logger logger;

    public static void LoadFile(String path) throws IOException{

        String tmp = new String();
        String[] buffer = new String[]{};

        File file = new File(path);
        try (BufferedReader br = new BufferedReader(new FileReader(file)))
        {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.contains("#masPlay")){
                    br.readLine();
                    for (int i = 0; i < 10; i++){
                        line = br.readLine();
                        buffer = line.split(", ");
                        for (int j = 0; j < 10; j++){
                            Game.masPlay[i][j] = Integer.parseInt(buffer[j]);
                        }
                    }
                }
                if (line.contains("#masOrientational")){
                    br.readLine();
                    for (int i = 0; i < 10; i++){
                        line = br.readLine();
                        buffer = line.split(", ");
                        for (int j = 0; j < 10; j++){
                            Game.masPlayOrientational[i][j] = Integer.parseInt(buffer[j]);
                        }
                    }
                }
                if (line.contains("#masComp")){
                    br.readLine();
                    for (int i = 0; i < 10; i++){
                        line = br.readLine();
                        buffer = line.split(", ");
                        for (int j = 0; j < 10; j++){
                            Game.masComp[i][j] = Integer.parseInt(buffer[j]);

                        }
                    }
                }
                if (line.contains("#P1")){
                    br.readLine();
                    line = br.readLine();

                    tmp = line;
                    Game.P1 = Integer.parseInt(line);
                }
                if (line.contains("#P2")){
                    br.readLine();
                    line = br.readLine();

                    tmp = line;
                    Game.P2 = Integer.parseInt(line);
                }
                if (line.contains("#P3")){
                    br.readLine();
                    line = br.readLine();

                    tmp = line;
                    Game.P3 = Integer.parseInt(line);
                }
                if (line.contains("#P4")){
                    br.readLine();
                    line = br.readLine();

                    tmp = line;
                    Game.P4 = Integer.parseInt(line);
                }
                if (line.contains("#C1")){
                    br.readLine();
                    line = br.readLine();

                    tmp = line;
                    Game.C1 = Integer.parseInt(line);
                }
                if (line.contains("#C2")){
                    br.readLine();
                    line = br.readLine();

                    tmp = line;
                    Game.C2 = Integer.parseInt(line);

                }
                if (line.contains("#C3")){
                    br.readLine();
                    line = br.readLine();

                    tmp = line;
                    Game.C3 = Integer.parseInt(line);
                }
                if (line.contains("#C4")){
                    br.readLine();
                    line = br.readLine();

                    tmp = line;
                    Game.C4 = Integer.parseInt(line);
                }

                if (line.contains("#countPlayerMove")){
                    br.readLine();
                    line = br.readLine();

                    tmp = line;
                    Game.countPlayerMove = Integer.parseInt(line);

                }
                if (line.contains("#countComputerMove")){
                    br.readLine();
                    line = br.readLine();

                    tmp = line;
                    Game.countComputerMove = Integer.parseInt(line);
                }
                if (line.contains("#myMove")){
                    br.readLine();
                    line = br.readLine();

                    tmp = line;
                    Game.myMove = Boolean.parseBoolean(line);
                }
                if (line.contains("#computerMove")){
                    br.readLine();
                    line = br.readLine();

                    tmp = line;
                    Game.computerMove = Boolean.parseBoolean(line);

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void SaveFile(String path) throws IOException
    {
        int tmpC1 = Game.C1,
                tmpC2 = Game.C2,
                tmpC3 = Game.C3,
                tmpC4 = Game.C4; // ???????????????????? ???????????????????? ???????????????? ????????????????????

        int tmpP1 = Game.P1,
                tmpP2 = Game.P2,
                tmpP3 = Game.P3,
                tmpP4 = Game.P4; // ???????????????????? ???????????????????? ???????????????? ????????????

        int tmpCountPlayerMove = Game.countPlayerMove;
        int tmpCountComputerMove = Game.countComputerMove;
        boolean tmpMyMove = Game.myMove; // true - ???????? ???????????? ?????? ????????????
        boolean tmpComputerMove = Game.computerMove;

        int tmpMas[][] = Game.masPlay;
        String tmp = new String();

        // ???????????? ??????-???? ????????????????
        try (FileWriter filewriter = new FileWriter(path)) {

            filewriter.write("\n\n#masPlay\n\n");
            for (int i=0;i<10;++i){
                for (int j=0;j<10;++j) {
                    tmp = String.valueOf(tmpMas[i][j]);
                    filewriter.write(tmp);
                    if (j < 9){
                        filewriter.write(", ");
                    }
                }
                filewriter.write("\n");
            }

            tmpMas = Game.masPlayOrientational;
            filewriter.write("\n\n#masOrientational\n\n");
            for (int i=0;i<10;++i){
                for (int j=0;j<10;++j) {
                    tmp = String.valueOf(tmpMas[i][j]);
                    filewriter.write(tmp);
                    if (j < 9){
                        filewriter.write(", ");
                    }
                }
                filewriter.write("\n");
            }

            tmpMas = Game.masComp;
            filewriter.write("\n\n#masComp\n\n");
            for (int i=0;i<10;++i){
                for (int j=0;j<10;++j) {
                    tmp = String.valueOf(tmpMas[i][j]);
                    filewriter.write(tmp);
                    if (j < 9){
                        filewriter.write(", ");
                    }
                }
                filewriter.write("\n");
            }

            filewriter.write("\n\n#P1\n\n");
            filewriter.write(String.valueOf(tmpP1));
            filewriter.write("\n");

            filewriter.write("\n\n#P2\n\n");
            filewriter.write(String.valueOf(tmpP2));
            filewriter.write("\n");

            filewriter.write("\n\n#P3\n\n");
            filewriter.write(String.valueOf(tmpP3));
            filewriter.write("\n");

            filewriter.write("\n\n#P4\n\n");
            filewriter.write(String.valueOf(tmpP4));
            filewriter.write("\n");


            filewriter.write("\n\n#C1\n\n");
            filewriter.write(String.valueOf(tmpC1));
            filewriter.write("\n");

            filewriter.write("\n\n#C2\n\n");
            filewriter.write(String.valueOf(tmpC2));
            filewriter.write("\n");

            filewriter.write("\n\n#C3\n\n");
            filewriter.write(String.valueOf(tmpC3));
            filewriter.write("\n");

            filewriter.write("\n\n#C4\n\n");
            filewriter.write(String.valueOf(tmpC4));
            filewriter.write("\n");


            filewriter.write("\n\n#countPlayerMove\n\n");
            filewriter.write(String.valueOf(tmpCountPlayerMove));
            filewriter.write("\n");

            filewriter.write("\n\n#countComputerMove\n\n");
            filewriter.write(String.valueOf(tmpCountComputerMove));
            filewriter.write("\n");


            filewriter.write("\n\n#myMove\n\n");
            filewriter.write(String.valueOf(tmpMyMove));
            filewriter.write("\n");

            filewriter.write("\n\n#computerMove\n\n");
            filewriter.write(String.valueOf(tmpComputerMove));
            filewriter.write("\n");
        }
    }

    public static void genLogFile(){
        SimpleDateFormat format = new SimpleDateFormat("d-M-y_HH.mm.ss");
        logger = Logger.getLogger(Game.class.getName());
        FileHandler fh;
        try {
            fh = new FileHandler("/Users/vladislavkacanovskij/Documents/??????????????????/IdeaProjects/seaBattle-master/log_files/Log_" +
                    format.format(Calendar.getInstance().getTime()).toString() + ".log");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        logger.addHandler(fh);
        SimpleFormatter formatter = new SimpleFormatter();
        fh.setFormatter(formatter);
    }

    public static void logFile(String message){

        try {
            logger.info(message);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }
}