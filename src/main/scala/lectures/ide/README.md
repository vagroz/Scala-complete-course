Домашнее задание по лекции "Среда разработки и тестирования"

[Markdown syntax](https://github.com/adam-p/markdown-here/wiki/Markdown-Cheatsheet)

### 1. Составить перечень горячих клавиш (минимум 10):

* Ctrl + N - Перейти к классу
* Ctrl + J - Показать экран Live templates
* Ctrl+Shift+S - new Scala-class
* Ctrl+Shift+, - new package
* Ctrl+Alt+L - автовыравнивание кода и Json в REST-обозревателе
* Alt+` - vcs меню
* Ctrl+K - git commit (all)
* Ctrl+Alt+K - git commit (current file)
* Ctrl+Alt+A - git add
* Ctrl+Shift+F10 - запустить программу из текущего файла
* Ctrl+F9 - собрать проект (инкрементальная компиляция)
* Сtrl+Alt+Shift+Insert - создать scratch
* Shift(x2) - поиск везде (по имень класса/функции и т.д.)
* Ctrl+Shift+F - поисе везде (по тексту)
* Ctrl+B - перейти к определению метода/класса
* Ctrl+Alt+B - перейти к иплементации метода
* Alt+F7 - найти использования класса/метода
* Ctrl+Shift+T - перейти к тесту
* Ctrl+Alt+S - settings

А еще Key Promoter plugin, высвечивает подсказку каждый раз при совершении действия мышкой, если на него назначен шорткат. 
Также предлагает назначить шорткаты на часто используемы действия, если таковые отсутствуют.


### 2. Завести себе минимум 3 шаблона быстрой подстановки (Live templates)

1. `apply`:

```
def apply (args): MyClass = new MyClass(args)
```

2. `mapct`:  мапа для монад с тьюплами
```
.map {
  case (x,y) => $END$
}
```
3. `++`:
```
+= 1
```

### 3. Настроить 2 таски на прогон всех тестов: через IDEA и через SBT
1) через Idea:
 * Run -> Edit Configurations
 * Новая конфигурация: ScalaTest
 * Test kind: all in packages
 * Search for tests: in whole project
 * мне еще понадобилось поставить чекбокс "use Sbt", без этого у идеи что-то не биндилось к localhost.
 
2) через Sbt:
`test`