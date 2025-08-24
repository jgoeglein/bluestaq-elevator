package jgoeglein.bluestaq.elevator;

import java.util.*;

public class Application {
    public static void main(String[] args) {
        Elevator elevator =
                new Elevator()
                        .floors(10)
                        .maxPassengers(8)
                        .currentFloor(1)
                        .waiting(new HashMap<>(Map.of(5, 2, 8, 1)))
                        .passengers(new HashMap<>())
                        .speedPerFloorS(10)
                        .doorOpenCloseSpeedS(4)
                        .personOnOffSpeedS(10);

        System.out.println("==== Starting Elevator State ====");
        System.out.println(elevator);
        ElevatorStateMachine stateMachine = new ElevatorStateMachine(elevator);

        System.out.println(
                "Initial: Floor "
                        + elevator.getCurrentFloor()
                        + ", Waiting: "
                        + elevator.getWaiting());

        while (stateMachine.hasWork()) {
            stateMachine.doWork();
            if (stateMachine.getCurrentState() == ElevatorState.STOPPED
                    && elevator.getTargetFloor() == null) {
                System.out.println(
                        "At floor "
                                + elevator.getCurrentFloor()
                                + ", Passengers: "
                                + elevator.getPassengers().values().stream()
                                        .mapToInt(Integer::intValue)
                                        .sum());
            }
        }

        System.out.println("\n==== Ending Elevator State ====");
        System.out.println(elevator);
        System.out.println("All passengers cleared in " + stateMachine.getTotalTime() + " seconds");
    }
}
