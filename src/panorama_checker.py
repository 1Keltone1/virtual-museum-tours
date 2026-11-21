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
        {'panorama_id': 300L, 'width': 4000, 'height': 2000, 'extension': '.jpg'},
        {'panorama_id': 301L, 'width': 4096, 'height': 2048, 'extension': '.gif'}, # Ошибка расширения
        {'panorama_id': 302L, 'width': 1000, 'height': 1000, 'extension': '.gif'}  # Ошибка соотношения
    ]

    pan_errors = validate_panorama_format(test_panoramas)
    if pan_errors:
        print(f"Найдено ошибок: {len(pan_errors)}")
        for err in pan_errors:
            print(f" - {err}")
    else:
        print("Все панорамы корректны.")