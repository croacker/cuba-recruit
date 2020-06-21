package com.company.recruit.web.screens;

import com.company.recruit.service.CandidateRepositoryService;
import com.haulmont.cuba.gui.UiComponents;
import com.haulmont.cuba.gui.model.KeyValueCollectionContainer;
import com.haulmont.cuba.gui.screen.ScreenFragment;
import com.haulmont.cuba.gui.screen.Subscribe;
import com.haulmont.cuba.gui.screen.UiController;
import com.haulmont.cuba.gui.screen.UiDescriptor;

import javax.inject.Inject;

@UiController("cubarecruit_Candidateslist")
@UiDescriptor("candidatesList.xml")
public class Candidateslist extends ScreenFragment {

    @Inject
    private UiComponents uiComponents;

    @Inject
    private CandidateRepositoryService candidateRepositoryService;

    @Inject
    private KeyValueCollectionContainer candidateCollection;

    @Subscribe
    private void onInit(ScreenFragment.InitEvent event) {
        System.out.println("On init");
        candidateCollection.getMutableItems().addAll(candidateRepositoryService.findAll());
    }
}