package tours;

/**
 * Демонстрационные туры для проверки валидаторов.
 *
 * buildCorrect() возвращает тот же набор данных, что и процедурная версия
 * на Python: три зала (Главный, Краеведение, Скульптуры), три точки
 * перехода, образующие связный граф без тупиков, и один экспонат с тремя
 * медиафайлами. Это позволяет проверяющему запустить
 *
 *     java -cp out tours.Main
 *
 * без подготовки файла и сверить вывод с процедурной версией.
 *
 * Main за один прогон проверяет оба тура:
 *   - buildCorrect() — корректный тур, все валидаторы вернут пустой список;
 *   - buildBroken()   — тур, содержащий по одной ошибке каждого из 5 типов,
 *     которые умеют ловить валидаторы (ошибки формата панорам, петли,
 *     битые ссылки, тупики, нарушения связности).
 *
 * Технический класс-фабрика, не входит в предметную модель.
 */
public class DemoTour {

    /** Корректный тур: три зала, связный граф 1→2→3→1, один экспонат. */
    public Tour buildCorrect() {
        Tour tour = new Tour(1L, "Виртуальная экскурсия: Краеведческий музей");

        // Три зала с панорамами 2:1.
        Hall hall1 = new Hall(101L, tour, "Главный зал",
                "/panoramas/hall1.jpg", 4096, 2048, ".jpg");
        Hall hall2 = new Hall(102L, tour, "Зал краеведения",
                "/panoramas/hall2.jpg", 4096, 2048, ".jpg");
        Hall hall3 = new Hall(103L, tour, "Зал скульптур",
                "/panoramas/hall3.png", 4096, 2048, ".png");
        tour.addHall(hall1);
        tour.addHall(hall2);
        tour.addHall(hall3);

        // Экспонат с тремя медиафайлами (аудио, фото, 3D-модель).
        Exhibit exhibit = new Exhibit(201L, tour,
                "Сабля офицерская, нач. XX в.",
                "Холодное оружие, состоявшее на вооружении офицеров русской армии.");
        tour.addExhibit(exhibit);
        MediaFile audio = new MediaFile(301L, "/media/sabre_audio.mp3", MediaType.AUDIO);
        MediaFile photo = new MediaFile(302L, "/media/sabre_photo.jpg", MediaType.PHOTO);
        MediaFile model = new MediaFile(303L, "/media/sabre_3d.gltf", MediaType.MODEL_3D);
        exhibit.addMedia(audio, 0);
        exhibit.addMedia(photo, 1);
        exhibit.addMedia(model, 2);

        // Точки перехода: 1→2, 2→3, 3→1. Граф связный, тупиков нет.
        hall1.addPoint(new TransitionPoint(11L, hall1, 100.0, 50.0, hall2));
        hall2.addPoint(new TransitionPoint(12L, hall2, 100.0, 50.0, hall3));
        hall3.addPoint(new TransitionPoint(13L, hall3, 100.0, 50.0, hall1));

        // Точка экспоната в первом зале.
        hall1.addPoint(new ExhibitPoint(21L, hall1, 200.0, 150.0, exhibit));

        tour.markReady();
        return tour;
    }

    /**
     * Тур с ошибками всех 5 типов, которые умеют ловить валидаторы.
     * Каждый валидатор находит свою ошибку:
     *
     *   PanoramaFormatValidator:
     *     - зал 301: недопустимое расширение .gif;
     *     - зал 302: неверное соотношение сторон 1:1 (должно быть 2:1).
     *   HotspotLinksValidator:
     *     - точка 13 в зале 303 — петля (ведёт в тот же зал 303);
     *     - точка 14 в зале 304 — битая ссылка (ведёт в несуществующий зал 999).
     *   GraphConnectivityValidator:
     *     - зал 305 — тупик (нет исходящих переходов);
     *     - нарушение связности в залах 302–305 как следствие однонаправленной
     *       цепи и тупика (зал 301 — единственный, из которого видны все).
     *
     * Граф переходов:
     *   301 → 302 → 303 → 304 → 305
     *              ↻ 303 (петля)        304 → 999 (битая ссылка)
     *   305: нет исходящих (тупик)
     */
    public Tour buildBroken() {
        Tour tour = new Tour(2L, "Тестовый тур с ошибками всех типов");

        // Зал 301: недопустимое расширение .gif.
        Hall hall1 = new Hall(301L, tour, "Зал с неверным расширением",
                "/panoramas/hall1.gif", 4096, 2048, ".gif");
        // Зал 302: неверное соотношение сторон (1:1 вместо 2:1).
        Hall hall2 = new Hall(302L, tour, "Зал с неверными пропорциями",
                "/panoramas/hall2.jpg", 1000, 1000, ".jpg");
        // Зал 303: содержит петлю (точка 13 ведёт в него же).
        Hall hall3 = new Hall(303L, tour, "Зал с петлёй",
                "/panoramas/hall3.jpg", 4096, 2048, ".jpg");
        // Зал 304: точка 14 ведёт в несуществующий зал 999.
        Hall hall4 = new Hall(304L, tour, "Зал с битой ссылкой",
                "/panoramas/hall4.jpg", 4096, 2048, ".jpg");
        // Зал 305: нет исходящих переходов (тупик).
        Hall hall5 = new Hall(305L, tour, "Зал-тупик",
                "/panoramas/hall5.jpg", 4096, 2048, ".jpg");
        tour.addHall(hall1);
        tour.addHall(hall2);
        tour.addHall(hall3);
        tour.addHall(hall4);
        tour.addHall(hall5);

        // Точки перехода.
        // 11: 301→302 — корректный.
        hall1.addPoint(new TransitionPoint(11L, hall1, 100.0, 50.0, hall2));
        // 12: 302→303 — корректный.
        hall2.addPoint(new TransitionPoint(12L, hall2, 100.0, 50.0, hall3));
        // 13: 303→303 — ПЕТЛЯ (ошибка HotspotLinksValidator).
        hall3.addPoint(new TransitionPoint(13L, hall3, 100.0, 50.0, hall3));
        // 16: 303→304 — корректный, чтобы зал 303 не считался тупиком.
        hall3.addPoint(new TransitionPoint(16L, hall3, 200.0, 50.0, hall4));
        // 14: 304→999 — БИТАЯ ССЫЛКА (несуществующий зал).
        hall4.addPoint(new TransitionPoint(14L, hall4, 100.0, 50.0,
                new Hall(999L, tour, "<несуществующий зал>", null, 0, 0, "")));
        // 15: 304→305 — корректный.
        hall4.addPoint(new TransitionPoint(15L, hall4, 200.0, 50.0, hall5));
        // У зала 305 нет точек перехода → ТУПИК.

        tour.markReady();
        return tour;
    }
}
