import java.io.*;
import java.util.*;

// Person class
class Person {
    protected String name;
    protected String contact;

    public Person(String name, String contact) {
        this.name = name;
        this.contact = contact;
    }
}

// Adopter class
class Adopter extends Person {
    public Adopter(String name, String contact) {
        super(name, contact);
    }
}

// Pet class
class Pet {
    protected String name;
    protected String type;
    protected boolean isAdopted;
    protected String healthRecord;

    public Pet(String name, String type, String healthRecord) {
        this.name = name;
        this.type = type;
        this.healthRecord = healthRecord;
        this.isAdopted = false;
    }

    public void adopt() {
        this.isAdopted = true;
    }

    public String getStatus() {
        return isAdopted ? "Adopted" : "Available";
    }

    public String getInfo() {
        return name + " (" + type + ") - " + getStatus();
    }
}

// Dog class
class Dog extends Pet {
    public Dog(String name, String healthRecord) {
        super(name, "Dog", healthRecord);
    }
}

// Cat class
class Cat extends Pet {
    public Cat(String name, String healthRecord) {
        super(name, "Cat", healthRecord);
    }
}

// Foster care
class FosterCare {
    public static String assignFosterHome(Pet pet) {
        return "Pet " + pet.name + " assigned to foster care.";
    }
}

// Donation
class Donation {
    public static void donate(String donor, double amount) {
        System.out.println("Thank you " + donor + " for donating $" + amount);
    }
}

