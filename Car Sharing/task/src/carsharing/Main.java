package carsharing;



public class Main {

    public static void main(String[] args)  {
        Database database = new Database(args);
        Menu menu = new Menu(database);
        menu.run();
    }
}