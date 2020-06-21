package com.company.recruit.web.screens;

import com.company.recruit.entity.InmemoryCandidate;
import com.company.recruit.service.CandidateService;
import com.company.recruit.service.mapper.MapperService;
import com.haulmont.cuba.core.global.Messages;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.UiComponents;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.model.KeyValueCollectionContainer;
import com.haulmont.cuba.gui.screen.*;
import org.springframework.util.StringUtils;

import javax.inject.Inject;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Окно регистрации кандидата
 * TODO вынести popup'ы в отдельные классы
 */
@UiController("cubarecruit_NoEntityCandidate")
@UiDescriptor("NoEntityCandidate.xml")
public class NoEntityCandidate extends Screen {

    /**
     * Сообщения
     */
    @Inject
    protected Messages messages;

    /**
     * Диалог сохранения кандидата
     */
    @Inject
    private PopupView saveCandidatePopup;

    /**
     * Диалог восстановления кандидата
     */
    @Inject
    private PopupView restoreCandidatePopup;

    /**
     * Сервис работы с хранимыми сущностями
     */
    @Inject
    private CandidateService candidateService;

    /**
     * Коллекция для привязки данных к таблицам
     */
    @Inject
    private KeyValueCollectionContainer candidateCollection;

    /**
     * Фабрика компонентов
     */
    @Inject
    private UiComponents uiComponents;

    /**
     * Уведомления
     */
    @Inject
    private Notifications notifications;

    /**
     * Наименование кандидата
     */
    @Inject
    private TextField<String> txtFullName;

    /**
     * Фрагмент с атрибутами кандидата
     */
    @Inject
    private CandidateAttributesFragment candidateAttributes;

    @Subscribe
    private void onInit(InitEvent event) {
        System.out.println("On init");
        refresh();
        prepareControls();
    }

    @Subscribe("valuesActions.saveValues")
    public void onValuesActionsSaveValues(Action.ActionPerformedEvent event) {
        showSavePopup();
    }

    @Subscribe("valuesActions.restoreValues")
    public void onValuesActionsRestoreValues(Action.ActionPerformedEvent event) {
        showRestorePopup();
    }

    @Subscribe("valuesActions.clearValues")
    public void onValuesActionsClearValues(Action.ActionPerformedEvent event) {
        clearValues();
    }

    @Subscribe("valuesActions.defaultValues")
    public void onValuesActionsDefaultValues(Action.ActionPerformedEvent event) {
        restoreFromDefault();
    }

    @Subscribe("btnCreateCandidate")
    public void onBtnCreateCandidateClick(Button.ClickEvent event) {
        saveCandidate();
    }

    @Install(to = "saveCandidateList.columnReplace", subject = "columnGenerator")
    private Component saveCandidateReplaceColumnGenerator(InmemoryCandidate candidate) {
        String caption = getMessage("saveCandidate.columns.btnReplace");
        Button component = uiComponents.create(Button.NAME);
        component.setCaption(caption);
        component.addClickListener(e -> {
            replace(candidate);
        });
        return component;
    }

    @Install(to = "saveCandidateList.columnFullName", subject = "columnGenerator")
    private Component saveCandidateFullNameColumnGenerator(InmemoryCandidate candidate) {
        Label component = uiComponents.create(Label.NAME);
        component.setValue(candidate.getFullName());
        return component;
    }

    @Install(to = "saveCandidateList.columnDefault", subject = "columnGenerator")
    private Component saveCandidateDefaultNameColumnGenerator(InmemoryCandidate candidate) {
        CheckBox component = uiComponents.create(CheckBox.NAME);
        component.setValue(candidate.isDefault());
        component.addValueChangeListener(e ->{
            candidateService.setDefaultCandidate(candidate.getId());
            reloadData();
        });

        return component;
    }

    @Install(to = "restoreCandidateList.columnSelect", subject = "columnGenerator")
    private Component restoreCandidateSelectColumnGenerator(InmemoryCandidate candidate) {
        String caption = getMessage("restoreCandidate.columns.btnRestore");
        Button component = uiComponents.create(Button.NAME);
        component.setCaption(caption);
        component.addClickListener(e -> {
            restoreValues(candidate);
            hidePopups();
            showInfo("Значения восстановлены");
        });
        return component;
    }

    @Install(to = "restoreCandidateList.columnFullName", subject = "columnGenerator")
    private Component restoreCandidateFullNameColumnGenerator(InmemoryCandidate candidate) {
        Label component = uiComponents.create(Label.NAME);
        component.setValue(candidate.getFullName());
        return component;
    }

    private void reloadData(){
        candidateCollection.getMutableItems().addAll(candidateService.findAll());
    }

    private void prepareControls() {
        saveCandidatePopup.setPopupPosition(100, 300);
        restoreCandidatePopup.setPopupPosition(100, 300);
        clearValues();
    }

    /**
     * Открыть диалог сохранения кандидата
     */
    private void showSavePopup() {
        getNewCandidate().ifPresent(candidate -> {
            reloadData();
            txtFullName.setValue(candidate.getFullName());
            saveCandidatePopup.setPopupVisible(true);
        });
    }

    /**
     * Открыть диалог выбора кандидата для восстановления значений
     */
    private void showRestorePopup(){
        reloadData();
        restoreCandidatePopup.setPopupVisible(true);
    }

    /**
     * Скрыть диалоги
     */
    private void hidePopups() {
        saveCandidatePopup.setPopupVisible(false);
        restoreCandidatePopup.setPopupVisible(false);
    }

    /**
     * Сохранить кандидата
     */
    private void saveCandidate(){
        candidateAttributes.getCandidate().ifPresent(candidate -> {
            candidateService.save(candidate);
            refresh();
            showInfo("Кандидат сохранен");
        });
    }

    /**
     * Заменить кандидата
     * @param candidate кандидат к-й будет заменен
     */
    private void replace(InmemoryCandidate candidate) {
        getNewCandidate().ifPresent(newCandidate -> {
            candidateService.replace(candidate.getId(), newCandidate);
            refresh();
            showInfo("Кандидат заменен");
        });
    }

    /**
     * Восстановить значения
     * @param candidate кандидат источник
     */
    private void restoreValues(InmemoryCandidate candidate) {
        candidateAttributes.restore(candidate);
    }

    /**
     * Очистить значения
     */
    private void clearValues() {
        candidateAttributes.clearValues();
    }

    /**
     * Восстановить из кандидата по-умолчанию
     */
    private void restoreFromDefault() {
        InmemoryCandidate candidate = candidateService.getDefault();
        if (candidate != null) {
            candidateAttributes.restore(candidate);
            showInfo("Восстановлены значения по-умолчанию");
        } else {
            showError("Нет кандидата по-умолчанию");
        }
    }

    /**
     * Получить нового кандидата
     * @return новый кандидат
     */
    private Optional<InmemoryCandidate> getNewCandidate() {
        return candidateAttributes.getCandidate();
    }

    /**
     * Информационное сообщение
     * TODO централизованное информационное сообщение
     * @param info текст сообщения
     */
    private void showInfo(String info){
        String caption = getMessage("notification.caption");
        notifications.create().withType(Notifications.NotificationType.TRAY)
                .withCaption(caption).withDescription(info).show();
    }

    /**
     * Обновить форму
     */
    private void refresh(){
        hidePopups();
        clearValues();
        reloadData();
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