// File handler
class FileHandler {
    public static void savePet(Pet pet) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("pets.txt", true))) {
            bw.write(pet.name + "," + pet.type + "," + pet.healthRecord + "," + pet.isAdopted);
            bw.newLine();
        } catch (IOException e) {
            System.out.println("Error saving pet.");
        }
    }

    public static void rewritePets(List<Pet> pets) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("pets.txt"))) {
            for (Pet pet : pets) {
                bw.write(pet.name + "," + pet.type + "," + pet.healthRecord + "," + pet.isAdopted);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error rewriting pet file.");
        }
    }

    public static void generateCertificate(Pet pet, String adopterName) {
        String fileName = pet.name + "_certificate.txt";
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
            bw.write("----- Adoption Certificate -----\n");
            bw.write("Pet Name: " + pet.name + "\n");
            bw.write("Adopter: " + adopterName + "\n");
            bw.write("Status: " + pet.getStatus() + "\n");
            bw.write("Health Record: " + pet.healthRecord + "\n");
        } catch (IOException e) {
            System.out.println("Error generating certificate.");
        }
    }

    // Save user to file
    public static void saveUser(String username, String password, String role) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("users.txt", true))) {
            bw.write(username + "," + password + "," + role);
            bw.newLine();
        } catch (IOException e) {
            System.out.println("Error saving user.");
        }
    }

    // Check login
    public static boolean checkLogin(String username, String password) {
        try (BufferedReader br = new BufferedReader(new FileReader("users.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3 && parts[0].equals(username) && parts[1].equals(password)) {
                    System.out.println("Welcome " + parts[2] + " " + parts[0]);
                    return true;
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading user file.");
        }
        return false;
    }
}

// Adoption Manager
class AdoptionManager {
    private List<Pet> pets = new ArrayList<>();
    private List<Adopter> adopters = new ArrayList<>();

    public void addPet(Pet pet) {
        pets.add(pet);
        FileHandler.savePet(pet);
    }

    public void removePet(String name) {
        pets.removeIf(p -> p.name.equalsIgnoreCase(name));
        FileHandler.rewritePets(pets);
    }

    public void addAdopter(Adopter adopter) {
        adopters.add(adopter);
    }

    public void processAdoption(String petName, String adopterName) {
        for (Pet p : pets) {
            if (p.name.equalsIgnoreCase(petName) && !p.isAdopted) {
                p.adopt();
                FileHandler.generateCertificate(p, adopterName);
                FileHandler.rewritePets(pets);
                System.out.println("Adoption successful!");
                return;
            }
        }
        System.out.println("Adoption failed. Pet not available.");
    }

    public void listPets() {
        for (Pet p : pets) {
            System.out.println(p.getInfo());
        }
    }
}

// Main class
public class PetAdoptionSystem {
    public static void register(Scanner sc) {
        System.out.print("Enter username: ");
        String username = sc.nextLine();
        System.out.print("Enter password: ");
        String password = sc.nextLine();
        System.out.print("Enter role (admin/volunteer): ");
        String role = sc.nextLine();
        FileHandler.saveUser(username, password, role);
        System.out.println("Registration successful!");
    }

    public static boolean login(Scanner sc) {
        System.out.print("Username: ");
        String username = sc.nextLine();
        System.out.print("Password: ");
        String password = sc.nextLine();
        if (FileHandler.checkLogin(username, password)) {
            System.out.println("Login successful!");
            return true;
        }
        System.out.println("Login failed.");
        return false;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        AdoptionManager manager = new AdoptionManager();

        System.out.println("--- Welcome to Pet Adoption System ---");

        while (true) {
            System.out.println("\n1. Register");
            System.out.println("2. Login");
            System.out.println("0. Exit");
            System.out.print("Choice: ");
            int choice = sc.nextInt();
            sc.nextLine();

            if (choice == 1) {
                register(sc);
            } else if (choice == 2) {
                if (login(sc)) break;
            } else if (choice == 0) {
                return;
            } else {
                System.out.println("Invalid option.");
            }
        }

        // Main menu after login

        while (true) {
            System.out.println("\n--- Pet Adoption Management ---");
            System.out.println("1. Add Pet");
            System.out.println("2. Remove Pet");
            System.out.println("3. Add Adopter");
            System.out.println("4. Process Adoption");
            System.out.println("5. List Pets");
            System.out.println("6. Foster Care");
            System.out.println("7. Make Donation");
            System.out.println("0. Logout");
            System.out.print("Enter choice: ");
            int ch = sc.nextInt();
            sc.nextLine();

            switch (ch) {
                case 1:
                    System.out.print("Pet type (Dog/Cat): ");
                    String type = sc.nextLine();
                    System.out.print("Pet name: ");
                    String name = sc.nextLine();
                    System.out.print("Health record: ");
                    String health = sc.nextLine();
                    if (type.equalsIgnoreCase("Dog"))
                        manager.addPet(new Dog(name, health));
                    else if (type.equalsIgnoreCase("Cat"))
                        manager.addPet(new Cat(name, health));
                    else
                        System.out.println("Invalid pet type.");
                    break;

                case 2:
                    System.out.print("Enter pet name to remove: ");
                    String removeName = sc.nextLine();
                    manager.removePet(removeName);
                    break;

                case 3:
                    System.out.print("Adopter name: ");
                    String adopterName = sc.nextLine();
                    System.out.print("Contact: ");
                    String contact = sc.nextLine();
                    manager.addAdopter(new Adopter(adopterName, contact));
                    break;

                case 4:
                    System.out.print("Pet name to adopt: ");
                    String petName = sc.nextLine();
                    System.out.print("Adopter name: ");
                    String adopter = sc.nextLine();
                    manager.processAdoption(petName, adopter);
                    break;

                case 5:
                    manager.listPets();
                    break;

                case 6:
                    System.out.print("Pet name for foster care: ");
                    String fosterPet = sc.nextLine();
                    System.out.println(FosterCare.assignFosterHome(new Pet(fosterPet, "Unknown", "Healthy")));
                    break;

                case 7:
                    System.out.print("Donor name: ");
                    String donor = sc.nextLine();
                    System.out.print("Donation amount: ");
                    double amount = sc.nextDouble();
                    Donation.donate(donor, amount);
                    break;

                case 0:
                    System.out.println("Logged out.");
                    return;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}
