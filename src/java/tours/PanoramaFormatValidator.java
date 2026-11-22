package tours;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 Операция №1 «Проверить формат панорам».

 Соответствует процедурной функции validate_panorama_format() из КТ2.
 Проверяет для каждой панорамы каждого зала:
   1) допустимое расширение файла (.jpg, .jpeg, .png);
   2) соотношение сторон 2:1 (ширина / высота == 2.0 с допуском 0.01).
 Возвращает список сообщений об ошибках. Пустой список = ошибок нет.

 Одна из реализаций интерфейса TourValidator.
 */
public class PanoramaFormatValidator implements TourValidator {

    // Допустимые расширения панорам.
    private static final Set<String> ALLOWED_EXTENSIONS =
            Set.of(".jpg", ".jpeg", ".png");

    // Допустимое отклонение соотношения сторон от 2.0.
    private static final double ASPECT_TOLERANCE = 0.01;

    @Override
    public List<String> validate(Tour tour) {
        List<String> errors = new ArrayList<>();
        for (Hall hall : tour.getHalls()) {
            String extension = hall.getExtension();
            if (extension == null) {
                errors.add("Ошибка: Панорама зала " + hall.getHallId()
                        + " имеет пустое расширение (null)");
                continue; // без расширения проверять пропорции бессмысленно
            }
            String ext = extension.toLowerCase();
            if (!ALLOWED_EXTENSIONS.contains(ext)) {
                errors.add("Ошибка: Панорама зала " + hall.getHallId()
                        + " имеет недопустимое расширение ("
                        + extension + ")");
                continue; // некорректное расширение - бессмысленно проверять пропорции
            }
            int width = hall.getWidth();
            int height = hall.getHeight();
            if (height <= 0) {
                errors.add("Ошибка: Панорама зала " + hall.getHallId()
                        + " имеет некорректную высоту (" + height
                        + "): деление на ноль невозможно");
                continue;
            }
            if (width <= 0) {
                errors.add("Ошибка: Панорама зала " + hall.getHallId()
                        + " имеет некорректную ширину (" + width + ")");
                continue;
            }
            double ratio = (double) width / height;
            if (Math.abs(ratio - 2.0) > ASPECT_TOLERANCE) {
                errors.add("Ошибка: Панорама зала " + hall.getHallId()
                        + " имеет неверное соотношение сторон ("
                        + width + "x" + height + ")");
            }
        }
        return errors;
    }
}