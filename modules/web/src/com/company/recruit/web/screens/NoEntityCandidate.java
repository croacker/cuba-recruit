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
import java.util.stream.Collectors;
import java.util.stream.Stream;

@UiController("cubarecruit_NoEntityCandidate")
@UiDescriptor("NoEntityCandidate.xml")
public class NoEntityCandidate extends Screen {

    public static final String CR_LF = "\r\n";

    @Inject
    protected Messages messages;

    @Inject
    private PopupView saveCandidatePopup;

    @Inject
    private PopupView restoreCandidatePopup;

    @Inject
    private CandidateService candidateService;
    @Inject
    MapperService mapperService;

    @Inject
    private KeyValueCollectionContainer candidateCollection;

    @Inject
    private UiComponents uiComponents;

    @Inject
    private Notifications notifications;

    @Inject
    private TextField<String> txtFullName;

    @Inject
    private TextField<String> txtLastName;
    @Inject
    private TextField<String> txtMiddleName;
    @Inject
    private TextField<String> txtEmail;
    @Inject
    private TextField<String> txtFirstName;
    @Inject
    private TextField<Integer> txtAge;

    @Subscribe
    private void onInit(InitEvent event) {
        System.out.println("On init");
        reloadData();
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
            candidateService.replace(candidate.getId(), getNewCandidate());
            hideSavePopup();
            clearValues();
            reloadData();
            showInfo("Кандидат заменен");
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
    private Component selectCandidateSelectColumnGenerator(InmemoryCandidate candidate) {
        String caption = getMessage("restoreCandidate.columns.btnRestore");
        Button component = uiComponents.create(Button.NAME);
        component.setCaption(caption);
        component.addClickListener(e -> {
            restoreValues(candidate);
            hideRestorePopup();
            showInfo("Значения восстановлены");
        });
        return component;
    }

    @Install(to = "restoreCandidateList.columnFullName", subject = "columnGenerator")
    private Component nameNameColumnGenerator(InmemoryCandidate candidate) {
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
     *
     */
    private void showSavePopup() {
        if (validate()) {
            reloadData();
            InmemoryCandidate candidate = getNewCandidate();
            txtFullName.setValue(candidate.getFullName());
            saveCandidatePopup.setPopupVisible(true);
        }
    }

    private void hideSavePopup() {
        saveCandidatePopup.setPopupVisible(false);
    }

    private void showRestorePopup(){
        reloadData();
        restoreCandidatePopup.setPopupVisible(true);
    }

    private void hideRestorePopup() {
        restoreCandidatePopup.setPopupVisible(false);
    }

    /**
     *
     */
    private void saveCandidate(){
        InmemoryCandidate candidate = getNewCandidate();
        candidateService.save(candidate);
        hideSavePopup();
        clearValues();
        reloadData();
        showInfo("Кандидат сохранен");
    }

    private void restoreValues(InmemoryCandidate candidate) {
        txtFirstName.setValue(candidate.getFirstName());
        txtMiddleName.setValue(candidate.getMiddleName());
        txtLastName.setValue(candidate.getLastName());
        txtEmail.setValue(candidate.getEmail());
        txtAge.setValue(candidate.getAge());
    }

    private void clearValues() {
        txtFirstName.setValue("");
        txtMiddleName.setValue("");
        txtLastName.setValue("");
        txtEmail.setValue("");
        txtAge.setValue(0);
    }

    private void restoreFromDefault(){
         InmemoryCandidate candidate = candidateService.getDefault();
         if (candidate != null){
             txtFirstName.setValue(candidate.getFirstName());
             txtMiddleName.setValue(candidate.getMiddleName());
             txtLastName.setValue(candidate.getLastName());
             txtEmail.setValue(candidate.getEmail());
             txtAge.setValue(candidate.getAge());
             showInfo("Восстановлены значения по-умолчанию");
         }else {
             showError("Нет кандидата по-умолчанию");
         }
    }

    // TODO возраст не берется
    private InmemoryCandidate getNewCandidate() {
        InmemoryCandidate candidate = new InmemoryCandidate("",
                txtFirstName.getValue(),
                txtMiddleName.getValue(),
                txtLastName.getValue(),
                txtEmail.getValue(),
                txtAge.getValue(),
                false);
        String fullName = mapperService.toFullName(candidate);
        candidate.setFullName(fullName);
        return candidate;
    }

    private void showInfo(String description){
        String caption = getMessage("notification.caption");
        notifications.create().withType(Notifications.NotificationType.TRAY)
                .withCaption(caption).withDescription(description).show();
    }

    private void showError(String description){
        String caption = getMessage("error.caption");
        notifications.create().withType(Notifications.NotificationType.ERROR)
                .withCaption(caption).withDescription(description).show();
    }

    /**
     *
     * @return
     */
    private boolean validate() {
        boolean result = true;
        String error = Stream.of(txtLastName, txtFirstName, txtMiddleName, txtEmail, txtAge)
                .map(this::tryValidate)
                .filter(err -> err != null)
                .collect(Collectors.joining(CR_LF));
        if(!StringUtils.isEmpty(error)){
            showError(error);
            result = false;
        }
        return result;
    }

    private String tryValidate(Validatable validatable){
        String result = null;
        try {
            validatable.validate();
        } catch (ValidationException e) {
            result = e.getMessage();
        }
        return result;
    }

    private String getMessage(String key){
        return messages.getMessage(getClass(), key);
    }

}