import java.util.*;

class Pet {
    String name;
    String type;
    boolean isAdopted;
    String healthRecord;

    Pet(String name, String type) {
        this.name = name;
        this.type = type;
        this.isAdopted = false;
        this.healthRecord = "Healthy";
    }

    public String getStatus() {
        return isAdopted ? "Adopted" : "Available";
    }
}

class Adopter {
    String name;

    Adopter(String name) {
        this.name = name;
    }
}

class AdoptionRequest {
    Pet pet;
    Adopter adopter;

    AdoptionRequest(Pet pet, Adopter adopter) {
        this.pet = pet;
        this.adopter = adopter;
    }

    public String generateCertificate() {
        return "Certificate of Adoption\n" +
               "Pet: " + pet.name + "\n" +
               "Adopted by: " + adopter.name + "\n" +
               "Status: " + pet.getStatus();
    }
}

class FosterCare {
    String petName;
    String fosterName;

    FosterCare(String petName, String fosterName) {
        this.petName = petName;
        this.fosterName = fosterName;
    }
}

class Donation {
    String donorName;
    double amount;

    Donation(String donorName, double amount) {
        this.donorName = donorName;
        this.amount = amount;
    }
}

public class PetAdoptionSystem {
    static ArrayList<Pet> pets = new ArrayList<>();
    static ArrayList<Adopter> adopters = new ArrayList<>();
    static ArrayList<FosterCare> fosterList = new ArrayList<>();
    static ArrayList<Donation> donations = new ArrayList<>();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\n--- Pet Adoption Management System ---");
            System.out.println("1. Add Pet");
            System.out.println("2. Remove Pet");
            System.out.println("3. Add Adopter");
            System.out.println("4. Remove Adopter");
            System.out.println("5. Process Adoption Request");
            System.out.println("6. View Pet Status");
            System.out.println("7. Update Health Record");
            System.out.println("8. Manage Foster Care");
            System.out.println("9. Manage Donations");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();
            sc.nextLine();  // consume newline

            switch (choice) {
                case 1:
                    System.out.print("Pet name: ");
                    String name = sc.nextLine();
                    System.out.print("Pet type (dog/cat/etc.): ");
                    String type = sc.nextLine();
                    pets.add(new Pet(name, type));
                    System.out.println("Pet added.");
                    break;

                case 2:
                    System.out.print("Enter pet name to remove: ");
                    String removePet = sc.nextLine();
                    pets.removeIf(p -> p.name.equalsIgnoreCase(removePet));
                    System.out.println("Pet removed (if existed).");
                    break;

                case 3:
                    System.out.print("Adopter name: ");
                    String adopterName = sc.nextLine();
                    adopters.add(new Adopter(adopterName));
                    System.out.println("Adopter added.");
                    break;

                case 4:
                    System.out.print("Enter adopter name to remove: ");
                    String removeAdopter = sc.nextLine();
                    adopters.removeIf(a -> a.name.equalsIgnoreCase(removeAdopter));
                    System.out.println("Adopter removed (if existed).");
                    break;

                case 5:
                    System.out.print("Pet name to adopt: ");
                    String adoptPet = sc.nextLine();
                    System.out.print("Adopter name: ");
                    String adopterN = sc.nextLine();

                    Pet foundPet = null;
                    Adopter foundAdopter = null;

                    for (Pet p : pets) {
                        if (p.name.equalsIgnoreCase(adoptPet) && !p.isAdopted) {
                            foundPet = p;
                            break;
                        }
                    }

                    for (Adopter a : adopters) {
                        if (a.name.equalsIgnoreCase(adopterN)) {
                            foundAdopter = a;
                            break;
                        }
                    }

                    if (foundPet != null && foundAdopter != null) {
                        foundPet.isAdopted = true;
                        AdoptionRequest request = new AdoptionRequest(foundPet, foundAdopter);
                        System.out.println(request.generateCertificate());
                    } else {
                        System.out.println("Pet not available or Adopter not found.");
                    }
                    break;

                case 6:
                    for (Pet p : pets) {
                        System.out.println(p.name + " - " + p.type + " - " + p.getStatus() + " - Health: " + p.healthRecord);
                    }
                    break;

                case 7:
                    System.out.print("Pet name to update health: ");
                    String healthPet = sc.nextLine();
                    System.out.print("New health record: ");
                    String health = sc.nextLine();
                    for (Pet p : pets) {
                        if (p.name.equalsIgnoreCase(healthPet)) {
                            p.healthRecord = health;
                            System.out.println("Health record updated.");
                            break;
                        }
                    }
                    break;

                case 8:
                    System.out.print("Enter pet name for foster care: ");
                    String fosterPet = sc.nextLine();
                    System.out.print("Enter foster person name: ");
                    String fosterName = sc.nextLine();
                    fosterList.add(new FosterCare(fosterPet, fosterName));
                    System.out.println("Foster care added.");
                    break;

                case 9:
                    System.out.print("Donor name: ");
                    String donor = sc.nextLine();
                    System.out.print("Donation amount: ");
                    double amount = sc.nextDouble();
                    donations.add(new Donation(donor, amount));
                    System.out.println("Donation recorded.");
                    break;

                case 0:
                    running = false;
                    System.out.println("Exiting system...");
                    break;

                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }

        sc.close();
    }
}
