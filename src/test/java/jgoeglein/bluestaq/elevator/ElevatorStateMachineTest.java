package jgoeglein.bluestaq.elevator;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;
import org.junit.jupiter.api.Test;

public class ElevatorStateMachineTest {

    @Test
    public void testSinglePassengerFromSecondFloor() {
        Map<Integer, Integer> waiting = new HashMap<>();
        waiting.put(2, 1);

        Elevator elevator =
                new Elevator()
                        .floors(3)
                        .maxPassengers(5)
                        .currentFloor(1)
                        .waiting(waiting)
                        .passengers(new HashMap<>())
                        .speedPerFloorS(2)
                        .doorOpenCloseSpeedS(3)
                        .personOnOffSpeedS(10);

        ElevatorStateMachine stateMachine = new ElevatorStateMachine(elevator);

        while (stateMachine.hasWork()) {
            stateMachine.doWork();
        }

        assertEquals(1, elevator.getCurrentFloor());
        assertTrue(elevator.getPassengers().isEmpty());
        assertTrue(elevator.getWaiting().isEmpty());
        assertEquals(39, stateMachine.getTotalTime());
    }

    @Test
    public void testSinglePassengerFromThirdFloor() {
        Map<Integer, Integer> waiting = new HashMap<>();
        waiting.put(3, 1);

        Elevator elevator =
                new Elevator()
                        .floors(3)
                        .maxPassengers(5)
                        .currentFloor(1)
                        .waiting(waiting)
                        .passengers(new HashMap<>())
                        .speedPerFloorS(2)
                        .doorOpenCloseSpeedS(3)
                        .personOnOffSpeedS(10);

        ElevatorStateMachine stateMachine = new ElevatorStateMachine(elevator);

        while (stateMachine.hasWork()) {
            stateMachine.doWork();
        }

        assertEquals(1, elevator.getCurrentFloor());
        assertTrue(elevator.getPassengers().isEmpty());
        assertTrue(elevator.getWaiting().isEmpty());
        assertEquals(43, stateMachine.getTotalTime());
    }

    @Test
    public void testPicksUpPassengers() {
        Map<Integer, Integer> waiting = new HashMap<>();
        waiting.put(3, 1);
        waiting.put(2, 1);

        Elevator elevator =
                new Elevator()
                        .floors(3)
                        .maxPassengers(5)
                        .currentFloor(1)
                        .waiting(waiting)
                        .passengers(new HashMap<>())
                        .speedPerFloorS(2)
                        .doorOpenCloseSpeedS(3)
                        .personOnOffSpeedS(10);

        ElevatorStateMachine stateMachine = new ElevatorStateMachine(elevator);

        while (stateMachine.hasWork()) {
            stateMachine.doWork();
        }

        assertEquals(1, elevator.getCurrentFloor());
        assertTrue(elevator.getPassengers().isEmpty());
        assertTrue(elevator.getWaiting().isEmpty());
        assertEquals(69, stateMachine.getTotalTime());
    }

    @Test
    public void testTakesPassengersUp() {
        Map<Integer, Integer> waiting = new HashMap<>();
        waiting.put(1, 1);

        Elevator elevator =
                new Elevator()
                        .floors(3)
                        .maxPassengers(5)
                        .currentFloor(1)
                        .waiting(waiting)
                        .passengers(new HashMap<>())
                        .speedPerFloorS(2)
                        .doorOpenCloseSpeedS(3)
                        .personOnOffSpeedS(10);

        ElevatorStateMachine stateMachine = new ElevatorStateMachine(elevator);

        while (stateMachine.hasWork()) {
            stateMachine.doWork();
        }

        assertTrue(elevator.getPassengers().isEmpty());
        assertTrue(elevator.getWaiting().isEmpty());
        assertTrue(elevator.getCurrentFloor() > 1);
    }
}
