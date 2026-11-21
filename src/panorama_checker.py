def validate_panorama_format(panoramas):
    errors = []
    allowed_extensions = {'.jpg', '.jpeg', '.png'}

    for p in panoramas:
        # 1. Проверка расширения файла
        if p['extension'].lower() not in allowed_extensions:
            errors.append(f"Ошибка: Панорама {p['panorama_id']} имеет недопустимое расширение ({p['extension']})")

        # 2. Проверка соотношения сторон
        if abs((p['width'] / p['height']) - 2.0) > 0.01:
            errors.append(f"Ошибка: Панорама {p['panorama_id']} имеет неверное соотношение сторон (должно быть 2:1)")

    return errors


if __name__ == "__main__":
    # Тест для проверки формата панорам
    print("\nЗапуск проверки формата панорам...")
    test_panoramas = [
        {'panorama_id': 1, 'width': 4000, 'height': 2000, 'extension': '.jpg'},
        {'panorama_id': 2, 'width': 4000, 'height': 3000, 'extension': '.png'}, # Ошибка соотношения
        {'panorama_id': 3, 'width': 8000, 'height': 4000, 'extension': '.gif'}  # Ошибка расширения
    ]

    pan_errors = validate_panorama_format(test_panoramas)
    if pan_errors:
        print(f"Найдено ошибок: {len(pan_errors)}")
        for err in pan_errors:
            print(f" - {err}")
    else:
        print("Все панорамы корректны.")