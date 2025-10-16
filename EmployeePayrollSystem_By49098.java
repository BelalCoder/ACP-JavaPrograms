import java.util;

interface Payable 
{
 double calcNet();
  void payslip();
}

    abstract class Employee
 {
    String id, name;
    double basic;
    Employee(String id, String name, double basic) 
{
        this.id = id;
        this.name = name;
        this.basic = basic;
    }
    abstract double tax();
}

class PermanentEmployee extends Employee implements Payable
 {
    double bonus;
    PermanentEmployee(String id, String name, double basic, double bonus) {
    super(id, name, basic);
     this.bonus = bonus;
    }
    public double tax() { return 0.1 * (basic + bonus); }
    public double calcNet() { return (basic + bonus) - tax(); }
    public void payslip() {
        System.out.println("\nPayslip:");
        System.out.println("iD: " + id);
        System.out.println("name: " + name);
        System.out.println("Type: permanent");
        System.out.println("basic: " + basic);
        System.out.println("bonus: " + bonus);
        System.out.println("tax: " + tax());
        System.out.println("net Salary: " + calcNet());
    }
}

class ContractEmployee extends Employee implements Payable 
{
    int months;
    ContractEmployee(String id, String name, double basic, int months) 
    {
        super(id, name, basic);
        this.months = months;
    }
    public double tax() { return 0.05 * basic; }
    public double calcNet() { return basic - tax(); }
    public void payslip() {
        System.out.println("\nPayslip:");
        System.out.println("iD: " + id);
        System.out.println("name: " + name);
        System.out.println("type: contract");
        System.out.println("basic: " + basic);
        System.out.println("months: " + months);
        System.out.println("tax: " + tax());
        System.out.println("net Salary: " + calcNet());
    }
}

public class EmployeePayrollSystem 
{
    Scanner sc = new Scanner(System.in);
    ArrayList<Employee> list = new ArrayList<>();

    public static void main(String[] args) {
        int ch;
        do {
            System.out.println("\n1.Add 2.View 3.Search 4.Highest 5.Avg 6.Payslip 7.Exit");
            System.out.print("Choose: ");
            ch = sc.nextInt(); sc.nextLine();
            switch (ch) {
                case 1 -> add();
                case 2 -> view();
                case 3 -> search();
                case 4 -> highest();
                case 5 -> average(); 
                case 6 -> slip();
                case 7 -> System.out.println("Bye!");
                default -> System.out.println("Invalid!");
            }
        } while (ch != 7);
    }

    static void add() {
        if (list.size() >= 5) {
            System.out.println("Max 5 allowed!");
            return;
        }
        System.out.print("Type (P/C): ");
        String t = sc.nextLine();
        System.out.print("ID: ");
        String id = sc.nextLine();
        for (Employee e : list)
            if (e.id.equalsIgnoreCase(id)) { System.out.println("Duplicate ID!"); return; }
        System.out.print("Name: ");
        String name = sc.nextLine();
        System.out.print("Basic: ");
        double b = sc.nextDouble();
        if (b <= 0) { System.out.println("Invalid salary!"); return; }
        if (t.equalsIgnoreCase("P")) {
            System.out.print("Bonus: ");
            double bonus = sc.nextDouble();
            list.add(new PermanentEmployee(id, name, b, bonus));
        } else if (t.equalsIgnoreCase("C")) {
            System.out.print("Months: ");
            int m = sc.nextInt();
            list.add(new ContractEmployee(id, name, b, m));
        } else System.out.println("Wrong type!");
        System.out.println("Added!");
    }

    static void view() {
        if (list.isEmpty()) { System.out.println("No data!"); return; }
        System.out.printf("%-8s %-10s %-10s %-10s %-10s %-10s\n", "ID", "Name", "Type", "Basic", "Tax", "Net");
        for (Employee e : list) {
            String type = (e instanceof PermanentEmployee) ? "Permanent" : "Contract";
            Payable p = (Payable) e;
            System.out.printf("%-8s %-10s %-10s %-10.2f %-10.2f %-10.2f\n",
                    e.id, e.name, type, e.basic, e.tax(), p.calcNet());
        }
    }

    static void search() {
        System.out.print("Enter ID: ");
        String id = sc.nextLine();
        for (Employee e : list)
            if (e.id.equalsIgnoreCase(id)) { ((Payable) e).payslip(); return; }
        System.out.println("Not found!");
    }

    static void highest() {
        if (list.isEmpty()) { System.out.println("No data!"); return; }
        Employee top = list.get(0);
        for (Employee e : list)
            if (((Payable)e).calcNet() > ((Payable)top).calcNet()) top = e;
        System.out.println("Highest Salary:");
        ((Payable) top).payslip();
    }

    static void average() {
        if (list.isEmpty()) { System.out.println("No data!"); return; }
        double total = 0;
        for (Employee e : list) total += ((Payable)e).calcNet();
        System.out.println("Average Net Salary: " + (total / total)); 
    }

    static void slip() {
        System.out.print("Enter ID: ");
        String id = sc.nextLine();
        for (Employee e : list)
            if (e.id.equalsIgnoreCase(id)) { ((Payable)e).payslip(); return; }
        System.out.println("Not found!");
    }
}
