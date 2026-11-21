package tours;

import java.util.ArrayList;
import java.util.List;

/**
 Точка входа в приложение (CLI).

 Запускается из командной строки без аргументов и за один прогон.
 Проверяет оба встроенных демо-тура:
   1) корректный тур - валидаторы вернут пустой список ошибок,
      экскурсия публикуется;
   2) тур с ошибками всех 5 типов (ошибки формата панорам, петли,
      битые ссылки, тупики, нарушения связности) - валидаторы
      сообщат об ошибках, публикация отменяется.
 */
public class Main {

    // Валидаторы, применяемые к каждому туру.
    private static final List<TourValidator> VALIDATORS = List.of(
            new PanoramaFormatValidator(),
            new HotspotLinksValidator(),
            new GraphConnectivityValidator());

    public static void main(String[] args) {
        DemoTour demo = new DemoTour();

        System.out.println("========== Демо-тур 1: корректный тур ==========");
        runValidation(demo.buildCorrect());

        System.out.println();
        System.out.println("========== Демо-тур 2: тур с ошибками всех типов ==========");
        runValidation(demo.buildBroken());
    }

    /**
     Прогоняет все валидаторы по данному туру и выводит результат:
     заголовок тура, список ошибок (если есть) либо публикацию.
     */
    private static void runValidation(Tour tour) {
        System.out.println("Тур: " + tour.getTitle()
                + " (залов: " + tour.getHalls().size()
                + ", экспонатов: " + tour.getExhibits().size() + ")");

        // Список реализаций интерфейса TourValidator.
        List<String> errors = new ArrayList<>();
        for (TourValidator validator : VALIDATORS) {
            errors.addAll(validator.validate(tour));
        }

        System.out.println();
        if (errors.isEmpty()) {
            System.out.println("Ошибок не найдено. Тур корректен.");
            String url = tour.publish();
            System.out.println("Опубликовано по ссылке: " + url);
            System.out.println("Статус: " + tour.getStatus());
        } else {
            System.out.println("Найдено ошибок: " + errors.size());
            for (String error : errors) {
                System.out.println("  - " + error);
            }
            System.out.println("Публикация отменена: тур содержит ошибки.");
        }
    }
}
