//package com.journeyplanner.journeyplanner.cli;
//
//import com.journeyplanner.journeyplanner.model.Journey;
//import com.journeyplanner.journeyplanner.repository.JourneyRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.annotation.Profile;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//import java.util.Optional;
//import java.util.Scanner;
//
//@Component
//@Profile("cli") // ✅ Runs only when 'cli' profile is active
//public class JourneyCLI implements CommandLineRunner {
//
//    @Autowired
//    private JourneyRepository journeyRepository;
//
//    @Override
//    public void run(String... args) {
//        Scanner scanner = new Scanner(System.in);
//        boolean running = true;
//
//        while (running) {
//            System.out.println("\n🚍 Journey Planner CLI Menu:");
//            System.out.println("1. Add Journey");
//            System.out.println("2. List All Journeys");
//            System.out.println("3. Get Journey by ID");
//            System.out.println("4. Update Journey");
//            System.out.println("5. Delete Journey");
//            System.out.println("6. Search Journey by Source");
//            System.out.println("0. Exit");
//            System.out.print("Choose option: ");
//
//            int choice = scanner.nextInt();
//            scanner.nextLine(); // consume newline
//
//            switch (choice) {
//                case 1 -> { // ✅ POST /journeys
//                    System.out.print("Enter Source: ");
//                    String source = scanner.nextLine();
//                    System.out.print("Enter Destination: ");
//                    String destination = scanner.nextLine();
//
//                    Journey newJourney = new Journey();
//                    newJourney.setSource(source);
//                    newJourney.setDestination(destination);
//
//                    journeyRepository.save(newJourney);
//                    System.out.println("✅ Journey Added: " + newJourney);
//                }
//                case 2 -> { // ✅ GET /journeys
//                    List<Journey> journeys = journeyRepository.findAll();
//                    if (journeys.isEmpty()) {
//                        System.out.println("⚠️ No journeys found!");
//                    } else {
//                        journeys.forEach(System.out::println);
//                    }
//                }
//                case 3 -> { // ✅ GET /journeys/{id}
//                    System.out.print("Enter Journey ID: ");
//                    Long id = scanner.nextLong();
//                    Optional<Journey> journey = journeyRepository.findById(id);
//                    journey.ifPresentOrElse(
//                            System.out::println,
//                            () -> System.out.println("❌ Journey not found")
//                    );
//                }
//                case 4 -> { // ✅ PUT /journeys/{id}
//                    System.out.print("Enter Journey ID to Update: ");
//                    Long updateId = scanner.nextLong();
//                    scanner.nextLine();
//                    if (journeyRepository.existsById(updateId)) {
//                        System.out.print("Enter New Source: ");
//                        String newSource = scanner.nextLine();
//                        System.out.print("Enter New Destination: ");
//                        String newDestination = scanner.nextLine();
//
//                        Journey updatedJourney = new Journey();
//                        updatedJourney.setId(updateId);
//                        updatedJourney.setSource(newSource);
//                        updatedJourney.setDestination(newDestination);
//
//                        journeyRepository.save(updatedJourney);
//                        System.out.println("✅ Journey Updated!");
//                    } else {
//                        System.out.println("❌ Journey not found!");
//                    }
//                }
//                case 5 -> { // ✅ DELETE /journeys/{id}
//                    System.out.print("Enter Journey ID to Delete: ");
//                    Long deleteId = scanner.nextLong();
//                    if (journeyRepository.existsById(deleteId)) {
//                        journeyRepository.deleteById(deleteId);
//                        System.out.println("🗑 Journey Deleted!");
//                    } else {
//                        System.out.println("❌ Journey not found!");
//                    }
//                }
//                case 6 -> { // ✅ GET /journeys/search?source=xxx
//                    System.out.print("Enter Source to Search: ");
//                    String searchSource = scanner.nextLine();
//                    List<Journey> searchResults = journeyRepository.findBySource(searchSource);
//                    if (searchResults.isEmpty()) {
//                        System.out.println("⚠️ No journeys found!");
//                    } else {
//                        searchResults.forEach(System.out::println);
//                    }
//                }
//                case 0 -> {
//                    running = false;
//                    System.out.println("👋 Exiting Journey Planner CLI...");
//                }
//                default -> System.out.println("❌ Invalid choice, try again.");
//            }
//        }
//
//        scanner.close();
//    }
//}

