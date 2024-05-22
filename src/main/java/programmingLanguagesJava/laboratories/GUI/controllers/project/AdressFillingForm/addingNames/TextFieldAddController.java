/**
 * Данный класс отвечает за добавление элементов в combobox.
 * Здесь все элементы уникальный, порядка нет при сохранении.
 */

package programmingLanguagesJava.laboratories.GUI.controllers.project.AdressFillingForm.addingNames;

import javafx.scene.control.TextField;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import programmingLanguagesJava.laboratories.GUI.controllers.project.AdressFillingForm.ElementAddressFillingForm;

import java.util.HashSet;

@RequiredArgsConstructor
public class TextFieldAddController implements ElementAddressFillingForm {

    private final TextField textField;

    /**
     * -- GETTER --
     *  Геттер, который возвращает нам информацию о людях в множестве
     */
    @Getter
    private final HashSet<String> persons = new HashSet<>();

    /**
     * Здесь запускается обработчик событий, который у нас добавляет элементы в множество
     */
    @Override
    public void event() {
        persons.add(textField.getText());
        textField.clear();
    }

}
