def check_hotspot_links(halls, transition_points):
    # Шаг 1: Создаем множество существующих ID залов
    existing_hall_ids = set()
    for hall in halls:
        existing_hall_ids.add(hall['hall_id'])

    errors = []

    # Шаг 2: Проверяем каждую точку перехода
    for point in transition_points:
        target_id = point['target_hall_id']
        if target_id not in existing_hall_ids:
            # Если ID нет в множестве, фиксируем ошибку
            errors.append(f"Ошибка: Точка {point['point_id']} ведет в несуществующий зал {target_id}")

    return errors

# Блок для запуска из командной строки (демонстрация работы)
if __name__ == "__main__":
    # 1 Тестовые данные (имитация записей из БД)
    test_halls = [
        {'hall_id': 1, 'name': 'Зал древностей'},
        {'hall_id': 2, 'name': 'Зал живописи'}
    ]

    # 3 точки перехода: две ведут в существующие залы (1 и 2), одна - в несуществующий (99)
    test_points = [
        {'point_id': 10, 'hall_id': 1, 'target_hall_id': 2},
        {'point_id': 11, 'hall_id': 2, 'target_hall_id': 1},
        {'point_id': 12, 'hall_id': 1, 'target_hall_id': 99}
    ]

    print("Запуск алгоритма проверки связей точек перехода")
    result = check_hotspot_links(test_halls, test_points)

    if result:
        print(f"Найдено ошибок: {len(result)}")
        for err in result:
            print(f" - {err}")
    else:
        print("Все связи корректны. Ошибок не найдено.")