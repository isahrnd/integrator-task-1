package ui;
import java.util.*;
import model.TaskReminderController;

public class Main {

    public static TaskReminderController controller ;
    public static Scanner sc;

    public Main() {
        controller = new TaskReminderController();
        sc = new Scanner(System.in);
    }

    public static void main(String[] args) {
        Main objMain = new Main();
        objMain.menu();
    }
    public void menu(){
        int choice;
        do {
            System.out.println("-WELCOME TO THE MENU-");
            System.out.println("--Select an option---");
            System.out.println("1: Add element.......");
            System.out.println("2: Edit element......");
            System.out.println("3: Delete element....");
            System.out.println("4: Undo action.......");
            System.out.println("0: Exit..............");
            choice = sc.nextInt();
            sc.nextLine();
            switch(choice){
                case 1:
                    add();
                    System.out.println("Press Enter to return to the menu...");
                    sc.nextLine();
                    break;
                case 2:
                    edit();
                    System.out.println("Press Enter to return to the menu...");
                    sc.nextLine();
                    break;
                case 3:
                    delete();
                    System.out.println("Press Enter to return to the menu...");
                    sc.nextLine();
                    break;
                case 4:
                    undo();
                    System.out.println("Press Enter to return to the menu...");
                    sc.nextLine();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Invalid option, try again!");
                    sc.nextLine();
            }
        } while (choice != 0);
    }

    public void add(){
        System.out.println("Enter ID:");
        String id = sc.nextLine();
        System.out.println("Enter title:");
        String title = sc.nextLine();
        System.out.println("Enter description:");
        String description = sc.nextLine();
        System.out.println("Enter due date (dd/mm/yyyy):");
        String dueDate = sc.nextLine();
        System.out.println("Select type \n 1: Task \n 2: Reminder");
        int type = sc.nextInt();
        sc.nextLine();
        String msg;
        if (type == 1){
            System.out.println("The task is priority? \n 1: Yes \n 2: No");
            int selection = sc.nextInt();
            sc.nextLine();
            int importance = 0;
            if (selection == 1){
                System.out.println("Select importance level \n 3: Very important \n 2: Important \n 1: Less important");
                importance = sc.nextInt();
                sc.nextLine();
            }
            msg = controller.addElement(id, title, description, dueDate, selection == 1, importance);
        } else {
            msg = controller.addElement(id, title, description, dueDate);
        }
        System.out.println(msg);

    }

    public void edit(){

    }

    public void delete(){
        System.out.println("Enter ID:");
        String id = sc.nextLine();
        String msg = controller.deleteElement(id);
        System.out.println(msg);

    }

    public void viewList(){

    }

    public void undo(){

    }
}