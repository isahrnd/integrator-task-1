package ui;
import java.util.*;
import model.TaskReminderController;

public class Main {

    public static TaskReminderController controller;
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
            System.out.println("-WELCOME TO THE APP-");
            System.out.println("--Select an option--");
            System.out.println("1: Add element......");
            System.out.println("2: Edit element.....");
            System.out.println("3: Delete element...");
            System.out.println("4: Undo action......");
            System.out.println("5: View list........");
            System.out.println("0: Exit.............");
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
                    undoAction();
                    System.out.println("Press Enter to return to the menu...");
                    sc.nextLine();
                    break;
                case 5:
                    viewList();
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
        System.out.println("Enter the ID of the new item:");
        String id = sc.nextLine();
        System.out.println("Enter the title:");
        String title = sc.nextLine();
        System.out.println("Enter the description:");
        String description = sc.nextLine();
        System.out.println("Enter the due date (dd/mm/yyyy):");
        String dueDate = sc.nextLine();
        int type;
        do {
            System.out.println("Please indicate the type of item: \n 1: Task \n 2: Reminder");
            type = sc.nextInt();
            sc.nextLine();
        } while (type != 1 && type != 2);
        String msg;
        if (type == 1){
            int selection;
            do {
                System.out.println("Is the task priority? \n 1: Yes \n 2: No");
                selection = sc.nextInt();
                sc.nextLine();
            } while (selection != 1 && selection != 2);
            int importance = 0;
            if (selection == 1){
                do {
                    System.out.println("Select the importance level: \n 1: Low \n 2: Moderate \n 3: High \n 4: Very high");
                    importance = sc.nextInt();
                    sc.nextLine();
                } while (importance < 1 || importance > 4);
            }
            msg = controller.addElement(id, title, description, dueDate, selection == 1, importance);
        } else {
            msg = controller.addElement(id, title, description, dueDate);
        }
        System.out.println(msg);
    }

    public void edit(){
        System.out.println("Enter the ID of the item to edit:");
        String id = sc.nextLine();
        System.out.println("Enter the new title:");
        String title = sc.nextLine();
        System.out.println("Enter the new description:");
        String description = sc.nextLine();
        System.out.println("Enter the new due date (dd/mm/yyyy):");
        String dueDate = sc.nextLine();
        int type;
        do {
            System.out.println("Please indicate the type of item: \n 1: Task \n 2: Reminder");
            type = sc.nextInt();
            sc.nextLine();
        } while (type != 1 && type != 2);
        String msg;
        if (type == 1){
            int selection;
            do {
                System.out.println("Was the task priority? \n 1: Yes \n 2: No");
                selection = sc.nextInt();
                sc.nextLine();
            } while (selection != 1 && selection != 2);
            if (selection == 1){
                int importance;
                do {
                    System.out.println("Select the new importance level: \n 1: Low \n 2: Moderate \n 3: High \n 4: Very high");
                    importance = sc.nextInt();
                    sc.nextLine();
                } while (importance < 1 || importance > 4);
                msg = controller.editPriorityTask(id, title, description, dueDate, importance, false);
            } else {
                msg = controller.editNoPriorityTask(id, title, description, dueDate, false);
            }
        } else {
            msg = controller.editReminder(id, title, description, dueDate, false);
        }
        System.out.println(msg);
    }

    public void delete(){
        System.out.println("Enter the ID of the item to delete:");
        String id = sc.nextLine();
        String msg = controller.deleteElement(id, false);
        System.out.println(msg);
    }

    public void viewList(){
        String msg = controller.showList();
        System.out.println(msg);
    }

    public void undoAction(){
        controller.undoAction();
    }
}