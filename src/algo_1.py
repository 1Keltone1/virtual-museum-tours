def check_hotspot_links(halls, transition_points):
    # Шаг 1: Множество существующих залов
    existing_hall_ids = set(hall['hall_id'] for hall in halls)
    errors = []

    # Шаг 2: Проверка точек
    for point in transition_points:
        source_id = point['hall_id']
        target_id = point['target_hall_id']

        # Проверка 1: Существует ли целевой зал?
        if target_id not in existing_hall_ids:
            errors.append(f"Ошибка: Точка {point['point_id']} ведет в несуществующий зал {target_id}")

        # Проверка 2: Не ведет ли точка в тот же зал, где она находится?
        elif source_id == target_id:
            errors.append(f"Ошибка: Точка {point['point_id']} ведет в тот же зал {source_id}")

    return errors

# Блок для запуска из командной строки (демонстрация работы)
if __name__ == "__main__":
    print("========== Демо 1: корректный тур ==========")
    # Три зала, точки перехода образуют связный граф без петель и битых ссылок.
    correct_halls = [
        {'hall_id': 101, 'name': 'Главный зал'},
        {'hall_id': 102, 'name': 'Зал краеведения'},
        {'hall_id': 103, 'name': 'Зал скульптур'},
    ]
    correct_points = [
        {'point_id': 11, 'hall_id': 101, 'target_hall_id': 102},
        {'point_id': 12, 'hall_id': 102, 'target_hall_id': 103},
        {'point_id': 13, 'hall_id': 103, 'target_hall_id': 101},
    ]
    print("Проверка связей точек перехода...")
    result = check_hotspot_links(correct_halls, correct_points)
    if result:
        print(f"Найдено ошибок: {len(result)}")
        for err in result:
            print(f"  - {err}")
    else:
        print("Все связи корректны. Ошибок не найдено.")

    print()
    print("========== Демо 2: тур с ошибками ==========")
    broken_halls = [
        {'hall_id': 301, 'name': 'Зал с неверным расширением'},
        {'hall_id': 302, 'name': 'Зал с неверными пропорциями'},
        {'hall_id': 303, 'name': 'Зал с петлей'},
        {'hall_id': 304, 'name': 'Зал с битой ссылкой'},
        {'hall_id': 305, 'name': 'Зал-тупик'},
    ]

    broken_points = [
        {'point_id': 11, 'hall_id': 301, 'target_hall_id': 302},
        {'point_id': 12, 'hall_id': 302, 'target_hall_id': 303},
        {'point_id': 13, 'hall_id': 303, 'target_hall_id': 303}, # петля
        {'point_id': 16, 'hall_id': 303, 'target_hall_id': 304},
        {'point_id': 14, 'hall_id': 304, 'target_hall_id': 999}, # битая ссылка
        {'point_id': 15, 'hall_id': 304, 'target_hall_id': 305},
    ]
    print("Проверка связей точек перехода...")
    result = check_hotspot_links(broken_halls, broken_points)
    if result:
        print(f"Найдено ошибок: {len(result)}")
        for err in result:
            print(f"  - {err}")
    else:
        print("Все связи корректны. Ошибок не найдено.")