package com.journeyplanner.journeyplanner.cli;

import com.journeyplanner.journeyplanner.model.Journey;
import com.journeyplanner.journeyplanner.repository.JourneyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.*;

@Component
@Profile("cli") // ✅ Runs only when 'cli' profile is active
public class JourneyCLI implements CommandLineRunner {

    @Autowired
    private JourneyRepository journeyRepository;

    private final Scanner scanner = new Scanner(System.in);

    @Override
    public void run(String... args) {
        boolean running = true;

        while (running) {
            printMenu();
            String choiceInput = scanner.nextLine().trim();

            if (choiceInput.equalsIgnoreCase("q")) {
                running = false;
                break;
            }

            int choice;
            try {
                choice = Integer.parseInt(choiceInput);
            } catch (NumberFormatException e) {
                System.out.println("❌ Invalid choice, enter a number.");
                continue;
            }

            switch (choice) {
                case 1 -> addJourney();
                case 2 -> listAllJourneys();
                case 3 -> getJourneyById();
                case 4 -> updateJourney();
                case 5 -> deleteJourney();
                case 6 -> searchJourneyBySource();
                case 7 -> searchJourneyByDestination(); // 🔥 New feature
                case 0 -> {
                    running = false;
                    System.out.println("👋 Exiting Journey Planner CLI...");
                }
                default -> System.out.println("❌ Invalid choice, try again.");
            }
        }
        scanner.close();
    }

    // ----------- MENU ----------
    private void printMenu() {
        System.out.println("\n🚍 Journey Planner CLI Menu:");
        System.out.println("1. Add Journey");
        System.out.println("2. List All Journeys");
        System.out.println("3. Get Journey by ID");
        System.out.println("4. Update Journey");
        System.out.println("5. Delete Journey");
        System.out.println("6. Search Journey by Source");
        System.out.println("7. Search Journey by Destination"); // 🔥
        System.out.println("0. Exit");
        System.out.print("Choose option (or 'q' to quit): ");
    }

    // ----------- ADD -----------
    private void addJourney() {
        System.out.print("Enter Source: ");
        String source = scanner.nextLine();
        System.out.print("Enter Destination: ");
        String destination = scanner.nextLine();
        System.out.print("Enter Date (YYYY-MM-DD) [Press Enter for Today]: ");
        String dateInput = scanner.nextLine();
        String date = dateInput.isBlank() ? LocalDate.now().toString() : dateInput;

        System.out.print("Enter Mode of Travel (Bus/Train/Flight/Car): ");
        String modeInput = scanner.nextLine();
        String mode = validateMode(modeInput);

        Journey newJourney = new Journey();
        newJourney.setSource(source);
        newJourney.setDestination(destination);
        newJourney.setDate(date);
        newJourney.setModeOfTravel(mode);

        journeyRepository.save(newJourney);
        System.out.println("✅ Journey Added!");
    }

    // ----------- LIST -----------
    private void listAllJourneys() {
        List<Journey> journeys = journeyRepository.findAll();
        if (journeys.isEmpty()) {
            System.out.println("⚠️ No journeys found!");
            return;
        }

        printTableHeader();
        for (Journey j : journeys) {
            printJourneyRow(j);
        }
    }

    // ----------- GET BY ID -----------
    private void getJourneyById() {
        System.out.print("Enter Journey ID: ");
        try {
            Long id = Long.parseLong(scanner.nextLine());
            Optional<Journey> journey = journeyRepository.findById(id);
            journey.ifPresentOrElse(
                    j -> {
                        printTableHeader();
                        printJourneyRow(j);
                    },
                    () -> System.out.println("❌ Journey not found")
            );
        } catch (NumberFormatException e) {
            System.out.println("❌ Invalid ID format.");
        }
    }

