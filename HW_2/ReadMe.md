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

## Задание 2:
1) *Квартира расположена в доме на улице в городе в стране*
![1-2](https://github.com/KamlR/DataBases/assets/115434090/b8a2a03c-9913-4707-852a-9a5237da8f6d)
#### Пояснение:
- в кадждой стране может быть много городов, но конкретный город принадлежит одной стране, т.е один город не может располагаться в разных странах
- в каждом городе много улиц, но конкретная улица может находиться в конкретном городе
- на каждой улице много домов, один дом стоит только на конкретной улице
- в доме может быть много квартир, конкретная квартира находится в конкретном доме

2) * Две команды играют друг против друга в футбол под руководством арбитра*
   ![second](https://github.com/KamlR/DataBases/assets/115434090/0b996fb9-5862-4989-9961-48ffb70001ab)

#### Пояснение:
- одна команда может играть только против одной во время игры
- команды судят несколько арбитров, арбитры судят сразу две команды, поэтому связь многие ко многим

3) * У каждого человека (мужчины и женщины) есть отец и мать*
  ![family](https://github.com/KamlR/DataBases/assets/115434090/f8f6aee5-03f8-4c62-a3d3-19241e06919f)
#### Пояснение:
- У каждого из родителей может быть много детей, но у каждого ребёнка только одни отец и мать

## Задание 3
*Смоделируйте E/R-модель в виде E/R диаграммы*
```
@startuml
entity Entity{}
entity Relationship{}
entity Attribute{}
entity Key{}
entity Role{}

Entity }|--|{Relationship
Entity }|--|{Attribute
Entity }|--||Key
Entity }|--|{Role
@enduml
```
### Отображение
<img width="654" alt="Снимок экрана 2023-09-26 в 20 11 20" src="https://github.com/KamlR/DataBases/assets/115434090/620b3033-02b9-45e7-afab-b4e670189b48">

