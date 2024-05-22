/**
 * Здесь реализован поиск в TextField.
 * Делал по гайду: https://youtu.be/2M0L6w3tMOY?si=kHdAuUkam6kUVjXG
 */

package programmingLanguagesJava.laboratories.GUI.controllers.project.viewingDatabase.searchingDatabase;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import lombok.RequiredArgsConstructor;
import programmingLanguagesJava.laboratories.GUI.controllers.project.database.utils.PersonInfo;
import programmingLanguagesJava.laboratories.GUI.controllers.project.viewingDatabase.ElementDatabaseView;
import programmingLanguagesJava.laboratories.GUI.controllers.project.viewingDatabase.searchingDatabase.strategy.FirstNameSearchStrategy;
import programmingLanguagesJava.laboratories.GUI.controllers.project.viewingDatabase.searchingDatabase.strategy.LastNameSearchStrategy;
import programmingLanguagesJava.laboratories.GUI.controllers.project.viewingDatabase.searchingDatabase.strategy.PatronymicSearchStrategy;
import programmingLanguagesJava.laboratories.GUI.controllers.project.viewingDatabase.searchingDatabase.strategy.SearchStrategy;

import java.util.Arrays;
import java.util.List;

/**
 * Класс HumanSearchController реализует интерфейс ElementDatabaseView и предоставляет функциональность для поиска людей в базе данных.
 * Этот класс использует паттерн проектирования Builder для создания экземпляров класса.
 */
@RequiredArgsConstructor
public class HumanSearchController implements ElementDatabaseView {

    // TableView для отображения информации о людях
    private final TableView<PersonInfo> customersTableView;

    // ObservableList для хранения исходных данных о людях
    private final ObservableList<PersonInfo> personInfos;

    // TextField для ввода ключевых слов для поиска
    private final TextField keywordTextField;

    // Список стратегий поиска для выполнения поиска
    private final List<SearchStrategy> searchStrategies = Arrays.asList(new FirstNameSearchStrategy(), new LastNameSearchStrategy(), new PatronymicSearchStrategy());

    /**
     * Метод event() слушает изменения в поле keywordTextField и обновляет отфильтрованные данные в соответствии
     * с введенным ключевым словом.
     * Если ключевое слово пустое, метод возвращает все данные.
     * В противном случае он возвращает только те данные, которые соответствуют хотя бы одной из стратегий поиска.
     * После фильтрации данных метод связывает comparator SortedList с comparator TableView и устанавливает
     * отсортированные данные в TableView.
     */
    @Override
    public void event() {
        // Создание FilteredList из ObservableList
        var filteredData = new FilteredList<>(personInfos);

        keywordTextField.textProperty().addListener((observable, oldValue, newValue) ->
                filteredData.setPredicate(personInfo -> {

                    if (newValue.isEmpty() || newValue.isBlank()) {
                        return true;
                    }

                    return searchStrategies.stream().anyMatch(strategy -> strategy.matches(personInfo, newValue));
                }));

        // Создание SortedList из FilteredList
        var sortedData = new SortedList<>(filteredData);

        // Привязка компаратора SortedList к компаратору TableView
        sortedData.comparatorProperty().bind(customersTableView.comparatorProperty());

        // Установка SortedList в качестве элементов TableView
        customersTableView.setItems(sortedData);
    }
}