    // ----------- UPDATE -----------
    private void updateJourney() {
        System.out.print("Enter Journey ID to Update: ");
        try {
            Long updateId = Long.parseLong(scanner.nextLine());
            Optional<Journey> optionalJourney = journeyRepository.findById(updateId);

            if (optionalJourney.isPresent()) {
                Journey updatedJourney = optionalJourney.get();

                System.out.print("Enter New Source (leave blank to keep old): ");
                String newSource = scanner.nextLine();
                if (!newSource.isBlank()) updatedJourney.setSource(newSource);

                System.out.print("Enter New Destination (leave blank to keep old): ");
                String newDestination = scanner.nextLine();
                if (!newDestination.isBlank()) updatedJourney.setDestination(newDestination);

                System.out.print("Enter New Date (YYYY-MM-DD) [Enter to skip]: ");
                String newDate = scanner.nextLine();
                if (!newDate.isBlank()) updatedJourney.setDate(newDate);

                System.out.print("Enter New Mode of Travel (Bus/Train/Flight/Car) [Enter to skip]: ");
                String newMode = scanner.nextLine();
                if (!newMode.isBlank()) updatedJourney.setModeOfTravel(validateMode(newMode));

                journeyRepository.save(updatedJourney);
                System.out.println("✅ Journey Updated!");
            } else {
                System.out.println("❌ Journey not found!");
            }
        } catch (NumberFormatException e) {
            System.out.println("❌ Invalid ID format.");
        }
    }

    // ----------- DELETE -----------
    private void deleteJourney() {
        System.out.print("Enter Journey ID to Delete: ");
        try {
            Long deleteId = Long.parseLong(scanner.nextLine());
            if (journeyRepository.existsById(deleteId)) {
                journeyRepository.deleteById(deleteId);
                System.out.println("🗑 Journey Deleted!");
            } else {
                System.out.println("❌ Journey not found!");
            }
        } catch (NumberFormatException e) {
            System.out.println("❌ Invalid ID format.");
        }
    }

    // ----------- SEARCH BY SOURCE -----------
    private void searchJourneyBySource() {
        System.out.print("Enter Source to Search: ");
        String searchSource = scanner.nextLine();
        List<Journey> results = journeyRepository.findBySource(searchSource);
        if (results.isEmpty()) {
            System.out.println("⚠️ No journeys found!");
        } else {
            printTableHeader();
            results.forEach(this::printJourneyRow);
        }
    }

    // ----------- SEARCH BY DESTINATION (NEW) -----------
    private void searchJourneyByDestination() {
        System.out.print("Enter Destination to Search: ");
        String searchDestination = scanner.nextLine();
        List<Journey> results = journeyRepository.findByDestination(searchDestination);
        if (results.isEmpty()) {
            System.out.println("⚠️ No journeys found!");
        } else {
            printTableHeader();
            results.forEach(this::printJourneyRow);
        }
    }

    // ----------- HELPERS -----------
    private String validateMode(String input) {
        Set<String> validModes = Set.of("Bus", "Train", "Flight", "Car");
        return validModes.contains(input) ? input : "Other";
    }

    private void printTableHeader() {
        System.out.printf("%-5s %-15s %-15s %-15s %-15s%n", "ID", "Source", "Destination", "Date", "Mode");
        System.out.println("--------------------------------------------------------------------------");
    }

    private void printJourneyRow(Journey j) {
        System.out.printf("%-5d %-15s %-15s %-15s %-15s%n",
                j.getId(),
                Optional.ofNullable(j.getSource()).orElse("N/A"),
                Optional.ofNullable(j.getDestination()).orElse("N/A"),
                Optional.ofNullable(j.getDate()).orElse("N/A"),
                Optional.ofNullable(j.getModeOfTravel()).orElse("N/A")
        );
    }
}


