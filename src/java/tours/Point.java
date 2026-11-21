package tours;

/**
 Абстрактная базовая точка на панораме зала.
 Обобщает две сущности логической модели - «Точка перехода» и «Точка экспоната».
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
     Описание точки. У точки перехода - «вести в зал X»,
     у точки экспоната - «открыть карточку экспоната Y». Реализация своя у каждого подкласса.
     */
    public abstract String getDescription();
}
