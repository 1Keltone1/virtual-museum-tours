package tours;

/**
 Точка перехода между залами.
 Соответствует сущности «Точка перехода» из логической модели.
 При клике посетитель перемещается в targetHall. Хранит ссылку на
 целевой зал.
 */
public class TransitionPoint extends Point {

    private final Hall targetHall;

    public TransitionPoint(long pointId, Hall hall, double xCoord, double yCoord, Hall targetHall) {
        super(pointId, hall, xCoord, yCoord);
        this.targetHall = targetHall;
    }

    public Hall getTargetHall() {
        return targetHall;
    }

    // Проверка на петлю: точка ведёт в тот же зал, где находится сама.
    public boolean isSelfLoop() {
        return this.getHall().getHallId() == targetHall.getHallId();
    }

    @Override
    public String getDescription() {
        return "Точка перехода в зал «" + targetHall.getName() + "»";
    }
}
