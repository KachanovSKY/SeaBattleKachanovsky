
public class Main {
    public static void main(String[] args) {
        System.out.println(Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory());
        Frame frame=new Frame();
        frame.setLocationRelativeTo(null);
    }
}
