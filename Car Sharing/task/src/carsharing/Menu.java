package carsharing;

import carsharing.DAO.CarDAO;
import carsharing.DAO.CompanyDAO;
import carsharing.DAO.CustomerDAO;
import carsharing.model.Car;
import carsharing.model.Company;
import carsharing.model.Customer;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class Menu {
    private final Database database;
    private final Scanner sc;

    private final CompanyDAO companyDAO;

    private final CarDAO carDAO;

    private final CustomerDAO customerDAO;

    Menu (Database database) {
        this.database = database;
        sc = new Scanner(System.in);
        this.companyDAO = new CompanyDAO(database.getConnection());
        this.carDAO = new CarDAO(database.getConnection());
        this.customerDAO = new CustomerDAO(database.getConnection());
    }

    void run() {
        startMenu();
    }

    void startMenu() {
        while (true) {
            System.out.println("1. Log in as a manager");
            System.out.println("2. Log in as a customer");
            System.out.println("3. Create a customer");
            System.out.println("0. Exit");
            int choice = sc.nextInt();
            sc.nextLine();
            System.out.println();

            switch (choice) {
                case 1:
                    managerMenu();
                    break;
                case 2:
                    customerMenu();
                    break;
                case 3:
                    createCustomer();
                    break;
                case 0:
                    return;
            }
        }
    }

    void createCustomer() {
        System.out.println("Enter the customer name:");
        String name = sc.nextLine();
        customerDAO.save(new Customer(name));
        System.out.println("The customer was created!");
        System.out.println();
    }

    void customerMenu() {

        List<Customer> customers = customerDAO.getAll();
        if (customers.isEmpty()) {
            System.out.println("The customer list is empty!");
            System.out.println();
            return;
        }

        System.out.println("Choose a customer:");

        for (int i = 0; i < customers.size(); i++) {
            System.out.println((i + 1) + ". " + customers.get(i).getName());
        }

        System.out.println("0. Back");
        int id = sc.nextInt();

        if (id == 0) {
            System.out.println();
        } else {
            Customer customer = customers.get(id - 1);
            System.out.println();
            processCutomerChoice(customer);
        }
    }

    void processCutomerChoice(Customer customer) {
        while (true) {
            System.out.println("1. Rent a car");
            System.out.println("2. Return a rented car");
            System.out.println("3. My rented car");
            System.out.println("0. Back");
            int choice = sc.nextInt();
            sc.nextLine();
            System.out.println();

            switch (choice) {
                case 1:
                    rentCar(customer);
                    break;
                case 2:
                    returnCar(customer);
                    break;
                case 3:
                    rentedCar(customer);
                    break;
                case 0:
                    return;
            }
        }
    }

    void rentCar(Customer customer) {
        if (customer.getRentedCarId() != 0) {
            System.out.println("You've already rented a car!\n");
            return;
        }
        Company company = chooseCompany();
        if (company == null) {
            return;
        }
        System.out.println();

        Car car = chooseCar(company);
        if (car == null) {
            return;
        }

        customer.setRentedCarId(car.getId());
        customerDAO.update(customer);
        System.out.println("You rented '" + car.getName() + "'");
        System.out.println();
    }

    void returnCar(Customer customer) {
        if (customer.getRentedCarId() == 0) {
            System.out.println("You didn't rent a car!\n");
            return;
        }

        customer.setRentedCarId(0);
        customerDAO.update(customer);
        System.out.println("You've returned a rented car!");
        System.out.println();
    }

    void rentedCar(Customer customer) {
        if (customer.getRentedCarId() == 0) {
            System.out.println("You didn't rent a car!\n");
            return;
        }

        Optional<Car> car = carDAO.getById(customer.getRentedCarId());
        System.out.println("Your rented car:");
        System.out.println(car.get().getName());
        System.out.println("Company:");
        System.out.println(companyDAO.getById(car.get().getCompanyId()).get().getName());
        System.out.println();
    }

    void managerMenu() {
        while (true) {
            System.out.println("1. Company list");
            System.out.println("2. Create a company");
            System.out.println("0. Back");
            int choice = sc.nextInt();
            sc.nextLine();
            System.out.println();

            switch (choice) {
                case 1:
                    companyList();
                    break;
                case 2:
                    createCompany();
                    break;
                case 0:
                    return;
            }
        }
    }

    private Company chooseCompany() {
        List<Company> companies = companyDAO.getAll();
        if (companies.isEmpty()) {
            System.out.println();
            System.out.println("The company list is empty!");
            System.out.println();
            return null;
        }

        System.out.println("Choose the company:");

        for (int i = 0; i < companies.size(); i++) {
            System.out.println((i + 1) +  ". " + companies.get(i).getName());
        }
        System.out.println("0. Back");

        int choice = sc.nextInt();
        int param = choice > companies.size() ? -1 : choice == 0 ? 0 : 1;
        switch (param) {
            case 0:
                System.out.println();
                return null;
            case 1:
                return companies.get(choice - 1);
            default:
                System.out.println("Invalid op, try again.");
                return null;
        }
    }

    private Car chooseCar(Company company) {
        List<Car> list = carDAO.getAllAvailable(company);
        if (list.size() == 0) {
            System.out.printf("No available cars in the %s company%n%n", company.getName());
            return null;
        }
//
//        List<Car> cars = carDAO.getAllByCompany(company);
//        if (cars.isEmpty()) {
//            System.out.println("The car list is empty!");
//            System.out.println();
//            return null;
//        }

        System.out.println("Choose the car:");

        for (int i = 0; i < list.size(); i++) {
            System.out.println((i + 1) +  ". " + list.get(i).getName());
        }
        System.out.println("0. Back");

        int choice = sc.nextInt();
        int param = choice > list.size() ? -1 : choice == 0 ? 0 : 1;
        switch (param) {
            case 0:
                System.out.println();
                return null;
            case 1:
                return list.get(choice - 1);
            default:
                System.out.println("Invalid op, try again.");
                return null;
        }
    }


    void companyList() {
        List<Company> companies = companyDAO.getAll();
        if (companies.isEmpty()) {
            System.out.println("The company list is empty!");
            System.out.println();
            return;
        }

        System.out.println("Choose the company:");


        for (int i = 0; i < companies.size(); i++) {
            System.out.println((i + 1) +  ". " + companies.get(i).getName());
        }
        System.out.println("0. Back");

        int choice = sc.nextInt();
        int param = choice > companies.size() ? -1 : choice == 0 ? 0 : 1;
        switch (param) {
            case 0:
                System.out.println();
                return;
            case 1:
                carMenu(companies.get(choice - 1));
                break;
            default:
                System.out.println("Invalid op, try again.");
        }
    }

    void carMenu(Company company) {
        System.out.println();
        if (company == null) {
            System.out.println("Invalid company, try again.");
            return;
        }
        while (true) {
            System.out.printf("%s company:\n", company.getName());
            System.out.println("1. Car list");
            System.out.println("2. Create a car");
            System.out.println("0. Back");
            int choice = sc.nextInt();
            sc.nextLine();
            System.out.println();

            switch (choice) {
                case 1:
                    List<Car> carlist = this.carDAO.getAllByCompany(company);

                    if (carlist.isEmpty()) {
                        System.out.println("The car list is empty!");
                        break;
                    }

                    for (int i = 0; i < carlist.size(); i++) {
                        System.out.println((i + 1) + ". " + carlist.get(i).getName());
                    }
                    break;
                case 2:
                    System.out.println("Enter the car name:");
                    String name = sc.nextLine();
                    carDAO.save(new Car(name, company.getId()));
                    System.out.println("The car was created!");
                    break;
                case 0:
                    return;
            }
            System.out.println();
        }

    }

    void createCompany() {
        System.out.println("Enter the company name:");
        String name = sc.nextLine();
        companyDAO.save(new Company(name));
        System.out.println("The company was created!");
        System.out.println();
    }

}
