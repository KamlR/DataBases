## Задание 1
### Код:
```
@startuml
entity Book {
  * ISNB
  --
  * year
  --
  * title
  --
  * author
  --
  * pages
}

entity BookCopy{
  * copy_id
  --
  * place
}

entity Publisher{
  * name
  --
  * address
}

entity Reader{
  * reader_id
  --
  * name
  --
  * surname
  --
  * address
  --
  * birtday
  return_day
}

entity Category{
  * category
}

Book||--|{BookCopy
Reader|o--|{BookCopy
Book}|--||Publisher
Book}|--|{Category

@enduml
```

### Отображение:
<img width="492" alt="Снимок экрана 2023-09-26 в 17 30 18" src="https://github.com/KamlR/DataBases/assets/115434090/d27c6bd4-1c81-4b98-a9a5-249f2c76c8e5">
