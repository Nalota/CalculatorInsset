package org.insset.client.pourcentage;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ResetButton;
import com.google.gwt.user.client.ui.SubmitButton;
import com.google.gwt.user.client.ui.TextBox;
import org.insset.client.message.dialogbox.DialogBoxInssetPresenter;
import org.insset.client.service.PourcentageService;
import org.insset.client.service.PourcentageServiceAsync;
import org.insset.shared.FieldVerifier;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author talend
 */
public class PourcentagePresenter extends Composite {


    @UiField
    public SubmitButton boutonConvertRToA;
    @UiField
    public ResetButton boutonClearD;
    @UiField
    public TextBox valR;
    @UiField
    public TextBox valA;
    @UiField
    public Label errorLabelAToR;


    interface MainUiBinder extends UiBinder<HTMLPanel, PourcentagePresenter> {
    }

    private static MainUiBinder ourUiBinder = GWT.create(MainUiBinder.class);
    /**
     * Create a remote service proxy to talk to the server-side Greeting
     * service.
     */
    private final PourcentageServiceAsync service = GWT.create(PourcentageService.class);

    /**
     * Constructeur
     */
    public PourcentagePresenter() {
        initWidget(ourUiBinder.createAndBindUi(this));
        initHandler();
    }

    /**
     * Init des handler
     */
    private void initHandler() {


        boutonConvertRToA.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                calculRemise();
            }

        });
        boutonClearD.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                valA.setText("");
                errorLabelAToR.setText("");
                valR.setText("");
            }
        });

    }

    /**
     * call server
     */
    private void calculRemise() {
        try {
            int valAInt = Integer.parseInt(valA.getText()); // Conversion sécurisée
            double valRDouble = Double.parseDouble(valR.getText());  // Conversion sécurisée

            service.calculRemise(valAInt, valRDouble, new AsyncCallback<Double>() {
                public void onFailure(Throwable caught) {
                    errorLabelAToR.setText("Format incorrect");
                }

                public void onSuccess(Double result) {
                    errorLabelAToR.setText(" ");
                   new DialogBoxInssetPresenter("Prix d'origine", valR.getText(), String.valueOf(result));
                }
            });
        } catch (NumberFormatException e) {
            // Si une erreur de format se produit, on affiche un message d'erreur
            errorLabelAToR.addStyleName("serverResponseLabelError");
            errorLabelAToR.setText("Entrée invalide. Assurez-vous que les valeurs sont numériques.");
        }
    }
}