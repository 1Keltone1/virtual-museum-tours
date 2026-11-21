package tours;

/**
 * Точка перехода между залами (hotspot навигации).
 *
 * Соответствует сущности «Точка перехода» из логической модели КТ2.
 * При клике посетитель перемещается в targetHall. Хранит ссылку на
 * целевой зал, что обеспечивает направленную ассоциацию
 * TransitionPoint * --> 1 Hall «ведёт в».
 *
 * Поведение, которого нет у точки экспоната: isSelfLoop() — проверка,
 * не ведёт ли точка в тот же зал, где она сама расположена (петля).
 * Эта проверка — часть алгоритма №1 в HotspotLinksValidator.
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

    /**
     * Петля: точка ведёт в тот же зал, где находится сама.
     * Недопустима: посетитель «телепортируется» на место.
     */
    public boolean isSelfLoop() {
        return this.getHall().getHallId() == targetHall.getHallId();
    }

    @Override
    public String getDescription() {
        return "Точка перехода в зал «" + targetHall.getName() + "»";
    }
}
