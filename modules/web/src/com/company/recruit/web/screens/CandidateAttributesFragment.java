package com.company.recruit.web.screens;

import com.company.recruit.entity.InmemoryCandidate;
import com.company.recruit.service.mapper.MapperService;
import com.haulmont.cuba.core.global.Messages;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.components.TextField;
import com.haulmont.cuba.gui.components.Validatable;
import com.haulmont.cuba.gui.components.ValidationException;
import com.haulmont.cuba.gui.screen.ScreenFragment;
import com.haulmont.cuba.gui.screen.UiController;
import com.haulmont.cuba.gui.screen.UiDescriptor;
import org.springframework.util.StringUtils;

import javax.inject.Inject;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Фрагмент, артибуты нового кандидата
 */
@UiController("cubarecruit_CandidateAttributesFragment")
@UiDescriptor("candidate-attributes-fragment.xml")
public class CandidateAttributesFragment extends ScreenFragment {

    /**
     * Разделитель строк
     * TODO перенести в сервисный слой из контроллера
     */
    private static final String CR_LF = "\r\n";

    /**
     * Локализация
     */
    @Inject
    protected Messages messages;

    /**
     * Сервис трансляции
     */
    @Inject
    MapperService mapperService;

    /**
     * Уведомления
     */
    @Inject
    private Notifications notifications;

    /**
     * Фамилия
     */
    @Inject
    private TextField<String> txtLastName;

    /**
     * мя
     */
    @Inject
    private TextField<String> txtFirstName;

    /**
     * Отчество
     */
    @Inject
    private TextField<String> txtMiddleName;

    /**
     * e-mail
     */
    @Inject
    private TextField<String> txtEmail;

    /**
     * Возраст
     */
    @Inject
    private TextField<Integer> txtAge;

    /**
     * Получить кандидата
     * @return сущность заполненная значениями
     */
    Optional<InmemoryCandidate> getCandidate() {
        Optional<InmemoryCandidate> result = Optional.empty();
        if(validate()){
            result = Optional.of(newCandidate());
        }
        return result;
    }

    /**
     * Очистить значения
     * TODO заменить "" на EMPTY_STRING
     */
    void clearValues() {
        txtFirstName.setValue("");
        txtMiddleName.setValue("");
        txtLastName.setValue("");
        txtEmail.setValue("");
        txtAge.setValue(0);
    }

    /**
     * Востановить значениями из сущности
     * @param candidate сущность источник
     */
    void restore(InmemoryCandidate candidate) {
        txtFirstName.setValue(candidate.getFirstName());
        txtMiddleName.setValue(candidate.getMiddleName());
        txtLastName.setValue(candidate.getLastName());
        txtEmail.setValue(candidate.getEmail());
        txtAge.setValue(candidate.getAge());
    }

    /**
     * Новый кандидат
     * @return кандидат
     */
    private InmemoryCandidate newCandidate() {
        com.company.recruit.entity.InmemoryCandidate candidate = new InmemoryCandidate()
                .setFirstName(txtFirstName.getValue())
                .setMiddleName(txtMiddleName.getValue())
                .setLastName(txtLastName.getValue())
                .setEmail(txtEmail.getValue())
                .setAge(txtAge.getValue());
        String fullName = mapperService.toFullName(candidate);
        candidate.setFullName(fullName);
        return candidate;
    }

    /**
     * Проверка атрибутов
     * TODO средствами платформы
     * @return успех/неуспех
     */
    private boolean validate() {
        boolean result = true;
        String error = Stream.of(txtLastName, txtFirstName, txtMiddleName, txtEmail, txtAge)
                .map(this::tryValidate)
                .filter(Objects::nonNull)
                .collect(Collectors.joining(CR_LF));
        if(!StringUtils.isEmpty(error)){
            showError(error);
            result = false;
        }
        return result;
    }

    /**
     * Вызов проверки для контроллера
     * TODO средствами платформы
     * @param validatable контроллер
     * @return ошибк/null
     */
    private String tryValidate(Validatable validatable){
        String result = null;
        try {
            validatable.validate();
        } catch (ValidationException e) {
            result = e.getMessage();
        }
        return result;
    }

    /**
     * Сообщение об ошибке
     * TODO централизованное сообщение об ошибках
     * @param error текст ошибки
     */
    private void showError(String error){
        String caption = getMessage("error.caption");
        notifications.create().withType(Notifications.NotificationType.ERROR)
                .withCaption(caption).withDescription(error).show();
    }

    /**
     * Локализация для указанного ключа
     * @param key ключ
     * @return строка локализации
     */
    private String getMessage(String key){
        return messages.getMessage(getClass(), key);
    }
}