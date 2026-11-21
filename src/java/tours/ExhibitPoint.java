package tours;

// Точка экспоната на панораме. Соответствует сущности «Точка экспоната» из логической модели.
public class ExhibitPoint extends Point {

    private final Exhibit exhibit;

    public ExhibitPoint(long pointId, Hall hall, double xCoord, double yCoord, Exhibit exhibit) {
        super(pointId, hall, xCoord, yCoord);
        this.exhibit = exhibit;
    }

    public Exhibit getExhibit() {
        return exhibit;
    }

    /**
     Открывает карточку экспоната: возвращает текстовое представление,
     в котором собраны название, описание и список медиафайлов экспоната
     (аудио, фото, 3D-модель), отсортированных по displayOrder.
     */
    public String openCard() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== Карточка экспоната: ").append(exhibit.getName()).append(" ===\n");
        sb.append("Описание: ").append(exhibit.getDescription()).append("\n");
        sb.append("Медиафайлы:\n");
        exhibit.getMediaLinks().stream()
                .sorted((a, b) -> Integer.compare(a.getDisplayOrder(), b.getDisplayOrder()))
                .forEach(link -> sb.append("  - [")
                        .append(link.getMedia().getMediaType())
                        .append("] ")
                        .append(link.getMedia().getFileUrl())
                        .append("\n"));
        return sb.toString();
    }

    @Override
    public String getDescription() {
        return "Точка экспоната «" + exhibit.getName() + "»";
    }
}
