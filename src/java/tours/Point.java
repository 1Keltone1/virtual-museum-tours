package tours;

/**
 * Абстрактная базовая точка на панораме зала.
 *
 * Обобщает две сущности логической модели КТ2 — «Точка перехода» и
 * «Точка экспоната». Общее у них: координаты на панораме (xCoord, yCoord)
 * и принадлежность залу. Разное — что происходит при клике.
 *
 * Каждая конкретная точка обязана реализовать getDescription(): поведение
 * действительно различается, поэтому наследование осмысленно, а не «ради
 * наследования». На уровне диаграммы классов Hall хранит List<Point>,
 * что обеспечивает полиморфизм: в одном списке могут лежать и точки
 * перехода, и точки экспоната.
 *
 * Поля protected — доступны подклассам, но не внешнему коду.
 */
public abstract class Point {

    private final long pointId;
    private final double xCoord;
    private final double yCoord;
    private final Hall hall;

    protected Point(long pointId, Hall hall, double xCoord, double yCoord) {
        this.pointId = pointId;
        this.hall = hall;
        this.xCoord = xCoord;
        this.yCoord = yCoord;
    }

    public long getPointId() {
        return pointId;
    }

    public double getXCoord() {
        return xCoord;
    }

    public double getYCoord() {
        return yCoord;
    }

    public Hall getHall() {
        return hall;
    }

    /**
     * Человекочитаемое описание точки. У точки перехода — «вести в зал X»,
     * у точки экспоната — «открыть карточку экспоната Y». Реализация
     * своя у каждого подкласса.
     */
    public abstract String getDescription();
}
